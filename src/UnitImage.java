import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;

public class UnitImage {
    //todo دیوریشن ها رو بهتره در نهایت برای هر گیف از فایل بخونیم
    private long attackDuration = 1500;
    private long deathDuration = 1000;
    private long spellDuration = 1500;
    private long runDuration = 1000;
    private ImageView unitView = new ImageView();
    private String id;
    private UnitStatus unitStatus;

    public UnitImage(String id) {
        unitStatus = UnitStatus.stand;
        try {
            unitView.setImage(new Image(new FileInputStream
                    ("./src/ApProjectResources/units/" + getUnitName()
                            + "/" + unitStatus.toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //todo
    }

    public UnitStatus getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(UnitStatus unitStatus) {
        this.unitStatus = unitStatus;
        try {
            unitView.setImage(new Image(new FileInputStream
                    ("./src/ApProjectResources/units/"
                            + getUnitName() + "/" + unitStatus.toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUnitName() {
        return id.split("_")[1];
    }

    public void showRun(int row, int column, AnchorPane root) {
        setUnitStatus(UnitStatus.run);

        double startX = unitView.getX() + unitView.getFitWidth() / 2;
        double startY = unitView.getY() + unitView.getFitHeight() / 2;
        double endX = row * GraphicConstants.CELL_HEIGHT
                + GraphicConstants.BATTLE_GROUND_START_Y + GraphicConstants.CELL_HEIGHT / 2;
        double endY = column * GraphicConstants.CELL_WIDTH
                + GraphicConstants.BATTLE_GROUND_START_X + GraphicConstants.CELL_WIDTH / 2;
        Path path = new Path(new MoveTo(startX, startY), new LineTo(endX, endY));
        path.setVisible(true);//todo remove it when not needed

        PathTransition pathTransition = new PathTransition
                (Duration.millis(runDuration), path, unitView);
        pathTransition.setAutoReverse(true);
        pathTransition.setCycleCount(1);
        pathTransition.play();

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now - lastTime > runDuration * 1000000) {
                    setUnitStatus(UnitStatus.stand);
                    this.stop();
                }
            }
        };
        animationTimer.start();
    }

    public void showAttack() {
        setUnitStatus(UnitStatus.stand);
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now - lastTime > attackDuration * 1000000) {
                    setUnitStatus(UnitStatus.stand);
                    this.stop();
                }
            }
        };
        animationTimer.start();
    }

    public void showDeath() {
        setUnitStatus(UnitStatus.death);
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now - lastTime > deathDuration * 1000000) {
                    setUnitStatus(UnitStatus.stand);
                    this.stop();
                }
            }
        };
        animationTimer.start();
    }

    public void showSpell() {
        setUnitStatus(UnitStatus.spell);
        //todo show spell effects
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now - lastTime > spellDuration * 1000000) {
                    setUnitStatus(UnitStatus.spell);
                    this.stop();
                }
            }
        };
        animationTimer.start();
    }
}