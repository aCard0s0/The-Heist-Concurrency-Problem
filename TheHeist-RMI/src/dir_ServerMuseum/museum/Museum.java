package dir_ServerMuseum.museum;

import dir_ServerMuseum.interfaces.GeneralRepositoryInterface;
import dir_ServerMuseum.interfaces.MuseumInterface;
import dir_ServerMuseum.interfaces.Register;
import dir_ServerMuseum.structures.Config;
import dir_ServerMuseum.structures.Ips;
import dir_ServerMuseum.structures.Ports;
import dir_ServerMuseum.structures.RegistryConfig;
import dir_ServerMuseum.structures.VectorTimestamp;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This data type defines museum
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class Museum implements MuseumInterface{

    /**
     * list of room at the museum
     *
     * @serialField rooms
     */
    private Room[] rooms;
    /**
     * repository instance
     *
     * @serialField repositoryInterface
     */
    private final GeneralRepositoryInterface repositoryInterface;
    
    /**
     * vector clock used
     *
     * @serialField clocks
     */
    private final VectorTimestamp clocks;

    /**
     * museum instantiation
     * @param repositoryInterface
     */
    public Museum(GeneralRepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
        
        this.rooms = new Room[Config.N_ROOMS];

        /*each room instantiation*/
        for (int i = 0; i < Config.N_ROOMS; i++) {
            this.rooms[i] = new Room(i);
        }
        this.clocks = new VectorTimestamp(Config.N_ORDINARY_THIEVES+1, 0);
    }
   
    @Override
    public Object[] rollACanvas(int roomId, VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        Object[] result = new Object[2];   
        result[0]=clocks.clone();
        
        if (rooms[roomId].getNumCanvas() > 0) {
            rooms[roomId].setNumCanvas(rooms[roomId].getNumCanvas() - 1);
            /*update repository*/
            repositoryInterface.setRollACanvas(roomId,vt.clone());
            result[1]=1;
            return result;
        }
        result[1]=0;
        return result;
    }

    @Override
    public int getRoomDistance(int roomId) throws RemoteException {
        return rooms[roomId].getDistance();
    }

    @Override
    public int getNumCanvas(int roomId) throws RemoteException {
        return rooms[roomId].getNumCanvas();
    }

    @Override
    public void signalShutdown() throws RemoteException {
        Register reg = null;
        Registry registry = null;

        String rmiRegHostName = Ips.IP_REGISTRY;
        int rmiRegPortNumb = Ports.PORT_REGISTRY;
        
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException ex) {
            System.out.println("exception: " + ex.getMessage());
        }

        String nameEntryBase = RegistryConfig.registerHandler;
        String nameEntryObject = RegistryConfig.registryMuseum;
        
        try {
            reg = (Register) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
        }
        
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println(" registration exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("not bound exception: " + e.getMessage());
        }
        
         try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            System.out.println("NoSuchObjectException exception: " + ex.getMessage());
        }

        System.out.println("closed.");
    }
    
}
