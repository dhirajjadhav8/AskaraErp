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
public class ZProjectIncharge implements Serializable{
    
    private int id;
     private int projectinchargeid;
     private Integer employeeid;
     private Integer projectid;
     private Date startDate;
     private Date endDate;
     private String endReason;
     private char opcode;
     private Integer modifiedby;
     private Timestamp modifiedon;

    public ZProjectIncharge() {
    }

    public ZProjectIncharge(int id, int projectinchargeid, Integer employeeid, Integer projectid, Date startDate, Date endDate, String endReason, char opcode, Integer modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.projectinchargeid = projectinchargeid;
        this.employeeid = employeeid;
        this.projectid = projectid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.endReason = endReason;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public char getOpcode() {
        return opcode;
    }

    public void setOpcode(char opcode) {
        this.opcode = opcode;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public int getProjectinchargeid() {
        return projectinchargeid;
    }

    public void setProjectinchargeid(int projectinchargeid) {
        this.projectinchargeid = projectinchargeid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }     
    
}
