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
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import orm.Employees;
import orm.ProjectIncharge;
import orm.Projects;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "projectInchargeDAO")
@ViewScoped
public class ProjectInchargeDAO implements Serializable {

    private List<Employees> searchEmployees;
    private List<Projects> allProjects;
    private List<ProjectIncharge> allProjectIncharges;
    private Employees selectedEmployee;
    private Projects selectedProject;
    private String strEmployeeSearch;
    private ProjectIncharge selectedProjectIncharge;
    private ProjectIncharge selectedProjectInchargeToupdate;

    public List<Projects> getAllProjects() {
        return allProjects;
    }

    public void setAllProjects(List<Projects> allProjects) {
        this.allProjects = allProjects;
    }

    public List<Employees> getSearchEmployees() {
        return searchEmployees;
    }

    public void setSearchEmployees(List<Employees> searchEmployees) {
        this.searchEmployees = searchEmployees;
    }

    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public Projects getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Projects selectedProject) {
        this.selectedProject = selectedProject;
    }

    public ProjectIncharge getSelectedProjectIncharge() {
        return selectedProjectIncharge;
    }

    public void setSelectedProjectIncharge(ProjectIncharge selectedProjectIncharge) {
        this.selectedProjectIncharge = selectedProjectIncharge;
    }

    public String getStrEmployeeSearch() {
        return strEmployeeSearch;
    }

    public void setStrEmployeeSearch(String strEmployeeSearch) {
        this.strEmployeeSearch = strEmployeeSearch;
    }

    public List<ProjectIncharge> getAllProjectIncharges() {
        return allProjectIncharges;
    }

    public void setAllProjectIncharges(List<ProjectIncharge> allProjectIncharges) {
        this.allProjectIncharges = allProjectIncharges;
    }

    public ProjectIncharge getSelectedProjectInchargeToupdate() {
        return selectedProjectInchargeToupdate;
    }

    public void setSelectedProjectInchargeToupdate(ProjectIncharge selectedProjectInchargeToupdate) {
        this.selectedProjectInchargeToupdate = selectedProjectInchargeToupdate;
    }

    /**
     * Creates a new instance of ProjectInchargeDAO
     */
    public ProjectInchargeDAO() {
        this.searchEmployees = new ArrayList<Employees>();
        this.allProjects = new ArrayList<Projects>();
        this.allProjectIncharges = new ArrayList<ProjectIncharge>();

        this.selectedEmployee = new Employees();
        this.selectedProject = new Projects();

        this.selectedProjectIncharge = new ProjectIncharge();
        this.selectedProjectInchargeToupdate = new ProjectIncharge();
    }

    public void retrieveAllProjectIncharge() {
        Session session = null;
        Transaction trx = null;


        Criteria cr = null;
        Query q = null;
        try {


            this.allProjectIncharges.clear();

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            cr = session.createCriteria(ProjectIncharge.class, "proIncharge")
                    .createAlias("proIncharge.projects", "project", JoinType.INNER_JOIN)
                    .createAlias("proIncharge.employees", "emp", JoinType.INNER_JOIN)
                    .addOrder(Order.desc("proIncharge.startDate"));
            this.allProjectIncharges.addAll(cr.list());

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

    public void retrieveEmployeeBySearch() {
        Session session = null;
        Transaction trx = null;

        Criteria cr = null;
        Query q = null;
        this.searchEmployees.clear();

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            if (this.strEmployeeSearch != null && !this.strEmployeeSearch.equals("")) {


                q = session.createQuery("Select distinct employees from Employees employees where Lower(employees.firstname) like '"
                        + strEmployeeSearch.toLowerCase() + "%' and employees.leavingDate is null");

                this.searchEmployees.addAll(q.list());
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

    public void retrieveProjects() {

        Session session = null;
        Transaction trx = null;



        Criteria cr = null;
        Query q = null;
        try {

            this.allProjects.clear();

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            //String hqlQuery = "select pro from Projects pro left join pro.projectIncharge proInch with proInch.employees.id =" + this.selectedEmployee.getId() + " and proInch.endDate is null where proInch.id is null";

            String hqlQuery = "select distinct pro from Projects pro where pro.id not in(select proincharge.projects.id "
                    + "from ProjectIncharge proincharge where proincharge.endDate is null)";

            q = session.createQuery(hqlQuery);

            this.allProjects.addAll(q.list());

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

    public void addProjectIncharge() {

        Session session = null;
        Transaction trx = null;
        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            Date date = new Date();

            FacesContext facesContext = FacesContext.getCurrentInstance();

            ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int userId = loginDAO.getLoggedinUser().getId();

            this.selectedProjectIncharge.setEmployees(this.selectedEmployee);
            this.selectedProjectIncharge.setProjects(this.selectedProject);
            this.selectedProjectIncharge.setCreatedby(userId);
            this.selectedProjectIncharge.setCreatedon(new Timestamp(date.getTime()));

            session.save(this.selectedProjectIncharge);

            trx.commit();

            this.setSelectedProjectIncharge(null);
            this.setSelectedProjectIncharge(new ProjectIncharge());

            this.setStrEmployeeSearch(null);
            // this.setSelectedProject(null);
            this.setSearchEmployees(null);

            this.retrieveAllProjectIncharge();
            this.retrieveProjects();

            String message = text.getString("ui.Bean.ProInchargeAddSuccess");

            RequestContext req = RequestContext.getCurrentInstance();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            req.update("growlmsg");


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

    public void updateProjectIncharge() {

        Session session = null;
        Transaction trx = null;
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            Date current_Date = new Date();

            if (this.selectedProjectInchargeToupdate.getEndDate() != null && !this.selectedProjectInchargeToupdate.getEndDate().equals("")) {

                if (this.selectedProjectInchargeToupdate.getEndReason() != null && !this.selectedProjectInchargeToupdate.getEndReason().equals("")) {

                    if (this.selectedProjectInchargeToupdate.getEndDate().compareTo(this.selectedProjectInchargeToupdate.getStartDate()) >= 0) {
                        if (this.selectedProjectInchargeToupdate.getEndDate().compareTo(current_Date) <= 0) {

                            session.update(this.selectedProjectInchargeToupdate);

                            trx.commit();

                            String message = text.getString("ui.Bean.ProInchargAddSuccess");

                            RequestContext req = RequestContext.getCurrentInstance();

                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                            req.update("growlmsg");

                            this.setSelectedProjectInchargeToupdate(null);
                            this.setSelectedProjectInchargeToupdate(new ProjectIncharge());

                            this.retrieveAllProjectIncharge();
                            this.retrieveProjects();
                        } else {
                            String message = text.getString("ui.Bean.EnddatelessthanCurrentDate");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                            RequestContext req = RequestContext.getCurrentInstance();
                            req.update("growlmsg");
                        }
                    } else {
                        String message = text.getString("ui.Bean.EnddateGreterThanStartDate");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                        RequestContext req = RequestContext.getCurrentInstance();
                        req.update("growlmsg");
                    }

                } else {
                    String message = text.getString("ui.Bean.ProInchargeupdEndReason");

                    RequestContext req = RequestContext.getCurrentInstance();

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                    req.update("growlmsg");

                }

            } else {
                String message = text.getString("ui.Bean.ProInchargeUpdateEndDate");

                RequestContext req = RequestContext.getCurrentInstance();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                req.update("growlmsg");

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
