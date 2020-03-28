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
public class Activities implements Serializable {

    private int id;
    private String name;
    private Date targetDate;
    private Milestones milestones;
    private int createdby;
    private Timestamp createdon;
    private Set tasks;

    public Activities() {
    }

    public Activities(int id, String name, Date targetDate, Milestones milestones, int createdby, Timestamp createdon) {
        this.id = id;
        this.name = name;
        this.targetDate = targetDate;
        this.milestones = milestones;
        this.createdby = createdby;
        this.createdon = createdon;
        this.tasks = new HashSet(0);
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

    public Milestones getMilestones() {
        return milestones;
    }

    public void setMilestones(Milestones milestones) {
        this.milestones = milestones;
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

    public Set getTasks() {
        return tasks;
    }

    public void setTasks(Set tasks) {
        this.tasks = tasks;
    }
}
