package ClientPackage;

abstract public class Buff {
    public transient static final DataBase dataBase = DataBase.getInstance();
    private String positiveOrNegative;
    private int startTurn;
    private int durationTurn;
    private int delayTurn;
    private boolean isDispellable;
    private boolean isContinuous;
    private boolean isDead;

    public Buff(int durationTurn, boolean isDispellable
            , boolean isContinuous, int startTurn) {
        this.durationTurn = durationTurn;
        this.isDispellable = isDispellable;
        this.isContinuous = isContinuous;
        this.startTurn = startTurn;
    }

    public abstract void doEffect(Unit unit);

    public abstract void doEndingEffect(Unit unit);

    public int getDelayTurn() {
        return delayTurn;
    }

    public void setDelayTurn(int delayTurn) {
        this.delayTurn = delayTurn;
    }

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
        return currentTurn > startTurn + durationTurn;
    }

    public abstract BuffType getType();

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

    public boolean isDead() {
        return isDead;
    }

    public int getDurationTurn() {
        return durationTurn;
    }
}
