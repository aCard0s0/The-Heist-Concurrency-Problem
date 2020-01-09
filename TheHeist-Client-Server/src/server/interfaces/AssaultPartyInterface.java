package server.interfaces;

import entities.states.MasterThiefStates;
import entities.states.OrdThiefStates;
import sharedRegions.AssaultParty;
import message.Message;
import message.MessageException;
import message.MsgInfos;
import server.AssaultParty1Server;
import server.AssaultParty2Server;
import server.proxys.AssaultPartyProxy;

/**
 * This data type defines the assault party Interface
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class AssaultPartyInterface implements SRegionInterface{
    /**
     * assault party region instance
     * @serial party
     */
    private final AssaultParty party;          
    /**
     * assault party interface instantiation
     * @param party the assault party instance
     */
    public AssaultPartyInterface(AssaultParty party) {
        this.party = party;
    }
    
    @Override
    public Message processAndReply(Message inMsg) throws MessageException {
        
        Message reply = null;       
        MasterThiefStates mtState = null;      
        OrdThiefStates tState = null;
        Integer rsp;                            
            
        switch(inMsg.getIdMethod()){
            
            case MsgInfos.SM_PRAPAREEXCURSION:
                party.prapareExcursion(inMsg.getIdSender());             
                reply = new Message(MsgInfos.RM_PRAPAREEXCURSION);  
                break;
                
            case MsgInfos.SM_CRAWLIN:
                party.crawlin(inMsg.getIdSender());
                reply = new Message(MsgInfos.RM_CRAWLIN); 
                break;
                
            case MsgInfos.SM_REVERSEDIRECTIONN:
                party.reverseDirection(inMsg.getIdSender());
                reply = new Message(MsgInfos.RM_REVERSEDIRECTION);
                break;
            
            case MsgInfos.SM_CRAWLOUT:              
                party.crawlout(inMsg.getIdSender());
                reply = new Message (MsgInfos.RM_CRAWLOUT);   
                break;
            
            case MsgInfos.SM_GETROOMID:
                rsp = party.getRoomId();
                reply = new Message (MsgInfos.RM_GETROOMID, rsp);           
                break;
                
            case MsgInfos.SM_SENDASSAULTPARTY:
                party.sendAssaultParty(inMsg.getInParam());
                reply = new Message (MsgInfos.RM_SENDASSAULTPARTY);          
                break;
            case MsgInfos.SM_SHUTDOWN:
                (((AssaultPartyProxy) (Thread.currentThread ())).getSconi() ).setTimeout (10);
                AssaultParty1Server.waitConnection = false;
                AssaultParty2Server.waitConnection = false;
                reply = new Message(MsgInfos.RM_SHUTDOWN);
                break;
            
        }
        
        return reply;
    }
}
