package ClientPackage;

import java.util.Date;

public class MatchInfo {
    private String opponent;
    private String winner = "";
    private long matchDate;
    private long diffInSeconds;
    private long diffInMinutes;
    private long diffInHours;
    private long diffInDays;
    private long diffInYears;

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setMatchDate() {
        Date date = new Date();
        this.matchDate = date.getTime();
    }

    public String getOpponent() {
        return opponent;
    }

    public String getWinner() {
        return winner;
    }

    public long getMatchDate() {
        return matchDate;
    }

    public void calculatePassedTime() {
        Date date = new Date();
        long now = date.getTime();
        long diffInMilli = now - getMatchDate();
        diffInYears = diffInMilli / ((long) 365 * 86400 * 1000);
        long daysInMilli = diffInMilli % ((long) 365 * 86400 * 1000);
        diffInDays = (daysInMilli) / ((long) 86400 * 1000);
        long hoursInMilli = daysInMilli % (86400 * 1000);
        diffInHours = hoursInMilli / ((long) 3600 * 1000);
        long minutesInMilli = hoursInMilli % (3600 * 1000);
        diffInMinutes = minutesInMilli / (60 * 1000);
        long secondInMilli = minutesInMilli % (60 * 1000);
        diffInSeconds = secondInMilli / (1000);
    }

    public long getDiffInSeconds() {
        return diffInSeconds;
    }

    public long getDiffInMinutes() {
        return diffInMinutes;
    }

    public long getDiffInDays() {
        return diffInDays;
    }

    public long getDiffInYears() {
        return diffInYears;
    }

    public long getDiffInHours() {
        return diffInHours;
    }
}
