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
    private ClientDB dataBase = ClientDB.getInstance();

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
        if (addCardText.getText().isEmpty()) {
            showCards(dataBase.getCardList(), dataBase.getUsableList());
            return;
        }
        List<Card> cardList = new ArrayList<>();
        for (int i = 0; i < dataBase.getCardList().size(); i++) {
            if (dataBase.getCardList().get(i).getName().contains(addCardText.getText())) {
                cardList.add(dataBase.getCardList().get(i));
            }
        }
        cardList.forEach(e-> System.out.println(e.getName()));
        List<Usable> usableList = new ArrayList<>();
        for (int i = 0; i < dataBase.getUsableList().size(); i++) {
            if (dataBase.getUsableList().get(i).getName().contains(addCardText.getText())) {
                usableList.add(dataBase.getUsableList().get(i));
            }
        }
        if (usableList.isEmpty() && cardList.isEmpty()) {
            return;
        }
        showInHBoxes(cardList, usableList);
    }

    public Label getBuyMessage() {
        return buyMessage;
    }

    public Label getMoneyLabel() {
        return moneyLabel;
    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerMainMenu.fxml"));

        Main.window.setScene(new Scene(root));
        Main.setCursor(Main.window);
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

    public void showCards(List<Card> cardList, List<Usable> usableList) throws IOException {
        upperBox.getChildren().clear();
        lowerBox.getChildren().clear();
        cardList.forEach(e-> System.out.println(e.getName()));
        moneyLabel.setText(Integer.toString(ClientDB.getInstance().getLoggedInAccount().getMoney()));
        showInHBoxes(cardList, usableList);
    }

    private void showInHBoxes(List<Card> cardList, List<Usable> usableList) throws IOException {
        List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < cardList.size(); i++) {
            addCardToBox(nodeList, cardList, i);
        }
        for (int i = 0; i < usableList.size(); i++) {
            addUsableToBox(nodeList, usableList, i);
        }
        for (int i = 0; i <= nodeList.size() - 1; i += 2) {
            upperBox.getChildren().add(nodeList.get(i));
            if (i + 1 < nodeList.size()) {
                lowerBox.getChildren().add(nodeList.get(i + 1));
            }
        }
    }

    private void addUsableToBox(List<Node> nodeList, List<Usable> usableList, int i) throws IOException {
        Usable usable = usableList.get(i);
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("CardBackGround.fxml"));
        nodeList.add(fxmlLoader.load());
        CardBackGroundController cardBackGroundController = fxmlLoader.getController();
        cardBackGroundController.setLabelsOfCardOrItem(usable);
    }

    private void addCardToBox(List<Node> nodeList, List<Card> cardList, int i) throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader();
        Card card = cardList.get(i);
        fxmlLoader.setLocation(getClass().getResource("CardBackGround.fxml"));
        nodeList.add(fxmlLoader.load());
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
    */
    }
}
