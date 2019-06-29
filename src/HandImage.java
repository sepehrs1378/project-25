import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class HandImage {
    private static ControllerBattleCommands controllerBattleCommands
            = ControllerBattleCommands.getOurInstance();
    private int number;
    private String id = null;
    private AnchorPane root;
    private ImageView ringView;
    private ImageView cardView;
    private Label manaLabel = new Label("0");

    public HandImage(int number, AnchorPane root) {
        this.ringView = ControllerBattleCommands.getOurInstance().getHandRings().get(number);
        this.number = number;
        this.root = root;
        addToRoot();
    }

    public void setCardImage(String id) {
        this.id = id;
        Card card = controllerBattleCommands.getLoggedInPlayer().getHand().getCardById(id);
//        if (card instanceof Unit)
//            setUnitImage(id);
//        if (card instanceof Spell)
//            setSpellImage(id);
    }

//    public void setUnitImage(String id) {
//        spellImage = null;
//        unitImage = new UnitImage(id, root);
//        unitImage.setInHand(number);
//
//    }
//
//    public void setSpellImage(String id) {
//        unitImage = null;
//
//        todo
//    }

    public void addToRoot() {
        //todo IMPORTANT
    }

    public boolean isEmpty() {
        return id == null;
    }

    public String getId() {
        return id;
    }
}
