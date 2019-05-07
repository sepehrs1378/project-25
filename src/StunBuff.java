public class StunBuff extends Buff {
    public StunBuff(int durationTurn, boolean isDispellable, boolean isContinuous) {
        super(durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.NEGATIVE);
    }

    @Override
    public void doEffect(Unit unit) {
        //todo looks like it's gonna' be empty
    }

    @Override
    public void doEndingEffect() {
        //todo looks gonna be empty
    }

    @Override
    public StunBuff clone(){
        return new StunBuff(getDurationTurn(),isDispellable(),isContinuous());
    }
}
