import java.util.ArrayList;
import java.util.List;

class PlayerCollection {
    private List<Card> cards = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public List<Card> getCards(){
        return cards;
    }

    public List<Item> getItems(){
        return items;
    }

    public void addCard(Card newCard){
        cards.add(newCard);
    }

    public void addItem(Item newItem){
        items.add(newItem);
    }

    public void deleteCard(Card card){
        cards.remove(card);
    }

    public void deleteItem(Item item){
        items.remove(item);
    }
}
