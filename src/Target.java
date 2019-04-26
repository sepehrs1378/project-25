import java.util.ArrayList;
import java.util.List;

class Target {
    private static DataBase dataBase = DataBase.getInstance();
    private static Battle currentBattle = dataBase.getCurrentBattle();
    private String typeOfTarget;
    private int width;
    private int length;
    private String friendlyOrEnemy;
    private boolean selfTargeting;

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

    public boolean isSelfTargeting() {
        return selfTargeting;
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

    public void setSelfTargeting(boolean selfTargeting) {
        this.selfTargeting = selfTargeting;
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
        return targetUnits;
        //todo
    }

    public boolean isCoordinationValid(int row, int column, int insertionRow, int insertionColumn) {
        if (row < 0 || row >= Constants.BATTLE_GROUND_WIDTH)
            return false;
        if (column < 0 || column >= Constants.BATTLE_GROUND_LENGTH)
            return false;
        //todo چک کردن بودن مختصات در مربع هدف
    }
}