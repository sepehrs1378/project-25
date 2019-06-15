import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.xml.crypto.Data;
import java.io.IOException;

public class ControllerMainMenu {
    private static ControllerMainMenu ourInstance;
    private DataBase dataBase = DataBase.getInstance();
    private Request request = Request.getInstance();
    private View view = View.getInstance();
    private ControllerMatchInfo controllerMatchInfo = ControllerMatchInfo.getInstance();
    private Label[][] battleGroundCells = new Label[5][9];
    private boolean changeOpacity = true;
    private boolean shouldClose = false;
    private ControllerShop controllerShop = ControllerShop.getOurInstance();

    public static ControllerMainMenu getInstance() {
        return ourInstance;
    }

    public ControllerMainMenu() {
        ourInstance = this;
    }

    @FXML
    private ImageView multiPlayerBtn;

    @FXML
    void enterSinglePlayer(MouseEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("ControllerBattleCommandsFXML.fxml"));
        setupBattleGroundCells(root);
        Battle battle = new Battle(DataBase.getInstance().getLoggedInAccount(), DataBase.getInstance().getTemp2()
                , Constants.CLASSIC, 0, null, Constants.SINGLE);
        DataBase.getInstance().setCurrentBattle(battle);
        //todo units images
        Main.window.setScene(new Scene(root));
    }

    public void startTempBattle(AnchorPane root) {
        //todo maybe not needed...
    }

    private void setupBattleGroundCells(AnchorPane root) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                battleGroundCells[i][j] = setLabelStyle(new Label());
                battleGroundCells[i][j].relocate
                        (GraphicConstants.BATTLE_GROUND_START_X + GraphicConstants.CELL_WIDTH * j
                                , GraphicConstants.BATTLE_GROUND_START_Y + GraphicConstants.CELL_HEIGHT * i);
                battleGroundCells[i][j].setMinWidth(63);
                battleGroundCells[i][j].setMinHeight(50);
                root.getChildren().add(battleGroundCells[i][j]);
            }
        }
    }

    @FXML
    void enterMultiPlayer(MouseEvent event) {
        //todo not needed for phase 2
    }

    @FXML
    void makeMultiPlayerBtnOpaque(MouseEvent event) {
        multiPlayerBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeMultiPlayerBtnTransparent(MouseEvent event) {
        multiPlayerBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    private ImageView battleBtn;

    @FXML
    private ImageView singleBtn;

    @FXML
    void customOrMulti(MouseEvent event) {
        battleBtn.setStyle("-fx-opacity: 1");
        changeOpacity = false;
        multiPlayerBtn.setVisible(true);
        singleBtn.setVisible(true);
        if (shouldClose) {
            multiPlayerBtn.setVisible(false);
            singleBtn.setVisible(false);
            shouldClose = false;
            changeOpacity = true;
        } else
            shouldClose = true;
    }

    @FXML
    void makeSingleBtnOpaque(MouseEvent event) {
        singleBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeSingleBtnTransparent(MouseEvent event) {
        singleBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeBattleBtnOpaque(MouseEvent event) {
        battleBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeBattleBtnTransparent(MouseEvent event) {
        if (changeOpacity) {
            battleBtn.setStyle("-fx-opacity: 0.6");
        }
    }

    @FXML
    private ImageView shopBtn;

    @FXML
    private ImageView collectionBtn;

    @FXML
    void enterCollection(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ControllerCollection.fxml"));
        Main.window.setScene(new Scene(root));
    }

    @FXML
    void makeCollectionBtnOpaque(MouseEvent event) {
        collectionBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCollectionBtnTransparent(MouseEvent event) {
        collectionBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void enterShop(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ControllerShop.fxml"));
        Main.window.setScene(new Scene(root));
        controllerShop = ControllerShop.getOurInstance();
        controllerShop.showCards();
    }

    @FXML
    void makeShopBtnOpaque(MouseEvent event) {
        shopBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeShopBtnTransparent(MouseEvent event) {
        shopBtn.setStyle("-fx-opacity: 0.6");
    }

    public void main() {
        boolean didLogout = false;
        while (!didLogout) {
            request.getNewCommand();
            switch (request.getType()) {
                case ENTER:
                    enter();
                    break;
                case LOGOUT:
                    logout();
                    didLogout = true;
                    break;
                case HELP:
                    help();
                    break;
                case MATCH_HISTORY:
                    view.showMatchHistoryTitle();
                    controllerMatchInfo.showMatchHistory(dataBase.getLoggedInAccount());
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    public void enter() {
        switch (request.getCommand()) {
            case "enter collection":
                ControllerCollection.getInstance().main();
                break;
            case "enter shop":
                ControllerShop.getOurInstance().main();
                break;
            case "enter battle":
                try {
                    ControllerBattleMenu.getInstance().main();
                } catch (GoToMainMenuException e) {
                }
                break;
            default:
                request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
                view.printOutputMessage(request.getOutputMessageType());
        }
    }

    public void logout() {
        dataBase.setLoggedInAccount(null);
    }

    public void help() {
        view.printHelp(HelpType.CONTROLLER_MAIN_MENU_HELP);
    }

    public Label setLabelStyle(Label label) {
        label.setMinWidth(60);
        label.setMinHeight(60);
        label.setStyle("-fx-background-radius: 10;-fx-background-color: #ebdad5;-fx-opacity: .2");
        label.setOnMouseEntered(e -> {
            label.setStyle("-fx-background-radius: 10;-fx-background-color: #ebdad5;-fx-opacity: .4");
        });
        label.setOnMouseExited(e -> {
            label.setStyle("-fx-background-radius: 10;-fx-background-color: #ebdad5;-fx-opacity: .2");
        });
        return label;
    }

}
