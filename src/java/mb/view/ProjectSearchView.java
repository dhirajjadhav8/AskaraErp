/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.ProjectsConverter;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.ProjectsManagerImpl;
import manager.interfaces.IProjectsManager;
import orm.Projects;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "projectSearchView")
@ViewScoped
public class ProjectSearchView implements Serializable {

    private IProjectsManager pm = new ProjectsManagerImpl();
    private Projects selectedProject = new Projects();
    private List<Projects> projectList;
    private ProjectsConverter projectsConverter = new ProjectsConverter();

    /**
     * Creates a new instance of ProjectSearchView
     */
    public ProjectSearchView() {
        System.gc();
        projectsConverter.setSearchProjects(this.getProjectList());
    }

    public IProjectsManager getPm() {
        return pm;
    }

    public void setPm(IProjectsManager pm) {
        this.pm = pm;
    }

    public Projects getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Projects selectedProject) {
        this.selectedProject = selectedProject;
    }

    public List<Projects> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Projects> projectList) {
        this.projectList = projectList;
    }

    public ProjectsConverter getProjectsConverter() {
        return projectsConverter;
    }

    public void setProjectsConverter(ProjectsConverter projectsConverter) {
        this.projectsConverter = projectsConverter;
    }

    public void retrieveProjects() {
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                HibernateUtil.beginTransaction();
                if (getProjectList() != null) {
                    this.getProjectList().clear();
                }
                setProjectList(pm.retrieveProjectList());
                HibernateUtil.commitTransaction();
            }
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
    }
}
