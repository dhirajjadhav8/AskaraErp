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
public class ZStorage implements Serializable{
    
     private int id;
     private int storageid;
     private String code;
     private Integer storagetypeid;
     private Boolean islocable;
     private String kyeNo;
     private Integer areaid;
     private char opcode;
     private int modifiedby;
     private Timestamp modifiedon;

    public ZStorage() {
    }

    public ZStorage(int id, int storageid, String code, Integer storagetypeid, Boolean islocable, String kyeNo, Integer areaid, char opcode, int modifiedby, Timestamp modifiedon) {
        this.id = id;
        this.storageid = storageid;
        this.code = code;
        this.storagetypeid = storagetypeid;
        this.islocable = islocable;
        this.kyeNo = kyeNo;
        this.areaid = areaid;
        this.opcode = opcode;
        this.modifiedby = modifiedby;
        this.modifiedon = modifiedon;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getIslocable() {
        return islocable;
    }

    public void setIslocable(Boolean islocable) {
        this.islocable = islocable;
    }

    public String getKyeNo() {
        return kyeNo;
    }

    public void setKyeNo(String kyeNo) {
        this.kyeNo = kyeNo;
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

    public char getOpcode() {
        return opcode;
    }

    public void setOpcode(char opcode) {
        this.opcode = opcode;
    }

    public int getStorageid() {
        return storageid;
    }

    public void setStorageid(int storageid) {
        this.storageid = storageid;
    }

    public Integer getStoragetypeid() {
        return storagetypeid;
    }

    public void setStoragetypeid(Integer storagetypeid) {
        this.storagetypeid = storagetypeid;
    }    
    
}
