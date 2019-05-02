import javax.xml.crypto.Data;
import java.util.List;
import java.util.regex.Matcher;

public class AI {
    private static final AI ourInstance = new AI();
    private DataBase dataBase = DataBase.getInstance();
    private BattleGround battleGround = dataBase.getCurrentBattle().getBattleGround();
    private Battle battle = dataBase.getCurrentBattle();
    private Player computerPlayer = dataBase.getCurrentBattle().getPlayer2();

    public static AI getInstance() {
        return ourInstance;
    }

    private AI() {
    }

    public void doNextMove() {
        int mana = computerPlayer.getMana();
        for (Unit unit : battleGround.getMinionsOfPlayer(battle.getPlayer2())) {
            int[] currentCoordinations = battleGround.getCoordinationOfUnit(unit);
            int[] enemyCoordinations = findEnemyUnitInRange(currentCoordinations[0], currentCoordinations[1]);
            if (enemyCoordinations == null)
                continue;
            else {

            }
        }
    }

    public void moveUnit(Unit unit) {

    }

    public void attackWithUnit(Unit unit) {

    }

    public Unit getBestTargetForUnitAttack(Unit unit) {
        return null;//todo
    }

    public List<Card> getSelectedCardsFromHand(Hand hand) {
        return null;//todo
    }

    public int[] findEnemyUnitInRange(int row, int column) {
        int rowCounter = 0;
        int coloumnCounter = 0;
        for (Cell[] cellRow : battleGround.getCells()) {
            for (Cell cell : cellRow) {
                if (Math.abs(row - rowCounter) + Math.abs(column - coloumnCounter) <= 3 && cell.getUnit().getName()
                        .contains(dataBase.getCurrentBattle().getPlayer1().getPlayerInfo().getPlayerName())) {
                    int[] coordinations = new int[2];
                    coordinations[0] = rowCounter;
                    coordinations[1] = coloumnCounter;
                    return coordinations;
                }
                coloumnCounter++;
            }
            rowCounter++;
        }
        return null;

    }
}
