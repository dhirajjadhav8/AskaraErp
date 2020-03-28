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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import orm.Employees;
import orm.Properties;
import orm.PropertyIssue;
import orm.Unites;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "issuedPropertyDAO")
@ViewScoped
public class IssuedProperty implements Serializable {

    private List<Employees> allEmployees;
    private List<Properties> allProperties;
    private List<Unites> allUnites;
    private PropertyIssue issuedEmpProperty;
    private Employees selectedEmployee;
    private Properties selectedProperty;
    private Unites selectedUnit;
    private List<IssuedProperty> issuedEmployeeProperty;

    /**
     * Creates a new instance of IssuedProperty
     */
    public IssuedProperty() {
        this.allEmployees = new ArrayList<Employees>();
        this.allProperties = new ArrayList<Properties>();
        this.allUnites = new ArrayList<Unites>();

        this.selectedEmployee = new Employees();
        this.selectedProperty = new Properties();
        this.issuedEmpProperty = new PropertyIssue();
        this.selectedUnit = new Unites();
        this.issuedEmployeeProperty = new ArrayList<IssuedProperty>();

        this.retrieveAllEmployees();
        this.retrieveAllProperties();
        this.retrieveAllUnites();

        System.out.println(" New IssuedProperty is created......");
    }

    public List<Employees> getAllEmployees() {
        return allEmployees;
    }

    public void setAllEmployees(List<Employees> allEmployees) {
        this.allEmployees = allEmployees;
    }

    public List<Properties> getAllProperties() {
        return allProperties;
    }

    public void setAllProperties(List<Properties> allProperties) {
        this.allProperties = allProperties;
    }

    public List<Unites> getAllUnites() {
        return allUnites;
    }

    public void setAllUnites(List<Unites> allUnites) {
        this.allUnites = allUnites;
    }

    public int getSelectedEmployee() {
        return selectedEmployee.getId();
    }

    public void setSelectedEmployee(int selectedEmployee) {
        this.selectedEmployee.setId(selectedEmployee);
    }

    public int getSelectedProperty() {
        return selectedProperty.getId();
    }

    public void setSelectedProperty(int selectedProperty) {
        this.selectedProperty.setId(selectedProperty);
    }

    public PropertyIssue getIssuedEmpProperty() {
        return issuedEmpProperty;
    }

    public void setIssuedEmpProperty(PropertyIssue issuedEmpProperty) {
        this.issuedEmpProperty = issuedEmpProperty;
    }

    public int getSelectedUnit() {
        return selectedUnit.getId();
    }

    public void setSelectedUnit(int selectedUnit) {
        this.selectedUnit.setId(selectedUnit);
    }

    public List<IssuedProperty> getIssuedEmployeeProperty() {
        return issuedEmployeeProperty;
    }

    public void setIssuedEmployeeProperty(List<IssuedProperty> issuedEmployeeProperty) {
        this.issuedEmployeeProperty = issuedEmployeeProperty;
    }

    public void retrieveAllEmployees() {

        Session session = null;
        Transaction trx = null;

        Query q = null;

        this.allEmployees.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            q = session.createQuery("select emp from Employees emp");

            this.allEmployees.addAll(q.list());
            trx.commit();

        } catch (Exception e) {
            trx.rollback();
            if (this.allEmployees == null) {
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

    public void retrieveAllProperties() {
        Session session = null;
        Transaction trx = null;

        Query q = null;

        this.allProperties.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            q = session.createQuery("select pro from Properties pro");

            this.allProperties.addAll(q.list());
            trx.commit();

        } catch (Exception e) {
            trx.rollback();
            if (this.allProperties == null) {
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

    public void retrieveAllUnites() {

        Session session = null;
        Transaction trx = null;

        this.allUnites.clear();
        Query q = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            q = session.createQuery("select uni from Unites uni");

            this.allUnites.addAll(q.list());
            trx.commit();

        } catch (Exception e) {
            trx.rollback();
            if (this.allUnites == null) {
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

    public void addIssuedProperty() {

        Session session = null;
        Transaction trx = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            this.issuedEmpProperty.setEmployees(this.selectedEmployee);
            this.issuedEmpProperty.setProperties(this.selectedProperty);
            this.issuedEmpProperty.setUnites(this.selectedUnit);
            session.save(this.issuedEmpProperty);
            trx.commit();
            RequestContext req = RequestContext.getCurrentInstance();
            //req.update("mainpnlforEmpProperty");

            this.retrieveIssuedPropertyByEmployee();
            this.setSelectedProperty(0);
            this.setSelectedUnit(0);
            this.issuedEmpProperty.setComments(null);
            this.issuedEmpProperty.setQuantity(null);

            req.update(":frIssueEmpProperty:mainpnlforEmpProperty:addEmpProperty");
            FacesMessage message = new FacesMessage(text.getString("ui.Bean.PropertyIssueSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            RequestContext.getCurrentInstance().reset(":frIssueEmpProperty:addEmpProperty");

        } catch (Exception e) {
            trx.rollback();
            System.out.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            if (this.issuedEmpProperty == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            }
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

    public void retrieveIssuedPropertyByEmployee() {
        Session session = null;
        Transaction trx = null;

        this.issuedEmployeeProperty.clear();
        Query q = null;
        Criteria cr = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            int id = this.getSelectedEmployee();

            //q = session.createQuery("select proissue from  PropertyIssue proissue inner join  proissue.properties as pro where proissue.employees.id="+id);

            cr = session.createCriteria(PropertyIssue.class, "issuePro").createAlias("properties", "pro", JoinType.INNER_JOIN).setFetchMode("issuePro.properties", FetchMode.JOIN).createAlias("employees", "emp", JoinType.INNER_JOIN).setFetchMode("issuePro.employees", FetchMode.JOIN).createAlias("unites", "uni", JoinType.INNER_JOIN).setFetchMode("issuePro.unites", FetchMode.JOIN).add(Restrictions.eq("issuePro.employees.id", id));

            this.issuedEmployeeProperty.addAll(cr.list());
            trx.commit();

        } catch (Exception e) {
            if (this.issuedEmployeeProperty == null) {
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
}
