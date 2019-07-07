import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLeaderBoard implements Initializable {
    @FXML
    private VBox leaderBoardBox;

    @FXML
    private ImageView backBtn;

    @FXML
    void goBack(MouseEvent event) {
        Main.playWhenButtonClicked();
        ControllerMainMenu.stage.close();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new ServerRequestSender(new Request(RequestType.leaderBoard, null , null, null)).start();
    }
}
