import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class AI {
    private static AI ourInstance = new AI();
    private static DataBase dataBase = DataBase.getInstance();

    public static AI getInstance() {
        return ourInstance;
    }

    private AI() {
    }

    public void doNextMove(AnchorPane battleGroundPane, Battle battle) {
        moveUnits(battle, battleGroundPane);
        for (Unit unit : battle.getBattleGround().getUnitsOfPlayer(battle.getPlayer2())) {
            attackWithUnit(unit, battle);
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
                        battle.insert(card, coordination[0], coordination[1], battle);
                        battle.getPlayer2().moveNextCardToHand();
                        battle.getPlayer2().setNextCard();
                        ControllerBattleCommands.getOurInstance().insertUnitView(coordination[0], coordination[1], card.getId());
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

    public void moveUnits(Battle battle, AnchorPane battleGroundPane) {
        for (Unit unit : battle.getBattleGround().getUnitsOfPlayer(battle.getPlayer2())) {
            if (!unit.didMoveThisTurn()) {
                int[] coordination = battle.getBattleGround().getRandomCellToMoveForUnit(unit);
                battle.getPlayer2().setSelectedUnit(unit);
                battle.getBattleGround().moveUnit(coordination[0], coordination[1], battle);
                UnitImage unitImage = ControllerBattleCommands.getOurInstance().getUnitImageWithId(unit.getId());
                unitImage.showRun(coordination[0], coordination[1]);
            }
        }
    }

    public void attackWithUnit(Unit unit, Battle battle) {
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
            OutputMessageType om = unit.attack(unitIds.get(counter), battle);
            if (om == OutputMessageType.UNIT_ATTACKED) {
                UnitImage unitImage = ControllerBattleCommands.getOurInstance().getUnitImageWithId(unit.getId());
                Unit unitTarget = battle.getBattleGround().getUnitWithID(unitIds.get(counter));
                unitImage.showAttack(battle.getBattleGround().getCoordinationOfUnit(unitTarget)[1]);
            } else if (om == OutputMessageType.UNIT_AND_ENEMY_ATTACKED) {
                UnitImage unitImage = ControllerBattleCommands.getOurInstance().getUnitImageWithId(unit.getId());
                UnitImage unitTargetImage = ControllerBattleCommands.getOurInstance().getUnitImageWithId(unitIds.get(counter));
                Unit unitTarget = battle.getBattleGround().getUnitWithID(unitIds.get(counter));
                unitImage.showAttack(battle.getBattleGround().getCoordinationOfUnit(unitTarget)[1]);
                unitTargetImage.showAttack(battle.getBattleGround().getCoordinationOfUnit(unit)[1]);
            }
            counter++;
        }
    }

    public int[] findEnemyUnitInRange(BattleGround battleGround, int row, int column, Battle battle) {
        int rowCounter = 0;
        int coloumnCounter = 0;
        for (Cell[] cellRow : battleGround.getCells()) {
            for (Cell cell : cellRow) {
                if (Math.abs(row - rowCounter) + Math.abs(column - coloumnCounter) <= 3 && cell.getUnit().getName()
                        .contains(battle.getPlayer1().getPlayerInfo().getPlayerName())) {
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
