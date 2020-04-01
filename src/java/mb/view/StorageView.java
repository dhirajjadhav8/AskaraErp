/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.AreaConverter;
import converter.ItemDetailsConverter;
import converter.StorageTypeConverter;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.AreasManagerImpl;
import manager.impl.ItemDetailsManagerImpl;
import manager.impl.StorageTypesManagerImpl;
import manager.impl.StoragesManagerImpl;
import manager.interfaces.IAreasManager;
import manager.interfaces.IItemDetailsManager;
import manager.interfaces.IStoragesManager;
import manager.interfaces.IStoragetypesManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import orm.Areas;
import orm.ItemDetails;
import orm.StorageTypes;
import orm.Storages;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "storageView")
@ViewScoped
public class StorageView implements Serializable {

    //loginBean
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    //Storage
    private List<Storages> allStorage;
    private Storages selectedStorageToAdd = new Storages();
    private IStoragesManager storageManager = new StoragesManagerImpl();
    //Area 
    private List<Areas> allAreas;
    private Areas selectedArea = new Areas();
    private AreaConverter areaConverter = new AreaConverter();
    private IAreasManager areaManager = new AreasManagerImpl();
    //StorageTypes
    private List<StorageTypes> allStorageTypes;
    private StorageTypes selectedStorageTypes = new StorageTypes();
    private StorageTypeConverter storageTypesConverter = new StorageTypeConverter();
    private IStoragetypesManager storageTypesManager = new StorageTypesManagerImpl();
    //ItemDetails
    private List<ItemDetails> allItemDetails;
    private ItemDetails selectedItemDetails = new ItemDetails();
    private ItemDetailsConverter itemDetailsConverter = new ItemDetailsConverter();
    private IItemDetailsManager itemDetailsManager = new ItemDetailsManagerImpl();

    /**
     * Creates a new instance of StorageView
     */
    public StorageView() {
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public List<Storages> getAllStorage() {
        return allStorage;
    }

    public void setAllStorage(List<Storages> allStorage) {
        this.allStorage = allStorage;
    }

    public Storages getSelectedStorageToAdd() {
        return selectedStorageToAdd;
    }

    public void setSelectedStorageToAdd(Storages selectedStorageToAdd) {
        this.selectedStorageToAdd = selectedStorageToAdd;
    }

    public IStoragesManager getStorageManager() {
        return storageManager;
    }

    public void setStorageManager(IStoragesManager storageManager) {
        this.storageManager = storageManager;
    }

    public List<Areas> getAllAreas() {
        return allAreas;
    }

    public void setAllAreas(List<Areas> allAreas) {
        this.allAreas = allAreas;
    }

    public Areas getSelectedArea() {
        return selectedArea;
    }

    public void setSelectedArea(Areas selectedArea) {
        this.selectedArea = selectedArea;
    }

    public AreaConverter getAreaConverter() {
        return areaConverter;
    }

    public void setAreaConverter(AreaConverter areaConverter) {
        this.areaConverter = areaConverter;
    }

    public IAreasManager getAreaManager() {
        return areaManager;
    }

    public void setAreaManager(IAreasManager areaManager) {
        this.areaManager = areaManager;
    }

    public List<StorageTypes> getAllStorageTypes() {
        return allStorageTypes;
    }

    public void setAllStorageTypes(List<StorageTypes> allStorageTypes) {
        this.allStorageTypes = allStorageTypes;
    }

    public StorageTypes getSelectedStorageTypes() {
        return selectedStorageTypes;
    }

    public void setSelectedStorageTypes(StorageTypes selectedStorageTypes) {
        this.selectedStorageTypes = selectedStorageTypes;
    }

    public StorageTypeConverter getStorageTypesConverter() {
        return storageTypesConverter;
    }

    public void setStorageTypesConverter(StorageTypeConverter storageTypesConverter) {
        this.storageTypesConverter = storageTypesConverter;
    }

    public IStoragetypesManager getStorageTypesManager() {
        return storageTypesManager;
    }

    public void setStorageTypesManager(IStoragetypesManager storageTypesManager) {
        this.storageTypesManager = storageTypesManager;
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

    public ItemDetailsConverter getItemDetailsConverter() {
        return itemDetailsConverter;
    }

    public void setItemDetailsConverter(ItemDetailsConverter itemDetailsConverter) {
        this.itemDetailsConverter = itemDetailsConverter;
    }

    public IItemDetailsManager getItemDetailsManager() {
        return itemDetailsManager;
    }

    public void setItemDetailsManager(IItemDetailsManager itemDetailsManager) {
        this.itemDetailsManager = itemDetailsManager;
    }

    @PostConstruct
    public void postConstruct() {
        this.loginbean.checkAccess();
        this.retrieveAllAreas();
        this.retrieveAllItemDetails();
        this.retrieveAllStorage();
        this.retrieveAllStorageTypes();
    }

    //To retrieve all Storage Types
    public void retrieveAllStorageTypes() {
        try {
            HibernateUtil.beginTransaction();
            allStorageTypes = storageTypesManager.loadAll(StorageTypes.class);

            HibernateUtil.commitTransaction();
            storageTypesConverter.setSearchStorageTypes(allStorageTypes);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }
    //to retrieve all areas

    public void retrieveAllAreas() {
        try {

            HibernateUtil.beginTransaction();

            allAreas = areaManager.loadAll(Areas.class);
            HibernateUtil.commitTransaction();
            areaConverter.setSearchArea(allAreas);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    //To retrieve ItemDetails
    public void retrieveAllItemDetails() {

        try {
            HibernateUtil.beginTransaction();
            allItemDetails = itemDetailsManager.retrieveAllItemDetails();
            HibernateUtil.commitTransaction();
            itemDetailsConverter.setSearchItemDetails(allItemDetails);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To retrieve all Storage types for showing it in datatable
    public void retrieveAllStorage() {
        try {
            HibernateUtil.beginTransaction();

            allStorage = storageManager.retrieveAllStorage();
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    public void check(SelectEvent event) {
        RequestContext req = RequestContext.getCurrentInstance();
        req.update("inptKeyNo");

    }

    //To add Storage into database
    public void addStorage() {

        try {
            RequestContext req = RequestContext.getCurrentInstance();
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

            if (this.selectedStorageToAdd.isIslocable() == true && (this.selectedStorageToAdd.getKyeNo() == null || this.selectedStorageToAdd.getKyeNo().equals(""))) {

                String msg = text.getString("ui.SelectKeyNoAdd");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
                req.update("growlmsg");
            } else {

                if (this.selectedStorageToAdd.isIslocable() == true && this.selectedStorageToAdd.getNoOfKeys() == 0) {

                    String msg = text.getString("ui.NoOfKeyRequired");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
                    req.update("growlmsg");
                } else {

                    if (this.selectedStorageToAdd.isIslocable() == false) {

                        HibernateUtil.beginTransaction();
                        this.selectedStorageToAdd.setNoOfKeys(0);
                        selectedStorageToAdd.setAreas(selectedArea);
                        selectedStorageToAdd.setItemDetails(selectedItemDetails);
                        selectedStorageToAdd.setStorageTypes(selectedStorageTypes);
                        this.storageManager.saveNew(selectedStorageToAdd);
                        HibernateUtil.commitTransaction();
                        String message = text.getString("ui.Bean.StorageddSuccess");

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                        req.update("growlmsg");
                        this.retrieveAllStorage();
                        this.setSelectedStorageToAdd(null);
                        this.setSelectedStorageToAdd(new Storages());
                        this.setSelectedArea(null);
                        this.setSelectedArea(new Areas());
                        this.setSelectedItemDetails(null);
                        this.setSelectedItemDetails(new ItemDetails());
                        this.setSelectedStorageTypes(null);
                        this.setSelectedStorageTypes(new StorageTypes());

                    } else {

                        HibernateUtil.beginTransaction();

                        selectedStorageToAdd.setAreas(selectedArea);
                        selectedStorageToAdd.setItemDetails(selectedItemDetails);
                        selectedStorageToAdd.setStorageTypes(selectedStorageTypes);
                        this.storageManager.saveNew(selectedStorageToAdd);
                        HibernateUtil.commitTransaction();
                        String message = text.getString("ui.Bean.StorageddSuccess");
    
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                        req.update("growlmsg");
                        this.retrieveAllStorage();
                        this.setSelectedStorageToAdd(null);
                        this.setSelectedStorageToAdd(new Storages());
                        this.setSelectedArea(null);
                        this.setSelectedArea(new Areas());
                        this.setSelectedItemDetails(null);
                        this.setSelectedItemDetails(new ItemDetails());
                        this.setSelectedStorageTypes(null);
                        this.setSelectedStorageTypes(new StorageTypes());

                    }

                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }
}
