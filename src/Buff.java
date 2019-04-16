public abstract class Buff {
    private String positiveOrNegative;
    private int startTurn;
    private int endTurn;
    private int delayTurn;
    private int durationTurn;

    public abstract void doEffect();
}
