public class BattleGround {
    private Cell[][] cells=new Cell[5][9];
    public Cell[][] getCells(){
        return this.cells;
    }
    public Cell getCoordinatesOfUnit(Unit unit){
        for(Cell[] cellRow:cells)
        for (Cell cell:cellRow){
            if (cell.getCrad(Unit)==Unit){
                return cell;
            }
        }
        return null;
    }
    public boolean doesHaveUnit(Unit unit){
        if(getCoordinatesOfUnit(unit)==null){
            return false;
        }
        return true;
    }
    public int getWidthOfUnit(Unit unit){

    }

    public int getLengthOfUnit(Unit unit){

    }

    public boolean doesHaveUnit(Unit unit){

    }

    public int getNumberOfFlags(){

    }

    public int getNumberOfFlagsOnGround(){

    }

}
