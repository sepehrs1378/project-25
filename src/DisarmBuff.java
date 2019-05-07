public class DisarmBuff extends Buff {
    public DisarmBuff(int durationTurn, boolean isDispellable, boolean isContinuous) {
        super(durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.NEGATIVE);
    }

    @Override
    public void doEffect(Unit unit) {
        //todo looks gonna be empty
    }

    @Override
    public void doEndingEffect() {
        //todo looks gonna be empty
    }

    @Override
    public DisarmBuff clone() {
        return new DisarmBuff(getDurationTurn(), isDispellable(), isContinuous());
    }
}
