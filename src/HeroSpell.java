import java.util.List;

public class HeroSpell extends Spell {
    private static final DataBase dataBase = DataBase.getInstance();
    private int cooldown;
    private HeroSpellTarget target;
    private SpellActivationType activationType;
    private String passiveOrCastable;
    private boolean isDispeller;

    public void doSpell(Unit unit) {
        for (Buff buff : getAddedBuffsToUnits()) {
            unit.getBuffs().add(buff);
        }
        if (isDispeller)
            removeBuffsFromUnit(unit);
        unit.changeAp(getHpChange());
        unit.changeHp(getApChange());
    }

    @Override
    public void doSpell(int insertionRow, int insertionColumn) {
        doSpellEffectOnCells(insertionRow, insertionColumn);
        doSpellEffectOnUnits(insertionRow, insertionColumn);
    }

    public void doSpellEffectOnCells(int insertionRow, int insertionColumn) {
        List<Cell> targetCells = target.getCells(insertionRow, insertionColumn);
        for (Cell cell : targetCells) {
            for (Buff buff : getAddedBuffsToCells()) {
                cell.getBuffs().add(buff);
            }
        }
    }

    public void doSpellEffectOnUnits(int insertionRow, int insertionColumn) {
        List<Unit> targetUnits = target.getUnits(insertionRow, insertionColumn);
        for (Unit unit : targetUnits) {
            for (Buff buff : getAddedBuffsToUnits()) {
                unit.getBuffs().add(buff);
            }
            if (isDispeller) {
                removeBuffsFromUnit(unit);
            }
            unit.changeAp(getApChange());
            unit.changeHp(getHpChange());
        }
    }

    private void removeBuffsFromUnit(Unit unit) {
        int i = 0;
        if (dataBase.getCurrentBattle().getBattleGround()
                .isUnitFriendlyOrEnemy(unit).equals(Constants.FRIEND)) {
            while (i < unit.getBuffs().size()) {
                if (unit.getBuffs().get(i).getPositiveOrNegative().equals(Constants.NEGATIVE)) {
                    unit.getBuffs().remove(i);
                    continue;
                }
                i++;
            }
        } else {
            while (i < unit.getBuffs().size()) {
                if (unit.getBuffs().get(i).getPositiveOrNegative().equals(Constants.POSITIVE)) {
                    unit.getBuffs().remove(i);
                    continue;
                }
                i++;
            }
        }
    }
}