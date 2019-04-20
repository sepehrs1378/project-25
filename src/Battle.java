public class Battle {
    private Player player1;
    private Player player2;
    private BattleGround battleGround=new BattleGround();
    private Player playerInTurn;
    private String mode;
    private int turnNumber=1;
    private boolean isBattleFinished=false;
    private int numberOfFlags;

    public Battle(Account firstPlayerAccount,Account secondPlayerAccount,String mode,int numberOfFlags){
        player1=new Player(firstPlayerAccount.getPlayerInfo(),firstPlayerAccount.getMainDeck());
        player2=new Player(secondPlayerAccount.getPlayerInfo(),secondPlayerAccount.getMainDeck());
        playerInTurn=player1;
        this.mode=mode;
        this.setNumberOfFlags(numberOfFlags);
    }
    public void endTurn() {

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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void addTurnNumber() {
        this.turnNumber++;
    }

    public void changePlayerInTurn() {
        if (playerInTurn == player1)
            playerInTurn = player2;
        else playerInTurn = player1;
    }

    public boolean isBattleFinished() {
        return isBattleFinished;
    }

    public void setBattleFinished(boolean battleFinished) {
        isBattleFinished = battleFinished;
    }

    public Player getPlayerInTurn() {
        return playerInTurn;
    }

    public void killUnit(Unit unit){
        this.getBattleGround().getCellOfUnit(unit).setUnit(null);
        if(unit.getId().contains(player1.getPlayerInfo().getPlayerName()))
            player1.getGraveYard().addDeadCard(unit);
        else if(unit.getId().contains(player2.getPlayerInfo().getPlayerName()))
            player2.getGraveYard().addDeadCard(unit);
    }


    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public void setNumberOfFlags(int numberOfFlags) {
        this.numberOfFlags = numberOfFlags;
    }
}
