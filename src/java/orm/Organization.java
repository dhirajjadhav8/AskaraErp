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
public class Organization implements Serializable {

    private int id;
    private String name;
    private int createdby;
    private Timestamp createdon;
    private Set branches;

    public Organization() {
    }

    public Organization(int id, String name, int createdby, Timestamp createdon) {
        this.id = id;
        this.name = name;
        this.createdby = createdby;
        this.createdon = createdon;
        this.branches = new HashSet(0);
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

    public Set getBranches() {
        return branches;
    }

    public void setBranches(Set branches) {
        this.branches = branches;
    }
}
