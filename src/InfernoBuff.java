import java.util.List;

public class InfernoBuff extends Buff {
    private int damagePerTurn;

    public InfernoBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous, int startTurn, int damagePerTurn) {
        super(durationTurn, isDispellable, isContinuous, startTurn);
        this.damagePerTurn = damagePerTurn;
    }

    //todo complete it later for damage to units on cells
    @Override
    public void doEffect(Unit unit, Battle battle) {
        List<Cell> cells = battle.getBattleGround().getCellsHavingBuff(this);
        if (isActive(battle)) {
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
        return new InfernoBuff(getDurationTurn(), isDispellable(), isContinuous(), getStartTurn(), damagePerTurn);
    }

    @Override
    public BuffType getType() {
        return BuffType.infernoBuff;
    }
}
