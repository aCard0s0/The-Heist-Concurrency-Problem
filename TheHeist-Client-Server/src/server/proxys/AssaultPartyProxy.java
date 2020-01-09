package server.proxys;

import client.shareRegions.GeneralRepositoryStub;
import client.shareRegions.MuseumStub;
import configurations.Ips;
import configurations.Ports;
import entities.states.MasterThiefStates;
import entities.states.OrdThiefStates;
import message.Message;
import message.MessageException;
import server.ServerCom;
import server.interfaces.AssaultPartyInterface;

/**
 * This data type defines the assault party proxy
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class AssaultPartyProxy extends Thread{
    /**
     * counts the number of threads thrown
     * @serialField nProxy
     */
    private static int nProxy = 0;           
    /**
     * ServerCom instance
     * @serialField sconi
     */
    private final ServerCom sconi;          
    /**
     * assault party interface instance
     * @serialField partyInter
     */
    private final AssaultPartyInterface partyInter;    
    /**
     * general repository stub instance
     * @serialField generalRepositoryStub
     */
    private final GeneralRepositoryStub generalRepositoryStub;
    /**
     * museum stub instance
     * @serialField museumStub
     */
    private final MuseumStub museumStub;
    /**
     * master thief state instance
     * @serialField mtState
     */
    private MasterThiefStates mtState;
    /**
     * ordinary thief state instance
     * @serialField tState
     */
    private OrdThiefStates  tState;
    /**
     * AssaultPartyProxy instantiation
     * @param sconi communication channel instance
     * @param partyInter assault party interface instance
     */
    public AssaultPartyProxy(ServerCom sconi, AssaultPartyInterface partyInter) {
        this.sconi = sconi;
        this.partyInter = partyInter;
        generalRepositoryStub = new GeneralRepositoryStub(Ips.IP_GENERAL_REPOSITORY, Ports.PORT_GENERAL_REPOSITORY);
        museumStub = new MuseumStub(Ips.IP_MUSUEM, Ports.PORT_MUSUEM);
    }
    
    @Override
    public void run() {
        
        Message inMsg = null, outMsg = null;  
        
        inMsg = (Message) sconi.readObject();  
        try { 
            outMsg = partyInter.processAndReply(inMsg);    
        } catch (MessageException e) { 
            System.out.println("Thread " + getName () + ": " + e.getMessage () + "!");
            System.out.println(e.getMessageVal ().toString ());
            System.exit (1);
        }
        sconi.writeObject(outMsg);                          
        sconi.close ();                                 
    }
    /**
     * get the ServerCom instance
     * @return the instance
     */
    public ServerCom getSconi() {
        return sconi;
    }
    /**
     * get the general repository stub
     * @return the instance
     */
    public GeneralRepositoryStub getGeneralRepositoryStub() {
        return generalRepositoryStub;
    }
     /**
     * get the museum stub
     * @return the instance
     */
    public MuseumStub getMuseumStub() {
        return museumStub;
    }
}
