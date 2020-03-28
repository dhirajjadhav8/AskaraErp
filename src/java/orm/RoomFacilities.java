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
public class RoomFacilities implements Serializable {

    private int id;
    private Rooms rooms;
    private ItemDetails itemdetails;
    private Date startDate;
    private Date endDate;
    private String endReason;
    private int createdby;
    private Timestamp createdon;

    public RoomFacilities() {
    }

    public RoomFacilities(int id, Rooms rooms, ItemDetails itemdetails, Date startDate, Date endDate, String endReason, int createdby, Timestamp createdon) {
        this.id = id;
        this.rooms = rooms;
        this.itemdetails = itemdetails;
        this.startDate = startDate;
        this.endDate = endDate;
        this.endReason = endReason;
        this.createdby = createdby;
        this.createdon = createdon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rooms getRooms() {
        return rooms;
    }

    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }

    public ItemDetails getItemdetails() {
        return itemdetails;
    }

    public void setItemdetails(ItemDetails itemdetails) {
        this.itemdetails = itemdetails;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEndReason() {
        return endReason;
    }

    public void setEndReason(String endReason) {
        this.endReason = endReason;
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
