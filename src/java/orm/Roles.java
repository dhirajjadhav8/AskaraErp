package orm;
// Generated 5 Oct, 2012 10:24:47 AM by Hibernate Tools 3.2.1.GA

import java.sql.Timestamp;




/**
 * Roles generated by hbm2java
 */
public class Roles  implements java.io.Serializable {


     private int id;
     private String name;
     private int createdby;
     private Timestamp createdon;

    public Roles() {
    }

    public Roles(int id, String name , int createdby , Timestamp createdon) {
       this.id = id;
       this.name = name;
       this.createdby = createdby;
       this.createdon = createdon;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
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


}


