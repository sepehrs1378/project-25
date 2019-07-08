import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler extends Thread {
    private Socket socket;
    private NetworkDB networkDB = NetworkDB.getInstance();
    private Connection connection;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        connection = new Connection(socket);
        networkDB.addConnection(connection);
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        JsonStreamParser parser = connection.getParser();
//        try {
            while (true) {
                JsonObject obj = parser.next().getAsJsonObject();
                Request request = yaGson.fromJson(obj.toString(), Request.class);
                handleMultiPlayerCase(request);
                handleFindMatch(request);
                handleAccountCase(request);
                handleGlobalChat(request);
                handleCollection(request);
                handleShop(request);
                Platform.runLater(() -> {
                    Server.getInstance().updateCardList();
                    Server.getInstance().updateUserList();
                });
//                logCase(request);
                if (request.getRequestType().equals(RequestType.close))
                    break;
            }
//        } catch (Exception e) {
//            networkDB.closeConnection(socket);
//            updateAccountsInLeaderBoard();
//        }
    }

    private void handleShop(Request request) {
        switch (request.getRequestType()) {
            case shop:
                NetworkDB.getInstance().getAccountStatusMap().put(connection.getAccount(), AccountStatus.shop);
                List<Object> cardList = new ArrayList<>(NetworkDB.getInstance().getCardList());
                List<Object> usableList = new ArrayList<>(NetworkDB.getInstance().getUsableList());
                List<Integer> integerList = new ArrayList<>();
                integerList.add(cardList.size());
                integerList.add(usableList.size());
                List<Object> cardsAndUsables = new ArrayList<>();
                cardList.forEach(e-> System.out.println(((Card)e).getName()));
                cardsAndUsables.addAll(cardList);
                cardsAndUsables.addAll(usableList);
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.shop, null, integerList, cardsAndUsables), connection);
                break;
            case buy:
                OutputMessageType outputMessageType = connection.getAccount().getPlayerInfo().getCollection().buy(connection.getAccount(), request.getMessage());
                List<Object> accountList = new ArrayList<>();
                accountList.add(connection.getAccount());
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.buy, outputMessageType.getMessage(), null, accountList), connection);
                break;
        }
    }

    private void handleCollection(Request request) {
        String deckName;
        JsonObject jsonObject;
        Deck deck;
        switch (request.getRequestType()) {
            case enterCollectoin:
                NetworkDB.getInstance().getAccountStatusMap().put(connection.getAccount(), AccountStatus.collection);
                break;
            case createDeck:
                deck = new Deck(request.getMessage());
                connection.getAccount().getPlayerInfo().getCollection().getDecks().add(deck);
                break;
            case removeDeck:
                deckName = request.getMessage();
                for (Deck tempDeck : connection.getAccount().getPlayerInfo().getCollection().getDecks()) {
                    if (tempDeck.getName().equals(deckName)) {
                        connection.getAccount().getPlayerInfo().getCollection().getDecks().remove(tempDeck);
                        if (connection.getAccount().getMainDeck().getName().equals(deckName)) {
                            connection.getAccount().setMainDeck(null);
                        }
                        break;
                    }
                }
                break;
            case setMainDeck:
                deckName = request.getMessage();
                deck = connection.getAccount().getPlayerInfo().getCollection().getDeckByName(deckName);
                connection.getAccount().setMainDeck(deck);
                break;
            case importDeck:
                deck = (Deck) request.getObjectList().get(0);
                connection.getAccount().getPlayerInfo().getCollection().getDecks().add(deck);
                break;
            case exitCollection:
                NetworkDB.getInstance().getAccountStatusMap().put(connection.getAccount(), AccountStatus.online);
                break;
            case moveCardToDeck:
                jsonObject = (JsonObject) request.getObjectList().get(0);
                deckName = jsonObject.get("deckName").getAsString();
                String cardId = jsonObject.get("cardID").getAsString();
                connection.getAccount().getPlayerInfo().getCollection().addCard(cardId, deckName);
                break;
            case removeCardFromDeck:
                jsonObject = (JsonObject) request.getObjectList().get(0);
                deckName = jsonObject.get("deck").getAsString();
                String id = jsonObject.get("id").getAsString();
                connection.getAccount().getPlayerInfo().getCollection().removeCard(id, deckName);
                break;
            case sell:
                Account account = connection.getAccount();
                OutputMessageType outputMessageType = account.getPlayerInfo().getCollection().sell(account, request.getMessage());
                List<Object> accountList = new ArrayList<>();
                accountList.add(account);
                networkDB.sendResponseToClient(new Response(ResponseType.sell, outputMessageType.getMessage(), null, accountList), connection);
                break;
        }
    }

    private void handleGlobalChat(Request request) {
        Account account;
        switch (request.getRequestType()) {
            case sendMessage:
                ChatMessage chatMessage = (ChatMessage) request.getObjectList().get(0);
                for (Account tempAccount : networkDB.getAccountStatusMap().keySet()) {
                    if (networkDB.getAccountStatusMap().get(tempAccount) == AccountStatus.chatting
                            && !tempAccount.getUsername().equals(chatMessage.getSender())) {
                        Connection receiver = networkDB.getConnectionWithAccount(tempAccount);
                        networkDB.sendResponseToClient(new Response(ResponseType.sendMessage, null, null, request.getObjectList()), receiver);
                    }
                }
                break;
            case enterGlobalChat:
                account = networkDB.getAccountWithUserName(request.getMessage());
                networkDB.getAccountStatusMap().put(account, AccountStatus.chatting);
                break;
            case exitGlobalChat:
                account = networkDB.getAccountWithUserName(request.getMessage());
                networkDB.getAccountStatusMap().put(account, AccountStatus.online);
                break;
        }
    }

    private void handleAccountCase(Request request) {
        Pattern pattern;
        Matcher matcher;
        Account account;
        switch (request.getRequestType()) {
            case signUp:
                handleSignup(request);
                break;
            case login:
                handleLogin(request);
                break;
            case logout:
                handleLogout(request);
                break;
            case leaderBoard:
                handleLeaderboard();
                break;
        }
    }

    private void handleLogout(Request request) {
        Pattern pattern;
        Matcher matcher;
        Account account;
        pattern = Pattern.compile("userName:(\\w+)");
        matcher = pattern.matcher(request.getMessage());
        if (matcher.find()) {
            account = NetworkDB.getInstance().getAccount(matcher.group(1));
            if (account != null) {
                connection.setAccount(null);
                NetworkDB.getInstance().getAccountStatusMap().put(account, AccountStatus.offline);
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.logout, OutputMessageType.LOGGED_OUT_SUCCESSFULLY.getMessage(), null, null), connection);
                updateAccountsInLeaderBoard();
            }
        }
    }

    private void handleLeaderboard() {
        NetworkDB.getInstance().getAccountStatusMap().put(connection.getAccount(), AccountStatus.leaderBoard);
        List<Object> accountList = new ArrayList<>(NetworkDB.getInstance().getAccountStatusMap().keySet());
        List<Integer> integerList = new ArrayList<>();
        setIntegerList(accountList, integerList);
        NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.leaderBoard, null, integerList, accountList), connection);
    }

    private void handleLogin(Request request) {
        Pattern pattern = Pattern.compile("userName:(\\w+)password:(\\w+)");
        Matcher matcher = pattern.matcher(request.getMessage());
        matcher.find();
        Account account = NetworkDB.getInstance().getAccount(matcher.group(1));
        if (account == null) {
            NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.login, OutputMessageType.ACCOUNT_DOESNT_EXIST.getMessage(), null, null), connection);
        } else {
            AccountStatus accountStatus = NetworkDB.getInstance().getAccountStatusMap().get(account);
            if (!matcher.group(2).equals(account.getPassword())) {
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.login, OutputMessageType.INVALID_PASSWORD.getMessage(), null, null), connection);
                return;
            }
            if (accountStatus == AccountStatus.offline) {
                List<Object> accountList = new ArrayList<>();
                accountList.add(account);
                NetworkDB.getInstance().getAccountStatusMap().put(account, AccountStatus.online);
                connection.setAccount(account);
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.login, OutputMessageType.LOGGED_IN_SUCCESSFULLY.getMessage(), null, accountList), connection);
                updateAccountsInLeaderBoard();
            } else {
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.login, OutputMessageType.ALREADY_LOGGED_IN.getMessage(), null, null), connection);
            }
        }
    }

    private void handleSignup(Request request) {
        Pattern pattern;
        Matcher matcher;
        Account account;
        pattern = Pattern.compile("userName:(\\w+)password:(\\w+)");
        matcher = pattern.matcher(request.getMessage());
        matcher.find();
        account = NetworkDB.getInstance().getAccount(matcher.group(1));
        if (account != null) {
            NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.signUp, OutputMessageType.USERNAME_ALREADY_EXISTS.getMessage(), null, null), connection);
        } else {
            List<Object> accountList = new ArrayList<>();
            Account newAccount = new Account(matcher.group(1), matcher.group(2));
            connection.setAccount(newAccount);
            NetworkDB.getInstance().getAccountStatusMap().put(newAccount, AccountStatus.online);
            accountList.add(newAccount);
            NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.signUp, OutputMessageType.CREATED_ACCOUNT_SUCCESSFULLY.getMessage(), null, accountList), connection);
            updateAccountsInLeaderBoard();
        }
    }

    private void setIntegerList(List<Object> accountList, List<Integer> integerList) {
        for (Object object : accountList) {
            Account account = (Account) object;
            if (NetworkDB.getInstance().getAccountStatusMap().get(account).equals(AccountStatus.offline)) {
                integerList.add(0);
            } else {
                integerList.add(1);
            }
        }
    }

    private void updateAccountsInLeaderBoard() {
        List<Object> accountList = new ArrayList<>(NetworkDB.getInstance().getAccountStatusMap().keySet());
        List<Integer> integerList = new ArrayList<>();
        setIntegerList(accountList, integerList);
        for (Object object : accountList) {
            Account account = (Account) object;
            if (NetworkDB.getInstance().getAccountStatusMap().get(account).equals(AccountStatus.leaderBoard)) {
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.updateLeaderBoard, null, integerList, accountList), NetworkDB.getInstance().getConnectionWithAccount(account));
            }
        }
    }

    private void handleFindMatch(Request request) {
        switch (request.getRequestType()) {
            case findClassicMatch:
                networkDB.addAccountWaitingForClassic(connection.getAccount());
                break;
            case findOneFlagMatch:
                networkDB.addAccountWaitingForOneFlag(connection.getAccount());
                break;
            case findMultiFlagsMatch:
                networkDB.addAccountWaitingForMultiFlags(connection.getAccount());
                break;
            default:
        }
    }

    private void handleMultiPlayerCase(Request request) {
        switch (request.getRequestType()) {
            case moveUnit:
                handleMoveUnit(request);
                break;
            case attackUnit:
                handleAttackUnit(request);
                break;
            case endTurn:
                handleEndTurn(request);
                break;
            case insertCard:
                handleInsertCard(request);
                break;
            case useSpecialPower:
                handleUseSpecialPower(request);
                break;
            case useCollectable:
                handleUseCollectable(request);
                break;
            case selectUnit:
                handleSelectUnit(request);
                break;
            case enterGraveYard:
                handleEnterGraveYard(request);
                break;
            case forfeit:
                handleForfeit(request);
                break;
        }
    }

    private void handleUseCollectable(Request request) {
        //todo
    }

    private void handleUseSpecialPower(Request request) {
        //todo
    }

    private void handleInsertCard(Request request) {
        //todo
    }

    private void handleEndTurn(Request request) {
        //todo
    }

    private void handleForfeit(Request request) {
        //todo
    }

    private void handleAttackUnit(Request request) {
        //todo
    }

    private void handleEnterGraveYard(Request request) {
        //todo
    }

    private void handleSelectUnit(Request request) {
        Battle battle = connection.getCurrentBattle();
        String id = request.getMessage();
        switch (battle.getPlayerInTurn().selectUnit(id, battle)) {
            case SELECTED:
                List<Object> battles = new ArrayList<>();
                battles.add(battle);
                Response response = new Response
                        (ResponseType.unitSelected, id, null, battles);
                networkDB.sendResponseToClient(response, connection);
                break;
            case INVALID_COLLECTABLE_CARD:
                //empty
                break;
            case ENEMY_UNIT_SELECTED:
                //empty
                break;
            case UNIT_IS_STUNNED:
                //empty
                break;
            default:
        }
    }

    private void handleMoveUnit(Request request) {
        Battle battle = connection.getCurrentBattle();
        Integer destinationRow = (Integer) request.getObjectList().get(0);
        Integer destinationColumn = (Integer) request.getObjectList().get(1);
        switch (battle.getBattleGround().moveUnit(destinationRow, destinationColumn, battle)) {
            case UNIT_MOVED:
                Unit selectedUnit = battle.getPlayerInTurn().getSelectedUnit();
                List<Object> objects = new ArrayList<>();
                objects.add(destinationRow);
                objects.add(destinationColumn);
                objects.add(battle);
                Response response = new Response(ResponseType.unitMoved, selectedUnit.getId()
                        , null, objects);
                networkDB.sendResponseToPlayerAndOpponent(response, connection);
                break;
            case OUT_OF_BOUNDARIES:
                //empty
                break;
            case UNIT_NOT_SELECTED:
                //empty
                break;
            case UNIT_ALREADY_MOVED:
                //empty
                break;
            case CELL_IS_FULL:
                //empty
                break;
            case CELL_OUT_OF_RANGE:
                //empty
                break;
            default:
                System.out.println("unhandled case!!!");
        }
    }

    private void logCase(Request request) {
        System.out.println(connection.getAccount().getUsername() + "-->>" + request.getRequestType());
    }
}