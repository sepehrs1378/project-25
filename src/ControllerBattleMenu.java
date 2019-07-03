public class ControllerBattleMenu {
    private static final ControllerBattleMenu ourInstance = new ControllerBattleMenu();
    private static final DataBase dataBase = DataBase.getInstance();

    public static ControllerBattleMenu getInstance() {
        return ourInstance;
    }

    private ControllerBattleMenu() {
    }
}
