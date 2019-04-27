import java.util.ArrayList;
import java.util.List;

abstract class Target {
    private static DataBase dataBase = DataBase.getInstance();
    private static Battle currentBattle = dataBase.getCurrentBattle();
    private String typeOfTarget;
    private int width;
    private int length;
    private String friendlyOrEnemy;
    private boolean isRandomSelecting;

    public String getTypeOfTarget() {
        return typeOfTarget;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String getFriendlyOrEnemy() {
        return friendlyOrEnemy;
    }

    public void setTypeOfTarget(String typeOfTarget) {
        this.typeOfTarget = typeOfTarget;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setFriendlyOrEnemy(String friendlyOrEnemy) {
        this.friendlyOrEnemy = friendlyOrEnemy;
    }

    public boolean isRandomSelecting() {
        return isRandomSelecting;
    }

    public void setRandomSelecting(boolean randomSelecting) {
        isRandomSelecting = randomSelecting;
    }

    public List<Cell> getCells(int insertionRow, int insertionColumn) {
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

    public List<Unit> getUnits(int insertionRow, int insertionColumn) {
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
        return false;
    }
}