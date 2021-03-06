package orm;
// Generated 12 Feb, 2013 3:25:21 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * ZResponsibilities generated by hbm2java
 */
public class ZResponsibilities  implements java.io.Serializable {


     private int id;
     private int responsibilityid;
     private String name;
     private String description;
     private char opcode;
     private Integer modifiedby;
     private Date modifiedon;

    public ZResponsibilities() {
    }

	
    public ZResponsibilities(int id, int responsibilityid, char opcode, Date modifiedon) {
        this.id = id;
        this.responsibilityid = responsibilityid;
        this.opcode = opcode;
        this.modifiedon = modifiedon;
    }
    public ZResponsibilities(int id, int responsibilityid, String name, String description, char opcode, Integer modifiedby, Date modifiedon) {
       this.id = id;
       this.responsibilityid = responsibilityid;
       this.name = name;
       this.description = description;
       this.opcode = opcode;
       this.modifiedby = modifiedby;
       this.modifiedon = modifiedon;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public int getResponsibilityid() {
        return this.responsibilityid;
    }
    
    public void setResponsibilityid(int responsibilityid) {
        this.responsibilityid = responsibilityid;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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




}


