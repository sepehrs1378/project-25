import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
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
import java.util.ArrayList;
import java.util.List;

public class UnitImage {
    //todo دیوریشن ها و سایز ها رو بهتره در نهایت برای هر گیف از فایل بخونیم
    private final String MOUSE_ENTERED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,1), 10, 0, 0, 0);";
    private final String MOUSE_EXITED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 10, 0, 0, 0);";
    private final String SELECTED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgb(255,255,0), 10, 0, 0, 0);";
    private AnchorPane root;
    private long attackDuration = 2000;
    private long deathDuration = 3000;
    private long spellDuration = 3000;
    private long runDuration = 1000;
    private int unitViewSize = 150;
    private int row = 0;
    private int column = 0;
    private ImageView unitView = new ImageView();
    private List<BuffImage> buffImageList = new ArrayList<>();
    private Label apNumber = new Label("0");//todo relocateCardView and reset it
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
        this.root = root;
        this.id = id;
        unitStatus = UnitStatus.stand;
        loadUnitImage();
        unitView.setOnMouseClicked(event -> {
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

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now - lastTime > runDuration * 1000000) {
                    setUnitStatus(UnitStatus.stand);
                    root.getChildren().remove(path);
                    this.stop();
                } else resetStatsPositions();
            }
        };
        animationTimer.start();
    }

    public void showAttack(int targetColumn) {
        setUnitStatus(UnitStatus.attack);
        changeFacing(this.column, targetColumn);
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
        hpNumber.setTranslateX(unitView.getTranslateX() + unitViewSize * 0.33);
        hpNumber.setTranslateY(unitView.getTranslateY() + unitViewSize);
        apNumber.setTranslateX(unitView.getTranslateX() + unitViewSize * 0.66);
        apNumber.setTranslateY(unitView.getTranslateY() + unitViewSize);
        for (BuffImage buffImage : buffImageList) {
            buffImage.relocate(unitView.getLayoutX(), unitView.getLayoutY());
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
                + GraphicConstants.CELL_WIDTH / 2 - unitViewSize / 2;
        double y = ControllerBattleCommands.getOurInstance().getCellLayoutY(row) - unitViewSize / 2;
        relocate(x, y);
    }

    public void relocate(double x, double y) {
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
        buffImage.relocate(unitView.getLayoutX(), unitView.getLayoutY());
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
}