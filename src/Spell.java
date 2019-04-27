import java.util.ArrayList;
import java.util.List;

abstract class Spell extends Card {
    private int apChange;
    private int hpChange;
    private List<Buff> addedBuffsToCells = new ArrayList<>();
    private List<Buff> addedBuffsToUnits = new ArrayList<>();
    private SpellActivationType activationType;
    private String description;
    private String name;
    private boolean isDispeller;

    public int getApChange() {
        return apChange;
    }

    public int getHpChange() {
        return hpChange;
    }

    public String getDescription() {
        return description;
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

    public List<Buff> getAddedBuffsToCells() {
        return addedBuffsToCells;
    }

    public void setAddedBuffsToCells(List<Buff> addedBuffsToCells) {
        this.addedBuffsToCells = addedBuffsToCells;
    }

    abstract public void doSpell(int insertionRow, int insertionColumn);

    public boolean isDispeller() {
        return isDispeller;
    }

    public void setDispeller(boolean dispeller) {
        isDispeller = dispeller;
    }
}
