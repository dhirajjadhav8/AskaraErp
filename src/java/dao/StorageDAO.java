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
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import orm.Area;
import orm.ItemDetails;
import orm.Storage;
import orm.Storagetypes;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "storageDAO")
@ViewScoped
public class StorageDAO implements Serializable {
    
    private List<Storage> allStorage;
    private List<Storagetypes> allStorageTypes;
    private List<Area> allArea;
    private List<ItemDetails> allItemDetails;
    private Storage selectedStorage;
    private Storagetypes selectedStorageTypes;
    private Area selectedArea;
    private ItemDetails selectedItemDetails;
    
    public List<Area> getAllArea() {
        return allArea;
    }
    
    public void setAllArea(List<Area> allArea) {
        this.allArea = allArea;
    }
    
    public List<Storage> getAllStorage() {
        return allStorage;
    }
    
    public void setAllStorage(List<Storage> allStorage) {
        this.allStorage = allStorage;
    }
    
    public List<Storagetypes> getAllStorageTypes() {
        return allStorageTypes;
    }
    
    public void setAllStorageTypes(List<Storagetypes> allStorageTypes) {
        this.allStorageTypes = allStorageTypes;
    }
    
    public Area getSelectedArea() {
        return selectedArea;
    }
    
    public void setSelectedArea(Area selectedArea) {
        this.selectedArea = selectedArea;
    }
    
    public Storage getSelectedStorage() {
        return selectedStorage;
    }
    
    public void setSelectedStorage(Storage selectedStorage) {
        this.selectedStorage = selectedStorage;
    }
    
    public Storagetypes getSelectedStorageTypes() {
        return selectedStorageTypes;
    }
    
    public void setSelectedStorageTypes(Storagetypes selectedStorageTypes) {
        this.selectedStorageTypes = selectedStorageTypes;
    }
    
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
     * Creates a new instance of StorageDAO
     */
    public StorageDAO() {
        this.allArea = new ArrayList<Area>();
        this.allStorage = new ArrayList<Storage>();
        this.allStorageTypes = new ArrayList<Storagetypes>();
        this.allItemDetails = new ArrayList<ItemDetails>();
        
        this.selectedArea = new Area();
        this.selectedStorage = new Storage();
        this.selectedStorageTypes = new Storagetypes();
        this.selectedItemDetails = new ItemDetails();
    }
    
    public void retrieveAllStorageTypes() {
        Session session = null;
        Transaction trx = null;
        
        
        Query q = null;
        
        try {
            
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            if (!FacesContext.getCurrentInstance().isPostback()) {
                this.allStorageTypes.clear();
                
                String hqlQuery = "from Storagetypes";
                
                q = session.createQuery(hqlQuery);
                
                this.allStorageTypes.addAll(q.list());
                trx.commit();
            }
            
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
    
    public void retrieveAllAreas() {
        
        Session session = null;
        Transaction trx = null;
        
        
        Query q = null;
        
        try {
            
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            if (!FacesContext.getCurrentInstance().isPostback()) {
                this.allArea.clear();
                
                String hqlQuery = "from Area";
                
                q = session.createQuery(hqlQuery);
                
                this.allArea.addAll(q.list());
                trx.commit();
            }
            
            
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
    
    public void retrieveAllItemDetails() {
        Session session = null;
        Transaction trx = null;
        
        Criteria cr = null;
        try {
            
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            if (!FacesContext.getCurrentInstance().isPostback()) {
                this.allItemDetails.clear();
                cr = session.createCriteria(ItemDetails.class, "itemDetails")
                        .createAlias("itemDetails.item", "item", JoinType.INNER_JOIN)
                        .createAlias("item.imake", "imake", JoinType.INNER_JOIN)
                        .createAlias("item.itype", "itype", JoinType.INNER_JOIN)
                        .createAlias("itype.icategory", "icategory", JoinType.INNER_JOIN)
                        .add(Restrictions.eq("icategory.id", 4));
                
                this.allItemDetails.addAll(cr.list());
                
                trx.commit();
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (cr != null) {
                cr = null;
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
    
    public void retrieveAllStorages() {
        
        Session session = null;
        Transaction trx = null;
        
        Query q = null;
        
        Criteria cr = null;
        
        this.allStorage.clear();
        
        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            
            cr = session.createCriteria(Storage.class, "storage").createAlias("storage.storagetypes", "storagetypes", JoinType.INNER_JOIN).createAlias("storage.area", "area", JoinType.INNER_JOIN);
            
            this.allStorage.addAll(cr.list());
            trx.commit();
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (cr != null) {
                cr = null;
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
    
    public void check(SelectEvent event) {
        RequestContext req = RequestContext.getCurrentInstance();
        req.update("inptKeyNo");
        
    }
    
    public void addStorage() {
        
        Session session = null;
        Transaction trx = null;
        RequestContext req = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        
        try {
            Login loginDAO = (Login) context.getApplication().createValueBinding("#{loginbean}").getValue(context);
            
            int userId = loginDAO.getLoggedinUser().getId();
            
            Date date = new Date();
            
            this.selectedStorage.setCreatedby(userId);
            this.selectedStorage.setCreatedon(new Timestamp(date.getTime()));
            this.selectedStorage.setArea(this.selectedArea);
            this.selectedStorage.setStoragetypes(this.getSelectedStorageTypes());
            this.selectedStorage.setItemDetails(this.getSelectedItemDetails());
            
            if (this.selectedStorage.isIslocable() == true && (this.selectedStorage.getKyeNo() == null || this.selectedStorage.getKyeNo().equals(""))) {
                
                String msg = text.getString("ui.SelectKeyNoAdd");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
                req.update("growlmsg");
            } else {
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                
                session.save(this.selectedStorage);
                
                trx.commit();
                
                String message = text.getString("ui.Bean.StorageddSuccess");
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                req.update("growlmsg");
                this.setSelectedStorage(null);
                this.setSelectedStorage(new Storage());
                
                this.setSelectedArea(null);
                this.setSelectedArea(new Area());
                
                this.setSelectedStorageTypes(null);
                this.setSelectedStorageTypes(new Storagetypes());
                
                this.setSelectedItemDetails(null);
                this.setSelectedItemDetails(new ItemDetails());
            }
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
