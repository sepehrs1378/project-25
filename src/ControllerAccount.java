public class ControllerAccount {
    private static final ControllerAccount ourInstance = ControllerAccount.getInstance();
    private static final View view = View.getInstance();

    private ControllerAccount() {

    }

    public ControllerAccount getInstance() {
        return ourInstance;
    }

    public void main() {
        boolean didExit = false;
        Request request = new Request();
        request.getNewCommand();
        while (!didExit) {
            switch (request.getType()) {
                case LOGIN:
                    break;
                case LOGOUT:
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

    public void logout(Request request) {

    }

    public void create(Request request) {

    }

    public void show(Request request) {
        if (!request.getCommand().matches("^show leaderboard$")) {
            request.setErrorType(ErrorType.WRONG_COMMAND);
            view.printError(request.getErrorType());
        }
        //todo else is missing
    }

    public void help(Request request) {
        request.setHelpType(HelpType.CONTROLLER_ACCOUNT_HELP);
        view.printHelp(request.getHelpType());
    }
}
