
package server;

import configurations.Ports;
import java.net.SocketTimeoutException;
import server.interfaces.ControlCollectionSiteInterface;
import server.proxys.ControlCollectionSiteProxy;
import sharedRegions.ControlCollectionSite;

/**
 * this data type define the main program for ControlCollectionSiteServer
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class ControlCollectionSiteServer {
    public static boolean waitConnection;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ControlCollectionSite collectionSite = new ControlCollectionSite();                                 
        ControlCollectionSiteInterface collectionSiteInterface = new ControlCollectionSiteInterface(collectionSite);                 
        ServerCom scon, sconi;                          
        ControlCollectionSiteProxy collectionSiteProxy;
        
        scon  = new ServerCom(Ports.PORT_CONTROL_COLLETION_SITE);
        scon.start ();                                     
        System.out.println("O serviço Control Collection Site foi estabelecido!");
        System.out.println("O servidor esta em escuta...");
        /* processamento de pedidos */
        waitConnection = true;
        while (waitConnection) {
            try { 
                sconi = scon.accept ();           
                collectionSiteProxy = new ControlCollectionSiteProxy(sconi, collectionSiteInterface);
                collectionSiteProxy.start ();
            } catch (SocketTimeoutException e){    
                scon.end();                                       
                System.out.println("O servidor foi desactivado.");
            }
            
        }
        
        
    }
}
