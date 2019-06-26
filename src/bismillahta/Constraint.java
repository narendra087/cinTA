/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bismillahta;

import java.util.ArrayList;

/**
 *
 * @author Umar Rizki
 */
public class Constraint {
//    ArrayList<Integer> smStart, smTime, difTime, smDays, difDays, smWeeks,
//            difWeeks, smRoom, difRoom, overlap, notOverlap, smAttend, precedence,
//            workday, mingap, mxDays, mxDayLoad, mxBreaks, mxBlock;

    private ArrayList<Integer> consClass;
    private String type;
    private int angka1;
    private int angka2;
    private int penalty;
    
    Constraint(String type, int angka1, int angka2, int penalty, ArrayList<Integer> cons){
        this.type = type;
        this.angka1 = angka1;
        this.angka2 = angka2;
        this.penalty = penalty;
        this.consClass = cons;
    }

    /**
     * @return the consClass
     */
    public ArrayList<Integer> getConsClass() {
        return consClass;
    }

    /**
     * @param consClass the consClass to set
     */
    public void setConsClass(ArrayList<Integer> consClass) {
        this.consClass = consClass;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the angka1
     */
    public int getAngka1() {
        return angka1;
    }

    /**
     * @param angka1 the angka1 to set
     */
    public void setAngka1(int angka1) {
        this.angka1 = angka1;
    }

    /**
     * @return the angka2
     */
    public int getAngka2() {
        return angka2;
    }

    /**
     * @param angka2 the angka2 to set
     */
    public void setAngka2(int angka2) {
        this.angka2 = angka2;
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
