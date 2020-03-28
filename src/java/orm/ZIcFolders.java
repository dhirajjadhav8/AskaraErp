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
public class ZIcFolders implements Serializable {

    private int id;
    private int cfolderid;
    private String name;
    private int employeeid;
    private char type;
    private char opcode;
    private int modifiedby;
    private Timestamp modifiedon;

    public ZIcFolders() {
    }

    public ZIcFolders(int id, int cfolderid, String name, int employeeid, char type, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.cfolderid = cfolderid;
        this.name = name;
        this.employeeid = employeeid;
        this.type = type;
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

    public int getCfolderid() {
        return cfolderid;
    }

    public void setCfolderid(int cfolderid) {
        this.cfolderid = cfolderid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
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
