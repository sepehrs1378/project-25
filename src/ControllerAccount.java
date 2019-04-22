public class ControllerAccount {
    private static final Request request=Request.getInstance();
    private static final DataBase dataBase = DataBase.getInstance();
    private static final ControllerAccount ourInstance = new ControllerAccount();
    private static final View view = View.getInstance();

    private ControllerAccount() {
    }

    public static ControllerAccount getInstance() {
        return ourInstance;
    }

    public void main() {
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case LOGIN:
                    break;
                case CREATE:
                    break;
                case SHOW:
                    show();
                    break;
                case HELP:
                    help();
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

    public void login() {

    }

    public void create() {
    }

    public void show() {
        if (!request.getCommand().matches("^show leaderboard$")) {
            request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
            view.printOutputMessage(request.getOutputMessageType());
        } else {
            dataBase.sortAccountsByWins();
            view.showLeaderboard(dataBase.getAccounts());
        }
    }

    public void help() {
        request.setHelpType(HelpType.CONTROLLER_ACCOUNT_HELP);
        view.printHelp(request.getHelpType());
    }

    public void showLoginError(OutputMessageType error) {
        view.printOutputMessage(error);
    }
}
