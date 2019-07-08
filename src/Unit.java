import java.util.ArrayList;
import java.util.List;

public class Unit extends Card {
    private int hp;
    private int ap;
    private int minRange;
    private int maxRange;
    private List<Spell> specialPowers = new ArrayList<>();
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
        this.specialPowers.add(specialPower);
        this.heroOrMinion = heroOrMinion;
        this.description = description;
        this.canUseComboAttack = canUseComboAttack;
    }

    public Unit(String id, String name, int price, int mana, int hp, int ap,
                int minRange, int maxRange, Spell specialPower, String heroOrMinion,
                String description, boolean canUseComboAttack, Buff buff) {
        super(id, name, price, mana);
        this.hp = hp;
        this.ap = ap;
        this.minRange = minRange;
        this.maxRange = maxRange;
        if (specialPower != null)
            this.specialPowers.add(specialPower);
        this.heroOrMinion = heroOrMinion;
        this.description = description;
        this.canUseComboAttack = canUseComboAttack;
        this.buffs.add(buff);
    }

    @Override
    public Unit clone() {
        Unit cloneUnit = new Unit(getId(), getName(), getPrice(), getMana(), hp, ap, minRange, maxRange, getMainSpecialPower(), heroOrMinion, description, canUseComboAttack);
        cloneUnit.specialPowers = this.specialPowers;
        return cloneUnit;
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

    public OutputMessageType attack(String targetId,Battle battle) {
        if (battle.getPlayerInTurn().
                getSelectedUnit().didAttackThisTurn)
            return OutputMessageType.ALREADY_ATTACKED;
        if (!battle.getBattleGround().doesHaveUnit(targetId))
            return OutputMessageType.INVALID_CARD;
        if (!isTargetUnitWithinRange(targetId,battle))
            return OutputMessageType.TARGET_NOT_IN_RANGE;
        Unit targetUnit = battle.getBattleGround().getUnitWithID(targetId);
        if (battle.getBattleGround().isUnitFriendlyOrEnemy(targetUnit,battle).equals(Constants.FRIEND))
            return OutputMessageType.ATTACKED_FRIENDLY_UNIT;
        this.attackUnit(targetId, false,battle);
        if (targetUnit.canAttackTarget(this, true,battle)) {
            targetUnit.attackUnit(this.getId(), true,battle);
            battle.checkForDeadUnits(battle);
            return OutputMessageType.UNIT_AND_ENEMY_ATTACKED;
        } else {
            battle.checkForDeadUnits(battle);
            return OutputMessageType.UNIT_ATTACKED;
        }
    }

    public void attackUnit(String targetId, boolean isCounterAttack,Battle battle) {
        if (this.didAttackThisTurn)
            return;
        if (!battle.getBattleGround().doesHaveUnit(targetId))
            return;
        if (!isTargetUnitWithinRange(targetId,battle))
            return;
        if (!isCounterAttack) {
            this.didAttackThisTurn = true;
            this.didMoveThisTurn = true;
        }
        Unit targetedUnit = battle.getBattleGround().
                getUnitWithID(targetId);
        int damageDealt = calculateDamageDealt(this, targetedUnit,battle);
        targetedUnit.changeHp(-damageDealt);
        for (Spell specialPower : specialPowers) {
            if (specialPower != null
                    && specialPower.getActivationType().equals(SpellActivationType.ON_ATTACK))
                specialPower.doSpell(targetedUnit,battle);
        }
    }

    public boolean canAttackTarget(Unit unit, boolean isCounterAttack,Battle battle) {
        if (!isCounterAttack && battle.getBattleGround().
                isUnitFriendlyOrEnemy(unit,battle).equals(Constants.FRIEND))
            return false;
        if (battle.getBattleGround().isUnitFriendlyOrEnemy(unit,battle).equals(Constants.FRIEND))
            if (this.isStunned())
                return false;
        if (!isCounterAttack && this.didAttackThisTurn)
            return false;
        return isTargetUnitWithinRange(unit.getId(),battle);
    }

    public static OutputMessageType attackCombo(String targetId, String[] attackersIds,Battle battle) {
        Unit target = battle.getBattleGround().getUnitWithID(targetId);
        List<Unit> attackers = new ArrayList<>();
        for (String attackerId : attackersIds) {
            Unit attacker = battle.getBattleGround().getUnitWithID(attackerId);
            if (attacker == null)
                return OutputMessageType.A_UNIT_DOESNT_EXIST;
            if (!attacker.canUseComboAttack)
                return OutputMessageType.A_UNIT_CANT_USE_COMBO;
            if (!attacker.canAttackTarget(target, false,battle))
                return OutputMessageType.A_UNIT_CANT_ATTACK_TARGET;
            attackers.add(attacker);
        }
        for (Unit attacker : attackers) {
            attacker.attackUnit(targetId, false,battle);
        }
        Unit targetUnit = battle.getBattleGround().getUnitWithID(targetId);
        targetUnit.attackUnit(attackers.get(0).getId(), true,battle);
        return OutputMessageType.COMBO_ATTACK_SUCCESSFUL;
    }

    private int calculateDamageDealt(Unit attackerUnit, Unit targetedUnit,Battle battle) {
        if (targetedUnit.isImmuneTo(Constants.WEAKER_AP)
                && targetedUnit.ap > attackerUnit.ap)
            return 0;
        if (this.isImmuneTo(Constants.HOLY_BUFF))
            return attackerUnit.ap + targetedUnit.getNegativeArmor();
        else return attackerUnit.ap - targetedUnit.getPositiveArmor(battle)
                + targetedUnit.getNegativeArmor();
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

    private boolean isTargetUnitWithinRange(String targetID,Battle battle) {
        Unit targetUnit = battle.getBattleGround().getUnitWithID(targetID);
        BattleGround battleGround = battle.getBattleGround();
        int distanceToTarget = getDistanceToTarget(
                battleGround.getCoordinationOfUnit(targetUnit)[0],
                battleGround.getCoordinationOfUnit(targetUnit)[1],battle);
        return distanceToTarget <= maxRange && distanceToTarget >= minRange;
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

    public boolean isStunned() {
        for (Buff buff : buffs) {
            if (buff instanceof StunBuff)
                return true;
        }
        return false;
    }

    public boolean isImmuneTo(String effect) {
        for (Buff buff : buffs) {
            if (buff instanceof ImmunityBuff
                    && ((ImmunityBuff) buff).getImmunity().matches(effect))
                return true;
        }
        return false;
    }

    public int getDistanceToTarget(int targetRow, int targetColumn,Battle battle) {
        int unitRow = battle.getBattleGround()
                .getCoordinationOfUnit(this)[0];
        int unitColumn = battle.getBattleGround()
                .getCoordinationOfUnit(this)[1];
        return battle.getBattleGround().getDistance(unitRow, unitColumn, targetRow, targetColumn);
    }

    public void takeFlags(List<Flag> flags) {
        this.flags = flags;
    }

    public void dropFlags(Cell cell) {
        for (Flag flag : flags)
            flag.setTurnsInUnitHand(0);
        cell.setFlags(flags);
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

    public int getPositiveArmor(Battle battle) {
        int armor = 0;
        for (Buff buff : buffs) {
            if (buff instanceof HolyBuff)
                armor += ((HolyBuff) buff).getArmor();
        }
        Cell cell = battle.getBattleGround().getCellOfUnit(this);
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

    public Spell getMainSpecialPower() {
        if (specialPowers.isEmpty())
            return null;
        return specialPowers.get(0);
    }

    public List<Spell> getSpecialPowers() {
        return specialPowers;
    }
}
