import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import javafx.application.Platform;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler extends Thread {
    private Socket socket;
    private NetworkDB netWorkDB = NetworkDB.getInstance();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Connection connection = new Connection(socket);
        NetworkDB.getInstance().addConnection(connection);
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        JsonStreamParser parser = connection.getParser();
        try {
            while (true) {
                JsonObject obj = parser.next().getAsJsonObject();
                Request request = yaGson.fromJson(obj.toString(), Request.class);
                switch (request.getRequestType()) {
                    case sendMessage: {
                        ChatMessage chatMessage = (ChatMessage) request.getObjects().get(0);
                        for (Account account : netWorkDB.getAccountStatusMap().keySet()) {
                            if (netWorkDB.getAccountStatusMap().get(account) == AccountStatus.chatting
                                    && !account.getUsername().equals(chatMessage.getSender())) {
                                Connection receiver = netWorkDB.getConnectionWithAccount(account);
                                netWorkDB.sendResponseToClient(new Response(ResponseType.sendMessage, null, null, request.getObjects()), receiver);
                            }
                        }
                        break;
                    }
                    case enterGlobalChat: {
                        Account account = netWorkDB.getAccountWithUserName(request.getMessage());
                        netWorkDB.getAccountStatusMap().put(account, AccountStatus.chatting);
                        break;
                    }
                    case exitGlobalChat: {
                        Account account = netWorkDB.getAccountWithUserName(request.getMessage());
                        netWorkDB.getAccountStatusMap().put(account, AccountStatus.online);
                        break;
                    }
                    case signUp: {
                        Pattern pattern = Pattern.compile("userName:(\\w+)password:(\\w+)");
                        Matcher matcher = pattern.matcher(request.getMessage());
                        matcher.find();
                        Account account = NetworkDB.getInstance().getAccount(matcher.group(1));
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
                        break;
                    }
                    case login: {
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
                                break;
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
                        break;
                    }
                    case logout: {
                        Pattern pattern = Pattern.compile("userName:(\\w+)");
                        Matcher matcher = pattern.matcher(request.getMessage());
                        if (matcher.find()) {
                            Account account = NetworkDB.getInstance().getAccount(matcher.group(1));
                            if (account != null) {
                                connection.setAccount(null);
                                NetworkDB.getInstance().getAccountStatusMap().put(account, AccountStatus.offline);
                                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.logout, OutputMessageType.LOGGED_OUT_SUCCESSFULLY.getMessage(), null, null), connection);
                                updateAccountsInLeaderBoard();
                            }
                        }
                        break;
                    }
                    case leaderBoard: {
                        NetworkDB.getInstance().getAccountStatusMap().put(connection.getAccount(), AccountStatus.leaderBoard);
                        List<Object> accountList = new ArrayList<>(NetworkDB.getInstance().getAccountStatusMap().keySet());
                        List<Integer> integerList = new ArrayList<>();
                        setIntegerList(accountList, integerList);
                        NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.leaderBoard, null, integerList, accountList), connection);
                        break;
                    }
                    case shop: {
                        NetworkDB.getInstance().getAccountStatusMap().put(connection.getAccount(), AccountStatus.shop);
                        List<Object> cardList = new ArrayList<>(NetworkDB.getInstance().getCardList());
                        List<Object> usableList = new ArrayList<>(NetworkDB.getInstance().getUsableList());
                        List<Integer> integerList = new ArrayList<>();
                        integerList.add(cardList.size());
                        integerList.add(usableList.size());
                        List<Object> cardsAndUsables = new ArrayList<>();
                        cardsAndUsables.addAll(cardList);
                        cardsAndUsables.addAll(usableList);
                        NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.shop, null, integerList, cardsAndUsables), connection);
                        break;
                    }
                    case enterCollectoin: {
                        NetworkDB.getInstance().getAccountStatusMap().put(connection.getAccount(), AccountStatus.collection);
                        break;
                    }
                    case createDeck: {
                        Deck deck = new Deck(request.getMessage());
                        connection.getAccount().getPlayerInfo().getCollection().getDecks().add(deck);
                        break;
                    }
                    case removeDeck: {
                        String deckName = request.getMessage();
                        for (Deck deck : connection.getAccount().getPlayerInfo().getCollection().getDecks()) {
                            if (deck.getName().equals(deckName)) {
                                connection.getAccount().getPlayerInfo().getCollection().getDecks().remove(deck);
                                if (connection.getAccount().getMainDeck().getName().equals(deckName)) {
                                    connection.getAccount().setMainDeck(null);
                                }
                                break;
                            }
                        }
                        break;
                    }
                    case setMainDeck: {
                        String deckName = request.getMessage();
                        Deck deck = connection.getAccount().getPlayerInfo().getCollection().getDeckByName(deckName);
                        connection.getAccount().setMainDeck(deck);
                        break;
                    }
                    case importDeck:{
                        Deck deck = (Deck) request.getObjects().get(0);
                        connection.getAccount().getPlayerInfo().getCollection().getDecks().add(deck);
                        break;
                    }
                    case exitCollection:{
                        NetworkDB.getInstance().getAccountStatusMap().put(connection.getAccount(),AccountStatus.online);
                        break;
                    }
                    case moveCardToDeck:{
                        JsonObject jsonObject =(JsonObject) request.getObjects().get(0);
                        String deckName = jsonObject.get("deckName").getAsString();
                        String cardId = jsonObject.get("cardID").getAsString();
                        connection.getAccount().getPlayerInfo().getCollection().addCard(cardId,deckName);
                        break;
                    }
                    case removeCardFromDeck:{
                        JsonObject jsonObject = (JsonObject)request.getObjects().get(0);
                        String deckName = jsonObject.get("deck").getAsString();
                        String id = jsonObject.get("id").getAsString();
                        connection.getAccount().getPlayerInfo().getCollection().removeCard(id,deckName);
                        break;
                    }
                }
                Platform.runLater(() -> {
                    Server.getInstance().updateCardList();
                    Server.getInstance().updateUserList();
                });
                if (request.getRequestType().equals(RequestType.close))
                    break;
            }
        } catch (Exception e) {
            NetworkDB.getInstance().closeConnection(socket);
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
}