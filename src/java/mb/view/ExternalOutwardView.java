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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.ContactsManagerImpl;
import manager.impl.EmployeesManagerImpl;
import manager.impl.EoutwardsManagerImpl;
import manager.interfaces.IContactsManager;
import manager.interfaces.IEmployeesManager;
import manager.interfaces.IEoutwardsManager;
import mb.util.session.LoginS;
import org.hibernate.Query;
import org.primefaces.context.RequestContext;
import orm.Contacts;
import orm.Employees;
import orm.Eoutwards;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "externalOutwardView")
@ViewScoped
public class ExternalOutwardView implements Serializable {

    /*
     Loginbean Inject 
     
     */
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;

    /*
     Outwards 
     
     */
    private IEoutwardsManager eom = new EoutwardsManagerImpl();
    private List<Eoutwards> extOutwardList;
    private List<Eoutwards> empExtOutwardList;
    private Eoutwards selectedOutward;
    private Date outwardFrom;
    private Date outwardTo;
    public boolean isReturnable = false;
    private boolean checkQuantity;
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

    public ExternalOutwardView() {
    }

    public IEoutwardsManager getEom() {
        return eom;
    }

    public void setEom(IEoutwardsManager eom) {
        this.eom = eom;
    }

    public List<Eoutwards> getExtOutwardList() {
        return extOutwardList;
    }

    public void setExtOutwardList(List<Eoutwards> extOutwardList) {
        this.extOutwardList = extOutwardList;
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public Date getOutwardFrom() {
        return outwardFrom;
    }

    public void setOutwardFrom(Date outwardFrom) {
        this.outwardFrom = outwardFrom;
    }

    public Date getOutwardTo() {
        return outwardTo;
    }

    public void setOutwardTo(Date outwardTo) {
        this.outwardTo = outwardTo;
    }

    public boolean isIsReturnable() {
        return isReturnable;
    }

    public void setIsReturnable(boolean isReturnable) {
        this.isReturnable = isReturnable;
    }

    public Eoutwards getSelectedOutward() {
        return selectedOutward;
    }

    public void setSelectedOutward(Eoutwards selectedOutward) {
        this.selectedOutward = selectedOutward;
    }

    public boolean isCheckQuantity() {
        return checkQuantity;
    }

    public void setCheckQuantity(boolean checkQuantity) {
        this.checkQuantity = checkQuantity;
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

    public List<Eoutwards> getEmpExtOutwardList() {
        return empExtOutwardList;
    }

    public void setEmpExtOutwardList(List<Eoutwards> empExtOutwardList) {
        this.empExtOutwardList = empExtOutwardList;
    }


    /*
     Retrieve External outward list
     */
    public void retrieveExternalOutward() {
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                HibernateUtil.beginTransaction();
                if (loginbean.isEInwardsOutwardsRolePresent()) {
                    extOutwardList = eom.retrieveLimitedAllExternalOutward(50);
                } else {
                    empExtOutwardList = eom.retrieveLimitedExternalOutwardOfLoggedInEmployee(loginbean.getLoggedinUser().getEmployees().getId(), 50);
                }

                HibernateUtil.commitTransaction();
            }
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*
     Date wise all external outwards list retrieve. 
     */
    public void searchDateWiseAllExtOutward() {
        try {
            HibernateUtil.beginTransaction();
            if (outwardFrom != null && outwardTo != null) {
                if (outwardFrom.compareTo(outwardTo) > 0) {
                    String error = "ToDate should be smaller than FromDate";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
                } else {
                    if (loginbean.isEInwardsOutwardsRolePresent()) {
                        extOutwardList = eom.searchDateWiseAllEoutwards(outwardFrom, outwardTo);
                        HibernateUtil.commitTransaction();
                    } else {
                        empExtOutwardList = eom.searchDateWiseEinwardsOfLoggedOutEmployee(outwardFrom, outwardTo, loginbean.getLoggedinUser().getEmployees().getId());
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
     To Add Outward set selected
     */
    public void clearselectedOutwardToAdd() {
        selectedOutward = null;
        selectedOutward = new Eoutwards();
    }

    /*
     check isReturnable
     */
    public void checkIsReturnable() {
        isReturnable = true;
    }

    public void disableQuantity() {
//        System.out.println("disableQuantity function");
        if (this.selectedOutward.getOtype() == 'I') {
            this.setCheckQuantity(false);
        } else if (this.selectedOutward.getOtype() == 'D') {
            this.setCheckQuantity(true);
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
     Add Outward
     */
    public void addOutward() {
        Query q = null;
        Query q1 = null;
        RequestContext req = null;
        try {
            if (selectedOutward.getOtype() == 'I' && selectedOutward.getQuantity() == 0) {

                String error = "Quantity is required.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));

            } else if (isReturnable == true && selectedOutward.getReturnDate() == null) {
                String error = "Return date is required.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
            } else {
                if (isReturnable == true) {
                    selectedOutward.setIsreturnable('Y');
                } else {
                    selectedOutward.setIsreturnable('Y');
                }
                req = RequestContext.getCurrentInstance();
                HibernateUtil.beginTransaction();
                String sqlQuery = "select YEAR(current_date) from SYSIBM.SYSDUMMY1";
                q = HibernateUtil.getSession().createSQLQuery(sqlQuery);

                String strYear = q.list().get(0).toString();

                String sqlQuery1 = "select count(*) from Eoutwards";
                q1 = HibernateUtil.getSession().createSQLQuery(sqlQuery1);

                String Count = q1.list().get(0).toString();
                int rowCount = Integer.parseInt(Count);

                if (rowCount == 0) {
                    selectedOutward.setOutNo(strYear + "-1");
                }
                if (rowCount > 0) {
                    int rowCount1 = ++rowCount;
                    selectedOutward.setOutNo(strYear + "-" + rowCount1);
                }
                eom.saveNew(selectedOutward);
                HibernateUtil.commitTransaction();
                selectedOutward = null;
                String message = "Outward added successfully.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                req.execute("DlgExternalOutwardConfirm.hide();");
                retrieveExternalOutward();
            }

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*
     Update Outward
     */
    public void updateOutward() {
        RequestContext req = null;
        try {
            if (selectedOutward.getOtype() == 'I' && selectedOutward.getQuantity() == 0) {

                String error = "Quantity is required.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));

            } else if (isReturnable == true && selectedOutward.getReturnDate() == null) {
                String error = "Return date is required.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
            } else {
                if (isReturnable == true) {
                    selectedOutward.setIsreturnable('Y');
                } else {
                    selectedOutward.setIsreturnable('Y');
                }
                req = RequestContext.getCurrentInstance();
                HibernateUtil.beginTransaction();
                eom.updateExternalOutward(selectedOutward);
                HibernateUtil.commitTransaction();
                selectedOutward = null;
                String message = "Outward updated successfully.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                req.execute("DlgExternalOutwardConfirm.hide();");
            }

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void prepareForUpdateNotReceivedExternalOutwards() {
        if (selectedOutward.getIsreturnable() == 'Y') {
            isReturnable = true;
        } else {
            isReturnable = false;
        }
        searchContactForAutocomplete(selectedOutward.getOto().getContactName());
        searchEmployeeForAutocomplete(selectedOutward.getOfrom().getFirstname());
    }
}
