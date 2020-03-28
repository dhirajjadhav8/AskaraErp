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
public class ZArea implements Serializable{
    
    private int id;
     private int areaid;
     private String code;
     private int roomsid;
     private int flooresid;
     private int buildingsid;
     private int areatypesid;
     private char opcode;
     private int modifiedby;
     private Timestamp modifiedon;

    public ZArea() {
    }

    public ZArea(int id, int areaid, String code, int roomsid, int flooresid, int buildingsid, int areatypesid, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.areaid = areaid;
        this.code = code;
        this.roomsid = roomsid;
        this.flooresid = flooresid;
        this.buildingsid = buildingsid;
        this.areatypesid = areatypesid;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
    }

    public int getAreaid() {
        return areaid;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public int getAreatypesid() {
        return areatypesid;
    }

    public void setAreatypesid(int areatypesid) {
        this.areatypesid = areatypesid;
    }

    public int getBuildingsid() {
        return buildingsid;
    }

    public void setBuildingsid(int buildingsid) {
        this.buildingsid = buildingsid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFlooresid() {
        return flooresid;
    }

    public void setFlooresid(int flooresid) {
        this.flooresid = flooresid;
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
    
     
}
