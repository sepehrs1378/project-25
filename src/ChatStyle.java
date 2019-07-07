import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class ChatStyle {

    private static ChatStyle instance ;
    public ChatStyle(){
        instance = this;
    }

    @FXML
    private TextArea messageTxtArea;

    @FXML
    private Label timeLbl;

    @FXML
    private Label senderLbl;

    @FXML
    private AnchorPane messageBoxPane;

    public static ChatStyle getInstance(){
        return instance;
    }

    public TextArea getMessageTxtArea() {
        return messageTxtArea;
    }

    public Label getTimeLbl() {
        return timeLbl;
    }

    public Label getSenderLbl() {
        return senderLbl;
    }

    public AnchorPane getMessageBoxPane() {
        return messageBoxPane;
    }
}
