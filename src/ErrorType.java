public enum ErrorType {
    WRONG_COMMAND("wrong command :("),
    BATTLE_NOT_FINISHED("battle isn't finished yet...");

    private String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
