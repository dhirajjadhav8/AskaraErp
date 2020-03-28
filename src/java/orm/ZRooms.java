/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author dhirajj
 */
public class ZRooms implements Serializable{
    
    private int id;
     private int roomsid;
     private String code;
     private String name;
     private Integer roomtypeid;
     private char opcode;
     private int modifiedby;
     private Timestamp modifiedon;

    public ZRooms() {
    }

    public ZRooms(int id, int roomsid, String code, String name, Integer roomtypeid, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.roomsid = roomsid;
        this.code = code;
        this.name = name;
        this.roomtypeid = roomtypeid;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(int modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Timestamp getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(Timestamp modifiedon) {
        this.modifiedon = modifiedon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getOpcode() {
        return opcode;
    }

    public void setOpcode(char opcode) {
        this.opcode = opcode;
    }

    public int getRoomsid() {
        return roomsid;
    }

    public void setRoomsid(int roomsid) {
        this.roomsid = roomsid;
    }

    public Integer getRoomtypeid() {
        return roomtypeid;
    }

    public void setRoomtypeid(Integer roomtypeid) {
        this.roomtypeid = roomtypeid;
    }
}
