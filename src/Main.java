public class Main {
    public static void main(String[] args) {
        DataBase.getInstance().makeEveryThing();
        ControllerAccount controllerAccount = ControllerAccount.getInstance();
        controllerAccount.main();
    }
}
//todo do we use a Card or its Clone in the Battle?
//todo change money in Constants before tahvil
//todo check insert method : when i move the hero i can't insert cards near it
//todo nullPointerException in "show match history"
//todo complete endGame (completing deck and restoring hp and ap)
//todo singlePlayer decks
