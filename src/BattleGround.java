import java.util.regex.Matcher;

class BattleGround {
    private static final DataBase dataBase = DataBase.getInstance();
    private Cell[][] cells = new
            Cell[Constants.BATTLE_GROUND_WIDTH][Constants.BATTLE_GROUND_LENGTH];

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

    public int getNumberOfFlagsOnGround() {
        int numberOfFlags = 0;
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                numberOfFlags += cell.getFlags().size();
            }
        }
        return numberOfFlags;
    }

    public OutputMessageType moveUnit(int destinationRow, int destinationColumn) {
        if (dataBase.getCurrentBattle().getPlayerInTurn().getSelectedUnit() == null)
            return OutputMessageType.UNIT_NOT_SELECTED;
        Unit selectedUnit = dataBase.getCurrentBattle().getPlayerInTurn().getSelectedUnit();
        //todo checking the manhattan destination and ...
        Cell originCell = getCellOfUnit(selectedUnit);
        originCell.setUnit(null);
        cells[destinationRow][destinationColumn].setUnit(selectedUnit);
        return OutputMessageType.UNIT_MOVED;
    }

    public int getDistance(int row1, int column1, int row2, int column2) {
        return Math.abs(row1 - row2) + Math.abs(column1 - column2);
    }
}
