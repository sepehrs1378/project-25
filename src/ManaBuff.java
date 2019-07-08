import java.util.List;

public class ManaBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int manaAddedPerTurn;

    public ManaBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous, int startTurn, int manaAddedPerTurn) {
        super(durationTurn, isDispellable, isContinuous, startTurn);
        this.manaAddedPerTurn = manaAddedPerTurn;
    }

    @Override
    public void doEffect(Unit unit,Battle battle) {
        List<Player> players = battle.getPlayersHavingBuff(this);
        if (isActive(battle)) {
            for (Player player : players) {
                player.changeMana(manaAddedPerTurn);
            }
        }
        //todo ممکنه تاثیرش به خاطر nextTurn اعمال نشه و بره زیر مقدار دهی معمولی مانا
    }

    public void doEffect(Player player,Battle battle) {
        if (!isActive(battle))
            player.changeMana(manaAddedPerTurn);
    }

    @Override
    public void doEndingEffect(Unit unit) {
        //empty
    }

    @Override
    public ManaBuff clone() {
        return new ManaBuff(getDurationTurn(), isDispellable(), isContinuous(), getStartTurn(), manaAddedPerTurn);
    }

    @Override
    public BuffType getType() {
        return BuffType.manaBuff;
    }
}