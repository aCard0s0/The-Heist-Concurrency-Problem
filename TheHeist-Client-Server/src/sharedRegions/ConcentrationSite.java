package sharedRegions;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import configurations.Config;
import entities.states.MasterThiefStates;
import entities.states.OrdThiefSituation;
import entities.states.OrdThiefStates;
import server.proxys.ConcentrationSiteProxy;
/**
 * This data type defines the ConcentrationSite shared region
 *
 * @author Dercio Bucuane: 83457
 */
public class ConcentrationSite {
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
     * Concentration Site Instantiation
     *
     */
    public ConcentrationSite() {
        /* -1 when no more assault party will be created: signals the end of operation to ordinary thieves*/
        this.assaultPartyID = -1;
        this.thievesFIFO = new ArrayBlockingQueue<>(Config.N_ORDINARY_THIEVES);
        this.waitService_OrdinaryT = new Boolean[Config.N_ORDINARY_THIEVES];
        this.waitService_MasterT = true;

        for (int i = 0; i < Config.N_ORDINARY_THIEVES; i++) {
            this.waitService_OrdinaryT[i] = true;
        }
    }
    /*
    *  Definition of operations performed in this shared region:
    *     - amINeeded
    *     - prepareAssaultParty
     */
    
    /**
     * amINeeded: the ordinary thief joins the concentration FIFO, wake up the
     * master thief and blocks waiting to be called
     *
     * Called by an Ordinary Thief
     *
     * @param id the thief Id
     * @return the id of the assaultParty he must join or -1 if the master thief
     * decides to end the operations
     */
    public synchronized Integer amINeeded(int id) {
         
        /*joins the concentration FIFO*/
        thievesFIFO.add(id);
        /*update general repository*/
        ((ConcentrationSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().setState(id, OrdThiefStates.OUTSIDE);
        ((ConcentrationSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().setSituation(id, OrdThiefSituation.WAIT_TO_JOIN_PARTY);
        
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
        return assaultPartyID;
    }
    /**
     * prepareAssaultParty: Master thief calls 3 thieves to join a specific
     * assault party
     *
     * Called by the Master Thief
     *
     * @param assaultPartyID the Id of the assault Party they must join
     * @throws java.lang.InterruptedException throws interrupted exception
     */
    public synchronized void prepareAssaultParty(int assaultPartyID) throws InterruptedException {
        /*asign assault party*/
        this.assaultPartyID = assaultPartyID;
        /*update repository*/
        ((ConcentrationSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().setState(MasterThiefStates.ASSEMBLING_THE_GROUP);
        ((ConcentrationSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().reportStatus();
        
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
    }
    /**
     * sumUpResults: this operations belongs to control and collection site also, this first part is to signal ordinary thieves of the end of operations 
     * 
     * Called by the Master Thief
     *
     */
    public synchronized void sumUpResults() {
        /* -1 define the end of operations*/
        assaultPartyID = -1;
        /*wakes up all thieves waiting in concentration FIFO to inform the end of operations*/
        for (int i = 0; i < Config.N_ORDINARY_THIEVES; i++) {
            this.waitService_OrdinaryT[i] = false;
        }
        waitService_MasterT = true;
        notifyAll();
    }
    
}
