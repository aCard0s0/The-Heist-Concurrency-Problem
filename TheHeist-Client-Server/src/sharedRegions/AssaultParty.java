package sharedRegions;

import entities.Thief;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import configurations.Config;
import entities.states.OrdThiefSituation;
import entities.states.OrdThiefStates;
import server.proxys.AssaultPartyProxy;

/**
 * This data type defines the AssaultParty shared region
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class AssaultParty {

    /**
     * assault party id
     *
     * @serialField assaultpartyId
     */
    private Integer assaultpartyId;
    /**
     * assault party members, control the crawl movement
     *
     * @serialField thievesFIFO
     */
    private BlockingQueue<Integer> thievesFIFO;
    /**
     * assault party members, helps to reverse direction and wait at room while
     * other thieves crawl
     *
     * @serialField atRoomFIFO
     */
    private BlockingQueue<Integer> atRoomFIFO;
    /**
     * Present position of each thief
     *
     * @serialField thievesPosition
     */
    private int[] thievesPosition;
    /**
     * Carrying Canvas situation of each thief
     *
     * @serialField carryingCanvas
     */
    private int[] carryingCanvas;
    /**
     * the room in which this group will go
     *
     * @serialField roomID
     */
    private Integer roomID;
    private Integer roomDistance;
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
     * Assault Party Instantiation
     *
     * @param assaultpartyId assault party identifier
     */
    public AssaultParty(Integer assaultpartyId) {
        this.assaultpartyId = assaultpartyId;
        this.roomID = null;
        this.carryingCanvas = new int[Config.N_ORDINARY_THIEVES];
        this.thievesPosition = new int[Config.N_ORDINARY_THIEVES];
        this.thievesFIFO = new ArrayBlockingQueue<>(Config.N_ASSAULTPARTY_THIEVES);
        this.waitService_OrdinaryT = new Boolean[Config.N_ORDINARY_THIEVES];
        this.waitService_MasterT = true;
        this.atRoomFIFO = new ArrayBlockingQueue<>(Config.N_ASSAULTPARTY_THIEVES);

        for (int i = 0; i < Config.N_ORDINARY_THIEVES; i++) {
            this.thievesPosition[i] = 0;
            this.carryingCanvas[i] = 0;
            this.waitService_OrdinaryT[i] = true;
        }
    }

    /*
    *  Definition of operations performed in this shared region:
    *     - prapareExcursion
    *     - sendAssaultParty
    *     - crawlin
    *     - reverseDirection
    *     - crawlout
     */
    /**
     * prapareExcursion: the ordinary thieves wait until the last thief join and
     * wake up the master thief
     *
     * Called by the ordinary Thief
     *
     * @param id the thief Id
     */
    public synchronized void prapareExcursion(int id) {
        
        /*enter assault party*/
        thievesFIFO.add(id);
        //update repository
        //((Thief) Thread.currentThread()).getGeneralRepository().setAssaultPartyMember(assaultpartyId);
        ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().setAssaultPartyMember(id,assaultpartyId);
        //wake up master thief if last to join group
        if (thievesFIFO.remainingCapacity() == 0) {
            //wake up master thief
            waitService_MasterT = false;
            notifyAll();
        }
        /*update situation*/
        //((Thief) Thread.currentThread()).getGeneralRepository().setSituation(OrdThiefSituation.IN_PARTY);
        ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().setSituation(id,OrdThiefSituation.IN_PARTY);
        /*block until master thief send the party*/
        while (waitService_OrdinaryT[id]) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        waitService_OrdinaryT[id] = true;
        notifyAll();
    }

    /**
     * crawl in: crawl in processes
     *
     * Called by the ordinary Thief
     * @param id the thief Id
     */
    public synchronized void crawlin(int id) {
        
        /*update repository*/
        ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().setState(id,OrdThiefStates.CRAWLING_INWARDS);

        do {
            /*crawl in*/
            crawlinwards(id);
            thievesFIFO.add(id);
            /*wake up next thief*/
            waitService_OrdinaryT[id] = true;
            waitService_OrdinaryT[thievesFIFO.poll()] = false;
            notifyAll();
            /*block waiting to next oportunity to crawlin*/
            while (waitService_OrdinaryT[id]) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        } while (thievesPosition[id] != roomDistance);

        /*when finished to crawlin*/
        waitService_OrdinaryT[id] = true;
        if (!thievesFIFO.isEmpty()) {
            /*wake up other thief*/
            waitService_OrdinaryT[thievesFIFO.poll()] = false;
        }
        notifyAll();
    }

    /**
     * crawl inwards: apply crawl in policies, crawling according to others
     * positions
     *
     * Called by the ordinary Thief
     * 
     */
    private int crawlinwards(int thiefId) {
        int lastPosition = thievesPosition[(int) thievesFIFO.toArray()[thievesFIFO.size() - 1]] + Config.THIEVES_SEPARATION_LENGH;
        if (lastPosition > roomDistance) {
            lastPosition -= (lastPosition - roomDistance);
        }

        int currentPosition;
        do {
            /* jump with own agility*/
            currentPosition = thievesPosition[thiefId] + ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().getOrdinaryT_MD(thiefId);
            /* veriry current position*/
            for (Integer thiefID : thievesFIFO) {
                if (thievesPosition[thiefID] == currentPosition) {
                    currentPosition--;
                }
            }
            if (currentPosition > lastPosition) {
                currentPosition -= (currentPosition - lastPosition);
            }
            // update position
            thievesPosition[thiefId] = currentPosition;
            ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().setPosition(thiefId,currentPosition);
            ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().reportStatus();

        } while (currentPosition != lastPosition);

        return currentPosition;
    }

    /**
     * reverseDirection: live thievesFIFO that controls the crawl movement and
     * join at room FIFO to wait others
     *
     * Called by the ordinary Thief
     * @param id the thief Id
     */
    public synchronized void reverseDirection(int id) {
        
        /*update repository*/
        ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().setState(id,OrdThiefStates.AT_A_ROOM);
        /* roll a canvas at the museum room*/
        carryingCanvas[id] = ((AssaultPartyProxy) Thread.currentThread()).getMuseumStub().rollACanvas(roomID);
        /*update repository*/
        ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().setCarryingCanvas(id,carryingCanvas[id]);
        /*join atroom waiting fifo*/
        atRoomFIFO.add(id);
        /*if last to join group, prepare crawlout and wake up first thief*/
        if (atRoomFIFO.remainingCapacity() == 0) {
            /* prepare thieves for crawlout*/
            for (int i = 0; i < Config.N_ASSAULTPARTY_THIEVES; i++) {
                thievesFIFO.add(atRoomFIFO.poll());
            }
            //wakes up first thief 
            waitService_OrdinaryT[thievesFIFO.poll()] = false;
            notifyAll();
        }
        /*block */
        while (waitService_OrdinaryT[id]) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        waitService_OrdinaryT[id] = true;
        notifyAll();
    }

    /**
     * crawl out: crawl out processes
     *
     * Called by the ordinary Thief
     * @param id the thief Id
     */
    public synchronized void crawlout(int id) {
        
        /*update repository*/
        ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().setState(id,OrdThiefStates.CRAWLING_OUTWARDS);

        do {
            /*crawl out*/
            crawloutwards(id);
            thievesFIFO.add(id);

            /*wake up next thief and go to sleep*/
            waitService_OrdinaryT[id] = true;
            waitService_OrdinaryT[thievesFIFO.poll()] = false;
            notifyAll();

            while (waitService_OrdinaryT[id]) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

        } while (thievesPosition[id] != 0);

        waitService_OrdinaryT[id] = true;
        if (!thievesFIFO.isEmpty()) {
            /*wake up other thief*/
            waitService_OrdinaryT[thievesFIFO.poll()] = false;
        }
        notifyAll();
    }

    /**
     * crawl outwards: apply crawl out policies, crawling according to others
     * positions
     *
     * Called by the ordinary Thief
     */
    private int crawloutwards(int thiefId) {
        int lastPosition = thievesPosition[(int) thievesFIFO.toArray()[thievesFIFO.size() - 1]] - Config.THIEVES_SEPARATION_LENGH;
        if (lastPosition < 0) {
            lastPosition = 0;
        }

        int currentPosition;
        do {
            /* jump with own agility*/
            currentPosition = thievesPosition[thiefId] - ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().getOrdinaryT_MD(thiefId);
            /* veriry current position*/
            for (Integer thiefID : thievesFIFO) {
                if (thievesPosition[thiefID] == currentPosition) {
                    currentPosition++;
                }
            }

            if (currentPosition < lastPosition) {
                currentPosition += (lastPosition - currentPosition);
            }
            /* update position */
            thievesPosition[thiefId] = currentPosition;

            /* update repository */
            ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().setPosition(thiefId,currentPosition);
            ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().reportStatus();

        } while (currentPosition != lastPosition);

        return currentPosition;
    }
     /**
     * sendAssaultParty: send the party waking up the first thief in the FIFO
     *
     * Called by the Master Thief
     *
     * @param roomID the room this party must go
     */
    public synchronized void sendAssaultParty(Integer roomID) {
        /* define the room to assault*/
        this.roomID = roomID;
        this.roomDistance = ((AssaultPartyProxy) Thread.currentThread()).getMuseumStub().getRoomDistance(roomID);
        
        //update repository
        ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().setAssaultPartyRoom(assaultpartyId, roomID);
        
        /*initialize assault party properties*/
        for (int i = 0; i < Config.N_ORDINARY_THIEVES; i++) {
            thievesPosition[i] = 0;
            carryingCanvas[i] = 0;
        }
        /*update repository*/
        ((AssaultPartyProxy) Thread.currentThread()).getGeneralRepositoryStub().initializePartyProperties(assaultpartyId);
        /* block until last thief wakes him up*/
        while (waitService_MasterT) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        /*wake up first thief in queue*/
        waitService_OrdinaryT[thievesFIFO.poll()] = false;
        waitService_MasterT = true;
        notifyAll();
    }
    /**
     * get the room to go
     *
     * @return the room of this party
     */
    public synchronized Integer getRoomId() {
        return roomID;
    } 
}
