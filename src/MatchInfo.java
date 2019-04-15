import java.time.LocalDateTime;

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

    public LocalDateTime getPassedTime(){

    }

    private void calculatePassedTime(LocalDateTime matchDate){    //todo this still has a lot of problems




        String[] dateAndTimeOfMatch = matchDate.toString().split("T");
        String[] timeOfMatch = dateAndTimeOfMatch[1].split(":");
        String[] dateAndTimeRightNow = LocalDateTime.now().toString().split("T");
        String[] timeRightNow = dateAndTimeRightNow[1].split(":");
        int hourOfMatch = Integer.parseInt(timeOfMatch[0]);
        int hourRightNow = Integer.parseInt(timeRightNow[0]);
        int howManyHoursPassed = hourRightNow - hourOfMatch;
        int minuteOfMatch = Integer.parseInt(timeOfMatch[1]);
        timeOfMatch[2] = timeOfMatch[2].replaceAll("(\\d+)[.](\\d+)", "$1");
        int secondOfMatch = Integer.parseInt(timeOfMatch[2]);


    }

}
