import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerMainMenu {
    private static ControllerMainMenu ourInstance;
    private DataBase dataBase = DataBase.getInstance();
    private Request request = Request.getInstance();
    private View view = View.getInstance();
    private ControllerMatchInfo controllerMatchInfo = ControllerMatchInfo.getInstance();
    private Label[][] battleGroundCells = new Label[5][9];
    private List<UnitImage> unitImageList = new ArrayList<>();
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

    public Label[][] getBattleGroundCells() {
        return battleGroundCells;
    }

    @FXML
    void enterSinglePlayer(MouseEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("ControllerBattleCommandsFXML.fxml"));
        setupBattleGroundCells(root);
        startTempBattle();//todo remove it later
        setupHeroesImages(root);
        //todo units images
        Main.window.setScene(new Scene(root));
    }

    private void startTempBattle() {
        Battle battle = new Battle(DataBase.getInstance().getLoggedInAccount(), DataBase.getInstance().getTemp2()
                , Constants.CLASSIC, 0, null, Constants.SINGLE);
        DataBase.getInstance().setCurrentBattle(battle);
    }

    private void setupHeroesImages(AnchorPane root) {
        Unit playerHero = dataBase.getCurrentBattle().getPlayer1().getDeck().getHero();
        Unit opponentHero = dataBase.getCurrentBattle().getPlayer2().getDeck().getHero();
        UnitImage playerHeroImage = new UnitImage(playerHero.getId(), root);
        UnitImage opponentHeroImage = new UnitImage(opponentHero.getId(), root);
        unitImageList.add(opponentHeroImage);
        unitImageList.add(playerHeroImage);
        playerHeroImage.setInCell(2, 0);
        opponentHeroImage.setInCell(2, 8);
        playerHeroImage.showRun(2,2,root);
    }

    private void setupBattleGroundCells(AnchorPane root) {
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 9; column++) {
                battleGroundCells[row][column] = setLabelStyle(new Label());
                battleGroundCells[row][column].relocate
                        (getCellLayoutX(column)
                                , getCellLayoutY(row));
                battleGroundCells[row][column].setMinWidth(63);
                battleGroundCells[row][column].setMinHeight(50);
                root.getChildren().add(battleGroundCells[row][column]);
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

    public double getCellLayoutX(int column) {
        return GraphicConstants.BATTLE_GROUND_START_X + GraphicConstants.CELL_WIDTH * column;
    }

    public double getCellLayoutY(int row) {
        return GraphicConstants.BATTLE_GROUND_START_Y + GraphicConstants.CELL_HEIGHT * row;
    }
}
