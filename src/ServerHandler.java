import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

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
                                        Main.dragAbilityForScenes(Main.window, root);
                                        Main.setCursor(Main.window);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        break;
                    }
                    case leaderBoard:
                        showAccountsInLeaderBoard(response);
                        break;
                    case updateLeaderBoard:
                        showAccountsInLeaderBoard(response);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAccountsInLeaderBoard(Response response) {
        List<Account> accountList = new ArrayList(response.getObjectList());
        List<Integer> integerList = new ArrayList<>(response.getIntegers());
        Map<Account, Integer> accountIntegerMap = new HashMap<>();
        for (int i = 0; i < accountList.size(); i++) {
            accountIntegerMap.put(accountList.get(i), integerList.get(i));
        }
        Collections.sort(accountList);
        Platform.runLater(new Runnable() {
            FXMLLoader fxmlLoader;
            @Override
            public void run() {
                VBox vBox = findVBoxInLeaderBoard(ControllerMainMenu.stage);
                vBox.getChildren().clear();
                for (int i = 0; i < accountList.size(); i++) {
                    fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("ControllerLeaderBoardAccount.fxml"));
                    try {
                        Node node = fxmlLoader.load();
                        ControllerLeaderBoardAccount controllerLeaderBoardAccount = fxmlLoader.getController();
                        controllerLeaderBoardAccount.setAccountLabels(accountList.get(i), accountIntegerMap.get(accountList.get(i)));
                        vBox.getChildren().add(node);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private VBox findVBoxInLeaderBoard(Stage stage){
        Parent root = stage.getScene().getRoot();
        ScrollPane scrollPane = null;
        for (int i = 0; i < root.getChildrenUnmodifiable().size(); i++) {
            if (root.getChildrenUnmodifiable().get(i) instanceof ScrollPane){
                scrollPane = (ScrollPane) root.getChildrenUnmodifiable().get(i);
            }
        }
        return (VBox) scrollPane.getContent();
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