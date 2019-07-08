import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;


public class CollectableImage {
    private static final int COLLECTABLE_VIEW_SIZE = 50;
    private int row;
    private int column;
    private AnchorPane root;
    private ImageView collectableView;

    public CollectableImage(AnchorPane root) {
        this.root = root;
        try {
            collectableView = new ImageView(new Image(new FileInputStream
                    ("src/ApProjectResources/collectables/collectable")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addToRoot();
    }

    public void setInCell(int row, int column) {
        this.row = row;
        this.column = column;
        double x = ControllerBattleCommands.getOurInstance().getCellLayoutX(column)
                + GraphicConstants.CELL_WIDTH / 2 - COLLECTABLE_VIEW_SIZE / 2;
        double y = ControllerBattleCommands.getOurInstance().getCellLayoutY(row) - COLLECTABLE_VIEW_SIZE / 2;
        relocate(x, y);
    }

    private void relocate(double x, double y) {
        collectableView.setTranslateX(x);
        collectableView.setTranslateY(y);
    }

    private void addToRoot() {
        root.getChildren().add(collectableView);
    }

    public void removeFromRoot() {
        root.getChildren().remove(collectableView);
    }
}
