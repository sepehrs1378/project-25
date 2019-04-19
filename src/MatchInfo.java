import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MatchInfo extends Account {
    private Account opponent;
    private Account winner;
    private LocalDateTime matchDate;

    public void setOpponent(Account opponent) {
        this.opponent = opponent;
    }

    public void setWinner(Account winner){
        this.winner = winner;
    }

    public void setMatchDate(){
        this.matchDate = LocalDateTime.now();
    }

    public Account getOpponent(){
        return opponent;
    }

    public Account getWinner(){
        return winner;
    }

    public LocalDateTime getMatchDate(){
        return matchDate;
    }

    private void calculatePassedTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().toString(), formatter);
        matchDate = LocalDateTime.parse(matchDate.toString(), formatter);

        long diffInSeconds = java.time.Duration.between(now, matchDate).getSeconds();
        long diffInMinutes = java.time.Duration.between(now, matchDate).toMinutes();
        long diffInHours = java.time.Duration.between(now, matchDate).toHours();
        long diffInDays = java.time.Duration.between(now, matchDate).toDays();


//        String[] dateAndTimeOfMatch = matchDate.toString().split("T");
//        String[] timeOfMatch = dateAndTimeOfMatch[1].split(":");
//        String[] dateAndTimeRightNow = LocalDateTime.now().toString().split("T");
//        String[] timeRightNow = dateAndTimeRightNow[1].split(":");
//        int hourOfMatch = Integer.parseInt(timeOfMatch[0]);
//        int hourRightNow = Integer.parseInt(timeRightNow[0]);
//        int howManyHoursPassed = hourRightNow - hourOfMatch;
//        int minuteOfMatch = Integer.parseInt(timeOfMatch[1]);
//        timeOfMatch[2] = timeOfMatch[2].replaceAll("(\\d+)[.](\\d+)", "$1");
//        int secondOfMatch = Integer.parseInt(timeOfMatch[2]);


    }

}
