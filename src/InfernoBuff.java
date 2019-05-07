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
    //todo complete it later for damage to units on cells
    public void doEffect(Unit unit) {
        List<Cell> cells = dataBase.getCurrentBattle().
                getBattleGround().getCellsHavingBuff(this);
        if (!isActive()) {
            for (Cell cell : cells) {
                if (!cell.isEmptyOfUnit())
                    cell.getUnit().changeHp(-damagePerTurn);
            }
        }
    }

    @Override
    public void doEndingEffect() {
        //todo looks gonna be empty
    }

    @Override
    public InfernoBuff clone() {
        return new InfernoBuff(getDurationTurn(), isDispellable(), isContinuous(), damagePerTurn);
    }
}
