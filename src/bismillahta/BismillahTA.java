/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bismillahta;

import java.io.IOException;
import java.util.ArrayList;
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
        String sourceFile = "lums-sum17.xml";
        
        ArrayList <Minggu> timeslot;
        ArrayList<Kelas> listKelas;
        String type, namaFile;
        int klsWTS, penaltyTS, penaltyRM, bobotTS,
                bobotRM, penaltySC, bobotSC, totalPenalty;
        int [] bobot;
        int [][] travel;
        int[][][][] unRoom;
        InitSol initialSol;
        CekSC cekSC = new CekSC();
        Distributions distrib = new Distributions();
        
        ReadFile readFile = new ReadFile(sourceFile);
        listKelas = readFile.getListKelas();
        timeslot = readFile.getTimeslot();
        travel = readFile.travelroom;
        unRoom = readFile.unroom;
        bobot = readFile.bobot;
        distrib = readFile.distrib;
        namaFile = readFile.namaFile;
        System.out.println(namaFile);
        
        
        initialSol = new InitSol(listKelas, timeslot, unRoom, travel);
        initialSol.countNoTS();
        klsWTS = initialSol.klsNoTS.size();
        System.out.println(klsWTS);
        listKelas = initialSol.listKelas;
        timeslot = initialSol.timeslot;
        
        bobotTS = bobot[0];
        bobotRM = bobot[1];
        bobotSC = bobot[2];
        penaltyTS = countTSPenalty(listKelas) * bobotTS;
        penaltyRM = countRMPenalty(listKelas) * bobotRM;
        penaltySC = cekSC.totalPenalty(listKelas, travel, distrib) * bobotSC;
        totalPenalty = penaltyTS + penaltyRM + penaltySC;
        
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
//        System.out.println("");
//        int anu =0;
//        for (int i = 0; i < listKelas.size(); i++) {
//            if (listKelas.get(i).getTs()==-1) {
//                System.out.print(listKelas.get(i).getClassID()+ " ");
//                anu++;
//            }
//        }
//        System.out.println(anu);

        
        System.out.println("Penalty timeslot = " + penaltyTS 
                + ", Penalty room = " + penaltyRM 
                + ", Penalty SC = " + penaltySC);
        System.out.println("Total penalty = " + totalPenalty);
    }
    
//    Kelas SearchKls(int IdKls){
//        for(Kelas a: listKelas){
//            if (a.getClassID()==IdKls) {
//                return a;
//            }
//        }
//        return null;
//    }
    
    public static int countTSPenalty(ArrayList<Kelas> listKelas){
        int totalPenaltiTS=0;
        for (int i = 0; i < listKelas.size(); i++) {
            Kelas kls = listKelas.get(i);
            int timeP = penaltyTS(kls);
            totalPenaltiTS += timeP;
        }
        return totalPenaltiTS;
    }
    
    public static int countRMPenalty(ArrayList<Kelas> listKelas){
        int totalPenaltiRM=0;
        for (int i = 0; i < listKelas.size(); i++) {
            Kelas kls = listKelas.get(i);
            int roomP = penaltyRM(kls);
            totalPenaltiRM += roomP;
        }
        return totalPenaltiRM;
    }
    
    static int penaltyTS(Kelas a){
        int penalty=0;
        int ts = a.getTs();
        if (ts!=-1) {
            TimeAss timeA = a.getAvailableTS().get(ts);
            penalty = timeA.getPenalty();
        }
        return penalty;
    }
    
    static int penaltyRM(Kelas a){
        int penalty=0;
        int rm = a.getRoom();
        if (rm!=-1) {
            RoomAss roomA = a.getAvailableroom().get(rm);
            penalty = roomA.getRoom_pen();
        }
        return penalty;
    }
}
