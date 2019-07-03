public class ImmunityBuff extends Buff {
    private String immunity;

    ImmunityBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous,int startTurn, String immunity) {
        super(durationTurn, isDispellable, isContinuous,startTurn);
        setPositiveOrNegative(Constants.POSITIVE);
        this.immunity = immunity;
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
    public ImmunityBuff clone() {
        return new ImmunityBuff(getDurationTurn(), isDispellable(), isContinuous(),getStartTurn(), immunity);
    }

    @Override
    public BuffType getType() {
        return BuffType.immunityBuff;
    }

    public String getImmunity() {
        return immunity;
    }
}
