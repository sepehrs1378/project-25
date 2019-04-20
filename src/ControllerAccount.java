public class ControllerAccount {
    private static final DataBase dataBase = DataBase.getInstance();
    private static final ControllerAccount ourInstance = ControllerAccount.getInstance();
    private static final View view = View.getInstance();

    private ControllerAccount() {
    }

    public static ControllerAccount getInstance() {
        return ourInstance;
    }

    public void main() {
        boolean didExit = false;
        Request request = new Request();
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case LOGIN:
                    break;
                case CREATE:
                    break;
                case SHOW:
                    break;
                case HELP:
                    ourInstance.help(request);
                    break;
                case EXIT:
                    didExit = true;
                    break;
                default:
                    System.out.println("!!!!!! bad requestType in Controller.main");
                    System.exit(-1);
            }
        }
    }

    public void login(Request request) {

    }

    public void create(Request request) {
    }

    public void show(Request request) {
        if (!request.getCommand().matches("^show leaderboard$")) {
            request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
            view.printOutputMessage(request.getOutputMessageType());
        } else {
            dataBase.sortAccountsByWins();
            view.showLeaderboard(dataBase.getAccounts());
        }
    }

    public void help(Request request) {
        request.setHelpType(HelpType.CONTROLLER_ACCOUNT_HELP);
        view.printHelp(request.getHelpType());
    }

    public void showLoginError(OutputMessageType error) {
        view.printOutputMessage(error);
    }
}
