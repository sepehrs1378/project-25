abstract public class Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private String positiveOrNegative;
    private int startTurn;
    private int delayTurn;
    private int durationTurn;
    private boolean isDispellable;
    private boolean isContinuous;
    private boolean isDead;

    public Buff(int startTurn, int delayTurn,
                int durationTurn, boolean isDispellable, boolean isContinuous) {
        this.startTurn = startTurn;
        this.delayTurn = delayTurn;
        this.durationTurn = durationTurn;
        this.isDispellable = isDispellable;
        this.isContinuous = isContinuous;
    }

    public abstract void doEffect();

    public void revive() {
        int currentTurn = dataBase.getCurrentBattle().getTurnNumber();
        this.startTurn = currentTurn;
        this.isDead = false;
        //todo meybe not complete
    }

    public boolean isActive() {
        int currentTurn = dataBase.getCurrentBattle().getTurnNumber();
        return currentTurn >= startTurn + delayTurn;
    }

    public boolean isInFirstActivationTurn() {
        int currentTurn = dataBase.getCurrentBattle().getTurnNumber();
        return currentTurn == startTurn + delayTurn;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public String getPositiveOrNegative() {
        return positiveOrNegative;
    }

    public void setPositiveOrNegative(String positiveOrNegative) {
        this.positiveOrNegative = positiveOrNegative;
    }

    public int getStartTurn() {
        return startTurn;
    }

    public int getDelayTurn() {
        return delayTurn;
    }

    public boolean isContinuous() {
        return isContinuous;
    }

    public boolean isDispellable() {
        return isDispellable;
    }
}
