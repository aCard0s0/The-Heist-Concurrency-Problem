package server;

import configurations.Ports;
import java.net.SocketTimeoutException;
import server.interfaces.GeneralRepositoryInterface;
import server.proxys.GeneralRepositoryProxy;
import sharedRegions.GeneralRepository;

/**
 * this data type define the main program for GeneralRepositoryServer
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class GeneralRepositoryServer {

        public static boolean waitConnection;    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        GeneralRepository repository;                                  
        GeneralRepositoryInterface repoInterface;                 
        ServerCom scon, sconi;                          
        GeneralRepositoryProxy repoProxy;                        
    
        /* estabelecimento do servico */
         
        repository = new GeneralRepository();                          
        repoInterface = new GeneralRepositoryInterface(repository);                
        scon = new ServerCom(Ports.PORT_GENERAL_REPOSITORY);                
        scon.start ();                                      
        System.out.println("O serviço com General Repository foi estabelecido!");
        System.out.println("O servidor esta em escuta...");
        
        /* processamento de pedidos */
        waitConnection = true;
        while (waitConnection) {
            try { 
                sconi = scon.accept ();                     
                repoProxy = new GeneralRepositoryProxy (sconi, repoInterface);  
                repoProxy.start ();
            } catch (SocketTimeoutException e){    
                scon.end();                                     
                System.out.println("O servidor foi desactivado.");
            }
            
        }
    }
    
}
