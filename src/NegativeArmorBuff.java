public class NegativeArmorBuff extends Buff {
    private int negativeArmorAmount;

    public NegativeArmorBuff(int durationTurn, boolean isContinuous
            , boolean isDispellable, int negativeArmorAmount) {
        super(durationTurn, isDispellable, isContinuous);
        this.negativeArmorAmount = negativeArmorAmount;
        setPositiveOrNegative(Constants.NEGATIVE);
    }

    @Override
    public void doEffect() {
        //todo looks like it's gonna' be empty
    }

    @Override
    public void doEndingEffect() {
        //todo looks gonna be empty
    }

    @Override
    public NegativeArmorBuff clone() {
        return new NegativeArmorBuff(getDurationTurn(), isContinuous(), isDispellable(), negativeArmorAmount);
    }

    public int getNegativeArmorAmount() {
        return negativeArmorAmount;
    }
}
