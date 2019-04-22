public enum outputMessageType {
    BATTLE_NOT_FINISHED("Battle isn't finished yet..."),
    INVALID_PASSWORD("Invalid password"),
    INVALID_USERNAME("This user does not exist"),
    NOT_IN_SHOP("Card or item doesn't exist in shop"),
    INSUFFICIENT_MONEY("You don't have enough money"),
    BUY_SUCCESSFUL("Card or item was bought successfully"),
    SELL_SUCCESSFUL("Card or item was sold successfully"),
    DECK_ALREADY_EXISTS("A deck with the specified name already exists"),
    NO_CARD_IN_BATTLEGROUND("there is no such card in the battle ground"),
    WRONG_COMMAND("Invalid command"),
    INVALID_DECK_PLAYER1(""),//todo
    INVALID_DECK_PLAYER2(""),//todo
    NO_ERROR("");

    private String message;

    outputMessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
