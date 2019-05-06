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
            else
                view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
        }
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
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
        if (command.toLowerCase().matches("^logout$"))
            return RequestType.LOGOUT;
        if (command.toLowerCase().matches("^login [^ ]+$"))
            return RequestType.LOGIN_NAME;
        if (command.toLowerCase().matches("^enter [^ ]+$"))
            return RequestType.ENTER;
        if (command.toLowerCase().matches("^exit$"))
            return RequestType.EXIT;
        if (command.toLowerCase().matches("^show leaderboard$"))
            return RequestType.SHOW_LEADERBOARD;
        if (command.toLowerCase().matches("^show collection$"))
            return RequestType.SHOW_COLLECTION;
        if (command.toLowerCase().matches("^show all decks$"))
            return RequestType.SHOW_ALL_DECKS;
        if (command.toLowerCase().matches("^show$"))
            return RequestType.SHOW;
        if (command.toLowerCase().matches("^show deck [^ ]+$"))
            return RequestType.SHOW_DECK_NAME;
        if (command.toLowerCase().matches("^show info$"))
            return RequestType.SHOW_INFO;
        if (command.toLowerCase().matches("^show hand$"))
            return RequestType.SHOW_HAND;
        if (command.toLowerCase().matches("^show menu$"))
            return RequestType.SHOW_MENU;
        if (command.toLowerCase().matches("^show cards$"))
            return RequestType.SHOW_CARDS;
        if (command.toLowerCase().matches("^show collectables$"))
            return RequestType.SHOW_COLLECTABLES;
        if (command.toLowerCase().matches("^show next card$"))
            return RequestType.SHOW_NEXT_CARD;
        if (command.toLowerCase().matches("^show card info [^ ]+_[^ ]+_[^ ]+$"))
            return RequestType.SHOW_CARD_INFO_ID;
        if (command.toLowerCase().matches("^show info [^ ]+_[^ ]+_[^ ]+"))
            return RequestType.SHOW_INFO_ID;
        if (command.toLowerCase().matches("^save$"))
            return RequestType.SAVE;
        if (command.toLowerCase().matches("^search [^ ]+$"))
/*
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
*/
            return RequestType.SEARCH_NAME;
        if (command.toLowerCase().matches("^search collection [^ ]+$"))
            return RequestType.SEARCH_COLLECTION_NAME;
        if (command.toLowerCase().matches("^help$"))
            return RequestType.HELP;
        if (command.toLowerCase().matches("^buy [^ ]+$"))
            return RequestType.BUY_NAME;
        if (command.toLowerCase().matches("^sell [^ ]+_[^ ]+_\\d+$"))
            return RequestType.SELL_ID;
        if (command.toLowerCase().matches("^create account [^ ]+$"))
            return RequestType.CREATE_ACCOUNT_NAME;
        if (command.toLowerCase().matches("^create deck [^ ]+$"))
            return RequestType.CREATE_DECK_NAME;
        if (command.toLowerCase().matches("^delete deck [^ ]+$"))
            return RequestType.DELETE_DECK_NAME;
        if (command.toLowerCase().matches("^add [^ ]+_[^ ]+_\\d+ to deck [^ ]+$"))
            return RequestType.ADD_ID_TO_DECK_NAME;
        if (command.toLowerCase().matches("^select deck [^ ]+$"))
            return RequestType.SELECT_DECK_NAME;
        if (command.toLowerCase().matches("^select [^ ]+_[^ ]+_\\d+"))
            return RequestType.SELECT_ID;
        if(command.toLowerCase().matches("^select user [^ ]+$"))
            return RequestType.SELECT_USER_NAME;
        if (command.toLowerCase().matches("^validate deck [^ ]+$"))
            return RequestType.VALIDATE_DECK_NAME;
        if (command.toLowerCase().matches("^show my minions$"))
            return RequestType.SHOW_MY_MINIONS;
        if (command.toLowerCase().matches("^show opponent minions$"))
            return RequestType.SHOW_OPPONENT_MINIONS;
        if (command.toLowerCase().matches("^show battleground$"))
            return RequestType.SHOW_BATTLEGROUND;
        if (command.toLowerCase().matches("^move to [(]\\d+,\\d+[)]$"))
            return RequestType.MOVE_TO_X_Y;
        if (command.toLowerCase().matches("^attack [^ ]+_[^ ]+_\\d+$"))
            return RequestType.ATTACK_ID;
        if (command.toLowerCase().matches("^attack combo( [^ ]+_[^ ]+_\\d+){2,}$"))
            return RequestType.ATTACK_COMBO;
        if (command.toLowerCase().matches("^use [(]\\d+,\\d+[)]$"))
            return RequestType.USE_COLLECTABLE_IN_X_Y;
        if (command.toLowerCase().matches("^use special power [(]\\d+,\\d+[)]$"))
            return RequestType.USE_SPECIAL_POWER_X_Y;
        if (command.toLowerCase().matches("^insert [^ ]+ in [(]\\d+,\\d+[)]$"))
            return RequestType.INSERT_NAME_IN_X_Y;
        if (command.toLowerCase().matches("^end game$"))
            return RequestType.END_GAME;
        if (command.toLowerCase().matches("^end turn$"))
            return RequestType.END_TURN;
        if (command.toLowerCase().matches("^game info$"))
            return RequestType.GAME_INFO;
        if (command.toLowerCase().matches("^/.+$"))
            return RequestType.CHEATS;
        if (command.toLowerCase().matches("^password [^ ]+$"))
            return RequestType.PASSWORD;
        if (command.toLowerCase().matches("^remove [^ ]+_[^ ]+_\\d+ from deck [^ ]+$"))
            return RequestType.REMOVE_ID_FROM_DECK_NAME;
        if (command.toLowerCase().matches("^start .+$")) //todo correct it later
            return RequestType.START;
        if (command.toLowerCase().matches("^match history$"))
            return RequestType.MATCH_HISTORY;
        return RequestType.WRONG_REQUEST;
    }

/*
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
*/
}
