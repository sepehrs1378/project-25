package ClientPackage;

public class ControllerAccount {
    private static ControllerAccount controllerAccount = new ControllerAccount();
    private static DataBase dataBase = DataBase.getInstance();
    private static ControllerMainMenu controllerMainMenu = ControllerMainMenu.getInstance();

    private ControllerAccount() {
    }

    public static ControllerAccount getInstance() {
        return controllerAccount;
    }
}
