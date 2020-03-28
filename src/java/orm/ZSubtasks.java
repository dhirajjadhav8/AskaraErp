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
public class ZSubtasks implements Serializable {

    private int id;
    private int subtaskid;
    private String name;
    private Date targetDate;
    private Date actualDate;
    private String lateReason;
    private int taskid;
    private char opcode;
    private int modifiedby;
    private Timestamp modifiedon;

    public ZSubtasks() {
    }

    public ZSubtasks(int id, int subtaskid, String name, Date targetDate, Date actualDate, String lateReason, int taskid, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.subtaskid = subtaskid;
        this.name = name;
        this.targetDate = targetDate;
        this.actualDate = actualDate;
        this.lateReason = lateReason;
        this.taskid = taskid;
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

    public int getSubtaskid() {
        return subtaskid;
    }

    public void setSubtaskid(int subtaskid) {
        this.subtaskid = subtaskid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public String getLateReason() {
        return lateReason;
    }

    public void setLateReason(String lateReason) {
        this.lateReason = lateReason;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
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
