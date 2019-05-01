import java.util.List;

public class PoisonBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int damagePerTurn;

    public PoisonBuff(int startTurn, int delayTurn, int durationTurn,
                      boolean isDispellable, boolean isContinuous, int damagePerTurn) {
        super(startTurn, delayTurn, durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.NEGATIVE);
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void doEffect() {
        List<Unit> units = dataBase.getCurrentBattle().
                getBattleGround().getUnitsHavingBuff(this);
        if (isActive()) {
            for (Unit unit : units) {
                unit.changeHp(-damagePerTurn);
            }
        }
    }
}
