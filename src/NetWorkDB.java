import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.*;

public class NetWorkDB {
    private List<Connection> connectionList = new ArrayList<>();
    private Map<Account, AccountStatus> accountStatusMap = new HashMap<>();
    private List<Account> accountsWaitingForBattleList = new LinkedList<>();

    private static NetWorkDB ourInstance = new NetWorkDB();

    public static NetWorkDB getInstance() {
        return ourInstance;
    }

    private NetWorkDB() {
    }

    public synchronized void addConnection(Connection connection) {
        connectionList.add(connection);
    }

    public synchronized void setAccountStatus(Account account, AccountStatus status) {
        accountStatusMap.put(account, status);
    }

    public Map<Account, AccountStatus> getAccountStatusMap() {
        return accountStatusMap;
    }

    public Connection getConnectionWithSocket(Socket socket) {
        for (Connection connection : connectionList) {
            if (connection.getSocket() == socket)
                return connection;
        }
        return null;
    }

    public synchronized void closeConnection(Socket socket) {
        Connection connection = getConnectionWithSocket(socket);
        connection.close();
        connectionList.remove(connection);
    }

    public synchronized void sendResponseToClient(Response response, Connection connection) {
        try {
            OutputStreamWriter output = connection.getOutput();
            YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
            yaGson.toJson(response, output);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnectionWithAccount(Account account) {
        for (Connection connection : connectionList) {
            if (connection.getAccount() == account)
                return connection;
        }
        return null;
    }

    public synchronized void addAccountWaitingForBattle(Account account) {
        accountsWaitingForBattleList.add(account);
        pairAccountsForBattle();
    }

    public synchronized void pairAccountsForBattle() {
        if (accountsWaitingForBattleList.size() >= 2) {
            Account account1 = accountsWaitingForBattleList.get(0);
            Account account2 = accountsWaitingForBattleList.get(1);
            accountsWaitingForBattleList.remove(0);
            accountsWaitingForBattleList.remove(1);
        }
    }
}