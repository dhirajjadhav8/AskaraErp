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
import orm.DepartmentIncharge;
import orm.Departments;
import orm.Employees;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "departmentInchargeDAO")
@ViewScoped
public class DeparmentInchargeDAO implements Serializable {

    private List<Employees> searchEmployees;
    private List<Departments> allDepartments;
    private List<DepartmentIncharge> allDepartmentIncharges;
    private Employees selectedEmployee;
    private Departments selectedDepartment;
    private String strEmployeeSearch;
    private DepartmentIncharge selectedDepartmentIncharge;
    private DepartmentIncharge selectedDepartmentInchargeToupdate;

    public List<DepartmentIncharge> getAllDepartmentIncharges() {
        return allDepartmentIncharges;
    }

    public void setAllDepartmentIncharges(List<DepartmentIncharge> allDepartmentIncharges) {
        this.allDepartmentIncharges = allDepartmentIncharges;
    }

    public List<Departments> getAllDepartments() {
        return allDepartments;
    }

    public void setAllDepartments(List<Departments> allDepartments) {
        this.allDepartments = allDepartments;
    }

    public List<Employees> getSearchEmployees() {
        return searchEmployees;
    }

    public void setSearchEmployees(List<Employees> searchEmployees) {
        this.searchEmployees = searchEmployees;
    }

    public Departments getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Departments selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public DepartmentIncharge getSelectedDepartmentIncharge() {
        return selectedDepartmentIncharge;
    }

    public void setSelectedDepartmentIncharge(DepartmentIncharge selectedDepartmentIncharge) {
        this.selectedDepartmentIncharge = selectedDepartmentIncharge;
    }

    public DepartmentIncharge getSelectedDepartmentInchargeToupdate() {
        return selectedDepartmentInchargeToupdate;
    }

    public void setSelectedDepartmentInchargeToupdate(DepartmentIncharge selectedDepartmentInchargeToupdate) {
        this.selectedDepartmentInchargeToupdate = selectedDepartmentInchargeToupdate;
    }

    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public String getStrEmployeeSearch() {
        return strEmployeeSearch;
    }

    public void setStrEmployeeSearch(String strEmployeeSearch) {
        this.strEmployeeSearch = strEmployeeSearch;
    }

    /**
     * Creates a new instance of DeparmentInchargeDAO
     */
    public DeparmentInchargeDAO() {

        this.searchEmployees = new ArrayList<Employees>();
        this.allDepartments = new ArrayList<Departments>();
        this.allDepartmentIncharges = new ArrayList<DepartmentIncharge>();

        this.selectedEmployee = new Employees();
        this.selectedDepartment = new Departments();
        this.selectedDepartmentIncharge = new DepartmentIncharge();
        this.selectedDepartmentInchargeToupdate = new DepartmentIncharge();

    }

    public void retrieveAllDepartmentIncharge() {
        Session session = null;
        Transaction trx = null;


        Criteria cr = null;
        Query q = null;
        try {

            this.allDepartmentIncharges.clear();

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            cr = session.createCriteria(DepartmentIncharge.class, "depIncharge").createAlias("depIncharge.departments", "department", JoinType.INNER_JOIN).createAlias("depIncharge.employees", "emp", JoinType.INNER_JOIN).addOrder(Order.desc("depIncharge.startDate"));
            this.allDepartmentIncharges.addAll(cr.list());

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

    public void retrieveDepartments() {

        Session session = null;
        Transaction trx = null;



        Criteria cr = null;
        Query q = null;
        try {

            this.allDepartments.clear();

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            //String hqlQuery = "select pro from Projects pro left join pro.projectIncharge proInch with proInch.employees.id =" + this.selectedEmployee.getId() + " and proInch.endDate is null where proInch.id is null";

            String hqlQuery = "select distinct dep from Departments dep where dep.id not in(select depincharge.departments.id from DepartmentIncharge depincharge where depincharge.endDate is null)";

            q = session.createQuery(hqlQuery);

            this.allDepartments.addAll(q.list());

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

    public void addDepartmentIncharge() {

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

            this.selectedDepartmentIncharge.setEmployees(this.selectedEmployee);
            this.selectedDepartmentIncharge.setDepartments(this.selectedDepartment);
            this.selectedDepartmentIncharge.setCreatedby(userId);
            this.selectedDepartmentIncharge.setCreatedon(new Timestamp(date.getTime()));

            session.save(this.selectedDepartmentIncharge);

            trx.commit();

            this.setSelectedDepartmentIncharge(null);
            this.setSelectedDepartmentIncharge(new DepartmentIncharge());

            this.setStrEmployeeSearch(null);
            // this.setSelectedDepartment(null);
            this.setSearchEmployees(null);

            this.retrieveAllDepartmentIncharge();
            this.retrieveDepartments();

            String message = text.getString("ui.Bean.DepInchargAddSuccess");

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

    public void updateDepartmentIncharge() {

        Session session = null;
        Transaction trx = null;
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            Date current_Date = new Date();
            if (this.selectedDepartmentInchargeToupdate.getEndDate() != null && !this.selectedDepartmentInchargeToupdate.getEndDate().equals("")) {

                if (this.selectedDepartmentInchargeToupdate.getEndReason() != null && !this.selectedDepartmentInchargeToupdate.getEndReason().equals("")) {

                    if (this.selectedDepartmentInchargeToupdate.getEndDate().compareTo(this.selectedDepartmentInchargeToupdate.getStartDate()) >= 0) {
                        if (this.selectedDepartmentInchargeToupdate.getEndDate().compareTo(current_Date) <= 0) {

                            session.update(this.selectedDepartmentInchargeToupdate);

                            trx.commit();

                            this.setSelectedDepartmentInchargeToupdate(null);
                            this.setSelectedDepartmentInchargeToupdate(new DepartmentIncharge());

                            this.retrieveAllDepartmentIncharge();
                            this.retrieveDepartments();

                            String message = text.getString("ui.Bean.DepInchargAddSuccess");

                            RequestContext req = RequestContext.getCurrentInstance();

                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                            req.update("growlmsg");
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
                    String message = text.getString("ui.Bean.DepInchargeupdEndReason");

                    RequestContext req = RequestContext.getCurrentInstance();

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                    req.update("growlmsg");

                }

            } else {
                String message = text.getString("ui.Bean.DepInchargeUpdateEndDate");

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
