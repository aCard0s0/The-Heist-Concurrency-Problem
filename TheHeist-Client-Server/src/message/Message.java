package message;

import entities.states.MasterThiefStates;
import entities.states.OrdThiefSituation;
import entities.states.OrdThiefStates;
import java.io.Serializable;

/**
 * This data type defines the messages that will be exchanged between clients
 * and the server, the message passing uses a TCP connection
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class Message implements Serializable {

    /**
     * serialization UID
     *
     * @serialField serialVersionUID
     */
    private static final long serialVersionUID = 1001L;
    /**
     * the client ID
     *
     * @serial idSender
     */
    private Integer idSender;
    /**
     * the message type ID
     *
     * @serial idMethod
     */
    private String idMethod;
    /**
     * state of master thief
     *
     * @serial masterState
     */
    private MasterThiefStates masterState;
    /**
     * state of ordinary thieves
     *
     * @serial thiefState
     */
    private OrdThiefStates thiefState;
    /**
     * the situation of a ordinary thief
     *
     * @serial thiefSituation
     */
    private OrdThiefSituation thiefSituation;
    /**
     * input parameter, an integer value
     *
     * @serial inParam
     */
    private Integer inParam;

    /**
     * message instantiation (form 1), only defines the message type
     *
     * @param idMethod message type
     */
    public Message(String idMethod) {
        this.idMethod = idMethod;
    }

    /**
     * message instantiation (form 2), defines the message type and sender
     *
     * @param idMethod message type
     * @param idSender the client(sender) ID, an primitive integer
     */
    public Message(String idMethod, int idSender) {
        this.idMethod = idMethod;
        this.idSender = idSender;
    }
    /**
     * message instantiation (form 3), defines the message type and sender
     *
     * @param idMethod message type
     * @param inParam the input parameter, an refence integer
     */
    public Message(String idMethod, Integer inParam) {
        this.idMethod = idMethod;
        this.inParam = inParam;
    }

    /**
     * message instantiation (form 4), defines the message type, the sender and
     * an integer parameter
     *
     * @param idMethod message type
     * @param idSender the client(sender) ID
     * @param inParam an integer parameter
     */
    public Message(String idMethod, Integer idSender, Integer inParam) {
        this.idMethod = idMethod;
        this.idSender = idSender;
        this.inParam = inParam;
    }

    /**
     * message instantiation (form 5), defines the message type and a master
     * thief state
     *
     * @param idMethod message type
     * @param masterState the state of master thief
     */
    public Message(String idMethod, MasterThiefStates masterState) {
        this.idMethod = idMethod;
        this.masterState = masterState;
    }
    /**
     * message instantiation (form 6), defines the message type and a ordinary
     * thief state
     *
     * @param idMethod message type
     * @param thiefState the state of ordinary thief
     */
    public Message(String idMethod, OrdThiefStates thiefState) {
        this.idMethod = idMethod;
        this.thiefState = thiefState;

    }
    /**
     * message instantiation (form 7), defines the message type and a ordinary
     * thief situation
     *
     * @param idMethod message type
     * @param thiefSituation the situation of ordinary thief
     */
    public Message(String idMethod, OrdThiefSituation thiefSituation) {
        this.idMethod = idMethod;
        this.thiefSituation = thiefSituation;
    }
    /**
     * get the client or message sender
     *
     * @return ID of the sender
     */
    public Integer getIdSender() {
        return idSender;
    }
    /**
     * get the message type
     *
     * @return type of message
     */
    public String getIdMethod() {
        return idMethod;
    }
    /**
     * get the state of the master thief
     *
     * @return master thief state
     */
    public MasterThiefStates getMasterState() {
        return masterState;
    }
    /**
     * get the state of a ordinary thief
     *
     * @return ordinary thief state
     */
    public OrdThiefStates getThiefState() {
        return thiefState;
    }
    /**
     * get the situation of a ordinary thief
     *
     * @return ordinary thief situation
     */
    public OrdThiefSituation getThiefSituation() {
        return thiefSituation;
    }
    /**
     * get the input parameter
     *
     * @return the input parameter
     */
    public Integer getInParam() {
        return inParam;
    }
    
    /**
     * set the identifier of the sender
     *
     * @param idSender the Id of the sender
     */
    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }
    
    @Override
    public String toString() {
        return "Method= " + idMethod + "\n"
                + "Sender= " + idSender;
    }

}
