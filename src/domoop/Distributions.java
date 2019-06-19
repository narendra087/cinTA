package domoop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Distributions {

    private ArrayList<Constraint> hardC = new ArrayList<>();
    private ArrayList<Constraint> softC = new ArrayList<>();

    Distributions() {

    }

    void addHard(Constraint a) {
        getHardC().add(a);
    }

    void addSoft(Constraint a) {
        getSoftC().add(a);
    }

    public ArrayList<Constraint> getHardC() {
        return hardC;
    }

    public void setHardC(ArrayList<Constraint> hardC) {
        this.hardC = hardC;
    }

    public ArrayList<Constraint> getSoftC() {
        return softC;
    }

    public void setSoftC(ArrayList<Constraint> softC) {
        this.softC = softC;
    }

}
