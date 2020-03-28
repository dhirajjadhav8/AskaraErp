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
public class ZItemDetails implements Serializable {

    private int id;
    private int itemdetailsid;
    private int itemid;
    private String manufserialno;
    private Date purchaseDate;
    private Date warrentyexpDate;
    private String code;
    private char opcode;
    private int modifiedby;
    private Timestamp modifiedon;
    private String specifications;

    public ZItemDetails() {
    }

    public ZItemDetails(int id, int itemdetailsid, int itemid, String manufserialno, Date purchaseDate, Date warrentyexpDate, String code, char opcode, int modifiedby, Timestamp modifiedon, String specifications) {
        this.id = id;
        this.itemdetailsid = itemdetailsid;
        this.itemid = itemid;
        this.manufserialno = manufserialno;
        this.purchaseDate = purchaseDate;
        this.warrentyexpDate = warrentyexpDate;
        this.code = code;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
        this.specifications = specifications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemdetailsid() {
        return itemdetailsid;
    }

    public void setItemdetailsid(int itemdetailsid) {
        this.itemdetailsid = itemdetailsid;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getManufserialno() {
        return manufserialno;
    }

    public void setManufserialno(String manufserialno) {
        this.manufserialno = manufserialno;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getWarrentyexpDate() {
        return warrentyexpDate;
    }

    public void setWarrentyexpDate(Date warrentyexpDate) {
        this.warrentyexpDate = warrentyexpDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
}
