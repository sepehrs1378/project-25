import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class EndGameVictory {
    @FXML
    private ImageView backBtn;

    @FXML
    private Label prizeLabel;

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        new ServerRequestSender(new Request(RequestType.gameFinished, null, null, null));
        Parent root = FXMLLoader.load(getClass().getResource("EndGameVictory.fxml"));
        Main.window.setScene(new Scene(root));
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
}