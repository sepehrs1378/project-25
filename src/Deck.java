import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards = new ArrayList<>();
    private Unit hero;
    private Item item;
    private String name;

    public Deck(Deck deck) {
        this.cards = new ArrayList<>();
        for (Card card:deck.getCards()){
            if (card instanceof Unit){
                cards.add(((Unit)card).clone());
            }else if(card instanceof Spell){
                cards.add(((Spell)card).clone());
            }
        }
        this.hero = deck.hero.clone();
        this.item = deck.item;
        this.name = deck.name;
    }

    public Deck(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Unit getHero() {
        return hero;
    }

    public Item getItem() {
        return item;
    }

    public void setHero(Unit hero) {
        this.hero = hero;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void addToCards(Card newCard) {
        cards.add(newCard);
    }

    public void deleteFromCards(Card card) {
        cards.remove(card);
    }

    public boolean isValid() {
        return cards.size() == 20 && hero != null;
    }

    public Card getCardByCardId(String cardID) {
        for (Card card : cards) {
            if (card.getId().equals(cardID)) {
                return card;
            }
        }
        return null;
    }

    public boolean hasCard(String cardID) {
        for (Card card : cards) {
            if (card.getId().equals(cardID)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCard(Card card) {
        for (Card card1 : cards) {
            if (card1 == card)
                return true;
        }
        return false;
    }
}
