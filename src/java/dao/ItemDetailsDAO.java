/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import orm.ItemDetails;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean
@ViewScoped
public class ItemDetailsDAO implements Serializable {
    
    private List<ItemDetails> allItemDetails;
    private ItemDetails selectedItemDetails;
    
    public List<ItemDetails> getAllItemDetails() {
        return allItemDetails;
    }
    
    public void setAllItemDetails(List<ItemDetails> allItemDetails) {
        this.allItemDetails = allItemDetails;
    }
    
    public ItemDetails getSelectedItemDetails() {
        return selectedItemDetails;
    }
    
    public void setSelectedItemDetails(ItemDetails selectedItemDetails) {
        this.selectedItemDetails = selectedItemDetails;
    }

    /**
     * Creates a new instance of ItemDetailsDAO
     */
    public ItemDetailsDAO() {
        this.allItemDetails = new ArrayList<ItemDetails>();
        this.selectedItemDetails = new ItemDetails();
        
        System.out.println(" New ItemDetailsDAO is created......");
    }
    
    public void retrieveAllItemDetails() {
        Session session = null;
        Transaction trx = null;
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        
        Query q = null;
        this.allItemDetails.clear();
        
        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ItemDAO itemDAO = (ItemDAO) facesContext.getApplication().createValueBinding("#{itemDAO}").getValue(facesContext);
            
            int id = itemDAO.getSelectedItem().getId();
            
            String hqlQuery = "from ItemDetails itemdet where itemdet.item.id = " + id;
            
            q = session.createQuery(hqlQuery);
            
            this.allItemDetails.addAll(q.list());
            trx.commit();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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
    
    public void addItemDetails() {
        
        Session session = null;
        Transaction trx = null;
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        
        try {
            
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
            
            ItemDAO itemDAO = (ItemDAO) facesContext.getApplication().createValueBinding("#{itemDAO}").getValue(facesContext);
            
            Date date = new Date();
            
            this.selectedItemDetails.setCreatedby(loginDAO.getLoggedinUser().getId());
            this.selectedItemDetails.setCreatedon(new Timestamp(date.getTime()));
            this.selectedItemDetails.setItem(itemDAO.getSelectedItem());
            
            session.save(this.getSelectedItemDetails());
            trx.commit();
            
            this.retrieveAllItemDetails();
            
            RequestContext req = RequestContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(text.getString("ui.msg.addItemDetailsSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            this.setSelectedItemDetails(null);
            this.setSelectedItemDetails(new ItemDetails());
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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
