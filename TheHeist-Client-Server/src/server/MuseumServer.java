
package server;

import configurations.Ports;
import java.net.SocketTimeoutException;
import server.interfaces.MuseumInterface;
import server.proxys.MuseumProxy;
import sharedRegions.Museum;

/**
 * this data type define the main program for MuseumServer
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class MuseumServer {
    public static boolean waitConnection;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Museum museum = new Museum();
        MuseumInterface museumInterface = new MuseumInterface(museum);
        ServerCom scon, sconi;        
        MuseumProxy museumProxy;
        scon  = new ServerCom(Ports.PORT_MUSUEM);
        scon.start ();                                     
        System.out.println("O serviço Museum foi estabelecido!");
        System.out.println("O servidor esta em escuta...");
        /* processamento de pedidos */
        waitConnection = true;
        while (waitConnection) {
            try { 
                sconi = scon.accept ();           
                museumProxy = new MuseumProxy(sconi, museumInterface);
                museumProxy.start ();
            } catch (SocketTimeoutException e){    
                scon.end();                                       
                System.out.println("O servidor foi desactivado.");
            }
            
        }
    }
    
}
