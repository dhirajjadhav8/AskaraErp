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
import orm.Activities;
import orm.Tasks;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "taskDAO")
@ViewScoped
public class TaskDAO implements Serializable {
    
    private List<Tasks> allTasks;
    private List<Activities> allActivities;
    private Tasks selectedTask;
    private Activities selectedActivity;
    private Tasks selectTaskToUpdate;
    
    public List<Tasks> getAllTasks() {
        return allTasks;
    }
    
    public void setAllTasks(List<Tasks> allTasks) {
        this.allTasks = allTasks;
    }
    
    public List<Activities> getAllActivities() {
        return allActivities;
    }
    
    public void setAllActivities(List<Activities> allActivities) {
        this.allActivities = allActivities;
    }
    
    public Tasks getSelectedTask() {
        return selectedTask;
    }
    
    public void setSelectedTask(Tasks selectedTask) {
        this.selectedTask = selectedTask;
    }
    
    public Activities getSelectedActivity() {
        return selectedActivity;
    }
    
    public void setSelectedActivity(Activities selectedActivity) {
        this.selectedActivity = selectedActivity;
    }
    
    public Tasks getSelectTaskToUpdate() {
        return selectTaskToUpdate;
    }
    
    public void setSelectTaskToUpdate(Tasks selectTaskToUpdate) {
        this.selectTaskToUpdate = selectTaskToUpdate;
    }

    /**
     * Creates a new instance of TaskDAO
     */
    public TaskDAO() {
        this.allActivities = new ArrayList<Activities>();
        this.allTasks = new ArrayList<Tasks>();
        this.selectTaskToUpdate = new Tasks();
        this.selectedActivity = new Activities();
        this.selectedTask = new Tasks();
        
        System.out.println(" New TaskDAO is created......");
    }
    
    public void retrieveAllTasks() {
        Session session = null;
        Transaction trx = null;
        Criteria cr = null;
        this.allTasks.clear();
        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            
            cr = session.createCriteria(Tasks.class, "task")
                    .createAlias("task.activities", "activities", JoinType.INNER_JOIN)
                    .add(Restrictions.isNull("task.actualDate"));
            cr.setMaxResults(50);
            this.allTasks.addAll(cr.list());
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
    
    public void retrieveAllActivities() {
        
        Session session = null;
        Transaction trx = null;
        Criteria cr = null;
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                
                this.allActivities.clear();
                
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                
                cr = session.createCriteria(Activities.class, "activities");
                cr.setMaxResults(50);
                this.allActivities.addAll(cr.list());
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
    
    public void addTasks() {
        
        Session session = null;
        Transaction trx = null;
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        
        Date date = new Date();
        
        try {
            
            if (this.selectedTask.getTargetDate().compareTo(date) < 0) {
                
                String message = text.getString("ui.Bean.TargetDateGreatorThanCurrDate");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            } else {
                
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                Login loginDAO = (Login) context.getApplication().createValueBinding("#{loginbean}").getValue(context);
                
                int userId = loginDAO.getLoggedinUser().getId();
                
                this.selectedTask.setCreatedby(userId);
                this.selectedTask.setCreatedon(new Timestamp(date.getTime()));
                
                this.selectedTask.setActivities(this.getSelectedActivity());
                
                session.save(this.getSelectedTask());
                
                trx.commit();
                
                String message = text.getString("ui.Bean.TaskAddsuccess");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                
                this.setSelectedActivity(null);
                this.setSelectedActivity(new Activities());
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
    
    public void updateTask(){
        Session session = null;
        Transaction trx = null;
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        
        try{
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally{
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
