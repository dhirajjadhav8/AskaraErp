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
public class ZBranches implements Serializable {

    private int id;
    private int branchesid;
    private String name;
    private String address;
    private String phone;
    private int organizationid;
    private char opcode;
    private int modifiedby;
    private Timestamp modifiedon;

    public ZBranches() {
    }

    public ZBranches(int id, int branchesid, String name, String address, String phone, int organizationid, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.branchesid = branchesid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.organizationid = organizationid;
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

    public int getBranchesid() {
        return branchesid;
    }

    public void setBranchesid(int branchesid) {
        this.branchesid = branchesid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(int organizationid) {
        this.organizationid = organizationid;
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
