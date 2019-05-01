import java.util.ArrayList;
import java.util.List;

class Target {
    private static DataBase dataBase = DataBase.getInstance();
    private static Battle currentBattle = dataBase.getCurrentBattle();
    private String typeOfTarget;
    private String friendlyOrEnemy;
    private String targetUnitClass;
    private int width;
    private int length;
    private int manhattanDistance;
    private boolean isRandomSelecting;
    private boolean isSelfTargeting;

    public Target(String typeOfTarget, int width, int length,
                  String friendlyOrEnemy, boolean isRandomSelecting,
                  boolean isSelfTargeting, int manhattanDistance, String targetUnitClass) {
        this.typeOfTarget = typeOfTarget;
        this.friendlyOrEnemy = friendlyOrEnemy;
        this.targetUnitClass = targetUnitClass;
        this.width = width;
        this.length = length;
        this.manhattanDistance = manhattanDistance;
        this.isRandomSelecting = isRandomSelecting;
        this.isSelfTargeting = isSelfTargeting;
    }

    public Target clone() {
        return new Target(typeOfTarget, width, length,
                friendlyOrEnemy, isRandomSelecting, isSelfTargeting,
                manhattanDistance, targetUnitClass);
    }

    public List<Cell> getTargetCells(int insertionRow, int insertionColumn) {
        List<Cell> targetCells = new ArrayList<>();
        if (!typeOfTarget.equals(Constants.CELL))
            return targetCells;
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                if (isCoordinationValid(i, j, insertionRow, insertionColumn))
                    targetCells.add(currentBattle.getBattleGround().getCells()[i][j]);
            }
        }
        return targetCells;
    }

    public List<Unit> getTargetUnits(int insertionRow, int insertionColumn) {
        List<Unit> targetUnits = new ArrayList<>();
        if (typeOfTarget.equals(Constants.CELL))
            return targetUnits;
        Unit unit;
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                unit = currentBattle.getBattleGround().getCells()[i][j].getUnit();
                if (unit.getHeroOrMinion().equals(typeOfTarget)
                        && currentBattle.getBattleGround().isUnitFriendlyOrEnemy(unit).equals(friendlyOrEnemy)
                        && isCoordinationValid(i, j, insertionRow, insertionColumn))
                    targetUnits.add(unit);
            }
        }
        if (isRandomSelecting)
            targetUnits = getRandomUnit(targetUnits);
        return targetUnits;
    }

    private List<Unit> getRandomUnit(List<Unit> units) {
        int randomNumber = (int) (Math.random() * units.size());
        units = units.subList(randomNumber, randomNumber + 1);
        //todo is previous line correct?
        return units;
    }

    public boolean isCoordinationValid(int row, int column, int insertionRow, int insertionColumn) {
        if (row < 0 || row >= Constants.BATTLE_GROUND_WIDTH)
            return false;
        if (column < 0 || column >= Constants.BATTLE_GROUND_LENGTH)
            return false;
        if (insertionRow - row > (length - 1) / 2 && insertionColumn - column > (width - 1) / 2)
            return true;
        if (row - insertionRow > length / 2 && insertionColumn - column > (width - 1) / 2)
            return true;
        if (insertionRow - row > (length - 1) / 2 && column - insertionColumn > width / 2)
            return true;
        if (row - insertionRow > length / 2 && column - insertionColumn > width / 2)
            return true;
        if (!typeOfTarget.equals(Constants.CELL)) {
            if (getManhattanDistance(row, column, insertionRow, insertionColumn) <= manhattanDistance)
                return true;
        }
        return false;
    }

    public int getManhattanDistance(int row1, int column1, int row2, int column2) {
        return Math.abs(row1 - row2) + Math.abs(column1 - column2);
    }
}