
package client.shareRegions;

import client.ClientCom;
import entities.states.MasterThiefStates;
import entities.states.OrdThiefSituation;
import entities.states.OrdThiefStates;
import message.Message;
import message.MsgInfos;

/**
 * This data type defines a stub for the general repository
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class GeneralRepositoryStub {
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
     * GeneralRepositoryStub instantiation
     *
     * @param hostName server host name
     * @param portNumber server port number
     */
    public GeneralRepositoryStub(String hostName, int portNumber) {
        serverHostName = hostName;
        serverPortNumb = portNumber;
    }
    /**
     * send setState message and get the response
     *
     * @param state the state of master thief
     */
    public void setState(MasterThiefStates state) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_SETSTATEMT, state);
        
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_SETSTATEMT)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send setState message and get the response
     *
     * @param id thief id
     * @param state the state of ordinary thief
     */
    public void setState(int id,OrdThiefStates state) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_SETSTATET, state);
        send.setIdSender(id);
        
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_SETSTATET)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send setSituation message and get the response
     *
     * @param id thief id
     * @param situation the thief situation
     */
    public void setSituation(int id,OrdThiefSituation situation) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_SETSITUATION, situation);
        send.setIdSender(id);
        
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_SETSITUATION)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send initializeRepository message and get the response
     *
     * @param id thief id
     * @param max the thief maximum displacement
     */
    public void initializeRepository(int id,Integer max) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_INITIALIZEREPOSITORY,id,max);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_INITIALIZEREPOSITORY)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send setRollACanvas message and get the response
     *
     * @param roomId the room id
     */
    public void setRollACanvas(Integer roomId) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_SETROLLACANVAS, roomId);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_SETROLLACANVAS)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send initializePartyProperties message and get the response
     *
     * @param assaultPartyId the assault party id
     */
    public void initializePartyProperties(Integer assaultPartyId) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_INITIALIZEPARTYPROPERTIES, assaultPartyId);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_INITIALIZEPARTYPROPERTIES)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send setCarryingCanvas message and get the response
     *
     * @param id the thief id
     * @param carryingCanvas if thief carries canvas
     */
    public void setCarryingCanvas(int id, Integer carryingCanvas) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_SETCARRYINGCANVAS, carryingCanvas);
        send.setIdSender(id);
        
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_SETCARRYINGCANVAS)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send getCarryingCanvas message and get the response
     *
     * @param id the thief id
     * @return output message parameter
     */
    public int getCarryingCanvas(int id) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_getCarryingCanvas, id);
        
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_getCarryingCanvas)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send setPosition message and get the response
     *
     * @param id the thief id
     * @param position the thief position
     */
    public void setPosition(int id,Integer position) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_setPosition, position);
        send.setIdSender(id);
        
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_setPosition)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send setAssaultPartyRoom message and get the response
     *
     * @param asId the assault party id
     * @param roomId the room id
     */
    public void setAssaultPartyRoom(int asId, Integer roomId) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_setAssaultPartyRoom, asId, roomId);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_setAssaultPartyRoom)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send getNumWaitingThieves message and get the response
     * @return output message parameter
     */
    public int getNumWaitingThieves() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_getNumWaitingThieves);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_getNumWaitingThieves)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send getAssaultParty1Members message and get the response
     * @return output message parameter
     */
    public int getAssaultParty1Members() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_GETASSAULTPARTY1MEMBERS);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_GETASSAULTPARTY1MEMBERS)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send getAssaultParty2Members message and get the response
     * @return output message parameter
     */
    public int getAssaultParty2Members() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_GETASSAULTPARTY2MEMBERS);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_GETASSAULTPARTY2MEMBERS)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send setAssaultPartyMember message and get the response
     * @param id thief id
     * @param asId assault party id
     */
    public void setAssaultPartyMember(int id, Integer asId) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_SETASSAULTPARTYMEMBER, id, asId);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_SETASSAULTPARTYMEMBER)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send removeAssaultPartyMember message and get the response
     * @param id thief id
     * @param asId assault party id
     */
    public void removeAssaultPartyMember(int id, Integer asId) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_REMOVEASSAULTPARTYMEMBER, id,asId);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_REMOVEASSAULTPARTYMEMBER)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send reportStatus message and get the response
     */
    public void reportStatus() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_REPORTSTATUS);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_REPORTSTATUS)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send reportFinalStatus message and get the response
     * @param result the final result
     */
    public void reportFinalStatus(Integer result) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_reportFinalStatus, result);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_reportFinalStatus)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send reportInitialStatus message and get the response
     */
    public void reportInitialStatus() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_reportInitialStatus);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_reportInitialStatus)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send shutDown message and get the response
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
    /**
     * send getOrdinaryT_MD message and get the response
     * @param id thief id
     * @return output message parameter
     */
    public Integer getOrdinaryT_MD(int id) {
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_GETORDINARYT_MD, id);
        send.setIdSender(id);
        
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_GETORDINARYT_MD)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send getAspRoom message and get the response
     * @param asp assault party id
     * @return output message parameter
     */
    public Integer getAspRoom(Integer asp){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_GETASPROOM, asp);
        
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_GETASPROOM)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
}
