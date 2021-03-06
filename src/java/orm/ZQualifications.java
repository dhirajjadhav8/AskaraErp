package orm;
// Generated 12 Feb, 2013 3:25:21 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * ZQualifications generated by hbm2java
 */
public class ZQualifications  implements java.io.Serializable {


     private int id;
     private int qualificationid;
     private String name;
     private String description;
     private char opcode;
     private Integer modifiedby;
     private Date modifiedon;

    public ZQualifications() {
    }

	
    public ZQualifications(int id, int qualificationid, char opcode, Date modifiedon) {
        this.id = id;
        this.qualificationid = qualificationid;
        this.opcode = opcode;
        this.modifiedon = modifiedon;
    }
    public ZQualifications(int id, int qualificationid, String name, String description, char opcode, Integer modifiedby, Date modifiedon) {
       this.id = id;
       this.qualificationid = qualificationid;
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
    public int getQualificationid() {
        return this.qualificationid;
    }
    
    public void setQualificationid(int qualificationid) {
        this.qualificationid = qualificationid;
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


