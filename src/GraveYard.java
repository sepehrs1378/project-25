import java.util.List;
import java.util.ArrayList;

class GraveYard {
    private List<Card> deadCards = new ArrayList<>();

    public List<Card> getDeadCards() {
        return deadCards;
    }

    public void addDeadCard(Card deadCard) {
        deadCards.add(deadCard);
    }

    public Card findCard(String cardId) {
        for (Card card : deadCards) {
            if (card.getId().equals(cardId)) {
                return card;
            }
        }
        return null;
    }

}
