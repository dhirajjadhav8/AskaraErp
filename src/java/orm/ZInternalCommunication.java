/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author dhirajj
 */
public class ZInternalCommunication implements Serializable {

    private int id;
    private int internalcommunicationid;
    private String subject;
    private char status;
    private Date requestDate;
    private String details;
    private int requestFrom;
    private char messagetype;
    private int icfolderid;
    private String rejectedReason;
    private Date rejectedDate;
    private String notes;
    private char opcode;
    private int modifiedby;
    private Timestamp modifiedon;
    private char msgFlag;

    public ZInternalCommunication() {
    }

    public ZInternalCommunication(int id, int internalcommunicationid, String subject, char status, Date requestDate, String details, int requestFrom, char messagetype, int icfolderid, String rejectedReason, Date rejectedDate, String notes, char opcode, int modifiedby, Timestamp modifiedon, char msgFlag) {
        this.id = id;
        this.internalcommunicationid = internalcommunicationid;
        this.subject = subject;
        this.status = status;
        this.requestDate = requestDate;
        this.details = details;
        this.requestFrom = requestFrom;
        this.messagetype = messagetype;
        this.icfolderid = icfolderid;
        this.rejectedReason = rejectedReason;
        this.rejectedDate = rejectedDate;
        this.notes = notes;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
        this.msgFlag = msgFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInternalcommunicationid() {
        return internalcommunicationid;
    }

    public void setInternalcommunicationid(int internalcommunicationid) {
        this.internalcommunicationid = internalcommunicationid;
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

    public int getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(int requestFrom) {
        this.requestFrom = requestFrom;
    }

    public char getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(char messagetype) {
        this.messagetype = messagetype;
    }

    public int getIcfolderid() {
        return icfolderid;
    }

    public void setIcfolderid(int icfolderid) {
        this.icfolderid = icfolderid;
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

    public char getMsgFlag() {
        return msgFlag;
    }

    public void setMsgFlag(char msgFlag) {
        this.msgFlag = msgFlag;
    }
}
