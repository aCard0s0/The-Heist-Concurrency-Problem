
package mainProgram;

import client.shareRegions.AssaultPartyStub;
import client.shareRegions.ConcentrationSiteStub;
import client.shareRegions.ControlCollectionSiteStub;
import client.shareRegions.GeneralRepositoryStub;
import configurations.Config;
import configurations.Ips;
import configurations.Ports;
import entities.MasterThief;

/**
 * this data type define the main program for the master thief
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class MasterThiefMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ConcentrationSiteStub concStub = new ConcentrationSiteStub(Ips.IP_CONCENTRATION_SITE, Ports.PORT_CONCENTRATION_SITE);
        ControlCollectionSiteStub ccolStub = new ControlCollectionSiteStub(Ips.IP_CONTROL_COLLETION_SITE, Ports.PORT_CONTROL_COLLETION_SITE);
        GeneralRepositoryStub repoStub = new GeneralRepositoryStub(Ips.IP_GENERAL_REPOSITORY, Ports.PORT_GENERAL_REPOSITORY);
        
        AssaultPartyStub[] partyStub = new AssaultPartyStub[Config.N_ASSAULTPARTIES];
        partyStub[0] = new AssaultPartyStub(Ips.IP_ASSAULT_PARTY_1, Ports.PORT_ASSAULT_PARTY_1);
        partyStub[1] = new AssaultPartyStub(Ips.IP_ASSAULT_PARTY_2, Ports.PORT_ASSAULT_PARTY_2);
        
        /*instantiate the master thief*/
        MasterThief masterThief = new MasterThief(partyStub, concStub, ccolStub, repoStub);
        
        /*start master thief*/
        masterThief.start();
        
        /*wait master thief to finish*/
        try {
            masterThief.join();
            System.out.println("\nAssaulto completado com sucesso!");
        } catch (InterruptedException ex) {
            System.out.println("Erro acabar Thread Master Thief.");
        }
        
        concStub.shutDown();
        ccolStub.shutDown();
        repoStub.shutDown();
        partyStub[0].shutDown();
        partyStub[1].shutDown();
    }
    
}
