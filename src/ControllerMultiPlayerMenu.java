import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class ControllerMultiPlayerMenu {
    private static ControllerMultiPlayerMenu ourInstance;
    private ClientDB clientDB = ClientDB.getInstance();
    private boolean isScreenLocked = false;

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
        if (isScreenLocked)
            return;
        ControllerMainMenu.multiPlayerStage.close();
        //todo
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
}
