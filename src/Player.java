import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Player {
    private PlayerInfo playerInfo;
    private Hand hand;
    private Deck deck;
    private Card nextCard;
    private int mana = 2;
    private GraveYard graveYard = new GraveYard();
    private Unit selectedUnit;
    private Collectable selectedCollectable;

    public Player(PlayerInfo playerInfo, Deck deck) {
        this.playerInfo = playerInfo;
        this.deck = new Deck(deck);
        setHand(this.deck);
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public Hand getHand() {
        return hand;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getMana() {
        return mana;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public void setHand(Deck deck) {
        for (int i = 0; i < Constants.NUMBER_OF_HAND_CARDS; i++) {
            int randomNumber = (int) (Math.random() * deck.getCards().size());
            hand.addCard(deck.getCards().get(randomNumber));
            deck.getCards().remove(randomNumber);
        }

    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public GraveYard getGraveYard() {
        return graveYard;
    }

    public Card getNextCard() {
        return nextCard;
    }

    public void setNextCard(Deck deck) {
        int randomNumber = (int) (Math.random() * deck.getCards().size());
        if (deck.getCards().size() != 0) {
            nextCard = deck.getCards().get(randomNumber);
            deck.getCards().remove(randomNumber);
        }
    }

    public void moveNextCardToHand() {
        if (hand.getCards().size() < 5) {
            hand.getCards().add(nextCard);
            setNextCard(this.deck);
        }
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public Collectable getSelectedCollectable() {
        return selectedCollectable;
    }

    public void setSelectedCollectable(Collectable selectedCollectable) {
        this.selectedCollectable = selectedCollectable;
    }
}
