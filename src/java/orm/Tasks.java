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
public class Tasks implements Serializable {

    private int id;
    private String name;
    private Date targetDate;
    private Date actualDate;
    private String lateReason;
    private Activities activities;
    private int createdby;
    private Timestamp createdon;
    private Set subTasks;

    public Tasks() {
    }

    public Tasks(int id, String name, Date targetDate, Date actualDate, String lateReason, Activities activities, int createdby, Timestamp createdon) {
        this.id = id;
        this.name = name;
        this.targetDate = targetDate;
        this.actualDate = actualDate;
        this.lateReason = lateReason;
        this.activities = activities;
        this.createdby = createdby;
        this.createdon = createdon;
        this.subTasks = new HashSet(0);
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

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public String getLateReason() {
        return lateReason;
    }

    public void setLateReason(String lateReason) {
        this.lateReason = lateReason;
    }

    public Activities getActivities() {
        return activities;
    }

    public void setActivities(Activities activities) {
        this.activities = activities;
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

    public Set getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(Set subTasks) {
        this.subTasks = subTasks;
    }

   
}
