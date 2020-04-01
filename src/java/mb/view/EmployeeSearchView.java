/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.ProjectsConverter;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
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
import manager.impl.EmployeesManagerImpl;
import manager.impl.ProjectsManagerImpl;
import manager.interfaces.IEmployeesManager;
import manager.interfaces.IProjectsManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;
import orm.Employees;
import orm.Projects;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "employeeSearchView")
@ViewScoped
public class EmployeeSearchView implements Serializable {

    /*
     Loginbean Inject 
     
     */
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    /**
     * Creates a new instance of EmployeeSearchView
     */
    /*
     Employee 
     
     */
    private IEmployeesManager em = new EmployeesManagerImpl();
    private List<Employees> employeeList;
    private Employees selectedEmployee = new Employees();
    private Employees searchEmployee = new Employees();
    private String selectedEmployeeStatus;
    /*
     Project 
     
     */
    private IProjectsManager pm = new ProjectsManagerImpl();
    private Projects selectedProject = new Projects();
    private List<Projects> projectsList;
    private ProjectsConverter projectsConverter = new ProjectsConverter();
    /*
     Fileupload 
     
     */
    private UploadedFile file;
    private static final int IMG_WIDTH = 100;
    private static final int IMG_HEIGHT = 100;
    private static final int IMG_WIDTHs = 50;
    private static final int IMG_HEIGHTs = 50;
    /*
     EmployeeSearchView constructor 
     
     */

    public EmployeeSearchView() {
        System.out.println("EmployeeSearchView called....");
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                HibernateUtil.beginTransaction();
                projectsList = pm.loadAll(Projects.class);
                //projectsList = pm.retrieveProjectList();
                projectsConverter.setSearchProjects(projectsList);
                HibernateUtil.commitTransaction();
            }
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public Employees getSearchEmployee() {
        return searchEmployee;
    }

    public void setSearchEmployee(Employees searchEmployee) {
        this.searchEmployee = searchEmployee;
    }

    public void setSelectedProject(Projects selectedProject) {
        this.selectedProject = selectedProject;
    }

    public Projects getSelectedProject() {
        return selectedProject;
    }

    public List<Projects> getProjectsList() {
        return projectsList;
    }

    public ProjectsConverter getProjectsConverter() {
        return projectsConverter;
    }

    public List<Employees> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employees> employeeList) {
        this.employeeList = employeeList;
    }

    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public IEmployeesManager getEm() {
        return em;
    }

    public void setEm(IEmployeesManager em) {
        this.em = em;
    }

    public String getSelectedEmployeeStatus() {
        return selectedEmployeeStatus;
    }

    public void setSelectedEmployeeStatus(String selectedEmployeeStatus) {
        this.selectedEmployeeStatus = selectedEmployeeStatus;
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void searchEmployees() {
        try {
            HibernateUtil.beginTransaction();
            if (getEmployeeList() != null) {
                getEmployeeList().clear();
            }
            setEmployeeList(em.searchByNameStatusProject(getSearchEmployee(),
                    getSelectedEmployeeStatus(), getSelectedProject()));
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void setSelectedEmployeeNewToAdd() {
        this.setSelectedEmployee(null);
        this.setSelectedEmployee(new Employees());
    }

    public void addEmployees() {
        try {
            HibernateUtil.beginTransaction();
            FacesContext context = FacesContext.getCurrentInstance();
            RequestContext req = RequestContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
            em.saveNew(selectedEmployee);
            HibernateUtil.commitTransaction();
            FacesMessage msg = new FacesMessage(text.getString("ui.Bean.Employee") + " "
                    + this.selectedEmployee.getFirstname() + " " + this.selectedEmployee.getLastname() + " " + text.getString("ui.Bean.AddSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            req.update("frmSearch:growlmsg");
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void updateEmployee() {
        try {
            HibernateUtil.beginTransaction();
            FacesContext context = FacesContext.getCurrentInstance();
            RequestContext req = RequestContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
            em.updateExisting(this.selectedEmployee);
            HibernateUtil.commitTransaction();
            FacesMessage msg = new FacesMessage(text.getString("ui.Bean.Employee") + " "
                    + this.selectedEmployee.getFirstname() + " " + this.selectedEmployee.getLastname() + " " + text.getString("ui.Bean.UpdateSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            req.update("frmSearch:growlmsg");
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }

    }

    // Resizing the image size. 
    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();
        return resizedImage;
    }

// Resizing the image size.
    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type) {

        BufferedImage resizedImage = new BufferedImage(IMG_WIDTHs, IMG_HEIGHTs, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTHs, IMG_HEIGHTs, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
    }
}
