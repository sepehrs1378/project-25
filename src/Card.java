import java.util.ArrayList;
import java.util.List;

class Card {
    private static List<Card> cardList= new ArrayList<>();
    private String cardID;
    private int price;
    private int mana;

    public String getCardID() {
        return cardID;
    }

    public int getPrice() {
        return price;
    }

    public int getMana() {
        return mana;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public static List<Card> getCardList(){
        return cardList;
    }

    public static void addCard(Card newCard){
        cardList.add(newCard);
    }

    public static void deleteCard(Card card){
        cardList.remove(card);
    }

    public Card getCardWithName(String cardName){
        for (int i = 0; i < cardList.size(); i++){
            if (cardList.get(i).cardID.equals(cardName)){
                return cardList.get(i);
            }
        }
        return null;
    }

    public boolean doesCardExist(String cardName){
        if (getCardWithName(cardName) == null){
            return false;
        }else {
            return true;
        }
    }
}