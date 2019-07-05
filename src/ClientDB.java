import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonStreamParser;

import java.io.*;
import java.net.Socket;

public class ClientDB {
    private static ClientDB ourInstance = new ClientDB();
    private Socket socket;
    private InputStreamReader input;
    private OutputStreamWriter output;
    private JsonStreamParser parser;
    private Account loggedInAccount;
    private Account computerPlayerLevel1;
    private Account computerPlayerLevel2;
    private Account computerPlayerLevel3;
    private Account computerPlayerCustom;

    public static ClientDB getInstance() {
        return ourInstance;
    }

    private ClientDB() {
        computerPlayerCustom = new Account("computerCustom", "custom");
        readComputerAccounts();
    }

    public InputStreamReader getInput() {
        return input;
    }

    public OutputStreamWriter getOutput() {
        return output;
    }

    public Socket getSocket() {
        return socket;
    }

    public JsonStreamParser getParser() {
        return parser;
    }

    public void setSocket(Socket socket) {
        try {
            this.socket = socket;
            this.input = new InputStreamReader(socket.getInputStream());
            this.output = new OutputStreamWriter(socket.getOutputStream());
            this.parser = new JsonStreamParser(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public void setLoggedInAccount(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public Account getComputerPlayerLevel1() {
        return computerPlayerLevel1;
    }

    public Account getComputerPlayerLevel2() {
        return computerPlayerLevel2;
    }

    public Account getComputerPlayerLevel3() {
        return computerPlayerLevel3;
    }

    public Account getComputerPlayerCustom() {
        return computerPlayerCustom;
    }

    public void readComputerAccounts(){
        Reader reader = null;
        YaGson gson = new YaGsonBuilder().setPrettyPrinting().create();
        try {
            reader = new FileReader("src/JSONFiles/Accounts/ComputerPlayers/account_computer1.json");
            computerPlayerLevel1 = gson.fromJson(reader, Account.class);
            reader = new FileReader("src/JSONFiles/Accounts/ComputerPlayers/account_computer2.json");
            computerPlayerLevel2 = gson.fromJson(reader, Account.class);
            reader = new FileReader("src/JSONFiles/Accounts/ComputerPlayers/account_computer3.json");
            computerPlayerLevel3 = gson.fromJson(reader, Account.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}