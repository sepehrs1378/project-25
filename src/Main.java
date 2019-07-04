import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Main extends Application {
    private DataBase dataBase = DataBase.getInstance();
    public static Stage window;
    private static MediaPlayer globalMediaPlayer;

    @FXML
    private Label invalidUsername;

    @FXML
    private Label invalidPassword;

    @FXML
    private ImageView loginBtn;

    @FXML
    private ImageView signUpBtn;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private ImageView closeBtn;

    @FXML
    void close(MouseEvent event) {
        Main.window.close();
    }

    @FXML
    void makeCloseBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        closeBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCloseBtnTransparent(MouseEvent event) {
        closeBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeSignUpOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        signUpBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeSignUpTransparent(MouseEvent event) {
        signUpBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void signUp(MouseEvent event) throws IOException {
        playWhenButtonClicked();
        invalidPassword.setText("");
        invalidUsername.setText("");
        if (username.getText().isEmpty()) {
            invalidUsername.setText("username is empty");
            return;
        }
        int index = findIndexOfAccount(username.getText());
        if (index != -1) {
            invalidUsername.setText("username already exists");
            return;
        }
        if (password.getText().isEmpty()) {
            invalidPassword.setText("password is empty");
            return;
        }
        Account account = new Account(username.getText(), password.getText());
        dataBase.setLoggedInAccount(account);
        dataBase.addAccount(account);
        Parent root = FXMLLoader.load(getClass().getResource("ControllerMainMenu.fxml"));
        window.setScene(new Scene(root));
        setCursor();
    }

    public static void main(String[] args) {
        String address = "localhost";
        DataBase.getInstance().makeEveryThing();
        ClientDB.getInstance();
        ServerHandler serverHandler = new ServerHandler(address, 5555);
        serverHandler.start();
        launch(args);
    }

    public static void setCursor() {
        File file = new File("src/pics/mouse_icon");
        Image image = new Image(file.toURI().toString());
        window.getScene().setCursor(new ImageCursor(image));
    }

    @FXML
    void login(MouseEvent event) throws IOException {
        playWhenButtonClicked();
        new ServerRequestSender(new Request(RequestType.sendMessage, "hello", null, null)).start();
        if (emptyInvalidUsername()) return;
        int index = findIndexOfAccount(username.getText());
        if (index == -1) {
            invalidUsername.setText("account does not exist");
            return;
        }
        if (!dataBase.getAccounts().get(index).getPassword().equals(password.getText())) {
            invalidPassword.setText("incorrect password");
            return;
        }
        dataBase.setLoggedInAccount(dataBase.getAccounts().get(index));
        Parent root = FXMLLoader.load(getClass().getResource("ControllerMainMenu.fxml"));
        window.setScene(new Scene(root));
        setCursor();
    }

    private boolean emptyInvalidUsername() {
        invalidUsername.setText("");
        invalidPassword.setText("");
        if (username.getText().isEmpty()) {
            invalidUsername.setText("username is empty");
            return true;
        }
        return false;
    }

    private int findIndexOfAccount(String userName) {
        for (int i = 0; i < dataBase.getAccounts().size(); i++) {
            if (dataBase.getAccounts().get(i).getUsername().equals(userName)) {
                return i;
            }
        }
        return -1;
    }

    @FXML
    void makeLoginOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        loginBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeLoginTransparent(MouseEvent event) {
        loginBtn.setStyle("-fx-opacity: 0.6");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DataBase.getInstance().readAccounts();
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("ControllerAccount.fxml"));
        primaryStage.setTitle("Duelyst");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        setCursor();
        playMusic();
        primaryStage.setOnCloseRequest(e -> {
            DataBase.getInstance().saveAccounts();
            primaryStage.close();
        });
        primaryStage.show();
    }

    private static void playMusic() {
        try {
            Media media = new Media(Paths.get("src/music/mainMenu.mp3").toUri().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(Integer.MAX_VALUE);
            mediaPlayer.setAutoPlay(true);
            globalMediaPlayer = mediaPlayer;
            mediaPlayer.play();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    public static void playWhenButtonClicked() {
        Media media = new Media(Paths.get("src/music/button_clicked.m4a").toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void playWhenMouseEntered() {
        Media media = new Media(Paths.get("src/music/mouse_entered.m4a").toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static MediaPlayer getGlobalMediaPlayer() {
        return globalMediaPlayer;
    }
}