import java.util.ArrayList;
import java.util.List;

class PlayerInfo {
    private String  playerName;
    private PlayerCollection collection;
    private List<Deck> decks=new ArrayList<Deck>();
    private Deck mainDeck;
    private int money;

    public void addCardToCollection(Card newCard){

    }

    public void setMainDeck(Deck mainDeck){
        this.mainDeck=mainDeck;
    }

    public void setPlayerName(String playerName){
        this.playerName=playerName;
    }

    public PlayerCollection getCollection() {
        return collection;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public String getPlayerName(){
        return playerName;
    }

    public int getMoney(){
        return money;
    }

    public void addDeckToDecks(Deck newDeck){

    }

    public void addMoney(int addedMoney){
        money+=addedMoney;
    }

    public void takeAwayMoney(int tookAwayMoney){
        money-=tookAwayMoney;
    }
}
