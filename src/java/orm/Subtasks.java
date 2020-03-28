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
public class Subtasks implements Serializable {

    private int id;
    private String name;
    private Date targetDate;
    private Date actualDate;
    private String lateReason;
    private Tasks tasks;
    private int createdby;
    private Timestamp createdon;

    public Subtasks() {
    }

    public Subtasks(int id, String name, Date targetDate, Date actualDate, String lateReason, Tasks tasks, int createdby, Timestamp createdon) {
        this.id = id;
        this.name = name;
        this.targetDate = targetDate;
        this.actualDate = actualDate;
        this.lateReason = lateReason;
        this.tasks = tasks;
        this.createdby = createdby;
        this.createdon = createdon;
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

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
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
