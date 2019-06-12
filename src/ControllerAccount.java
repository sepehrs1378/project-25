public class ControllerAccount {
    private static ControllerAccount controllerAccount = new ControllerAccount();
    private Request request = Request.getInstance();
    private DataBase dataBase = DataBase.getInstance();
    private View view = View.getInstance();
    private ControllerMainMenu controllerMainMenu = ControllerMainMenu.getInstance();

    private ControllerAccount() {
    }

    public static ControllerAccount getInstance() {
        return controllerAccount;
    }

    public void main() {
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case CREATE_ACCOUNT_NAME:
                    createAccountName();
                    break;
                case LOGIN_NAME:
                    loginName();
                    break;
                case SHOW_LEADERBOARD:
                    showLeaderboard();
                    break;
                case EXIT:
                    didExit = true;
                    break;
                case HELP:
                    help();
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    private void loginName() {
        String username = request.getCommand().split(" ")[1];
        if (!dataBase.doesAccountExist(username)) {
            view.printOutputMessage(OutputMessageType.ACCOUNT_DOESNT_EXIST);
            return;
        }
        view.printOutputMessage(OutputMessageType.PLEASE_ENTER_PASSWORD);
        request.getNewCommand();
        Account account = dataBase.getAccountWithUsername(username);
        if (request.getCommand().split(" ").length < 2) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        if (account.getPassword().equals(request.getCommand().split(" ")[1])) {
            dataBase.setLoggedInAccount(account);
            view.printOutputMessage(OutputMessageType.LOGGED_IN_SUCCESSFULLY);
            controllerMainMenu.main();
        } else
            showError(OutputMessageType.INVALID_PASSWORD);
    }


    public void help() {
        view.printHelp(HelpType.CONTROLLER_ACCOUNT_HELP);
    }

    private void showError(OutputMessageType error) {
        view.printOutputMessage(error);
    }

    private void createAccountName() {
        if (request.getCommand().split(" ").length < 3) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        String username = request.getCommand().split(" ")[2];
        if (dataBase.doesAccountExist(username)) {
            view.printOutputMessage(OutputMessageType.USERNAME_ALREADY_EXISTS);
            return;
        }
        String password;
        view.printOutputMessage(OutputMessageType.PLEASE_ENTER_PASSWORD);
        while (true) {
            request.getNewCommand();
            if (request.getType() != RequestType.PASSWORD) {
                view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
                continue;
            }
            password = request.getCommand().split(" ")[1];
            if (!password.equals(""))
                break;
            else
                view.printOutputMessage(OutputMessageType.BAD_PASSWORD);
        }
        Account account = new Account(username, password);
        dataBase.setLoggedInAccount(account);
        dataBase.addAccount(account);
        view.printOutputMessage(OutputMessageType.CREATED_ACCOUNT_SUCCESSFULLY);
        controllerMainMenu.main();
    }

    private void showLeaderboard() {
        dataBase.sortAccountsByWins();
        view.showLeaderBoard(dataBase.getAccounts());
    }
}
