import java.util.ArrayList;
import java.util.List;

class PlayerInfo {
    private String playerName;
    private PlayerCollection collection = new PlayerCollection();
    private List<Deck> decks = new ArrayList<>();
    //private Deck mainDeck;

    public void addCardToCollection(Card newCard) {
        collection.addCard(newCard);
    }

    public void addUsableToCollection(Usable usable){
        collection.addItem(usable);
    }

//    public void setMainDeck(Deck mainDeck) {
//        this.mainDeck = mainDeck;
//    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public PlayerCollection getCollection() {
        return collection;
    }

//    public Deck getMainDeck() {
//        return mainDeck;
//    }

    public String getPlayerName(){
        return playerName;
    }

    public void addDeckToDecks(Deck newDeck) {
        decks.add(newDeck);
    }

    public Deck getDeck(String deckName) {
        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i).getName().equals(deckName)) {
                return decks.get(i);
            }
        }
        return null;
    }

    public boolean doesDeckExist(String deckName) {
        if (getDeck(deckName) == null) {
            return false;
        } else {
            return false;
        }
    }
}
