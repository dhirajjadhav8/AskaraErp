/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.LocationsConverter;
import converter.ProjectClientsConverter;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.LocationsManagerImpl;
import manager.impl.ProjectClientsManagerImpl;
import manager.impl.ProjectLocationsManagerImpl;
import manager.interfaces.ILocationsManager;
import manager.interfaces.IProjectClientsManager;
import manager.interfaces.IProjectLocationsManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import orm.Locations;
import orm.ProjectClients;
import orm.ProjectLocations;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "projectLocationView")
@ViewScoped
public class ProjectLocationView implements Serializable {

    //loginBean
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    //ProjectLocation
    private IProjectLocationsManager projectLocationsManager = new ProjectLocationsManagerImpl();
    private List<ProjectLocations> allActiveProjectLocations;
    private List<ProjectLocations> allProjectLocationHistory;
    private ProjectLocations selectedProjectLocation = new ProjectLocations();
    private ProjectLocations selectedProjectLocationToUpdate;
    //ProjectClients
    private IProjectClientsManager projectClientsManager = new ProjectClientsManagerImpl();
    private List<ProjectClients> allProjectClients;
    private ProjectClients selectedProjectCliets = new ProjectClients();
    private ProjectClientsConverter projectClientsConverter = new ProjectClientsConverter();
    //Locations
    private ILocationsManager locationManager = new LocationsManagerImpl();
    private LocationsConverter locationsConverter = new LocationsConverter();
    private List<Locations> allLocations;
    private Locations selectedLocation = new Locations();
    //To enable and disable fields at the time of  add and update
    boolean isEndDateEnable = true;
    boolean isEndReasonEnable = true;
    boolean isNotesEnable = true;

    /**
     * Creates a new instance of ProjectLocationView
     */
    public ProjectLocationView() {
    }

    @PostConstruct
    public void postConstruct() {
        loginbean.checkAccess();
        System.out.println("retrieveActiveProjectLocation");
        retrieveActiveProjectLocation();
        System.out.println("retrieveProjectLocationHistory");
        retrieveProjectLocationHistory();
        System.out.println("retrieveAllProjectsClient");
        retrieveAllProjectsClient();

    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public IProjectLocationsManager getProjectLocationsManager() {
        return projectLocationsManager;
    }

    public void setProjectLocationsManager(IProjectLocationsManager projectLocationsManager) {
        this.projectLocationsManager = projectLocationsManager;
    }

    public List<ProjectLocations> getAllActiveProjectLocations() {
        return allActiveProjectLocations;
    }

    public void setAllActiveProjectLocations(List<ProjectLocations> allActiveProjectLocations) {
        this.allActiveProjectLocations = allActiveProjectLocations;
    }

    public List<ProjectLocations> getAllProjectLocationHistory() {
        return allProjectLocationHistory;
    }

    public void setAllProjectLocationHistory(List<ProjectLocations> allProjectLocationHistory) {
        this.allProjectLocationHistory = allProjectLocationHistory;
    }

    public ProjectLocations getSelectedProjectLocation() {
        return selectedProjectLocation;
    }

    public void setSelectedProjectLocation(ProjectLocations selectedProjectLocation) {
        this.selectedProjectLocation = selectedProjectLocation;
    }

    public ProjectLocations getSelectedProjectLocationToUpdate() {
        return selectedProjectLocationToUpdate;
    }

    public void setSelectedProjectLocationToUpdate(ProjectLocations selectedProjectLocationToUpdate) {
        this.selectedProjectLocationToUpdate = selectedProjectLocationToUpdate;
    }

    public List<ProjectClients> getAllProjectClients() {
        return allProjectClients;
    }

    public void setAllProjectClients(List<ProjectClients> allProjectClients) {
        this.allProjectClients = allProjectClients;
    }

    public ProjectClients getSelectedProjectCliets() {
        return selectedProjectCliets;
    }

    public void setSelectedProjectCliets(ProjectClients selectedProjectCliets) {
        this.selectedProjectCliets = selectedProjectCliets;
    }

    public List<Locations> getAllLocations() {
        return allLocations;
    }

    public void setAllLocations(List<Locations> allLocations) {
        this.allLocations = allLocations;
    }

    public Locations getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(Locations selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public IProjectClientsManager getProjectClientsManager() {
        return projectClientsManager;
    }

    public void setProjectClientsManager(IProjectClientsManager projectClientsManager) {
        this.projectClientsManager = projectClientsManager;
    }

    public ProjectClientsConverter getProjectClientsConverter() {
        return projectClientsConverter;
    }

    public void setProjectClientsConverter(ProjectClientsConverter projectClientsConverter) {
        this.projectClientsConverter = projectClientsConverter;
    }

    public ILocationsManager getLocationManager() {
        return locationManager;
    }

    public void setLocationManager(ILocationsManager locationManager) {
        this.locationManager = locationManager;
    }

    public LocationsConverter getLocationsConverter() {
        return locationsConverter;
    }

    public void setLocationsConverter(LocationsConverter locationsConverter) {
        this.locationsConverter = locationsConverter;
    }

    public boolean isIsEndDateEnable() {
        return isEndDateEnable;
    }

    public void setIsEndDateEnable(boolean isEndDateEnable) {
        this.isEndDateEnable = isEndDateEnable;
    }

    public boolean isIsEndReasonEnable() {
        return isEndReasonEnable;
    }

    public void setIsEndReasonEnable(boolean isEndReasonEnable) {
        this.isEndReasonEnable = isEndReasonEnable;
    }

    public boolean isIsNotesEnable() {
        return isNotesEnable;
    }

    public void setIsNotesEnable(boolean isNotesEnable) {
        this.isNotesEnable = isNotesEnable;
    }

    //to retrieve all active project locations.
    public void retrieveActiveProjectLocation() {

        try {
            HibernateUtil.beginTransaction();
            allActiveProjectLocations = projectLocationsManager.retrieveActiveProjectLocation();
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    public void retrieveAllProjectsClient() {
        try {
            HibernateUtil.beginTransaction();
            allProjectClients = projectClientsManager.retrieveAllProjectClients();
            HibernateUtil.commitTransaction();
            projectClientsConverter.setSearchProjectContacts(allProjectClients);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //retrieve all not assigneded locations to projectClients after selecting project client.
    public void onselectProjectClientRetrieveLocation() {
        int locationid;
        if (getSelectedLocation().getId() == null) {
            locationid = 0;
        } else {
            locationid = getSelectedLocation().getId().intValue();
        }
        try {
            HibernateUtil.beginTransaction();
            allLocations = locationManager.retrieveUnasignedLocationsByProjectClientId(getSelectedProjectCliets().getId(), locationid);
            HibernateUtil.commitTransaction();
//            locationsConverter.setSearchLocations(allLocations);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    //to disable endDate,endReason and notes fields on add
    public void onAddDisableSelectedFields() {

        this.setIsEndDateEnable(true);
        this.setIsEndReasonEnable(true);
        this.setIsNotesEnable(true);

    }

    public void onUpdateSelect() {
        setSelectedProjectLocation(getSelectedProjectLocationToUpdate());
        setSelectedProjectCliets(getSelectedProjectLocationToUpdate().getProjectClients());

        setSelectedLocation(getSelectedProjectLocationToUpdate().getLocations());
        onselectProjectClientRetrieveLocation();
        setIsEndDateEnable(false);
        setIsEndReasonEnable(false);
        setIsNotesEnable(false);
        RequestContext.getCurrentInstance().update(":frmOrgLocAdd:PnlOrgLocAdd");

    }

    public void onAddSelect() {
//      allLocations.clear();
        selectedProjectLocation = null;
        selectedProjectLocation = new ProjectLocations();
        selectedLocation = null;
        selectedLocation = new Locations();
        selectedProjectCliets = null;
        selectedProjectCliets = new ProjectClients();



    }

    public void addProjectLocation() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

            if (selectedProjectLocation.getStartDate() != null) {
                selectedProjectLocation.setProjectClients(getSelectedProjectCliets());
                selectedProjectLocation.setLocations(getSelectedLocation());
                HibernateUtil.beginTransaction();
                projectLocationsManager.saveNew(selectedProjectLocation);
                HibernateUtil.commitTransaction();
                String message = text.getString("ui.Bean.ProLocationAddSuccess");
                retrieveActiveProjectLocation();
                RequestContext req = RequestContext.getCurrentInstance();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));

                selectedProjectLocation = null;
                selectedProjectLocation = new ProjectLocations();
                selectedLocation = null;
                selectedLocation = new Locations();
                selectedProjectCliets = null;
                selectedProjectCliets = new ProjectClients();

            } else {
                String message = text.getString("ui.Bean.StartDate");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    public void updateProjectLocation() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
            if (getSelectedProjectLocationToUpdate().getEndDate() != null) {

                if (getSelectedProjectLocationToUpdate().getEndReason().equals("")
                        || getSelectedProjectLocationToUpdate().getEndReason() == null) {
                    String message = text.getString("ui.Bean.EndReason");

                    RequestContext req = RequestContext.getCurrentInstance();

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                } else {

                    if (selectedProjectLocationToUpdate.getStartDate().compareTo(selectedProjectLocationToUpdate.getEndDate()) <= 0) {

                        HibernateUtil.beginTransaction();
                        projectLocationsManager.updateExisting(selectedProjectLocationToUpdate);
                        HibernateUtil.commitTransaction();

                        String message = text.getString("ui.Bean.OrgLocationUpdateSuccess");

                        RequestContext req = RequestContext.getCurrentInstance();

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                        selectedProjectLocationToUpdate = null;
                        selectedProjectLocationToUpdate = new ProjectLocations();
                        selectedLocation = null;
                        selectedLocation = new Locations();
                        selectedProjectCliets = null;
                        selectedProjectCliets = new ProjectClients();
                       retrieveActiveProjectLocation();
                        retrieveProjectLocationHistory();
                        RequestContext.getCurrentInstance().execute("DlgConfirmProLoc.hide();");
                        RequestContext.getCurrentInstance().update(":frmProjectLocationTable:tblProjectLocation");

                    } else {
                        String message = text.getString("ui.Bean.EnddateGreterThanStartDate");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                    }

                }

            } else {

                String message = text.getString("ui.Bean.EndDate");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    //to retrieve projectlocations history
    public void retrieveProjectLocationHistory() {
        try {
            HibernateUtil.beginTransaction();
            allProjectLocationHistory = projectLocationsManager.retrieveProjectLocationHistory();
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
