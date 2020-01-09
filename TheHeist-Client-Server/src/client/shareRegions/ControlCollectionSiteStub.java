package client.shareRegions;

import client.ClientCom;
import message.Message;
import message.MsgInfos;

/**
 * This data type defines a stub for the control and collection site
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class ControlCollectionSiteStub {
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
     * ControlCollectionSiteStub instantiation
     *
     * @param serverHostName server host name
     * @param serverPortNumb server port number
     */
    public ControlCollectionSiteStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }
    /**
     * send startOperations message and get the response
     *
     * @return output message parameter
     */
    public Integer startOperations() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_STARTOPERATIONS);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_STARTOPERATIONS)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send appraiseSit message and get the response
     *
     * @return output message parameter
     */
    public Integer appraiseSit() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_APPRAISESIT);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_APPRAISESIT)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send sumUpResults message and get the response
     *
     * @return output message parameter
     */
    public Integer sumUpResults() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_SUMUPRESULTS);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_SUMUPRESULTS)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send takeRest message and get the response
     *
     * @return output message parameter
     */
    public Integer takeRest() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_TAKEREST);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_TAKEREST)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send collectCanvas message and get the response
     *
     * @return output message parameter
     */
    public Integer collectCanvas() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_COLLECTCANVAS);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_COLLECTCANVAS)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send prepareAssaultParty message and get the response
     *
     * @return output message parameter
     */
    public Integer prepareAssaultParty() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_PREPAREASSAULTPARTY);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_PREPAREASSAULTPARTY)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send prepareAssaultParty_1 message and get the response
     *
     * @return output message parameter
     */
    public Integer prepareAssaultParty_1() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_PREPAREASSAULTPARTY_1);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_PREPAREASSAULTPARTY_1)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send prepareAssaultParty_2 message and get the response
     *
     * @param aspId assault party id
     * @return output message parameter
     */
    public Integer prepareAssaultParty_2(Integer aspId) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_PREPAREASSAULTPARTY_2,aspId);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_PREPAREASSAULTPARTY_2)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send handACanvas message and get the response
     *
     * @param id thief id
     * @param asp assault party id
     * @return output message parameter
     */
    public Integer handACanvas(int id,Integer asp) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_HANDACANVAS,id,asp);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_HANDACANVAS)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send shutDown message and get the response
     *
     */
    public void shutDown () {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb);
        Message send, reply;

        while (!con.open()) {                        
            try { 
                Thread.sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        send = new Message (MsgInfos.SM_SHUTDOWN);
        con.writeObject (send);
        
        reply = (Message) con.readObject ();
        if (!reply.getIdMethod().equals(MsgInfos.RM_SHUTDOWN)) { 
            System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid type!");
            System.out.println(send.toString ());
            System.exit (1);
        }
        con.close ();
    }
}
