import java.util.ArrayList;
import java.util.List;

abstract class Spell extends Card {
    private int coolDown;
    private int apChange;
    private int hpChange;
    private List<Buff> addedBuffsToCells = new ArrayList<>();
    private List<Buff> addedBuffsToUnits = new ArrayList<>();
    private String passiveOrCastable;
    private String description;
    private String name;

    public int getCooldown() {
        return coolDown;
    }

    public int getApChange() {
        return apChange;
    }

    public int getHpChange() {
        return hpChange;
    }

    public String getDescription() {
        return description;
    }

    public void setCooldown(int cooldown) {
        this.coolDown = cooldown;
    }

    public void setApChange(int apChange) {
        this.apChange = apChange;
    }

    public void setHpChange(int hpChange) {
        this.hpChange = hpChange;
    }

    public List<Buff> getAddedBuffsToUnits() {
        return addedBuffsToUnits;
    }

    public void setAddedBuffsToUnits(List<Buff> addedBuffsToUnits) {
        this.addedBuffsToUnits = addedBuffsToUnits;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    abstract public void doSpell(int insertionRow, int insertionColumn);
}
