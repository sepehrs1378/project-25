public class ControllerMultiPlayerMenu {
    private static ControllerMultiPlayerMenu ourInstance = new ControllerMultiPlayerMenu();
    private DataBase database = DataBase.getInstance();

    public static ControllerMultiPlayerMenu getInstance() {
        return ourInstance;
    }

    private ControllerMultiPlayerMenu() {
    }

    private void selectUser() throws GoToMainMenuException {
        /*Account secondPlayer = database.getAccountWithUsername(request.getCommand().split(" ")[2]);
        if (secondPlayer == null) {
            request.setOutputMessageType(OutputMessageType.INVALID_USERNAME);
            view.printOutputMessage(request.getOutputMessageType());
        } else {
            request.setHelpType(HelpType.MODES_HELP);
            view.printHelp(request.getHelpType());
            request.getNewCommand();
            if (request.getCommand().matches("start multiplayer game \\w+\\s*\\w*")) {
                //todo refactor this method (works already)
                int numberOfFlags = 0;
                String mode = request.getCommand().split(" ")[3];
                if (mode.equals(Constants.FLAGS)) {
                    if (request.getCommand().split(" ").length == 5) {
                        numberOfFlags = Integer.parseInt(request.getCommand().split(" ")[4]);
                    } else {
                        numberOfFlags = 7;
                    }
                } else if (mode.equals(Constants.ONE_FLAG)) {
                    numberOfFlags = 1;
                }
                if (database.getLoggedInAccount().getMainDeck() != null &&
                        secondPlayer.getMainDeck() != null
                        && database.getLoggedInAccount().getMainDeck().isValid() && secondPlayer.getMainDeck().isValid()) {
                    Battle battle = new Battle(database.getLoggedInAccount(), secondPlayer, mode, numberOfFlags, null, Constants.MULTI,1000);
                    database.setCurrentBattle(battle);
                    ControllerBattleCommands.getOurInstance().main();
                } else {
                    request.setOutputMessageType(OutputMessageType.INVALID_DECK_PLAYER);
                    view.printOutputMessage(request.getOutputMessageType());
                }
            } else {
                request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
                view.printOutputMessage(request.getOutputMessageType());
            }
        }*/
    }
}
