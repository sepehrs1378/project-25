import java.util.ArrayList;
import java.util.List;

class Account {
    private static List<Account> accounts=new ArrayList<>();
    private boolean[] levelsOpennessStatus =new boolean[3];
    private String password;
    private String username;
    private PlayerInfo playerInfo;
    private List<MatchInfo> matchList=new ArrayList<>();
    private static Account currentAccount;

    {
        levelsOpennessStatus[0]=true;
    }

    public static List<Account> getAccounts() {
        return accounts;
    }

    public static void addAccount(Account newAccount){

    }

    public static boolean checkValidation(String username,String password){

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

    public void addMatchToMatchList(MatchInfo Match){

    }

    public void openLevelOfStory(int level){
        levelsOpennessStatus[level]=true;
    }

    private int getNumberOfWins(){

    }

    public static void login(String username){

    }

    public static void showLeaderboard(){

    }



}
