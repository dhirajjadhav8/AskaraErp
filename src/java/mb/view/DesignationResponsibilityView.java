/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.view;

import converter.DesignationsConverter;
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
import manager.impl.DesignationResponsibilitiesManagerImpl;
import manager.impl.DesignationsManagerImpl;
import manager.impl.ResponsibilitiesManagerImpl;
import manager.interfaces.IDesignationResponsibilitiesManager;
import manager.interfaces.IDesignationsManager;
import manager.interfaces.IResponsibilitiesManager;
import mb.util.session.LoginS;
import org.primefaces.context.RequestContext;
import orm.DesignationResponsibilities;
import orm.Designations;
import orm.Responsibilities;
import util.HibernateUtil;

/**
 *
 * @author pradipl
 */
@ManagedBean(name = "designationResponsibilityView")
@ViewScoped
public class DesignationResponsibilityView {

    // Designations
    private IDesignationsManager designationsManager = new DesignationsManagerImpl();
    private List<Designations> designationList;
    private Designations selectedDesignation = new Designations();
    private DesignationsConverter desigConverter = new DesignationsConverter();
    //DesignationResponsibilities
    private IDesignationResponsibilitiesManager DesignationResponsibilityManager = new DesignationResponsibilitiesManagerImpl();
    private List<DesignationResponsibilities> notAssigneddesignationResponsibilities;
    private List<DesignationResponsibilities> assignedDesignationResponsibilities;
    private List<DesignationResponsibilities> designationResponsibilitiesHistory;
    private DesignationResponsibilities selectDesignationresponsibilityToAdd;
    private DesignationResponsibilities selectDesignationResponsibilityToUpdate;
    //Responsibilities
    private IResponsibilitiesManager responsibilitiesManager = new ResponsibilitiesManagerImpl();
    @ManagedProperty(value = "#{loginbean}")
    private LoginS loginbean;

    /**
     * Creates a new instance of DesignationResponsibilityView
     */
    public DesignationResponsibilityView() {
        System.out.println("DesignationResponsibilityView created");
    }

    //post construct annotation
    @PostConstruct
    public void postConstruct() {
       loginbean.checkAccess();
        retrieveDesignationList();

    }

    public IDesignationsManager getDesignationsManager() {
        return designationsManager;
    }

    public void setDesignationsManager(IDesignationsManager designationsManager) {
        this.designationsManager = designationsManager;
    }

    public List<Designations> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<Designations> designationList) {
        this.designationList = designationList;
    }

    //we have return the is of selected designation
    public Designations getSelectedDesignation() {
        return selectedDesignation;
    }

    public void setSelectedDesignation(Designations selectedDesignation) {
        this.selectedDesignation = selectedDesignation;
    }

    public DesignationsConverter getDesigConverter() {
        return desigConverter;
    }

    public void setDesigConverter(DesignationsConverter desigConverter) {
        this.desigConverter = desigConverter;
    }

    public List<DesignationResponsibilities> getNotAssigneddesignationResponsibilities() {
        return notAssigneddesignationResponsibilities;
    }

    public void setNotAssigneddesignationResponsibilities(List<DesignationResponsibilities> notAssigneddesignationResponsibilities) {
        this.notAssigneddesignationResponsibilities = notAssigneddesignationResponsibilities;
    }

    public List<DesignationResponsibilities> getAssignedDesignationResponsibilities() {
        return assignedDesignationResponsibilities;
    }

    public void setAssignedDesignationResponsibilities(List<DesignationResponsibilities> assignedDesignationResponsibilities) {
        this.assignedDesignationResponsibilities = assignedDesignationResponsibilities;
    }

    public List<DesignationResponsibilities> getDesignationResponsibilitiesHistory() {
        return designationResponsibilitiesHistory;
    }

    public void setDesignationResponsibilitiesHistory(List<DesignationResponsibilities> designationResponsibilitiesHistory) {
        this.designationResponsibilitiesHistory = designationResponsibilitiesHistory;
    }

    public DesignationResponsibilities getSelectDesignationresponsibilityToAdd() {
        return selectDesignationresponsibilityToAdd;
    }

    public void setSelectDesignationresponsibilityToAdd(DesignationResponsibilities selectDesignationresponsibilityToAdd) {
        this.selectDesignationresponsibilityToAdd = selectDesignationresponsibilityToAdd;
    }

    public DesignationResponsibilities getSelectDesignationResponsibilityToUpdate() {
        return selectDesignationResponsibilityToUpdate;
    }

    public void setSelectDesignationResponsibilityToUpdate(DesignationResponsibilities selectDesignationResponsibilityToUpdate) {
        this.selectDesignationResponsibilityToUpdate = selectDesignationResponsibilityToUpdate;
    }

    public IDesignationResponsibilitiesManager getDesignationResponsibilityManager() {
        return DesignationResponsibilityManager;
    }

    public void setDesignationResponsibilityManager(IDesignationResponsibilitiesManager DesignationResponsibilityManager) {
        this.DesignationResponsibilityManager = DesignationResponsibilityManager;
    }

    public LoginS getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginS loginbean) {
        this.loginbean = loginbean;
    }

    //To execute 3 methods 
    public void retrieveDesignationResponsibilitiesAfterSelection() {
       System.out.println("Assigned Responsibilities");
        retrieveAssignedDesignationResponsibilities();
         System.out.println("Not Assigned Responsibilities");
        retrieveNotAssignedDesignationResponsibilities();
         System.out.println("History");
        retrieveDesignationResponsibilitiesHistory();
    }
    //To retrieve the Designation List

    public void retrieveDesignationList() {

        try {

            HibernateUtil.beginTransaction();
            designationList = designationsManager.retrieveDesignationList();
            desigConverter.setSearchDesignation(designationList);
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }

    }

    //to retrieve Not Assigned Responsibilities Of Designations.
    public void retrieveNotAssignedDesignationResponsibilities() {
           System.out.println("not assigned desig ID="+this.getSelectedDesignation().getId());
        try {
            List<Responsibilities> allResponsibilities;
            DesignationResponsibilities selectDesignationresponsibility;
            List<DesignationResponsibilities> designationResponsibilities = new ArrayList<DesignationResponsibilities>();
            HibernateUtil.beginTransaction();
            allResponsibilities = responsibilitiesManager.retrieveDesignationResponsibilitiesDetails(getSelectedDesignation().getId());
            for (int i = 0; i < allResponsibilities.size(); i++) {
                selectDesignationresponsibility = new DesignationResponsibilities();
                selectDesignationresponsibility.setResponsibilities(allResponsibilities.get(i));
                designationResponsibilities.add(selectDesignationresponsibility);
                selectDesignationresponsibility = null;
            }
            notAssigneddesignationResponsibilities = designationResponsibilities;
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }


    }

    //to retrieve Assigned Responsibilities of Designations.
    public void retrieveAssignedDesignationResponsibilities() {
System.out.println(" assigned desig ID="+this.getSelectedDesignation().getId());
        try {
            HibernateUtil.beginTransaction();
            assignedDesignationResponsibilities = DesignationResponsibilityManager
                    .retrieveAssignedDesignationResponsibilities(getSelectedDesignation().getId());

            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());

        } finally {
            HibernateUtil.closeSession();
        }

    }

    //to retrieve DesignationResponsibilitiesHistory
    public void retrieveDesignationResponsibilitiesHistory() {
System.out.println("History Desig ID="+this.getSelectedDesignation().getId());
        try {
            HibernateUtil.beginTransaction();
            designationResponsibilitiesHistory = DesignationResponsibilityManager
                    .retrieveDesignationResponsibilitiesHistory(getSelectedDesignation().getId());
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }

    //To add DesignationResponsibility
    public void addDesignationResponsibility() {

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        try {
            if (this.selectDesignationresponsibilityToAdd == null) {

                String message = text.getString("ui.Bean.SelectDesigRespon");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlrespontbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

            } else if (this.selectDesignationresponsibilityToAdd.getStartDate() == null) {

                String message = text.getString("ui.Bean.StartDate");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlrespontbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

            } else {

                HibernateUtil.beginTransaction();
                this.selectDesignationresponsibilityToAdd.setDesignations(selectedDesignation);
                this.DesignationResponsibilityManager.saveNew(selectDesignationresponsibilityToAdd);
                HibernateUtil.commitTransaction();
                setSelectDesignationresponsibilityToAdd(null);
                setSelectDesignationresponsibilityToAdd(selectDesignationresponsibilityToAdd);
                retrieveDesignationResponsibilitiesAfterSelection();
                String message = text.getString("ui.Bean.ResponsibilityAdd");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            }


        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    //To update DesignationResponsibility
    public void updateDesignationResponsibility() {

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        Date current_Date = new Date();
        try {
            if (this.selectDesignationResponsibilityToUpdate == null) {
                String message = text.getString("ui.Bean.SelectDesigResToAdd");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlrespontbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            } else if (selectDesignationResponsibilityToUpdate.getEndDate() == null) {
                String message = text.getString("ui.Bean.EndDate");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlrespontbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            } else if (selectDesignationResponsibilityToUpdate.getEndReason() == null) {
                String message = text.getString("ui.Bean.EndReason");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlrespontbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            } else {
                if (selectDesignationResponsibilityToUpdate.getEndDate().compareTo(selectDesignationResponsibilityToUpdate.getStartDate()) >= 0) {

                    if (selectDesignationResponsibilityToUpdate.getEndDate().compareTo(current_Date) <= 0) {

                        HibernateUtil.beginTransaction();
                        DesignationResponsibilityManager.updateExisting(selectDesignationResponsibilityToUpdate);

                        HibernateUtil.commitTransaction();
                        setSelectDesignationResponsibilityToUpdate(null);
                        setSelectDesignationResponsibilityToUpdate(selectDesignationResponsibilityToUpdate);

                        retrieveDesignationResponsibilitiesAfterSelection();
                        String message = text.getString("ui.Bean.ResponUpdateSuccess");
                        RequestContext req = RequestContext.getCurrentInstance();
                        req.update("growlmsgssage");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));


                    } else {
                        String message = text.getString("ui.Bean.EnddateGreterThanStartDate");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                        RequestContext req = RequestContext.getCurrentInstance();
                        req.update("growlmsgssage");
                    }


                } else {
                    String message = text.getString("ui.Bean.EnddateGreterThanStartDate");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                    RequestContext req = RequestContext.getCurrentInstance();
                    req.update("growlmsgssage");
                }
            }

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }



    }
}
