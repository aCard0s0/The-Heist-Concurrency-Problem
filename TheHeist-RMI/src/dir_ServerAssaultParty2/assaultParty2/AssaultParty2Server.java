/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dir_ServerAssaultParty2.assaultParty2;

import dir_ServerAssaultParty2.interfaces.Register;
import dir_ServerAssaultParty2.structures.Ips;
import dir_ServerAssaultParty2.interfaces.AssaultPartyInterface;
import dir_ServerAssaultParty2.interfaces.GeneralRepositoryInterface;
import dir_ServerAssaultParty2.structures.Ports;
import dir_ServerAssaultParty2.structures.RegistryConfig;
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
public class AssaultParty2Server {

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

        AssaultPartyInterface assPartyInter = null;
        AssaultParty assParty = new AssaultParty(repositoryInterface, 1);

        try {
            assPartyInter = (AssaultPartyInterface) UnicastRemoteObject.exportObject(assParty, Ports.PORT_ASSAULT_PARTY_2);
        } catch (RemoteException e) {
            System.out.println("Excepção na geração do stub para o concentration site: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("O stub para o concentration site foi gerado!");

        /* seu registo no serviço de registo RMI */
        String nameEntryBase = RegistryConfig.nameEntryBase;
        String nameEntryObject = RegistryConfig.registryAssaultParty2;
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
            reg.bind(nameEntryObject, assPartyInter);
        } catch (RemoteException e) {
            System.out.println("Excepção no registo: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("já está registado: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("A AssaulParty 2 foi registada!");
    }
    
}
