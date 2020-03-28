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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import orm.Subtasks;
import orm.Tasks;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "subTasksDAO")
@ViewScoped
public class SubTasksDAO implements Serializable {

    private List<Subtasks> allSubTasks;
    private List<Tasks> allTasks;
    private Subtasks selectedSubTask;
    private Tasks selectedTask;
    private Subtasks selectedSubTaskToUpdate;

    public List<Subtasks> getAllSubTasks() {
        return allSubTasks;
    }

    public void setAllSubTasks(List<Subtasks> allSubTasks) {
        this.allSubTasks = allSubTasks;
    }

    public List<Tasks> getAllTasks() {
        return allTasks;
    }

    public void setAllTasks(List<Tasks> allTasks) {
        this.allTasks = allTasks;
    }

    public Subtasks getSelectedSubTask() {
        return selectedSubTask;
    }

    public void setSelectedSubTask(Subtasks selectedSubTask) {
        this.selectedSubTask = selectedSubTask;
    }

    public Tasks getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Tasks selectedTask) {
        this.selectedTask = selectedTask;
    }

    public Subtasks getSelectedSubTaskToUpdate() {
        return selectedSubTaskToUpdate;
    }

    public void setSelectedSubTaskToUpdate(Subtasks selectedSubTaskToUpdate) {
        this.selectedSubTaskToUpdate = selectedSubTaskToUpdate;
    }

    /**
     * Creates a new instance of SubTasksDAO
     */
    public SubTasksDAO() {
        this.allSubTasks = new ArrayList<Subtasks>();
        this.allTasks = new ArrayList<Tasks>();
        this.selectedSubTask = new Subtasks();
        this.selectedSubTaskToUpdate = new Subtasks();
        this.selectedTask = new Tasks();

        System.out.println(" New SubTasksDAO is created......");
    }

    public void retrieveAllTasks() {
        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                this.allTasks.clear();
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                cr = session.createCriteria(Tasks.class, "task");
                cr.setMaxResults(50);
                this.allTasks.addAll(cr.list());
                trx.commit();
            }

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

    public void retrieveAllSubTasks() {

        Session session = null;
        Transaction trx = null;
        Criteria cr = null;
        this.allSubTasks.clear();

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            cr = session.createCriteria(Subtasks.class, "subtasks")
                    .createAlias("subtasks.tasks", "tasks", JoinType.INNER_JOIN)
                    .add(Restrictions.isNull("subtasks.actualDate"));

            cr.setMaxResults(50);
            this.allSubTasks.addAll(cr.list());
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

    public void addSubTask() {

        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        Date date = new Date();

        try {
            if (this.selectedSubTask.getTargetDate().compareTo(date) < 0) {

                String message = text.getString("ui.Bean.TargetDateGreatorThanCurrDate");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            } else {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                Login loginDAO = (Login) context.getApplication().createValueBinding("#{loginbean}").getValue(context);

                int userId = loginDAO.getLoggedinUser().getId();
                this.selectedSubTask.setCreatedby(userId);
                this.selectedSubTask.setCreatedon(new Timestamp(date.getTime()));

                this.selectedSubTask.setTasks(this.getSelectedTask());

                session.save(this.selectedSubTask);

                trx.commit();

                String message = text.getString("ui.Bean.subTaskAddsuccess");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));

                this.setSelectedSubTask(null);
                this.setSelectedSubTask(new Subtasks());
                this.setSelectedTask(null);
                this.setSelectedTask(new Tasks());
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
