import java.util.ArrayList;
import java.util.List;

public class Cell {
    private Unit unit;
    private Collectable collectable;
    private List<Buff> buffs = new ArrayList<>();
    private List<Flag> flags = new ArrayList<>();

    public Cell() {
    }

    public Collectable getCollectable() {
        return collectable;
    }

    public void setCollectable(Collectable collectable) {
        this.collectable = collectable;
    }

    public Unit getUnit() {
        return unit;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void deleteBuff(Buff buff) {
        buffs.remove(buff);
    }

    public List<Flag> getFlags() {
        return this.flags;
    }

    public void setFlags(List<Flag> flags) {
        this.flags = flags;
    }

    public void addFlag(Flag flag) {
        flags.add(flag);
    }

    public boolean isEmptyOfUnit() {
        return unit == null;
    }
}
