public class PoisonBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int damagePerTurn;

    public PoisonBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous,int startTurn, int damagePerTurn) {
        super(durationTurn, isDispellable, isContinuous,startTurn);
        setPositiveOrNegative(Constants.NEGATIVE);
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void doEffect(Unit unit) {
        if (isActive()) {
            unit.changeHp(-damagePerTurn);
        }
    }

    @Override
    public void doEndingEffect(Unit unit) {
        //empty
    }

    @Override
    public PoisonBuff clone() {
        return new PoisonBuff(getDurationTurn(), isDispellable(), isContinuous(),getStartTurn(), damagePerTurn);
    }
}
