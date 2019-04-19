import java.util.ArrayList;
import java.util.List;

public class ControllerGraveYard {
    private static final ControllerGraveYard ourInstance = new ControllerGraveYard();
    private static final View view = View.getInstance();

    private ControllerGraveYard(){
    }

    public static ControllerGraveYard getInstance(){
        return ourInstance;
    }

    public void showInfoOfCards(List<Card> cards){
        view.showInfoOfCards(cards);
    }
}
