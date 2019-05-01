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
    private boolean isDispeller;

    public Spell(String id, String name, int price, int mana,
                 int apChange, int hpChange, int cooldown,
                 Target target, List<Buff> addedBuffsToCells,
                 List<Buff> addedBuffsToUnits, SpellActivationType activationType
            , String description, boolean isDispeller) {
        super(id, name, price, mana);
        this.apChange = apChange;
        this.hpChange = hpChange;
        this.cooldown = cooldown;
        this.target = target;
        this.addedBuffsToCells = addedBuffsToCells;
        this.addedBuffsToUnits = addedBuffsToUnits;
        this.activationType = activationType;
        this.description = description;
        this.isDispeller = isDispeller;
    }

    public void doSpell(Unit unit) {
        addBuffsToUnit(unit);
        if (isDispeller())
            removeBuffsFromUnit(unit);
        unit.changeAp(getHpChange());
        unit.changeHp(getApChange());
    }

    public Spell clone() {
        return new Spell(getId(), getName(), getPrice(),
                getMana(), apChange, hpChange, cooldown,
                target.clone(), addedBuffsToCells, addedBuffsToUnits,
                activationType, description, isDispeller);
    }

    public void doSpell(int insertionRow, int insertionColumn) {
        doSpellEffectOnCells(insertionRow, insertionColumn);
        doSpellEffectOnUnits(insertionRow, insertionColumn);
    }

    private void doSpellEffectOnCells(int insertionRow, int insertionColumn) {
        List<Cell> targetCells = target.getTargetCells(insertionRow, insertionColumn);
        for (Cell cell : targetCells) {
            for (Buff buff : getAddedBuffsToCells()) {
                cell.getBuffs().add(buff);
            }
        }
    }

    private void doSpellEffectOnUnits(int insertionRow, int insertionColumn) {
        List<Unit> targetUnits = target.getTargetUnits(insertionRow, insertionColumn);
        for (Unit unit : targetUnits) {
            if (unit.isImmuneTo(Constants.ENEMY_CARD_SPELL)
                    && dataBase.getCurrentBattle().getBattleGround().
                    isUnitFriendlyOrEnemy(unit).equals(Constants.ENEMY))
                continue;
            addBuffsToUnit(unit);
            if (isDispeller)
                removeBuffsFromUnit(unit);
            unit.changeAp(getApChange());
            unit.changeHp(getHpChange());
        }
    }

    private void addBuffsToUnit(Unit unit) {
        for (Buff buff : getAddedBuffsToUnits()) {
            if (buff instanceof PoisonBuff && unit.isImmuneTo(Constants.POISON))
                continue;
            if (buff instanceof DisarmBuff && unit.isImmuneTo(Constants.DISARM))
                continue;
            unit.getBuffs().add(buff);
        }
    }

    private void removeBuffsFromUnit(Unit unit) {
        int i = 0;
        //if unit is friendly we remove negative buffs
        if (dataBase.getCurrentBattle().getBattleGround()
                .isUnitFriendlyOrEnemy(unit).equals(Constants.FRIEND)) {
            while (i < unit.getBuffs().size()) {
                Buff buff = unit.getBuffs().get(i);
                if (buff.getPositiveOrNegative().equals(Constants.NEGATIVE)
                        && buff.isDispellable()) {
                    unit.getBuffs().remove(i);
                    continue;
                }
                i++;
            }
        }
        //if unit is enemy we remove positive buffs
        else {
            while (i < unit.getBuffs().size()) {
                Buff buff = unit.getBuffs().get(i);
                if (buff.getPositiveOrNegative().equals(Constants.POSITIVE)
                        && buff.isDispellable()) {
                    unit.getBuffs().remove(i);
                    continue;
                }
                i++;
            }
        }
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
