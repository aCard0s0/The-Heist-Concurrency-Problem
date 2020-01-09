package dir_ServerControlCollectionSite.controlCollectionSite;

import dir_ServerControlCollectionSite.interfaces.ControlCollectionSiteInterface;
import dir_ServerControlCollectionSite.interfaces.GeneralRepositoryInterface;
import dir_ServerControlCollectionSite.interfaces.Register;
import dir_ServerControlCollectionSite.structures.Config;
import dir_ServerControlCollectionSite.structures.Ips;
import dir_ServerControlCollectionSite.structures.MasterThiefStates;
import dir_ServerControlCollectionSite.structures.Ports;
import dir_ServerControlCollectionSite.structures.RegistryConfig;
import dir_ServerControlCollectionSite.structures.VectorTimestamp;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This data type defines the control and collection site
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class ControlCollectionSite implements ControlCollectionSiteInterface {

    /**
     * repository instance
     *
     * @serialField repositoryInterface
     */
    private final GeneralRepositoryInterface repositoryInterface;

    /**
     * list of room that were not stolen yet
     *
     * @serialField availableRooms
     */
    private List<Integer> availableRooms;
    /**
     * array to allow thieves to hand canvas to master thief, inform carrying
     * canvas from specific room
     *
     * @serialField room_cv
     */
    private Integer[] room_cv;
    /**
     * counts the collected canvas
     *
     * @serialField numCollectedCanvas
     */
    private int numCollectedCanvas;
    /**
     * ordinary thieves waiting to hand a canvas
     *
     * @serialField thievesFIFO
     */
    private BlockingQueue<Integer> thievesFIFO;
    /**
     * array of conditional variables for each ordinary thief, allows the
     * monitor implementation
     *
     * @serialField waitService_OrdinaryT
     */
    private Boolean[] waitService_OrdinaryT;
    /**
     * conditional variable for the master thief, allows the monitor
     * implementation
     *
     * @serialField waitService_MasterT
     */
    private Boolean waitService_MasterT;

    /**
     * vector clock used
     *
     * @serialField clocks
     */
    private final VectorTimestamp clocks;

    /**
     * Control Collection Site Instantiation
     *
     * @param repositoryInterface repository instance
     */
    public ControlCollectionSite(GeneralRepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;

        this.waitService_MasterT = true;
        this.waitService_OrdinaryT = new Boolean[Config.N_ORDINARY_THIEVES];
        this.room_cv = new Integer[Config.N_ROOMS];
        this.availableRooms = new ArrayList<>();
        this.thievesFIFO = new ArrayBlockingQueue<>(Config.N_ORDINARY_THIEVES);
        for (int i = 0; i < Config.N_ORDINARY_THIEVES; i++) {
            this.waitService_OrdinaryT[i] = true;
        }

        for (int i = 0; i < Config.N_ROOMS; i++) {
            this.availableRooms.add(i);
            this.room_cv[i] = 1;
        }

        this.clocks = new VectorTimestamp(Config.N_ORDINARY_THIEVES + 1, 0);
    }

    /**
     * startOperations: initializes the repository and allow all ordinary
     * thieves to enter the concentration site
     *
     * Called by the Master Thief
     *
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized VectorTimestamp startOperations(VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        repositoryInterface.setState(MasterThiefStates.PLANNING_THE_HEIST, clocks.clone());
        repositoryInterface.reportInitialStatus(clocks.clone());
        repositoryInterface.reportStatus(clocks.clone());

        return clocks.clone();
    }

    /**
     * appraiseSit: "deciding what to do" state, evaluate the option that must
     * be taken
     *
     * Called by the Master Thief
     *
     * @param vt
     * @return the option that must be taken, OPTION_PREPARE_ASSAULTPARTY,
     * OPTION_TAKE_REST or OPTION_END_OF_OPERATIONS
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized Object[] appraiseSit(VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        Object[] result = new Object[2];

        /*update repository*/
        repositoryInterface.setState(MasterThiefStates.DECIDING_WHAT_TO_DO, clocks.clone());
        repositoryInterface.reportStatus(clocks.clone());

        /*if theres all rooms are empty and all thieves are already in the concentration site, end operations*/
        if (availableRooms.isEmpty()
                && repositoryInterface.getNumWaitingThieves(vt.clone())[1] == Config.N_ORDINARY_THIEVES) {
            result[0] = clocks.clone();
            result[1] = Config.OPTION_END_OF_OPERATIONS;
            return result;
        }
        /*if there rooms available and enough thieves to form a party, prepare assault party*/
        if (!availableRooms.isEmpty() && (((int) repositoryInterface.getAssaultParty1Members(clocks.clone())[1] == 0)
                || ((int) repositoryInterface.getAssaultParty2Members(clocks.clone())[1] == 0))) {

            result[0] = clocks.clone();
            result[1] = Config.OPTION_PREPARE_ASSAULTPARTY;
            return result;
        }
        /*otherwise take a rest*/
        result[0] = clocks.clone();
        result[1] = Config.OPTION_TAKE_REST;
        return result;

    }

    /**
     * prepareAssaultParty_1: verifies which assault party is available
     *
     * Called by the Master Thief
     *
     * @param vt
     * @return the Id of the assault party to prepare
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized Object[] prepareAssaultParty_1(VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        Object[] result = new Object[2];

        result[0] = clocks.clone();
        /*return the available assault party*/
        if ((int) repositoryInterface.getAssaultParty1Members(clocks.clone())[1] == 0) {
            result[1] = 0;
            return result;
        } else if ((int) repositoryInterface.getAssaultParty2Members(clocks.clone())[1] == 0) {
            result[1] = 1;
            return result;
        }
        result[1] = -1;
        return result;
    }

    /**
     * prepareAssaultParty_2: verifies which room is available and return its
     * number
     *
     * Called by the Master Thief
     *
     * @param assaultPartyId the assault party id that its been prepared
     * @param vt
     * @return the Id of the room
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized Object[] prepareAssaultParty_2(Integer assaultPartyId, VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        Object[] result = new Object[2];
        result[0] = clocks.clone();
        /*chose a room  based on ends of the room list */
 /*if assaultparty 1 chose room in the beginning of the list, 
        otherwise chose the room at the end of the list*/
        if (assaultPartyId == 0) {
            /*choose from the beginning*/
            result[1] = availableRooms.get(0);
            return result;
        }
        /*choose from the end*/
        result[1] = availableRooms.get(availableRooms.size() - 1);
        return result;
    }

    /**
     * takeRest: wait to be waked up to collect a canvas
     *
     * Called by the Master Thief
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized VectorTimestamp takeRest(VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        /*update repository*/
        repositoryInterface.setState(MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL, clocks.clone());
        repositoryInterface.reportStatus(clocks.clone());
        /*block*/
        while (waitService_MasterT) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        waitService_MasterT = true;
        notifyAll();
        return clocks.clone();
    }

    /**
     * collectCanvas: collect the canvas and verifies if the room is empty
     *
     * Called by the Master Thief
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized VectorTimestamp collectCanvas(VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        
        Integer id;
        /*wake up each thief that is waiting to hand a canvas and collect*/
        while ((id = thievesFIFO.poll()) != null) {
            waitService_MasterT = true;
            waitService_OrdinaryT[id] = false;
            notifyAll();

            while (waitService_MasterT) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

            //remove the empty room from the avalilable rooms list
            for (Integer i = 0; i < Config.N_ROOMS; i++) {
                if (room_cv[i] == 0) {
                    availableRooms.remove(i);
                    room_cv[i] = 1;
                }
            }
        }
        waitService_MasterT = true;
        notifyAll();
        return vt.clone();
    }

    /**
     * handACanvas: hand the canvas and wakes up the master thief
     *
     * Called by the ordinary thief
     * @param id the thief id
     * @param asPid assault party id the thief belongs
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized VectorTimestamp handACanvas(Integer id, Integer asPid, VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        
        /*join the waiting list to hand the canvas*/
        thievesFIFO.add(id);
        /*wake up master thief*/
        waitService_MasterT = false;
        notifyAll();

        while (waitService_OrdinaryT[id]) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        Integer roomId = (int)repositoryInterface.getAspRoom(asPid,clocks.clone())[1];
        /*inform canvas situation*/
        room_cv[roomId] = (int)repositoryInterface.getCarryingCanvas(id,clocks.clone())[1];
        /*if not empty handed, deliver canvas*/
        if (room_cv[roomId] != 0) {
            numCollectedCanvas++;
        }

        /*leave assault party*/
        repositoryInterface.removeAssaultPartyMember(id,asPid,clocks.clone());
   
        waitService_OrdinaryT[id] = true;
        /*wake up master thief*/
        waitService_MasterT = false;
        notifyAll();
        return clocks.clone();
    }

    /**
     * sumUpResults: register the earnings in the repository and updates the
     * logging file, ending the operations
     *
     * Called by the Master Thief
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    @Override
    public VectorTimestamp sumUpResults(VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        
        /*update repository*/
        repositoryInterface.setState(MasterThiefStates.PRESENTING_THE_REPORT, clocks.clone());
        repositoryInterface.reportFinalStatus(numCollectedCanvas, clocks.clone());
        return clocks.clone();
    }

    @Override
    public void signalShutdown() throws RemoteException {
        Register reg = null;
        Registry registry = null;

        String rmiRegHostName = Ips.IP_REGISTRY;
        int rmiRegPortNumb = Ports.PORT_REGISTRY;
        
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException ex) {
            System.out.println("exception: " + ex.getMessage());
        }

        String nameEntryBase = RegistryConfig.registerHandler;
        String nameEntryObject = RegistryConfig.registryControlCollectionSite;
        
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
            System.out.println(" registration exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("not bound exception: " + e.getMessage());
        }
        
         try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(ControlCollectionSite.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("closed.");
    }

}
