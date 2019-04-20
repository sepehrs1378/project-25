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

    public Card getCardWithID(String id) {
        for (Card card : cards) {
            if (card.getId().equals(id))
                return card;
        }
        return null;
    }

    public boolean doesCardExist(String id) {
        return getCardWithID(id) != null;
    }

    public Item getItemWithID(String id) {
        for (Item item : items) {
            if (item.getId().equals(id))
                return item;
        }
        return null;
    }

    public boolean doesItemExist(String id) {
        return getCardWithID(id) != null;
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

    public OutputMessageType createDeck(String deckName) {
        if (doesHaveDeck(deckName))
            return OutputMessageType.DECK_ALREADY_EXISTS;
        Deck newDeck = new Deck(deckName);
        decks.add(newDeck);
        return OutputMessageType.NO_ERROR;
    }

    public OutputMessageType buy(String name) {
        if (dataBase.doesCardExist(name)) {
            Card card = dataBase.getCardWithName(name);
            if (loggedInAccount.getMoney() < card.getPrice())
                return OutputMessageType.INSUFFICIENT_MONEY;
            if (items.size() == 3)
                return OutputMessageType.CANT_HAVE_MORE_ITEMS;
            else {
                //todo
                return OutputMessageType.BUY_SUCCESSFUL;
            }
        }
        if (dataBase.doesUsableExist(name)) {
            Usable usable = dataBase.getUsableWithName(name);
            if (loggedInAccount.getMoney() < usable.getPrice())
                return OutputMessageType.INSUFFICIENT_MONEY;
            if (items.size() == 3)
                return OutputMessageType.CANT_HAVE_MORE_ITEMS;
            else {
                //todo
                return OutputMessageType.BUY_SUCCESSFUL;
            }
        }
        return OutputMessageType.NOT_IN_SHOP;
    }

    public OutputMessageType sell(String id) {
        if ()
    }
}
