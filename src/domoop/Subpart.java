package domoop;

import java.util.ArrayList;

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
