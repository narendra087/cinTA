package domoop;

import java.util.ArrayList;

public class Minggu {

    private int nMinggu;
    ArrayList<Hari> listHari = new ArrayList<>();

    Minggu(int minggu) {
        this.nMinggu = minggu;
    }

    void addHari(Hari x) {
        getListHari().add(x);
    }

    /**
     * @return the nMinggu
     */
    public int getnMinggu() {
        return nMinggu;
    }

    /**
     * @return the listHari
     */
    public ArrayList<Hari> getListHari() {
        return listHari;
    }
}
