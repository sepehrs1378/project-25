import java.util.ArrayList;
import java.util.List;

class Spell extends Card {
    private static transient DataBase dataBase = DataBase.getInstance();
    private int apChange;
    private int hpChange;
    private int coolDown;
    private int turnsToGetReady = 0;
    private Target target;
    private List<Spell> addedSpells = new ArrayList<>();
    private List<Buff> addedBuffs = new ArrayList<>();
    private SpellActivationType activationType;
    private String description;
    private boolean isDispeller;

    Spell(String id, String name, int price, int mana,
                 int apChange, int hpChange, int coolDown,
                 Target target, Buff addedBuff,
                 SpellActivationType activationType,
                 String description, boolean isDispeller) {
        super(id, name, price, mana);
        this.apChange = apChange;
        this.hpChange = hpChange;
        this.coolDown = coolDown;
        this.target = target;
        this.activationType = activationType;
        this.description = description;
        this.isDispeller = isDispeller;
        this.addedBuffs.add(addedBuff);
    }

    public Spell(String id, String name, int price, int mana,
                 int apChange, int hpChange, int coolDown,
                 Target target, List<Buff> addedBuffs,
                 SpellActivationType activationType,
                 String description, boolean isDispeller) {
        super(id, name, price, mana);
        this.apChange = apChange;
        this.hpChange = hpChange;
        this.coolDown = coolDown;
        this.target = target;
        this.activationType = activationType;
        this.description = description;
        this.isDispeller = isDispeller;
        this.addedBuffs = addedBuffs;
    }

    public Spell(String id, String name, int price, int mana,
                 int apChange, int hpChange, int coolDown,
                 Target target, Buff addedBuff,
                 SpellActivationType activationType,
                 String description, boolean isDispeller,
                 Spell addedSpell) {
        super(id, name, price, mana);
        this.apChange = apChange;
        this.hpChange = hpChange;
        this.coolDown = coolDown;
        this.target = target;
        this.activationType = activationType;
        this.description = description;
        this.isDispeller = isDispeller;
        this.addedBuffs.add(addedBuff);
        this.addedSpells.add(addedSpell);
    }

    public void doSpell(Unit unit,Battle battle) {
        addBuffsToUnit(unit,battle);
        if (isDispeller())
            dispelBuffsOfUnit(unit,battle);
        unit.changeAp(getHpChange());
        unit.changeHp(getApChange());
    }

    public void doSpell(int insertionRow, int insertionColumn,Battle battle) {
        doSpellEffectOnCells(insertionRow, insertionColumn,battle);
        doSpellEffectOnUnits(insertionRow, insertionColumn,battle);
    }

    public Spell clone() {
        return new Spell(getId(), getName(), getPrice(),
                getMana(), apChange, hpChange, coolDown,
                target, addedBuffs, activationType,
                description, isDispeller);
    }

    private void doSpellEffectOnCells(int insertionRow, int insertionColumn,Battle battle) {
        List<Cell> targetCells = target.getTargetCells(insertionRow, insertionColumn,battle);
        for (Cell cell : targetCells) {
            for (Buff buff : getAddedBuffs()) {
                Buff cloneBuff = buff.clone();
                cloneBuff.setDead(false);
                cloneBuff.setStartTurn(battle.getTurnNumber());
                cell.getBuffs().add(buff);
            }
        }
    }

    private void doSpellEffectOnUnits(int insertionRow, int insertionColumn,Battle battle) {
        List<Unit> targetUnits = target.getTargetUnits(insertionRow, insertionColumn,battle);
        for (Unit unit : targetUnits) {
            if (unit.isImmuneTo(Constants.ENEMY_CARD_SPELL)
                    && battle.getBattleGround().
                    isUnitFriendlyOrEnemy(unit,battle).equals(Constants.ENEMY))
                continue;
            addBuffsToUnit(unit,battle);
            addSpellsToUnit(unit);
            if (isDispeller)
                dispelBuffsOfUnit(unit,battle);
            unit.changeAp(getApChange());
            unit.changeHp(getHpChange());
        }
    }

    private void addSpellsToUnit(Unit unit) {
        for (Spell spell : addedSpells) {
            unit.getSpecialPowers().add(spell);
        }
    }

    private void addBuffsToUnit(Unit unit,Battle battle) {
        for (Buff buff : addedBuffs) {
            if (buff != null && unit != null) {
                if (buff instanceof PoisonBuff && unit.isImmuneTo(Constants.POISON))
                    continue;
                if (buff instanceof DisarmBuff && unit.isImmuneTo(Constants.DISARM))
                    continue;
                Buff cloneBuff = buff.clone();
                cloneBuff.setDead(false);
                cloneBuff.setStartTurn(battle.getTurnNumber());
                unit.getBuffs().add(cloneBuff);
            }
        }
    }

    private void dispelBuffsOfUnit(Unit unit,Battle battle) {
        int i = 0;
        //if unit is friendly we removeIdFromDeckName negative buffs
        if (battle.getBattleGround().isUnitFriendlyOrEnemy(unit,battle).equals(Constants.FRIEND)) {
            while (i < unit.getBuffs().size()) {
                Buff buff = unit.getBuffs().get(i);
                if (buff.getPositiveOrNegative().equals(Constants.NEGATIVE)
                        && buff.isDispellable()) {
                    unit.getBuffs().get(i).setDead(true);
                    continue;
                }
                i++;
            }
        }
        //if unit is enemy we removeIdFromDeckName positive buffs
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

    public void changeTurnsToGetReady(int amount) {
        turnsToGetReady += amount;
        if (turnsToGetReady < 0)
            turnsToGetReady = 0;
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

    public int getCoolDown() {
        return coolDown;
    }

    public SpellActivationType getActivationType() {
        return activationType;
    }

    public int getTurnsToGetReady() {
        return turnsToGetReady;
    }

    public void setTurnsToGetReady(int turnsToGetReady) {
        this.turnsToGetReady = turnsToGetReady;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public void setActivationType(SpellActivationType activationType) {
        this.activationType = activationType;
    }
}
