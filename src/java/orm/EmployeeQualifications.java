package orm;
// Generated 5 Oct, 2012 10:24:47 AM by Hibernate Tools 3.2.1.GA

import java.sql.Timestamp;
import orm.Employees;




/**
 * EmployeeQualifications generated by hbm2java
 */
public class EmployeeQualifications  implements java.io.Serializable {


     private int id;
     private Qualifications qualifications;
     private Employees employees;
     private String filename;
     private int createdby;
     private Timestamp createdon;

    public EmployeeQualifications() {
    }

    public EmployeeQualifications(int id, Qualifications qualifications, Employees employees , String filename , int createdby , Timestamp createdon) {
       this.id = id;
       this.qualifications = qualifications;
       this.employees = employees;
       this.filename = filename;
       this.createdby = createdby;
       this.createdon = createdon;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public Qualifications getQualifications() {
        return qualifications;
    }

    public void setQualifications(Qualifications qualifications) {
        this.qualifications = qualifications;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

