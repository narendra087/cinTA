package domoop;

import java.util.ArrayList;
import java.util.List;

public class Constraint {

    ArrayList<List<Integer>> smStart = new ArrayList<>();
    ArrayList<List<Integer>> smTime = new ArrayList<>();
    ArrayList<List<Integer>> difTime = new ArrayList<>();
    ArrayList<List<Integer>> smDays = new ArrayList<>();
    ArrayList<List<Integer>> difDays = new ArrayList<>();
    ArrayList<List<Integer>> smWeeks = new ArrayList<>();
    ArrayList<List<Integer>> difWeeks = new ArrayList<>();
    ArrayList<List<Integer>> smRoom = new ArrayList<>();
    ArrayList<List<Integer>> difRoom = new ArrayList<>();
    ArrayList<List<Integer>> overlap = new ArrayList<>();
    ArrayList<List<Integer>> notOverlap = new ArrayList<>();
    ArrayList<List<Integer>> smAttend = new ArrayList<>();
    ArrayList<List<Integer>> precedence = new ArrayList<>();
    ArrayList<List<Integer>> workday = new ArrayList<>();
    ArrayList<List<Integer>> mingap = new ArrayList<>();
    ArrayList<List<Integer>> mxDays = new ArrayList<>();
    ArrayList<List<Integer>> mxDayLoad = new ArrayList<>();
    ArrayList<List<Integer>> mxBreaks = new ArrayList<>();
    ArrayList<List<Integer>> mxBlock = new ArrayList<>();
    ArrayList<Integer> consClass;
    private String type;
    private int angka1;
    private int angka2;
    private int penalty;

    Constraint(String type, int angka1, int angka2, int penalty, ArrayList<Integer> cons) {
        this.type = type;
        this.angka1 = angka1;
        this.angka2 = angka2;
        this.penalty = penalty;
        this.consClass = cons;
    }

    void addClassCons1(ArrayList<Integer> cons) {
        if (getType().contains("WorkDay")) {
            workday.add(cons);
        } else if (getType().contains("MinGap")) {
            mingap.add(cons);
        } else if (getType().contains("MaxDays")) {
            mxDays.add(cons);
        } else if (getType().contains("MaxDayLoad")) {
            mxDayLoad.add(cons);
        } else if (getType().contains("MaxBreaks")) {
            mxBreaks.add(cons);
        } else if (getType().contains("MaxBlock")) {
            mxBlock.add(cons);
        }
    }

    void addClassCons2(ArrayList<Integer> cons) {
        switch (getType()) {
            case "SameStart":
                smStart.add(cons);
                break;
            case "SameTime":
                smTime.add(cons);
                break;
            case "DifferentTime":
                difTime.add(cons);
                break;
            case "SameDays":
                smDays.add(cons);
                break;
            case "DifferentDays":
                difDays.add(cons);
                break;
            case "SameWeeks":
                smWeeks.add(cons);
                break;
            case "DifferentWeeks":
                difWeeks.add(cons);
                break;
            case "SameRoom":
                smRoom.add(cons);
                break;
            case "DifferentRoom":
                difRoom.add(cons);
                break;
            case "Overlap":
                overlap.add(cons);
                break;
            case "NotOverlap":
                notOverlap.add(cons);
                break;
            case "SameAttendees":
                smAttend.add(cons);
                break;
            case "Precedence":
                precedence.add(cons);
                break;
            default:
                break;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public ArrayList<Integer> getConsClass() {
        return consClass;
    }

    public void setConsClass(ArrayList<Integer> consClass) {
        this.consClass = consClass;
    }

    /**
     * @return the angka1
     */
    public int getAngka1() {
        return angka1;
    }

    /**
     * @return the angka2
     */
    public int getAngka2() {
        return angka2;
    }

}
