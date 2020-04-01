/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.EmployeesConverter;
import converter.ProjectClientsConverter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.EmployeesManagerImpl;
import manager.impl.ProjectClientsManagerImpl;
import manager.impl.ProjectInchargeManagerImpl;
import manager.interfaces.IEmployeesManager;
import manager.interfaces.IProjectClientsManager;
import manager.interfaces.IProjectInchargeManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import orm.Employees;
import orm.ProjectClients;
import orm.ProjectIncharge;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "ProjectInchargeView")
@ViewScoped
public class ProjectInchargeView implements Serializable{

    //Employees
    private IEmployeesManager employeeManager = new EmployeesManagerImpl();
    private List<Employees> autocompleteSearchEmployeeList;
    private Employees selectedEmployee;
    private EmployeesConverter employeesConverter = new EmployeesConverter();
    //project 
    private IProjectClientsManager projectClientManager = new ProjectClientsManagerImpl();
    private List<ProjectClients> projectClientList;
    private List<ProjectClients> allProjectClientList;
    private ProjectClients selectedClientProject = new ProjectClients();
    private ProjectClients selectedClientProjectToShowHistory;
    private ProjectClientsConverter projectClientConverter = new ProjectClientsConverter();
    //ProjectIncharge
    private ProjectIncharge selectedProjectIncharge = new ProjectIncharge();
    private ProjectIncharge selectedProjectInchargeToupdate;
    private IProjectInchargeManager projectIncharge = new ProjectInchargeManagerImpl();
    private List<ProjectIncharge> allClientsProjectIncharges;
    private List<ProjectIncharge> projectInchargeHistoryList;
    private String strEmployeeSearch;
    //To Enable EmployeeAutocomplete
    boolean isEmployeeEnable = true;
    /*
     Loginbean Inject 
     
     */
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    /*

     /**
     * Creates a new instance of ProjectInchargeView
     */

    public ProjectInchargeView() {
        System.out.println("ProjectInchargeView created");
    }

    @PostConstruct
    public void postConstruct() {
        this.loginbean.checkAccess();
        retrieveAllNotAssignedClientProjects();
        retrieveAllActiveProjectIncharge();
        retrieveAllProjectClient();
    }

    public List<Employees> getAutocompleteSearchEmployeeList() {
        return autocompleteSearchEmployeeList;
    }

    public void setAutocompleteSearchEmployeeList(List<Employees> autocompleteSearchEmployeeList) {
        this.autocompleteSearchEmployeeList = autocompleteSearchEmployeeList;
    }

    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public EmployeesConverter getEmployeesConverter() {
        return employeesConverter;
    }

    public void setEmployeesConverter(EmployeesConverter employeesConverter) {
        this.employeesConverter = employeesConverter;
    }

    public String getStrEmployeeSearch() {
        return strEmployeeSearch;
    }

    public void setStrEmployeeSearch(String strEmployeeSearch) {
        this.strEmployeeSearch = strEmployeeSearch;
    }

    public IEmployeesManager getEmployeeManager() {
        return employeeManager;
    }

    public void setEmployeeManager(IEmployeesManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public List<ProjectClients> getProjectClientList() {
        return projectClientList;
    }

    public void setProjectClientList(List<ProjectClients> projectClientList) {
        this.projectClientList = projectClientList;
    }

    public ProjectClients getSelectedClientProject() {
        return selectedClientProject;
    }

    public void setSelectedClientProject(ProjectClients selectedClientProject) {
        this.selectedClientProject = selectedClientProject;
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public ProjectIncharge getSelectedProjectIncharge() {
        return selectedProjectIncharge;
    }

    public void setSelectedProjectIncharge(ProjectIncharge selectedProjectIncharge) {
        this.selectedProjectIncharge = selectedProjectIncharge;
    }

    public ProjectIncharge getSelectedProjectInchargeToupdate() {
        return selectedProjectInchargeToupdate;
    }

    public void setSelectedProjectInchargeToupdate(ProjectIncharge selectedProjectInchargeToupdate) {
        this.selectedProjectInchargeToupdate = selectedProjectInchargeToupdate;
    }

    public List<ProjectIncharge> getAllClientsProjectIncharges() {
        return allClientsProjectIncharges;
    }

    public void setAllClientsProjectIncharges(List<ProjectIncharge> allClientsProjectIncharges) {
        this.allClientsProjectIncharges = allClientsProjectIncharges;
    }

    public IProjectClientsManager getProjectClientManager() {
        return projectClientManager;
    }

    public void setProjectClientManager(IProjectClientsManager projectClientManager) {
        this.projectClientManager = projectClientManager;
    }

    public ProjectClientsConverter getProjectClientConverter() {
        return projectClientConverter;
    }

    public void setProjectClientConverter(ProjectClientsConverter projectClientConverter) {
        this.projectClientConverter = projectClientConverter;
    }

    public IProjectInchargeManager getProjectIncharge() {
        return projectIncharge;
    }

    public void setProjectIncharge(IProjectInchargeManager projectIncharge) {
        this.projectIncharge = projectIncharge;
    }

    public boolean isIsEmployeeEnable() {
        return isEmployeeEnable;
    }

    public void setIsEmployeeEnable(boolean isEmployeeEnable) {
        this.isEmployeeEnable = isEmployeeEnable;
    }

    public List<ProjectClients> getAllProjectClientList() {
        return allProjectClientList;
    }

    public void setAllProjectClientList(List<ProjectClients> allProjectClientList) {
        this.allProjectClientList = allProjectClientList;
    }

    public List<ProjectIncharge> getProjectInchargeHistoryList() {
        return projectInchargeHistoryList;
    }

    public void setProjectInchargeHistoryList(List<ProjectIncharge> projectInchargeHistoryList) {
        this.projectInchargeHistoryList = projectInchargeHistoryList;
    }

    public ProjectClients getSelectedClientProjectToShowHistory() {
        return selectedClientProjectToShowHistory;
    }

    public void setSelectedClientProjectToShowHistory(ProjectClients selectedClientProjectToShowHistory) {
        this.selectedClientProjectToShowHistory = selectedClientProjectToShowHistory;
    }

    //Enable Employee Autocomplete
    public void checkProjectClientSelected() {
        this.setIsEmployeeEnable(false);
    }

    //Employee Search For autocomplete.
    public List<Employees> searchEmployeeForAutocomplete(String strSearchEmployeeCriteria) {
        try {
            if (selectedProjectIncharge.getProjectClients().getId() != 0) {
                HibernateUtil.beginTransaction();
                this.autocompleteSearchEmployeeList = employeeManager.searchEmployeeForProjectInchargeByProjectClientId(strSearchEmployeeCriteria,
                        selectedProjectIncharge.getProjectClients().getId());
                HibernateUtil.commitTransaction();
                this.employeesConverter.setSearchEmployees(autocompleteSearchEmployeeList);
            } else {
                this.setIsEmployeeEnable(true);
            }
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
        return autocompleteSearchEmployeeList;
    }

    //to retrieve AllNotAssignedClientProjects
    public void retrieveAllNotAssignedClientProjects() {

        try {
            HibernateUtil.beginTransaction();
            projectClientList = projectClientManager.retrieveAllNotAssignedClientProjects();
            HibernateUtil.commitTransaction();
            projectClientConverter.setSearchProjectContacts(projectClientList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    //to retrieve all Active ProjectIncharge
    public void retrieveAllActiveProjectIncharge() {
        try {
            HibernateUtil.beginTransaction();
            allClientsProjectIncharges = projectIncharge.retrieveAllActiveProjectIncharge();
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //to retrieve all ProjectClient for showing history
    public void retrieveAllProjectClient() {
        try {
            HibernateUtil.beginTransaction();
            allProjectClientList = projectClientManager.retrieveAllProjectClients();
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To add project incharge
    public void addProjectIncharge() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

            HibernateUtil.beginTransaction();

            this.projectIncharge.saveNew(selectedProjectIncharge);

            HibernateUtil.commitTransaction();

            selectedProjectIncharge = null;

            this.setIsEmployeeEnable(true);

            retrieveAllActiveProjectIncharge();

            String message = text.getString("ui.Bean.ProInchargeAddSuccess");

            RequestContext req = RequestContext.getCurrentInstance();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            req.update("growlmsg");

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To update ProjectIncharge
    public void updateProjectIncharge() {

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());
            if (this.selectedProjectInchargeToupdate.getEndDate() != null && !this.selectedProjectInchargeToupdate.getEndDate().equals("")) {

                if (this.selectedProjectInchargeToupdate.getEndReason() != null && !this.selectedProjectInchargeToupdate.getEndReason().equals("")) {
                    if (this.selectedProjectInchargeToupdate.getEndDate().compareTo(this.selectedProjectInchargeToupdate.getStartDate()) >= 0) {
                        if (this.selectedProjectInchargeToupdate.getEndDate().compareTo(new Date()) <= 0) {
                            HibernateUtil.beginTransaction();
                            this.projectIncharge.updateExisting(selectedProjectInchargeToupdate);
                            HibernateUtil.commitTransaction();
                            selectedProjectInchargeToupdate = null;
                            retrieveAllActiveProjectIncharge();
                            retrieveAllNotAssignedClientProjects();
                            String message = text.getString("ui.Bean.ProInchargUpdateSuccess");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                            RequestContext req = RequestContext.getCurrentInstance();
                            req.update(":frmAddProjectIncharge:growlmsg");

                        } else {
                            String message = text.getString("ui.Bean.EnddatelessthanCurrentDate");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                            RequestContext req = RequestContext.getCurrentInstance();
                            req.update(":frmAddProjectIncharge:growlmsg");
                        }

                    } else {
                        String message = text.getString("ui.Bean.EnddateGreterThanStartDate");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                        RequestContext req = RequestContext.getCurrentInstance();
                        req.update(":frmAddProjectIncharge:growlmsg");
                    }

                } else {
                    String message = text.getString("ui.Bean.ProInchargeupdEndReason");

                    RequestContext req = RequestContext.getCurrentInstance();

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                    req.update(":frmAddProjectIncharge:growlmsg");
                }

            } else {
                String message = text.getString("ui.Bean.ProInchargeUpdateEndDate");

                RequestContext req = RequestContext.getCurrentInstance();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                req.update(":frmAddProjectIncharge:growlmsg");
            }


        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    //to retrieve all ProjectIncharge history of selected Project client
    public void retrieveSelectedProjectClientProjectInchargeHistory() {
        try {
            HibernateUtil.beginTransaction();
            if (projectInchargeHistoryList != null) {
                projectInchargeHistoryList.clear();
                projectInchargeHistoryList = projectIncharge.retrieveSelectedProjectClientProjectInchargeHistory(selectedClientProjectToShowHistory.getId());

            } else {

                projectInchargeHistoryList = projectIncharge.retrieveSelectedProjectClientProjectInchargeHistory(selectedClientProjectToShowHistory.getId());
            }


            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
