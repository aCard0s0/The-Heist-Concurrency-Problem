package server.proxys;

import client.shareRegions.GeneralRepositoryStub;
import configurations.Ips;
import configurations.Ports;
import message.Message;
import message.MessageException;
import server.ServerCom;
import server.interfaces.MuseumInterface;

/**
 * This data type defines the museum proxy
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class MuseumProxy extends Thread {

    /**
     * counts the number of threads thrown
     *
     * @serialField nProxy
     */
    private static int nProxy = 0;
    /**
     * ServerCom instance
     *
     * @serial sconi
     */
    private final ServerCom sconi;
    /**
     * museum interface instance
     *
     * @serialField museumInterface
     */
    private final MuseumInterface museumInterface;
    /**
     * general repository stub instance
     *
     * @serialField generalRepositoryStub
     */
    private final GeneralRepositoryStub repoStub;

    /**
     * museum proxy instantiation
     *
     * @param sconi communication channel instance
     * @param museumInterface museum interface instance
     */
    public MuseumProxy(ServerCom sconi, MuseumInterface museumInterface) {
        this.sconi = sconi;
        this.museumInterface = museumInterface;
        repoStub = new GeneralRepositoryStub(Ips.IP_GENERAL_REPOSITORY, Ports.PORT_GENERAL_REPOSITORY);
    }

    @Override
    public void run() {
        Message inMsg = null, outMsg = null;
        inMsg = (Message) sconi.readObject();
        try {
            outMsg = museumInterface.processAndReply(inMsg);
        } catch (MessageException e) {
            System.out.println("Thread " + getName() + ": " + e.getMessage() + "!");
            System.out.println(e.getMessageVal().toString());
            System.exit(1);
        }
        sconi.writeObject(outMsg);
        sconi.close();
    }

    /**
     * get the ServerCom instance
     *
     * @return the instance
     */
    public ServerCom getSconi() {
        return sconi;
    }

    /**
     * get the general repository stub
     *
     * @return the instance
     */
    public GeneralRepositoryStub getGeneralRepositoryStub() {
        return repoStub;
    }
}
