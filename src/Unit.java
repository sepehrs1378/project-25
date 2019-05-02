import java.util.ArrayList;
import java.util.List;

public class Unit extends Card {
    private static final DataBase dataBase = DataBase.getInstance();
    private int hp;
    private int ap;
    private int minRange;
    private int maxRange;
    private Spell specialPower;
    private List<Flag> flags = new ArrayList<>();
    private List<Buff> buffs = new ArrayList<>();
    private String heroOrMinion;
    private String description;
    private boolean didAttackThisTurn;
    private boolean didMoveThisTurn;
    private boolean canUseComboAttack;

    public Unit(String id, String name, int price, int mana, int hp, int ap,
                int minRange, int maxRange, Spell specialPower, String heroOrMinion,
                String description, boolean canUseComboAttack) {
        super(id, name, price, mana);
        this.hp = hp;
        this.ap = ap;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.specialPower = specialPower;
        this.heroOrMinion = heroOrMinion;
        this.description = description;
        this.canUseComboAttack = canUseComboAttack;
    }

    public Unit clone() {
        return new Unit(getId(), getName(), getPrice(),
                getMana(), hp, ap, minRange, maxRange, specialPower.clone(),
                heroOrMinion, description, canUseComboAttack);
    }

    public int getHp() {
        return hp;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    public int getMinRange() {
        return minRange;
    }

    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }

    public int getAp() {
        return ap;
    }

    public String getHeroOrMinion() {
        return heroOrMinion;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public void addFlag(Flag newFlag) {
        flags.add(newFlag);
    }

    public OutputMessageType attackUnit(String targetId) {
        if (dataBase.getCurrentBattle().getPlayerInTurn().
                getSelectedUnit().didAttackThisTurn)
            return OutputMessageType.ALREADY_ATTACKED;
        if (!dataBase.getCurrentBattle().getBattleGround().doesHaveUnit(targetId))
            return OutputMessageType.INVALID_CARD;
        if (!isTargetUnitWithinRange(targetId))
            return OutputMessageType.TARGET_NOT_IN_RANGE;
        this.didAttackThisTurn = true;
        Unit targetedUnit = dataBase.getCurrentBattle().getBattleGround().
                getUnitWithID(targetId);
        int damageDealt = calculateDamageDealt(this, targetedUnit);
        targetedUnit.changeHp(-damageDealt);
        if (this.specialPower.getActivationType().equals(SpellActivationType.ON_ATTACK))
            this.specialPower.doSpell(targetedUnit);
        return OutputMessageType.ATTACKED_SUCCESSFULLY;
    }

    public static OutputMessageType attackCombo(String targetId, String[] attackers) {
        for ()
    }

    private int calculateDamageDealt(Unit attackerUnit, Unit targetedUnit) {
        if (targetedUnit.isImmuneTo(Constants.WEAKER_AP)
                && targetedUnit.ap > attackerUnit.ap)
            return 0;
        if (this.isImmuneTo(Constants.HOLY_BUFF))
            return attackerUnit.ap;
        else
            return attackerUnit.ap - targetedUnit.getArmor();
    }

    private boolean isTargetUnitWithinRange(String targetID) {
        Unit targetUnit = dataBase.getCurrentBattle().getBattleGround().getUnitWithID(targetID);
        BattleGround battleGround = dataBase.getCurrentBattle().getBattleGround();
        int distanceToTarget = getDistanceToTarget(
                battleGround.getCoordinationOfUnit(targetUnit)[0],
                battleGround.getCoordinationOfUnit(targetUnit)[1]);
        return distanceToTarget <= maxRange && distanceToTarget >= minRange;
    }

    public void counterAttackUnit(Unit unit) {
        if (!this.isDisarmed()) {
            attackUnit(unit.getId());//todo delete attackUnit in it and complete it without that
            //todo maybe not complete
        }
    }

    public List<Flag> getFlags() {
        return flags;
    }

    public String getUnitClass() {
        if (minRange == 1 && maxRange == 1)
            return Constants.MELEE;
        if (minRange == 1)
            return Constants.HYBRID;
        return Constants.RANGED;
    }

    public void changeHp(int hpChange) {
        hp += hpChange;
        if (hp < 0)
            hp = 0;
    }

    public void changeAp(int apChange) {
        ap += apChange;
        if (ap < 0)
            ap = 0;
    }

    public boolean didAttackThisTurn() {
        return didAttackThisTurn;
    }

    public void setDidAttackThisTurn(boolean didAttackThisTurn) {
        this.didAttackThisTurn = didAttackThisTurn;
    }

    public boolean didMoveThisTurn() {
        return didMoveThisTurn;
    }

    public void setDidMoveThisTurn(boolean didMoveThisTurn) {
        this.didMoveThisTurn = didMoveThisTurn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDisarmed() {
        for (Buff buff : buffs) {
            if (buff instanceof DisarmBuff)
                return true;
        }
        return false;
    }

    public boolean isStuned() {
        for (Buff buff : buffs) {
            if (buff instanceof StunBuff)
                return true;
        }
        return false;
    }

    public boolean isImmuneTo(String effect) {
        for (Buff buff : buffs) {
            if (buff instanceof ImmunityBuff
                    && ((ImmunityBuff) buff).getImmunities().contains(effect))
                return true;
        }
        return false;
    }

    public int getDistanceToTarget(int targetRow, int targetColumn) {
        int unitRow = dataBase.getCurrentBattle().getBattleGround()
                .getCoordinationOfUnit(this)[0];
        int unitColumn = dataBase.getCurrentBattle().getBattleGround()
                .getCoordinationOfUnit(this)[1];
        return dataBase.getCurrentBattle().getBattleGround()
                .getDistance(unitRow, unitColumn, targetRow, targetColumn);
    }

    public void takeFlags(List<Flag> flags) {
        this.flags = flags;
    }

    public void dropFlags() {
        for (Flag flag : flags)
            flag.setTurnsInUnitHand(0);
        this.flags = null;
    }

    public int getArmor() {
        int armor = 0;
        for (Buff buff : buffs) {
            if (buff instanceof HolyBuff)
                armor += ((HolyBuff) buff).getArmor();
        }
        Cell cell = dataBase.getCurrentBattle().getBattleGround().getCellOfUnit(this);
        for (Buff buff : cell.getBuffs()) {
            if (buff instanceof HolyBuff)
                armor += ((HolyBuff) buff).getArmor();
        }
        return armor;
    }

    public boolean canUseComboAttack() {
        return canUseComboAttack;
    }

    public void setCanUseComboAttack(boolean canUseComboAttack) {
        this.canUseComboAttack = canUseComboAttack;
    }

    public Spell getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Spell specialPower) {
        this.specialPower = specialPower;
    }
}
