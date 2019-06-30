import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class BattleGround {
    private static final DataBase dataBase = DataBase.getInstance();
    private Cell[][] cells = new
            Cell[Constants.BATTLE_GROUND_WIDTH][Constants.BATTLE_GROUND_LENGTH];

    {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int[] getCoordinationOfUnit(Unit unit) {
        int[] coordination = new int[2];
        for (int i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (int j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                if (cells[i][j].getUnit() == unit) {
                    coordination[0] = i;
                    coordination[1] = j;
                    return coordination;
                }
            }
        }
        return null;
    }

    public Cell getCellOfUnit(Unit unit) {
        for (Cell[] cellRow : cells)
            for (Cell cell : cellRow) {
                if (cell.getUnit() == unit) {
                    return cell;
                }
            }
        return null;
    }

    public Card getCardByID(String cardId) {
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                Unit unit = cell.getUnit();
                if (unit == null)
                    continue;
                if (unit.getId().equals(cardId)) {
                    return cell.getUnit();
                }
            }
        }
        return null;
    }

    public boolean doesHaveUnit(Unit unit) {
        return getCellOfUnit(unit) != null;
    }

    public boolean doesHaveUnit(String id) {
        return getUnitWithID(id) != null;
    }

    public Unit getUnitWithID(String id) {
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (cell.getUnit() != null && cell.getUnit().getId().equals(id))
                    return cell.getUnit();
            }
        }
        return null;
    }

    public int getNumberOfFlags() {
        int numberOfFlags = 0;
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                numberOfFlags += cell.getFlags().size();
                numberOfFlags += cell.getUnit().getFlags().size();
            }
        }
        return numberOfFlags;
    }

    public int getNumberOfFlagsForPlayer(Player player) {
        int numberOfFlags = 0;
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (cell.getUnit() != null && cell.getUnit().getId().split("_")[0].equals(player.getPlayerInfo().getPlayerName())) {
                    numberOfFlags += cell.getUnit().getFlags().size();
                }
            }
        }
        return numberOfFlags;
    }

    public int getNumberOfFlagsOnGround() {
        int numberOfFlags = 0;
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                numberOfFlags += cell.getFlags().size();
            }
        }
        return numberOfFlags;
    }

    //method below is for oneFlag mode game
    public Cell getCellWithFlag() {
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (!cell.getFlags().isEmpty())
                    return cell;
            }
        }
        return null;
    }

    public Unit getUnitHavingFlag() {
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (cell.getUnit()!=null&&!cell.getUnit().getFlags().isEmpty())
                    return cell.getUnit();
            }
        }
        return null;
    }

    public List<Unit> getMinionsOfPlayer(Player player) {
        List<Unit> minions = new ArrayList<>();
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                Unit unit = cell.getUnit();
                if (unit != null && unit.getId().split("_")[0].equals(player.getPlayerInfo().getPlayerName())) {
                    minions.add(unit);
                }
            }
        }
        return minions;
    }

    public OutputMessageType moveUnit(int destinationRow, int destinationColumn) {
        if (destinationRow >= Constants.BATTLE_GROUND_WIDTH
                || destinationColumn >= Constants.BATTLE_GROUND_LENGTH)
            return OutputMessageType.OUT_OF_BOUNDARIES;
        if (dataBase.getCurrentBattle().getPlayerInTurn().getSelectedUnit() == null)
            return OutputMessageType.UNIT_NOT_SELECTED;
        Unit selectedUnit = dataBase.getCurrentBattle().getPlayerInTurn().getSelectedUnit();
        if (selectedUnit.didMoveThisTurn())
            return OutputMessageType.UNIT_ALREADY_MOVED;
        if (cells[destinationRow][destinationColumn].getUnit() != null)
            return OutputMessageType.CELL_IS_FULL;
        int[] coordination = getCoordinationOfUnit(selectedUnit);
        if (Math.abs(destinationRow - coordination[0]) + Math.abs(destinationColumn - coordination[1]) > 2) {
            return OutputMessageType.CELL_OUT_OF_RANGE;
        }
        Cell originCell = getCellOfUnit(selectedUnit);
        originCell.setUnit(null);
        cells[destinationRow][destinationColumn].setUnit(selectedUnit);
        gatherCollectable(destinationRow, destinationColumn);
        gatherFlags(selectedUnit, destinationRow, destinationColumn);
        selectedUnit.setDidMoveThisTurn(true);
        return OutputMessageType.UNIT_MOVED;
    }

    public void gatherFlags(Unit unit, int destinationRow, int destinationColumn) {
        Cell cell = dataBase.getCurrentBattle().getBattleGround().getCells()[destinationRow][destinationColumn];
        List<Flag> flags = cell.getFlags();
        for (Flag flag : flags) {
            unit.addFlag(flag);
        }
        cell.getFlags().removeAll(flags);
    }

    public void gatherCollectable(int destinationRow, int destinationColumn) {
        Cell cell = dataBase.getCurrentBattle().getBattleGround().getCells()[destinationRow][destinationColumn];
        Collectable collectable = cell.getCollectable();
        if (collectable != null) {
            dataBase.changePlayerNameInId(collectable, dataBase.getCurrentBattle().getPlayerInTurn());
            dataBase.getCurrentBattle().getPlayerInTurn().getCollectables().add(collectable);
            cell.setCollectable(null);
        }
    }

    public int getDistance(int row1, int column1, int row2, int column2) {
        return Math.abs(row1 - row2) + Math.abs(column1 - column2);
    }

    public String isUnitFriendlyOrEnemy(Unit unit) {
        Pattern pattern = Pattern.compile(Constants.ID_PATTERN);
        Matcher matcher = pattern.matcher(unit.getId());
        String username = "";
        if (matcher.find()) {
            username = matcher.group(1);
        }
        if (username.isEmpty()) {
            return "";
        }
        String playerName = dataBase.getCurrentBattle().getPlayerInTurn().getPlayerInfo().getPlayerName();
        if (playerName.equals(username))
            return Constants.FRIEND;
        return Constants.ENEMY;


    }

    public List<Unit> getUnitsHavingBuff(Buff buff) {
        List<Unit> unitsHavingBuff = new ArrayList<>();
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                if (cells[i][j].getUnit().getBuffs().contains(buff))
                    unitsHavingBuff.add(cells[i][j].getUnit());
            }
        }
        return unitsHavingBuff;
    }

    public List<Cell> getCellsHavingBuff(Buff buff) {
        List<Cell> cellsHavingBuff = new ArrayList<>();
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                if (cells[i][j].getBuffs().contains(buff))
                    cellsHavingBuff.add(cells[i][j]);
            }
        }
        return cellsHavingBuff;
    }

    public void setFlagsOnGround(int numberOfFlags) {
        List<Flag> flags = new ArrayList<>();
        for (int i = 0; i < numberOfFlags; i++)
            flags.add(new Flag());
        int counter = flags.size();
        while (counter > 0) {
            int column = (int) (Math.random() * Constants.BATTLE_GROUND_LENGTH);
            int row = (int) (Math.random() * Constants.BATTLE_GROUND_WIDTH);
            if (cells[row][column].getFlags().isEmpty() && cells[row][column].getUnit() == null && cells[row][column].getCollectable() == null) {
                cells[row][column].getFlags().add(flags.get(counter - 1));
                flags.remove(counter - 1);
                counter--;
            }
        }
    }

    public void setCollectableOnGround(Collectable collectable) {
        if (collectable == null) {
            int random = (int) (Math.random() * dataBase.getCollectableList().size());
            collectable = dataBase.getCollectableList().get(random);
        }
        int rowRandom = (int) (Math.random() * Constants.BATTLE_GROUND_WIDTH);
        int columnRandom = (int) ((Math.random() * (Constants.BATTLE_GROUND_LENGTH - 2)) + 1);
        cells[rowRandom][columnRandom].setCollectable(collectable);
    }

    public Unit getHeroOfPlayer(Player player) {
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (cell.getUnit() != null && cell.getUnit().getHeroOrMinion().equals(Constants.HERO)
                        && cell.getUnit().getId().contains(player.getPlayerInfo().getPlayerName())) {
                    return cell.getUnit();
                }
            }
        }
        return null;
    }

    public List<Unit> getUnitsOfPlayer(Player player) {
        List<Unit> units = new ArrayList<>();
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (cell.getUnit() != null && cell.getUnit().getId().split("_")[0].equals(player.getPlayerInfo().getPlayerName())) {
                    units.add(cell.getUnit());
                }
            }
        }
        return units;
    }

    public int[] getCoordinationOfCell(Cell cell) {
        int[] coordination = new int[2];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cell == cells[i][j]) {
                    coordination[0] = i;
                    coordination[1] = j;
                    return coordination;
                }
            }
        }
        return null;
    }

    public int[] getRandomCellToMoveForUnit(Unit unit) {
        int[] coordination = new int[2];
        int[] unitCoordination = getCoordinationOfUnit(unit);
        List<Cell> cellsList = new ArrayList<>();
        int row = 0;
        for (Cell[] cellRow : cells) {
            int column = 0;
            for (Cell cell : cellRow) {
                if (cell.getUnit() == null && (Math.abs(row - unitCoordination[0]) + Math.abs(column - unitCoordination[1])) <= 2) {
                    cellsList.add(cell);
                }
                column++;
            }
            row++;
        }
        int randomNumber = (int) (Math.random() * cellsList.size());
        return getCoordinationOfCell(cellsList.get(randomNumber));
    }
}
