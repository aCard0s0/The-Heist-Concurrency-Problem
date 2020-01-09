package sharedRegions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import configurations.Config;
import entities.states.MasterThiefStates;
import server.proxys.ControlCollectionSiteProxy;

/**
 * This data type defines the Control and Collection Site shared region
 *
 * @author Dercio Bucuane: 83457
 */
public class ControlCollectionSite {

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
     * Control and Collection Site Instantiation
     *
     */
    public ControlCollectionSite() {
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
    }

    /*
    *  Definition of operations performed in this shared region:
    *     - startOperations
    *     - appraiseSit
    *     - sumUpResults
    *     - takeRest
    *     - collectCanvas
    *     - handACanvas
    *     - prepareAssaultParty
     */
    /**
     * startOperations: initializes the repository and allow all ordinary
     * thieves to enter the concentration site
     *
     * Called by the Master Thief
     *
     */
    public synchronized void startOperations() {
        ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().setState(MasterThiefStates.PLANNING_THE_HEIST);

        ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().reportInitialStatus();
        ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().reportStatus();
    }

    /**
     * appraiseSit: "deciding what to do" state, evaluate the option that must
     * be taken
     *
     * Called by the Master Thief
     *
     * @return the option that must be taken, OPTION_PREPARE_ASSAULTPARTY,
     * OPTION_TAKE_REST or OPTION_END_OF_OPERATIONS
     */
    public synchronized int appraiseSit() {
        /*update repository*/
        ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().setState(MasterThiefStates.DECIDING_WHAT_TO_DO);
        ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().reportStatus();

        /*if theres all rooms are empty and all thieves are already in the concentration site, end operations*/
        if (availableRooms.isEmpty()
                && ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().getNumWaitingThieves() == Config.N_ORDINARY_THIEVES) {
            return Config.OPTION_END_OF_OPERATIONS;
        }
        /*if there rooms available and enough thieves to form a party, prepare assault party*/
        if (!availableRooms.isEmpty() && (((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().getAssaultParty1Members() == 0
                || ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().getAssaultParty2Members() == 0)) {
            return Config.OPTION_PREPARE_ASSAULTPARTY;
        }
        /*otherwise take a rest*/
        return Config.OPTION_TAKE_REST;
    }

    /**
     * prepareAssaultParty_1: verifies which assault party is available
     *
     * Called by the Master Thief
     *
     * @return the Id of the assault party to prepare
     */
    public synchronized int prepareAssaultParty_1() {
        /*return the available assault party*/
        if (((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().getAssaultParty1Members() == 0) {
            return 0;
        } else if (((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().getAssaultParty2Members() == 0) {
            return 1;
        }
        return -1;
    }

    /**
     * prepareAssaultParty_2: verifies which room is available and return its
     * number
     *
     * Called by the Master Thief
     *
     * @param assaultPartyId the assault party id that its been prepared
     * @return the Id of the room
     */
    public synchronized int prepareAssaultParty_2(Integer assaultPartyId) {
        /*chose a room  based on ends of the room list */
        /*if assaultparty 1 chose room in the beginning of the list, 
        otherwise chose the room at the end of the list*/
        if (assaultPartyId == 0) {
            /*choose from the beginning*/
            return availableRooms.get(0);
        }
        /*choose from the end*/
        return availableRooms.get(availableRooms.size() - 1);
    }
    /**
     * takeRest: wait to be waked up to collect a canvas
     *
     * Called by the Master Thief
     */
    public synchronized void takeRest() {
        /*update repository*/
        ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().setState(MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL);
        ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().reportStatus();
        /*block*/
        while (waitService_MasterT) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        waitService_MasterT = true;
        notifyAll();
    }
    /**
     * collectCanvas: collect the canvas and verifies if the room is empty
     *
     * Called by the Master Thief
     */
    public synchronized void collectCanvas() {

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
    }
    /**
     * handACanvas: hand the canvas and wakes up the master thief
     *
     * Called by the ordinary thief
     * @param id the thief id
     * @param asPid assault party id the thief belongs
     */
    public synchronized void handACanvas(Integer id,Integer asPid) {
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

        Integer roomId = ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().getAspRoom(asPid);
        /*inform canvas situation*/
        room_cv[roomId] = ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().getCarryingCanvas(id);
        /*if not empty handed, deliver canvas*/
        if (room_cv[roomId] != 0) {
            numCollectedCanvas++;
        }

        /*leave assault party*/
        ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().removeAssaultPartyMember(id,asPid);
   
        waitService_OrdinaryT[id] = true;
        /*wake up master thief*/
        waitService_MasterT = false;
        notifyAll();
    }

    /**
     * sumUpResults: register the earnings in the repository and updates the
     * logging file, ending the operations
     *
     * Called by the Master Thief
     */
    public synchronized void sumUpResults() {
        /*update repository*/
        ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().setState(MasterThiefStates.PRESENTING_THE_REPORT);
        ((ControlCollectionSiteProxy) Thread.currentThread()).getGeneralRepositoryStub().reportFinalStatus(numCollectedCanvas);
    }

}
