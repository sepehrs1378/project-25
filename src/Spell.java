import java.util.ArrayList;
import java.util.List;

abstract class Spell extends Card {
    private int coolDown;
    private int apChange;
    private int hpChange;
    private List<Buff> addedBuffsToCells = new ArrayList<>();
    private List<Buff> deletedBuffsFromCells = new ArrayList<>();
    private List<Buff> addedBuffsToUnits = new ArrayList<>();
    private List<Buff> deletedBuffsFromUnits = new ArrayList<>();
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

    abstract public void doSpell(int insertionRow, int insertionColumn);

    public List<Buff> getAddedBuffsToUnits() {
        return addedBuffsToUnits;
    }

    public void setAddedBuffsToUnits(List<Buff> addedBuffsToUnits) {
        this.addedBuffsToUnits = addedBuffsToUnits;
    }

    public List<Buff> getDeletedBuffsFromUnits() {
        return deletedBuffsFromUnits;
    }

    public void setDeletedBuffsFromUnits(List<Buff> deletedBuffsFromUnits) {
        this.deletedBuffsFromUnits = deletedBuffsFromUnits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
