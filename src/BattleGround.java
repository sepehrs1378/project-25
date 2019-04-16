class BattleGround {
    private Cell[][] cells = new
            Cell[Constants.BATTLE_GROUND_WIDTH][Constants.BATTLE_GROUND_LENGTH];

    public Cell[][] getCells() {
        return cells;
    }

    public int getWidthOfUnit(Unit unit) {
        int i;
        int j;
        for (i = 0; i <= Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j <= Constants.BATTLE_GROUND_LENGTH; j++) {
                if (cells[i][j].getUnit() == unit)
                    return i;
            }
        }
        return -1;
    }

    public int getLengthOfUnit(Unit unit) {
        int i;
        int j;
        for (i = 0; i <= Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j <= Constants.BATTLE_GROUND_LENGTH; j++) {
                if (cells[i][j].getUnit() == unit)
                    return j;
            }
        }
        return -1;
    }

    public boolean doesHaveUnit(Unit unit) {
        int i;
        int j;
        for (i = 0; i <= Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j <= Constants.BATTLE_GROUND_LENGTH; j++) {
                if (cells[i][j].getUnit() == unit)
                    return true;
            }
        }
        return false;
    }

    public boolean doesHaveUnit(String unitName) {

    }

    public int getNumberOfFlags() {

    }

    public int getNumberOfFlagsOnGround() {

    }
}
