import java.util.Scanner;

public class Request {
    private static final Request ourInstance = new Request();
    private static final View view = View.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    private String command;
    private OutputMessageType outputMessageType;
    private HelpType helpType;

    private Request() {

    }

    public static Request getInstance() {
        return ourInstance;
    }

    public void getNewCommand() {
        boolean isValid = false;
        while (!isValid) {
            command = scanner.nextLine().trim().replaceAll("\\s+", " ");
            if (!getType().equals(RequestType.WRONG_REQUEST))
                isValid = true;
            else {
                outputMessageType = OutputMessageType.WRONG_COMMAND;
                view.printOutputMessage(outputMessageType);
            }
        }
    }

    public String getCommand() {
        return command;
    }

    public void setOutputMessageType(OutputMessageType outputMessageType) {
        this.outputMessageType = outputMessageType;
    }

    public OutputMessageType getOutputMessageType() {
        return outputMessageType;
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
        if (command.matches("^logout$")) {
            command = command.toLowerCase();
            return RequestType.LOGOUT;
        }
        if (command.matches("^login .+$")) {
            command = command.split(" ")[0].toLowerCase() + " " + command.split(" ")[1];
            return RequestType.LOGIN;
        }
        if (command.matches("^enter .+$")) {
            command = command.toLowerCase();
            return RequestType.ENTER;
        }
        if (command.matches("^exit$")) {
            command = command.toLowerCase();
            return RequestType.END;
        }
        if (command.matches("^show\\s*(.)*$")) {
            command = command.toLowerCase();
            return RequestType.SHOW;
        }
        if (command.matches("^save$")) {
            command = command.toLowerCase();
            return RequestType.SAVE;
        }
        if (command.matches("^search .+$")) {
            command = command.toLowerCase();
            return RequestType.SEARCH;
        }
        if (command.matches("^help$")) {
            command = command.toLowerCase();
            return RequestType.HELP;
        }
        if (command.matches("^buy .+$")) {
            command = command.split(" ")[0].toLowerCase() + " " + command.split(" ")[1];
            return RequestType.BUY;
        }
        if (command.matches("^sell .+$")) {
            command = command.split(" ")[0].toLowerCase() + " " + command.split(" ")[1];
            return RequestType.SELL;
        }
        if (command.matches("^create .+$")) {
            command = command.toLowerCase();
            return RequestType.CREATE;
        }
        if (command.matches("^delete .+$")) {
            command = command.toLowerCase();
            return RequestType.DELETE;
        }
        if (command.matches("^add .+$")) {
            command = command.toLowerCase();
            return RequestType.ADD;
        }
        if (command.matches("^select .+$")) {
            command = command.toLowerCase();
            return RequestType.SELECT;
        }
        if (command.matches("^validate .+$")) {
            command = command.toLowerCase();
            return RequestType.VALIDATE;
        }
        if (command.matches("^show \\w+ minions$")) {
            command = command.toLowerCase();
            return RequestType.SHOW_MINIONS;
        }
        if (command.matches("^move to .+$")) {
            command = command.toLowerCase();
            return RequestType.MOVE;
        }
        if (command.matches("^attack .+$")) {
            command = command.toLowerCase();
            return RequestType.ATTACK;
        }
        if (command.matches("^use .+$")) {
            command = command.toLowerCase();
            return RequestType.USE;
        }
        if (command.matches("^insert .+$")) {
            command = command.toLowerCase();
            return RequestType.INSERT;
        }
        if (command.matches("^end .+$")) {
            command = command.toLowerCase();
            return RequestType.END;
        }
        if (command.matches("^game info$")) {
            command = command.toLowerCase();
            return RequestType.GAME_INFO;
        }
        if (command.matches("^/.+$"))
            return RequestType.CHEATS;
        if (command.matches("^password .+$")) {
            command = command.split(" ")[0].toLowerCase() + " " + command.split(" ")[1];
            return RequestType.PASSWORD;
        }
        return RequestType.WRONG_REQUEST;
    }
}
