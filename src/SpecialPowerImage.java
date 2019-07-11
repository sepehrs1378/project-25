import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SpecialPowerImage {
    private final int SPECIAL_POWER_SIZE = 80;
    private final String MOUSE_ENTERED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,1), 10, 0, 0, 0);";
    private final String MOUSE_EXITED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 10, 0, 0, 0);";
    private final String SELECTED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgb(255,255,0), 10, 0, 0, 0);";
    private ClientDB clientDB = ClientDB.getInstance();
    private ControllerBattleCommands controllerBattleCommands = ControllerBattleCommands.getOurInstance();
    private AnchorPane root;
    private Label manaCost = new Label("0");
    private Label cooldown = new Label("0");
    private ImageView specialPowerView;

    {
        manaCost.setStyle("-fx-text-fill: #5a5a5a;-fx-background-color: #0057ff;-fx-background-radius: 100;-fx-font-size: 23");
        cooldown.setStyle("-fx-text-fill: #5a5a5a;-fx-background-color: #befffa;-fx-background-radius: 100;-fx-font-size: 23");
    }

    public SpecialPowerImage(AnchorPane root) {
        this.root = root;
        specialPowerView = controllerBattleCommands.getSpecialPowerView();
        Unit hero = clientDB.getLoggedInAccount().getMainDeck().getHero();
        if (hero.getMainSpecialPower() == null) {
            return;
        }
        try {
            specialPowerView.setImage(new Image(new FileInputStream
                    ("src/ApProjectResources/units/" + hero.getName() + "/special_power.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        specialPowerView.setOnMouseClicked(event -> {
            if (!controllerBattleCommands.canPlayerTouchScreen())
                return;
            if (controllerBattleCommands.getClickedImageView() != null
                    && controllerBattleCommands.getClickedImageView().equals(specialPowerView)) {
                controllerBattleCommands.setClickedImageView(null);
                specialPowerView.setStyle(null);
            } else {
                controllerBattleCommands.setClickedImageView(specialPowerView);
                specialPowerView.setStyle(SELECTED_STYLE);
            }
            controllerBattleCommands.updatePane();
        });
        specialPowerView.setOnMouseEntered(event -> {
            if (!specialPowerView.getStyle().equals(SELECTED_STYLE)) {
                specialPowerView.setStyle(MOUSE_ENTERED_STYLE);
            }
        });
        specialPowerView.setOnMouseExited(event -> {
            if (!specialPowerView.getStyle().equals(SELECTED_STYLE)) {
                specialPowerView.setStyle(MOUSE_EXITED_STYLE);
            }
        });
        setStatsPositions();
        addToRoot();
    }

    public void setManaCost(int manaCost) {
        this.manaCost.setText(manaCost + "");
    }

    public void setCooldown(int cooldown) {
        this.cooldown.setText(cooldown + "");
    }

    private void setStatsPositions() {
        manaCost.setTranslateX(specialPowerView.getLayoutX() + SPECIAL_POWER_SIZE * 0.4);
        manaCost.setTranslateY(specialPowerView.getLayoutY() - SPECIAL_POWER_SIZE * 0.1);
        cooldown.setTranslateX(specialPowerView.getLayoutX() + SPECIAL_POWER_SIZE * 1.1);
        cooldown.setTranslateY(specialPowerView.getLayoutY() + SPECIAL_POWER_SIZE * 0.4);
    }

    private void addToRoot() {
        root.getChildren().add(manaCost);
        root.getChildren().add(cooldown);
    }
}