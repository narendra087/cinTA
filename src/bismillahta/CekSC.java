package bismillahta;

import java.util.ArrayList;

public class CekSC {

    ArrayList<Kelas> listKelas;
    ArrayList<Integer> weeksA, daysA;
    ArrayList<Integer> weeksB, daysB;
    Distributions distrib;

    //Return jumlah semua penalti
    int totalPenalty(ArrayList<Kelas> listKls, int[][] travel, Distributions distri) {
        this.listKelas = listKls;
        this.distrib = distri;
        int penalty;
        int jmlPenalty = 0;
        ArrayList<Constraint> SoftCons = distrib.getSoftC();
        for (int i = 0; i < SoftCons.size(); i++) {
            Constraint Soft = SoftCons.get(i);
            ArrayList<Integer> KlsCons = Soft.getConsClass();
            String type = Soft.getType();
            int angka1 = Soft.getAngka1();
            int angka2 = Soft.getAngka2();
            penalty = Soft.getPenalty();
            if (type.contains("WorkDay") || type.contains("MinGap") || type.contains("MaxDays")
                    || type.contains("MaxDayLoad") || type.contains("MaxBreaks") || type.contains("MaxBlock")) {
                jmlPenalty += Cons1(type, angka1, angka2, KlsCons, penalty);
            } else {
                jmlPenalty += Cons2(type, KlsCons, travel, penalty);
            }
        }
        return jmlPenalty;
    }

    Kelas SearchKls(int IdKls) {
        for (Kelas a : listKelas) {
            if (a.getClassID() == IdKls) {
                return a;
            }
        }
        return null;
    }

    int Cons1(String type, int angka1, int angka2,
            ArrayList<Integer> distriKls, int penalty) {
        if (type.contains("WorkDay")) {
            return Workday(angka1, distriKls, penalty);
        } else if (type.contains("MinGap")) {
            return Mingap(angka1, distriKls, penalty);
        } else if (type.contains("MaxDays")) {
            return Maxdays(angka1, penalty);
        } else if (type.contains("MaxDayLoad")) {
            return MaxdayLoad(angka1, distriKls, penalty);
        } else if (type.contains("MaxBreaks")) {
            return Maxbreaks(angka1, angka2, distriKls, penalty);
        } else if (type.contains("MaxBlock")) {
            return Maxblock(angka1, angka2, distriKls, penalty);
        }
        return 0;
    }

    int Cons2(String type, ArrayList<Integer> distriKls, int[][] travel, int penalty) {
        switch (type) {
            case "SameStart":
                return SameStart(distriKls, penalty);
            case "SameTime":
                return SameTime(distriKls, penalty);
            case "DifferentTime":
                return DifferentTime(distriKls, penalty);
            case "SameDays":
                return SameDays(distriKls, penalty);
            case "DifferentDays":
                return DifferentDays(distriKls, penalty);
            case "SameWeeks":
                return SameWeeks(distriKls, penalty);
            case "DifferentWeeks":
                return DifferentWeeks(distriKls, penalty);
            case "SameRoom":
                return SameRoom(distriKls, penalty);
            case "DifferentRoom":
                return DifferentRoom(distriKls, penalty);
            case "Overlap":
                return Overlap(distriKls, penalty);
            case "NotOverlap":
                return NotOverlap(distriKls, penalty);
            case "SameAttendees":
                return SameAttendees(travel, distriKls, penalty);
            case "Precedence":
                return Precedence(distriKls, penalty);
            default:
                break;
        }
        return 0;
    }

    //1.SAME START                                                              //Ci.start = Cj.start
    int SameStart(ArrayList<Integer> listSS, int penalty) {
        int startA, startB;
        int TSA, TSB;
        int jPenalty = 0;

        //CEK ISI CONSTRAINT, JIKA 1 MAKA TRUE
        if (listSS.size() == 1) {
            return jPenalty;
        }

        //AMBIL KELAS PERTAMA
        for (int i = 0; i < listSS.size(); i++) {
            Kelas klsA = SearchKls(listSS.get(i));
            TSA = klsA.getTs();
            startA = klsA.getAvailableTS().get(TSA).getStart();

            //AMBIL KELAS BERIKUTNYA
            for (int j = i + 1; j < listSS.size(); j++) {
                Kelas klsB = SearchKls(listSS.get(j));
                TSB = klsB.getTs();
                startB = klsB.getAvailableTS().get(TSB).getStart();

                //CEK CONSTRAINT
                if (startA == startB) {
                    jPenalty += 0;
                } else {
                    jPenalty += penalty;
                }
            }
        }
        return jPenalty;
    }

    //2.SAME TIME                                                               //(Ci.start ≤ Cj.start ∧ Cj.end ≤ Ci.end) ∨ (Cj.start ≤ Ci.start ∧ Ci.end ≤ Cj.end)
    int SameTime(ArrayList<Integer> listST, int penalty) {
        int startA, lengthA, endA;
        int startB, lengthB, endB;
        int TSA, TSB;
        int jPenalty = 0;

        if (listST.size() == 1) {
            return jPenalty;
        }

        for (int i = 0; i < listST.size(); i++) {
            Kelas klsA = SearchKls(listST.get(i));
            TSA = klsA.getTs();
            startA = klsA.getAvailableTS().get(TSA).getStart();
            lengthA = klsA.getAvailableTS().get(TSA).getLength();
            endA = startA + lengthA;

            for (int j = i + 1; j < listST.size(); j++) {
                Kelas klsB = SearchKls(listST.get(j));
                TSB = klsB.getTs();
                startB = klsB.getAvailableTS().get(TSB).getStart();
                lengthB = klsB.getAvailableTS().get(TSB).getLength();
                endB = startB + lengthB;

                if ((startA <= startB && endB <= endA)
                        || (startB <= startA && endA <= endB)) {
                    jPenalty += 0;
                } else {
                    jPenalty += penalty;
                }
            }

        }
        return jPenalty;
    }

    //3.DIFFERENT TIME                                                          //(Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start)
    int DifferentTime(ArrayList<Integer> listDT, int penalty) {
        int startA, startB;
        int lengthA, lengthB;
        int endA, endB;
        int TSA, TSB;
        int jPenalty = 0;

        for (int i = 0; i < listDT.size(); i++) {
            Kelas klsA = SearchKls(listDT.get(i));
            TSA = klsA.getTs();
            startA = klsA.getAvailableTS().get(TSA).getStart();
            lengthA = klsA.getAvailableTS().get(TSA).getLength();
            endA = startA + lengthA;

            for (int j = i + 1; j < listDT.size(); j++) {
                Kelas klsB = SearchKls(listDT.get(j));
                TSB = klsB.getTs();
                startB = klsB.getAvailableTS().get(TSB).getStart();
                lengthB = klsB.getAvailableTS().get(TSB).getLength();
                endB = startB + lengthB;

                if (endA <= startB || endB <= startA) {
                    jPenalty += 0;
                } else {
                    jPenalty += penalty;
                }
            }
        }
        return jPenalty;
    }

    //4.SAME DAYS                                                               //((Ci.days or Cj.days) = Ci.days) ∨ ((Ci.days or Cj.days) = Cj.days)
    int SameDays(ArrayList<Integer> listSD, int penalty) {
        int TSA, TSB;
        int jPenalty = 0;
        int salah = 0;

        for (int i = 0; i < listSD.size(); i++) {
            Kelas klsA = SearchKls(listSD.get(i));
            TSA = klsA.getTs();
            daysA = klsA.getAvailableTS().get(TSA).getDays();
            for (int j = i + 1; j < listSD.size(); j++) {
                Kelas klsB = SearchKls(listSD.get(j));
                TSB = klsB.getTs();
                daysB = klsB.getAvailableTS().get(TSB).getDays();
                for (int dy = 0; dy < daysA.size(); dy++) {
                    if (daysA.get(dy) == 1 && daysB.get(dy) == 1) {
                        jPenalty += 0;
                        break;
                    } else {
                        salah += 1;
                    }

                    //CEK APAKAH SEMUA HARI SALAH
                    if (salah == daysA.size()) {
                        jPenalty += penalty;
                    }
                }
            }
        }
        return jPenalty;
    }

    //5.DIFFERENT DAYS                                                          //(Ci.days and Cj.days) = 0
    int DifferentDays(ArrayList<Integer> listDD, int penalty) {
        int TSA, TSB;
        int jPenalty = 0;

        for (int i = 0; i < listDD.size(); i++) {
            Kelas klsA = SearchKls(listDD.get(i));
            TSA = klsA.getTs();
            daysA = klsA.getAvailableTS().get(TSA).getDays();
            for (int j = i + 1; j < listDD.size(); j++) {
                Kelas klsB = SearchKls(listDD.get(j));
                TSB = klsB.getTs();
                daysB = klsB.getAvailableTS().get(TSB).getDays();
                for (int dy = 0; dy < daysA.size(); dy++) {
                    if ((daysA.get(dy) == 0 && daysB.get(dy) == 0)
                            || (daysA.get(dy) == 1 && daysB.get(dy) == 0)
                            || (daysA.get(dy) == 0 && daysB.get(dy) == 1)) {
                        jPenalty += 0;
                    } else {
                        jPenalty += penalty;
                        break;
                    }
                }
            }
        }
        return jPenalty;
    }

    //6.SAME WEEKS                                                              //(Ci.weeks or Cj.weeks) = Ci.weeks) ∨ (Ci.weeks or Cj.weeks) = Cj.weeks)
    int SameWeeks(int TS, Kelas kls, ArrayList<Integer> listSW, int penalty) {
        int TSA, TSB;
        int jPenalty = 0;
        int salah = 0;

        for (int i = 0; i < listSW.size(); i++) {
            Kelas klsA = SearchKls(listSW.get(i));
            TSA = klsA.getTs();
            weeksA = klsA.getAvailableTS().get(TSA).getWeeks();
            for (int j = i + 1; j < listSW.size(); j++) {
                Kelas klsB = SearchKls(listSW.get(j));
                TSB = klsB.getTs();
                weeksA = klsB.getAvailableTS().get(TSB).getWeeks();
                for (int wk = 0; wk < weeksA.size(); wk++) {
                    weeksB = klsB.getAvailableTS().get(TSB).getWeeks();
                    if (weeksA.get(wk) == 1 && weeksB.get(wk) == 1) {
                        jPenalty += 0;
                        break;
                    } else {
                        salah += 1;
                    }

                    //CEK APAKAH SEMUA MINGGU SALAH
                    if (salah == weeksA.size()) {
                        jPenalty += penalty;
                    }
                }
            }
        }
        return jPenalty;
    }

    //7.DIFFERENT WEEKS                                                         //(Ci.weeks and Cj.weeks) = 0
    int DifferentWeeks(ArrayList<Integer> listDW, int penalty) {
        int TSA, TSB;
        int jPenalty = 0;

        for (int i = 0; i < listDW.size(); i++) {
            Kelas klsA = SearchKls(listDW.get(i));
            TSA = klsA.getTs();
            weeksA = klsA.getAvailableTS().get(TSA).getWeeks();

            for (int j = i + 1; j < listDW.size(); j++) {
                Kelas klsB = SearchKls(listDW.get(j));
                TSB = klsB.getTs();
                weeksB = klsB.getAvailableTS().get(TSB).getWeeks();

                for (int wk = 0; wk < weeksA.size(); wk++) {
                    weeksB = klsB.getAvailableTS().get(TSB).getWeeks();
                    if ((weeksA.get(wk) == 0 && weeksB.get(wk) == 0)
                            || (weeksA.get(wk) == 1 && weeksB.get(wk) == 0)
                            || (weeksA.get(wk) == 0 && weeksB.get(wk) == 1)) {
                        jPenalty += 0;
                    } else {
                        jPenalty += penalty;
                        break;
                    }
                }
            }
        }
        return jPenalty;
    }

    //8.SAME ROOM                                                               //Ci.room = Cj.room
    int SameRoom(int Rom, Kelas kls, ArrayList<Integer> listSR, int penalty) {
        int roomA = kls.getAvailableroom().get(Rom).getRoom_id();
        int idKLSA = kls.getClassID();
        int jPenalty = 0;

        for (int i = 0; i < listSR.size(); i++) {
            Kelas klsB = SearchKls(listSR.get(i));
            int idKLSB = klsB.getClassID();
            if (idKLSA != idKLSB) {
                int iRM = klsB.getRoom();
                if (iRM != -1) {
                    int roomB = klsB.getAvailableroom().get(iRM).getRoom_id();
                    if (roomA == roomB) {
                        return jPenalty;
                    } else {
                        jPenalty += penalty;
                    }
                } else if (iRM == -1) {
                    return jPenalty;
                }
            } else {
                return jPenalty;
            }
        }
        return jPenalty;
    }

    //9.DIFFERENT ROOM                                                          //Ci.room ≠ Cj.room
    int DifferentRoom(int Rom, Kelas kls, ArrayList<Integer> listDR, int penalty) {
        int roomA = kls.getAvailableroom().get(Rom).getRoom_id();
        int jPenalty = 0;

        for (int i = 0; i < listDR.size(); i++) {
            Kelas klsB = SearchKls(listDR.get(i));
            int iRM = klsB.getTs();
            if (iRM != -1) {
                int roomB = klsB.getAvailableroom().get(iRM).getRoom_id();
                if (roomA != roomB) {
                    return jPenalty;
                } else {
                    jPenalty += penalty;
                }
            } else if (iRM == -1) {
                return jPenalty;
            }
        }
        return jPenalty;
    }

    //10.OVERLAP                                                                //(Cj.start < Ci.end) ∧ (Ci.start < Cj.end) ∧ ((Ci.days and Cj.days) ≠ 0) ∧ ((Ci.weeks and Cj.weeks) ≠ 0)
    int Overlap(int TS, Kelas kls, ArrayList<Integer> listOv, int penalty) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        weeksA = kls.getAvailableTS().get(TS).getWeeks();
        daysA = kls.getAvailableTS().get(TS).getDays();
        int jPenalty = 0;
        int benar = 0;

        for (int i = 0; i < listOv.size(); i++) {
            Kelas klsB = SearchKls(listOv.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                daysB = klsB.getAvailableTS().get(iTS).getDays();

                if ((startB < endA) && (startA < endB)) {
                    outerloop:
                    for (int wk = 0; wk < weeksA.size(); wk++) {
                        if (weeksA.get(wk) == 1 && weeksB.get(wk) == 1) {
                            for (int dy = 0; dy < daysA.size(); dy++) {
                                if (daysA.get(dy) == 1 && daysB.get(dy) == 1) {
                                    benar++;
                                    break outerloop;
                                } else {
                                    jPenalty += penalty;
                                }
                            }
                        }
                    }
                } else {                //MASIH BINGUNG DIKASIH ELSE/ENGGAK
                    return jPenalty;
                }
            } else if (iTS == -1) {
                return jPenalty;
            }
        }
        return jPenalty;
    }

    //11.NOT OVERLAP                                                            //(Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0)
    int NotOverlap(int TS, Kelas kls, ArrayList<Integer> listNotOv, int penalty) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        weeksA = kls.getAvailableTS().get(TS).getWeeks();
        daysA = kls.getAvailableTS().get(TS).getDays();
        int jPenalty = 0;

        for (int i = 0; i < listNotOv.size(); i++) {
            Kelas klsB = SearchKls(listNotOv.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                daysB = klsB.getAvailableTS().get(iTS).getDays();

                for (int wk = 0; wk < weeksA.size(); wk++) {
                    if (weeksA.get(wk) == 1 && weeksB.get(wk) == 1) {
                        for (int dy = 0; dy < daysA.size(); dy++) {
                            if (daysA.get(dy) == 1 && daysB.get(dy) == 1) {
                                if ((endA <= startB) || (endB <= startA)) {
                                    return jPenalty;
                                } else {
                                    jPenalty += penalty;
                                }
                            }
                        }
                    }
                }
            } else if (iTS == -1) {
                return jPenalty;
            }
        }
        return jPenalty;
    }

    //12.SAME ATTENDEES                                                         //(Ci.end + Ci.room.travel[Cj.room] ≤ Cj.start) ∨ (Cj.end + Cj.room.travel[Ci.room] ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) Ci.room.travel[Cj.room] is the travel time between the assigned rooms of Ci and Cj
    int SameAttendees(int TS, int Rom, Kelas kls, int[][] valueTravel,
            ArrayList<Integer> listSA, int penalty) {
        int trvl = 0;
        int roomA = 0;
        boolean noRoomA = false;
        boolean noRoomB = false;
        int idKLSA = kls.getClassID();
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        weeksA = kls.getAvailableTS().get(TS).getWeeks();
        daysA = kls.getAvailableTS().get(TS).getDays();
        int jPenalty = 0;
        int benar = 0;

        if (Rom < 0) {
            noRoomA = true;
        } else {
            roomA = kls.getAvailableroom().get(Rom).getRoom_id() - 1;
        }

        for (int i = 0; i < listSA.size(); i++) {
            Kelas klsB = SearchKls(listSA.get(i));
            int idKLSB = klsB.getClassID();
            int iTS = klsB.getTs();
            int iRM = klsB.getRoom();
            if (idKLSA != idKLSB) {
                if (klsB.isHasRoom() == false) {
                    iRM = 999;
                }
                if (iTS != -1 && iRM != -1) {
                    int startB = klsB.getAvailableTS().get(iTS).getStart();
                    int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                    int endB = startB + lengthB;
                    weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                    daysB = klsB.getAvailableTS().get(iTS).getDays();
                    int roomB = 0;

                    if (iRM == 999) {
                        noRoomB = true;
                    } else {
                        roomB = klsB.getAvailableroom().get(iRM).getRoom_id() - 1;
                    }

                    if (noRoomA == false && noRoomB == false) {
                        trvl = valueTravel[roomA][roomB];
                    }

                    outerloop:
                    for (int wk = 0; wk < weeksA.size(); wk++) {
                        if (weeksA.get(wk) == 1 && weeksB.get(wk) == 1) {
                            for (int dy = 0; dy < daysA.size(); dy++) {
                                if (daysA.get(dy) == 1 && daysB.get(dy) == 1) {
                                    if ((endA + trvl) <= startB || (endB + trvl) <= startA) {
                                        benar++;
                                        break outerloop;
                                    } else {
                                        jPenalty += penalty;
                                    }
                                }
                            }
                        }
                    }
                } else if (iTS == -1) {
                    return jPenalty;
                }
            } else if (idKLSA == idKLSB) {    //CEK BENER NGGAK
                return jPenalty;
            }
        }
        return jPenalty;
    }

    //13.PRECEDENCE                                                             //(first(Ci.weeks) < first(Cj.weeks)) ∨ [(first(Ci.weeks) = first(Cj.weeks)) ∧ [(first(Ci .days) < first(Cj .days)) ∨ ((first(Ci.days) = first(Cj.days)) ∧ (Ci.end ≤ Cj.start))]] for any two classes Ci and Cj from the constraint where i < j and first(x) is the index of the first non-zero bit in the binary string x.
    int Precedence(int TS, Kelas kls, ArrayList<Integer> listP, int penalty) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        weeksA = kls.getAvailableTS().get(TS).getWeeks();
        daysA = kls.getAvailableTS().get(TS).getDays();
        int iKlsA = listP.indexOf(kls.getClassID());
        int jPenalty = 0;
        int benar = 0;

        for (int i = 0; i < listP.size(); i++) {
            if (i < iKlsA) {
                Kelas klsB = SearchKls(listP.get(i));
                int iTS = klsB.getTs();
                if (iTS != -1) {
                    int startB = klsB.getAvailableTS().get(iTS).getStart();
                    int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                    int endB = startB + lengthB;
                    weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                    daysB = klsB.getAvailableTS().get(iTS).getDays();

                    outerloop:
                    for (int wk = 0; wk < weeksA.size(); wk++) {
                        if (weeksA.get(wk) == 1 && weeksB.get(wk) == 1) {
                            for (int dy = 0; dy < daysA.size(); dy++) {
                                if (daysA.get(dy) == 1 && daysB.get(dy) == 1) {
                                    if (endB <= startA) {
                                        benar++;
                                        break outerloop;
                                    } else {
                                        jPenalty += penalty;
                                    }
                                } else if (daysA.get(dy) < daysB.get(dy)) {
                                    benar++;
                                    break outerloop;
                                } else if (daysA.get(dy) > daysB.get(dy)) {
                                    jPenalty += penalty;
                                }
                            }
                        } else if (weeksA.get(wk) < weeksB.get(wk)) {
                            benar++;
                            break;
                        } else if (weeksA.get(wk) > weeksB.get(wk)) {
                            jPenalty += penalty;
                        }
                    }
                } else if (iTS == -1) {
                    return jPenalty;
                }
            } else if (iKlsA < i) {
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
                        if (weeksA.get(wk) == 1 && weeksB.get(wk) == 1) {
                            for (int dy = 0; dy < daysA.size(); dy++) {
                                if (daysA.get(dy) == 1 && daysB.get(dy) == 1) {
                                    if (endA <= startB) {
                                        benar++;
                                        break outerloop;
                                    } else {
                                        jPenalty += penalty;
                                    }
                                } else if (daysA.get(dy) > daysB.get(dy)) {
                                    benar++;
                                    break outerloop;
                                } else if (daysA.get(dy) < daysB.get(dy)) {
                                    jPenalty += penalty;
                                }
                            }
                        } else if (weeksA.get(wk) > weeksB.get(wk)) {
                            benar++;
                            break;
                        } else if (weeksA.get(wk) < weeksB.get(wk)) {
                            jPenalty += penalty;
                        }
                    }
                } else if (iTS == -1) {
                    return jPenalty;
                }
            }
        }
        return jPenalty;
    }

    //14.WORKDAY(S)                                                             //((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (max(Ci.end,Cj.end)−min(Ci.start,Cj.start) ≤ S)
    int Workday(int TS, int S, Kelas kls, ArrayList<Integer> listWD, int penalty) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        weeksA = kls.getAvailableTS().get(TS).getWeeks();
        daysA = kls.getAvailableTS().get(TS).getDays();
        int jPenalty = 0;
        int benar = 0;

        for (int i = 0; i < listWD.size(); i++) {
            Kelas klsB = SearchKls(listWD.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                daysB = klsB.getAvailableTS().get(iTS).getDays();

                outerloop:
                for (int wk = 0; wk < weeksA.size(); wk++) {
                    if (weeksA.get(wk) == 1 && weeksB.get(wk) == 1) {
                        for (int dy = 0; dy < daysA.size(); dy++) {
                            if (daysA.get(dy) == 1 && daysB.get(dy) == 1) {
                                if ((Math.max(endA, endB)) - (Math.min(startA,
                                        startB)) <= S) {
                                    benar++;
                                    break outerloop;
                                } else {
                                    jPenalty += penalty;
                                }
                            }
                        }
                    }
                }
            } else if (iTS == -1) {
                return jPenalty;
            }
        }
        return jPenalty;
    }

    //15.MINGAP(G)                                                              //((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (Ci.end + G ≤ Cj.start) ∨ (Cj.end + G ≤ Ci.start)
    int Mingap(int TS, int G, Kelas kls, ArrayList<Integer> listMG, int penalty) {
        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        weeksA = kls.getAvailableTS().get(TS).getWeeks();
        daysA = kls.getAvailableTS().get(TS).getDays();
        int jPenalty = 0;
        int benar = 0;

        for (int i = 0; i < listMG.size(); i++) {
            Kelas klsB = SearchKls(listMG.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                weeksB = klsB.getAvailableTS().get(iTS).getWeeks();
                daysB = klsB.getAvailableTS().get(iTS).getDays();

                outerloop:
                for (int k = 0; k < weeksA.size(); k++) {
                    if (weeksA.get(k) == 1 && weeksB.get(k) == 1) {
                        for (int l = 0; l < daysA.size(); l++) {
                            if (daysA.get(l) == 1 && daysB.get(l) == 1) {
                                if ((endA + G <= startB) || (endB + G <= startA)) {
                                    benar++;
                                    break outerloop;
                                } else {
                                    jPenalty += penalty;
                                }
                            } else {
                                jPenalty += penalty;
                            }
                        }
                    } else {
                        jPenalty += penalty;
                    }
                }
            } else if (iTS == -1) {
                return jPenalty;
            }
        }
        return jPenalty;
    }

    //16.MAXDAYS(D)                                                             //countNonzeroBits(C1.days or C2.days or ⋅ ⋅ ⋅ Cn.days) ≤ D
    int Maxdays(int TS, int D, Kelas kls, int penalty) {
        daysA = kls.getAvailableTS().get(TS).getDays();
        int jPenalty = 0;
        int lebih = 0;

        if (countNonzeroBits(daysA) <= D) {
            return jPenalty;
        } else {
            lebih = (countNonzeroBits(daysA) - D);
            jPenalty += (lebih * penalty);
        }
        return jPenalty;
    }

    //17.MAXDAYLOAD(S)                                                          //DayLoad(d,w) ≤ S >>> DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
    int MaxdayLoad(int TS, int S, Kelas kls, ArrayList<Integer> listMDL,
            int penalty) {
        int length = kls.getAvailableTS().get(TS).getLength();
        weeksA = kls.getAvailableTS().get(TS).getWeeks();
        daysA = kls.getAvailableTS().get(TS).getDays();
        int jPenalty = 0;

        if (DayLoad(S, length, daysA, weeksA) == 0) {
            return jPenalty;
        } else {
            jPenalty = (penalty * (DayLoad(S, length, daysA, weeksA)) / weeksA.size());
        }
        return jPenalty;                                                        //PENALTY : (penalty × ∑w,d max(DayLoad(d,w) − S, 0)) / nrWeeks
    }

    //18.MAXBREAKS(R,S)                                                         //|MergeBlocks{(C.start, C.end) | (C.days and 2d) ≠ 0 ∧ (C.weeks and 2w) ≠ 0})| ≤ R + 1 >> (Ba.end + S ≥ Bb.start) ∧ (Bb.end + S ≥ Ba.start) ⇒ (B.start = min(Ba.start, Bb.start)) ∧ (B.end = max(Ba.end, Bb.end))
    int Maxbreaks(int TS, int R, int S, Kelas kls, ArrayList<Integer> listMB,
            int penalty) {
        int jPenalty = 0;

        if (MergeBlocks(TS, S, kls, listMB) <= (R + 1)) {
            return jPenalty;
        } else {
            jPenalty = (penalty * (MergeBlocks(TS, S, kls, listMB) - R)) / weeksA.size();
        }
        return jPenalty;
    }

    //19.MAXBLOCK(M,S)                                                          //max {B.end − B.start | B ∈ MergeBlocks{(C.start, C.end) | (C.days and 2d) ≠ 0 ∧ (C.weeks and 2w) ≠ 0}} ≤ M
    int Maxblock(int TS, int M, int S, Kelas kls, ArrayList<Integer> listMBL,
            int penalty) {
        int jPenalty = 0;

        if (MergeBlockB(TS, M, S, kls, listMBL) <= M) {
            return jPenalty;
        } else {
            jPenalty = (penalty * MergeBlockB(TS, M, S, kls, listMBL)) / weeksA.size();
        }
        return jPenalty;
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
            ArrayList<Integer> weeks) {                                                       //DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
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

        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        daysA = kls.getAvailableTS().get(TS).getDays();
        weeksA = kls.getAvailableTS().get(TS).getWeeks();

        for (int i = 1; i < listMB.size(); i++) {
            Kelas klsB = SearchKls(listMB.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                daysB = klsB.getAvailableTS().get(iTS).getDays();
                weeksB = klsB.getAvailableTS().get(iTS).getWeeks();

                outerloop:
                for (int wk = 0; wk < weeksA.size(); wk++) {
                    for (int dy = 0; dy < daysA.size(); dy++) {
                        if (weeksA.get(wk) == 1 && daysA.get(dy) == 1
                                && weeksB.get(wk) == 1 && daysB.get(dy) == 1) {
                            if (endA + S >= startB && endB + S >= startA) {
                                startA = Math.min(startA, startB);
                                endA = Math.max(endA, endB);
                            } else {
                                initialBlock++;
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
                return initialBlock;
            }
        }
        return initialBlock;
    }

    private int MergeBlockB(int TS, int M, int S, Kelas kls,
            ArrayList<Integer> listMBL) {
        int lengthBlock = 0;
        int lebih = 0;
        boolean blockBaru = true;

        int startA = kls.getAvailableTS().get(TS).getStart();
        int lengthA = kls.getAvailableTS().get(TS).getLength();
        int endA = startA + lengthA;
        daysA = kls.getAvailableTS().get(TS).getDays();
        weeksA = kls.getAvailableTS().get(TS).getWeeks();

        for (int i = 1; i < listMBL.size(); i++) {
            Kelas klsB = SearchKls(listMBL.get(i));
            int iTS = klsB.getTs();
            if (iTS != -1) {
                int startB = klsB.getAvailableTS().get(iTS).getStart();
                int lengthB = klsB.getAvailableTS().get(iTS).getLength();
                int endB = startB + lengthB;
                daysB = klsB.getAvailableTS().get(iTS).getDays();
                weeksB = klsB.getAvailableTS().get(iTS).getWeeks();

                outerloop:
                for (int wk = 0; wk < weeksA.size(); wk++) {
                    for (int dy = 0; dy < daysA.size(); dy++) {
                        if (weeksA.get(wk) == 1 && daysA.get(dy) == 1
                                && weeksB.get(wk) == 1 && daysB.get(dy) == 1) {
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
