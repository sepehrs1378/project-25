class HolyBuff extends Buff {
    private int armor;

    HolyBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous, int armor,int startTurn) {
        super(durationTurn, isDispellable, isContinuous,startTurn);
        setPositiveOrNegative(Constants.POSITIVE);
        this.armor = armor;
    }

    @Override
    public void doEffect(Unit unit,Battle battle) {
        //empty
    }

    @Override
    public void doEndingEffect(Unit unit) {
        //empty
    }

    @Override
    public HolyBuff clone() {
        return new HolyBuff(getDurationTurn(), isDispellable(), isContinuous(), armor,getStartTurn());
    }

    @Override
    public BuffType getType() {
        return BuffType.holyBuff;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
