import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


public class ControllerMultiPlayerMenu {
    private static ControllerMultiPlayerMenu ourInstance = new ControllerMultiPlayerMenu();
    private DataBase database = DataBase.getInstance();
    private boolean isScreenLocked = false;

    public static ControllerMultiPlayerMenu getInstance() {
        return ourInstance;
    }

    private ControllerMultiPlayerMenu() {
    }

    @FXML
    private ImageView loadingIcon;

    @FXML
    public void findClassicMatch() {
        if (isScreenLocked)
            return;
        new ServerRequestSender
                (new Request(RequestType.findClassicMatch, null, null, null));
        lockScreen();
    }

    @FXML
    public void findOneFlagMatch() {
        if (isScreenLocked)
            return;
        new ServerRequestSender(
                new Request(RequestType.findOneFlagMatch, null, null, null));
        lockScreen();
    }

    @FXML
    public void findMultiFlagsMatch() {
        if (isScreenLocked)
            return;
        new ServerRequestSender
                (new Request(RequestType.findMultiFlagsMatch, null, null, null));
        lockScreen();
    }

    @FXML
    public void cancelMatchFinding() {
        if (!isScreenLocked)
            return;
        new ServerRequestSender
                (new Request(RequestType.cancelMatchFinding, null, null, null));
        unlockScreen();
    }

    @FXML
    public void exitMultiPlayerMenu() {
        if (isScreenLocked)
            return;
        //todo
    }

    private void lockScreen() {
        isScreenLocked = true;
        loadingIcon.setVisible(true);
        //todo
    }

    private void unlockScreen() {
        isScreenLocked = false;
        loadingIcon.setVisible(false);
        //todo
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
