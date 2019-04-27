import java.util.ArrayList;
import java.util.List;

public class SequentialDamageBuff extends Buff {
    private List<Integer> damagePerTurn = new ArrayList<>();

    public SequentialDamageBuff() {
        setPositiveOrNegative(Constants.NEGATIVE);
    }

    @Override
    public void doEffect() {

    }
}
