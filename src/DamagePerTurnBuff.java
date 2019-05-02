import java.util.List;

public class DamagePerTurnBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int[] damagePerTurn = new int[1000];

    public DamagePerTurnBuff(int durationTurn, boolean isDispellable,
                             boolean isContinuous, int... damagePerTurn) {
        super(durationTurn, isDispellable, isContinuous);
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void doEffect() {
        List<Unit> units = dataBase.getCurrentBattle()
                .getBattleGround().getUnitsHavingBuff(this);
        int currentTurn = dataBase.getCurrentBattle().getTurnNumber();
        if (isActive()) {
            int damage = damagePerTurn[currentTurn - (getStartTurn() )];
            for (Unit unit : units) {
                unit.changeHp(damage);
            }
        }
    }
}
