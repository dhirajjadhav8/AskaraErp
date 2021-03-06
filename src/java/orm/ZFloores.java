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
public class ZFloores implements Serializable{
    
    private int id;
     private int flooresid;
     private String code;
     private String name;
     private char opcode;
     private int modifiedby;
     private Timestamp modifiedon;

    public ZFloores() {
    }

    public ZFloores(int id, int flooresid, String code, String name, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.flooresid = flooresid;
        this.code = code;
        this.name = name;
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
