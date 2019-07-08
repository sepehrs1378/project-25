import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonStreamParser;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
    private List<Usable> usableList = new ArrayList<>();
    private List<Collectable> collectableList = new ArrayList<>();
    private List<Card> cardList = new ArrayList<>();

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
        DataBase.getInstance().setLoggedInAccount(loggedInAccount);
        this.loggedInAccount = loggedInAccount;
    }

    public Account getComputerPlayerLevel1() {
        return computerPlayerLevel1;
    }

    public Account getComputerPlayerLevel2() {
        return computerPlayerLevel2;
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
    public List<Card> getCardList() {
        return cardList;
    }

    public List<Usable> getUsableList() {
        return usableList;
    }

    public List<Collectable> getCollectableList() {
        return collectableList;
    }

    public Deck importDeck(String address) {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        if (!address.endsWith(".json")) {
            new Alert(Alert.AlertType.ERROR, "please select a valid json file!");
        }
        FileReader reader = null;
        try {
            reader = new FileReader(new File(address));
            Deck deck = yaGson.fromJson(reader, Deck.class);
            return deck;
        } catch (FileNotFoundException ignored) {
        } catch (ClassCastException e) {
            new Alert(Alert.AlertType.ERROR, "please select a deck!").showAndWait();
        }
        return null;
    }

    public void exportDeck(Deck deck, String address) {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        File folder = new File(address);
        String[] fileNames = folder.list();
        String fileName = "deck_" + deck.getName() + "_";
        int numberOfDecks = 0;
        if (fileNames != null) {
            for (String name : fileNames) {
                if (name.contains(fileName)) {
                    numberOfDecks = numberOfDecks + 1;
                }
            }
        }
        fileName += numberOfDecks;
        fileName += ".json";
        try {
            FileWriter fileWriter = new FileWriter(new File(address + "/" + fileName));
            yaGson.toJson(deck, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}