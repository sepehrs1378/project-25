import java.util.List;

public class InfernoBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int damagePerTurn;

    @Override
    public void doEffect() {
        List<Cell> cells = dataBase.getCurrentBattle().
                getBattleGround().getCellsHavingBuff(this);
        for (Cell cell : cells) {
            if (!cell.isEmptyOfUnit())
                cell.getUnit().changeHp(-damagePerTurn);
        }
    }
}
