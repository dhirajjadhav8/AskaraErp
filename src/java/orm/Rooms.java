/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dhirajj
 */
public class Rooms implements Serializable {

    private int id;
    private String code;
    private String name;
    private RoomTypes roomtypes;
    private int createdby;
    private Timestamp createdon;
    private Set area;
    private Set roomFacilities;

    public Rooms() {
    }

    public Rooms(int id, String code, String name, RoomTypes roomtypes, int createdby, Timestamp createdon) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.roomtypes = roomtypes;
        this.createdby = createdby;
        this.createdon = createdon;
        this.area = new HashSet(0);
        this.roomFacilities = new HashSet(0);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCreatedby() {
        return createdby;
    }

    public void setCreatedby(int createdby) {
        this.createdby = createdby;
    }

    public Timestamp getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Timestamp createdon) {
        this.createdon = createdon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomTypes getRoomtypes() {
        return roomtypes;
    }

    public void setRoomtypes(RoomTypes roomtypes) {
        this.roomtypes = roomtypes;
    }

    public Set getArea() {
        return area;
    }

    public void setArea(Set area) {
        this.area = area;
    }

    public Set getRoomFacilities() {
        return roomFacilities;
    }

    public void setRoomFacilities(Set roomFacilities) {
        this.roomFacilities = roomFacilities;
    }
}
