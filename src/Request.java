import java.util.Scanner;

public class Request {
    private static Scanner scanner = new Scanner(System.in);
    private String command;
    private ErrorType errorType;
    private HelpType helpType;

    public void getNewCommand() {
        command = scanner.nextLine().toLowerCase();
    }

    public String getCommand() {
        return command;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setHelpType(HelpType helpType) {
        this.helpType = helpType;
    }

    public HelpType getHelpType() {
        return helpType;
    }

    public RequestType getType() {
        if (command == null || command.equals(""))
            return RequestType.WRONG_REQUEST;
        if (command.matches("^logout .+$"))
            return RequestType.LOGOUT;
        if (command.matches("^login .+$"))
            return RequestType.LOGIN;
        if (command.matches("^enter .+$"))
            return RequestType.ENTER;
        if (command.matches("^exit$"))
            return RequestType.END;
        if (command.matches("^show .+$"))
            return RequestType.SHOW;
        if (command.matches("^save$"))
            return RequestType.SHOW.SAVE;
        if (command.matches("^search .+$"))
            return RequestType.SEARCH;
        if (command.matches("^help$"))
            return RequestType.HELP;
        if (command.matches("^buy .+$"))
            return RequestType.BUY;
        if (command.matches("^sell .+$"))
            return RequestType.SELL;
        if (command.matches("^create .+$"))
            return RequestType.CREATE;
        if (command.matches("^delete .+$"))
            return RequestType.DELETE;
        if (command.matches("^add .+$"))
            return RequestType.ADD;
        if (command.matches("^select .+$"))
            return RequestType.SELECT;
        if (command.matches("^validate .+$"))
            return RequestType.VALIDATE;
        if (command.matches("^show \\w+ minions$"))
            return RequestType.SHOW_MINIONS;
        if (command.matches("^move to .+$"))
            return RequestType.MOVE;
        if (command.matches("^attack .+$"))
            return RequestType.ATTACK;
        if (command.matches("^use .+$"))
            return RequestType.USE;
        if (command.matches("^insert .+$"))
            return RequestType.INSERT;
        if (command.matches("^end .+$"))
            return RequestType.END;
        if (command.matches("^game info$"))
            return RequestType.GAME_INFO;
        return RequestType.WRONG_REQUEST;
    }


}
