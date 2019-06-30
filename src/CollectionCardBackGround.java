import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class CollectionCardBackGround {
    private DataBase dataBase = DataBase.getInstance();
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
    void makeCardBackGroundOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        cardBackGround.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCardBackGroundTransparent(MouseEvent event) {
        cardBackGround.setStyle("-fx-opacity: 0.8");
    }

    public void setLabelsOfCardOrItem(Object object){
        this.object = object;
        if (object instanceof Unit){
            Unit unit = (Unit) object;
            if(unit.getHeroOrMinion().equals(Constants.HERO)){
                cardName.setStyle("-fx-text-fill: #e0da00");
            }
            hp.setText(Integer.toString(unit.getHp()));
            ap.setText(Integer.toString(unit.getAp()));
            cardType.setText(unit.getHeroOrMinion());
            mana.setText(Integer.toString(unit.getMana()));
            cardName.setText(unit.getName());
        }
        if (object instanceof Spell){
            Spell spell = (Spell) object;
            mana.setText(Integer.toString(spell.getMana()));
            cardName.setText(spell.getName());
            cardType.setText(Constants.SPELL);
            hp.setText(Integer.toString(spell.getHpChange()));
            ap.setText(Integer.toString(spell.getApChange()));
        }
        if (object instanceof Usable){
            Usable usable = (Usable) object;
            cardType.setText(Constants.USABLE);
            cardName.setText(usable.getName());
        }
    }


    @FXML
    void moveCardToDeck(MouseEvent event) {
        deckName = ControllerCollectionEditMenu.getDeckName();
        OutputMessageType outputMessageType = null;
        if (object instanceof Unit){
            Unit unit = (Unit) object;
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().addCard(unit.getId(), deckName);
        }else if (object instanceof Spell){
            Spell spell = (Spell) object;
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().addCard(spell.getId(), deckName);
        }else if (object instanceof Usable){
            Usable usable = (Usable) object;
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().addCard(usable.getId(), deckName);
        }
        if (outputMessageType != null){
            ControllerCollectionEditMenu.getOurInstance().getMessageLabel().setText(outputMessageType.getMessage());
        }
        removeMessage();
        ControllerCollectionEditMenu.getOurInstance().showCardsInCollection();
        ControllerCollectionEditMenu.getOurInstance().showCardsInDeck();
    }

    public void setDeckName(String name){
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
        OutputMessageType outputMessageType = null;
        if (object instanceof Unit){
            Unit unit = (Unit) object;
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().removeCard(unit.getId(), deckName);
        }else if (object instanceof Spell){
            Spell spell = (Spell) object;
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().removeCard(spell.getId(), deckName);
        }else if (object instanceof Usable){
            Usable usable = (Usable) object;
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().removeCard(usable.getId(), deckName);
        }
        if (outputMessageType != null){
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
        ControllerCollectionEditMenu controllerCollectionEditMenu = ControllerCollectionEditMenu.getOurInstance();
        OutputMessageType outputMessageType = null;
        if (object instanceof Card){
            Card card = (Card) object;
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().sell(card.getId());
        }if (object instanceof Usable){
            Usable usable = (Usable) object;
            outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().sell(usable.getId());
        }
        if (outputMessageType != null){
            controllerCollectionEditMenu.getMessageLabel().setText(outputMessageType.getMessage());
            removeMessage();
        }
        controllerCollectionEditMenu.showCardsInCollection();
        controllerCollectionEditMenu.showCardsInDeck();
    }

    @FXML
    void makeSellBtnOpaque(MouseEvent event) {
        sellBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeSellBtnTransparent(MouseEvent event) {
        sellBtn.setStyle("-fx-opacity: 0.6");
    }

    public void disableEveryThing(){
        cardBackGround.setStyle("-fx-opacity: 1");
        removeBtn.setVisible(false);
        sellBtn.setVisible(false);
        cardBackGround.setDisable(true);
    }
}
