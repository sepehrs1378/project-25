public class Battle {
    private Player player1;
    private Player player2;
    private BattleGround battleGround;
    private GraveYard graveYard1;
    private GraveYard graveYard2;
    private Player turn;
    private String mode;
    private int turnNumber;

    public Player getTurn() {
        return turn;
    }

    public void endTurn() {

    }

    public void changeTurn() {
        if (turn == player1)
            turn = player2;
        else turn = player1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public BattleGround getBattleGround() {
        return battleGround;
    }

    public void setBattleGround(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public GraveYard getGraveYard1() {
        return graveYard1;
    }

    public void setGraveYard1(GraveYard graveYard1) {
        this.graveYard1 = graveYard1;
    }

    public GraveYard getGraveYard2() {
        return graveYard2;
    }

    public void setGraveYard2(GraveYard graveYard2) {
        this.graveYard2 = graveYard2;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public void setTurn(Player turn) {
        this.turn = turn;
    }
}
