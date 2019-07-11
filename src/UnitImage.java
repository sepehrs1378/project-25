import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UnitImage {
    private static ClientDB clientDB = ClientDB.getInstance();
    private final String MOUSE_ENTERED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,1), 10, 0, 0, 0);";
    private final String MOUSE_EXITED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 10, 0, 0, 0);";
    private final String SELECTED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgb(255,255,0), 10, 0, 0, 0);";
    private final int UNIT_VIEW_SIZE = 150;
    private final int UNIT_EFFECT_SIZE = 80;
    private AnchorPane root;
    private Long attackDuration = 2000L;
    private Long deathDuration = 1200L;
    private Long spellDuration = 3000L;
    private Long runDuration = 1000L;
    private Long spawnDuration = 1500L;
    private int row = 0;
    private int column = 0;
    private ImageView unitView = new ImageView();
    private List<BuffImage> buffImageList = new ArrayList<>();
    private Label apNumber = new Label("0");
    private Label hpNumber = new Label("0");
    private String id;
    private UnitStatus unitStatus;

    {
        apNumber.setStyle("-fx-text-fill: #5a5a5a;-fx-background-color: #fff700;-fx-background-radius: 100;-fx-font-size: 18");
        hpNumber.setStyle("-fx-text-fill: #5a5a5a;-fx-background-color: #ff0003;-fx-background-radius: 100;-fx-font-size: 18");
        unitView.setFitWidth(UNIT_VIEW_SIZE);
        unitView.setFitHeight(UNIT_VIEW_SIZE);
    }

    public UnitImage(String id, AnchorPane root) {
        this.root = root;
        this.id = id;
        unitStatus = UnitStatus.stand;
        loadUnitImage();
        unitView.setOnMouseClicked(event -> {
            if (!ControllerBattleCommands.getOurInstance().canPlayerTouchScreen())
                return;
            ControllerBattleCommands.getOurInstance().handleUnitClicked(id);
        });
        unitView.setOnMouseEntered(event -> {
            if (!unitView.getStyle().equals(SELECTED_STYLE))
                unitView.setStyle(MOUSE_ENTERED_STYLE);
        });
        unitView.setOnMouseExited(event -> {
            if (!unitView.getStyle().equals(SELECTED_STYLE))
                unitView.setStyle(MOUSE_EXITED_STYLE);
        });
        resetStatsPositions();
        addToRoot();
        //todo
    }

    public void setId(String id) {
        this.id = id;
        loadUnitImage();
    }

    private void loadUnitImage() {
        try {
            unitView.setImage(new Image(new FileInputStream
                    ("./src/ApProjectResources/units/" + getUnitName()
                            + "/" + unitStatus.toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
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

    public void setUnitStyleAsSelected() {
        unitView.setStyle(SELECTED_STYLE);
    }

    public void setStyleAsNotSelected() {
        unitView.setStyle(null);
    }

    public String getUnitName() {
        return id.split("_")[1];
    }

    public void showRun(int destinationRow, int destinationColumn) {
        ControllerBattleCommands.getOurInstance().setScreenLocked(true);
        Main.playMedia("src/music/step.mp3"
                , Duration.millis(runDuration.doubleValue())
                , Integer.MAX_VALUE, false, 1);

        setUnitStatus(UnitStatus.run);
        double startX = unitView.getTranslateX() + unitView.getFitWidth() / 2;
        double startY = unitView.getTranslateY() + unitView.getFitHeight() / 2;
        double endX = ControllerBattleCommands.getOurInstance().getCellLayoutX(destinationColumn)
                + GraphicConstants.CELL_WIDTH / 2.0;
        double endY = ControllerBattleCommands.getOurInstance().getCellLayoutY(destinationRow);
        changeFacing(this.column, destinationColumn);
        this.row = destinationRow;
        this.column = destinationColumn;

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
            private long duration = runDuration * 1000000;

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now - lastTime > duration) {
                    setUnitStatus(UnitStatus.stand);
                    root.getChildren().remove(path);
                    ControllerBattleCommands.getOurInstance().setScreenLocked(false);
                    this.stop();
                } else resetStatsPositions();
            }
        };
        animationTimer.start();
    }

    public void showAttack(int targetColumn) {
        ControllerBattleCommands.getOurInstance().setScreenLocked(true);
        Main.playMedia("src/ApProjectResources/units/" + getUnitName() + "/attack.m4a"
                , Duration.INDEFINITE, 1, false, 1);

        setUnitStatus(UnitStatus.attack);
        changeFacing(this.column, targetColumn);
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;
            private long duration = attackDuration * 1000000;

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now - lastTime > duration) {
                    setUnitStatus(UnitStatus.stand);
                    ControllerBattleCommands.getOurInstance().setScreenLocked(false);
                    this.stop();
                }
            }
        };
        animationTimer.start();
    }

    public void showSpawn() {
        ControllerBattleCommands.getOurInstance().setScreenLocked(true);
        Main.playMedia("src/music/unitSpawn.mp3"
                , Duration.millis(spawnDuration.doubleValue())
                , Integer.MAX_VALUE, false, 1);

        ImageView effectView = addEffectToUnit(UnitEffectType.spawnEffect);

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;
            private long duration = spawnDuration * 1000000;

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now - lastTime > duration) {
                    root.getChildren().remove(effectView);
                    ControllerBattleCommands.getOurInstance().setScreenLocked(false);
                    this.stop();
                } else {
                    unitView.setStyle("-fx-opacity: " + (now - lastTime - 0.0) / duration);
                }
            }
        };
        animationTimer.start();

    }

    public void showDeath() {
        ControllerBattleCommands.getOurInstance().setScreenLocked(true);
        Main.playMedia("src/ApProjectResources/units/" + getUnitName() + "/death.m4a"
                , Duration.INDEFINITE, 1, false, 1);

        setUnitStatus(UnitStatus.death);
        ImageView effectView = addEffectToUnit(UnitEffectType.bloodDrop);

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;
            private long duration = deathDuration * 1000000;

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now - lastTime > duration) {
                    setUnitStatus(UnitStatus.stand);
                    root.getChildren().remove(effectView);
                    ControllerBattleCommands.getOurInstance().setScreenLocked(false);
                    removeFromRoot();
                    this.stop();
                } else {
                    unitView.setStyle("-fx-opacity: " + (1 - (now - lastTime - 0.0) / duration));
                }
            }
        };
        animationTimer.start();
    }

    public void showSelect() {
        Main.playMedia("src/ApProjectResources/units/" + getUnitName() + "/selection.mp3"
                , Duration.INDEFINITE, 1, false, 1);
    }

    public void showSpell() {
        ControllerBattleCommands.getOurInstance().setScreenLocked(true);
        setUnitStatus(UnitStatus.spell);
        //todo show spell effects
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;
            private long duration = spellDuration * 1000000;

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now - lastTime > duration) {
                    setUnitStatus(UnitStatus.stand);
                    ControllerBattleCommands.getOurInstance().setScreenLocked(false);
                    this.stop();
                }
            }
        };
        animationTimer.start();
    }

    private void changeFacing(int currentColumn, int toColumn) {
        if (currentColumn < toColumn)
            unitView.setScaleX(1);
        else unitView.setScaleX(-1);
    }

    private void addToRoot() {
        root.getChildren().add(unitView);
        root.getChildren().add(apNumber);
        root.getChildren().add(hpNumber);
    }

    private void resetStatsPositions() {
        hpNumber.setTranslateX(unitView.getTranslateX() + UNIT_VIEW_SIZE * 0.15);
        hpNumber.setTranslateY(unitView.getTranslateY() + UNIT_VIEW_SIZE * 0.15);
        apNumber.setTranslateX(unitView.getTranslateX() + UNIT_VIEW_SIZE * 0.25);
        apNumber.setTranslateY(unitView.getTranslateY() + UNIT_VIEW_SIZE * 0.4);
        for (BuffImage buffImage : buffImageList) {
            buffImage.relocate(unitView.getTranslateX(), unitView.getTranslateY());
        }
    }

    public void setApNumber(int apNumber) {
        this.apNumber.setText(" " + apNumber + " ");
    }

    public void setHpNumber(int hpNumber) {
        this.hpNumber.setText(" " + hpNumber + " ");
    }

    public void setInCell(int row, int column) {
        this.row = row;
        this.column = column;
        double x = ControllerBattleCommands.getOurInstance().getCellLayoutX(column)
                + GraphicConstants.CELL_WIDTH / 2 - UNIT_VIEW_SIZE / 2;
        double y = ControllerBattleCommands.getOurInstance().getCellLayoutY(row) - UNIT_VIEW_SIZE / 2;
        relocate(x, y);
    }

    private void relocate(double x, double y) {
        unitView.setTranslateX(x);
        unitView.setTranslateY(y);
        resetStatsPositions();
    }

    public void addBuffImage(BuffType buffType) {
        for (BuffImage buffImage : buffImageList) {
            if (buffImage.getBuffType().equals(buffType))
                return;
        }
        BuffImage buffImage = new BuffImage(buffType, root);
        buffImageList.add(buffImage);
        buffImage.relocate(unitView.getTranslateX() + UNIT_VIEW_SIZE / 2 - BuffImage.BUFF_VIEW_SIZE / 2
                , unitView.getTranslateY() + UNIT_VIEW_SIZE / 2 - BuffImage.BUFF_VIEW_SIZE / 2);
    }

    public void clearBuffImageList() {
        root.getChildren().removeAll(buffImageList);
        buffImageList.clear();
    }

    public ImageView getUnitView() {
        return unitView;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public ImageView addEffectToUnit(UnitEffectType effectType) {
        ImageView effectView = null;
        try {
            effectView = new ImageView(new Image(new FileInputStream
                    ("./src/ApProjectResources/units/unitEffects/" + effectType.toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.getChildren().add(effectView);
        effectView.setTranslateX(unitView.getTranslateX() - UNIT_VIEW_SIZE / 2 + UNIT_EFFECT_SIZE / 2);
        effectView.setTranslateY(unitView.getTranslateY() - UNIT_VIEW_SIZE / 2 + UNIT_EFFECT_SIZE / 2);
        return effectView;
    }

    private void removeFromRoot() {
        List<UnitImage> unitImageList = ControllerBattleCommands.getOurInstance().getUnitImageList();
        root.getChildren().remove(unitView);
        root.getChildren().remove(apNumber);
        root.getChildren().remove(hpNumber);
        for (BuffImage buffImage : buffImageList) {
            root.getChildren().remove(buffImage);
        }
        unitImageList.remove(this);
    }
}