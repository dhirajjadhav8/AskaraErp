/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.IcFoldersConverter;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import manager.impl.IcFoldersManagerImpl;
import manager.impl.IcToManagerImpl;
import manager.impl.InternalCommunicationsManagerImpl;
import manager.interfaces.IIcFoldersManager;
import manager.interfaces.IIcToManager;
import manager.interfaces.IInternalCommunicationsManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import orm.IcFolders;
import orm.InternalCommunications;
import util.HibernateUtil;
import util.MainIncludeFilesSelect;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "internalCommunicationsView")
@ViewScoped
public class InternalCommunicationsView implements Serializable {

    /*
     Loginbean Inject 
     
     */
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;
    /*
     mainIncludeFilesSelect Inject 
     
     */
    @ManagedProperty(value = "#{mainIncludeFilesSelect}")
    private MainIncludeFilesSelect mainIncludeFilesSelect;
    /*
     InternalCommunicationManger
     */
    private IInternalCommunicationsManager icm = new InternalCommunicationsManagerImpl();
    private List<InternalCommunications> allInboxInterCommunication;
    private List<InternalCommunications> allOutboxInterCommunication;
    private List<InternalCommunications> allOldInboxInterCommunication;
    private InternalCommunications selectedInternalCommunication;
    //to show the old inbox internal communication details
    private InternalCommunications selectedOldInboxInternalCommunicationView;
    // for node tree
    private TreeNode root;
    // for selection node 
    private TreeNode selectedNode;
    TreeNode inbox;
    TreeNode outbox;
    // IcFolder 
    private IIcFoldersManager icfm = new IcFoldersManagerImpl();
    private List<IcFolders> allOutoxFolderwithDraftTrash;
    private List<IcFolders> allInboxFolderWithDraftTrash;
    private List<IcFolders> allInboxFolderWithoutDraftTrash;
    private List<IcFolders> allOutboxFolderWithDraftTrash;
    private List<IcFolders> allOutboxFolderWithoutDraftTrash;
    private IcFolders selectedIcFolder = new IcFolders();
    private IcFoldersConverter icFoldersConverter = new IcFoldersConverter();
    /*
     IcTos     
     */
    private IIcToManager ictm = new IcToManagerImpl();
    /*
     Boolean fields
     */
    private boolean isRejected;
    private boolean isNotes;

    /**
     * Creates a new instance of InternalCommunicationsView
     */
    public InternalCommunicationsView() {
        System.out.println("InternalCommunicationsView created...............");
        root = new DefaultTreeNode("root", null);
        inbox = new DefaultTreeNode("Inbox", this.root);
        outbox = new DefaultTreeNode("Outbox", this.root);

        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                HibernateUtil.beginTransaction();
                allInboxFolderWithoutDraftTrash = icfm.loadAll(IcFolders.class);
                icFoldersConverter.setSearchIcFolders(allInboxFolderWithoutDraftTrash);
                HibernateUtil.commitTransaction();
            }
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @PostConstruct
    public void postConstructInternalCommunicationsView() {
        retrieveInboxFoldersInFront();
        retrieveOutboxFoldersInFront();
        retrieveLoggedInEmployeeInboxFoldersWithoutTrash();
        retrieveAllLoggedinEmployeeOldInboxInternalCommunication();
        setIsRejected(true);
        setIsNotes(true);
    }

    public IInternalCommunicationsManager getIcm() {
        return icm;
    }

    public void setIcm(IInternalCommunicationsManager icm) {
        this.icm = icm;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getInbox() {
        return inbox;
    }

    public void setInbox(TreeNode inbox) {
        this.inbox = inbox;
    }

    public TreeNode getOutbox() {
        return outbox;
    }

    public void setOutbox(TreeNode outbox) {
        this.outbox = outbox;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public List<IcFolders> getAllOutoxFolderwithDraftTrash() {
        return allOutoxFolderwithDraftTrash;
    }

    public void setAllOutoxFolderwithDraftTrash(List<IcFolders> allOutoxFolderwithDraftTrash) {
        this.allOutoxFolderwithDraftTrash = allOutoxFolderwithDraftTrash;
    }

    public IIcFoldersManager getIcfm() {
        return icfm;
    }

    public void setIcfm(IIcFoldersManager icfm) {
        this.icfm = icfm;
    }

    public List<IcFolders> getAllInboxFolderWithDraftTrash() {
        return allInboxFolderWithDraftTrash;
    }

    public void setAllInboxFolderWithDraftTrash(List<IcFolders> allInboxFolderWithDraftTrash) {
        this.allInboxFolderWithDraftTrash = allInboxFolderWithDraftTrash;
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    public List<InternalCommunications> getAllInboxInterCommunication() {
        return allInboxInterCommunication;
    }

    public void setAllInboxInterCommunication(List<InternalCommunications> allInboxInterCommunication) {
        this.allInboxInterCommunication = allInboxInterCommunication;
    }

    public List<IcFolders> getAllInboxFolderWithoutDraftTrash() {
        return allInboxFolderWithoutDraftTrash;
    }

    public void setAllInboxFolderWithoutDraftTrash(List<IcFolders> allInboxFolderWithoutDraftTrash) {
        this.allInboxFolderWithoutDraftTrash = allInboxFolderWithoutDraftTrash;
    }

    public InternalCommunications getSelectedInternalCommunication() {
        return selectedInternalCommunication;
    }

    public void setSelectedInternalCommunication(InternalCommunications selectedInternalCommunication) {
        this.selectedInternalCommunication = selectedInternalCommunication;
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

    public List<IcFolders> getAllOutboxFolderWithDraftTrash() {
        return allOutboxFolderWithDraftTrash;
    }

    public void setAllOutboxFolderWithDraftTrash(List<IcFolders> allOutboxFolderWithDraftTrash) {
        this.allOutboxFolderWithDraftTrash = allOutboxFolderWithDraftTrash;
    }

    public List<IcFolders> getAllOutboxFolderWithoutDraftTrash() {
        return allOutboxFolderWithoutDraftTrash;
    }

    public void setAllOutboxFolderWithoutDraftTrash(List<IcFolders> allOutboxFolderWithoutDraftTrash) {
        this.allOutboxFolderWithoutDraftTrash = allOutboxFolderWithoutDraftTrash;
    }

    public List<InternalCommunications> getAllOutboxInterCommunication() {
        return allOutboxInterCommunication;
    }

    public void setAllOutboxInterCommunication(List<InternalCommunications> allOutboxInterCommunication) {
        this.allOutboxInterCommunication = allOutboxInterCommunication;
    }

    public IcFolders getSelectedIcFolder() {
        return selectedIcFolder;
    }

    public void setSelectedIcFolder(IcFolders selectedIcFolder) {
        this.selectedIcFolder = selectedIcFolder;
    }

    public MainIncludeFilesSelect getMainIncludeFilesSelect() {
        return mainIncludeFilesSelect;
    }

    public void setMainIncludeFilesSelect(MainIncludeFilesSelect mainIncludeFilesSelect) {
        this.mainIncludeFilesSelect = mainIncludeFilesSelect;
    }

    public IcFoldersConverter getIcFoldersConverter() {
        return icFoldersConverter;
    }

    public void setIcFoldersConverter(IcFoldersConverter icFoldersConverter) {
        this.icFoldersConverter = icFoldersConverter;
    }

    public List<InternalCommunications> getAllOldInboxInterCommunication() {
        return allOldInboxInterCommunication;
    }

    public void setAllOldInboxInterCommunication(List<InternalCommunications> allOldInboxInterCommunication) {
        this.allOldInboxInterCommunication = allOldInboxInterCommunication;
    }

    public InternalCommunications getSelectedOldInboxInternalCommunicationView() {
        return selectedOldInboxInternalCommunicationView;
    }

    public void setSelectedOldInboxInternalCommunicationView(InternalCommunications selectedOldInboxInternalCommunicationView) {
        this.selectedOldInboxInternalCommunicationView = selectedOldInboxInternalCommunicationView;
    }
    /*
     Select tree folder
     */
    private String treeFolder;

    public String getTreeFolder() {
        return treeFolder;
    }

    public void setTreeFolder(String treeFolder) {
        this.treeFolder = treeFolder;
    }

    /*
     Select folder     
     */
    public void folderSelect() {

        if (getSelectedNode().getParent() == inbox || getSelectedNode() == inbox) {
            System.out.println("in inbox folder ");
            setTreeFolder("Inbox");
            onFolderSelectRetrieveInbox();
        } else if (getSelectedNode().getParent() == outbox || getSelectedNode() == outbox) {
            System.out.println("in outbox folder ");
            setTreeFolder("Outbox");
            onFolderSelectRetrieveOutbox();
        }
    }

    //Retrieve employee inbox folder. 
    public void retrieveInboxFoldersInFront() {
        try {
            IcFolders icf1 = new IcFolders();
            icf1.setIcFolderName("Trash");
            icf1.setId(-2);
            HibernateUtil.beginTransaction();
            allInboxFolderWithDraftTrash = icfm.retrieveAllInboxFoldersForLoggedInEmployee(loginbean.getLoggedinUser().getEmployees().getId());
            HibernateUtil.commitTransaction();
            allInboxFolderWithDraftTrash.add(icf1);
            TreeNode tn = null;
            for (int i = 0; i < allInboxFolderWithDraftTrash.size(); i++) {
                tn = new DefaultTreeNode(allInboxFolderWithDraftTrash.get(i), inbox);
                tn = null;
            }
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }

    }

    //Retrieve employee outbox folder.  
    public void retrieveOutboxFoldersInFront() {
        try {
            IcFolders icf = new IcFolders();
            icf.setIcFolderName("Draft");
            icf.setId(-1);
            IcFolders icf1 = new IcFolders();
            icf1.setIcFolderName("Trash");
            icf1.setId(-2);
            HibernateUtil.beginTransaction();
            allOutoxFolderwithDraftTrash = icfm.retrieveAllOutboxFoldersForLoggedInEmployee(loginbean.getLoggedinUser().getEmployees().getId());
            HibernateUtil.commitTransaction();
            allOutoxFolderwithDraftTrash.add(icf);
            allOutoxFolderwithDraftTrash.add(icf1);

            TreeNode tn = null;
            for (int i = 0; i < allOutoxFolderwithDraftTrash.size(); i++) {
                tn = new DefaultTreeNode(allOutoxFolderwithDraftTrash.get(i), outbox);
                tn = null;
            }

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }
    /*      
     Retrieve All inbox intercommunication 
     */

    public void retrieveAllInboxIntercommunication() {
        try {
            HibernateUtil.beginTransaction();
            allInboxInterCommunication = icm.retrieveAllLimitedInboxIntercommunication(loginbean.getLoggedinUser().getEmployees().getId());
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }
    /*      
     Retrieve loggedin employee  inboxfolder
     */

    public void retrieveLoggedInEmployeeInboxFoldersWithoutTrash() {
        try {
            HibernateUtil.beginTransaction();
            allInboxFolderWithoutDraftTrash = icfm.retrieveLoggedInEmployeeInboxFoldersWithoutTrash(loginbean.getLoggedinUser().getEmployees().getId());
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*      
     Retrieve loggedin employee  outboxfolder
     */
    public void retrieveLoggedInEmployeeOutboxFoldersWithoutDraftTrash() {
        try {
            HibernateUtil.beginTransaction();
            allOutboxFolderWithoutDraftTrash = icfm.retrieveLoggedInEmployeeOutboxFoldersWithoutDraftTrash(loginbean.getLoggedinUser().getEmployees().getId());
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void onFolderSelectRetrieveInbox() {
        try {
            HibernateUtil.beginTransaction();
            if (selectedNode == this.getInbox()) {
                allInboxInterCommunication = icm.retrieveLimitedInboxFolderInterCommunication(loginbean.getLoggedinUser().getEmployees().getId());
            } else if (((IcFolders) selectedNode.getData()).getId() == -2) {
                allInboxInterCommunication = icm.retrieveLimitedInboxTrashFolderInterCommunication(loginbean.getLoggedinUser().getEmployees().getId());
            } else {
                allInboxInterCommunication = icm.retrieveLimitedInboxSelectedFolderInterCommunication(loginbean.getLoggedinUser().getEmployees().getId(), ((IcFolders) selectedNode.getData()).getId());
            }
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void onFolderSelectRetrieveOutbox() {
        try {
            HibernateUtil.beginTransaction();
            if (selectedNode == this.getOutbox()) {
                allOutboxInterCommunication = icm.retrieveLimitedOutboxFolderInterCommunication(loginbean.getLoggedinUser().getEmployees().getId());

            } else if (((IcFolders) selectedNode.getData()).getId() == -1) {
                allOutboxInterCommunication = icm.retrieveLimitedOutboxDraftFolderInterCommunication(loginbean.getLoggedinUser().getEmployees().getId());
            } else if (((IcFolders) selectedNode.getData()).getId() == -2) {
                allOutboxInterCommunication = icm.retrieveLimitedOutboxTrashFolderInterCommunication(loginbean.getLoggedinUser().getEmployees().getId());
            } else {
                allOutboxInterCommunication = icm.retrieveLimitedOutboxSelectedFolderInterCommunication(loginbean.getLoggedinUser().getEmployees().getId(), ((IcFolders) selectedNode.getData()).getId());
            }
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }
    /*
     check Status For selected OutboxView
     */

    public void checkStatusForSelectedInternalCommunicationView() {
        if (selectedInternalCommunication != null) {
            if (selectedInternalCommunication.getStatus() != ' ') {
                if (selectedInternalCommunication.getStatus() == 'R') {
                    setIsRejected(false);
                    setIsNotes(true);
                } else if (selectedInternalCommunication.getStatus() == 'I' || selectedInternalCommunication.getStatus() == 'C') {
                    setIsNotes(false);
                    setIsRejected(true);
                }
            } else {
                setIsNotes(true);
                setIsRejected(true);
            }
        } else {
            setIsNotes(true);
            setIsRejected(true);
        }
    }

    /* 
     Outbox View
     */
    public void viewOutbox() {
        if (((IcFolders) selectedNode.getData()).getId() != -1 && ((IcFolders) selectedNode.getData()).getId() != -2) {
            mainIncludeFilesSelect.setIncludeFile("viewOutbox");
        } else if (((IcFolders) selectedNode.getData()).getId() == -1) {
            mainIncludeFilesSelect.setIncludeFile("addDraftMessage");
        } else if (((IcFolders) selectedNode.getData()).getId() == -2) {
            mainIncludeFilesSelect.setIncludeFile("viewTrashMessage");
        }
    }
    /*
     Outbox view
     */

    public void viewInbox() {
        if (selectedNode.getData().equals("Inbox")) {
            mainIncludeFilesSelect.setIncludeFile("viewInbox");
        } else if (((IcFolders) selectedNode.getData()).getIcFolderName().equals("Trash")) {
            mainIncludeFilesSelect.setIncludeFile("viewInboxTrash");
        }
    }
    /*
     Update selected Inbox selected Internal communication.
     */

    public void updateSelectedInternalCommunicationView() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {
            HibernateUtil.beginTransaction();
            if (this.selectedInternalCommunication.getStatus() == 'R') {
                if (this.selectedInternalCommunication.getRejectedDate() == null) {
                    String message = text.getString("ui.Bean.RejectDateRequired");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                } else if (this.selectedInternalCommunication.getRejectedReason().equals("")) {
                    String message = text.getString("ui.Bean.RejectReasonRequired");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                } else {
                    icm.updateExisting(selectedInternalCommunication);
                    HibernateUtil.commitTransaction();
                    String message = text.getString("ui.Bean.InboxUpdatesuccess");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                    RequestContext.getCurrentInstance().execute("DlgConfirmForInbox.hide()");
                }
            } else if (this.selectedInternalCommunication.getStatus() == 'I' || this.selectedInternalCommunication.getStatus() == 'C') {
                if (this.selectedInternalCommunication.getNotes().equals("")) {
                    String message = text.getString("ui.Bean.NotesRequired");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

                } else {
                    icm.updateExisting(selectedInternalCommunication);
                    HibernateUtil.commitTransaction();
                    String message = text.getString("ui.Bean.InboxUpdatesuccess");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));

                    RequestContext.getCurrentInstance().execute("DlgConfirmForInbox.hide()");
                }
            } else if (selectedInternalCommunication.getStatus() == 'O') {
                icm.updateExisting(selectedInternalCommunication);
                HibernateUtil.commitTransaction();
                String message = text.getString("ui.Bean.InboxUpdatesuccess");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                RequestContext.getCurrentInstance().execute("DlgConfirmForInbox.hide()");
            }
            this.selectedIcFolder = null;
            this.selectedIcFolder = new IcFolders();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }
    /*
     On folder select move inbox selected internal communication to selected folder 
     */

    public void updateSelectedInboxInternalCommunicationToMoveInBoxMessageToSelectedFolder() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {
            HibernateUtil.beginTransaction();
            //ictm.moveInboxMessageToFolder(selectedIcFolder.getId(), loginbean.getLoggedinUser().getEmployees().getId(), selectedInternalCommunication.getId());
            HibernateUtil.commitTransaction();
            onFolderSelectRetrieveInbox();
            String message = text.getString("ui.Bean.InboxmsgmoveSuccess");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*
     Retrieve all old internal communication
     */
    public void retrieveAllLoggedinEmployeeOldInboxInternalCommunication() {
        try {
            HibernateUtil.beginTransaction();
            allOldInboxInterCommunication = icm.retrieveAllLoggedinEmployeeOldInboxInternalCommunication(loginbean.getLoggedinUser().getEmployees().getId());
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
