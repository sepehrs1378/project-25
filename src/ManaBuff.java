import java.util.List;

public class ManaBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int manaAddedPerTurn;

    public ManaBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous, int manaAddedPerTurn) {
        super(durationTurn, isDispellable, isContinuous);
        this.manaAddedPerTurn = manaAddedPerTurn;
    }

    @Override
    public void doEffect(Unit unit) {
        List<Player> players = dataBase.getCurrentBattle().getPlayersHavingBuff(this);
        if (!isActive()) {
            for (Player player : players) {
                player.changeMana(manaAddedPerTurn);
            }
        }
        //todo ممکنه تاثیرش به خاطر nextTurn اعمال نشه و بره زیر مقدار دهی معمولی مانا
    }

    public void doEffect(Player player) {
        if (!isActive())
            player.changeMana(manaAddedPerTurn);
    }

    @Override
    public void doEndingEffect(Unit unit) {
        //empty
    }

    @Override
    public ManaBuff clone() {
        return new ManaBuff(getDurationTurn(), isDispellable(), isContinuous(), manaAddedPerTurn);
    }
}
