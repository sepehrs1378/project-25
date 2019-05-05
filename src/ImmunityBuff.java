public class ImmunityBuff extends Buff {
    private String immunity;

    ImmunityBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous, String immunity) {
        super(durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.POSITIVE);
        this.immunity = immunity;
    }

    @Override
    public void doEffect() {
        //todo looks gonna be empty
    }

    public String getImmunity() {
        return immunity;
    }
}
