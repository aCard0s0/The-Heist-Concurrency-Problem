package entities;

import client.shareRegions.AssaultPartyStub;
import client.shareRegions.ConcentrationSiteStub;
import client.shareRegions.ControlCollectionSiteStub;
import client.shareRegions.GeneralRepositoryStub;
import configurations.Config;

/**
 * This data type defines the MasterThief Thread
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class MasterThief extends Thread {

    /**
     * Assault Parties
     *
     * @serialField assaultPartys
     */
    private AssaultPartyStub[] assaultPartys;

    /**
     * Concentration site of ordinary thieves
     *
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
     * general repository sharing region
     *
     * @serialField generalRepository
     */
    private GeneralRepositoryStub generalRepository;

    /**
     * MasterThief Instantiation
     * 
     * @param assaultPartys the assault party shared regions 
     * @param concentrationSite concentration site shared region
     * @param collectionSite control and collection site shared region
     * @param generalRepository general repository shared region
     */
    public MasterThief(AssaultPartyStub[] assaultPartys, ConcentrationSiteStub concentrationSite, ControlCollectionSiteStub collectionSite, GeneralRepositoryStub generalRepository) {
        this.assaultPartys = assaultPartys;
        this.concentrationSite = concentrationSite;
        this.collectionSite = collectionSite;
        this.generalRepository = generalRepository;
    }

    @Override
    public void run() {
        collectionSite.startOperations();
        int option;
        Integer assaultPartyId;
        Integer roomId;
        while ((option = collectionSite.appraiseSit()) != Config.OPTION_END_OF_OPERATIONS) {
            switch (option) {
                case 0: {
                    assaultPartyId = collectionSite.prepareAssaultParty_1();
                    roomId = collectionSite.prepareAssaultParty_2(assaultPartyId);
                    concentrationSite.prepareAssaultParty(assaultPartyId);
                    assaultPartys[assaultPartyId].sendAssaultParty(roomId);
                    break;
                }
                case 1:
                    collectionSite.takeRest();
                    collectionSite.collectCanvas();
                    break;
            }
        }
        concentrationSite.sumUpResults();
        collectionSite.sumUpResults();
    }
    
    /**
     * get the the general repository instance
     *
     * @return general repository instance
     */
    public GeneralRepositoryStub getGeneralRepository() {
        return generalRepository;
    }

}
