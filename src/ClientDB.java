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
    private Player loggedInPlayer;
    private Battle currentBattle;
    private Account computerPlayerLevel1;
    private Account computerPlayerLevel2;
    private Account computerPlayerLevel3;
    private Account computerPlayerCustom;
//    private Account temp2;

    public void setLoggedInAccount(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public void setLoggedInPlayer(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    public void setCurrentBattle(Battle currentBattle) {
        this.currentBattle = currentBattle;
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public Battle getCurrentBattle() {
        return currentBattle;
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

    public Account getComputerAccount(int level) {
        switch (level) {
            case 1:
                return computerPlayerLevel1;
            case 2:
                return computerPlayerLevel2;
            case 3:
                return computerPlayerLevel3;
            default:
                throw new RuntimeException("level out of bounds");
        }
    }

    public void setNewIdsForCustomPlayer() {
        Deck deck = computerPlayerCustom.getMainDeck();
        if (deck != null) {
            for (Card card : deck.getCards()) {
                card.setId(computerPlayerCustom.getUsername() + "_" + card.getId().split("_")[1] + "_"
                        + card.getId().split("_")[2]);
            }
            deck.getHero().setId(computerPlayerCustom.getUsername() + "_" + deck.getHero().getId().split("_")[1] + "_"
                    + deck.getHero().getId().split("_")[2]);
            deck.getItem().setId(computerPlayerCustom.getUsername() + "_" + deck.getItem().getId().split("_")[1] + "_"
                    + deck.getItem().getId().split("_")[2]);

        }
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