/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dhirajj
 */
public class Departments implements java.io.Serializable{
    
    private int id;
    private String name;
     private int createdby;
     private Timestamp createdon;
    
    private Set employeeDetails;
    private Set departmentIncharge;

    public Departments() {
    }

    public Departments(int id, String name ,int createdby,Timestamp createdon) {
        this.id = id;
        this.name = name;
        this.createdby = createdby;
        this.createdon = createdon;
        this.employeeDetails = new HashSet(0);
        this.departmentIncharge = new HashSet(0);
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
    
    public Set getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(Set employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

    public Set getDepartmentIncharge() {
        return departmentIncharge;
    }

    public void setDepartmentIncharge(Set departmentIncharge) {
        this.departmentIncharge = departmentIncharge;
    }
        
}
