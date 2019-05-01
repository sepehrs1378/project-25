
import java.util.ArrayList;

import Buff.*;

import java.util.List;

public class Cell {
    private Unit unit;
    private Item item;
    private List<Buff> buffs = new ArrayList<>();
    private ArrayList<Flag> flags = new ArrayList<>();

    public Cell() {
    }

    public Unit getUnit() {
        return unit;
    }

    public Item getItem() {
        return item;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void deleteBuff(Buff buff) {
        buffs.remove(buff);
    }

    public ArrayList<Flag> getFlags() {
        return this.flags;
    }

    public void addFlag(Flag flag) {
        flags.add(flag);
    }

    public boolean isEmptyOfUnit() {
        return unit == null;
    }
}
