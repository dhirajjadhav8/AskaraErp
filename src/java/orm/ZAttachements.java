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
public class ZAttachements implements Serializable {

    private int id;
    private int attachementsid;
    private int internalcommunicationid;
    private String filename;
    private char opcode;
    private int modifiedby;
    private Timestamp modifiedon;

    public ZAttachements() {
    }

    public ZAttachements(int id, int attachementsid, int internalcommunicationid, String filename, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.attachementsid = attachementsid;
        this.internalcommunicationid = internalcommunicationid;
        this.filename = filename;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttachementsid() {
        return attachementsid;
    }

    public void setAttachementsid(int attachementsid) {
        this.attachementsid = attachementsid;
    }

    public int getInternalcommunicationid() {
        return internalcommunicationid;
    }

    public void setInternalcommunicationid(int internalcommunicationid) {
        this.internalcommunicationid = internalcommunicationid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public char getOpcode() {
        return opcode;
    }

    public void setOpcode(char opcode) {
        this.opcode = opcode;
    }

    public int getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(int modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Timestamp getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(Timestamp modifiedon) {
        this.modifiedon = modifiedon;
    }
}
