/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.LocationsConverter;
import converter.OrganizationsConverter;
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
import manager.impl.OrganizationLocationsManagerImpl;
import manager.impl.OrganizationsManagerImpl;
import manager.interfaces.ILocationsManager;
import manager.interfaces.IOrganizationLocationsManager;
import manager.interfaces.IOrganizationsManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import orm.Locations;
import orm.OrganizationLocations;
import orm.Organizations;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "organizationLocationView")
@ViewScoped
public class OrganizationLocationView implements Serializable {

    //loginBean
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    // Organizations
    private Organizations selectedOrganization = new Organizations();
    private List<Organizations> allOrganizations;
    private OrganizationsConverter organizationsConverter = new OrganizationsConverter();
    private IOrganizationsManager organizationsManager = new OrganizationsManagerImpl();
    //Locations
    private Locations selectedLocation = new Locations();
    private List<Locations> allLocations;
    private LocationsConverter locationsConverter = new LocationsConverter();
    private ILocationsManager locationManager = new LocationsManagerImpl();
    //OrganizationLocations
    private OrganizationLocations selectedOrganizationLocation = new OrganizationLocations();
    private List<OrganizationLocations> allOrganisationLocations;
    private OrganizationLocations selectedOrganizationLocationUpdate;
    private List<OrganizationLocations> allOrganizationLocationHistory;
    private IOrganizationLocationsManager organizaionLocationManager = new OrganizationLocationsManagerImpl();

    //To enable and disable fields at the time of  add and update
    boolean isEndDateEnable = true;
    boolean isEndReasonEnable = true;
    boolean isNotesEnable = true;
    /**
     * Creates a new instance of OrganizationLocationView
     */
    public OrganizationLocationView() {
    }

    @PostConstruct
    public void postConstruct() {
        loginbean.checkAccess();
        retrieveAllActiveOrganizationLocation();
        retrieveOrganizationLocationHistory();
        retrieveAllOrganizations();

    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public Organizations getSelectedOrganization() {
        return selectedOrganization;
    }

    public void setSelectedOrganization(Organizations selectedOrganization) {
        this.selectedOrganization = selectedOrganization;
    }

    public List<Organizations> getAllOrganizations() {
        return allOrganizations;
    }

    public void setAllOrganizations(List<Organizations> allOrganizations) {
        this.allOrganizations = allOrganizations;
    }

    public Locations getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(Locations selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public List<Locations> getAllLocations() {
        return allLocations;
    }

    public void setAllLocations(List<Locations> allLocations) {
        this.allLocations = allLocations;
    }

    public OrganizationLocations getSelectedOrganizationLocation() {
        return selectedOrganizationLocation;
    }

    public void setSelectedOrganizationLocation(OrganizationLocations selectedOrganizationLocation) {
        this.selectedOrganizationLocation = selectedOrganizationLocation;
    }

    public List<OrganizationLocations> getAllOrganisationLocations() {
        return allOrganisationLocations;
    }

    public void setAllOrganisationLocations(List<OrganizationLocations> allOrganisationLocations) {
        this.allOrganisationLocations = allOrganisationLocations;
    }

    public OrganizationLocations getSelectedOrganizationLocationUpdate() {
        return selectedOrganizationLocationUpdate;
    }

    public void setSelectedOrganizationLocationUpdate(OrganizationLocations selectedOrganizationLocationUpdate) {
        this.selectedOrganizationLocationUpdate = selectedOrganizationLocationUpdate;
    }

    public List<OrganizationLocations> getAllOrganizationLocationHistory() {
        return allOrganizationLocationHistory;
    }

    public void setAllOrganizationLocationHistory(List<OrganizationLocations> allOrganizationLocationHistory) {
        this.allOrganizationLocationHistory = allOrganizationLocationHistory;
    }

    public OrganizationsConverter getOrganizationsConverter() {
        return organizationsConverter;
    }

    public void setOrganizationsConverter(OrganizationsConverter organizationsConverter) {
        this.organizationsConverter = organizationsConverter;
    }

    public IOrganizationsManager getOrganizationsManager() {
        return organizationsManager;
    }

    public void setOrganizationsManager(IOrganizationsManager organizationsManager) {
        this.organizationsManager = organizationsManager;
    }

    public LocationsConverter getLocationsConverter() {
        return locationsConverter;
    }

    public void setLocationsConverter(LocationsConverter locationsConverter) {
        this.locationsConverter = locationsConverter;
    }

    public ILocationsManager getLocationManager() {
        return locationManager;
    }

    public void setLocationManager(ILocationsManager locationManager) {
        this.locationManager = locationManager;
    }

    public IOrganizationLocationsManager getOrganizaionLocation() {
        return organizaionLocationManager;
    }

    public void setOrganizaionLocation(IOrganizationLocationsManager organizaionLocationManager) {
        this.organizaionLocationManager = organizaionLocationManager;
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

    
    //to retrieve all active Organization Locations
    public void retrieveAllActiveOrganizationLocation() {

        try {

            HibernateUtil.beginTransaction();

            allOrganisationLocations = organizaionLocationManager.retrieveAllActiveOrganizationLocation();

            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    //To Retrieve all Organization.
    public void retrieveAllOrganizations() {

        try {

            HibernateUtil.beginTransaction();
            allOrganizations = organizationsManager.loadAll(Organizations.class);
            HibernateUtil.commitTransaction();
            organizationsConverter.setSearchOrganizations(allOrganizations);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To retrieve Not Assigned locations to Organizations on selection of Organizations
    public void onselectOrganizationRetrieveLocation() {

        int locationid;
        if (getSelectedLocation().getId() == null) {
            locationid = 0;
        } else {
            locationid = getSelectedLocation().getId().intValue();
        }
        try {

            HibernateUtil.beginTransaction();

            allLocations = locationManager.retrieveUnassignedLocationsByOrganizationId(getSelectedOrganization().getId(), locationid);
            HibernateUtil.commitTransaction();
            locationsConverter.setSearchLocations(allLocations);

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
        setSelectedOrganizationLocation(getSelectedOrganizationLocationUpdate());
        setSelectedOrganization(getSelectedOrganizationLocationUpdate().getOrganizations());
        setSelectedLocation(getSelectedOrganizationLocationUpdate().getLocations());
        onselectOrganizationRetrieveLocation();
         setIsEndDateEnable(false);
        setIsEndReasonEnable(false);
        setIsNotesEnable(false);
        RequestContext.getCurrentInstance().update(":frmOrgLocAdd:PnlOrgLocAdd");

    }

    public void onAddSelect() {
//        this.allLocations.clear();
        this.selectedOrganizationLocation = null;
        this.selectedOrganizationLocation = new OrganizationLocations();
        this.selectedLocation = null;
        this.selectedLocation = new Locations();
        this.selectedOrganization = null;
        this.selectedOrganization = new Organizations();
    }

    //To add organization Location
    public void addOrganizationLocation() {

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {
            HibernateUtil.beginTransaction();

            this.selectedOrganizationLocation.setOrganizations(this.getSelectedOrganization());
            this.selectedOrganizationLocation.setLocations(this.getSelectedLocation());
            this.organizaionLocationManager.saveNew(selectedOrganizationLocation);
            HibernateUtil.commitTransaction();
            String message = text.getString("ui.Bean.OrgLocationAddSuccess");
            this.retrieveAllActiveOrganizationLocation();
            RequestContext req = RequestContext.getCurrentInstance();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));

            this.selectedOrganizationLocation = null;
            this.selectedOrganizationLocation = new OrganizationLocations();
            this.selectedLocation = null;
            this.selectedLocation = new Locations();
            this.selectedOrganization = null;
            this.selectedOrganization = new Organizations();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    //To update OrganizationLocation 
    public void updateOrganizationLocation() {

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            if (this.getSelectedOrganizationLocationUpdate().getEndDate() != null) {
                if (this.getSelectedOrganizationLocationUpdate().getEndReason().equals("")
                        || this.getSelectedOrganizationLocationUpdate().getEndReason() == null) {
                    String message = text.getString("ui.Bean.EndReason");

                    RequestContext req = RequestContext.getCurrentInstance();

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                } else {
                    if (selectedOrganizationLocationUpdate.getStartDate().compareTo(selectedOrganizationLocationUpdate.getEndDate()) <= 0) {

                        HibernateUtil.beginTransaction();
                      organizaionLocationManager.updateExisting(selectedOrganizationLocationUpdate);
                        HibernateUtil.commitTransaction();
                        String message = text.getString("ui.Bean.OrgLocationUpdateSuccess");

                        RequestContext req = RequestContext.getCurrentInstance();

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                        selectedOrganizationLocationUpdate = null;
                        selectedOrganizationLocationUpdate = new OrganizationLocations();
                        selectedLocation = null;
                        selectedLocation = new Locations();
                        selectedOrganization = null;
                        selectedOrganization = new Organizations();
                        retrieveAllActiveOrganizationLocation();
                        retrieveOrganizationLocationHistory();
                        RequestContext.getCurrentInstance().execute("DlgConfirmOrgLoc.hide();");
                        RequestContext.getCurrentInstance().update(":frmOrganizationLocationTable");
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

    //To retrieve all OrganizationLocation History
    public void retrieveOrganizationLocationHistory() {

        try {

            HibernateUtil.beginTransaction();

            allOrganizationLocationHistory = organizaionLocationManager.retrieveOrganizationLocationHistory();

            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }
}
