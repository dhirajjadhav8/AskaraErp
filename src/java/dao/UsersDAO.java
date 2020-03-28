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
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import orm.Users;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "usersDAO")
@RequestScoped
public class UsersDAO implements Serializable {

    private List<Users> allUsers;
    private Users selectedUser;
    
    private Boolean isAvailable;

    public List<Users> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<Users> allUsers) {
        this.allUsers = allUsers;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
        
    /**
     * Creates a new instance of UsersDAO
     */
    public UsersDAO() {

        this.allUsers = new ArrayList<Users>();
        this.selectedUser = new Users();

        System.out.println(" New UserDAO is created......");
        
        this.setIsAvailable(false);

    }

    public void retrieveAllUsers() {

        Session session = null;
        Transaction trx = null;

        Criteria cr = null;

        this.allUsers.clear();

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            cr = session.createCriteria(Users.class, "user");

            this.allUsers.addAll(cr.list());

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

    public void addUser() {

        Session session = null;
        Transaction trx = null;

        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            Date date = new Date();

            FacesContext facesContext = FacesContext.getCurrentInstance();

            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int userId = loginDAO.getLoggedinUser().getId();

            EmployeesDAO employeesDAO = (EmployeesDAO) facesContext.getApplication().createValueBinding("#{employeesDAO}").getValue(facesContext);


            this.selectedUser.setCreatedby(userId);
            this.selectedUser.setCreatedon(new Timestamp(date.getTime()));
            this.selectedUser.setEmployees(employeesDAO.getSelectedEmployee());
            session.save(this.selectedUser);

            trx.commit();

            String message = text.getString("ui.Bean.UserAddSuccess");

            RequestContext req = RequestContext.getCurrentInstance();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));


            req.update("growlmsg");

            this.setSelectedUser(null);
            this.setSelectedUser(new Users());

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

    public void updateUser() {

        Session session = null;
        Transaction trx = null;

        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            Date date = new Date();

            this.selectedUser.setPasswordchangedon(new Timestamp(date.getTime()));

            session.update(this.selectedUser);

            trx.commit();

            String message = text.getString("ui.Bean.UserUpdateSuccess");

            RequestContext req = RequestContext.getCurrentInstance();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));


            req.update("growlmsg");

            this.setSelectedUser(null);
            this.setSelectedUser(new Users());

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

    public void checkUserExist() {
        Session session = null;
        Transaction trx = null;
        Query q = null;
        try {
            
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            EmployeesDAO employeesDAO = (EmployeesDAO) facesContext.getApplication().createValueBinding("#{employeesDAO}").getValue(facesContext);
            
            
            
            String hqlQuery = "from Users u where u.employees.id = "+employeesDAO.getSelectedEmployee().getId();
            
            q = session.createQuery(hqlQuery);
            
            if(q.list().isEmpty()){
                this.setIsAvailable(true);
            }
            
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
}
