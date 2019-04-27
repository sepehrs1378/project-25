public class ManaBuff extends Buff {
    private int manaAddedPerTurn;

    @Override
    public void doEffect() {

    }

    public int getManaAddedPerTurn() {
        return manaAddedPerTurn;
    }

    public void setManaAddedPerTurn(int manaAddedPerTurn) {
        this.manaAddedPerTurn = manaAddedPerTurn;
    }
}
