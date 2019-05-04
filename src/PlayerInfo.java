class PlayerInfo {
    private String playerName;
    private PlayerCollection collection = new PlayerCollection();

    public PlayerInfo(String playerName) {
        this.playerName = playerName;
    }

    public void addCardToCollection(Card newCard) {
        collection.addCard(newCard);
    }

    public void addUsableToCollection(Usable usable) {
        collection.addItem(usable);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public PlayerCollection getCollection() {
        return collection;
    }

    public String getPlayerName() {
        return playerName;
    }

}
