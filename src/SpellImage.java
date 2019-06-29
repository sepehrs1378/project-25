import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;

public class SpellImage {
    private AnchorPane root;
    private String id;
    private ImageView spellView = new ImageView();
    private ImageView spellEffect = new ImageView();

    public SpellImage(String id, AnchorPane root) {
        this.root = root;
        try {
            spellView.setImage(new Image(new FileInputStream
                    ("./src/ApProjectResources/" + getSpellName() + "/icon")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addToRoot();
    }

    public String getSpellName() {
        return id.split("_")[1];
    }

    public void addToRoot() {
        root.getChildren().add(spellView);
    }
}