import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Collectable> collectables = new ArrayList<>();
    private PlayerInfo playerInfo;
    private Hand hand = new Hand();
    private Deck deck;
    private Card nextCard;
    private int mana;
    private GraveYard graveYard = new GraveYard();
    private Unit selectedUnit;
    private List<Buff> buffs = new ArrayList<>();

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

    public void setHand(Deck deck) {
        for (int i = 0; i < Constants.NUMBER_OF_HAND_CARDS; i++) {
            int randomNumber = (int) (Math.random() * deck.getCards().size());
            hand.addCard(deck.getCards().get(randomNumber));
            deck.getCards().remove(randomNumber);
        }
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void reduceMana(int reducedMana) {
        mana -= reducedMana;
    }

    public void changeMana(int mana) {
        this.mana += mana;
    }

    public GraveYard getGraveYard() {
        return graveYard;
    }

    public Card getNextCard() {
        return nextCard;
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public List<Collectable> getCollectables() {
        return collectables;
    }

    public void setNextCard() {
        int randomNumber = (int) (Math.random() * deck.getCards().size());
        if (!deck.getCards().isEmpty()) {
            nextCard = deck.getCards().get(randomNumber);
            deck.getCards().remove(randomNumber);
        }
    }

    public void moveNextCardToHand() {
        if (hand.getCards().size() < 5) {
            hand.getCards().add(nextCard);
            setNextCard();
        }
    }

    public Collectable getCollectableWithID(String id) {
        for (Collectable collectable : collectables) {
            if (collectable.getId().equals(id))
                return collectable;
        }
        return null;
    }

    public boolean doesHaveCollectable(String id) {
        return getCollectableWithID(id) != null;
    }

    public OutputMessageType selectUnit(String id, Battle battle) {
        if (battle.getBattleGround().getUnitWithID(id) == null)
            return OutputMessageType.INVALID_COLLECTABLE_CARD;
        Unit unit = battle.getBattleGround().getUnitWithID(id);
        if (battle.getBattleGround().isUnitFriendlyOrEnemy(unit,battle).equals(Constants.ENEMY))
            return OutputMessageType.ENEMY_UNIT_SELECTED;
        if (unit.isStunned())
            return OutputMessageType.UNIT_IS_STUNNED;
        selectedUnit = unit;
        return OutputMessageType.SELECTED;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(List<Buff> buffs) {
        this.buffs = buffs;
    }
}