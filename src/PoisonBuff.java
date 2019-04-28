import java.util.List;

public class PoisonBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int damagePerTurn;

    public PoisonBuff() {
        setPositiveOrNegative(Constants.NEGATIVE);
    }

    @Override
    public void doEffect() {
        List<Unit> units = dataBase.getCurrentBattle().
                getBattleGround().getUnitsHavingBuff(this);
        for (Unit unit : units) {
            unit.changeHp(-damagePerTurn);
        }
    }
}
