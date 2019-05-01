import javax.xml.crypto.Data;
import java.util.List;

public class AI {
    private static final AI ourInstance = new AI();
    private DataBase dataBase=DataBase.getInstance();
    private BattleGround battleGround=dataBase.getCurrentBattle().getBattleGround();
    public static AI getInstance() {
        return ourInstance;
    }

    private AI() {
    }

    public void doNextMove() {

    }

    public void moveUnit(Unit unit) {

    }

    public void attackWithUnit(Unit unit) {

    }

    public Unit getBestTargetForUnitAttack(Unit unit) {
        return null;//todo
    }

    public List<Card> getSelectedCardsFromHand(Hand hand) {
        return null;//todo
    }
}
