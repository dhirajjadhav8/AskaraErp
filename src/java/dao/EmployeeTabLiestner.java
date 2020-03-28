/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "employeeTabLiestner")
@ViewScoped
public class EmployeeTabLiestner {

    /**
     * Creates a new instance of EmployeeTabLiestner
     */
    public EmployeeTabLiestner() {
    }

    public void onTabChange(TabChangeEvent event) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        EmployeeDesignationsDAO employeeDesignationsDAO = (EmployeeDesignationsDAO) facesContext.getApplication().createValueBinding("#{employeeDesignationsDAO}").getValue(facesContext);

        EmployeeDetailsDAO employeeDetailsDAO = (EmployeeDetailsDAO) facesContext.getApplication().createValueBinding("#{employeeDetailsDAO}").getValue(facesContext);
        UsersDAO usersDAO = (UsersDAO) facesContext.getApplication().createValueBinding("#{usersDAO}").getValue(facesContext);

        System.out.println(event.getTab().getTitle());

        if (event.getTab().getTitle().equals("Employement Details")) {

            employeeDetailsDAO.retrieveEmpDetailsforAdd();

        } else if (event.getTab().getTitle().equals("Employee Designation")) {
            employeeDesignationsDAO.retrieveEmployeeDesignationDetails();

        } else if (event.getTab().getTitle().equals("UserName/Password")) {
            usersDAO.checkUserExist();
        }

    }
}
