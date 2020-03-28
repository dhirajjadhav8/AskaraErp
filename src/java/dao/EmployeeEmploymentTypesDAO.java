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
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import orm.EmployeeEmpntTypes;
import orm.Employees;
import orm.EmpntTypes;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "employeeEmploymentTypesDAO")
@ViewScoped
public class EmployeeEmploymentTypesDAO implements Serializable {

    private List<Employees> employeesList;
    private List<EmployeeEmpntTypes> employeeEmploymentestoAdd;
    private List<EmployeeEmpntTypes> employeeEmploymentestoUpdate;
    private List<EmployeeEmpntTypes> employeeEmploymentesHistory;
    private Employees selectedEmployee;
    private EmployeeEmpntTypes selectedEmployeeEmploymenttoAdd;
    private EmployeeEmpntTypes selectedEmployeeEmploymenttoUpdate;

    public List<EmployeeEmpntTypes> getEmployeeEmploymentesHistory() {
        return employeeEmploymentesHistory;
    }

    public void setEmployeeEmploymentesHistory(List<EmployeeEmpntTypes> employeeEmploymentesHistory) {
        this.employeeEmploymentesHistory = employeeEmploymentesHistory;
    }

    public List<EmployeeEmpntTypes> getEmployeeEmploymentestoAdd() {
        return employeeEmploymentestoAdd;
    }

    public void setEmployeeEmploymentestoAdd(List<EmployeeEmpntTypes> employeeEmploymentestoAdd) {
        this.employeeEmploymentestoAdd = employeeEmploymentestoAdd;
    }

    public List<EmployeeEmpntTypes> getEmployeeEmploymentestoUpdate() {
        return employeeEmploymentestoUpdate;
    }

    public void setEmployeeEmploymentestoUpdate(List<EmployeeEmpntTypes> employeeEmploymentestoUpdate) {
        this.employeeEmploymentestoUpdate = employeeEmploymentestoUpdate;
    }

    public List<Employees> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employees> employeesList) {
        this.employeesList = employeesList;
    }

    public EmployeeEmpntTypes getSelectedEmployeeEmploymenttoAdd() {
        return selectedEmployeeEmploymenttoAdd;
    }

    public void setSelectedEmployeeEmploymenttoAdd(EmployeeEmpntTypes selectedEmployeeEmploymenttoAdd) {
        this.selectedEmployeeEmploymenttoAdd = selectedEmployeeEmploymenttoAdd;
    }

    public EmployeeEmpntTypes getSelectedEmployeeEmploymenttoUpdate() {
        return selectedEmployeeEmploymenttoUpdate;
    }

    public void setSelectedEmployeeEmploymenttoUpdate(EmployeeEmpntTypes selectedEmployeeEmploymenttoUpdate) {
        this.selectedEmployeeEmploymenttoUpdate = selectedEmployeeEmploymenttoUpdate;
    }

    public int getSelectedEmployee() {
        return selectedEmployee.getId();
    }

    public void setSelectedEmployee(int selectedEmployee) {
        this.selectedEmployee.setId(selectedEmployee);
    }

    /**
     * Creates a new instance of EmployeeEmploymentTypesDAO
     */
    public EmployeeEmploymentTypesDAO() {

        this.selectedEmployee = new Employees();
        this.selectedEmployeeEmploymenttoUpdate = new EmployeeEmpntTypes();
        this.selectedEmployeeEmploymenttoAdd = new EmployeeEmpntTypes();

        this.employeeEmploymentestoAdd = new ArrayList<EmployeeEmpntTypes>();
        this.employeeEmploymentestoUpdate = new ArrayList<EmployeeEmpntTypes>();
        this.employeeEmploymentesHistory = new ArrayList<EmployeeEmpntTypes>();
        this.employeesList = new ArrayList<Employees>();

        this.retrieveEmployeeList();
        
         System.out.println(" New EmployeeEmploymentTypesDAO is created......");

    }

    public void retrieveEmployeeList() {

        Session session = null;
        Transaction trx = null;

        Query q = null;
        this.employeesList.clear();

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            String hqlQuery = "from Employees";

            q = session.createQuery(hqlQuery);

            this.employeesList.addAll(q.list());

            trx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    public void retrieveEmployeeEmploymentDetails() {

        Session session = null;
        Transaction trx = null;


        Query q = null;
        Criteria cr = null;

        int id = this.getSelectedEmployee();
       
        this.employeeEmploymentestoAdd.clear();
        this.employeeEmploymentestoUpdate.clear();
        this.employeeEmploymentesHistory.clear();


        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            String hqlQuery = "select empl from EmpntTypes empl left join empl.employeeEmploymenttypes empEmpl with empEmpl.employees.id =" + id + "  and empEmpl.endDate is null where empEmpl.id is null";

            q = session.createQuery(hqlQuery);

            EmployeeEmpntTypes empEmploy = null;

            List<EmpntTypes> empl = new ArrayList<EmpntTypes>();

            empl.addAll(q.list());

            for (int i = 0; i < empl.size(); i++) {

                EmpntTypes employment = (EmpntTypes) empl.get(i);

                empEmploy = new EmployeeEmpntTypes();

                empEmploy.setEmpnttypes(employment);

                this.employeeEmploymentestoAdd.add(empEmploy);
            }

            trx.commit();

            trx = session.beginTransaction();

            cr = session.createCriteria(EmployeeEmpntTypes.class, "empEmpl").createAlias("empEmpl.empnttypes", "empl", JoinType.INNER_JOIN, Restrictions.isNull("empEmpl.endDate")).setFetchMode("empEmpl.employees", FetchMode.JOIN).add(Restrictions.eq("empEmpl.employees.id", id));
            this.employeeEmploymentestoUpdate.addAll(cr.list());
            trx.commit();
            trx = session.beginTransaction();

            cr = session.createCriteria(EmployeeEmpntTypes.class, "empEmpl").createAlias("empEmpl.employees", "emp", JoinType.INNER_JOIN).createAlias("empEmpl.empnttypes", "Empl", JoinType.INNER_JOIN, Restrictions.eq("Empl.id", id)).add(Restrictions.isNotNull("empEmpl.endDate")).addOrder(Order.desc("empEmpl.startDate"));
            this.employeeEmploymentesHistory.addAll(cr.list());
            trx.commit();

            RequestContext req = RequestContext.getCurrentInstance();

            req.update("growlmsgssage");
            req.update("frmempEmployment");
            req.update("frmAddEmployments:addEmploymentstbl");
            req.update("frmUpdateEmployments:employmentstbl");
            req.update("frmHistory:historytbl");

            this.setSelectedEmployeeEmploymenttoAdd(null);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (q != null) {
                q = null;
            }

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

    public void addEmploymentToEmployee() {

        Session session = null;
        Transaction trx = null;
         FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            if (this.selectedEmployeeEmploymenttoAdd == null) {
                String message = text.getString("ui.Bean.SelectEmployment");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlemploymenttbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            } else if (this.selectedEmployeeEmploymenttoAdd.getStartDate() == null) {
                String message = text.getString("ui.Bean.StartDate");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlemploymenttbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            } else {

                this.selectedEmployeeEmploymenttoAdd.setEmployees(this.selectedEmployee);

                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                int userId = loginDAO.getLoggedinUser().getId();

                this.selectedEmployeeEmploymenttoAdd.setCreatedby(userId);

                Date date = new Date();

                this.selectedEmployeeEmploymenttoAdd.setCreatedon(new Timestamp(date.getTime()));

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                session.save(this.selectedEmployeeEmploymenttoAdd);

                trx.commit();

                this.retrieveEmployeeEmploymentDetails();

                String message = text.getString("ui.Bean.EmploymentAddSuccess");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));

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

    public void updateEmployeeEmployment() {

        Session session = null;
        Transaction trx = null;
         FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {
            
            if(this.selectedEmployeeEmploymenttoUpdate == null){
                String message = text.getString("ui.Bean.EmploymentUpdate");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlemploymenttbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            }else if(this.selectedEmployeeEmploymenttoUpdate.getEndDate() == null){
                String message = text.getString("ui.Bean.EndDate");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlemploymenttbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                
            }else if(this.selectedEmployeeEmploymenttoUpdate.getEndReason() == null || this.selectedEmployeeEmploymenttoUpdate.getEndReason().equals("")){
                String message = text.getString("ui.Bean.EndReason");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlemploymenttbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            }else{
                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                int userId = loginDAO.getLoggedinUser().getId();

                Date date = new Date();
                
                this.selectedEmployeeEmploymenttoUpdate.setCreatedby(userId);
                this.selectedEmployeeEmploymenttoUpdate.setCreatedon(new Timestamp(date.getTime()));
                
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                session.update(this.selectedEmployeeEmploymenttoUpdate);
                
                trx.commit();
                
                 this.retrieveEmployeeEmploymentDetails();

                String message = text.getString("ui.Bean.EmploymentUpadteSuccess");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                
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
}
