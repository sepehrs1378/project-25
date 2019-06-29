import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCollection implements Initializable {
    private static ControllerCollection ourInstance = new ControllerCollection();
    private Request request = Request.getInstance();
    private DataBase dataBase = DataBase.getInstance();
    private View view = View.getInstance();
    private Label selectedLabel = null;

    public ControllerCollection() {
        ourInstance = this;
    }

    public static ControllerCollection getInstance() {
        return ourInstance;
    }

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox deckListBox;

    @FXML
    private ImageView removeDeckBtn;

    @FXML
    private ImageView importBtn;

    @FXML
    private ImageView exportBtn;

    @FXML
    private ImageView editDeckBtn;

    @FXML
    private ImageView createDeckBtn;

    @FXML
    private JFXTextField deckNameLabel;

    @FXML
    private Label createDeckLabel;

    @FXML
    private Label mainDeckLabel;

    @FXML
    private ImageView mainDeckBtn;

    @FXML
    void setAsMainDeck(MouseEvent event) {
        if (selectedLabel.getText().isEmpty()) {
            return;
        }
        Deck deck = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getDeckByName(selectedLabel.getText().split("\\s+")[0]);
        dataBase.getLoggedInAccount().setMainDeck(deck);
        createDeckLabel.setText("Selected Deck Has Been Set As The Main Deck");
        mainDeckLabel.setText("Main Deck : " + dataBase.getLoggedInAccount().getMainDeck().getName());
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(3)
        );
        visiblePause.setOnFinished(
                event1 -> createDeckLabel.setText("")
        );
        visiblePause.play();
    }

    @FXML
    void makeMainDeckBtnOpaque(MouseEvent event) {
        mainDeckBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeMainDeckBtnTransparent(MouseEvent event) {
        mainDeckBtn.setStyle("-fx-opacity: 0.6");
    }


    @FXML
    void createDeck(MouseEvent event) {
        if (deckNameLabel.getText().isEmpty()) {
            return;
        }
        OutputMessageType outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().createDeck(deckNameLabel.getText());
        createDeckLabel.setText(outputMessageType.getMessage());
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(3)
        );
        visiblePause.setOnFinished(
                event1 -> createDeckLabel.setText("")
        );
        visiblePause.play();
        showDecks();
    }

    @FXML
    void makeCreateDeckBtnOpaque(MouseEvent event) {
        createDeckBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCreateDeckBtnTransparent(MouseEvent event) {
        createDeckBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void importDeck(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(Main.window);
        if (file == null) {
            return;
        }
        Deck deck = dataBase.importDeck(file.getAbsolutePath());
        if (!hasAllCards(deck)) {
            new Alert(Alert.AlertType.ERROR, "you don't possess all the cards that you need").showAndWait();
        } else {
            changeIDs(deck);
            dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getDecks().add(deck);
            showDecks();
        }
    }
    private void changeIDs(Deck deck){
        for (Card card:deck.getCards()){
            String oldID = card.getId();
            String[] idSplit = oldID.split("_");
            idSplit[0] = DataBase.getInstance().getLoggedInAccount().getUsername();
            card.setId(idSplit[0]+"_"+idSplit[1]+"_"+idSplit[2]);
        }
        if (deck.getHero()!= null){
            String oldID = deck.getHero().getId();
            String[] idSplit = oldID.split("_");
            idSplit[0] = DataBase.getInstance().getLoggedInAccount().getUsername();
            deck.getHero().setId(idSplit[0]+"_"+idSplit[1]+"_"+idSplit[2]);
        }
        if (deck.getItem()!=null){
            String oldID = deck.getItem().getId();
            String[] idSplit = oldID.split("_");
            idSplit[0] = DataBase.getInstance().getLoggedInAccount().getUsername();
            deck.getItem().setId(idSplit[0]+"_"+idSplit[1]+"_"+idSplit[2]);
        }
    }
    private boolean hasAllCards(Deck deck) {
        List<Card> cards = new ArrayList<>(deck.getCards());
        while (!cards.isEmpty()) {
            if (findNumOfSimilarCards(cards.get(0).getName(), cards) >
                    findNumOfSimilarCards(cards.get(0).getName(), dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getCards())) {
                return false;
            } else {
                removeSimilarCards(cards.get(0).getName(), cards);
            }
        }
        if (deck.getHero() != null && findNumOfSimilarCards(deck.getHero().getName()
                , dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getCards()) == 0) {
            return false;
        }
        if (deck.getItem()==null){
            return true;
        }
        return hasItem(deck.getItem().getName(), dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getItems());
    }

    private boolean hasItem(String name, List<Usable> items) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private int findNumOfSimilarCards(String name, List<Card> cardList) {
        int num = 0;
        for (int i = 0; i < cardList.size(); i++) {
            if (cardList.get(i).getName().equals(name)) {
                num++;
            }
        }
        return num;
    }

    private void removeSimilarCards(String name, List<Card> cardList) {
        for (int i = 0; i < cardList.size(); i++) {
            if (cardList.get(i).getName().equals(name)) {
                cardList.remove(i--);
            }
        }
    }

    @FXML
    void makeEditDeckBtnOpaque(MouseEvent event) {
        editDeckBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeEditDeckBtnTransparent(MouseEvent event) {
        editDeckBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeExportBtnTransparent(MouseEvent event) {
        exportBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeExportBtnOpaque(MouseEvent event) {
        exportBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeImportBtnOpaque(MouseEvent event) {
        importBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeImportBtnTransparent(MouseEvent event) {
        importBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeRemoveDeckBtnOpaque(MouseEvent event) {
        removeDeckBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeRemoveDeckBtnTransparent(MouseEvent event) {
        removeDeckBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void removeDeck(MouseEvent event) {
        Deck deck = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getDeckByName(selectedLabel.getText().split("\\s+")[0]);
        dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getDecks().remove(deck);
        if (deck == dataBase.getLoggedInAccount().getMainDeck()) {
            dataBase.getLoggedInAccount().setMainDeck(null);
        }
        showDecks();
    }


    @FXML
    void enterEditMenu(MouseEvent event) throws IOException {
        if (selectedLabel == null) {
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ControllerCollectionEditMenu.fxml"));
        ControllerCollectionEditMenu.setDeckName(selectedLabel.getText().split("\\s+")[0]);
        Parent root = fxmlLoader.load();
        Main.window.setScene(new Scene(root));

        Main.setCursor();
    }

    @FXML
    void exportDeck(MouseEvent event) {
        if (selectedLabel == null) {
            new Alert(Alert.AlertType.ERROR,"please select a deck!").showAndWait();
            return;
        }
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(Main.window);
        Deck deck = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getDeckByName(selectedLabel.getText().split("\\s+")[0]);
        if (deck == null) {
            return;
        }
        DataBase.getInstance().exportDeck(deck, file.getAbsolutePath());
    }

    @FXML
    private ImageView backBtn;

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ControllerMainMenu.fxml"));
        Main.window.setScene(new Scene(root));
        Main.setCursor();
    }

    @FXML
    void makeBackBtnOpaque(MouseEvent event) {
        backBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeBackBtnTransparent(MouseEvent event) {
        backBtn.setStyle("-fx-opacity: 0.6");
    }

    //    @FXML
//    void addItemToDeck(MouseEvent event) {
//        if (itemText.getText().isEmpty()) {
//            return;
//        }
//        PlayerCollection playerCollection = dataBase.getLoggedInAccount().getPlayerInfo().getCollection();
//        if (deckNameItemText.getText().isEmpty()) {
//            return;
//        }
//        Deck deck = playerCollection.getDeckByName(deckNameText.getText());
//        Item item = playerCollection.getItemWithID(itemText.getText());
//        deck.setItem(item);
//    }
//
//
//    @FXML
//    void addCardToDeck(MouseEvent event) {
//        if (cardInCollectionText.getText().isEmpty()) {
//            return;
//        }
//        if (deckNameText.getText().isEmpty()) {
//            return;
//        }
//        PlayerCollection playerCollection = dataBase.getLoggedInAccount().getPlayerInfo().getCollection();
//        OutputMessageType outputMessageType =
//                playerCollection.addCard(cardInCollectionText.getText(), deckNameText.getText());
//        Alert alert = new Alert(Alert.AlertType.INFORMATION, outputMessageType.getMessage());
//        alert.showAndWait();
//    }
//
//
//    @FXML
//    void createNewDeck(MouseEvent event) {
//        if (createNewDeckText.getText().isEmpty()) {
//            return;
//        }
//        OutputMessageType outputMessageType =
//                dataBase.getLoggedInAccount().getPlayerInfo().getCollection().createDeck(createNewDeckText.getText());
//        Alert alert = new Alert(Alert.AlertType.INFORMATION, outputMessageType.getMessage());
//        alert.showAndWait();
//    }
    private void showDecks() {
        deckListBox.getChildren().clear();
        List<Deck> decks = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getDecks();
        for (int i = 0; i < decks.size(); i++) {
            Label label = new Label();
            Deck deck = decks.get(i);
            label.setText((deck != null ? deck.getName() : "") + "    Hero : " +
                    ((deck != null && deck.getHero() != null) ? deck.getHero().getName() : "") + "    Minions : "
                    + ((deck != null) ? deck.calculateNumberOfMinionsOrSpells(Constants.MINION) : "")
                    + "    Spells : " + ((deck != null) ? deck.calculateNumberOfMinionsOrSpells(Constants.SPELL) : "")
                    + "    Item :  " + ((deck != null && deck.getItem() != null) ? deck.getItem().getName() : ""));
            label.setStyle("-fx-background-radius: 10; -fx-font-size: 30; -fx-border-radius: 10; -fx-text-fill: #e0da00");
            label.setPrefHeight(50);
            label.setPrefWidth(deckListBox.getPrefWidth());
            deckListBox.getChildren().add(label);
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (selectedLabel != null) {
                        selectedLabel.setStyle("-fx-background-radius: 10; -fx-font-size: 30; -fx-border-radius: 10; -fx-text-fill: #e0da00");
                        selectedLabel.setEffect(null);
                    }
                    if (label == selectedLabel) {
                        selectedLabel = null;
                        label.setStyle("-fx-background-radius: 10; -fx-font-size: 30; -fx-border-radius: 10; -fx-text-fill: #e0da00;");
                        label.setEffect(null);
                        return;
                    }
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setOffsetX(2);
                    dropShadow.setOffsetY(2);
                    dropShadow.setColor(Color.rgb(150, 50, 50, .688));
                    selectedLabel = label;
                    label.setStyle("-fx-background-radius: 10; -fx-font-size: 30;-fx-border-color: #ffe700; -fx-border-radius: 10; -fx-text-fill: #e02100");
                    label.setEffect(dropShadow);
                }
            });
        }
    }

    public void main() {
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case EXIT:
                    didExit = true;
                    break;
                case SHOW:
                    show();
                    break;
                case SEARCH_NAME:
                    searchName();
                    break;
                case CREATE_DECK_NAME:
                    createDeckName();
                    break;
                case DELETE_DECK_NAME:
                    deleteDeckName();
                    break;
                case ADD_ID_TO_DECK_NAME:
                    addIdToDeckName();
                    break;
                case REMOVE_ID_FROM_DECK_NAME:
                    removeIdFromDeckName();
                    break;
                case VALIDATE_DECK_NAME:
                    validateDeckName();
                    break;
                case SELECT_DECK_NAME:
                    selectDeckName();
                    break;
                case SHOW_ALL_DECKS:
                    showAllDecks();
                    break;
                case SHOW_DECK_NAME:
                    showDeckName();
                    break;
                case HELP:
                    help();
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    public void createDeckName() {
        String deckName = request.getCommand().split(" ")[2];
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().createDeck(deckName)) {
            case DECK_ALREADY_EXISTS:
                view.printOutputMessage(OutputMessageType.DECK_ALREADY_EXISTS);
                break;
            case DECK_CREATED:
                view.printOutputMessage(OutputMessageType.DECK_CREATED);
                break;
            default:
                view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
        }
    }

    private void show() {
        view.showCardsAndItemsOfCollection(dataBase.getLoggedInAccount().getPlayerInfo().getCollection());
    }

    private void showDeckName() {
        PlayerCollection temp = dataBase.getLoggedInAccount().getPlayerInfo().getCollection();
        Deck deck = temp.getDeckByName(request.getCommand().split("\\s+")[2]);
        view.showDeck(deck, "");
    }

    private void showAllDecks() {
        Deck mainDeck = dataBase.getLoggedInAccount().getMainDeck();
        view.showAllDecks(dataBase.getLoggedInAccount().getPlayerInfo().getCollection(), mainDeck);
    }

    private void help() {
        view.printHelp(HelpType.CONTROLLER_COLLECTION_HELP);
    }

    public void selectDeckName() {
        String deckName = request.getCommand().split(" ")[2];
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection()
                .selectDeckAsMain(deckName)) {
            case DECK_DOESNT_EXIST:
                view.printOutputMessage(OutputMessageType.DECK_DOESNT_EXIST);
                break;
            case DECK_SELECTED:
                view.printOutputMessage(OutputMessageType.DECK_SELECTED);
                break;
            default:
        }
    }

    public void validateDeckName() {
        String deckName = request.getCommand().split(" ")[2];
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().validateDeck(deckName)) {
            case DECK_DOESNT_EXIST:
                view.printOutputMessage(OutputMessageType.DECK_DOESNT_EXIST);
                break;
            case DECK_VALID:
                view.printOutputMessage(OutputMessageType.DECK_VALID);
                break;
            case DECK_NOT_VALID:
                view.printOutputMessage(OutputMessageType.DECK_NOT_VALID);
                break;
            default:
        }
    }

    public void addIdToDeckName() {
        String[] order = request.getCommand().split(" ");
        OutputMessageType outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo()
                .getCollection().addCard(order[1], order[4]);
        view.printOutputMessage(outputMessageType);
    }

    public void deleteDeckName() {
        String deckName = request.getCommand().split(" ")[2];
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().deleteDeck(deckName)) {
            case DECK_DOESNT_EXIST:
                view.printOutputMessage(OutputMessageType.DECK_DOESNT_EXIST);
                break;
            case DECK_DELETED:
                view.printOutputMessage(OutputMessageType.DECK_DELETED);
                break;
            default:
                break;
        }
    }

    public void removeIdFromDeckName() {
        String[] order = request.getCommand().split("\\s+");
        OutputMessageType outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection()
                .removeCard(order[1], order[4]);
        view.printOutputMessage(outputMessageType);
    }

    private void searchName() {
        List<String> output = dataBase.getLoggedInAccount().getPlayerInfo().getCollection()
                .searchCardOrItemWithName(request.getCommand().split("\\s+")[1]);
        view.printList(output);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDecks();
        mainDeckLabel.setText("Main Deck : " + dataBase.getLoggedInAccount().getMainDeck().getName());
    }

}
