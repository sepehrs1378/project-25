import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EndGameVictory implements Initializable {
    private static EndGameVictory instance;

    public EndGameVictory() {
        instance = this;
    }

    @FXML
    private ImageView backBtn;

    @FXML
    private Label prizeLabel;

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        new ServerRequestSender(new Request(RequestType.gameFinished, null, null, null));
        Main.openMainMenu();
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
        ControllerBattleCommands.getOurInstance().getBackgroundMusic().stop();
        Main.playMedia("src/music/victorySound.mp3", Duration.INDEFINITE, 1, false, 100);
    }

    public void setPrizeLabel(int prize) {
        prizeLabel.setText(prize + "");
    }

    public static EndGameVictory getInstance() {
        return instance;
    }
}