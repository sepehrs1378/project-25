import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


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
