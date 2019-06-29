import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCustomCard implements Initializable {
    private ObservableList<String> modeList = FXCollections.observableArrayList();
    private ObservableList<String> buffTypeList = FXCollections.observableArrayList();
    private ObservableList<SpellActivationType> activationTypeList = FXCollections.observableArrayList();
    private ObservableList<String> friendOrEnemyList = FXCollections.observableArrayList();
    private ObservableList<String> targetTypeList = FXCollections.observableArrayList();
    private ObservableList<String> targetClassList = FXCollections.observableArrayList();
    private Spell minionSpell;
    private Spell heroSpell;
    private List<Buff> spellBuffs = new ArrayList<>();


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
    private JFXTextField spellManaTxt;

    @FXML
    private JFXTextField minionManaTxt;

    @FXML
    private JFXTextField heroManaTxt;

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
    private JFXButton addBuffBtn;

    @FXML
    private JFXButton clearBuffsBtn;

    @FXML
    private VBox buffBox;

    @FXML
    void clearBuffs(ActionEvent event) {
        spellBuffs.clear();
        showBuffsInBuffBox();
    }

    @FXML
    void createSpell(MouseEvent event) {
        Spell spell = makeSpell();
        if (spell == null)
            return;
        DataBase.getInstance().getCardList().add(spell);
        DataBase.getInstance().saveCutsomCard(spell);
    }

    private Spell makeSpell() {
        if (targetTypeCmb.getValue() == null || targetClassCmb.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "please select Type and Class of targets").showAndWait();
            return null;
        }
        if (manhatanTxt.getText().equals("") || targetWidthTxt.getText().equals("") || targetHeigthTxt.getText().equals("")
                || spellCosttxt.getText().equals("") || spellName.getText().equals("") || friendOrEnemyCmb.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "you must fill all the fields!").showAndWait();
            return null;
        }
        if (manhatanTxt.getText().matches("\\d+") || targetWidthTxt.getText().matches("\\d+") || targetHeigthTxt.getText().matches("\\d+")
                || spellCosttxt.getText().matches("\\d+") || spellName.getText().matches("\\w+")) {
            new Alert(Alert.AlertType.ERROR, "invalid input!").showAndWait();
            return null;
        }
        if (!isNameUnique(spellName.getText())) {
            new Alert(Alert.AlertType.ERROR,"choosed name already exists please select another name for this spell").showAndWait();
            return null;
        }
        Target target = new Target(targetTypeCmb.getValue(), Integer.parseInt(targetWidthTxt.getText())
                , Integer.parseInt(targetHeigthTxt.getText()), friendOrEnemyCmb.getValue(), false,
                false, Integer.parseInt(manhatanTxt.getText()), targetClassCmb.getValue());
        String id = "shop_" + spellName.getText() + "_0";
        Spell spell = new Spell(id, spellName.getText(), Integer.parseInt(spellCosttxt.getText()),
                Integer.parseInt(spellManaTxt.getText()), 0, 0, 0
                , target, new ArrayList<>(spellBuffs), SpellActivationType.ON_CAST, "", false);
        spellBuffs.clear();
        return spell;
    }

    @FXML
    void makeCreateSpellOpaque(MouseEvent event) {
        createSpellBtn.setOpacity(1);
    }

    @FXML
    void makeCreateSpellTransparent(MouseEvent event) {
        createSpellBtn.setOpacity(0.6);
    }

    @FXML
    void createHero(MouseEvent event) {
        if (heroCosttxt.getText().equals("") || heroAPtxt.getText().equals("") || heroHPtxt.getText().equals("")
                || heroName.getText().equals("") || heroManaTxt.getText().equals("") || heroAttackTypeBox.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "mana, Attack Value, cost, name, HP and AP must be filled").showAndWait();
            return;
        }
        if (heroSpell != null && activationCoolDown.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "please select an activation CoolDown for your SpecialPower").showAndWait();
            return;
        }
        if (heroAttackTypeBox.getValue() != null
                && (heroAttackTypeBox.getValue().equals(Constants.HYBRID) || heroAttackTypeBox.getValue().equals(Constants.RANGED))
                && heroRangetxt.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "please select a maximum range for your minion!").showAndWait();
            return;
        }
        if (!heroAPtxt.getText().matches("\\d+") || !heroHPtxt.getText().matches("\\d+")
                || !heroCosttxt.getText().matches("\\d+") || !heroRangetxt.getText().matches("\\d*")
                || !heroManaTxt.getText().matches("\\d+") || !activationCoolDown.getText().matches("\\w*")) {
            new Alert(Alert.AlertType.ERROR, "invalid input in AP, mana, cooldown, HP, Range or cost").showAndWait();
            return;
        }
        if (!heroName.getText().matches("[a-zA-z]+")) {
            new Alert(Alert.AlertType.ERROR, "name box can only include a-z and A-Z").showAndWait();
            return;
        }
        if (!isNameUnique(heroName.getText())) {
            new Alert(Alert.AlertType.ERROR, "hero name already exists please select a new name").showAndWait();
            return;
        }
        String id = "shop_" + heroName.getText() + "_0";
        int minRange = 0;
        int maxRange = 0;
        switch (heroAttackTypeBox.getValue()) {
            case Constants.MELEE:
                minRange = 1;
                maxRange = 1;
                break;
            case Constants.RANGED:
                minRange = 2;
                maxRange = Integer.parseInt(heroRangetxt.getText());
                break;
            case Constants.HYBRID:
                minRange = 1;
                maxRange = Integer.parseInt(heroRangetxt.getText());
                break;
        }
        if (heroSpell != null) {
            heroSpell.setActivationType(SpellActivationType.ON_CAST);
            heroSpell.setCoolDown(Integer.parseInt(activationCoolDown.getText()));
        }
        Unit unit = new Unit(id, heroName.getText(), Integer.parseInt(heroCosttxt.getText()),
                Integer.parseInt(heroCosttxt.getText()), Integer.parseInt(heroHPtxt.getText()),
                Integer.parseInt(heroAPtxt.getText()), minRange, maxRange, heroSpell
                , Constants.HERO, "", false);
        DataBase.getInstance().getCardList().add(unit);
        heroSpell = null;
        spellBuffs.clear();
        clearEveryThing();
        DataBase.getInstance().saveCutsomCard(unit);
        new Alert(Alert.AlertType.INFORMATION, "hero created successfully!").showAndWait();
    }

    @FXML
    void makeCreateHeroOpaque(MouseEvent event) {
        createHeroBtn.setOpacity(1);
    }

    @FXML
    void makeCreateHeroTransparent(MouseEvent event) {
        createHeroBtn.setOpacity(0.6);
    }

    @FXML
    void createMinion(MouseEvent event) {
        if (minionCosttxt.getText().equals("") || minionHptxt.getText().equals("")
                || minionAptxt.getText().equals("") || minionName.getText().equals("") || minionAttackTypeBox.getValue() == null
                || minionManaTxt.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "name, mana, AP, HP, Attack Type and cost must be filled ").showAndWait();
            return;
        }
        if (minionSpell != null && activationTypecombox.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "please select an activation type for created special power!").showAndWait();
            return;
        }
        if (minionAttackTypeBox.getValue() != null
                && (minionAttackTypeBox.getValue().equals(Constants.RANGED) || minionAttackTypeBox.getValue().equals(Constants.HYBRID))
                && minionRangetxt.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "please select a maximum range for your minion!").showAndWait();
            return;
        }
        if (!minionAptxt.getText().matches("\\d+") || !minionHptxt.getText().matches("\\d+")
                || !minionCosttxt.getText().matches("\\d+") || !minionRangetxt.getText().matches("\\d*")
                || !minionManaTxt.getText().matches("\\d+")) {
            new Alert(Alert.AlertType.ERROR, "invalid input in AP, mana, HP, Range or cost").showAndWait();
            return;
        }
        if (!minionName.getText().matches("[a-zA-z]+")) {
            new Alert(Alert.AlertType.ERROR, "name box can only include a-z and A-Z").showAndWait();
            return;
        }
        if (!isNameUnique(minionName.getText())) {
            new Alert(Alert.AlertType.ERROR, "minion name already exists please select a new name").showAndWait();
            return;
        }
        String id = "shop_" + minionName.getText() + "_0";
        int minRange = 0;
        int maxRange = 0;
        switch (minionAttackTypeBox.getValue()) {
            case Constants.MELEE:
                minRange = 1;
                maxRange = 1;
                break;
            case Constants.RANGED:
                minRange = 2;
                maxRange = Integer.parseInt(minionRangetxt.getText());
                break;
            case Constants.HYBRID:
                minRange = 1;
                maxRange = Integer.parseInt(minionRangetxt.getText());
                break;
        }
        if (minionSpell != null) {
            minionSpell.setActivationType(activationTypecombox.getValue());
        }
        Unit unit = new Unit(id, minionName.getText(), Integer.parseInt(minionCosttxt.getText()),
                Integer.parseInt(minionCosttxt.getText()), Integer.parseInt(minionHptxt.getText()),
                Integer.parseInt(minionAptxt.getText()), minRange, maxRange, minionSpell
                , Constants.MINION, "", false);
        DataBase.getInstance().getCardList().add(unit);
        minionSpell = null;
        spellBuffs.clear();
        clearEveryThing();
        DataBase.getInstance().saveCutsomCard(unit);
        new Alert(Alert.AlertType.INFORMATION, "minion created successfully!").showAndWait();
    }

    private boolean isNameUnique(String name) {
        for (Card card : DataBase.getInstance().getCardList()) {
            if (card.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void makeCreateMinionOpaque(MouseEvent event) {
        createMinionBtn.setOpacity(1);
    }

    @FXML
    void makeCreateMinionTransparent(MouseEvent event) {
        createMinionBtn.setOpacity(0.6);
    }

    @FXML
    void addBuffToSpell(ActionEvent event) {
        if (buffTypeCmb.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "please first select a type for your buff").showAndWait();
            return;
        }
        if (buffValueTxt.getText().equals("") || buffLasTxt.getText().equals("") || buffDelayTxt.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "you must fill Effect Value, Delay and Duration").showAndWait();
            return;
        }
        if (!buffValueTxt.getText().matches("\\d+") || !buffLasTxt.getText().matches("\\d+")
                || !buffDelayTxt.getText().matches("\\d+")) {
            new Alert(Alert.AlertType.ERROR, "delay and duration can only contain numbers").showAndWait();
            return;
        }
        switch (buffTypeCmb.getValue()) {
            case "Holy Buff":
                HolyBuff holyBuff = new HolyBuff(Integer.parseInt(buffLasTxt.getText()), false, false,
                        Integer.parseInt(buffValueTxt.getText()), Integer.parseInt(buffDelayTxt.getText()));
                spellBuffs.add(holyBuff);
                break;
            case "Power Buff":
                PowerBuff powerBuff = new PowerBuff(Integer.parseInt(buffLasTxt.getText()), false, false,
                        Integer.parseInt(buffValueTxt.getText()), Integer.parseInt(buffValueTxt.getText())
                        , Integer.parseInt(buffDelayTxt.getText()));
                spellBuffs.add(powerBuff);
                break;
            case "Poison Buff":
                PoisonBuff poisonBuff = new PoisonBuff(Integer.parseInt(buffLasTxt.getText()), false, false,
                        Integer.parseInt(buffValueTxt.getText()), Integer.parseInt(buffDelayTxt.getText()));
                spellBuffs.add(poisonBuff);
                break;
            case "Weakness Buff":
                WeaknessBuff weaknessBuff = new WeaknessBuff(Integer.parseInt(buffLasTxt.getText()), false, false
                        , Integer.parseInt(buffDelayTxt.getText()), Integer.parseInt(buffValueTxt.getText())
                        , Integer.parseInt(buffValueTxt.getText())
                );
                spellBuffs.add(weaknessBuff);
                break;
            case "Stun Buff":
                StunBuff stunBuff = new StunBuff(Integer.parseInt(buffLasTxt.getText()), false, false,
                        Integer.parseInt(buffDelayTxt.getText()));
                spellBuffs.add(stunBuff);
                break;
            case "Disarm Buff":
                DisarmBuff disarmBuff = new DisarmBuff(Integer.parseInt(buffLasTxt.getText()), false, false,
                        Integer.parseInt(buffDelayTxt.getText()));
                spellBuffs.add(disarmBuff);
        }
        showBuffsInBuffBox();
    }

    private void showBuffsInBuffBox(){
        buffBox.getChildren().clear();
        for (int i = 0; i < spellBuffs.size(); i++) {
            Label label = new Label();
            label.setAlignment(Pos.CENTER);
            label.setTextFill(Color.WHITE);
            label.setPrefWidth(177);
            label.setStyle("-fx-border-color: #dde0bc; -fx-font-style: italic; -fx-font-weight: bold");
            if (spellBuffs.get(i) instanceof HolyBuff){
                label.setText(Constants.HOLY_BUFF);
            }else if(spellBuffs.get(i) instanceof  PowerBuff){
                label.setText(Constants.POWER_BUFF);
            }else if (spellBuffs.get(i) instanceof PoisonBuff){
                label.setText(Constants.POISON_BUFF);
            }else if(spellBuffs.get(i) instanceof DisarmBuff){
                label.setText(Constants.DISARM_BUFF);
            }else if (spellBuffs.get(i) instanceof WeaknessBuff){
                label.setText(Constants.WEAKNESS_BUFF);
            }else if(spellBuffs.get(i) instanceof StunBuff){
                label.setText(Constants.STUN_BUFF);
            }
            buffBox.getChildren().add(label);
        }
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
    void minionEditSpecialPower(ActionEvent event) {
        Spell spell = makeSpell();
        if (spell != null) {
            minionSpell = spell;
            spellBuffs.clear();
        }
    }

    @FXML
    void heroEditSpecialPower(ActionEvent event) {
        Spell spell = makeSpell();
        if (spell != null) {
            heroSpell = spell;
            spellBuffs.clear();
        }
    }

    @FXML
    void activationTypeCmbSelected(ActionEvent event) {
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

    private void clearEveryThing() {
        //todo
    }
}
