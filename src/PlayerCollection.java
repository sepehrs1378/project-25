import java.util.ArrayList;
import java.util.List;

class PlayerCollection {
    private static final DataBase dataBase = DataBase.getInstance();
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

    public void createDeck(String deckName) {
        //todo if deck exists...?
        Deck newDeck = new Deck(deckName);
        decks.add(newDeck);
    }

    public void buy(String name) {
        if (dataBase.doesCardExist(name)) {

            return;
        }
        if (dataBase.doesItemExit(name)) {

            return;
        }

    }

    public void sell(String name) {

    }
}
