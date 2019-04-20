public enum OutputMessageType {
    WRONG_COMMAND("Wrong command"),
    BATTLE_NOT_FINISHED("Battle isn't finished yet..."),
    INVALID_PASSWORD("Invalid password"),
    INVALID_USERNAME("This user does not exist"),
    NOT_IN_SHOP("Card or item doesn't exist in shop"),
    INSUFFICIENT_MONEY("You don't have enough money"),
    BUY_SUCCESSFUL("Card or item was bought successfully"),
    SELL_SUCCESSFUL("Card or item was sold successfully"),
    DECK_ALREADY_EXISTS("A deck with the specified name already exists"),
    INVALID_DECK_PLAYER1(""),//todo
    INVALID_DECK_PLAYER2(""),//todo
    CANT_HAVE_MORE_ITEMS("You can't have more items"),
    DECK_CREATED("Deck was created"),
    DECK_DOESNT_EXIST("Deck doesn't exist"),
    DECK_DELETED("Deck was deleted"),
    DECK_SELECTED("Deck was selected as main deck"),
    NO_ERROR("");

    private String message;

    OutputMessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
