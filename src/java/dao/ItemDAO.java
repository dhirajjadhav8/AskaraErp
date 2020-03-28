/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import javax.imageio.ImageIO;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import orm.Icategory;
import orm.Imake;
import orm.Item;
import orm.Itype;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "itemDAO")
@ViewScoped
public class ItemDAO implements Serializable {

    private List<Icategory> allCategories;
    private List<Imake> allMakes;
    private List<Itype> allTypes;
    private List<Item> allItems;
    private Icategory selectedCategory;
    private Itype selectedType;
    private Imake selectedMake;
    private Item selectedItem;
    private Item selectedItemToDisplay;
    private boolean tabview;
    private boolean tabviewindex;
    private int activeIndex;
    int id;
    private UploadedFile file;
    private static final int IMG_WIDTH = 100;
    private static final int IMG_HEIGHT = 100;
    private static final int IMG_WIDTHs = 50;
    private static final int IMG_HEIGHTs = 50;
    // to temp directory of os
    String tempDir = System.getProperty("java.io.tmpdir");
    // for file seperator
    String seperator = System.getProperty("file.separator");

    public List<Icategory> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<Icategory> allCategories) {
        this.allCategories = allCategories;
    }

    public List<Imake> getAllMakes() {
        return allMakes;
    }

    public void setAllMakes(List<Imake> allMakes) {
        this.allMakes = allMakes;
    }

    public List<Itype> getAllTypes() {
        return allTypes;
    }

    public void setAllTypes(List<Itype> allTypes) {
        this.allTypes = allTypes;
    }

    public Icategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Icategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public List<Item> getAllItems() {
        return allItems;
    }

    public void setAllItems(List<Item> allItems) {
        this.allItems = allItems;
    }

    public Itype getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(Itype selectedType) {
        this.selectedType = selectedType;
    }

    public Imake getSelectedMake() {
        return selectedMake;
    }

    public void setSelectedMake(Imake selectedMake) {
        this.selectedMake = selectedMake;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public boolean isTabview() {
        return tabview;
    }

    public void setTabview(boolean tabview) {
        this.tabview = tabview;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Item getSelectedItemToDisplay() {
        return selectedItemToDisplay;
    }

    public void setSelectedItemToDisplay(Item selectedItemToDisplay) {
        this.selectedItemToDisplay = selectedItemToDisplay;
    }

    public boolean isTabviewindex() {
        return tabviewindex;
    }

    public void setTabviewindex(boolean tabviewindex) {
        this.tabviewindex = tabviewindex;
    }

    /**
     * Creates a new instance of ItemDAO
     */
    public ItemDAO() {
        this.allCategories = new ArrayList<Icategory>();
        this.allMakes = new ArrayList<Imake>();
        this.allTypes = new ArrayList<Itype>();
        this.allItems = new ArrayList<Item>();

        this.selectedCategory = new Icategory();
        this.selectedMake = new Imake();
        this.selectedType = new Itype();
        this.selectedItem = new Item();
        this.selectedItemToDisplay = new Item();

        this.setTabview(true);
        this.setTabviewindex(false);

        System.out.println(" New ItemDAO is created......");
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

            RequestContext.getCurrentInstance().update("pnlMain");

        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    text.getString("ui.Bean.FileNotUpload"), "");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }

    public void retrieveAllCategoryImake() {
        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        Query q = null;
        Query q1 = null;

        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                this.allCategories.clear();
                this.allMakes.clear();

                String hqlQueryforIcategory = "from Icategory";

                String hqlQueryforImake = "from Imake";

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                q = session.createQuery(hqlQueryforIcategory);
                q.setMaxResults(50);
                this.allCategories.addAll(q.list());

                q1 = session.createQuery(hqlQueryforImake);
                q1.setMaxResults(50);
                this.allMakes.addAll(q1.list());

                trx.commit();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (q != null) {
                q = null;
            }
            if (q1 != null) {
                q1 = null;
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

    public void retrieveItemType() {

        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        Query q = null;

        try {
            this.allTypes.clear();
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            String hqlQueryforType = "from Itype type where type.icategory.id = " + this.selectedCategory.getId();

            q = session.createQuery(hqlQueryforType);
            q.setMaxResults(50);

            this.allTypes.addAll(q.list());
            trx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (q != null) {
                q = null;
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

    public void retrieveAllItemsBySearch() {

        Session session = null;
        Transaction trx = null;


        RequestContext req = RequestContext.getCurrentInstance();

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        Query q = null;

        Criteria cr = null;

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            this.allItems.clear();
            if (this.selectedMake.getId() == 0 && this.selectedType.getId() == 0) {
                FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.msg.maktyeselect"), text.getString("ui.msg.maktyeselect"));
                FacesContext.getCurrentInstance().addMessage(null, error);
                req.update(":frmItemSearch:grolwlmsg");

            } else {
                if (this.selectedMake.getId() != 0 && this.selectedType.getId() != 0) {
                    cr = session.createCriteria(Item.class, "item")
                            .createAlias("item.itype", "itype", JoinType.INNER_JOIN)
                            .createAlias("item.imake", "imake", JoinType.INNER_JOIN)
                            .add(Restrictions.eq("itype.id", this.selectedType.getId()))
                            .add(Restrictions.eq("imake.id", this.selectedMake.getId()));

                    this.allItems.addAll(cr.list());

                } else if (this.selectedMake.getId() != 0) {
                    cr = session.createCriteria(Item.class, "item")
                            .createAlias("item.itype", "itype", JoinType.INNER_JOIN)
                            .createAlias("item.imake", "imake", JoinType.INNER_JOIN)
                            .add(Restrictions.eq("imake.id", this.selectedMake.getId()));

                    this.allItems.addAll(cr.list());
                } else if (this.selectedType.getId() != 0) {
                    cr = session.createCriteria(Item.class, "item")
                            .createAlias("item.itype", "itype", JoinType.INNER_JOIN)
                            .createAlias("item.imake", "imake", JoinType.INNER_JOIN)
                            .add(Restrictions.eq("itype.id", this.selectedType.getId()));

                    this.allItems.addAll(cr.list());
                }
            }

            trx.commit();

        } catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        } finally {
            if (q != null) {
                q = null;
            }
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
    // to set selectedItem  SelectedCategory to new  

    public void setSelectedItemCategoryTypMakeNewToAdd() {

        this.setSelectedItem(null);
        this.setSelectedCategory(null);
        this.setSelectedType(null);
        this.setSelectedMake(null);
        this.allTypes.clear();

        this.setSelectedItem(new Item());
        this.setSelectedCategory(new Icategory());
        this.setSelectedType(new Itype());
        this.setSelectedMake(new Imake());

    }

    public void addItem() {

        Session session = null;
        Transaction trx = null;


        RequestContext req = RequestContext.getCurrentInstance();

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            this.selectedItem.setCreatedby(loginDAO.getLoggedinUser().getId());

            Date date = new Date();

            this.selectedItem.setCreatedon(new Timestamp(date.getTime()));
            this.selectedItem.setImake(this.getSelectedMake());
            this.selectedItem.setItype(this.getSelectedType());

            session.save(this.selectedItem);

            this.id = this.selectedItem.getId();

            trx.commit();
            this.retrieveAddedItemToDisplay();


            this.setTabview(false);
            this.setTabviewindex(true);
            this.setActiveIndex(1);

            FacesMessage msg = new FacesMessage(text.getString("ui.Bean.ItemAddSccess"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            req.update("tbvItem");


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

    public void retrieveAddedItemToDisplay() {

        Session session = null;
        Transaction trx = null;

        Criteria cr = null;

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            this.setSelectedItemToDisplay(new Item());
            cr = session.createCriteria(Item.class, "item")
                    .createAlias("item.itype", "itype", JoinType.INNER_JOIN)
                    .createAlias("item.imake", "imake", JoinType.INNER_JOIN)
                    .createAlias("itype.icategory", "icategory", JoinType.INNER_JOIN)
                    .add(Restrictions.eq("item.id", id));

            this.setSelectedItemToDisplay((Item) cr.list().get(0));

            trx.commit();
            RequestContext.getCurrentInstance().update("frmItempDetailsDisplay");

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

    // update the new added item after the photo uploading. 
    public void updateaddedItem() throws Exception {

        Session session = null;
        Transaction trx = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            String strPhotoPath = seperator + "resources" + seperator + "items" + seperator + this.getSelectedItem().getId() + "_jpg.jpg";

            System.out.println(strPhotoPath);
            this.getSelectedItem().setPhoto(strPhotoPath);
            session.update(this.selectedItem);
            trx.commit();
            this.retrieveAddedItemToDisplay();
            FacesMessage msgs = new FacesMessage(text.getString("ui.msg.PhotoUploadSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, msgs);

            RequestContext req = RequestContext.getCurrentInstance();

            req.update(":frmItemPhoto:img");
            this.retrieveAllItemsBySearch();

        } catch (Exception e) {
            trx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            throw e;

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

    public void finishUploading() {

        this.setActiveIndex(0);
        this.setTabview(true);
        this.setTabviewindex(false);
        RequestContext.getCurrentInstance().update("confirmDlg");
    }
    public void nextTabToChange(){
        this.setActiveIndex(2);
        RequestContext.getCurrentInstance().update("tbvItem");
    }
}
