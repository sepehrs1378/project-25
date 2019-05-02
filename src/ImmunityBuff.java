import java.util.ArrayList;
import java.util.List;

public class ImmunityBuff extends Buff {
    private List<String> immunities = new ArrayList<>();

    public ImmunityBuff(int startTurn, int delayTurn, int durationTurn,
                        boolean isDispellable, boolean isContinuous, List<String> immunities) {
        super(startTurn, delayTurn, durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.POSITIVE);
        this.immunities = immunities;
    }

    @Override
    public void doEffect() {
        //todo looks gonna be empty
    }

    public List<String> getImmunities() {
        return immunities;
    }

    public void setImmunities(List<String> immunities) {
        this.immunities = immunities;
    }
}
