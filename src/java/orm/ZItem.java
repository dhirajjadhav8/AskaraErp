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
public class ZItem implements Serializable {

    private int id;
    private int itemid;
    private String model;
    private String description;
    private int itypeid;
    private int imakeid;
    private int reorderlevel;
    private char opcode;
    private Integer modifiedby;
    private Timestamp modifiedon;
    private char classification;
    private String photo;

    public ZItem() {
    }

    public ZItem(int id, int itemid, String model, String description, int itypeid, int imakeid, int reorderlevel, char opcode, Integer modifiedby, Timestamp modifiedon, Character classification, String photo) {
        this.id = id;
        this.itemid = itemid;
        this.model = model;
        this.description = description;
        this.itypeid = itypeid;
        this.imakeid = imakeid;
        this.reorderlevel = reorderlevel;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
        this.classification = classification;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
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

    public int getItypeid() {
        return itypeid;
    }

    public void setItypeid(int itypeid) {
        this.itypeid = itypeid;
    }

    public int getImakeid() {
        return imakeid;
    }

    public void setImakeid(int imakeid) {
        this.imakeid = imakeid;
    }

    public int getReorderlevel() {
        return reorderlevel;
    }

    public void setReorderlevel(int reorderlevel) {
        this.reorderlevel = reorderlevel;
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

    public Character getClassification() {
        return classification;
    }

    public void setClassification(Character classification) {
        this.classification = classification;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
