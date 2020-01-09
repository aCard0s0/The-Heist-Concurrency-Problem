package dir_ServerGeneralRepository.generalRepository;

import dir_ServerGeneralRepository.interfaces.AssaultPartyInterface;
import dir_ServerGeneralRepository.interfaces.ConcentrationSiteInterface;
import dir_ServerGeneralRepository.interfaces.ControlCollectionSiteInterface;
import dir_ServerGeneralRepository.interfaces.GeneralRepositoryInterface;
import dir_ServerGeneralRepository.interfaces.MuseumInterface;
import dir_ServerGeneralRepository.interfaces.Register;
import dir_ServerGeneralRepository.structures.Config;
import dir_ServerGeneralRepository.structures.Ips;
import dir_ServerGeneralRepository.structures.MasterThiefStates;
import dir_ServerGeneralRepository.structures.OrdThiefSituation;
import dir_ServerGeneralRepository.structures.OrdThiefStates;
import dir_ServerGeneralRepository.structures.Ports;
import dir_ServerGeneralRepository.structures.RegistryConfig;
import dir_ServerGeneralRepository.structures.VectorTimestamp;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This data type defines the concentration site
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class GeneralRepository implements GeneralRepositoryInterface {

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

    private MuseumInterface museumInterface;

    /**
     * vector clock used
     *
     * @serialField clocks
     */
    private final VectorTimestamp clocks;
    
    private int numberEntitiesRunning;

    /**
     * General Repository Instantiation
     *
     */
    public GeneralRepository() {
        this.numberEntitiesRunning = Config.N_ORDINARY_THIEVES+1;
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
        try {
            File file = new File(Config.LOGFILE_PATH);
            this.logFile = new BufferedWriter(new FileWriter(file));
        } catch (IOException ex) {
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.clocks = new VectorTimestamp(Config.N_ORDINARY_THIEVES + 1, 0);
    }

    @Override
    public synchronized void setState(MasterThiefStates state, VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        masterT_State = state;
    }

    @Override
    public synchronized void setState(int id, OrdThiefStates state, VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        ordinaryT_States[id] = state;
    }

    @Override
    public synchronized void setSituation(int id, OrdThiefSituation situation, VectorTimestamp vt) throws RemoteException {

        clocks.update(vt);
        ordinaryT_Situation[id] = situation;
    }

    @Override
    public synchronized void initializeRepository(int tId, Integer max, VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        /*define museum properties*/
        if (roomDistance == null || roomNCanvas == null) {
            roomDistance = new int[Config.N_ROOMS];
            roomNCanvas = new int[Config.N_ROOMS];

            for (int i = 0; i < Config.N_ROOMS; i++) {
                roomDistance[i] = museumInterface.getRoomDistance(i);
                roomNCanvas[i] = museumInterface.getNumCanvas(i);
            }
        }
        /*define thieves*/
        thieves.add(tId);
        /*define displacement*/
        ordinaryT_MD[tId] = max;
    }

    @Override
    public synchronized void setRollACanvas(int roomId, VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        roomNCanvas[roomId]--;
    }

    @Override
    public synchronized void initializePartyProperties(int assaultPartyId, VectorTimestamp vt) throws RemoteException {
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

    @Override
    public synchronized void setCarryingCanvas(int id, Integer carryingCanvas, VectorTimestamp vt) throws RemoteException {
        this.carryingCanvas[id] = carryingCanvas;
    }

    @Override
    public synchronized int getCarryingCanvas(int id) throws RemoteException {
        return carryingCanvas[id];
    }

    @Override
    public synchronized void setPosition(int id, Integer position, VectorTimestamp vt) throws RemoteException {
        thievesPosition[id] = position;
    }

    @Override
    public synchronized void setAssaultPartyRoom(int asId, int roomId, VectorTimestamp vt) throws RemoteException {
        assaultPartyRoom[asId] = roomId;
    }

    @Override
    public synchronized void reportStatus(VectorTimestamp vt) throws RemoteException {
        String s = masterT_State + "  ";
        String as = "     ";
        /*write thieves status*/
        for (int i = 0; i < Config.N_ORDINARY_THIEVES; i++) {
            s += String.format("%-4s %-1s  %-1s    ", ordinaryT_States[i], ordinaryT_Situation[i], ordinaryT_MD[i]);
        }

        for (int i = 0; i < clocks.getTimeStamp().length; i++) {
            s += String.format("%-3s ", clocks.getTimeStamp()[i]);
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

    @Override
    public synchronized void reportInitialStatus(VectorTimestamp vt) throws RemoteException {
        logFileHeader();
    }

    /**
     * write the initial header of the logging file
     */
    private void logFileHeader() {
        try {
            logFile.write("                             Heist to the Museum - Description of the internal state \n");
            logFile.newLine();
            logFile.write("MstT   Thief 1      Thief 2      Thief 3      Thief 4      Thief 5      Thief 6                VCk");
            logFile.newLine();
            logFile.write("Stat  Stat S MD    Stat S MD    Stat S MD    Stat S MD    Stat S MD    Stat S MD    0   1   2   3   4   5   6");
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

    @Override
    public synchronized Object[] getNumWaitingThieves(VectorTimestamp vt) throws RemoteException {
        Object[] result = new Object[2];
        result[0] = clocks.clone();
        result[1] = thieves.size();
        return result;
    }

    @Override
    public synchronized Object[] getAssaultParty1Members(VectorTimestamp vt) throws RemoteException {
        Object[] result = new Object[2];
        result[0] = clocks.clone();
        result[1] = assaultParty1Members.size();
        return result;
    }

    @Override
    public synchronized Object[] getAssaultParty2Members(VectorTimestamp vt) throws RemoteException {
        Object[] result = new Object[2];
        result[0] = clocks.clone();
        result[1] = assaultParty2Members.size();
        return result;
    }

    @Override
    public synchronized VectorTimestamp setAssaultPartyMember(Integer tId, Integer asId, VectorTimestamp vt) throws RemoteException {
        //leave concentration site
        thieves.remove(tId);
        //joins assault party
        if (asId == 0) {
            assaultParty1Members.add(tId);
        } else {
            assaultParty2Members.add(tId);
        }
        return clocks.clone();
    }

    @Override
    public synchronized VectorTimestamp removeAssaultPartyMember(Integer tId, Integer asId, VectorTimestamp vt) throws RemoteException {
        //leave assautl party
        if (asId == 0) {
            assaultParty1Members.remove(tId);
        } else {
            assaultParty2Members.remove(tId);
        }
        //wait in concentration site
        thieves.add(tId);
        return clocks.clone();
    }

    @Override
    public synchronized Object[] getAspRoom(int asp, VectorTimestamp vt) throws RemoteException {
        Object[] result = new Object[2];
        result[0] = clocks.clone();
        result[1] = assaultPartyRoom[asp];
        return result;
    }

    @Override
    public synchronized Object[] getOrdinaryT_MD(int id, VectorTimestamp vt) throws RemoteException {
        Object[] result = new Object[2];
        result[0] = clocks.clone();
        result[1] = ordinaryT_MD[id];
        return result;
    }

    @Override
    public synchronized Object[] getCarryingCanvas(int id, VectorTimestamp vt) throws RemoteException {
        Object[] result = new Object[2];
        result[0] = clocks.clone();
        result[1] = carryingCanvas[id];
        return result;
    }

    @Override
    public synchronized VectorTimestamp reportFinalStatus(int result, VectorTimestamp vt) throws RemoteException {
        String s = "My friends, tonight's effort produced " + result + " priceless paintings!";
        try {
            logFile.newLine();
            logFile.write(s);
            logFile.close();
        } catch (IOException ex) {
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clocks.clone();
    }

    @Override
    public synchronized MuseumInterface getMuseumInterface() {
        return museumInterface;
    }

    @Override
    public synchronized void setMuseumInterface(MuseumInterface museumInterface) {
        this.museumInterface = museumInterface;
    }

    @Override
    public synchronized void Shutdown() throws RemoteException {
        numberEntitiesRunning--;
        if (numberEntitiesRunning > 0)
            return;
        
        
        Register reg = null;
        Registry registry = null;

        String rmiRegHostName = Ips.IP_REGISTRY;
        int rmiRegPortNumb = Ports.PORT_REGISTRY;
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException ex) {
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        String nameEntryBase = RegistryConfig.registerHandler;
        String nameEntryObject = RegistryConfig.registryGeneralRepository;

        // shutdown concentration site
        try {
            ConcentrationSiteInterface conSiteInterface = (ConcentrationSiteInterface) registry.lookup(RegistryConfig.registryConcentrationSite);
            conSiteInterface.signalShutdown();
        } catch (RemoteException e) {
            System.out.println("Exception thrown while locating shop: " + e.getMessage() + "!");
        } catch (NotBoundException e) {
            System.out.println("concentration is not registered: " + e.getMessage() + "!");
        }
        
        // shutdown control and collection site
        try {
            ControlCollectionSiteInterface collSiteInterface = (ControlCollectionSiteInterface) registry.lookup(RegistryConfig.registryControlCollectionSite);
            collSiteInterface.signalShutdown();
        } catch (RemoteException e) {
            System.out.println("Exception thrown while locating shop: " + e.getMessage() + "!");
        } catch (NotBoundException e) {
            System.out.println("control and collection is not registered: " + e.getMessage() + "!");
        }
        // shutdown assault party site
        try {
            AssaultPartyInterface assSiteInterface = (AssaultPartyInterface) registry.lookup(RegistryConfig.registryAssaultParty1);
            assSiteInterface.signalShutdown();
            
            AssaultPartyInterface assSiteInterface1 = (AssaultPartyInterface) registry.lookup(RegistryConfig.registryAssaultParty2);
            assSiteInterface1.signalShutdown();
        } catch (RemoteException e) {
            System.out.println("Exception thrown while locating shop: " + e.getMessage() + "!");
        } catch (NotBoundException e) {
            System.out.println("control and collection is not registered: " + e.getMessage() + "!");
        }
        // shutdown museum site
        try {
            MuseumInterface mSiteInterface = (MuseumInterface) registry.lookup(RegistryConfig.registryMuseum);
            mSiteInterface.signalShutdown();
        } catch (RemoteException e) {
            System.out.println("Exception thrown while locating shop: " + e.getMessage() + "!");
        } catch (NotBoundException e) {
            System.out.println("control and collection is not registered: " + e.getMessage() + "!");
        }
        
        try {
            reg = (Register) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
        }
        
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("Logging registration exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("Logging not bound exception: " + e.getMessage());
        }

        try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
        }

        System.out.println("Logging closed.");

    }

}
