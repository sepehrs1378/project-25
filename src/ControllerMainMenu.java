import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class ControllerMainMenu {
    private static ControllerMainMenu ourInstance;
    private DataBase dataBase = DataBase.getInstance();
    private ControllerMatchInfo controllerMatchInfo = ControllerMatchInfo.getInstance();
    private boolean changeOpacity = true;
    private boolean shouldClose = false;
    private ControllerShop controllerShop = ControllerShop.getOurInstance();
    public static Stage stage;
    public static Stage multiPlayerStage;
    public static Stage auctionBuy;

    public static ControllerMainMenu getInstance() {
        return ourInstance;
    }

    public ControllerMainMenu() {
        ourInstance = this;
    }

    @FXML
    private ImageView auctionsBtn;

    @FXML
    private ImageView multiPlayerBtn;

    @FXML
    private ImageView backBtn;

    @FXML
    private ImageView closeBtn;

    @FXML
    private ImageView enterGlobalChatBtn;

    @FXML
    private ImageView leaderBoardBtn;

    @FXML
    private ImageView matchHistoryBtn;

    @FXML
    private ImageView settingsBtn;

    @FXML
    void makeSettingsBtnOpaque(MouseEvent event) {
        settingsBtn.setStyle("-fx-opacity: 1");
        Main.playWhenMouseEntered();
    }

    @FXML
    void makeSettingsBtnTransparent(MouseEvent event) {
        settingsBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void openSettingsMenu(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerSettingsMenu.fxml"));
        Main.window.setScene(new Scene(root));
        Main.setCursor(Main.window);
    }

    @FXML
    void makeMatchHistoryBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        matchHistoryBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeMatchHistoryBtnTransparent(MouseEvent event) {
        matchHistoryBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void showMatchHistory(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Stage matchHistoryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerMatchInfo.fxml"));
        Scene scene = new Scene(root);
        matchHistoryStage.setScene(scene);
        matchHistoryStage.initModality(Modality.APPLICATION_MODAL);
        matchHistoryStage.initStyle(StageStyle.UNDECORATED);
        ControllerMatchInfo.matchHistoryStage = matchHistoryStage;
        File file = new File("src/pics/cursor.png");
        Image image = new Image(file.toURI().toString());
        matchHistoryStage.getScene().setCursor(new ImageCursor(image));
        matchHistoryStage.showAndWait();
    }

    @FXML
    void makeLeaderBoardBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        leaderBoardBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeLeaderBoardBtnTransparent(MouseEvent event) {
        leaderBoardBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void showLeaderBoard(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerLeaderBoard.fxml"));
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        Main.setCursor(stage);
        Main.dragAbilityForScenes(stage, root);
        stage.showAndWait();
    }


    @FXML
    void close(MouseEvent event) {
        Main.playWhenButtonClicked();
        new ServerRequestSender(new Request(RequestType.logout, "userName:" + ClientDB.getInstance().getLoggedInAccount().getUsername()
                , null, null)).start();
        Main.window.close();
    }

    @FXML
    void makeCloseBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        closeBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCloseBtnTransparent(MouseEvent event) {
        closeBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        new ServerRequestSender(new Request(RequestType.logout, "userName:" + ClientDB.getInstance().getLoggedInAccount().getUsername()
                , null,null)).start();
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


    @FXML
    void enterSinglePlayer(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerSinglePlayerMenu.fxml"));
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        Main.setCursor(stage);
//        File file = new File("src/pics/cursor.png");
//        Image image = new Image(file.toURI().toString());
//        stage.getScene().setCursor(new ImageCursor(image));
        stage.showAndWait();
    }

    @FXML
    void enterMultiPlayer(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerMultiPlayerMenu.fxml"));
        stage = new Stage();
        multiPlayerStage = stage;
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        Main.setCursor(stage);
//        File file = new File("src/pics/cursor.png");
//        Image image = new Image(file.toURI().toString());
//        stage.getScene().setCursor(new ImageCursor(image));
        stage.showAndWait();
    }

    @FXML
    void makeMultiPlayerBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
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
        Main.playWhenButtonClicked();
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
        Main.playWhenMouseEntered();
        singleBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeSingleBtnTransparent(MouseEvent event) {
        singleBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeBattleBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
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
        Main.playWhenButtonClicked();
        new ServerRequestSender(new Request(RequestType.enterCollectoin, null, null,null)).start();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerCollection.fxml"));
        Main.window.setScene(new Scene(root));
        Main.setCursor(Main.window);
    }

    @FXML
    void makeCollectionBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        collectionBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCollectionBtnTransparent(MouseEvent event) {
        collectionBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void enterShop(MouseEvent event) throws IOException {
        new ServerRequestSender(new Request(RequestType.shop, null, null, null)).start();
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerShop.fxml"));
        Main.window.setScene(new Scene(root));
        Main.dragAbilityForScenes(Main.window, root);
        controllerShop = ControllerShop.getOurInstance();
        Main.setCursor(Main.window);
    }

    @FXML
    void makeShopBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        shopBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeShopBtnTransparent(MouseEvent event) {
        shopBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void enterGlobalChat(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        new ServerRequestSender(new Request(RequestType.enterGlobalChat, ClientDB.getInstance().getLoggedInAccount().getUsername(), null, null)).start();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerGlobalChat.fxml"));
        Main.window.setScene(new Scene(root));
        Main.setCursor(Main.window);
    }

    @FXML
    void makeGlobalChatBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        enterGlobalChatBtn.setOpacity(1);
    }

    @FXML
    void makeGlobalChatBtnTransparent(MouseEvent event) {
        enterGlobalChatBtn.setOpacity(.6);
    }

    @FXML
    void enterAuctions(MouseEvent event) {
        Main.playWhenButtonClicked();
        new ServerRequestSender(new Request(RequestType.enterBuyAuction,null,null,null)).start();
    }

    @FXML
    void makeAuctionBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        auctionsBtn.setOpacity(1);
    }

    @FXML
    void makeAuctionBtnTransparent(MouseEvent event) {
        auctionsBtn.setOpacity(.6);
    }
}