import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class NetworkDB {
    private static NetworkDB ourInstance = new NetworkDB();
    private List<Connection> connectionList = new ArrayList<>();
    private Map<Account, AccountStatus> accountStatusMap = new HashMap<>();
    private Map<String, Integer> numberOfCards = new HashMap<>();
    private List<Account> accountsWaitingForClassic = new LinkedList<>();
    private List<Account> accountsWaitingForOneFlag = new LinkedList<>();
    private List<Account> accountsWaitingForMultiFlags = new LinkedList<>();
    private List<Battle> currentBattlesList = new ArrayList<>();
    private List<Usable> usableList = new ArrayList<>();
    private List<Collectable> collectableList = new ArrayList<>();
    private List<Card> cardList = new ArrayList<>();

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
        accountStatusMap.put(account, AccountStatus.waitingForMatch);
        pairAccountsForBattle(accountsWaitingForClassic,Constants.CLASSIC,0);
    }

    public static NetworkDB getInstance() {
        return ourInstance;
    }

    public void addAccountWaitingForOneFlag(Account account) {
        accountsWaitingForOneFlag.add(account);
        accountStatusMap.put(account, AccountStatus.waitingForMatch);
        pairAccountsForBattle(accountsWaitingForOneFlag, Constants.ONE_FLAG, 1);
    }

    public void addAccountWaitingForMultiFlags(Account account) {
        accountsWaitingForMultiFlags.add(account);
        accountStatusMap.put(account, AccountStatus.waitingForMatch);
        pairAccountsForBattle(accountsWaitingForMultiFlags, Constants.FLAGS, 7);
    }

    private NetworkDB() {
    }

    public void makeEveryThing() {
        readSpells();
        readHeroes();
        readMinions();
        readCollectibles();
        readUsables();
        readCustomCards();
        readNumberCardMap();
    }

    public Map<String, Integer> getNumberOfCards() {
        return numberOfCards;
    }

//    private void readCardNumberMap() {
//        cardList.forEach(e->{
//           numberOfCards.put(e.getName(),10);
//        });
//    }

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

    public List<Battle> getCurrentBattlesList() {
        return currentBattlesList;
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
            if (connection.getAccount().equals(account))
                return connection;
        }
        return null;
    }

    public void pairAccountsForBattle(List<Account> accountList, String mode, int numberOfFlags) {
        Account account1;
        Account account2;
        Connection connection1;
        Battle battle;
        Connection connection2;
        if (accountList.size() >= 2) {
            account1 = accountList.get(0);
            account2 = accountList.get(1);
            connection1 = getConnectionWithAccount(account1);
            connection2 = getConnectionWithAccount(account2);
            accountList.remove(0);
            accountList.remove(0);
            battle = new Battle(account1, account2, mode
                    , numberOfFlags, null, Constants.MULTI, 2000);
            String duration = returnLesserDuration(account1, account2);
            currentBattlesList.add(battle);
            connection1.setCurrentBattle(battle);
            connection2.setCurrentBattle(battle);
            List<Object> objects = new ArrayList<>();
            objects.add(battle);
            sendResponseToClient(new Response
                    (ResponseType.matchFound, duration, null, objects), connection1);
            sendResponseToClient(new Response
                    (ResponseType.matchFound, duration, null, objects), connection2);
        }
        //todo IMPORTANT complete it for other modes too...
    }

    private String returnLesserDuration(Account account1, Account account2){
        boolean nullOrNoLimit1 = (account1.getTurnDuration() == null || account1.getTurnDuration().equals(Constants.NO_LIMIT));
        boolean nullOrNoLimit2 = (account2.getTurnDuration() == null || account2.getTurnDuration().equals(Constants.NO_LIMIT));
        if (nullOrNoLimit1 && !nullOrNoLimit2){
            return account2.getTurnDuration();
        }
        if (!nullOrNoLimit1 && nullOrNoLimit2){
            return account1.getTurnDuration();
        }
        if (nullOrNoLimit1 && nullOrNoLimit2){
            return Constants.NO_LIMIT;
        }
        int duration1 = Integer.parseInt(account1.getTurnDuration());
        int duration2 = Integer.parseInt(account2.getTurnDuration());
        if (duration1 < duration2)
            return account1.getTurnDuration();
        else
            return account2.getTurnDuration();
    }

    public Connection getOpponentConnection(Connection connection) {
        for (Connection tempConnection : connectionList) {
            if (!tempConnection.equals(connection)
                    && tempConnection.getCurrentBattle().equals(connection.getCurrentBattle())) {
                return tempConnection;
            }
        }
        return null;
    }

    public void sendResponseToPlayerAndOpponent(Response response, Connection connection) {
        sendResponseToClient(response, connection);
        System.out.println(getOpponentConnection(connection));
        sendResponseToClient(response, getOpponentConnection(connection));
    }

    public Account getAccountWithUserName(String username) {
        for (Account account : accountStatusMap.keySet()) {
            if (account.getUsername().equals(username))
                return account;
        }
        return null;
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

    public Card getCardWithName(String cardName) {
        for (Card card : cardList) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }

    public boolean doesCardExist(String cardName) {
        return getCardWithName(cardName) != null;
    }

    public Usable getUsableWithName(String usableName) {
        for (Usable usable : usableList) {
            if (usable.getName().equals(usableName))
                return usable;
        }
        return null;
    }

    public boolean doesUsableExist(String itemName) {
        return getUsableWithName(itemName) != null;
    }

    public Collectable getCollectableWithName(String collectableName) {
        for (Collectable collectable : collectableList) {
            if (collectable.getId().equals(collectableName))
                return collectable;
        }
        return null;
    }

    public boolean doesCollectableExist(String collectableName) {
        return getCollectableWithName(collectableName) != null;
    }

    public Card findCardInShop(String cardName) {
        for (Card card : cardList) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    public Usable findUsableInShop(String usableName) {
        for (Usable usable : usableList) {
            if (usable.getName().equals(usableName)) {
                return usable;
            }
        }
        return null;
    }

    public void changePlayerNameInId(Object object, Player player) {
        if (object instanceof Card) {
            Card card = (Card) object;
            String[] idPieces = card.getId().split("_");
            card.setId(player.getPlayerInfo().getPlayerName() + "_" + idPieces[1] + "_" + idPieces[2]);
        }
        if (object instanceof Item) {
            Item item = (Item) object;
            String[] idPieces = item.getId().split("_");
            item.setId(player.getPlayerInfo().getPlayerName() + "_" + idPieces[1] + "_" + idPieces[2]);
        }
    }

    public void savaCards() {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        for (Card card : cardList) {
            String fileName = "card_" + card.getName() + ".json";
            FileWriter fileWriter = null;
            try {
                if (card instanceof Spell) {
                    fileWriter = new FileWriter(new File("src/JSONFiles/Cards/Spells/" + fileName));
                } else if (card instanceof Unit) {
                    if (((Unit) card).getHeroOrMinion().equals(Constants.HERO)) {
                        fileWriter = new FileWriter(new File("src/JSONFiles/Cards/Heroes/" + fileName));
                    } else if (((Unit) card).getHeroOrMinion().equals(Constants.MINION)) {
                        fileWriter = new FileWriter(new File("src/JSONFiles/Cards/Minions/" + fileName));
                    }
                }
                yaGson.toJson(card, fileWriter);
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Usable usable : usableList) {
            String fileName = "card_" + usable.getName() + ".json";
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(new File("src/JSONFiles/Cards/Usables/" + fileName));
                yaGson.toJson(usable, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Collectable collectable : collectableList) {
            String fileName = "card_" + collectable.getName() + ".json";
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(new File("src/JSONFiles/Cards/Collectibles/" + fileName));
                yaGson.toJson(collectable, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readCollectibles() {
        YaGson gson = new YaGsonBuilder().setPrettyPrinting().create();
        File folder = new File("src/JSONFiles/Cards/Collectibles");
        String[] fileNames = folder.list();
        FileReader reader;
        if (fileNames != null) {
            for (String fileName : fileNames) {
                if (fileName.endsWith(".json")) {
                    try {
                        reader = new FileReader("src/JSONFiles/Cards/Collectibles/" + fileName);
                        collectableList.add(gson.fromJson(reader, Collectable.class));
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void readUsables() {
        YaGson gson = new YaGsonBuilder().setPrettyPrinting().create();
        File folder = new File("src/JSONFiles/Cards/Usables");
        String[] fileNames = folder.list();
        FileReader reader;
        if (fileNames != null) {
            for (String fileName : fileNames) {
                if (fileName.endsWith(".json")) {
                    try {
                        reader = new FileReader("src/JSONFiles/Cards/Usables/" + fileName);
                        usableList.add(gson.fromJson(reader, Usable.class));
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void readHeroes() {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        File folder = new File("src/JSONFiles/Cards/Heroes");
        String[] fileNames = folder.list();
        FileReader reader;
        if (fileNames != null) {
            for (String fileName : fileNames) {
                if (fileName.endsWith(".json")) {
                    try {
                        reader = new FileReader("src/JSONFiles/Cards/Heroes/" + fileName);
                        cardList.add(yaGson.fromJson(reader, Unit.class));
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void readMinions() {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        File folder = new File("src/JSONFiles/Cards/Minions");
        String[] fileNames = folder.list();
        FileReader reader;
        if (fileNames != null) {
            for (String fileName : fileNames) {
                if (fileName.endsWith(".json")) {
                    try {
                        reader = new FileReader("src/JSONFiles/Cards/Minions/" + fileName);
                        cardList.add(yaGson.fromJson(reader, Unit.class));
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void readSpells() {
        YaGson gson = new YaGsonBuilder().setPrettyPrinting().create();
        File folder = new File("src/JSONFiles/Cards/Spells");
        String[] fileNames = folder.list();
        FileReader reader;
        if (fileNames != null) {
            for (String fileName : fileNames) {
                if (fileName.endsWith(".json")) {
                    try {
                        reader = new FileReader("src/JSONFiles/Cards/Spells/" + fileName);
                        cardList.add(gson.fromJson(reader, Spell.class));
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void readCustomCards() {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        {
            File folder = new File("src/JSONFiles/Cards/Custom/Heroes");
            String[] fileNames = folder.list();
            FileReader reader;
            if (fileNames != null) {
                for (String fileName : fileNames) {
                    if (fileName.endsWith(".json")) {
                        try {
                            reader = new FileReader("src/JSONFiles/Cards/Custom/Heroes/" + fileName);
                            cardList.add(yaGson.fromJson(reader, Unit.class));
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        {
            File folder = new File("src/JSONFiles/Cards/Custom/Minions");
            String[] fileNames = folder.list();
            FileReader reader;
            if (fileNames != null) {
                for (String fileName : fileNames) {
                    if (fileName.endsWith(".json")) {
                        try {
                            reader = new FileReader("src/JSONFiles/Cards/Custom/Minions/" + fileName);
                            cardList.add(yaGson.fromJson(reader, Unit.class));
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        {
            File folder = new File("src/JSONFiles/Cards/Custom/Spells");
            String[] fileNames = folder.list();
            FileReader reader;
            if (fileNames != null) {
                for (String fileName : fileNames) {
                    if (fileName.endsWith(".json")) {
                        try {
                            reader = new FileReader("src/JSONFiles/Cards/Custom/Spells/" + fileName);
                            cardList.add(yaGson.fromJson(reader, Spell.class));
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    public void saveGame(Battle battle) {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        File folder = new File("src/JSONFiles/Games");
        String[] fileNames = folder.list();
        String fileName = "battle_" + battle.getPlayer1().getPlayerInfo().getPlayerName()
                + "_" + battle.getPlayer2().getPlayerInfo().getPlayerName() + "_";
        int numberOfBattles = 0;
        if (fileNames != null) {
            for (String name : fileNames) {
                if (name.contains(fileName)) {
                    numberOfBattles++;
                }
            }
        }
        fileName += numberOfBattles;
        fileName += ".json";
        try {
            FileWriter fileWriter = new FileWriter(new File("src/JSONFiles/Games/" + fileName));
            yaGson.toJson(battle, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame(String address) {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        if (!address.endsWith(".json")) {
            System.out.println("selected file is not a json file");
            return;
        }
        FileReader reader;
        try {
            reader = new FileReader(new File(address));
            Battle battle = yaGson.fromJson(reader, Battle.class);
            System.out.println(battle.getPlayer2().getPlayerInfo().getPlayerName());
            System.out.println(battle.getPlayer1().getPlayerInfo().getPlayerName());
        } catch (FileNotFoundException e) {
            //todo show this message in correct place
            System.out.println("file not found");
        } catch (ClassCastException e) {
            System.out.println("invalid file, selected file is not a saved battle");
        }

    }

    public void saveCustomCard(Card card) {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        String fileName = "cardCustom_" + card.getName() + ".json";
        FileWriter fileWriter = null;
        try {
            if (card instanceof Spell) {
                fileWriter = new FileWriter(new File("src/JSONFiles/Cards/Custom/Spells/" + fileName));
            } else if (card instanceof Unit) {
                if (((Unit) card).getHeroOrMinion().equals(Constants.HERO)) {
                    fileWriter = new FileWriter(new File("src/JSONFiles/Cards/Custom/Heroes/" + fileName));
                } else if (((Unit) card).getHeroOrMinion().equals(Constants.MINION)) {
                    fileWriter = new FileWriter(new File("src/JSONFiles/Cards/Custom/Minions/" + fileName));
                }
            }
            yaGson.toJson(card, fileWriter);
            if (fileWriter != null) {
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveNumberCardMap() {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        numberOfCards.forEach((name, number) -> {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(new File("src/JSONFiles/Cards/Numbers/" + name + ".json"));
                JsonObject obj = new JsonObject();
                obj.addProperty("cardName", name);
                obj.addProperty("number", number);
                yaGson.toJson(obj, fileWriter);
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void readNumberCardMap() {
        YaGson gson = new YaGsonBuilder().setPrettyPrinting().create();
        File folder = new File("src/JSONFiles/Cards/Numbers");
        String[] fileNames = folder.list();
        FileReader reader;
        if (fileNames != null) {
            for (String fileName : fileNames) {
                if (fileName.endsWith(".json")) {
                    try {
                        reader = new FileReader("src/JSONFiles/Cards/Numbers/" + fileName);
                        JsonObject object = gson.fromJson(reader, JsonObject.class);
                        numberOfCards.put(object.get("cardName").getAsString(), object.get("number").getAsInt());
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void removeAccountFromWaitingList(Account account) {
        accountsWaitingForClassic.remove(account);
        accountsWaitingForOneFlag.remove(account);
        accountsWaitingForMultiFlags.remove(account);
    }

    public Player getPlayerWithAccount(Account account, Battle battle) {
        if (account.getUsername().equals(battle.getPlayer1().getPlayerInfo().getPlayerName()))
            return battle.getPlayer1();
        if (account.getUsername().equals(battle.getPlayer2().getPlayerInfo().getPlayerName()))
            return battle.getPlayer2();
        return null;
    }
}