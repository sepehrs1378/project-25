public class HolyBuff extends Buff {
    private int armor;

    public HolyBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous, int armor) {
        super(durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.POSITIVE);
        this.armor = armor;
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
    public HolyBuff clone() {
        return new HolyBuff(getDurationTurn(), isDispellable(), isContinuous(), armor);
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
