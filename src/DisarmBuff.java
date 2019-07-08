public class DisarmBuff extends Buff {
    public DisarmBuff(int durationTurn, boolean isDispellable, boolean isContinuous,int startTurn) {
        super(durationTurn, isDispellable, isContinuous,startTurn);
        setPositiveOrNegative(Constants.NEGATIVE);
    }

    @Override
    public void doEffect(Unit unit,Battle battle) {
        //empty
    }

    @Override
    public void doEndingEffect(Unit unit) {
        //empty
    }

    @Override
    public DisarmBuff clone() {
        return new DisarmBuff(getDurationTurn(), isDispellable(), isContinuous(),getStartTurn());
    }

    @Override
    public BuffType getType() {
        return BuffType.disarmBuff;
    }
}
