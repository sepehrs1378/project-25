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
    public void doEffect(Unit unit) {
        if (isInFirstActivationTurn()) {
            unit.changeHp(hpPlus);
            unit.changeAp(apPlus);
        }
    }

    @Override
    public void doEndingEffect(Unit unit) {
        unit.changeHp(-hpPlus);
        unit.changeAp(-apPlus);
        //todo
    }

    @Override
    public PowerBuff clone() {
        return new PowerBuff(getDurationTurn(), isDispellable(), isContinuous(), hpPlus, apPlus);
    }
}
