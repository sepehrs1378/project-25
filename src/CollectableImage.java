import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class CollectableImage {
    private AnchorPane root;
    private ImageView collectableView;

    public CollectableImage(AnchorPane root) {
        this.root = root;
    }

    private void addToRoot() {
        root.getChildren().add(collectableView);
    }
}
