import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ControllerSettingsMenu {
    private DataBase dataBase = DataBase.getInstance();
    private static ClientDB clientDB = ClientDB.getInstance();

    @FXML
    private JFXComboBox<String> timeComboBox;

    @FXML
    private ImageView backBtn;

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        clientDB.getLoggedInAccount().setTurnDuration(timeComboBox.getValue());
        Parent root = FXMLLoader.load(getClass().getResource("ControllerMainMenu.fxml"));
        Main.window.setScene(new Scene(root));
        Main.setCursor(Main.window);
    }

    @FXML
    void makeBackBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        backBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeBackBtnTransparent(MouseEvent event) {
        backBtn.setStyle("-fx-opacity: 0.6");
    }


    @FXML
    void chooseTime(MouseEvent event) {
        ObservableList<String> timeList = FXCollections.observableArrayList();
        timeList.add("30");
        timeList.add("60");
        timeList.add("90");
        timeList.add("120");
        timeList.add(Constants.NO_LIMIT);
        timeComboBox.setItems(timeList);
    }
}
