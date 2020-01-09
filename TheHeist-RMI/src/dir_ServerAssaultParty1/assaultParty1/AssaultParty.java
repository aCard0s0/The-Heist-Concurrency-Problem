/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dir_ServerAssaultParty1.assaultParty1;


import dir_ServerAssaultParty1.interfaces.AssaultPartyInterface;
import dir_ServerAssaultParty1.interfaces.GeneralRepositoryInterface;
import dir_ServerAssaultParty1.interfaces.Register;
import dir_ServerAssaultParty1.structures.Config;
import dir_ServerAssaultParty1.structures.Ips;
import dir_ServerAssaultParty1.structures.OrdThiefSituation;
import dir_ServerAssaultParty1.structures.OrdThiefStates;
import dir_ServerAssaultParty1.structures.Ports;
import dir_ServerAssaultParty1.structures.RegistryConfig;
import dir_ServerAssaultParty1.structures.VectorTimestamp;
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
 *
 * @author User
 */
public class AssaultParty implements AssaultPartyInterface{
    
    /**
     * repository instance
     *
     * @serialField repositoryInterface
     */
    private final GeneralRepositoryInterface repositoryInterface;
    
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
     * vector clock used
     *
     * @serialField clocks
     */
    private final VectorTimestamp clocks;
    
    /**
     * Assault Party Instantiation
     *
     * @param assaultpartyId assault party identifier
     */
    public AssaultParty(GeneralRepositoryInterface repositoryInterface, Integer assaultpartyId) {
        this.repositoryInterface = repositoryInterface;
        
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
        
        this.clocks = new VectorTimestamp(Config.N_ORDINARY_THIEVES+1, 0);
    }
    
    @Override
    public VectorTimestamp prapareExcursion(int id, VectorTimestamp vt) throws RemoteException {
        
        clocks.update(vt);
        
        /*enter assault party*/
        thievesFIFO.add(id);
        //update repository
        //((Thief) Thread.currentThread()).getGeneralRepository().setAssaultPartyMember(assaultpartyId);
        repositoryInterface.setAssaultPartyMember(id,assaultpartyId, clocks.clone());
        //wake up master thief if last to join group
        if (thievesFIFO.remainingCapacity() == 0) {
            //wake up master thief
            waitService_MasterT = false;
            notifyAll();
        }
        /*update situation*/
        //((Thief) Thread.currentThread()).getGeneralRepository().setSituation(OrdThiefSituation.IN_PARTY);
        repositoryInterface.setSituation(id, OrdThiefSituation.IN_PARTY, clocks.clone());
        /*block until master thief send the party*/
        while (waitService_OrdinaryT[id]) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        waitService_OrdinaryT[id] = true;
        notifyAll();
        
        return clocks.clone();
    }

    @Override
    public VectorTimestamp crawlin(int id, VectorTimestamp vt) throws RemoteException {
        
        clocks.update(vt);
        
        /*update repository*/
        repositoryInterface.setState(id,OrdThiefStates.CRAWLING_INWARDS, clocks.clone());

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
        
        return clocks.clone();
    }

    /**
     * crawl inwards: apply crawl in policies, crawling according to others
     * positions
     *
     * Called by the ordinary Thief
     * 
     */
    private int crawlinwards(int thiefId) throws RemoteException {
        int lastPosition = thievesPosition[(int) thievesFIFO.toArray()[thievesFIFO.size() - 1]] + Config.THIEVES_SEPARATION_LENGH;
        if (lastPosition > roomDistance) {
            lastPosition -= (lastPosition - roomDistance);
        }

        int currentPosition;
        do {
            /* jump with own agility*/
            currentPosition = thievesPosition[thiefId] + (int) repositoryInterface.getOrdinaryT_MD(thiefId, clocks.clone())[1];
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
            repositoryInterface.setPosition(thiefId,currentPosition, clocks.clone());
            repositoryInterface.reportStatus(clocks.clone());

        } while (currentPosition != lastPosition);

        return currentPosition;
    }
    
    @Override
    public VectorTimestamp reverseDirection(int id, VectorTimestamp vt) throws RemoteException {
        
        clocks.update(vt);
        
        /*update repository*/
        repositoryInterface.setState(id,OrdThiefStates.AT_A_ROOM, clocks.clone());
        /* roll a canvas at the museum room*/
        carryingCanvas[id] = (int) repositoryInterface.getMuseumInterface().rollACanvas(roomID, clocks.clone())[1];
        /*update repository*/
        repositoryInterface.setCarryingCanvas(id,carryingCanvas[id], clocks.clone());
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
        
        return clocks.clone();
    }

    @Override
    public VectorTimestamp crawlout(int id, VectorTimestamp vt) throws RemoteException {
        
        clocks.update(vt);
        
        /*update repository*/
        repositoryInterface.setState(id,OrdThiefStates.CRAWLING_OUTWARDS, clocks.clone());

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
        
        return clocks.clone();
    }

    /**
     * crawl outwards: apply crawl out policies, crawling according to others
     * positions
     *
     * Called by the ordinary Thief
     */
    private int crawloutwards(int thiefId) throws RemoteException {
        int lastPosition = thievesPosition[(int) thievesFIFO.toArray()[thievesFIFO.size() - 1]] - Config.THIEVES_SEPARATION_LENGH;
        if (lastPosition < 0) {
            lastPosition = 0;
        }

        int currentPosition;
        do {
            /* jump with own agility*/
            currentPosition = thievesPosition[thiefId] - (int) repositoryInterface.getOrdinaryT_MD(thiefId, clocks.clone())[1];
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
            repositoryInterface.setPosition(thiefId,currentPosition, clocks.clone());
            repositoryInterface.reportStatus(clocks.clone());

        } while (currentPosition != lastPosition);

        return currentPosition;
    }
    
    @Override
    public VectorTimestamp sendAssaultParty(Integer roomID, VectorTimestamp vt) throws RemoteException {
        
        clocks.update(vt);
        
        /* define the room to assault*/
        this.roomID = roomID;
        this.roomDistance =  repositoryInterface.getMuseumInterface().getRoomDistance(roomID);
        
        //update repository
        repositoryInterface.setAssaultPartyRoom(assaultpartyId, roomID, clocks.clone());
        
        /*initialize assault party properties*/
        for (int i = 0; i < Config.N_ORDINARY_THIEVES; i++) {
            thievesPosition[i] = 0;
            carryingCanvas[i] = 0;
        }
        /*update repository*/
        repositoryInterface.initializePartyProperties(assaultpartyId, clocks.clone());
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
        
        return clocks.clone();
    }

    @Override
    public Integer getRoomId() throws RemoteException {
        return roomID;
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
        String nameEntryObject1 = RegistryConfig.registryAssaultParty1;
        String nameEntryObject2 = RegistryConfig.registryAssaultParty2;
        
        try {
            reg = (Register) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
        }
        
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject1);
            reg.unbind(nameEntryObject2);
        } catch (RemoteException e) {
            System.out.println(" registration exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("not bound exception: " + e.getMessage());
        }
        
         try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(AssaultParty.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("closed.");
    }

    
}
