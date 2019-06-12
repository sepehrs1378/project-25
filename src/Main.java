import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage window;

    @FXML
    private ImageView loginBtn;

    @FXML
    private ImageView signUpBtn;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    void makeSignUpOpaque(MouseEvent event) {
        signUpBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeSignUpTransparent(MouseEvent event) {
        signUpBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void signUp(MouseEvent event) {
        //todo
    }

    @FXML
    void login(MouseEvent event) {
        //todo
    }

//    private void enterNextPage(){
//        Parent root = FXMLLoader.load(getClass().getResource(""))
//        window.setScene();
//    }

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
        Parent root = FXMLLoader.load(getClass().getResource("ControllerAccount.fxml"));
        primaryStage.setTitle("Duelyst");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
