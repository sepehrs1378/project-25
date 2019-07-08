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
        NetworkDB.getInstance().addConnection(connection);
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        JsonStreamParser parser = connection.getParser();
        try{
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
                        netWorkDB.getAccountStatusMap().put(account,AccountStatus.online);
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
                    case shop:{
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
                    case buy:{
                        OutputMessageType outputMessageType = connection.getAccount().getPlayerInfo().getCollection().buy(connection.getAccount(), request.getMessage());
                        List<Object> accountList = new ArrayList<>();
                        accountList.add(connection.getAccount());
                        NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.buy, outputMessageType.getMessage(), null, accountList), connection);
                        break;
                    }
                }
                Platform.runLater(()->{
                    Server.getInstance().updateCardList();
                    Server.getInstance().updateUserList();
                });
                if (request.getRequestType().equals(RequestType.close))
                    break;
            }
        }catch (Exception e){
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

    private void updateAccountsInLeaderBoard(){
        List<Object> accountList = new ArrayList<>(NetworkDB.getInstance().getAccountStatusMap().keySet());
        List<Integer> integerList = new ArrayList<>();
        setIntegerList(accountList, integerList);
        for (Object object : accountList){
            Account account = (Account) object;
            if (NetworkDB.getInstance().getAccountStatusMap().get(account).equals(AccountStatus.leaderBoard)){
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.updateLeaderBoard, null, integerList, accountList), NetworkDB.getInstance().getConnectionWithAccount(account));
            }
        }
    }

    private void handleMatchFinding(Request request) {
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

    private void handleAccountCase(Request request) {
        switch (request.getRequestType()) {
            case signUp:
                handleSignUpCase(request);
                break;
            case login:
                handleLoginCase(request);
                break;
            case logout:
                handleLogoutCase(request);
                break;
        }
    }

    private void handleSignUpCase(Request request) {
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
        }
    }

    private void handleLoginCase(Request request) {
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
            } else {
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.login, OutputMessageType.ALREADY_LOGGED_IN.getMessage(), null, null), connection);
            }
        }
    }

    private void handleLogoutCase(Request request) {
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
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.login, OutputMessageType.LOGGED_OUT_SUCCESSFULLY.getMessage(), null, null), connection);
            }
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
                Response response = new Response(ResponseType.unitSelected, id, null, battle);
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
        Integer destinationRow = (Integer) request.getObjects().get(0);
        Integer destinationColumn = (Integer) request.getObjects().get(1);
        switch (battle.getBattleGround().moveUnit(destinationRow, destinationColumn, battle)) {
            case UNIT_MOVED:
                Unit selectedUnit = battle.getPlayerInTurn().getSelectedUnit();
                Response response = new Response(ResponseType.unitMoved, selectedUnit.getId()
                        , null, destinationRow, destinationColumn, battle);
                networkDB.sendResponeToPlayerAndOpponent(response, connection);
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
}