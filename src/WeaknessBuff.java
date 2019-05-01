import java.util.List;

public class WeaknessBuff extends Buff {
    private int apMinus;
    private int hpMinus;

    public WeaknessBuff(int startTurn, int delayTurn, int durationTurn,
                        boolean isDispellable, boolean isContinuous,
                        int hpMinus, int apMinus) {
        super(startTurn, delayTurn, durationTurn, isDispellable, isContinuous);
        this.hpMinus = hpMinus;
        this.apMinus = apMinus;
    }

    {
        setPositiveOrNegative(Constants.NEGATIVE);
    }

    @Override
    public void doEffect() {
        List<Unit> units = dataBase.getCurrentBattle()
                .getBattleGround().getUnitsHavingBuff(this);
        if (isInFirstActivationTurn()) {
            for (Unit unit : units) {
                unit.changeHp(-hpMinus);
                unit.changeAp(-apMinus);
            }
        }
    }
}