package ServerPackage;

import java.net.Socket;

public class Connection {
    private static DataBase dataBase = DataBase.getInstance();
    private Socket socket;
    private Account account;

    public Connection(String username, Socket socket) {
        this.socket = socket;
        if (username != null)
            this.account = dataBase.getAccountWithUsername(username);
        else this.account = null;

    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Socket getSocket() {
        return socket;
    }
}
