public class Main {
    public static void main(String[] args) {
        DataBase.getInstance().makeEveryThing();
        ControllerAccount controllerAccount = ControllerAccount.getInstance();
        controllerAccount.main();
    }
}
//todo do we use a Card or its Clone in the Battle?
//todo change money in Constants before tahvil
