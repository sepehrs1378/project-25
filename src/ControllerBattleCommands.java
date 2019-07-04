import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerBattleCommands implements Initializable {
    private static ClientDB dataBase = ClientDB.getInstance();
    private static ControllerBattleCommands ourInstance;
    private Player loggedInPlayer;
    private List<ImageView> handRings = new ArrayList<>();
    private List<UnitImage> unitImageList = new ArrayList<>();
    private List<HandImage> handImageList = new ArrayList<>();
    private List<SpellImage> spellImageList = new ArrayList<>();
    private CellImage[][] cellsImages = new CellImage[5][9];
    private ImageView clickedImageView = new ImageView();
    private List<FlagImage> flagImages = new ArrayList<>();
    private Timeline timeline = new Timeline();
    //todo next card has bug

    public void setClickedImageView(ImageView clickedImageView) {
        this.clickedImageView = clickedImageView;
    }

    public ImageView getClickedImageView() {
        return clickedImageView;
    }

    @FXML
    private ImageView endTurnMineBtn;

    @FXML
    private ImageView endTurnEnemyBtn;

    @FXML
    private AnchorPane battleGroundPane;

    @FXML
    private ImageView graveYardBtn;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    @FXML
    private ImageView specialPowerView;

    @FXML
    private ImageView collectableView;

    @FXML
    private ImageView usableView;

    @FXML
    private ImageView forfeitBtn;

    @FXML
    void forfeitGame(MouseEvent event) {
        forfeitGame();
        returnToMainMenu();
    }

    @FXML
    void makeForfeitBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        forfeitBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeForfeitBtnTransparent(MouseEvent event) {
        forfeitBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void enterGraveYard(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ControllerGraveYard.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        File file = new File("src/pics/cursors/main_cursor.png");
        Image image = new Image(file.toURI().toString());
        scene.setCursor(new ImageCursor(image));
        stage.setScene(scene);
        ControllerGraveYard.stage = stage;
        stage.showAndWait();
    }

    @FXML
    void makeGraveYardBtnOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        graveYardBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeGraveYardBtnTransparent(MouseEvent event) {
        graveYardBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeEndTurnMineOpaque(MouseEvent event) {
        Main.playWhenMouseEntered();
        endTurnMineBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeEndTurnMineTransparent(MouseEvent event) {
        endTurnMineBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    private ImageView handRing1;

    @FXML
    private ImageView handRing2;

    @FXML
    private ImageView handRing3;

    @FXML
    private ImageView handRing4;

    @FXML
    private ImageView handRing5;

    @FXML
    private ImageView nextCardRing;

    @FXML
    private Label playerManaLabel;

    @FXML
    private Label computerManaLabel;

    @FXML
    private ProgressBar timeBar;

    @FXML
    void endTurn(MouseEvent event) throws GoToMainMenuException {
        //todo
        endTurnWhenClicked();
        endTurnMineBtn.setVisible(false);
        endTurnEnemyBtn.setVisible(true);
    }

    private void endTurnWhenClicked() {
        Media media = new Media(Paths.get("src/music/end_turn.m4a").toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
        clickedImageView = null;
        if (dataBase.getCurrentBattle().getSingleOrMulti().equals(Constants.MULTI)) {
            new ServerRequestSender(new Request(RequestType.endTurn, null, null, null));
            endTurnMineBtn.setVisible(false);
            endTurnEnemyBtn.setVisible(true);
        }
        if (dataBase.getCurrentBattle().getSingleOrMulti().equals(Constants.SINGLE)) {
            if (endTurn()) {
                return;
            }
        /*Battle battle = dataBase.getCurrentBattle();
        if (battle.getSingleOrMulti().equals(Constants.SINGLE) && battle.getPlayerInTurn().equals(battle.getPlayer2())) {
            AI.getInstance().doNextMove(battleGroundPane);
            if (endTurn()) {
                return;
            }
        }*/
            //todo turn it on
        }
        setTimeBar();
        updatePane();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        startTempBattle();//todo remove it later
        setTimeBar();
        Main.getGlobalMediaPlayer().stop();
        this.loggedInPlayer = dataBase.getCurrentBattle().getPlayerInTurn();
        setupPlayersInfoViews();
        setupBattleGroundCells();
        setupHandRings();
        setupHeroesImages();
        setupCursor();
        setupHeroSpecialPowerView();
        setupItemView();
        updatePane();
    }

    private void setTimeBar() {
        String turnDuration = dataBase.getLoggedInAccount().getTurnDuration();
        if (turnDuration != null && !turnDuration.equals(Constants.NO_LIMIT)) {
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeBar.setProgress(0);
            timeBar.setVisible(true);
            double seconds = Integer.parseInt(turnDuration);
            KeyValue keyValue = new KeyValue(timeBar.progressProperty(), 1);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(seconds), keyValue);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
            timeline.setOnFinished(e -> {
                timeBar.setProgress(0);
                endTurnWhenClicked();
            });
        }
    }

    private void setupPlayersInfoViews() {
        player1Label.setText(dataBase.getCurrentBattle().getPlayer1().getPlayerInfo().getPlayerName());
        player2Label.setText(dataBase.getCurrentBattle().getPlayer2().getPlayerInfo().getPlayerName());
    }

    private void setupItemView() {
        Usable usable = (Usable) dataBase.getLoggedInAccount().getMainDeck().getItem();
        try {
            if (usable != null) {
                usableView.setImage(new Image(new FileInputStream("/src/ApProjectResources/units/" + usable.getName() + "/usable")));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setupHeroSpecialPowerView() {
        final String MOUSE_ENTERED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,1), 10, 0, 0, 0);";
        final String MOUSE_EXITED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 10, 0, 0, 0);";
        final String SELECTED_STYLE = "-fx-effect: dropshadow(three-pass-box, rgb(255,255,0), 10, 0, 0, 0);";
        Unit hero = dataBase.getLoggedInAccount().getMainDeck().getHero();
        if (hero.getMainSpecialPower() == null) {
            return;
        }
        try {
            specialPowerView.setImage(new Image(new FileInputStream
                    ("src/ApProjectResources/units/" + hero.getName() + "/special_power.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        specialPowerView.setOnMouseEntered(event -> {
            if (!specialPowerView.getStyle().equals(SELECTED_STYLE))
                specialPowerView.setStyle(MOUSE_ENTERED_STYLE);
        });
        specialPowerView.setOnMouseExited(event -> {
            if (!specialPowerView.getStyle().equals(SELECTED_STYLE))
                specialPowerView.setStyle(MOUSE_EXITED_STYLE);
        });
        specialPowerView.setOnMouseClicked(event -> {
            if (clickedImageView != null && clickedImageView == specialPowerView) {
                clickedImageView = null;
                specialPowerView.setStyle(null);
            } else {
                clickedImageView = specialPowerView;
                specialPowerView.setStyle(SELECTED_STYLE);
            }
            updatePane();
        });
    }

    private void setupCursor() {
        try {
            ImageCursor cursor = new ImageCursor(new Image(new FileInputStream("./src/pics/mouse_icon")));
            battleGroundPane.setCursor(cursor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ControllerBattleCommands getOurInstance() {
        return ourInstance;
    }

    public ControllerBattleCommands() {
        ourInstance = this;
    }

    private void setupHeroesImages() {
        Unit playerHero = dataBase.getCurrentBattle().getPlayer1().getDeck().getHero();
        Unit opponentHero = dataBase.getCurrentBattle().getPlayer2().getDeck().getHero();
        UnitImage playerHeroImage = new UnitImage(playerHero.getId(), battleGroundPane);
        UnitImage opponentHeroImage = new UnitImage(opponentHero.getId(), battleGroundPane);
        unitImageList.add(opponentHeroImage);
        unitImageList.add(playerHeroImage);
        playerHeroImage.setInCell(2, 0);
        opponentHeroImage.setInCell(2, 8);
        opponentHeroImage.getUnitView().setScaleX(-1);
    }

    private void setupBattleGroundCells() {
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 9; column++) {
                cellsImages[row][column] = new CellImage(row, column, battleGroundPane);
            }
        }
    }

    public void handleCellClicked(int row, int column) {
        //todo complete it for other purposes too
        if (isClickedImageViewInHand())
            if (handleCardInsertion(row, column)) {
                updatePane();
                return;
            }
        if (clickedImageView == specialPowerView) {
            if (handleSpecialPowerInsertion(row, column)) {
                updatePane();
                return;
            }
        }
        if (handleUnitMove(row, column)) {
            updatePane();
            return;
        }
        updatePane();
    }

    private boolean handleSpecialPowerInsertion(int row, int column) {
        Unit hero = dataBase.getCurrentBattle().getBattleGround().getHeroOfPlayer(loggedInPlayer);
        UnitImage heroImage = getUnitImageWithId(hero.getId());
        switch (dataBase.getCurrentBattle().useSpecialPower(loggedInPlayer, row, column)) {
            case NO_HERO:
                //empty
                break;
            case HERO_HAS_NO_SPELL:
                //empty
                break;
            case SPECIAL_POWER_IN_COOLDOWN:
                //empty
                break;
            case NOT_ENOUGH_MANA:
                //empty
                break;
            case SPECIAL_POWER_USED:
                heroImage.showSpell();
                SpellImage specialPowerImage = new SpellImage
                        (hero.getId(), row, column, battleGroundPane, SpellType.specialPower);
                spellImageList.add(specialPowerImage);
                clickedImageView = null;
                break;
            default:
                System.out.println("unhandled case!!!!!");
        }
        //todo
        return false;
    }

    private boolean handleUnitMove(int row, int column) {
        switch (dataBase.getCurrentBattle().getBattleGround()
                .moveUnit(row, column)) {
            case UNIT_NOT_SELECTED:
                //empty
                break;
            case OUT_OF_BOUNDARIES:
                //empty
                break;
            case CELL_IS_FULL:
                //empty
                break;
            case CELL_OUT_OF_RANGE:
                //empty
                break;
            case UNIT_ALREADY_MOVED:
                //empty
                break;
            case UNIT_MOVED:
                Player currentPlayer = dataBase.getCurrentBattle().getPlayerInTurn();
                UnitImage movedUnitImage = getUnitImageWithId(currentPlayer.getSelectedUnit().getId());
                movedUnitImage.showRun(row, column);
                return true;
            default:
        }
        return false;
    }

    private boolean handleCardInsertion(int row, int column) {
        HandImage handImage = getHandImageWithCardView(clickedImageView);
        Card card = dataBase.getCurrentBattle()
                .getPlayerInTurn().getHand().getCardById(handImage.getId());
        switch (dataBase.getCurrentBattle().insert(card, row, column)) {
            case NO_SUCH_CARD_IN_HAND:
                System.out.println("1");
                //empty
                break;
            case NOT_ENOUGH_MANA:
                System.out.println("2");
                //empty
                break;
            case INVALID_NUMBER:
                System.out.println("3");
                //empty
                break;
            case NOT_NEARBY_FRIENDLY_UNITS:
                System.out.println("4");
                //empty
                break;
            case THIS_CELL_IS_FULL:
                System.out.println("5");
                //empty
                break;
            case CARD_INSERTED:
                System.out.println("6");
                if (card instanceof Unit)
                    insertUnitView(row, column, card);
                if (card instanceof Spell) {
                    insertSpellView(row, column, card);
                }
                handImage.clearHandImage();
                return true;
            default:
        }
        return false;
    }

    public List<SpellImage> getSpellImageList() {
        return spellImageList;
    }

    private void insertSpellView(int row, int column, Card card) {
        SpellImage insertedSpellImage = new SpellImage
                (card.getId(), row, column, battleGroundPane, SpellType.spell);
        spellImageList.add(insertedSpellImage);
        clickedImageView = null;
    }

    public void insertUnitView(int row, int column, Card card) {
        UnitImage insertedUnitImage = new UnitImage(card.getId(), battleGroundPane);
        unitImageList.add(insertedUnitImage);
        insertedUnitImage.setInCell(row, column);
        insertedUnitImage.showSpawn();
        clickedImageView = null;
    }

    public HandImage getHandImageWithCardView(ImageView cardView) {
        for (HandImage handImage : handImageList) {
            if (handImage.getCardView().equals(cardView))
                return handImage;
        }
        return null;
    }

    public boolean isClickedImageViewInHand() {
        for (HandImage handImage : handImageList) {
            if (handImage.getCardView().equals(clickedImageView))
                return true;
        }
        return false;
    }

    public void handleUnitClicked(String id) {
        Player currentPlayer = dataBase.getCurrentBattle().getPlayerInTurn();
        if (currentPlayer.getSelectedCollectable() == null) {
            if (handleUnitSelection(id)) {
                updatePane();
                return;
            }
        }
        if (currentPlayer.getSelectedUnit() != null) {
            if (handleUnitAttack(id)) {
                updatePane();
                return;
            }
        }
        updatePane();
    }

    private boolean handleUnitAttack(String id) {
        Player currentPlayer = dataBase.getCurrentBattle().getPlayerInTurn();
        UnitImage selectedUnitImage = getUnitImageWithId(currentPlayer.getSelectedUnit().getId());
        UnitImage targetedUnitImage = getUnitImageWithId(id);
        //todo add this feature to unselect a unit with clicking on it if needed
        switch (currentPlayer.getSelectedUnit().attack(id)) {
            case UNIT_ATTACKED:
                selectedUnitImage.showAttack(targetedUnitImage.getColumn());
                return true;
            case UNIT_AND_ENEMY_ATTACKED:
                selectedUnitImage.showAttack(targetedUnitImage.getColumn());
                targetedUnitImage.showAttack(selectedUnitImage.getColumn());
                return true;
            case ATTACKED_FRIENDLY_UNIT:
                //empty
                break;
            case ALREADY_ATTACKED:
                //empty
                break;
            case INVALID_CARD:
                //empty
                break;
            case TARGET_NOT_IN_RANGE:
                //empty
                break;
            default:
                System.out.println("unhandled case");
        }
        return false;
    }

    private boolean handleUnitSelection(String id) {
        Player currentPlayer = dataBase.getCurrentBattle().getPlayerInTurn();
        UnitImage unitImage = getUnitImageWithId(id);
        switch (currentPlayer.selectUnit(id)) {
            case SELECTED:
                unitImage.setUnitStyleAsSelected();
                clickedImageView = unitImage.getUnitView();
                return true;
            case ENEMY_UNIT_SELECTED:
                //empty
                break;
            case INVALID_COLLECTABLE_CARD:
                //empty
                break;
            case UNIT_IS_STUNNED:
                //empty
                break;
            default:
                System.out.println("unhandled case !!!!!!!!");
        }
        return false;
    }

    public List<ImageView> getHandRings() {
        return handRings;
    }

    private void setupHandRings() {
        handRings.add(handRing1);
        handRings.add(handRing2);
        handRings.add(handRing3);
        handRings.add(handRing4);
        handRings.add(handRing5);
        handImageList.add(new HandImage(0, getBattleGroundPane()));
        handImageList.add(new HandImage(1, getBattleGroundPane()));
        handImageList.add(new HandImage(2, getBattleGroundPane()));
        handImageList.add(new HandImage(3, getBattleGroundPane()));
        handImageList.add(new HandImage(4, getBattleGroundPane()));
    }

    public UnitImage getUnitImageWithId(String id) {
        for (UnitImage unitImage : unitImageList) {
            if (unitImage.getId().equals(id))
                return unitImage;
        }
        return null;
    }

    public void updatePane() {
        updateUnitImages();
        updateSpecialPowerImage();
        updateCellImages();
        Collectable collectable = dataBase.getCurrentBattle().getCollectable();
        Player player1 = dataBase.getCurrentBattle().getPlayer1();
        if (!player1.getCollectables().isEmpty()) {
            if (dataBase.getLoggedInAccount().getPlayerInfo().getPlayerName().equals(player1.getPlayerInfo().getPlayerName()) &&
                    player1.getCollectables().get(0).equals(collectable)) {
                try {
                    collectableView.setImage(new Image(new FileInputStream("src/ApProjectResources/collectables/" +
                            collectable.getName() + "/collectable")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        showFlags();
        updateHandImages();
        updatePlayersInfo();
        updateHand();
    }

    private void updateCellImages() {
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 9; column++) {
                Cell cell = dataBase.getCurrentBattle().getBattleGround().getCells()[row][column];
                CellImage cellImage = cellsImages[row][column];
                cellImage.clearBuffImageList();
                for (Buff buff : cell.getBuffs()) {
                    cellImage.addBuffImage(buff.getType());
                }
            }
        }
    }

    private void updateUnitImages() {
        for (UnitImage unitImage : unitImageList) {
            if (unitImage == null)
                continue;
            BattleGround battleGround = dataBase.getCurrentBattle().getBattleGround();
            if (!battleGround.doesHaveUnit(unitImage.getId())) {
                unitImage.showDeath();
                continue;
            }
            Unit unit = battleGround.getUnitWithID(unitImage.getId());
            if (unit == null)
                continue;
            unitImage.setApNumber(unit.getAp());
            unitImage.setHpNumber(unit.getHp());
            if (unitImage.getUnitView().equals(clickedImageView))
                unitImage.setUnitStyleAsSelected();
            else unitImage.setStyleAsNotSelected();
            unitImage.clearBuffImageList();
            for (Buff buff : unit.getBuffs()) {
                unitImage.addBuffImage(buff.getType());
            }
        }
    }

    private void updateSpecialPowerImage() {
        if (clickedImageView != specialPowerView)
            specialPowerView.setStyle(null);
    }

    private void showFlags() {
        BattleGround battleGround = dataBase.getCurrentBattle().getBattleGround();
        List<ImageView> images = new ArrayList<>();
        for (FlagImage flagImage : flagImages)
            images.add(flagImage.getFlagView());
        battleGroundPane.getChildren().removeAll(images);
        flagImages.clear();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (battleGround.getCells()[i][j].getFlags().size() > 0) {
                    FlagImage flagImage = new FlagImage();
                    flagImages.add(flagImage);
                    flagImage.getFlagView().setTranslateX(getCellLayoutX(j) - 10);
                    flagImage.getFlagView().setTranslateY(getCellLayoutY(i) - 10);
                    battleGroundPane.getChildren().add(flagImage.getFlagView());
                }
            }
        }
    }

    private void updateHandImages() {
        for (HandImage handImage : handImageList) {
            if (handImage.getCardView().equals(clickedImageView))
                handImage.setStyleAsSelected();
            else handImage.setStyleAsNotSelected();
        }
    }

    private void updatePlayersInfo() {
        playerManaLabel.setText(Integer.toString(dataBase.getCurrentBattle().getPlayer1().getMana()));
        computerManaLabel.setText(Integer.toString(dataBase.getCurrentBattle().getPlayer1().getMana()));
    }

    private void updateHand() {
        List<Card> handCards = getLoggedInPlayer().getHand().getCards();
        for (int i = 0; i < handCards.size(); i++) {
            Card card = handCards.get(i);
            handImageList.get(i).setCardImage(card.getId());
        }
    }

    public Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    private void showNextCard() {
    /*    Card card = dataBase.getCurrentBattle().getPlayerInTurn().getNextCard();
        if (card instanceof Spell) {
            view.showCardInfoSpell((Spell) card);
        } else if (card instanceof Unit) {
            view.showCardInfoMinion((Unit) card);
        }
    */
    }

    private void attackCombo() {
      /*  String[] orderPieces = request.getCommand().split(" ");
        String[] attackers = new String[orderPieces.length - 3];
        if (orderPieces.length - 3 >= 0)
            System.arraycopy(orderPieces, 3, attackers, 0
                    , orderPieces.length - 3);
        view.printOutputMessage(Unit.attackCombo(orderPieces[2], attackers));
    */
    }

    public void useCollectable() {
        /*int row = Integer.parseInt(request.getCommand().split("[ (),]")[2]);
        int column = Integer.parseInt(request.getCommand().split("[ (),]")[3]);
        Collectable collectable = dataBase.getCurrentBattle().getPlayerInTurn().getSelectedCollectable();
        view.printOutputMessage(dataBase.getCurrentBattle().useCollectable(collectable, row, column));
    */
    }

    private void forfeitGame() {
        Main.getGlobalMediaPlayer().play();
        Account account = dataBase.getAccountWithUsername(dataBase.getCurrentBattle().getPlayerInTurn().getPlayerInfo().getPlayerName());
        Account player1 = dataBase.getAccountWithUsername(dataBase.getCurrentBattle().getPlayer1().getPlayerInfo().getPlayerName());
        Account player2 = dataBase.getAccountWithUsername(dataBase.getCurrentBattle().getPlayer2().getPlayerInfo().getPlayerName());
        MatchInfo matchInfo1 = player1.getMatchList().get(player1.getMatchList().size() - 1);
        MatchInfo matchInfo2 = player2.getMatchList().get(player2.getMatchList().size() - 1);
        if (player1 == account) {
            matchInfo1.setWinner(player2.getUsername());
            matchInfo2.setWinner(player2.getUsername());
        } else {
            matchInfo1.setWinner(player1.getUsername());
            matchInfo2.setWinner(player1.getUsername());
        }
    }

    private boolean endGame() {
        timeBar.setDisable(true);
        dataBase.setCurrentBattle(null);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "game has finished please press ok to exit to main menu");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
        returnToMainMenu();
        return true;
    }

    private void returnToMainMenu() {
        endTurnMineBtn.setDisable(true);
        graveYardBtn.setDisable(true);
        KeyValue keyValue = new KeyValue(Main.window.opacityProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), keyValue);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("ControllerMainMenu.fxml"));
                Main.window.setScene(new Scene(root));
                Main.setCursor();
            } catch (IOException ignored) {

            }
            KeyValue keyValueFinished = new KeyValue(Main.window.opacityProperty(), 1);
            KeyFrame keyFrameFinished = new KeyFrame(Duration.millis(2000), keyValueFinished);
            Timeline timelineFinished = new Timeline();
            timelineFinished.getKeyFrames().add(keyFrameFinished);
            timelineFinished.play();
        });
        timeline.play();
    }

    private boolean endTurn() {
        OutputMessageType outputMessageType = dataBase.getCurrentBattle().nextTurn();
        if (outputMessageType == OutputMessageType.WINNER_PLAYER1
                || outputMessageType == OutputMessageType.WINNER_PLAYER2) {
            return endGame();
            //todo check end game
        }
        return false;
    }

    public double getCellLayoutX(int column) {
        return GraphicConstants.BATTLE_GROUND_START_X + GraphicConstants.CELL_WIDTH * column;
    }

    public double getCellLayoutY(int row) {
        return GraphicConstants.BATTLE_GROUND_START_Y + GraphicConstants.CELL_HEIGHT * row;
    }

    public AnchorPane getBattleGroundPane() {
        return battleGroundPane;
    }

    public ImageView getNextCardRing() {
        return nextCardRing;
    }

    public List<UnitImage> getUnitImageList() {
        return unitImageList;
    }
}