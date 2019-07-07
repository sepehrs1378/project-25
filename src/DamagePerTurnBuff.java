public class DamagePerTurnBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int[] damagePerTurn;

    public DamagePerTurnBuff(int durationTurn, boolean isDispellable,
                             boolean isContinuous, int startTurn, int... damagePerTurn) {
        super(durationTurn, isDispellable, isContinuous, startTurn);
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void doEffect(Unit unit, Battle battle) {
        int currentTurn = battle.getTurnNumber();
        if (isActive(battle)) {
            int damage = damagePerTurn[currentTurn - (getStartTurn())];
            unit.changeHp(damage);
        }
    }

    @Override
    public void doEndingEffect(Unit unit) {
        //todo looks gonna be empty
    }

    @Override
    public DamagePerTurnBuff clone() {
        return new DamagePerTurnBuff(getDurationTurn(), isDispellable()
                , isContinuous(), getStartTurn(), damagePerTurn);
    }

    @Override
    public BuffType getType() {
        return BuffType.damagePerTurnBuff;
    }
}
