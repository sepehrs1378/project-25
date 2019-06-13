import java.util.List;

public class ControllerSinglePlayerMenu {
    private static ControllerSinglePlayerMenu ourInstance = new ControllerSinglePlayerMenu();
    private Request request = Request.getInstance();
    private View view = View.getInstance();
    private DataBase database = DataBase.getInstance();

    private ControllerSinglePlayerMenu() {
    }

    public void main() throws GoToMainMenuException {
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
                    break;
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

    public void enter() throws GoToMainMenuException {
        if (request.getCommand().equals("enter story")) {
            view.printHelp(HelpType.STORY_MODE_OPTIONS);
            view.print("please enter your level:");
            request.getNewCommand();
            if (request.getCommand().equals("enter level1")) {
                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerLevel1()
                        , Constants.CLASSIC, 0, null, Constants.SINGLE);
                database.setCurrentBattle(battle);
                ControllerBattleCommands.getInstance().main();
            }
            if (request.getCommand().equals("enter level2") && database.getLoggedInAccount().getLevelsOpennessStatus()[1]) {
                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerLevel2()
                        , Constants.ONE_FLAG, 1, null, Constants.SINGLE);
                database.setCurrentBattle(battle);
                ControllerBattleCommands.getInstance().main();
            } else if (request.getCommand().equals("enter level2") && !database.getLoggedInAccount().getLevelsOpennessStatus()[1]) {
                view.printOutputMessage(OutputMessageType.LEVEL_IS_LOCKED);
            }
            if (request.getCommand().equals("enter level3") && database.getLoggedInAccount().getLevelsOpennessStatus()[2]) {
                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerLevel3()
                        , Constants.FLAGS, 7, null, Constants.SINGLE);
                database.setCurrentBattle(battle);
                ControllerBattleCommands.getInstance().main();
            } else if (request.getCommand().equals("enter level3") && !database.getLoggedInAccount().getLevelsOpennessStatus()[2]) {
                view.printOutputMessage(OutputMessageType.LEVEL_IS_LOCKED);
            }
        } else if (request.getCommand().equals("enter custom")) {
            List<Deck> decks = database.getLoggedInAccount().getValidDecks();
            view.printOutputMessage(OutputMessageType.PLEASE_SELECT_A_DECK);
            view.showValidDecks(decks);
            request.getNewCommand();
            if (request.getCommand().matches("^start game \\w+ \\w+\\s*\\w*$")) {
                Deck deck = database.getLoggedInAccount().getPlayerInfo().getCollection().
                        getDeckFromListOfDecks(decks, request.getCommand().split("\\s+")[2]);
                if (deck != null) {
                    database.getComputerPlayerCostum().setMainDeck(new Deck(deck));
                    database.setNewIdsForCustomPlayer();
                    switch (request.getCommand().split("\\s+")[3]) {
                        case Constants.CLASSIC: {
                            Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerCostum(),
                                    Constants.CLASSIC, 0, null, Constants.SINGLE);
                            database.setCurrentBattle(battle);
                            ControllerBattleCommands.getInstance().main();
                            break;
                        }
                        case Constants.ONE_FLAG: {
                            Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerCostum(),
                                    Constants.ONE_FLAG, 1, null, Constants.SINGLE);
                            database.setCurrentBattle(battle);
                            ControllerBattleCommands.getInstance().main();
                            break;
                        }
                        case Constants.FLAGS:
                            if (request.getCommand().split("\\s+").length == 5) {
                                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerCostum(),
                                        Constants.FLAGS, Integer.parseInt(request.getCommand().split("\\s+")[4]), null,
                                        Constants.SINGLE);
                                database.setCurrentBattle(battle);
                                ControllerBattleCommands.getInstance().main();
                            } else {
                                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerCostum(),
                                        Constants.FLAGS, 7, null, Constants.SINGLE);
                                database.setCurrentBattle(battle);
                                ControllerBattleCommands.getInstance().main();
                            }
                            break;
                        default:
                            view.printOutputMessage(OutputMessageType.INVALID_MODE);
                            break;
                    }
                } else view.printOutputMessage(OutputMessageType.DECK_NOT_VALID);
            } else view.printOutputMessage(OutputMessageType.WRONG_COMMAND);


        } else view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
    }


    public static ControllerSinglePlayerMenu getInstance() {
        return ourInstance;
    }
}
