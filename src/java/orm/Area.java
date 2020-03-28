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
public class Area implements Serializable{
    
    private int id;
     private String code;
     private Rooms rooms;
     private Floores floores;
     private Buildings buildings;
     private AreaTypes areatypes;
     private int createdby;
     private Timestamp createdon;
     
     private Set<Storage> storage;

    public Area() {
    }

    public Area(int id, String code, Rooms rooms, Floores floores, Buildings buildings, AreaTypes areatypes, int createdby, Timestamp createdon) {
        this.id = id;
        this.code = code;
        this.rooms = rooms;
        this.floores = floores;
        this.buildings = buildings;
        this.areatypes = areatypes;
        this.createdby = createdby;
        this.createdon = createdon;
        
        this.storage = new HashSet<Storage>();
    }

    public AreaTypes getAreatypes() {
        return areatypes;
    }

    public void setAreatypes(AreaTypes areatypes) {
        this.areatypes = areatypes;
    }

    public Buildings getBuildings() {
        return buildings;
    }

    public void setBuildings(Buildings buildings) {
        this.buildings = buildings;
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

    public Floores getFloores() {
        return floores;
    }

    public void setFloores(Floores floores) {
        this.floores = floores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rooms getRooms() {
        return rooms;
    }

    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }

    public Set<Storage> getStorage() {
        return storage;
    }

    public void setStorage(Set<Storage> storage) {
        this.storage = storage;
    }        
}
