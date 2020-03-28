/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dhirajj
 */
public class InternalCommunication implements Serializable {

    private int id;
    private String subject;
    private char status;
    private Date requestDate;
    private String details;
    private Employees requestFrom;
    private char messagetype;
    private IcFolders outboxicfolders;
    private String rejectedReason;
    private Date rejectedDate;
    private String notes;
    private int createdby;
    private Timestamp createdon;
    private char msgFlag;
    private Set attachements;
    private Set icTo;

    public InternalCommunication() {
    }

    public InternalCommunication(int id, String subject, char status, Date requestDate, String details, Employees requestFrom, char messagetype, IcFolders outboxicfolders, String rejectedReason, Date rejectedDate, String notes, int createdby, Timestamp createdon, char msgFlag) {
        this.id = id;
        this.subject = subject;
        this.status = status;
        this.requestDate = requestDate;
        this.details = details;
        this.requestFrom = requestFrom;
        this.messagetype = messagetype;
        this.outboxicfolders = outboxicfolders;
        this.rejectedReason = rejectedReason;
        this.rejectedDate = rejectedDate;
        this.notes = notes;
        this.createdby = createdby;
        this.createdon = createdon;
        this.msgFlag = msgFlag;
        this.attachements = new HashSet(0);
        this.icTo = new HashSet(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Employees getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(Employees requestFrom) {
        this.requestFrom = requestFrom;
    }

    public char getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(char messagetype) {
        this.messagetype = messagetype;
    }

    public IcFolders getOutboxicfolders() {
        return outboxicfolders;
    }

    public void setOutboxicfolders(IcFolders outboxicfolders) {
        this.outboxicfolders = outboxicfolders;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public Date getRejectedDate() {
        return rejectedDate;
    }

    public void setRejectedDate(Date rejectedDate) {
        this.rejectedDate = rejectedDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Set getAttachements() {
        return attachements;
    }

    public void setAttachements(Set attachements) {
        this.attachements = attachements;
    }

    public Set getIcTo() {
        return icTo;
    }

    public void setIcTo(Set icTo) {
        this.icTo = icTo;
    }

    public char getMsgFlag() {
        return msgFlag;
    }

    public void setMsgFlag(char msgFlag) {
        this.msgFlag = msgFlag;
    }
}
