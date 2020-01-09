package dir_ServerConcentrationSite.concentrationSite;


import dir_ServerConcentrationSite.interfaces.ConcentrationSiteInterface;
import dir_ServerConcentrationSite.structures.Ips;
import dir_ServerConcentrationSite.interfaces.GeneralRepositoryInterface;
import dir_ServerConcentrationSite.interfaces.Register;
import dir_ServerConcentrationSite.structures.Ports;
import dir_ServerConcentrationSite.structures.RegistryConfig;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author User
 */
public class ConcentrationSiteServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /* get location of the registry service */
        String rmiRegHostName;
        int rmiRegPortNumb;
        rmiRegHostName = Ips.IP_REGISTRY;
        rmiRegPortNumb = Ports.PORT_REGISTRY;

        GeneralRepositoryInterface repositoryInterface = null;
        try {
            Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
            repositoryInterface = (GeneralRepositoryInterface) registry.lookup(RegistryConfig.registryGeneralRepository);
        
        } catch (RemoteException e) {
            System.out.println("Excepção na localização do general repopsitory: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        
        } catch (NotBoundException e) {
            System.out.println("O GR não está registado: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        }

        /* create and install the security manager */
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        System.out.println("Security manager was installed!");

        ConcentrationSiteInterface concentrationSiteInterface = null;
        ConcentrationSite concentrationSite = new ConcentrationSite(repositoryInterface);

        try {
            concentrationSiteInterface = (ConcentrationSiteInterface) UnicastRemoteObject.exportObject(concentrationSite, Ports.PORT_CONCENTRATION_SITE);
        
        } catch (RemoteException e) {
            System.out.println("Excepção na geração do stub para o concentration site: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("O stub para o concentration site foi gerado!");

        /* seu registo no serviço de registo RMI */
        String nameEntryBase = RegistryConfig.nameEntryBase;
        String nameEntryObject = RegistryConfig.registryConcentrationSite;
        Registry registry = null;
        Register reg = null;

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        
        } catch (RemoteException e) {
            System.out.println("Excepção na criação do registo RMI: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("O registo RMI foi criado!");
        
        try {
            reg = (Register) registry.lookup(nameEntryBase);
       
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        try {
            reg.bind(nameEntryObject, concentrationSiteInterface);
        
        } catch (RemoteException e) {
            System.out.println("Excepção no registo: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        
        } catch (AlreadyBoundException e) {
            System.out.println("já está registado: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("O ConcentrationSite foi registado!");
    }

}
