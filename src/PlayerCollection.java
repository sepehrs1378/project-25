import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerCollection {
    private static final DataBase dataBase = DataBase.getInstance();
    private static final Account loggedInAccount = dataBase.getLoggedInAccount();
    private static final ControllerShop controllerShop = ControllerShop.getOurInstance();
    private List<Deck> decks = new ArrayList<>();
    private List<Card> cards = new ArrayList<>();
    private List<Usable> items = new ArrayList<>();

    public List<Card> getCards() {
        return cards;
    }

    public List<Usable> getItems() {
        return items;
    }

    public void addCard(Card newCard) {
        cards.add(newCard);
    }

    public OutputMessageType addCard(String id, String toDeck) {
        Deck destinationDeck = getDeckByName(toDeck);
        Card card = getCardWithID(id);
        Item item = getItemWithID(id);
        if (card == null && item == null) {
            return OutputMessageType.NOT_IN_COLLECTION;
        } else if (destinationDeck.getItem() == item || destinationDeck.hasCard(card)) {
            return OutputMessageType.CARD_ALREADY_IN_BATTLE;
        } else if (destinationDeck.getCards().size() == 20) {
            return OutputMessageType.DECK_IS_FULL;
        } else if (destinationDeck.getHero() != null) {
            return OutputMessageType.DECK_HAS_HERO;
        } else {
            if (card != null) {
                if (card instanceof Unit) {
                    if (((Unit) card).getHeroOrMinion().equals(Constants.HERO)) {
                        destinationDeck.setHero((Unit) card);
                    } else {
                        destinationDeck.getCards().add(card);
                    }
                }
            } else {
                destinationDeck.setItem(item);
            }
            return OutputMessageType.NO_ERROR;
        }
    }

    public OutputMessageType removeCard(String id, String fromDeck) {
        Deck deck = getDeckByName(fromDeck);
        if (deck == null) {
            return OutputMessageType.DECK_DOESNT_EXIST;
        } else {
            Card temp = deck.getCardByCardId(id);
            if (temp != null) {
                if (temp instanceof Unit && ((Unit) temp).getHeroOrMinion().equals(Constants.HERO)) {
                    deck.setHero(null);
                } else {
                    if (deck.hasCard(id)) {
                        deck.getCards().remove(temp);
                    } else return OutputMessageType.NO_SUCH_CARD_IN_DECK;
                }
            } else {
                if (deck.getItem() == null) {
                    return OutputMessageType.ITEM_IS_EMTPY;
                } else if (deck.getItem().getId().equals(id)) {
                    deck.setItem(null);
                } else {
                    return OutputMessageType.NO_SUCH_CARD_IN_DECK;
                }
            }
        }
        return OutputMessageType.NO_ERROR;

    }

    public void addItem(Usable newItem) {
        items.add(newItem);
    }

    public void deleteCard(Card card) {
        cards.remove(card);
    }

    public void deleteItem(Usable item) {
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

    public boolean doesHaveCard(String id) {
        return getCardWithID(id) != null;
    }

    public Item getItemWithID(String id) {
        for (Item item : items) {
            if (item.getId().equals(id))
                return item;
        }
        return null;
    }

    public boolean doesHaveItem(String id) {
        return getCardWithID(id) != null;
    }

    public OutputMessageType deleteDeck(String deckName) {
        if (!doesHaveDeck(deckName))
            return OutputMessageType.DECK_DOESNT_EXIST;
        Deck deck = getDeckByName(deckName);
        decks.remove(deck);
        if (dataBase.getLoggedInAccount().getMainDeck() == deck)
            dataBase.getLoggedInAccount().setMainDeck(null);
        return OutputMessageType.DECK_DELETED;
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
        return OutputMessageType.DECK_CREATED;
    }

    private static Collectable findCollectableInShop(String collectableName) {
        for (Collectable collectable : DataBase.getCollectableList()) {
            if (collectable.getName().equals(collectableName)) {
                return collectable;
            }
        }
        return null;
    }

    public OutputMessageType buy(String name) {
        if (dataBase.doesCardExist(name)) {
            Card card = dataBase.getCardWithName(name);
            if (loggedInAccount.getMoney() < card.getPrice())
                return OutputMessageType.INSUFFICIENT_MONEY;
            else {
                buySuccessful(name);
                return OutputMessageType.BOUGHT_SUCCESSFULLY;
            }
        }
        if (dataBase.doesUsableExist(name)) {
            Usable usable = dataBase.getUsableWithName(name);
            if (loggedInAccount.getMoney() < usable.getPrice())
                return OutputMessageType.INSUFFICIENT_MONEY;
            if (items.size() == 3)
                return OutputMessageType.CANT_HAVE_MORE_ITEMS;
            else {
                buySuccessful(name);
                return OutputMessageType.BOUGHT_SUCCESSFULLY;
            }
        }
        return OutputMessageType.NOT_IN_SHOP;
    }

    public void buySuccessful(String name) {
        Card card = findCardInShop(name);
        if (card != null) {
            Card cloneCard = card.clone();
            loggedInAccount.getPlayerInfo().addCardToCollection(cloneCard);
            loggedInAccount.takeAwayMoney(card.getPrice());
            defineNewId(cloneCard);
            return;
        }
        Usable usable = findUsableInShop(name);
        if (usable != null) {
            Usable cloneUsable = usable.clone();
            loggedInAccount.getPlayerInfo().addUsableToCollection(cloneUsable);
            loggedInAccount.takeAwayMoney(usable.getPrice());
            defineNewId(cloneUsable);
        }
    }


    private static Card findCardInShop(String cardName) {
        for (Card card : DataBase.getCardList()) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    private static Usable findUsableInShop(String usableName) {
        for (Usable usable : DataBase.getUsableList()) {
            if (usable.getName().equals(usableName)) {
                return usable;
            }
        }
        return null;
    }

    private void defineNewId(Object obj) {
        PlayerCollection collection = loggedInAccount.getPlayerInfo().getCollection();
        if (obj instanceof Card) {
            int numberOfSimilarCards = 0;
            for (Card card : collection.getCards()) {
                if (card.getName().equals(((Card) obj).getName())) {
                    numberOfSimilarCards++;
                }
            }
            Card card = (Card) obj;
            String id = loggedInAccount.getUsername() + "_" + card.getName() + "_" + numberOfSimilarCards;
            card.setId(id);
        } else if (obj instanceof Usable) {
            int numberOfSimilarUsables = 0;
            for (Usable usable : collection.getItems()) {
                if (usable.getName().equals(((Usable) obj).getName())) {
                    numberOfSimilarUsables++;
                }
            }
            Usable usable = (Usable) obj;
            String id = loggedInAccount.getUsername() + "_" + usable.getName() + "_" + numberOfSimilarUsables;
            usable.setId(id);
        }
    }

    public static void searchInShop(String command) {
        String[] strings = command.split("\\s+");
        Card card = findCardInShop(strings[1]);
        Usable usable = findUsableInShop(strings[1]);
        Collectable collectable = findCollectableInShop(strings[1]);
        controllerShop.showIdInShop(card, usable, collectable);
    }

    public OutputMessageType sell(String id) {
        Object obj = searchCardOrItemWithId(id);
        if (obj != null) {
            if (obj instanceof Card) {
                Card card = (Card) obj;
                loggedInAccount.addMoney(card.getPrice());
                cards.remove(card);
                for (Deck deck : decks) {
                    deck.getCards().remove(card);
                    if (deck.getHero().equals(card)){
                        deck.setHero(null);
                    }
                }
            }
            if (obj instanceof Usable) {
                Usable usable = (Usable) obj;
                loggedInAccount.addMoney(usable.getPrice());
                items.remove(usable);
                for (Deck deck : decks){
                    if (deck.getItem().equals(usable)){
                        deck.setItem(null);
                    }
                }
            }
            return OutputMessageType.SOLD_SUCCESSFULLY;
        }
        return OutputMessageType.NOT_IN_COLLECTION;
    }

    public OutputMessageType selectDeckAsMain(String deckName) {
        if (!doesHaveDeck(deckName))
            return OutputMessageType.DECK_DOESNT_EXIST;
        dataBase.getLoggedInAccount().setMainDeck(getDeckByName(deckName));
        return OutputMessageType.DECK_SELECTED;
    }

    public OutputMessageType validateDeck(String deckName) {
        if (!doesHaveDeck(deckName))
            return OutputMessageType.DECK_DOESNT_EXIST;
        Deck deck = getDeckByName(deckName);
        if (deck.isValid())
            return OutputMessageType.DECK_VALID;
        return OutputMessageType.DECK_NOT_VALID;
    }

    public Object searchCardOrItemWithId(String id) {
        for (Card card : cards) {
            if (card.getId().equals(id)) {
                return card;
            }
        }
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public List<String> searchCardOrItemWithName(String name) {
        List<String> output = new ArrayList<>();
        for (Card card : cards) {
            if (card.getName().equals(name)) {
                output.add(card.getId());
            }
        }
        for (Item item : items) {
            if (item.getName().equals(name)) {
                output.add(item.getId());
            }
        }
        if (output.isEmpty()) {
            output.add(OutputMessageType.NO_SUCH_CARD_IN_COLLECTION.toString());
        }
        return output;
    }
}
