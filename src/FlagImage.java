import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.imageio.stream.FileImageInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FlagImage {
    private static final int FLAG_VIEW_SIZE = 50;
    private int row = 0;
    private int column = 0;
    private AnchorPane root;
    private ImageView flagView;
    private String id;

    public FlagImage(AnchorPane root) {
        this.root = root;
        try {
            flagView = new ImageView(new Image(new FileInputStream("./src/ApProjectResources/flag/flag")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        addToRoot();
    }

    public void setInCell(int row, int column) {
        this.row = row;
        this.column = column;
        double x = ControllerBattleCommands.getOurInstance().getCellLayoutX(column)
                + GraphicConstants.CELL_WIDTH / 2 - FLAG_VIEW_SIZE / 2;
        double y = ControllerBattleCommands.getOurInstance().getCellLayoutY(row) - FLAG_VIEW_SIZE / 2;
        relocate(x, y);
    }

    private void relocate(double x, double y) {
        flagView.setTranslateX(x);
        flagView.setTranslateY(y);
    }

    private void addToRoot() {
        root.getChildren().add(flagView);
    }

    public void removeFromRoot() {
        root.getChildren().remove(flagView);
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setFlagView(ImageView flagView) {
        this.flagView = flagView;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public ImageView getFlagView() {
        return flagView;
    }

    public String getId() {
        return id;
    }
}
