package sharedRegions;

import configurations.Config;
import server.proxys.MuseumProxy;

/**
 * This data type defines museum
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public class Museum {
    /**
     * list of room at the museum
     *
     * @serialField rooms
     */
    private Room[] rooms;
    /**
     * museum instantiation
     */
    public Museum() {
        this.rooms = new Room[Config.N_ROOMS];

        /*each room instantiation*/
        for (int i = 0; i < Config.N_ROOMS; i++) {
            this.rooms[i] = new Room(i);
        }
    }
    /**
     * rollACanvas: take a canvas from a specific room
     * @param roomId the room id
     * @return 1 if there's a canvas or 0 if not
     */
    public synchronized Integer rollACanvas(int roomId) {

        if (rooms[roomId].getNumCanvas() > 0) {
            rooms[roomId].setNumCanvas(rooms[roomId].getNumCanvas() - 1);
            /*update repository*/
            ((MuseumProxy) Thread.currentThread()).getGeneralRepositoryStub().setRollACanvas(roomId);
            return 1;
        }
        return 0;
    }
    /**
     * get a room distance
     * @param roomId the room id
     * @return the room distance
     */
    public synchronized int getRoomDistance(int roomId) {
        return rooms[roomId].getDistance();
    }
    /**
     * get the number of canvas in a room
     * @param roomId the room id
     * @return the number of canvas
     */
    public synchronized int getNumCanvas(int roomId) {
        return rooms[roomId].getNumCanvas();
    }
}
