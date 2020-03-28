/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author dhirajj
 */
public class Inwordse implements Serializable{
    
    private int id;
     private String inNo;
     private Date inDate;
     private char type;
     private char deliverymode;
     private String comments;
     private Contacts ifrom;
     private Employees forwardedTo;
     private boolean isreceived;
     private int createdby;
     private Timestamp createdon;

    public Inwordse() {
    }

    public Inwordse(int id, String inNo, Date inDate, char type, char deliverymode, String comments, Contacts ifrom, Employees forwardedTo, boolean isreceived, int createdby, Timestamp createdon) {
        this.id = id;
        this.inNo = inNo;
        this.inDate = inDate;
        this.type = type;
        this.deliverymode = deliverymode;
        this.comments = comments;
        this.ifrom = ifrom;
        this.forwardedTo = forwardedTo;
        this.isreceived = isreceived;
        this.createdby = createdby;
        this.createdon = createdon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInNo() {
        return inNo;
    }

    public void setInNo(String inNo) {
        this.inNo = inNo;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public char getDeliverymode() {
        return deliverymode;
    }

    public void setDeliverymode(char deliverymode) {
        this.deliverymode = deliverymode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Contacts getIfrom() {
        return ifrom;
    }

    public void setIfrom(Contacts ifrom) {
        this.ifrom = ifrom;
    }

    public Employees getForwardedTo() {
        return forwardedTo;
    }

    public void setForwardedTo(Employees forwardedTo) {
        this.forwardedTo = forwardedTo;
    }

    public boolean isIsreceived() {
        return isreceived;
    }

    public void setIsreceived(boolean isreceived) {
        this.isreceived = isreceived;
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
    
}
