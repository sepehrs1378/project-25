import java.util.List;

abstract public class Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private String positiveOrNegative;
    private int startTurn;
    private int durationTurn;
    private boolean isDispellable;
    private boolean isContinuous;
    private boolean isDead;

    public Buff(int durationTurn, boolean isDispellable
            , boolean isContinuous) {
        this.durationTurn = durationTurn;
        this.isDispellable = isDispellable;
        this.isContinuous = isContinuous;
    }

    public abstract void doEffect();

    public abstract void doEndingEffect();

    public void revive() {
        this.startTurn = dataBase.getCurrentBattle().getTurnNumber();
        this.setDead(false);
        //todo maybe not completed
    }

    public boolean isActive() {
        int currentTurn = dataBase.getCurrentBattle().getTurnNumber();
        return currentTurn >= startTurn;
    }

    public boolean isInFirstActivationTurn() {
        int currentTurn = dataBase.getCurrentBattle().getTurnNumber();
        return currentTurn == startTurn;
    }

    public boolean isExpired() {
        int currentTurn = dataBase.getCurrentBattle().getTurnNumber();
        return currentTurn <= startTurn + durationTurn;//todo condition may be wrong
    }

    public void remove() {
        List<Cell> cells = dataBase.getCurrentBattle().getBattleGround().getCellsHavingBuff(this);
        List<Unit> units = dataBase.getCurrentBattle().getBattleGround().getUnitsHavingBuff(this);
        List<Player> players = dataBase.getCurrentBattle().getPlayersHavingBuff(this);
        for (Cell cell : cells)
            cell.getBuffs().remove(this);
        for (Unit unit : units)
            unit.getBuffs().remove(this);
        for (Player player : players)
            player.getBuffs().remove(this);
    }

    public abstract Buff clone();

    public String getPositiveOrNegative() {
        return positiveOrNegative;
    }

    public void setPositiveOrNegative(String positiveOrNegative) {
        this.positiveOrNegative = positiveOrNegative;
    }

    public int getStartTurn() {
        return startTurn;
    }

    public void setStartTurn(int startTurn) {
        this.startTurn = startTurn;
    }

    public boolean isContinuous() {
        return isContinuous;
    }

    public boolean isDispellable() {
        return isDispellable;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getDurationTurn() {
        return durationTurn;
    }
}
