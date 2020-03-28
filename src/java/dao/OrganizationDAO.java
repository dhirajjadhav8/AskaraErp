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
import orm.Organization;
import util.HibernateUtil;
import util.MainIncludeFilesSelect;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "organizationDAO")
@ViewScoped
public class OrganizationDAO implements Serializable {

    private List<Organization> allOrganizations;
    private List<Organization> OrganizationList;
    private Organization selectedOrganization;
    private Organization selectedOrganizationToUpdate;
    private Organization selectedFirstOrganization;

    public List<Organization> getAllOrganizations() {
        return allOrganizations;
    }

    public void setAllOrganizations(List<Organization> allOrganizations) {
        this.allOrganizations = allOrganizations;
    }

    public List<Organization> getOrganizationList() {
        return OrganizationList;
    }

    public void setOrganizationList(List<Organization> OrganizationList) {
        this.OrganizationList = OrganizationList;
    }

    public Organization getSelectedOrganization() {
        return selectedOrganization;
    }

    public void setSelectedOrganization(Organization selectedOrganization) {
        this.selectedOrganization = selectedOrganization;
    }

    public Organization getSelectedFirstOrganization() {
        return selectedFirstOrganization;
    }

    public void setSelectedFirstOrganization(Organization selectedFirstOrganization) {
        this.selectedFirstOrganization = selectedFirstOrganization;
    }

    public Organization getSelectedOrganizationToUpdate() {
        return selectedOrganizationToUpdate;
    }

    public void setSelectedOrganizationToUpdate(Organization selectedOrganizationToUpdate) {
        this.selectedOrganizationToUpdate = selectedOrganizationToUpdate;
    }

    /**
     * Creates a new instance of OrganizationDAO
     */
    public OrganizationDAO() {

        this.allOrganizations = new ArrayList<Organization>();
        this.OrganizationList = new ArrayList<Organization>();
        this.selectedOrganization = new Organization();
        this.selectedFirstOrganization = new Organization();
        this.selectedOrganizationToUpdate = new Organization();
    }

    public void retrieveAllOrganization() {

        Session session = null;
        Transaction trx = null;

        Query q = null;
        this.allOrganizations.clear();

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            String hqlQuery = "from Organization";

            q = session.createQuery(hqlQuery);
            this.allOrganizations.addAll(q.list());

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

    public List<Organization> completeOrganisation(String organization) {
        Session session = null;
        Transaction trx = null;
        Query q = null;
        this.OrganizationList.clear();
        try {
            if (organization != null || !organization.equals("")) {
                this.selectedFirstOrganization.setName("Organization already exist");
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                q = session.createQuery("from Organization org where Lower(org.name) like Lower('" + organization + "%')");
                if (q.list().isEmpty() == false) {
                    this.OrganizationList.add(this.getSelectedFirstOrganization());
                }
                this.OrganizationList.addAll(q.list());
                trx.commit();

                return OrganizationList;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addOrganization() {

        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        Query q = null;

        try {
            if (this.selectedOrganization.getName().equals("") || this.selectedOrganization.getName() == null) {
                String message = text.getString("ui.msg.Name");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                this.selectedOrganization = null;
                this.selectedOrganization = new Organization();
//                RequestContext req = RequestContext.getCurrentInstance();
//                req.update(":frmAddOrganization");

            } else {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                List<Organization> allOrganizationList = new ArrayList<Organization>();

                allOrganizationList.clear();
                q = session.createQuery("from Organization org where Lower(org.name) like Lower('" + this.selectedOrganization.getName() + "%')");
                allOrganizationList.addAll(q.list());

                if (allOrganizationList.isEmpty() == true) {
                    Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                    Date date = new Date();
                    int id = loginDAO.getLoggedinUser().getEmployees().getId();

                    this.selectedOrganization.setCreatedby(id);
                    this.selectedOrganization.setCreatedon(new Timestamp(date.getTime()));

                    session.save(this.getSelectedOrganization());

                    String message = text.getString("ui.Bean.OrganizationAddSucess");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
//                    RequestContext req = RequestContext.getCurrentInstance();
//                    req.update(":frmAddOrganization");
                    RequestContext req = RequestContext.getCurrentInstance();
                    req.update(":frmOrganizationTable");

                    this.selectedOrganization = null;
                    this.selectedOrganization = new Organization();


                } else {
                    String message = text.getString("ui.Bean.OrganizationPresent");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
//                    RequestContext req = RequestContext.getCurrentInstance();
//                    req.update(":frmAddOrganization");

                    RequestContext req = RequestContext.getCurrentInstance();
                    req.update(":frmOrganizationTable");

                    this.selectedOrganization = null;
                    this.selectedOrganization = new Organization();
                }

                trx.commit();
                this.retrieveAllOrganization();
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

    public void onRowSelect() {
        this.setSelectedOrganization(this.getSelectedOrganizationToUpdate());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);

        mainIncludeFilesSelect.setIncludeFile("updateOrganization");

    }

    public void updateOrganization() {

        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        Query q = null;

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            if (this.selectedOrganization.getName().equals("") || this.selectedOrganization.getName() == null) {
                String message = text.getString("ui.msg.Name");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                this.selectedOrganization = null;
                this.selectedOrganization = new Organization();
//                RequestContext req = RequestContext.getCurrentInstance();
//                req.update(":frmAddOrganization");

            } else {


                session.update(this.selectedOrganization);

                String message = text.getString("ui.Bean.OrganizationUpdateSuccess");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            }
            trx.commit();

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

    public void finishUploading() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);

        mainIncludeFilesSelect.setIncludeFile("");

        this.selectedOrganizationToUpdate = null;
        this.selectedOrganizationToUpdate = new Organization();

        this.selectedOrganization = null;
        this.selectedOrganization = new Organization();

    }
}
