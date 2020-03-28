/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dhirajj
 */
public class ItemDetails implements Serializable {

    private int id;
    private Item item;
    private String manufserialno;
    private Date purchaseDate;
    private Date warrentyexpDate;
    private String code;
    private int createdby;
    private Timestamp createdon;
    private String specifications;
    
    private Set roomFacilities;

    public ItemDetails() {
    }

    public ItemDetails(int id, Item item, String manufserialno, Date purchaseDate, Date warrentyexpDate, String code, int createdby, Timestamp createdon, String specifications) {
        this.id = id;
        this.item = item;
        this.manufserialno = manufserialno;
        this.purchaseDate = purchaseDate;
        this.warrentyexpDate = warrentyexpDate;
        this.code = code;
        this.createdby = createdby;
        this.createdon = createdon;
        this.specifications = specifications;
        
        this.roomFacilities= new HashSet(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Set getRoomFacilities() {
        return roomFacilities;
    }

    public void setRoomFacilities(Set roomFacilities) {
        this.roomFacilities = roomFacilities;
    }
    
}
