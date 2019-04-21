import java.security.AllPermission;
import java.util.ArrayList;
import java.util.List;

class Unit extends Card {
    private static final DataBase dataBase = DataBase.getInstance();
    private int hp;
    private int ap;
    private int minRange;
    private int maxRange;
    private List<Flag> flags = new ArrayList<>();
    private List<Buff> buffs = new ArrayList<>();
    private String heroOrMinion;
    private Spell specialPower;
    private boolean didAttackThisTurn;
    private boolean didMoveThisTurn;

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

    public Spell getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Spell specialPower) {
        this.specialPower = specialPower;
    }

    public int getAp() {
        return ap;
    }

    public String getHeroOrMinion() {
        return heroOrMinion;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public void setHeroOrMinion(String heroOrMinion) {
        this.heroOrMinion = heroOrMinion;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(List<Buff> buffs) {
        this.buffs = buffs;
    }

    public void addFlag(Flag newFlag) {
        flags.add(newFlag);
    }

    public void dropFlags() {

    }

    public void doSpecialPower() {

    }

    public OutputMessageType attackUnit(String targetID) {
//        if (dataBase.getCurrentBattle().getPlayerInTurn().getSelectedUnit() == null)
//            return OutputMessageType.UNIT_NOT_SELECTED;
//        todo isn't needed because "this" refers to the unit
        if (dataBase.getCurrentBattle().getPlayerInTurn().
                getSelectedUnit().didAttackThisTurn)
            return OutputMessageType.ALREADY_ATTACKED;
        if (!dataBase.getCurrentBattle().getBattleGround().doesHaveUnit(targetID))
            return OutputMessageType.INVALID_CARD;
        if (false) {
            //todo check range
            return OutputMessageType.TARGET_NOT_IN_RANGE;
        }
        this.didAttackThisTurn = true;
        Unit targetedUnit = dataBase.getCurrentBattle().getBattleGround().
                getUnitWithID(targetID);
        targetedUnit.changeHp(-this.ap);
        return OutputMessageType.ATTACKED_SUCCESSFULLY;
    }

    public void counterAttackUnit(Unit unit) {

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

    public boolean isDidAttackThisTurn() {
        return didAttackThisTurn;
    }

    public void setDidAttackThisTurn(boolean didAttackThisTurn) {
        this.didAttackThisTurn = didAttackThisTurn;
    }

    public boolean isDidMoveThisTurn() {
        return didMoveThisTurn;
    }

    public void setDidMoveThisTurn(boolean didMoveThisTurn) {
        this.didMoveThisTurn = didMoveThisTurn;
    }
}
