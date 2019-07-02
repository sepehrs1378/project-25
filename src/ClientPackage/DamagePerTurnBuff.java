package ClientPackage;

public class DamagePerTurnBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int[] damagePerTurn = new int[1000];

    public DamagePerTurnBuff(int durationTurn, boolean isDispellable,
                             boolean isContinuous,int startTurn, int... damagePerTurn) {
        super(durationTurn, isDispellable, isContinuous,startTurn);
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void doEffect(Unit unit) {
        int currentTurn = dataBase.getCurrentBattle().getTurnNumber();
        if (isActive()) {
            int damage = damagePerTurn[currentTurn - (getStartTurn())];
            unit.changeHp(damage);
        }
    }

    @Override
    public void doEndingEffect(Unit unit) {
        //todo looks gonna be empty
    }

    @Override
    public DamagePerTurnBuff clone() {
        return new DamagePerTurnBuff(getDurationTurn(), isDispellable()
                , isContinuous(),getStartTurn(), damagePerTurn);
    }

    @Override
    public BuffType getType() {
        return BuffType.damagePerTurnBuff;
    }
}
