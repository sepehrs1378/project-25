import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerSinglePlayerMenu implements Initializable {
    private static ControllerSinglePlayerMenu ourInstance;
    private ClientDB clientDB = ClientDB.getInstance();

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        Parent root = FXMLLoader.load(getClass().getResource("ControllerMainMenu.fxml"));
        Main.window.setScene(new Scene(root));
    }

    public ControllerSinglePlayerMenu() {
        ourInstance = this;
    }

    @FXML
    private ImageView closeBtn;

    @FXML
    private ImageView level1Btn;

    @FXML
    private ImageView level2Btn;

    @FXML
    private ImageView level3Btn;

    @FXML
    private ComboBox<String> selectDeckBox;

    @FXML
    private ComboBox<String> selectModeBox;

    @FXML
    private JFXTextField flagNumberLabel;

    @FXML
    private ImageView flagNumberImage;

    @FXML
    private ImageView playBtn;

    @FXML
    private Label invalidDeckLabel;

    @FXML
    private Label invalidModeLabel;

    @FXML
    private Label invalidNumberLabel;

    @FXML
    private ImageView singleDisabledImage;

    @FXML
    private ImageView disabledBackGround;

    @FXML
    void enterLevel1(MouseEvent event) throws IOException {
        Battle battle = new Battle(clientDB.getLoggedInAccount(), ClientDB.getInstance().getComputerPlayerLevel1(),
                Constants.CLASSIC, 0, null, Constants.SINGLE, 1000);
        setBattle(battle);
        ControllerBattleCommands.getOurInstance().recordVideo(clientDB.generateNameForVideoRecord("level1"));
    }

    @FXML
    void enterLevel2(MouseEvent event) throws IOException {
        Main.getGlobalMediaPlayer().stop();
        Battle battle = new Battle(clientDB.getLoggedInAccount(), ClientDB.getInstance().getComputerPlayerLevel2()
                , Constants.ONE_FLAG, 1, null, Constants.SINGLE, 1000);
        setBattle(battle);
        ControllerBattleCommands.getOurInstance().recordVideo(clientDB.generateNameForVideoRecord("level2"));
    }

    @FXML
    void enterLevel3(MouseEvent event) throws IOException {
        Main.getGlobalMediaPlayer().stop();
        Battle battle = new Battle(clientDB.getLoggedInAccount(), ClientDB.getInstance().getComputerPlayerLevel3()
                , Constants.FLAGS, 7, null, Constants.SINGLE, 1500);
        setBattle(battle);
        ControllerBattleCommands.getOurInstance().recordVideo(clientDB.generateNameForVideoRecord("level3"));
    }

    private void setBattle(Battle battle) throws IOException {
        clientDB.setCurrentBattle(battle);
        Parent root = FXMLLoader.load(getClass().getResource("ControllerBattleCommandsFXML.fxml"));
        Main.window.setScene(new Scene(root));
        ControllerMainMenu.stage.close();
    }

    @FXML
    void makeLevel1BtnOpaque(MouseEvent event) {
        level1Btn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeLevel1BtnTransparent(MouseEvent event) {
        level1Btn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeLevel2BtnOpaque(MouseEvent event) {
        level2Btn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeLevel2BtnTransparent(MouseEvent event) {
        level2Btn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeLevel3BtnOpaque(MouseEvent event) {
        level3Btn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeLevel3BtnTransparent(MouseEvent event) {
        level3Btn.setStyle("-fx-opacity: 0.6");
    }


    @FXML
    void closeSinglePlayerMenu(MouseEvent event) throws IOException {
        Main.playWhenButtonClicked();
        ControllerMainMenu.stage.close();
    }

    @FXML
    void makeCloseBtnOpaque(MouseEvent event) {
        closeBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCloseBtnTransparent(MouseEvent event) {
        closeBtn.setStyle("-fx-opacity: 0.6");
    }

    public static ControllerSinglePlayerMenu getInstance() {
        return ourInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        level1Btn.setDisable(false);
        selectModeBox.setDisable(false);
        selectDeckBox.setDisable(false);
        playBtn.setDisable(false);
        singleDisabledImage.setVisible(false);
        disabledBackGround.setVisible(false);
        if (clientDB.getLoggedInAccount().getLevelsOpennessStatus()[1]) {
            level2Btn.setDisable(false);
        }
        if (clientDB.getLoggedInAccount().getLevelsOpennessStatus()[2]) {
            level3Btn.setDisable(false);
        }
        ObservableList<String> deckList = FXCollections.observableArrayList();
        for (int i = 0; i < clientDB.getLoggedInAccount().getValidDecks().size(); i++) {
            deckList.add(clientDB.getLoggedInAccount().getValidDecks().get(i).getName());
        }
        selectDeckBox.setItems(deckList);
        ObservableList<String> modeList = FXCollections.observableArrayList();
        modeList.add(Constants.CLASSIC);
        modeList.add(Constants.ONE_FLAG);
        modeList.add(Constants.FLAGS);
        selectModeBox.setItems(modeList);
        if (clientDB.getLoggedInAccount().getMainDeck() == null || !clientDB.getLoggedInAccount().getMainDeck().isValid()) {
            level1Btn.setDisable(true);
            level2Btn.setDisable(true);
            level3Btn.setDisable(true);
            selectModeBox.setDisable(true);
            selectDeckBox.setDisable(true);
            playBtn.setDisable(true);
            singleDisabledImage.setVisible(true);
            disabledBackGround.setVisible(true);
        }
    }

    @FXML
    void selectModeOfGame(ActionEvent event) {
        invalidModeLabel.setVisible(false);
        if (selectModeBox.getValue().isEmpty()) {
            flagNumberImage.setVisible(false);
            flagNumberLabel.setVisible(false);
            return;
        }
        if (!selectModeBox.getValue().equals(Constants.FLAGS)) {
            flagNumberImage.setVisible(false);
            flagNumberLabel.setVisible(false);
            return;
        }
        flagNumberImage.setVisible(true);
        flagNumberLabel.setVisible(true);
    }

    @FXML
    void selectDeck(ActionEvent event) {
        invalidDeckLabel.setVisible(false);
    }

    @FXML
    void enterCustomGame(MouseEvent event) throws IOException {
        if (selectDeckBox.getValue() == null && selectModeBox.getValue() == null) {
            invalidDeckLabel.setVisible(true);
            invalidModeLabel.setVisible(true);
            return;
        }
        if (selectDeckBox.getValue() == null) {
            invalidDeckLabel.setVisible(true);
            return;
        }
        if (selectModeBox.getValue() == null) {
            invalidModeLabel.setVisible(true);
            return;
        }
        if (selectModeBox.getValue().equals(Constants.FLAGS)) {
            if (!flagNumberLabel.getText().matches("\\d*")) {
                invalidNumberLabel.setText("please enter a number");
                return;
            } else if (!flagNumberLabel.getText().isEmpty() && Integer.parseInt(flagNumberLabel.getText()) > 35) {
                invalidNumberLabel.setText("please enter a number less than 35");
                return;
            }
        }
        Main.getGlobalMediaPlayer().stop();
        Deck deck = clientDB.getLoggedInAccount().getPlayerInfo().getCollection().getDeckByName(selectDeckBox.getValue());
        Deck newDeck = new Deck(deck);
        changeIDtoCustomPlayer(newDeck);
        ClientDB.getInstance().getComputerPlayerCustom().setMainDeck(newDeck);
        switch (selectModeBox.getValue()) {
            case Constants.CLASSIC: {
                Battle battle = new Battle(clientDB.getLoggedInAccount(), ClientDB.getInstance().getComputerPlayerCustom(),
                        Constants.CLASSIC, 0, null, Constants.SINGLE, 1000);
                setBattle(battle);
                ControllerBattleCommands.getOurInstance().recordVideo(clientDB.generateNameForVideoRecord("Classic"));
                break;
            }
            case Constants.ONE_FLAG: {
                Battle battle = new Battle(clientDB.getLoggedInAccount(), ClientDB.getInstance().getComputerPlayerCustom(),
                        Constants.ONE_FLAG, 1, null, Constants.SINGLE, 1000);
                setBattle(battle);
                ControllerBattleCommands.getOurInstance().recordVideo(clientDB.generateNameForVideoRecord("oneFlag"));
                break;
            }
            case Constants.FLAGS:
                if (flagNumberLabel.getText().isEmpty()) {
                    Battle battle = new Battle(clientDB.getLoggedInAccount(), clientDB.getComputerPlayerCustom(),
                            Constants.FLAGS, 7, null, Constants.SINGLE, 1000);
                    setBattle(battle);
                    ControllerBattleCommands.getOurInstance().recordVideo(clientDB.generateNameForVideoRecord("Flags"));
                } else {
                    Battle battle = new Battle(clientDB.getLoggedInAccount(), ClientDB.getInstance().getComputerPlayerCustom(),
                            Constants.FLAGS, Integer.parseInt(flagNumberLabel.getText()), null,
                            Constants.SINGLE, 1000);
                    clientDB.setCurrentBattle(battle);
                    ControllerMainMenu.stage.close();
                    Parent root = FXMLLoader.load(getClass().getResource("ControllerBattleCommandsFXML.fxml"));
                    Main.window.setScene(new Scene(root));
                }
                break;
            default:
                break;
        }
    }

    private void changeIDtoCustomPlayer(Deck deck) {
        for (Card card : deck.getCards()) {
            String oldID = card.getId();
            String[] idSplit = oldID.split("_");
            idSplit[0] = ClientDB.getInstance().getComputerPlayerCustom().getUsername();
            card.setId(idSplit[0] + "_" + idSplit[1] + "_" + idSplit[2]);
        }
        if (deck.getHero() != null) {
            String oldID = deck.getHero().getId();
            String[] idSplit = oldID.split("_");
            idSplit[0] = ClientDB.getInstance().getComputerPlayerCustom().getUsername();
            deck.getHero().setId(idSplit[0] + "_" + idSplit[1] + "_" + idSplit[2]);
        }
        if (deck.getItem() != null) {
            String oldID = deck.getItem().getId();
            String[] idSplit = oldID.split("_");
            idSplit[0] = ClientDB.getInstance().getComputerPlayerCustom().getUsername();
            deck.getItem().setId(idSplit[0] + "_" + idSplit[1] + "_" + idSplit[2]);
        }
    }

    @FXML
    void makePlayBtnOpaque(MouseEvent event) {
        playBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makePlayBtnTransparent(MouseEvent event) {
        playBtn.setStyle("-fx-opacity: 0.6");
    }
}
