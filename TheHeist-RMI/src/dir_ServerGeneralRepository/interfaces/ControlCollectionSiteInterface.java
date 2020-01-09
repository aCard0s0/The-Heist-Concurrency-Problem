package dir_ServerGeneralRepository.interfaces;

import dir_ServerGeneralRepository.structures.VectorTimestamp;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * this data type represent the interface for the concentration site
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public interface ControlCollectionSiteInterface extends Remote{
    
    public VectorTimestamp startOperations(VectorTimestamp vt) throws RemoteException;
    
    public Object[] appraiseSit(VectorTimestamp vt) throws RemoteException;
    public Object[] prepareAssaultParty_1(VectorTimestamp vt) throws RemoteException;
    public Object[] prepareAssaultParty_2(Integer assaultPartyId, VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp takeRest(VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp collectCanvas(VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp handACanvas(Integer id,Integer asPid, VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp sumUpResults(VectorTimestamp vt) throws RemoteException;
    public void signalShutdown() throws RemoteException;
    
}
