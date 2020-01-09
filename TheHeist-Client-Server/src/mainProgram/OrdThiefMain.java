
package mainProgram;

import client.shareRegions.AssaultPartyStub;
import client.shareRegions.ConcentrationSiteStub;
import client.shareRegions.ControlCollectionSiteStub;
import client.shareRegions.GeneralRepositoryStub;
import client.shareRegions.MuseumStub;
import configurations.Config;
import configurations.Ips;
import configurations.Ports;
import entities.Thief;

/**
 * this data type define the main program for the ordinary thief
 * 
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class OrdThiefMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ConcentrationSiteStub concStub = new ConcentrationSiteStub(Ips.IP_CONCENTRATION_SITE, Ports.PORT_CONCENTRATION_SITE);
        ControlCollectionSiteStub ccolStub = new ControlCollectionSiteStub(Ips.IP_CONTROL_COLLETION_SITE, Ports.PORT_CONTROL_COLLETION_SITE);
        MuseumStub museumStub = new MuseumStub(Ips.IP_MUSUEM, Ports.PORT_MUSUEM);
        GeneralRepositoryStub repoStub = new GeneralRepositoryStub(Ips.IP_GENERAL_REPOSITORY, Ports.PORT_GENERAL_REPOSITORY);

        AssaultPartyStub[] partyStub = new AssaultPartyStub[Config.N_ASSAULTPARTIES];
        partyStub[0] = new AssaultPartyStub(Ips.IP_ASSAULT_PARTY_1, Ports.PORT_ASSAULT_PARTY_1);
        partyStub[1] = new AssaultPartyStub(Ips.IP_ASSAULT_PARTY_2, Ports.PORT_ASSAULT_PARTY_2);

        /*instantiate the thieves array*/
        Thief[] thiefs = new Thief[Config.N_ORDINARY_THIEVES];
        /*instantiate each thief*/
        for (int i = 0; i < Config.N_ORDINARY_THIEVES; i++) {
            thiefs[i] = new Thief(i, concStub, ccolStub, partyStub, repoStub, museumStub);
            thiefs[i].start();
        }

        /*wait each thief to finish*/
        for (int i = 0; i < Config.N_ORDINARY_THIEVES; i++) {
            try {
                thiefs[i].join();
                System.out.println(i +"º Thief foi terminou com sucesso!");
            } catch (InterruptedException ex) {
                System.out.println("Erro acabar Thread Ordinary Thief.");
            }
        }

        museumStub.shutDown();
    }

}
