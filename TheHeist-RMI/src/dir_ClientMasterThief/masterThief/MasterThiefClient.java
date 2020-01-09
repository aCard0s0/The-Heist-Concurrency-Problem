
package dir_ClientMasterThief.masterThief;

import dir_ClientMasterThief.interfaces.AssaultPartyInterface;
import dir_ClientMasterThief.interfaces.ConcentrationSiteInterface;
import dir_ClientMasterThief.interfaces.ControlCollectionSiteInterface;
import dir_ClientMasterThief.interfaces.GeneralRepositoryInterface;
import dir_ClientMasterThief.interfaces.MuseumInterface;
import dir_ClientMasterThief.structures.Config;
import dir_ClientMasterThief.structures.Ips;
import dir_ClientMasterThief.structures.Ports;
import dir_ClientMasterThief.structures.RegistryConfig;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this data type define the main program for the master thief
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class MasterThiefClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String rmiRegHostName;                      // nome do sistema onde está localizado o serviço de registos RMI
        int rmiRegPortNumb;                         // port de escuta do serviço

        rmiRegHostName = Ips.IP_REGISTRY;
        rmiRegPortNumb = Ports.PORT_REGISTRY;
        
        ConcentrationSiteInterface conSiteInt = null;
        ControlCollectionSiteInterface ccollInt = null;
        AssaultPartyInterface[] assPartyInt = new AssaultPartyInterface[Config.N_ASSAULTPARTIES];
        GeneralRepositoryInterface grInt = null;
        MuseumInterface museumInt = null;
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            conSiteInt = (ConcentrationSiteInterface) registry.lookup (RegistryConfig.registryConcentrationSite);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating logger: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("GeneralRepository is not registered: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            ccollInt = (ControlCollectionSiteInterface) registry.lookup (RegistryConfig.registryControlCollectionSite);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating shop: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("ControlCollectionSite is not registered: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit(1);
        }
        
        try
        { 
            // Como se faz para um array?
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            assPartyInt[0] = (AssaultPartyInterface) registry.lookup (RegistryConfig.registryAssaultParty1);
            assPartyInt[1] = (AssaultPartyInterface) registry.lookup (RegistryConfig.registryAssaultParty2);
            
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating workshop: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Workshop is not registered: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            grInt = (GeneralRepositoryInterface) registry.lookup (RegistryConfig.registryGeneralRepository);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating shop: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("ControlCollectionSite is not registered: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            museumInt = (MuseumInterface) registry.lookup (RegistryConfig.registryMuseum);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating shop: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("ControlCollectionSite is not registered: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit(1);
        }
        
        MasterThief masterThief = new MasterThief(assPartyInt, conSiteInt, ccollInt, grInt);
        masterThief.start();
        try {
            masterThief.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(MasterThiefClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("master Thief finished the job.");
    }
    
}
