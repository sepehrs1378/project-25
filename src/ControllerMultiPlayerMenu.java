public class ControllerMultiPlayerMenu {
    private static ControllerMultiPlayerMenu ourInstance = new ControllerMultiPlayerMenu();
    private Request request = Request.getInstance();
    private View view = View.getInstance();
    private DataBase database = DataBase.getInstance();

    public static ControllerMultiPlayerMenu getInstance() {
        return ourInstance;
    }

    private ControllerMultiPlayerMenu() {
    }

    public void main() {
        view.showUsers(database.getAccounts(), database.getLoggedInAccount().getUsername());
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case SELECT_USER_NAME:
                    selectUser();
                    break;
                case EXIT:
                    didExit = true;
                    break;
                case HELP:
                    help();
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    private void help() {
        view.printHelp(HelpType.CONTROLLER_MULTI_PLAYER_MENU);
    }

    private void selectUser() {
        Account secondPlayer = database.getAccountWithUsername(request.getCommand().split(" ")[2]);
        if (secondPlayer == null) {
            request.setOutputMessageType(OutputMessageType.INVALID_USERNAME);
            view.printOutputMessage(request.getOutputMessageType());
        } else {
            request.setHelpType(HelpType.MODES_HELP);
            view.printHelp(request.getHelpType());
            request.getNewCommand();
            if (request.getCommand().matches("start multiplayer game \\w+\\s*\\w*")) {
                //todo refactor this method
                int numberOfFlags = 0;
                String mode = request.getCommand().split(" ")[3];
                if (mode.equals(Constants.FLAGS)) {
                    if (request.getCommand().split(" ").length == 5) {
                        numberOfFlags = Integer.parseInt(request.getCommand().split(" ")[4]);
                    } else {
                        request.setOutputMessageType(OutputMessageType.NO_FLAG_NUMBER);
                        view.printOutputMessage(request.getOutputMessageType());
                    }
                } else if (mode.equals(Constants.ONE_FLAG)) {
                    numberOfFlags = 1;
                }
                if (database.getLoggedInAccount().getMainDeck() != null &&
                        secondPlayer.getMainDeck() != null
                        && database.getLoggedInAccount().getMainDeck().isValid() && secondPlayer.getMainDeck().isValid()) {
                    Battle battle = new Battle(database.getLoggedInAccount(), secondPlayer, mode, numberOfFlags, null);
                    database.setCurrentBattle(battle);
                    ControllerBattleCommands.getInstance().main();
                } else {
                    request.setOutputMessageType(OutputMessageType.INVALID_DECK_PLAYER);
                    view.printOutputMessage(request.getOutputMessageType());
                }
            } else {
                request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
                view.printOutputMessage(request.getOutputMessageType());
            }
        }
    }
}
