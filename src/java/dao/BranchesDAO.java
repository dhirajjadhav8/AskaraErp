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
import org.hibernate.sql.JoinType;
import orm.Branches;
import orm.Organization;
import util.HibernateUtil;
import util.MainIncludeFilesSelect;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "branchesDAO")
@ViewScoped
public class BranchesDAO implements Serializable {

    private List<Branches> allBranches;
    private List<Branches> branchesList;
    private Branches selectedFirstBranch;
    private Branches selectedBranch;
    private Branches selectedBranchUpdate;
    private Organization selectedOrganization;

    public List<Branches> getAllBranches() {
        return allBranches;
    }

    public void setAllBranches(List<Branches> allBranches) {
        this.allBranches = allBranches;
    }

    public List<Branches> getBranchesList() {
        return branchesList;
    }

    public void setBranchesList(List<Branches> branchesList) {
        this.branchesList = branchesList;
    }

    public Branches getSelectedFirstBranch() {
        return selectedFirstBranch;
    }

    public void setSelectedFirstBranch(Branches selectedFirstBranch) {
        this.selectedFirstBranch = selectedFirstBranch;
    }

    public Branches getSelectedBranch() {
        return selectedBranch;
    }

    public void setSelectedBranch(Branches selectedBranch) {
        this.selectedBranch = selectedBranch;
    }

    public Organization getSelectedOrganization() {
        return selectedOrganization;
    }

    public void setSelectedOrganization(Organization selectedOrganization) {
        this.selectedOrganization = selectedOrganization;
    }

    public Branches getSelectedBranchUpdate() {
        return selectedBranchUpdate;
    }

    public void setSelectedBranchUpdate(Branches selectedBranchUpdate) {
        this.selectedBranchUpdate = selectedBranchUpdate;
    }

    /**
     * Creates a new instance of BranchesDAO
     */
    public BranchesDAO() {

        this.allBranches = new ArrayList<Branches>();
        this.branchesList = new ArrayList<Branches>();
        this.selectedFirstBranch = new Branches();
        this.selectedBranch = new Branches();
        this.selectedOrganization = new Organization();
        this.selectedBranchUpdate = new Branches();
    }

    public void retrieveAllBranches() {

        Session session = null;
        Transaction trx = null;

        Criteria cr = null;
        this.allBranches.clear();

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            cr = session.createCriteria(Branches.class, "branches")
                    .createAlias("branches.organization", "organization", JoinType.INNER_JOIN);
            cr.setMaxResults(50);
            this.allBranches.addAll(cr.list());
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

    public List<Branches> completeBranches(String branches) {
        Session session = null;
        Transaction trx = null;
        Query q = null;
        this.branchesList.clear();
        try {
            if (branches != null || !branches.equals("")) {
                this.selectedFirstBranch.setName("Branch already exist");
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                q = session.createQuery("from Branches brc where Lower(brc.name) like Lower('" + branches + "%')");
                if (q.list().isEmpty() == false) {
                    this.branchesList.add(this.getSelectedFirstBranch());
                }
                this.branchesList.addAll(q.list());
                trx.commit();

                return branchesList;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addBranch() {
        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        Query q = null;

        try {
            if (this.selectedOrganization.getId() == 0) {
                String message = text.getString("msg.organizationRequired");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                this.selectedBranch = null;
                this.selectedBranch = new Branches();

            } else if (this.selectedBranch.getName().equals("") || this.selectedBranch.getName() == null) {
                String message = text.getString("ui.msg.Name");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                this.selectedBranch = null;
                this.selectedBranch = new Branches();

            } else {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();


                List<Branches> BranchList = new ArrayList<Branches>();

                q = session.createQuery("from Branches brc where Lower(brc.name) like Lower('" + this.selectedBranch.getName() + "%')");

                BranchList.addAll(q.list());

                if (BranchList.isEmpty() == true) {


                    this.selectedBranch.setOrganization(this.getSelectedOrganization());

                    Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                    Date date = new Date();
                    int id = loginDAO.getLoggedinUser().getEmployees().getId();

                    this.selectedBranch.setCreatedby(id);
                    this.selectedBranch.setCreatedon(new Timestamp(date.getTime()));

                    session.save(this.selectedBranch);



                    this.selectedBranch = null;
                    this.selectedBranch = new Branches();
                    this.selectedOrganization = null;
                    this.selectedOrganization = new Organization();
                    String message = text.getString("ui.Bean.BranchAddSucess");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                } else {
                    this.selectedBranch = null;
                    this.selectedBranch = new Branches();
                    String message = text.getString("ui.Bean.BranchAlreadyExist");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                }
            }
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

    public void onRowSelect() {
        this.setSelectedBranch(this.getSelectedBranchUpdate());
        this.setSelectedOrganization(this.getSelectedBranchUpdate().getOrganization());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);

        mainIncludeFilesSelect.setIncludeFile("updateBranch");

    }

    public void updateBranch() {
        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        Query q = null;

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            this.selectedBranch.setOrganization(this.getSelectedOrganization());
            if (this.selectedOrganization.getId() == 0) {
                String message = text.getString("msg.organizationRequired");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                this.selectedBranch = null;
                this.selectedBranch = new Branches();

            } else if (this.selectedBranch.getName().equals("") || this.selectedBranch.getName() == null) {
                String message = text.getString("ui.msg.Name");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                this.selectedBranch = null;
                this.selectedBranch = new Branches();

            } else {


                session.update(this.selectedBranch);
                String message = text.getString("ui.Bean.BranchUpdateSucess");
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

        this.selectedBranch = null;
        this.selectedBranch = new Branches();

        this.selectedBranchUpdate = null;
        this.selectedBranchUpdate = new Branches();
        this.selectedOrganization = null;
        this.selectedOrganization = new Organization();
    }
}
