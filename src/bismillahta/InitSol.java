/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bismillahta;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Umar Rizki
 */
public final class InitSol {
    ArrayList<Integer> weeks, days;
    int start, length, penalty_ts,
            idRoom, pen_rom;
    int[][][][] unRoom;
    ArrayList<Kelas> listKelas;
    ArrayList<Minggu> timeslot;
    ArrayList<Integer> klsNoTS;
    
    InitSol (ArrayList<Kelas> listKls, ArrayList<Minggu> timeslt, int[][][][] unR, int[][] travel){
        this.listKelas = listKls;
        this.timeslot = timeslt;
        this.unRoom = unR;
        
        CekHC cekHC = new CekHC();
        for (int kls = 0; kls < listKelas.size(); kls++) {
            System.out.println("");
            System.out.println("------- URUTAN KE " + kls +" -------");
            Kelas kelas = listKelas.get(kls);
            int TS = kelas.getTs();
            int RM = kelas.getRoom();
            int idKelas = kelas.getClassID();
            System.out.println(idKelas);
            System.out.println("TS awal "+TS+", RM awal "+RM);
            ArrayList<TimeAss> availableTS = kelas.getAvailableTS();
            ArrayList<RoomAss> availableroom = kelas.getAvailableroom();
            
//            if(TS==-1){
                if (kelas.isHasRoom()==false) {
                    int rmI = kelas.getRoom();
                    for (int ts = 0; ts < availableTS.size(); ts++) {
//                        Random rand = new Random();
//                        int rndm = rand.nextInt(availableTS.size());
//                        TimeAss Tims = availableTS.get(rndm);
                        boolean hardC = cekHC.HardConstraint(ts, rmI, kelas, listKls, travel);
                        System.out.println(hardC);
                        if (hardC) {
                            kelas.setTs(ts);
                            TS=ts;
//                            System.out.println(TS);
    //                        assignNoRom(idKelas, Tims);
                            break;
                        }
                    }
                } else {
                    outerloop:
                    for (int ts = 0; ts < availableTS.size(); ts++) {
    //                    Random rand = new Random();
    //                    int rndm = rand.nextInt(availableTS.size());
                        TimeAss Tims = availableTS.get(ts);
//                        System.out.println("TS ke "+ts);
                        for (int rm = 0; rm < availableroom.size(); rm++) {
                            
                            RoomAss roms = availableroom.get(rm);
                            boolean cek = cekTS(Tims, roms);
                            if (!cek) {
                                System.out.println("RM ke "+rm);
                                System.out.println("TS=X");
                            }
                            boolean cekUnv = cekUnvroom(Tims, roms);
                            if (!cekUnv) {
                                System.out.println("RM ke "+rm);
                                System.out.println("Unvroom=X");
                                }
                            boolean hardC = cekHC.HardConstraint(ts, rm, kelas, listKls, travel);
                            if (!hardC) {
                                System.out.println("RM ke "+rm);
                                System.out.println("HC=X");
                                }
                            if (cek  && cekUnv && hardC) {
                                kelas.setTs(ts);
                                TS=ts;
                                System.out.println("TS "+ts+", RM "+rm);
                                kelas.setRoom(rm);
                                assign(idKelas, Tims, roms);
                                break outerloop;
                            }
                        }
                    }
                }
//                if (TS==-1) {
//                    System.out.println("Balik Nih");
//                    kls -= 2;
//                }
//            } else if (TS!=-1) {
//                int TS2=TS;
//                int RM2=RM, RM1=RM;
//                kelas.setTs(-1);
//                kelas.setRoom(-1);
////                TimeAss timeLama = availableTS.get(TS);
////                RoomAss roomLama = availableroom.get(RM);
////                removeKls(timeLama, roomLama);
//                if (TS+1==availableTS.size() && RM+1==availableroom.size()) {
//                    if (kelas.isHasRoom()) {
//                        System.out.println("Balik nih");
//                        kls -= 2;
//                        kelas.setTs(-1);
//                        kelas.setRoom(-1);
//                        TimeAss timeLama = availableTS.get(TS);
//                        RoomAss roomLama = availableroom.get(RM);
//                        removeKls(timeLama, roomLama);
//                    } else {
//                        System.out.println("Balik nih");
//                        kls -= 2;
//                        kelas.setTs(-1);
//                    }
//                } else {
//                    if (kelas.isHasRoom()==false) {
//                        int rmI = kelas.getRoom();
//                        for (int ts = TS+1; ts < availableTS.size(); ts++) {
//                            boolean hardC = cekHC.HardConstraint(ts, rmI, kelas, listKls, travel);
//                            if (hardC) {
//                                kelas.setTs(ts);
//                                TS2=ts;
//                                System.out.println("Ganti TS "+ts);
//                                break;
//                            }
//                        }
//                    } else {
////                        System.out.println("ini");
//                        TimeAss timeLama = availableTS.get(TS);
//                        RoomAss roomLama = availableroom.get(RM);
//                        removeKls(timeLama, roomLama);
//                        outerloop:
//                        for (int ts = TS; ts < availableTS.size(); ts++) {
//                            TimeAss Tims = availableTS.get(ts);
////                            System.out.println("anu "+ts);
//
//                            for (int rm = RM+1; rm < availableroom.size(); rm++) {
//                                RoomAss roms = availableroom.get(rm);
////                                System.out.println(rm);
//                                boolean cek = cekTS(Tims, roms);
//                                if (!cek) {
//                                    System.out.println("RM ke "+rm);
//                                    System.out.println("TS=X");
//                                }
//                                boolean cekUnv = cekUnvroom(Tims, roms);
//                                if (!cekUnv) {
//                                    System.out.println("RM ke "+rm);
//                                    System.out.println("Unvroom=X");
//                                    }
//                                boolean hardC = cekHC.HardConstraint(ts, rm, kelas, listKls, travel);
//                                if (!hardC) {
//                                    System.out.println("RM ke "+rm);
//                                    System.out.println("HC=X");
//                                    }
//                                if (cek  && cekUnv && hardC) {
//                                    kelas.setTs(ts);
//                                    TS2=ts;
//                                    System.out.println("Ganti TS "+ts);
//                                    System.out.println("Ganti ROM "+rm);
//                                    kelas.setRoom(rm);
//                                    RM2=rm;
//                                    assign(idKelas, Tims, roms);
//                                    break outerloop;
//                                }
//                            }
//                            RM = -1;
//                        }
//                    }
//                    if (TS==TS2 && RM2==RM1) {
//                        System.out.println("Balik Nih");
//                        kls -= 2;
//                        kelas.setTs(-1);
//                        if (kelas.isHasRoom()) {
//                            kelas.setRoom(-1);
//                        }
//                    }
//                }
//            }
//            if (kls==-2) {
//                kls=-1;
//            }
        }
//        countNoTS();
    }
    
    void countNoTS(){
        klsNoTS = new ArrayList<>();
        for (int i = 0; i < listKelas.size(); i++) {
            if (listKelas.get(i).getTs()==-1) {
                klsNoTS.add(i);
            }
        }
    }
    
    void assign(int idKls, TimeAss time, RoomAss room){
        weeks = time.getWeeks();
        days = time.getDays();
        start = time.getStart();
        length = time.getLength();
        idRoom = room.getRoom_id();
        
        for (int wk = 0; wk < weeks.size(); wk++) {
            if (weeks.get(wk)==1) {
                for (int day = 0; day < days.size(); day++) {
                    if (days.get(day)==1) {
                        for (int tm = start; tm <= (start+length); tm++) {
                            timeslot.get(wk).listHari.get(day).timeslot[tm][idRoom-1] = idKls;
                        }
                    }
                }
            }
        }
    }
    
    void removeKls( TimeAss time, RoomAss room){
        weeks = time.getWeeks();
        days = time.getDays();
        start = time.getStart();
        length = time.getLength();
        idRoom = room.getRoom_id();
        
        for (int wk = 0; wk < weeks.size(); wk++) {
            if (weeks.get(wk)==1) {
                for (int day = 0; day < days.size(); day++) {
                    if (days.get(day)==1) {
                        for (int tm = start; tm <= (start+length); tm++) {
                            timeslot.get(wk).listHari.get(day).timeslot[tm][idRoom-1] = 0;
                        }
                    }
                }
            }
        }
    }
    
    boolean cekTS(TimeAss time, RoomAss room){
        weeks = time.getWeeks();
        days = time.getDays();
        start = time.getStart();
        length = time.getLength();
        idRoom = room.getRoom_id();
        
        
        for (int wk = 0; wk < weeks.size(); wk++) {
            if (weeks.get(wk)==1) {
                for (int day = 0; day < days.size(); day++) {
                    if (days.get(day)==1) {
                        for (int tm = start; tm <= (start+length); tm++) {
                            if (timeslot.get(wk).listHari.get(day).timeslot[tm][idRoom-1]!=0) {
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
        start = time.getStart();
        length = time.getLength();
        idRoom = room.getRoom_id();

        for (int wk = 0; wk < weeks.size(); wk++) {
            if (weeks.get(wk) == 1) {
                for (int dy = 0; dy < days.size(); dy++) {
                    if (days.get(dy) == 1) {
                        for (int tm = start; tm <= (start+length); tm++) {
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
    
    boolean cekTSwR(TimeAss time){
        weeks = time.getWeeks();
        days = time.getDays();
        start = time.getStart();
        length = time.getLength();
        
        for (int wk = 0; wk < weeks.size(); wk++) {
            if (weeks.get(wk)==1) {
                for (int day = 0; day < days.size(); day++) {
                    if (days.get(day)==1) {
                        for (int tm = start; tm <= (start+length); tm++) {
                            if (timeslot.get(wk).listHari.get(day).timeNoRom[tm]!=0) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
        void assignNoRom(int idKls, TimeAss time){
        weeks = time.getWeeks();
        days = time.getDays();
        start = time.getStart();
        length = time.getLength();
        for (int wk = 0; wk < weeks.size(); wk++) {
            if (weeks.get(wk)==1) {
                for (int day = 0; day < days.size(); day++) {
                    if (days.get(day)==1) {
                        for (int tm = start; tm <= (start+length); tm++) {
                            timeslot.get(wk).listHari.get(day).timeNoRom[tm] = idKls;
                        }
                    }
                }
            }
        }
    }
    
}
