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
import org.primefaces.context.RequestContext;
import orm.Employees;
import orm.IcFolders;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "icFoldersDAO")
@ViewScoped
public class IcFoldersDAO implements Serializable {

    private IcFolders selectedInboxIcFolder;
    private IcFolders selectedOutboxIcFolder;
    private IcFolders selectedFirstInboxIcFolder;
    private List<IcFolders> allInboxIcFolders;
    private List<IcFolders> allAddedInboxFolders;
    private List<IcFolders> allAddedOutboxFolders;

    public List<IcFolders> getAllInboxIcFolders() {
        return allInboxIcFolders;
    }

    public void setAllInboxIcFolders(List<IcFolders> allInboxIcFolders) {
        this.allInboxIcFolders = allInboxIcFolders;
    }

    public IcFolders getSelectedInboxIcFolder() {
        return selectedInboxIcFolder;
    }

    public void setSelectedInboxIcFolder(IcFolders selectedInboxIcFolder) {
        this.selectedInboxIcFolder = selectedInboxIcFolder;
    }

    public IcFolders getSelectedFirstInboxIcFolder() {
        return selectedFirstInboxIcFolder;
    }

    public void setSelectedFirstInboxIcFolder(IcFolders selectedFirstInboxIcFolder) {
        this.selectedFirstInboxIcFolder = selectedFirstInboxIcFolder;
    }

    public List<IcFolders> getAllAddedInboxFolders() {
        return allAddedInboxFolders;
    }

    public void setAllAddedInboxFolders(List<IcFolders> allAddedInboxFolders) {
        this.allAddedInboxFolders = allAddedInboxFolders;
    }

    public List<IcFolders> getAllAddedOutboxFolders() {
        return allAddedOutboxFolders;
    }

    public void setAllAddedOutboxFolders(List<IcFolders> allAddedOutboxFolders) {
        this.allAddedOutboxFolders = allAddedOutboxFolders;
    }

    public IcFolders getSelectedOutboxIcFolder() {
        return selectedOutboxIcFolder;
    }

    public void setSelectedOutboxIcFolder(IcFolders selectedOutboxIcFolder) {
        this.selectedOutboxIcFolder = selectedOutboxIcFolder;
    }

    /**
     * Creates a new instance of IcFoldersDAO
     */
    public IcFoldersDAO() {

        this.selectedInboxIcFolder = new IcFolders();
        this.selectedOutboxIcFolder = new IcFolders();
        this.selectedFirstInboxIcFolder = new IcFolders();
        this.allInboxIcFolders = new ArrayList<IcFolders>();
        this.allAddedInboxFolders = new ArrayList<IcFolders>();
        this.allAddedOutboxFolders = new ArrayList<IcFolders>();
    }

    public List<IcFolders> completeIcFolder(String folder) {
        Session session = null;
        Transaction trx = null;
        Criteria cr = null;
        this.allInboxIcFolders.clear();
        Query q = null;

        try {
            if (folder != null || !folder.equals("")) {

                List<IcFolders> suggessionFolders = new ArrayList<IcFolders>();

                this.selectedFirstInboxIcFolder.setName("Folder already exist");

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                q = session.createQuery("from IcFolders ic where Lower(ic.name) like Lower('" + folder + "%')");
//                cr = session.createCriteria(IcFolders.class);
//                cr.setMaxResults(50);
                if (q.list().isEmpty() == false) {
                    this.allInboxIcFolders.add(this.getSelectedFirstInboxIcFolder());
                }
                this.allInboxIcFolders.addAll(q.list());
                trx.commit();
//                for (IcFolders icf : allIcFolders) {
//                    if (icf.getName().toLowerCase().startsWith(folder.toLowerCase())) {
//                        suggessionFolders.add(icf);
//                    }
//                }
                return allInboxIcFolders;

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void retrieveAllInboxFolders() {
        Session session = null;
        Transaction trx = null;
        Query q = null;
        this.allAddedInboxFolders.clear();

        try {
            if (FacesContext.getCurrentInstance().isPostback()) {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
                int id = loginDAO.getLoggedinUser().getEmployees().getId();
                q = session.createQuery("from IcFolders ic where ic.employees.id =" + id + " and ic.type = 'R'");
                this.allAddedInboxFolders.addAll(q.list());

                trx.commit();
            }

        } catch (Exception e) {
            e.getMessage();
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

    public void addInboxFolder() {
        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        Query q = null;

        try {

            if (this.selectedInboxIcFolder.getName().equals("") || this.selectedInboxIcFolder.getName() == null) {
                String message = text.getString("ui.msg.Name");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                RequestContext req = RequestContext.getCurrentInstance();
                req.update(":tbvInternalComunication:frmInbox");

            } else {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                List<IcFolders> allFolders = new ArrayList<IcFolders>();
                allFolders.clear();
                q = session.createQuery("from IcFolders ic where Lower(ic.name) like Lower('" + this.selectedInboxIcFolder.getName() + "')");
                allFolders.addAll(q.list());


                if (allFolders.isEmpty() == true) {

                    Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                    Date date = new Date();
                    int id = loginDAO.getLoggedinUser().getEmployees().getId();

                    Employees selectedEmployee = loginDAO.getLoggedinUser().getEmployees();

                    this.selectedInboxIcFolder.setType('R');
                    this.selectedInboxIcFolder.setCreatedby(id);
                    this.selectedInboxIcFolder.setCreatedon(new Timestamp(date.getTime()));
                    this.selectedInboxIcFolder.setEmployees(selectedEmployee);

                    session.save(this.selectedInboxIcFolder);



                    String message = text.getString("ui.Bean.InboxIcFolderAddSuccess");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                    RequestContext req = RequestContext.getCurrentInstance();
                    req.update(":tbvInternalComunication:frmInbox");
                    this.selectedInboxIcFolder = null;
                    this.selectedInboxIcFolder = new IcFolders();

                } else {
                    String message = text.getString("ui.msg.folderPresent");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                    RequestContext req = RequestContext.getCurrentInstance();
                    req.update(":tbvInternalComunication:frmInbox");
                    this.selectedInboxIcFolder = null;
                    this.selectedInboxIcFolder = new IcFolders();
                }
                trx.commit();
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

    public void retrieveAllOutboxFolders() {
        Session session = null;
        Transaction trx = null;
        Query q = null;
        this.allAddedOutboxFolders.clear();

        try {
            if (FacesContext.getCurrentInstance().isPostback()) {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
                int id = loginDAO.getLoggedinUser().getEmployees().getId();
                q = session.createQuery("from IcFolders ic where ic.employees.id =" + id + " and ic.type = 'S'");
                this.allAddedOutboxFolders.addAll(q.list());

                trx.commit();
            }

        } catch (Exception e) {
            e.getMessage();
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

    public void addOutboxFolder() {
        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        Query q = null;

        try {

            if (this.selectedOutboxIcFolder.getName().equals("") || this.selectedOutboxIcFolder.getName() == null) {
                String message = text.getString("ui.msg.Name");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                RequestContext req = RequestContext.getCurrentInstance();
                req.update(":tbvInternalComunication:frmInbox");

            } else {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                List<IcFolders> allFolders = new ArrayList<IcFolders>();
                allFolders.clear();
                q = session.createQuery("from IcFolders ic where Lower(ic.name) like Lower('" + this.selectedOutboxIcFolder.getName() + "')");
                allFolders.addAll(q.list());


                if (allFolders.isEmpty() == true) {

                    Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                    Date date = new Date();
                    int id = loginDAO.getLoggedinUser().getEmployees().getId();

                    Employees selectedEmployee = loginDAO.getLoggedinUser().getEmployees();

                    this.selectedOutboxIcFolder.setType('S');
                    this.selectedOutboxIcFolder.setCreatedby(id);
                    this.selectedOutboxIcFolder.setCreatedon(new Timestamp(date.getTime()));
                    this.selectedOutboxIcFolder.setEmployees(selectedEmployee);

                    session.save(this.selectedOutboxIcFolder);



                    String message = text.getString("ui.Bean.OutboxIcFolderAddSuccess");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                    RequestContext req = RequestContext.getCurrentInstance();
                    req.update(":tbvInternalComunication:frmOutbox");
                    this.selectedOutboxIcFolder = null;
                    this.selectedOutboxIcFolder = new IcFolders();

                } else {
                    String message = text.getString("ui.msg.folderPresent");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                    RequestContext req = RequestContext.getCurrentInstance();
                    req.update(":tbvInternalComunication:frmOutbox");
                    this.selectedOutboxIcFolder = null;
                    this.selectedOutboxIcFolder = new IcFolders();
                }
                trx.commit();
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
