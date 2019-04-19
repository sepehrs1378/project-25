import java.util.ArrayList;
import java.util.List;

public class Account {
    private static List<Account> accounts = new ArrayList<>();
    private boolean[] openedLevels = new boolean[3];
    private String password;
    private String username;
    private PlayerInfo playerInfo;
    private List<MatchInfo> matchList=new ArrayList<>();
    private static final ControllerAccount controllerAccount = ControllerAccount.getInstance();
    //private static Account currentAccount;  find a use for this or delete it

    {
        openedLevels[0]=true;
    }

    public static List<Account> getAccounts() {
        return accounts;
    }

    public static void addAccount(Account newAccount){
        accounts.add(newAccount);
    }

    public static void loginToAccount(String username,String password){
        for (int i = 0; i < accounts.size(); i++){
            Account account = accounts.get(i);
            if (account.username.equals(username)){
                if (account.password.equals(password)){
                    login(username);
                }
            }else{
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

    public boolean[] getOpenedLevels() {
        return openedLevels;
    }

    public List<MatchInfo> getMatchList() {
        return matchList;
    }

    public void addMatchToMatchList(MatchInfo Match){
        matchList.add(Match);
    }

    public void openLevelOfStory(int level){
        openedLevels[level]=true;
    }

    private int getNumberOfWins(){

    }

    public static void login(String username){

    }

    public static void showLeaderboard(){

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
}
