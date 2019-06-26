/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bismillahta;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Umar Rizki
 */
public class Distributions {

    /**
     * @return the hardC
     */
    public ArrayList<Constraint> getHardC() {
        return hardC;
    }

    /**
     * @param hardC the hardC to set
     */
    public void setHardC(ArrayList<Constraint> hardC) {
        this.hardC = hardC;
    }

    /**
     * @return the softC
     */
    public ArrayList<Constraint> getSoftC() {
        return softC;
    }

    /**
     * @param softC the softC to set
     */
    public void setSoftC(ArrayList<Constraint> softC) {
        this.softC = softC;
    }
    private ArrayList<Constraint> hardC = new ArrayList<>();
    private ArrayList<Constraint> softC = new ArrayList<>();
    
    Distributions(){
        
    }
    
//    void addListCons (ArrayList<Integer> cons1, ArrayList<Integer> cons2){
//        if (true) {
//            
//            Set<Integer> set = new LinkedHashSet<>(cons1);
//            set.addAll(cons2);
//            ArrayList<Integer> combinedList = new ArrayList<>(set);
//        }
//    }
    
    void addHard(Constraint a){
        getHardC().add(a);
    }
    
    void addSoft(Constraint a){
        getSoftC().add(a);
    }
    
}
