import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
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
    //todo دیوریشن ها و سایز ها رو بهتره در نهایت برای هر گیف از فایل بخونیم
    private String mouseEnteredStyle = "-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,1), 10, 0, 0, 0);";
    private String mouseExitedStyle = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 10, 0, 0, 0);";
    private String mouseClickedStyle = "-fx-effect: dropshadow(three-pass-box, rgb(255,254,0), 10, 0, 0, 0);";
    private long attackDuration = 3000;
    private long deathDuration = 3000;
    private long spellDuration = 3000;
    private long runDuration = 2000;
    private int unitViewSize = 150;
    private ImageView unitView = new ImageView();
    private Label apNumber = new Label("0");//todo relocate and reset it
    private Label hpNumber = new Label("0");//todo
    private String id;
    private UnitStatus unitStatus;

    {
        apNumber.setStyle("-fx-text-fill: #5a5a5a;-fx-background-color: #fff700;-fx-background-radius: 100;-fx-font-size: 18");
        hpNumber.setStyle("-fx-text-fill: #5a5a5a;-fx-background-color: #ff0003;-fx-background-radius: 100;-fx-font-size: 18");
        unitView.setFitWidth(unitViewSize);
        unitView.setFitHeight(unitViewSize);
    }

    public UnitImage(String id, AnchorPane root) {
        this.id = id;
        unitStatus = UnitStatus.stand;
        try {
            unitView.setImage(new Image(new FileInputStream
                    ("./src/ApProjectResources/units/" + getUnitName()
                            + "/" + unitStatus.toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        unitView.setOnMouseClicked(event -> {

            unitView.setStyle(mouseClickedStyle);
        });
        unitView.setOnMouseEntered(event -> {
            unitView.setStyle(mouseEnteredStyle);
        });
        unitView.setOnMouseExited(event -> {
            if (!unitView.getStyle().equals(mouseClickedStyle))
                unitView.setStyle(mouseExitedStyle);
        });
        resetStatsPositions();
        addToRoot(root);
        //todo
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

    public void showRun(int destinationRow, int destinationColumn, AnchorPane root) {
        setUnitStatus(UnitStatus.run);

        double startX = unitView.getTranslateX() + unitView.getFitWidth() / 2;
        double startY = unitView.getTranslateY() + unitView.getFitHeight() / 2;
        double endX = ControllerBattleCommands.getOurInstance().getCellLayoutX(destinationColumn)
                + GraphicConstants.CELL_WIDTH / 2.0;
        double endY = ControllerBattleCommands.getOurInstance().getCellLayoutY(destinationRow)
                + GraphicConstants.CELL_HEIGHT / 2.0;

        Path path = new Path(new MoveTo(startX, startY), new LineTo(endX, endY));
        path.setVisible(false);
        root.getChildren().add(path);

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
                    root.getChildren().remove(path);
                    System.out.println(unitView.getTranslateX() + "    " + unitView.getTranslateY());
                    this.stop();
                } else resetStatsPositions();
            }
        };
        animationTimer.start();
    }

    public void showAttack() {
        setUnitStatus(UnitStatus.attack);
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
                    setUnitStatus(UnitStatus.stand);
                    this.stop();
                }
            }
        };
        animationTimer.start();
    }

    private void addToRoot(AnchorPane root) {
        root.getChildren().add(unitView);
        root.getChildren().add(apNumber);
        root.getChildren().add(hpNumber);
    }

    private void resetStatsPositions() {
        hpNumber.setTranslateX(unitView.getTranslateX() + unitViewSize * 0.33);
        hpNumber.setTranslateY(unitView.getTranslateY() + unitViewSize);
        apNumber.setTranslateX(unitView.getTranslateX() + unitViewSize * 0.66);
        apNumber.setTranslateY(unitView.getTranslateY() + unitViewSize);
    }

    public void changeApNumber(int apNumber) {
        this.apNumber.setText(" " + apNumber + " ");
    }

    public void changeHpNumber(int hpNumber) {
        this.hpNumber.setText(" " + hpNumber + " ");
    }

    public void setInCell(int row, int column) {
        relocate(ControllerBattleCommands.getOurInstance().getCellLayoutX(column)
                , ControllerBattleCommands.getOurInstance().getCellLayoutY(row));
    }

    public void relocate(double x, double y) {
        unitView.setTranslateX(x);
        unitView.setTranslateY(y);
        resetStatsPositions();
    }

    public void setInHand(int ringNumber) {
        //todo
    }
}