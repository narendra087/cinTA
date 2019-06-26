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
public class Minggu {
    int nMinggu;
    ArrayList <Hari> listHari = new ArrayList <>();
    
    Minggu(int minggu){
        this.nMinggu = minggu;
    }
    
    void addHari(Hari x){
        listHari.add(x);
    }
}
