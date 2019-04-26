import java.util.List;

public class CardSpell extends Spell {
    private CardSpellTarget target;

    public void doSpell(int insertionRow, int insertionColumn) {
        doSpellEffectOnCells(insertionRow, insertionColumn);
        doSpellEffectOnUnits(insertionRow, insertionColumn);
    }
    public void doSpellEffectOnCells(int insertionRow, int insertionColumn) {
        List<Cell> targetCells = target.getCells(insertionRow, insertionColumn);
        for (Cell cell : targetCells) {
            for (Buff buff : getAddedBuffsToUnits()) {
                cell.getBuffs().add(buff);
            }
            for (Buff buff : deletedBuffsFromCells) {
                cell.getBuffs().remove(buff);
            }
        }
        //todo duplicate
    }

    public void doSpellEffectOnUnits(int insertionRow, int insertionColumn) {
        List<Unit> targetUnits = target.getUnits(insertionRow, insertionColumn);
        for (Unit unit : targetUnits) {
            for (Buff buff : addedBuffsToUnits) {
                unit.getBuffs().add(buff);
            }
            for (Buff buff : deletedBuffsFromUnits) {
                unit.getBuffs().remove(buff);
            }
            unit.changeAp(apChange);
            unit.changeHp(hpChange);
        }
        //todo duplicate
    }
}
