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
import org.primefaces.context.RequestContext;
import orm.Milestones;
import orm.Projects;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "milestonesDAO")
@ViewScoped
public class MilestonesDAO implements Serializable {

    private List<Projects> allProjects;
    private List<Milestones> allMilestones;
    private Milestones selectedMilestone;
    private Projects selectedProject;

    public List<Projects> getAllProjects() {
        return allProjects;
    }

    public void setAllProjects(List<Projects> allProjects) {
        this.allProjects = allProjects;
    }

    public List<Milestones> getAllMilestones() {
        return allMilestones;
    }

    public void setAllMilestones(List<Milestones> allMilestones) {
        this.allMilestones = allMilestones;
    }

    public Milestones getSelectedMilestone() {
        return selectedMilestone;
    }

    public void setSelectedMilestone(Milestones selectedMilestone) {
        this.selectedMilestone = selectedMilestone;
    }

    public Projects getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Projects selectedProject) {
        this.selectedProject = selectedProject;
    }

    /**
     * Creates a new instance of MilestonesDAO
     */
    public MilestonesDAO() {

        this.allMilestones = new ArrayList<Milestones>();
        this.allProjects = new ArrayList<Projects>();
        this.selectedMilestone = new Milestones();
        this.selectedProject = new Projects();

        System.out.println(" New MilestonesDAO is created......");
    }

    public void retrieveMilestones() {
        Session session = null;
        Transaction trx = null;
        Criteria cr = null;
        this.allMilestones.clear();

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            cr = session.createCriteria(Milestones.class, "milestones")
                    .createAlias("milestones.projects", "projects", JoinType.INNER_JOIN);

            cr.setMaxResults(50);
            this.allMilestones.addAll(cr.list());
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

    public void retrieveAllProjects() {

        Session session = null;
        Transaction trx = null;
        Criteria cr = null;
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {

                this.allProjects.clear();
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                cr = session.createCriteria(Projects.class, "pro");

                cr.setMaxResults(50);

                this.allProjects.addAll(cr.list());
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

    public void addMilestone() {

        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        Date date = new Date();

        try {
            if (this.selectedMilestone.getTargetDate().compareTo(date) < 0) {

                String message = text.getString("ui.Bean.TargetDateGreatorThanCurrDate");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

            } else {
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                Login loginDAO = (Login) context.getApplication().createValueBinding("#{loginbean}").getValue(context);

                int userId = loginDAO.getLoggedinUser().getId();

                this.selectedMilestone.setCreatedby(userId);


                this.selectedMilestone.setCreatedon(new Timestamp(date.getTime()));

                this.selectedMilestone.setProjects(this.getSelectedProject());

                session.save(this.selectedMilestone);

                trx.commit();

                String message = text.getString("ui.Bean.MilestoneAddsuccess");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));

                this.setSelectedProject(null);
                this.setSelectedProject(new Projects());
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
