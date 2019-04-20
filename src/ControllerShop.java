import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ControllerShop {
    private static final ControllerShop ourInstance = new ControllerShop();
    private static final DataBase dataBase = DataBase.getInstance();
    private static final Account loggedInAccount = dataBase.getLoggedInAccount();
    private static final View view = View.getInstance();

    private ControllerShop() {

    }

    public static ControllerShop getOurInstance() {
        return ourInstance;
    }

    public void main() {
        boolean didExit = false;
        Request request = new Request();
        while (!didExit) {
            request.getNewCommand();
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
        if (!request.getCommand().matches("^sell .+$")) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        Pattern pattern = Pattern.compile("^sell (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        switch (loggedInAccount.getPlayerInfo().getCollection().sell(matcher.group(1))) {
            default:
        }
    }

    public void buy(Request request) {
        if (!request.getCommand().matches("^buy .+$")) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        Pattern pattern = Pattern.compile("^buy (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        switch (loggedInAccount.getPlayerInfo().getCollection().buy(matcher.group(1))) {
            case NOT_IN_SHOP:
                view.printOutputMessage(OutputMessageType.NOT_IN_SHOP);
                break;
            case INSUFFICIENT_MONEY:
                view.printOutputMessage(OutputMessageType.INSUFFICIENT_MONEY);
                break;
            case CANT_HAVE_MORE_ITEMS:
                view.printOutputMessage(OutputMessageType.CANT_HAVE_MORE_ITEMS);
                break;
            case BUY_SUCCESSFUL:
                view.printOutputMessage(OutputMessageType.BUY_SUCCESSFUL);
                break;
            default:
        }
        //todo
    }

    public void search(Request request) {

    }

    public void help(Request request) {
        request.setHelpType(HelpType.CONTROLLER_SHOP_HELP);
        view.printHelp(request.getHelpType());
    }
}
