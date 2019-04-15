public class Battle {
    private Player player1;
    private Player player2;
    private BattleGround battleGround;
    private GraveYard graveYardPlayer1;
    private GraveYard graveYardPlayer2;
    private Player playerInTurn;
    private String mode;
    public void setPlayer1(Player player1){
        this.player1=palyer1;
    }
    public void setPlayer2(Player player2){
        this.player2=player2;
    }
    public void addToGraveYard1(Card card){
        graveYardPlayer1.addDeadCard(card);
    }
    public void addToGraveYard2(Card card){
        graveYardPlayer2.addDeadCard(card);
    }
    public Player getPlayer1(){
        return player1;
    }
    public Player getPlayer2(){
        return player2;
    }
    public GraveYard getGraveYardPlayer1() {
        return graveYardPlayer1;
    }
    public GraveYard getGraveYardPlayer2() {
        return graveYardPlayer2;
    }
    public int getTurn(){
        if(playerInTurn==player1)
            return 1;
        return 2;
    }
    public void changeTurn(){
        if(playerInTurn==player1)
            playerInTurn=player2;
        else playerInTurn=player1;
    }
}
