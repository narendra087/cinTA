package domoop;
public class Hari {
    private int nHari;
    int[] timeNoRom;
    int [][] timeslot;
    
    Hari(int hari, int jRoom){
        this.nHari = hari;
        timeslot = new int [289][jRoom];
        timeNoRom = new int[289];
    }

    /**
     * @return the nHari
     */
    public int getnHari() {
        return nHari;
    }
}
