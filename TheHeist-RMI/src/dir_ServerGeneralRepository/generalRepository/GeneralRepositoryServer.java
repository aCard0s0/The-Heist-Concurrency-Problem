/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dir_ServerGeneralRepository.generalRepository;

import dir_ServerGeneralRepository.interfaces.GeneralRepositoryInterface;
import dir_ServerGeneralRepository.interfaces.Register;
import dir_ServerGeneralRepository.structures.Ips;
import dir_ServerGeneralRepository.structures.Ports;
import dir_ServerGeneralRepository.structures.RegistryConfig;
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
public class GeneralRepositoryServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        /* get location of the registry service */
        String rmiRegHostName;
        int rmiRegPortNumb;
        rmiRegHostName = Ips.IP_REGISTRY;
        rmiRegPortNumb = Ports.PORT_REGISTRY;

        /* create and install the security manager */
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        System.out.println("Security manager was installed!");

        /* instantiate a remote object that runs mobile code and generate a stub for it */
        GeneralRepository generalRepository = new GeneralRepository();
        GeneralRepositoryInterface generalRepositoryInterface = null;
        
        try {
            generalRepositoryInterface = (GeneralRepositoryInterface) UnicastRemoteObject.exportObject(generalRepository, Ports.PORT_GENERAL_REPOSITORY);
        
        } catch (RemoteException e) {
            System.out.println("Excepção na geração do stub para a Logging: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("O stub para o GeneralRepository foi gerado!");
        
        
        /* seu registo no serviço de registo RMI */
        String nameEntryBase = RegistryConfig.nameEntryBase;
        String nameEntryObject = RegistryConfig.registryGeneralRepository;
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
            reg.bind(nameEntryObject, generalRepositoryInterface);
        
        } catch (RemoteException e) {
            System.out.println("Excepção no registo: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        
        } catch (AlreadyBoundException e) {
            System.out.println("já está registado: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("O GeneralRepository foi registado!");

    }

}
