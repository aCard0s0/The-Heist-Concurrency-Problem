
package dir_ClientOrdinaryThief.structures;

/**
 * this data type define a data structure for the possible situations of the ordinary thief
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public enum OrdThiefSituation {
    
    WAIT_TO_JOIN_PARTY(0, "W"),     // state of outside, in concentration site
    IN_PARTY(1,"P");                // state of crawling in to musuem

    private final int id;           // identification of the state
    private final String state;    // representation of the state for the log

    /**
     * 
     * @param id    identification of the state
     * @param state representation of the state for the log
     */
    private OrdThiefSituation(int _id, String _state) {
        id = _id;
        state = _state;
    }
    
    /**
     * 
     * @return the identification of state
     */
    public int getId() {
        return id;
    }
    
    @Override
    public String toString(){
        return state;
    }
}
