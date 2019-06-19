package domoop;

import java.util.ArrayList;

public class Students {

    private int cID;
    private ArrayList<Integer> stud;

    Students(int cID, ArrayList<Integer> stud) {
        this.cID = cID;
        this.stud = stud;
    }

    void addStudents() {

    }

    /**
     * @return the cID
     */
    public int getcID() {
        return cID;
    }

    /**
     * @return the stud
     */
    public ArrayList<Integer> getStud() {
        return stud;
    }

}
