import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ControllerGlobalChat {

    private static ControllerGlobalChat instance;

    public ControllerGlobalChat() {
        instance = this;
    }

    public static ControllerGlobalChat getInstance() {
        return instance;
    }

    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    private VBox chatVBox;

    @FXML
    private TextField messageBoxTxt;

    @FXML
    private ImageView sendBtn;

    @FXML
    private ImageView backBtn;

    @FXML
    void back(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        new ServerRequestSender(new Request(RequestType.exitGlobalChat,ClientDB.getInstance().getLoggedInAccount().getUsername(),null,null)).start();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerMainMenu.fxml"));
        Main.window.setScene(new Scene(root));
        Main.setCursor(Main.window);
    }

    @FXML
    void makeBackBtnOpaque(MouseEvent event) {
        backBtn.setOpacity(1);
    }

    @FXML
    void makeBackBtnTransparent(MouseEvent event) {
        Main.playWhenMouseEntered();
        backBtn.setOpacity(.6);
    }

    @FXML
    void send(MouseEvent event) throws IOException {
        if (!messageBoxTxt.getText().isEmpty()) {
            ChatMessage chatMessage = new ChatMessage(messageBoxTxt.getText(), ClientDB.getInstance().getLoggedInAccount().getUsername());
            List<Object> chatMessages = new ArrayList<>();
            chatMessages.add(chatMessage);
            new ServerRequestSender(new Request(RequestType.sendMessage, null, null, chatMessages)).start();
            messageBoxTxt.setText("");
            AnchorPane anchorPane = (AnchorPane) Main.window.getScene().getRoot();
            AnchorPane chatBox = FXMLLoader.load(getClass().getResource("ChatStyle.fxml"));
            setChatBox(chatMessage, chatBox, "#18f527");
            HBox hBox = new HBox();
            hBox.setPrefWidth(chatVBox.getPrefWidth()-30);
            hBox.setAlignment(Pos.BASELINE_RIGHT);
            hBox.getChildren().add(chatBox);
            chatVBox.getChildren().add(hBox);

        }
        Main.playWhenButtonClicked();
        sendMessage();
    }

    public static void setChatBox(ChatMessage chatMessage, AnchorPane pane, String color) {
        String style = "-fx-background-color:" + color + ";-fx-text-fill: #ffffff";
        for (Object o : pane.getChildren()) {
            if (o instanceof Label) {
                Label label = ((Label) o);
                label.setStyle(style);
                if (label.getId().equals("senderLbl")) {
                    label.setText(chatMessage.getSender());
                } else if (label.getId().equals("timeLbl")) {
                    label.setText(chatMessage.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                }
            } else if (o instanceof TextArea) {
                ((TextArea) o).setStyle("-fx-text-fill: #000000");
                ((TextArea) o).setText(chatMessage.getMessage());

            }
        }
    }

    @FXML
    void makeSendBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        sendBtn.setOpacity(1);
    }

    @FXML
    void makeSendBtnTransparent(MouseEvent event) {
        sendBtn.setOpacity(.6);
    }

    private void sendMessage() {

    }

    public VBox getChatVBox() {
        return chatVBox;
    }
}
