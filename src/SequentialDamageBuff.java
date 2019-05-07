import java.util.ArrayList;
import java.util.List;

public class SequentialDamageBuff extends Buff {
    private List<Integer> damagePerTurn = new ArrayList<>();

    public SequentialDamageBuff(int durationTurn, boolean isContinuous
            , boolean isDispellable, int... damagePerTurn) {
        super(durationTurn, isContinuous, isDispellable);
        setPositiveOrNegative(Constants.NEGATIVE);
        for (int damage : damagePerTurn) {
            this.damagePerTurn.add(damage);
        }
    }

    @Override
    public void doEffect() {
        //todo looks like it's gonna' be empty
    }

    @Override
    public SequentialDamageBuff clone() {
        int[] damageArray = new int[10];
        for (int i = 0; i < damagePerTurn.size(); i++)
            damageArray[i] = damagePerTurn.get(i);
        return new SequentialDamageBuff(getDurationTurn(), isContinuous(), isDispellable(), damageArray);
    }

    public void doEffect(Unit unit) {
        int currentTurn = dataBase.getCurrentBattle().getTurnNumber();
        unit.changeHp(-damagePerTurn.get(currentTurn - getStartTurn()));
    }
}
