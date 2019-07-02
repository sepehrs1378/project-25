package ClientPackage;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerShop {
    private static ControllerShop ourInstance = new ControllerShop();
    private DataBase dataBase = DataBase.getInstance();

    public ControllerShop() {
        ourInstance = this;
    }
    @FXML
    private HBox upperBox;

    @FXML
    private HBox lowerBox;

    @FXML
    private AnchorPane shopPane;

    @FXML
    private JFXTextField addCardText;

    @FXML
    private ImageView backBtn;

    @FXML
    private Label moneyLabel;

    @FXML
    private Label buyMessage;

    @FXML
    void displayAppropriateCards(ActionEvent event) throws IOException {
        lowerBox.getChildren().clear();
        upperBox.getChildren().clear();
        if (addCardText.getText().isEmpty()){
            showCards();
            return;
        }
        List<Card> cardList = new ArrayList<>();
        for (int i = 0; i < dataBase.getCardList().size(); i++) {
            if(dataBase.getCardList().get(i).getName().contains(addCardText.getText())){
                cardList.add(dataBase.getCardList().get(i));
            }
        }
        List<Usable> usableList = new ArrayList<>();
        for (int i = 0; i < dataBase.getUsableList().size(); i++) {
            if (dataBase.getUsableList().get(i).getName().contains(addCardText.getText())){
                usableList.add(dataBase.getUsableList().get(i));
            }
        }
        if(usableList.isEmpty() && cardList.isEmpty()){
            return;
        }
        int size = (usableList.size() + cardList.size());
        int originalSize = size;
        Node[] nodes = new Node[size];
        for (int i = 0; i < cardList.size(); i++) {
            addCardToBox(nodes, cardList, i , i);
        }
        for (int i = 0; i < usableList.size(); i++) {
            addUsableToBox(nodes, usableList, i, i + cardList.size());
        }
        if (size % 2 == 0){
            size /= 2;
        }else {
            size = size / 2 + 1;
        }
        for (int i = 0; i < size; i++) {
            upperBox.getChildren().add(nodes[i]);
        }
        if (originalSize % 2 == 0){
            for (int i = size; i < size * 2; i++) {
                lowerBox.getChildren().add(nodes[i]);
            }
        }else {
            for (int i = size; i < size * 2 - 1; i++) {
                lowerBox.getChildren().add(nodes[i]);
            }
        }
    }

    public Label getBuyMessage(){
        return buyMessage;
    }

    public Label getMoneyLabel(){
        return moneyLabel;
    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ClientPackage/ControllerMainMenu.fxml"));
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

    public void showCards() throws IOException {
        moneyLabel.setText(Integer.toString(dataBase.getLoggedInAccount().getMoney()));
        Node[] nodes = new Node[80];
        List<Card> cardList = dataBase.getCardList();
        List<Usable> usableList = dataBase.getUsableList();
        for (int i = 0; i < 40; i++) {
            addCardToBox(nodes, cardList, i, i);
            upperBox.getChildren().add(nodes[i]);
        }
        lowerBox.setLayoutY(upperBox.getLayoutY() + upperBox.getPrefHeight() + 18);
        for (int i = 40; i < 70; i++) {
            addCardToBox(nodes, cardList, i, i);
            lowerBox.getChildren().add(nodes[i]);
        }
        for (int i = 70; i < 79; i++) {
            //todo i should be less than 81 not 79
            addUsableToBox(nodes, usableList, i - 70, i);
            lowerBox.getChildren().add(nodes[i]);
        }
    }

    private void addUsableToBox(Node[] nodes, List<Usable> usableList, int i, int j) throws IOException {
        Usable usable = usableList.get(i);
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ClientPackage/CardBackGround.fxml"));
        nodes[j] = fxmlLoader.load();
        CardBackGroundController cardBackGroundController = fxmlLoader.getController();
        cardBackGroundController.setLabelsOfCardOrItem(usable);
    }

    private void addCardToBox(Node[] nodes, List<Card> cardList, int i, int j) throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader();
        Card card = cardList.get(i);
        fxmlLoader.setLocation(getClass().getResource("ClientPackage/CardBackGround.fxml"));
        nodes[j] = fxmlLoader.load();
        CardBackGroundController cardBackGroundController = fxmlLoader.getController();
        cardBackGroundController.setLabelsOfCardOrItem(card);
    }

    public static ControllerShop getOurInstance() {
        return ourInstance;
    }

    public void showIdInShop(Card card, Usable usable, Collectable collectable) {
        /*if (card != null) {
            view.showId(card.getId());
        } else if (usable != null) {
            view.showId(usable.getId());
        } else if (collectable != null) {
            view.showId(collectable.getId());
        } else {
            view.showCardOrItemDoesNotExist();
        }
    */}
}
