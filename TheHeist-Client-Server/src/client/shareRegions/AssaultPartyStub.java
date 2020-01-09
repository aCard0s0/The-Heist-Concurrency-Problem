package client.shareRegions;

import client.ClientCom;
import message.Message;
import message.MsgInfos;

/**
 * This data type defines a stub of the assault party
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class AssaultPartyStub {
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
     * AssaultPartyStub instantiation
     *
     * @param hostName server host name
     * @param portNumber server port number
     */
    public AssaultPartyStub(String hostName, int portNumber) {
        serverHostName = hostName;
        serverPortNumb = portNumber;
    }
    
    /**
     * send prapareExcursion message and get the response
     *
     * Called by the ordinary Thief
     * @param id the thief id
     */
    public void prapareExcursion(int id) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb);
        Message send, reply;
        
        while (!con.open ()){                                          
            try {
                Thread.sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_PRAPAREEXCURSION, id);
        con.writeObject(send);
        
        reply = (Message) con.readObject();
        if (!reply.getIdMethod().equals(MsgInfos.RM_PRAPAREEXCURSION)) {        
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Invalid type!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close(); 
    }
    /**
     * send crawl in message and get the response
     *
     * Called by the ordinary Thief
     * @param id thief id
     */
    public void crawlin(int id) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); 
        Message send, reply;
        
        while (!con.open ()){                                        
            try {
                Thread.sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_CRAWLIN, id);
        con.writeObject(send);
        
        reply = (Message) con.readObject();
        if (!reply.getIdMethod().equals(MsgInfos.RM_CRAWLIN)) {        
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Invalid type!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send reverse direction message and get the response
     *
     * Called by the ordinary Thief
     * @param id thief id
     */
    public void reverseDirection(int id) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); 
        Message send, reply;  
        
        while (!con.open ()){                                      
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_REVERSEDIRECTIONN, id);
        con.writeObject(send);
        
        reply = (Message) con.readObject();
        if (!reply.getIdMethod().equals(MsgInfos.RM_REVERSEDIRECTION)) {         
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Invalid type!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send crawl out message and get the response
     *
     * Called by the ordinary Thief
     * @param id thief id
     */
    public void crawlout(int id) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); 
        Message send, reply;  
        
        while (!con.open ()){                                      
            try {
                Thread.sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_CRAWLOUT, id);
        con.writeObject(send);
        
        reply = (Message) con.readObject();
        if (!reply.getIdMethod().equals(MsgInfos.RM_CRAWLOUT)) {         
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Invalid type!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * send assault party message and get the response
     *
     * Called by the master Thief
     * @param roomID the room to roll canvas
     */
    public void sendAssaultParty(Integer roomID) {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb);
        Message send, reply;
         
        while (!con.open ()){                                      
            try {
                Thread.sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_SENDASSAULTPARTY, roomID);
        con.writeObject(send);
        reply = (Message) con.readObject();
        
        if (!reply.getIdMethod().equals(MsgInfos.RM_SENDASSAULTPARTY)) {     
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Invalid type!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
    }
    /**
     * get room Id message and get the response
     *
     * Called by the ordinary Thief
     * @return the museum room Id
     */
    public Integer getRoomId() {
        
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); 
        Message send, reply;  
        
        while (!con.open ()){                                      
            try {
                Thread.sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgInfos.SM_GETROOMID);
        con.writeObject(send);
        
        reply = (Message) con.readObject();
        if (!reply.getIdMethod().equals(MsgInfos.RM_GETROOMID)) {         
            System.out.println("Thread " + Thread.currentThread ().getName ()+ ": Invalid type!");
            System.out.println(reply.toString ());
            System.exit(1);
        }
        con.close();
        
        return reply.getInParam();
    }
    
   /**
    *  request the shutdown of the server
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
