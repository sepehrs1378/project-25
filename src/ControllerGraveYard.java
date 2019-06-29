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
    private static ControllerGraveYard ourInstance = new ControllerGraveYard();
    private Request request = Request.getInstance();
    private View view = View.getInstance();
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


    public void main() {
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case SHOW_INFO_ID:
                    showInfoId();
                    break;
                case SHOW_CARDS:
                    showCards();
                    break;
                case HELP:
                    help();
                    break;
                case EXIT:
                    didExit = true;
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    private void help() {
        view.printHelp(HelpType.CONTROLLER_GRAVEYARD);
    }

    private void showCards() {
        view.showInfoOfDeadCards(dataBase.getCurrentBattle()
                .getPlayerInTurn().getGraveYard().getDeadCards());
    }

    private void showInfoId() {
        String[] strings = request.getCommand().split("\\s+");
        String cardId = strings[2];
        Card card = dataBase.getCurrentBattle().getPlayerInTurn().getGraveYard().findCard(cardId);
        if (card instanceof Unit) {
            Unit unit = (Unit) card;
            if (unit.getHeroOrMinion().equals(Constants.HERO)) {
                view.showCardInfoHero(unit);
            }
            if (unit.getHeroOrMinion().equals(Constants.MINION)) {
                view.showCardInfoMinion(unit);
            }
        }
    }

    public void showInfoOfCards(List<Card> cards) {
        view.showInfoOfDeadCards(cards);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Node> nodes = new ArrayList<>();
        List<Card> deadCards;
        PlayerInfo playerInfo1 = dataBase.getCurrentBattle().getPlayer1().getPlayerInfo();
        if (dataBase.getLoggedInAccount().getPlayerInfo().getPlayerName().equals(playerInfo1.getPlayerName())){
            deadCards = dataBase.getCurrentBattle().getPlayer1().getGraveYard().getDeadCards();
        }else {
            deadCards = dataBase.getCurrentBattle().getPlayer2().getGraveYard().getDeadCards();
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
