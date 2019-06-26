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
public class Subpart {
    private ArrayList<Kelas> listKelas = new ArrayList<>();
    private int subpartId;
    
    Subpart(int subpartId){
        this.subpartId = subpartId;
    }
    
    void addKelas(Kelas a){
        getListKelas().add(a);
    }

    /**
     * @return the listKelas
     */
    public ArrayList<Kelas> getListKelas() {
        return listKelas;
    }

    /**
     * @param listKelas the listKelas to set
     */
    public void setListKelas(ArrayList<Kelas> listKelas) {
        this.listKelas = listKelas;
    }

    /**
     * @return the subpartId
     */
    public int getSubpartId() {
        return subpartId;
    }

    /**
     * @param subpartId the subpartId to set
     */
    public void setSubpartId(int subpartId) {
        this.subpartId = subpartId;
    }
    
}
