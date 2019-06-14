import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;

public class SpellImage {
    private int targetDuration = 1000;
    private ImageView targetAnimation = new ImageView();
    private ImageView icon = new ImageView();
    private String id;

    public SpellImage(String id) {
        try {
            icon.setImage(new Image(new FileInputStream("/src/spells/" + getSpellName() + "/icon")));
            targetAnimation.setImage(new Image(new FileInputStream("/src/spells/" + getSpellName() + "/target")));
            targetAnimation.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getSpellName() {
        return id.split("_")[1];
    }

    public void showSpellUse(int row, int column) {

    }
}
