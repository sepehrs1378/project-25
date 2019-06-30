import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;

public class SpellImage {
    private final long effectDuration = 2500;
    private final int EFFECT_VIEW_SIZE = 250;
    private AnchorPane root;
    private String id;
    private ImageView spellEffect = new ImageView();

    public SpellImage(String id, int row, int column, AnchorPane root) {
        ControllerBattleCommands controller = ControllerBattleCommands.getOurInstance();
        this.id = id;
        this.root = root;
        try {
            spellEffect.setImage(new Image(new FileInputStream
                    ("./src/ApProjectResources/spells/" + getSpellName() + "/effect")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        spellEffect.setFitWidth(EFFECT_VIEW_SIZE);
        spellEffect.setFitHeight(EFFECT_VIEW_SIZE);
        addToRoot();
        relocate(controller.getCellLayoutX(column) + GraphicConstants.CELL_WIDTH / 2 - EFFECT_VIEW_SIZE / 2
                , controller.getCellLayoutY(row) - EFFECT_VIEW_SIZE / 2);
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