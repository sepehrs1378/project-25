public class HolyBuff extends Buff {
    private int armor;

    public HolyBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous, int armor) {
        super(durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.POSITIVE);
        this.armor = armor;
    }

    @Override
    public void doEffect() {
        //todo looks gonna be empty
    }

    @Override
    public void doEndingEffect() {
        //todo looks gonna be empty
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
