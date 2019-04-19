import java.util.ArrayList;
import java.util.List;

public class ControllerGraveYard {
    private static final ControllerGraveYard ourInstance = new ControllerGraveYard();
    private static final View view = View.getInstance();

    private ControllerGraveYard(){
    }
    private static final View view=View.getInstance();
    private static final ControllerGraveYard ourInstance = new ControllerGraveYard();

    public static ControllerGraveYard getInstance() {
        return ourInstance;
    }

    private ControllerGraveYard() {
    }

    public void main() {
        Request request = new Request();
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case SHOW:
                    break;
                case EXIT:
                    didExit = true;
                    break;
                default:
                    System.out.println("!!!!!! bad input in ControllerGraveYard.main");
                    System.exit(-1);
            }
        }
    }

    public void show(Request request){
        if(!request.getCommand().matches("^show cards$") &&
                !request.getCommand().matches("^show info .+$")){
            request.setErrorType(ErrorType.WRONG_COMMAND);
            view.printError(request.getErrorType());
            return;
        }
        if(request.getCommand().matches("^show cards$")){
            //todo
        }
        if(request.getCommand().matches("^show info .+$")){
            //todo
        }
    public void showInfoOfCards(List<Card> cards){
        view.showInfoOfCards(cards);
    }
}