public class ControllerMultiPlayerMenu {
    private static final View view=View.getInstance();
    private static final ControllerMultiPlayerMenu ourInstance=new ControllerMultiPlayerMenu();
    private static final DataBase database=DataBase.getInstance();
    public static ControllerMultiPlayerMenu getInstance(){
        return ourInstance;
    }
    private ControllerMultiPlayerMenu(){
    }
    public void main(){
        view.showUsers(database.getAccounts(),database.getLoggedInAccount().getUsername());
        Request request=new Request();
        boolean didExit=false;
        while(!didExit)
        {
            request.getNewCommand();
            switch (request.getType()){
                case SELECT:
                    select(request);
                    break;
                case ENTER:
                   break;
                case EXIT:
                    didExit=true;
                    break;
            }
        }
    }
    private void enter(Request request){

    }
    private void select(Request request){
        if(request.getCommand().matches("select user \\w+"));
        {
            Account secondPlayer=Account.getAccount(request.getCommand().split(" ")[2]);
            if(secondPlayer==null)
            {
                request.setErrorType(ErrorType.INVALID_USERNAME);
                view.printError(request.getErrorType());
            }else{
                Battle battle= new Battle();//todo
            }


        }
    }
}
