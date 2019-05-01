public class NegativeArmorBuff extends Buff {
    private int negativeArmorAmount;

    public NegativeArmorBuff(int startTurn, int delayTurn
            , int durationTurn, boolean isContinuous
            , boolean isDispellable, int negativeArmorAmount) {
        super(startTurn, delayTurn, durationTurn, isDispellable, isContinuous);
        this.negativeArmorAmount = negativeArmorAmount;
        setPositiveOrNegative(Constants.NEGATIVE);
    }

    @Override
    public void doEffect() {

    }
}
