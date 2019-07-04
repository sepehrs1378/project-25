import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetWorkDB {
    private List<Connection> connectionList = new ArrayList<>();
    private Map<Account, AccountStatus> accountStatusMap = new HashMap<>();

    private static NetWorkDB ourInstance = new NetWorkDB();

    public static NetWorkDB getInstance() {
        return ourInstance;
    }

    private NetWorkDB() {
    }

    public void addConnection(Connection connection) {
        connectionList.add(connection);
    }

    public void setAccountStatus(Account account, AccountStatus status) {
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

    public void closeConnection(Socket socket) {
        Connection connection = getConnectionWithSocket(socket);
        connection.close();
        connectionList.remove(connection);
    }

    public void sendResponseToClinet(Response response, Connection connection) {
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
}