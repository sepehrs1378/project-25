public class Main {
    public static void main(String[] args) {
        DataBase.getInstance().makeEveryThing();
        ControllerAccount controllerAccount = ControllerAccount.getInstance();
        controllerAccount.main();
    }
}
