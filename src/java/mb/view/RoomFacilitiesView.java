/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.IcategoriesConverter;
import converter.ItemDetailsConverter;
import converter.RoomConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.IcategoriesManagerImpl;
import manager.impl.ItemDetailsManagerImpl;
import manager.impl.RoomFacilitiesManagerImpl;
import manager.impl.RoomsManagerImpl;
import manager.interfaces.IIcategoriesManager;
import manager.interfaces.IItemDetailsManager;
import manager.interfaces.IRoomFacilitiesManager;
import manager.interfaces.IRoomsManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import orm.Icategories;
import orm.ItemDetails;
import orm.RoomFacilities;
import orm.Rooms;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "roomFacilitiesView")
@ViewScoped
public class RoomFacilitiesView implements Serializable {

    /*
     Loginbean Inject 
     
     */
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    //RoomFacilities
    private RoomFacilities selectedRoomFacilitiesToAdd = new RoomFacilities();
    private List<RoomFacilities> allRoomFacilitiesList;
    private List<RoomFacilities> allRoomFacilitiesHistory;
    private RoomFacilities selectedRoomFacilitiesToUpdate;
    private IRoomFacilitiesManager roomFacilityManager = new RoomFacilitiesManagerImpl();
    //Rooms
    private Rooms selectedRoom = new Rooms();
    private List<Rooms> allrooms;
    private RoomConverter roomsConverter = new RoomConverter();
    private IRoomsManager roomManager = new RoomsManagerImpl();
    //
    private IcategoriesConverter icategoriesConverter = new IcategoriesConverter();
    private IIcategoriesManager icategoriesManager = new IcategoriesManagerImpl();
    private Icategories selectedCategory = new Icategories();
    private List<Icategories> allCategories;
    //
    private List<ItemDetails> allItemDetails;
    private ItemDetails selectedItemDetails = new ItemDetails();
    private IItemDetailsManager itemDetailManager = new ItemDetailsManagerImpl();
    private ItemDetailsConverter itemDetailsConverter = new ItemDetailsConverter();

    /**
     * Creates a new instance of RoomFacilitiesView
     */
    public RoomFacilitiesView() {
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public RoomFacilities getSelectedRoomFacilitiesToAdd() {
        return selectedRoomFacilitiesToAdd;
    }

    public void setSelectedRoomFacilitiesToAdd(RoomFacilities selectedRoomFacilitiesToAdd) {
        this.selectedRoomFacilitiesToAdd = selectedRoomFacilitiesToAdd;
    }

    public List<RoomFacilities> getAllRoomFacilitiesList() {
        return allRoomFacilitiesList;
    }

    public void setAllRoomFacilitiesList(List<RoomFacilities> allRoomFacilitiesList) {
        this.allRoomFacilitiesList = allRoomFacilitiesList;
    }

    public List<RoomFacilities> getAllRoomFacilitiesHistory() {
        return allRoomFacilitiesHistory;
    }

    public void setAllRoomFacilitiesHistory(List<RoomFacilities> allRoomFacilitiesHistory) {
        this.allRoomFacilitiesHistory = allRoomFacilitiesHistory;
    }

    public RoomFacilities getSelectedRoomFacilitiesToUpdate() {
        return selectedRoomFacilitiesToUpdate;
    }

    public void setSelectedRoomFacilitiesToUpdate(RoomFacilities selectedRoomFacilitiesToUpdate) {
        this.selectedRoomFacilitiesToUpdate = selectedRoomFacilitiesToUpdate;
    }

    public IRoomFacilitiesManager getRoomFacilityManager() {
        return roomFacilityManager;
    }

    public void setRoomFacilityManager(IRoomFacilitiesManager roomFacilityManager) {
        this.roomFacilityManager = roomFacilityManager;
    }

    public Rooms getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Rooms selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    public List<Rooms> getAllrooms() {
        return allrooms;
    }

    public void setAllrooms(List<Rooms> allrooms) {
        this.allrooms = allrooms;
    }

    public RoomConverter getRoomsConverter() {
        return roomsConverter;
    }

    public void setRoomsConverter(RoomConverter roomsConverter) {
        this.roomsConverter = roomsConverter;
    }

    public IRoomsManager getRoomManager() {
        return roomManager;
    }

    public void setRoomManager(IRoomsManager roomManager) {
        this.roomManager = roomManager;
    }

    public IcategoriesConverter getIcategoriesConverter() {
        return icategoriesConverter;
    }

    public void setIcategoriesConverter(IcategoriesConverter icategoriesConverter) {
        this.icategoriesConverter = icategoriesConverter;
    }

    public IIcategoriesManager getIcategoriesManager() {
        return icategoriesManager;
    }

    public void setIcategoriesManager(IIcategoriesManager icategoriesManager) {
        this.icategoriesManager = icategoriesManager;
    }

    public Icategories getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Icategories selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public List<Icategories> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<Icategories> allCategories) {
        this.allCategories = allCategories;
    }

    public List<ItemDetails> getAllItemDetails() {
        return allItemDetails;
    }

    public void setAllItemDetails(List<ItemDetails> allItemDetails) {
        this.allItemDetails = allItemDetails;
    }

    public ItemDetails getSelectedItemDetails() {
        return selectedItemDetails;
    }

    public void setSelectedItemDetails(ItemDetails selectedItemDetails) {
        this.selectedItemDetails = selectedItemDetails;
    }

    public IItemDetailsManager getItemDetailManager() {
        return itemDetailManager;
    }

    public void setItemDetailManager(IItemDetailsManager itemDetailManager) {
        this.itemDetailManager = itemDetailManager;
    }

    public ItemDetailsConverter getItemDetailsConverter() {
        return itemDetailsConverter;
    }

    public void setItemDetailsConverter(ItemDetailsConverter itemDetailsConverter) {
        this.itemDetailsConverter = itemDetailsConverter;
    }

    @PostConstruct
    public void postConstruct() {
        loginbean.checkAccess();
        retrieveAllCategories();
        retrieveAllRoomFacility();
        retrieveRoomFacilityHistory();
    }

    //To retrieve allRoomFacilities where end date is null
    public void retrieveAllRoomFacility() {

        try {
            HibernateUtil.beginTransaction();
            allRoomFacilitiesList = roomFacilityManager.retrieveAllRoomFacility();
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }
    //To retrieve AllCategories

    public void retrieveAllCategories() {
        try {
            HibernateUtil.beginTransaction();
            allCategories = icategoriesManager.loadAll(Icategories.class);
            HibernateUtil.commitTransaction();
            icategoriesConverter.setSearchIcategories(allCategories);
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To retrieve allRooms for autocomplete
    public List<Rooms> retrieveAllRoomsForAutomplete(String strSearchRoomsCriteria) {

        try {
            HibernateUtil.beginTransaction();
            System.out.println("111111");
            allrooms = roomManager.retrieveAllRoomsForAutomplete(strSearchRoomsCriteria);
            System.out.println("2222");
            HibernateUtil.commitTransaction();
            roomsConverter.setSearchRooms(allrooms);

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
        return allrooms;
    }
//To retrieve All ItemCategories

    public List<ItemDetails> retrievellItemDetalisOfSelectedCategoriesForAutoComplete(String strSearchCriteria) {

        try {

            List<ItemDetails> itemDetailsList = new ArrayList<ItemDetails>();
            HibernateUtil.beginTransaction();
            itemDetailsList = itemDetailManager.retrieveNotCompletedItemDetails();
            List<Integer> id = new ArrayList<Integer>();
            for (int i = 0; i < itemDetailsList.size(); i++) {
                id.add(itemDetailsList.get(i).getId());
            }
            if (id.isEmpty()) {
                allItemDetails = itemDetailManager.retrievellItemCategories1(strSearchCriteria, getSelectedCategory().getId());
            } else {

                allItemDetails = itemDetailManager.retrievellItemCategories2(strSearchCriteria, getSelectedCategory().getId(), id);
            }

            HibernateUtil.commitTransaction();
            itemDetailsConverter.setSearchItemDetails(allItemDetails);

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }

        return allItemDetails;
    }

    //To add Room Facilities
    public void addRoomFacilities() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        try {
            if (getSelectedRoom() == null) {
                String message = text.getString("ui.Bean.selectRoomRequired");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update(":frmaddRoomFacilities:growlmsg");
            } else if (getSelectedItemDetails() == null) {
                String message = text.getString("ui.Bean.selectItemDetailsRequired");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update(":frmaddRoomFacilities:growlmsg");
            } else {
                HibernateUtil.beginTransaction();
                selectedRoomFacilitiesToAdd.setRooms(selectedRoom);
                selectedRoomFacilitiesToAdd.setItemDetails(selectedItemDetails);
                roomFacilityManager.saveNew(selectedRoomFacilitiesToAdd);
                HibernateUtil.commitTransaction();
                retrieveAllRoomFacility();
                String message = text.getString("ui.Bean.roomFacilityAddSuccess");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update(":frmaddRoomFacilities:growlmsg");
                setSelectedRoomFacilitiesToAdd(null);
                setSelectedRoomFacilitiesToAdd(new RoomFacilities());
                setSelectedCategory(null);
                setSelectedCategory(new Icategories());
                setSelectedItemDetails(null);
                setSelectedItemDetails(new ItemDetails());
                setSelectedRoom(null);
                setSelectedRoom(new Rooms());
            }

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To update RoomFacility
    public void updateRoomFacility() {
        Date current_date = new Date();
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        try {
            if (selectedRoomFacilitiesToUpdate.getEndDate() == null) {
                String message = text.getString("ui.Bean.EndDate");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update(":frmaddRoomFacilities:growlmsg");
            } else if (selectedRoomFacilitiesToUpdate.getEndReason() == null) {
                String message = text.getString("ui.Bean.EndReason");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update(":frmaddRoomFacilities:growlmsg");
            } else {
                if (selectedRoomFacilitiesToUpdate.getEndDate().compareTo(selectedRoomFacilitiesToUpdate.getStartDate()) >= 0) {
                    if (selectedRoomFacilitiesToUpdate.getEndDate().compareTo(current_date) <= 0) {

                        HibernateUtil.beginTransaction();

                       roomFacilityManager.updateExisting(selectedRoomFacilitiesToUpdate);
                        HibernateUtil.commitTransaction();
                       retrieveAllRoomFacility();
                        retrieveRoomFacilityHistory();
                        String message = text.getString("ui.Bean.roomFacilityUpdateSuccess");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                        RequestContext req = RequestContext.getCurrentInstance();
                        req.update(":frmaddRoomFacilities:growlmsg");
                    } else {
                        String message = text.getString("ui.Bean.EnddateGreterThanStartDate");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                        RequestContext req = RequestContext.getCurrentInstance();
                        req.update(":frmaddRoomFacilities:growlmsg");

                    }


                } else {
                    String message = text.getString("ui.Bean.EnddateGreterThanStartDate");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                    RequestContext req = RequestContext.getCurrentInstance();
                    req.update(":frmaddRoomFacilities:growlmsg");

                }
            }
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }

    }

    //To retrieve  RoomFacility history
    public void retrieveRoomFacilityHistory() {
        try {
            HibernateUtil.beginTransaction();
            allRoomFacilitiesHistory = roomFacilityManager.retrieveRoomFacilityHistory();
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
