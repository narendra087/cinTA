/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bismillahta;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Umar Rizki
 */
public final class TimeAss {
    private ArrayList<Integer> weeks = new ArrayList<>();
    private ArrayList<Integer> days = new ArrayList<>();
    int start;
    int length;
    int penalty;
    
    TimeAss (int[] week, int[] day, int start, int length, int penalty){
        this.start = start;
        this.length = length;
        this.penalty = penalty;
        
        int pw = week.length;
        for (int i = 0; i < pw; i++) {
            addWeeks(week[i]);
        }
        
        int pd = day.length;
        for (int i = 0; i < pd; i++) {
            addDays(day[i]);
        }
    }
    
    void addWeeks(int week){
        weeks.add(week);
    }
    
    void addDays(int day){
        getDays().add(day);
    }
    
    /**
     * @return the weeks
     */
    public ArrayList<Integer> getWeeks() {
        return weeks;
    }

    /**
     * @param weeks the weeks to set
     */
    public void setWeeks(ArrayList<Integer> weeks) {
        this.weeks = weeks;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the penalty
     */
    public int getPenalty() {
        return penalty;
    }

    /**
     * @param penalty the penalty to set
     */
    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    /**
     * @return the days
     */
    public ArrayList<Integer> getDays() {
        return days;
    }

    /**
     * @param days the days to set
     */
    public void setDays(ArrayList<Integer> days) {
        this.days = days;
    }
    
}
