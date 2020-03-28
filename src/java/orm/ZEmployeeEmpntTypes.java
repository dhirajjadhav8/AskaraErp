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
public class ZEmployeeEmpntTypes implements Serializable{
    
    private int id;
     private int employeeempnttypesid;
     private Integer employeeid;
     private Integer empnttypesid;
     private Date startDate;
     private Date endDate;
     private String endReason;
     private char opcode;
     private Integer modifiedby;
     private Timestamp modifiedon;

    public ZEmployeeEmpntTypes() {
    }

    public ZEmployeeEmpntTypes(int id, int employeeempnttypesid, Integer employeeid, Integer empnttypesid, Date startDate, Date endDate, String endReason, char opcode, Integer modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.employeeempnttypesid = employeeempnttypesid;
        this.employeeid = employeeid;
        this.empnttypesid = empnttypesid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.endReason = endReason;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
    }

    public int getEmployeeempnttypesid() {
        return employeeempnttypesid;
    }

    public void setEmployeeempnttypesid(int employeeempnttypesid) {
        this.employeeempnttypesid = employeeempnttypesid;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public Integer getEmpnttypesid() {
        return empnttypesid;
    }

    public void setEmpnttypesid(Integer empnttypesid) {
        this.empnttypesid = empnttypesid;
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
