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
import javax.print.attribute.standard.DateTimeAtCompleted;
import org.apache.derby.client.am.DateTime;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;
import orm.Employees;
import orm.Todos;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "todosDAO")
@ViewScoped
public class TodosDAO implements Serializable {

    private List<Todos> allTodos;
    private List<Todos> allTodosHistory;
    private Todos selectedTodo;
    private Todos selectedTodoToUpdate;
    
    public List<Todos> getAllTodos() {
        return allTodos;
    }

    public void setAllTodos(List<Todos> allTodos) {
        this.allTodos = allTodos;
    }

    public List<Todos> getAllTodosHistory() {
        return allTodosHistory;
    }

    public void setAllTodosHistory(List<Todos> allTodosHistory) {
        this.allTodosHistory = allTodosHistory;
    }

    public Todos getSelectedTodo() {
        return selectedTodo;
    }

    public void setSelectedTodo(Todos selectedTodo) {
        this.selectedTodo = selectedTodo;
    }

    public Todos getSelectedTodoToUpdate() {
        return selectedTodoToUpdate;
    }

    public void setSelectedTodoToUpdate(Todos selectedTodoToUpdate) {
        this.selectedTodoToUpdate = selectedTodoToUpdate;
    }

    

    /**
     * Creates a new instance of TodosDAO
     */
    public TodosDAO() {

        this.allTodos = new ArrayList<Todos>();
        this.allTodosHistory = new ArrayList<Todos>();
        this.selectedTodo = new Todos();
        this.selectedTodoToUpdate = new Todos();

        System.out.println("New TodosDAO is created......");
    }

    public void retrieveAllTodos() {
        Session session = null;
        Transaction trx = null;

        Criteria cr = null;
        this.allTodos.clear();
        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            cr = session.createCriteria(Todos.class, "todos")
                    .add(Restrictions.eq("todos.iscompleted", false))
                    .addOrder(Order.asc("todos.todoDate"));
            this.allTodos.addAll(cr.list());
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

    public void addTodo() {

        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        Criteria cr = null;

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            Login loginbean = (Login) context.getApplication().createValueBinding("#{loginbean}").getValue(context);

            cr = session.createCriteria(Employees.class, "emp")
                    .add(Restrictions.eq("emp.id", loginbean.getLoggedinUser().getEmployees().getId()));

            this.selectedTodo.setTodofor((Employees) cr.list().get(0));
            this.selectedTodo.setCreatedby(loginbean.getLoggedinUser().getId());

            Date date = new Date();

            this.selectedTodo.setCreatedon(new Timestamp(date.getTime()));

            session.save(this.selectedTodo);

            trx.commit();

            FacesMessage message = new FacesMessage(text.getString("ui.msg.addTodoSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, message);

            this.setSelectedTodo(null);
            this.setSelectedTodo(new Todos());

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

    public void todoToUpdate() {
        this.setSelectedTodo(this.getSelectedTodoToUpdate());
        RequestContext.getCurrentInstance().update(":frmAddUpdTodos");
    }

    public void finishUploading() {
        this.setSelectedTodo(null);
        this.setSelectedTodo(new Todos());
        this.setSelectedTodoToUpdate(null);
        this.setSelectedTodoToUpdate(new Todos());
        RequestContext.getCurrentInstance().update(":frmTodotbl");
        RequestContext.getCurrentInstance().update(":frmAddUpdTodos");
    }

    public void updateTodo() {
        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            this.setSelectedTodoToUpdate(this.getSelectedTodo());
            session.update(this.selectedTodoToUpdate);

            trx.commit();
            FacesMessage message = new FacesMessage(text.getString("ui.msg.updateTodoSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, message);

            this.setSelectedTodoToUpdate(null);
            this.setSelectedTodoToUpdate(new Todos());

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

    public void retrieveTodoHistory() {
        Session session = null;
        Transaction trx = null;

        Criteria cr = null;

        this.allTodosHistory.clear();

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            cr = session.createCriteria(Todos.class, "todos")
                    .add(Restrictions.eq("todos.iscompleted", true))
                    .addOrder(Order.asc("todos.todoDate"));

            this.allTodosHistory.addAll(cr.list());
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

    public String getColumnStyleClass(String priority) {
        if ("L".equals(priority)) {
            return "yellow";
        } else if ("M".equals(priority)) {
            return "lightgreen";
        } else if ("H".equals(priority)) {
            return "tomato";
        } else if ("I".equals(priority)) {
            return "red";
        }
        return null;
    }

    public void updateTodoIscomplete() {

        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            this.selectedTodoToUpdate.setIscompleted(true);
            Date date = new Date();

            this.selectedTodoToUpdate.setCompleteDate(new Timestamp(date.getTime()));
            session.update(this.selectedTodoToUpdate);

            trx.commit();
            FacesMessage message = new FacesMessage(text.getString("ui.msg.updateTodoSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, message);

            this.setSelectedTodoToUpdate(null);
            this.setSelectedTodoToUpdate(new Todos());

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
