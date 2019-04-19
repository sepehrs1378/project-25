import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ControllerShop {
    private static final ControllerShop ourInstance = new ControllerShop();
    private static final View view = View.getInstance();

    private ControllerShop() {

    }

    public static ControllerShop getOurInstance() {
        return ourInstance;
    }

    public void main() {
        boolean didExit = false;
        Request request = new Request();
        request.getNewCommand();
        while (!didExit) {
            switch (request.getType()) {
                case EXIT:
                    didExit = true;
                    break;
                case SHOW:
                    break;
                case SEARCH:
                    break;
                case BUY:
                    buy(request);
                    break;
                case SELL:
                    break;
                case HELP:
                    break;
                default:
                    System.out.println("!!!!!! bad input in ControllerShop.main");
                    System.exit(-1);
            }
        }
    }

    public void show(Request request) {

    }

    public void sell(Request request) {

    }

    public void buy(Request request) {
        if (!request.getCommand().matches("^buy .+$")) {
            request.setOutputMessageType(outputMessageType.WRONG_COMMAND);
            view.printError(request.getOutputMessageType());
            return;
        }
        Pattern pattern = Pattern.compile("^buy (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        //todo
    }

    public void search(Request request) {

    }

    public void help(Request request) {
        request.setHelpType(HelpType.CONTROLLER_SHOP_HELP);
        view.printHelp(request.getHelpType());
    }
}
