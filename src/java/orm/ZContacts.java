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
public class ZContacts implements Serializable{
    
    private int id;
     private int contactid;
     private String name;
     private String address;
     private String phone;
     private String mobile;
     private String email;
     private String comments;
     private int contacttypeid;
     private char opcode;
     private int modifiedby;
     private Timestamp modifiedon;

    public ZContacts() {
    }

    public ZContacts(int id, int contactid, String name, String address, String phone, String mobile, String email, String comments, int contacttypeid, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.contactid = contactid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.mobile = mobile;
        this.email = email;
        this.comments = comments;
        this.contacttypeid = contacttypeid;
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

    public int getContactid() {
        return contactid;
    }

    public void setContactid(int contactid) {
        this.contactid = contactid;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getContacttypeid() {
        return contacttypeid;
    }

    public void setContacttypeid(int contacttypeid) {
        this.contacttypeid = contacttypeid;
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
