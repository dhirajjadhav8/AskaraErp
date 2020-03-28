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
public class ZEmployeeDesignations implements Serializable{
    
     private int id;
     private int employeedesignationid;
     private Integer employeeid;
     private Integer designationid;
     private Date startDate;
     private Date endDate;
     private String endReason;
     private char opcode;
     private Integer modifiedby;
     private Timestamp modifiedon;

    public ZEmployeeDesignations() {
    }

    public ZEmployeeDesignations(int id, int employeedesignationid, Integer employeeid, Integer designationid, Date startDate, Date endDate, String endReason, char opcode, Integer modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.employeedesignationid = employeedesignationid;
        this.employeeid = employeeid;
        this.designationid = designationid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.endReason = endReason;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
    }

    public Integer getDesignationid() {
        return designationid;
    }

    public void setDesignationid(Integer designationid) {
        this.designationid = designationid;
    }

    public int getEmployeedesignationid() {
        return employeedesignationid;
    }

    public void setEmployeedesignationid(int employeedesignationid) {
        this.employeedesignationid = employeedesignationid;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
     
}
