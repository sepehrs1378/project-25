public class PowerBuff extends Buff {
    private int apPlus;
    private int hpPlus;

    public PowerBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous, int hpPlus, int apPlus,int startTurn) {
        super(durationTurn, isDispellable, isContinuous,startTurn);
        setPositiveOrNegative(Constants.POSITIVE);
        this.hpPlus = hpPlus;
        this.apPlus = apPlus;
    }

    @Override
    public void doEffect(Unit unit,Battle battle) {
        if (isInFirstActivationTurn(battle)) {
            unit.changeHp(hpPlus);
            unit.changeAp(apPlus);
        }
    }

    @Override
    public void doEndingEffect(Unit unit) {
        unit.changeHp(-hpPlus);
        unit.changeAp(-apPlus);
    }

    @Override
    public PowerBuff clone() {
        return new PowerBuff(getDurationTurn(), isDispellable(), isContinuous(), hpPlus, apPlus,getStartTurn());
    }

    @Override
    public BuffType getType() {
        return BuffType.powerBuff;
    }

    public int getApPlus() {
        return apPlus;
    }

    public int getHpPlus() {
        return hpPlus;
    }
}
