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
public class ZIcategory implements Serializable {

    private int id;
    private int icategoryid;
    private String name;
    private String description;
    private char opcode;
    private Integer modifiedby;
    private Timestamp modifiedon;

    public ZIcategory() {
    }

    public ZIcategory(int id, int icategoryid, String name, String description, char opcode, Integer modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.icategoryid = icategoryid;
        this.name = name;
        this.description = description;
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

    public int getIcategoryid() {
        return icategoryid;
    }

    public void setIcategoryid(int icategoryid) {
        this.icategoryid = icategoryid;
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
