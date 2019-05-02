public class Collectable extends Item {
    private Spell spell;

    public Collectable(String itemID, String description, Spell spell) {
        super(itemID, description);
        this.spell = spell;
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }
}
