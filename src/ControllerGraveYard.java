import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGraveYard implements Initializable {
    private static ClientDB clientDB = ClientDB.getInstance();
    private static ControllerGraveYard ourInstance = new ControllerGraveYard();
    private DataBase dataBase = DataBase.getInstance();
    public static Stage stage = null;

    public static ControllerGraveYard getInstance() {
        return ourInstance;
    }

    public ControllerGraveYard() {
        ourInstance = this;
    }

    @FXML
    private ImageView closeBtn;

    @FXML
    private HBox upperBox;

    @FXML
    private HBox lowerBox;

    @FXML
    void close(MouseEvent event) {
        stage.close();
    }

    @FXML
    void makeCloseBtnOpaque(MouseEvent event) {
        closeBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCloseBtnTransparent(MouseEvent event) {
        closeBtn.setStyle("-fx-opacity: 0.6");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Node> nodes = new ArrayList<>();
        List<Card> deadCards;
        PlayerInfo playerInfo1 = clientDB.getCurrentBattle().getPlayer1().getPlayerInfo();
        if (clientDB.getLoggedInAccount().getPlayerInfo().getPlayerName().equals(playerInfo1.getPlayerName())) {
            deadCards = clientDB.getCurrentBattle().getPlayer1().getGraveYard().getDeadCards();
        } else {
            deadCards = clientDB.getCurrentBattle().getPlayer2().getGraveYard().getDeadCards();
        }
        int size = deadCards.size();
        FXMLLoader fxmlLoader;
        for (Card deadCard : deadCards) {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("CollectionCardBackGround.fxml"));
            try {
                nodes.add(fxmlLoader.load());
                CollectionCardBackGround collectionCardBackGround = fxmlLoader.getController();
                collectionCardBackGround.setLabelsOfCardOrItem(deadCard);
                collectionCardBackGround.disableEveryThing();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < size / 2; i++) {
            upperBox.getChildren().add(nodes.get(i));
        }
        for (int i = size / 2; i < size; i++) {
            lowerBox.getChildren().add(nodes.get(i));
        }
    }
}