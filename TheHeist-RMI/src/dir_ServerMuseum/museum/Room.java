
package dir_ServerMuseum.museum;

import dir_ServerMuseum.structures.Config;


/**
 * This data type defines the Museum Room
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class Room {
    /**
     * the room Id
     *
     * @serialField roomID
     */
    private int roomID;
    /**
     * the number of paintings hanging in the room, is a random number between 8
     * and 16
     *
     * @serialField numCanvas
     */
    private int numCanvas;
    /**
     * distance to the outside gathering site, is a random number between 15 and
     * 30
     *
     * @serialField distance
     */
    private int distance;
    /**
     * Room Instantiation
     *
     * @param roomID the identifier of the room
     */
    public Room(int roomID) {
        this.roomID = roomID;
        this.numCanvas = Config.generateRandom(Config.ROOM_NCANVAS_LOWER_BOUND, Config.ROOM_NCANVAS_UPPER_BOUND);
        this.distance = distance = Config.generateRandom(Config.ROOM_DISTANCE_LOWER_BOUND, Config.ROOM_DISTANCE_UPPER_BOUND);
    }
    /**
     * get the number of canvas in the room
     * @return the number of canvas
     */
    public synchronized int getNumCanvas() {
        return numCanvas;
    }
    /**
     * get the distance from outside
     * @return the distance
     */
    public synchronized int getDistance() {
        return distance;
    }
    /**
     * set the distance from outside
     * @param numCanvas the number of canvas
     */
    public synchronized void setNumCanvas(int numCanvas) {
        this.numCanvas = numCanvas;
    }
    
    
}
