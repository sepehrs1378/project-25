import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCustomCard implements Initializable {
    private ObservableList<String> modeList = FXCollections.observableArrayList();
    private ObservableList<String> buffTypeList = FXCollections.observableArrayList();
    private ObservableList<SpellActivationType> activationTypeList = FXCollections.observableArrayList();
    private ObservableList<String> friendOrEnemyList = FXCollections.observableArrayList();
    private ObservableList<String> targetTypeList = FXCollections.observableArrayList();
    private ObservableList<String> targetClassList = FXCollections.observableArrayList();


    @FXML
    private JFXTextField minionName;

    @FXML
    private JFXTextField minionCosttxt;

    @FXML
    private ImageView maxRangeHero;

    @FXML
    private JFXTextField minionRangetxt;

    @FXML
    private JFXTextField spellCosttxt;

    @FXML
    private JFXButton minionAddSpecialbtn;

    @FXML
    private JFXTextField heroCosttxt;

    @FXML
    private JFXTextField heroHPtxt;

    @FXML
    private JFXTextField heroRangetxt;

    @FXML
    private JFXTextField minionAptxt;

    @FXML
    private JFXTextField minionHptxt;

    @FXML
    private JFXTextField heroAPtxt;

    @FXML
    private JFXButton heroAddSpecialbtn;

    @FXML
    private JFXTextField buffValueTxt;

    @FXML
    private JFXTextField spellName;

    @FXML
    private ImageView maxRangeMinion;

    @FXML
    private JFXComboBox<String> friendOrEnemyCmb;

    @FXML
    private JFXTextField buffDelayTxt;

    @FXML
    private JFXTextField buffLasTxt;

    @FXML
    private JFXComboBox<String> buffTypeCmb;

    @FXML
    private JFXComboBox<SpellActivationType> activationTypecombox;

    @FXML
    private JFXTextField manhatanTxt;

    @FXML
    private JFXComboBox<String> heroAttackTypeBox;

    @FXML
    private JFXTextField activationCoolDown;

    @FXML
    private JFXTextField heroName;

    @FXML
    private JFXComboBox<String> minionAttackTypeBox;

    @FXML
    private JFXTextField targetWidthTxt;

    @FXML
    private JFXComboBox<String> targetClassCmb;

    @FXML
    private JFXComboBox<String> targetTypeCmb;

    @FXML
    private JFXTextField targetHeigthTxt;

    @FXML
    private ImageView createSpellBtn;

    @FXML
    private ImageView createMinionBtn;

    @FXML
    private ImageView createHeroBtn;

    @FXML
    void createSpell(MouseEvent event) {
    }

    @FXML
    void makeCreateSpellOpeque(MouseEvent event) {
        createSpellBtn.setOpacity(1);
    }

    @FXML
    void makeCreateSpellTransparent(MouseEvent event) {
        createSpellBtn.setOpacity(0.6);
    }

    @FXML
    void createHero(MouseEvent event) {

    }

    @FXML
    void makeCreateHeroOpeque(MouseEvent event) {
        createHeroBtn.setOpacity(1);
    }

    @FXML
    void makeCreateHeroTransparent(MouseEvent event) {
        createHeroBtn.setOpacity(0.6);
    }

    @FXML
    void createMinion(MouseEvent event) {

    }

    @FXML
    void makeCreateMinionOpeque(MouseEvent event) {
        createMinionBtn.setOpacity(1);
    }

    @FXML
    void makeCreateMinionTransparent(MouseEvent event) {
        createMinionBtn.setOpacity(0.6);
    }

    @FXML
    void addBuffToSpell(ActionEvent event) {

    }

    @FXML
    void minionComboOnAction(ActionEvent event) {
        minionAttackTypeBox.setStyle("-fx-background-color: #7da8ed;");
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
        } catch (Throwable ignore) {
        }
    }

    @FXML
    void heroComboOnAction(ActionEvent event) {
        heroAttackTypeBox.setStyle("-fx-background-color: #7da8ed;");
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

    @FXML
    void minioinEditSpecialPower(ActionEvent event) {
        //todo open edit page
    }

    @FXML
    void heroEditSpecialPower(ActionEvent event) {
        //todo open edit page
    }

    @FXML
    void activastionTypeCmbSelected(ActionEvent event) {
        activationTypecombox.setStyle("-fx-background-color: #7da8ed;");
    }

    @FXML
    void friendOrEnemyCmbSelected(ActionEvent event) {
        friendOrEnemyCmb.setStyle("-fx-background-color: #7da8ed;");
    }

    @FXML
    void targetClassCmbSelected(ActionEvent event) {
        targetClassCmb.setStyle("-fx-background-color: #7da8ed;");
    }

    @FXML
    void targetTypeCmbSelected(ActionEvent event) {
        targetTypeCmb.setStyle("-fx-background-color: #7da8ed;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String style = ("-fx-text-inner-color: #c3c3c3;-fx-prompt-text-fill: #969696;");
        String styleBuff = ("-fx-text-inner-color: #3f3434;-fx-prompt-text-fill: #3f3434;");
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
        minionCosttxt.setStyle(style);
        maxRangeHero.setVisible(false);
        activationTypecombox.setStyle(style);
        spellCosttxt.setStyle(style);
        activationCoolDown.setStyle(style);
        heroCosttxt.setStyle(style);
        buffDelayTxt.setStyle(styleBuff);
        buffLasTxt.setStyle(styleBuff);
        buffTypeCmb.setStyle(styleBuff);
        buffValueTxt.setStyle(styleBuff);
        friendOrEnemyCmb.setStyle(style);
        friendOrEnemyList.add(Constants.FRIEND);
        friendOrEnemyList.add(Constants.ENEMY);
        friendOrEnemyList.add(Constants.ALL);
        friendOrEnemyCmb.setItems(friendOrEnemyList);

        activationTypeList.add(SpellActivationType.PASSIVE);
        activationTypeList.add(SpellActivationType.ON_CAST);
        activationTypeList.add(SpellActivationType.ON_ATTACK);
        activationTypeList.add(SpellActivationType.ON_SPAWN);
        activationTypeList.add(SpellActivationType.ON_DEFEND);
        activationTypeList.add(SpellActivationType.ON_DEATH);
        activationTypeList.add(SpellActivationType.ON_CARD_INSERTION);
        activationTypecombox.setItems(activationTypeList);

        buffTypeList.add("Holy Buff");
        buffTypeList.add("Power Buff");
        buffTypeList.add("Poison Buff");
        buffTypeList.add("Weakness Buff");
        buffTypeList.add("Stun Buff");
        buffTypeList.add("Disarm Buff");
        buffTypeCmb.setItems(buffTypeList);

        targetClassCmb.setStyle(style);
        targetHeigthTxt.setStyle(style);
        targetTypeCmb.setStyle(style);
        targetWidthTxt.setStyle(style);
        targetTypeList.add(Constants.MINION);
        targetTypeList.add(Constants.HERO);
        targetTypeList.add(Constants.HERO_MINION);
        targetTypeCmb.setItems(targetTypeList);

        targetClassList.add(Constants.MELEE);
        targetClassList.add(Constants.HYBRID);
        targetClassList.add(Constants.RANGED);
        targetClassList.add(Constants.MELEE_HYBRID);
        targetClassList.add(Constants.MELLE_RANGED);
        targetClassList.add(Constants.RANGED_HYBRID);
        targetClassList.add(Constants.ALL);
        targetClassCmb.setItems(targetClassList);

        manhatanTxt.setStyle(style);



    }
}
