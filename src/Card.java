class Card {
    private String cardID;
    private int price;
    private int mana;
    private String name;

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

    public static void addCard(Card newCard) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
