public enum ErrorType {
    WRONG_COMMAND("wrong command :(");

    private String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
