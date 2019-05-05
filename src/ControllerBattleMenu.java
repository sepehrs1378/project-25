public class ControllerBattleMenu {
    private static final ControllerBattleMenu ourInstance = new ControllerBattleMenu();
    private final Request request = Request.getInstance();
    private final View view = View.getInstance();

    public static ControllerBattleMenu getInstance() {
        return ourInstance;
    }

    private ControllerBattleMenu() {
    }

    public void main() {
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case EXIT:
                    didExit = true;
                    break;
                case ENTER:
                    enter();
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
        view.printHelp(HelpType.CONTROLLER_BATTLEMENU_HELP);
    }

    private void enter() {
        if (request.getCommand().equals("enter single player")) {
            ControllerSinglePlayerMenu.getInstance().main();
        } else if (request.getCommand().equals("enter multi player")) {
            ControllerMultiPlayerMenu.getInstance().main();
        } else {
            request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
            view.printOutputMessage(request.getOutputMessageType());
        }
    }
}
