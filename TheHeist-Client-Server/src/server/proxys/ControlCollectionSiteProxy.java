
package server.proxys;

import client.shareRegions.GeneralRepositoryStub;
import configurations.Ips;
import configurations.Ports;
import message.Message;
import message.MessageException;
import server.ServerCom;
import server.interfaces.ControlCollectionSiteInterface;

/**
 * This data type defines the control and collection site proxy
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class ControlCollectionSiteProxy extends Thread{
    /**
     * counts the number of threads thrown
     * @serialField nProxy
     */
    private static int nProxy = 0;           
    /**
     * ServerCom instance
     * @serial sconi
     */
    private final ServerCom sconi;       
    /**
     * general repository stub instance
     * @serialField generalRepositoryStub
     */
    private final GeneralRepositoryStub generalRepositoryStub;
    /**
     * control and collection site interface instance
     * @serialField collectionSiteInterface
     */
    private final ControlCollectionSiteInterface collectionSiteInterface;  
    /**
     * control and collection site proxy instantiation
     * @param sconi communication channel instance
     * @param collectionSiteInterface control and collection site interface instance
     */
    public ControlCollectionSiteProxy(ServerCom sconi, ControlCollectionSiteInterface collectionSiteInterface) {
        this.sconi = sconi;
        generalRepositoryStub = new GeneralRepositoryStub(Ips.IP_GENERAL_REPOSITORY, Ports.PORT_GENERAL_REPOSITORY);
        this.collectionSiteInterface = collectionSiteInterface;
    }

    @Override
    public void run() {
        Message inMsg = null, outMsg = null;  
        inMsg = (Message) sconi.readObject();  
        try { 
            outMsg = collectionSiteInterface.processAndReply(inMsg);       
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
