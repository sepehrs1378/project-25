import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class
DataBase {
    private static DataBase ourInstance = new DataBase();
    private static List<Usable> usableList = new ArrayList<>();//todo WTF?
    private static List<Collectable> collectableList = new ArrayList<>();
    private static List<Card> cardList = new ArrayList<>();
    private static List<Account> accounts = new ArrayList<>();
    private static Account loggedInAccount;
    private static Battle currentBattle;

    public static DataBase getInstance() {
        return ourInstance;
    }

    private DataBase() {
    }

    public static List<Card> getCardList() {
        return cardList;
    }

    public static List<Collectable> getCollectableList() {
        return collectableList;
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

    public Card getCardWithName(String cardName) {
        for (Card card : cardList) {
            if (card.getCardID().equals(cardName))
                return card;
        }
        return null;
    }

    public boolean doesCardExist(String cardName) {
        return getCardWithName(cardName) != null;
    }

    public Usable getUsableWithName(String usableName) {
        for (Usable usable : usableList) {
            if (usable.getItemID().equals(usableName))
                return usable;
        }
        return null;
    }

    public boolean doesUsableExist(String itemName) {
        return getUsableWithName(itemName) != null;
    }

    public Collectable getCollectableWithName(String collectableName) {
        for (Collectable collectable : collectableList) {
            if (collectable.getItemID().equals(collectableName))
                return collectable;
        }
        return null;
    }

    public boolean doesCollectableExist(String collectableName) {
        return getCollectableWithName(collectableName) != null;
    }

}