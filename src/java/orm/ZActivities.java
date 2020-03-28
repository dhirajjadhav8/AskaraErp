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
public class ZActivities implements Serializable {

    private int id;
    private int activitiesid;
    private String name;
    private Date targetDate;
    private int milestoneid;
    private char opcode;
    private int modifiedby;
    private Timestamp modifiedon;

    public ZActivities() {
    }

    public ZActivities(int id, int activitiesid, String name, Date targetDate, int milestoneid, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.activitiesid = activitiesid;
        this.name = name;
        this.targetDate = targetDate;
        this.milestoneid = milestoneid;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivitiesid() {
        return activitiesid;
    }

    public void setActivitiesid(int activitiesid) {
        this.activitiesid = activitiesid;
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

    public int getMilestoneid() {
        return milestoneid;
    }

    public void setMilestoneid(int milestoneid) {
        this.milestoneid = milestoneid;
    }

    public char getOpcode() {
        return opcode;
    }

    public void setOpcode(char opcode) {
        this.opcode = opcode;
    }

    public int getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(int modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Timestamp getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(Timestamp modifiedon) {
        this.modifiedon = modifiedon;
    }
}
