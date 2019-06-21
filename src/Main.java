import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.File;
import java.io.IOException;

public class Main extends Application {
    private DataBase dataBase = DataBase.getInstance();
    public static Stage window;

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
        closeBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCloseBtnTransparent(MouseEvent event) {
        closeBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeSignUpOpaque(MouseEvent event) {
        signUpBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeSignUpTransparent(MouseEvent event) {
        signUpBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void signUp(MouseEvent event) throws IOException {
        invalidPassword.setText("");
        invalidUsername.setText("");
        if (username.getText().isEmpty()){
            invalidUsername.setText("username is empty");
            return;
        }
        int index = findIndexOfAccount(username.getText());
        if (index != -1){
             invalidUsername.setText("username already exists");
             return;
        }
        if (password.getText().isEmpty()){
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

    public static void setCursor() {
        File file = new File("src/pics/cursors/main_cursor.png");
        Image image = new Image(file.toURI().toString());
        window.getScene().setCursor(new ImageCursor(image));
    }
    @FXML
    void login(MouseEvent event) throws IOException {
        if (emptyInvalidUsername()) return;
        int index = findIndexOfAccount(username.getText());
        if (index == -1){
            invalidUsername.setText("account does not exist");
            return;
        }
        if (!dataBase.getAccounts().get(index).getPassword().equals(password.getText())){
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
        if (username.getText().isEmpty()){
            invalidUsername.setText("username is empty");
            return true;
        }
        return false;
    }

    private int  findIndexOfAccount(String userName){
        for (int i = 0; i < dataBase.getAccounts().size(); i++) {
            if (dataBase.getAccounts().get(i).getUsername().equals(userName)){
                return i;
            }
        }
        return -1;
    }

    @FXML
    void makeLoginOpaque(MouseEvent event) {
        loginBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeLoginTransparent(MouseEvent event) {
        loginBtn.setStyle("-fx-opacity: 0.6");
    }

    public static void main(String[] args) {
        DataBase.getInstance().makeEveryThing();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DataBase.getInstance().readAccounts();
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("ControllerAccount.fxml"));
        primaryStage.setTitle("Duelyst");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        File file = new File("src/pics/cursors/main_cursor.png");
        Image image = new Image(file.toURI().toString());
        primaryStage.getScene().setCursor(new ImageCursor(image));
        primaryStage.setOnCloseRequest(e->{
            DataBase.getInstance().saveAccounts();
            primaryStage.close();
        });
        primaryStage.show();
    }
}
