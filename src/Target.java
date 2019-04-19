import java.util.ArrayList;
import java.util.List;

class Target {
    private static DataBase dataBase = DataBase.getInstance();
    private static Battle currentBattle = dataBase.getCurrentBattle();
    //type of target: hero minion cell
    private String typeOfTarget;
    private int width;
    private int length;
    private String friendOrEnemy;
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

    public String getFriendOrEnemy() {
        return friendOrEnemy;
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

    public void setFriendOrEnemy(String friendOrEnemy) {
        this.friendOrEnemy = friendOrEnemy;
    }

    public void setSelfTargeting(boolean selfTargeting) {
        this.selfTargeting = selfTargeting;
    }

    public List<Cell> getCells(int insertWidth, int insetLength) {
        List<Cell> targetCells = new ArrayList<>();
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                if (Math.abs(i - insertWidth) <= width && Math.abs(j - insetLength) <= length)
                    targetCells.add(currentBattle.getBattleGround().getCells()[i][j]);
            }
        }
        return targetCells;
    }
}
