package domoop;

import java.util.ArrayList;
import java.util.Random;

public final class InitSol {

    ArrayList<Integer> weeks, days, sol;
    ArrayList<Kelas> listKelas;
    ArrayList<Minggu> timeslot;
    ArrayList<Constraint> cons;

    int[][][][] unRoom;
    int start, length, penalty_ts, idRoom, pen_rom;
    int rmIndex = 0;

    InitSol(ArrayList<Kelas> listKls, ArrayList<Minggu> timeslt, int[][][][] unR, int[][] travel) {
        this.listKelas = listKls;
        this.timeslot = timeslt;
        this.unRoom = unR;
        CekHC cekHC = new CekHC();

        for (int kls = 0; kls < listKelas.size(); kls++) {
            Kelas kelas = listKelas.get(kls);
            int idKelas = kelas.getClassID();
            ArrayList<TimeAss> availableTS = kelas.getAvailableTS();
            ArrayList<RoomAss> availableroom = kelas.getAvailableroom();

            if (kelas.isHasRoom() == false) {
                rmIndex = 999;
                Random rand = new Random();

                outerloop:
                for (int ts = 0; ts < availableTS.size(); ts++) {
                    int rndm1 = rand.nextInt(availableTS.size());
                    TimeAss Tims = availableTS.get(rndm1);
                    boolean cek = cekTSwR(Tims);
                    boolean hardC = cekHC.HardConstraint(rndm1, rmIndex, kelas, listKls, travel);
                    if (hardC) {
                        kelas.setTs(rndm1);
                        assignNoRom(idKelas, Tims);
                        break outerloop;
                    }
                }
            }

            Random rnd = new Random();
            outerloop:
            for (int rm = 0; rm < availableroom.size(); rm++) {
                int rndmR = rnd.nextInt(availableroom.size());
                RoomAss roms = availableroom.get(rndmR);

                Random rand = new Random();
                for (int ts = 0; ts < availableTS.size(); ts++) {
                    int rndm2 = rand.nextInt(availableTS.size());
                    TimeAss Tims = availableTS.get(rndm2);

                    boolean cek = cekTS(Tims, roms);
                    boolean cekUnv = cekUnvroom(Tims, roms);
                    boolean hardC = cekHC.HardConstraint(rndm2, rndmR, kelas, listKls, travel);

                    if (cek && cekUnv && hardC) {
                        kelas.setTs(rndm2);
                        kelas.setRoom(rndmR);
                        assign(idKelas, Tims, roms);
                        break outerloop;
                    }
                }
            }
        }
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

    void assignNoRom(int idKls, TimeAss time) {
        weeks = time.getWeeks();
        days = time.getDays();
        start = time.getStart();
        length = time.getLength();
        for (int wk = 0; wk < weeks.size(); wk++) {
            if (weeks.get(wk) == 1) {
                for (int day = 0; day < days.size(); day++) {
                    if (days.get(day) == 1) {
                        for (int tm = start; tm <= (start + length); tm++) {
                            timeslot.get(wk).listHari.get(day).timeNoRom[tm] = idKls;
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
                            if (timeslot.get(wk).getListHari().get(day).timeslot[tm][idRoom - 1] != 0) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    boolean cekUnvroom(TimeAss time, RoomAss room) {
        weeks = time.getWeeks();
        days = time.getDays();
        idRoom = room.getRoom_id();

        for (int wk = 0; wk < weeks.size(); wk++) {
            if (weeks.get(wk) == 1) {
                for (int dy = 0; dy < days.size(); dy++) {
                    if (days.get(dy) == 1) {
                        for (int tm = 0; tm <= 288; tm++) {
                            if ((unRoom[idRoom - 1][wk][dy][tm] == 1)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    boolean cekTSwR(TimeAss time) {
        weeks = time.getWeeks();
        days = time.getDays();
        start = time.getStart();
        length = time.getLength();

        for (int wk = 0; wk < weeks.size(); wk++) {
            if (weeks.get(wk) == 1) {
                for (int day = 0; day < days.size(); day++) {
                    if (days.get(day) == 1) {
                        for (int tm = start; tm <= (start + length); tm++) {
                            if (timeslot.get(wk).getListHari().get(day).timeNoRom[tm] != 0) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    void cekSol() {
        sol = new ArrayList<>();
        for (int i = 0; i < listKelas.size(); i++) {
            if (listKelas.get(i).getTs() == -1) {
                sol.add(i);
            }
        }
    }
}
