/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeListener;
import javax.imageio.ImageIO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import orm.Employees;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "testDAO")
@ViewScoped
public class TestDAO implements Serializable {

    
    private String selectedEmployee;
    private UploadedFile file;
    private List<Employees> allemployee;
    


    /**
     * Creates a new instance of TestDAO
     */
    public TestDAO() {
        this.allemployee = new ArrayList<Employees>();
        
        System.out.println(" New TestDAO is created......"); 
      //  this.retrieveAllEmployee();
        
    }
 

    public String getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(String selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public List<Employees> getAllemployee() {
        return allemployee;
    }

    public void setAllemployee(List<Employees> allemployee) {
        this.allemployee = allemployee;
    }

    
    public void retrieveAllEmployee(){
        
        Session session = null;
        Transaction trx = null;
        try{
            
            if (!FacesContext.getCurrentInstance().isPostback()){
            
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            
            this.allemployee.clear();
            Query q = session.createQuery("from Employees");
            
            this.allemployee.addAll(q.list());
            
            trx.commit();
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    

   

    
    
    public void retrieveFirstname(){
        System.out.println(this.selectedEmployee);
    }
    
}
