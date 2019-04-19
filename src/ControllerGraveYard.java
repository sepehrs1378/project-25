import java.util.List;

public class ControllerGraveYard {
    private static final ControllerGraveYard ourInstance = ControllerGraveYard.getInstance();
    private static final View view = View.getInstance();

    private ControllerGraveYard(){
    }

    public static ControllerGraveYard getInstance(){
        return ourInstance;
    }

    public void showListOfCards(List list){
        view.printContentsOfAList(list);
    }
}
