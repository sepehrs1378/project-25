import java.util.ArrayList;
import java.util.List;

public class PlayerCollection {
    private DataBase dataBase = DataBase.getInstance();
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
        if (destinationDeck == null){
            return OutputMessageType.DECK_DOESNT_EXIST;
        }
        if (card == null && item == null) {
            return OutputMessageType.NOT_IN_COLLECTION;
        } else if ((item!= null && destinationDeck.getItem() == item) ||(card!= null && destinationDeck.hasCard(card))) {
            return OutputMessageType.CARD_ALREADY_IN_DECK;
        } else if (destinationDeck.getCards().size() == 20) {
            return OutputMessageType.DECK_IS_FULL;
        } else if (card instanceof Unit && ((Unit)card).getHeroOrMinion().equals(Constants.HERO)&&destinationDeck.getHero() != null) {
            return OutputMessageType.DECK_HAS_HERO;
        } else {
            if (card != null) {
                    if (card instanceof Unit && ((Unit) card).getHeroOrMinion().equals(Constants.HERO)) {
                        destinationDeck.setHero((Unit) card);
                    } else {
                        destinationDeck.getCards().add(card);
                    }
            } else {
                destinationDeck.setItem(item);
            }
            return OutputMessageType.CARD_ADDED_SUCCESSFULLY;
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
        return OutputMessageType.CARD_REMOVED_SUCCESSFULLY;

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

    public OutputMessageType buy(String name) {
        if (dataBase.doesCardExist(name)) {
            Card card = dataBase.getCardWithName(name);
            if (dataBase.getLoggedInAccount().getMoney() < card.getPrice())
                return OutputMessageType.INSUFFICIENT_MONEY;
            else {
                buySuccessful(name);
                return OutputMessageType.BOUGHT_SUCCESSFULLY;
            }
        } else if (dataBase.doesUsableExist(name)) {
            Usable usable = dataBase.getUsableWithName(name);
            if (dataBase.getLoggedInAccount().getMoney() < usable.getPrice())
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
        Card card = dataBase.findCardInShop(name);
        if (card != null) {
            Card cloneCard = card.clone();
            dataBase.getLoggedInAccount().takeAwayMoney(card.getPrice());
            defineNewId(cloneCard);
            if (cloneCard instanceof Unit) {
                Unit unit = ((Unit) cloneCard).clone();
                dataBase.getLoggedInAccount().getPlayerInfo().addCardToCollection(unit);
            } else if (cloneCard instanceof Spell) {
                Spell spell = ((Spell) cloneCard).clone();
                dataBase.getLoggedInAccount().getPlayerInfo().addCardToCollection(spell);
            }
            return;
        }
        Usable usable = dataBase.findUsableInShop(name);
        if (usable != null) {
            Usable cloneUsable = usable.clone();
            dataBase.getLoggedInAccount().getPlayerInfo().addUsableToCollection(cloneUsable);
            dataBase.getLoggedInAccount().takeAwayMoney(usable.getPrice());
            defineNewId(cloneUsable);
        }
    }

    private void defineNewId(Object obj) {
        PlayerCollection collection = dataBase.getLoggedInAccount().getPlayerInfo().getCollection();
        boolean didExit = false;
        int counter = 1;
        String id = "";
        if (obj instanceof Card) {
            while (!didExit) {
                id = dataBase.getLoggedInAccount().getUsername() + "_" + ((Card) obj).getName() + "_" + counter;
                didExit = isIdUnique(collection.getCards(), id);
                counter++;
            }
            ((Card) obj).setId(id);
        } else if (obj instanceof Usable) {
            while (!didExit) {
                id = dataBase.getLoggedInAccount().getUsername() + "_" + ((Usable) obj).getName() + "_" + counter;
                didExit = isIdUnique(id, collection.getItems());
                counter++;
            }
            ((Usable) obj).setId(id);
        }
    }

    public boolean isIdUnique(List<Card> collection, String id) {
        boolean isUnique = true;
        for (Card card : collection) {
            if (card.getId().equals(id)) {
                isUnique = false;
                break;
            }
        }
        return isUnique;
    }

    public boolean isIdUnique(String id, List<Usable> collection) {
        boolean isUnique = true;
        for (Usable usable : collection) {
            if (usable.getId().equals(id)) {
                isUnique = false;
                break;
            }
        }
        return isUnique;
    }

    public OutputMessageType sell(String id) {
        Object obj = searchCardOrItemWithId(id);
        if (obj != null) {
            if (obj instanceof Card) {
                Card card = (Card) obj;
                dataBase.getLoggedInAccount().addMoney(card.getPrice());
                cards.remove(card);
                for (Deck deck : decks) {
                    deck.getCards().remove(card);
                    if (deck.getHero().equals(card)) {
                        deck.setHero(null);
                    }
                }
            }
            if (obj instanceof Usable) {
                Usable usable = (Usable) obj;
                dataBase.getLoggedInAccount().addMoney(usable.getPrice());
                items.remove(usable);
                for (Deck deck : decks) {
                    if (deck.getItem().equals(usable)) {
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
            output.add(OutputMessageType.NO_SUCH_CARD_IN_COLLECTION.getMessage());
        }
        return output;
    }
}
