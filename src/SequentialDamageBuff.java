import java.util.ArrayList;
import java.util.List;

public class SequentialDamageBuff extends Buff {
    private List<Integer> damagePerTurn = new ArrayList<>();

    public SequentialDamageBuff(int durationTurn, boolean isContinuous
            , boolean isDispellable,int startTurn, int... damagePerTurn) {
        super(durationTurn, isDispellable,isContinuous,startTurn);
        setPositiveOrNegative(Constants.NEGATIVE);
        for (int damage : damagePerTurn) {
            this.damagePerTurn.add(damage);
        }
    }

    @Override
    public void doEffect(Unit unit) {
        int currentTurn = dataBase.getCurrentBattle().getTurnNumber();
        unit.changeHp(-damagePerTurn.get(currentTurn - getStartTurn()));
    }

    @Override
    public void doEndingEffect(Unit unit) {
        //empty
    }

    @Override
    public SequentialDamageBuff clone() {
        int[] damageArray = new int[10];
        for (int i = 0; i < damagePerTurn.size(); i++)
            damageArray[i] = damagePerTurn.get(i);
        return new SequentialDamageBuff(getDurationTurn(),  isDispellable(),isContinuous(),getStartTurn(), damageArray);
    }

    @Override
    public BuffType getType() {
        return BuffType.sequentialDamageBuff;
    }
}
