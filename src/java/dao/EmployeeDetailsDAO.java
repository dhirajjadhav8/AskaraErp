/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import orm.*;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "employeeDetailsDAO")
@ViewScoped
public class EmployeeDetailsDAO implements Serializable {

    private Locations selectedLocation;
    private Projects selectedProject;
    private Designations selectedDesignation;
    private Departments selectedDepartments;
    private EmployeeDetails employeeDetails;
    private EmployeeDetails selectedEmpDetails;
    private List<Employees> allEmployees;
    private List<Locations> allLocations;
    private List<Projects> allProjects;
    private List<Designations> allDesignations;
    private List<Departments> allDepartments;
    private List<EmployeeDetails> allEmployeeDetails;
    private List<EmployeeDetails> existingEmpDetails;

    /**
     * Creates a new instance of EmployeeDetailsDAO
     */
    public EmployeeDetailsDAO() {

        this.allEmployees = new ArrayList<Employees>();
        this.allLocations = new ArrayList<Locations>();
        this.allProjects = new ArrayList<Projects>();
        this.allDesignations = new ArrayList<Designations>();
        this.allDepartments = new ArrayList<Departments>();
        this.allEmployeeDetails = new ArrayList<EmployeeDetails>();
        this.existingEmpDetails = new ArrayList<EmployeeDetails>();


        this.selectedLocation = new Locations();
        this.selectedProject = new Projects();
        this.selectedDesignation = new Designations();
        this.selectedDepartments = new Departments();
        this.employeeDetails = new EmployeeDetails();
        this.selectedEmpDetails = new EmployeeDetails();

        System.out.println(" New EmployeeDetailsDAO is created......");



    }

    public List<Employees> getAllEmployees() {
        return allEmployees;
    }

    public void setAllEmployees(List<Employees> allEmployees) {
        this.allEmployees = allEmployees;
    }

    public List<Locations> getAllLocations() {
        return allLocations;
    }

    public void setAllLocations(List<Locations> allLocations) {
        this.allLocations = allLocations;
    }

    public int getSelectedLocation() {
        return selectedLocation.getId();
    }

    public void setSelectedLocation(int selectedLocation) {
        this.selectedLocation.setId(selectedLocation);
    }

    public List<Departments> getAllDepartments() {
        return allDepartments;
    }

    public void setAllDepartments(List<Departments> allDepartments) {
        this.allDepartments = allDepartments;
    }

    public List<Designations> getAllDesignations() {
        return allDesignations;
    }

    public void setAllDesignations(List<Designations> allDesignations) {
        this.allDesignations = allDesignations;
    }

    public List<Projects> getAllProjects() {
        return allProjects;
    }

    public void setAllProjects(List<Projects> allProjects) {
        this.allProjects = allProjects;
    }

    public int getSelectedDepartments() {
        return selectedDepartments.getId();
    }

    public void setSelectedDepartments(int selectedDepartments) {
        this.selectedDepartments.setId(selectedDepartments);
    }

    public int getSelectedDesignation() {
        return selectedDesignation.getId();
    }

    public void setSelectedDesignation(int selectedDesignation) {
        this.selectedDesignation.setId(selectedDesignation);
    }

    public int getSelectedProject() {
        return selectedProject.getId();
    }

    public void setSelectedProject(int selectedProject) {
        this.selectedProject.setId(selectedProject);
    }

    public List<EmployeeDetails> getAllEmployeeDetails() {
        return allEmployeeDetails;
    }

    public void setAllEmployeeDetails(List<EmployeeDetails> allEmployeeDetails) {
        this.allEmployeeDetails = allEmployeeDetails;
    }

    public EmployeeDetails getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(EmployeeDetails employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

    public EmployeeDetails getSelectedEmpDetails() {
        return selectedEmpDetails;
    }

    public void setSelectedEmpDetails(EmployeeDetails selectedEmpDetails) {
        this.selectedEmpDetails = selectedEmpDetails;
    }

    public List<EmployeeDetails> getExistingEmpDetails() {
        return existingEmpDetails;
    }

    public void setExistingEmpDetails(List<EmployeeDetails> existingEmpDetails) {
        this.existingEmpDetails = existingEmpDetails;
    }

    // for getting employee list.
    public void retrieveEmployee() {

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

    // for update the perticular employee details through this method
    public void retrieveEmpDetailsforAdd() {

        System.out.println("I am in retrieveEmployeeDetails 127127127127127127127127");

        Session session = null;
        Transaction trx = null;
        Transaction trx1 = null;
        Transaction trx2 = null;
        Transaction trx3 = null;
        Transaction trx4 = null;




        Query q = null;
        Query q1 = null;
        Query q2 = null;
        Query q3 = null;
        Query q4 = null;

        Criteria cr = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());


        try {
           
                this.allLocations.clear();
                this.allProjects.clear();
                this.allDesignations.clear();
                this.allDepartments.clear();
                this.allEmployeeDetails.clear();


                FacesContext facesContext = FacesContext.getCurrentInstance();
                EmployeesDAO employeesDAO = (EmployeesDAO) facesContext.getApplication().createValueBinding("#{employeesDAO}").getValue(facesContext);



                int id = employeesDAO.getSelectedEmployee().getId();



                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                q = session.createQuery("select loc from Locations loc ");

                this.allLocations.addAll(q.list());

                trx.commit();


                trx1 = session.beginTransaction();

                q1 = session.createQuery("select proj from Projects proj ");

                this.allProjects.addAll(q1.list());

                trx1.commit();


                trx2 = session.beginTransaction();

                q2 = session.createQuery("select desig from Designations desig ");

                this.allDesignations.addAll(q2.list());

                trx2.commit();


                trx3 = session.beginTransaction();

                q3 = session.createQuery("select  dept from Departments dept");

                this.allDepartments.addAll(q3.list());

                trx3.commit();


                trx4 = session.beginTransaction();

                q4 = session.createQuery("select  empDetails "
                        + "from EmployeeDetails empDetails  "
                        + "inner join empDetails.projects proj "
                        + "inner join empDetails.locations loc "
                        + "inner join empDetails.designations desig "
                        + "inner join empDetails.departments  dept "
                        + "where empDetails.employees.id=" + id);

                cr = session.createCriteria(EmployeeDetails.class, "edetails").createAlias("projects", "pro", JoinType.INNER_JOIN).createAlias("locations", "loc", JoinType.INNER_JOIN).createAlias("designations", "desig", JoinType.INNER_JOIN).createAlias("departments", "dep", JoinType.INNER_JOIN).setFetchMode("edetails.employees", FetchMode.JOIN).add(Restrictions.eq("edetails.employees.id", id)).addOrder(Order.desc("edetails.startDate"));


                this.allEmployeeDetails.addAll(cr.list());

                trx4.commit();

            

        } catch (Exception e) {

            trx.rollback();
            trx1.rollback();
            trx2.rollback();
            trx3.rollback();

            if (this.allLocations == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            }
            if (this.allProjects == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            }
            if (this.allDesignations == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            }
            if (this.allDepartments == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            }


        } finally {

            if (q != null) {
                q = null;
            }
            if (q1 != null) {
                q1 = null;
            }
            if (q2 != null) {
                q2 = null;
            }
            if (q3 != null) {
                q3 = null;
            }
            if (trx != null) {
                trx = null;
            }
            if (trx1 != null) {
                trx1 = null;
            }
            if (trx2 != null) {
                trx2 = null;
            }
            if (trx3 != null) {
                trx3 = null;
            }
            if (session != null) {
                session.clear();
                session.close();
                session = null;
            }


        }



    }

    // for getting location list
    public void retrieveEmployeeDetailsforAdd() {

        Session session = null;
        Transaction trx = null;
        Transaction trx1 = null;
        Transaction trx2 = null;
        Transaction trx3 = null;
        Transaction trx4 = null;




        Query q = null;
        Query q1 = null;
        Query q2 = null;
        Query q3 = null;
        Query q4 = null;

        Criteria cr = null;


        this.allLocations.clear();
        this.allProjects.clear();
        this.allDesignations.clear();
        this.allDepartments.clear();
        this.allEmployeeDetails.clear();


        FacesContext facesContext = FacesContext.getCurrentInstance();
        EmployeesDAO employeesDAO = (EmployeesDAO) facesContext.getApplication().createValueBinding("#{employeesDAO}").getValue(facesContext);


        int id = employeesDAO.getSelectedEmployee().getId();

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            q = session.createQuery("select loc from Locations loc ");

            this.allLocations.addAll(q.list());

            trx.commit();


            trx1 = session.beginTransaction();

            q1 = session.createQuery("select proj from Projects proj ");

            this.allProjects.addAll(q1.list());

            trx1.commit();


            trx2 = session.beginTransaction();

            q2 = session.createQuery("select desig from Designations desig ");

            this.allDesignations.addAll(q2.list());

            trx2.commit();


            trx3 = session.beginTransaction();

            q3 = session.createQuery("select  dept from Departments dept");

            this.allDepartments.addAll(q3.list());

            trx3.commit();


            trx4 = session.beginTransaction();

            q4 = session.createQuery("select  empDetails "
                    + "from EmployeeDetails empDetails  "
                    + "inner join empDetails.projects proj "
                    + "inner join empDetails.locations loc "
                    + "inner join empDetails.designations desig "
                    + "inner join empDetails.departments  dept "
                    + "where empDetails.employees.id=" + id);

            cr = session.createCriteria(EmployeeDetails.class, "edetails").createAlias("projects", "pro", JoinType.INNER_JOIN).createAlias("locations", "loc", JoinType.INNER_JOIN).createAlias("designations", "desig", JoinType.INNER_JOIN).createAlias("departments", "dep", JoinType.INNER_JOIN).setFetchMode("edetails.employees", FetchMode.JOIN).add(Restrictions.eq("edetails.employees.id", id)).addOrder(Order.desc("edetails.startDate"));


            this.allEmployeeDetails.addAll(cr.list());

            trx4.commit();

            RequestContext.getCurrentInstance().update("dialogforupdate");

        } catch (Exception e) {

            trx.rollback();
            trx1.rollback();
            trx2.rollback();
            trx3.rollback();

            if (this.allLocations == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            }
            if (this.allProjects == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            }
            if (this.allDesignations == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            }
            if (this.allDepartments == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            }


        } finally {

            if (q != null) {
                q = null;
            }
            if (q1 != null) {
                q1 = null;
            }
            if (q2 != null) {
                q2 = null;
            }
            if (q3 != null) {
                q3 = null;
            }
            if (trx != null) {
                trx = null;
            }
            if (trx1 != null) {
                trx1 = null;
            }
            if (trx2 != null) {
                trx2 = null;
            }
            if (trx3 != null) {
                trx3 = null;
            }
            if (session != null) {
                session.clear();
                session.close();
                session = null;
            }


        }



    }

    // for adding employee details.
    public void addEmployeeDetails() {

        Session session = null;
        Transaction trx = null;
        Transaction trx1 = null;

        Query q = null;

        this.existingEmpDetails.clear();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        EmployeesDAO employeesDAO = (EmployeesDAO) facesContext.getApplication().createValueBinding("#{employeesDAO}").getValue(facesContext);


        this.employeeDetails.setEmployees(employeesDAO.getSelectedEmployee());
        this.employeeDetails.setLocations(selectedLocation);
        this.employeeDetails.setDepartments(selectedDepartments);
        this.employeeDetails.setDesignations(selectedDesignation);
        this.employeeDetails.setProjects(selectedProject);

        RequestContext req = RequestContext.getCurrentInstance();

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {



            if (this.employeeDetails.getEmployees() == null || this.employeeDetails.getLocations() == null || this.employeeDetails.getDesignations() == null || this.employeeDetails.getDepartments() == null) {



                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.ReuiredFields"), text.getString("ui.Bean.ReuiredFields")));

                req.update("messages");
                req.update("frmupdate");
                req.update("pnlMain");
            } else {
                session = HibernateUtil.getSession();


                trx1 = session.beginTransaction();


                q = session.createQuery("from EmployeeDetails edtl where edtl.projects.id=" + this.selectedProject.getId() + " and edtl.locations.id=" + this.selectedLocation.getId() + " and edtl.designations.id=" + this.selectedDesignation.getId() + " and edtl.departments.id =" + this.selectedDepartments.getId() + " and edtl.employees.id=" + employeesDAO.getSelectedEmployee().getId() + " and edtl.endDate is null");

                this.existingEmpDetails.addAll(q.list());

                trx1.commit();

                if (this.existingEmpDetails.isEmpty()) {

                    trx = session.beginTransaction();

                    Date date = new Date();

                    Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                    int userId = loginDAO.getLoggedinUser().getId();

                    this.employeeDetails.setCreatedby(userId);
                    this.employeeDetails.setCreatedon(new Timestamp(date.getTime()));

                    session.save(this.employeeDetails);
                    trx.commit();

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text.getString("ui.Bean.EmpDetailsAddSuccss")));

                    this.setSelectedProject(0);
                    this.setSelectedDepartments(0);
                    this.setSelectedDesignation(0);
                    this.setSelectedEmpDetails(null);
                    this.setSelectedEmpDetails(new EmployeeDetails());
                    this.setSelectedLocation(0);
                    
                    this.retrieveEmpDetailsforAdd();

                    // RequestContext req = RequestContext.getCurrentInstance();
                    req.update("messages");
                    req.update("frmupdate");
                    req.update("pnlMain");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text.getString("ui.Bean.EmpDetailsAlreadyExist")));

                    //RequestContext req = RequestContext.getCurrentInstance();
                    req.update("messages");
                    req.update("frmupdate");
                    req.update("pnlMain");
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            trx.rollback();

            if (this.employeeDetails == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
                // RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");
                req.update("frmupdate");
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

    public void update(ActionEvent actionEvent) {
        this.updateEmployeeDetails();
    }

    // for update employee Details.
    public void updateEmployeeDetails() {

        Session session = null;
        Transaction trx = null;
        RequestContext req = RequestContext.getCurrentInstance();

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            if (this.selectedEmpDetails == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));

                //RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");
                req.update("frmupdate");
                return;
            }

            if (this.selectedEmpDetails.getEndDate() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.EndDate"), text.getString("ui.Bean.EndDate")));

                // RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");
                req.update("frmupdate");
                return;
            }

            if (this.selectedEmpDetails.getEndReason() == null || "".equals(this.selectedEmpDetails.getEndReason())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.EndReason"), text.getString("ui.Bean.EndReason")));

                // RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");
                req.update("frmupdate");
                return;
            } else {


                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                session.saveOrUpdate(this.selectedEmpDetails);
                trx.commit();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text.getString("ui.Bean.EmpDetailsUpdateSucess")));

                // RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");
                req.update("frmupdate");
            }
            req.update("pnlMain");

        } catch (Exception e) {
            trx.rollback();
            System.out.println(e.getMessage());

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
