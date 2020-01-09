package dir_ServerConcentrationSite.interfaces;

import dir_ServerConcentrationSite.structures.VectorTimestamp;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * this data type represent the interface for the museum
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public interface MuseumInterface extends Remote{
    
    public Object[] rollACanvas(int roomId, VectorTimestamp vt)throws RemoteException;
    
    public int getRoomDistance(int roomId)throws RemoteException;
    
    public int getNumCanvas(int roomId) throws RemoteException;
    
    public void signalShutdown() throws RemoteException;
}
