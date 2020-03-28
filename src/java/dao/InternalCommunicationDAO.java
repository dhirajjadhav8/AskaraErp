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
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import orm.Attachements;
import orm.Employees;
import orm.IcFolders;
import orm.IcTo;
import orm.InternalCommunication;
import orm.Users;
import util.HibernateUtil;
import util.MainIncludeFilesSelect;
import util.PhaseListenerImpl;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "internalCommunicationDAO")
@ViewScoped
public class InternalCommunicationDAO implements Serializable {

    private List<Employees> allTos;
    private Employees selectedTo;
    private InternalCommunication selectedInterCommOutAdd;
    private InternalCommunication selectedInterCommInboxView;
    private InternalCommunication selectedInnerCommOldInboxView;
    private Attachements selectedAttachement;
    private List<InternalCommunication> allOutboxInterCommunication;
    private List<InternalCommunication> allInboxInterCommunication;
    private List<InternalCommunication> allOldInboxInterCommunication;
    private InternalCommunication selectedInterCommOutboxView;
    private List<IcFolders> allIcFolders;
    private List<IcFolders> allInboxFolderWithDraftTrash;
    private List<IcFolders> allInboxFolderWithoutDraftTrash;
    private List<IcFolders> allOutoxFolderwithDraftTrash;
    private List<IcFolders> allOutoxFolderwithoutDraftTrash;
    private List<Employees> selectedIcTos;
    private IcTo selectedIctoToAdd;
    private IcFolders selectedIcFolder;
    private IcFolders selectedOutBoxFolder;
    private List<IcTo> allOutBoxViewTos;
    private IcTo selectedOutBoxViewTo;
    private String strCheckEmp;
    private int activeIndex;
    private boolean activeTab;
    private boolean isRejected;
    private boolean isNotes;
    private UploadedFile file;
    private static final int IMG_WIDTH = 100;
    private static final int IMG_HEIGHT = 100;
    private static final int IMG_WIDTHs = 50;
    private static final int IMG_HEIGHTs = 50;
    private int id;
// to temp directory of os
    String tempDir = System.getProperty("java.io.tmpdir");
// for file seperator
    String seperator = System.getProperty("file.separator");
// for retrieve methods
    int counter = 0;
// for node tree
    private TreeNode root;
// for selection node 
    private TreeNode selectedNode;
    // for active index in tabView
    private int currentIndex;
    private PhaseListenerImpl phaseListenerImpl;

    public List<Employees> getAllTos() {
        return allTos;
    }

    public void setAllTos(List<Employees> allTos) {
        this.allTos = allTos;
    }

    public Employees getSelectedTo() {
        return selectedTo;
    }

    public void setSelectedTo(Employees selectedTo) {
        this.selectedTo = selectedTo;
    }

    public InternalCommunication getSelectedInterCommOutAdd() {
        return selectedInterCommOutAdd;
    }

    public void setSelectedInterCommOutAdd(InternalCommunication selectedInterCommOutAdd) {
        this.selectedInterCommOutAdd = selectedInterCommOutAdd;
    }

    public List<InternalCommunication> getAllOutboxInterCommunication() {
        return allOutboxInterCommunication;
    }

    public void setAllOutboxInterCommunication(List<InternalCommunication> allOutboxInterCommunication) {
        this.allOutboxInterCommunication = allOutboxInterCommunication;
    }

    public List<InternalCommunication> getAllInboxInterCommunication() {
        return allInboxInterCommunication;
    }

    public void setAllInboxInterCommunication(List<InternalCommunication> allInboxInterCommunication) {
        this.allInboxInterCommunication = allInboxInterCommunication;
    }

    public String getStrCheckEmp() {
        return strCheckEmp;
    }

    public void setStrCheckEmp(String strCheckEmp) {
        this.strCheckEmp = strCheckEmp;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public boolean isActiveTab() {
        return activeTab;
    }

    public void setActiveTab(boolean activeTab) {
        this.activeTab = activeTab;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Attachements getSelectedAttachement() {
        return selectedAttachement;
    }

    public void setSelectedAttachement(Attachements selectedAttachement) {
        this.selectedAttachement = selectedAttachement;
    }

    public InternalCommunication getSelectedInterCommInboxView() {
        return selectedInterCommInboxView;
    }

    public void setSelectedInterCommInboxView(InternalCommunication selectedInterCommInboxView) {
        this.selectedInterCommInboxView = selectedInterCommInboxView;
    }

    public List<InternalCommunication> getAllOldInboxInterCommunication() {
        return allOldInboxInterCommunication;
    }

    public void setAllOldInboxInterCommunication(List<InternalCommunication> allOldInboxInterCommunication) {
        this.allOldInboxInterCommunication = allOldInboxInterCommunication;
    }

    public InternalCommunication getSelectedInnerCommOldInboxView() {
        return selectedInnerCommOldInboxView;
    }

    public void setSelectedInnerCommOldInboxView(InternalCommunication selectedInnerCommOldInboxView) {
        this.selectedInnerCommOldInboxView = selectedInnerCommOldInboxView;
    }

    public boolean isIsRejected() {
        return isRejected;
    }

    public void setIsRejected(boolean isRejected) {
        this.isRejected = isRejected;
    }

    public boolean isIsNotes() {
        return isNotes;
    }

    public void setIsNotes(boolean isNotes) {
        this.isNotes = isNotes;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public List<IcFolders> getAllIcFolders() {
        return allIcFolders;
    }

    public void setAllIcFolders(List<IcFolders> allIcFolders) {
        this.allIcFolders = allIcFolders;
    }

    public IcFolders getSelectedIcFolder() {
        return selectedIcFolder;
    }

    public void setSelectedIcFolder(IcFolders selectedIcFolder) {
        this.selectedIcFolder = selectedIcFolder;
    }

    public List<Employees> getSelectedIcTos() {
        return selectedIcTos;
    }

    public void setSelectedIcTos(List<Employees> selectedIcTos) {
        this.selectedIcTos = selectedIcTos;
    }

    public IcTo getSelectedIctoToAdd() {
        return selectedIctoToAdd;
    }

    public void setSelectedIctoToAdd(IcTo selectedIctoToAdd) {
        this.selectedIctoToAdd = selectedIctoToAdd;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public InternalCommunication getSelectedInterCommOutboxView() {
        return selectedInterCommOutboxView;
    }

    public void setSelectedInterCommOutboxView(InternalCommunication selectedInterCommOutboxView) {
        this.selectedInterCommOutboxView = selectedInterCommOutboxView;
    }

    public IcFolders getSelectedOutBoxFolder() {
        return selectedOutBoxFolder;
    }

    public void setSelectedOutBoxFolder(IcFolders selectedOutBoxFolder) {
        this.selectedOutBoxFolder = selectedOutBoxFolder;
    }

    public List<IcTo> getAllOutBoxViewTos() {
        return allOutBoxViewTos;
    }

    public void setAllOutBoxViewTos(List<IcTo> allOutBoxViewTos) {
        this.allOutBoxViewTos = allOutBoxViewTos;
    }

    public IcTo getSelectedOutBoxViewTo() {
        return selectedOutBoxViewTo;
    }

    public void setSelectedOutBoxViewTo(IcTo selectedOutBoxViewTo) {
        this.selectedOutBoxViewTo = selectedOutBoxViewTo;
    }

    public PhaseListenerImpl getPhaseListenerImpl() {
        return phaseListenerImpl;
    }

    public void setPhaseListenerImpl(PhaseListenerImpl phaseListenerImpl) {
        this.phaseListenerImpl = phaseListenerImpl;
    }

    public List<IcFolders> getAllInboxFolderWithDraftTrash() {
        return allInboxFolderWithDraftTrash;
    }

    public void setAllInboxFolderWithDraftTrash(List<IcFolders> allInboxFolderWithDraftTrash) {
        this.allInboxFolderWithDraftTrash = allInboxFolderWithDraftTrash;
    }

    public List<IcFolders> getAllInboxFolderWithoutDraftTrash() {
        return allInboxFolderWithoutDraftTrash;
    }

    public void setAllInboxFolderWithoutDraftTrash(List<IcFolders> allInboxFolderWithoutDraftTrash) {
        this.allInboxFolderWithoutDraftTrash = allInboxFolderWithoutDraftTrash;
    }

    public List<IcFolders> getAllOutoxFolderwithDraftTrash() {
        return allOutoxFolderwithDraftTrash;
    }

    public void setAllOutoxFolderwithDraftTrash(List<IcFolders> allOutoxFolderwithDraftTrash) {
        this.allOutoxFolderwithDraftTrash = allOutoxFolderwithDraftTrash;
    }

    public List<IcFolders> getAllOutoxFolderwithoutDraftTrash() {
        return allOutoxFolderwithoutDraftTrash;
    }

    public void setAllOutoxFolderwithoutDraftTrash(List<IcFolders> allOutoxFolderwithoutDraftTrash) {
        this.allOutoxFolderwithoutDraftTrash = allOutoxFolderwithoutDraftTrash;
    }

    /**
     * Creates a new instance of InternalCommunicationDAO
     */
    public InternalCommunicationDAO() {
        this.allTos = new ArrayList<Employees>();
        this.selectedTo = new Employees();
        this.selectedInterCommOutAdd = new InternalCommunication();
        this.selectedInterCommInboxView = new InternalCommunication();
        this.selectedInnerCommOldInboxView = new InternalCommunication();
        this.allOutboxInterCommunication = new ArrayList<InternalCommunication>();
        this.allInboxInterCommunication = new ArrayList<InternalCommunication>();
        this.allOldInboxInterCommunication = new ArrayList<InternalCommunication>();
        this.selectedInterCommOutboxView = new InternalCommunication();
        this.allIcFolders = new ArrayList<IcFolders>();
        this.selectedIcTos = new ArrayList<Employees>();
        this.selectedIctoToAdd = new IcTo();
        this.selectedOutBoxFolder = new IcFolders();
        this.allOutBoxViewTos = new ArrayList<IcTo>();

        // Folder Lists
        this.allInboxFolderWithDraftTrash = new ArrayList<IcFolders>();
        this.allInboxFolderWithoutDraftTrash = new ArrayList<IcFolders>();
        this.allOutoxFolderwithDraftTrash = new ArrayList<IcFolders>();
        this.allOutoxFolderwithoutDraftTrash = new ArrayList<IcFolders>();


        this.selectedAttachement = new Attachements();
        this.selectedIcFolder = new IcFolders();
        this.root = new DefaultTreeNode("root", null);
        TreeNode Inbox = new DefaultTreeNode("Inbox", this.root);
        TreeNode Outbox = new DefaultTreeNode("Outbox", this.root);

        this.setSelectedNode(Inbox);

        this.setStrCheckEmp("");
        this.setActiveTab(true);
        this.setIsRejected(true);
        this.setIsNotes(true);
        this.setCurrentIndex(0);

        System.out.println(" New InternalCommunicationDAO is created......");
    }

//    public void retrieveAllTos() {
//
//        Session session = null;
//        Transaction trx = null;
//        Criteria cr = null;
//        try {
//            if (!FacesContext.getCurrentInstance().isPostback()) {
//                FacesContext facesContext = FacesContext.getCurrentInstance();
//                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
//
//                int id = loginDAO.getLoggedinUser().getEmployees().getId();
//                this.allTos.clear();
//                session = HibernateUtil.getSession();
//                trx = session.beginTransaction();
//
//                cr = session.createCriteria(Employees.class, "emp")
//                        .add(Restrictions.ne("emp.id", id));
//                cr.setMaxResults(50);
//                this.allTos.addAll(cr.list());
//                trx.commit();
//            }
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//
//        } finally {
//            if (cr != null) {
//                cr = null;
//            }
//            if (trx != null) {
//                trx = null;
//            }
//            if (session != null) {
//                session.clear();
//                session.close();
//                session = null;
//            }
//        }
//
//    }
    public List<Employees> completeEmployee(String employee) {
        Session session = null;
        Transaction trx = null;
        Criteria cr = null;
        this.allTos.clear();
        try {
            if (employee != null || !employee.equals("")) {
                List<Employees> suggessionEmployees = new ArrayList<Employees>();
                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
                int id = loginDAO.getLoggedinUser().getEmployees().getId();
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                cr = session.createCriteria(Employees.class, "emp")
                        .add(Restrictions.ne("emp.id", id));
                cr.setMaxResults(50);
                this.allTos.addAll(cr.list());
                trx.commit();
                for (Employees e : allTos) {
                    if ((e.getFirstname().toLowerCase()).startsWith(employee.toLowerCase())) {
                        suggessionEmployees.add(e);
                    }
                }
                return suggessionEmployees;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    int count = 0;

    public void checkValidEmployee() {

        Session session = null;
        Transaction trx = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());
        Criteria cr = null;
        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();



            cr = session.createCriteria(Users.class, "usr")
                    .createAlias("usr.employees", "emp", JoinType.INNER_JOIN)
                    .add(Restrictions.eq("emp.id", this.selectedIcTos.get(count).getId()));

            count++;

            if (cr.list().isEmpty()) {
                String message = text.getString("ui.Bean.notvalidUser");
                this.setStrCheckEmp(message);
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbvOutbox:frmOutAdd:frmOutAdd");
            } else {
                this.setStrCheckEmp("");
            }
            trx.commit();

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

    public void handleUnselect(UnselectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "To removed successfully", null);

        FacesContext.getCurrentInstance().addMessage(null, message);
        this.setStrCheckEmp(null);
        count--;
    }

    public void addIntCommOut() {
        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        Criteria cr = null;

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();



            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
            Date date = new Date();

            if (this.selectedIcTos == null) {
                String message = text.getString("ui.Bean.ToRequired");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbvOutbox");

            } else if (selectedInterCommOutAdd.getDetails() == null || selectedInterCommOutAdd.getDetails().equals("")) {
                String message = text.getString("ui.msg.details");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbvOutbox");

            } else {

                this.selectedInterCommOutAdd.setCreatedby(loginDAO.getLoggedinUser().getId());
                this.selectedInterCommOutAdd.setCreatedon(new Timestamp(date.getTime()));
                this.selectedInterCommOutAdd.setRequestFrom(loginDAO.getLoggedinUser().getEmployees());
                this.selectedInterCommOutAdd.setStatus('O');
                this.selectedInterCommOutAdd.setMsgFlag('S');
                this.selectedInterCommOutAdd.setRequestDate(date);

                session.save(this.selectedInterCommOutAdd);


                for (int i = 0; i < this.selectedIcTos.size(); i++) {

                    this.selectedIctoToAdd.setInternalcommunication(selectedInterCommOutAdd);
                    this.selectedIctoToAdd.setRequestTo(selectedIcTos.get(i));
                    this.selectedIctoToAdd.setMsgFlag('I');
                    this.selectedIctoToAdd.setCreatedby(loginDAO.getLoggedinUser().getId());
                    this.selectedIctoToAdd.setCreatedon(new Timestamp(date.getTime()));

                    session.save(this.selectedIctoToAdd);

                    this.selectedIctoToAdd = null;
                    this.selectedIctoToAdd = new IcTo();

                }


                trx.commit();

                this.retrieveAllOutboxIntercommunication();

                String message = text.getString("ui.Bean.IntCommAddSuccess");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));

                this.setSelectedTo(null);
                this.setSelectedTo(new Employees());
                this.setActiveTab(false);
                this.setActiveIndex(1);
                RequestContext.getCurrentInstance().update("tbvInternalComunication:tbvOutbox");
                counter++;
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
//Counter file upload
    int i = 0;

// for uploading Attached Document
    public void handleFileUpload(FileUploadEvent event) {
        ++i;

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
            String filename = event.getFile().getFileName();
            filename = filename.replaceAll(" ", "_");
            String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());


// for attach file
            this.id = this.selectedInterCommOutAdd.getId();

            int from = this.selectedInterCommOutAdd.getRequestFrom().getId();

            File file1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "attachment" + seperator + id);
            file1.mkdir();

            File file2 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "attachment" + seperator + id + seperator + from + "_" + i + "_" + filename);
            result.renameTo(file2);
            String strfilePath = seperator + "resources" + seperator + "attachment" + seperator + id + "_" + from + "_" + i + "_" + filename;
            this.selectedAttachement.setInternalcommunication(this.getSelectedInterCommOutAdd());
            this.selectedAttachement.setFilename(strfilePath);
            this.addAttachment();


        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    text.getString("ui.Bean.FileNotUpload"), "");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }

    public void addAttachment() {

        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());


        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
            Date date = new Date();

            this.selectedAttachement.setCreatedby(loginDAO.getLoggedinUser().getId());
            this.selectedAttachement.setCreatedon(new Timestamp(date.getTime()));

            session.save(this.selectedAttachement);
            trx.commit();
            String message = text.getString("ui.Bean.AttachmentAddSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            this.retrieveAllOutboxIntercommunication();
            RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbvOutbox:frmOutAdd");
            this.counter++;


        } catch (Exception e) {
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

    public void retrieveInboxFolders() {

        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int id = loginDAO.getLoggedinUser().getEmployees().getId();

            if ("viewInbox".equals(mainIncludeFilesSelect.getIncludeFile())) {

                this.allInboxFolderWithoutDraftTrash.clear();
                cr = session.createCriteria(IcFolders.class, "folders")
                        .add(Restrictions.eq("folders.employees.id", id))
                        .add(Restrictions.eq("folders.type", 'R'))
                        .setMaxResults(50);
                this.allInboxFolderWithoutDraftTrash.addAll(cr.list());

            } else if (!"viewInbox".equals(mainIncludeFilesSelect.getIncludeFile())) {
                this.allInboxFolderWithDraftTrash.clear();
                cr = session.createCriteria(IcFolders.class, "folders")
                        .add(Restrictions.eq("folders.employees.id", id))
                        .add(Restrictions.eq("folders.type", 'R'))
                        .setMaxResults(50);
//                IcFolders icf = new IcFolders();
//                icf.setName("Draft");
//                icf.setId(-1);

                IcFolders icf1 = new IcFolders();
                icf1.setName("Trash");
                icf1.setId(-2);

//                this.allInboxFolderWithDraftTrash.add(icf);
                this.allInboxFolderWithDraftTrash.add(icf1);
                this.allInboxFolderWithDraftTrash.addAll(cr.list());
            }

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

    public void retrieveOutboxFolders() {

        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int id = loginDAO.getLoggedinUser().getEmployees().getId();
            if ("viewOutbox".equals(mainIncludeFilesSelect.getIncludeFile())) {

                this.allOutoxFolderwithoutDraftTrash.clear();

                cr = session.createCriteria(IcFolders.class, "folders")
                        .add(Restrictions.eq("folders.employees.id", id))
                        .add(Restrictions.eq("folders.type", 'S'))
                        .setMaxResults(50);
                this.allOutoxFolderwithoutDraftTrash.addAll(cr.list());
            } else if (!"viewOutbox".equals(mainIncludeFilesSelect.getIncludeFile())) {

                this.allOutoxFolderwithDraftTrash.clear();
                cr = session.createCriteria(IcFolders.class, "folders")
                        .add(Restrictions.eq("folders.employees.id", id))
                        .add(Restrictions.eq("folders.type", 'S'))
                        .setMaxResults(50);
                IcFolders icf = new IcFolders();
                icf.setName("Draft");
                icf.setId(-1);

                IcFolders icf1 = new IcFolders();
                icf1.setName("Trash");
                icf1.setId(-2);

                this.allOutoxFolderwithDraftTrash.add(icf);
                this.allOutoxFolderwithDraftTrash.add(icf1);
                this.allOutoxFolderwithDraftTrash.addAll(cr.list());

            }

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

//    public void retrieveAllFolders() {
//
//        Session session = null;
//        Transaction trx = null;
//        Criteria cr = null;
//
//        try {
//            session = HibernateUtil.getSession();
//            trx = session.beginTransaction();
//
//            this.allIcFolders.clear();
//            FacesContext facesContext = FacesContext.getCurrentInstance();
//            MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);
//            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
//
//            int id = loginDAO.getLoggedinUser().getEmployees().getId();
//            if (this.getCurrentIndex() == 0) {
//                cr = session.createCriteria(IcFolders.class, "folders")
//                        .add(Restrictions.eq("folders.employees.id", id))
//                        .add(Restrictions.eq("folders.type", 'R'))
//                        .setMaxResults(50);
//
//                this.allIcFolders.addAll(cr.list());
//
//            } else if (this.getCurrentIndex() == 1) {
//
//                IcFolders icf = new IcFolders();
//                icf.setName("Draft");
//                icf.setId(-1);
//
//                cr = session.createCriteria(IcFolders.class, "folders")
//                        .add(Restrictions.eq("folders.employees.id", id))
//                        .add(Restrictions.eq("folders.type", 'S'))
//                        .setMaxResults(50);
//                this.allIcFolders.add(icf);
//                this.allIcFolders.addAll(cr.list());
//
//            }
//            trx.commit();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//
//        } finally {
//            if (cr != null) {
//                cr = null;
//            }
//            if (trx != null) {
//                trx = null;
//            }
//            if (session != null) {
//                session.clear();
//                session.close();
//                session = null;
//            }
//        }
//
//
//    }
    public void retrieveAllOutboxIntercommunication() {

        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        try {
            if (!FacesContext.getCurrentInstance().isPostback() || counter > 0) {
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                this.allOutboxInterCommunication.clear();
                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                int id = loginDAO.getLoggedinUser().getEmployees().getId();

                cr = session.createCriteria(InternalCommunication.class, "internalCommunication")
                        .add(Restrictions.eq("internalCommunication.requestFrom.id", id))
                        .add(Restrictions.ne("internalCommunication.status", 'C'))
                        .add(Restrictions.isNull("internalCommunication.outboxicfolders.id"))
                        .add(Restrictions.eq("internalCommunication.msgFlag", 'S'));


//                cr = session.createCriteria(IcTo.class, "icTo")
//                        .createAlias("icTo.requestTo", "requestTo", JoinType.INNER_JOIN)
//                        .createAlias("icTo.internalcommunication", "internalCommunication", JoinType.INNER_JOIN)
//                        .add(Restrictions.eq("internalCommunication.requestFrom.id", id))
//                        .add(Restrictions.ne("internalCommunication.status", 'C'))
//                        .add(Restrictions.isNull("internalCommunication.outboxicfolders.id"));
//
                cr.setMaxResults(50);

                this.allOutboxInterCommunication.addAll(cr.list());
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

    public void onFolderSelectRetrieveInbox() {

        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            this.allInboxInterCommunication.clear();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int id = loginDAO.getLoggedinUser().getEmployees().getId();

            if (this.selectedIcFolder.getId() == 0) {

                cr = session.createCriteria(InternalCommunication.class, "internalCommunication")
                        .createAlias("internalCommunication.requestFrom", "requestFrom", JoinType.INNER_JOIN)
                        .createAlias("internalCommunication.icTo", "icTo", JoinType.INNER_JOIN)
                        .add(Restrictions.eq("icTo.requestTo.id", id))
                        .add(Restrictions.disjunction().add(Restrictions.eq("internalCommunication.status", 'O'))
                        .add(Restrictions.eq("internalCommunication.status", 'I')))
                        .add(Restrictions.isNull("icTo.inboxicfolders.id"))
                        .add(Restrictions.eq("icTo.msgFlag", 'I'));

                cr.setMaxResults(50);
                this.allInboxInterCommunication.addAll(cr.list());

            } else if (this.selectedIcFolder.getId() == -2) {

                cr = session.createCriteria(InternalCommunication.class, "internalCommunication")
                        .createAlias("internalCommunication.requestFrom", "requestFrom", JoinType.INNER_JOIN)
                        .createAlias("internalCommunication.icTo", "icTo", JoinType.INNER_JOIN)
                        .add(Restrictions.eq("icTo.requestTo.id", id))
                        .add(Restrictions.disjunction().add(Restrictions.eq("internalCommunication.status", 'O'))
                        .add(Restrictions.eq("internalCommunication.status", 'I')))
                        .add(Restrictions.isNull("icTo.inboxicfolders.id"))
                        .add(Restrictions.eq("icTo.msgFlag", 'T'));

                cr.setMaxResults(50);
                this.allInboxInterCommunication.addAll(cr.list());

//            } else if (this.selectedIcFolder.getId() == -1) {
                // this.allInboxInterCommunication = null;
//                cr = session.createCriteria(InternalCommunication.class, "internalCommunication")
//                        .createAlias("internalCommunication.requestFrom", "requestFrom", JoinType.INNER_JOIN)
//                        .createAlias("internalCommunication.icTo", "icTo", JoinType.INNER_JOIN)
//                        .add(Restrictions.eq("icTo.requestTo.id", id))
//                        .add(Restrictions.disjunction().add(Restrictions.eq("internalCommunication.status", 'O'))
//                        .add(Restrictions.eq("internalCommunication.status", 'I')))
//                        .add(Restrictions.eq("icTo.inboxicfolders.id", null))
//                        .add(Restrictions.eq("icTo.msgFlag", 'T'));
//
//                cr.setMaxResults(50);
//                this.allInboxInterCommunication.addAll(cr.list());


            } else {
                cr = session.createCriteria(InternalCommunication.class, "internalCommunication")
                        .createAlias("internalCommunication.requestFrom", "requestFrom", JoinType.INNER_JOIN)
                        .createAlias("internalCommunication.icTo", "icTo", JoinType.INNER_JOIN)
                        .add(Restrictions.eq("icTo.requestTo.id", id))
                        .add(Restrictions.disjunction().add(Restrictions.eq("internalCommunication.status", 'O'))
                        .add(Restrictions.eq("internalCommunication.status", 'I')))
                        .add(Restrictions.eq("icTo.inboxicfolders.id", this.selectedIcFolder.getId()));

                cr.setMaxResults(50);
                this.allInboxInterCommunication.addAll(cr.list());

            }

            trx.commit();
            this.selectedInterCommInboxView = null;
            this.selectedInterCommInboxView = new InternalCommunication();
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

    public void onFolderSelectRetrieveOutbox() {

        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int id = loginDAO.getLoggedinUser().getEmployees().getId();
            this.allOutboxInterCommunication.clear();


            if (this.selectedOutBoxFolder.getId() == -1) {
                cr = session.createCriteria(InternalCommunication.class, "internalCommunication")
                        .add(Restrictions.eq("internalCommunication.requestFrom.id", id))
                        .add(Restrictions.ne("internalCommunication.status", 'C'))
                        .add(Restrictions.isNull("internalCommunication.outboxicfolders.id"))
                        .add(Restrictions.eq("internalCommunication.msgFlag", 'D'));

                cr.setMaxResults(50);

                this.allOutboxInterCommunication.addAll(cr.list());

                trx.commit();

            } else if (this.selectedOutBoxFolder.getId() == -2) {
                cr = session.createCriteria(InternalCommunication.class, "internalCommunication")
                        .add(Restrictions.eq("internalCommunication.requestFrom.id", id))
                        .add(Restrictions.ne("internalCommunication.status", 'C'))
                        .add(Restrictions.isNull("internalCommunication.outboxicfolders.id"))
                        .add(Restrictions.eq("internalCommunication.msgFlag", 'T'));
                cr.setMaxResults(50);

                this.allOutboxInterCommunication.addAll(cr.list());

                trx.commit();
            } else if (this.selectedOutBoxFolder.getId() == 0) {
                cr = session.createCriteria(InternalCommunication.class, "internalCommunication")
                        .add(Restrictions.eq("internalCommunication.requestFrom.id", id))
                        .add(Restrictions.ne("internalCommunication.status", 'C'))
                        .add(Restrictions.isNull("internalCommunication.outboxicfolders.id"))
                        .add(Restrictions.ne("internalCommunication.msgFlag", 'D'))
                        .add(Restrictions.ne("internalCommunication.msgFlag", 'T'))
                        .add(Restrictions.ne("internalCommunication.msgFlag", 'X'));
                cr.setMaxResults(50);

                this.allOutboxInterCommunication.addAll(cr.list());

                trx.commit();
            } else {
                cr = session.createCriteria(InternalCommunication.class, "internalCommunication")
                        .add(Restrictions.eq("internalCommunication.requestFrom.id", id))
                        .add(Restrictions.ne("internalCommunication.status", 'C'))
                        .add(Restrictions.eq("internalCommunication.outboxicfolders.id", this.selectedOutBoxFolder.getId()));

                cr.setMaxResults(50);

                this.allOutboxInterCommunication.addAll(cr.list());

                trx.commit();
                this.selectedInterCommOutboxView = null;
                this.selectedInterCommOutboxView = new InternalCommunication();
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

    public void retrieveAllInboxIntercommunication() {

        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        try {
            if (!FacesContext.getCurrentInstance().isPostback() || counter > 0) {
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                this.allInboxInterCommunication.clear();

                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                int id = loginDAO.getLoggedinUser().getEmployees().getId();

                cr = session.createCriteria(InternalCommunication.class, "internalCommunication")
                        .createAlias("internalCommunication.requestFrom", "requestFrom", JoinType.INNER_JOIN)
                        .createAlias("internalCommunication.icTo", "icTo", JoinType.INNER_JOIN)
                        .add(Restrictions.eq("icTo.requestTo.id", id))
                        .add(Restrictions.disjunction().add(Restrictions.eq("internalCommunication.status", 'O'))
                        .add(Restrictions.eq("internalCommunication.status", 'I')))
                        .add(Restrictions.isNull("icTo.inboxicfolders.id"))
                        .add(Restrictions.eq("icTo.msgFlag", 'I'));

                cr.setMaxResults(50);
                this.allInboxInterCommunication.addAll(cr.list());
                trx.commit();
                counter = 0;

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

    public void retrieveAllOldInboxIntercommunication() {

        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                this.allOldInboxInterCommunication.clear();

                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                int id = loginDAO.getLoggedinUser().getEmployees().getId();

                cr = session.createCriteria(InternalCommunication.class, "internalCommunication")
                        .createAlias("internalCommunication.requestFrom", "requestFrom", JoinType.INNER_JOIN)
                        .createAlias("internalCommunication.icTo", "icTo", JoinType.LEFT_OUTER_JOIN)
                        .add(Restrictions.eq("icTo.requestTo.id", id))
                        .add(Restrictions.disjunction().add(Restrictions.eq("internalCommunication.status", 'C'))
                        .add(Restrictions.eq("internalCommunication.status", 'R')));

                cr.setMaxResults(50);
                this.allOldInboxInterCommunication.addAll(cr.list());
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

    public void viewInbox() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);

        if (this.selectedIcFolder.getId() == -2) {
            mainIncludeFilesSelect.setIncludeFile("viewInboxTrash");
        } else {
            mainIncludeFilesSelect.setIncludeFile("viewInbox");
        }
    }

    public void viewOutbox() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);
        if (this.selectedOutBoxFolder.getId() != -1 && this.selectedOutBoxFolder.getId() != -2) {
            mainIncludeFilesSelect.setIncludeFile("viewOutbox");
        } else if (this.selectedOutBoxFolder.getId() == -1) {
            mainIncludeFilesSelect.setIncludeFile("addDraftMessage");
            this.setSelectedInterCommOutAdd(this.getSelectedInterCommOutboxView());
        } else if (this.selectedOutBoxFolder.getId() == -2) {
            mainIncludeFilesSelect.setIncludeFile("viewTrashMessage");
            this.setSelectedInterCommOutAdd(this.getSelectedInterCommOutboxView());
        }
//        this.checkStatusForOutbox();
    }

    public void retrieveAllOutboxViewTos() {

        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            this.allOutBoxViewTos.clear();

            cr = session.createCriteria(IcTo.class, "icTo")
                    .createAlias("icTo.internalcommunication", "internalcommunication", JoinType.INNER_JOIN)
                    .createAlias("icTo.requestTo", "requestTo", JoinType.INNER_JOIN)
                    .add(Restrictions.eq("internalcommunication.id", this.selectedInterCommOutboxView.getId()));

            cr.setMaxResults(50);

            this.allOutBoxViewTos.addAll(cr.list());

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

    public void finishUploading() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);

        this.setStrCheckEmp("");
        this.selectedInterCommOutAdd = null;
        this.setSelectedInterCommOutAdd(new InternalCommunication());

        this.selectedTo = null;
        this.setSelectedTo(new Employees());

        this.selectedInnerCommOldInboxView = null;
        this.setSelectedInnerCommOldInboxView(new InternalCommunication());

        mainIncludeFilesSelect.setIncludeFile(null);
        i = 0;

        RequestContext req = RequestContext.getCurrentInstance();
        this.setActiveTab(true);
        count = 0;

        this.selectedOutBoxFolder = null;
        this.selectedOutBoxFolder = new IcFolders();

        this.selectedInterCommOutboxView = null;
        this.selectedInterCommOutboxView = new InternalCommunication();

        this.onFolderSelectRetrieveInbox();
        this.onFolderSelectRetrieveOutbox();

        RequestContext.getCurrentInstance().update(":tbvInternalComunication");
        RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbvOutbox:frmOutbox");
        RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbvInbox:frmInbox");

    }

    public void removeSelected() {

        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("DlgConfirmForInbox.hide()");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);

        mainIncludeFilesSelect.setIncludeFile("oldView");

        req.execute("DlgConfirmForInbox.show()");
        req.update(":tbvInternalComunication:confirmDlgForInbox");


    }

    public void onTabChange(TabChangeEvent event) {

        if (event.getTab().getTitle().equals("Inbox")) {

            this.setCurrentIndex(0);

        } else if (event.getTab().getTitle().equals("Outbox")) {
            this.setCurrentIndex(1);
        }

    }

    public void backToOldView() {

        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("DlgConfirmForInbox.hide()");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);

        mainIncludeFilesSelect.setIncludeFile("old");

        req.execute("DlgConfirmForInbox.show()");
        req.update(":tbvInternalComunication:confirmDlgForInbox");

    }

    public void checkStatusForInboxView() {
        if (this.selectedInterCommInboxView.getStatus() == 'R') {
            this.setIsRejected(false);
            this.setIsNotes(true);
        } else if (this.selectedInterCommInboxView.getStatus() == 'I' || this.selectedInterCommInboxView.getStatus() == 'C') {
            this.setIsNotes(false);
            this.setIsRejected(true);
        } else {
            this.setIsNotes(true);
            this.setIsRejected(true);
        }
    }

    public void checkStatusForOutboxView() {
        if (this.selectedInterCommOutboxView.getStatus() == 'R') {
            this.setIsRejected(false);
            this.setIsNotes(true);
        } else if (this.selectedInterCommOutboxView.getStatus() == 'I' || this.selectedInterCommOutboxView.getStatus() == 'C') {
            this.setIsNotes(false);
            this.setIsRejected(true);
        } else {
            this.setIsNotes(true);
            this.setIsRejected(true);
        }
        RequestContext.getCurrentInstance().update(":tbvInternalComunication:confirmDlgForOutbox:scrOutboxView:frmviewOutbox");
    }

    public void updateSelectedInboxView() {


        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());
        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            if (this.selectedInterCommInboxView.getStatus() == 'R') {
                if (this.selectedInterCommInboxView.getRejectedDate() == null) {
                    String message = text.getString("ui.Bean.RejectDateRequired");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
//                    RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbInbox:frmInbox");
                } else if (this.selectedInterCommInboxView.getRejectedReason().equals("")) {
                    String message = text.getString("ui.Bean.RejectReasonRequired");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
//                    RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbInbox:frmInbox");
                } else {
                    session.update(this.getSelectedInterCommInboxView());
                    trx.commit();
                    counter++;
                    String message = text.getString("ui.Bean.InboxUpdatesuccess");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                    RequestContext.getCurrentInstance().execute("DlgConfirmForInbox.hide()");
//                    RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbInbox:frmInbox");

                }
            } else if (this.selectedInterCommInboxView.getStatus() == 'I' || this.selectedInterCommInboxView.getStatus() == 'C') {
                if (this.selectedInterCommInboxView.getNotes().equals("")) {
                    String message = text.getString("ui.Bean.NotesRequired");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
//                    RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbInbox:frmInbox");
                } else {
                    session.update(this.getSelectedInterCommInboxView());
                    trx.commit();
                    counter++;
                    String message = text.getString("ui.Bean.InboxUpdatesuccess");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
//                    RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbInbox:frmInbox");
                    RequestContext.getCurrentInstance().execute("DlgConfirmForInbox.hide()");
                }

            } else if (this.selectedInterCommInboxView.getStatus() == 'O') {
                session.update(this.getSelectedInterCommInboxView());
                trx.commit();
                counter++;
                String message = text.getString("ui.Bean.InboxUpdatesuccess");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                RequestContext.getCurrentInstance().execute("DlgConfirmForInbox.hide()");

            }

            this.selectedIcFolder = null;
            this.selectedIcFolder = new IcFolders();


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

    public void onNodeSelect(NodeSelectEvent event) {

        String strNode = event.getTreeNode().toString();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);

        mainIncludeFilesSelect.setIncludeFile(strNode);


    }

    public void moveToInBoxMessageToSelectedFolder() {

        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            // this.selectedInterCommInboxView.get(this.getSelectedIcFolder());

            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int internalCommunicationId = this.selectedInterCommInboxView.getId();
            int requstToId = loginDAO.getLoggedinUser().getEmployees().getId();

            String hqlQuery = "update IcTo set inboxicfolders.id = :folder where requestTo.id = " + requstToId + " and internalcommunication.id = " + internalCommunicationId;

            Query q = null;

            q = session.createQuery(hqlQuery);
            q.setInteger("folder", this.selectedIcFolder.getId());

            q.executeUpdate();

            trx.commit();
            String message = text.getString("ui.Bean.InboxmsgmoveSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));

//            RequestContext.getCurrentInstance().execute("DlgConfirmForInbox.hide()");

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

    public void moveToOutBoxMessageToSelectedFolder() {

        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int internalCommunicationId = this.selectedInterCommOutboxView.getId();
            int requstFromId = loginDAO.getLoggedinUser().getEmployees().getId();

            String hqlQuery = "update InternalCommunication set outboxicfolders.id = :folder where requestFrom.id = " + requstFromId + " and id = " + internalCommunicationId;

            Query q = null;

            q = session.createQuery(hqlQuery);
            q.setInteger("folder", this.selectedOutBoxFolder.getId());

            q.executeUpdate();

            trx.commit();
            String message = text.getString("ui.Bean.OutboxmsgmoveSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            counter++;
            this.retrieveAllOutboxIntercommunication();


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

    public void removeSelectedInboxIcFolder() {

        this.setSelectedIcFolder(null);
        this.setSelectedIcFolder(new IcFolders());

        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("DlgInboxFolderConfirm.show()");
        req.update("confirmInboxFolderDlg");

    }

    public void removeSelectedOutboxIcFolder() {

        this.setSelectedIcFolder(null);
        this.setSelectedIcFolder(new IcFolders());

        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("DlgOutboxFolderConfirm.show()");
        req.update("confirmOutboxFolderDlg");

    }

    public void addDraftMessage() {
        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
            Employees emp = loginDAO.getLoggedinUser().getEmployees();
            Date date = new Date();
            if (this.selectedInterCommOutAdd.getDetails().equals("") || this.selectedInterCommOutAdd.getDetails() == null) {
                this.selectedInterCommOutAdd.setDetails(" ");
            }
            this.selectedInterCommOutAdd.setMsgFlag('D');
            this.selectedInterCommOutAdd.setStatus('O');
            this.selectedInterCommOutAdd.setRequestFrom(emp);
            this.selectedInterCommOutAdd.setRequestDate(date);
            this.selectedInterCommOutAdd.setCreatedon(new Timestamp(date.getTime()));
            this.selectedInterCommOutAdd.setCreatedby(emp.getId());

            session.save(this.selectedInterCommOutAdd);
            trx.commit();

            String message = text.getString("ui.Bean.DraftMsgAddSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            this.selectedInterCommOutAdd = null;
            this.selectedInterCommOutAdd = new InternalCommunication();


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

    public void updateDraftMessage() {

        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            session.update(this.selectedInterCommOutAdd);
            trx.commit();
            String message = text.getString("ui.Bean.DraftMsgUpdateSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));


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

    public void sendDraftMessage() {

        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        Criteria cr = null;

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
            Date date = new Date();

            if (this.selectedIcTos == null) {
                String message = text.getString("ui.Bean.ToRequired");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbvOutbox");

            } else if (selectedInterCommOutAdd.getDetails() == null || selectedInterCommOutAdd.getDetails().equals("") || selectedInterCommOutAdd.getDetails().equals(" ")) {
                String message = text.getString("ui.msg.details");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbvOutbox");

            } else {

                this.selectedInterCommOutAdd.setCreatedon(new Timestamp(date.getTime()));
                this.selectedInterCommOutAdd.setMsgFlag('S');
                this.selectedInterCommOutAdd.setRequestDate(date);

                session.update(this.selectedInterCommOutAdd);


                for (int i = 0; i < this.selectedIcTos.size(); i++) {

                    this.selectedIctoToAdd.setInternalcommunication(selectedInterCommOutAdd);
                    this.selectedIctoToAdd.setRequestTo(selectedIcTos.get(i));
                    this.selectedIctoToAdd.setMsgFlag('I');
                    this.selectedIctoToAdd.setCreatedby(loginDAO.getLoggedinUser().getId());
                    this.selectedIctoToAdd.setCreatedon(new Timestamp(date.getTime()));

                    session.save(this.selectedIctoToAdd);

                    this.selectedIctoToAdd = null;
                    this.selectedIctoToAdd = new IcTo();

                }


                trx.commit();

                this.retrieveAllOutboxIntercommunication();

                String message = text.getString("ui.Bean.IntCommAddSuccess");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));

                this.setSelectedTo(null);
                this.setSelectedTo(new Employees());
                this.setActiveTab(false);
                this.setActiveIndex(1);
                RequestContext.getCurrentInstance().update("tbvInternalComunication:tbvOutbox");
                counter++;
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

    public void moveInboxMsgToTrash() {
        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            // this.selectedInterCommInboxView.get(this.getSelectedIcFolder());

            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int internalCommunicationId = this.selectedInterCommInboxView.getId();
            int requstToId = loginDAO.getLoggedinUser().getEmployees().getId();

            String hqlQuery = "update IcTo set inboxicfolders.id = cast(null as int),msgFlag ='T' where requestTo.id = " + requstToId + " and internalcommunication.id = " + internalCommunicationId;

            Query q = null;

            q = session.createQuery(hqlQuery);
            q.executeUpdate();

            trx.commit();
            RequestContext.getCurrentInstance().execute("DlgConfirmForInbox.hide()");
            String message = text.getString("ui.Bean.InboxmsgmoveTrashSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            counter++;

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

    public void moveOutboxMsgToTrash() {

        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();//selectedInterCommOutboxView

            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int internalCommunicationId = this.selectedInterCommOutboxView.getId();
            int requstfromId = loginDAO.getLoggedinUser().getEmployees().getId();

            String strhql = "update InternalCommunication ic set ic.outboxicfolders.id = cast(null as int),ic.msgFlag = 'T' where ic.id = " + internalCommunicationId + " and ic.requestFrom = " + requstfromId;
            Query q = null;

            q = session.createQuery(strhql);
            q.executeUpdate();

            trx.commit();
            RequestContext.getCurrentInstance().execute("DlgConfirmForOutbox.hide()");
            RequestContext.getCurrentInstance().update(":tbvInternalComunication:tbvOutbox:frmOutbox");
            String message = text.getString("ui.Bean.OutboxmsgmoveTrashSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            counter++;
            this.retrieveAllOutboxIntercommunication();

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

    public void discardTrashMessage() {

        Session session = null;
        Transaction trx = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            session.delete(this.getSelectedInterCommOutAdd());
            trx.commit();
            RequestContext.getCurrentInstance().execute("DlgConfirmForInbox.hide()");
            String message = text.getString("ui.Bean.DiscardDraftMsgSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));

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

    public void removeSelectedTrashInboxMessage() {

        Session session = null;
        Transaction trx = null;

        Query q = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            // set the trash msg flag to x for removing the trash message by radio selection in Inbox

            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int internalCommunicationId = this.selectedInterCommInboxView.getId();
            int requstToId = loginDAO.getLoggedinUser().getEmployees().getId();
            String hqlQuery = "update IcTo set inboxicfolders.id = cast(null as int),msgFlag ='X' where requestTo.id = " + requstToId + " and internalcommunication.id = " + internalCommunicationId;

            q = session.createQuery(hqlQuery);
            q.executeUpdate();
            trx.commit();
            String message = text.getString("ui.Bean.DeleteTrashMsgSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            this.selectedIcFolder.setId(-2);
            this.onFolderSelectRetrieveInbox();

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

    public void removeSelectedTrashOutboxMessage() {

        Session session = null;
        Transaction trx = null;

        Query q = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            // set the trash msg flag to x for removing the trash message by radio selection in outbox

            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int internalCommunicationId = this.selectedInterCommOutboxView.getId();
            int requstFromId = loginDAO.getLoggedinUser().getEmployees().getId();

            String hqlQuery = "update InternalCommunication set outboxicfolders.id = cast(null as int),msgFlag ='X' where requestFrom.id = " + requstFromId + " and id = " + internalCommunicationId;

            q = session.createQuery(hqlQuery);
            q.executeUpdate();
            trx.commit();
            String message = text.getString("ui.Bean.DeleteTrashMsgSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            this.selectedOutBoxFolder.setId(-2);
            this.onFolderSelectRetrieveOutbox();


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

    public void emptyInboxTrash() {

        Session session = null;
        Transaction trx = null;
        Query q = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            // set the trash msg flag to x for removing the trash message by button click to empty trash

            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int requstToId = loginDAO.getLoggedinUser().getEmployees().getId();

            for (int i = 0; i < this.allInboxInterCommunication.size(); i++) {

                InternalCommunication ic = new InternalCommunication();
                ic = this.allInboxInterCommunication.get(i);
                String hqlQuery = "update IcTo set inboxicfolders.id = cast(null as int),msgFlag ='X' where requestTo.id = " + requstToId + " and internalcommunication.id = " + ic.getId();

                q = session.createQuery(hqlQuery);
                q.executeUpdate();


            }
            trx.commit();

            String message = text.getString("ui.Bean.DeleteAllInboxTrashMsgSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            this.selectedIcFolder.setId(-2);
            this.onFolderSelectRetrieveInbox();



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

    public void emptyOutboxTrash() {

        Session session = null;
        Transaction trx = null;
        Query q = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", facesContext.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            // set the trash msg flag to x for removing the trash message by button click to empty trash

            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int requstFromId = loginDAO.getLoggedinUser().getEmployees().getId();
            for (int i = 0; i < this.allOutboxInterCommunication.size(); i++) {
                int internalCommunicationId = this.allOutboxInterCommunication.get(i).getId();

                String hqlQuery = "update InternalCommunication set outboxicfolders.id = cast(null as int),msgFlag ='X' where requestFrom.id = " + requstFromId + " and id = " + internalCommunicationId;

                q = session.createQuery(hqlQuery);
                q.executeUpdate();

            }
            trx.commit();

//            this.selectedOutBoxFolder.setId(-2);
//            this.onFolderSelectRetrieveOutbox();

            String message = text.getString("ui.Bean.DeleteAllOutboxTrashMsgSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            this.selectedOutBoxFolder.setId(-2);
            this.onFolderSelectRetrieveOutbox();


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
}
