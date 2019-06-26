/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bismillahta;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Umar Rizki
 */
public final class ReadFile {

    private int jWeek;
    private int jDays;
    private int jSlot;
    private int jRoom;
    int  jClass, courseId, configId, subpartId, classId, idRomBesar,
            parent, limit, required, penalty, classDis, jCourse;
    String type;
    boolean hasRoom;
    int[] roomcap;
    int[][] travelroom, classSpec, classlimit;
    int[][][][] unroom;
    ArrayList<Room> listRoom = new ArrayList<>();
    private ArrayList <Minggu> timeslot = new ArrayList<>();
    private ArrayList<Kelas> listKelas = new ArrayList<>();
    private ArrayList<Course> listCourse = new ArrayList<>();
    private ArrayList<Distributions> distributions = new ArrayList<>();

    public ReadFile(String filename) throws ParserConfigurationException, SAXException, IOException {
        File fXmlFile = new File("src/Test/" + filename);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();

        Element rootElement = doc.getDocumentElement();
        Element problemChild = (Element) rootElement.getChildNodes();

        jWeek = Integer.parseInt(rootElement.getAttribute("nrWeeks"));
        jDays = Integer.parseInt(rootElement.getAttribute("nrDays"));
        jSlot = Integer.parseInt(rootElement.getAttribute("slotsPerDay")) + 1;

        ReadRoom(problemChild);
        ReadCourse(problemChild);
        ReadCons(problemChild);
        makeTS(jWeek, jDays, idRomBesar);
        
        int []inUrutan;
        Collections.sort(listKelas, new sortTSKelas());
        for (int i = 0; i < listKelas.size(); i++) {
            Kelas kls = listKelas.get(i);
            inUrutan = kls.getInUrutan();
            inUrutan[0]= i;
            kls.setInUrutan(inUrutan);
        }
        Collections.sort(listKelas, new sortRoomKelas());
        for (int i = 0; i < listKelas.size(); i++) {
            Kelas kls = listKelas.get(i);
            inUrutan = kls.getInUrutan();
            inUrutan[1]= i;
            kls.setInUrutan(inUrutan);
        }
        Collections.sort(listKelas, new sortKlsHC());
        for (int i = 0; i < listKelas.size(); i++) {
            Kelas kls = listKelas.get(i);
            inUrutan = kls.getInUrutan();
            inUrutan[2]= i;
            inUrutan[3]= inUrutan[0]+inUrutan[1]+inUrutan[2];
            kls.setInUrutan(inUrutan);
        }
        
        Collections.sort(listKelas, new groupSort(
                new sortTSKelas(), new sortRoomKelas(), new sortKlsHC(), new sortUrutanKls() ));
    }

    void makeTS(int jWeek, int jDays, int jRoom){
        for (int mg = 0; mg < jWeek; mg++) {
            Minggu minggu = new Minggu(mg);
            for (int ds = 0; ds < jDays; ds++) {
                Hari hari = new Hari(ds, jRoom);
                minggu.listHari.add(hari);
            }
            getTimeslot().add(minggu);
        }
    }
    
    Kelas SearchKls(int IdKls){
    for(Kelas a: listKelas){
        if (a.getClassID()==IdKls) {
            return a;
        }
    }
    return null;
    }

    void ReadRoom(Element problemChild){
        NodeList rL = problemChild.getElementsByTagName("rooms");
        Node rN = rL.item(0);
        Element roomsElement = (Element) rN.getChildNodes();
        NodeList childRoomsL = (NodeList) roomsElement.getElementsByTagName("room");

        setjRoom(childRoomsL.getLength());
        ArrayList<Integer> idRom = new ArrayList<>();

        for (int rm = 0; rm < jRoom; rm++) {
            Node nRoom = childRoomsL.item(rm);
            Element roomElement = (Element) nRoom;
            int roomID, capRoom;
            roomID = Integer.parseInt(roomElement.getAttribute("id"));
            capRoom = Integer.parseInt(roomElement.getAttribute("capacity"));

            Room room = new Room(roomID, capRoom);
            listRoom.add(room);
            idRom.add(roomID);
        }
        
        idRomBesar = Collections.max(idRom);
        roomcap = new int[idRomBesar];
        unroom = new int[idRomBesar][getjWeek()][getjDays()][getjSlot()];
        travelroom = new int[idRomBesar][idRomBesar];
//        System.out.println(listRoom.indexOf(SearchRom(95)));

        for (int room = 0; room < childRoomsL.getLength(); room++) {
            Node nRoom = childRoomsL.item(room);
            Element roomElement = (Element) nRoom;
            Room roomA = listRoom.get(room);
            int roomID = Integer.parseInt(roomElement.getAttribute("id"))-1;
//            roomcap[room] = Integer.parseInt(roomElement.getAttribute("capacity"));

            NodeList travelList = (NodeList) roomElement.getElementsByTagName("travel");
            NodeList unRoomList = (NodeList) roomElement.getElementsByTagName("unavailable");

            for (int tvrm = 0; tvrm < travelList.getLength(); tvrm++) {
                Node travelNode = travelList.item(tvrm);
                Element travelElement = (Element) travelNode;
                int trvl = Integer.parseInt(travelElement.getAttribute("room"))-1;
                travelroom[roomID][trvl] = Integer.parseInt(travelElement.getAttribute("value")); //input data setiap travel room ke array
                travelroom[trvl][roomID] = travelroom[roomID][trvl];
                
                
                
            }

            for (int unrm = 0; unrm < unRoomList.getLength(); unrm++) {
                Node unavNode = unRoomList.item(unrm);

                Element unRoomElement = (Element) unavNode;

                String[] wk = unRoomElement.getAttribute("weeks").split("");
                String[] ds = unRoomElement.getAttribute("days").split("");

                int[] a = new int[wk.length];
                int[] b = new int[ds.length];
                int ts = Integer.parseInt(unRoomElement.getAttribute("start"));
                int te = Integer.parseInt(unRoomElement.getAttribute("start")) + Integer.parseInt(unRoomElement.getAttribute("length"));

                //MEMASUKKAN NILAI UNAVAILABLE ROOM KE ARRAY
                for (int j = 0; j < wk.length; j++) {
                    a[j] = Integer.parseInt(wk[j]);
                    if (a[j] == 1) {
                        for (int k = 0; k < ds.length; k++) {
                            b[k] = Integer.parseInt(ds[k]);
                            if (b[k]==1) {
                                for (int l = ts; l <= te; l++) {
                                    unroom[room][j][k][l]=1;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    void ReadCourse(Element problemChild){
        NodeList cL = problemChild.getElementsByTagName("courses");
        Node cN = cL.item(0);
        Element coursesElement = (Element) cN.getChildNodes();
        NodeList courseL = coursesElement.getElementsByTagName("course");
        jCourse = courseL.getLength();

        for (int i = 0; i < courseL.getLength(); i++) {
            Node courseN = courseL.item(i);
            Element courseE = (Element) courseN;
            NodeList configL = courseE.getElementsByTagName("config");

            courseId = Integer.parseInt(courseE.getAttribute("id"));
            Course course = new Course(courseId);
            for (int j = 0; j < configL.getLength(); j++) {
                Node configN = configL.item(j);
                Element configE = (Element) configN;
                NodeList subpartL = configE.getElementsByTagName("subpart");

                configId = Integer.parseInt(configE.getAttribute("id"));
                Config config = new Config(configId);
                for (int k = 0; k < subpartL.getLength(); k++) {
                    Node subpartN = subpartL.item(k);
                    Element subpartE = (Element) subpartN;
                    NodeList classL = subpartE.getElementsByTagName("class");

                    subpartId = Integer.parseInt(subpartE.getAttribute("id"));
                    Subpart subpart = new Subpart(subpartId);
                    for (int l = 0; l < classL.getLength(); l++) {
                        Node classN = classL.item(l);
                        Element classE = (Element) classN;
                        NodeList roomL = classE.getElementsByTagName("room");
                        NodeList timeL = classE.getElementsByTagName("time");

                        classId = Integer.parseInt(classE.getAttribute("id"));
                        limit = Integer.parseInt(classE.getAttribute("limit"));
                        if (classE.hasAttribute("parent")) {
                            parent = Integer.parseInt(classE.getAttribute("parent"));
                        } else {
                            parent = -1;
                        }
                        hasRoom = !classE.hasAttribute("room");
                        Kelas kelas = new Kelas(classId, parent, limit, hasRoom);

                        for (int ruang = 0; ruang < roomL.getLength(); ruang++) {
                            Node roomN = roomL.item(ruang);
                            Element ruangE = (Element) roomN;
                            int rg = Integer.parseInt(ruangE.getAttribute("id"));
                            int pen = Integer.parseInt(ruangE.getAttribute("penalty"));

                            RoomAss room = new RoomAss(rg, pen);
                            kelas.addARoom(room);
                        }

                        for (int time = 0; time < timeL.getLength(); time++) {
                            Node timeN = timeL.item(time);
                            Element timeE = (Element) timeN;
                            String[] wk = timeE.getAttribute("weeks").split("");
                            String[] ds = timeE.getAttribute("days").split("");

                            int[] week = new int[wk.length];
                            int[] day = new int[ds.length];
                            for (int m = 0; m < wk.length; m++) {
                                week[m] = Integer.parseInt(wk[m]);
                            }
                            for (int m = 0; m < ds.length; m++) {
                                day[m] = Integer.parseInt(ds[m]);
                            }

                            int start = Integer.parseInt(timeE.getAttribute("start"));
                            int length = Integer.parseInt(timeE.getAttribute("length"));
                            int penT = Integer.parseInt(timeE.getAttribute("penalty"));

                            TimeAss timeslot = new TimeAss (week, day, start, length, penT);
                            kelas.addTS(timeslot);
            //                System.out.println(z.getDays());
                        }

                        Collections.sort(kelas.getAvailableTS(), new sortTSpen());
                        Collections.sort(kelas.getAvailableroom(), new sortRMpen());

                        subpart.addKelas(kelas);
                        addKelas(kelas);
                    }
                    config.addSubpart(subpart);
                }
                course.addConfig(config);
            }
            addCourse(course);
        }
    }

    void ReadCons(Element problemChild){
        String[] typeS;
        NodeList dL = problemChild.getElementsByTagName("distributions");
        Node dN = dL.item(0);
        Element distribsElement = (Element) dN.getChildNodes();
        NodeList distriL = (NodeList) distribsElement.getElementsByTagName("distribution");

        Distributions distrib = new Distributions();
        for (int distri = 0; distri < distriL.getLength(); distri++) {
            required = 0;
            penalty = 10000;
            int angka1 = -1, angka2 = -1;
            Node distribNode = distriL.item(distri);
            Element disElement = (Element) distribNode;
            NodeList disCild = disElement.getElementsByTagName("class");
            ArrayList<Integer> temp  = new ArrayList<>();
            type = disElement.getAttribute("type");

            if (disElement.hasAttribute("required")) {
                required = 1;
            } else {
                penalty = Integer.parseInt(disElement.getAttribute("penalty"));
            }

            typeS = type.split("[),(]");
            if (typeS.length == 2) {
                angka1 = Integer.parseInt(typeS[1]);
            } else if (typeS.length == 3) {
                angka2 = Integer.parseInt(typeS[2]);
            }

            for (int cons = 0; cons < disCild.getLength(); cons++) {
                Node consNode = disCild.item(cons);
                Element consElement = (Element) consNode;
                classDis = Integer.parseInt(consElement.getAttribute("id"));
                temp.add(classDis);
            }
            Constraint cns = new Constraint(type, angka1, angka2, penalty, temp);

            if (required == 1) {
                distrib.addHard(cns);
                for (int i = 0; i < temp.size(); i++) {
                    Kelas kelas = listKelas.get(temp.get(i)-1);
//                    System.out.println(kelas.getClassID());
                    kelas.addclasHC(cns);
                }
            } else {
                distrib.addSoft(cns);
                for (int i = 0; i < temp.size(); i++) {
                    Kelas kelas = listKelas.get(temp.get(i)-1);
//                    System.out.println(kelas.getClassID());
                    kelas.addclasSC(cns);
                }
            }

        }
        getDistributions().add(distrib);
    }

    Room SearchRom(int IdRoom){
        for(Room a: listRoom){
            if (a.getIdRoom()==IdRoom) {
                return a;
            }
        }
        return null;
    }

    void addCourse(Course a){
        getListCourse().add(a);
    }

    void addKelas(Kelas a){
        listKelas.add(a);
    }

    /**
     * @return the jWeek
     */
    public int getjWeek() {
        return jWeek;
    }

    /**
     * @param jWeek the jWeek to set
     */
    public void setjWeek(int jWeek) {
        this.jWeek = jWeek;
    }

    /**
     * @return the jDays
     */
    public int getjDays() {
        return jDays;
    }

    /**
     * @param jDays the jDays to set
     */
    public void setjDays(int jDays) {
        this.jDays = jDays;
    }

    /**
     * @return the jSlot
     */
    public int getjSlot() {
        return jSlot;
    }

    /**
     * @param jSlot the jSlot to set
     */
    public void setjSlot(int jSlot) {
        this.jSlot = jSlot;
    }

    /**
     * @return the jRoom
     */
    public int getjRoom() {
        return jRoom;
    }

    /**
     * @param jRoom the jRoom to set
     */
    public void setjRoom(int jRoom) {
        this.jRoom = jRoom;
    }

    /**
     * @return the listKelas
     */
    public ArrayList<Kelas> getListKelas() {
        return listKelas;
    }

    /**
     * @return the listCourse
     */
    public ArrayList<Course> getListCourse() {
        return listCourse;
    }

    /**
     * @return the distributions
     */
    public ArrayList<Distributions> getDistributions() {
        return distributions;
    }

    /**
     * @return the timeslot
     */
    public ArrayList <Minggu> getTimeslot() {
        return timeslot;
    }


}
