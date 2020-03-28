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
public class Item implements Serializable {

    private int id;
    private String model;
    private String description;
    private Itype itype;
    private Imake imake;
    private int reorderlevel;
    private int createdby;
    private Timestamp createdon;
    private char classification;
    private String photo;
    private Set itemDetails;

    public Item() {
    }

    public Item(int id, String model, String description, Itype itype, Imake imake, int reorderlevel, int createdby, Timestamp createdon, char classification, String photo, Set itemDetails) {
        this.id = id;
        this.model = model;
        this.description = description;
        this.itype = itype;
        this.imake = imake;
        this.reorderlevel = reorderlevel;
        this.createdby = createdby;
        this.createdon = createdon;
        this.classification = classification;
        this.photo = photo;
        this.itemDetails = itemDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Itype getItype() {
        return itype;
    }

    public void setItype(Itype itype) {
        this.itype = itype;
    }

    public Imake getImake() {
        return imake;
    }

    public void setImake(Imake imake) {
        this.imake = imake;
    }

    public int getReorderlevel() {
        return reorderlevel;
    }

    public void setReorderlevel(int reorderlevel) {
        this.reorderlevel = reorderlevel;
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

    public Set getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(Set itemDetails) {
        this.itemDetails = itemDetails;
    }

    public char getClassification() {
        return classification;
    }

    public void setClassification(char classification) {
        this.classification = classification;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
