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
public class LoginDetails implements Serializable{
    
    private int id;
    private String ipAddress;
    private Timestamp loginDate;
    private Timestamp logoutDate;
    private char isSuccess;
    private String failReason;
    private String userName;
    private String sessionId;
    private int createdby;
    private Timestamp createdon;

    public LoginDetails() {
    }

    public LoginDetails(int id, String ipAddress, Timestamp loginDate, Timestamp logoutDate, char isSuccess, String failReason, String userName, String sessionId , int createdby , Timestamp createdon) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.loginDate = loginDate;
        this.logoutDate = logoutDate;
        this.isSuccess = isSuccess;
        this.failReason = failReason;
        this.userName = userName;
        this.sessionId = sessionId;
        this.createdby = createdby;
        this.createdon = createdon;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public char getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(char isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Timestamp getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Timestamp loginDate) {
        this.loginDate = loginDate;
    }

    public Timestamp getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Timestamp logoutDate) {
        this.logoutDate = logoutDate;
    }
   
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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
