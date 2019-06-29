package domoop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class TSA {

    ArrayList<Integer> weeks, days;
    int X, start, length, idRoom, delta;
    int penaltyRM, penaltyTS, penaltySC;
    int currentPenalty;
    int newPenalty;
    boolean hardC;
    Random rand = new Random();
    CekHC hard;
    CekSC soft;
    ArrayList<Minggu> timeslot;
    ArrayList<Kelas> listKelas;

    TSA(int[][] travel, int initPen, int[] bobot, Distributions distrib, ArrayList<Kelas> list) {
        this.listKelas = list;
        X = 1000000;
        LinkedList<int[]> tabu = new LinkedList<>();
        hard = new CekHC();
        soft = new CekSC();
        double temp = 1000000000;
        double alpha = 0.999;
        int iterasi = 0;
        currentPenalty = initPen;

        do {
            int random = rand.nextInt(3);
//            int random = 1;
            if (random == 0) {
                move1TS();
            }
            if (random == 1) {
                move2TS();
            }
            if (random == 2) {
                move3TS();
            }
            for (int i = 0; i < listKelas.size(); i++) {
                Kelas kelas = listKelas.get(i);
                int TS = kelas.getTs();
                int RM = kelas.getRoom();
                hardC = hard.HardConstraint(TS, RM, kelas, listKelas, travel);
                if (hardC) {
                    hardC = true;
                } else {
                    hardC = false;
                    break;
                }
            }
            if (hardC == true) {
                penaltyTS = countTSPenalty(listKelas) * bobot[0];
                penaltyRM = countRMPenalty(listKelas) * bobot[1];
                penaltySC = soft.totalPenalty(listKelas, travel, distrib) * bobot[2];
                newPenalty = penaltyTS + penaltyRM + penaltySC;
            } else {
                System.out.println("HARD CONSTRAINT SALAH COOYYY!");
                newPenalty = X;
            }

            delta = currentPenalty - newPenalty;
            System.out.println("--------HITUNG PENALTY--------");
            System.out.println("Current Penalty: " + currentPenalty);
            System.out.println("New Penalty: \nTS: " + penaltyTS + " RM: " + penaltyRM + " SC: " + penaltySC);
            System.out.println("------------------------------");
            if (delta > 0 && newPenalty != X) {
                currentPenalty = newPenalty;
                for (int i = 0; i < listKelas.size(); i++) {
                    Kelas kelas = listKelas.get(i);
                    kelas.setTsBaru(kelas.getTs());
                }
            } else {
                for (int i = 0; i < listKelas.size(); i++) {
                    Kelas kelas = listKelas.get(i);
                    kelas.setTs(kelas.getTsBaru());
                }
            }
            temp *= alpha;
            iterasi++;
        } while (temp > 0.1);
        System.out.println("Jumlah iterasi: " + iterasi);
    }

    void move1TS() {
        int kls, ts, tsBaru = 0, rm, rmBaru, idKls;
        ArrayList<TimeAss> times;
        ArrayList<RoomAss> rooms;

        do {
            //AMBIL KELAS
            kls = rand.nextInt(listKelas.size() - 1);
            Kelas kelas = listKelas.get(kls);
            int id = kelas.getClassID();
            System.out.println("");
            System.out.println("----------START MOVE 1 TS----------");
            System.out.println("ID random kelas terpilih: " + id);

            times = kelas.getAvailableTS();
            ts = kelas.getTs();
            System.out.println("Index TS lama: " + ts);
            if ((ts + 1) < times.size()) {
                tsBaru = ts + 1;
                kelas.setTs(tsBaru);
                System.out.println("Index TS baru: " + tsBaru);
            } else if (ts + 1 >= times.size()) {
                tsBaru = 0;
                kelas.setTs(tsBaru);
            }
        } while (ts == tsBaru);
    }

    void move2TS() {
        int kls, klsB, ts, tsB, rm, rmBaru, idKls;
        int tsBaru = 0, tsBaruB = 0;
        ArrayList<TimeAss> times, timesB;

        do {
            //AMBIL KELAS
            kls = rand.nextInt(listKelas.size() - 1);
            klsB = rand.nextInt(listKelas.size() - 1);
            Kelas kelas = listKelas.get(kls);
            Kelas kelasB = listKelas.get(klsB);
            int id = kelas.getClassID();
            int idB = kelasB.getClassID();
            System.out.println("");
            System.out.println("----------START MOVE 2 TS----------");
            System.out.println("ID random kelas terpilih: " + id + " dan " + idB);

            times = kelas.getAvailableTS();
            timesB = kelasB.getAvailableTS();
            ts = kelas.getTs();
            tsB = kelasB.getTs();
            System.out.println("Index TS lama: " + ts + " dan " + tsB);
            if ((ts + 1) < times.size()) {
                tsBaru = ts + 1;
                kelas.setTs(tsBaru);
                System.out.println("Index TS baru: " + tsBaru);
            } else if (ts + 1 >= times.size()) {
                tsBaru = 0;
                kelas.setTs(tsBaru);
            }
            if ((tsB + 1) < timesB.size()) {
                tsBaruB = tsB + 1;
                kelasB.setTs(tsBaruB);
                System.out.println("Index TS baru: " + tsBaruB);
            } else if (tsB + 1 >= timesB.size()) {
                tsBaruB = 0;
                kelasB.setTs(tsBaruB);
            }
        } while (ts == tsBaru && tsB == tsBaruB);
    }

    void move3TS() {
        int kls, klsB, klsC, ts, tsB, tsC, rm, rmBaru, idKls;
        int tsBaru = 0, tsBaruB = 0, tsBaruC = 0;
        ArrayList<TimeAss> times, timesB, timesC;

        do {
            //AMBIL KELAS
            kls = rand.nextInt(listKelas.size() - 1);
            klsB = rand.nextInt(listKelas.size() - 1);
            klsC = rand.nextInt(listKelas.size() - 1);
            Kelas kelas = listKelas.get(kls);
            Kelas kelasB = listKelas.get(klsB);
            Kelas kelasC = listKelas.get(klsC);
            int id = kelas.getClassID();
            int idB = kelasB.getClassID();
            int idC = kelasC.getClassID();
            System.out.println("");
            System.out.println("----------START MOVE 3 TS----------");
            System.out.println("ID random kelas terpilih: " + id + " dan " + idB + " dan " + idC);

            times = kelas.getAvailableTS();
            timesB = kelasB.getAvailableTS();
            timesC = kelasC.getAvailableTS();
            ts = kelas.getTs();
            tsB = kelasB.getTs();
            tsC = kelasC.getTs();
            System.out.println("Index TS lama: " + ts + " dan " + tsB + " dan " + tsC);
            if ((ts + 1) < times.size()) {
                tsBaru = ts + 1;
                kelas.setTs(tsBaru);
                System.out.println("Index TS baru: " + tsBaru);
            } else if (ts + 1 >= times.size()) {
                tsBaru = 0;
                kelas.setTs(tsBaru);
            }
            if ((tsB + 1) < timesB.size()) {
                tsBaruB = tsB + 1;
                kelasB.setTs(tsBaruB);
                System.out.println("Index TS baru: " + tsBaruB);
            } else if (tsC + 1 >= timesB.size()) {
                tsBaruB = 0;
                kelasB.setTs(tsBaruB);
            }
            if ((tsC + 1) < timesC.size()) {
                tsBaruC = tsC + 1;
                kelasC.setTs(tsBaruC);
                System.out.println("Index TS baru: " + tsBaruC);
            } else if (tsC + 1 >= timesC.size()) {
                tsBaruC = 0;
                kelasC.setTs(tsBaruC);
            }
        } while (ts == tsBaru && tsB == tsBaruB && tsC == tsBaruC);
    }

    void assign(int idKls, TimeAss time, RoomAss room) {
        weeks = time.getWeeks();
        days = time.getDays();
        start = time.getStart();
        length = time.getLength();
        idRoom = room.getRoom_id();

        for (int wk = 0; wk < weeks.size(); wk++) {
            if (weeks.get(wk) == 1) {
                for (int day = 0; day < days.size(); day++) {
                    if (days.get(day) == 1) {
                        for (int tm = start; tm <= (start + length); tm++) {
                            timeslot.get(wk).listHari.get(day).timeslot[tm][idRoom - 1] = idKls;
                        }
                    }
                }
            }
        }
    }

    boolean cekTS(TimeAss time, RoomAss room) {
        weeks = time.getWeeks();
        days = time.getDays();
        start = time.getStart();
        length = time.getLength();
        idRoom = room.getRoom_id();

        for (int wk = 0; wk < weeks.size(); wk++) {
            if (weeks.get(wk) == 1) {
                for (int day = 0; day < days.size(); day++) {
                    if (days.get(day) == 1) {
                        for (int tm = start; tm <= (start + length); tm++) {
                            if (timeslot.get(wk).listHari.get(day).timeslot[tm][idRoom - 1] != 0) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    void removeKls(TimeAss time, RoomAss room) {
        weeks = time.getWeeks();
        days = time.getDays();
        start = time.getStart();
        length = time.getLength();
        idRoom = room.getRoom_id();

        for (int wk = 0; wk < weeks.size(); wk++) {
            if (weeks.get(wk) == 1) {
                for (int day = 0; day < days.size(); day++) {
                    if (days.get(day) == 1) {
                        for (int tm = start; tm <= (start + length); tm++) {
                            timeslot.get(wk).listHari.get(day).timeslot[tm][idRoom - 1] = 0;
                        }
                    }
                }
            }
        }
    }

    public static int countTSPenalty(ArrayList<Kelas> listKelas) {
        int totalPenaltiTS = 0;
        for (int i = 0; i < listKelas.size(); i++) {
            Kelas kls = listKelas.get(i);
            int timeP = penaltyTS(kls);
            totalPenaltiTS += timeP;
        }
        return totalPenaltiTS;
    }

    public static int countRMPenalty(ArrayList<Kelas> listKelas) {
        int totalPenaltiRM = 0;
        for (int i = 0; i < listKelas.size(); i++) {
            Kelas kls = listKelas.get(i);
            int roomP = penaltyRM(kls);
            totalPenaltiRM += roomP;
        }
        return totalPenaltiRM;
    }

    static int penaltyTS(Kelas a) {
        int penalty = 0;
        int ts = a.getTs();
        if (ts != -1) {
            TimeAss timeA = a.getAvailableTS().get(ts);
            penalty = timeA.getPenalty();
        }
        return penalty;
    }

    static int penaltyRM(Kelas a) {
        int penalty = 0;
        int rm = a.getRoom();
        if (rm != -1) {
            RoomAss roomA = a.getAvailableroom().get(rm);
            penalty = roomA.getRoom_pen();
        }
        return penalty;
    }

}
