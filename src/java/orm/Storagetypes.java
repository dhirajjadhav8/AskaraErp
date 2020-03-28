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
public class Storagetypes implements Serializable {

    private int id;
    private String name;
    private String code;
    private int createdby;
    private Timestamp createdon;
    
    private Set<Storage> storage;

    public Storagetypes() {
    }

    public Storagetypes(int id, String name, String code, int createdby, Timestamp createdon) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.createdby = createdby;
        this.createdon = createdon;
        
        this.storage = new HashSet<Storage>(0);
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

    public Set<Storage> getStorage() {
        return storage;
    }

    public void setStorage(Set<Storage> storage) {
        this.storage = storage;
    }
    
}
