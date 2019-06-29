import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class AI {
    private static AI ourInstance = new AI();
    private static DataBase dataBase = DataBase.getInstance();
    Battle battle;
    public static AI getInstance() {
        return ourInstance;
    }

    private AI() {
    }

    public void doNextMove(AnchorPane battleGroundPane) {
        Battle battle = dataBase.getCurrentBattle();
        moveUnits(battle,battleGroundPane);
        for (Unit unit : battle.getBattleGround().getUnitsOfPlayer(battle.getPlayer2())) {
            attackWithUnit(battle, unit);
        }
        insertNextCard(battle);
    }

    private void insertNextCard(Battle battle) {
        for (Card card : battle.getPlayer2().getHand().getCards()) {
            if (card instanceof Spell)
                continue;
            if (card.getMana() <= battle.getPlayer2().getMana()) {
                List<Cell> cells = getAvailableCells(battle, battle.getBattleGround());
                for (Cell cell : cells) {
                    int[] coordination = battle.getBattleGround().getCoordinationOfCell(cell);
                    if (cell.getUnit() == null && coordination != null) {
                        battle.insert(card, coordination[0], coordination[1]);
                        battle.getPlayer2().moveNextCardToHand();
                        battle.getPlayer2().setNextCard();
                        ControllerBattleCommands.getOurInstance().insertUnitView(coordination[0],coordination[1],card);
                        return;
                    }
                }
            }
        }
    }

    private List<Cell> getAvailableCells(Battle battle, BattleGround battleGround) {
        List<Unit> units = battleGround.getUnitsOfPlayer(battle.getPlayer2());
        List<Cell> cells = new ArrayList<>();
        for (Unit unit : units) {
            int[] coordination = battleGround.getCoordinationOfUnit(unit);
            for (int i = coordination[0] - 1; i <= coordination[0] + 1; i++) {
                for (int j = coordination[1] - 1; j <= coordination[1] + 1; j++) {
                    if (i < Constants.BATTLE_GROUND_WIDTH && i >= 0 && j < Constants.BATTLE_GROUND_LENGTH && j >= 0) {
                        if (i == coordination[0] && j == coordination[1]) {
                            continue;
                        }
                        cells.add(battleGround.getCells()[i][j]);
                    }
                }
            }
        }
        return cells;
    }

    public void moveUnits(Battle battle,AnchorPane battleGroundPane) {
        for (Unit unit : battle.getBattleGround().getUnitsOfPlayer(battle.getPlayer2())) {
            if (!unit.didMoveThisTurn()) {
                int[] coordination = battle.getBattleGround().getRandomCellToMoveForUnit(unit);
                battle.getPlayer2().setSelectedUnit(unit);
                battle.getBattleGround().moveUnit(coordination[0], coordination[1]);
                UnitImage unitImage =ControllerBattleCommands.getOurInstance().getUnitImageWithId(unit.getId());
                unitImage.showRun(coordination[0],coordination[1]);
            }
        }
    }

    public void attackWithUnit(Battle battle, Unit unit) {
        List<String> unitIds = new ArrayList<>();
        for (Cell[] cellRow : battle.getBattleGround().getCells()) {
            for (Cell cell : cellRow) {
                if (cell.getUnit() != null && cell.getUnit().getId().split("_")[0]
                        .equals(battle.getPlayer1().getPlayerInfo().getPlayerName())) {
                    unitIds.add(cell.getUnit().getId());
                }
            }
        }
        int counter = 0;
        while (!unit.didAttackThisTurn() && counter < unitIds.size()) {
            unit.attack(unitIds.get(counter));
            counter++;
        }
    }

    public Unit getBestTargetForUnitAttack(Unit unit) {
        return null;//todo
    }

    public List<Card> getSelectedCardsFromHand(Hand hand) {
        return null;//todo
    }

    public int[] findEnemyUnitInRange(BattleGround battleGround, int row, int column) {
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
