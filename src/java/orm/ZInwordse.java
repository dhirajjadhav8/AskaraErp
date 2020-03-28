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
public class ZInwordse implements Serializable {

    private int id;
    private int inwordseid;
    private String inNo;
    private Date inDate;
    private Character type;
    private Character deliverymode;
    private String comments;
    private int ifrom;
    private int forwardedTo;
    private Boolean isreceived;
    private char opcode;
    private int modifiedby;
    private Timestamp modifiedon;

    public ZInwordse() {
    }

    public ZInwordse(int id, int inwordseid, String inNo, Date inDate, Character type, Character deliverymode, String comments, int ifrom, int forwardedTo, Boolean isreceived, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.inwordseid = inwordseid;
        this.inNo = inNo;
        this.inDate = inDate;
        this.type = type;
        this.deliverymode = deliverymode;
        this.comments = comments;
        this.ifrom = ifrom;
        this.forwardedTo = forwardedTo;
        this.isreceived = isreceived;
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

    public int getInwordseid() {
        return inwordseid;
    }

    public void setInwordseid(int inwordseid) {
        this.inwordseid = inwordseid;
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

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    public Character getDeliverymode() {
        return deliverymode;
    }

    public void setDeliverymode(Character deliverymode) {
        this.deliverymode = deliverymode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getIfrom() {
        return ifrom;
    }

    public void setIfrom(int ifrom) {
        this.ifrom = ifrom;
    }

    public int getForwardedTo() {
        return forwardedTo;
    }

    public void setForwardedTo(int forwardedTo) {
        this.forwardedTo = forwardedTo;
    }

    public Boolean getIsreceived() {
        return isreceived;
    }

    public void setIsreceived(Boolean isreceived) {
        this.isreceived = isreceived;
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
