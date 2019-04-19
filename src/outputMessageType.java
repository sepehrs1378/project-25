public enum outputMessageType {
    WRONG_COMMAND("Wrong command"),
    BATTLE_NOT_FINISHED("Battle isn't finished yet..."),
    INVALID_PASSWORD("Invalid password"),
    INVALID_USERNAME("This user does not exist"),
    NOT_IN_SHOP("Card or item doesn't exist in shop"),
    INSUFFICIENT_MONEY("You don't have enough money"),
    BUY_SUCCESSFUL("Card or item was bought successfully"),
    SELL_SUCCESSFUL("Card or item was sold successfully"),
    NO_ERROR("");

    private String message;

    outputMessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
