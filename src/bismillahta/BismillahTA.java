/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bismillahta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Umar Rizki
 */
public class BismillahTA {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        String[] fileName = {"agh-fis-spr17.xml", "agh-ggis-spr17.xml", "bet-fal17.xml", "iku-fal17.xml", "mary-spr17.xml", 
            "muni-fi-spr16.xml", "muni-fsps-spr17.xml", "muni-pdf-spr16c.xml", "pu-llr-spr17.xml", "tg-fal17.xml"};
        int instance = 10;
//        String sourceFile = fileName[instance-1];
        String sourceFile = "bet-sum18.xml";
        
        ArrayList <Minggu> timeslot;
        ArrayList<Kelas> listKelas;
        ArrayList<Distributions> consClass;
        ArrayList<Constraint> HardC;
        ArrayList<Integer> klsNoTS;
        String type;
        int jRoom, jWeek, jDays, jSlot, klsWTS;
        int [][] travel;
        int[][][][] unRoom;
        InitSol initialSol;
        
        
//        jRoom = readFile.getjRoom();
//        jWeek = readFile.getjWeek();
//        jDays = readFile.getjDays();
//        consClass = readFile.getDistributions();
        
        
//        HardC = consClass.get(0).getHardC();
        
        
        
        
//        for (int i = 0; i < listKelas.size(); i++) {
//            Kelas kelas = listKelas.get(i);
//            System.out.println(kelas.getClassID());
//        }
        ReadFile readFile = new ReadFile(sourceFile);
        listKelas = readFile.getListKelas();
        timeslot = readFile.getTimeslot();
        travel = readFile.travelroom;
        unRoom = readFile.unroom;
        
//        int idKlss = listKelas.indexOf(readFile.SearchKls(297));
//        Kelas a = readFile.SearchKls(433);
//        listKelas.remove(a);
//        listKelas.add(0, a);
        
//        do {
//            
//            initialSol = new InitSol(listKelas, timeslot, unRoom, travel);
//            
////            klsNoTS = initialSol.klsNoTS;
//            initialSol.countNoTS();
//            klsWTS = initialSol.klsNoTS.size();
//            System.out.println(klsWTS);
//        } while (klsWTS !=0);
//        listKelas = initialSol.listKelas;
//        timeslot = initialSol.timeslot;
        
        
        initialSol = new InitSol(listKelas, timeslot, unRoom, travel);
        initialSol.countNoTS();
        klsWTS = initialSol.klsNoTS.size();
        System.out.println(klsWTS);
        listKelas = initialSol.listKelas;
        timeslot = initialSol.timeslot;
        
//        for (int i = 0; i < listKelas.size(); i++) {
//            Kelas kelas = listKelas.get(i);
//            
//        }
//        Collections.sort(listKelas, new sortIdKelas());
//        Kelas kelas = listKelas.get(421);
//        System.out.println(kelas.getClassID());
//        int iTS = kelas.getTs();
//        System.out.println(iTS);
//        TimeAss time = kelas.getAvailableTS().get(iTS);
//        ArrayList<Integer> weeks = time.getWeeks();
//        ArrayList<Integer> days = time.getDays();
//        int start = time.getStart();
//        int length = time.getLength();
//        for (int wek = 0; wek < jWeek; wek++) {
//            if (wek==9) {
//                for (int da = 0; da < jDays; da++) {
//                    if (da==4) {
//                        for (int tm = 0; tm < 289; tm++) {
//                            for (int rm = 0; rm < jRoom; rm++) {
//                                System.out.print(timeslot.get(wek).listHari.get(da).timeslot[tm][rm] + " ");
//                            }
//                            System.out.println(" = "+tm);
//                        }
//                    }
//                }
//            }
//        }
        int ini = 0;
        for (int i = 0; i < listKelas.size(); i++) {
            if (listKelas.get(i).isHasRoom() == false && listKelas.get(i).getTs()==-1) {
                System.out.print(listKelas.get(i).getClassID()+ " ");
                ini++;
                System.out.println(ini);
            }
        }
//        System.out.println("");
//        System.out.println(ini);
        System.out.println("");
        int anu = 0;
        for (int i = 0; i < listKelas.size(); i++) {
            if (listKelas.get(i).getTs()==-1) {
                System.out.print(listKelas.get(i).getClassID()+ " ");
            }
        }
    }
    
//    Kelas SearchKls(int IdKls){
//        for(Kelas a: listKelas){
//            if (a.getClassID()==IdKls) {
//                return a;
//            }
//        }
//        return null;
//    }
    
}
