package domoop;

import java.util.ArrayList;

public class UnavTimeAss {

    ArrayList<Integer> weeks = new ArrayList<>();
    private ArrayList<Integer> days = new ArrayList<>();
    int start, length;

    UnavTimeAss(int[] week, int[] day, int start, int length) {
        this.start = start;
        this.length = length;

        int pw = week.length;
        for (int i = 0; i < pw; i++) {
            addWeeks(week[i]);
        }

        int pd = day.length;
        for (int i = 0; i < pd; i++) {
            addDays(day[i]);
        }
    }

    void addWeeks(int week) {
        weeks.add(week);
    }

    void addDays(int day) {
        getDays().add(day);
    }

    public ArrayList<Integer> getWeeks() {
        return weeks;
    }

    public void setWeeks(ArrayList<Integer> weeks) {
        this.weeks = weeks;
    }

    public ArrayList<Integer> getDays() {
        return days;
    }

    public void setDays(ArrayList<Integer> days) {
        this.days = days;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
