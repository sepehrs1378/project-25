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

public class ServerHandler extends Thread {
    private static ClientDB clientDB = ClientDB.getInstance();
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
            clientDB.setSocket(socket);
            JsonStreamParser parser = ClientDB.getInstance().getParser();
            JsonObject obj;
            YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
            while (parser.hasNext()) {
                obj = parser.next().getAsJsonObject();
                Response response = yaGson.fromJson(obj.toString(), Response.class);
                handleAccountCase(response);
                handleMatchFoundCase(response);
                handleMultiPlayerCase(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMultiPlayerCase(Response response) {
        switch (response.getResponseType()) {
            case unitMoved:
                handleUnitMovedCase(response);
            case unitSelected:
                UnitImage selectedUnit = ControllerBattleCommands.getOurInstance().getUnitImageWithId(response.getMessage());
                ControllerBattleCommands.getOurInstance().setClickedImageView();
                break;
            default:
        }
    }

    private void handleUnitMovedCase(Response response) {
        clientDB.setCurrentBattle((Battle) response.getObjectList().get(2));
        String id = response.getMessage();
        Integer row = (Integer) response.getObjectList().get(0);
        Integer column = (Integer) response.getObjectList().get(1);
        UnitImage movedUnit = ControllerBattleCommands.getOurInstance().getUnitImageWithId(id);
        movedUnit.showRun(row, column);
    }

    private void handleMatchFoundCase(Response response) {
        if (response.getResponseType().equals(ResponseType.matchFound)) {
            clientDB.setCurrentBattle((Battle) response.getObjectList().get(0));
            Player player1 = clientDB.getCurrentBattle().getPlayer1();
            if (clientDB.getLoggedInAccount().getUsername().equals(player1.getPlayerInfo().getPlayerName()))
                clientDB.setLoggedInPlayer(player1);
            else clientDB.setLoggedInPlayer(clientDB.getCurrentBattle().getPlayer2());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Main.playWhenButtonClicked();
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("ControllerBattleCommandsFXML.fxml"));
                        Main.window.setScene(new Scene(root));
                        Main.setCursor(Main.window);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void handleAccountCase(Response response) {
        Account account;
        switch (response.getResponseType()) {
            case signUp:
                if (response.getMessage().equals(OutputMessageType.CREATED_ACCOUNT_SUCCESSFULLY.getMessage())) {
                    account = (Account) response.getObjectList().get(0);
                    ClientDB.getInstance().setLoggedInAccount(account);
                    openMainMenu();
                } else if (response.getMessage().equals(OutputMessageType.USERNAME_ALREADY_EXISTS.getMessage())) {
                    Label label = findInvalidUserName(Main.window, "loginPane", "invalidUsername");
                    if (label != null) {
                        Platform.runLater(() -> label.setText(OutputMessageType.USERNAME_ALREADY_EXISTS.getMessage()));
                    }
                }
                break;
            case login:
                if (response.getMessage().equals(OutputMessageType.LOGGED_IN_SUCCESSFULLY.getMessage())) {
                    account = (Account) response.getObjectList().get(0);
                    ClientDB.getInstance().setLoggedInAccount(account);
                    openMainMenu();
                } else if (response.getMessage().equals(OutputMessageType.INVALID_PASSWORD.getMessage())) {
                    Label label = findInvalidUserName(Main.window, "loginPane", "invalidPassword");
                    if (label != null) {
                        Platform.runLater(() -> label.setText(OutputMessageType.INVALID_PASSWORD.getMessage()));
                    }
                } else if (response.getMessage().equals(OutputMessageType.ACCOUNT_DOESNT_EXIST.getMessage())) {
                    Label label = findInvalidUserName(Main.window, "loginPane", "invalidUsername");
                    if (label != null) {
                        Platform.runLater(() -> label.setText(OutputMessageType.ACCOUNT_DOESNT_EXIST.getMessage()));
                    }
                } else if (response.getMessage().equals(OutputMessageType.ALREADY_LOGGED_IN.getMessage())) {
                    Label label = findInvalidUserName(Main.window, "loginPane", "invalidUsername");
                    if (label != null) {
                        Platform.runLater(() -> label.setText(OutputMessageType.ALREADY_LOGGED_IN.getMessage()));
                    }
                }
                break;
            case logout:
                if (response.getMessage().equals(OutputMessageType.LOGGED_OUT_SUCCESSFULLY.getMessage())) {
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
            default:
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

    private Label findInvalidUserName(Stage stage, String paneID, String labelID) {
        AnchorPane anchorPane = null;
        for (Object object : stage.getScene().getRoot().getChildrenUnmodifiable()) {
            if (object instanceof AnchorPane) {
                anchorPane = (AnchorPane) object;
                if (anchorPane.getId().equals(paneID)) {
                    break;
                }
            }
        }
        if (anchorPane != null) {
            for (Object object : anchorPane.getChildren()) {
                if (object instanceof Label) {
                    Label label = (Label) object;
                    if (label.getId().equals(labelID)) {
                        return label;
                    }
                }
            }
        }
        return null;
    }
}