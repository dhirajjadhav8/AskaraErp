package orm;
// Generated 12 Feb, 2013 3:25:21 PM by Hibernate Tools 3.2.1.GA


import java.sql.Timestamp;
import java.util.Date;

/**
 * ZUsers generated by hbm2java
 */
public class ZUsers  implements java.io.Serializable {


     private int id;
     private int userid;
     private String username;
     private String password;
     private int employeeid;
     private char opcode;
     private Integer modifiedby;
     private Date modifiedon;
     private Timestamp passwordchangedon;
     private String salt;

    public ZUsers() {
    }

	
    public ZUsers(int id, int userid, int employeeid, char opcode, Date modifiedon, Timestamp passwordchangedon) {
        this.id = id;
        this.userid = userid;
        this.employeeid = employeeid;
        this.opcode = opcode;
        this.modifiedon = modifiedon;
        this.passwordchangedon = passwordchangedon;
    }
    public ZUsers(int id, int userid, String username, String password, int employeeid, char opcode, Integer modifiedby, Date modifiedon, Timestamp passwordchangedon , String salt) {
       this.id = id;
       this.userid = userid;
       this.username = username;
       this.password = password;
       this.employeeid = employeeid;
       this.opcode = opcode;
       this.modifiedby = modifiedby;
       this.modifiedon = modifiedon;
       this.passwordchangedon = passwordchangedon;
       this.salt = salt;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public int getUserid() {
        return this.userid;
    }
    
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public int getEmployeeid() {
        return this.employeeid;
    }
    
    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }
    public char getOpcode() {
        return this.opcode;
    }
    
    public void setOpcode(char opcode) {
        this.opcode = opcode;
    }
    public Integer getModifiedby() {
        return this.modifiedby;
    }
    
    public void setModifiedby(Integer modifiedby) {
        this.modifiedby = modifiedby;
    }
    public Date getModifiedon() {
        return this.modifiedon;
    }
    
    public void setModifiedon(Date modifiedon) {
        this.modifiedon = modifiedon;
    }

    public Timestamp getPasswordchangedon() {
        return passwordchangedon;
    }

    public void setPasswordchangedon(Timestamp passwordchangedon) {
        this.passwordchangedon = passwordchangedon;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    

}


