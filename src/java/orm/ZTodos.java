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
public class ZTodos implements Serializable {

    private int id;
    private int todosid;
    private String subject;
    private String discription;
    private Timestamp todoDate;
    private char priority;
    private int todofor;
    private Boolean iscompleted;
    private char opcode;
    private int modifiedby;
    private Timestamp modifiedon;
    private Timestamp completeDate;

    public ZTodos() {
    }

    public ZTodos(int id, int todosid, String subject, String discription, Timestamp todoDate, char priority, int todofor, Boolean iscompleted, char opcode, int modifiedby, Timestamp modifiedon, Timestamp completeDate) {
        this.id = id;
        this.todosid = todosid;
        this.subject = subject;
        this.discription = discription;
        this.todoDate = todoDate;
        this.priority = priority;
        this.todofor = todofor;
        this.iscompleted = iscompleted;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
        this.completeDate = completeDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTodosid() {
        return todosid;
    }

    public void setTodosid(int todosid) {
        this.todosid = todosid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Timestamp getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(Timestamp todoDate) {
        this.todoDate = todoDate;
    }

    public char getPriority() {
        return priority;
    }

    public void setPriority(char priority) {
        this.priority = priority;
    }

    public int getTodofor() {
        return todofor;
    }

    public void setTodofor(int todofor) {
        this.todofor = todofor;
    }

    public Boolean getIscompleted() {
        return iscompleted;
    }

    public void setIscompleted(Boolean iscompleted) {
        this.iscompleted = iscompleted;
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

    public Timestamp getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Timestamp completeDate) {
        this.completeDate = completeDate;
    }
}
