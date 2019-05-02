public class ControllerSinglePlayerMenu {
    private static final Request request = Request.getInstance();
    private static final ControllerSinglePlayerMenu ourInstance=new ControllerSinglePlayerMenu();
    private static final View view = View.getInstance();
    private static final DataBase database = DataBase.getInstance();
    private ControllerSinglePlayerMenu(){

    }
    public void main(){
        view.printHelp(HelpType.CONTROLLER_SINGLE_PLAYER_MENU);
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case SELECT:
                    break;
                case ENTER:
                    break;
                case EXIT:
                    didExit = true;
                    break;
            }
        }
    }
    public void enter(){
        if (request.getCommand().equals("story")){
            view.printHelp(HelpType.//todo);
        }else if(request.getCommand().equals("custom")){

        }
    }
    public static ControllerSinglePlayerMenu getInstance(){
        return ourInstance;
    }
}
