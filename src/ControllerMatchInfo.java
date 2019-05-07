import java.util.Date;
import java.util.List;

public class ControllerMatchInfo {
    private static ControllerMatchInfo ourInstance = new ControllerMatchInfo();
    private View view = View.getInstance();
    private static final DataBase dataBase = DataBase.getInstance();

    private ControllerMatchInfo() {
    }

    public static ControllerMatchInfo getInstance() {
        return ourInstance;
    }

    public void showMatchHistory(Account account) {
        List<MatchInfo> matchList = account.getMatchList();
        for (MatchInfo matchInfo : matchList) {
            String opponent = matchInfo.getOpponent();
            String winOrLoss;
            if (matchInfo.getWinner().equals(opponent)) {
                winOrLoss = "Loss!";
                String spaces = view.generateEmptySpace(winOrLoss);
                winOrLoss += spaces;
            } else {
                winOrLoss = "Win!";
                String spaces = view.generateEmptySpace(winOrLoss);
                winOrLoss += spaces;
            }
            matchInfo.calculatePassedTime();
            view.showMatchHistory(opponent, winOrLoss, matchInfo.getDiffInSeconds(),
                    matchInfo.getDiffInMinutes(), matchInfo.getDiffInHours(), matchInfo.getDiffInDays(), matchInfo.getDiffInYears());
        }
    }
}
