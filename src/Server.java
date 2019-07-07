import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.nio.ch.Net;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;

public class Server extends Application implements Initializable {
    public static Stage window = null;
    private static double xOffset = 0;
    private static double yOffset = 0;
    private static Server instance;
    public Server(){
        instance = this;
    }
    public static Server getInstance(){
        return instance;
    }

    @FXML
    private ListView<String> cardList;

    @FXML
    private ListView<String> userList;

    @FXML
    private ImageView customCardBtn;

    @FXML
    private ImageView saveAccountsBtn;

    @FXML
    void makeSaveAccountsBtnTransparent(MouseEvent event) {
        saveAccountsBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeSaveAccountsBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        saveAccountsBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void saveAccounts(MouseEvent event) {
        Main.playWhenButtonClicked();
        NetworkDB.getInstance().saveAccounts();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Accounts Have Been Successfully Saved");
        alert.showAndWait();
    }

    @FXML
    void makeCustomCardBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        customCardBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCustomCardBtnTransparent(MouseEvent event) {
        customCardBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void enterCustomCardMenu(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerCustomCard.fxml"));
        Server.window.setScene(new Scene(root));
        Server.setCursor(Server.window);
    }

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                NetworkDB.getInstance().readAccounts();
                ServerSocket serverSocket = new ServerSocket(5555);
                while (true) {
                    Socket socket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(socket);
                    clientHandler.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Server.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        dragAbilityForScenes(primaryStage, root);
        setCursor(primaryStage);
        primaryStage.show();
    }

    private void dragAbilityForScenes(Stage primaryStage, Parent root) {
        root.setOnMousePressed(event -> {
            yOffset = primaryStage.getY() - event.getScreenY();
            xOffset = primaryStage.getX() - event.getScreenX();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });
    }

    public static void setCursor(Stage stage) {
        File file = new File("src/pics/mouse_icon");
        Image image = new Image(file.toURI().toString());
        stage.getScene().setCursor(new ImageCursor(image));
    }

    public void updateUserList(){
        userList.getItems().clear();
        NetworkDB.getInstance().getAccountStatusMap().forEach((account, accountStatus) -> {
            userList.getItems().add(account.getUsername()+"     "+accountStatus.toString());
        });
    }

    public void updateCardList(){
        cardList.getItems().clear();
        NetworkDB.getInstance().getNumberOfCards().forEach((s, integer) -> {
            cardList.getItems().add(s+"    "+integer);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NetworkDB.getInstance().makeEveryThing();
        updateCardList();
        updateUserList();
    }
}