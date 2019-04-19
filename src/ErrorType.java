public enum ErrorType {
    WRONG_COMMAND("wrong command :("),
    BATTLE_NOT_FINISHED("battle isn't finished yet..."),
    INVALID_PASSWORD("invalid password"),
    INVALID_USERNAME("this user does not exist"),
    INVALID_DECK_PLAYER2("selected deck for second player is invalid"),
    INVALID_DECK_PLAYER1("selected deck for first player is invalid");


    private String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
