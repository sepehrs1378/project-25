public class ControllerSinglePlayerMenu {
    private static final Request request = Request.getInstance();
    private static final ControllerSinglePlayerMenu ourInstance=new ControllerSinglePlayerMenu();
    private static final View view = View.getInstance();
    private static final DataBase database = DataBase.getInstance();
    private ControllerSinglePlayerMenu(){

    }
    public void main(){
        view.printHelp(HelpType.);
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
}
