import java.util.ArrayList;
import java.util.List;

class Spell extends Card {
    public static final String PASSIVE = "passive";
    public static final String CASTABLE = "castable";
    private Target target;
    private int coolDown;
    private int apChange;
    private int hpChange;
    private List<Buff> addedBuffs = new ArrayList<>();
    private List<Buff> deletedBuffs = new ArrayList<>();
    private List<Unit> addedUnits = new ArrayList<>();
    private String passiveOrCastable;
    private String description;
    private String name;

    public Target getTarget() {
        return target;
    }

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

    public void setTarget(Target target) {
        this.target = target;
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

    public void doSpell() {

    }

    public void changeAp() {

    }

    public void changeHp() {

    }

    public void addBuffs() {

    }

    public void deleteBuffs() {

    }

    public void createUnits() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
