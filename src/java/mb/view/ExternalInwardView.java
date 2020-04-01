/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.ContactsConverter;
import converter.EmployeesConverter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.ContactsManagerImpl;
import manager.impl.EinwardsManagerImpl;
import manager.impl.EmployeesManagerImpl;
import manager.interfaces.IContactsManager;
import manager.interfaces.IEinwardsManager;
import manager.interfaces.IEmployeesManager;
import mb.util.session.LoginS;
import org.hibernate.Query;
import org.primefaces.context.RequestContext;
import orm.Contacts;
import orm.Einwards;
import orm.Employees;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "externalInwardView")
@ViewScoped
public class ExternalInwardView implements Serializable {

    /*
     Loginbean Inject 
     
     */
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;

    /*
     Inwards 
     
     */
    private IEinwardsManager eim = new EinwardsManagerImpl();
    private List<Einwards> empExtInwardList;
    private List<Einwards> extInwardList;
    private List<Einwards> extInwardHistoryList;
    private Einwards selectedInward;
    private Date inwardFrom;
    private Date inwardTo;
    /*
     Contacts   
     */
    private IContactsManager cm = new ContactsManagerImpl();
    List<Contacts> autocompleteSearchContactsList;
    /* Contact converter*/
    private ContactsConverter contactsConverter = new ContactsConverter();
    /*
     Employee
     */
    private IEmployeesManager em = new EmployeesManagerImpl();
    private List<Employees> autocompleteSearchEmployeesList;
    /* Emploeey converter*/
    private EmployeesConverter employeesConverter = new EmployeesConverter();

    /**
     * Creates a new instance of ExternalInwardView
     */
    public ExternalInwardView() {

        System.out.println("ExternalInwardView created..........");

    }

    @PostConstruct
    public void postConstructExternalInwardView() {
        retrieveExternalInward();
    }

    public IEinwardsManager getEim() {
        return eim;
    }

    public void setEim(IEinwardsManager eim) {
        this.eim = eim;
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public List<Einwards> getEmpExtInwardList() {
        return empExtInwardList;
    }

    public void setEmpExtInwardList(List<Einwards> empExtInwardList) {
        this.empExtInwardList = empExtInwardList;
    }

    public Einwards getSelectedInward() {
        return selectedInward;
    }

    public void setSelectedInward(Einwards selectedInward) {
        this.selectedInward = selectedInward;
    }

    public List<Einwards> getExtInwardList() {
        return extInwardList;
    }

    public void setExtInwardList(List<Einwards> extInwardList) {
        this.extInwardList = extInwardList;
    }

    public List<Einwards> getExtInwardHistoryList() {
        return extInwardHistoryList;
    }

    public void setExtInwardHistoryList(List<Einwards> extInwardHistoryList) {
        this.extInwardHistoryList = extInwardHistoryList;
    }

    public Date getInwardFrom() {
        return inwardFrom;
    }

    public void setInwardFrom(Date inwardFrom) {
        this.inwardFrom = inwardFrom;
    }

    public Date getInwardTo() {
        return inwardTo;
    }

    public void setInwardTo(Date inwardTo) {
        this.inwardTo = inwardTo;
    }

    public IContactsManager getCm() {
        return cm;
    }

    public void setCm(IContactsManager cm) {
        this.cm = cm;
    }

    public ContactsConverter getContactsConverter() {
        return contactsConverter;
    }

    public void setContactsConverter(ContactsConverter contactsConverter) {
        this.contactsConverter = contactsConverter;
    }

    public IEmployeesManager getEm() {
        return em;
    }

    public void setEm(IEmployeesManager em) {
        this.em = em;
    }

    public EmployeesConverter getEmployeesConverter() {
        return employeesConverter;
    }

    public void setEmployeesConverter(EmployeesConverter employeesConverter) {
        this.employeesConverter = employeesConverter;
    }

    /*
     Employee wise External Inward retrieve.
     */
    public void retrieveExternalInward() {
        System.out.println("retrieveLimitedExternalInwardOfLoggedInEmployee");

        try {

            HibernateUtil.beginTransaction();
            if (loginbean.isEInwardsOutwardsRolePresent()) {
                extInwardList = eim.retrieveLimitedAllExternalInward(50);
            } else {
                empExtInwardList = eim.retrieveLimitedExternalInwardOfLoggedInEmployee(loginbean.getLoggedinUser().getEmployees().getId(), 50);
            }

            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }

    }

    /*
     External inward history list retrieve.   
     */
    public void retrieveExtInwardHistory() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            try {
                HibernateUtil.beginTransaction();
                if (loginbean.isEInwardsOutwardsRolePresent()) {
                    extInwardHistoryList = eim.retrieveLimitedAllExtInwardHistory(50);
                    HibernateUtil.commitTransaction();
                } else {
                    extInwardHistoryList = eim.retrieveLimitedExtInwardHistoryOfLoggedInEmployee(loginbean.getLoggedinUser().getEmployees().getId(), 50);
                    HibernateUtil.commitTransaction();
                }
            } catch (Exception e) {
                HibernateUtil.rollbackTransaction();
                System.out.println(e.getMessage());

            } finally {
                HibernateUtil.closeSession();
            }
        }
    }


    /*
     Date wise all external inwards list retrieve. 
     */
    public void searchDateWiseExtInward() {
        try {
            HibernateUtil.beginTransaction();
            if (inwardFrom != null && inwardTo != null) {
                if (inwardFrom.compareTo(inwardTo) > 0) {
                    String error = "ToDate should be smaller than FromDate";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
                } else {
                    if (loginbean.isEInwardsOutwardsRolePresent()) {
                        extInwardList = eim.searchDateWiseAllEinwards(inwardFrom, inwardTo);
                        HibernateUtil.commitTransaction();
                    } else {
                        empExtInwardList = eim.searchDateWiseEinwardsOfLoggedInEmployee(inwardFrom, inwardTo, loginbean.getLoggedinUser().getEmployees().getId());
                        HibernateUtil.commitTransaction();
                    }
                }
            } else {
                String error = "FromDate or ToDate is required";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
            }

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*
     To Add Inward set selected
     */
    public void clearSelectedInwardToAdd() {
        selectedInward = null;
        selectedInward = new Einwards();
    }


    /*
     updateExtInwardOfLoggedInEmployee
     */
    public void updateToReceivedInward() {
        RequestContext req = null;
        try {
            req = RequestContext.getCurrentInstance();
            if (selectedInward.getIsreceived()) {
                HibernateUtil.beginTransaction();
                eim.updateToReceivedInward(selectedInward);
                HibernateUtil.commitTransaction();
                selectedInward = null;
                String message = "Inward updated successfully.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                req.execute("DlgConfirmForInward.hide();");
                retrieveExternalInward();

            } else {
                String error = "Please check isreceived to update.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
            }

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*
     Contacts Autocomplete method
     */
    public List<Contacts> searchContactForAutocomplete(String strSearchContactCriteria) {

        try {
            HibernateUtil.beginTransaction();
            autocompleteSearchContactsList = cm.searchContactForAutocomplete(strSearchContactCriteria);
            HibernateUtil.commitTransaction();
            contactsConverter.setSearchContacts(autocompleteSearchContactsList);
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
        return autocompleteSearchContactsList;
    }

    /*
     Employee Autocomplete method
     */
    public List<Employees> searchEmployeeForAutocomplete(String strSearchEmployeeCriteria) {

        try {
            HibernateUtil.beginTransaction();
            autocompleteSearchEmployeesList = em.searchEmployeeForAutocomplete(strSearchEmployeeCriteria);
            HibernateUtil.commitTransaction();
            employeesConverter.setSearchEmployees(autocompleteSearchEmployeesList);
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
        return autocompleteSearchEmployeesList;
    }

    /*
     Add Inward
     */
    public void addInward() {
        Query q = null;
        Query q1 = null;
        RequestContext req = null;
        try {
            req = RequestContext.getCurrentInstance();
            HibernateUtil.beginTransaction();
            String sqlQuery = "select YEAR(current_date) from SYSIBM.SYSDUMMY1";
            q = HibernateUtil.getSession().createSQLQuery(sqlQuery);

            String strYear = q.list().get(0).toString();

            String sqlQuery1 = "select count(*) from Einwards";
            q1 = HibernateUtil.getSession().createSQLQuery(sqlQuery1);

            String Count = q1.list().get(0).toString();
            int rowCount = Integer.parseInt(Count);

            if (rowCount == 0) {
                selectedInward.setInNo(strYear + "-1");
            }
            if (rowCount > 0) {
                int rowCount1 = ++rowCount;
                selectedInward.setInNo(strYear + "-" + rowCount1);
            }
            selectedInward.setIsreceived(Boolean.FALSE);
            eim.saveNew(selectedInward);
            HibernateUtil.commitTransaction();
            selectedInward = null;
            String message = "Inward added successfully.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            req.execute("DlgConfirmForInward.hide();");
            retrieveExternalInward();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*
     updateExtInwardOfLoggedInEmployee
     */
    public void updateNotReceivedExternalInwards() {
        RequestContext req = null;
        try {
            req = RequestContext.getCurrentInstance();
            HibernateUtil.beginTransaction();
            eim.updateNotReceivedExternalInwards(selectedInward);
            HibernateUtil.commitTransaction();
            selectedInward = null;
            String message = "Inward updated successfully.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            req.execute("DlgConfirmForInward.hide();");
            retrieveExternalInward();

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    public void prepareForUpdateNotReceivedExternalInwards() {
        searchContactForAutocomplete(selectedInward.getIfrom().getContactName());
        searchEmployeeForAutocomplete(selectedInward.getForwardedTo().getFirstname());
    }
}
