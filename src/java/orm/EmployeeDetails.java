/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author dhirajj
 */
public class EmployeeDetails implements java.io.Serializable{
    
    private int id;
    private Projects projects;
    private Locations locations;
    private Designations designations;
    private Departments departments;
    private Employees employees;
    private Date startDate;
    private Date endDate;
    private String endReason;
     private int createdby;
     private Timestamp createdon;

    public EmployeeDetails() {
    }

    public EmployeeDetails(int id, Projects projects, Locations locations, Designations designations, Departments departments, Employees employees, Date startDate, Date endDate, String endReason ,int createdby,Timestamp createdon) {
        this.id = id;
        this.projects = projects;
        this.locations = locations;
        this.designations = designations;
        this.departments = departments;
        this.employees = employees;
        this.startDate = startDate;
        this.endDate = endDate;
        this.endReason = endReason;
        this.createdby = createdby;
        this.createdon = createdon;
    }

   
    public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    public Designations getDesignations() {
        return designations;
    }

    public void setDesignations(Designations designations) {
        this.designations = designations;
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

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }

    public Projects getProjects() {
        return projects;
    }

    public void setProjects(Projects projects) {
        this.projects = projects;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
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
