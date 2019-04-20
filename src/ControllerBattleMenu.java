import javax.lang.model.type.ErrorType;

public class ControllerBattleMenu {
    private static ControllerBattleMenu ourInstance = new ControllerBattleMenu();
    private static View view = View.getInstance();

    public static ControllerBattleMenu getInstance() {
        return ourInstance;
    }

    private ControllerBattleMenu() {
    }

    public void main() {
        Request request = new Request();
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case EXIT:
                    didExit = true;
                    break;
                case ENTER:
                    enter(request);
                    break;
            }
        }
    }

    private void enter(Request request) {
        if (request.getCommand().equals("enter single player")) {
            //todo
        } else if (request.getCommand().toLowerCase().equals("enter multi player")) {
            ControllerMultiPlayerMenu.getInstance().main();
        } else {
            request.setOutputMessageType(outputMessageType.WRONG_COMMAND);
            view.printError(request.getOutputMessageType());
        }
    }
}
