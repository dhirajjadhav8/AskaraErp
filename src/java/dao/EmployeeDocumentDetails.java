/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.*;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.DbTimestampType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.primefaces.context.RequestContext;
import orm.Documents;
import orm.EmployeeDocuments;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name="employeeDocumentDetailsDAO")
@ViewScoped
public class EmployeeDocumentDetails implements Serializable{
    
    private List<EmployeeDocuments> allEmpExpDocuments;
    private EmployeeDocuments selectedEmpDocument;
    private int count;
    

    /**
     * Creates a new instance of EmployeeDocumentDetails
     */
    public EmployeeDocumentDetails() {
        
        this.allEmpExpDocuments = new ArrayList<EmployeeDocuments>();
        this.selectedEmpDocument = new EmployeeDocuments();
        
         System.out.println(" New EmployeeDocumentDetails is created......");
        
        this.retrieveAllExpDocuments();
        
    }

    public List<EmployeeDocuments> getAllEmpExpDocuments() {
        return allEmpExpDocuments;
    }

    public void setAllEmpExpDocuments(List<EmployeeDocuments> allEmpExpDocuments) {
        this.allEmpExpDocuments = allEmpExpDocuments;
    }

    public EmployeeDocuments getSelectedEmpDocument() {
        return selectedEmpDocument;
    }

    public void setSelectedEmpDocument(EmployeeDocuments selectedEmpDocument) {
        this.selectedEmpDocument = selectedEmpDocument;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

     
    
    public void retrieveAllExpDocuments(){
         Session session = null;
        Transaction trx = null;
        
        Query q = null;
        
        Criteria cr = null;
        this.allEmpExpDocuments.clear();
         FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        
        try{
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
                        
            cr = session.createCriteria(EmployeeDocuments.class)
                     .add(Restrictions.sqlRestriction(" {fn TIMESTAMPDIFF( SQL_TSI_DAY, CURRENT_DATE, EXPAIRY_DATE)} <= 10 or (CURRENT_DATE>=EXPAIRY_DATE)"))
                     .setFetchMode("documents", FetchMode.JOIN)
                     .setFetchMode("employees", FetchMode.JOIN);       
            
            this.allEmpExpDocuments.addAll(cr.list());
            trx.commit();
            
        }catch (Exception e) {

            trx.rollback();
            
            if (this.allEmpExpDocuments == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            }


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
    
    public void employeeDocExpCount(){
        
        Session session = null;
        Transaction trx = null;      
        Query q = null;
        
        Criteria cr1 = null;
        
        
        try{
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
                        
            cr1 = session.createCriteria(EmployeeDocuments.class)
                    .setProjection( Projections.rowCount() )
                     .add(Restrictions.sqlRestriction(" {fn TIMESTAMPDIFF( SQL_TSI_DAY, CURRENT_DATE, EXPAIRY_DATE)} <= 10 or (CURRENT_DATE>=EXPAIRY_DATE)"))
                     .setFetchMode("documents", FetchMode.JOIN)
                     .setFetchMode("employees", FetchMode.JOIN);       
           
           
           Iterator itr= cr1.list().iterator();
           int i =1;
           while(itr.hasNext()){
              if(i==1){
                   this.count = ((Long)itr.next()).intValue();                                
                  break;
              }               
               i++;
           }
            
            trx.commit();
            
        }catch (Exception e) {

            trx.rollback();
           

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
    
}
