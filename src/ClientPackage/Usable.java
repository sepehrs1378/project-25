package ClientPackage;

public class Usable extends Item {
    private int price;
    private Spell passivePower;

    public Usable(String id, String description, int price, Spell passivePower) {
        super(id, description);
        this.price = price;
        this.passivePower = passivePower;
    }

    public Usable clone() {
        return new Usable(getId(), getDescription(),
                price, passivePower.clone());
    }

    public int getPrice() {
        return price;
    }
}