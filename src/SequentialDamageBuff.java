import java.util.ArrayList;
import java.util.List;

public class SequentialDamageBuff extends Buff {
    private DataBase dataBase=DataBase.getInstance();
    private List<Integer> damagePerTurn = new ArrayList<>();

    public SequentialDamageBuff(int startTurn, int delayTurn, int durationTurn, boolean isContinuous, boolean isDispellable, int... damagePerTurn) {
        super(startTurn, delayTurn, durationTurn, isContinuous, isDispellable);
        setPositiveOrNegative(Constants.NEGATIVE);
        for(int damage:damagePerTurn){
            this.damagePerTurn.add(damage);
        }
    }

    @Override
    public void doEffect() {

    }

    public void doEffect(Unit unit) {
        int currentTurn=dataBase.getCurrentBattle().getTurnNumber();
        unit.changeHp(-damagePerTurn.get(currentTurn-(getStartTurn()+getDelayTurn())));
    }
}
