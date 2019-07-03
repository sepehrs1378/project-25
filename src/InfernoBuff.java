import java.util.List;

public class InfernoBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int damagePerTurn;

    public InfernoBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous,int startTurn, int damagePerTurn) {
        super(durationTurn, isDispellable, isContinuous,startTurn);
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    //todo complete it later for damage to units on cells
    public void doEffect(Unit unit) {
        List<Cell> cells = dataBase.getCurrentBattle().
                getBattleGround().getCellsHavingBuff(this);
        if (isActive()) {
            for (Cell cell : cells) {
                if (!cell.isEmptyOfUnit())
                    cell.getUnit().changeHp(-damagePerTurn);
            }
        }
    }

    @Override
    public void doEndingEffect(Unit unit) {
        //empty
    }

    @Override
    public InfernoBuff clone() {
        return new InfernoBuff(getDurationTurn(), isDispellable(), isContinuous(),getStartTurn(), damagePerTurn);
    }

    @Override
    public BuffType getType() {
        return BuffType.infernoBuff;
    }
}
