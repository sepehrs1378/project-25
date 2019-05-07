import java.util.List;

public class InfernoBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int damagePerTurn;

    public InfernoBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous, int damagePerTurn) {
        super(durationTurn, isDispellable, isContinuous);
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void doEffect() {
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
    public InfernoBuff clone() {
        return new InfernoBuff(getDurationTurn(), isDispellable(), isContinuous(), damagePerTurn);
    }
}
