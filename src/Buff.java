public abstract class Buff {
    private String positiveOrNegative;
    private int startTurn;
    private int delayTurn;
    private int durationTurn;
    private boolean isDispellable;
    private boolean isContinuous;
    private boolean isActive;

    public abstract void doEffect();

    public int getStartTurn() {
        return startTurn;
    }

    public void setStartTurn(int startTurn) {
        this.startTurn = startTurn;
    }

    public int getDelayTurn() {
        return delayTurn;
    }

    public void setDelayTurn(int delayTurn) {
        this.delayTurn = delayTurn;
    }

    public int getDurationTurn() {
        return durationTurn;
    }

    public void setDurationTurn(int durationTurn) {
        this.durationTurn = durationTurn;
    }

    public boolean isDispellable() {
        return isDispellable;
    }

    public void setDispellable(boolean dispellable) {
        isDispellable = dispellable;
    }

    public boolean isContinuous() {
        return isContinuous;
    }

    public void setContinuous(boolean continuous) {
        isContinuous = continuous;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPositiveOrNegative() {
        return positiveOrNegative;
    }

    public void setPositiveOrNegative(String positiveOrNegative) {
        this.positiveOrNegative = positiveOrNegative;
    }
}
