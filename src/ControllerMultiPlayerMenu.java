public class ControllerMultiPlayerMenu {
    private static final Request request = Request.getInstance();
    private static final View view = View.getInstance();
    private static final ControllerMultiPlayerMenu ourInstance = new ControllerMultiPlayerMenu();
    private static final DataBase database = DataBase.getInstance();

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
                case SELECT:
                    select();
                    break;
                case ENTER:
                    break;
                case EXIT:
                    didExit = true;
                    break;
            }
        }
    }

    private void enter() {
        if(request.getCommand().equals("story")){
            //todo
        }
        else if(request.getCommand().equals("custom")){
            //todo
        }
    }

    private void select() {
        if (request.getCommand().matches("select user \\w+")) {
            Account secondPlayer = Account.getAccount(request.getCommand().split(" ")[2]);
            if (secondPlayer == null) {
                request.setOutputMessageType(OutputMessageType.INVALID_USERNAME);
                view.printOutputMessage(request.getOutputMessageType());
            } else {
                request.setHelpType(HelpType.MODES_HELP);
                view.printHelp(request.getHelpType());
                request.getNewCommand();
                if (request.getCommand().matches("start multiplayer game \\w+ \\w+")) {
                    int numberOfFlags = 0;
                    String mode = request.getCommand().split(" ")[3];
                    if (mode.equals(Constants.FLAGS)) {
                        if (request.getCommand().split(" ").length == 5) {
                            numberOfFlags = Integer.parseInt(request.getCommand().split(" ")[4]);
                        } else {
                            request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
                            view.printOutputMessage(request.getOutputMessageType());
                        }
                    } else if (mode.equals(Constants.ONE_FLAG)) {
                        numberOfFlags = 1;
                    }
                    if (database.getLoggedInAccount().getMainDeck().isValid() && secondPlayer.getMainDeck().isValid()) {
                        Battle battle = new Battle(database.getLoggedInAccount(), secondPlayer, mode, numberOfFlags);
                        database.setCurrentBattle(battle);
                    } else {
                        request.setOutputMessageType(OutputMessageType.INVALID_DECK_PLAYER2);
                        view.printOutputMessage(request.getOutputMessageType());
                    }
                } else {
                    request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
                    view.printOutputMessage(request.getOutputMessageType());
                }

            }
        }
    }
}
