import Buff.Buff;

import java.util.ArrayList;
import java.util.List;

public class Battle {
    public static final DataBase dataBase = DataBase.getInstance();
    private Player player1;
    private Player player2;
    private BattleGround battleGround = new BattleGround();
    private Player playerInTurn;
    private String mode;
    private int turnNumber = 1;
    private boolean isBattleFinished = false;
    private int numberOfFlags;

    public Battle(Account firstPlayerAccount, Account secondPlayerAccount, String mode, int numberOfFlags) {
        player1 = new Player(firstPlayerAccount.getPlayerInfo(), firstPlayerAccount.getMainDeck());
        player2 = new Player(secondPlayerAccount.getPlayerInfo(), secondPlayerAccount.getMainDeck());
        playerInTurn = player1;
        this.mode = mode;
        this.setNumberOfFlags(numberOfFlags);
        List<Flag> temp=new ArrayList<>();
        for(int i=0;i<numberOfFlags;i++){
            temp.add(new Flag());
        }
        this.battleGround.addFlagsToBattleGround(temp);
    }

    public void nextTurn() {
        reviveContinuousBuffs();
        removeExpiredBuffs();
        resetUnitsMoveAndAttack();
        doBuffsEffects();
        //todo check turns of flag in hand??
        changeTurn();
        turnNumber++;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public BattleGround getBattleGround() {
        return battleGround;
    }

    public void setBattleGround(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void addTurnNumber() {
        this.turnNumber++;
    }

    public void changeTurn() {
        this.addTurnNumber();
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
        this.getBattleGround().getCellOfUnit(unit).setUnit(null);
        if (unit.getId().contains(player1.getPlayerInfo().getPlayerName()))
            player1.getGraveYard().addDeadCard(unit);
        else if (unit.getId().contains(player2.getPlayerInfo().getPlayerName()))
            player2.getGraveYard().addDeadCard(unit);
    }

    public void setManaBasedOnTurnNumber() {
        if (turnNumber % 2 == 1) {
            if (turnNumber < 14) {
                player1.setMana((turnNumber + 1) / 2 + 1);
            } else player1.setMana(9);
        } else if (turnNumber % 2 == 0) {
            if (turnNumber < 15) {
                player2.setMana((turnNumber / 2 + 2));
            } else player2.setMana(9);
        }
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public void setNumberOfFlags(int numberOfFlags) {
        this.numberOfFlags = numberOfFlags;
    }

    public Player checkEndBattleModeClassic() {
        if (this.getPlayer1().getDeck().getHero().getHp() <= 0)
            return player2;
        else if (this.getPlayer2().getDeck().getHero().getHp() <= 0) {
            return player1;
        }
        return null;
    }

    public Player checkEndBattleModeOneFlag() {
        int numberOfFlagsPlayer1 = this.battleGround.getNumberOfFlagsForPlayer(player1);
        int numberOfFlagsPlayer2 = this.battleGround.getNumberOfFlagsForPlayer(player2);
        if (numberOfFlagsPlayer1 >= this.getNumberOfFlags() / 2 + 1)
            return player1;
        else if (numberOfFlagsPlayer2 >= this.getNumberOfFlags() / 2 + 1)
            return player2;
        return null;
    }

    public Player checkEndBattleModeFlags() {
        Cell cell = this.getBattleGround().getCellWithFlag();
        if (cell != null) {
            if (cell.getUnit().getFlags().get(0).getTurnsInUnitHand() >= 6) {
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
        int i;
        int j;
        for (i = 0; i < Constants.BATTLE_GROUND_WIDTH; i++) {
            for (j = 0; j < Constants.BATTLE_GROUND_LENGTH; j++) {
                Cell cell = dataBase.getCurrentBattle().getBattleGround().getCells()[i][j];
                for (Buff buff : cell.getBuffs()) {
                    if (buff.isContinuous())
                        buff.revive();
                }
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
                Unit unit = dataBase.getCurrentBattle().getBattleGround()
                        .getCells()[i][j].getUnit();
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

}
