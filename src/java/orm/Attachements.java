/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author dhirajj
 */
public class Attachements implements Serializable {

    private int id;
    private InternalCommunication internalcommunication;
    private String filename;
    private int createdby;
    private Timestamp createdon;

    public Attachements() {
    }

    public Attachements(int id, InternalCommunication internalcommunication, String filename, int createdby, Timestamp createdon) {
        this.id = id;
        this.internalcommunication = internalcommunication;
        this.filename = filename;
        this.createdby = createdby;
        this.createdon = createdon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InternalCommunication getInternalcommunication() {
        return internalcommunication;
    }

    public void setInternalcommunication(InternalCommunication internalcommunication) {
        this.internalcommunication = internalcommunication;
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
