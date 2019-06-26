/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bismillahta;

import java.util.ArrayList;

/**
 *
 * @author Umar Rizki
 */
public class Room {
    
    private int idRoom;
    private int roomcap;
    private ArrayList <Integer> travelroom = new ArrayList<>();
    private int[][][][] unRoom;

    Room(int idRoom, int roomcap) {
        this.idRoom = idRoom;
        this.roomcap = roomcap;
    }

    /**
     * @return the roomcap
     */
    public int getRoomcap() {
        return roomcap;
    }

    /**
     * @param roomcap the roomcap to set
     */
    public void setRoomcap(int roomcap) {
        this.roomcap = roomcap;
    }

    /**
     * @return the unRoom
     */
    public int[][][][] getUnRoom() {
        return unRoom;
    }

    /**
     * @param unRoom the unRoom to set
     */
    public void setUnRoom(int[][][][] unRoom) {
        this.unRoom = unRoom;
    }

    /**
     * @return the idRoom
     */
    public int getIdRoom() {
        return idRoom;
    }

    /**
     * @param idRoom the idRoom to set
     */
    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    /**
     * @return the travelroom
     */
    public ArrayList <Integer> getTravelroom() {
        return travelroom;
    }

    /**
     * @param travelroom the travelroom to set
     */
    public void setTravelroom(ArrayList <Integer> travelroom) {
        this.travelroom = travelroom;
    }
}
