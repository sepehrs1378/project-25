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
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class

Main extends Application {
    public static Stage window;
    private static MediaPlayer globalMediaPlayer;
    private static double xOffset = 0;
    private static double yOffset = 0;

    @FXML
    private AnchorPane loginPane;

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
        if (password.getText().isEmpty()) {
            invalidPassword.setText("password is empty");
            return;
        }
        new ServerRequestSender(new Request(RequestType.signUp, "userName:" + username.getText() + "password:"
                + password.getText(), null, null)).start();
    }

    public static void main(String[] args) {
        String address = "localhost";
//        DataBase.getInstance().makeEveryThing();
        ClientDB.getInstance();
        ServerHandler serverHandler = new ServerHandler(address, 5555);
        serverHandler.start();
        launch(args);
    }

    public static void setCursor(Stage stage) {
        File file = new File("src/pics/mouse_icon");
        Image image = new Image(file.toURI().toString());
        stage.getScene().setCursor(new ImageCursor(image));
    }

    @FXML
    void login(MouseEvent event) throws IOException {
        playWhenButtonClicked();
        if (emptyInvalidUsername()) return;
        new ServerRequestSender(new Request(RequestType.login, "userName:" + username.getText() + "password:"
                + password.getText(), null, null)).start();
    }

    private boolean emptyInvalidUsername() {
        invalidUsername.setText("");
        invalidPassword.setText("");
        if (username.getText().isEmpty()) {
            invalidUsername.setText("username is empty");
            return true;
        }
        if (password.getText().isEmpty()) {
            invalidPassword.setText("password is empty");
            return true;
        }
        return false;
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
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("ControllerAccount.fxml"));
        primaryStage.setTitle("Duelyst");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        setCursor(primaryStage);
        globalMediaPlayer = playMedia("src/music/mainMenu.mp3", Duration.INDEFINITE
                , Integer.MAX_VALUE, true, 100);
        dragAbilityForScenes(primaryStage, root);
        primaryStage.setOnCloseRequest(e -> {
            new ServerRequestSender(new Request(RequestType.logout, "userName:" + ClientDB.getInstance().getLoggedInAccount().getUsername()
                    , null, null)).start();
            primaryStage.close();
        });
        primaryStage.show();
    }

    public static void dragAbilityForScenes(Stage primaryStage, Parent root) {
        root.setOnMousePressed(event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });
    }

    public static MediaPlayer playMedia(String filePath, Duration stopDuration, int cycleCount
            , boolean autoPlay, double volume) {
        MediaPlayer mediaPlayer = null;
        try {
            Media media = new Media(Paths.get(filePath).toUri().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(cycleCount);
            mediaPlayer.setAutoPlay(autoPlay);
            mediaPlayer.setStopTime(stopDuration);
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaPlayer;
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