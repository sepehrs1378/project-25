import java.util.List;

public class ControllerMatchInfo {
    private static ControllerMatchInfo ourInstance;
    private View view = View.getInstance();
    private static final DataBase dataBase = DataBase.getInstance();

    public ControllerMatchInfo() {
    }

    public static ControllerMatchInfo getInstance() {
        return ourInstance;
    }

    public void showMatchHistory(Account account) {
        List<MatchInfo> matchList = account.getMatchList();
        for (int i = matchList.size() - 1; i >= 0; i--) {
            MatchInfo matchInfo = matchList.get(i);
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
