import com.google.gson.JsonStreamParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private Account account;
    private Battle currentBattle;
    private InputStreamReader input;
    private OutputStreamWriter output;
    private JsonStreamParser parser;

    public Connection(Socket socket) {
        this.socket = socket;
        try {
            input = new InputStreamReader(socket.getInputStream());
            output = new OutputStreamWriter(socket.getOutputStream());
            parser = new JsonStreamParser(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public JsonStreamParser getParser() {
        return parser;
    }

    public Socket getSocket() {
        return socket;
    }

    public Account getAccount() {
        return account;
    }

    public InputStreamReader getInput() {
        return input;
    }

    public OutputStreamWriter getOutput() {
        return output;
    }

    public void close() {
        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentBattle(Battle currentBattle) {
        this.currentBattle = currentBattle;
    }

    public Battle getCurrentBattle() {
        return currentBattle;
    }
}
