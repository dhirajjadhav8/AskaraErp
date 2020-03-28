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
public class ZImake implements Serializable {

    private int id;
    private int imakeid;
    private String name;
    private String description;
    private Integer icategoryid;
    private char opcode;
    private Integer modifiedby;
    private Timestamp modifiedon;

    public ZImake() {
    }

    public ZImake(int id, int imakeid, String name, String description, Integer icategoryid, char opcode, Integer modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.imakeid = imakeid;
        this.name = name;
        this.description = description;
        this.icategoryid = icategoryid;
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

    public int getImakeid() {
        return imakeid;
    }

    public void setImakeid(int imakeid) {
        this.imakeid = imakeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIcategoryid() {
        return icategoryid;
    }

    public void setIcategoryid(Integer icategoryid) {
        this.icategoryid = icategoryid;
    }

    public char getOpcode() {
        return opcode;
    }

    public void setOpcode(char opcode) {
        this.opcode = opcode;
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
}
