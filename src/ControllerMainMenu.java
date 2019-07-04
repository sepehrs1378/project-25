import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class ControllerMainMenu {
    private static ControllerMainMenu ourInstance;
    private DataBase dataBase = DataBase.getInstance();
    private ControllerMatchInfo controllerMatchInfo = ControllerMatchInfo.getInstance();
    private boolean changeOpacity = true;
    private boolean shouldClose = false;
    private ControllerShop controllerShop = ControllerShop.getOurInstance();
    public static Stage stage;

    public static ControllerMainMenu getInstance() {
        return ourInstance;
    }

    public ControllerMainMenu() {
        ourInstance = this;
    }

    @FXML
    private ImageView multiPlayerBtn;

    @FXML
    private ImageView backBtn;

    @FXML
    private ImageView closeBtn;

    @FXML
    private ImageView leaderBoardBtn;

    @FXML
    private ImageView matchHistoryBtn;

    @FXML
    private ImageView customCardBtn;

    @FXML
    private ImageView saveAccountsBtn;

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
    void makeSaveAccountsBtnTransparent(MouseEvent event) {
        saveAccountsBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeSaveAccountsBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        saveAccountsBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void saveAccounts(MouseEvent event) {
        Main.playWhenButtonClicked();
        dataBase.saveAccounts();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Accounts Have Been Successfully Saved");
        alert.showAndWait();
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
    void makeCustomCardBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        customCardBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCustomCardBtnTransparent(MouseEvent event) {
        customCardBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void enterCustomCardMenu(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerCustomCard.fxml"));
        Main.window.setScene(new Scene(root));
        Main.setCursor(Main.window);
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
    void showLeaderBoard(MouseEvent event) {
        Main.playWhenButtonClicked();
        //todo
    }


    @FXML
    void close(MouseEvent event) {
        Main.playWhenButtonClicked();
        dataBase.saveAccounts();
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
    void goBack(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerAccount.fxml"));
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


    @FXML
    void enterSinglePlayer(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerSinglePlayerMenu.fxml"));
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        File file = new File("src/pics/cursor.png");
        Image image = new Image(file.toURI().toString());
        stage.getScene().setCursor(new ImageCursor(image));
        stage.showAndWait();
    }

    @FXML
    void enterMultiPlayer(MouseEvent event) {
        Main.playWhenButtonClicked();
        //todo not needed for phase 2
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
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerShop.fxml"));
        Main.window.setScene(new Scene(root));
        controllerShop = ControllerShop.getOurInstance();
        Main.setCursor(Main.window);
        controllerShop.showCards();
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
}