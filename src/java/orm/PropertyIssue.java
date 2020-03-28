/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author dhirajj
 */
public class PropertyIssue implements java.io.Serializable{
    
    private int id;
    private Employees employees;
    private Properties properties;
    private Unites unites;
    private String comments;
    private Users issuedBy;
    private Date issueDate;
    private Users returnedBy;
    private Date returnDate;
    private Users verifiedBy;
    private Date verifyDate;
    private String quantity;
    private int createdby;
    private Timestamp createdon;

    public PropertyIssue() {
    }

    public PropertyIssue(int id, Employees employees, Properties properties, Unites unites, String comments, Users issuedBy, Date issueDate, Users returnedBy, Date returnDate, Users verifiedBy, Date verifyDate, String quantity , int createdby, Timestamp createdon) {
        this.id = id;
        this.employees = employees;
        this.properties = properties;
        this.unites = unites;
        this.comments = comments;
        this.issuedBy = issuedBy;
        this.issueDate = issueDate;
        this.returnedBy = returnedBy;
        this.returnDate = returnDate;
        this.verifiedBy = verifiedBy;
        this.verifyDate = verifyDate;
        this.quantity = quantity;
        this.createdby = createdby;
        this.createdon = createdon;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Users getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(Users issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Users getReturnedBy() {
        return returnedBy;
    }

    public void setReturnedBy(Users returnedBy) {
        this.returnedBy = returnedBy;
    }

    public Unites getUnites() {
        return unites;
    }

    public void setUnites(Unites unites) {
        this.unites = unites;
    }    

    public Users getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(Users verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Date getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
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
