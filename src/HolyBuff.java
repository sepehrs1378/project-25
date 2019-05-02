public class HolyBuff extends Buff {
    private int armor;

    public HolyBuff(int durationTurn, boolean isDispellable, boolean isContinuous) {
        super(durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.POSITIVE);
    }

    @Override
    public void doEffect() {
        //todo looks gonna be empty
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
