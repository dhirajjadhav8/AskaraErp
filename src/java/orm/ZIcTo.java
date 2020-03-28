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
public class ZIcTo implements Serializable {

    private int id;
    private int ictoid;
    private int requestTo;
    private int internalcommunicationid;
    private Boolean seen;
    private Timestamp seenDate;
    private int icfolderid;
    private char opcode;
    private int modifiedby;
    private Timestamp modifiedon;
    private char msgFlag;

    public ZIcTo() {
    }

    public ZIcTo(int id, int ictoid, int requestTo, int internalcommunicationid, Boolean seen, Timestamp seenDate, int icfolderid, char opcode, int modifiedby, Timestamp modifiedon, char msgFlag) {
        this.id = id;
        this.ictoid = ictoid;
        this.requestTo = requestTo;
        this.internalcommunicationid = internalcommunicationid;
        this.seen = seen;
        this.seenDate = seenDate;
        this.icfolderid = icfolderid;
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

    public int getIctoid() {
        return ictoid;
    }

    public void setIctoid(int ictoid) {
        this.ictoid = ictoid;
    }

    public int getRequestTo() {
        return requestTo;
    }

    public void setRequestTo(int requestTo) {
        this.requestTo = requestTo;
    }

    public int getInternalcommunicationid() {
        return internalcommunicationid;
    }

    public void setInternalcommunicationid(int internalcommunicationid) {
        this.internalcommunicationid = internalcommunicationid;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Timestamp getSeenDate() {
        return seenDate;
    }

    public void setSeenDate(Timestamp seenDate) {
        this.seenDate = seenDate;
    }

    public int getIcfolderid() {
        return icfolderid;
    }

    public void setIcfolderid(int icfolderid) {
        this.icfolderid = icfolderid;
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
