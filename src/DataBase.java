import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBase {
    private static DataBase ourInstance = new DataBase();
    private static List<Item> itemList = new ArrayList<>();
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

    public static List<Item> getItemList() {
        return itemList;
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

    public Item getItemWithName(String itemName) {
        for (Item item : itemList) {
            if (item.getItemID().equals(itemName))
                return item;
        }
        return null;
    }

    public boolean doesItemExit(String itemName) {
        return getItemWithName(itemName) != null;
    }
}