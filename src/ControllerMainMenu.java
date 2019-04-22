import javax.print.DocFlavor;

public class ControllerMainMenu {
    private static final Request request = Request.getInstance();
    private static final ControllerMainMenu ourInstance = new ControllerMainMenu();
    private static final View view = View.getInstance();

    public static ControllerMainMenu getInstance() {
        return ourInstance;
    }

    private ControllerMainMenu() {
    }

    public void main() {
        boolean didLogout = false;
        while (!didLogout) {
            request.getNewCommand();
            switch (request.getType()) {
                case ENTER:
                    enter();
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

    public void enter() {
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
                request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
                view.printOutputMessage(request.getOutputMessageType());
        }
    }
}
