public class ControllerMainMenu {
    private static ControllerMainMenu ourInstance = new ControllerMainMenu();
    private static final View view = View.getInstance();

    public static ControllerMainMenu getInstance() {
        return ourInstance;
    }

    private ControllerMainMenu() {
    }

    public void main() {
        boolean didLogout = false;
        Request request = new Request();
        while (!didLogout) {
            request.getNewCommand();
            switch (request.getType()) {
                case ENTER:
                    ourInstance.enter(request);
                    break;
                case SAVE:
                    //todo
                    break;
                case LOGOUT:
                    didLogout = true;
                    break;
                default:
                    System.out.println("!!!!!! bad input in ControllerMainMenu.main");
                    System.exit(-1);
            }
        }
    }

    public void save() {

    }

    public void enter(Request request) {
        switch (request.getCommand()) {
            case "enter collection":
                ControllerCollection.getInstance().main();
                break;
            case "enter shop":
                ControllerShop.getOurInstance().main();
                break;
            case "enter battle":
                ControllerBattleMenu.getInstance().main();
                break;
            default:
                request.setOutputMessageType(outputMessageType.WRONG_COMMAND);
                view.printError(request.getOutputMessageType());
        }
    }
}
