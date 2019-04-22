import java.util.ArrayList;
import java.util.List;

class BattleGround {
    private Cell[][] cells = new
            Cell[Constants.BATTLE_GROUND_WIDTH][Constants.BATTLE_GROUND_LENGTH];

    public Cell[][] getCells() {
        return cells;
    }

    public int[] getCoordinationsOfUnit(Unit unit) {
        int[] coordinations = new int[2];
        for (int i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (int j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                if (cells[i][j].getUnit() == unit) {
                    coordinations[0] = i;
                    coordinations[1] = j;
                    return coordinations;
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
    public Card getCardByID(String cardid){
        for(Cell[] cellRow:cells)
        {
            for (Cell cell:cellRow){
                if(cell.getUnit().getCardID().equals(cardid)){
                    return cell.getUnit();
                }
            }
        }
        return null;
    }
    public boolean doesHaveUnit(Unit unit) {
        if (getCellOfUnit(unit) == null) {
            return false;
        }
        return true;
    }

    public boolean doesHaveUnit(String unitName) {
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (cell.getUnit() != null && cell.getUnit().getCardID() == unitName)
                    return true;
            }
        }
        return false;
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
                if(cell.getUnit()!=null&&cell.getUnit().getCardID().contains(player.getPlayerInfo().getPlayerName())){
                    numberOfFlags+=cell.getUnit().getFlags().size();
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
    public Cell getCellWithFlag(){
        for (Cell[] cellRow:cells){
            for(Cell cell:cellRow)
            {
                if(cell.getFlags().size()>0)
                    return cell;
            }
        }
        return null;
    }
    public List<Unit> getMinionsOfPlayer(Player player){
        List<Unit> minions=new ArrayList<>();
        for(Cell[] cellRow:cells){
            for(Cell cell:cellRow)
            {
                if (cell.getUnit()!=null&&cell.getUnit().getCardID().equals(player.getPlayerInfo().getPlayerName())
                && cell.getUnit().getHeroOrMinion().equals("Minion")){
                    minions.add(cell.getUnit());
                }
            }
        }
        return minions;
    }
}
