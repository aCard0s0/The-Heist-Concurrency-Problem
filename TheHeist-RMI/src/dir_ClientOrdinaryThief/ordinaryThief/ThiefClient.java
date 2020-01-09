
package dir_ClientOrdinaryThief.ordinaryThief;

import dir_ClientOrdinaryThief.interfaces.AssaultPartyInterface;
import dir_ClientOrdinaryThief.interfaces.ConcentrationSiteInterface;
import dir_ClientOrdinaryThief.interfaces.ControlCollectionSiteInterface;
import dir_ClientOrdinaryThief.interfaces.GeneralRepositoryInterface;
import dir_ClientOrdinaryThief.interfaces.MuseumInterface;
import dir_ClientOrdinaryThief.structures.Config;
import dir_ClientOrdinaryThief.structures.Ips;
import dir_ClientOrdinaryThief.structures.Ports;
import dir_ClientOrdinaryThief.structures.RegistryConfig;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * this data type define the main program for the ordinary thief
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class ThiefClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
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
        
       
        Thief[] thiefs = new Thief[Config.N_ORDINARY_THIEVES];
        
        for(int i = 0; i < Config.N_ORDINARY_THIEVES; i++)
        {
            thiefs[i] = new Thief(i, conSiteInt, ccollInt, assPartyInt, grInt, museumInt);
            thiefs[i].start();
        }

        System.out.println("Ordinary Thieves started working.."); 
        for(Thief t : thiefs)
        {
            try {
                t.join();
            } catch (InterruptedException e) {}
            
        }
        System.out.println("Ordinary Thieves finished their job.");
    }
    
}
