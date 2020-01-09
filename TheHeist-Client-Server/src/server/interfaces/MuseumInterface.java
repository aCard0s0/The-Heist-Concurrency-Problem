
package server.interfaces;

import message.Message;
import message.MessageException;
import message.MsgInfos;
import server.MuseumServer;
import server.proxys.MuseumProxy;
import sharedRegions.Museum;

/**
 * This data type defines the museum Interface
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class MuseumInterface implements SRegionInterface{
    /**
     * museum instance
     * @serial museum
     */
    private final Museum museum;
    /**
     * museum interface instantiation
     * @param museum the museum instance
     */
    public MuseumInterface(Museum museum) {
        this.museum = museum;
    }
    
    @Override
    public Message processAndReply(Message msg) throws MessageException {
        Message reply = null;              
        Integer rsp;          
        
        /* Validação & Processamento da mensagem recebida */
        switch(msg.getIdMethod()){
            
            case MsgInfos.SM_ROLLACANVAS:
                rsp = museum.rollACanvas(msg.getInParam()); //check
                reply = new Message(MsgInfos.RM_ROLLACANVAS, rsp); 
                break;
                
            case MsgInfos.SM_GETROOMDISTANCE:
                rsp = museum.getRoomDistance(msg.getInParam());
                reply = new Message(MsgInfos.RM_GETROOMDISTANCE, rsp);
                break;
                
            case MsgInfos.SM_GETNUMCANVAS:
                rsp = museum.getNumCanvas(msg.getInParam());
                reply = new Message(MsgInfos.RM_GETNUMCANVAS, rsp);
                break;
             
            case MsgInfos.SM_SHUTDOWN:
                (((MuseumProxy) (Thread.currentThread ())).getSconi() ).setTimeout (10);
                MuseumServer.waitConnection = false;
                reply = new Message(MsgInfos.RM_SHUTDOWN);
                break;
        }
        
        return reply;
    }
    
}
