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
public class Todos implements Serializable {

    private int id;
    private String subject;
    private String discription;
    private Timestamp todoDate;
    private char priority;
    private Employees todofor;
    private boolean iscompleted;
    private int createdby;
    private Timestamp createdon;
    private Timestamp completeDate;

    public Todos() {
    }

    public Todos(int id, String subject, String discription, Timestamp todoDate, char priority, Employees todofor, boolean iscompleted, int createdby, Timestamp createdon, Timestamp completeDate) {
        this.id = id;
        this.subject = subject;
        this.discription = discription;
        this.todoDate = todoDate;
        this.priority = priority;
        this.todofor = todofor;
        this.iscompleted = iscompleted;
        this.createdby = createdby;
        this.createdon = createdon;
        this.completeDate = completeDate;
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

    public Employees getTodofor() {
        return todofor;
    }

    public void setTodofor(Employees todofor) {
        this.todofor = todofor;
    }

    public boolean isIscompleted() {
        return iscompleted;
    }

    public void setIscompleted(boolean iscompleted) {
        this.iscompleted = iscompleted;
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

    public Timestamp getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Timestamp completeDate) {
        this.completeDate = completeDate;
    }
}
