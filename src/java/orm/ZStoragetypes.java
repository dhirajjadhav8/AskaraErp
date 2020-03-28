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
public class ZStoragetypes implements Serializable{
    
     private int id;
     private int stroragetypeid;
     private String name;
     private String code;
     private char opcode;
     private int modifiedby;
     private Timestamp modifiedon;

    public ZStoragetypes() {
    }

    public ZStoragetypes(int id, int stroragetypeid, String name, String code, char opcode, Integer modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.stroragetypeid = stroragetypeid;
        this.name = name;
        this.code = code;
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

    public Integer getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(Integer modifiedby) {
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

    public int getStroragetypeid() {
        return stroragetypeid;
    }

    public void setStroragetypeid(int stroragetypeid) {
        this.stroragetypeid = stroragetypeid;
    }    
    
}
