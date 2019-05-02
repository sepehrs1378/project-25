import java.util.List;

public class ImmunityBuff extends Buff {
    private String immunite;

    public ImmunityBuff(int durationTurn, boolean isDispellable
            , boolean isContinuous, String immunite) {
        super(durationTurn, isDispellable, isContinuous);
        setPositiveOrNegative(Constants.POSITIVE);
        this.immunite = immunite;
    }

    @Override
    public void doEffect() {
        //todo looks gonna be empty
    }

    public String getImmunite() {
        return immunite;
    }
}
