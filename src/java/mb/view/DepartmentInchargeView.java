/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.DepartmentInchargeConverter;
import converter.DepartmentsConverter;
import converter.EmployeesConverter;
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
import manager.impl.DepartmentInchargeManagerImpl;
import manager.impl.DepartmentManagerImpl;
import manager.impl.EmployeesManagerImpl;
import manager.interfaces.IDepartmentInchargeManager;
import manager.interfaces.IDepartmentsManager;
import manager.interfaces.IEmployeesManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import orm.DepartmentIncharge;
import orm.Departments;
import orm.Employees;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "departmentInchargeView")
@ViewScoped
public class DepartmentInchargeView implements Serializable {

    /*
     Loginbean Inject 
     
     */
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    /*
     
     Deparment
     */
    private IDepartmentsManager dm = new DepartmentManagerImpl();
    private List<Departments> departmentList;
    private DepartmentsConverter deptmentsconverter = new DepartmentsConverter();
    /*
     DepartmentIncharge
     */
    private IDepartmentInchargeManager departmentIncharge = new DepartmentInchargeManagerImpl();
    private List<DepartmentIncharge> departmentInchargeList;
    private DepartmentIncharge selectedDepartmentIncharge = new DepartmentIncharge();
    private DepartmentIncharge selectedDepartmentInchargeToupdate;
    /*
     Employees
     */
    private IEmployeesManager employeeManager = new EmployeesManagerImpl();
    private List<Employees> EmployeeList;
    List<Employees> autocompleteSearchEmployeeList;
    private EmployeesConverter empConverter = new EmployeesConverter();

    /**
     *
     * /**
     * Creates a new instance of DepartmentInchargeView
     */
    public DepartmentInchargeView() {
        System.out.println("DepartmentInchargeView called....");
    }

    @PostConstruct
    public void postConstruct() {
        this.loginbean.checkAccess();
        retrieveAllNotAssignedDepartments();
        retrieveAllDepartmentIncharge();
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public IDepartmentsManager getDm() {
        return dm;
    }

    public void setDm(IDepartmentsManager dm) {
        this.dm = dm;
    }

    public List<Departments> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Departments> departmentList) {
        this.departmentList = departmentList;
    }

    public DepartmentsConverter getDeptmentsconverter() {
        return deptmentsconverter;
    }

    public void setDeptmentsconverter(DepartmentsConverter deptmentsconverter) {
        this.deptmentsconverter = deptmentsconverter;
    }

    public IDepartmentInchargeManager getDepartmentIncharge() {
        return departmentIncharge;
    }

    public void setDepartmentIncharge(IDepartmentInchargeManager departmentIncharge) {
        this.departmentIncharge = departmentIncharge;
    }

    public List<DepartmentIncharge> getDepartmentInchargeList() {
        return departmentInchargeList;
    }

    public void setDepartmentInchargeList(List<DepartmentIncharge> departmentInchargeList) {
        this.departmentInchargeList = departmentInchargeList;
    }

    public DepartmentIncharge getSelectedDepartmentIncharge() {
        return selectedDepartmentIncharge;
    }

    public void setSelectedDepartmentIncharge(DepartmentIncharge selectedDepartmentIncharge) {
        this.selectedDepartmentIncharge = selectedDepartmentIncharge;
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

    public DepartmentIncharge getSelectedDepartmentInchargeToupdate() {
        return selectedDepartmentInchargeToupdate;
    }

    public void setSelectedDepartmentInchargeToupdate(DepartmentIncharge selectedDepartmentInchargeToupdate) {
        this.selectedDepartmentInchargeToupdate = selectedDepartmentInchargeToupdate;
    }

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

    public void retrieveAllDepartmentIncharge() {
        try {
            HibernateUtil.beginTransaction();
            departmentInchargeList = departmentIncharge.retrieveDepartmentInchargeList();
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void retrieveAllNotAssignedDepartments() {
        try {
            HibernateUtil.beginTransaction();
            departmentList = dm.retrieveDepartmentList();
            HibernateUtil.commitTransaction();
            deptmentsconverter.setSearchDepartments(departmentList);
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void addDepartmentIncharge() {
        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());
            HibernateUtil.beginTransaction();
            departmentIncharge.saveNew(selectedDepartmentIncharge);
            HibernateUtil.commitTransaction();
            setSelectedDepartmentIncharge(null);
            setSelectedDepartmentIncharge(new DepartmentIncharge());
            retrieveAllNotAssignedDepartments();
            retrieveAllDepartmentIncharge();
            String message = text.getString("ui.Bean.DepInchargeAddSuccess");
            RequestContext req = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            req.update("growlmsg");
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void updateDepartmentIncharge() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());
            Date current_Date = new Date();
            if (selectedDepartmentInchargeToupdate.getEndDate() != null && !selectedDepartmentInchargeToupdate.getEndDate().equals("")) {
                if (selectedDepartmentInchargeToupdate.getEndReason() != null && !selectedDepartmentInchargeToupdate.getEndReason().equals("")) {
                    if (selectedDepartmentInchargeToupdate.getEndDate().compareTo(selectedDepartmentInchargeToupdate.getStartDate()) >= 0) {
                        if (selectedDepartmentInchargeToupdate.getEndDate().compareTo(current_Date) <= 0) {
                            HibernateUtil.beginTransaction();
                            departmentIncharge.updateExisting(selectedDepartmentInchargeToupdate);
                            HibernateUtil.commitTransaction();
                            setSelectedDepartmentInchargeToupdate(null);
                            retrieveAllDepartmentIncharge();
                            retrieveAllNotAssignedDepartments();
                            String message = text.getString("ui.Bean.DepInchargeUpdateSuccess");
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
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                    req.update("growlmsg");
                }
            } else {
                String message = text.getString("ui.Bean.DepInchargeUpdateEndDate");
                RequestContext req = RequestContext.getCurrentInstance();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                req.update("growlmsg");
            }
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
