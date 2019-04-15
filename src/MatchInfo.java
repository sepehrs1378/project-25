import java.util.Date;

class MatchInfo {
    public static final String OPPONENT = "opponent";
    public static final String PLAYER_HIM_SELF = "player him self";
    private Account opponet;
    private String winner;
    private Date matchDate;

    public Account getOpponet() {
        return opponet;
    }

    public String getWinner() {
        return winner;
    }

    public Date passedTime() {

    }

    public void setOpponet(Account opponet) {
        this.opponet = opponet;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }
}