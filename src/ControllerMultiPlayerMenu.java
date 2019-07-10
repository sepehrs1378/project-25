import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class ControllerMultiPlayerMenu implements Initializable {
    private static ControllerMultiPlayerMenu ourInstance;
    private ClientDB clientDB = ClientDB.getInstance();
    private boolean isScreenLocked = false;
    private MediaPlayer backgroundMusic;

    public static ControllerMultiPlayerMenu getInstance() {
        return ourInstance;
    }

    public ControllerMultiPlayerMenu() {
        ourInstance = this;
    }

    @FXML
    public ImageView loadingIcon;

    @FXML
    private ImageView classicBtn;

    @FXML
    private ImageView multiFlagsBtn;

    @FXML
    private ImageView singleFlagBtn;

    @FXML
    private ImageView cancelBtn;

    @FXML
    private ImageView closeBtn;

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
    void makeCancelBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        cancelBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCancelBtnTransparent(MouseEvent event) {
        cancelBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeClassicBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        classicBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeClassicBtnTransparent(MouseEvent event) {
        classicBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeMultiFlagsBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        multiFlagsBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeMultiFlagsBtnTransparent(MouseEvent event) {
        multiFlagsBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeSingleFlagBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        singleFlagBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeSingleFlagBtnTransparent(MouseEvent event) {
        singleFlagBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    public void findClassicMatch() {
        if (isScreenLocked)
            return;
        new ServerRequestSender
                (new Request(RequestType.findClassicMatch, null, null, null)).start();
        lockScreen();
    }

    @FXML
    public void findOneFlagMatch() {
        if (isScreenLocked)
            return;
        new ServerRequestSender(
                new Request(RequestType.findOneFlagMatch, null, null, null)).start();
        lockScreen();
    }

    @FXML
    public void findMultiFlagsMatch() {
        if (isScreenLocked)
            return;
        new ServerRequestSender
                (new Request(RequestType.findMultiFlagsMatch, null, null, null)).start();
        lockScreen();
    }

    @FXML
    public void cancelMatchFinding() {
        if (!isScreenLocked)
            return;
        new ServerRequestSender
                (new Request(RequestType.cancelMatchFinding, null, null, null)).start();
        unlockScreen();
    }

    @FXML
    public void exitMultiPlayerMenu() {
        backgroundMusic.stop();
        Main.getGlobalMediaPlayer().play();
        if (isScreenLocked)
            return;
        ControllerMainMenu.multiPlayerStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getGlobalMediaPlayer().stop();
        backgroundMusic = Main.playMedia("src/music/multiPlayerMenu.mp3"
                , Duration.INDEFINITE, Integer.MAX_VALUE, true, 1);
    }

    private void lockScreen() {
        isScreenLocked = true;
        loadingIcon.setVisible(true);
        //todo
    }

    private void unlockScreen() {
        isScreenLocked = false;
        loadingIcon.setVisible(false);
        //todo
    }

    public static ControllerMultiPlayerMenu getOurInstance() {
        return ourInstance;
    }

    public MediaPlayer getBackgroundMusic() {
        return backgroundMusic;
    }
}
