public class ControllerBattleMenu {
    private static ControllerBattleMenu ourInstance = new ControllerBattleMenu();

    public static ControllerBattleMenu getInstance() {
        return ourInstance;
    }

    private ControllerBattleMenu() {
    }

    public void main(){
        Request request=new Request();
        request.getNewCommand();
        boolean didExit=false;
        while (!didExit){
            //todo
        }
    }
}
