public class WeaknessBuff extends Buff {
    private int apMinus;
    private int hpMinus;

    public WeaknessBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous,int startTurn, int hpMinus, int apMinus) {
        super(durationTurn, isDispellable, isContinuous,startTurn);
        this.hpMinus = hpMinus;
        this.apMinus = apMinus;
    }

    {
        setPositiveOrNegative(Constants.NEGATIVE);
    }

    @Override
    public void doEffect(Unit unit,Battle battle) {
        if (isInFirstActivationTurn(battle)) {
            unit.changeHp(-hpMinus);
            unit.changeAp(-apMinus);
        }
    }

    @Override
    public void doEndingEffect(Unit unit) {
        unit.changeHp(hpMinus);
        unit.changeHp(apMinus);
    }

    @Override
    public WeaknessBuff clone() {
        return new WeaknessBuff(getDurationTurn(), isDispellable(), isContinuous(),getStartTurn(), hpMinus, apMinus);
    }

    @Override
    public BuffType getType() {
        return BuffType.weaknessBuff;
    }

    public int getApMinus() {
        return apMinus;
    }
}