
package dir_ServerMuseum.structures;

/**
 * this data type define a data structure for the possible states of the ordinary thief
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public enum OrdThiefStates {
    
    OUTSIDE(0,"OUT "),              // state of outside, in concentration site
    CRAWLING_INWARDS(1,"CRIN"),     // state of crawling in to musuem
    AT_A_ROOM (2, "ROOM"),          // state of in the room of the musuem
    CRAWLING_OUTWARDS(3, "CROU");   // state of crawling out to control collection site

    private final int id;           // identification of the state
    private final String state;    // representation of the state for the log

    /**
     * 
     * @param id    identification of the state
     * @param state representation of the state for the log
     */
    private OrdThiefStates(int _id, String _state) {
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
