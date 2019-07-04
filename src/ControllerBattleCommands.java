import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerBattleCommands implements Initializable {
    private static DataBase dataBase = DataBase.getInstance();
    private static ControllerBattleCommands ourInstance;
    private View view = View.getInstance();
    private Request request = Request.getInstance();
    private Player loggedInPlayer;
    private List<ImageView> handRings = new ArrayList<>();
    private List<UnitImage> unitImageList = new ArrayList<>();
    private List<HandImage> handImageList = new ArrayList<>();
    private Label[][] battleGroundCells = new Label[5][9];
    private ImageView clickedImageView = new ImageView();//todo

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
        graveYardBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeGraveYardBtnTransparent(MouseEvent event) {
        graveYardBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void makeEndTurnMineOpaque(MouseEvent event) {
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
    void endTurn(MouseEvent event) throws GoToMainMenuException {
        //todo
        clickedImageView = null;
        endTurn();
        updatePane();
//        endTurnMineBtn.setVisible(false);
//        endTurnEnemyBtn.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startTempBattle();//todo remove it later
        this.loggedInPlayer = dataBase.getCurrentBattle().getPlayerInTurn();
        setupBattleGroundCells(battleGroundPane);
        setupHandRings();
        setupHeroesImages(battleGroundPane);
        setupPlayerInfoViews(battleGroundPane);
        setupCursor();
        Main.window.setScene(new Scene(battleGroundPane));
        player1Label.setText(dataBase.getCurrentBattle().getPlayer1().getPlayerInfo().getPlayerName());
        player2Label.setText(dataBase.getCurrentBattle().getPlayer2().getPlayerInfo().getPlayerName());
        Unit hero = dataBase.getLoggedInAccount().getMainDeck().getHero();
        try {
            specialPowerView.setImage(new Image(new FileInputStream
                    ("src/ApProjectResources/units/" + hero.getName() + "/special_power.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Usable usable = (Usable) dataBase.getLoggedInAccount().getMainDeck().getItem();
        try {
            if (usable != null) {
                usableView.setImage(new Image(new FileInputStream("/src/ApProjectResources/units/" + usable.getName() + "/usable")));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        updatePane();
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

    private void startTempBattle() {
        Battle battle = new Battle(DataBase.getInstance().getLoggedInAccount(), DataBase.getInstance().getAccountWithUsername("temp2")
                , Constants.CLASSIC, 0, null, Constants.SINGLE);
        DataBase.getInstance().setCurrentBattle(battle);
    }

    private void setupHeroesImages(AnchorPane root) {
        Unit playerHero = dataBase.getCurrentBattle().getPlayer1().getDeck().getHero();
        Unit opponentHero = dataBase.getCurrentBattle().getPlayer2().getDeck().getHero();
        UnitImage playerHeroImage = new UnitImage(playerHero.getId(), root);
        UnitImage opponentHeroImage = new UnitImage(opponentHero.getId(), root);
        unitImageList.add(opponentHeroImage);
        unitImageList.add(playerHeroImage);
        playerHeroImage.setInCell(2, 0);
        opponentHeroImage.setInCell(2, 8);
        opponentHeroImage.getUnitView().setScaleX(-1);
    }

    private void setupBattleGroundCells(AnchorPane root) {
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 9; column++) {
                battleGroundCells[row][column] = setLabelStyle(new Label());
                battleGroundCells[row][column].relocate
                        (getCellLayoutX(column), getCellLayoutY(row));
                battleGroundCells[row][column].setMinWidth(63);
                battleGroundCells[row][column].setMinHeight(50);
                root.getChildren().add(battleGroundCells[row][column]);
                final int cellRow = row;
                final int cellColumn = column;
                battleGroundCells[row][column].setOnMouseClicked(event -> {
                    handleCellClicked(cellRow, cellColumn);
                });
            }
        }
    }

    public void handleCellClicked(int row, int column) {
        //todo complete it for other purposes too
        if (isClickedImageViewInHand())
            handleCardInsertion(row, column);
        handleUnitMove(row, column);
        updatePane();
    }

    private void handleUnitMove(int row, int column) {
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
                break;
            default:
        }
    }

    private void handleCardInsertion(int row, int column) {
        HandImage handImage = getHandImageWithCardView(clickedImageView);
        Card card = dataBase.getCurrentBattle()
                .getPlayerInTurn().getHand().getCardById(handImage.getId());
        switch (dataBase.getCurrentBattle().insert(card, row, column)) {
            case NO_SUCH_CARD_IN_HAND:
                System.out.println();
                //empty
                break;
            case NOT_ENOUGH_MANA:
                //empty
                break;
            case INVALID_NUMBER:
                //empty
                break;
            case NOT_NEARBY_FRIENDLY_UNITS:
                //empty
                break;
            case THIS_CELL_IS_FULL:
                //empty
                break;
            case CARD_INSERTED:
                insertUnitView(row, column, card);
                handImage.clearHandImage();
                break;
            default:
        }
    }

    private void insertUnitView(int row, int column, Card card) {
        UnitImage insertedUnitView = new UnitImage(card.getId(), battleGroundPane);
        unitImageList.add(insertedUnitView);
        insertedUnitView.setInCell(row, column);
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
        if (currentPlayer.getSelectedUnit() == null && currentPlayer.getSelectedCollectable() == null) {
            handleUnitSelection(id);
        }
        if (currentPlayer.getSelectedUnit() != null) {
            handleUnitAttack(id);
        }
        updatePane();
    }

    private void handleUnitAttack(String id) {
        Player currentPlayer = dataBase.getCurrentBattle().getPlayerInTurn();
        UnitImage selectedUnitImage = getUnitImageWithId(currentPlayer.getSelectedUnit().getId());
        UnitImage targetedUnitImage = getUnitImageWithId(id);
        //todo add this feature to unselect a unit with clicking on it if needed
        switch (currentPlayer.getSelectedUnit().attack(id)) {
            case UNIT_ATTACKED:
                selectedUnitImage.showAttack(targetedUnitImage.getColumn());
                break;
            case UNIT_AND_ENEMY_ATTACKED:
                selectedUnitImage.showAttack(targetedUnitImage.getColumn());
                targetedUnitImage.showAttack(selectedUnitImage.getColumn());
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
        }
    }

    private void handleUnitSelection(String id) {
        Player currentPlayer = dataBase.getCurrentBattle().getPlayerInTurn();
        UnitImage unitImage = getUnitImageWithId(id);
        //todo fix this: if a friendly unit is selected, another unit cannot be selected
        switch (currentPlayer.selectUnit(id)) {
            case SELECTED:
                unitImage.setUnitStyleAsSelected();
                clickedImageView = unitImage.getUnitView();
                break;
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

    private void setupPlayerInfoViews(AnchorPane root) {
        PlayerInfoView player1InfoView = new PlayerInfoView(1);
        PlayerInfoView player2InfoView = new PlayerInfoView(2);
    }

    private Label setLabelStyle(Label label) {
        label.setMinWidth(60);
        label.setMinHeight(60);
        label.setStyle("-fx-background-radius: 10;-fx-background-color: #ebdad5;-fx-opacity: .2");
        label.setOnMouseEntered(e -> {
            label.setStyle("-fx-background-radius: 10;-fx-background-color: #ebdad5;-fx-opacity: .4");
        });
        label.setOnMouseExited(e -> {
            label.setStyle("-fx-background-radius: 10;-fx-background-color: #ebdad5;-fx-opacity: .2");
        });
        return label;
    }

    public UnitImage getUnitImageWithId(String id) {
        for (UnitImage unitImage : unitImageList) {
            if (unitImage.getId().equals(id))
                return unitImage;
        }
        return null;
    }

    public void updatePane() {
        for (UnitImage unitImage : unitImageList) {
            BattleGround battleGround = dataBase.getCurrentBattle().getBattleGround();
            Unit unit = battleGround.getUnitWithID(unitImage.getId());
            unitImage.setApNumber(unit.getAp());
            unitImage.setHpNumber(unit.getHp());
            if (unitImage.getUnitView().equals(clickedImageView))
                unitImage.setUnitStyleAsSelected();
            else unitImage.setStyleAsNotSelected();
        }
        Collectable collectable = dataBase.getCurrentBattle().getCollectable();
        Player player1 = dataBase.getCurrentBattle().getPlayer1();
        if (!player1.getCollectables().isEmpty()){
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
        playerManaLabel.setText(Integer.toString(dataBase.getCurrentBattle().getPlayer1().getMana()));
        computerManaLabel.setText(Integer.toString(dataBase.getCurrentBattle().getPlayer1().getMana()));
        List<Card> handCards = loggedInPlayer.getHand().getCards();
        for (int i = 0; i < handCards.size(); i++) {
            Card card = handCards.get(i);
            handImageList.get(i).setCardImage(card.getId());
        }
    }

    public Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public void main() throws GoToMainMenuException {
        boolean didExit = false;
        while (!didExit) {
            try {
                if (dataBase.getCurrentBattle().getSingleOrMulti().equals(Constants.SINGLE)
                        && dataBase.getCurrentBattle().getPlayerInTurn() == dataBase.getCurrentBattle().getPlayer2()) {
                    AI.getInstance().doNextMove();
                    endTurn();
                }
                dataBase.getCurrentBattle().checkForDeadUnits();
                request.getNewCommand();
                switch (request.getType()) {
                    case GAME_INFO:
                        showGameInfo();
                        break;
                    case SHOW_MY_MINIONS:
                        showMyMinions();
                        break;
                    case SHOW_OPPONENT_MINIONS:
                        showOpponentMinions();
                        break;
                    case SHOW_CARD_INFO_ID:
                        showCardInfoId();
                        break;
                    case SELECT_ID:
                        selectId();
                        break;
                    case MOVE_TO_X_Y:
                        moveTo();
                        break;
                    case ATTACK_ID:
                        attackId();
                        break;
                    case ATTACK_COMBO:
                        attackCombo();
                        break;
                    case USE_SPECIAL_POWER_X_Y:
                        useSpecialPower();
                        break;
                    case SHOW_HAND:
                        showHand();
                        break;
                    case INSERT_NAME_IN_X_Y:
                        insertName();
                        break;
                    case END_TURN:
                        didExit = endTurn();
                        break;
                    case SHOW_COLLECTABLES:
                        showCollectables();
                        break;
                    case SHOW_INFO:
                        showInfo();
                        break;
                    case USE_COLLECTABLE_IN_X_Y:
                        useCollectable();
                        break;
                    case SHOW_NEXT_CARD:
                        showNextCard();
                        break;
                    case ENTER:
                        enter();
                        break;
                    case END_GAME:
                        endGame();
                        throw new GoToMainMenuException("go to main menu");
                    case FORFEIT:
                        forfeitGame();
                        endGame();
                        throw new GoToMainMenuException("go to main menu");
                    case SHOW_MENU:
                        showMenu();
                        break;
                    case SHOW_BATTLEGROUND:
                        showBattleground();
                        break;
                    default:
                        view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
                }
            } catch (GoToMainMenuException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showGameInfo() {
        if (request.getCommand().equals("game info")) {
            view.showGameInfo(dataBase.getCurrentBattle());
        }
    }

    private void showMyMinions() {
        List<Unit> minions = dataBase.getCurrentBattle().getBattleGround()
                .getMinionsOfPlayer(dataBase.getCurrentBattle().getPlayerInTurn());
        for (Unit minion : minions) {
            view.showMinionInBattle(minion, dataBase.getCurrentBattle().getBattleGround().getCoordinationOfUnit(minion));
        }
    }

    private void showOpponentMinions() {
        Player player;
        if (dataBase.getCurrentBattle().getPlayerInTurn() == dataBase.getCurrentBattle().getPlayer1())
            player = dataBase.getCurrentBattle().getPlayer2();
        else player = dataBase.getCurrentBattle().getPlayer1();
        List<Unit> minions = dataBase.getCurrentBattle().getBattleGround().getMinionsOfPlayer(player);
        for (Unit minion : minions) {
            view.showMinionInBattle(minion, dataBase.getCurrentBattle().getBattleGround().getCoordinationOfUnit(minion));
        }
    }

    private void showCardInfoId() {
        String cardId = request.getCommand().split("\\s+")[3];
        Card card = dataBase.getCurrentBattle().getBattleGround().getCardByID(cardId);
        if (card != null) {
            if (card instanceof Spell) {
                view.showCardInfoSpell((Spell) card);
                return;
            }
            if (card instanceof Unit) {
                if (((Unit) card).getHeroOrMinion().equals(Constants.MINION)) {
                    view.showCardInfoMinion((Unit) card);
                } else if (((Unit) card).getHeroOrMinion().equals(Constants.HERO)) {
                    view.showCardInfoHero((Unit) card);
                }
            }
        } else
            view.printOutputMessage(OutputMessageType.NO_CARD_IN_BATTLEGROUND);
    }

    private void showCollectables() {
        view.showCollectables(dataBase.getCurrentBattle().getPlayerInTurn().getCollectables());
    }

    private void showInfo() {
        if (dataBase.getCurrentBattle().getPlayerInTurn().getSelectedCollectable() != null) {
            view.showCollectable(dataBase.getCurrentBattle().getPlayerInTurn().getSelectedCollectable());
        }
    }

    private void showNextCard() {
        Card card = dataBase.getCurrentBattle().getPlayerInTurn().getNextCard();
        if (card instanceof Spell) {
            view.showCardInfoSpell((Spell) card);
        } else if (card instanceof Unit) {
            view.showCardInfoMinion((Unit) card);
        }
    }

    private void showHand() {
        view.showHand(dataBase.getCurrentBattle().getPlayerInTurn().getHand());
    }

    private void showMenu() {
        view.printHelp(HelpType.BATTLE_COMMANDS_HELP);
    }

    private void showBattleground() {
        for (int i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (int j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                Cell cell = dataBase.getCurrentBattle().getBattleGround().getCells()[i][j];
                if (cell.getUnit() == null && cell.getFlags().isEmpty() && cell.getCollectable() == null) {
                    view.showCell(" ");
                    continue;
                }
                if (cell.getUnit() != null) {
                    if (cell.getUnit().getId().split("_")[0].equals(dataBase.getCurrentBattle().getPlayer1().getPlayerInfo().getPlayerName())) {
                        if (cell.getUnit().getHeroOrMinion().equals(Constants.HERO)) {
                            view.showCell("H");
                        } else view.showCell("1");
                    } else if (cell.getUnit().getId().split("_")[0].equals(dataBase.getCurrentBattle().getPlayer2().getPlayerInfo().getPlayerName())) {
                        if (cell.getUnit().getHeroOrMinion().equals(Constants.HERO)) {
                            view.showCell("h");
                        } else view.showCell("2");
                        continue;
                    }
                }
                if (!cell.getFlags().isEmpty()) {
                    view.showCell("f");
                    continue;
                }
                if (cell.getCollectable() != null) {
                    view.showCell("c");
                }
            }
            view.print("");
        }

    }

    private void selectId() {
        String id = request.getCommand().split(" ")[1];
        view.printOutputMessage(dataBase.getCurrentBattle().getPlayerInTurn().select(id));
    }

    private void moveTo() {
        Pattern pattern = Pattern.compile("^move to [(](\\d+),(\\d+)[)]$");
        Matcher matcher = pattern.matcher(request.getCommand().toLowerCase());
        if (!matcher.find()) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        int destinationRow = Integer.parseInt(matcher.group(1));
        int destinationColumn = Integer.parseInt(matcher.group(2));
        switch (dataBase.getCurrentBattle().getBattleGround().
                moveUnit(destinationRow, destinationColumn)) {
            case UNIT_NOT_SELECTED:
                view.printOutputMessage(OutputMessageType.UNIT_NOT_SELECTED);
                break;
            case OUT_OF_BOUNDARIES:
                view.printOutputMessage(OutputMessageType.OUT_OF_BOUNDARIES);
                break;
            case CELL_IS_FULL:
                view.printOutputMessage(OutputMessageType.CELL_IS_FULL);
                break;
            case CELL_OUT_OF_RANGE:
                view.printOutputMessage(OutputMessageType.CELL_OUT_OF_RANGE);
                break;
            case UNIT_ALREADY_MOVED:
                view.printOutputMessage(OutputMessageType.UNIT_ALREADY_MOVED);
                break;
            case UNIT_MOVED:
                view.showUnitMove(dataBase.getCurrentBattle().
                                getPlayerInTurn().getSelectedUnit().getId()
                        , destinationRow, destinationColumn);
                break;
            default:
        }
    }

    private void attackId() {
        String id = request.getCommand().split(" ")[1];
        view.printOutputMessage(dataBase.getCurrentBattle()
                .getPlayerInTurn().getSelectedUnit().attack(id));
    }

    private void attackCombo() {
        String[] orderPieces = request.getCommand().split(" ");
        String[] attackers = new String[orderPieces.length - 3];
        if (orderPieces.length - 3 >= 0)
            System.arraycopy(orderPieces, 3, attackers, 0
                    , orderPieces.length - 3);
        view.printOutputMessage(Unit.attackCombo(orderPieces[2], attackers));
    }

    public void useSpecialPower() {
        int row = Integer.parseInt(request.getCommand().split("[ (),]")[4]);
        int column = Integer.parseInt(request.getCommand().split("[ (),]")[5]);
        Player player = dataBase.getCurrentBattle().getPlayerInTurn();
        Unit hero = dataBase.getCurrentBattle().getBattleGround().getHeroOfPlayer(player);
        view.printOutputMessage(dataBase.getCurrentBattle().useSpecialPower(hero, player, row, column));
    }

    public void useCollectable() {
        int row = Integer.parseInt(request.getCommand().split("[ (),]")[2]);
        int column = Integer.parseInt(request.getCommand().split("[ (),]")[3]);
        Collectable collectable = dataBase.getCurrentBattle().getPlayerInTurn().getSelectedCollectable();
        view.printOutputMessage(dataBase.getCurrentBattle().useCollectable(collectable, row, column));
    }

    private void insertName() {
        /*String id = request.getCommand().split("[ (),]")[1];
        int row = Integer.parseInt(request.getCommand().split("[ (),]")[4]);
        int column = Integer.parseInt(request.getCommand().split("[ (),]")[5]);
        Card card = dataBase.getCurrentBattle().getPlayerInTurn()
                .getHand().getCardByName(id);
        view.printOutputMessage(dataBase.getCurrentBattle().insert(card, row, column));
   */
    }

    private void forfeitGame() {
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
        dataBase.setCurrentBattle(null);
        return true;
    }

    private boolean endTurn() throws GoToMainMenuException {
        OutputMessageType outputMessageType = dataBase.getCurrentBattle().nextTurn();
        view.printOutputMessage(outputMessageType);
        if (outputMessageType == OutputMessageType.WINNER_PLAYER1
                || outputMessageType == OutputMessageType.WINNER_PLAYER2) {
            endGame();
            throw new GoToMainMenuException("go to main menu");
        }
        return false;
    }

    public void enter() {
        if (!request.getCommand().equals("enter graveyard")) {
            request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
            view.printOutputMessage(request.getOutputMessageType());
        } else {
            ControllerGraveYard.getInstance().main();
        }
    }

    public double getCellLayoutX(int column) {
        return GraphicConstants.BATTLE_GROUND_START_X + GraphicConstants.CELL_WIDTH * column;
    }

    public double getCellLayoutY(int row) {
        return GraphicConstants.BATTLE_GROUND_START_Y + GraphicConstants.CELL_HEIGHT * row;
    }

    public double getHandRingLayoutX(int number) {
        return handRings.get(number).getLayoutX();
    }

    public double getHandRingLayoutY(int number) {
        return handRings.get(number).getLayoutX();
    }

    public AnchorPane getBattleGroundPane() {
        return battleGroundPane;
    }

    public ImageView getNextCardRing() {
        return nextCardRing;
    }
}