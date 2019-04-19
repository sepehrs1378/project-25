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
                request.setHelpType(HelpType.MODES_HELP);
                view.printHelp(request.getHelpType());
                request.getNewCommand();
                if(request.getCommand().matches("start multiplayer game \\w+ \\w+")) {
                    int numberOfFlags = 0;
                    String mode = request.getCommand().split(" ")[3];
                    if (mode.equals(Constants.FLAGS)) {
                        if (request.getCommand().split(" ").length == 5) {
                            numberOfFlags = Integer.parseInt(request.getCommand().split(" ")[4]);
                        } else {
                            request.setErrorType(ErrorType.WRONG_COMMAND);
                            view.printError(request.getErrorType());
                        }
                    } else if (mode.equals(Constants.ONE_FLAG)) {
                        numberOfFlags = 1;
                    }
                    if(Account.getLoggedInAccount().getMainDeck().isValid()&&secondPlayer.getMainDeck().isValid()){
                        Battle battle = new Battle(Account.getLoggedInAccount(), secondPlayer, mode, numberOfFlags);
                        database.setCurrentBattle(battle);
                    }else{
                        request.setErrorType(ErrorType.INVALID_DECK_PLAYER2);
                        view.printError(request.getErrorType());
                    }
                }else{
                    request.setErrorType(ErrorType.WRONG_COMMAND);
                    view.printError(request.getErrorType());
                }

            }


        }
    }
}
