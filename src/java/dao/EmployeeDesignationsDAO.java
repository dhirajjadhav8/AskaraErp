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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import orm.Designations;
import orm.EmployeeDesignations;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "employeeDesignationsDAO")
@ViewScoped
public class EmployeeDesignationsDAO implements Serializable {

    private List<EmployeeDesignations> designationToAdd;
    private List<EmployeeDesignations> assignedDesignations;
    private List<EmployeeDesignations> designationHistory;
    private EmployeeDesignations selectedEmployeeDesignationToAdd;
    private EmployeeDesignations selectedEmployeeDesignationToUpdate;

    /**
     * Creates a new instance of EmployeeDesignationsDAO
     */
    public EmployeeDesignationsDAO() {
        this.assignedDesignations = new ArrayList<EmployeeDesignations>();
        //this.designationToAdd = new ArrayList<EmployeeDesignations>();
        this.designationHistory = new ArrayList<EmployeeDesignations>();

        //this.selectedEmployeeDesignationToAdd = new EmployeeDesignations();
        this.selectedEmployeeDesignationToUpdate = new EmployeeDesignations();

        System.out.println(" New EmployeeDesignationsDAO is created......");


    }

    public List<EmployeeDesignations> getAssignedDesignations() {
        return assignedDesignations;
    }

    public void setAssignedDesignations(List<EmployeeDesignations> assignedDesignations) {
        this.assignedDesignations = assignedDesignations;
    }

    public List<EmployeeDesignations> getDesignationHistory() {
        return designationHistory;
    }

    public void setDesignationHistory(List<EmployeeDesignations> designationHistory) {
        this.designationHistory = designationHistory;
    }

    public List<EmployeeDesignations> getDesignationToAdd() {
        return designationToAdd;
    }

    public void setDesignationToAdd(List<EmployeeDesignations> designationToAdd) {
        this.designationToAdd = designationToAdd;
    }

    public EmployeeDesignations getSelectedEmployeeDesignationToAdd() {
        return selectedEmployeeDesignationToAdd;
    }

    public void setSelectedEmployeeDesignationToAdd(EmployeeDesignations selectedEmployeeDesignationToAdd) {
        this.selectedEmployeeDesignationToAdd = selectedEmployeeDesignationToAdd;
    }

    public EmployeeDesignations getSelectedEmployeeDesignationToUpdate() {
        return selectedEmployeeDesignationToUpdate;
    }

    public void setSelectedEmployeeDesignationToUpdate(EmployeeDesignations selectedEmployeeDesignationToUpdate) {

        this.selectedEmployeeDesignationToUpdate = selectedEmployeeDesignationToUpdate;
    }

    public void retrieveEmployeeDesignationDetails() {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        EmployeesDAO employeesDAO = (EmployeesDAO) facesContext.getApplication().createValueBinding("#{employeesDAO}").getValue(facesContext);

        Session session = null;
        Transaction trx = null;

        Query q = null;
        Criteria cr = null;



        try {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                this.assignedDesignations.clear();
                //this.designationToAdd.clear();
                this.setDesignationToAdd(null);
                this.setDesignationToAdd(new ArrayList<EmployeeDesignations>());
                this.designationHistory.clear();



                int employeeId = employeesDAO.getSelectedEmployee().getId();
                //int employeeId =1;
                String hqlQuery = "select desig from Designations desig left join desig.employeeDesignations empdesig with empdesig.employees.id=" + employeeId + " and empdesig.endDate is null where empdesig.id is null";

                q = session.createQuery(hqlQuery);


                EmployeeDesignations eDesig = null;
                List<Designations> designation = new ArrayList<Designations>();
                List<EmployeeDesignations> empDesignations = new ArrayList<EmployeeDesignations>();
                designation.addAll(q.list());

                for (int i = 0; i < designation.size(); i++) {

                    Designations des = (Designations) designation.get(i);

                    eDesig = new EmployeeDesignations();
                    eDesig.setDesignations(des);

                    empDesignations.add(eDesig);
                    eDesig = null;

                }

                this.setDesignationToAdd(empDesignations);

                designation.clear();

                cr = session.createCriteria(EmployeeDesignations.class, "eDesig").createAlias("eDesig.designations", "desig", JoinType.INNER_JOIN, Restrictions.isNull("eDesig.endDate")).setFetchMode("eDesig.designations", FetchMode.JOIN).add(Restrictions.eq("eDesig.employees.id", employeeId));

                this.assignedDesignations.addAll(cr.list());


                cr = null;

                cr = session.createCriteria(EmployeeDesignations.class, "eDesig").createAlias("eDesig.designations", "desig", JoinType.INNER_JOIN).createAlias("eDesig.employees", "emp", JoinType.INNER_JOIN, Restrictions.eq("emp.id", employeeId)).add(Restrictions.isNotNull("eDesig.endDate")).addOrder(Order.desc("eDesig.startDate"));

                this.designationHistory.addAll(cr.list());

                trx.commit();

            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        } finally {

            if (cr != null) {
                cr = null;
            }
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

    public void addDesignationToEmployee() {

        Session session = null;
        Transaction trx = null;
        Date date = new Date();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        EmployeesDAO employeesDAO = (EmployeesDAO) facesContext.getApplication().createValueBinding("#{employeesDAO}").getValue(facesContext);

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());


        try {

            if (this.selectedEmployeeDesignationToAdd == null) {

                String message = text.getString("ui.Bean.selectDesignationAdd");


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsg");

            } else if (this.selectedEmployeeDesignationToAdd.getStartDate() == null) {

                String message = text.getString("ui.Bean.StartDate");


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsg");

            } else {

                this.selectedEmployeeDesignationToAdd.setEmployees(employeesDAO.getSelectedEmployee());

                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                int userId = loginDAO.getLoggedinUser().getId();

                this.selectedEmployeeDesignationToAdd.setCreatedby(userId);
                this.selectedEmployeeDesignationToAdd.setCreatedon(new Timestamp(date.getTime()));

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                session.save(this.selectedEmployeeDesignationToAdd);

                trx.commit();

                //this.setSelectedEmployeeDesignationToAdd(null);

                this.retrieveEmployeeDesignationDetails();

                String message = text.getString("ui.Bean.DesigAddSuccess");

                RequestContext req = RequestContext.getCurrentInstance();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                req.update("growlmsg");
            }

        } catch (Exception e) {

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

    public void updateEmployeeDesignations() {
        Session session = null;
        Transaction trx = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        EmployeesDAO employeesDAO = (EmployeesDAO) facesContext.getApplication().createValueBinding("#{employeesDAO}").getValue(facesContext);

        Date date = new Date();
        try {
            if (this.selectedEmployeeDesignationToUpdate == null) {

                String message = text.getString("ui.Bean.selectDesignationUpdate");


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsg");

            } else if (this.selectedEmployeeDesignationToUpdate.getEndDate() == null) {

                String message = text.getString("ui.Bean.EndDate");


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsg");

            } else if (this.selectedEmployeeDesignationToUpdate.getEndReason() == null) {

                String message = text.getString("ui.Bean.EndReason");


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsg");

            } else {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();


                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                int userId = loginDAO.getLoggedinUser().getId();
                this.selectedEmployeeDesignationToUpdate.setEmployees(employeesDAO.getSelectedEmployee());
                this.selectedEmployeeDesignationToUpdate.setCreatedby(userId);
                this.selectedEmployeeDesignationToUpdate.setCreatedon(new Timestamp(date.getTime()));

                session.update(this.selectedEmployeeDesignationToUpdate);
                trx.commit();

                // this.setSelectedEmployeeDesignationToAdd(null);

                this.retrieveEmployeeDesignationDetails();

                String message = "Designation updated successfuly";

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
