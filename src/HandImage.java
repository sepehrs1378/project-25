import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class HandImage {
    private int number;
    private AnchorPane root;
    private UnitImage unitImage;
    private ImageView ringView;
    private Label manaLabel = new Label("0");

    public HandImage(int number, AnchorPane root) {
        this.ringView = ControllerBattleCommands.getOurInstance().getHandRings().get(number);
        this.number = number;
        this.root = root;
        addToRoot();
    }

    public void setUnitImage(String id) {
        unitImage = new UnitImage(id, root);
        unitImage.setInHand(number);
    }

    public UnitImage getUnitImage() {
        return unitImage;
    }

    public void addToRoot() {
//        root.getChildren().add(unitImage.getUnitView());
//        root.getChildren().add(manaLabel);
    }
}
