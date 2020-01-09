package server.interfaces;

import entities.states.MasterThiefStates;
import entities.states.OrdThiefStates;
import message.Message;
import message.MessageException;
import message.MsgInfos;
import server.GeneralRepositoryServer;
import server.proxys.GeneralRepositoryProxy;
import sharedRegions.GeneralRepository;
/**
 * This data type defines the general repository Interface
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class GeneralRepositoryInterface implements SRegionInterface{
    /**
     * general repository site instance
     * @serial repo
     */
    private final GeneralRepository repo;           // Instaciamento da regiao partilhada
    /**
     * general repository interface instantiation
     * @param repo the general repository instance
     */
    public GeneralRepositoryInterface(GeneralRepository repo) {
        this.repo = repo;
    }
    
    @Override
    public Message processAndReply(Message inMsg) throws MessageException {
        
        Message reply = null;               
        MasterThiefStates mtState = null;          
        OrdThiefStates tState = null;
        Integer rsp;                           
           
        /* Validação & Processamento da mensagem recebida */
        switch(inMsg.getIdMethod()){
            
            case MsgInfos.SM_SETSTATEMT:
                repo.setState( inMsg.getMasterState() );                        // processamento
                reply = new Message(MsgInfos.RM_SETSTATEMT);  // gerar resposta
                break;
                
            case MsgInfos.SM_SETSTATET:
                repo.setState(inMsg.getIdSender(), inMsg.getThiefState());
                reply = new Message(MsgInfos.RM_SETSTATET);  // gerar resposta
                break;
                
            case MsgInfos.SM_SETSITUATION:
                repo.setSituation(inMsg.getIdSender(), inMsg.getThiefSituation() );
                reply = new Message(MsgInfos.RM_SETSITUATION);  // gerar resposta
                break;
                
            case MsgInfos.SM_INITIALIZEREPOSITORY:
                repo.initializeRepository(inMsg.getIdSender(),inMsg.getInParam());
                reply = new Message(MsgInfos.RM_INITIALIZEREPOSITORY);  // gerar resposta
                break;
                
            case MsgInfos.SM_SETROLLACANVAS:
                repo.setRollACanvas( inMsg.getInParam() );
                reply = new Message(MsgInfos.RM_SETROLLACANVAS);  // gerar resposta
                break;
                
            case MsgInfos.SM_INITIALIZEPARTYPROPERTIES:
                repo.initializePartyProperties( inMsg.getInParam() );
                reply = new Message(MsgInfos.RM_INITIALIZEPARTYPROPERTIES);  // gerar resposta
                break;
                
            case MsgInfos.SM_SETCARRYINGCANVAS:
                repo.setCarryingCanvas( inMsg.getIdSender(),inMsg.getInParam());
                reply = new Message(MsgInfos.RM_SETCARRYINGCANVAS);  
                break;
                
            case MsgInfos.SM_getCarryingCanvas:
                rsp = repo.getCarryingCanvas(inMsg.getIdSender());
                reply = new Message(MsgInfos.RM_getCarryingCanvas, rsp);
                break;
                
            case MsgInfos.SM_setPosition:
                repo.setPosition( inMsg.getIdSender(), inMsg.getInParam());
                reply = new Message(MsgInfos.RM_setPosition);
                break;
                
            case MsgInfos.SM_setAssaultPartyRoom:
                repo.setAssaultPartyRoom(inMsg.getIdSender(), inMsg.getInParam());
                reply = new Message(MsgInfos.RM_setAssaultPartyRoom);
                break;
                
            case MsgInfos.SM_getNumWaitingThieves:
                rsp = repo.getNumWaitingThieves();
                reply = new Message(MsgInfos.RM_getNumWaitingThieves, rsp);
                break;
                
            case MsgInfos.SM_GETASSAULTPARTY1MEMBERS:
                rsp = repo.getAssaultParty1Members();
                reply = new Message(MsgInfos.RM_GETASSAULTPARTY1MEMBERS, rsp);
                break;
                
            case MsgInfos.SM_GETASSAULTPARTY2MEMBERS:
                rsp = repo.getAssaultParty2Members();
                reply = new Message(MsgInfos.RM_GETASSAULTPARTY2MEMBERS, rsp);
                break;
                
            case MsgInfos.SM_SETASSAULTPARTYMEMBER:
                repo.setAssaultPartyMember( inMsg.getIdSender(), inMsg.getInParam() );
                reply = new Message(MsgInfos.RM_SETASSAULTPARTYMEMBER);
                break;
                
            case MsgInfos.SM_REMOVEASSAULTPARTYMEMBER:
                repo.removeAssaultPartyMember(inMsg.getIdSender(), inMsg.getInParam());
                reply = new Message(MsgInfos.RM_REMOVEASSAULTPARTYMEMBER);
                break;
                
            case MsgInfos.SM_REPORTSTATUS:
                repo.reportStatus();
                reply = new Message(MsgInfos.RM_REPORTSTATUS);
                break;
                
            case MsgInfos.SM_reportFinalStatus:
                repo.reportFinalStatus( inMsg.getInParam() );
                reply = new Message(MsgInfos.RM_reportFinalStatus);
                break;
                
            case MsgInfos.SM_reportInitialStatus:
                repo.reportInitialStatus();
                reply = new Message(MsgInfos.RM_reportInitialStatus);
                break;
            case MsgInfos.SM_GETORDINARYT_MD:
                rsp = repo.getOrdinaryT_MD(inMsg.getIdSender());
                reply = new Message(MsgInfos.RM_GETORDINARYT_MD, rsp);
                break;
            case MsgInfos.SM_GETASPROOM:
                rsp = repo.getAspRoom(inMsg.getInParam());
                reply = new Message(MsgInfos.RM_GETASPROOM, rsp);
                break;
                
            case MsgInfos.SM_SHUTDOWN:
                (((GeneralRepositoryProxy) (Thread.currentThread()) ).getSconi() ).setTimeout(10);
                GeneralRepositoryServer.waitConnection = false;
                reply = new Message(MsgInfos.RM_SHUTDOWN);
                break;
        }
        
        return reply;
    }
}
