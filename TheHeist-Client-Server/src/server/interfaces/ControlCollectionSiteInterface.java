package server.interfaces;

import message.Message;
import message.MessageException;
import message.MsgInfos;
import server.ControlCollectionSiteServer;
import server.proxys.ControlCollectionSiteProxy;
import sharedRegions.ControlCollectionSite;

/**
 * This data type defines the control and collection site Interface
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class ControlCollectionSiteInterface implements SRegionInterface{
    /**
     * control and collection site instance
     * @serial collectionSite
     */
    private final ControlCollectionSite collectionSite;
    /**
     * control and collection site interface instantiation
     * @param collectionSite the control and collection site instance
     */
    public ControlCollectionSiteInterface(ControlCollectionSite collectionSite) {
        this.collectionSite = collectionSite;
    }
    
    @Override
    public Message processAndReply(Message inMsg) throws MessageException {
        Message reply = null;              
        Integer rsp;          
        
        /* Validação & Processamento da mensagem recebida */
        switch(inMsg.getIdMethod()){
            
            case MsgInfos.SM_STARTOPERATIONS:
                collectionSite.startOperations();                     
                reply = new Message(MsgInfos.RM_STARTOPERATIONS); 
                break;
                
            case MsgInfos.SM_APPRAISESIT:
                rsp = collectionSite.appraiseSit();
                reply = new Message(MsgInfos.RM_APPRAISESIT,rsp);
                break;
                
            case MsgInfos.SM_PREPAREASSAULTPARTY_1:
                rsp = collectionSite.prepareAssaultParty_1();
                reply = new Message(MsgInfos.RM_PREPAREASSAULTPARTY_1,rsp);  
                break;
                
            case MsgInfos.SM_PREPAREASSAULTPARTY_2:
                rsp = collectionSite.prepareAssaultParty_2(inMsg.getInParam());
                reply = new Message(MsgInfos.RM_PREPAREASSAULTPARTY_2,rsp);  
                break;
                
            case MsgInfos.SM_TAKEREST:
                collectionSite.takeRest();
                reply = new Message(MsgInfos.RM_TAKEREST);  
                break;
                
            case MsgInfos.SM_COLLECTCANVAS:
                collectionSite.collectCanvas();
                reply = new Message(MsgInfos.RM_COLLECTCANVAS);  
                break;
                
            case MsgInfos.SM_HANDACANVAS:
                collectionSite.handACanvas(inMsg.getIdSender(),inMsg.getInParam());
                reply = new Message(MsgInfos.RM_HANDACANVAS);  
                break;
            case MsgInfos.SM_SUMUPRESULTS:
                collectionSite.sumUpResults();
                reply = new Message(MsgInfos.RM_SUMUPRESULTS);  
                break;
                
            case MsgInfos.SM_SHUTDOWN:
                (((ControlCollectionSiteProxy) (Thread.currentThread ())).getSconi() ).setTimeout (10);
                ControlCollectionSiteServer.waitConnection = false;
                reply = new Message(MsgInfos.RM_SHUTDOWN);
                break;
        }
        
        return reply;
        
    }
}
