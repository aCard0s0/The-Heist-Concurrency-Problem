/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dir_ServerControlCollectionSite.interfaces;

import dir_ServerControlCollectionSite.structures.MasterThiefStates;
import dir_ServerControlCollectionSite.structures.OrdThiefSituation;
import dir_ServerControlCollectionSite.structures.OrdThiefStates;
import dir_ServerControlCollectionSite.structures.VectorTimestamp;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * this data type represent the interface for the general repository
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public interface GeneralRepositoryInterface extends Remote{
    
    public void setState(MasterThiefStates state, VectorTimestamp vt) throws RemoteException;
    
    public void setState(int id, OrdThiefStates state, VectorTimestamp vt) throws RemoteException;
    
    public void setSituation(int id, OrdThiefSituation situation, VectorTimestamp vt) throws RemoteException;
    
    public void initializeRepository(int tId, Integer max, VectorTimestamp vt) throws RemoteException;
    
    public void setRollACanvas(int roomId, VectorTimestamp vt) throws RemoteException;
    
    public void initializePartyProperties(int assaultPartyId, VectorTimestamp vt) throws RemoteException;
    
    public void setCarryingCanvas(int id, Integer carryingCanvas, VectorTimestamp vt) throws RemoteException;
    
    public int getCarryingCanvas(int id) throws RemoteException;
    
    public void setPosition(int id, Integer position, VectorTimestamp vt) throws RemoteException;

    public void setAssaultPartyRoom(int asId, int roomId, VectorTimestamp vt) throws RemoteException;
    
    public void reportStatus(VectorTimestamp vt) throws RemoteException;
    public void reportInitialStatus(VectorTimestamp vt) throws RemoteException;
    public Object[] getNumWaitingThieves(VectorTimestamp vt) throws RemoteException;
    public Object[] getAssaultParty1Members(VectorTimestamp vt) throws RemoteException;
    public Object[] getAssaultParty2Members(VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp setAssaultPartyMember(Integer tId, Integer asId, VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp removeAssaultPartyMember(Integer tId, Integer asId, VectorTimestamp vt) throws RemoteException;
    public Object[] getAspRoom(int asp, VectorTimestamp vt) throws RemoteException;
    public Object[] getOrdinaryT_MD(int id, VectorTimestamp vt) throws RemoteException;
    public Object[] getCarryingCanvas(int id, VectorTimestamp vt) throws RemoteException;
    public MuseumInterface getMuseumInterface() throws RemoteException;
    public void setMuseumInterface(MuseumInterface museumInterface) throws RemoteException;
    public VectorTimestamp reportFinalStatus(int result, VectorTimestamp vt) throws RemoteException;
    
    public void Shutdown() throws RemoteException;
}
