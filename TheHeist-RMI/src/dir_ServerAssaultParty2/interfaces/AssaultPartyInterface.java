
package dir_ServerAssaultParty2.interfaces;

import dir_ServerAssaultParty2.structures.VectorTimestamp;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * this data type represent the interface for the assault party
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public interface AssaultPartyInterface extends Remote{
    
    /**
     * prapareExcursion: the ordinary thieves wait until the last thief join and
     * wake up the master thief
     *
     * Called by the ordinary Thief
     *
     * @param id the thief Id
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp prapareExcursion(int id, VectorTimestamp vt)throws RemoteException;
    
    /**
     * crawl in: crawl in processes
     *
     * Called by the ordinary Thief
     * @param id the thief Id
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp crawlin(int id, VectorTimestamp vt)throws RemoteException;
    
    /**
     * reverseDirection: live thievesFIFO that controls the crawl movement and
     * join at room FIFO to wait others
     *
     * Called by the ordinary Thief
     * @param id the thief Id
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp reverseDirection(int id, VectorTimestamp vt)throws RemoteException;
    
    /**
     * crawl out: crawl out processes
     *
     * Called by the ordinary Thief
     * @param id the thief Id
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp crawlout(int id, VectorTimestamp vt)throws RemoteException;
    
    /**
     * sendAssaultParty: send the party waking up the first thief in the FIFO
     *
     * Called by the Master Thief
     *
     * @param roomID the room this party must go
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp sendAssaultParty(Integer roomID, VectorTimestamp vt)throws RemoteException;
    
    /**
     * get the room to go
     *
     * @return the room of this party
     * @throws java.rmi.RemoteException
     */
    public Integer getRoomId()throws RemoteException;
    
    public void signalShutdown() throws RemoteException;
}
