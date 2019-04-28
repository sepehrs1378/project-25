import java.util.ArrayList;
import java.util.List;

class Spell extends Card {
    public static final DataBase dataBase = DataBase.getInstance();
    private int apChange;
    private int hpChange;
    private int cooldown;
    private Target target;
    private List<Buff> addedBuffsToCells = new ArrayList<>();
    private List<Buff> addedBuffsToUnits = new ArrayList<>();
    private SpellActivationType activationType;
    private String description;
    private String name;
    private boolean isDispeller;

    public Spell(int apChange, int hpChange, int cooldown,
                 Target target, List<Buff> addedBuffsToCells,
                 List<Buff> addedBuffsToUnits, SpellActivationType activationType
            , String description, String name, boolean isDispeller) {
        this.apChange = apChange;
        this.hpChange = hpChange;
        this.cooldown = cooldown;
        this.target = target;
        this.addedBuffsToCells = addedBuffsToCells;
        this.addedBuffsToUnits = addedBuffsToUnits;
        this.activationType = activationType;
        this.description = description;
        this.name = name;
        this.isDispeller = isDispeller;
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

    public boolean isDispeller() {
        return isDispeller;
    }

    public void setDispeller(boolean dispeller) {
        isDispeller = dispeller;
    }

    public void doSpell(Unit unit) {
        for (Buff buff : getAddedBuffsToUnits()) {
            unit.getBuffs().add(buff);
        }
        if (isDispeller())
            removeBuffsFromUnit(unit);
        unit.changeAp(getHpChange());
        unit.changeHp(getApChange());
    }

    public void doSpell(int insertionRow, int insertionColumn) {
        doSpellEffectOnCells(insertionRow, insertionColumn);
        doSpellEffectOnUnits(insertionRow, insertionColumn);
    }

    public void doSpellEffectOnCells(int insertionRow, int insertionColumn) {
        List<Cell> targetCells = target.getCells(insertionRow, insertionColumn);
        for (Cell cell : targetCells) {
            for (Buff buff : getAddedBuffsToCells()) {
                cell.getBuffs().add(buff);
            }
        }
    }

    public void doSpellEffectOnUnits(int insertionRow, int insertionColumn) {
        List<Unit> targetUnits = target.getUnits(insertionRow, insertionColumn);
        for (Unit unit : targetUnits) {
            for (Buff buff : getAddedBuffsToUnits()) {
                unit.getBuffs().add(buff);
            }
            if (isDispeller()) {
                removeBuffsFromUnit(unit);
            }
            unit.changeAp(getApChange());
            unit.changeHp(getHpChange());
        }
    }

    private void removeBuffsFromUnit(Unit unit) {
        int i = 0;
        if (dataBase.getCurrentBattle().getBattleGround()
                .isUnitFriendlyOrEnemy(unit).equals(Constants.FRIEND)) {
            while (i < unit.getBuffs().size()) {
                if (unit.getBuffs().get(i).getPositiveOrNegative().equals(Constants.NEGATIVE)) {
                    unit.getBuffs().remove(i);
                    continue;
                }
                i++;
            }
        } else {
            while (i < unit.getBuffs().size()) {
                if (unit.getBuffs().get(i).getPositiveOrNegative().equals(Constants.POSITIVE)) {
                    unit.getBuffs().remove(i);
                    continue;
                }
                i++;
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public SpellActivationType getActivationType() {
        return activationType;
    }

    public void setActivationType(SpellActivationType activationType) {
        this.activationType = activationType;
    }
}
