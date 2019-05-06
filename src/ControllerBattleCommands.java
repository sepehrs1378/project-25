import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerBattleCommands {
    private static ControllerBattleCommands instance = new ControllerBattleCommands();
    private Request request = Request.getInstance();
    private DataBase database = DataBase.getInstance();
    private View view = View.getInstance();

    public static ControllerBattleCommands getInstance() {
        return instance;
    }

    private ControllerBattleCommands() {
    }

    public void main() {
        boolean didExit = false;
        while (!didExit) {
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
                    //todo handles to situations
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
                    endTurn();
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
                    didExit = endGame();
                    break;
                case FORFEIT:
                    forfeitGame();
                    didExit = endGame();
                    break;
                case SHOW_MENU:
                    showMenu();
                    break;
                case HELP:
                    help();
                    break;
                case SHOW_BATTLEGROUND:
                    showBattleground();
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    public void showGameInfo() {
        if (request.getCommand().equals("game info")) {
            view.showGameInfo(database.getCurrentBattle());
        }
    }

    /*public void showMinions() {
        if (request.getCommand().equals("show my minions")) {
            List<Unit> minions = database.getCurrentBattle().getBattleGround()
                    .getMinionsOfPlayer(database.getCurrentBattle().getPlayerInTurn());
            for (Unit minion : minions) {
                view.showMinionInBattle(minion, database.getCurrentBattle().getBattleGround().getCoordinationOfUnit(minion));
            }
        } else if (request.getCommand().equals("show opponent minions")) {
            Player player;
            if (database.getCurrentBattle().getPlayerInTurn() == database.getCurrentBattle().getPlayer1())
                player = database.getCurrentBattle().getPlayer2();
            else player = database.getCurrentBattle().getPlayer1();
            List<Unit> minions = database.getCurrentBattle().getBattleGround().getMinionsOfPlayer(player);
            for (Unit minion : minions) {
                view.showMinionInBattle(minion, database.getCurrentBattle().getBattleGround().getCoordinationOfUnit(minion));
            }
        }
    }
    */
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
        switch (database.getCurrentBattle().getPlayerInTurn().select(id)) {
            case INVALID_COLLECTABLE_CARD:
                view.printOutputMessage(OutputMessageType.INVALID_COLLECTABLE_CARD);
                break;
            case UNIT_IS_STUNNED:
                view.printOutputMessage(OutputMessageType.UNIT_IS_STUNNED);
                break;
            case SELECTED:
                view.printOutputMessage(OutputMessageType.SELECTED);
                break;
            default:
        }
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
        switch (database.getCurrentBattle().getPlayerInTurn()
                .getSelectedUnit().attack(id)) {
            case TARGET_NOT_IN_RANGE:
                view.printOutputMessage(OutputMessageType.TARGET_NOT_IN_RANGE);
                break;
            case INVALID_CARD:
                view.printOutputMessage(OutputMessageType.INVALID_CARD);
                break;
            case ALREADY_ATTACKED:
                view.printOutputMessage(OutputMessageType.ALREADY_ATTACKED);
                break;
            default:
        }
    }

    private void attackCombo() {
        String[] orderPieces = request.getCommand().split(" ");
        String[] attackers = new String[orderPieces.length - 3];
        if (orderPieces.length - 3 >= 0)
            System.arraycopy(orderPieces, 3, attackers, 0
                    , orderPieces.length - 3);
        switch (Unit.attackCombo(orderPieces[2], attackers)) {
            case A_UNIT_CANT_ATTACK_TARGET:
                view.printOutputMessage(OutputMessageType.A_UNIT_CANT_ATTACK_TARGET);
                break;
            case A_UNIT_DOESNT_EXIST:
                view.printOutputMessage(OutputMessageType.A_UNIT_DOESNT_EXIST);
                break;
            case COMBO_ATTACK_SUCCESSFUL:
                view.printOutputMessage(OutputMessageType.COMBO_ATTACK_SUCCESSFUL);
                break;
            default:
        }
    }

    private void useSpecialPower() {
        Pattern pattern = Pattern.compile("use special power [(](\\d+),(\\d+)[)]");
        Matcher matcher = pattern.matcher(request.getCommand());
        if (!matcher.find()) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        if (Integer.parseInt(matcher.group(1)) < 5 && Integer.parseInt(matcher.group(1)) >= 0
                && Integer.parseInt(matcher.group(2)) < 9 && Integer.parseInt(matcher.group(2)) >= 0) {
            int row = Integer.parseInt(matcher.group(1));
            int column = Integer.parseInt(matcher.group(2));
            Player player = database.getCurrentBattle().getPlayerInTurn();
            Unit hero = database.getCurrentBattle().getBattleGround().getHeroOfPlayer(player);
            view.printOutputMessage(database.getCurrentBattle().useSpecialPower(hero, player, row, column));
        } else {
            view.printOutputMessage(OutputMessageType.INVALID_NUMBER);
        }
    }

    private void useCollectable() {
        //todo complete it later
    }

    private void insertName() {
        String id = request.getCommand().split("[ (),]")[1];//todo is it correct?
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
            matchInfo1.setWinner(player2);
            matchInfo2.setWinner(player2);
        } else {
            matchInfo1.setWinner(player1);
            matchInfo2.setWinner(player1);
        }
    }

    private boolean endGame() {
        database.setCurrentBattle(null);
        return true;
    }

    private void endTurn() {
        view.printOutputMessage(database.getCurrentBattle().nextTurn());
    }

    public void enter() {
        if (!request.getCommand().equals("enter graveyard")) {
            request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
            view.printOutputMessage(request.getOutputMessageType());
        } else {
            ControllerGraveYard.getInstance().main();
        }
    }

    public void help() {
        view.printList(database.getCurrentBattle().getAvailableMoves());
    }
}
