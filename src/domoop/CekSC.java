package domoop;

import java.util.ArrayList;

public class CekSC {

    ArrayList<Kelas> listKelas;

    boolean HardConstraint(int TS, int Rom, Kelas kls, ArrayList<Kelas> listKls, int[][] travel) {
        this.listKelas = listKls;
        ArrayList<Constraint> HardCons = kls.getClasHC();
        for (int i = 0; i < HardCons.size(); i++) {
            Constraint Hard = HardCons.get(i);
            ArrayList<Integer> KlsCons = Hard.getConsClass();
            String type = Hard.getType();
            int angka1 = Hard.getAngka1();
            int angka2 = Hard.getAngka2();
            int penalty = Hard.getPenalty();
            if (Cons1(type, angka1, angka2, TS, Rom, kls, KlsCons, penalty) || Cons2(type, TS, Rom, kls, KlsCons, travel, penalty)) {
                return true;
            }
        }
        return false;
    }

    Kelas SearchKls(int IdKls) {
        for (Kelas a : listKelas) {
            if (a.getClassID() == IdKls) {
                return a;
            }
        }
        return null;
    }

    boolean Cons1(String type, int angka1, int angka2, int TS, int Rom, Kelas kls, ArrayList<Integer> distriKls, int penalty) {
        if (type.contains("WorkDay")) {
            Workday(TS, angka1, kls, distriKls, penalty);
        } else if (type.contains("MinGap")) {
            Mingap(TS, angka1, kls, distriKls, penalty);
        } else if (type.contains("MaxDays")) {
            Maxdays(TS, angka1, kls, penalty);
        } else if (type.contains("MaxDayLoad")) {
            MaxdayLoad(TS, angka1, kls, penalty);
        } else if (type.contains("MaxBreaks")) {
            Maxbreaks(TS, angka1, angka2, kls, distriKls);
        } else if (type.contains("MaxBlock")) {
            Maxblock(TS, angka1, angka2, kls, distriKls);
        }
        return false;
    }

    boolean Cons2(String type, int TS, int Rom, Kelas kls, ArrayList<Integer> distriKls, int[][] travel, int penalty) {
        switch (type) {
            case "SameStart":
                SameStart(TS, kls, distriKls);
                break;
            case "SameTime":
                SameTime(TS, kls, distriKls);
                break;
            case "DifferentTime":
                DifferentTime(TS, kls, distriKls);
                break;
            case "SameDays":
                SameDays(TS, kls, distriKls);
                break;
            case "DifferentDays":
                DifferentDays(TS, kls, distriKls);
                break;
            case "SameWeeks":
                SameWeeks(TS, kls, distriKls);
                break;
            case "DifferentWeeks":
                DifferentWeeks(TS, kls, distriKls);
                break;
            case "SameRoom":
                SameRoom(Rom, kls, distriKls);
                break;
            case "DifferentRoom":
                DifferentRoom(Rom, kls, distriKls);
                break;
            case "Overlap":
                Overlap(TS, kls, distriKls);
                break;
            case "NotOverlap":
                NotOverlap(TS, kls, distriKls);
                break;
            case "SameAttendees":
                SameAttendees(TS, Rom, kls, travel, distriKls);
                break;
            case "Precedence":
                Precedence(TS, kls, distriKls);
                break;
            default:
                break;
        }
        return false;
    }

    //1.SAME START                                                              //Ci.start = Cj.start
    boolean SameStart(int TS, Kelas kls, ArrayList<Integer> listSS) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        for (int i = 0; i < listSS.size(); i++) {
            Kelas klsB = SearchKls(listSS.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                if (startA == startB) {
                    return true;
                }
            } else if (iTS == -1) {
                return true;
            }
        }
        return false;
    }

    //2.SAME TIME                                                               //(Ci.start ≤ Cj.start ∧ Cj.end ≤ Ci.end) ∨ (Cj.start ≤ Ci.start ∧ Ci.end ≤ Cj.end)
    boolean SameTime(int TS, Kelas kls, ArrayList<Integer> listST) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;

        for (int i = 0; i < listST.size(); i++) {
            Kelas klsB = SearchKls(listST.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                if ((startA <= startB && endB <= endA)
                        || (startB <= startA && endA <= endB)) {
                    return true;
                }
            } else if (iTS == -1) {
                return true;
            }
        }
        return false;
    }

    //3.DIFFERENT TIME                                                          //(Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start)
    boolean DifferentTime(int TS, Kelas kls, ArrayList<Integer> listDT) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        for (int i = 0; i < listDT.size(); i++) {
//            if (kls.getClassID() != listDT.get(i)) {
            Kelas klsB = SearchKls(listDT.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                if (endA <= startB || endB <= startA) {
                    return true;
                }
            } else if (iTS == -1) {
                return true;
            }
//            } else if (kls.getClassID() == listDT.get(i)) {
//                return true;
//            }
        }
        return false;
    }

    //4.SAME DAYS                                                               //((Ci.days or Cj.days) = Ci.days) ∨ ((Ci.days or Cj.days) = Cj.days)
    boolean SameDays(int TS, Kelas kls, ArrayList<Integer> listSD) {
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        for (int i = 0; i < listSD.size(); i++) {
            Kelas klsB = SearchKls(listSD.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();
                for (int j = 0; j < daysA.size(); j++) {
                    if (daysA.get(j) == 1 && daysB.get(j) == 1) {
                        return true;
                    }
                }
            } else if (iTS == -1) {
                return true;
            }
        }
        return false;
    }

    //5.DIFFERENT DAYS                                                          //(Ci.days and Cj.days) = 0
    boolean DifferentDays(int TS, Kelas kls, ArrayList<Integer> listDD) {
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        for (int i = 0; i < listDD.size(); i++) {
            Kelas klsB = SearchKls(listDD.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();
                for (int j = 0; j < daysA.size(); j++) {
                    if ((daysA.get(j) == 0 && daysB.get(j) == 0)
                            || (daysA.get(j) == 1 && daysB.get(j) == 0)
                            || (daysA.get(j) == 0 && daysB.get(j) == 1)) {
                        return true;
                    }
                }
            } else if (iTS == -1) {
                return true;
            }
        }
        return false;
    }

    //6.SAME WEEKS                                                              //(Ci.weeks or Cj.weeks) = Ci.weeks) ∨ (Ci.weeks or Cj.weeks) = Cj.weeks)
    boolean SameWeeks(int TS, Kelas kls, ArrayList<Integer> listSW) {

        ArrayList<Integer> weekA = kls.getAvailableTS().get(TS).getWeeks();
        for (int i = 0; i < listSW.size(); i++) {
            Kelas klsB = SearchKls(listSW.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                for (int k = 0; k < weekA.size(); k++) {
                    ArrayList<Integer> weekB = klsB.getAvailableTS().get(iTS).getWeeks();
                    if (weekA.get(k) == 1 && weekB.get(k) == 1) {
                        return true;
                    }
                }
            } else if (iTS == -1) {
                return true;
            }
        }
        return false;
    }

    //7.DIFFERENT WEEKS                                                         //(Ci.weeks and Cj.weeks) = 0
    boolean DifferentWeeks(int TS, Kelas kls, ArrayList<Integer> listDW) {

        ArrayList<Integer> weekA = kls.getAvailableTS().get(TS).getWeeks();

        for (int i = 0; i < listDW.size(); i++) {
            Kelas klsB = SearchKls(listDW.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                for (int k = 0; k < weekA.size(); k++) {
                    ArrayList<Integer> weekB = klsB.getAvailableTS().get(iTS).getWeeks();
                    if (weekA.get(k) == 0 && weekB.get(k) == 0
                            || weekA.get(k) == 1 && weekB.get(k) == 0
                            || weekA.get(k) == 0 && weekB.get(k) == 1) {
                        return true;
                    }
                }
            } else if (iTS == -1) {
                return true;
            }
        }
        return false;
    }

    //8.SAME ROOM                                                               //Ci.room = Cj.room
    boolean SameRoom(int Rom, Kelas kls, ArrayList<Integer> listSR) {
        System.out.println(kls.getClassID());
        int roomA = kls.getAvailableroom().get(Rom).getRoom_id();
        for (int i = 0; i < listSR.size(); i++) {
            Kelas klsB = SearchKls(listSR.get(i));
            int iRM = klsB.getRoom();
            if (iRM != -1) {
                int roomB = klsB.getAvailableroom().get(iRM).getRoom_id();
                if (roomA == roomB) {
                    return true;
                }
            } else if (iRM == -1) {
                return true;
            }
        }
        return false;
    }

    //9.DIFFERENT ROOM                                                          //Ci.room ≠ Cj.room
    boolean DifferentRoom(int Rom, Kelas kls, ArrayList<Integer> listDR) {

        int roomA = kls.getAvailableroom().get(Rom).getRoom_id();
        for (int i = 0; i < listDR.size(); i++) {
            Kelas klsB = SearchKls(listDR.get(i));
            int iRM = klsB.getRoom();
            if (iRM != -1) {
                int roomB = klsB.getAvailableroom().get(iRM).getRoom_id();
                if (roomA != roomB) {
                    return true;
                }
            } else if (iRM == -1) {
                return true;
            }
        }
        return false;
    }

    //10.OVERLAP                                                                //(Cj.start < Ci.end) ∧ (Ci.start < Cj.end) ∧ ((Ci.days and Cj.days) ≠ 0) ∧ ((Ci.weeks and Cj.weeks) ≠ 0)
    boolean Overlap(int TS, Kelas kls, ArrayList<Integer> listOv) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        ArrayList<Integer> weeksA = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();

        for (int i = 0; i < listOv.size(); i++) {
            Kelas klsB = SearchKls(listOv.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                ArrayList<Integer> weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();

                for (int wk = 0; wk < weeksA.size(); wk++) {
                    for (int dy = 0; dy < daysA.size(); dy++) {
                        if ((weeksA.get(wk) == 1 && weeksB.get(wk) == 1)
                                && (daysA.get(dy) == 1 && daysB.get(dy) == 1)
                                && (startB < endA) && (startA < endB)) {
                            return true;
                        }
                    }
                }
            } else if (iTS == -1) {
                return true;
            }
        }
        return false;
    }

    //11.NOT OVERLAP                                                            //(Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0)
    boolean NotOverlap(int TS, Kelas kls, ArrayList<Integer> listNotOv) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        ArrayList<Integer> weeksA = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();

        for (int i = 0; i < listNotOv.size(); i++) {
            Kelas klsB = SearchKls(listNotOv.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                ArrayList<Integer> weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();

                for (int wk = 0; wk < weeksA.size(); wk++) {
                    for (int dy = 0; dy < daysA.size(); dy++) {
                        if ((endA <= startB) || (endB <= startA)
                                || (weeksA.get(wk) == 0 && weeksB.get(wk) == 0)
                                || (daysA.get(dy) == 0 && daysB.get(dy) == 0)) {
                            return true;
                        }
                    }
                }
            } else if (iTS == -1) {
                return true;
            }
        }
        return false;
    }

    //12.SAME ATTENDEES --- MASIH ERROR TRAVEL ROOM                             //(Ci.end + Ci.room.travel[Cj.room] ≤ Cj.start) ∨ (Cj.end + Cj.room.travel[Ci.room] ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) Ci.room.travel[Cj.room] is the travel time between the assigned rooms of Ci and Cj
    boolean SameAttendees(int TS, int Rom, Kelas kls, int[][] valueTravel,
            ArrayList<Integer> listSA) {
        int trvl = 0;
        boolean noRoomA = false;
        boolean noRoomB = false;
        int roomA = 0;
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        ArrayList<Integer> weeksA = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        if (Rom == 999) {
            noRoomA = true;
        } else {
            roomA = kls.getAvailableroom().get(Rom).getRoom_id() - 1;
        }

        for (int i = 0; i < listSA.size(); i++) {
            Kelas klsB = SearchKls(listSA.get(i));
            int iTS = klsB.getTs();
            int iRM = 0;
            if (klsB.isHasRoom() == false) {
                iRM = 999;
            } else {
                iRM = klsB.getRoom();
            }
            if (iTS != -1 && iRM != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                ArrayList<Integer> weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();
                int roomB = 0;
                if (iRM == 999) {
                    noRoomB = true;
                } else {
                    roomB = klsB.getAvailableroom().get(iRM).getRoom_id() - 1;
                }

                if ((noRoomA == true && noRoomB == true)
                        || (noRoomA == true && noRoomB == false)
                        || (noRoomA == false && noRoomB == true)) {
                    trvl = 0;
                } else {
                    trvl = valueTravel[roomA][roomB];
                }

                for (int wk = 0; wk < weeksA.size(); wk++) {
                    for (int dy = 0; dy < daysA.size(); dy++) {
                        if (((endA + trvl) <= startB)
                                || ((endB + trvl) <= startA)
                                || (weeksA.get(wk) == 0 && weeksB.get(wk) == 0)
                                || (daysA.get(dy) == 0 && daysB.get(dy) == 0)) {
                            return true;
                        }
                    }
                }
            } else if (iTS == -1 && iRM == -1) {
                return true;
            }
        }
        return false;
    }

    //13.PRECEDENCE                                                             //(first(Ci.weeks) < first(Cj.weeks)) ∨ [(first(Ci.weeks) = first(Cj.weeks)) ∧ [(first(Ci .days) < first(Cj .days)) ∨ ((first(Ci.days) = first(Cj.days)) ∧ (Ci.end ≤ Cj.start))]] for any two classes Ci and Cj from the constraint where i < j and first(x) is the index of the first non-zero bit in the binary string x.
    boolean Precedence(int TS, Kelas kls, ArrayList<Integer> listP) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        ArrayList<Integer> weeksA = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();

        for (int i = 0; i < listP.size(); i++) {
            Kelas klsB = SearchKls(listP.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                ArrayList<Integer> weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();

                for (int wk = 0; wk < weeksA.size(); wk++) {
                    for (int dy = 0; dy < daysA.size(); dy++) {
                        if ((weeksA.get(wk) < weeksB.get(wk))
                                || (weeksA.get(wk) == weeksB.get(wk))
                                && (daysA.get(dy) < daysB.get(dy))
                                || (daysA.get(dy) == daysB.get(dy))
                                && (endA <= startB)) {
                            return true;
                        }
                    }
                }
            } else if (iTS == -1) {
                return true;
            }
        }
        return false;
    }

    //14.WORKDAY(S)                                                             //((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (max(Ci.end,Cj.end)−min(Ci.start,Cj.start) ≤ S)
    int Workday(int TS, int S, Kelas kls, ArrayList<Integer> listWD, int penalty) {
        int jPenalty = 0;
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        ArrayList<Integer> weeksA = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        for (int i = 0; i < listWD.size(); i++) {
            Kelas klsB = SearchKls(listWD.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                ArrayList<Integer> weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();
                for (int k = 0; k < weeksA.size(); k++) {
                    for (int l = 0; l < daysA.size(); l++) {
                        if ((daysA.get(l) == 0 && daysB.get(l) == 0)
                                || (weeksA.get(k) == 0 && weeksB.get(k) == 0)
                                || (Math.max(endA, endB)) - (Math.min(startA,
                                startB)) <= S) {
                            jPenalty += 0;
                        }
                    }
                }
            } else if (iTS == -1) {
                jPenalty += 0;
            } else {
                jPenalty += penalty;
            }
        }
        return jPenalty;
    }

    //15.MINGAP(G)                                                              //((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (Ci.end + G ≤ Cj.start) ∨ (Cj.end + G ≤ Ci.start)
    int Mingap(int TS, int G, Kelas kls, ArrayList<Integer> listMG, int penalty) {
        int jPenalty = 0;
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        ArrayList<Integer> weeksA = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        for (int i = 0; i < listMG.size(); i++) {
            Kelas klsB = SearchKls(listMG.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                ArrayList<Integer> weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();
                for (int k = 0; k < weeksA.size(); k++) {
                    for (int l = 0; l < daysA.size(); l++) {
                        if ((daysA.get(l) == 0 && daysB.get(l) == 0)
                                || (weeksA.get(k) == 0 && weeksB.get(k) == 0)
                                || (endA + G <= startB) || (endB + G <= startA)) {
                            jPenalty += 0;
                        }
                    }
                }
            } else if (iTS == -1) {
                jPenalty += 0;
            } else {
                jPenalty += penalty;
            }
        }
        return jPenalty;
    }

    //16.MAXDAYS(D)                                                             //countNonzeroBits(C1.days or C2.days or ⋅ ⋅ ⋅ Cn.days) ≤ D
    int Maxdays(int TS, int D, Kelas kls, int penalty) {
        int jPenalty = 0;
        int temp = 0;
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        if (countNonzeroBits(daysA) <= D) {
            //Jangan Lupa Method countNonzeroBits berubah
            jPenalty += 0;
        } else {
            temp = (countNonzeroBits(daysA) - D);
            jPenalty += (temp * penalty);
        }
        return jPenalty;
    }

    //17.MAXDAYLOAD(S)                                                         //DayLoad(d,w) ≤ S >>> DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
    int MaxdayLoad(int TS, int S, Kelas kls, int penalty) {
        int jPenalty = 0;
        int length = kls.getAvailableTS().get(TS).getLength();
        ArrayList<Integer> weeks = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> days = kls.getAvailableTS().get(TS).getDays();
        if (DayLoad(S, length, days, weeks) == 0) {
            //Jangan Lupa Method DayLoad berubah
            jPenalty += 0;
        } else {
            jPenalty = (penalty * (DayLoad(S, length, days, weeks)) / weeks.size());
        }
        return jPenalty;
    }

    //18.MAXBREAKS(R,S)
    //|MergeBlocks{(C.start, C.end) | (C.days and 2d) ≠ 0 ∧ (C.weeks and 2w) ≠ 0})| ≤ R + 1 >> (Ba.end + S ≥ Bb.start) ∧ (Bb.end + S ≥ Ba.start) ⇒ (B.start = min(Ba.start, Bb.start)) ∧ (B.end = max(Ba.end, Bb.end))
    boolean Maxbreaks(int TS, int R, int S, Kelas kls, ArrayList<Integer> listMB) {

        if (MergeBlocks(TS, S, kls, listMB) <= R + 1) {
            return true;
        }
        return false;
    }

    ///19.MAXBLOCK(M,S)                                                          //max {B.end − B.start | B ∈ MergeBlocks{(C.start, C.end) | (C.days and 2d) ≠ 0 ∧ (C.weeks and 2w) ≠ 0}} ≤ M
    boolean Maxblock(int TS, int M, int S, Kelas kls, ArrayList<Integer> listMBL) {
        if (MergeBlockB(TS, M, S, kls, listMBL) <= M) {
            return true;
        }

        return false;
    }

    private int countNonzeroBits(ArrayList<Integer> daysA) {
        int count = 0;
        for (int i = 0; i < daysA.size(); i++) {
            if (daysA.get(i) == 1) {
                count++;
            }
        }
        return count;
    }

    private int DayLoad(int S, int length, ArrayList<Integer> days,
            ArrayList<Integer> weeks) {                                         //DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
        int lebih = 0;
        for (int wk = 0; wk < weeks.size(); wk++) {
            for (int dy = 0; dy < days.size(); dy++) {
                if (weeks.get(wk) == 1 && days.get(dy) == 1) {
                    if (length > S) {
                        lebih += (length - S);
                    }
                }
            }
        }
        return lebih;
    }

    private int MergeBlocks(int TS, int S, Kelas kls, ArrayList<Integer> listMB) {
        int initialBlock = 1;

        ArrayList<Integer> dayA = kls.getAvailableTS().get(TS).getDays();
        ArrayList<Integer> weekA = kls.getAvailableTS().get(TS).getWeeks();
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lenA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lenA;

        for (int i = 1; i < listMB.size(); i++) {
            Kelas klsB = SearchKls(listMB.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                ArrayList<Integer> dayB = klsB.getAvailableTS().get(iTS).getDays();
                ArrayList<Integer> weekB = klsB.getAvailableTS().get(iTS).getWeeks();
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lenB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lenB;

                outerloop:
                for (int wk = 0; wk < weekA.size(); wk++) {
                    for (int ds = 0; ds < dayA.size(); ds++) {
                        if (weekA.get(wk) == 1 && dayA.get(ds) == 1 && weekB.get(wk) == 1 && dayB.get(ds) == 1) {
                            if (endA + S >= startB && endB + S >= startA) {
                                startA = Math.min(startA, startB);
                                endA = Math.max(endA, endB);
                            } else {
                                initialBlock++;
                                startA = startB;
                                endA = endB;
                                dayA = dayB;
                                weekA = weekB;
                                break outerloop;
                            }
                        }
                    }
                }
            } else if (iTS == -1) {
                return initialBlock;
            }
        }
        return initialBlock;
    }

    private int MergeBlockB(int TS, int M, int S, Kelas kls, ArrayList<Integer> listMBL) {

        int lengthBlock = 0;
        int lebih = 0;
        boolean blockBaru = true;

        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        ArrayList<Integer> weeksA = kls.getAvailableTS().get(TS).getWeeks();
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;

        for (int i = 0; i < listMBL.size(); i++) {
            Kelas klsB = SearchKls(listMBL.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {

                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();
                ArrayList<Integer> weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;

                outerloop:
                for (int wk = 0; wk < weeksA.size(); wk++) {
                    for (int j = 0; j < daysA.size(); j++) {
                        if (weeksA.get(wk) == 1 && daysA.get(j) == 1 && weeksB.get(wk) == 1
                                && daysB.get(j) == 1) {
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
                                daysA = daysB;
                                weeksA = weeksB;
                                break outerloop;
                            }
                        }
                    }
                }
            } else if (iTS == -1) {
                return lengthBlock;
            }
        }
        return lengthBlock;
    }
}
