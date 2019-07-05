import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class NetworkDB {
    private static NetworkDB ourInstance = new NetworkDB();
    private List<Connection> connectionList = new ArrayList<>();
    private Map<Account, AccountStatus> accountStatusMap = new HashMap<>();
    private List<Account> accountsWaitingForClassic = new LinkedList<>();
    private List<Account> accountsWaitingForOneFlag = new LinkedList<>();
    private List<Account> accountsWaitingForMultiFlags = new LinkedList<>();

    public List<Account> getAccountsWaitingForClassic() {
        return accountsWaitingForClassic;
    }

    public List<Account> getAccountsWaitingForOneFlag() {
        return accountsWaitingForOneFlag;
    }

    public List<Account> getAccountsWaitingForMultiFlags() {
        return accountsWaitingForMultiFlags;
    }

    public void addAccountWaitingForClassic(Account account) {
        accountsWaitingForClassic.add(account);
        pairAccountsForBattle();
    }

    public void addAccountWaitingForOneFlag(Account account) {
        accountsWaitingForOneFlag.add(account);
        pairAccountsForBattle();
    }

    public void addAccountWaitingForMultiFlags(Account account) {
        accountsWaitingForMultiFlags.add(account);
        pairAccountsForBattle();
    }

    private NetworkDB() {
    }

    public Account getAccount(String userName) {
        Set<Account> accounts = getAccountStatusMap().keySet();
        for (Account account : accounts) {
            System.out.println(account.getUsername());
            if (account.getUsername().equals(userName)) {
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
                        accountStatusMap.put(gson.fromJson(reader, Account.class), AccountStatus.offline);
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

    public synchronized void pairAccountsForBattle() {
        Account account1, account2;
        if (accountsWaitingForClassic.size() >= 2) {
            account1 = accountsWaitingForClassic.get(0);
            account2 = accountsWaitingForClassic.get(1);
            accountsWaitingForClassic.remove(0);
            accountsWaitingForClassic.remove(1);
        }
        if (accountsWaitingForOneFlag.size() >= 2) {
            account1 = accountsWaitingForOneFlag.get(0);
            account2 = accountsWaitingForOneFlag.get(1);
            accountsWaitingForOneFlag.remove(0);
            accountsWaitingForOneFlag.remove(1);
        }
        if (accountsWaitingForMultiFlags.size() >= 2) {
            account1 = accountsWaitingForMultiFlags.get(0);
            account2 = accountsWaitingForMultiFlags.get(1);
            accountsWaitingForMultiFlags.remove(0);
            accountsWaitingForMultiFlags.remove(1);
        }
        //todo
    }
}