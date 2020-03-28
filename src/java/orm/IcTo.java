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
public class IcTo implements Serializable {

    private int id;
    private Employees requestTo;
    private InternalCommunication internalcommunication;
    private boolean seen;
    private Timestamp seenDate;
    private IcFolders inboxicfolders;
    private int createdby;
    private Timestamp createdon;
    private char msgFlag;

    public IcTo() {
    }

    public IcTo(int id, Employees requestTo, InternalCommunication internalcommunication, boolean seen, Timestamp seenDate, IcFolders inboxicfolders, int createdby, Timestamp createdon, char msgFlag) {
        this.id = id;
        this.requestTo = requestTo;
        this.internalcommunication = internalcommunication;
        this.seen = seen;
        this.seenDate = seenDate;
        this.inboxicfolders = inboxicfolders;
        this.createdby = createdby;
        this.createdon = createdon;
        this.msgFlag = msgFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employees getRequestTo() {
        return requestTo;
    }

    public void setRequestTo(Employees requestTo) {
        this.requestTo = requestTo;
    }

    public InternalCommunication getInternalcommunication() {
        return internalcommunication;
    }

    public void setInternalcommunication(InternalCommunication internalcommunication) {
        this.internalcommunication = internalcommunication;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Timestamp getSeenDate() {
        return seenDate;
    }

    public void setSeenDate(Timestamp seenDate) {
        this.seenDate = seenDate;
    }

    public IcFolders getInboxicfolders() {
        return inboxicfolders;
    }

    public void setInboxicfolders(IcFolders inboxicfolders) {
        this.inboxicfolders = inboxicfolders;
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

    public char getMsgFlag() {
        return msgFlag;
    }

    public void setMsgFlag(char msgFlag) {
        this.msgFlag = msgFlag;
    }
}
