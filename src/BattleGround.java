class BattleGround {
    private Cell[][] cells = new
            Cell[Constants.BATTLE_GROUND_WIDTH][Constants.BATTLE_GROUND_LENGTH];

    public Cell[][] getCells() {
        return cells;
    }

    public int getWidthOfUnit(Unit unit) {
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                if (cells[i][j].getUnit() == unit)
                    return i;
            }
        }
        return -1;
    }

    public Cell getCoordinatesOfUnit(Unit unit) {
        for (Cell[] cellRow : cells)
            for (Cell cell : cellRow) {
                if (cell.getUnit() == unit) {
                    return cell;
                }
            }
        return null;
    }

    public boolean doesHaveUnit(Unit unit) {
        if (getCoordinatesOfUnit(unit) == null) {
            return false;
        }
        return true;
    }

    public int getLengthOfUnit(Unit unit) {
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                if (cells[i][j].getUnit() == unit)
                    return j;
            }
        }
        return -1;
    }

    public boolean doesHaveUnit(String unitName) {
        for(Cell[] cellRow:cells){
            for (Cell cell:cellRow){
                if(cell.getUnit()!=null&&cell.getUnit().getCardID()==unitName)
                    return true;
            }
        }
        return false;
    }

    public int getNumberOfFlags() {
        int numberOfFlags=0;
        for(Cell[] cellRow:cells){
            for (Cell cell:cellRow){
                numberOfFlags+=cell.getFlags().size();
                numberOfFlags+=cell.getUnit().getFlags().size()
            }
        }
        return numberOfFlags;
    }

    public int getNumberOfFlagsOnGround() {
        int numberOfFlags=0;
        for(Cell[] cellRow:cells){
            for (Cell cell:cellRow){
                numberOfFlags+=cell.getFlags().size();
            }
        }

    }
}
