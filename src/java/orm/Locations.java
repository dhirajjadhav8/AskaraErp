package orm;
// Generated 5 Oct, 2012 10:24:47 AM by Hibernate Tools 3.2.1.GA

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;




/**
 * Locations generated by hbm2java
 */
public class Locations  implements java.io.Serializable {


     private int id;
     private String name;
     private int createdby;
     private Timestamp createdon;
     private Set employeeDetails;

    public Locations() {
    }

    public Locations(int id, String name) {
       this.id = id;
       this.name = name;
       this.employeeDetails = new HashSet(0);
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Set getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(Set employeeDetails) {
        this.employeeDetails = employeeDetails;
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

