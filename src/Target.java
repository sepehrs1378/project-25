import java.util.ArrayList;
import java.util.List;

class Target {
    private List<String> typeOfTarget = new ArrayList<>();
    private List<String> friendlyOrEnemy = new ArrayList<>();
    private List<String> targetUnitClass = new ArrayList<>();
    private int width;
    private int length;
    private int manhattanDistance;
    private boolean isRandomSelecting;
    private boolean doesAffectAllCards;

    public Target(String typeOfTarget, int width, int length,
                  String friendlyOrEnemy, boolean isRandomSelecting,
                  boolean doesAffectAllCards, int manhattanDistance, String targetUnitClass) {
        switch (typeOfTarget) {
            case Constants.MINION:
                this.typeOfTarget.add(Constants.MINION);
                break;
            case Constants.HERO:
                this.typeOfTarget.add(Constants.HERO);
                break;
            case Constants.HERO_MINION:
                this.typeOfTarget.add(Constants.HERO);
                this.typeOfTarget.add(Constants.MINION);
                break;
            case Constants.WHOLE_HEROES:
                //todo
                break;
            case Constants.WHOLE_MINIONS:
                //todo
                break;
            case Constants.WHOLE_UNITS:
                //todo
                break;
            default:
        }
        switch (friendlyOrEnemy) {
            case Constants.FRIEND:
                this.friendlyOrEnemy.add(Constants.FRIEND);
                break;
            case Constants.ENEMY:
                this.friendlyOrEnemy.add(Constants.ENEMY);
                break;
            case Constants.ALL:
                this.friendlyOrEnemy.add(Constants.FRIEND);
                this.friendlyOrEnemy.add(Constants.ENEMY);
                break;
            default:
        }
        switch (targetUnitClass) {
            case Constants.MELEE:
                this.targetUnitClass.add(Constants.MELEE);
                break;
            case Constants.HYBRID:
                this.targetUnitClass.add(Constants.HYBRID);
                break;
            case Constants.RANGED:
                this.targetUnitClass.add(Constants.RANGED);
                break;
            case Constants.MELLE_RANGED:
                this.targetUnitClass.add(Constants.MELEE);
                this.targetUnitClass.add(Constants.RANGED);
                break;
            case Constants.MELEE_HYBRID:
                this.targetUnitClass.add(Constants.MELEE);
                this.targetUnitClass.add(Constants.HYBRID);
                break;
            case Constants.RANGED_HYBRID:
                this.targetUnitClass.add(Constants.RANGED);
                this.targetUnitClass.add(Constants.HYBRID);
                break;
            case Constants.ALL:
                this.targetUnitClass.add(Constants.HYBRID);
                this.targetUnitClass.add(Constants.RANGED);
                this.targetUnitClass.add(Constants.MELEE);
                break;
            default:
        }
        this.width = width;
        this.length = length;
        this.manhattanDistance = manhattanDistance;
        this.isRandomSelecting = isRandomSelecting;
        this.doesAffectAllCards = doesAffectAllCards;
    }

//    public Target clone() {
//        return new Target()
//    }

    public List<Cell> getTargetCells(int insertionRow, int insertionColumn,Battle battle) {
        List<Cell> targetCells = new ArrayList<>();
        if (!typeOfTarget.equals(Constants.CELL))
            return targetCells;
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                if (isCoordinationValid(i, j, insertionRow, insertionColumn))
                    targetCells.add(battle.getBattleGround().getCells()[i][j]);
            }
        }
        return targetCells;
    }

    public List<Unit> getTargetUnits(int insertionRow, int insertionColumn,Battle battle) {
        List<Unit> targetUnits = new ArrayList<>();
        if (typeOfTarget.equals(Constants.CELL))
            return targetUnits;
        Unit unit;
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                unit = battle.getBattleGround().getCells()[i][j].getUnit();
                if (unit != null) {
                    if (typeOfTarget.contains(unit.getHeroOrMinion())
                            && friendlyOrEnemy.contains(battle.getBattleGround().isUnitFriendlyOrEnemy(unit,battle))
                            && isCoordinationValid(i, j, insertionRow, insertionColumn))
                        targetUnits.add(unit);
                }
            }
        }
        if (isRandomSelecting)
            targetUnits = getRandomUnit(targetUnits);
        return targetUnits;
    }

    private List<Unit> getRandomUnit(List<Unit> units) {
        if (units.isEmpty())
            return new ArrayList<>();
        int randomNumber = (int) (Math.random() * units.size());
        List<Unit> tempUnits = new ArrayList<>();
        tempUnits.add(units.get(randomNumber));
        units = tempUnits;
        return units;
    }

    public boolean isCoordinationValid(int row, int column, int insertionRow, int insertionColumn) {
        if (row < 0 || row >= Constants.BATTLE_GROUND_WIDTH)
            return false;
        if (column < 0 || column >= Constants.BATTLE_GROUND_LENGTH)
            return false;
        if (insertionRow - row <= (length - 1) / 2 && insertionColumn - column <= (width - 1) / 2)
            return true;
        if (row - insertionRow <= length / 2 && insertionColumn - column <= (width - 1) / 2)
            return true;
        if (insertionRow - row <= (length - 1) / 2 && column - insertionColumn <= width / 2)
            return true;
        if (row - insertionRow <= length / 2 && column - insertionColumn <= width / 2)
            return true;
        if (!typeOfTarget.equals(Constants.CELL)) {
            return getManhattanDistance(row, column, insertionRow, insertionColumn) <= manhattanDistance;
        }
        return false;
    }

    public int getManhattanDistance(int row1, int column1, int row2, int column2) {
        return Math.abs(row1 - row2) + Math.abs(column1 - column2);
    }
}