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
public class ZEmpntTypes implements Serializable{
    
     private int id;
     private int empnttypesid;
     private String name;
     private String description;
     private char opcode;
     private Integer modifiedby;
     private Timestamp modifiedon;

    public ZEmpntTypes() {
    }

    public ZEmpntTypes(int id, int empnttypesid, String name, String description, char opcode, Integer modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.empnttypesid = empnttypesid;
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

    public int getEmpnttypesid() {
        return empnttypesid;
    }

    public void setEmpnttypesid(int empnttypesid) {
        this.empnttypesid = empnttypesid;
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
         
}
