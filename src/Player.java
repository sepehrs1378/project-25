class Player {
    private PlayerInfo playerInfo;
    private Hand hand;
    private Deck deck;
    private int mana;
    private GraveYard graveYard=new GraveYard();

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public Hand getHand(){
        return hand;
    }

    public Deck getDeck(){
        return deck;
    }

    public int getMana(){
        return mana;
    }

    public void setPlayerInfo(PlayerInfo playerInfo){
        this.playerInfo=playerInfo;
    }

    public void setHand(Hand hand){
        this.hand=hand;
    }

    public void setDeck(Deck deck){
        this.deck=deck;
    }

    public void setMana(int mana){
        this.mana=mana;
    }
}
