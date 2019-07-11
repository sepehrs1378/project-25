import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Account implements Comparable<Account> {
    private boolean[] levelsOpennessStatus = new boolean[3];
    private String password;
    private String username;
    private PlayerInfo playerInfo;
    private List<MatchInfo> matchList = new ArrayList<>();
    private Deck mainDeck;
    private int money;
    private String turnDuration = Constants.NO_LIMIT;

    {
        levelsOpennessStatus[0] = true;
        levelsOpennessStatus[1] = true;
        levelsOpennessStatus[2] = true;
    }

    public Account(String userName, String password) {
        this.username = userName;
        this.password = password;
        this.playerInfo = new PlayerInfo(userName);
        this.money = Constants.MONEY;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addMoney(int addedMoney) {
        money += addedMoney;
    }

    public void takeAwayMoney(int tookAwayMoney) {
        money -= tookAwayMoney;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public boolean[] getLevelsOpennessStatus() {
        return levelsOpennessStatus;
    }

    public List<MatchInfo> getMatchList() {
        return matchList;
    }

    public void addMatchToMatchList(MatchInfo match) {
        matchList.add(match);
    }

    public void openLevelOfStory(int level) {
        levelsOpennessStatus[level] = true;
    }

    public int getNumberOfWins() {
        int numberOfWins = 0;
        for (MatchInfo matchInfo : matchList) {
            if (matchInfo.getWinner().equals(this.getUsername()))
                numberOfWins++;
        }
        return numberOfWins;
    }

    public int getNumberOfLosses() {
        int numberOfLosses = 0;
        for (MatchInfo matchInfo : matchList) {
            if (!matchInfo.getWinner().equals(this.getUsername()))
                numberOfLosses++;
        }
        return numberOfLosses;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public List<Deck> getValidDecks() {
        List<Deck> decks = new ArrayList<>();
        for (Deck deck : this.getPlayerInfo().getCollection().getDecks()) {
            if (deck.isValid()) {
                decks.add(deck);
            }
        }
        return decks;
    }

    public int getMoney() {
        return money;
    }

    public String getTurnDuration() {
        return turnDuration;
    }

    public void setTurnDuration(String turnDuration) {
        this.turnDuration = turnDuration;
    }

    @Override
    public int compareTo(Account o) {
        int numberOfWins1 = o.getNumberOfWins();
        int numberOfWins2 = this.getNumberOfWins();
        int numberOfLosses1 = o.getNumberOfLosses();
        int numberOfLosses2 = this.getNumberOfLosses();
        if (numberOfWins1 > numberOfWins2) {
            return 1;
        }
        if (numberOfWins1 < numberOfWins2) {
            return -1;
        }
        return Integer.compare(numberOfLosses2, numberOfLosses1);
    }
}
