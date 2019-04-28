import java.util.List;

public class ManaBuff extends Buff {
    public static final DataBase dataBase = DataBase.getInstance();
    private int manaAddedPerTurn;

    @Override
    public void doEffect() {
        List<Player> players = dataBase.getCurrentBattle().getPlayersHavingBuff(this);
        for (Player player : players) {
            player.changeMana(manaAddedPerTurn);
        }
    }

    public int getManaAddedPerTurn() {
        return manaAddedPerTurn;
    }

    public void setManaAddedPerTurn(int manaAddedPerTurn) {
        this.manaAddedPerTurn = manaAddedPerTurn;
    }
}
