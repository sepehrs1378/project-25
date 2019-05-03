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

    public void attackUnit(String targetId) {
        if (this.didAttackThisTurn)
            return;
        if (!dataBase.getCurrentBattle().getBattleGround().doesHaveUnit(targetId))
            return;
        if (!isTargetUnitWithinRange(targetId))
            return;
        this.didAttackThisTurn = true;
        Unit targetedUnit = dataBase.getCurrentBattle().getBattleGround().
                getUnitWithID(targetId);
        int damageDealt = calculateDamageDealt(this, targetedUnit);
        targetedUnit.changeHp(-damageDealt);
        if (this.specialPower.getActivationType().equals(SpellActivationType.ON_ATTACK))
            this.specialPower.doSpell(targetedUnit);
    }

    public OutputMessageType attack(String targetId) {
        if (dataBase.getCurrentBattle().getPlayerInTurn().
                getSelectedUnit().didAttackThisTurn)
            return OutputMessageType.ALREADY_ATTACKED;
        if (!dataBase.getCurrentBattle().getBattleGround().doesHaveUnit(targetId))
            return OutputMessageType.INVALID_CARD;
        if (!isTargetUnitWithinRange(targetId))
            return OutputMessageType.TARGET_NOT_IN_RANGE;
        this.attackUnit(targetId);
        return OutputMessageType.ATTACKED_SUCCESSFULLY;
    }

    public static OutputMessageType attackCombo(String targetId, String[] attackersIds) {
        Unit target = dataBase.getCurrentBattle()
                .getBattleGround().getUnitWithID(targetId);
        List<Unit> attackers = new ArrayList<>();
        for (String attackerId : attackersIds) {
            Unit attacker = dataBase.getCurrentBattle()
                    .getBattleGround().getUnitWithID(attackerId);
            if (attacker == null)
                return OutputMessageType.A_UNIT_DOESNT_EXIST;
            if (!attacker.canUseComboAttack)
                return OutputMessageType.A_UNIT_CANT_USE_COMBO;
            if (!attacker.canAttackTarget(target))
                return OutputMessageType.A_UNIT_CANT_ATTACK_TARGET;
            attackers.add(attacker);
        }
        for (Unit attacker : attackers) {
            attacker.attackUnit(targetId);
        }
        Unit targetUnit = dataBase.getCurrentBattle()
                .getBattleGround().getUnitWithID(targetId);
        targetUnit.counterAttackUnit(attackers.get(0));
        return OutputMessageType.COMBO_ATTACK_SUCCESSFUL;
    }

    public void counterAttackUnit(Unit unit) {
        if (!this.isDisarmed() && !this.isStuned()) {
            attackUnit(unit.getId());
        }
    }

    public boolean canAttackTarget(Unit unit) {
        if (dataBase.getCurrentBattle().getBattleGround()
                .isUnitFriendlyOrEnemy(unit).equals(Constants.FRIEND))
            return false;
        if (this.isStuned())
            return false;
        if (this.didAttackThisTurn)
            return false;
        if (!isTargetUnitWithinRange(unit.getId()))
            return false;
        return true;
    }

    private int calculateDamageDealt(Unit attackerUnit, Unit targetedUnit) {
        if (targetedUnit.isImmuneTo(Constants.WEAKER_AP)
                && targetedUnit.ap > attackerUnit.ap)
            return 0;
        if (this.isImmuneTo(Constants.HOLY_BUFF))
            return attackerUnit.ap + targetedUnit.getNegativeArmor();
        else
            return attackerUnit.ap - targetedUnit.getPositiveArmor()
                    + targetedUnit.getNegativeArmor();
    }

    private boolean isTargetUnitWithinRange(String targetID) {
        Unit targetUnit = dataBase.getCurrentBattle().getBattleGround().getUnitWithID(targetID);
        BattleGround battleGround = dataBase.getCurrentBattle().getBattleGround();
        int distanceToTarget = getDistanceToTarget(
                battleGround.getCoordinationOfUnit(targetUnit)[0],
                battleGround.getCoordinationOfUnit(targetUnit)[1]);
        return distanceToTarget <= maxRange && distanceToTarget >= minRange;
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
                    && ((ImmunityBuff) buff).getImmunity().contains(effect))
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

    public int getNegativeArmor() {
        int armor = 0;
        for (Buff buff : buffs) {
            if (buff instanceof NegativeArmorBuff)
                armor += ((NegativeArmorBuff) buff).getNegativeArmorAmount();
        }
        return armor;
    }

    public int getPositiveArmor() {
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
