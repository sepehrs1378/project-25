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
        if (command.toLowerCase().matches("^logout$")) {
            command = command.toLowerCase();
            return RequestType.LOGOUT;
        }
        if (command.toLowerCase().matches("^login .+$")) {
            command = command.split(" ")[0].toLowerCase() + " " + command.split(" ")[1];
            return RequestType.LOGIN;
        }
        if (command.toLowerCase().matches("^enter .+$")) {
            command = command.toLowerCase();
            return RequestType.ENTER;
        }
        if (command.toLowerCase().matches("^exit$")) {
            command = command.toLowerCase();
            return RequestType.EXIT;
        }
        if (command.toLowerCase().matches("^show\\s*(.)*$")) {
            command = command.toLowerCase();
            return RequestType.SHOW;
        }
        if (command.toLowerCase().matches("^save$")) {
            command = command.toLowerCase();
            return RequestType.SAVE;
        }
        if (command.toLowerCase().matches("^search .+$")) {
            String[] strings = command.split(" ");
            String output = "";
            int counter = 0;
            for (String string : strings) {
                if (counter != strings.length - 1) {
                    output += string.toLowerCase() + " ";
                } else {
                    output += string;
                }
                counter++;
            }
            command = output;
            return RequestType.SEARCH;
        }
        if (command.toLowerCase().matches("^help$")) {
            command = command.toLowerCase();
            return RequestType.HELP;
        }
        if (command.toLowerCase().matches("^buy .+$")) {
            command = command.split(" ")[0].toLowerCase() + " " + command.split(" ")[1];
            return RequestType.BUY;
        }
        if (command.toLowerCase().matches("^sell .+$")) {
            command = command.split(" ")[0].toLowerCase() + " " + command.split(" ")[1];
            return RequestType.SELL;
        }
        if (command.toLowerCase().matches("^create .+$")) {
            command = command.toLowerCase();
            return RequestType.CREATE;
        }
        if (command.toLowerCase().matches("^delete .+$")) {
            command = command.toLowerCase();
            return RequestType.DELETE;
        }
        if (command.toLowerCase().matches("^add .+$")) {
            setCommandWithUnderLine();
            return RequestType.ADD;
        }
        if (command.toLowerCase().matches("^select .+$")) {
            command = command.toLowerCase();
            return RequestType.SELECT;
        }
        if (command.toLowerCase().matches("^validate .+$")) {
            command = command.toLowerCase();
            return RequestType.VALIDATE;
        }
        if (command.toLowerCase().matches("^show \\w+ minions$")) {
            command = command.toLowerCase();
            return RequestType.SHOW_MINIONS;
        }
        if (command.toLowerCase().matches("^move to .+$")) {
            command = command.toLowerCase();
            return RequestType.MOVE;
        }
        if (command.toLowerCase().matches("^attack .+$")) {
            command = command.toLowerCase();
            return RequestType.ATTACK;
        }
        if (command.toLowerCase().matches("^use .+$")) {
            command = command.toLowerCase();
            return RequestType.USE;
        }
        if (command.toLowerCase().matches("^insert .+$")) {
            command = command.toLowerCase();
            return RequestType.INSERT;
        }
        if (command.toLowerCase().matches("^end .+$")) {
            command = command.toLowerCase();
            return RequestType.END;
        }
        if (command.toLowerCase().matches("^game info$")) {
            command = command.toLowerCase();
            return RequestType.GAME_INFO;
        }
        if (command.toLowerCase().matches("^/.+$"))
            return RequestType.CHEATS;
        if (command.toLowerCase().matches("^password .+$")) {
            command = command.split(" ")[0].toLowerCase() + " " + command.split(" ")[1];
            return RequestType.PASSWORD;
        }
        if(command.toLowerCase().matches("^remove .+$")){
            setCommandWithUnderLine();
            return RequestType.REMOVE;
        }
        if(command.toLowerCase().matches("^start .+$")){
            command = command.toLowerCase();
            return RequestType.START;
        }
        return RequestType.WRONG_REQUEST;
    }

    private void setCommandWithUnderLine() {
        String[] strings = command.split("\\s+");
        String output = "";
        for (String string : strings) {
            if (string.contains("_")) {
                output += string + " ";
            } else output += string.toLowerCase() + " ";
        }
        command = output.trim();
    }

}
