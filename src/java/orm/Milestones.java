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
public class Milestones implements Serializable {

    private int id;
    private String name;
    private Date targetDate;
    private Projects projects;
    private int createdby;
    private Timestamp createdon;
    private Set activities;

    public Milestones() {
    }

    public Milestones(int id, String name, Date targetDate, Projects projects, int createdby, Timestamp createdon) {
        this.id = id;
        this.name = name;
        this.targetDate = targetDate;
        this.projects = projects;
        this.createdby = createdby;
        this.createdon = createdon;
        this.activities = new HashSet(0);
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

    public Projects getProjects() {
        return projects;
    }

    public void setProjects(Projects projects) {
        this.projects = projects;
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

    public Set getActivities() {
        return activities;
    }

    public void setActivities(Set activities) {
        this.activities = activities;
    }

    
}
