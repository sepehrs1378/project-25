import java.util.ArrayList;
import java.util.List;

class PlayerCollection {
    private static final DataBase dataBase = DataBase.getInstance();
    private static final Account loggedInAccount = dataBase.getLoggedInAccount();
    private List<Deck> decks = new ArrayList<>();
    private List<Card> cards = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public List<Card> getCards() {
        return cards;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addCard(Card newCard) {
        cards.add(newCard);
    }

    public void addItem(Item newItem) {
        items.add(newItem);
    }

    public void deleteCard(Card card) {
        cards.remove(card);
    }

    public void deleteItem(Item item) {
        items.remove(item);
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void addNewDeck() {
        //todo maybe it isn't needed
    }

    public void deleteDeck(Deck deck) {
        decks.remove(deck);
        if (dataBase.getLoggedInAccount().getMainDeck() == deck)
            dataBase.getLoggedInAccount().setMainDeck(null);
    }

    public void deleteDeck(String deckName) {
        deleteDeck(getDeckByName(deckName));
    }

    public Deck getDeckByName(String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName))
                return deck;
        }
        return null;
    }

    public boolean doesHaveDeck(Deck deck) {
        return doesHaveDeck(deck.getName());
    }

    public boolean doesHaveDeck(String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName))
                return true;
        }
        return false;
    }

    public outputMessageType createDeck(String deckName) {
        if (doesHaveDeck(deckName))
            return outputMessageType.DECK_ALREADY_EXISTS;
        Deck newDeck = new Deck(deckName);
        decks.add(newDeck);
        return outputMessageType.NO_ERROR;
    }

    public outputMessageType buy(String name) {
        if (dataBase.doesCardExist(name)) {
            Card card = dataBase.getCardWithName(name);
            if (loggedInAccount.getMoney() < card.getPrice())
                return outputMessageType.INSUFFICIENT_MONEY;
            else {
                //todo
                return outputMessageType.NO_ERROR;
            }
        }
        if (dataBase.doesUsableExist(name)) {
            Usable usable = dataBase.getUsableWithName(name);
            if (loggedInAccount.getMoney() < usable.getPrice())
                return outputMessageType.INSUFFICIENT_MONEY;
            else {
                //todo
                return outputMessageType.NO_ERROR;
            }
        }
        return outputMessageType.NOT_IN_SHOP;
    }

    public void sell(String name) {

    }
}
