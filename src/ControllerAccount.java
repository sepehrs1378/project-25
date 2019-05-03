public class ControllerAccount {
    private static final Request request = Request.getInstance();
    private static final DataBase dataBase = DataBase.getInstance();
    private static final ControllerAccount ourInstance = new ControllerAccount();
    private static final View view = View.getInstance();
    private final ControllerMainMenu controllerMainMenu = ControllerMainMenu.getInstance();

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
                    System.out.println("!!!!!! bad requestType in Controller.main");
                    System.exit(-1);
            }
        }
    }

    public void login() {
        request.getNewCommand();
        for (Account account : dataBase.getAccounts()) {
            if (account.getUsername().equals(request.getCommand())) {
                request.getNewCommand();
                if (account.getPassword().equals(request.getCommand())) {
                    dataBase.setLoggedInAccount(account);
                    controllerMainMenu.main();
                }
            } else {
                showError(OutputMessageType.INVALID_PASSWORD);
            }
        }
        showError(OutputMessageType.INVALID_USERNAME);
    }

    public void create() {
        boolean isUserNameUnique = false;
        while (!isUserNameUnique){
            request.getNewCommand();
            boolean flag= false;
            for (Account account: dataBase.getAccounts()){
                if(request.getCommand().equals(account.getUsername())){
                    flag =true;
                }
            }
            if (!flag){
                isUserNameUnique = true;
            }
        }
        String userName = request.getCommand();
        request.getNewCommand();
        String password =request.getCommand();
        Account account = new Account(userName,password);
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
        request.setHelpType(HelpType.CONTROLLER_ACCOUNT_HELP);
        view.printHelp(request.getHelpType());
    }

    public void showError(OutputMessageType error) {
        view.printOutputMessage(error);
    }
}
