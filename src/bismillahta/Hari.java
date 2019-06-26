/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bismillahta;

/**
 *
 * @author Umar Rizki
 */
public class Hari {
    int nHari;
    int[] timeNoRom;
    int [][] timeslot ;
    
    Hari(int hari, int jRoom){
        this.nHari = hari;
        timeslot = new int [289][jRoom];
        timeNoRom = new int[289];
    }
}
