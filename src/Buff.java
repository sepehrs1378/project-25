public abstract class Buff {
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

    public abstract void doEffect(Unit unit,Battle battle);

    public abstract void doEndingEffect(Unit unit);

    public int getDelayTurn() {
        return delayTurn;
    }

    public void setDelayTurn(int delayTurn) {
        this.delayTurn = delayTurn;
    }

    public void revive(Battle battle) {
        this.startTurn = battle.getTurnNumber();
        this.setDead(false);
        //todo maybe not completed
    }

    public boolean isActive(Battle battle) {
        int currentTurn = battle.getTurnNumber();
        return currentTurn >= startTurn;
    }

    public boolean isInFirstActivationTurn(Battle battle) {
        int currentTurn = battle.getTurnNumber();
        return currentTurn == startTurn;
    }

    public boolean isExpired(Battle battle) {
        int currentTurn = battle.getTurnNumber();
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
