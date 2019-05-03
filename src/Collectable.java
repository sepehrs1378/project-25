public class Collectable extends Item {
    private Spell spell;

    public Collectable(String id, String description, Spell spell) {
        super(id, description);
        this.spell = spell;
    }

    public Collectable clone() {
        return new Collectable(getId(), getDescription(), spell.clone());
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }
}
