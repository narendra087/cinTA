package domoop;

import java.util.ArrayList;

public class MethodConstraint {

    boolean hardConstraint() {
//        if(SameStart(start, jumlahClass) &&
//           SameTime(start, length, end, jumlahClass) &&
//           DifferentTime(start, end, jumlahClass) &&
//           SameDays(days, jumlahClass) &&
//           DifferentDays(days, jumlahClass) &&
//           SameWeeks(weeks, jumlahClass) &&
//           DifferentWeeks(weeks, jumlahClass) &&
//           SameRoom(room, jumlahClass) &&
//           DifferentRoom(room, jumlahClass) &&
//           Overlap(start, end, days, weeks, jumlahClass) &&
//           NotOverlap(start, end, days, weeks, jumlahClass)){
//            return true;
//        }
        return false;
    }

    //1.SAME START  Ci.start = Cj.start
    boolean SameStart(int[] start, ArrayList<Integer> listSS) {
        for (int i = 0; i < listSS.size(); i++) {
            for (int j = 0; j < listSS.size(); j++) {
                if (start[i] == start[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    //2.SAME TIME   (Ci.start ≤ Cj.start ∧ Cj.end ≤ Ci.end) ∨ (Cj.start ≤ Ci.start ∧ Ci.end ≤ Cj.end)
    boolean SameTime(int[] length, int[] start, int[] end, ArrayList<Integer> listST) {
        for (int i = 0; i < listST.size(); i++) {
            for (int j = 0; j < listST.size(); j++) {
                if ((start[i] <= start[j] && end[j] <= end[i])
                        || (start[j] <= start[i] && end[i] <= end[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    //3.DIFFERENT TIME  (Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start)
    boolean DifferentTime(int[] start, int[] end, ArrayList<Integer> listDT) {
        for (int i = 0; i < listDT.size(); i++) {
            for (int j = 0; j < listDT.size(); j++) {
                if (end[i] <= start[j]
                        || end[j] <= start[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    //4.SAME DAYS ((Ci.days or Cj.days) = Ci.days) ∨ ((Ci.days or Cj.days) = Cj.days)
    boolean SameDays(int[] days, ArrayList<Integer> listSD) {
        for (int i = 0; i < listSD.size(); i++) {
            for (int j = 0; j < listSD.size(); j++) {
                if (days[i] == days[i]
                        || days[j] == days[i]
                        || days[i] == days[j]
                        || days[j] == days[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    //5.DIFFERENT DAYS  (Ci.days and Cj.days) = 0
    boolean DifferentDays(int[] days, ArrayList<Integer> listDD) {
        for (int i = 0; i < listDD.size(); i++) {
            for (int j = 0; j < listDD.size(); j++) {
                if (days[i] == 0 && days[j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    //6.SAME WEEKS  (Ci.weeks or Cj.weeks) = Ci.weeks) ∨ (Ci.weeks or Cj.weeks) = Cj.weeks)
    boolean SameWeeks(int[] weeks, ArrayList<Integer> listSW) {
        for (int i = 0; i < listSW.size(); i++) {
            for (int j = 0; j < listSW.size(); j++) {
                if (weeks[i] == weeks[i]
                        || weeks[j] == weeks[i]
                        || weeks[i] == weeks[j]
                        || weeks[j] == weeks[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    //7.DIFFERENT WEEKS (Ci.weeks and Cj.weeks) = 0
    boolean DifferentWeeks(int[] weeks, ArrayList<Integer> listDW) {
        for (int i = 0; i < listDW.size(); i++) {
            for (int j = 0; j < listDW.size(); j++) {
                if (weeks[i] == 0 && weeks[j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    //8.SAME ROOM   Ci.room = Cj.room
    boolean SameRoom(int[] room, ArrayList<Integer> listSR) {
        for (int i = 0; i < listSR.size(); i++) {
            for (int j = 0; j < listSR.size(); j++) {
                if (room[i] == room[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    //9.DIFFERENT ROOM  Ci.room ≠ Cj.room
    boolean DifferentRoom(int[] room, ArrayList<Integer> listDR) {
        for (int i = 0; i < listDR.size(); i++) {
            for (int j = 0; j < listDR.size(); j++) {
                if (room[i] != room[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    //10.OVERLAP    (Cj.start < Ci.end) ∧ (Ci.start < Cj.end) ∧ ((Ci.days and Cj.days) ≠ 0) ∧ ((Ci.weeks and Cj.weeks) ≠ 0)
    boolean Overlap(int[] start, int[] end, int[] days, int[] weeks, ArrayList<Integer> listOv) {
        for (int i = 0; i < listOv.size(); i++) {
            for (int j = 0; j < listOv.size(); j++) {
                if (start[j] < end[i]
                        && start[i] < end[j]
                        && ((days[i] != 0)
                        && days[j] != 0)
                        && ((weeks[i] != 0)
                        && weeks[j] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    //11.NOT OVERLAP    (Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0)
    boolean NotOverlap(int[] start, int[] end, int[] days, int[] weeks, ArrayList<Integer> listNotOv) {
        for (int i = 0; i < listNotOv.size(); i++) {
            for (int j = 0; j < listNotOv.size(); j++) {
                if (end[i] <= start[j]
                        || end[j] <= start[i]
                        || ((days[i] == 0) && days[j] == 0)
                        || ((weeks[i] == 0) && weeks[j] == 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    //12.SAME ATTENDEES (Ci.end + Ci.room.travel[Cj.room] ≤ Cj.start) ∨ (Cj.end + Cj.room.travel[Ci.room] ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0)
    //Ci.room.travel[Cj.room] is the travel time between the assigned rooms of Ci and Cj
    boolean SameAttendees(int[] start, int[] end, int[] days, int[] weeks, int[][] valueTravel, ArrayList<Integer> listSA) {
        for (int i = 0; i < listSA.size(); i++) {
            for (int j = 0; j < listSA.size(); j++) {
                if (end[i] + valueTravel[i][j] <= start[j]
                        || end[j] + valueTravel[j][i] <= start[i]
                        || (days[i] == 0 && days[j] == 0)
                        || (weeks[i] == 0 && weeks[j] == 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    //13.PRECEDENCE (first(Ci.weeks) < first(Cj.weeks)) ∨ [(first(Ci.weeks) = first(Cj.weeks)) ∧
    //[(first(Ci .days) < first(Cj .days)) ∨ ((first(Ci.days) = first(Cj.days)) ∧ (Ci.end ≤ Cj.start))]]
    //for any two classes Ci and Cj from the constraint where i < j and first(x) is the index of the first non-zero bit in the binary string x.
    boolean Precedence(int[] start, int[] end, int[] days, int[] weeks, ArrayList<Integer> listP) {
        for (int i = 0; i < listP.size(); i++) {
            for (int j = 0; j < listP.size(); j++) {
                if (weeks[listP.get(i)] < weeks[listP.get(j)]
                        || weeks[listP.get(i)] == weeks[listP.get(j)]
                        && days[listP.get(i)] < days[listP.get(j)]
                        || days[listP.get(i)] == days[listP.get(j)]
                        && end[i] <= start[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    //14.WORKDAY(S) ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (max(Ci.end,Cj.end)−min(Ci.start,Cj.start) ≤ S)
    boolean Workday(int S, int[] start, int[] end, int[] days, int[] weeks, ArrayList<Integer> listWD) {
        for (int i = 0; i < listWD.size(); i++) {
            for (int j = 0; j < listWD.size(); j++) {
                if ((days[i] == 0 && days[j] == 0)
                        || (weeks[i] == 0 && weeks[j] == 0)
                        || (Math.max(end[i], end[j])) - (Math.min(start[i], start[j])) <= S) {
                    return true;
                }
            }
        }
        return false;
    }

    //15.MINGAP(G)  ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (Ci.end + G ≤ Cj.start) ∨ (Cj.end + G ≤ Ci.start)
    boolean Mingap(int G, int[] start, int[] end, int[] days, int[] weeks, ArrayList<Integer> listMG) {
        for (int i = 0; i < listMG.size(); i++) {
            for (int j = 0; j < listMG.size(); j++) {
                if ((days[i] == 0 && days[j] == 0)
                        || (weeks[i] == 0 && weeks[j] == 0)
                        || end[i] + G <= start[j]
                        || end[j] + G <= start[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    //16.MAXDAYS(D) countNonzeroBits(C1.days or C2.days or ⋅ ⋅ ⋅ Cn.days) ≤ D
    boolean Maxdays(int D, int[] days, ArrayList<Integer> listMD) {
        for (int i = 0; i < listMD.size(); i++) {
            for (int j = 0; j < listMD.size(); j++) {
                if (countNonzeroBits(days[i]) <= D) {
                    return true;
                }
            }
        }
        return false;
    }

    //17.MAXDAY LOAD(S)
    //DayLoad(d,w) ≤ S
    //DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
    //2d is a bit string with the only non-zero bit on position d
    //2w is a bit string with the only non-zero bit on position w
    int d[] = {0, 1, 2, 3, 4, 5, 6};                          //HARUSNYA GET JUMLAH DAYS PER DATASET
    int w[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};  //HARUSNYA GET JUMLAH WEEKS PER DATASET
    int sum = 0;

    boolean MaxdayLoad(int S, int d, int w, ArrayList<Integer> listMDL) {
        for (int i = 0; i < listMDL.size(); i++) {
            sum += i;
            if (DayLoad(d, w) <= S) {
                return true;
            }
        }
        return false;
    }

    //18.MAXBREAKS(R,S)
    boolean Maxbreaks() {
        return true;
    }

    //19.MAXBLOCK(M,S)
    boolean Maxblock() {
        return true;
    }

    private int countNonzeroBits(int n) {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            count += 1;
        }
        return count;
    }

    //DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
    private int DayLoad(int d, int w) {
        ArrayList<Integer> listMDL = null;
        int[] days = null, length = null, weeks = null;
        for (int i = 0; i < listMDL.size(); i++) {
            //sum += i;
            if ((days[i] != 0 && Math.pow(2, d) != 0) && (weeks[i] != 0 && Math.pow(2, w) != 0)) {
                sum += i;
            }
        }
        return 0;
    }
}
