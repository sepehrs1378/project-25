import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

public class NetWorkDB {
    private Set<Account> onlineAccountList = new HashSet<>();
    private Set<Account> playingAccountList = new HashSet<>();
    private Set<Connection> connectionList = new HashSet<>();

    private static NetWorkDB ourInstance = new NetWorkDB();

    public static NetWorkDB getInstance() {
        return ourInstance;
    }

    private NetWorkDB() {
    }

    public Set<Account> getOnlineAccountList() {
        return onlineAccountList;
    }

    public Set<Account> getPlayingAccountList() {
        return playingAccountList;
    }

    public Set<Connection> getConnectionList() {
        return connectionList;
    }

    public void setAccountOnline(Account account) {
        onlineAccountList.add(account);
    }

    public void setAccountPlaying(Account account) {
        playingAccountList.add(account);
    }

    public void addConnection(Connection connection) {
        connectionList.add(connection);
    }
}
