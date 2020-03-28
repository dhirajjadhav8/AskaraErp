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
public class ZRoomTypes implements Serializable{
    
     private int id;
     private int roomtypesid;
     private String name;
     private String description;
     private char opcode;
     private int modifiedby;
     private Timestamp modifiedon;

    public ZRoomTypes() {
    }

    public ZRoomTypes(int id, int roomtypesid, String name, String description, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.roomtypesid = roomtypesid;
        this.name = name;
        this.description = description;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getRoomtypesid() {
        return roomtypesid;
    }

    public void setRoomtypesid(int roomtypesid) {
        this.roomtypesid = roomtypesid;
    }
    
}
