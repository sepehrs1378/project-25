import java.util.ArrayList;
import java.util.List;

class Cell {
    private Unit unit;
    private Item item;
    private List<Buff> buffs = new ArrayList<>();

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

    public void addBuff(Buff newBuff) {
        buffs.add(newBuff);
    }

    public void deleteBuff(Buff buff){
        buffs.remove(buff);
    }
}
