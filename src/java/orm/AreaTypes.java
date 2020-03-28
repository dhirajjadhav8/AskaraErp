/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dhirajj
 */
public class AreaTypes implements Serializable{
    
     private int id;
     private String name;
     private int createdby;
     private Timestamp createdon;
     
     private Set area;

    public AreaTypes() {
    }

    public AreaTypes(int id, String name, int createdby, Timestamp createdon) {
        this.id = id;
        this.name = name;
        this.createdby = createdby;
        this.createdon = createdon;
        
        this.area = new HashSet(0);
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

    public Set getArea() {
        return area;
    }

    public void setArea(Set area) {
        this.area = area;
    }
    
}
