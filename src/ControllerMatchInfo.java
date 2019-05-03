import java.util.ArrayList;
import java.util.List;

public class ControllerMatchInfo {
    private static final ControllerMatchInfo ourInstance = new ControllerMatchInfo();
    private final View view = View.getInstance();
    private final DataBase dataBase = DataBase.getInstance();

    private ControllerMatchInfo() {
    }

    public static ControllerMatchInfo getInstance() {
        return ourInstance;
    }

    public void showMatchHistory(Account account) {
        List<MatchInfo> matchList = new ArrayList<>(account.getMatchList());
        for (MatchInfo matchInfo : matchList) {
            Account opponent = matchInfo.getOpponent();
            String winOrLoss;
            if (matchInfo.getWinner().equals(opponent)) {
                winOrLoss = "Loss!";
            } else {
                winOrLoss = "Win!";
            }
            matchInfo.calculatePassedTime();
            view.showMatchHistory(opponent.getUsername(), winOrLoss, matchInfo.getDiffInSeconds(),
                    matchInfo.getDiffInMinutes(), matchInfo.getDiffInHours(), matchInfo.getDiffInDays());
        }
    }

}
