import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;


public class HandImage {
    private static ClientDB clientDB = ClientDB.getInstance();
    private final String UNIT = "unit";
    private final String SPELL = "spell";
    private final int UNIT_VIEW_SIZE = 150;
    private final int SPELL_VIEW_SIZE = 80;
    private final String MOUSE_ENTERED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,1), 10, 0, 0, 0);";
    private final String MOUSE_EXITED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 10, 0, 0, 0);";
    private final String SELECTED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgb(255,255,0), 10, 0, 0, 0);";
    private String id = "";
    private String unitOrSpell = "";
    private AnchorPane root;
    private ImageView ringView;
    private ImageView cardView = new ImageView();
    private Label manaLabel = new Label("0");
    private Label hpLabel = new Label("0");
    private Label apLabel = new Label("0");
    private Label cardName = new Label("0");

    {
        apLabel.setStyle("-fx-text-fill: #5a5a5a;-fx-background-color: #fff700;-fx-background-radius: 100;-fx-font-size: 18");
        hpLabel.setStyle("-fx-text-fill: #5a5a5a;-fx-background-color: #ff0003;-fx-background-radius: 100;-fx-font-size: 18");
        manaLabel.setStyle("-fx-text-fill: #5a5a5a;-fx-background-color: #4789ff;-fx-background-radius: 100;-fx-font-size: 23");
        cardName.setStyle("-fx-text-fill: #ff7c26;-fx-font-size: 23;");
    }

    public HandImage(int number, AnchorPane root) {
        this.ringView = ControllerBattleCommands.getOurInstance().getHandRings().get(number);
        this.root = root;
        cardView.setOnMouseClicked(event -> {
            if (!clientDB.getLoggedInPlayer().equals(clientDB.getCurrentBattle().getPlayerInTurn()))
                return;
            ControllerBattleCommands controller = ControllerBattleCommands.getOurInstance();
            if (controller.getClickedImageView() != null
                    && controller.getClickedImageView().equals(cardView)) {
                ControllerBattleCommands.getOurInstance().setClickedImageView(null);
                setStyleAsNotSelected();
            } else {
                ControllerBattleCommands.getOurInstance().setClickedImageView(cardView);
                setStyleAsSelected();
            }
            ControllerBattleCommands.getOurInstance().updatePane();
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
        Card card = clientDB.getLoggedInPlayer().getHand().getCardById(id);
        cardView.setVisible(true);
        manaLabel.setVisible(true);
        cardName.setVisible(true);
        try {
            if (card instanceof Unit) {
                cardView.setImage(new Image(new FileInputStream
                        ("./src/ApProjectResources/units/" + getCardName() + "/stand")));
                unitOrSpell = UNIT;
                hpLabel.setText(((Unit) card).getHp() + "");
                apLabel.setText(((Unit) card).getAp() + "");
                apLabel.setVisible(true);
                hpLabel.setVisible(true);
            }
            if (card instanceof Spell) {
                cardView.setImage(new Image(new FileInputStream
                        ("./src/ApProjectResources/spells/" + getCardName() + "/icon")));
                unitOrSpell = SPELL;
                apLabel.setVisible(false);
                hpLabel.setVisible(false);
            }
            setCardViewStyle();
            relocateCardToHand();
            //todo very important check later
            manaLabel.setText(card.getMana() + "");
            cardName.setText(card.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCardViewStyle() {
        if (unitOrSpell.equals(UNIT)) {
            cardView.setFitWidth(UNIT_VIEW_SIZE);
            cardView.setFitHeight(UNIT_VIEW_SIZE);
        }
        if (unitOrSpell.equals(SPELL)) {
            cardView.setFitWidth(SPELL_VIEW_SIZE);
            cardView.setFitHeight(SPELL_VIEW_SIZE);
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
        root.getChildren().add(cardName);
    }

    private void setStatsPositions() {
        int ringSize = GraphicConstants.HAND_RING_SIZE;
        hpLabel.setTranslateX(ringView.getLayoutX() + ringSize * 0.15);
        hpLabel.setTranslateY(ringView.getLayoutY() + ringSize * 0.8);
        apLabel.setTranslateX(ringView.getLayoutX() + ringSize * 0.75);
        apLabel.setTranslateY(ringView.getLayoutY() + ringSize * 0.8);
        manaLabel.setTranslateX(ringView.getLayoutX() + ringSize * 0.45);
        manaLabel.setTranslateY(ringView.getLayoutY() + ringSize * 0.9);
        cardName.setTranslateX(ringView.getLayoutX() + ringSize * 0.2);
        cardName.setTranslateY(ringView.getLayoutY());
    }

    private void relocateCardToHand() {
        double x = 0;
        double y = 0;
        if (unitOrSpell.equals(UNIT)) {
            x = ringView.getLayoutX() + ringView.getFitWidth() * 0.5 - UNIT_VIEW_SIZE / 2;
            y = ringView.getLayoutY() + ringView.getFitHeight() * 0.3 - UNIT_VIEW_SIZE / 2;
        }
        if (unitOrSpell.equals(SPELL)) {
            x = ringView.getLayoutX() + ringView.getFitWidth() * 0.5 - SPELL_VIEW_SIZE / 2;
            y = ringView.getLayoutY() + ringView.getFitHeight() * 0.5 - SPELL_VIEW_SIZE / 2;
        }
        cardView.setTranslateX(x);
        cardView.setTranslateY(y);
        setStatsPositions();
    }

    public void clearHandImage() {
        //todo maybe works incorrect because
        // maybe the imageView remains in the hand
        // and some methods don't work properly
        cardName.setVisible(false);
        cardView.setVisible(false);
        apLabel.setVisible(false);
        hpLabel.setVisible(false);
        manaLabel.setVisible(false);
    }

    public String getId() {
        return id;
    }

    public ImageView getCardView() {
        return cardView;
    }

    public void setStyleAsSelected() {
        cardView.setStyle(SELECTED_STYLE);
    }

    public void setStyleAsNotSelected() {
        cardView.setStyle(null);
    }
}