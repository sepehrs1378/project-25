import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class ServerHandler extends Thread {
    private static ServerHandler instance;

    private String address;
    private int port;
    public ServerHandler(String address, int port) {
        this.address = address;
        this.port = port;
        instance = this;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(address, port);
            ClientDB.getInstance().setSocket(socket);
            JsonStreamParser parser = ClientDB.getInstance().getParser();
            JsonObject obj;
            YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
            while (parser.hasNext()) {
                obj = parser.next().getAsJsonObject();
                Response response = yaGson.fromJson(obj.toString(), Response.class);
                switch (response.getResponseType()) {
                    case sendMessage:{
                        ChatMessage chatMessage = (ChatMessage) response.getObjectList().get(0);
                        Platform.runLater(()->{
                            AnchorPane chatBox = null;
                            try {
                                chatBox = FXMLLoader.load(getClass().getResource("ChatStyle.fxml"));
                                ControllerGlobalChat.setChatBox(chatMessage,chatBox,"#1919f7");
                                HBox hBox = new HBox();
                                hBox.setPrefWidth(ControllerGlobalChat.getInstance().getChatVBox().getPrefWidth()-30);
                                hBox.setAlignment(Pos.BASELINE_LEFT);
                                hBox.getChildren().add(chatBox);
                                ControllerGlobalChat.getInstance().getChatVBox().getChildren().add(hBox);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });
                        break;
                    }
                    case signUp:{
                        if (response.getMessage().equals(OutputMessageType.CREATED_ACCOUNT_SUCCESSFULLY.getMessage())){
                            Account account = (Account) response.getObjectList().get(0);
                            ClientDB.getInstance().setLoggedInAccount(account);
                            openMainMenu();
                        }else if (response.getMessage().equals(OutputMessageType.USERNAME_ALREADY_EXISTS.getMessage())){
                            Label label = findInvalidUserName(Main.window, "loginPane", "invalidUsername");
                            if (label != null){
                                Platform.runLater(() -> label.setText(OutputMessageType.USERNAME_ALREADY_EXISTS.getMessage()));
                            }
                        }
                        break;
                    }
                    case login:{
                        if (response.getMessage().equals(OutputMessageType.LOGGED_IN_SUCCESSFULLY.getMessage())){
                            Account account = (Account) response.getObjectList().get(0);
                            ClientDB.getInstance().setLoggedInAccount(account);
                            openMainMenu();
                        }else if(response.getMessage().equals(OutputMessageType.INVALID_PASSWORD.getMessage())){
                            Label label = findInvalidUserName(Main.window, "loginPane", "invalidPassword");
                            if (label != null){
                                Platform.runLater(() -> label.setText(OutputMessageType.INVALID_PASSWORD.getMessage()));
                            }
                        }else if (response.getMessage().equals(OutputMessageType.ACCOUNT_DOESNT_EXIST.getMessage())){
                            Label label = findInvalidUserName(Main.window, "loginPane", "invalidUsername");
                            if (label != null){
                                Platform.runLater(() -> label.setText(OutputMessageType.ACCOUNT_DOESNT_EXIST.getMessage()));
                            }
                        }else if (response.getMessage().equals(OutputMessageType.ALREADY_LOGGED_IN.getMessage())){
                            Label label = findInvalidUserName(Main.window, "loginPane", "invalidUsername");
                            if (label != null){
                                Platform.runLater(() -> label.setText(OutputMessageType.ALREADY_LOGGED_IN.getMessage()));
                            }
                        }
                        break;
                    }
                    case logout:{
                        if (response.getMessage().equals(OutputMessageType.LOGGED_OUT_SUCCESSFULLY.getMessage())){
                            ClientDB.getInstance().setLoggedInAccount(null);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Main.playWhenButtonClicked();
                                    try {
                                        Parent root = FXMLLoader.load(getClass().getResource("ControllerAccount.fxml"));
                                        Main.window.setScene(new Scene(root));
                                        Main.setCursor(Main.window);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMainMenu() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("ControllerMainMenu.fxml"));
                    Main.window.setScene(new Scene(root));
                    Main.setCursor(Main.window);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Label findInvalidUserName(Stage stage, String paneID, String labelID){
        AnchorPane anchorPane = null;
        for (Object object : stage.getScene().getRoot().getChildrenUnmodifiable()){
            if (object instanceof AnchorPane){
                anchorPane = (AnchorPane) object;
                if (anchorPane.getId().equals(paneID)){
                    break;
                }
            }
        }
        if (anchorPane != null){
            for (Object object : anchorPane.getChildren()){
                if (object instanceof Label){
                    Label label = (Label) object;
                    if (label.getId().equals(labelID)){
                        return label;
                    }
                }
            }
        }
        return null;
    }

    public static ServerHandler getInstance() {
        return instance;
    }
}