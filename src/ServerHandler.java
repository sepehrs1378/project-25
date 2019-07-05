import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ServerHandler extends Thread {
    private String address;
    private int port;

    public ServerHandler(String address, int port) {
        this.address = address;
        this.port = port;
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
                        System.out.println(response.getMessage());
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
}