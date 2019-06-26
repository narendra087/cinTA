package bismillahta;

import java.util.ArrayList;

public class MethodConstraintTry {
    
    MethodConstraintTry(String type){
        
    }
    
    //1.SAME START                                                              //Ci.start = Cj.start
    boolean SameStart(int[] start, ArrayList<Integer> listSS, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listSS.size(); i++) {
            for (int j = i+1; j < listSS.size(); j++) {
                int a = listSS.get(i);
                int b = listSS.get(j);
                if(start[a] == start[b]){
                    //benar++
                    return true;
                } //else{
//                    jPenalty += penalty;
//                }
            }
        }
        //return jPenalty;
        return false;
    }
    
    //2.SAME TIME                                                               //(Ci.start ≤ Cj.start ∧ Cj.end ≤ Ci.end) ∨ (Cj.start ≤ Ci.start ∧ Ci.end ≤ Cj.end)
    boolean SameTime(int[] length, int[] start, ArrayList<Integer> listST, 
            int penalty){
        int jPenalty = 0;
        int benar = 0; 
        for (int i = 0; i < listST.size(); i++) {
            for (int j = 0; j < listST.size(); j++) {
                int a = listST.get(i);
                int b = listST.get(j);
                int endA = start[a]+length[a];
                int endB = start[b]+length[b];
                if((start[a] <= start[b] && endB <= endA) || 
                        (start[b] <= start[a] && endA <= endB)){
                    //benar++;
                    return true;
                } //else{
//                    jPenalty += penalty;
//                }                    
            }
        }
        //return jPenalty;
        return false;
    }    
    
    //3.DIFFERENT TIME                                                          //(Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start)
    boolean DifferentTime(int[] start, int[] length, ArrayList<Integer> listDT, 
            int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listDT.size(); i++) {
            for (int j = 0; j < listDT.size(); j++) {
                int a = listDT.get(i);
                int b = listDT.get(j);
                int endA = start[a]+length[a];
                int endB = start[b]+length[b];
                if(endA <= start[b] || endB <= start[a]){
                    //benar++;
                    return true;
                } //else{
//                    jPenalty += penalty;
//                }
            }
        }
        //return jPenalty; 
        return false;
    }
    
    //4.SAME DAYS                                                               //((Ci.days or Cj.days) = Ci.days) ∨ ((Ci.days or Cj.days) = Cj.days)
    boolean SameDays(String[] days, int d, ArrayList<Integer> listSD, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listSD.size(); i++) {
            for (int j = 0; j < listSD.size(); j++) {
                //Ambil classID eg. class ID="100"
                int a = listSD.get(i); 
                int b = listSD.get(j);
                //Ambil value days dari classID 100 : "0110001" lalu Split value days dan simpan hasil split ke Array
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                for (int k = 0; k < d; k++) {
                    if(dayA[k] == "1" && dayB[k] == "1"){
                        //benar++;
                        return true;
                        //break;
                    } //else{
//                        jPenalty += penalty;
//                    }    
                }
            }
        }
        //return jPenalty;
        return false;
    }
    
    //5.DIFFERENT DAYS                                                          //(Ci.days and Cj.days) = 0
    boolean DifferentDays(String[] days, int d, ArrayList<Integer> listDD, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listDD.size(); i++) {
            for (int j = 0; j < listDD.size(); j++) {
                int a = listDD.get(i);
                int b = listDD.get(j);
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                for (int k = 0; k < d; k++) {
                    if(dayA[k] == "0" && dayB[k] == "0" || 
                            dayA[k] == "1" && dayB[k] == "0" || 
                            dayA[k] == "0" && dayB[k] == "1"){
                        //benar++;
                        return true;
                    } //else{
//                        jPenalty += penalty;
//                    }
                }
            }
        }
        //return jPenalty;
        return false;
    }
    
    //6.SAME WEEKS                                                              //(Ci.weeks or Cj.weeks) = Ci.weeks) ∨ (Ci.weeks or Cj.weeks) = Cj.weeks)
    boolean SameWeeks(String[] weeks, int w, ArrayList<Integer> listSW, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listSW.size(); i++) {
            for (int j = 0; j < listSW.size(); j++) {
                int a = listSW.get(i);
                int b = listSW.get(j);
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    if(weekA[k] == "1" && weekB[k] == "1"){
                        //benar++;
                        return true;
                        //break;
                    } else{
                        jPenalty += penalty;
                    } 
                }
            }
        }
        //return jPenalty;
        return false;
    }
    
    //7.DIFFERENT WEEKS                                                         //(Ci.weeks and Cj.weeks) = 0
    boolean DifferentWeeks(String[] weeks, int w, ArrayList<Integer> listDW, 
            int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listDW.size(); i++) {
            for (int j = 0; j < listDW.size(); j++) {
                int a = listDW.get(i);
                int b = listDW.get(j);
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    if(weekA[k] == "0" && weekB[k] == "0" || 
                            weekA[k] == "1" && weekB[k] == "0" || 
                            weekA[k] == "0" && weekB[k] == "1"){
                        //benar++;
                        return true;
                    } //else{
//                        jPenalty += penalty;
//                    }
                }   
            }
        }
        //return jPenalty;
        return false;
    }
    
    //8.SAME ROOM                                                               //Ci.room = Cj.room
    boolean SameRoom(int[] room, ArrayList<Integer> listSR, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listSR.size(); i++) {
            for (int j = 0; j < listSR.size(); j++) {
                int a = listSR.get(i);
                int b = listSR.get(j);
                if(room[a] == room[b]){
                    //benar++;
                    return true;
                } //else{
//                    jPenalty += penalty;
//                }
            }
        }
        //return jPenalty;
        return false;
    }
    
    //9.DIFFERENT ROOM                                                          //Ci.room ≠ Cj.room
    boolean DifferentRoom(int[] room, ArrayList<Integer> listDR, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listDR.size(); i++) {
            for (int j = 0; j < listDR.size(); j++) {
                int a = listDR.get(i);
                int b = listDR.get(j);
                if(room[a] != room[b]){
                    //benar++;
                    return true;
                } //else{
//                    jPenalty += penalty;
//                }
            }
        }
        //return jPenalty;
        return false;
    }
    
    //10.OVERLAP                                                                //(Cj.start < Ci.end) ∧ (Ci.start < Cj.end) ∧ ((Ci.days and Cj.days) ≠ 0) ∧ ((Ci.weeks and Cj.weeks) ≠ 0)
    boolean Overlap(int[] start, int[] length, String[] days, String[] weeks, int d, 
            int w, ArrayList<Integer> listOv, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listOv.size(); i++) {
            for (int j = 0; j < listOv.size(); j++) {
                int a = listOv.get(i);
                int b = listOv.get(j);
                int endA = start[a]+length[a];
                int endB = start[b]+length[b];
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    for (int l = 0; l < d; l++) {
                        if(start[b] < endA && start[a] < endB && 
                                ((dayA[l] != "0") && dayB[l] != "0") && 
                                ((weekA[k] != "0") && weekB[k] != "0")){
                            //benar++;
                            return true;
                        } //else{
//                            jPenalty += penalty;
//                        }
                    }
                }
            }
        }
        //return jPenalty;
        return false;
    }
    
    //11.NOT OVERLAP                                                            //(Ci.end ≤ Cj.start) ∨ (Cj.end ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0)
    boolean NotOverlap(int[] start, int[] length, String[] days, String[] weeks, 
            int d, int w, ArrayList<Integer> listNotOv, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listNotOv.size(); i++) {
            for (int j = 0; j < listNotOv.size(); j++) {
                int a = listNotOv.get(i);
                int b = listNotOv.get(j);
                int endA = start[a]+length[a];
                int endB = start[b]+length[b];
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    for (int l = 0; l < d; l++) {
                        if(endA <= start[b] || endB <= start[a] || 
                                ((dayA[l] == "0") && dayB[l] == "0") || 
                                ((weekA[k] == "0") && weekB[k] == "0")){
                            //benar++;
                            return true;
                        } //else{
//                            jPenalty += penalty;
//                        }
                    }
                }                
            }
        }
        //return jPenalty;
        return false;
    }
    
    //12.SAME ATTENDEES                                                         //(Ci.end + Ci.room.travel[Cj.room] ≤ Cj.start) ∨ (Cj.end + Cj.room.travel[Ci.room] ≤ Ci.start) ∨ ((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) Ci.room.travel[Cj.room] is the travel time between the assigned rooms of Ci and Cj
    boolean SameAttendees(int[] start, int[] length, String[] days, String[] weeks, 
            int d, int w, int[][] valueTravel, ArrayList<Integer> listSA, 
            int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listSA.size(); i++) {
            for (int j = 0; j < listSA.size(); j++) {
                int a = listSA.get(i);
                int b = listSA.get(j);
                int endA = start[a]+length[a];
                int endB = start[b]+length[b];
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    for (int l = 0; l < d; l++) {
                        if(endA + valueTravel[a][b] <= start[b] || 
                                endB + valueTravel[b][a] <= start[a] || 
                                (dayA[l] == "0" && dayB[l] == "0") || 
                                (weekA[k] == "0" && weekB[k] == "0")){
                            //benar++;
                            return true;
                        } //else{
//                            jPenalty += penalty;
//                        }
                    }
                }
            }
        }
        //return jPenalty;
        return false;
    }

    //13.PRECEDENCE                                                             //(first(Ci.weeks) < first(Cj.weeks)) ∨ [(first(Ci.weeks) = first(Cj.weeks)) ∧ [(first(Ci .days) < first(Cj .days)) ∨ ((first(Ci.days) = first(Cj.days)) ∧ (Ci.end ≤ Cj.start))]] for any two classes Ci and Cj from the constraint where i < j and first(x) is the index of the first non-zero bit in the binary string x.
    boolean Precedence(int[] start, int[] length, String[] days, String[] weeks, 
            int w, int d, ArrayList<Integer> listP, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listP.size(); i++) {
            for (int j = 0; j < listP.size(); j++) {
                int a = listP.get(i);
                int b = listP.get(j);
                int endA = start[a]+length[a];
                int endB = start[b]+length[b];
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                int firstWeekA = Integer.parseInt(weekA[0]);
                int firstWeekB = Integer.parseInt(weekB[0]);
                int firstDayA = Integer.parseInt(dayA[0]);
                int firstDayB = Integer.parseInt(dayB[0]);
                for (int k = 0; k < w; k++) {
                    for (int l = 0; l < d; l++) {
                        if(firstWeekA < firstWeekB || weekA[k] == weekB[k] &&
                                firstDayA < firstDayB || dayA[l] == dayB[l] && 
                                endA <= start[b]){
                            //benar++;
                            return true;
                        } //else{
//                            jPenalty += penalty;
//                        }
                    }
                }
            }
        }
        //return jPenalty;
        return false;
    }
    
    //14.WORKDAY(S)                                                             //((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (max(Ci.end,Cj.end)−min(Ci.start,Cj.start) ≤ S)
    boolean Workday(int S, int[] start, int[] length, String[] days, String[] weeks, 
            int w, int d, ArrayList<Integer> listWD, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listWD.size(); i++) {
            for (int j = 0; j < listWD.size(); j++) {
                int a = listWD.get(i);
                int b = listWD.get(j);
                int endA = start[a]+length[a];
                int endB = start[b]+length[b];
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    for (int l = 0; l < d; l++) {
                        if((dayA[l] == "0" && dayB[l] == "0") || 
                                (weekA[k] == "0" && weekB[k] == "0") || 
                                (Math.max(endA,endB)) - (Math.min(start[a], start[b])) <= S){
                            //benar++;
                            return true;
                        } //else{
//                            jPenalty += penalty;
//                        }
                    }
                }
            }
        }
        //return jPenalty;
        return false;
    }
    
    //15.MINGAP(G)                                                              //((Ci.days and Cj.days) = 0) ∨ ((Ci.weeks and Cj.weeks) = 0) ∨ (Ci.end + G ≤ Cj.start) ∨ (Cj.end + G ≤ Ci.start)
    boolean Mingap(int G, int[] start, int[] length, String[] days, String[] weeks, 
            int w, int d, ArrayList<Integer> listMG, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listMG.size(); i++) {
            for (int j = 0; j < listMG.size(); j++) {
                int a = listMG.get(i);
                int b = listMG.get(j);
                int endA = start[a]+length[a];
                int endB = start[b]+length[b];
                String[] dayA = days[a].split("");
                String[] dayB = days[b].split("");
                String[] weekA = weeks[a].split("");
                String[] weekB = weeks[b].split("");
                for (int k = 0; k < w; k++) {
                    for (int l = 0; l < d; l++) {
                        if((dayA[l] == "0" && dayB[l] == "0") || 
                                (weekA[k] == "0" && weekB[k] == "0") ||
                                endA + G <= start[b] || endB + G <= start[a]){
                            //benar++;
                            return true;
                        } //else{
//                            jPenalty += penalty;
//                        }
                    }
                }
            }
        }
        //return jPenalty;
        return false;
    }
    
    //16.MAXDAYS(D)                                                             //countNonzeroBits(C1.days or C2.days or ⋅ ⋅ ⋅ Cn.days) ≤ D
    boolean Maxdays(int D, String[] days, ArrayList<Integer> listMD, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listMD.size(); i++) {
            for (int j = 0; j < listMD.size(); j++) {
                int a = listMD.get(i);
                int b = listMD.get(j);
                if(countNonzeroBits(days[a]) <= D){
                    //benar++;
                    return true;
                } //else{
//                    jPenalty += penalty;
//                }
            }
        }
        //return jPenalty;
        return false;
    }
    
    //17.MAXDAY LOAD(S)                                                         //DayLoad(d,w) ≤ S >>> DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
    boolean MaxdayLoad(int S, int d, int w, String[] days, String[] weeks, 
            int[] length, ArrayList<Integer> listMDL, int penalty){
        int jPenalty = 0;
        int benar = 0;
        for (int i = 0; i < listMDL.size(); i++){
            int a = listMDL.get(i);
            int l = length[a];
            String day = days[a];
            String week = weeks[a];
            if(DayLoad(d,w,day,week,l) <= S){
                //benar++;
                return true;
            } //else{
//                int maxLoad = 0;
//                maxLoad = Math.max(DayLoad(d,w,day,week,l)-S, 0);
//                jPenalty = (penalty*maxLoad)/w;
//            }
        }
        //return jPenalty;                                                        //PENALTY : (penalty × ∑w,d max(DayLoad(d,w) − S, 0)) / nrWeeks
        return false;
    }
    
    //18.MAXBREAKS(R,S)                                                         //|MergeBlocks{(C.start, C.end) | (C.days and 2d) ≠ 0 ∧ (C.weeks and 2w) ≠ 0})| ≤ R + 1 >> (Ba.end + S ≥ Bb.start) ∧ (Bb.end + S ≥ Ba.start) ⇒ (B.start = min(Ba.start, Bb.start)) ∧ (B.end = max(Ba.end, Bb.end))
    boolean Maxbreaks(int R, int S, int d, int w, int[] start, int[] length, 
            String[] days, String[] weeks, ArrayList<Integer> listMB, 
            int penalty){
        int jPenalty = 0; 
        int benar = 0;
        for (int i = 0; i < listMB.size(); i++) {
            for (int j = 0; j < listMB.size(); j++) {
                int a = listMB.get(i);
                int b = listMB.get(j);
                int s = start[a];
                int endA = start[a]+length[a];
                int endB = start[b]+length[b];
                String day = days[a];
                String week = weeks[a];
                if(MergeBlocks(s,endA,S) <= R+1){
                    //benar++;
                    return true;
                } else{

                }
            }
        }
        //return jPenalty;
        return false;
    }
    
    //19.MAXBLOCK(M,S)                                                          //max {B.end − B.start | B ∈ MergeBlocks{(C.start, C.end) | (C.days and 2d) ≠ 0 ∧ (C.weeks and 2w) ≠ 0}} ≤ M
    boolean Maxblock(){
        
        return false;
    }

    private int countNonzeroBits(String days) {
        int count = 0;
        String[] week = days.split("");
        for (int i = 0; i < 6; i++) {
            if(week[i] == "1"){
                count++;
            }
        }
        return count;
    }
    
    private int DayLoad(int d, int w, String days, String weeks, int length) {  //DayLoad(d,w) = ∑i {Ci.length | (Ci.days and 2^d) ≠ 0 ∧ (Ci.weeks and 2^w) ≠ 0)}
        int sum = 0;
        String[] day = days.split("");
        String[] week = weeks.split("");
        for (int wk = 0; wk < w; wk++){
            for (int dy = 0; dy < d; dy++){
                if (week[wk] == "1" && day[dy] == "1"){
                    sum += length;
                }
            }
        }
        return sum;
    }

    private int MergeBlocks(int s, int e, int S) {
        return 0;
    }
}
