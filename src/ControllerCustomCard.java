import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCustomCard implements Initializable {

    @FXML
    private JFXTextField minionHptxt;

    @FXML
    private JFXTextField heroAPtxt;

    @FXML
    private JFXTextField heroHPtxt;

    @FXML
    private JFXTextField minionAptxt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String style = ("-fx-text-inner-color: white;");
        minionAptxt.setStyle(style);
        heroHPtxt.setStyle(style);
        heroAPtxt.setStyle(style);
        minionHptxt.setStyle(style);

    }
}
