import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCollectionEditMenu implements Initializable {
    private DataBase dataBase = DataBase.getInstance();
    private static ControllerCollectionEditMenu ourInstance;

    public ControllerCollectionEditMenu(){
        ourInstance = this;
    }

    @FXML
    private Label messageLabel;

    private static String deckName;
    @FXML
    private ImageView backBtn;

    @FXML
    private HBox deckBox;

    @FXML
    private HBox collectionBox;

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerCollection.fxml"));
        Main.window.setScene(new Scene(root));
        Main.setCursor(Main.window);
    }

    @FXML
    void makeBackBtnOpaque(MouseEvent event) {
        backBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeBackBtnTransparent(MouseEvent event) {
        backBtn.setStyle("-fx-opacity: 0.6");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showCardsInDeck();
        showCardsInCollection();
    }

    public void showCardsInCollection() {
        collectionBox.getChildren().clear();
        List<Node> nodes = new ArrayList<>();
        PlayerCollection playerCollection = dataBase.getLoggedInAccount().getPlayerInfo().getCollection();
        List<Card> cardList = playerCollection.getCards();
        List<Usable> usableList = playerCollection.getItems();
        FXMLLoader fxmlLoader;
        for (int i = 0; i < cardList.size(); i++){
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("CollectionCardBackGround.fxml"));
            try {
                nodes.add(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            CollectionCardBackGround collectionCardBackGround =  fxmlLoader.getController();
            collectionCardBackGround.setLabelsOfCardOrItem(cardList.get(i));
        }
        for (int i = 0; i < usableList.size(); i++) {
            fxmlLoader = new FXMLLoader();
            try {
                fxmlLoader.setLocation(getClass().getResource("CollectionCardBackGround.fxml"));
                nodes.add(fxmlLoader.load());
                CollectionCardBackGround collectionCardBackGround =  fxmlLoader.getController();
                collectionCardBackGround.setLabelsOfCardOrItem(cardList.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < nodes.size(); i++){
            collectionBox.getChildren().add(nodes.get(i));
        }
    }

    public void showCardsInDeck() {
        deckBox.getChildren().clear();
        Node[] nodes = new Node[22];
        Deck deck = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getDeckByName(deckName);
        List<Card> cardList = deck.getCards();
        Usable usable = (Usable) deck.getItem();
        Unit hero = deck.getHero();
        FXMLLoader fxmlLoader;
        if(!cardList.isEmpty()){
            for (int i = 0; i < deck.getCards().size(); i++) {
                fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("CollectionCardBackGround.fxml"));
                try {
                    if (deck.getCards().get(i) != null){
                        nodes[i] = fxmlLoader.load();
                        CollectionCardBackGround collectionCardBackGround = fxmlLoader.getController();
                        deckBox.getChildren().add(nodes[i]);
                        collectionCardBackGround.setLabelsOfCardOrItem(cardList.get(i));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("CollectionCardBackGround.fxml"));
        try {
            if (usable != null){
                int size = deck.getCards().size();
                nodes[size] = fxmlLoader.load();
                CollectionCardBackGround collectionCardBackGround = fxmlLoader.getController();
                collectionCardBackGround.setLabelsOfCardOrItem(usable);
                collectionCardBackGround.setDeckName(deckName);
                deckBox.getChildren().add(nodes[size]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("CollectionCardBackGround.fxml"));
        try {
            int size = 0;
            if (hero != null){
                if (usable == null){
                    size = deck.getCards().size() + 1;
                    nodes[size] = fxmlLoader.load();
                }else {
                    nodes[size] = fxmlLoader.load();
                }
                CollectionCardBackGround collectionCardBackGround = fxmlLoader.getController();
                collectionCardBackGround.setLabelsOfCardOrItem(hero);
                collectionCardBackGround.setDeckName(deckName);
                deckBox.getChildren().add(nodes[size]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setDeckName(String name){
        deckName = name;
    }

    public static ControllerCollectionEditMenu getOurInstance() {
        return ourInstance;
    }

    public static String getDeckName(){
        return deckName;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}
