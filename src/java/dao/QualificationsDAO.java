/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import orm.Qualifications;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name="qualificationsDAO")
@ViewScoped
public class QualificationsDAO implements Serializable{
    
    private List<Qualifications> allQualifications;
    private Qualifications qualification;

    public List<Qualifications> getAllQualifications() {
        return allQualifications;
    }

    public void setAllQualifications(List<Qualifications> allQualifications) {
        this.allQualifications = allQualifications;
    }

    public Qualifications getQualification() {
        return qualification;
    }

    public void setQualification(Qualifications qualification) {
        this.qualification = qualification;
    }
    
    
       

    /**
     * Creates a new instance of QualificationsDAO
     */
    public QualificationsDAO() {
        this.allQualifications = new ArrayList<Qualifications>();
        this.qualification = new Qualifications();
        this.retrieveQualifications();
         System.out.println(" New QualificationDAO is created......");    
    }
    
    
    // list of qualifications to be retrieve.
    
    public void retrieveQualifications(){
        
        Session session = null;
        Transaction trx = null;
        
        this.allQualifications.clear();
        
        Query q = null;
         FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        
        try{
            
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            
            q= session.createQuery("from Qualifications");
            
            this.allQualifications.addAll(q.list());
            
            trx.commit();
            
            
        }catch (Exception e) {

            trx.rollback();

            if (this.allQualifications == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            }
            System.out.println(e.getMessage());

        } finally {

            if (q != null) {
                q = null;
            }
            if (trx != null) {
                trx = null;
            }
            if (session != null) {
                session.clear();
                session.close();
                session = null;
            }

        }
        
        
    }
    
    
    public void addQualifications(){
        
        Session session = null;
        Transaction trx = null;
        
         FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        
        
        try{
            
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
                    
            session.save(this.qualification); 
                        
            trx.commit();
            
        }catch (Exception e) {
                       

            trx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "1. Please click Add again. OR ", "1. Please click Add again. OR"));
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "2. close the tab and refresh the page. OR ", "2. close the tab and refresh the page. OR "));
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "3. contact the system adminitrator. ", "3. contact the system adminitrator."));
            
            System.out.println(e.getMessage());

        } finally {

            if (trx != null) {
                trx = null;
            }
            if (session != null) {
                session.clear();
                session.close();
                session = null;
            }

        }
        
    }
    
    
}
