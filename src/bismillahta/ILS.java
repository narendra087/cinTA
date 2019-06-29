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
public class ILS {
    ArrayList<Integer> weeks, days;
    int start, length, penalty_ts,
            idRoom, pen_rom;
    Random rand;
    CekHC hard;
    ArrayList<Minggu> timeslot;
    ArrayList<Kelas> listKelas;
    
    void move(){
        int kls, ts, tsBaru, rm, rmBaru, idKls;
        ArrayList<TimeAss> times;
        ArrayList<RoomAss> rooms;
//        if () {
//            
//        }
        do {
            kls = rand.nextInt(listKelas.size());
            Kelas kelas = listKelas.get(kls);
            ts = kelas.getTs();
            tsBaru = ts;
            rm = kelas.getRoom();
            rmBaru = rm;
            times = kelas.getAvailableTS();
            TimeAss timeLama = times.get(ts);
            rooms = kelas.getAvailableroom();
            RoomAss roomLama = rooms.get(rm);
            for (int i = 0; i < times.size(); i++) {
                TimeAss time = times.get(i);
                for (int j = 0; j < rooms.size(); j++) {
                    if (i!=ts && j!=rm) {
                        RoomAss room = rooms.get(i);
                        if (cekTS(time, room)) {
                            idKls = kelas.getClassID();
                            assign(idKls, time, room);
                            removeKls(timeLama, roomLama);
                        }
                    }

                }
            }
        } while (ts==tsBaru && rm==rmBaru);
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
    
}
