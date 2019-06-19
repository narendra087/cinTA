package domoop;

import java.util.ArrayList;

/**
 *
 * @author R
 */
public class softPenalty {
    
    //Return jumlah semua penalti
    int totalPenalti(){
        return 0;
    }
    
    //Menghitung penalti setiap constraint
    
    //SAME START (Ci.start = Cj.start)
    int SameStart(int[] start, ArrayList<Integer> listSS, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listSS.size(); i++) {
            for (int j = i+1; j < listSS.size(); j++) {
                int a = listSS.get(i);
                int b = listSS.get(j);
                if(start[a] == start[b])
                {
                    benar++;
                } else{
                    jmlPenalti += penalti;
                }
            }
        }
        return jmlPenalti;
    }
    
    //2.SAME TIME   (Ci.start ≤ Cj.start ∧ Cj.end ≤ Ci.end) ∨ (Cj.start ≤ Ci.start ∧ Ci.end ≤ Cj.end)
    int SameTime(int[] length, int[] start, int[] end, ArrayList<Integer> listST, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listST.size(); i++) {
            for (int j = 0; j < listST.size(); j++) {
                int a = listST.get(i);
                int b = listST.get(j);
                if((start[a] <= start[b] && end[b] <= end[a]) || 
                   (start[b] <= start[a] && end[a] <= end[b]))
                {
                    benar++;
                } else{
                    jmlPenalti += penalti;
                }                  
            }
        }
        return jmlPenalti;
    }
    
    //3.DIFFERENT TIME  (Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start)
    int DifferentTime(int[] start, int[] end, ArrayList<Integer> listDT, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listDT.size(); i++) {
            for (int j = i+1; j < listDT.size(); j++) {
                int a = listDT.get(i);
                int b = listDT.get(j);
                if(end[a] <= start[b] || 
                   end[b] <= start[a])
                {
                    benar++;
                } else{
                    jmlPenalti += penalti;
                }
            }
        }
        return jmlPenalti; 
    }
    
    //4.SAME DAYS ((Ci.days or Cj.days) = Ci.days) ∨ ((Ci.days or Cj.days) = Cj.days)
    //MASIH BINGUNG
    int SameDays(String[] days, int d, ArrayList<Integer> listSD, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        //String[] day = days.split("");
        for (int i = 0; i < listSD.size(); i++) {
            for (int j = i+1; j < listSD.size(); j++) {
                int a = listSD.get(i);
                int b = listSD.get(j);
                String[] day1 = days[a].split("");
                String[] day2 = days[b].split("");
                for (int k = 0; k < d; k++) {
                    if ("1".equals(day1[k]) && "1".equals(day2[k])) {
                        benar++;
                        break;
                    } else{
                        jmlPenalti += penalti;
                    }
                } 
            }
        }
        return jmlPenalti;
    }
    
    //5.DIFFERENT DAYS  (Ci.days and Cj.days) = 0
    int DifferentDays(String[] days, int d, ArrayList<Integer> listDD, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listDD.size(); i++) {
            for (int j = i+1; j < listDD.size(); j++) {
                int a = listDD.get(i);
                int b = listDD.get(j);
                String[] day1 = days[a].split("");
                String[] day2 = days[b].split("");
                for (int k = 0; k < d; k++) {
                    if(("0".equals(day1[k]) && "0".equals(day2[k])) || 
                       ("1".equals(day1[k]) && "0".equals(day2[k])) ||
                       ("0".equals(day1[k]) && "1".equals(day2[k])))
                    {
                        benar++;
                    } else{
                        jmlPenalti += penalti;
                    }
                }
            }
        }
        return jmlPenalti;
    }
    //MASIH BINGUNG
    //6.SAME WEEKS  (Ci.weeks or Cj.weeks) = Ci.weeks) ∨ (Ci.weeks or Cj.weeks) = Cj.weeks)
    int SameWeeks(String[] weeks, int w, ArrayList<Integer> listSW, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listSW.size(); i++) {
            for (int j = i+1; j < listSW.size(); j++) {
                int a = listSW.get(i);
                int b = listSW.get(j);
                String[] week1 = weeks[a].split("");
                String[] week2 = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    if ("1".equals(week1[k]) && "1".equals(week2[k])) {
                        benar++;
                        break;
                    } else{
                        jmlPenalti += penalti;
                    }
                }
            }
        }
        return jmlPenalti;
    }
    
    //7.DIFFERENT WEEKS (Ci.weeks and Cj.weeks) = 0
    int DifferentWeeks(String[] weeks, int w, ArrayList<Integer> listDW, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listDW.size(); i++) {
            for (int j = i+1; j < listDW.size(); j++) {
                int a = listDW.get(i);
                int b = listDW.get(j);
                String[] week1 = weeks[a].split("");
                String[] week2 = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    if(("0".equals(week1[k]) && "0".equals(week2[k])) || 
                       ("1".equals(week1[k]) && "0".equals(week2[k])) ||
                       ("0".equals(week1[k]) && "1".equals(week2[k])))
                    {
                         benar++;
                    } else{
                        jmlPenalti += penalti;
                    }
                }
            }
        }
        return jmlPenalti;
    }
    
    //8.SAME ROOM   Ci.room = Cj.room
    int SameRoom(int[] room, ArrayList<Integer> listSR, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listSR.size(); i++) {
            for (int j = i+1; j < listSR.size(); j++) {
                int a = listSR.get(i);
                int b = listSR.get(j);
                if(room[a] == room[b])
                {
                    benar++;
                } else{
                    jmlPenalti += penalti;
                }
            }
        }
        return jmlPenalti;
    }
    
    //9.DIFFERENT ROOM  Ci.room ≠ Cj.room
    int DifferentRoom(int[] room, ArrayList<Integer> listDR, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listDR.size(); i++) {
            for (int j = i+1; j < listDR.size(); j++) {
                int a = listDR.get(i);
                int b = listDR.get(j);
                if(room[a] != room[b])
                {
                    benar++;
                } else{
                    jmlPenalti += penalti;
                }
            }
        }
        return jmlPenalti;
    }
    
    //10.OVERLAP    (Cj.start < Ci.end) ∧ (Ci.start < Cj.end) ∧ ((Ci.days and Cj.days) ≠ 0) ∧ ((Ci.weeks and Cj.weeks) ≠ 0)
    int Overlap(int[] start, int[] length, String[] days, String[] weeks, int d, int w, ArrayList<Integer> listOv, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listOv.size(); i++) {
            for (int j = i+1; j < listOv.size(); j++) {
                int a = listOv.get(i);
                int b = listOv.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                String[] day1 = days[a].split("");
                String[] day2 = days[b].split("");
                String[] week1 = weeks[a].split("");
                String[] week2 = weeks[b].split("");
                for (int wk = 0; wk < w; wk++) {
                    for (int dy = 0; dy < d; dy++) {
                        if (("1".equals(week1[wk]) && "1".equals(week2[wk])) && ("1".equals(day1[dy]) && "1".equals(day2[dy])) && (start[b] < endA) && (start[a] < endB)) {
                                benar++;
                            } else{
                                jmlPenalti += penalti;
                        }
                    }
                }
            }
        }
        return jmlPenalti;
    }
    
    //11.NOT OVERLAP    (Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0)
    int NotOverlap(int[] start, int[] length, String[] days, String[] weeks, int d, int w, ArrayList<Integer> listNotOv, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listNotOv.size(); i++) {
            for (int j = i+1; j < listNotOv.size(); j++) {
                int a = listNotOv.get(i);
                int b = listNotOv.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                String[] day1 = days[a].split("");
                String[] day2 = days[b].split("");
                String[] week1 = weeks[a].split("");
                String[] week2 = weeks[b].split("");
                for (int wk = 0; wk < w; wk++) {
                    for (int dy = 0; dy < d; dy++) {
                        if (("0".equals(week1[wk]) && "0".equals(week2[wk])) || ("0".equals(day1[dy]) && "0".equals(day2[dy])) || (endA <= start[b]) || (endB <= start[a])) {
                            benar++;
                        } else{
                            jmlPenalti += penalti;
                        }
                    }
                }               
            }
        }
        return jmlPenalti;
    }
    
    //12.SAME ATTENDEES (Ci.end + Ci.room.travel[Cj.room] ≤ Cj.start) ∨ (Cj.end + Cj.room.travel[Ci.room] ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0)
    //Ci.room.travel[Cj.room] is the travel time between the assigned rooms of Ci and Cj
    int SameAttendees(int[] start, int[] length, String[] days, String[] weeks, int d, int w, int[][] valueTravel, ArrayList<Integer> listSA, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listSA.size(); i++) {
            for (int j = i+1; j < listSA.size(); j++) {
                int a = listSA.get(i);
                int b = listSA.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                String[] day1 = days[a].split("");
                String[] day2 = days[b].split("");
                String[] week1 = weeks[a].split("");
                String[] week2 = weeks[b].split("");
                for (int wk = 0; wk < w; wk++) {
                    for (int dy = 0; dy < d; dy++) {
                        if (((endA + valueTravel[a][b]) <= start[b]) ||
                            ((endB + valueTravel[b][a]) <= start[a]) ||
                            ("0".equals(week1[wk]) && "0".equals(week2[wk])) ||  
                            ("0".equals(day1[dy]) && "0".equals(day2[dy]))) 
                        {
                            benar++;
                        } else{
                            jmlPenalti += penalti;
                        }
                    }
                }
            }
        }
        return jmlPenalti;
    }

    //13.PRECEDENCE (first(Ci.weeks) < first(Cj.weeks)) ∨ [(first(Ci.weeks) = first(Cj.weeks)) ∧ 
    //[(first(Ci .days) < first(Cj .days)) ∨ ((first(Ci.days) = first(Cj.days)) ∧ (Ci.end ≤ Cj.start))]]
    //for any two classes Ci and Cj from the constraint where i < j and first(x) is the index of the first non-zero bit in the binary string x.
    int Precedence(int[] start, int[] length, String[] days, String[] weeks, int d, int w, ArrayList<Integer> listP, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listP.size(); i++) {
            for (int j = i+1; j < listP.size(); j++) {
                int a = listP.get(i);
                int b = listP.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                String[] day1 = days[a].split("");
                String[] day2 = days[b].split("");
                String[] week1 = weeks[a].split("");
                String[] week2 = weeks[b].split("");
                for (int wk = 0; wk < w; wk++) {
                    for (int dy = 0; dy < d; dy++) {
                        if ((Integer.parseInt(week1[wk]) < Integer.parseInt(week2[wk])) ||
                            (Integer.parseInt(week1[wk]) == Integer.parseInt(week2[wk])) &&
                            (Integer.parseInt(day1[dy]) < Integer.parseInt(day2[dy])) ||
                            (Integer.parseInt(day1[dy]) == Integer.parseInt(day2[dy])) &&
                            (endA <= start[b])) 
                        {
                            benar++;
                        } else{
                            jmlPenalti += penalti;
                        }
                    }
                }
            }
        }
        return jmlPenalti;
    }
    
    //14.WORKDAY(S) ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (max(Ci.end,Cj.end)−min(Ci.start,Cj.start) ≤ S)
    int Workday(int S, int[] start, int[] length, String[] days, String[] weeks, int d, int w, ArrayList<Integer> listWD, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listWD.size(); i++) {
            for (int j = i+1; j < listWD.size(); j++) {
                int a = listWD.get(i);
                int b = listWD.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                String[] day1 = days[a].split("");
                String[] day2 = days[b].split("");
                String[] week1 = weeks[a].split("");
                String[] week2 = weeks[b].split("");
                for (int wk = 0; wk < w; wk++) {
                    for (int dy = 0; dy < d; dy++) {
                        if (("0".equals(week1[wk]) && "0".equals(week2[wk])) ||
                            ("0".equals(day1[dy]) && "0".equals(day2[dy])) ||
                                (((Math.max(endA, endB))-(Math.min(start[a], start [b]))) <= S)) 
                        {
                            benar++;
                        } else{
                            jmlPenalti += penalti;
                        }
                    }
                }
            }
        }
        return jmlPenalti;
    }
    
    //15.MINGAP(G)  ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (Ci.end + G ≤ Cj.start) ∨ (Cj.end + G ≤ Ci.start)
    int Mingap(int G, int[] start, int[] length, String[] days, String[] weeks, int d, int w, ArrayList<Integer> listMG, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listMG.size(); i++) {
            for (int j = i+1; j < listMG.size(); j++) {
                int a = listMG.get(i);
                int b = listMG.get(j);
                int endA = start[a] + length[a];
                int endB = start[b] + length[b];
                String[] day1 = days[a].split("");
                String[] day2 = days[b].split("");
                String[] week1 = weeks[a].split("");
                String[] week2 = weeks[b].split("");
                for (int wk = 0; wk < w; wk++) {
                    for (int dy = 0; dy < d; dy++) {
                        if (("0".equals(week1[wk]) && "0".equals(week2[wk])) ||
                            ("0".equals(day1[dy]) && "0".equals(day2[dy])) ||
                            ((endA + G) <= start[b]) ||
                            ((endB + G) <= start[a])) 
                        {
                            benar++;
                        } else{
                            jmlPenalti += penalti;
                        }
                    }
                }
            }
        }
        return jmlPenalti;
    }
    
    //16.MAXDAYS(D) countNonzeroBits(C1.days or C2.days or ⋅ ⋅ ⋅ Cn.days) ≤ D
    int Maxdays(int D, String[] days, ArrayList<Integer> listMD, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        int lebih = 0;
        for (int i = 0; i < listMD.size(); i++) {
                if(countNonzeroBits(days[i]) <= D){
                    benar++;
                } else{
                    lebih = (countNonzeroBits(days[i]) - D);
                    jmlPenalti += (lebih*penalti);
                }
            }
        return jmlPenalti;
    }
    
    //17.MAXDAY LOAD(S)
    //DayLoad(d,w) ≤ S
    //DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
    //2d is a bit string with the only non-zero bit on position d
    //2w is a bit string with the only non-zero bit on position w
//    int d[] = {0,1,2,3,4,5,6};                          //HARUSNYA GET JUMLAH DAYS PER DATASET
//    int w[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};  //HARUSNYA GET JUMLAH WEEKS PER DATASET
//    int sum = 0;
    int MaxdayLoad(int S, int d, int w, String[] days, String[] weeks, int[] length, ArrayList<Integer> listMDL, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        for (int i = 0; i < listMDL.size(); i++){
            int a = listMDL.get(i);
            String day = days[a];
            String week = weeks[a];
            int l = length[a];
            if(DayLoad(S, d, w, day, week, l) == 0){
                benar = 1;
            } else{
                jmlPenalti = (penalti*(DayLoad(S, d, w, day, week, l))/w);
            }
        }
        return jmlPenalti;
    }
    
    //18.MAXBREAKS(R,S)
    int Maxbreaks(int R, int S, int d, int w, String[] days, String[] weeks, int[] start, int[] length, ArrayList<Integer> listMB, int penalti){
        int jmlPenalti = 0;
        int benar = 0;
        if (mergeBlocks(R, S, d, w, days, weeks, start, length, listMB) <= (R+1)) {
            benar = 1;
        } else{
            jmlPenalti = (penalti*(mergeBlocks(R, S, d, w, days, weeks, start, length, listMB)-R))/w;
        }
        return jmlPenalti;
    }
    
    //19.MAXBLOCK(M,S)
    int Maxblock(int M, int S, int d, int w, String[] days, String[] weeks, int[] start, int[] length, ArrayList<Integer> listMBlock, int penalti){
        int jmlPenalti = 0;
        jmlPenalti = penalti*mergeBlocksM(M, S, d, w, days, weeks, start, length, listMBlock)/w;
        return jmlPenalti;
    }
    
    
    //METHOD

    //Menghitung jumlah angka 1 pada days
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
    
    //DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
    private int DayLoad(int S,int d, int w, String days, String weeks, int length) {
        int lebih = 0;
        String[] day = days.split("");
        String[] week = weeks.split("");
        for (int wk = 0; wk < w; wk++) {
            for (int dy = 0; dy < d; dy++) {
                if ("1".equals(week[wk]) && "1".equals(day[dy])) {
                    if (length > S) {
                        lebih = lebih + (length - S);
                    }
                }
            }
        }
        return lebih;
    }
    
    private int mergeBlocks(int R, int S, int d, int w, String[] days, String[] weeks, int[] start, int[] length, ArrayList<Integer> listMB){
        int sum = 0;
        int initialB = 1;
        
        int a = listMB.get(0);
        String day1 = days[a];
        String week1 = weeks[a];
        int len1 = length[a];
        int strt1 = start[a];
        int end1 = strt1+len1;
        
        for (int i = 1; i < listMB.size(); i++) {
            int b = listMB.get(i);
            String day2 = days[b];
            String week2 = weeks[b];
            int len2 = length[b];
            int strt2 = start[b];
            int end2 = strt2+len2;
                
            String[] dy1 = day1.split("");
            String[] wk1 = week1.split("");
            String[] dy2 = day2.split("");
            String[] wk2 = week2.split("");
            
            for (int wk = 0; wk < w; wk++) {
                for (int dy = 0; dy < d; dy++) {
                    if ("1".equals(wk1[wk]) && "1".equals(dy1[dy]) && "1".equals(wk2[wk]) && "1".equals(dy2[dy])) {
                        if (end1 + S >= strt2 && end2 + S >= strt1) {
                            strt1 = Math.min(strt1, strt2);
                            end1 = Math.max(end1, end2);
                        } else{
                            initialB++;
                            strt1 = strt2;
                            end1 = end2;
                            dy1 = dy2;
                            wk1 = wk2;
                        }
                    }
                }
            }
        }
        
        return initialB;
    }
    
    private int mergeBlocksM(int M, int S, int d, int w, String[] days, String[] weeks, int[] start, int[] length, ArrayList<Integer> listMB){
        int salah = 0;
        int lengthBlock = 0;
        boolean blockBaru = true;
        
        int a = listMB.get(0);
        String day1 = days[a];
        String week1 = weeks[a];
        int len1 = length[a];
        int strt1 = start[a];
        int end1 = strt1+len1;
        
        for (int i = 1; i < listMB.size(); i++) {
            int b = listMB.get(i);
            String day2 = days[b];
            String week2 = weeks[b];
            int len2 = length[b];
            int strt2 = start[b];
            int end2 = strt2+len2;
                
            String[] dy1 = day1.split("");
            String[] wk1 = week1.split("");
            String[] dy2 = day2.split("");
            String[] wk2 = week2.split("");
            
            for (int wk = 0; wk < w; wk++) {
                for (int dy = 0; dy < d; dy++) {
                    if ("1".equals(wk1[wk]) && "1".equals(dy1[dy]) && "1".equals(wk2[wk]) && "1".equals(dy2[dy])) {
                        if (end1 + S >= strt2 && end2 + S >= strt1) {
                            strt1 = Math.min(strt1, strt2);
                            end1 = Math.max(end1, end2);
                            lengthBlock = end1 - strt1;
                            if (lengthBlock > M && blockBaru == true) {
                                salah += 1;
                                blockBaru = false;
                            } else {
                                salah += 0;
                            }
                        } else{
                            lengthBlock = end1 - strt1;
                            if (lengthBlock > M && blockBaru == true) {
                                salah += 1;
                            } else {
                                salah += 0;
                            }
                            blockBaru = true;
                            strt1 = strt2;
                            end1 = end2;
                            dy1 = dy2;
                            wk1 = wk2;
                        }
                    }
                }
            }
        }
        return salah;
    }
    

    
}
