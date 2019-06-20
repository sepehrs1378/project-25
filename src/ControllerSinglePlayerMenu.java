import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerSinglePlayerMenu implements Initializable{
    private static ControllerSinglePlayerMenu ourInstance;
    private Request request = Request.getInstance();
    private View view = View.getInstance();
    private DataBase database = DataBase.getInstance();

    @FXML
    void goBack(MouseEvent event) throws IOException {
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
    void enterLevel1(MouseEvent event) throws IOException {
        Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerLevel1(),
                Constants.CLASSIC, 0, null, Constants.SINGLE);
        database.setCurrentBattle(battle);
        AnchorPane root = FXMLLoader.load(getClass().getResource("ControllerBattleCommandsFXML.fxml"));
        ControllerMainMenu.stage.close();
        Main.window.setScene(new Scene(root));
    }

    @FXML
    void enterLevel2(MouseEvent event) throws IOException {
        Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerLevel2()
                , Constants.ONE_FLAG, 1, null, Constants.SINGLE);
        database.setCurrentBattle(battle);
        AnchorPane root = FXMLLoader.load(getClass().getResource("ControllerBattleCommandsFXML.fxml"));
        ControllerMainMenu.stage.close();
        Main.window.setScene(new Scene(root));
    }

    @FXML
    void enterLevel3(MouseEvent event) throws IOException {
        Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerLevel3()
                , Constants.FLAGS, 7, null, Constants.SINGLE);
        database.setCurrentBattle(battle);
        AnchorPane root = FXMLLoader.load(getClass().getResource("ControllerBattleCommandsFXML.fxml"));
        ControllerMainMenu.stage.close();
        Main.window.setScene(new Scene(root));
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


    public void main() throws GoToMainMenuException {
        view.printHelp(HelpType.CONTROLLER_SINGLE_PLAYER_MENU);
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case SELECT_DECK_NAME:
                    break;
                case ENTER:
                    enter();
                    break;
                case HELP:
                    help();
                    break;
                case EXIT:
                    didExit = true;
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    private void help() {
        view.printHelp(HelpType.CONTROLLER_SINGLE_PLAYER_MENU);
    }

    public void enter() throws GoToMainMenuException {
        if (request.getCommand().equals("enter story")) {
            view.printHelp(HelpType.STORY_MODE_OPTIONS);
            view.print("please enter your level:");
            request.getNewCommand();
            if (request.getCommand().equals("enter level1")) {
                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerLevel1()
                        , Constants.CLASSIC, 0, null, Constants.SINGLE);
                database.setCurrentBattle(battle);
                ControllerBattleCommands.getInstance().main();
            }
            if (request.getCommand().equals("enter level2") && database.getLoggedInAccount().getLevelsOpennessStatus()[1]) {
                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerLevel2()
                        , Constants.ONE_FLAG, 1, null, Constants.SINGLE);
                database.setCurrentBattle(battle);
                ControllerBattleCommands.getInstance().main();
            } else if (request.getCommand().equals("enter level2") && !database.getLoggedInAccount().getLevelsOpennessStatus()[1]) {
                view.printOutputMessage(OutputMessageType.LEVEL_IS_LOCKED);
            }
            if (request.getCommand().equals("enter level3") && database.getLoggedInAccount().getLevelsOpennessStatus()[2]) {
                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerLevel3()
                        , Constants.FLAGS, 7, null, Constants.SINGLE);
                database.setCurrentBattle(battle);
                ControllerBattleCommands.getInstance().main();
            } else if (request.getCommand().equals("enter level3") && !database.getLoggedInAccount().getLevelsOpennessStatus()[2]) {
                view.printOutputMessage(OutputMessageType.LEVEL_IS_LOCKED);
            }
        } else if (request.getCommand().equals("enter custom")) {
            List<Deck> decks = database.getLoggedInAccount().getValidDecks();
            view.printOutputMessage(OutputMessageType.PLEASE_SELECT_A_DECK);
            view.showValidDecks(decks);
            request.getNewCommand();
            if (request.getCommand().matches("^start game \\w+ \\w+\\s*\\w*$")) {
                Deck deck = database.getLoggedInAccount().getPlayerInfo().getCollection().
                        getDeckFromListOfDecks(decks, request.getCommand().split("\\s+")[2]);
                if (deck != null) {
                    database.getComputerPlayerCustom().setMainDeck(new Deck(deck));
                    database.setNewIdsForCustomPlayer();
                    switch (request.getCommand().split("\\s+")[3]) {
                        case Constants.CLASSIC: {
                            Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerCustom(),
                                    Constants.CLASSIC, 0, null, Constants.SINGLE);
                            database.setCurrentBattle(battle);
                            ControllerBattleCommands.getInstance().main();
                            break;
                        }
                        case Constants.ONE_FLAG: {
                            Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerCustom(),
                                    Constants.ONE_FLAG, 1, null, Constants.SINGLE);
                            database.setCurrentBattle(battle);
                            ControllerBattleCommands.getInstance().main();
                            break;
                        }
                        case Constants.FLAGS:
                            if (request.getCommand().split("\\s+").length == 5) {
                                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerCustom(),
                                        Constants.FLAGS, Integer.parseInt(request.getCommand().split("\\s+")[4]), null,
                                        Constants.SINGLE);
                                database.setCurrentBattle(battle);
                                ControllerBattleCommands.getInstance().main();
                            } else {
                                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerCustom(),
                                        Constants.FLAGS, 7, null, Constants.SINGLE);
                                database.setCurrentBattle(battle);
                                ControllerBattleCommands.getInstance().main();
                            }
                            break;
                        default:
                            view.printOutputMessage(OutputMessageType.INVALID_MODE);
                            break;
                    }
                } else view.printOutputMessage(OutputMessageType.DECK_NOT_VALID);
            } else view.printOutputMessage(OutputMessageType.WRONG_COMMAND);


        } else view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
    }

    public static ControllerSinglePlayerMenu getInstance() {
        return ourInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (database.getLoggedInAccount().getLevelsOpennessStatus()[1]) {
            level2Btn.setDisable(false);
        }
        if (database.getLoggedInAccount().getLevelsOpennessStatus()[1]) {
            level3Btn.setDisable(false);
        }
        ObservableList<String> deckList = FXCollections.observableArrayList();
        for (int i = 0; i < database.getLoggedInAccount().getValidDecks().size(); i++) {
            deckList.add(database.getLoggedInAccount().getValidDecks().get(i).getName());
        }
        selectDeckBox.setItems(deckList);
        ObservableList<String> modeList = FXCollections.observableArrayList();
        modeList.add(Constants.CLASSIC);
        modeList.add(Constants.ONE_FLAG);
        modeList.add(Constants.FLAGS);
        selectModeBox.setItems(modeList);
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
        if (selectDeckBox.getValue() == null && selectModeBox.getValue() == null){
            invalidDeckLabel.setVisible(true);
            invalidModeLabel.setVisible(true);
            return;
        }
        if (selectDeckBox.getValue() == null){
            invalidDeckLabel.setVisible(true);
            return;
        }
        if (selectModeBox.getValue() == null) {
            invalidModeLabel.setVisible(true);
            return;
        }
        if (selectModeBox.getValue().equals(Constants.FLAGS)) {
            if (!flagNumberLabel.getText().matches("\\d*")){
                invalidNumberLabel.setText("please enter a number");
                return;
            }else if (!flagNumberLabel.getText().isEmpty() && Integer.parseInt(flagNumberLabel.getText()) > 43){
                invalidNumberLabel.setText("please enter a number less than 43");
                return;
            }
        }
        Deck deck = database.getLoggedInAccount().getPlayerInfo().getCollection().getDeckByName(selectDeckBox.getValue());
        switch (selectModeBox.getValue()) {
            case Constants.CLASSIC: {
                database.getComputerPlayerCustom().setMainDeck(deck);
                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerCustom(),
                        Constants.CLASSIC, 0, null, Constants.SINGLE);
                database.setCurrentBattle(battle);
                AnchorPane root = FXMLLoader.load(getClass().getResource("ControllerBattleCommandsFXML.fxml"));
                ControllerMainMenu.stage.close();
                Main.window.setScene(new Scene(root));
                break;
            }
            case Constants.ONE_FLAG: {
                database.getComputerPlayerCustom().setMainDeck(deck);
                Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerCustom(),
                        Constants.ONE_FLAG, 1, null, Constants.SINGLE);
                database.setCurrentBattle(battle);
                AnchorPane root = FXMLLoader.load(getClass().getResource("ControllerBattleCommandsFXML.fxml"));
                ControllerMainMenu.stage.close();
                Main.window.setScene(new Scene(root));
                break;
            }
            case Constants.FLAGS:
                database.getComputerPlayerCustom().setMainDeck(deck);
                if (flagNumberLabel.getText().isEmpty()) {
                    Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerCustom(),
                            Constants.FLAGS, 7, null, Constants.SINGLE);
                    database.setCurrentBattle(battle);
                    AnchorPane root = FXMLLoader.load(getClass().getResource("ControllerBattleCommandsFXML.fxml"));
                    ControllerMainMenu.stage.close();
                    Main.window.setScene(new Scene(root));
                } else {
                    Battle battle = new Battle(database.getLoggedInAccount(), database.getComputerPlayerCustom(),
                            Constants.FLAGS, Integer.parseInt(flagNumberLabel.getText()), null,
                            Constants.SINGLE);
                    database.setCurrentBattle(battle);
                    ControllerMainMenu.stage.close();
                    AnchorPane root = FXMLLoader.load(getClass().getResource("ControllerBattleCommandsFXML.fxml"));
                    Main.window.setScene(new Scene(root));
                }
                break;
            default:
                view.printOutputMessage(OutputMessageType.INVALID_MODE);
                break;
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
