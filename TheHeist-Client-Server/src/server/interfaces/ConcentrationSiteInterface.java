package server.interfaces;

import java.util.logging.Level;
import java.util.logging.Logger;
import message.Message;
import message.MessageException;
import message.MsgInfos;
import server.ConcentrationSiteServer;
import server.proxys.ConcentrationSiteProxy;
import sharedRegions.ConcentrationSite;

/**
 * This data type defines the concentration site Interface
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class ConcentrationSiteInterface implements SRegionInterface{
    /**
     * concentration site instance
     * @serial conc
     */
    private final ConcentrationSite conc;         
    /**
     * concentration site interface instantiation
     * @param conc the concentration site instance
     */
    public ConcentrationSiteInterface(ConcentrationSite conc) {
        this.conc = conc;
    }
    
    @Override
    public Message processAndReply(Message inMsg) throws MessageException {
        
        Message reply = null;               
        Integer rsp;                            
           
        /* Validação & Processamento da mensagem recebida */
        switch(inMsg.getIdMethod()){
            
            case MsgInfos.SM_AMINEEDED:
                rsp = conc.amINeeded( inMsg.getIdSender() );                        // processamento
                reply = new Message(MsgInfos.RM_AMINEEDED, rsp);  // gerar resposta
                break;
                
            case MsgInfos.SM_PREPAREASSAULTPARTY:
                try {
                    conc.prepareAssaultParty( inMsg.getInParam());
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConcentrationSiteInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
                reply = new Message(MsgInfos.RM_PREPAREASSAULTPARTY);  // gerar resposta
                break;
                
            case MsgInfos.SM_SUMUPRESULTS:
                conc.sumUpResults();
                reply = new Message(MsgInfos.RM_SUMUPRESULTS);  // gerar resposta
                break;
                
            case MsgInfos.SM_SHUTDOWN:
                (((ConcentrationSiteProxy) (Thread.currentThread())).getSconi() ).setTimeout(10);
                ConcentrationSiteServer.waitConnection = false;
                reply = new Message(MsgInfos.RM_SHUTDOWN);
                break;
        }
        
        return reply;
    }
}
