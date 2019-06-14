import java.util.ArrayList;
import java.util.List;

class Spell extends Card {
    private transient DataBase dataBase = DataBase.getInstance();
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

    public Spell(String id, String name, int price, int mana,
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

    public void doSpell(Unit unit) {
        addBuffsToUnit(unit);
        if (isDispeller())
            dispelBuffsOfUnit(unit);
        unit.changeAp(getHpChange());
        unit.changeHp(getApChange());
    }

    public void doSpell(int insertionRow, int insertionColumn) {
        doSpellEffectOnCells(insertionRow, insertionColumn);
        doSpellEffectOnUnits(insertionRow, insertionColumn);
    }

    public Spell clone() {
        return new Spell(getId(), getName(), getPrice(),
                getMana(), apChange, hpChange, coolDown,
                target, addedBuffs, activationType,
                description, isDispeller);
    }

    private void doSpellEffectOnCells(int insertionRow, int insertionColumn) {
        List<Cell> targetCells = target.getTargetCells(insertionRow, insertionColumn);
        for (Cell cell : targetCells) {
            for (Buff buff : getAddedBuffs()) {
                Buff cloneBuff = buff.clone();
                cloneBuff.setDead(false);
                cloneBuff.setStartTurn(dataBase.getCurrentBattle().getTurnNumber());
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
            addSpellsToUnit(unit);
            if (isDispeller)
                dispelBuffsOfUnit(unit);
            unit.changeAp(getApChange());
            unit.changeHp(getHpChange());
        }
    }

    private void addSpellsToUnit(Unit unit) {
        for (Spell spell : addedSpells) {
            unit.getSpecialPowers().add(spell);
        }
    }

    private void addBuffsToUnit(Unit unit) {
        for (Buff buff : addedBuffs) {
            if (buff != null && unit != null) {
                if (buff instanceof PoisonBuff && unit.isImmuneTo(Constants.POISON))
                    continue;
                if (buff instanceof DisarmBuff && unit.isImmuneTo(Constants.DISARM))
                    continue;
                Buff cloneBuff = buff.clone();
                cloneBuff.setDead(false);
                cloneBuff.setStartTurn(dataBase.getCurrentBattle().getTurnNumber());
                unit.getBuffs().add(cloneBuff);
            }
        }
    }

    private void dispelBuffsOfUnit(Unit unit) {
        int i = 0;
        //if unit is friendly we removeIdFromDeckName negative buffs
        if (dataBase.getCurrentBattle().getBattleGround()
                .isUnitFriendlyOrEnemy(unit).equals(Constants.FRIEND)) {
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
}
