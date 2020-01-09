package dir_ServerConcentrationSite.concentrationSite;

import dir_ServerConcentrationSite.interfaces.ConcentrationSiteInterface;
import dir_ServerConcentrationSite.interfaces.GeneralRepositoryInterface;
import dir_ServerConcentrationSite.interfaces.Register;
import dir_ServerConcentrationSite.structures.Config;
import dir_ServerConcentrationSite.structures.Ips;
import dir_ServerConcentrationSite.structures.MasterThiefStates;
import dir_ServerConcentrationSite.structures.OrdThiefSituation;
import dir_ServerConcentrationSite.structures.OrdThiefStates;
import dir_ServerConcentrationSite.structures.Ports;
import dir_ServerConcentrationSite.structures.RegistryConfig;
import dir_ServerConcentrationSite.structures.VectorTimestamp;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This data type defines the concentration site 
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class ConcentrationSite implements ConcentrationSiteInterface{
    
    /**
     * repository instance
     *
     * @serialField repositoryInterface
     */
    private final GeneralRepositoryInterface repositoryInterface;
    
    /**
     * Id of the prepared assault party
     *
     * @serialField assaultPartyID
     */
    private Integer assaultPartyID;
    /**
     * ordinary thieves waiting to be called
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
     * Concentration Site Instantiation
     *
     * @param repositoryInterface repository instance
     */
    public ConcentrationSite(GeneralRepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
        
        /* -1 when no more assault party will be created: signals the end of operation to ordinary thieves*/
        this.assaultPartyID = -1;
        this.thievesFIFO = new ArrayBlockingQueue<>(Config.N_ORDINARY_THIEVES);
        this.waitService_OrdinaryT = new Boolean[Config.N_ORDINARY_THIEVES];
        this.waitService_MasterT = true;

        for (int i = 0; i < Config.N_ORDINARY_THIEVES; i++) {
            this.waitService_OrdinaryT[i] = true;
        }
        
        this.clocks = new VectorTimestamp(Config.N_ORDINARY_THIEVES+1, 0);
    }
    
    
    /**
     * amINeeded: the ordinary thief joins the concentration FIFO, wake up the
     * master thief and blocks waiting to be called
     *
     * Called by an Ordinary Thief
     *
     * @param id the thief Id
     * @param vt vector clock
     * @return an object array where: [0]=vector clock, [1]-return parameter
     * @throws java.rmi.RemoteException RMI exception
     */
    @Override
    public synchronized Object[] amINeeded(int id, VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        Object[] result = new Object[2];    
        
        /*joins the concentration FIFO*/
        thievesFIFO.add(id);
        
        /*update general repository*/
        repositoryInterface.setState(id, OrdThiefStates.OUTSIDE, clocks.clone());
        repositoryInterface.setSituation(id, OrdThiefSituation.WAIT_TO_JOIN_PARTY, clocks.clone());
        
         /*wake up master thief*/
        waitService_MasterT = false;
        notifyAll();
        /* block and wait to be called*/
        while (waitService_OrdinaryT[id]) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        waitService_OrdinaryT[id] = true;
        /*wake up master thief*/
        waitService_MasterT = false;
        notifyAll();
        
        result[0] = clocks.clone();
        result[1] = assaultPartyID;
        return result;
        
    }

    /**
     * prepareAssaultParty: Master thief calls 3 thieves to join a specific
     * assault party
     *
     * Called by the Master Thief
     *
     * @param assaultPartyID the Id of the assault Party they must join
     * @param vt vector clock
     * @return timestamp
     * @throws java.rmi.RemoteException RMI exception
     * @throws java.lang.InterruptedException throws interrupted exception
     */
    @Override
    public synchronized VectorTimestamp prepareAssaultParty(int assaultPartyID, VectorTimestamp vt) throws RemoteException, InterruptedException {
        clocks.update(vt);
        /*asign assault party*/
        this.assaultPartyID = assaultPartyID;
        /*update repository*/
        repositoryInterface.setState(MasterThiefStates.ASSEMBLING_THE_GROUP, clocks.clone());
        repositoryInterface.reportStatus(vt);
        
        for (int i = 0; i < Config.N_ASSAULTPARTY_THIEVES; i++) { //calls 3 thieves
            /*blocks and wait to be called by an ordinary thief: amINeeded*/
            while (waitService_MasterT) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            /* take one thief from the concentration FIFO and wakes him up*/
            Integer tid = thievesFIFO.take();
            waitService_OrdinaryT[tid] = false;
            /*block*/
            waitService_MasterT = true;
            notifyAll();
        }
        waitService_MasterT = true;
        notifyAll();
        return clocks.clone();
    }

    /**
     * sumUpResults: this operations belongs to control and collection site also, this first part is to signal ordinary thieves of the end of operations 
     * 
     * Called by the Master Thief
     *
     * @param vt vector clock
     * @return  timestamp
     * @throws java.rmi.RemoteException RMI exception
     */
    @Override
    public synchronized VectorTimestamp sumUpResults(VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        
        /* -1 define the end of operations*/
        assaultPartyID = -1;
        /*wakes up all thieves waiting in concentration FIFO to inform the end of operations*/
        for (int i = 0; i < Config.N_ORDINARY_THIEVES; i++) {
            this.waitService_OrdinaryT[i] = false;
        }
        waitService_MasterT = true;
        notifyAll();
        
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
        String nameEntryObject = RegistryConfig.registryConcentrationSite;
        
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
            Logger.getLogger(ConcentrationSite.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Concentration Site closed.");
    }
    
}
