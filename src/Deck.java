import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards = new ArrayList<>();
    private Unit hero;
    private Item item;
    private String name;

    public Deck(Deck deck){
        this.cards=new ArrayList<>(deck.getCards());
        this.hero=deck.hero;
        this.item=deck.item;
        this.name=deck.name;
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
        return (hero!=null&&item!=null&&cards.size()==20);
    }
}
