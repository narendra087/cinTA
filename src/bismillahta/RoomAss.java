/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bismillahta;

/**
 *
 * @author Umar Rizki
 */
public class RoomAss {
    private int room_id;
    private int room_pen;
    
    RoomAss(int room_id, int room_pen){
        this.room_id = room_id;
        this.room_pen = room_pen;
    }

    /**
     * @return the room_id
     */
    public int getRoom_id() {
        return room_id;
    }

    /**
     * @param room_id the room_id to set
     */
    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    /**
     * @return the room_pen
     */
    public int getRoom_pen() {
        return room_pen;
    }

    /**
     * @param room_pen the room_pen to set
     */
    public void setRoom_pen(int room_pen) {
        this.room_pen = room_pen;
    }
}
