public class ControllerAccount {
    private static ControllerAccount ourInstance = new ControllerAccount();
    private Request request = Request.getInstance();
    private DataBase dataBase = DataBase.getInstance();
    private View view = View.getInstance();
    private ControllerMainMenu controllerMainMenu = ControllerMainMenu.getInstance();

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
                    login();
                    break;
                case CREATE:
                    create();
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
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    public void login() {
        String username = request.getCommand().split(" ")[1];
        if (!dataBase.doesAccountExist(username)) {
            view.printOutputMessage(OutputMessageType.ACCOUNT_DOESNT_EXIST);
            return;
        }
        view.printOutputMessage(OutputMessageType.PLEASE_ENTER_PASSWORD);
        request.getNewCommand();
        Account account = dataBase.getAccountWithUsername(username);
        if (request.getCommand().split(" ").length < 2){
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        String password = request.getCommand().split(" ")[1];
        if (account.getPassword().equals(password)) {
            dataBase.setLoggedInAccount(account);
            view.printOutputMessage(OutputMessageType.LOGGED_IN_SUCCESSFULLY);
            controllerMainMenu.main();
        } else
            showError(OutputMessageType.INVALID_USERNAME);
    }

    public void create() {
        if (request.getCommand().split(" ").length < 3){
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        String username = request.getCommand().split(" ")[2];
        if (dataBase.doesAccountExist(username)) {
            view.printOutputMessage(OutputMessageType.USERNAME_ALREADY_EXISTS);
            return;
        }
        view.printOutputMessage(OutputMessageType.PLEASE_ENTER_PASSWORD);
        String password;
        while (true) {
            request.getNewCommand();
            if (request.getType() != RequestType.PASSWORD) {
                view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
                continue;
            }
            password = request.getCommand().split(" ")[1];
            if (!password.equals(""))
                //todo check for password validation
                break;
            else
                view.printOutputMessage(OutputMessageType.BAD_PASSWORD);
        }
        Account account = new Account(username, password);
        dataBase.setLoggedInAccount(account);
        dataBase.addAccount(account);
        controllerMainMenu.main();
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
        view.printHelp(HelpType.CONTROLLER_ACCOUNT_HELP);
    }

    public void showError(OutputMessageType error) {
        view.printOutputMessage(error);
    }
}
