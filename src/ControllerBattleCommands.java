import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerBattleCommands {
    private static ControllerBattleCommands ourInstance;
    private Request request = Request.getInstance();
    private DataBase database = DataBase.getInstance();
    private View view = View.getInstance();

    @FXML
    private ImageView endTurnMineBtn;

    @FXML
    private ImageView endTurnEnemyBtn;

    @FXML
    void makeEndTurnMineOpaque(MouseEvent event) {
        endTurnMineBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeEndTurnMineTransparent(MouseEvent event) {
        endTurnMineBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    void endTurn(MouseEvent event) throws GoToMainMenuException {
//        endTurn(); todo
        endTurnMineBtn.setVisible(false);
        endTurnEnemyBtn.setVisible(true);
    }

    public static ControllerBattleCommands getOurInstance() {
        return ourInstance;
    }

    public ControllerBattleCommands() {
        ourInstance = this;
    }

    public void main() throws GoToMainMenuException {
        boolean didExit = false;
        while (!didExit) {
            try {
                if (database.getCurrentBattle().getSingleOrMulti().equals(Constants.SINGLE)
                        && database.getCurrentBattle().getPlayerInTurn() == database.getCurrentBattle().getPlayer2()) {
                    AI.getInstance().doNextMove();
                    endTurn();
                }
                database.getCurrentBattle().checkForDeadUnits();
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
            view.showGameInfo(database.getCurrentBattle());
        }
    }

    private void showMyMinions() {
        List<Unit> minions = database.getCurrentBattle().getBattleGround()
                .getMinionsOfPlayer(database.getCurrentBattle().getPlayerInTurn());
        for (Unit minion : minions) {
            view.showMinionInBattle(minion, database.getCurrentBattle().getBattleGround().getCoordinationOfUnit(minion));
        }
    }

    private void showOpponentMinions() {
        Player player;
        if (database.getCurrentBattle().getPlayerInTurn() == database.getCurrentBattle().getPlayer1())
            player = database.getCurrentBattle().getPlayer2();
        else player = database.getCurrentBattle().getPlayer1();
        List<Unit> minions = database.getCurrentBattle().getBattleGround().getMinionsOfPlayer(player);
        for (Unit minion : minions) {
            view.showMinionInBattle(minion, database.getCurrentBattle().getBattleGround().getCoordinationOfUnit(minion));
        }
    }

    private void showCardInfoId() {
        String cardId = request.getCommand().split("\\s+")[3];
        Card card = database.getCurrentBattle().getBattleGround().getCardByID(cardId);
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
        view.showCollectables(database.getCurrentBattle().getPlayerInTurn().getCollectables());
    }

    private void showInfo() {
        if (database.getCurrentBattle().getPlayerInTurn().getSelectedCollectable() != null) {
            view.showCollectable(database.getCurrentBattle().getPlayerInTurn().getSelectedCollectable());
        }
    }

    private void showNextCard() {
        Card card = database.getCurrentBattle().getPlayerInTurn().getNextCard();
        if (card instanceof Spell) {
            view.showCardInfoSpell((Spell) card);
        } else if (card instanceof Unit) {
            view.showCardInfoMinion((Unit) card);
        }
    }

    private void showHand() {
        view.showHand(database.getCurrentBattle().getPlayerInTurn().getHand());
    }

    private void showMenu() {
        view.printHelp(HelpType.BATTLE_COMMANDS_HELP);
    }

    private void showBattleground() {
        for (int i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (int j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                Cell cell = database.getCurrentBattle().getBattleGround().getCells()[i][j];
                if (cell.getUnit() == null && cell.getFlags().isEmpty() && cell.getCollectable() == null) {
                    view.showCell(" ");
                    continue;
                }
                if (cell.getUnit() != null) {
                    if (cell.getUnit().getId().split("_")[0].equals(database.getCurrentBattle().getPlayer1().getPlayerInfo().getPlayerName())) {
                        if (cell.getUnit().getHeroOrMinion().equals(Constants.HERO)) {
                            view.showCell("H");
                        } else view.showCell("1");
                    } else if (cell.getUnit().getId().split("_")[0].equals(database.getCurrentBattle().getPlayer2().getPlayerInfo().getPlayerName())) {
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
        view.printOutputMessage(database.getCurrentBattle().getPlayerInTurn().select(id));
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
        switch (database.getCurrentBattle().getBattleGround().
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
                view.showUnitMove(database.getCurrentBattle().
                                getPlayerInTurn().getSelectedUnit().getId()
                        , destinationRow, destinationColumn);
                break;
            default:
        }
    }

    private void attackId() {
        String id = request.getCommand().split(" ")[1];
        view.printOutputMessage(database.getCurrentBattle()
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
        Player player = database.getCurrentBattle().getPlayerInTurn();
        Unit hero = database.getCurrentBattle().getBattleGround().getHeroOfPlayer(player);
        view.printOutputMessage(database.getCurrentBattle().useSpecialPower(hero, player, row, column));
    }

    public void useCollectable() {
        int row = Integer.parseInt(request.getCommand().split("[ (),]")[2]);
        int column = Integer.parseInt(request.getCommand().split("[ (),]")[3]);
        Collectable collectable = database.getCurrentBattle().getPlayerInTurn().getSelectedCollectable();
        view.printOutputMessage(database.getCurrentBattle().useCollectable(collectable, row, column));
    }

    private void insertName() {
        String id = request.getCommand().split("[ (),]")[1];
        int row = Integer.parseInt(request.getCommand().split("[ (),]")[4]);
        int column = Integer.parseInt(request.getCommand().split("[ (),]")[5]);
        Card card = database.getCurrentBattle().getPlayerInTurn()
                .getHand().getCardByName(id);
        view.printOutputMessage(database.getCurrentBattle().insert(card, row, column));
    }

    private void forfeitGame() {
        Account account = database.getAccountWithUsername(database.getCurrentBattle().getPlayerInTurn().getPlayerInfo().getPlayerName());
        Account player1 = database.getAccountWithUsername(database.getCurrentBattle().getPlayer1().getPlayerInfo().getPlayerName());
        Account player2 = database.getAccountWithUsername(database.getCurrentBattle().getPlayer2().getPlayerInfo().getPlayerName());
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
        database.setCurrentBattle(null);
        return true;
    }

    private boolean endTurn() throws GoToMainMenuException {
        OutputMessageType outputMessageType = database.getCurrentBattle().nextTurn();
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
}
