package entities;

import client.shareRegions.AssaultPartyStub;
import client.shareRegions.ConcentrationSiteStub;
import client.shareRegions.ControlCollectionSiteStub;
import client.shareRegions.GeneralRepositoryStub;
import client.shareRegions.MuseumStub;
import configurations.Config;

/**
 *  This data type defines the ordinary Thief Thread 
 * 
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class Thief extends Thread{
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
    private ConcentrationSiteStub concentrationSite;
     /**
     * control and collection site sharing region
     *
     * @serialField collectionSite
     */
    private ControlCollectionSiteStub collectionSite;
    /**
     * assault party
     *
     * @serialField assaultParty
     */
    private AssaultPartyStub[] assaultPartys;
    
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
    private GeneralRepositoryStub generalRepository;
    /**
     * the museum were the heist take place
     *
     * @serialField museum
     */
    private MuseumStub museum;

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
    public Thief(Integer thiefId, ConcentrationSiteStub concentrationSite, ControlCollectionSiteStub collectionSite, AssaultPartyStub[] assaultPartys, GeneralRepositoryStub generalRepository, MuseumStub museum) {

        this.thiefId = thiefId;
        /* max displacement is a random number between 2 and 6*/
        this.maxDisplacement = Config.generateRandom(Config.THIEF_DISPLACEMENT_LOWER_BOUND, Config.THIEF_DISPLACEMENT_UPPER_BOUND);
        this.concentrationSite = concentrationSite;
        this.collectionSite = collectionSite;
        this.assaultPartys = assaultPartys;
        this.generalRepository = generalRepository;
        this.museum = museum;
    }

    @Override
    public void run() {
        /*init repository*/
        generalRepository.initializeRepository(thiefId,maxDisplacement);
        /*do operations*/
        while ((assaultPartyId = concentrationSite.amINeeded(thiefId)) != -1) {
            assaultPartys[assaultPartyId].prapareExcursion(thiefId);
            assaultPartys[assaultPartyId].crawlin(thiefId);
            assaultPartys[assaultPartyId].reverseDirection(thiefId);
            assaultPartys[assaultPartyId].crawlout(thiefId);
            collectionSite.handACanvas(thiefId,assaultPartyId);
        }
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
    public GeneralRepositoryStub getGeneralRepository() {
        return generalRepository;
    }
    /**
     * get the museum instance
     * @return museum instance
     */
    public MuseumStub getMuseum() {
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
    public AssaultPartyStub[] getAssaultPartys() {
        return assaultPartys;
    }
}
