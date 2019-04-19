import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBase {
    private static DataBase ourInstance = new DataBase();
    private static List<Account> accounts = new ArrayList<>();
    private static Account loggedInAccount;
    private static Battle currentBattle;

    public static DataBase getInstance() {
        return ourInstance;
    }

    private DataBase() {
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public void setLoggedInAccount(Account loggedInAccount) {
        DataBase.loggedInAccount = loggedInAccount;
    }

    public Battle getCurrentBattle() {
        return currentBattle;
    }

    public void setCurrentBattle(Battle currentBattle) {
        DataBase.currentBattle = currentBattle;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount() {
    }

    public void sortAccountsByWins() {
        Collections.sort(accounts);
    }

    public void createNewUnits() {

    }
}