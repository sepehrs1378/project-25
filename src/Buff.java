public abstract class Buff {
    private String positiveOrNegative;
    private int startTurn;
    private int endTurn;
    private int delayTurn;
    private int durationTurn;
    private boolean isDispellable;
    private boolean isContinuous;
    private boolean isActive;

    public abstract void doEffect();
}
