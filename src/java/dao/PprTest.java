/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name="pprTestDAO")
@RequestScoped
public class PprTest {
     private String firstname;       
    private String surname;  

    /**
     * Creates a new instance of PprTest
     */
    public PprTest() {
         System.out.println(" New PprTest is created......");
    }
    
     public String getFirstname() {  
        return firstname;  
    }  
  
    public void setFirstname(String firstname) {  
        this.firstname = firstname;  
    }  
  
    public String getSurname() {  
        return surname;  
    }  
  
    public void setSurname(String surname) {  
        this.surname = surname;  
    }  
      
    public void reset() {  
        RequestContext.getCurrentInstance().reset("form:panel");  
    }  
  
    public void resetFail() {  
        this.firstname = null;  
        this.surname = null;  
          
        FacesMessage msg = new FacesMessage("Model reset, but it won't work.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
    
}
