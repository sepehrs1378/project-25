import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;


public class HandImage {
    private static ControllerBattleCommands controllerBattleCommands
            = ControllerBattleCommands.getOurInstance();
    private final String UNIT = "unit";
    private final String SPELL = "spell";
    private final int UNIT_VIEW_SIZE = 150;
    private final int SPELL_VIEW_SIZE = 80;
    private final String MOUSE_ENTERED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,1), 10, 0, 0, 0);";
    private final String MOUSE_EXITED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 10, 0, 0, 0);";
    private final String SELECTED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgb(255,255,0), 10, 0, 0, 0);";
    private int number;
    private String id = "";
    private String unitOrSpell = "";
    private AnchorPane root;
    private ImageView ringView;
    private ImageView cardView = new ImageView();
    private Label manaLabel = new Label("0");
    private Label hpLabel = new Label("0");
    private Label apLabel = new Label("0");

    {
        apLabel.setStyle("-fx-text-fill: #5a5a5a;-fx-background-color: #fff700;-fx-background-radius: 100;-fx-font-size: 18");
        hpLabel.setStyle("-fx-text-fill: #5a5a5a;-fx-background-color: #ff0003;-fx-background-radius: 100;-fx-font-size: 18");
        manaLabel.setStyle("-fx-text-fill: #5a5a5a;-fx-background-color: #4789ff;-fx-background-radius: 100;-fx-font-size: 23");
    }

    public HandImage(int number, AnchorPane root) {
        this.ringView = ControllerBattleCommands.getOurInstance().getHandRings().get(number);
        this.number = number;
        this.root = root;
        cardView.setOnMouseClicked(event -> {

        });
        cardView.setOnMouseEntered(event -> {
            if (!cardView.getStyle().equals(SELECTED_STYLE))
                cardView.setStyle(MOUSE_ENTERED_STYLE);
        });
        cardView.setOnMouseExited(event -> {
            if (!cardView.getStyle().equals(SELECTED_STYLE))
                cardView.setStyle(MOUSE_EXITED_STYLE);
        });
        addToRoot();
    }

    public void setCardImage(String id) {
        this.id = id;
        Card card = controllerBattleCommands.getLoggedInPlayer().getHand().getCardById(id);
        try {
            if (card instanceof Unit) {
                cardView.setImage(new Image(new FileInputStream
                        ("./src/ApProjectResources/units/" + getCardName() + "/stand")));
                unitOrSpell = UNIT;
                cardView.setFitWidth(UNIT_VIEW_SIZE);
                cardView.setFitHeight(UNIT_VIEW_SIZE);
                hpLabel.setText(((Unit) card).getHp() + "");
                apLabel.setText(((Unit) card).getAp() + "");
            }
            if (card instanceof Spell) {
                cardView.setImage(new Image(new FileInputStream
                        ("./src/ApProjectResources/spells/" + getCardName() + "/icon")));
                cardView.setFitWidth(SPELL_VIEW_SIZE);
                cardView.setFitHeight(SPELL_VIEW_SIZE);
                unitOrSpell = SPELL;
                apLabel.setVisible(false);
                hpLabel.setVisible(false);
            }
            relocateCardToHand();
            manaLabel.setText(card.getMana() + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCardName() {
        return id.split("_")[1];
    }

    private void addToRoot() {
        root.getChildren().add(cardView);
        root.getChildren().add(manaLabel);
        root.getChildren().add(hpLabel);
        root.getChildren().add(apLabel);
    }

    private void resetStatsPositions() {
        if (unitOrSpell.equals(UNIT)) {
            hpLabel.setTranslateX(cardView.getTranslateX() + UNIT_VIEW_SIZE * 0.33);
            hpLabel.setTranslateY(cardView.getTranslateY() + UNIT_VIEW_SIZE);
            apLabel.setTranslateX(cardView.getTranslateX() + UNIT_VIEW_SIZE * 0.66);
            apLabel.setTranslateY(cardView.getTranslateY() + UNIT_VIEW_SIZE);
            manaLabel.setTranslateX(cardView.getTranslateX() + UNIT_VIEW_SIZE * 0.50);
            manaLabel.setTranslateY(cardView.getTranslateY() + UNIT_VIEW_SIZE * 1.20);
        }
        if (unitOrSpell.equals(SPELL)) {
            manaLabel.setTranslateX(cardView.getTranslateX() + SPELL_VIEW_SIZE * 0.50);
            manaLabel.setTranslateY(cardView.getTranslateY() + SPELL_VIEW_SIZE * 1.20);
        }
    }

    private void relocateCardToHand() {
        double x = 0;
        double y = 0;
        if (unitOrSpell.equals(UNIT)) {
            x = ringView.getLayoutX() + ringView.getFitWidth() / 2 - UNIT_VIEW_SIZE / 2;
            y = ringView.getLayoutY() - UNIT_VIEW_SIZE / 2;
        }
        if (unitOrSpell.equals(SPELL)) {
            x = ringView.getLayoutX() + ringView.getFitWidth() / 2 - SPELL_VIEW_SIZE / 2;
            y = ringView.getLayoutY() + ringView.getFitHeight() / 2 - SPELL_VIEW_SIZE / 2;
        }
        cardView.setTranslateX(x);
        cardView.setTranslateY(y);
        resetStatsPositions();
    }

    public String getId() {
        return id;
    }
}
