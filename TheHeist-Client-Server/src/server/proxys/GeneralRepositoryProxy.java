/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.proxys;

import entities.states.MasterThiefStates;
import entities.states.OrdThiefStates;
import message.Message;
import message.MessageException;
import server.ServerCom;
import server.interfaces.GeneralRepositoryInterface;

/**
 * This data type defines the general repository proxy
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class GeneralRepositoryProxy extends Thread{
    /**
     * counts the number of threads thrown
     * @serialField nProxy
     */
    private static int nProxy = 0;     
    /**
     * ServerCom instance
     * @serial sconi
     */
    private final ServerCom sconi;               
    /**
     * general repository interface instance
     * @serialField repoInter
     */
    private final GeneralRepositoryInterface repoInter;   
    /**
     * master thief state instance
     * @serialField mtState
     */
    private MasterThiefStates mtState;
    /**
     * ordinary thief state instance
     * @serialField tState
     */
    private OrdThiefStates  tState;
    /**
     * general repository proxy instantiation
     * @param sconi communication channel instance
     * @param repoInter general repository interface instance
     */
    public GeneralRepositoryProxy(ServerCom sconi, GeneralRepositoryInterface repoInter) {
        this.sconi = sconi;
        this.repoInter = repoInter;
    }
    
    @Override
    public void run() {
        
        Message inMsg = null, outMsg = null; 
        
        inMsg = (Message) sconi.readObject();  
        try { 
            outMsg = repoInter.processAndReply(inMsg);      
        } catch (MessageException e) { 
            System.out.println("Thread " + getName () + ": " + e.getMessage () + "!");
            System.out.println(e.getMessageVal ().toString ());
            System.exit (1);
        }
        sconi.writeObject(outMsg);        
        sconi.close ();                                           
    }
    
    /**
     * get the ServerCom instance
     * @return the instance
     */
    public ServerCom getSconi() {
        return sconi;
    }
}
