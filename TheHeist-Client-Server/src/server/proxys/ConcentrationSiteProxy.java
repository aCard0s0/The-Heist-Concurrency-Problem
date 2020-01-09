package server.proxys;

import client.shareRegions.GeneralRepositoryStub;
import configurations.Ips;
import configurations.Ports;
import entities.states.MasterThiefStates;
import entities.states.OrdThiefStates;
import message.Message;
import message.MessageException;
import server.ServerCom;
import server.interfaces.ConcentrationSiteInterface;

/**
 * This data type defines the concentration site proxy
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class ConcentrationSiteProxy extends Thread{
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
     * concentration site interface instance
     * @serialField concInter
     */
    private final ConcentrationSiteInterface concInter;    
    /**
     * general repository stub instance
     * @serialField generalRepositoryStub
     */
    private GeneralRepositoryStub generalRepositoryStub;
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
     * concentration site proxy instantiation
     * @param sconi communication channel instance
     * @param concInter concentration site interface instance
     */
    public ConcentrationSiteProxy(ServerCom sconi, ConcentrationSiteInterface concInter) {
        this.sconi = sconi;
        this.concInter = concInter;
        generalRepositoryStub = new GeneralRepositoryStub(Ips.IP_GENERAL_REPOSITORY, Ports.PORT_GENERAL_REPOSITORY);
    }
    
    @Override
    public void run() {
        
        Message inMsg = null, outMsg = null; 
        
        inMsg = (Message) sconi.readObject(); 
        try { 
            outMsg = concInter.processAndReply(inMsg);        
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
}
