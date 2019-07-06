import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ControllerGlobalChat {
    @FXML
    private TextField messageBoxTxt;

    @FXML
    private ImageView sendBtn;

    @FXML
    private ImageView backBtn;

    @FXML
    void back(ActionEvent event) {
        Main.playWhenButtonClicked();
        //todo exit chat mode
    }

    @FXML
    void makeBackBtnOpaque(ActionEvent event) {
        backBtn.setOpacity(1);
    }

    @FXML
    void makeBackBtnTransparent(ActionEvent event) {
        Main.playWhenMouseEntered();
        backBtn.setOpacity(.6);
    }

    @FXML
    void send(ActionEvent event) {
        //todo send
        Main.playWhenButtonClicked();
        sendMessage();
    }

    @FXML
    void makeSendBtnOpaque(ActionEvent event) {
        Main.playWhenMouseEntered();
        sendBtn.setOpacity(1);
    }

    @FXML
    void makeSendBtnTransparent(ActionEvent event) {
        sendBtn.setOpacity(.6);
    }

    private void sendMessage(){

    }

}
