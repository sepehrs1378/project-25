import java.util.ArrayList;
import java.util.List;

public class Account {
    private static final ControllerAccount controllerAccount = ControllerAccount.getInstance();
    private static final DataBase dataBase = DataBase.getInstance();
    private static List<Account> accounts = DataBase.getInstance().getAccounts();
    private static Account loggedInAccount = dataBase.getLoggedInAccount();
    private boolean[] levelsOpennessStatus = new boolean[3];
    private String password;
    private String username;
    private PlayerInfo playerInfo;
    private List<MatchInfo> matchList = new ArrayList<>();
    private Deck mainDeck;

    {
        levelsOpennessStatus[0] = true;
    }

    public static List<Account> getAccounts() {
        return accounts;
    }

    public static void addAccount(Account newAccount) {
        accounts.add(newAccount);
    }

    public static boolean checkValidation(String username, String password) {
        //todo
        return true;
    }

    public static void loginToAccount(String username, String password) {
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.username.equals(username)) {
                if (account.password.equals(password)) {
                    loggedInAccount = account;
                    login(username);
                }
            } else {
                controllerAccount.showLoginError(ErrorType.INVALID_PASSWORD);
            }
        }
        controllerAccount.showLoginError(ErrorType.INVALID_USERNAME);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

    public void addMatchToMatchList(MatchInfo Match) {
        matchList.add(Match);
    }

    public void openLevelOfStory(int level) {
        levelsOpennessStatus[level] = true;
    }

    private int getNumberOfWins() {
        //todo ask what you should put in this
    }

    public static void login(String username) {
        //todo where should this connect to?
    }

    public static void showLeaderboard() {
        //todo what does leader board consist of?
    }

    private static void showMatchHistory() {

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
}
