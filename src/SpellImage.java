import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;

public class SpellImage {
    private final int effectDuration = 2000;
    private AnchorPane root;
    private String id;
    private ImageView spellEffect = new ImageView();

    public SpellImage(String id, int row, int column, AnchorPane root) {
        ControllerBattleCommands controller = ControllerBattleCommands.getOurInstance();
        this.id = id;
        this.root = root;
        try {
            spellEffect.setImage(new Image(new FileInputStream
                    ("./src/ApProjectResources/" + getSpellName() + "/effect")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addToRoot();
        relocate(controller.getCellLayoutX(column), controller.getCellLayoutY(row));
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now - lastTime > effectDuration * 1000000) {
                    removeFromRoot();
                    this.stop();
                }
            }
        };
        animationTimer.start();
    }

    private String getSpellName() {
        return id.split("_")[1];
    }

    private void addToRoot() {
        root.getChildren().add(spellEffect);
    }

    public void relocate(double x, double y) {
        spellEffect.setTranslateX(x);
        spellEffect.setTranslateY(y);
    }

    public void removeFromRoot() {
        root.getChildren().remove(spellEffect);
        ControllerBattleCommands.getOurInstance().getSpellImageList().remove(this);
    }
}