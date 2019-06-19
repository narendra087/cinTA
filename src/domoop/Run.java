package domoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Run {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        String[] fileName = {"agh-fis-spr17.xml", "agh-ggis-spr17.xml", "bet-fal17.xml", "iku-fal17.xml", "mary-spr17.xml",
            "muni-fi-spr16.xml", "muni-fsps-spr17.xml", "muni-pdf-spr16c.xml", "pu-llr-spr17.xml", "tg-fal17.xml"};
        int instance = 10;
        String sourceFile = fileName[instance - 1];

        ArrayList<Minggu> timeslot;
        ArrayList<Kelas> listKelas;
        ArrayList<TimeAss> aTS;
        ArrayList<RoomAss> aRM;
        ArrayList<Distributions> consClass;
        ArrayList<Constraint> consList;
        int[][][][] unRoom;
        int[][] travel;
        String type;
        int jRoom, jWeek, jDays, jSlot;
        InitSol initialSol;

        int total = 0;
        int ceks = 0;

        System.out.println("-----INITIAL SOLUTION-----");
        System.out.println("Problem name: " + sourceFile);
        System.out.println("--------------------------");

        do {
            ReadFile readFile = new ReadFile(sourceFile);
            jRoom = readFile.getjRoom();
            jWeek = readFile.getjWeek();
            jDays = readFile.getjDays();
            listKelas = readFile.getListKelas();
            timeslot = readFile.getTimeslot();
            consClass = readFile.getDistributions();
            unRoom = readFile.unroom;
            travel = readFile.travelroom;
            Collections.sort(listKelas, new groupSort(
                    new sortTSKelas(), new sortKlasHC(), new sortRoomKelas()));

            initialSol = new InitSol(listKelas, timeslot, unRoom, travel);
            listKelas = initialSol.listKelas;
            timeslot = initialSol.timeslot;

            initialSol.cekSol();
            ceks = initialSol.sol.size();
            System.out.println(ceks);

            for (int i = 0; i < listKelas.size(); i++) {
                if (listKelas.get(i).getTs() == -1) {
                    System.out.print(listKelas.get(i).getClassID() + " ");
                }
            }
            System.out.println("");
        } while (ceks != 0);

//        for (int i = 0; i < listKelas.size(); i++) {
//            if (listKelas.get(i).getTs() == -1) {
//                System.out.print(listKelas.get(i).getClassID() + " ");
//                total++;
//            }
//        }
//        System.out.println("");
//        System.out.println("Total Kelas belum masuk: " + total);
//        for (int i = 0; i < listKelas.size(); i++) {
//        Kelas kelas = listKelas.get(108);
//        System.out.println(kelas.getClassID());
//            aTS = kelas.getAvailableTS();
//        aRM = kelas.getAvailableroom();
//        for (int j = 0; j < aRM.size(); j++) {
//            System.out.println(aRM.get(j).getRoom_id());
//                for (int k = 0; k < aTS.size(); k++) {
//                    System.out.println(aTS.get(k).getWeeks());
//                }
//            }
//        }
    }

}
