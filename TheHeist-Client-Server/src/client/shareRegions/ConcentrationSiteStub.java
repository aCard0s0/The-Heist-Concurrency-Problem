package client.shareRegions;

import client.ClientCom;
import message.Message;
import message.MsgInfos;

/**
 * This data type defines a stub for the concentration site
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class ConcentrationSiteStub {

    /**
     * server host name
     *
     * @serialField serverHostName
     */
    private final String serverHostName;
    /**
     * server port number
     *
     * @serialField serverPortNumb
     */
    private final int serverPortNumb;

    /**
     * ConcentrationSiteStub instantiation
     *
     * @param hostName server host name
     * @param portNumber server port number
     */
    public ConcentrationSiteStub(String hostName, int portNumber) {
        serverHostName = hostName;
        serverPortNumb = portNumber;
    }

    /**
     * send amINeeded message and get the response
     *
     * @param idThief the thief id
     * @return out message parameter
     */
    public Integer amINeeded(int idThief) {

        ClientCom con = new ClientCom(serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;

        while (!con.open()) {                                           // aguarda ligação
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        send = new Message(MsgInfos.SM_AMINEEDED, idThief);
        con.writeObject(send);
        reply = (Message) con.readObject();

        if (!reply.getIdMethod().equals(MsgInfos.RM_AMINEEDED)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            System.out.println(reply.toString());
            System.exit(1);
        }
        con.close();

        return reply.getInParam();
    }

    /**
     * send prepareAssaultParty message and get the response
     *
     * @param assaultPartyID assault party Id
     */
    public void prepareAssaultParty(Integer assaultPartyID) {

        ClientCom con = new ClientCom(serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;

        while (!con.open()) {                                           // aguarda ligação
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        send = new Message(MsgInfos.SM_PREPAREASSAULTPARTY, assaultPartyID);
        con.writeObject(send);
        reply = (Message) con.readObject();

        if (!reply.getIdMethod().equals(MsgInfos.RM_PREPAREASSAULTPARTY)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            System.out.println(reply.toString());
            System.exit(1);
        }
        con.close();
    }

    /**
     * send sumUpResults message and get the response
     */
    public void sumUpResults() {

        ClientCom con = new ClientCom(serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;

        while (!con.open()) {                                           // aguarda ligação
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        send = new Message(MsgInfos.SM_SUMUPRESULTS);
        con.writeObject(send);
        reply = (Message) con.readObject();

        if (!reply.getIdMethod().equals(MsgInfos.RM_SUMUPRESULTS)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            System.out.println(reply.toString());
            System.exit(1);
        }
        con.close();
    }

    /**
     * send shutdown signal to the server
     */
    public void shutDown() {

        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message send, reply;

        while (!con.open()) {                                                // aguarda ligação
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        send = new Message(MsgInfos.SM_SHUTDOWN);
        con.writeObject(send);
        reply = (Message) con.readObject();

        if (!reply.getIdMethod().equals(MsgInfos.RM_SHUTDOWN)) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            System.out.println(send.toString());
            System.exit(1);
        }
        con.close();
    }
}
