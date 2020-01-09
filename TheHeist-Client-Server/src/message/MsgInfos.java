package message;

/**
 * This data type defines the messages that can be used in each shared region
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class MsgInfos {
    /**
     * send shutdown message
     *
     * @serial SM_shutDown
     */
    public static final String SM_SHUTDOWN = "SM_SHUTDOWN";
    /**
     * shutdown message RESPONSE
     *
     * @serial RM_SHUTDOWN
     */
    public static final String RM_SHUTDOWN = "RM_SHUTDOWN";
    /**
     * send prepare excursion message
     *
     * @serial SM_PRAPAREEXCURSION
     */
    public static final String SM_PRAPAREEXCURSION = "SM_PRAPAREEXCURSION";
    /**
     * prepare excursion message response
     *
     * @serial RM_PRAPAREEXCURSION
     */
    public static final String RM_PRAPAREEXCURSION = "RM_PRAPAREEXCURSION";
    /**
     * send crawl in message
     *
     * @serial SM_CRAWLIN
     */
    public static final String SM_CRAWLIN = "SM_CRAWLIN";
    /**
     * crawl in message response
     *
     * @serial RM_CRAWLIN
     */
    public static final String RM_CRAWLIN = "RM_CRAWLIN";
    /**
     * send reverse direction message
     *
     * @serial SM_REVERSEDIRECTIONN
     */
    public static final String SM_REVERSEDIRECTIONN = "SM_REVERSEDIRECTIONN";
    /**
     * reverse direction message response
     *
     * @serial RM_REVERSEDIRECTION
     */
    public static final String RM_REVERSEDIRECTION = "RM_REVERSEDIRECTION";
    /**
     * send crawl out message
     *
     * @serial SM_CRAWLOUT
     */
    public static final String SM_CRAWLOUT = "SM_CRAWLOUT";
    /**
     * crawl out message response
     *
     * @serial RM_CRAWLOUT
     */
    public static final String RM_CRAWLOUT = "RM_CRAWLOUT";
    /**
     * send get museum room id message
     *
     * @serial SM_GETROOMID
     */
    public static final String SM_GETROOMID = "SM_GETROOMID";
    /**
     * get museum room id message response
     *
     * @serial RM_GETROOMID
     */
    public static final String RM_GETROOMID = "RM_GETROOMID";
    /**
     * send send assault party message
     *
     * @serial SM_SENDASSAULTPARTY
     */
    public static final String SM_SENDASSAULTPARTY = "SM_SENDASSAULTPARTY";
    /**
     * get send assault party message response
     *
     * @serial RM_SENDASSAULTPARTY
     */
    public static final String RM_SENDASSAULTPARTY = "RM_SENDASSAULTPARTY";
    /**
     * send am I needed message
     *
     * @serial SM_AMINEEDED
     */
    public static final String SM_AMINEEDED = "SM_AMINEEDED";
    /**
     * get am I needed message response
     *
     * @serial RM_AMINEEDED
     */
    public static final String RM_AMINEEDED = "RM_AMINEEDED";
    /**
     * send prepare assault party message
     *
     * @serial SM_PREPAREASSAULTPARTY
     */
    public static final String SM_PREPAREASSAULTPARTY = "SM_PREPAREASSAULTPARTY";
    /**
     * get prepare assault party message response
     *
     * @serial RM_PREPAREASSAULTPARTY
     */
    public static final String RM_PREPAREASSAULTPARTY = "RM_PREPAREASSAULTPARTY";
    /**
     * send prepare assault 1 party message
     *
     * @serial SM_PREPAREASSAULTPARTY
     */
    public static final String SM_PREPAREASSAULTPARTY_1 = "SM_PREPAREASSAULTPARTY_1";
    /**
     * get prepare assault party 1 message response
     *
     * @serial RM_PREPAREASSAULTPARTY
     */
    public static final String RM_PREPAREASSAULTPARTY_1 = "RM_PREPAREASSAULTPARTY_1";
    /**
     * send prepare assault 2 party message
     *
     * @serial SM_PREPAREASSAULTPARTY
     */
    public static final String SM_PREPAREASSAULTPARTY_2 = "SM_PREPAREASSAULTPARTY_2";
    /**
     * get prepare assault party 2 message response
     *
     * @serial RM_PREPAREASSAULTPARTY
     */
    public static final String RM_PREPAREASSAULTPARTY_2 = "RM_PREPAREASSAULTPARTY_2";
    /**
     * send get assault party 1 members message
     *
     * @serial SM_GETASSAULTPARTY1MEMBERS
     */
    public static final String SM_GETASSAULTPARTY1MEMBERS = "SM_GETASSAULTPARTY1MEMBERS";
    /**
     * get prepare assault party 1 members message response
     *
     * @serial RM_GETASSAULTPARTY1MEMBERS
     */
    public static final String RM_GETASSAULTPARTY1MEMBERS = "RM_GETASSAULTPARTY1MEMBERS";
    /**
     * send get assault party 2 members message
     *
     * @serial SM_GETASSAULTPARTY2MEMBERS
     */
    public static final String SM_GETASSAULTPARTY2MEMBERS = "SM_GETASSAULTPARTY2MEMBERS";
    /**
     * get assault party 2 members message response
     *
     * @serial RM_GETASSAULTPARTY2MEMBERS
     */
    public static final String RM_GETASSAULTPARTY2MEMBERS = "RM_GETASSAULTPARTY2MEMBERS";
    /**
     * send set assault party member message
     *
     * @serial SM_SETASSAULTPARTYMEMBER
     */
    public static final String SM_SETASSAULTPARTYMEMBER = "SM_SETASSAULTPARTYMEMBER";
    /**
     * get set assault party member message response
     *
     * @serial RM_SETASSAULTPARTYMEMBER
     */
    public static final String RM_SETASSAULTPARTYMEMBER = "RM_SETASSAULTPARTYMEMBER";
    /**
     * send remove assault party member message
     *
     * @serial SM_REMOVEASSAULTPARTYMEMBER
     */
    public static final String SM_REMOVEASSAULTPARTYMEMBER = "SM_REMOVEASSAULTPARTYMEMBER";
    /**
     * get remove assault party member message response
     *
     * @serial RM_REMOVEASSAULTPARTYMEMBER
     */
    public static final String RM_REMOVEASSAULTPARTYMEMBER = "RM_REMOVEASSAULTPARTYMEMBER";
    /**
     * send sum up results message
     *
     * @serial SM_SUMUPRESULTS
     */
    public static final String SM_SUMUPRESULTS = "SM_SUMUPRESULTS";
    /**
     * get sum up results message response
     *
     * @serial RM_SUMUPRESULTS
     */
    public static final String RM_SUMUPRESULTS = "RM_SUMUPRESULTS";
    /**
     * send set state of master thief message
     *
     * @serial SM_SETSTATEMT
     */
    public static final String SM_SETSTATEMT = "SM_SETSTATEMT";
    /**
     * get set state of master thief message response
     *
     * @serial RM_SETSTATEMT
     */
    public static final String RM_SETSTATEMT = "RM_SETSTATEMT";
    /**
     * send set state of ordinary thief message
     *
     * @serial SM_SETSTATET
     */
    public static final String SM_SETSTATET = "SM_SETSTATET";
    /**
     * get set state of ordinary thief message response
     *
     * @serial RM_SETSTATET
     */
    public static final String RM_SETSTATET = "RM_SETSTATET";
    /**
     * send set situation of ordinary thief message
     *
     * @serial SM_SETSITUATION
     */
    public static final String SM_SETSITUATION = "SM_SETSITUATION";
    /**
     * get set situation of ordinary thief message response
     *
     * @serial RM_SETSITUATION
     */
    public static final String RM_SETSITUATION = "RM_SETSITUATION";
    /**
     * send initialize repository message
     *
     * @serial SM_INITIALIZEREPOSITORY
     */
    public static final String SM_INITIALIZEREPOSITORY = "SM_INITIALIZEREPOSITORY";
    /**
     * get initialize repository message response
     *
     * @serial RM_INITIALIZEREPOSITORY
     */
    public static final String RM_INITIALIZEREPOSITORY = "RM_INITIALIZEREPOSITORY";
    /**
     * send set roll a canvas message
     *
     * @serial SM_SETROLLACANVAS
     */
    public static final String SM_SETROLLACANVAS = "SM_SETROLLACANVAS";
    /**
     * get set roll a canvas message response
     *
     * @serial RM_SETROLLACANVAS
     */
    public static final String RM_SETROLLACANVAS = "RM_SETROLLACANVAS";
     /**
     * send initialize party message
     *
     * @serial SM_INITIALIZEPARTYPROPERTIES
     */
    public static final String SM_INITIALIZEPARTYPROPERTIES = "SM_INITIALIZEPARTYPROPERTIES";
    /**
     * get initialize party message response
     *
     * @serial RM_INITIALIZEPARTYPROPERTIES
     */
    public static final String RM_INITIALIZEPARTYPROPERTIES = "RM_INITIALIZEPARTYPROPERTIES";
    /**
     * send set carrying canvas message
     *
     * @serial SM_SETCARRYINGCANVAS
     */
    public static final String SM_SETCARRYINGCANVAS = "SM_SETCARRYINGCANVAS";
    /**
     * get set carrying canvas message response
     *
     * @serial RM_SETCARRYINGCANVAS
     */
    public static final String RM_SETCARRYINGCANVAS = "RM_SETCARRYINGCANVAS";
    public static final String SM_getCarryingCanvas = "SM_getCarryingCanvas";
    public static final String RM_getCarryingCanvas = "RM_getCarryingCanvas";
    public static final String SM_setPosition = "SM_setPosition";
    public static final String RM_setPosition = "RM_setPosition";
    public static final String SM_setAssaultPartyRoom = "SM_setAssaultPartyRoom";
    public static final String RM_setAssaultPartyRoom = "RM_setAssaultPartyRoom";
    public static final String SM_getNumWaitingThieves = "SM_getNumWaitingThieves";
    public static final String RM_getNumWaitingThieves = "RM_getNumWaitingThieves";
    
    public static final String SM_REPORTSTATUS = "SM_REPORTSTATUS";
    public static final String RM_REPORTSTATUS = "RM_REPORTSTATUS";
    public static final String SM_reportFinalStatus = "SM_reportFinalStatus";
    public static final String RM_reportFinalStatus = "RM_reportFinalStatus";
    public static final String SM_reportInitialStatus = "SM_reportInitialStatus";
    public static final String RM_reportInitialStatus = "RM_reportInitialStatus";

    public static final String CONTROLCOLLECTION_SITE = "CONTROLCOLLECTION_SITE";
    // Master Thief  
    public static final String SM_STARTOPERATIONS = "SM_startOperations";
    public static final String RM_STARTOPERATIONS = "RM_startOperations";
    public static final String SM_APPRAISESIT = "SM_appraiseSit";
    public static final String RM_APPRAISESIT = "RM_appraiseSit";
    
    public static final String SM_TAKEREST = "SM_takeRest";
    public static final String RM_TAKEREST = "RM_takeRest";
    public static final String SM_COLLECTCANVAS = "SM_collectCanvas";
    public static final String RM_COLLECTCANVAS = "RM_collectCanvas";
    // Ordinary Thiefs
    public static final String SM_HANDACANVAS = "SM_handACanvas";
    public static final String RM_HANDACANVAS = "RM_handACanvas";

    public static final String MUSEUM = "MUSEUM";
    // Ordinary Thiefs
    public static final String SM_ROLLACANVAS = "SM_rollACanvas";
    public static final String RM_ROLLACANVAS = "RM_rollACanvas";
    public static final String SM_GETROOMDISTANCE = "SM_getRoomDistance";
    public static final String RM_GETROOMDISTANCE = "RM_getRoomDistance";
    public static final String SM_GETNUMCANVAS = "SM_getNumCanvas";
    public static final String RM_GETNUMCANVAS = "RM_getNumCanvas";
    public static final String SM_GETORDINARYT_MD = "SM_GETORDINARYT_MD";
    public static final String RM_GETORDINARYT_MD = "RM_GETORDINARYT_MD";
    public static final String SM_GETASPROOM = "SM_GETASPROOM";
    public static final String RM_GETASPROOM = "RM_GETASPROOM";
}
