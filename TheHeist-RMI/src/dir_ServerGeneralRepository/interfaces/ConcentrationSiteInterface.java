
package dir_ServerGeneralRepository.interfaces;

import dir_ServerGeneralRepository.structures.VectorTimestamp;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * this data type represent the interface for the concentration site
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public interface ConcentrationSiteInterface extends Remote{
    
    public Object[] amINeeded(int id, VectorTimestamp vt) throws RemoteException;
    
    public VectorTimestamp prepareAssaultParty(int assaultPartyID, VectorTimestamp vt) throws RemoteException, InterruptedException;
    
    public VectorTimestamp sumUpResults(VectorTimestamp vt) throws RemoteException;
    public void signalShutdown() throws RemoteException;
}
