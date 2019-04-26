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
                if (Math.abs(i - insertionRow) <= width && Math.abs(j - insertionColumn) <= length)
                    targetCells.add(currentBattle.getBattleGround().getCells()[i][j]);
            }
        }
        return targetCells;
    }

    public List<Unit> getUnits(int insertionRow, int insertionColumn) {
        if (typeOfTarget.equals(Constants.CELL))
            return null;
        //todo
    }
}