/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bismillahta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Umar Rizki
 */
public class Kelas {

    private int room = -1;
    private int ts = -1;
    private int courseID;
    private int configID;
    private int subpartID;
    private int classID;
    private int limit;
    private int parent;
    private int penalty;
    private boolean hasRoom;
    private int[] inUrutan = new int[4];
    private ArrayList<RoomAss> availableroom = new ArrayList<>();
    private ArrayList<TimeAss> availableTS = new ArrayList<>();
    private ArrayList<Constraint> clasHC = new ArrayList<>();
    private ArrayList<Constraint> clasSC = new ArrayList<>();

    public Kelas (int classID, int parent, int limit, boolean hasRoom) {
        this.classID = classID;
        this.parent = parent;
        this.limit = limit;
        this.hasRoom = hasRoom;
    }
    
    int jmlUrutan(){
        return inUrutan[3];
    }
    
    int jmlDistri(){
        return jmlHC()+jmlSC();
    }
    
    int jmlHC(){
        return clasHC.size();
    }
    
    int jmlSC(){
        return clasSC.size();
    }
    
    int jmlTS(){
        return availableTS.size();
    }
    
    int jmlRoom(){
        return availableroom.size();
    }
    
    void addARoom(RoomAss a){
        getAvailableroom().add(a);
    }
    
    void addTS(TimeAss a){
        getAvailableTS().add(a);
    }
    
    void addclasHC (Constraint a){
        getClasHC().add(a);
    }
    
    void addclasSC (Constraint a){
        getClasSC().add(a);
    }
    
//    void addListCons (ArrayList<Integer> cons1){
//        if (true) {
//            cons1.removeAll(combinedArray);
//            combinedArray.addAll(cons1);
//        }
//    }
    
    /**
     * @return the courseID
     */
    public int getCourseID() {
        return courseID;
    }

    /**
     * @param courseID the courseID to set
     */
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    /**
     * @return the configID
     */
    public int getConfigID() {
        return configID;
    }

    /**
     * @param configID the configID to set
     */
    public void setConfigID(int configID) {
        this.configID = configID;
    }

    /**
     * @return the subpartID
     */
    public int getSubpartID() {
        return subpartID;
    }

    /**
     * @param subpartID the subpartID to set
     */
    public void setSubpartID(int subpartID) {
        this.subpartID = subpartID;
    }

    /**
     * @return the classID
     */
    public int getClassID() {
        return classID;
    }

    /**
     * @param classID the classID to set
     */
    public void setClassID(int classID) {
        this.classID = classID;
    }

    /**
     * @return the room
     */
    public int getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(int room) {
        this.room = room;
    }

    /**
     * @return the ts
     */
    public int getTs() {
        return ts;
    }

    /**
     * @param ts the ts to set
     */
    public void setTs(int ts) {
        this.ts = ts;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * @return the parent
     */
    public int getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(int parent) {
        this.parent = parent;
    }

    /**
     * @return the hasRoom
     */
    public boolean isHasRoom() {
        return hasRoom;
    }

    /**
     * @param hasRoom the hasRoom to set
     */
    public void setHasRoom(boolean hasRoom) {
        this.hasRoom = hasRoom;
    }

    /**
     * @return the availableroom
     */
    public ArrayList<RoomAss> getAvailableroom() {
        return availableroom;
    }

    /**
     * @param availableroom the availableroom to set
     */
    public void setAvailableroom(ArrayList<RoomAss> availableroom) {
        this.availableroom = availableroom;
    }

    /**
     * @return the availableTS
     */
    public ArrayList<TimeAss> getAvailableTS() {
        return availableTS;
    }

    /**
     * @param availableTS the availableTS to set
     */
    public void setAvailableTS(ArrayList<TimeAss> availableTS) {
        this.availableTS = availableTS;
    }

    /**
     * @return the clasHC
     */
    public ArrayList<Constraint> getClasHC() {
        return clasHC;
    }

    /**
     * @return the clasSC
     */
    public ArrayList<Constraint> getClasSC() {
        return clasSC;
    }

    /**
     * @return the inUrutan
     */
    public int[] getInUrutan() {
        return inUrutan;
    }

    /**
     * @param inUrutan the inUrutan to set
     */
    public void setInUrutan(int[] inUrutan) {
        this.inUrutan = inUrutan;
    }

    /**
     * @return the penalty
     */
    public int getPenalty() {
        return penalty;
    }

    /**
     * @param penalty the penalty to set
     */
    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
}

class groupSort implements Comparator<Kelas>{
    private List<Comparator<Kelas>> listComparators;
    
    public groupSort(Comparator<Kelas>... comparators) {
        this.listComparators = Arrays.asList(comparators);
    }
 
    @Override
    public int compare(Kelas o1, Kelas o2) {
        for (Comparator<Kelas> comparator : listComparators) {
            int result = comparator.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}

class sortUrutanKls implements Comparator<Kelas>{

    @Override
    public int compare(Kelas o1, Kelas o2) {
        return o1.jmlUrutan()-o2.jmlUrutan();
    }
    
}

class sortKlsHC implements Comparator<Kelas>{

    @Override
    public int compare(Kelas o1, Kelas o2) {
        return o2.jmlHC()-o1.jmlHC();
    }
    
}

class sortIdKelas implements Comparator<Kelas>{

    @Override
    public int compare(Kelas o1, Kelas o2) {
        return o1.getClassID()-o2.getClassID();
    }
    
}

class sortRoomKelas implements Comparator<Kelas>{

    @Override
    public int compare(Kelas o1, Kelas o2) {
        return o1.jmlRoom()-o2.jmlRoom();
    }
    
}

class sortTSKelas implements Comparator<Kelas>{

    @Override
    public int compare(Kelas o1, Kelas o2) {
        return o1.jmlTS()-o2.jmlTS();
    }
    
}

class sortTSpen implements Comparator<TimeAss> {

    @Override
    public int compare(TimeAss o1, TimeAss o2) {
        return o1.getPenalty()-o2.getPenalty();        
    }
    
}

class sortRMpen implements Comparator<RoomAss> {

    @Override
    public int compare(RoomAss o1, RoomAss o2) {
        return o1.getRoom_pen()-o2.getRoom_pen();
    }
    
}