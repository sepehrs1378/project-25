import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollectionCardBackGround {
    private ClientDB dataBase = ClientDB.getInstance();
    Object object = null;
    String deckName = "";
    @FXML
    private Label hp;

    @FXML
    private Label ap;

    @FXML
    private Label mana;

    @FXML
    private Label cardName;

    @FXML
    private Label cardType;

    @FXML
    private ImageView cardBackGround;

    @FXML
    private ImageView removeBtn;

    @FXML
    private ImageView sellBtn;

    @FXML
    private JFXButton sellByAuction;

    @FXML
    void makeCardBackGroundOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        cardBackGround.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCardBackGroundTransparent(MouseEvent event) {
        cardBackGround.setStyle("-fx-opacity: 0.8");
    }

    public void setLabelsOfCardOrItem(Object object) {
        this.object = object;
        if (object instanceof Unit) {
            Unit unit = (Unit) object;
            if (unit.getHeroOrMinion().equals(Constants.HERO)) {
                cardName.setStyle("-fx-text-fill: #e0da00");
            }
            hp.setText(Integer.toString(unit.getHp()));
            ap.setText(Integer.toString(unit.getAp()));
            cardType.setText(unit.getHeroOrMinion());
            mana.setText(Integer.toString(unit.getMana()));
            cardName.setText(unit.getName());
        }
        if (object instanceof Spell) {
            Spell spell = (Spell) object;
            mana.setText(Integer.toString(spell.getMana()));
            cardName.setText(spell.getName());
            cardType.setText(Constants.SPELL);
            hp.setText(Integer.toString(spell.getHpChange()));
            ap.setText(Integer.toString(spell.getApChange()));
        }
        if (object instanceof Usable) {
            Usable usable = (Usable) object;
            cardType.setText(Constants.USABLE);
            cardName.setText(usable.getName());
        }
    }


    @FXML
    void moveCardToDeck(MouseEvent event) {
        deckName = ControllerCollectionEditMenu.getDeckName();
        OutputMessageType outputMessageType = null;
        if (object instanceof Unit) {
            Unit unit = (Unit) object;
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().addCard(unit.getId(), deckName);
            if (outputMessageType.getMessage().equals(OutputMessageType.CARD_ADDED_SUCCESSFULLY.getMessage())) {
                JsonObject obj = new JsonObject();
                obj.addProperty("cardID", unit.getId());
                obj.addProperty("deckName", deckName);
                List<Object> objList = new ArrayList<>();
                objList.add(obj);
                new ServerRequestSender(new Request(RequestType.moveCardToDeck, null, null, objList)).start();
            }
        } else if (object instanceof Spell) {
            Spell spell = (Spell) object;
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().addCard(spell.getId(), deckName);
            if (outputMessageType.getMessage().equals(OutputMessageType.CARD_ADDED_SUCCESSFULLY.getMessage())) {
                JsonObject obj = new JsonObject();
                obj.addProperty("deckName", deckName);
                List<Object> objList = new ArrayList<>();
                obj.addProperty("cardID", spell.getId());
                objList.add(obj);
                new ServerRequestSender(new Request(RequestType.moveCardToDeck, null, null, objList)).start();
            }
        } else if (object instanceof Usable) {
            Usable usable = (Usable) object;
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().addCard(usable.getId(), deckName);
            if (outputMessageType.getMessage().equals(OutputMessageType.CARD_ADDED_SUCCESSFULLY.getMessage())) {
                JsonObject obj = new JsonObject();
                obj.addProperty("cardID", usable.getId());
                List<Object> objList = new ArrayList<>();
                obj.addProperty("deckName", deckName);
                objList.add(obj);
                new ServerRequestSender(new Request(RequestType.moveCardToDeck, null, null, objList)).start();
            }
        }
        if (outputMessageType != null) {
            ControllerCollectionEditMenu.getOurInstance().getMessageLabel().setText(outputMessageType.getMessage());
        }
        removeMessage();
        ControllerCollectionEditMenu.getOurInstance().showCardsInCollection();
        ControllerCollectionEditMenu.getOurInstance().showCardsInDeck();
    }

    public void setDeckName(String name) {
        deckName = name;
    }

    @FXML
    void makeRemoveBtnOpaque(MouseEvent event) {
        removeBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeRemoveBtnTransparent(MouseEvent event) {
        removeBtn.setStyle("-fx-opacity: 0.8");
    }

    @FXML
    void removeCardFromDeck(MouseEvent event) {
        deckName = ControllerCollectionEditMenu.getDeckName();
        String id = null;
        OutputMessageType outputMessageType = null;
        if (object instanceof Unit) {
            Unit unit = (Unit) object;
            id = unit.getId();
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().removeCard(unit.getId(), deckName);
        } else if (object instanceof Spell) {
            Spell spell = (Spell) object;
            id = spell.getId();
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().removeCard(spell.getId(), deckName);
        } else if (object instanceof Usable) {
            Usable usable = (Usable) object;
            id = usable.getId();
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().removeCard(usable.getId(), deckName);
        }
        if (outputMessageType != null && outputMessageType.getMessage().equals(OutputMessageType.CARD_REMOVED_SUCCESSFULLY.getMessage())) {
            JsonObject object = new JsonObject();
            object.addProperty("id",id);
            object.addProperty("deck",deckName);
            List<Object> objectList = new ArrayList<>();
            objectList.add(object);
            new ServerRequestSender(new Request(RequestType.removeCardFromDeck,null,null,objectList)).start();
        }
        if (outputMessageType != null) {
            ControllerCollectionEditMenu.getOurInstance().getMessageLabel().setText(outputMessageType.getMessage());
        }
        removeMessage();
        ControllerCollectionEditMenu.getOurInstance().showCardsInDeck();
        ControllerCollectionEditMenu.getOurInstance().showCardsInCollection();
    }


    private void removeMessage() {
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(5)
        );
        visiblePause.setOnFinished(
                event1 -> ControllerCollectionEditMenu.getOurInstance().getMessageLabel().setText("")
        );
        visiblePause.play();
    }

    @FXML
    void sellCard(MouseEvent event) {
        if (object instanceof Card) {
            Card card = (Card) object;
            new ServerRequestSender(new Request(RequestType.sell, card.getId(), null, null)).start();
        }
        if (object instanceof Usable) {
            Usable usable = (Usable) object;
            new ServerRequestSender(new Request(RequestType.sell, usable.getId(), null, null)).start();
        }
    }

    @FXML
    void makeSellBtnOpaque(MouseEvent event) {
        sellBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeSellBtnTransparent(MouseEvent event) {
        sellBtn.setStyle("-fx-opacity: 0.6");
    }

    public void disableEveryThing() {
        cardBackGround.setStyle("-fx-opacity: 1");
        removeBtn.setVisible(false);
        sellBtn.setVisible(false);
        cardBackGround.setDisable(true);
    }

    @FXML
    void enterAuction(ActionEvent event) throws IOException {
        if (object instanceof Card){
            Stage stage = new Stage();
            AnchorPane root = FXMLLoader.load(getClass().getResource("ControllerAuctionSell.fxml"));
            Card card = (Card)object;
            List<Object> objectList = new ArrayList<>();
            objectList.add(card);
            new ServerRequestSender(new Request(RequestType.enterSellAuction,
                    ClientDB.getInstance().getLoggedInAccount().getUsername(),null , objectList)).start();
            ControllerAuctionSell.getInstance().getPrizeLbl().setText(card.getPrice()*80/100+"");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            ControllerMainMenu.auctionSell = stage;
            stage.showAndWait();
        }
    }

}
