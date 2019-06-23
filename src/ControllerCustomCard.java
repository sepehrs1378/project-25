import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCustomCard implements Initializable {
    private ObservableList<String> modeList = FXCollections.observableArrayList();
    @FXML
    private JFXComboBox<String> heroAttackTypeBox;

    @FXML
    private JFXComboBox<String> minionAttackTypeBox;

    @FXML
    private JFXTextField minionName;

    @FXML
    private JFXTextField minionHptxt;

    @FXML
    private JFXTextField heroAPtxt;

    @FXML
    private JFXTextField spellName;

    @FXML
    private JFXTextField heroName;

    @FXML
    private JFXTextField heroHPtxt;

    @FXML
    private JFXTextField minionAptxt;

    @FXML
    private JFXTextField minionRangetxt;

    @FXML
    private ImageView maxRangeHero;

    @FXML
    private ImageView maxRangeMinion;

    @FXML
    private JFXTextField heroRangetxt;

    @FXML
    void minionComboOnAction(ActionEvent event) {

        try {
            switch (minionAttackTypeBox.getValue()) {
                case Constants.MELEE:
                    maxRangeMinion.setVisible(false);
                    minionRangetxt.setVisible(false);
                    break;
                case Constants.HYBRID:
                    maxRangeMinion.setVisible(true);
                    minionRangetxt.setVisible(true);
                    break;
                case Constants.RANGED:
                    maxRangeMinion.setVisible(true);
                    minionRangetxt.setVisible(true);
                    break;
                default:
                    maxRangeMinion.setVisible(false);
                    minionRangetxt.setVisible(false);
                    break;
            }
        }catch (Throwable ignore){
        }
    }

    @FXML
    void heroComboOnAction(ActionEvent event) {
        switch (heroAttackTypeBox.getValue()) {
            case Constants.MELEE:
                maxRangeHero.setVisible(false);
                heroRangetxt.setVisible(false);
                break;
            case Constants.HYBRID:
                maxRangeHero.setVisible(true);
                heroRangetxt.setVisible(true);
                break;
            case Constants.RANGED:
                maxRangeHero.setVisible(true);
                heroRangetxt.setVisible(true);
                break;
            default:
                maxRangeHero.setVisible(false);
                heroRangetxt.setVisible(false);
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String style = ("-fx-text-inner-color: #c3c3c3;-fx-prompt-text-fill: #969696;");
        minionAptxt.setStyle(style);
        heroHPtxt.setStyle(style);
        heroAPtxt.setStyle(style);
        minionHptxt.setStyle(style);
        spellName.setStyle(style);
        heroName.setStyle(style);
        minionName.setStyle(style);
        heroAttackTypeBox.setStyle(style);
        minionAttackTypeBox.setStyle(style);
        modeList.add(Constants.MELEE);
        modeList.add(Constants.HYBRID);
        modeList.add(Constants.RANGED);
        minionAttackTypeBox.setItems(modeList);
        heroAttackTypeBox.setItems(modeList);
        minionRangetxt.setStyle(style);
        heroRangetxt.setStyle(style);
        maxRangeMinion.setVisible(false);
        minionRangetxt.setVisible(false);
        heroRangetxt.setVisible(false);
        maxRangeHero.setVisible(false);

    }
}
