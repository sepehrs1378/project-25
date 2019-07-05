import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class NetworkDB {
    private List<Connection> connectionList = new ArrayList<>();
    private Map<Account, AccountStatus> accountStatusMap = new HashMap<>();

    private static NetworkDB ourInstance = new NetworkDB();

    public static NetworkDB getInstance() {
        return ourInstance;
    }

    private NetworkDB() {
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

    public void sendResponseToClient(Response response, Connection connection) {
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

    public Account getAccount(String userName){
        Set<Account> accounts = getAccountStatusMap().keySet();
        for (Account account : accounts){
            System.out.println(account.getUsername());
            if (account.getUsername().equals(userName)){
                return account;
            }

        }
        return null;
    }
    public void readAccounts() {
        YaGson gson = new YaGsonBuilder().setPrettyPrinting().create();
        File folder = new File("src/JSONFiles/Accounts/PlayerAccounts");
        String[] fileNames = folder.list();
        FileReader reader;
        if (fileNames != null) {
            for (String fileName : fileNames) {
                if (fileName.endsWith(".json")) {
                    try {
                        reader = new FileReader("src/JSONFiles/Accounts/PlayerAccounts/" + fileName);
                        NetworkDB.getInstance().getAccountStatusMap().put(gson.fromJson(reader, Account.class),AccountStatus.offline);
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void saveAccounts() {
        YaGson gson = new YaGsonBuilder().setPrettyPrinting().create();
        for (Account account : accountStatusMap.keySet()) {
            String fileName = "account_" + account.getUsername() + ".json";
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(new File("src/JSONFiles/Accounts/PlayerAccounts/" + fileName));
                gson.toJson(account, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}