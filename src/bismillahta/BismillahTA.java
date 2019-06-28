package bismillahta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Run {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        String[] fileName = {"agh-fis-spr17.xml", "agh-ggis-spr17.xml", "bet-fal17.xml", "iku-fal17.xml", "mary-spr17.xml",
            "muni-fi-spr16.xml", "muni-fsps-spr17.xml", "muni-pdf-spr16c.xml", "pu-llr-spr17.xml", "tg-fal17.xml", "lums-sum17.xml",
            "bet-sum18.xml", "pu-cs-fal07.xml", "pu-c8-spr07.xml"};
        int instance = 13;
        String sourceFile = fileName[instance - 1];

        ArrayList<Minggu> timeslot;
        ArrayList<Kelas> listKelas;
        int[][][][] unRoom;
        int[][] travel;
        int jRoom, jWeek, jDays, jSlot;
        InitSol2 initialSol;
        CekSC cekSC = new CekSC();
        Distributions distrib = new Distributions();

        int total = 0;
        int ceks = 0;

        System.out.println("-----INITIAL SOLUTION-----");
        System.out.println("Problem name: " + sourceFile);
        System.out.println("--------------------------");

//        do {
        ReadFile readFile = new ReadFile(sourceFile);
        jRoom = readFile.getjRoom();
        jWeek = readFile.getjWeek();
        jDays = readFile.getjDays();
        listKelas = readFile.getListKelas();
        timeslot = readFile.getTimeslot();
        unRoom = readFile.unroom;
        travel = readFile.travelroom;
        distrib = readFile.distrib;
        initialSol = new InitSol2(listKelas, timeslot, unRoom, travel);
        listKelas = initialSol.listKelas;
        timeslot = initialSol.timeslot;

        initialSol.cekSol();
        ceks = initialSol.sol.size();
        System.out.println();

        System.out.println("Initial Solution Terpenuhi!");
        for (int i = 0; i < listKelas.size(); i++) {
            if (listKelas.get(i).getTs() == -1) {
                System.out.print(listKelas.get(i).getClassID() + " ");
            }
        }
        System.out.println("");
        //        } while (ceks != 0);

        System.out.println("");
        int anu = 0;
        for (int i = 0; i < listKelas.size(); i++) {
            if (listKelas.get(i).getTs() == -1) {
                System.out.print(listKelas.get(i).getClassID() + " ");
                anu++;
            }
        }

        int totalPenaltySC = cekSC.totalPenalty(listKelas, travel, distrib);
        System.out.println(anu);
        System.out.println("Total penalty = " + countPenalty(listKelas));
        System.out.println("Penalty SC = " + totalPenaltySC);

    }

    public static int countPenalty(ArrayList<Kelas> listKelas) {
        int totalPenalti = 0;
        for (int i = 0; i < listKelas.size(); i++) {
            Kelas kls = listKelas.get(i);
            int timeP = penaltyTS(kls);
            int roomP = penaltyRM(kls);
            totalPenalti += timeP + roomP;
        }
        return totalPenalti;
    }

    static int penaltyTS(Kelas a) {
        int penalty = 0;
        int ts = a.getTs();
        if (ts != -1) {
            TimeAss timeA = a.getAvailableTS().get(ts);
            penalty = timeA.getPenalty();
        }
        return penalty;
    }

    static int penaltyRM(Kelas a) {
        int penalty = 0;
        int rm = a.getRoom();
        if (rm != -1) {
            RoomAss roomA = a.getAvailableroom().get(rm);
            penalty = roomA.getRoom_pen();
        }
        return penalty;
    }
}
