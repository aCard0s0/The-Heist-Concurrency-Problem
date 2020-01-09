/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dir_ClientOrdinaryThief.ordinaryThief;

import dir_ClientOrdinaryThief.interfaces.AssaultPartyInterface;
import dir_ClientOrdinaryThief.interfaces.ConcentrationSiteInterface;
import dir_ClientOrdinaryThief.interfaces.ControlCollectionSiteInterface;
import dir_ClientOrdinaryThief.interfaces.GeneralRepositoryInterface;
import dir_ClientOrdinaryThief.interfaces.MuseumInterface;
import dir_ClientOrdinaryThief.structures.Config;
import dir_ClientOrdinaryThief.structures.VectorTimestamp;
import java.rmi.RemoteException;

/**
 *
 * @author User
 */
public class Thief extends Thread {
    
    /**
     * Id of ordinary thief
     * @serialField thiefId
     */
    private Integer thiefId;
    /**
     * Maximum displacement of ordinary thief, specific to each thief.
     * @serialField maxDisplacement
     */
    private Integer maxDisplacement;
    
    /**
     * Concentration site of ordinary thieves
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
     * assault party
     *
     * @serialField assaultParty
     */
    private AssaultPartyInterface[] assaultPartys;
    
    /**
     * assault party this thief joined
     *
     * @serialField assaultPartyId
     */
    private Integer assaultPartyId;
    
    /**
     * general repository sharing region
     *
     * @serialField generalRepository
     */
    private GeneralRepositoryInterface generalRepository;
    /**
     * the museum were the heist take place
     *
     * @serialField museum
     */
    private MuseumInterface museum;
    
    private final VectorTimestamp myClock;
    
   /**
     * Thief Instantiation
     * 
     * @param thiefId ordinary thief identifier
     * @param assaultPartys the assault party shared regions 
     * @param concentrationSite concentration site shared region
     * @param collectionSite control and collection site shared region
     * @param generalRepository general repository shared region
     * @param museum the museum
     */
    public Thief(Integer thiefId, ConcentrationSiteInterface concentrationSite, ControlCollectionSiteInterface collectionSite, AssaultPartyInterface[] assaultPartys, GeneralRepositoryInterface generalRepository, MuseumInterface museum) {

        this.thiefId = thiefId;
        /* max displacement is a random number between 2 and 6*/
        this.maxDisplacement = Config.generateRandom(Config.THIEF_DISPLACEMENT_LOWER_BOUND, Config.THIEF_DISPLACEMENT_UPPER_BOUND);
        this.concentrationSite = concentrationSite;
        this.collectionSite = collectionSite;
        this.assaultPartys = assaultPartys;
        this.generalRepository = generalRepository;
        this.museum = museum;
        
        myClock = new VectorTimestamp(Config.N_ORDINARY_THIEVES+1, thiefId);
    }

    @Override
    public void run() {
        
        Object[] resObj;
        VectorTimestamp resVT;
        
        try {
            /*init repository*/
            generalRepository.initializeRepository(thiefId, maxDisplacement, myClock.clone());
        
            /*do operations*/
            myClock.increment();
            while ( (int)(resObj = concentrationSite.amINeeded(thiefId, myClock.clone()))[1] != -1) {
                myClock.update((VectorTimestamp) resObj[0]);
                
                myClock.increment();
                resVT = assaultPartys[assaultPartyId].prapareExcursion(thiefId, myClock.clone());
                myClock.update(resVT);
                
                myClock.increment();
                resVT = assaultPartys[assaultPartyId].crawlin(thiefId, myClock.clone());
                myClock.update(resVT);
                
                myClock.increment();
                resVT = assaultPartys[assaultPartyId].reverseDirection(thiefId, myClock.clone());
                myClock.update(resVT);
                
                myClock.increment();
                resVT = assaultPartys[assaultPartyId].crawlout(thiefId, myClock.clone());
                myClock.update(resVT);
                
                myClock.increment();
                resVT = collectionSite.handACanvas(thiefId,assaultPartyId, myClock.clone());
                myClock.update(resVT);
                
                myClock.increment();
            }
            generalRepository.Shutdown();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Ladrão "+thiefId+" acabou execução!");
    }
    /**
     * get the ordinary thief identifier
     * @return thief ID
     */
    public Integer getThiefId() {
        return thiefId;
    }
    /**
     * get the ordinary thief maximum displacement
     * @return maximum displacement
     */
    public int getMaxDisplacement() {
        return maxDisplacement;
    }
    /**
     * get the general repository instance
     * @return general repository instance
     */
    public GeneralRepositoryInterface getGeneralRepository() {
        return generalRepository;
    }
    /**
     * get the museum instance
     * @return museum instance
     */
    public MuseumInterface getMuseum() {
        return museum;
    }
    /**
     * get the assault party Id of the ordinary thief
     * @return assault party ID
     */
    public Integer getAssaultPartyId() {
        return assaultPartyId;
    }
    /**
     * get the assault parties instances
     * @return assault parties instances
     */
    public AssaultPartyInterface[] getAssaultPartys() {
        return assaultPartys;
    }
}
