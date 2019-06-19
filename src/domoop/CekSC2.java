package domoop;

import java.util.ArrayList;

public class CekSC2 {

    //Return jumlah semua penalti
    int totalPenalty(int w, int d, int S, int G, int D, int R, int M,
            String[] weeks, String[] days, int[] start, int[] length,
            int[] room, int[][] valueTravel, int penalty,
            ArrayList<Integer> listSS, ArrayList<Integer> listST,
            ArrayList<Integer> listDT, ArrayList<Integer> listSD,
            ArrayList<Integer> listDD, ArrayList<Integer> listSW,
            ArrayList<Integer> listDW, ArrayList<Integer> listSR,
            ArrayList<Integer> listDR, ArrayList<Integer> listOv,
            ArrayList<Integer> listNotOv, ArrayList<Integer> listSA,
            ArrayList<Integer> listP, ArrayList<Integer> listWD,
            ArrayList<Integer> listMG, ArrayList<Integer> listMD,
            ArrayList<Integer> listMDL, ArrayList<Integer> listMB,
            ArrayList<Integer> listMBL) {
        return (SameStart(start, listSS, penalty) + SameTime(start, length, listST,
                penalty) + DifferentTime(start, length, listDT, penalty) + SameDays(days, d, listSD, penalty) + DifferentDays(days, d, listDD, penalty)
                + SameWeeks(weeks, w, listSW, penalty) + DifferentWeeks(weeks, w, listDW,
                penalty) + SameRoom(room, listSR, penalty) + DifferentRoom(room, listDR,
                penalty) + Overlap(start, length, days, weeks, d, w, listOv, penalty)
                + NotOverlap(start, length, days, weeks, d, w, listNotOv, penalty)
                + SameAttendees(start, length, days, weeks, d, w, valueTravel, listSA,
                        penalty) + Precedence(start, length, days, weeks, w, d, listP, penalty)
                + Workday(S, start, length, days, weeks, w, d, listWD, penalty) + Mingap(G,
                start, length, days, weeks, w, d, listMG, penalty) + Maxdays(D, days,
                        listMD, penalty) + MaxdayLoad(S, d, w, days, weeks, length, listMDL,
                        penalty) + Maxbreaks(R, S, d, w, start, length, days, weeks, listMB,
                        penalty) + Maxblock(M, S, d, w, start, length, days, weeks, listMBL,
                        penalty));
    }

    //1.SAME START                                                              //Ci.start = Cj.start
    int SameStart(int[] start, ArrayList<Integer> listSS, int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listSS.size(); i++) {
            for (int j = i + 1; j < listSS.size(); j++) {
                int a = listSS.get(i);
                int b = listSS.get(j);
                if (start[a] == start[b]) {
                    benar++;
                } else {
                    jPenalty += penalty;
                }
            }
        }
        return jPenalty;
    }

    //2.SAME TIME                                                               //(Ci.start ≤ Cj.start ∧ Cj.end ≤ Ci.end) ∨ (Cj.start ≤ Ci.start ∧ Ci.end ≤ Cj.end)
    int SameTime(int[] length, int[] start, ArrayList<Integer> listST,
            int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listST.size(); i++) {
            for (int j = i + 1; j < listST.size(); j++) {
                int a = listST.get(i);
                int b = listST.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                if ((start[a] <= start[b] && endB <= endA)
                        || (start[b] <= start[a] && endA <= endB)) {
                    benar++;
                } else {
                    jPenalty += penalty;
                }
            }
        }
        return jPenalty;
    }

    //3.DIFFERENT TIME                                                          //(Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start)
    int DifferentTime(int[] start, int[] length, ArrayList<Integer> listDT,
            int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listDT.size(); i++) {
            for (int j = i + 1; j < listDT.size(); j++) {
                int a = listDT.get(i);
                int b = listDT.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                if (endA <= start[b] || endB <= start[a]) {
                    benar++;
                } else {
                    jPenalty += penalty;
                }
            }
        }
        return jPenalty;
    }

    //4.SAME DAYS                                                               //((Ci.days or Cj.days) = Ci.days) ∨ ((Ci.days or Cj.days) = Cj.days)
    int SameDays(String[] days, int d, ArrayList<Integer> listSD, int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listSD.size(); i++) {
            for (int j = i + 1; j < listSD.size(); j++) {
                //Ambil classID eg. class ID="100"
                int a = listSD.get(i);
                int b = listSD.get(j);
                //Ambil value days dari classID 100 : "0110001" lalu Split value days dan simpan hasil split ke Array
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                for (int k = 0; k < d; k++) {
                    if ("1".equals(dayA[k]) && "1".equals(dayB[k])) {
                        benar++;
                        break;
                    } else {
                        jPenalty += penalty;
                    }
                }
            }
        }
        return jPenalty;
    }

    //5.DIFFERENT DAYS                                                          //(Ci.days and Cj.days) = 0
    int DifferentDays(String[] days, int d, ArrayList<Integer> listDD,
            int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listDD.size(); i++) {
            for (int j = i + 1; j < listDD.size(); j++) {
                int a = listDD.get(i);
                int b = listDD.get(j);
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                for (int k = 0; k < d; k++) {
                    if (("0".equals(dayA[k]) && "0".equals(dayB[k]))
                            || ("1".equals(dayA[k]) && "0".equals(dayB[k]))
                            || ("0".equals(dayA[k]) && "1".equals(dayB[k]))) {
                        benar++;
                    } else {
                        jPenalty += penalty;
                    }
                }
            }
        }
        return jPenalty;
    }

    //6.SAME WEEKS                                                              //(Ci.weeks or Cj.weeks) = Ci.weeks) ∨ (Ci.weeks or Cj.weeks) = Cj.weeks)
    int SameWeeks(String[] weeks, int w, ArrayList<Integer> listSW, int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listSW.size(); i++) {
            for (int j = i + 1; j < listSW.size(); j++) {
                int a = listSW.get(i);
                int b = listSW.get(j);
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    if ("1".equals(weekA[k]) && "1".equals(weekB[k])) {
                        benar++;
                        break;
                    } else {
                        jPenalty += penalty;
                    }
                }
            }
        }
        return jPenalty;
    }

    //7.DIFFERENT WEEKS                                                         //(Ci.weeks and Cj.weeks) = 0
    int DifferentWeeks(String[] weeks, int w, ArrayList<Integer> listDW,
            int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listDW.size(); i++) {
            for (int j = i + 1; j < listDW.size(); j++) {
                int a = listDW.get(i);
                int b = listDW.get(j);
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    if (("0".equals(weekA[k]) && "0".equals(weekB[k]))
                            || ("1".equals(weekA[k]) && "0".equals(weekB[k]))
                            || ("0".equals(weekA[k]) && "1".equals(weekB[k]))) {
                        benar++;
                    } else {
                        jPenalty += penalty;
                    }
                }
            }
        }
        return jPenalty;
    }

    //8.SAME ROOM                                                               //Ci.room = Cj.room
    int SameRoom(int[] room, ArrayList<Integer> listSR, int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listSR.size(); i++) {
            for (int j = i + 1; j < listSR.size(); j++) {
                int a = listSR.get(i);
                int b = listSR.get(j);
                if (room[a] == room[b]) {
                    benar++;
                } else {
                    jPenalty += penalty;
                }
            }
        }
        return jPenalty;
    }

    //9.DIFFERENT ROOM                                                          //Ci.room ≠ Cj.room
    int DifferentRoom(int[] room, ArrayList<Integer> listDR, int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listDR.size(); i++) {
            for (int j = 0; j < listDR.size(); j++) {
                int a = listDR.get(i);
                int b = listDR.get(j);
                if (room[a] != room[b]) {
                    benar++;
                } else {
                    jPenalty += penalty;
                }
            }
        }
        return jPenalty;
    }

    //10.OVERLAP                                                                //(Cj.start < Ci.end) ∧ (Ci.start < Cj.end) ∧ ((Ci.days and Cj.days) ≠ 0) ∧ ((Ci.weeks and Cj.weeks) ≠ 0)
    int Overlap(int[] start, int[] length, String[] days, String[] weeks, int d,
            int w, ArrayList<Integer> listOv, int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listOv.size(); i++) {
            for (int j = i + 1; j < listOv.size(); j++) {
                int a = listOv.get(i);
                int b = listOv.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    for (int l = 0; l < d; l++) {
                        if (("1".equals(weekA[k]) && "1".equals(weekB[k]))
                                && ("1".equals(dayA[l]) && "1".equals(dayB[l]))
                                && (start[b] < endA) && (start[a] < endB)) {
                            benar++;
                        } else {
                            jPenalty += penalty;
                        }
                    }
                }
            }
        }
        return jPenalty;
    }

    //11.NOT OVERLAP                                                            //(Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0)
    int NotOverlap(int[] start, int[] length, String[] days, String[] weeks,
            int d, int w, ArrayList<Integer> listNotOv, int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listNotOv.size(); i++) {
            for (int j = 0; j < listNotOv.size(); j++) {
                int a = listNotOv.get(i);
                int b = listNotOv.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    for (int l = 0; l < d; l++) {
                        if ((endA <= start[b]) || (endB <= start[a])
                                || ("0".equals(weekA[k]) && "0".equals(weekB[k]))
                                || ("0".equals(dayA[l]) && "0".equals(dayB[l]))) {
                            benar++;
                        } else {
                            jPenalty += penalty;
                        }
                    }
                }
            }
        }
        return jPenalty;
    }

    //12.SAME ATTENDEES                                                         //(Ci.end + Ci.room.travel[Cj.room] ≤ Cj.start) ∨ (Cj.end + Cj.room.travel[Ci.room] ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) Ci.room.travel[Cj.room] is the travel time between the assigned rooms of Ci and Cj
    int SameAttendees(int[] start, int[] length, String[] days, String[] weeks,
            int d, int w, int[][] valueTravel, ArrayList<Integer> listSA,
            int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listSA.size(); i++) {
            for (int j = 0; j < listSA.size(); j++) {
                int a = listSA.get(i);
                int b = listSA.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    for (int l = 0; l < d; l++) {
                        if (((endA + valueTravel[a][b]) <= start[b])
                                || ((endB + valueTravel[b][a]) <= start[a])
                                || ("0".equals(weekA[k]) && "0".equals(weekB[k]))
                                || ("0".equals(dayA[l]) && "0".equals(dayB[l]))) {
                            benar++;
                        } else {
                            jPenalty += penalty;
                        }
                    }
                }
            }
        }
        return jPenalty;
    }

    //13.PRECEDENCE                                                             //(first(Ci.weeks) < first(Cj.weeks)) ∨ [(first(Ci.weeks) = first(Cj.weeks)) ∧ [(first(Ci .days) < first(Cj .days)) ∨ ((first(Ci.days) = first(Cj.days)) ∧ (Ci.end ≤ Cj.start))]] for any two classes Ci and Cj from the constraint where i < j and first(x) is the index of the first non-zero bit in the binary string x.
    int Precedence(int[] start, int[] length, String[] days, String[] weeks,
            int w, int d, ArrayList<Integer> listP, int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listP.size(); i++) {
            for (int j = 0; j < listP.size(); j++) {
                int a = listP.get(i);
                int b = listP.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    for (int l = 0; l < d; l++) {
                        if ((Integer.parseInt(weekA[k]) < Integer.parseInt(weekB[k]))
                                || (Integer.parseInt(weekA[k]) == Integer.parseInt(weekB[k]))
                                && (Integer.parseInt(dayA[l]) < Integer.parseInt(dayB[l]))
                                || (Integer.parseInt(dayA[l]) == Integer.parseInt(dayB[l]))
                                && (endA <= start[b])) {
                            benar++;
                        } else {
                            jPenalty += penalty;
                        }
                    }
                }
            }
        }
        return jPenalty;
    }

    //14.WORKDAY(S)                                                             //((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (max(Ci.end,Cj.end)−min(Ci.start,Cj.start) ≤ S)
    int Workday(int S, int[] start, int[] length, String[] days, String[] weeks,
            int w, int d, ArrayList<Integer> listWD, int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listWD.size(); i++) {
            for (int j = 0; j < listWD.size(); j++) {
                int a = listWD.get(i);
                int b = listWD.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    for (int l = 0; l < d; l++) {
                        if (("0".equals(weekA[k]) && "0".equals(weekB[k]))
                                || ("0".equals(dayA[l]) && "0".equals(dayB[l]))
                                || (((Math.max(endA, endB)) - (Math.min(start[a],
                                start[b]))) <= S)) {
                            benar++;
                        } else {
                            jPenalty += penalty;
                        }
                    }
                }
            }
        }
        return jPenalty;
    }

    //15.MINGAP(G)                                                              //((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (Ci.end + G ≤ Cj.start) ∨ (Cj.end + G ≤ Ci.start)
    int Mingap(int G, int[] start, int[] length, String[] days, String[] weeks,
            int w, int d, ArrayList<Integer> listMG, int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listMG.size(); i++) {
            for (int j = 0; j < listMG.size(); j++) {
                int a = listMG.get(i);
                int b = listMG.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    for (int l = 0; l < d; l++) {
                        if (("0".equals(weekA[k]) && "0".equals(weekB[k]))
                                || ("0".equals(dayA[l]) && "0".equals(dayB[l]))
                                || ((endA + G) <= start[b]) || ((endB + G) <= start[a])) {
                            benar++;
                        } else {
                            jPenalty += penalty;
                        }
                    }
                }
            }
        }
        return jPenalty;
    }

    //16.MAXDAYS(D)                                                             //countNonzeroBits(C1.days or C2.days or ⋅ ⋅ ⋅ Cn.days) ≤ D
    int Maxdays(int D, String[] days, ArrayList<Integer> listMD, int penalty) {
        int jPenalty = 0;
        int benar = 0;
        int lebih = 0;
        for (int i = 0; i < listMD.size(); i++) {
            if (countNonzeroBits(days[i]) <= D) {
                benar++;
            } else {
                lebih = (countNonzeroBits(days[i]) - D);
                jPenalty += (lebih * penalty);
            }
        }
        return jPenalty;
    }

    //17.MAXDAYLOAD(S)                                                         //DayLoad(d,w) ≤ S >>> DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
    int MaxdayLoad(int S, int d, int w, String[] days, String[] weeks,
            int[] length, ArrayList<Integer> listMDL, int penalty) {
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listMDL.size(); i++) {
            int a = listMDL.get(i);
            int l = length[a];
            String day = days[a];
            String week = weeks[a];
            if (DayLoad(S, d, w, day, week, l) == 0) {
                benar = 1;
            } else {
                jPenalty = (penalty * (DayLoad(S, d, w, day, week, l)) / w);
            }
        }
        return jPenalty;                                                        //PENALTY : (penalty × ∑w,d max(DayLoad(d,w) − S, 0)) / nrWeeks
    }

    //18.MAXBREAKS(R,S)                                                         //|MergeBlocks{(C.start, C.end) | (C.days and 2d) ≠ 0 ∧ (C.weeks and 2w) ≠ 0})| ≤ R + 1 >> (Ba.end + S ≥ Bb.start) ∧ (Bb.end + S ≥ Ba.start) ⇒ (B.start = min(Ba.start, Bb.start)) ∧ (B.end = max(Ba.end, Bb.end))
    int Maxbreaks(int R, int S, int d, int w, int[] start, int[] length,
            String[] days, String[] weeks, ArrayList<Integer> listMB,
            int penalty) {
        int jPenalty = 0;
        int benar = 0;
        if (MergeBlocks(R, S, d, w, start, length, days, weeks, listMB) <= (R + 1)) {
            benar = 1;
        } else {
            jPenalty = (penalty * (MergeBlocks(R, S, d, w, start, length, days, weeks,
                    listMB) - R)) / w;
        }
        return jPenalty;
    }

    //19.MAXBLOCK(M,S)                                                          //max {B.end − B.start | B ∈ MergeBlocks{(C.start, C.end) | (C.days and 2d) ≠ 0 ∧ (C.weeks and 2w) ≠ 0}} ≤ M
    int Maxblock(int M, int S, int d, int w, int[] start, int[] length,
            String[] days, String[] weeks, ArrayList<Integer> listMBL,
            int penalty) {
        int jPenalty = 0;
        jPenalty = (penalty * MergeBlockB(M, S, d, w, start, length, days, weeks, listMBL)) / w;
        return jPenalty;
    }

    private int countNonzeroBits(String days) {
        int count = 0;
        String[] day = days.split("");
        for (int i = 0; i < 6; i++) {
            if ("1".equals(day[i])) {
                count++;
            }
        }
        return count;
    }

    private int DayLoad(int S, int d, int w, String days, String weeks,
            int length) {                                                       //DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
        int lebih = 0;
        String[] day = days.split("");
        String[] week = weeks.split("");
        for (int wk = 0; wk < w; wk++) {
            for (int dy = 0; dy < d; dy++) {
                if ("1".equals(week[wk]) && "1".equals(day[dy])) {
                    if (length > S) {
                        lebih += (length - S);
                    }
                }
            }
        }
        return lebih;
    }

    private int MergeBlocks(int R, int S, int d, int w, int[] start, int[] length,
            String[] days, String[] weeks, ArrayList<Integer> listMB) {
        int initialBlock = 1;

        int a = listMB.get(0);
        String dayA = days[a];
        String weekA = weeks[a];
        int startA = start[a];
        int lenA = length[a];
        int endA = startA + lenA;

        for (int i = 1; i < listMB.size(); i++) {
            int b = listMB.get(i);
            String dayB = days[b];
            String weekB = weeks[b];
            int startB = start[b];
            int lenB = length[b];
            int endB = startB + lenB;

            String[] day1 = dayA.split("");
            String[] day2 = dayB.split("");
            String[] week1 = weekA.split("");
            String[] week2 = weekB.split("");

            for (int k = 0; k < w; k++) {
                for (int j = 0; j < d; j++) {
                    if ("1".equals(week1[k]) && "1".equals(day1[j])
                            && "1".equals(week2[k]) && "1".equals(day2[j])) {
                        if (endA + S >= startB && endB + S >= startA) {
                            startA = Math.min(startA, startB);
                            endA = Math.max(endA, endB);
                        } else {
                            initialBlock++;
                            startA = startB;
                            endA = endB;
                            dayA = dayB;
                            weekA = weekB;
                        }
                    }
                }
            }
        }
        return initialBlock;
    }

    private int MergeBlockB(int M, int S, int d, int w, int[] start, int[] length,
            String[] days, String[] weeks, ArrayList<Integer> listMBL) {
        int lengthBlock = 0;
        int lebih = 0;
        boolean blockBaru = true;

        int a = listMBL.get(0);
        String dayA = days[a];
        String weekA = weeks[a];
        int startA = start[a];
        int lenA = length[a];
        int endA = startA + lenA;

        for (int i = 1; i < listMBL.size(); i++) {
            int b = listMBL.get(i);
            String dayB = days[b];
            String weekB = weeks[b];
            int startB = start[b];
            int lenB = length[b];
            int endB = startB + lenB;

            String[] day1 = dayA.split("");
            String[] day2 = dayB.split("");
            String[] week1 = weekA.split("");
            String[] week2 = weekB.split("");

            for (int k = 0; k < w; k++) {
                for (int j = 0; j < d; j++) {
                    if ("1".equals(week1[k]) && "1".equals(day1[j])
                            && "1".equals(week2[k]) && "1".equals(day2[j])) {
                        if (endA + S >= startB && endB + S >= startA) {
                            startA = Math.min(startA, startB);
                            endA = Math.max(endA, endB);
                            lengthBlock = endA - startA;
                            if (lengthBlock > M && blockBaru == true) {
                                lebih += 1;
                                blockBaru = false;
                            } else {
                                lebih += 0;
                            }
                        } else {
                            lengthBlock = endA - startA;
                            if (lengthBlock > M && blockBaru == true) {
                                lebih += 1;
                            } else {
                                lebih += 0;
                            }
                            blockBaru = true;
                            startA = startB;
                            endA = endB;
                            dayA = dayB;
                            weekA = weekB;
                        }
                    }
                }
            }
        }
        return lebih;
    }
}
