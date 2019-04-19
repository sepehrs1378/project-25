public class Usable extends Item {
    private int price;
    private Spell passivePower;

    public Usable(String itemID, String description, int price) {
        super(itemID, description);
        this.price = price;
        //todo what about passivePower??
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void doSpell() {

    }
}
