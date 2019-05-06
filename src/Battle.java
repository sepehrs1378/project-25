import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.ArrayList;
import java.util.List;

public class Battle {
    private static final DataBase dataBase = DataBase.getInstance();
    private static final ControllerMatchInfo controllerMatchInfo = ControllerMatchInfo.getInstance();
    private Player player1;
    private Player player2;
    private BattleGround battleGround = new BattleGround();
    private Player playerInTurn;
    private String mode;
    private Collectable collectable;
    private int turnNumber = 1;
    private boolean isBattleFinished = false;
    private int numberOfFlags;

    public Battle(Account firstPlayerAccount, Account secondPlayerAccount
            , String mode, int numberOfFlags, Collectable collectable) {
        dataBase.setCurrentBattle(this);
        player1 = new Player(firstPlayerAccount.getPlayerInfo(), firstPlayerAccount.getMainDeck());
        player2 = new Player(secondPlayerAccount.getPlayerInfo(), secondPlayerAccount.getMainDeck());
        playerInTurn = player1;
        this.mode = mode;
        this.numberOfFlags = numberOfFlags;
        List<Flag> flags = new ArrayList<>();
        for (int i = 0; i < numberOfFlags; i++)
            flags.add(new Flag());
        battleGround.addFlagsToBattleGround(flags);
        battleGround.setCollectableOnGround(collectable);
        this.collectable = collectable;
        MatchInfo matchInfo1 = new MatchInfo();
        MatchInfo matchInfo2 = new MatchInfo();
        Account playerAccount1 = dataBase.getAccountWithUsername(dataBase.getCurrentBattle().getPlayer1().getPlayerInfo().getPlayerName());
        Account playerAccount2 = dataBase.getAccountWithUsername(dataBase.getCurrentBattle().getPlayer2().getPlayerInfo().getPlayerName());
        playerAccount1.addMatchToMatchList(matchInfo1);
        playerAccount2.addMatchToMatchList(matchInfo2);
        matchInfo1.setOpponent(playerAccount2);
        matchInfo2.setOpponent(playerAccount1);
        startBattle();
    }

    public OutputMessageType nextTurn() {
        if (isBattleFinished)
            return OutputMessageType.BATTLE_FINISHED;
        if(checkEndBattle() != null){

        }
        reviveContinuousBuffs();
        removeExpiredBuffs();
        resetUnitsMoveAndAttack();
        doBuffsEffects();
        checkForDeadUnits();
        //todo check turns of flag in hand??
        changeTurn();
        turnNumber++;
        setManaBasedOnTurnNumber();
        checkEndBattle();
        return OutputMessageType.TURN_CHANGED;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public BattleGround getBattleGround() {
        return battleGround;
    }

    public String getMode() {
        return mode;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void changeTurn() {
        if (playerInTurn == player1)
            playerInTurn = player2;
        else playerInTurn = player1;
    }

    public boolean isBattleFinished() {
        return isBattleFinished;
    }

    public void setBattleFinished(boolean battleFinished) {
        isBattleFinished = battleFinished;
    }

    public Player getPlayerInTurn() {
        return playerInTurn;
    }

    public void killUnit(Unit unit) {
        if (unit.getId().contains(player1.getPlayerInfo().getPlayerName()))
            player1.getGraveYard().addDeadCard(unit);
        if (unit.getId().contains(player2.getPlayerInfo().getPlayerName()))
            player2.getGraveYard().addDeadCard(unit);
        for (Spell specialPower : unit.getSpecialPowers()) {
            if (specialPower.equals(SpellActivationType.ON_DEATH))
                specialPower.doSpell(battleGround.getCoordinationOfUnit(unit)[0]
                        , battleGround.getCoordinationOfUnit(unit)[1]);
        }
        this.getBattleGround().getCellOfUnit(unit).setUnit(null);
    }

    public void checkForDeadUnits() {
        for (int i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (int j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                Cell cell = battleGround.getCells()[i][j];
                if (cell.isEmptyOfUnit())
                    continue;
                if (cell.getUnit().getHp() <= 0)
                    killUnit(cell.getUnit());
            }
        }
    }

    public void setManaBasedOnTurnNumber() {
        if (turnNumber % 2 == 1) {
            if (turnNumber < 14) {
                player1.setMana((turnNumber + 1) / 2 + 1);
                player2.setMana(0);
            } else {
                player1.setMana(9);
                player2.setMana(0);
            }
        } else {
            if (turnNumber < 15) {
                player1.setMana(0);
                player2.setMana((turnNumber / 2 + 2));
            } else {
                player1.setMana(0);
                player2.setMana(9);
            }
        }
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public void setNumberOfFlags(int numberOfFlags) {
        this.numberOfFlags = numberOfFlags;
    }

    public Player checkEndBattle() {
        if (mode.equals(Constants.CLASSIC)) {
            return checkEndBattleModeClassic();
        }
        if (mode.equals(Constants.ONE_FLAG)) {
            return checkEndBattleModeOneFlag();
        }
        if (mode.equals(Constants.FLAGS)) {
            return checkEndBattleModeFlags();
        }
        return null;
    }

    public Player checkEndBattleModeClassic() {
        if (player1.getDeck().getHero().getHp() <= 0) {
            isBattleFinished = true;
            return player2;
        } else if (player2.getDeck().getHero().getHp() <= 0) {
            isBattleFinished = true;
            return player1;
        }
        return null;
    }

    public Player checkEndBattleModeOneFlag() {
        int numberOfFlagsPlayer1 = battleGround.getNumberOfFlagsForPlayer(player1);
        int numberOfFlagsPlayer2 = battleGround.getNumberOfFlagsForPlayer(player2);
        if (numberOfFlagsPlayer1 >= getNumberOfFlags() / 2 + 1) {
            isBattleFinished = true;
            return player1;
        } else if (numberOfFlagsPlayer2 >= getNumberOfFlags() / 2 + 1) {
            isBattleFinished = true;
            return player2;
        }
        return null;
    }

    public Player checkEndBattleModeFlags() {
        Cell cell = battleGround.getCellWithFlag();
        if (cell != null) {
            if (cell.getUnit().getFlags().get(0).getTurnsInUnitHand() >= 6) {
                isBattleFinished = true;
                if (cell.getUnit().getId().contains(player1.getPlayerInfo().getPlayerName()))
                    return player1;
                return player2;
            }
        }
        return null;
    }

    public Card getCardByCardID(String id) {
        Card card = this.getBattleGround().getCardByID(id);
        return null;
        //todo complete this method
    }

    public List<Player> getPlayersHavingBuff(Buff buff) {
        List<Player> players = new ArrayList<>();
        if (player1.getBuffs().contains(buff))
            players.add(player1);
        if (player2.getBuffs().contains(buff))
            players.add(player2);
        return players;
    }

    public void reviveContinuousBuffs() {
        for (int i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (int j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                Cell cell = dataBase.getCurrentBattle().getBattleGround().getCells()[i][j];
                for (Buff buff : cell.getBuffs()) {
                    if (buff.isContinuous())
                        buff.revive();
                }
                if (cell.getUnit() == null)
                    continue;
                for (Buff buff : cell.getUnit().getBuffs()) {
                    if (buff.isContinuous())
                        buff.revive();
                }
            }
        }
    }

    public void removeExpiredBuffs() {
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                Cell cell = dataBase.getCurrentBattle().getBattleGround().getCells()[i][j];
                for (Buff buff : cell.getBuffs()) {
                    if (buff.isExpired())
                        buff.remove();
                }
            }
        }
        for (Buff buff : player1.getBuffs()) {
            if (buff.isExpired())
                buff.remove();
        }
        for (Buff buff : player2.getBuffs()) {
            if (buff.isExpired())
                buff.remove();
        }
    }

    public void resetUnitsMoveAndAttack() {
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                Cell cell = dataBase.getCurrentBattle().getBattleGround().getCells()[i][j];
                if (cell.isEmptyOfUnit())
                    continue;
                Unit unit = cell.getUnit();
                unit.setDidAttackThisTurn(false);
                unit.setDidMoveThisTurn(false);
            }
        }
    }

    public void doBuffsEffects() {
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                Cell cell = dataBase.getCurrentBattle().getBattleGround().getCells()[i][j];
                for (Buff buff : cell.getBuffs()) {
                    buff.doEffect();
                }
                if (cell.isEmptyOfUnit())
                    continue;
                for (Buff buff : cell.getUnit().getBuffs()) {
                    buff.doEffect();
                }
            }
        }
        for (Buff buff : player1.getBuffs())
            buff.doEffect();
        for (Buff buff : player2.getBuffs())
            buff.doEffect();
    }

    public OutputMessageType insert(Card card, int row, int column) {
        if (card == null)
            return OutputMessageType.NO_SUCH_CARD_IN_HAND;
        if (card.getMana() > playerInTurn.getMana())
            return OutputMessageType.NOT_ENOUGH_MANA;
        if (card instanceof Unit) {
            Unit unit = (Unit) card;
            if (row >= Constants.BATTLE_GROUND_WIDTH || row < 0
                    || column >= Constants.BATTLE_GROUND_LENGTH || column < 0)
                return OutputMessageType.INVALID_NUMBER;
            if (!isCellNearbyFriendlyUnits(row, column))
                return OutputMessageType.NOT_NEARBY_FRIENDLY_UNITS;
            if (battleGround.getCells()[row][column].getUnit() == null) {
                battleGround.getCells()[row][column].setUnit(unit);
                playerInTurn.getHand().getCards().remove(unit);
                playerInTurn.setNextCard();
                //todo is it complete?
                playerInTurn.reduceMana(unit.getMana());
            } else return OutputMessageType.THIS_CELL_IS_FULL;
        }
        if (card instanceof Spell) {
            Spell spell = (Spell) card;
            spell.doSpell(row, column);
            playerInTurn.getGraveYard().addDeadCard(spell);
            playerInTurn.getHand().deleteCard(spell);
            playerInTurn.reduceMana(spell.getMana());
        }
        return OutputMessageType.CARD_INSERTED;
    }

    public boolean isCellNearbyFriendlyUnits(int row, int column) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (!isCoordinationValid(i, j) || i == j)
                    continue;
                Cell cell = dataBase.getCurrentBattle().getBattleGround().getCells()[i][j];
                if (cell.isEmptyOfUnit())
                    continue;
                if (dataBase.getCurrentBattle().getBattleGround()
                        .isUnitFriendlyOrEnemy(cell.getUnit()).equals(Constants.FRIEND))
                    return true;
            }
        }
        return false;
    }

    public boolean isCoordinationValid(int row, int column) {
        if (row < 0 || row >= Constants.BATTLE_GROUND_WIDTH)
            return false;
        return column >= 0 && column < Constants.BATTLE_GROUND_LENGTH;
    }

    public OutputMessageType useSpecialPower(Unit hero, Player player, int row, int column) {
        if (hero.getMainSpecialPower().getMana() <= player.getMana()
                && hero.getMainSpecialPower().getCoolDown() == 0
                && hero.getMainSpecialPower().getActivationType() == SpellActivationType.ON_CAST) {
            hero.getMainSpecialPower().doSpell(row, column);
        } else {
            return OutputMessageType.NO_HERO;
        }
        return OutputMessageType.SPECIAL_POWER_USED;
    }

    public List<String> getAvailableMoves() {
        List<String> output = new ArrayList<>();
        for (Unit unit : battleGround.getUnitsOfPlayer(playerInTurn)) {
            String temp = "";
            temp += unit.getId() + ":" +
                    "\n\tCan Attack: " + !unit.didAttackThisTurn()
                    + "\n\tCan Move: " + !unit.didMoveThisTurn() +
                    "\n\tAttack Options: ";
            Player player;
            if (player2 == playerInTurn) {
                player = player1;
            } else {
                player = player2;
            }
            if (!unit.didAttackThisTurn()) {
                for (Unit enemyUnit : battleGround.getUnitsOfPlayer(player)) {
                    if (unit.canAttackTarget(enemyUnit)) {
                        temp += "\n\t\t" + enemyUnit.getId();
                    }
                }
            }
            output.add(temp);
        }
        return output;
    }

    public void startBattle() {
        setManaBasedOnTurnNumber();
        battleGround.getCells()[Constants.BATTLE_GROUND_WIDTH / 2][0]
                .setUnit(player1.getDeck().getHero());
        battleGround.getCells()[Constants.BATTLE_GROUND_WIDTH / 2][Constants.BATTLE_GROUND_LENGTH - 1]
                .setUnit(player2.getDeck().getHero());
        player2.setNextCard();
        player1.setNextCard();
    }

    public OutputMessageType endBattle() {
        //todo complete if
        return OutputMessageType.WRONG_COMMAND;
    }
}
