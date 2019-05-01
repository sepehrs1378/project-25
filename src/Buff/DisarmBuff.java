package Buff;

public class DisarmBuff extends Buff {
    public DisarmBuff(int startTurn, int delayTurn, int durationTurn,
                      boolean isDispellable, boolean isContinuous) {
        super(startTurn, delayTurn, durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.NEGATIVE);
    }

    @Override
    public void doEffect() {
        //todo looks gonna be empty
    }
}
