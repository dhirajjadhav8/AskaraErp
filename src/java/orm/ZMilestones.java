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
public class ZMilestones implements Serializable {

    private int id;
    private int milestoneid;
    private String name;
    private Date targetDate;
    private int projectid;
    private char opcode;
    private int modifiedby;
    private Timestamp modifiedon;

    public ZMilestones() {
    }

    public ZMilestones(int id, int milestoneid, String name, Date targetDate, Integer projectid, char opcode, Integer modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.milestoneid = milestoneid;
        this.name = name;
        this.targetDate = targetDate;
        this.projectid = projectid;
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

    public int getMilestoneid() {
        return milestoneid;
    }

    public void setMilestoneid(int milestoneid) {
        this.milestoneid = milestoneid;
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

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
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
}
