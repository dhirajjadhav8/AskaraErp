/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.IcategoriesConverter;
import converter.ImakesConverter;
import converter.ItypesConverter;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import manager.impl.IcategoriesManagerImpl;
import manager.impl.ImakesManagerImpl;
import manager.impl.ItemDetailsManagerImpl;
import manager.impl.ItemsManagerImpl;
import manager.impl.ItypesManagerImpl;
import manager.interfaces.IIcategoriesManager;
import manager.interfaces.IImakesManager;
import manager.interfaces.IItemDetailsManager;
import manager.interfaces.IItemsManager;
import manager.interfaces.IItypesManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import orm.Icategories;
import orm.Imakes;
import orm.ItemDetails;
import orm.Items;
import orm.Itypes;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "itemsView")
@ViewScoped
public class ItemsView {
    /*
     Loginbean Inject 
     
     */

    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    //ICategories
    private Icategories selectedIcategories = new Icategories();
    private List<Icategories> allIcategories;
    private IcategoriesConverter icategoriesConverter = new IcategoriesConverter();
    private IIcategoriesManager icategoriesManager = new IcategoriesManagerImpl();
    //IMake
    private List<Imakes> allMakes;
    private Imakes selectedImakes = new Imakes();
    private ImakesConverter imakesConverter = new ImakesConverter();
    private IImakesManager imakesManager = new ImakesManagerImpl();
    //IType
    private List<Itypes> allItypes;
    private Itypes selectedItypes = new Itypes();
    private ItypesConverter itypesConverter = new ItypesConverter();
    private IItypesManager ItypesManager = new ItypesManagerImpl();
    //Items
    private List<Items> allItems;
    private Items selectedItem = new Items();
    private Items selectedItemToDisplay = new Items();
    private IItemsManager iItemsManager = new ItemsManagerImpl();
    private boolean tabview;
    private boolean tabviewindex;
    private int activeIndex;
    int id;
    //ItemDetails
    private List<ItemDetails> allItemDetails;
    private ItemDetails selectedItemDetailsToAdd = new ItemDetails();
    private IItemDetailsManager itemDetailManager = new ItemDetailsManagerImpl();
    private UploadedFile file;
    private static final int IMG_WIDTH = 100;
    private static final int IMG_HEIGHT = 100;
    private static final int IMG_WIDTHs = 50;
    private static final int IMG_HEIGHTs = 50;
    // to temp directory of os
    String tempDir = System.getProperty("java.io.tmpdir");
    // for file seperator
    String seperator = System.getProperty("file.separator");

    /**
     * Creates a new instance of ItemsView
     */
    public ItemsView() {
          this.setTabview(true);
        this.setTabviewindex(false);
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public Icategories getSelectedIcategories() {
        return selectedIcategories;
    }

    public void setSelectedIcategories(Icategories selectedIcategories) {
        this.selectedIcategories = selectedIcategories;
    }

    public List<Icategories> getAllIcategories() {
        return allIcategories;
    }

    public void setAllIcategories(List<Icategories> allIcategories) {
        this.allIcategories = allIcategories;
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

    public List<Imakes> getAllMakes() {
        return allMakes;
    }

    public void setAllMakes(List<Imakes> allMakes) {
        this.allMakes = allMakes;
    }

    public Imakes getSelectedImakes() {
        return selectedImakes;
    }

    public void setSelectedImakes(Imakes selectedImakes) {
        this.selectedImakes = selectedImakes;
    }

    public ImakesConverter getImakesConverter() {
        return imakesConverter;
    }

    public void setImakesConverter(ImakesConverter imakesConverter) {
        this.imakesConverter = imakesConverter;
    }

    public IImakesManager getImakesManager() {
        return imakesManager;
    }

    public void setImakesManager(IImakesManager imakesManager) {
        this.imakesManager = imakesManager;
    }

    public List<Itypes> getAllItypes() {
        return allItypes;
    }

    public void setAllItypes(List<Itypes> allItypes) {
        this.allItypes = allItypes;
    }

    public Itypes getSelectedItypes() {
        return selectedItypes;
    }

    public void setSelectedItypes(Itypes selectedItypes) {
        this.selectedItypes = selectedItypes;
    }

    public ItypesConverter getItypesConverter() {
        return itypesConverter;
    }

    public void setItypesConverter(ItypesConverter itypesConverter) {
        this.itypesConverter = itypesConverter;
    }

    public IItypesManager getItypesManager() {
        return ItypesManager;
    }

    public void setItypesManager(IItypesManager ItypesManager) {
        this.ItypesManager = ItypesManager;
    }

    public List<Items> getAllItems() {
        return allItems;
    }

    public void setAllItems(List<Items> allItems) {
        this.allItems = allItems;
    }

    public Items getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Items selectedItem) {
        this.selectedItem = selectedItem;
    }

    public Items getSelectedItemToDisplay() {
        return selectedItemToDisplay;
    }

    public void setSelectedItemToDisplay(Items selectedItemToDisplay) {
        this.selectedItemToDisplay = selectedItemToDisplay;
    }

    public IItemsManager getiItemsManager() {
        return iItemsManager;
    }

    public void setiItemsManager(IItemsManager iItemsManager) {
        this.iItemsManager = iItemsManager;
    }

    public boolean isTabview() {
        return tabview;
    }

    public void setTabview(boolean tabview) {
        this.tabview = tabview;
    }

    public boolean isTabviewindex() {
        return tabviewindex;
    }

    public void setTabviewindex(boolean tabviewindex) {
        this.tabviewindex = tabviewindex;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public List<ItemDetails> getAllItemDetails() {
        return allItemDetails;
    }

    public void setAllItemDetails(List<ItemDetails> allItemDetails) {
        this.allItemDetails = allItemDetails;
    }

    public ItemDetails getSelectedItemDetailsToAdd() {
        return selectedItemDetailsToAdd;
    }

    public void setSelectedItemDetailsToAdd(ItemDetails selectedItemDetailsToAdd) {
        this.selectedItemDetailsToAdd = selectedItemDetailsToAdd;
    }

    public IItemDetailsManager getItemDetailManager() {
        return itemDetailManager;
    }

    public void setItemDetailManager(IItemDetailsManager itemDetailManager) {
        this.itemDetailManager = itemDetailManager;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    @PostConstruct
    public void postConstruct() {
        this.loginbean.checkAccess();
        this.retrieveAllCategories();
        this.retrieveAllMakes();
//        this.retrieveAllItemDetailsOnSelectedItemId();
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

    // for uploading the employee photo.
    public void handleFileUpload(FileUploadEvent event) {

        int BUFFER_SIZE = 4096;


        File result = new File(tempDir + event.getFile().getFileName());

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(result);

            byte[] buffer = new byte[BUFFER_SIZE];

            int bulk;
            InputStream inputStream = event.getFile().getInputstream();
            while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();

            BufferedImage originalImage = ImageIO.read(new File(tempDir + event.getFile().getFileName()));
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            FacesMessage msg =
                    new FacesMessage("File Description" + "file name: "
                    + event.getFile().getFileName() + " \n file size: "
                    + event.getFile().getSize() / 1024
                    + " Kb \n content type: "
                    + event.getFile().getContentType()
                    + text.getString("ui.Bean.FileUpload"));


            // for add new employee upload photo 

            File file2 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "items" + seperator + id + ".jpg");
            result.renameTo(file2);

            //update method

            this.updateaddedItem();

            BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type);
            ImageIO.write(resizeImageHintJpg, "jpg", new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "items" + seperator + id + "_jpg.jpg"));

            BufferedImage resizeImageJpg = resizeImage(originalImage, type);
            ImageIO.write(resizeImageJpg, "jpg", new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "items" + seperator + id + ".jpg"));

            RequestContext.getCurrentInstance().update(":pnlMain");

        } catch (Exception e) {
            System.out.println(e.getMessage());

            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    text.getString("ui.Bean.FileNotUpload"), "");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }

    //To Retrieve all categories 
    public void retrieveAllCategories() {
        try {

            HibernateUtil.beginTransaction();
            allIcategories = icategoriesManager.loadAll(Icategories.class);

            HibernateUtil.commitTransaction();

            icategoriesConverter.setSearchIcategories(allIcategories);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To retrieve all make
    public void retrieveAllMakes() {
        try {

            HibernateUtil.beginTransaction();
            this.allMakes = this.imakesManager.loadAll(Imakes.class);
            HibernateUtil.commitTransaction();
            this.imakesConverter.setSearchImakes(allMakes);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To retrieve IType of selected category
    public void retrieveSelectedCategoryItems() {
        try {
            HibernateUtil.beginTransaction();

            allItypes = ItypesManager.retrieveSelectedCategoryItems(getSelectedIcategories().getId());
            HibernateUtil.commitTransaction();
            this.itypesConverter.setSearchItypes(allItypes);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    //For Search
    public void retrieveAllItemsBySearch() {

        try {
            System.out.println(selectedItypes.getId());
            System.out.println(selectedImakes.getId());

            RequestContext req = RequestContext.getCurrentInstance();

            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

            if ((selectedImakes.getId() == 0) && (selectedItypes.getId() == 0)) {
                FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.msg.maktyeselect"), text.getString("ui.msg.maktyeselect"));
                FacesContext.getCurrentInstance().addMessage(null, error);
                req.update(":frmItemSearch:grolwlmsg");
            } else {
                if (selectedImakes.getId() != 0 && selectedItypes.getId() != 0) {

                    System.out.println("BOTH");
                    HibernateUtil.beginTransaction();
                    allItems = iItemsManager.retrieveAllItemsByITypeIdAndIMakeId(selectedItypes.getId(), selectedImakes.getId());
                    HibernateUtil.commitTransaction();

                } else if (selectedImakes.getId() != 0) {

                    HibernateUtil.beginTransaction();
                    System.out.println("IMAKE");
                    allItems = iItemsManager.retrieveAllItemsByIMakeId(this.selectedImakes.getId());
                    HibernateUtil.commitTransaction();
                } else if (selectedItypes.getId() != 0) {
                    HibernateUtil.beginTransaction();
                    System.out.println("ITYEP");
                    allItems = iItemsManager.retrieveAllItemsByITypeId(this.selectedItypes.getId());
                    HibernateUtil.commitTransaction();
                }




            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void setSelectedItemCategoryTypMakeNewToAdd() {



        this.setSelectedItem(null);
        this.setSelectedIcategories(null);
        this.setSelectedItypes(null);
        this.setSelectedImakes(null);
        this.allItypes.clear();

        this.setSelectedIcategories(new Icategories());
        this.setSelectedImakes(new Imakes());
        this.setSelectedItem(new Items());
    }

    //To Add item in database
    public void addItem() {

        try {

            RequestContext req = RequestContext.getCurrentInstance();

            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
            HibernateUtil.beginTransaction();
            this.selectedItem.setImakes(selectedImakes);
            // this.setSelectedItypes(this.selectedIcategories);
//            this.selectedItypes.setIcategories(selectedIcategories);
            this.selectedItem.setItypes(selectedItypes);
            this.iItemsManager.saveNew(selectedItem);
            HibernateUtil.commitTransaction();
            id = selectedItem.getId();
            this.retrieveAddedItemToDisplay();
            this.setTabview(false);
            this.setTabviewindex(true);
            this.setActiveIndex(1);

            FacesMessage msg = new FacesMessage(text.getString("ui.Bean.ItemAddSccess"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            req.update("tbvItem");


        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To retrieve AllItemDetailsOnSelectedItemId
    public void retrieveAllItemDetailsOnSelectedItemId() {

        try {
            HibernateUtil.beginTransaction();

            this.allItemDetails = this.itemDetailManager.retrieveAllItemDetailsOnSelectedItemId(this.getSelectedItem().getId());
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void addItemDetails() {

        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

            HibernateUtil.beginTransaction();

            this.selectedItemDetailsToAdd.setItems(selectedItem);
            this.itemDetailManager.saveNew(selectedItemDetailsToAdd);
            HibernateUtil.commitTransaction();

            this.retrieveAllItemDetailsOnSelectedItemId();
            RequestContext req = RequestContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(text.getString("ui.msg.addItemDetailsSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            this.setSelectedItemDetailsToAdd(null);
            this.setSelectedItemDetailsToAdd(new ItemDetails());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();



        } finally {
            HibernateUtil.closeSession();
        }

    }

    //To display added items
    public void retrieveAddedItemToDisplay() {

        try {
            HibernateUtil.beginTransaction();

            this.selectedItemToDisplay = this.iItemsManager.retrieveAddedItemToDisplay(id).get(0);
            HibernateUtil.commitTransaction();
            RequestContext.getCurrentInstance().update("frmItempDetailsDisplay");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }



    }

    // update the new added item after the photo uploading. 
    public void updateaddedItem() {

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        try {
            HibernateUtil.beginTransaction();


            String strPhotoPath = seperator + "resources" + seperator + "items" + seperator + this.getSelectedItem().getId() + "_jpg.jpg";

            System.out.println(strPhotoPath);
            this.getSelectedItem().setPhoto(strPhotoPath);
            this.iItemsManager.updateExisting(selectedItem);
            HibernateUtil.commitTransaction();
            this.retrieveAddedItemToDisplay();
            FacesMessage msgs = new FacesMessage(text.getString("ui.msg.PhotoUploadSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, msgs);

            RequestContext req = RequestContext.getCurrentInstance();

            req.update(":frmItemPhoto:img");
            this.retrieveAllItemsBySearch();



        } catch (Exception e) {
            System.out.println(e.getMessage());
            HibernateUtil.rollbackTransaction();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
        } finally {
            HibernateUtil.closeSession();
        }

    }

    public void finishUploading() {

        this.setActiveIndex(0);
        this.setTabview(true);
        this.setTabviewindex(false);
        RequestContext.getCurrentInstance().update("confirmDlg");
    }

    public void nextTabToChange() {
        this.setActiveIndex(2);
        RequestContext.getCurrentInstance().update("tbvItem");
    }
}
