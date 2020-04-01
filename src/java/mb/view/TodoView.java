/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.TodosManagerImpl;
import manager.interfaces.ITodosManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import orm.Todos;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "todoView")
@ViewScoped
public class TodoView implements Serializable {

    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    private ITodosManager tm = new TodosManagerImpl();

    /*
     Todo 
     
     */
    private Todos selectedTodo = new Todos();
    private Todos updateSelectedTodo;
    private List<Todos> todoList;
    private List<Todos> todoHistoryList;

    /**
     * Creates a new instance of TodoView
     */
    public TodoView() {
    }

    public ITodosManager getTm() {
        return tm;
    }

    public void setTm(ITodosManager tm) {
        this.tm = tm;
    }

    public Todos getSelectedTodo() {
        return selectedTodo;
    }

    public void setSelectedTodo(Todos selectedTodo) {
        this.selectedTodo = selectedTodo;
    }

    public List<Todos> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todos> todoList) {
        this.todoList = todoList;
    }

    public Todos getUpdateSelectedTodo() {
        return updateSelectedTodo;
    }

    public void setUpdateSelectedTodo(Todos updateSelectedTodo) {
        this.updateSelectedTodo = updateSelectedTodo;
    }

    public List<Todos> getTodoHistoryList() {
        return todoHistoryList;
    }

    public void setTodoHistoryList(List<Todos> todoHistoryList) {
        this.todoHistoryList = todoHistoryList;
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public void retrieveTodos() {
        try {
            HibernateUtil.beginTransaction();
            if (getTodoList() != null) {
                getTodoList().clear();
            }
            setTodoList(tm.retrieveNotCompletedTodos());
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void completeTodo() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
            HibernateUtil.beginTransaction();
            Date date = new Date();
            this.updateSelectedTodo.setCompleteDate(new Timestamp(date.getTime()));
            tm.completeTodo(getUpdateSelectedTodo());
            HibernateUtil.commitTransaction();
            FacesMessage message = new FacesMessage(text.getString("ui.msg.updateTodoSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            setUpdateSelectedTodo(null);

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void retrieveCompletedTodos() {
        try {
            HibernateUtil.beginTransaction();
            if (getTodoHistoryList() != null) {
                getTodoHistoryList().clear();
            }
            setTodoHistoryList(tm.retrieveCompletedTodos());
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
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

    public void addTodo() {
        try {
            HibernateUtil.beginTransaction();
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
            selectedTodo.setEmployees(loginbean.getLoggedinUser().getEmployees());
            selectedTodo.setIscompleted(Boolean.FALSE);
            tm.saveNew(selectedTodo);
            HibernateUtil.commitTransaction();
            FacesMessage message = new FacesMessage(text.getString("ui.msg.addTodoSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            this.setSelectedTodo(null);
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void todoToAdd() {
        setSelectedTodo(null);
        setSelectedTodo(new Todos());
        RequestContext.getCurrentInstance().update(":frmAddUpdTodos");
    }

    public void updateTodo() {
        try {
            HibernateUtil.beginTransaction();
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
            tm.updateExisting(selectedTodo);
            HibernateUtil.commitTransaction();
            FacesMessage message = new FacesMessage(text.getString("ui.msg.updateTodoSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }

    }
}
