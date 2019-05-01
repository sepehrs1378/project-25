import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

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

    public Card getCardByID(String cardId) {
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (cell.getUnit().getId().equals(cardId)) {
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
                if (cell.getUnit() != null && cell.getUnit().getId().contains(player.getPlayerInfo().getPlayerName())) {
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
                if (cell.getFlags().size() > 0)
                    return cell;
            }
        }
        return null;
    }

    public List<Unit> getMinionsOfPlayer(Player player) {
        List<Unit> minions = new ArrayList<>();
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (cell.getUnit() != null && cell.getUnit().getId().contains(player.getPlayerInfo().getPlayerName())
                        && cell.getUnit().getHeroOrMinion().equals("Minion")) {
                    minions.add(cell.getUnit());
                }
            }
        }
        return minions;
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

    public String isUnitFriendlyOrEnemy(Unit unit) {
        Pattern pattern = Pattern.compile(Constants.ID_PATTERN);
        Matcher matcher = pattern.matcher(unit.getId());
        String username = matcher.group(1);
        if (dataBase.getCurrentBattle().getPlayerInTurn().getPlayerInfo().getPlayerName().equals(username))
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
    public void addFlagsToBattleGround(List<Flag> flags){
        int counter=flags.size();
        while(counter>0){
            int column= (int) (Math.random() * Constants.BATTLE_GROUND_LENGTH);
            int row=(int)(Math.random()*Constants.BATTLE_GROUND_WIDTH);
            if(cells[row][column].getFlags().size()==0){
                cells[row][column].getFlags().add(flags.get(counter-1));
                flags.remove(counter-1);
                counter--;
            }
        }
    }
    public Unit getHeroOfPlayer(Player player){
        for (Cell[] cellRow:cells){
            for (Cell cell:cellRow){
                if(cell.getUnit()!=null&&cell.getUnit().getHeroOrMinion().equals(Constants.HERO)
                && cell.getUnit().getId().contains(player.getPlayerInfo().getPlayerName())){
                    return cell.getUnit();
                }
            }
        }
        return null;
    }
}
