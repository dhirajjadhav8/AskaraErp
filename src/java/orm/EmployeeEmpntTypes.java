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
public class EmployeeEmpntTypes implements Serializable{
    
    private int id;
     private Employees employees;
     private EmpntTypes empnttypes;
     private Date startDate;
     private Date endDate;
     private String endReason;
     private int createdby;
     private Timestamp createdon;

    public EmployeeEmpntTypes() {
    }

    public EmployeeEmpntTypes(int id, Employees employees, EmpntTypes empnttypes, Date startDate, Date endDate, String endReason, int createdby, Timestamp createdon) {
        this.id = id;
        this.employees = employees;
        this.empnttypes = empnttypes;
        this.startDate = startDate;
        this.endDate = endDate;
        this.endReason = endReason;
        this.createdby = createdby;
        this.createdon = createdon;
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

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public EmpntTypes getEmpnttypes() {
        return empnttypes;
    }

    public void setEmpnttypes(EmpntTypes empnttypes) {
        this.empnttypes = empnttypes;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
}
