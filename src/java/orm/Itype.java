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
public class Itype implements Serializable {

    private int id;
    private String name;
    private String description;
    private Icategory icategory;
    private int createdby;
    private Timestamp createdon;
    private Set item;

    public Itype() {
    }

    public Itype(int id, String name, String description, Icategory icategory, int createdby, Timestamp createdon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icategory = icategory;
        this.createdby = createdby;
        this.createdon = createdon;
        this.item = new HashSet(0);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Icategory getIcategory() {
        return icategory;
    }

    public void setIcategory(Icategory icategory) {
        this.icategory = icategory;
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

    public Set getItem() {
        return item;
    }

    public void setItem(Set item) {
        this.item = item;
    }
}
