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
import org.hibernate.sql.JoinType;
import orm.Activities;
import orm.Milestones;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "activitiesDAO")
@ViewScoped
public class ActivitiesDAO implements Serializable {

    private List<Activities> allActivities;
    private List<Milestones> allMilestones;
    private Activities selectedActivity;
    private Milestones selectedMilestone;

    public List<Activities> getAllActivities() {
        return allActivities;
    }

    public void setAllActivities(List<Activities> allActivities) {
        this.allActivities = allActivities;
    }

    public List<Milestones> getAllMilestones() {
        return allMilestones;
    }

    public void setAllMilestones(List<Milestones> allMilestones) {
        this.allMilestones = allMilestones;
    }

    public Activities getSelectedActivity() {
        return selectedActivity;
    }

    public void setSelectedActivity(Activities selectedActivity) {
        this.selectedActivity = selectedActivity;
    }

    public Milestones getSelectedMilestone() {
        return selectedMilestone;
    }

    public void setSelectedMilestone(Milestones selectedMilestone) {
        this.selectedMilestone = selectedMilestone;
    }

    /**
     * Creates a new instance of ActivitiesDAO
     */
    public ActivitiesDAO() {

        this.allActivities = new ArrayList<Activities>();
        this.allMilestones = new ArrayList<Milestones>();
        this.selectedActivity = new Activities();
        this.selectedMilestone = new Milestones();

        System.out.println(" New ActivitiesDAO is created......");
    }

    public void retrieveAllMilestones() {
        Session session = null;
        Transaction trx = null;
        Criteria cr = null;
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {

                this.allMilestones.clear();
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                cr = session.createCriteria(Milestones.class, "milestone");
                cr.setMaxResults(50);


                this.allMilestones.addAll(cr.list());
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

    public void retrieveAllActivities() {

        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        this.allActivities.clear();
        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            cr = session.createCriteria(Activities.class, "activities")
                    .createAlias("activities.milestones", "milestones", JoinType.INNER_JOIN);
            cr.setMaxResults(50);
            this.allActivities.addAll(cr.list());
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

    public void addActivity() {
        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        Date date = new Date();

        try {
            if (this.selectedActivity.getTargetDate().compareTo(date) < 0) {

                String message = text.getString("ui.Bean.TargetDateGreatorThanCurrDate");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

            } else {
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                Login loginDAO = (Login) context.getApplication().createValueBinding("#{loginbean}").getValue(context);

                int userId = loginDAO.getLoggedinUser().getId();
                this.selectedActivity.setCreatedby(userId);
                this.selectedActivity.setCreatedon(new Timestamp(date.getTime()));

                this.selectedActivity.setMilestones(this.getSelectedMilestone());

                session.save(this.getSelectedActivity());

                trx.commit();

                String message = text.getString("ui.Bean.ActivityAddsuccess");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));

                this.setSelectedActivity(null);
                this.setSelectedActivity(new Activities());
                this.setSelectedMilestone(null);
                this.setSelectedMilestone(new Milestones());
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
