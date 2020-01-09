package dir_ServerAssaultParty1.structures;

import java.util.Random;

/**
 *  this data type define final constants
 * 
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class Config {
    /**
     * class instance and instantiation
     * @serial instance
     */
    private static Config instance = new Config();
    /**
     * random variable instance
     * @serial random
     */
    private static Random random = new Random();
    
    /**
     * lower bound of ordinary thief maximum displacement
     * @serial THIEF_DISPLACEMENT_LOWER_BOUND
     */
    public static final Integer THIEF_DISPLACEMENT_LOWER_BOUND = 2;
    /**
     * upper bound of ordinary thief maximum displacement
     * @serial THIEF_DISPLACEMENT_UPPER_BOUND
     */
    public static final Integer THIEF_DISPLACEMENT_UPPER_BOUND = 6;
    /**
     * lower bound of the range of the distance to the outside gathering site 
     * @serial ROOM_DISTANCE_LOWER_BOUND
     */
    public static final Integer ROOM_DISTANCE_LOWER_BOUND = 15;
    
    /**
     * upper bound of the range of the distance to the outside gathering site 
     * @serial ROOM_DISTANCE_UPPER_BOUND
     */
    public static final Integer ROOM_DISTANCE_UPPER_BOUND = 30;
    /**
     * lower bound of the range of the number of paintings hanging in each room 
     * @serial ROOM_NCANVAS_LOWER_BOUND
     */
    public static final Integer ROOM_NCANVAS_LOWER_BOUND = 8;
    /**
     * upper bound of the range of the number of paintings hanging in each room 
     * @serial ROOM_NCANVAS_UPPER_BOUND
     */
    public static final Integer ROOM_NCANVAS_UPPER_BOUND = 16;
    /**
     * number of exhibition rooms having paintings in display
     * @serial N_ROOMS
     */
    public static final Integer N_ROOMS = 5;
    /**
     * number of ordinary thieves
     *
     * @serial N_ORDINARY_THIEVES
     */
    public static final Integer N_ORDINARY_THIEVES= 6;
    
    /**
     * number of ordinary thieves in each assault party (number of assault party members)
     *
     * @serial N_ASSAULTPARTY_THIEVES
     */
    public static final Integer N_ASSAULTPARTY_THIEVES= 3;
    /**
     * number of assaultParty shared regions that must be used, or number of assault parties that will be formed
     *
     * @serial N_ASSAULTPARTIES
     */
    public static final Integer N_ASSAULTPARTIES= 2;
    /**
     * separation between thief positions, thieves can never be separated by a distance larger than S length units 
     * maximum separation limit between thieves crawling in line is 3 length units
     * @serial THIEVES_SEPARATION_LENGH
     */
    public static final Integer THIEVES_SEPARATION_LENGH= 3;
    
    /**
     * Master Thief Option: Prepare Assault Party
     * @serial THIEVES_SEPARATION_LENGH
     */
    public static final Integer OPTION_PREPARE_ASSAULTPARTY = 0;
    /**
     * Master Thief Option: take a rest
     * @serial OPTION_TAKE_REST
     */
    public static final Integer OPTION_TAKE_REST = 1;
    /**
     * Master Thief Option: End of Operations
     * @serial OPTION_END_OF_OPERATIONS
     */
    public static final Integer OPTION_END_OF_OPERATIONS = 2;
    /**
     * Logging file path
     * @serial LOGFILE_PATH
     */
    public static final String LOGFILE_PATH = System.getProperty("user.dir")+"/logFile.txt";
    /**
     * definition of possible master thief states
     */
    public static enum MasterThief_State{PLTH,DWTD,ASAG,WFAR,PREP};
    
    
    /**
     * Class Instantiation
     * A private Constructor prevents any other class from instantiating
     */
    private Config() {
    }
    
    /**
     * static 'instance' method 
     * @return the class instance
     */
    public static Config getInstance(){
        return instance;
    }
    
    /**
     * generate a random integer number in a specific range
     * 
     * @param start lower bound of the range
     * @param end upper bound of the range
     * 
     * @return the generated number in the specified range
     */
    public static int generateRandom(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long) end - (long) start + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long) (range * random.nextDouble());
        int randomNumber = (int) (fraction + start);
        return randomNumber;
    }

}
