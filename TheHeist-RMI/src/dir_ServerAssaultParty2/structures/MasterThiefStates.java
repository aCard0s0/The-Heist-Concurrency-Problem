package dir_ServerAssaultParty2.structures;

/**
 * this data type define a data structure for the possible states of the master thief
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public enum MasterThiefStates {
    
    PLANNING_THE_HEIST(0, "PLAN"),          // state of planing the heist
    DECIDING_WHAT_TO_DO(1, "DECI"),         // state on deciding what to do
    ASSEMBLING_THE_GROUP(2, "ASSG"),        // state on assembling the group
    WAITING_FOR_GROUP_ARRIVAL(3, "WAIT"),   // state on waiting for group
    PRESENTING_THE_REPORT(4, "REPO");       // state for report the final result// state for report the final result
    
    private final int id;                   // identification of the state
    private final String state;             // representation of the state for the log

    /**
     * 
     * @param identification  code of the state
     * @param state representation of the state for the log
     */
    private MasterThiefStates(int _id, String _state) {
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
