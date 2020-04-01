/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.EmployeesConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.EmployeeResponsibilitiesManagerImpl;
import manager.impl.EmployeesManagerImpl;
import manager.impl.ResponsibilitiesManagerImpl;
import manager.interfaces.IEmployeeResponsibilitiesManager;
import manager.interfaces.IEmployeesManager;
import manager.interfaces.IResponsibilitiesManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import orm.EmployeeResponsibilities;
import orm.Employees;
import orm.Responsibilities;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "employeeResponsibilityView")
@ViewScoped
public class EmployeeResponsibilityView implements Serializable {

    /*
     Loginbean Inject 
     
     */
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    //Employee
    private IEmployeesManager employeeManager = new EmployeesManagerImpl();
    private EmployeesConverter empConverter = new EmployeesConverter();
    private List<Employees> EmployeeList;
    List<Employees> autocompleteSearchEmployeeList;
    private Employees selectedEmployee;
    //EmployeeResponsibilities
    private IEmployeeResponsibilitiesManager employeeResponsibility = new EmployeeResponsibilitiesManagerImpl();
    private List<EmployeeResponsibilities> employeeResponsibilitiesToAdd;
    private List<EmployeeResponsibilities> assignedEmployeeResponsibilities;
    private List<EmployeeResponsibilities> empResponsibilityHistory;
    private EmployeeResponsibilities selectedEmpResponToAdd = new EmployeeResponsibilities();
    private EmployeeResponsibilities selectedEmpResponToUpdate;
    //Responsibilities
    private IResponsibilitiesManager responsibilitiesManager = new ResponsibilitiesManagerImpl();

    /**
     * Creates a new instance of EmployeeResponsibilityView
     */
    public EmployeeResponsibilityView() {
        System.out.println("EmployeeResponsibilityView called....");

    }

    @PostConstruct
    public void postConstruct() {
        this.loginbean.checkAccess();
    }

    public IEmployeesManager getEmployeeManager() {
        return employeeManager;
    }

    public void setEmployeeManager(IEmployeesManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public EmployeesConverter getEmpConverter() {
        return empConverter;
    }

    public void setEmpConverter(EmployeesConverter empConverter) {
        this.empConverter = empConverter;
    }

    public List<Employees> getEmployeeList() {
        return EmployeeList;
    }

    public void setEmployeeList(List<Employees> EmployeeList) {
        this.EmployeeList = EmployeeList;
    }

    public List<Employees> getAutocompleteSearchEmployeeList() {
        return autocompleteSearchEmployeeList;
    }

    public void setAutocompleteSearchEmployeeList(List<Employees> autocompleteSearchEmployeeList) {
        this.autocompleteSearchEmployeeList = autocompleteSearchEmployeeList;
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public List<EmployeeResponsibilities> getEmployeeResponsibilitiesToAdd() {
        return employeeResponsibilitiesToAdd;
    }

    public void setEmployeeResponsibilitiesToAdd(List<EmployeeResponsibilities> employeeResponsibilitiesToAdd) {
        this.employeeResponsibilitiesToAdd = employeeResponsibilitiesToAdd;
    }

    public List<EmployeeResponsibilities> getAssignedEmployeeResponsibilities() {
        return assignedEmployeeResponsibilities;
    }

    public void setAssignedEmployeeResponsibilities(List<EmployeeResponsibilities> assignedEmployeeResponsibilities) {
        this.assignedEmployeeResponsibilities = assignedEmployeeResponsibilities;
    }

    public List<EmployeeResponsibilities> getEmpResponsibilityHistory() {
        return empResponsibilityHistory;
    }

    public void setEmpResponsibilityHistory(List<EmployeeResponsibilities> empResponsibilityHistory) {
        this.empResponsibilityHistory = empResponsibilityHistory;
    }

    public EmployeeResponsibilities getSelectedEmpResponToAdd() {
        return selectedEmpResponToAdd;
    }

    public void setSelectedEmpResponToAdd(EmployeeResponsibilities selectedEmpResponToAdd) {
        this.selectedEmpResponToAdd = selectedEmpResponToAdd;
    }

    public EmployeeResponsibilities getSelectedEmpResponToUpdate() {
        return selectedEmpResponToUpdate;
    }

    public void setSelectedEmpResponToUpdate(EmployeeResponsibilities selectedEmpResponToUpdate) {
        this.selectedEmpResponToUpdate = selectedEmpResponToUpdate;
    }

    public IEmployeeResponsibilitiesManager getEmployeeResponsibility() {
        return employeeResponsibility;
    }

    public void setEmployeeResponsibility(IEmployeeResponsibilitiesManager employeeResponsibility) {
        this.employeeResponsibility = employeeResponsibility;
    }

    //for autocomplete of employees
    public List<Employees> searchEmployeeForAutocomplete(String strSearchEmployeeCriteria) {
        try {

            HibernateUtil.beginTransaction();
            this.autocompleteSearchEmployeeList = employeeManager.searchEmployeeForAutocomplete(strSearchEmployeeCriteria);
            HibernateUtil.commitTransaction();
            this.empConverter.setSearchEmployees(autocompleteSearchEmployeeList);
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
        return autocompleteSearchEmployeeList;
    }

    //this method is called in Ajax Event from .xhtml page
    public void retrieveEmployeeResponsibilities() {
        retrieveEmployeeAssignedResponsibilities();
        retrieveEmployeeNotAssignedResponsibilities();
        retrieveEmployeeResponsibilitiesHistory();
    }

    // to retrieve the Employee's  Not Assigned Rresponsibility
    public void retrieveEmployeeNotAssignedResponsibilities() {
        try {
            List<Responsibilities> allResponsibilities;
            EmployeeResponsibilities selectedEmployeeResponsibility;
            List<EmployeeResponsibilities> employeeResponsibilitiesList = new ArrayList<EmployeeResponsibilities>();
            HibernateUtil.beginTransaction();
            allResponsibilities = responsibilitiesManager.retrieveEmployeeResponsibilities(getSelectedEmployee().getId());

            for (int i = 0; i < allResponsibilities.size(); i++) {
                selectedEmployeeResponsibility = new EmployeeResponsibilities();
                selectedEmployeeResponsibility.setResponsibilities(allResponsibilities.get(i));
                employeeResponsibilitiesList.add(selectedEmployeeResponsibility);
                selectedEmployeeResponsibility = null;
            }
            employeeResponsibilitiesToAdd = employeeResponsibilitiesList;
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }

    }

    // to retrieve the assigned Employee Responsibility 
    public void retrieveEmployeeAssignedResponsibilities() {
        try {
            HibernateUtil.beginTransaction();
            assignedEmployeeResponsibilities = employeeResponsibility
                    .retrieveEmployeeAssignedResponsibilities(this.getSelectedEmployee().getId());
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }

    }

    // to retrieve the employee responsibility history
    public void retrieveEmployeeResponsibilitiesHistory() {
        try {
            HibernateUtil.beginTransaction();
            empResponsibilityHistory = employeeResponsibility.retrieveEmployeeResponsibilitiesHistory(this.getSelectedEmployee().getId());
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
    }

    //to update the employee responsibility
    public void updateEmployeeResponsibility() {

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

                if (selectedEmpResponToUpdate.getEndDate().compareTo(selectedEmpResponToUpdate.getStartDate()) >= 0) {

                    if (selectedEmpResponToUpdate.getEndDate().compareTo(current_Date) <= 0) {
                        
                        HibernateUtil.beginTransaction();
                        employeeResponsibility.updateExisting(selectedEmpResponToUpdate);
                        HibernateUtil.commitTransaction();
                        setSelectedEmpResponToUpdate(null);
                        setSelectedEmpResponToUpdate(new orm.EmployeeResponsibilities());

                        retrieveEmployeeResponsibilities();

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
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    // to add the employee responsibility
    public void addEmployeeResponsibility() {

        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
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

                HibernateUtil.beginTransaction();
                selectedEmpResponToAdd.setEmployees(selectedEmployee);
                employeeResponsibility.saveNew(selectedEmpResponToAdd);
                HibernateUtil.commitTransaction();
                this.setSelectedEmpResponToAdd(null);
                this.setSelectedEmpResponToAdd(new EmployeeResponsibilities());
                retrieveEmployeeResponsibilities();

                String message = text.getString("ui.EmpResponsAddedSuccess");

                RequestContext req = RequestContext.getCurrentInstance();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));


                req.update("growlmsgssage");
            }

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }
}
