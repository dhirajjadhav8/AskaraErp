/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.EmployeesConverter;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.EmployeesManagerImpl;
import manager.impl.RolesManagerImpl;
import manager.impl.UserRolesManagerImpl;
import manager.impl.UsersManagerImpl;
import manager.interfaces.IEmployeesManager;
import manager.interfaces.IRolesManager;
import manager.interfaces.IUserRolesManager;
import manager.interfaces.IUsersManager;
import orm.Employees;
import orm.Roles;
import orm.UserRoles;
import orm.Users;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "userView")
@ViewScoped
public class UserView implements Serializable {

    /**
     * UsersManagerImpl
     */
    private IUsersManager um = new UsersManagerImpl();
    private Users selectedUser = new Users();
    /**
     * EmployeesManagerImpl
     */
    private IEmployeesManager em = new EmployeesManagerImpl();
    private Employees selectedEmployee;
    private List<Employees> employeeSearchList;
    private EmployeesConverter empConverter;
    private String re_enterPassword;
    /*
     RolesManagerImpl
     */
    private IRolesManager rm = new RolesManagerImpl();
    private List<Roles> allRoles;
    private List<Roles> selectedRoles;
    /*
     UserRolesImpl
     */
    private IUserRolesManager urm = new UserRolesManagerImpl();

    /**
     * Creates a new instance of UserView
     */
    public UserView() {
        empConverter = new EmployeesConverter();

    }

    public IUsersManager getUm() {
        return um;
    }

    public void setUm(IUsersManager um) {
        this.um = um;
    }

    public IEmployeesManager getEm() {
        return em;
    }

    public void setEm(IEmployeesManager em) {
        this.em = em;
    }

    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public List<Employees> getEmployeeSearchList() {
        return employeeSearchList;
    }

    public void setEmployeeSearchList(List<Employees> employeeSearchList) {
        this.employeeSearchList = employeeSearchList;
    }

    public EmployeesConverter getEmpConverter() {
        return empConverter;
    }

    public void setEmpConverter(EmployeesConverter empConverter) {
        this.empConverter = empConverter;
    }

    public String getRe_enterPassword() {
        return re_enterPassword;
    }

    public void setRe_enterPassword(String re_enterPassword) {
        this.re_enterPassword = re_enterPassword;
    }

    public IRolesManager getRm() {
        return rm;
    }

    public void setRm(IRolesManager rm) {
        this.rm = rm;
    }

    public List<Roles> getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(List<Roles> allRoles) {
        this.allRoles = allRoles;
    }

    public List<Roles> getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(List<Roles> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public IUserRolesManager getUrm() {
        return urm;
    }

    public void setUrm(IUserRolesManager urm) {
        this.urm = urm;
    }

    /**
     * Employee search list for autocomplete
     */
    public List<Employees> employeeListForAutocomplete(String strSearchEmployeeCriteria) {
        try {
            HibernateUtil.beginTransaction();
            if (getEmployeeSearchList() != null) {
                getEmployeeSearchList().clear();
            }
            employeeSearchList = em.searchEmployeeForCreateUser(strSearchEmployeeCriteria);
            empConverter.setSearchEmployees(employeeSearchList);
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
        return employeeSearchList;
    }
    /*
     * Roles List for creating user
     */

    public void retrieveRolesListForCreateUser() {
        try {
            HibernateUtil.beginTransaction();
            if (getAllRoles() != null) {
                getAllRoles().clear();
            }
            allRoles = rm.retrieveRolesExceptXXXForCreateUser();

            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
    }
    /*
     * Create new user
     */

    public void createUser() {
        try {
            HibernateUtil.beginTransaction();
            if (selectedUser.getPassword().equals(re_enterPassword)) {
                if (selectedRoles.isEmpty()) {
                    String errorMsg = "Please select at least one role to create user.";
                    FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            errorMsg, "");
                    FacesContext.getCurrentInstance().addMessage(null, error);
                    return;
                } else {
                    if (!um.isUsernameAvaliable(selectedUser.getUsername())) {
                        String errorMsg = "User name is already present, please choose other user name.";
                        FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                errorMsg, "");
                        FacesContext.getCurrentInstance().addMessage(null, error);
                        return;
                    }
                }


                UserRoles ur;
                for (int p = 0; p < selectedRoles.size(); p++) {
                    ur = new UserRoles();
                    ur.setRoles(selectedRoles.get(p));
                    ur.setUsers(selectedUser);
                    ur.setIsDefault(Boolean.FALSE);
                    ur.setStartDate(new Date());
                    selectedUser.getUserRolesSet().add(ur);
                    ur = null;
                }
                selectedUser.setPasswordchangedon(new Timestamp(new Date().getTime()));
                selectedUser.setSalt("");
                selectedUser.setEmployees(selectedEmployee);
                um.saveNew(selectedUser);
                HibernateUtil.commitTransaction();
                String successMsg = "User added successfully.";
                FacesMessage success = new FacesMessage(successMsg, "");
                FacesContext.getCurrentInstance().addMessage(null, success);
                try {
                    Thread.sleep(3000);

                } catch (Exception e) {
                }System.out.println("11111111122222222222222");
                FacesContext.getCurrentInstance().getExternalContext().redirect("createUser.xhtml");
                System.out.println("33333333333333344444444444444444444");
                return;

            } else {
                String errorMsg = "Passwords do not match";
                FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        errorMsg, "");
                FacesContext.getCurrentInstance().addMessage(null, error);
            }


        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            System.out.println("In finally of userview.createuser");
            HibernateUtil.closeSession();

        }
    }
}
