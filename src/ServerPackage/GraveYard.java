package ServerPackage;

import java.util.ArrayList;
import java.util.List;

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
