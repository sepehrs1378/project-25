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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCollection implements Initializable {
    private static ControllerCollection ourInstance = new ControllerCollection();
    private DataBase dataBase = DataBase.getInstance();
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
        Main.playWhenButtonClicked();
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
        Main.playWhenMouseEntered();
        mainDeckBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeMainDeckBtnTransparent(MouseEvent event) {
        mainDeckBtn.setStyle("-fx-opacity: 0.6");
    }


    @FXML
    void createDeck(MouseEvent event) {
        Main.playWhenButtonClicked();
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
        Main.playWhenMouseEntered();
        createDeckBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCreateDeckBtnTransparent(MouseEvent event) {
        createDeckBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void importDeck(MouseEvent event) {
        Main.playWhenButtonClicked();
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

    private void changeIDs(Deck deck) {
        for (Card card : deck.getCards()) {
            String oldID = card.getId();
            String[] idSplit = oldID.split("_");
            idSplit[0] = DataBase.getInstance().getLoggedInAccount().getUsername();
            card.setId(idSplit[0] + "_" + idSplit[1] + "_" + idSplit[2]);
        }
        if (deck.getHero() != null) {
            String oldID = deck.getHero().getId();
            String[] idSplit = oldID.split("_");
            idSplit[0] = DataBase.getInstance().getLoggedInAccount().getUsername();
            deck.getHero().setId(idSplit[0] + "_" + idSplit[1] + "_" + idSplit[2]);
        }
        if (deck.getItem() != null) {
            String oldID = deck.getItem().getId();
            String[] idSplit = oldID.split("_");
            idSplit[0] = DataBase.getInstance().getLoggedInAccount().getUsername();
            deck.getItem().setId(idSplit[0] + "_" + idSplit[1] + "_" + idSplit[2]);
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
        if (deck.getItem() == null) {
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
        Main.playWhenMouseEntered();
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
        Main.playWhenMouseEntered();
        exportBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeImportBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        importBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeImportBtnTransparent(MouseEvent event) {
        importBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeRemoveDeckBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        removeDeckBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeRemoveDeckBtnTransparent(MouseEvent event) {
        removeDeckBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void removeDeck(MouseEvent event) {
        Main.playWhenButtonClicked();
        Deck deck = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getDeckByName(selectedLabel.getText().split("\\s+")[0]);
        dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getDecks().remove(deck);
        if (deck == dataBase.getLoggedInAccount().getMainDeck()) {
            dataBase.getLoggedInAccount().setMainDeck(null);
        }
        showDecks();
    }


    @FXML
    void enterEditMenu(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
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
        Main.playWhenButtonClicked();
        if (selectedLabel == null) {
            new Alert(Alert.AlertType.ERROR, "please select a deck!").showAndWait();
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
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerMainMenu.fxml"));
        Main.window.setScene(new Scene(root));
        Main.setCursor();
    }

    @FXML
    void makeBackBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        backBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeBackBtnTransparent(MouseEvent event) {
        backBtn.setStyle("-fx-opacity: 0.6");
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDecks();
        if (dataBase.getLoggedInAccount().getMainDeck() != null)
            mainDeckLabel.setText("Main Deck : " + dataBase.getLoggedInAccount().getMainDeck().getName());
    }

}
