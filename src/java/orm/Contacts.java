/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dhirajj
 */
public class Contacts implements Serializable {

    private int id;
    private String name;
    private String address;
    private String phone;
    private String mobile;
    private String email;
    private String comments;
    private ContactTypes contacttypes;
    private int createdby;
    private Timestamp createdon;
    private Set inwordse;

    public Contacts() {
    }

    public Contacts(int id, String name, String address, String phone, String mobile, String email, String comments, ContactTypes contacttypes, int createdby, Timestamp createdon) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.mobile = mobile;
        this.email = email;
        this.comments = comments;
        this.contacttypes = contacttypes;
        this.createdby = createdby;
        this.createdon = createdon;
        
        this.inwordse = new HashSet(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ContactTypes getContacttypes() {
        return contacttypes;
    }

    public void setContacttypes(ContactTypes contacttypes) {
        this.contacttypes = contacttypes;
    }

    public int getCreatedby() {
        return createdby;
    }

    public void setCreatedby(int createdby) {
        this.createdby = createdby;
    }

    public Timestamp getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Timestamp createdon) {
        this.createdon = createdon;
    }

    public Set getInwordse() {
        return inwordse;
    }

    public void setInwordse(Set inwordse) {
        this.inwordse = inwordse;
    }

    
}
