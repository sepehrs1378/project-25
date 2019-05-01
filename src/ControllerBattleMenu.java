public class ControllerBattleMenu {
    private static final Request request = Request.getInstance();
    private static final ControllerBattleMenu ourInstance = new ControllerBattleMenu();
    private static final View view = View.getInstance();

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
            }
        }
    }

    private void enter() {
        if (request.getCommand().equals("enter single player")) {
            //todo
        } else if (request.getCommand().equals("enter multi player")) {
            ControllerMultiPlayerMenu.getInstance().main();
        } else {
            request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
            view.printOutputMessage(request.getOutputMessageType());
        }
    }
}
