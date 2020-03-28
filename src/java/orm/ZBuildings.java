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
public class ZBuildings implements Serializable{
    
    private int id;
     private int buildingsid;
     private String code;
     private String name;
     private String description;
     private char opcode;
     private int modifiedby;
     private Timestamp modifiedon;

    public ZBuildings() {
    }

    public ZBuildings(int id, int buildingsid, String code, String name, String description, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.buildingsid = buildingsid;
        this.code = code;
        this.name = name;
        this.description = description;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
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
     
}
