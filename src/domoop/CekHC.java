package domoop;

import java.util.ArrayList;

public class CekHC {

    ArrayList<Kelas> listKelas;

    boolean HardConstraint(int TS, int Rom, Kelas kls, ArrayList<Kelas> listKls, int[][] travel) {
        this.listKelas = listKls;
        ArrayList<Constraint> HardCons = kls.getClasHC();
        boolean cek = false;
        for (int i = 0; i < HardCons.size(); i++) {
            Constraint Hard = HardCons.get(i);
            ArrayList<Integer> KlsCons = Hard.getConsClass();
            String type = Hard.getType();
            int angka1 = Hard.getAngka1();
            int angka2 = Hard.getAngka2();
            if (type == "WorkDay" || type == "MinGap" || type == "MaxDays" || type == "MaxDayLoad" || type == "MaxBreaks" || type == "MaxBlock") {
                if (Cons1(type, angka1, angka2, TS, Rom, kls, KlsCons)) {
                    cek = true;
                } else {
                    return false;
                }
            } else {
                if (Cons2(type, TS, Rom, kls, KlsCons, travel)) {
                    cek = true;
                } else {
                    return false;
                }
            }
//            if (Cons1(type, angka1, angka2, TS, Rom, kls, KlsCons) || Cons2(type, TS, Rom, kls, KlsCons, travel)) {
//                cek = true;
//            } else {
//                return false;
//            }
        }
        return cek;
    }

    Kelas SearchKls(int IdKls) {
        for (Kelas a : listKelas) {
            if (a.getClassID() == IdKls) {
                return a;
            }
        }
        return null;
    }

    boolean Cons1(String type, int angka1, int angka2, int TS, int Rom, Kelas kls, ArrayList<Integer> distriKls) {
        if (type.contains("WorkDay")) {
            return Workday(TS, angka1, kls, distriKls);
        } else if (type.contains("MinGap")) {
            return Mingap(TS, angka1, kls, distriKls);
        } else if (type.contains("MaxDays")) {
            return Maxdays(TS, angka1, kls);
        } else if (type.contains("MaxDayLoad")) {
            return MaxdayLoad(TS, angka1, kls);
        } else if (type.contains("MaxBreaks")) {
            return Maxbreaks(TS, angka1, angka2, kls, distriKls);
        } else if (type.contains("MaxBlock")) {
            return Maxblock(TS, angka1, angka2, kls, distriKls);
        }
        return false;
    }

    boolean Cons2(String type, int TS, int Rom, Kelas kls, ArrayList<Integer> distriKls, int[][] travel) {
        switch (type) {
            case "SameStart":
                return SameStart(TS, kls, distriKls);
            case "SameTime":
                return SameTime(TS, kls, distriKls);
            case "DifferentTime":
                return DifferentTime(TS, kls, distriKls);
            case "SameDays":
                return SameDays(TS, kls, distriKls);
            case "DifferentDays":
                return DifferentDays(TS, kls, distriKls);
            case "SameWeeks":
                return SameWeeks(TS, kls, distriKls);
            case "DifferentWeeks":
                return DifferentWeeks(TS, kls, distriKls);
            case "SameRoom":
                return SameRoom(Rom, kls, distriKls);
            case "DifferentRoom":
                return DifferentRoom(Rom, kls, distriKls);
            case "Overlap":
                return Overlap(TS, kls, distriKls);
            case "NotOverlap":
                return NotOverlap(TS, kls, distriKls);
            case "SameAttendees":
                return SameAttendees(TS, Rom, kls, travel, distriKls);
            case "Precedence":
                return Precedence(TS, kls, distriKls);
            default:
                break;
        }
        return false;
    }

    //1.SAME START                                                              //Ci.start = Cj.start
    boolean SameStart(int TS, Kelas kls, ArrayList<Integer> listSS) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        boolean SS = false;
        for (int i = 0; i < listSS.size(); i++) {
            Kelas klsB = SearchKls(listSS.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                if (startA == startB) {
                    SS = true;
                } else {
                    return false;
                }
            } else if (iTS == -1) {
                SS = true;
            }
        }
        return SS;
    }

    //2.SAME TIME                                                               //(Ci.start ≤ Cj.start ∧ Cj.end ≤ Ci.end) ∨ (Cj.start ≤ Ci.start ∧ Ci.end ≤ Cj.end)
    boolean SameTime(int TS, Kelas kls, ArrayList<Integer> listST) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        boolean ST = false;
        for (int i = 0; i < listST.size(); i++) {
            Kelas klsB = SearchKls(listST.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                if ((startA <= startB && endB <= endA)
                        || (startB <= startA && endA <= endB)) {
                    ST = true;
                } else {
                    return false;
                }
            } else if (iTS == -1) {
                ST = true;
            }
        }
        return ST;
    }

    //3.DIFFERENT TIME                                                          //(Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start)
    boolean DifferentTime(int TS, Kelas kls, ArrayList<Integer> listDT) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        boolean DT = false;
        for (int i = 0; i < listDT.size(); i++) {
//            if (kls.getClassID() != listDT.get(i)) {
            Kelas klsB = SearchKls(listDT.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                if (endA <= startB || endB <= startA) {
                    DT = true;
                } else {
                    return false;
                }
            } else if (iTS == -1) {
                DT = true;
            }
        }
        return DT;
    }

    //4.SAME DAYS                                                               //((Ci.days or Cj.days) = Ci.days) ∨ ((Ci.days or Cj.days) = Cj.days)
    boolean SameDays(int TS, Kelas kls, ArrayList<Integer> listSD) {
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        boolean SD = false;
        for (int i = 0; i < listSD.size(); i++) {
            Kelas klsB = SearchKls(listSD.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();
                for (int j = 0; j < daysA.size(); j++) {
                    if (daysA.get(j) == 1 && daysB.get(j) == 1) {
                        SD = true;
                        break;
                    } else {
                        SD = false;
                    }
                }
                if (SD == false) {
                    return false;
                }
            } else if (iTS == -1) {
                SD = true;
            }
        }
        return SD;
    }

    //5.DIFFERENT DAYS                                                          //(Ci.days and Cj.days) = 0
    boolean DifferentDays(int TS, Kelas kls, ArrayList<Integer> listDD) {
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        boolean DD = false;
        for (int i = 0; i < listDD.size(); i++) {
            Kelas klsB = SearchKls(listDD.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();
                for (int j = 0; j < daysA.size(); j++) {
                    if ((daysA.get(j) != 1 || daysB.get(j) != 1)) {
                        DD = true;
                    } else {
                        DD = false;
                        break;
                    }
                }
                if (DD == false) {
                    return false;
                }
            } else if (iTS == -1) {
                DD = true;
            }
        }
        return DD;
    }

    //6.SAME WEEKS                                                              //(Ci.weeks or Cj.weeks) = Ci.weeks) ∨ (Ci.weeks or Cj.weeks) = Cj.weeks)
    boolean SameWeeks(int TS, Kelas kls, ArrayList<Integer> listSW) {
        boolean SW = false;
        ArrayList<Integer> weekA = kls.getAvailableTS().get(TS).getWeeks();
        for (int i = 0; i < listSW.size(); i++) {
            Kelas klsB = SearchKls(listSW.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                for (int k = 0; k < weekA.size(); k++) {
                    ArrayList<Integer> weekB = klsB.getAvailableTS().get(iTS).getWeeks();
                    if (weekA.get(k) == 1 && weekB.get(k) == 1) {
                        SW = true;
                        break;
                    } else {
                        SW = false;
                    }
                }
                if (SW == false) {
                    return false;
                }
            } else if (iTS == -1) {
                SW = true;
            }
        }
        return SW;
    }

    //7.DIFFERENT WEEKS                                                         //(Ci.weeks and Cj.weeks) = 0
    boolean DifferentWeeks(int TS, Kelas kls, ArrayList<Integer> listDW) {
        boolean DW = false;
        ArrayList<Integer> weekA = kls.getAvailableTS().get(TS).getWeeks();
        for (int i = 0; i < listDW.size(); i++) {
            Kelas klsB = SearchKls(listDW.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                for (int k = 0; k < weekA.size(); k++) {
                    ArrayList<Integer> weekB = klsB.getAvailableTS().get(iTS).getWeeks();
                    if (weekA.get(k) != 1 || weekB.get(k) != 1) {
                        DW = true;
                    } else {
                        DW = false;
                        break;
                    }
                }
                if (DW == false) {
                    return false;
                }
            } else if (iTS == -1) {
                DW = true;
            }
        }
        return DW;
    }

    //8.SAME ROOM                                                               //Ci.room = Cj.room
    boolean SameRoom(int Rom, Kelas kls, ArrayList<Integer> listSR) {
        boolean SR = false;
        int roomA = kls.getAvailableroom().get(Rom).getRoom_id();
        for (int i = 0; i < listSR.size(); i++) {
            Kelas klsB = SearchKls(listSR.get(i));
            int iRM = klsB.getRoom();
            if (iRM != -1) {
                int roomB = klsB.getAvailableroom().get(iRM).getRoom_id();
                if (roomA == roomB) {
                    SR = true;
                } else {
                    return false;
                }
            } else if (iRM == -1) {
                SR = true;
            }
        }
        return SR;
    }

    //9.DIFFERENT ROOM                                                          //Ci.room ≠ Cj.room
    boolean DifferentRoom(int Rom, Kelas kls, ArrayList<Integer> listDR) {
        boolean DR = false;
        int roomA = kls.getAvailableroom().get(Rom).getRoom_id();
        for (int i = 0; i < listDR.size(); i++) {
            Kelas klsB = SearchKls(listDR.get(i));
            int iRM = klsB.getRoom();
            if (iRM != -1) {
                int roomB = klsB.getAvailableroom().get(iRM).getRoom_id();
                if (roomA != roomB) {
                    DR = true;
                } else {
                    return false;
                }
            } else if (iRM == -1) {
                DR = true;
            }
        }
        return DR;
    }

    //10.OVERLAP                                                                //(Cj.start < Ci.end) ∧ (Ci.start < Cj.end) ∧ ((Ci.days and Cj.days) ≠ 0) ∧ ((Ci.weeks and Cj.weeks) ≠ 0)
    boolean Overlap(int TS, Kelas kls, ArrayList<Integer> listOv) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        ArrayList<Integer> weeksA = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        boolean O = false;

        for (int i = 0; i < listOv.size(); i++) {
            Kelas klsB = SearchKls(listOv.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                ArrayList<Integer> weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();

                if ((startB < endA) && (startA < endB)) {
                    for (int wk = 0; wk < weeksA.size(); wk++) {
                        if (weeksA.get(wk) == 1 && weeksB.get(wk) == 1) {
                            for (int dy = 0; dy < daysA.size(); dy++) {
                                if (daysA.get(dy) == 1 && daysB.get(dy) == 1) {
                                    O = true;
                                } else {
                                    return false;
                                }
                            }
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else if (iTS == -1) {
                O = true;
            }
        }
        return O;
    }

    //11.NOT OVERLAP                                                            //(Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0)
    boolean NotOverlap(int TS, Kelas kls, ArrayList<Integer> listNotOv) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        ArrayList<Integer> weeksA = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        boolean NO = false;

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
                    if (weeksA.get(wk) == 1 && weeksB.get(wk) == 1) {
                        for (int dy = 0; dy < daysA.size(); dy++) {
                            if (daysA.get(dy) == 1 && daysB.get(dy) == 1) {
                                if ((endA <= startB) || (endB <= startA)) {
                                    NO = true;
                                } else {
                                    return false;
                                }
                            } else {
                                NO = true;
                            }
                        }
                    } else {
                        NO = true;
                    }
                }
            } else if (iTS == -1) {
                NO = true;
            }
        }
        return NO;
    }

//12.SAME ATTENDEES                                                         //(Ci.end + Ci.room.travel[Cj.room] ≤ Cj.start) ∨ (Cj.end + Cj.room.travel[Ci.room] ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) Ci.room.travel[Cj.room] is the travel time between the assigned rooms of Ci and Cj
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
        boolean SA = false;

        if (Rom == 999) {
            noRoomA = true;
        } else {
            roomA = kls.getAvailableroom().get(Rom).getRoom_id() - 1;
        }

        for (int i = 0; i < listSA.size(); i++) {
            Kelas klsB = SearchKls(listSA.get(i));
            int iTS = klsB.getTs();
            int iRM = klsB.getRoom();
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

                if ((noRoomA == false && noRoomB == false)) {
                    trvl = valueTravel[roomA][roomB];
                }

                outerloop:
                for (int wk = 0; wk < weeksA.size(); wk++) {
                    if (weeksA.get(wk) == 1 && weeksB.get(wk) == 1) {
                        for (int dy = 0; dy < daysA.size(); dy++) {
                            if (daysA.get(dy) == 1 && daysB.get(dy) == 1) {
                                if (((endA + trvl) <= startB) || ((endB + trvl) <= startA)) {
                                    SA = true;
                                    break outerloop;
                                } else {
                                    return false;
                                }
                            } else {
                                SA = true;
                            }
                        }
                    } else {
                        SA = true;
                    }
                }
            } else if (iTS == -1) {
                SA = true;
            }
        }
        return SA;
    }

    //13.PRECEDENCE                                                             //(first(Ci.weeks) < first(Cj.weeks)) ∨ [(first(Ci.weeks) = first(Cj.weeks)) ∧ [(first(Ci .days) < first(Cj .days)) ∨ ((first(Ci.days) = first(Cj.days)) ∧ (Ci.end ≤ Cj.start))]] for any two classes Ci and Cj from the constraint where i < j and first(x) is the index of the first non-zero bit in the binary string x.
    boolean Precedence(int TS, Kelas kls, ArrayList<Integer> listP) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        ArrayList<Integer> weeksA = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        boolean P = false;
        int klsA = listP.indexOf(kls.getClassID());

        for (int i = 0; i < listP.size(); i++) {
            if (i < klsA) {
                Kelas klsB = SearchKls(listP.get(i));
                int iTS = klsB.getTs();
                if (iTS != -1) {
                    int startB = klsB.getAvailableTS().get(iTS).getStart();
                    int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                    int endB = startB + lengthB;
                    ArrayList<Integer> weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                    ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();

                    outerloop:
                    for (int wk = 0; wk < weeksA.size(); wk++) {
                        if (weeksA.get(wk) < weeksB.get(wk)) {
                            P = true;
                            break outerloop;
                        } else if (weeksA.get(wk) == 1 && weeksB.get(wk) == 1) {
                            for (int dy = 0; dy < daysA.size(); dy++) {
                                if (daysA.get(dy) < daysB.get(dy)) {
                                    P = true;
                                    break outerloop;
                                } else if (daysA.get(dy) == 1 && daysB.get(dy) == 1) {
                                    if ((endA >= startB)) {
                                        P = true;
                                        break outerloop;
                                    } else {
                                        return false;
                                    }
                                } else {
                                    P = false;
                                }
                            }
                        } else {
                            P = false;
                        }
                    }
                } else if (iTS == -1) {
                    P = true;
                }
            } else if (i > klsA) {
                Kelas klsB = SearchKls(listP.get(i));
                int iTS = klsB.getTs();
                if (iTS != -1) {
                    int startB = klsB.getAvailableTS().get(iTS).getStart();
                    int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                    int endB = startB + lengthB;
                    ArrayList<Integer> weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                    ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();

                    outerloop:
                    for (int wk = 0; wk < weeksA.size(); wk++) {
                        if (weeksA.get(wk) > weeksB.get(wk)) {
                            P = true;
                            break outerloop;
                        } else if (weeksA.get(wk) == 1 && weeksB.get(wk) == 1) {
                            for (int dy = 0; dy < daysA.size(); dy++) {
                                if (daysA.get(dy) > daysB.get(dy)) {
                                    P = true;
                                    break outerloop;
                                } else if (daysA.get(dy) == 1 && daysB.get(dy) == 1) {
                                    if ((endA <= startB)) {
                                        P = true;
                                        break outerloop;
                                    } else {
                                        return false;
                                    }
                                } else {
                                    P = false;
                                }
                            }
                        } else {
                            P = false;
                        }
                    }
                } else if (iTS == -1) {
                    P = true;
                }
            }
        }
        return P;
    }

//14.WORKDAY(S)                                                             //((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (max(Ci.end,Cj.end)−min(Ci.start,Cj.start) ≤ S)
    boolean Workday(int TS, int S, Kelas kls,
            ArrayList<Integer> listWD
    ) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        ArrayList<Integer> weeksA = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        boolean WD = false;

        for (int i = 0; i < listWD.size(); i++) {
            Kelas klsB = SearchKls(listWD.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                ArrayList<Integer> weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();

                outerloop:
                for (int k = 0; k < weeksA.size(); k++) {
                    if (weeksA.get(k) == 1 && weeksB.get(k) == 1) {
                        for (int l = 0; l < daysA.size(); l++) {
                            if (daysA.get(l) == 1 && daysB.get(l) == 1) {
                                if ((Math.max(endA, endB)) - (Math.min(startA,
                                        startB)) <= S) {
                                    WD = true;
                                    break outerloop;
                                } else {
                                    return false;
                                }
                            } else {
                                WD = true;
                            }
                        }
                    } else {
                        WD = true;
                    }
                }
                if (WD == false) {
                    return false;
                }
            } else if (iTS == -1) {
                WD = true;
            }
        }
        return WD;
    }

    //15.MINGAP(G)                                                              //((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (Ci.end + G ≤ Cj.start) ∨ (Cj.end + G ≤ Ci.start)
    boolean Mingap(int TS, int G, Kelas kls,
            ArrayList<Integer> listMG
    ) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        ArrayList<Integer> weeksA = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        boolean MG = false;

        for (int i = 0; i < listMG.size(); i++) {
            Kelas klsB = SearchKls(listMG.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                ArrayList<Integer> weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                ArrayList<Integer> daysB = klsB.getAvailableTS().get(iTS).getDays();
                outerloop:
                for (int k = 0; k < weeksA.size(); k++) {
                    if (weeksA.get(k) == 1 && weeksB.get(k) == 1) {
                        for (int l = 0; l < daysA.size(); l++) {
                            if (daysA.get(l) == 1 && daysB.get(l) == 1) {
                                if ((endA + G <= startB) || (endB + G <= startA)) {
                                    MG = true;
                                    break outerloop;
                                } else {
                                    return false;
                                }
                            } else {
                                MG = true;
                            }
                        }
                    } else {
                        MG = true;
                    }
                }
                if (MG == false) {
                    return false;
                }
            } else if (iTS == -1) {
                MG = true;
            }
        }
        return MG;
    }

    //16.MAXDAYS(D)                                                             //countNonzeroBits(C1.days or C2.days or ⋅ ⋅ ⋅ Cn.days) ≤ D
    boolean Maxdays(int TS, int D, Kelas kls
    ) {
        ArrayList<Integer> daysA = kls.getAvailableTS().get(TS).getDays();
        boolean MD = false;
        if (countNonzeroBits(daysA) <= D) {
            //Jangan Lupa Method countNonzeroBits berubah
            MD = true;
        } else {
            return false;
        }
        return MD;
    }

    //17.MAXDAYLOAD(S)                                                         //DayLoad(d,w) ≤ S >>> DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
    boolean MaxdayLoad(int TS, int S, Kelas kls
    ) {
        int length = kls.getAvailableTS().get(TS).getLength();
        ArrayList<Integer> weeks = kls.getAvailableTS().get(TS).getWeeks();
        ArrayList<Integer> days = kls.getAvailableTS().get(TS).getDays();
        boolean MDL = false;

        if (DayLoad(S, length, days, weeks) == 0) {
            //Jangan Lupa Method DayLoad berubah
            MDL = true;
        } else {
            return false;
        }
        return MDL;
    }

    //18.MAXBREAKS(R,S)
    //|MergeBlocks{(C.start, C.end) | (C.days and 2d) ≠ 0 ∧ (C.weeks and 2w) ≠ 0})| ≤ R + 1 >> (Ba.end + S ≥ Bb.start) ∧ (Bb.end + S ≥ Ba.start) ⇒ (B.start = min(Ba.start, Bb.start)) ∧ (B.end = max(Ba.end, Bb.end))
    boolean Maxbreaks(int TS, int R, int S, Kelas kls,
            ArrayList<Integer> listMB
    ) {
        boolean MB = false;
        if (MergeBlocks(TS, S, kls, listMB) <= R + 1) {
            MB = true;
        } else {
            return false;
        }
        return MB;
    }

    ///19.MAXBLOCK(M,S)                                                          //max {B.end − B.start | B ∈ MergeBlocks{(C.start, C.end) | (C.days and 2d) ≠ 0 ∧ (C.weeks and 2w) ≠ 0}} ≤ M
    boolean Maxblock(int TS, int M, int S, Kelas kls,
            ArrayList<Integer> listMBL
    ) {
        boolean MB = false;
        if (MergeBlockB(TS, M, S, kls, listMBL) <= M) {
            MB = true;
        } else {
            return false;
        }

        return MB;
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
                initialBlock += 0;
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
                lengthBlock += 0;
            }
        }
        return lengthBlock;
    }
}
