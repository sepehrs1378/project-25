package ClientPackage;

public class NegativeArmorBuff extends Buff {
    private int negativeArmorAmount;

    public NegativeArmorBuff(int durationTurn, boolean isContinuous
            , boolean isDispellable,int startTurn, int negativeArmorAmount) {
        super(durationTurn, isDispellable, isContinuous,startTurn);
        this.negativeArmorAmount = negativeArmorAmount;
        setPositiveOrNegative(Constants.NEGATIVE);
    }

    @Override
    public void doEffect(Unit unit) {
        //empty
    }

    @Override
    public void doEndingEffect(Unit unit) {
        //empty
    }

    @Override
    public NegativeArmorBuff clone() {
        return new NegativeArmorBuff(getDurationTurn(), isContinuous(), isDispellable(),getStartTurn(), negativeArmorAmount);
    }

    @Override
    public BuffType getType() {
        return BuffType.negativeArmorBuff;
    }

    public int getNegativeArmorAmount() {
        return negativeArmorAmount;
    }
}
