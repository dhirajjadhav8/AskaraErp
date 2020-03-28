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
public class Storage implements Serializable {

    private int id;
    private String code;
    private Storagetypes storagetypes;
    private boolean islocable;
    private String kyeNo;
    private ItemDetails itemDetails;
    private Area area;
    private int createdby;
    private Timestamp createdon;

    public Storage() {
    }

    public Storage(int id, String code, Storagetypes storagetypes, boolean islocable, String kyeNo, ItemDetails itemDetails, Area area, int createdby, Timestamp createdon) {
        this.id = id;
        this.code = code;
        this.storagetypes = storagetypes;
        this.islocable = islocable;
        this.kyeNo = kyeNo;
        this.itemDetails = itemDetails;
        this.area = area;
        this.createdby = createdby;
        this.createdon = createdon;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIslocable() {
        return islocable;
    }

    public void setIslocable(boolean islocable) {
        this.islocable = islocable;
    }

    public String getKyeNo() {
        return kyeNo;
    }

    public void setKyeNo(String kyeNo) {
        this.kyeNo = kyeNo;
    }

    public Storagetypes getStoragetypes() {
        return storagetypes;
    }

    public void setStoragetypes(Storagetypes storagetypes) {
        this.storagetypes = storagetypes;
    }

    public ItemDetails getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemDetails itemDetails) {
        this.itemDetails = itemDetails;
    }
}
