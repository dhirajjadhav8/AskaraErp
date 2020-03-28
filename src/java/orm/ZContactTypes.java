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
public class ZContactTypes implements Serializable {

    private int id;
    private int contacttypesid;
    private String name;
    private char opcode;
    private int modifiedby;
    private Timestamp modifiedon;

    public ZContactTypes() {
    }

    public ZContactTypes(int id, int contacttypesid, String name, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.contacttypesid = contacttypesid;
        this.name = name;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContacttypesid() {
        return contacttypesid;
    }

    public void setContacttypesid(int contacttypesid) {
        this.contacttypesid = contacttypesid;
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
}
