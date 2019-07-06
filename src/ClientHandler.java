import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler extends Thread {
    private static NetworkDB networkDB = NetworkDB.getInstance();
    private Socket socket;
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
        while (true) {
            JsonObject obj = parser.next().getAsJsonObject();
            Request request = yaGson.fromJson(obj.toString(), Request.class);
            handleAccountCase(request);
            handleMatchFindingCase(request);
            handleMultiPlayerCase(request);
            if (request.getRequestType().equals(RequestType.close))
                break;
        }
        networkDB.closeConnection(socket);
    }

    private void handleMatchFindingCase(Request request) {
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
        Pattern pattern;
        Matcher matcher;
        Account account;
        switch (request.getRequestType()) {
            case signUp:
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
                }
                break;
            case login:
                pattern = Pattern.compile("userName:(\\w+)password:(\\w+)");
                matcher = pattern.matcher(request.getMessage());
                matcher.find();
                account = NetworkDB.getInstance().getAccount(matcher.group(1));
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
                    } else {
                        NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.login, OutputMessageType.ALREADY_LOGGED_IN.getMessage(), null, null), connection);
                    }
                }
                break;
            case logout:
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
                break;
        }
    }

    private void handleMultiPlayerCase(Request request) {
        switch (request.getRequestType()) {
            case moveUnit:
                handleUnitMoveCase(request);
                break;
            case attackUnit:
                break;
            case endTurn:
                break;
            case insertCard:
                break;
            case useSpecialPower:
                break;
            case useCollectable:
                break;
            case selectUnit:
                break;
            case enterGraveYard:
                break;
            case forfeit:
                break;
        }
    }

    private void handleUnitMoveCase(Request request) {
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