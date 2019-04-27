import java.util.ArrayList;
import java.util.List;

public class ImmunityBuff extends Buff {
    private List<String> immunities = new ArrayList<>();

    public ImmunityBuff() {
        setPositiveOrNegative(Constants.POSITIVE);
    }

    @Override
    public void doEffect() {

    }

    public List<String> getImmunities() {
        return immunities;
    }

    public void setImmunities(List<String> immunities) {
        this.immunities = immunities;
    }
}
