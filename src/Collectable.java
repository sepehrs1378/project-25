public class Collectable extends Item {
    private Spell spell;

    public Collectable(String itemID, String description, Spell spell) {
        super(itemID, description);
        this.spell = spell;
    }
}
