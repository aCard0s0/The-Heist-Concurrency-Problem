/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dir_ClientMasterThief.masterThief;

import dir_ClientMasterThief.interfaces.AssaultPartyInterface;
import dir_ClientMasterThief.interfaces.ConcentrationSiteInterface;
import dir_ClientMasterThief.interfaces.ControlCollectionSiteInterface;
import dir_ClientMasterThief.interfaces.GeneralRepositoryInterface;
import dir_ClientMasterThief.structures.Config;
import dir_ClientMasterThief.structures.VectorTimestamp;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class MasterThief extends Thread {

    /**
     * Assault Parties
     *
     * @serialField assaultPartys
     */
    private AssaultPartyInterface[] assaultPartys;

    /**
     * Concentration site of ordinary thieves
     *
     * @serialField concentrationSite
     */
    private ConcentrationSiteInterface concentrationSite;
    /**
     * control and collection site sharing region
     *
     * @serialField collectionSite
     */
    private ControlCollectionSiteInterface collectionSite;
    /**
     * general repository sharing region
     *
     * @serialField generalRepository
     */
    private GeneralRepositoryInterface generalRepository;

    private final VectorTimestamp myClock;
    private VectorTimestamp receivedClock;

    public MasterThief(AssaultPartyInterface[] assaultPartys, ConcentrationSiteInterface concentrationSite, ControlCollectionSiteInterface collectionSite, GeneralRepositoryInterface generalRepository) {
        this.assaultPartys = assaultPartys;
        this.concentrationSite = concentrationSite;
        this.collectionSite = collectionSite;
        this.generalRepository = generalRepository;

        this.myClock = new VectorTimestamp(Config.N_ORDINARY_THIEVES + 1, Config.N_ORDINARY_THIEVES);
    }

    @Override
    public void run() {
        try {
            myClock.increment();
            receivedClock = collectionSite.startOperations(myClock.clone());
            myClock.update(receivedClock);
            
            int option;
            Object[] opt;
            Integer assaultPartyId;
            Integer roomId;
            while ((int)(opt = collectionSite.appraiseSit(myClock.clone()))[1] != Config.OPTION_END_OF_OPERATIONS) {
                myClock.update((VectorTimestamp)opt[0]);
                option = (int)opt[1];
                
                switch (option) {
                    case 0: {
                        myClock.increment();
                        opt = collectionSite.prepareAssaultParty_1(myClock.clone());
                        assaultPartyId = (int)opt[1];
                        myClock.update((VectorTimestamp)opt[0]);
                        
                        opt = collectionSite.prepareAssaultParty_2(assaultPartyId,myClock.clone());
                        myClock.update((VectorTimestamp)opt[0]);
                        roomId = (int)opt[1];
                        
                        myClock.update(concentrationSite.prepareAssaultParty(assaultPartyId,myClock.clone()));
                        
                        myClock.increment();
                        myClock.update(assaultPartys[assaultPartyId].sendAssaultParty(roomId,myClock.clone()));
                        break;
                    }
                    case 1:
                        myClock.increment();
                        myClock.update(collectionSite.takeRest(myClock.clone()));
                        
                        myClock.increment();
                        myClock.update(collectionSite.collectCanvas(myClock.clone()));
                        break;
                }
            }
            myClock.increment();
            myClock.update(concentrationSite.sumUpResults(myClock.clone()));
            collectionSite.sumUpResults(myClock.clone());
            generalRepository.Shutdown();

        } catch (RemoteException ex) {
            Logger.getLogger(MasterThief.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MasterThief.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
