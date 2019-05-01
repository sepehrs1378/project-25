import java.util.List;

public class ManaBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int manaAddedPerTurn;

    public ManaBuff(int startTurn, int delayTurn, int durationTurn,
                    boolean isDispellable, boolean isContinuous, int manaAddedPerTurn) {
        super(startTurn, delayTurn, durationTurn, isDispellable, isContinuous);
        this.manaAddedPerTurn = manaAddedPerTurn;
    }

    @Override
    public void doEffect() {
        List<Player> players = dataBase.getCurrentBattle().getPlayersHavingBuff(this);
        if (isActive()) {
            for (Player player : players) {
                player.changeMana(manaAddedPerTurn);
            }
        }
        //todo ممکنه تاثیرش به خاطر nextTurn اعمال نشه و بره زیر مقدار دهی معمولی مانا
    }

    public int getManaAddedPerTurn() {
        return manaAddedPerTurn;
    }

    public void setManaAddedPerTurn(int manaAddedPerTurn) {
        this.manaAddedPerTurn = manaAddedPerTurn;
    }
}
