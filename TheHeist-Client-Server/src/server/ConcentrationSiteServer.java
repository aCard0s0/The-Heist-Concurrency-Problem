
package server;

import configurations.Ports;
import java.net.SocketTimeoutException;
import server.interfaces.ConcentrationSiteInterface;
import server.proxys.ConcentrationSiteProxy;
import sharedRegions.ConcentrationSite;

/**
 * this data type define the main program for ConcentrationSiteServer
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class ConcentrationSiteServer {
    
    public static boolean waitConnection;       
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ConcentrationSite concSite;                                 
        ConcentrationSiteInterface concInterface;                    
        ServerCom scon, sconi;                         
        ConcentrationSiteProxy concProxy;                       
    
        /* estabelecimento do servico */
         
        concSite = new ConcentrationSite();                            
        concInterface = new ConcentrationSiteInterface(concSite);                
        scon = new ServerCom(Ports.PORT_CONCENTRATION_SITE);                
        scon.start ();                                          
        System.out.println("O serviço com Concentration Site foi estabelecido!");
        System.out.println("O servidor esta em escuta...");
        
        /* processamento de pedidos */
        waitConnection = true;
        while (waitConnection) {
            try { 
                sconi = scon.accept ();                          
                concProxy = new ConcentrationSiteProxy (sconi, concInterface);  
                concProxy.start ();
            } catch (SocketTimeoutException e){    
                scon.end();                                        
                System.out.println("O servidor foi desactivado.");
            }
            
        }
    }
    
}
