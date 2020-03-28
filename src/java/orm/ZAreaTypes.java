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
public class ZAreaTypes implements Serializable{
    
    private int id;
     private int areatypesid;
     private String name;
     private char opcode;
     private int modifiedby;
     private Timestamp modifiedon;

    public ZAreaTypes() {
    }

    public ZAreaTypes(int id, int areatypesid, String name, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.areatypesid = areatypesid;
        this.name = name;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
    }

    public int getAreatypesid() {
        return areatypesid;
    }

    public void setAreatypesid(int areatypesid) {
        this.areatypesid = areatypesid;
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
