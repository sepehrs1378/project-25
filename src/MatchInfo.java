import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MatchInfo {
    private final ControllerMatchInfo controllerMatchInfo = ControllerMatchInfo.getInstance();
    private Account opponent;
    private Account winner;
    private LocalDateTime matchDate;
    private long diffInSeconds;
    private long diffInMinutes;
    private long diffInHours;
    private long diffInDays;

    public void setOpponent(Account opponent) {
        this.opponent = opponent;
    }

    public void setWinner(Account winner) {
        this.winner = winner;
    }

    public void setMatchDate() {
        this.matchDate = LocalDateTime.now();
    }

    public Account getOpponent() {
        return opponent;
    }

    public Account getWinner() {
        return winner;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    private void calculatePassedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().toString(), formatter);
        matchDate = LocalDateTime.parse(matchDate.toString(), formatter);

        diffInSeconds = java.time.Duration.between(now, matchDate).getSeconds();
        diffInMinutes = java.time.Duration.between(now, matchDate).toMinutes();
        diffInHours = java.time.Duration.between(now, matchDate).toHours();
        diffInDays = java.time.Duration.between(now, matchDate).toDays();
    }

    public long getDiffInSeconds() {
        return diffInSeconds;
    }

    public long getDiffInMinutes() {
        return diffInMinutes;
    }

    public long getDiffInHours() {
        return diffInHours;
    }

    public long getDiffInDays() {
        return diffInDays;
    }
}
