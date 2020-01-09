
package client.shareRegions;

import client.ClientCom;
import message.Message;
import message.MsgInfos;

/**
 * This data type defines a stub for the museum
 * 
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class MuseumStub {
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
     * MuseumStub instantiation
     *
     * @param hostName server host name
     * @param portNumber server port number
     */
    public MuseumStub(String hostName, int portNumber) {
        serverHostName = hostName;
        serverPortNumb = portNumber;
    }
    /**
     * send rollACanvas message and get the response
     *
     * @param roomId the room id
     * @return output message parameter
     */
    public Integer rollACanvas(Integer roomId){
        
        ClientCom con = new ClientCom(serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_ROLLACANVAS, roomId);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_ROLLACANVAS)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send getRoomDistance message and get the response
     *
     * @param roomId the room id
     * @return output message parameter
     */
    public int getRoomDistance(Integer roomId){
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_GETROOMDISTANCE, roomId);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_GETROOMDISTANCE)) {         // verificar resposta
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    /**
     * send getNumCanvas message and get the response
     *
     * @param roomId the room id
     * @return output message parameter
     */
    public int getNumCanvas(Integer roomId){
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_GETNUMCANVAS, roomId);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_GETNUMCANVAS)) {         // verificar resposta
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
    public void shutDown() {
        
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
