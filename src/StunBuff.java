public class StunBuff extends Buff {
    public StunBuff(int startTurn, int delayTurn, int durationTurn,
                    boolean isDispellable, boolean isContinuous) {
        super(startTurn, delayTurn, durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.NEGATIVE);
    }

    @Override
    public void doEffect() {
        //todo looks gonna be empty
    }
}
