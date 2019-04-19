public class ControllerBattleMenu {
    private static ControllerBattleMenu ourInstance = new ControllerBattleMenu();
    private static View view=View.getInstance();
    public static ControllerBattleMenu getInstance() {
        return ourInstance;
    }

    private ControllerBattleMenu() {
    }

    public void main(){
        Request request=new Request();
        boolean didExit=false;
        while (!didExit){
            request.getNewCommand();
            switch (request.getType()){
                case EXIT:
                    didExit=true;
                    break;
                case ENTER:
                    break;
            }
        }
    }
    private void enter(Request request){
        if(request.getCommand().toLowerCase().equals("enter single player")){

        }else if(request.getCommand().toLowerCase().equals("enter multi player")){

        }else {
            request.setErrorType(ErrorType.WRONG_COMMAND);
            view.printError(request.getErrorType());
        }
    }
}
