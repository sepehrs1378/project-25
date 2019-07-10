import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoImage {
    private Label PlayerName;
    private ImageView heroView;
    private List<ImageView> manaCells = new ArrayList<>();
    private int playerNumber = 1;

    public PlayerInfoImage(int playerNumber, AnchorPane root) {
        this.playerNumber = playerNumber;

    }

    private void addToRoot() {

    }
}
