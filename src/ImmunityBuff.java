import java.util.ArrayList;
import java.util.List;

public class ImmunityBuff extends Buff {
    private List<String> immunities = new ArrayList<>();

    public ImmunityBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous, String... immunities) {
        super(durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.POSITIVE);
        this.immunities = immunities;//todo
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
