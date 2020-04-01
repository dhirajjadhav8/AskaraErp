/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.AreaTypesConverter;
import converter.BuildingsConverter;
import converter.FloorsConverter;
import converter.RoomConverter;
import converter.RoomTypesConverter;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.AreaTypesManagerImpl;
import manager.impl.AreasManagerImpl;
import manager.impl.BuildingsManagerImpl;
import manager.impl.FloorsManagerImpl;
import manager.impl.RoomTypesManagerImpl;
import manager.impl.RoomsManagerImpl;
import manager.interfaces.IAreaTypesManager;
import manager.interfaces.IAreasManager;
import manager.interfaces.IBuildingsManager;
import manager.interfaces.IFloorsManager;
import manager.interfaces.IRoomTypesManager;
import manager.interfaces.IRoomsManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import orm.AreaTypes;
import orm.Areas;
import orm.Buildings;
import orm.Floors;
import orm.RoomTypes;
import orm.Rooms;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "areaView")
@ViewScoped
public class AreaView implements Serializable {

    //loginBean
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    //Area
    private List<Areas> allArea;
    private Areas selectedAreaToAdd = new Areas();
    private IAreasManager areaManager = new AreasManagerImpl();
    //Rooms
    private List<Rooms> allRooms;
    private Rooms selectedRoomToAdd = new Rooms();
    private IRoomsManager roomsManager = new RoomsManagerImpl();
    private RoomConverter roomConverter = new RoomConverter();
    //Floors
    private List<Floors> allFloors;
    private Floors selectedFloorToAdd = new Floors();
    private IFloorsManager floorManager = new FloorsManagerImpl();
    private FloorsConverter floorConverter = new FloorsConverter();
    //Buildings
    private List<Buildings> allBuildings;
    private Buildings selectedBuildingToAdd = new Buildings();
    private IBuildingsManager buildingManager = new BuildingsManagerImpl();
    private BuildingsConverter buildingConverter = new BuildingsConverter();
    //AreaTypes
    private List<AreaTypes> allAreaTypes;
    private AreaTypes selectedAreaTypesToAdd = new AreaTypes();
    private IAreaTypesManager areaTypesManager = new AreaTypesManagerImpl();
    private AreaTypesConverter areaTypesConverter = new AreaTypesConverter();
    private List<RoomTypes> allRoomTypes;
    private RoomTypes selectedRoomTypesToAdd = new RoomTypes();
    private IRoomTypesManager roomTypeManager = new RoomTypesManagerImpl();
    private RoomTypesConverter roomTypesConverter = new RoomTypesConverter();

    /**
     * Creates a new instance of AreaView
     */
    public AreaView() {
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public IAreasManager getAreaManager() {
        return areaManager;
    }

    public void setAreaManager(IAreasManager areaManager) {
        this.areaManager = areaManager;
    }

    public List<Areas> getAllArea() {
        return allArea;
    }

    public void setAllArea(List<Areas> allArea) {
        this.allArea = allArea;
    }

    public Areas getSelectedAreaToAdd() {
        return selectedAreaToAdd;
    }

    public void setSelectedAreaToAdd(Areas selectedAreaToAdd) {
        this.selectedAreaToAdd = selectedAreaToAdd;
    }

    public List<Rooms> getAllRooms() {
        return allRooms;
    }

    public void setAllRooms(List<Rooms> allRooms) {
        this.allRooms = allRooms;
    }

    public Rooms getSelectedRoomToAdd() {
        return selectedRoomToAdd;
    }

    public void setSelectedRoomToAdd(Rooms selectedRoomToAdd) {
        this.selectedRoomToAdd = selectedRoomToAdd;
    }

    public IRoomsManager getRoomsManager() {
        return roomsManager;
    }

    public void setRoomsManager(IRoomsManager roomsManager) {
        this.roomsManager = roomsManager;
    }

    public RoomConverter getRoomConverter() {
        return roomConverter;
    }

    public void setRoomConverter(RoomConverter roomConverter) {
        this.roomConverter = roomConverter;
    }

    public List<Floors> getAllFloors() {
        return allFloors;
    }

    public void setAllFloors(List<Floors> allFloors) {
        this.allFloors = allFloors;
    }

    public Floors getSelectedFloorToAdd() {
        return selectedFloorToAdd;
    }

    public void setSelectedFloorToAdd(Floors selectedFloorToAdd) {
        this.selectedFloorToAdd = selectedFloorToAdd;
    }

    public IFloorsManager getFloorManager() {
        return floorManager;
    }

    public void setFloorManager(IFloorsManager floorManager) {
        this.floorManager = floorManager;
    }

    public FloorsConverter getFloorConverter() {
        return floorConverter;
    }

    public void setFloorConverter(FloorsConverter floorConverter) {
        this.floorConverter = floorConverter;
    }

    public List<Buildings> getAllBuildings() {
        return allBuildings;
    }

    public void setAllBuildings(List<Buildings> allBuildings) {
        this.allBuildings = allBuildings;
    }

    public Buildings getSelectedBuildingToAdd() {
        return selectedBuildingToAdd;
    }

    public void setSelectedBuildingToAdd(Buildings selectedBuildingToAdd) {
        this.selectedBuildingToAdd = selectedBuildingToAdd;
    }

    public IBuildingsManager getBuildingManager() {
        return buildingManager;
    }

    public void setBuildingManager(IBuildingsManager buildingManager) {
        this.buildingManager = buildingManager;
    }

    public BuildingsConverter getBuildingConverter() {
        return buildingConverter;
    }

    public void setBuildingConverter(BuildingsConverter buildingConverter) {
        this.buildingConverter = buildingConverter;
    }

    public List<AreaTypes> getAllAreaTypes() {
        return allAreaTypes;
    }

    public void setAllAreaTypes(List<AreaTypes> allAreaTypes) {
        this.allAreaTypes = allAreaTypes;
    }

    public AreaTypes getSelectedAreaTypesToAdd() {
        return selectedAreaTypesToAdd;
    }

    public void setSelectedAreaTypesToAdd(AreaTypes selectedAreaTypesToAdd) {
        this.selectedAreaTypesToAdd = selectedAreaTypesToAdd;
    }

    public IAreaTypesManager getAreaTypesManager() {
        return areaTypesManager;
    }

    public void setAreaTypesManager(IAreaTypesManager areaTypesManager) {
        this.areaTypesManager = areaTypesManager;
    }

    public AreaTypesConverter getAreaTypesConverter() {
        return areaTypesConverter;
    }

    public void setAreaTypesConverter(AreaTypesConverter areaTypesConverter) {
        this.areaTypesConverter = areaTypesConverter;
    }

    public List<RoomTypes> getAllRoomTypes() {
        return allRoomTypes;
    }

    public void setAllRoomTypes(List<RoomTypes> allRoomTypes) {
        this.allRoomTypes = allRoomTypes;
    }

    public RoomTypes getSelectedRoomTypesToAdd() {
        return selectedRoomTypesToAdd;
    }

    public void setSelectedRoomTypesToAdd(RoomTypes selectedRoomTypesToAdd) {
        this.selectedRoomTypesToAdd = selectedRoomTypesToAdd;
    }

    public IRoomTypesManager getRoomTypeManager() {
        return roomTypeManager;
    }

    public void setRoomTypeManager(IRoomTypesManager roomTypeManager) {
        this.roomTypeManager = roomTypeManager;
    }

    public RoomTypesConverter getRoomTypesConverter() {
        return roomTypesConverter;
    }

    public void setRoomTypesConverter(RoomTypesConverter roomTypesConverter) {
        this.roomTypesConverter = roomTypesConverter;
    }

    @PostConstruct
    public void postConstruct() {
        loginbean.checkAccess();
        this.retrieveAllArea();
        this.retrieveAllAreaTypes();
        this.retrieveAllBuildings();
        this.retrieveAllFloors();
        this.retrieveAllRoomTypes();
        this.retrieveAllrooms();

    }

    //To show in all Area in Datatable in area.xhtml
    public void retrieveAllArea() {

        try {

            HibernateUtil.beginTransaction();

            allArea = areaManager.retrieveAllArea();

            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    //To retrieve all rooms
    public void retrieveAllrooms() {

        try {
            HibernateUtil.beginTransaction();

            allRooms = roomsManager.retrieveAllrooms();
            HibernateUtil.commitTransaction();
            roomConverter.setSearchRooms(allRooms);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To retrieve all floors ordered by desc.
    public void retrieveAllFloors() {

        try {
            HibernateUtil.beginTransaction();

            allFloors = floorManager.retrieveAllFloors();
            HibernateUtil.commitTransaction();
            floorConverter.setSearchFloors(allFloors);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To retrieve all Buildings by desc
    public void retrieveAllBuildings() {
        try {
            HibernateUtil.beginTransaction();

            allBuildings = buildingManager.retrieveAllBuildings();
            HibernateUtil.commitTransaction();
            buildingConverter.setSearchBuildings(allBuildings);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To retrieve all area types by desc
    public void retrieveAllAreaTypes() {

        try {
            HibernateUtil.beginTransaction();

            allAreaTypes = areaTypesManager.retrieveAllAreaTypes();
            HibernateUtil.commitTransaction();
            areaTypesConverter.setSearchAreaTypes(allAreaTypes);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To retrieve RoomTypes
    public void retrieveAllRoomTypes() {

        try {

            HibernateUtil.beginTransaction();

            allRoomTypes = roomTypeManager.retrieveAllRoomTypes();
            HibernateUtil.commitTransaction();
            roomTypesConverter.setSearchRoomTypes(allRoomTypes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void removeSelected() {

        this.setSelectedRoomTypesToAdd(null);
        this.setSelectedRoomTypesToAdd(new RoomTypes());

        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("DlgAreaConfirm.hide()");
        req.execute("DlgRoomTypeConfirm.show()");
        req.update("confirmRoomTypeDlg");


    }

    //To add RoomTypes in database
    public void addRoomType() {
        try {

            HibernateUtil.beginTransaction();

            roomTypeManager.saveNew(selectedRoomTypesToAdd);
            HibernateUtil.commitTransaction();
            FacesMessage msg = new FacesMessage("Room Type added successfully.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            setSelectedRoomTypesToAdd(null);
            setSelectedRoomTypesToAdd(new RoomTypes());
            this.retrieveAllRoomTypes();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }


    }

    //To add Rooms in database
    public void addRoom() {

        try {
            HibernateUtil.beginTransaction();
            this.selectedRoomToAdd.setRoomTypes(selectedRoomTypesToAdd);
            roomsManager.saveNew(selectedRoomToAdd);
            HibernateUtil.commitTransaction();
            FacesMessage msg = new FacesMessage("Room added successfully.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            setSelectedRoomToAdd(null);
            setSelectedRoomToAdd(new Rooms());
            setSelectedRoomTypesToAdd(new RoomTypes());
            retrieveAllrooms();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To add Floors in database
    public void addFloors() {

        try {
            HibernateUtil.beginTransaction();

            this.floorManager.saveNew(selectedFloorToAdd);
            HibernateUtil.commitTransaction();

            FacesMessage msg = new FacesMessage("Floore added successfully.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            setSelectedFloorToAdd(null);
            setSelectedFloorToAdd(new Floors());
            retrieveAllFloors();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To add Buildings in Database
    public void addBuildings() {

        try {

            HibernateUtil.beginTransaction();

            buildingManager.saveNew(selectedBuildingToAdd);
            HibernateUtil.commitTransaction();
            FacesMessage msg = new FacesMessage("Floore added successfully.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            setSelectedBuildingToAdd(null);
            setSelectedBuildingToAdd(new Buildings());
            retrieveAllBuildings();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To add AreaTypes in Database
    public void addAreaTypes() {

        try {
            HibernateUtil.beginTransaction();

            areaTypesManager.saveNew(selectedAreaTypesToAdd);
            HibernateUtil.commitTransaction();
            FacesMessage msg = new FacesMessage("Area Type added successfully.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            setSelectedAreaTypesToAdd(null);
            setSelectedAreaTypesToAdd(new AreaTypes());
            retrieveAllAreaTypes();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }
    //To add Area in database

    public void addArea() {

        try {

            HibernateUtil.beginTransaction();

            selectedAreaToAdd.setRooms(selectedRoomToAdd);
            selectedAreaToAdd.setFloors(selectedFloorToAdd);
            selectedAreaToAdd.setBuildings(selectedBuildingToAdd);
            selectedAreaToAdd.setAreaTypes(selectedAreaTypesToAdd);
            areaManager.saveNew(selectedAreaToAdd);
            HibernateUtil.commitTransaction();
            retrieveAllArea();
            FacesMessage msg = new FacesMessage("Area added successfully.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setSelectedAreaToAdd(null);
            this.setSelectedAreaToAdd(new Areas());
            this.setSelectedAreaTypesToAdd(new AreaTypes());
            this.setSelectedBuildingToAdd(new Buildings());
            this.setSelectedFloorToAdd(new Floors());
            this.setSelectedRoomToAdd(new Rooms());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }
}
