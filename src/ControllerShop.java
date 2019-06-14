import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerShop {
    private static ControllerShop ourInstance = new ControllerShop();
    private Request request = Request.getInstance();
    private DataBase dataBase = DataBase.getInstance();
    private View view = View.getInstance();

    public ControllerShop() {
        ourInstance = this;
    }

    @FXML
    private AnchorPane shopPane;

    @FXML
    private JFXTextField addCardText;

    @FXML
    private ImageView backBtn;

    @FXML
    void makeBackBtnOpaque(MouseEvent event) {
        backBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeBackBtnTransparent(MouseEvent event) {
        backBtn.setStyle("-fx-opacity: 0.6");
    }


    @FXML
    void goBack(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ControllerMainMenu.fxml"));
        Main.window.setScene(new Scene(root));
    }

    public void showCards() throws IOException {
//        File file = new File("../pics/card_background.png");
//        Image image = new Image(file.toURI().toString());
//        ImageView imageView = new ImageView();
//        imageView.setImage(image);
//        imageView.setFitHeight(100);
//        imageView.setFitWidth(100);
//        imageView.setX(200);
//        imageView.setY(200);
//        shopPane.getChildren().add(imageView);
    }

    @FXML
    void addCard(MouseEvent event) {
        if (addCardText.getText().isEmpty()){
            return;
        }
        OutputMessageType outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().buy(addCardText.getText());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, outputMessageType.getMessage());
        alert.showAndWait();
    }

    public static ControllerShop getOurInstance() {
        return ourInstance;
    }

    public void main() {
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case SHOW_COLLECTION:
                    showCollection();
                    break;
                case SEARCH_NAME:
                    searchName();
                    break;
                case SEARCH_COLLECTION_NAME:
                    searchCollectionName();
                    break;
                case BUY_NAME:
                    buyName();
                    break;
                case SELL_ID:
                    sellId();
                    break;
                case SHOW:
                    show();
                    break;
                case EXIT:
                    didExit = true;
                    break;
                case HELP:
                    help();
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    public void show() {
        view.showCardsAndItemsInShop();
    }

    private void showCollection() {
        view.showCardsAndItemsOfCollection(dataBase.getLoggedInAccount().getPlayerInfo().getCollection());
    }

    public void sellId() {
        String id = request.getCommand().split(" ")[1];
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().sell(id)) {
            case NOT_IN_COLLECTION:
                view.printOutputMessage(OutputMessageType.NOT_IN_COLLECTION);
                break;
            case SOLD_SUCCESSFULLY:
                dataBase.getLoggedInAccount().getPlayerInfo().getCollection().sell(id);
                view.printOutputMessage(OutputMessageType.SOLD_SUCCESSFULLY);
                break;
            default:
        }
    }

    private void buyName() {
        String name = request.getCommand().split(" ")[1];
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().buy(name)) {
            case INSUFFICIENT_MONEY:
                view.printOutputMessage(OutputMessageType.INSUFFICIENT_MONEY);
                break;
            case CANT_HAVE_MORE_ITEMS:
                view.printOutputMessage(OutputMessageType.CANT_HAVE_MORE_ITEMS);
                break;
            case BOUGHT_SUCCESSFULLY:
                view.printOutputMessage(OutputMessageType.BOUGHT_SUCCESSFULLY);
                break;
            case NOT_IN_SHOP:
                view.printOutputMessage(OutputMessageType.NOT_IN_SHOP);
                break;
            default:
        }
    }

    private void searchName() {
        dataBase.searchInShop(request.getCommand());
    }

    private void searchCollectionName() {
        String[] strings = request.getCommand().split("\\s+");
        ArrayList<String> cardsAndItems =
                new ArrayList<>(dataBase.getLoggedInAccount().getPlayerInfo().getCollection().searchCardOrItemWithName(strings[2]));
        view.printList(cardsAndItems);
    }

    public void help() {
        view.printHelp(HelpType.CONTROLLER_SHOP_HELP);
    }

    public void showIdInShop(Card card, Usable usable, Collectable collectable) {
        if (card != null) {
            view.showId(card.getId());
        } else if (usable != null) {
            view.showId(usable.getId());
        } else if (collectable != null) {
            view.showId(collectable.getId());
        } else {
            view.showCardOrItemDoesNotExist();
        }
    }
}
