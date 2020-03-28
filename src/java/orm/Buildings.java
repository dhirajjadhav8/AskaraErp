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
public class Buildings implements Serializable{
    private int id;
     private String code;
     private String name;
     private String description;
     private int createdby;
     private Timestamp createdon;
     
     private Set area;

    public Buildings() {
    }

    public Buildings(int id, String code, String name, String description, int createdby, Timestamp createdon) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.createdby = createdby;
        this.createdon = createdon;
        this.area = new HashSet(0);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
