/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import orm.Designations;
import orm.Employees;
import orm.Responsibilities;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "employeeResponsibilitiesDAO")
@ViewScoped
public class EmployeeResponsibilities {

    private List<Employees> allEmployees;
    private List<orm.EmployeeResponsibilities> responsibilitiesToAdd;
    private List<orm.EmployeeResponsibilities> assignedResponsibilities;
    private List<orm.EmployeeResponsibilities> empResponsibilityHistory;
    private Employees selectedEmployee;
    private orm.EmployeeResponsibilities selectedEmpResponToAdd;
    private orm.EmployeeResponsibilities selectedEmpResponToUpdate;

    /**
     * Creates a new instance of EmployeeResponsibilities
     */
    public EmployeeResponsibilities() {
        this.allEmployees = new ArrayList<Employees>();
        this.assignedResponsibilities = new ArrayList<orm.EmployeeResponsibilities>();
        this.responsibilitiesToAdd = new ArrayList<orm.EmployeeResponsibilities>();
        this.empResponsibilityHistory = new ArrayList<orm.EmployeeResponsibilities>();

        this.selectedEmployee = new Employees();
        this.selectedEmpResponToAdd = new orm.EmployeeResponsibilities();
        this.selectedEmpResponToUpdate = new orm.EmployeeResponsibilities();

        System.out.println(" New EmployeeResponsibilities is created......");
    }

    public List<Employees> getAllEmployees() {
        return allEmployees;
    }

    public void setAllEmployees(List<Employees> allEmployees) {
        this.allEmployees = allEmployees;
    }

    public int getSelectedEmployee() {
        return selectedEmployee.getId();
    }

    public void setSelectedEmployee(int selectedEmployee) {
        this.selectedEmployee.setId(selectedEmployee);
    }

    public List<orm.EmployeeResponsibilities> getAssignedResponsibilities() {
        return assignedResponsibilities;
    }

    public void setAssignedResponsibilities(List<orm.EmployeeResponsibilities> assignedResponsibilities) {
        this.assignedResponsibilities = assignedResponsibilities;
    }

    public List<orm.EmployeeResponsibilities> getResponsibilitiesToAdd() {
        return responsibilitiesToAdd;
    }

    public void setResponsibilitiesToAdd(List<orm.EmployeeResponsibilities> responsibilitiesToAdd) {
        this.responsibilitiesToAdd = responsibilitiesToAdd;
    }

    public List<orm.EmployeeResponsibilities> getEmpResponsibilityHistory() {
        return empResponsibilityHistory;
    }

    public void setEmpResponsibilityHistory(List<orm.EmployeeResponsibilities> empResponsibilityHistory) {
        this.empResponsibilityHistory = empResponsibilityHistory;
    }

    public orm.EmployeeResponsibilities getSelectedEmpResponToAdd() {
        return selectedEmpResponToAdd;
    }

    public void setSelectedEmpResponToAdd(orm.EmployeeResponsibilities selectedEmpResponToAdd) {
        this.selectedEmpResponToAdd = selectedEmpResponToAdd;
    }

    public orm.EmployeeResponsibilities getSelectedEmpResponToUpdate() {
        return selectedEmpResponToUpdate;
    }

    public void setSelectedEmpResponToUpdate(orm.EmployeeResponsibilities selectedEmpResponToUpdate) {
        this.selectedEmpResponToUpdate = selectedEmpResponToUpdate;
    }

    public void retrieveEmployeeList() {

        Session session = null;
        Transaction trx = null;

        Query q = null;



        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                this.allEmployees.clear();
                String hqlQuery = "from Employees";

                q = session.createQuery(hqlQuery);

                this.allEmployees.addAll(q.list());

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

    public void retrieveEmployeeResponsibilities() {
        Session session = null;
        Transaction trx = null;

        Query q = null;
        Criteria cr = null;
        Criteria cr1 = null;

        this.assignedResponsibilities.clear();
        this.responsibilitiesToAdd.clear();
        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

//            cr1 = session.createCriteria(Designations.class, "desig").createAlias("desig.employeeDesignations", "edesig", JoinType.INNER_JOIN).add(Restrictions.eq("edesig.employees.id", this.getSelectedEmployee())).add(Restrictions.isNull("edesig.endDate"));
//
//            List<Designations> desig = new ArrayList<Designations>();
//            
//            desig.addAll(cr1.list());
//            
//             ArrayList desigid = new ArrayList();
//            
//            for(int i =0;i<desig.size();i++){
//               desigid.add(desig.get(i).getId());
//            }
//            
//            cr = session.createCriteria(Responsibilities.class, "res")
//                    .createAlias("res.designationResponsibilities", "dres", JoinType.LEFT_OUTER_JOIN)
//                    .add(Restrictions.in("dres.designations.id", desigid))
//                    .add(Restrictions.isNull("dres.endDate"))
//                    .createAlias("res.employeeResposibilities", "empRes", JoinType.LEFT_OUTER_JOIN)
//                    .add(Restrictions.eq("empRes.employees.id", this.getSelectedEmployee()))
//                    .add(Restrictions.isNull("empRes.endDate"))
//                    .add(Restrictions.isNull("dres.id")).add(Restrictions.isNull("empRes.id"));


            q = session.createQuery("select res from Responsibilities res left join res.designationResponsibilities dres "
                    + "with dres.designations.id in(select desig from Designations desig inner join desig.employeeDesignations edesig "
                    + "with edesig.employees.id = " + this.getSelectedEmployee() + " and edesig.endDate is null)"
                    + "and dres.endDate is null left join res.employeeResposibilities empres "
                    + "with empres.employees.id = " + this.getSelectedEmployee() + " and empres.endDate is null where dres.id is null and empres.id is null ");

            for (int i = 0; i < q.list().size(); i++) {


                orm.EmployeeResponsibilities empRes = new orm.EmployeeResponsibilities();

                Responsibilities res = (Responsibilities) q.list().get(i);

                empRes.setResponsibilities(res);

                this.responsibilitiesToAdd.add(empRes);

            }



            cr = session.createCriteria(orm.EmployeeResponsibilities.class, "empResp").createAlias("responsibilities", "res", JoinType.INNER_JOIN).createAlias("employees", "emp", JoinType.INNER_JOIN).add(Restrictions.eq("empResp.employees.id", this.getSelectedEmployee())).add(Restrictions.isNull("empResp.endDate"));

            this.assignedResponsibilities.addAll(cr.list());


            cr = session.createCriteria(orm.EmployeeResponsibilities.class, "empResp").createAlias("responsibilities", "res", JoinType.INNER_JOIN).createAlias("employees", "emp", JoinType.INNER_JOIN).add(Restrictions.eq("empResp.employees.id", this.getSelectedEmployee())).add(Restrictions.isNotNull("empResp.endDate"));
            this.empResponsibilityHistory.addAll(cr.list());

            trx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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

    public void addEmployeeResponsibility() {

        Session session = null;
        Transaction trx = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            if (this.selectedEmpResponToAdd == null) {

                String message = text.getString("ui.selectReponsibilityAdd");


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");

            } else if (this.selectedEmpResponToAdd.getStartDate() == null) {

                String message = text.getString("ui.Bean.StartDate");


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");

            } else {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                this.selectedEmpResponToAdd.setEmployees(this.selectedEmployee);

                this.selectedEmpResponToAdd.setCreatedby(loginDAO.getLoggedinUser().getId());

                Date date = new Date();

                this.selectedEmpResponToAdd.setCreatedon(new Timestamp(date.getTime()));

                session.save(this.selectedEmpResponToAdd);
                trx.commit();

                this.setSelectedEmpResponToAdd(null);
                this.setSelectedEmpResponToAdd(new orm.EmployeeResponsibilities());

                this.retrieveEmployeeResponsibilities();

                String message = text.getString("ui.EmpResponsAddedSuccess");

                RequestContext req = RequestContext.getCurrentInstance();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));


                req.update("growlmsgssage");
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

    public void updateEmployeeResponsibility() {
        Session session = null;
        Transaction trx = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        Date current_Date = new Date();

        try {
            if (this.selectedEmpResponToUpdate == null) {

                String message = text.getString("ui.Bean.selectDesignationUpdate");


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");

            } else if (this.selectedEmpResponToUpdate.getEndDate() == null) {

                String message = text.getString("ui.Bean.EndDate");


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");

            } else if (this.selectedEmpResponToUpdate.getEndReason() == null || this.selectedEmpResponToUpdate.getEndReason().equals("")) {

                String message = text.getString("ui.Bean.EndReason");


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");

            } else {
                if (this.selectedEmpResponToUpdate.getEndDate().compareTo(this.selectedEmpResponToUpdate.getStartDate()) >= 0) {

                    if (this.selectedEmpResponToUpdate.getEndDate().compareTo(current_Date) <= 0) {

                        session = HibernateUtil.getSession();
                        trx = session.beginTransaction();


                        Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                        int userId = loginDAO.getLoggedinUser().getId();
                        Date date = new Date();

                        this.selectedEmpResponToUpdate.setCreatedby(userId);
                        this.selectedEmpResponToUpdate.setCreatedon(new Timestamp(date.getTime()));

                        session.update(this.selectedEmpResponToUpdate);
                        trx.commit();

                        this.setSelectedEmpResponToUpdate(null);
                        this.setSelectedEmpResponToUpdate(new orm.EmployeeResponsibilities());

                        this.retrieveEmployeeResponsibilities();

                        String message = "Employee Responsibility updated successfully";

                        RequestContext req = RequestContext.getCurrentInstance();

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));

                        req.update("growlmsgssage");
                    } else {
                        String message = text.getString("ui.Bean.EnddateGreterThanStartDate");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                        RequestContext req = RequestContext.getCurrentInstance();
                        req.update("growlmsgssage");
                    }
                } else {
                    String message = text.getString("ui.Bean.EnddateGreterThanStartDate");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                    RequestContext req = RequestContext.getCurrentInstance();
                    req.update("growlmsgssage");
                }

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
