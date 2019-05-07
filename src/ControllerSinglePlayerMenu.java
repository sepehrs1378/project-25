public class ControllerSinglePlayerMenu {
    private static ControllerSinglePlayerMenu ourInstance = new ControllerSinglePlayerMenu();
    private Request request = Request.getInstance();
    private View view = View.getInstance();
    private DataBase database = DataBase.getInstance();

    private ControllerSinglePlayerMenu() {

    }

    public void main() throws GoToMainMenuException{
        view.printHelp(HelpType.CONTROLLER_SINGLE_PLAYER_MENU);
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case SELECT_DECK_NAME:
                    break;
                case ENTER:
                    enter();
                    break;
                case HELP:
                    help();
                case EXIT:
                    didExit = true;
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    private void help() {
        view.printHelp(HelpType.CONTROLLER_SINGLE_PLAYER_MENU);
    }

    public void enter() throws GoToMainMenuException{
        if (request.getCommand().equals("story")) {
            view.printHelp(HelpType.STORY_MODE_OPTIONS);
            view.print("please enter your level:");
            request.getNewCommand();
            if (request.getCommand().equals("level1")) {
                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerLevel1()
                        , Constants.CLASSIC, 0, null);
                database.setCurrentBattle(battle);
                ControllerBattleCommands.getInstance().main();
            } else if (request.getCommand().equals("level2")) {
                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerLevel2()
                        , Constants.ONE_FLAG, 1, null);
                database.setCurrentBattle(battle);
                ControllerBattleCommands.getInstance().main();
            } else if (request.getCommand().equals("level3")) {
                view.print("Please enter number of flags:");
                boolean isTrueNumber = false;
                int numberOfFlags = 0;
                while (!isTrueNumber) {
                    request.getNewCommand();
                    if (request.getCommand().matches("\\d+") && Integer.parseInt(request.getCommand()) > 0) {
                        numberOfFlags = Integer.parseInt(request.getCommand());
                        isTrueNumber = true;
                    } else {
                        view.printOutputMessage(OutputMessageType.INVALID_NUMBER);
                        view.print("please enter a correct number");
                    }

                }
                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerLevel3()
                        , Constants.FLAGS, numberOfFlags, null);
                database.setCurrentBattle(battle);
                ControllerBattleCommands.getInstance().main();
            }
        } else if (request.getCommand().equals("custom")) {
            //todo
        }
    }

    public static ControllerSinglePlayerMenu getInstance() {
        return ourInstance;
    }
}
