
package server;

import configurations.Ports;
import java.net.SocketTimeoutException;
import server.interfaces.AssaultPartyInterface;
import server.proxys.AssaultPartyProxy;
import sharedRegions.AssaultParty;

/**
 * this data type define the main program for AssaultParty1Server
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class AssaultParty2Server {
    
    public static boolean waitConnection;      
    
    public static void main (String [] args) {
        
        AssaultParty party = new AssaultParty(1);                                 
        AssaultPartyInterface partyInter = new AssaultPartyInterface(party);                    
        ServerCom scon, sconi;                         
        AssaultPartyProxy partyProxy;                        
    
        scon = new ServerCom(Ports.PORT_ASSAULT_PARTY_2);                
        scon.start ();                                          
        System.out.println("O serviço com Assault Party 2 foi estabelecido!");
        System.out.println("O servidor esta em escuta...");
        
        /* processamento de pedidos */

        waitConnection = true;      
        while (waitConnection) {
            try { 
                sconi = scon.accept ();                         
                partyProxy = new AssaultPartyProxy (sconi, partyInter);  
                partyProxy.start();
            } catch (SocketTimeoutException e){    
                scon.end();                                        
                System.out.println("O servidor foi desactivado.");
            }
            
        }
    }
}
