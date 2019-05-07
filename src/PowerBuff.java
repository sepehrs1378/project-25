import java.util.List;

public class PowerBuff extends Buff {
    private int apPlus;
    private int hpPlus;

    public PowerBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous, int hpPlus, int apPlus) {
        super(durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.POSITIVE);
        this.hpPlus = hpPlus;
        this.apPlus = apPlus;
    }

    @Override
    public void doEffect() {
        List<Unit> units = dataBase.getCurrentBattle()
                .getBattleGround().getUnitsHavingBuff(this);
        if (isInFirstActivationTurn()) {
            for (Unit unit : units) {
                unit.changeHp(hpPlus);
                unit.changeAp(apPlus);
            }
        }
    }

    @Override
    public void doEndingEffect() {
        //todo
    }

    @Override
    public PowerBuff clone() {
        return new PowerBuff(getDurationTurn(), isDispellable(), isContinuous(), hpPlus, apPlus);
    }
}
