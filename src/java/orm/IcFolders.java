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
public class IcFolders implements Serializable {

    private int id;
    private String name;
    private Employees employees;
    private char type;
    private int createdby;
    private Timestamp createdon;
    private Set internalcommunication;
    private Set icTo;

    public IcFolders() {
    }

    public IcFolders(int id, String name, Employees employees, char type, int createdby, Timestamp createdon) {
        this.id = id;
        this.name = name;
        this.employees = employees;
        this.type = type;
        this.createdby = createdby;
        this.createdon = createdon;
        this.internalcommunication = new HashSet(0);
        this.icTo = new HashSet(0);
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

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
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

    public Set getInternalcommunication() {
        return internalcommunication;
    }

    public void setInternalcommunication(Set internalcommunication) {
        this.internalcommunication = internalcommunication;
    }

    public Set getIcTo() {
        return icTo;
    }

    public void setIcTo(Set icTo) {
        this.icTo = icTo;
    }
}
