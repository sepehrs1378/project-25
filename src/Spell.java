import java.util.List;

class Spell extends Card {
    public static final DataBase dataBase = DataBase.getInstance();
    private int apChange;
    private int hpChange;
    private int cooldown;
    private Target target;
    private List<Buff> addedBuffs;
    private SpellActivationType activationType;
    private String description;
    private boolean isDispeller;

    public Spell(String id, String name, int price, int mana,
                 int apChange, int hpChange, int cooldown,
                 Target target, Buff addedBuff,
                 SpellActivationType activationType,
                 String description, boolean isDispeller) {
        super(id, name, price, mana);
        this.apChange = apChange;
        this.hpChange = hpChange;
        this.cooldown = cooldown;
        this.target = target;
        this.activationType = activationType;
        this.description = description;
        this.isDispeller = isDispeller;
        this.addedBuffs.add(addedBuff);
    }

    public Spell(String id, String name, int price, int mana,
                 int apChange, int hpChange, int cooldown,
                 Target target, List<Buff> addedBuffs
            , SpellActivationType activationType
            , String description, boolean isDispeller) {
        super(id, name, price, mana);
        this.apChange = apChange;
        this.hpChange = hpChange;
        this.cooldown = cooldown;
        this.target = target;
        this.activationType = activationType;
        this.description = description;
        this.isDispeller = isDispeller;
        this.addedBuffs = addedBuffs;
    }

    public void doSpell(Unit unit) {
        addBuffsToUnit(unit);
        if (isDispeller())
            dispellBuffsOfUnit(unit);
        unit.changeAp(getHpChange());
        unit.changeHp(getApChange());
    }

    public void doSpell(int insertionRow, int insertionColumn) {
        doSpellEffectOnCells(insertionRow, insertionColumn);
        doSpellEffectOnUnits(insertionRow, insertionColumn);
    }

    public Spell clone() {
        return new Spell(getId(), getName(), getPrice(),
                getMana(), apChange, hpChange, cooldown,
                target.clone(), addedBuffs, activationType,
                description, isDispeller);
    }

    private void doSpellEffectOnCells(int insertionRow, int insertionColumn) {
        List<Cell> targetCells = target.getTargetCells(insertionRow, insertionColumn);
        for (Cell cell : targetCells) {
            for (Buff buff : getAddedBuffs()) {
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
                dispellBuffsOfUnit(unit);
            unit.changeAp(getApChange());
            unit.changeHp(getHpChange());
        }
    }

    private void addBuffsToUnit(Unit unit) {
        for (Buff buff : getAddedBuffs()) {
            if (buff instanceof PoisonBuff && unit.isImmuneTo(Constants.POISON))
                continue;
            if (buff instanceof DisarmBuff && unit.isImmuneTo(Constants.DISARM))
                continue;
            unit.getBuffs().add(buff);
        }
    }

    private void dispellBuffsOfUnit(Unit unit) {
        int i = 0;
        //if unit is friendly we remove negative buffs
        if (dataBase.getCurrentBattle().getBattleGround()
                .isUnitFriendlyOrEnemy(unit).equals(Constants.FRIEND)) {
            while (i < unit.getBuffs().size()) {
                Buff buff = unit.getBuffs().get(i);
                if (buff.getPositiveOrNegative().equals(Constants.NEGATIVE)
                        && buff.isDispellable()) {
                    unit.getBuffs().get(i).setActive(false);
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

    public List<Buff> getAddedBuffs() {
        return addedBuffs;
    }

    public boolean isDispeller() {
        return isDispeller;
    }

    public int getCooldown() {
        return cooldown;
    }

    public SpellActivationType getActivationType() {
        return activationType;
    }
}
