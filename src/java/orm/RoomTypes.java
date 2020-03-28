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
public class RoomTypes implements Serializable{
    
    private int id;
     private String name;
     private String description;
     private int createdby;
     private Timestamp createdon;
     
     private Set rooms;

    public RoomTypes() {
    }

    public RoomTypes(int id, String name, String description, int createdby, Timestamp createdon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdby = createdby;
        this.createdon = createdon;
        this.rooms = new HashSet(0);
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

    public Set getRooms() {
        return rooms;
    }

    public void setRooms(Set rooms) {
        this.rooms = rooms;
    }
        
}
