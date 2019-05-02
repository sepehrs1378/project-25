import java.util.ArrayList;
import java.util.List;

class Hand {
    private List<Card> cards = new ArrayList<>();

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card newCard) {
        cards.add(newCard);
    }

    public void deleteCard(Card card) {
        cards.remove(card);
    }

    public Card getCard(String cardId){
        for(Card card:cards){
            if(card.getId().equals(cardId)){
                return card;
            }
        }
        return null;
    }

}
