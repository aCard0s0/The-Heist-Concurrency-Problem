package sharedRegions;

import client.shareRegions.MuseumStub;
import entities.Thief;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import configurations.Config;
import configurations.Ips;
import configurations.Ports;
import entities.states.MasterThiefStates;
import entities.states.OrdThiefSituation;
import entities.states.OrdThiefStates;

/**
 * This data type defines General Repository
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class GeneralRepository {

    /**
     * the state of master thief
     *
     * @serialField masterT_State
     */
    private MasterThiefStates masterT_State;
    /**
     * the state of each ordinary thief
     *
     * @serialField ordinaryT_States
     */
    private OrdThiefStates[] ordinaryT_States;
    /**
     * the situation of each ordinary thief
     *
     * @serialField ordinaryT_Situation
     */
    private OrdThiefSituation[] ordinaryT_Situation;
    /**
     * record the ordinary thieves crawling positions
     *
     * @serialField thievesPosition
     */
    private int[] thievesPosition;
    /**
     * record the ordinary thieves canvas situation
     *
     * @serialField carryingCanvas
     */
    private int[] carryingCanvas;
    /**
     * the maximum displacement of each ordinary thief
     *
     * @serialField ordinaryT_MD
     */
    private int[] ordinaryT_MD;
    /**
     * record the ordinary thieves waiting in the concentration site
     *
     * @serialField thieves
     */
    private List<Integer> thieves;
    /**
     * record the ordinary thieves in the first assault party
     *
     * @serialField assaultParty1Members
     */
    private List<Integer> assaultParty1Members;
    /**
     * record the ordinary thieves in the second assault party
     *
     * @serialField assaultParty2Members
     */
    private List<Integer> assaultParty2Members;
    /**
     * record the room of each assault party
     *
     * @serialField assaultParty2Members
     */
    private int[] assaultPartyRoom;
    /**
     * record the distance from outside gathering to each room
     *
     * @serialField roomDistance
     */
    private int[] roomDistance;
    /**
     * record the number of canvas in each room
     *
     * @serialField roomNCanvas
     */
    private int[] roomNCanvas;
    /**
     * logging file access buffer
     *
     * @serialField logFile
     */
    private BufferedWriter logFile;
    /**
     * General Repository Instantiation
     *
     */
    MuseumStub museumStub;

    public GeneralRepository() {
        this.assaultParty1Members = new ArrayList<>(Config.N_ASSAULTPARTY_THIEVES);
        this.assaultParty2Members = new ArrayList<>(Config.N_ASSAULTPARTY_THIEVES);

        this.ordinaryT_MD = new int[Config.N_ORDINARY_THIEVES];
        this.ordinaryT_States = new OrdThiefStates[Config.N_ORDINARY_THIEVES];
        this.ordinaryT_Situation = new OrdThiefSituation[Config.N_ORDINARY_THIEVES];

        this.assaultPartyRoom = new int[Config.N_ASSAULTPARTIES];
        this.carryingCanvas = new int[Config.N_ORDINARY_THIEVES];
        this.thievesPosition = new int[Config.N_ORDINARY_THIEVES];
        this.thieves = new ArrayList<>(Config.N_ORDINARY_THIEVES);
        this.roomDistance = null;
        this.roomNCanvas = null;
        museumStub = new MuseumStub(Ips.IP_MUSUEM, Ports.PORT_MUSUEM);
        try {
            File file = new File(Config.LOGFILE_PATH);
            this.logFile = new BufferedWriter(new FileWriter(file));
        } catch (IOException ex) {
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * update the state of master thief
     *
     * @param state the new state
     */
    public synchronized void setState(MasterThiefStates state) {
        masterT_State = state;
    }

    /**
     * update the state of ordinary thief
     *
     * @param id thief Id
     * @param state the new state
     */
    public synchronized void setState(int id, OrdThiefStates state) {
        ordinaryT_States[id] = state;
    }

    /**
     * update the situation of ordinary thief
     *
     * @param id the thief Id
     * @param situation the new situation
     */
    public synchronized void setSituation(int id, OrdThiefSituation situation) {
        ordinaryT_Situation[id] = situation;
    }

    /**
     * initialize base properties, namely, the museum properties, the ordinary
     * thieves displacement and list
     *
     * @param tId thief id
     * @param max maximum displacement of ordinary thief
     */
    public synchronized void initializeRepository(int tId, Integer max) {
        /*define museum properties*/
        if (roomDistance == null || roomNCanvas == null) {
            roomDistance = new int[Config.N_ROOMS];
            roomNCanvas = new int[Config.N_ROOMS];

            for (int i = 0; i < Config.N_ROOMS; i++) {
                roomDistance[i] = museumStub.getRoomDistance(i);
                roomNCanvas[i] = museumStub.getNumCanvas(i);
            }
        }
        /*define thieves*/
        thieves.add(tId);
        /*define displacement*/
        ordinaryT_MD[tId] = max;
    }

    /**
     * update the number of canvas in a room, a rollAcanvas operation
     *
     * @param roomId the room to update
     */
    public synchronized void setRollACanvas(int roomId) {
        roomNCanvas[roomId]--;
    }

    /**
     * initialize the assault parties members positions and canvas
     *
     * @param assaultPartyId the assault party to initialize
     */
    public synchronized void initializePartyProperties(int assaultPartyId) {
        if (assaultPartyId == 0) {
            for (Integer thiefId : assaultParty1Members) {
                thievesPosition[thiefId] = 0;
                carryingCanvas[thiefId] = 0;
            }
        } else {
            for (Integer thiefId : assaultParty2Members) {
                thievesPosition[thiefId] = 0;
                carryingCanvas[thiefId] = 0;
            }
        }
    }

    /**
     * update the carrying canvas situation of a ordinary thief
     *
     * @param id thief id
     * @param carryingCanvas carrying canvas situation
     */
    public synchronized void setCarryingCanvas(int id, Integer carryingCanvas) {
        this.carryingCanvas[id] = carryingCanvas;
    }

    /**
     * update the carrying canvas situation of a ordinary thief
     *
     * @param id thief id
     * @return the carrying canvas situation
     */
    public synchronized int getCarryingCanvas(int id) {
        return carryingCanvas[id];
    }

    /**
     * update the position of a ordinary thief
     *
     * @param id thief id
     * @param position the new position
     */
    public synchronized void setPosition(int id, Integer position) {
        thievesPosition[id] = position;
    }

    /**
     * update the room of a party
     *
     * @param asId the assault party Id
     * @param roomId the room Id
     */
    public synchronized void setAssaultPartyRoom(int asId, int roomId) {
        assaultPartyRoom[asId] = roomId;
    }

    /**
     * get the number of thieves waiting in the concentration site
     *
     * @return the number of thieves
     */
    public synchronized int getNumWaitingThieves() {
        return thieves.size();
    }

    /**
     * get the number of member of the first party
     *
     * @return the number of members
     */
    public synchronized int getAssaultParty1Members() {
        return assaultParty1Members.size();
    }

    /**
     * get the number of member of the second party
     *
     * @return the number of members
     */
    public synchronized int getAssaultParty2Members() {
        return assaultParty2Members.size();
    }

    /**
     * update the member of a assault party, set a new member
     *
     * @param tId thief id
     * @param asId the assault party Id
     */
    public synchronized void setAssaultPartyMember(Integer tId, Integer asId) {
        //leave concentration site
        thieves.remove(tId);
        //joins assault party
        if (asId == 0) {
            assaultParty1Members.add(tId);
        } else {
            assaultParty2Members.add(tId);
        }
    }

    /**
     * remove a member in a assault party
     *
     * @param tId thief id
     * @param asId the assault party Id
     */
    public synchronized void removeAssaultPartyMember(Integer tId, Integer asId) {
        //leave assautl party
        if (asId == 0) {
            assaultParty1Members.remove(tId);
        } else {
            assaultParty2Members.remove(tId);
        }
        //wait in concentration site
        thieves.add(tId);
    }

    /**
     * write the initial header of the logging file
     */
    private void logFileHeader() {
        try {
            logFile.write("                             Heist to the Museum - Description of the internal state \n");
            logFile.newLine();
            logFile.write("MstT   Thief 1      Thief 2      Thief 3      Thief 4      Thief 5      Thief 6");
            logFile.newLine();
            logFile.write("Stat  Stat S MD    Stat S MD    Stat S MD    Stat S MD    Stat S MD    Stat S MD");
            logFile.newLine();
            logFile.write("                   Assault party 1                       Assault party 2                       Museum");
            logFile.newLine();
            logFile.write("           Elem 1     Elem 2     Elem 3          Elem 1     Elem 2     Elem 3   Room 1  Room 2  Room 3  Room 4  Room 5");
            logFile.newLine();
            logFile.write("    RId  Id Pos Cv  Id Pos Cv  Id Pos Cv  RId  Id Pos Cv  Id Pos Cv  Id Pos Cv   NP DT   NP DT   NP DT   NP DT   NP DT");
            logFile.newLine();

        } catch (IOException ex) {
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * update the logging file
     */
    public synchronized void reportStatus() {
        String s = masterT_State + "  ";
        String as = "     ";
        /*write thieves status*/
        for (int i = 0; i < Config.N_ORDINARY_THIEVES; i++) {
            s += String.format("%-4s %-1s  %-1s    ", ordinaryT_States[i], ordinaryT_Situation[i], ordinaryT_MD[i]);
        }
        /*write assault party members status*/
        if (!assaultParty1Members.isEmpty()) {
            int c = 0;
            /*write Assault Party room*/
            as += assaultPartyRoom[0] + "    ";
            /*write already in party thieves*/
            for (Integer asM : assaultParty1Members) {
                as += String.format("%-1s  %-2s  %-1s   ", (asM + 1), thievesPosition[asM], carryingCanvas[asM]);
                c++;
            }
            /*write --- for not in party thieves*/
            for (int i = c; i < Config.N_ASSAULTPARTY_THIEVES; i++) {
                as += String.format("-  --  -   ");
            }
        } else {
            /*write dashes*/
            as += String.format("-    -  --  -   -  --  -   -  --  -   ");

        }

        if (!assaultParty2Members.isEmpty()) {
            int c = 0;
            /*write Assault Party room*/
            as += assaultPartyRoom[1] + "    ";
            /*write already in party thieves*/
            for (Integer asM : assaultParty2Members) {
                as += String.format("%-1s  %-2s  %-1s   ", (asM + 1), thievesPosition[asM], carryingCanvas[asM]);
                c++;
            }
            /*write --- for not in party thieves*/
            for (int i = c; i < Config.N_ASSAULTPARTY_THIEVES; i++) {
                as += String.format("-  --  -   ");
            }
        } else {
            /*write dashes*/
            as += String.format("-    -  --  -   -  --  -   -  --  -   ");

        }

        for (int i = 0; i < Config.N_ROOMS; i++) {
            as += String.format("%-2s %-2s   ", roomNCanvas[i], roomDistance[i]);
        }

        try {
            logFile.write(s);
            logFile.newLine();
            logFile.write(as);
            logFile.newLine();
        } catch (IOException ex) {
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * write the final status to the logging file and close it
     *
     * @param result the heist earnings
     */
    public synchronized void reportFinalStatus(int result) {

        String s = "My friends, tonight's effort produced " + result + " priceless paintings!";
        try {
            logFile.newLine();
            logFile.write(s);
            logFile.close();
        } catch (IOException ex) {
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized Integer getOrdinaryT_MD(int id) {
        return ordinaryT_MD[id];
    }

    public synchronized Integer getAspRoom(int asp) {
        return assaultPartyRoom[asp];
    }

    /**
     * write the initial header to the logging file
     */
    public synchronized void reportInitialStatus() {
        logFileHeader();
    }
}
