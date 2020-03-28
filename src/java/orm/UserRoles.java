/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author dhirajj
 */
public class UserRoles implements java.io.Serializable {

    private int id;
    private Users users;
    private Roles roles;
    private Date startDate;
    private Date endDate;
    private String endReason;
    private int createdby;
    private Timestamp createdon;

    public UserRoles() {
    }

    public UserRoles(int id, Users users, Roles roles, Date startDate, Date endDate, String endReason, int createdby, Timestamp createdon) {
        this.id = id;
        this.users = users;
        this.roles = roles;
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

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
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
