/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import orm.DesignationResponsibilities;
import orm.Designations;
import orm.Responsibilities;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "designationResponcibilitiesDAO")
@ViewScoped
public class DesignationResponcibilitiesDAO implements Serializable {

    private List<Designations> designationList;
    private Designations selectedDesignation;
    private List<DesignationResponsibilities> designationResponsibilitiesToAdd;
    private List<DesignationResponsibilities> designationResponsibilities;
    private List<DesignationResponsibilities> designationResponsibilitiesHistory;
    private DesignationResponsibilities selectDesignationresponsibilityToAdd;
    private DesignationResponsibilities selectDesignationResponsibilityToUpdate;

    public int getSelectedDesignation() {
        return selectedDesignation.getId();
    }

    public void setSelectedDesignation(int selectedDesignation) {
        this.selectedDesignation.setId(selectedDesignation);
    }

    public List<Designations> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<Designations> designationList) {
        this.designationList = designationList;
    }

    public List<DesignationResponsibilities> getDesignationResponsibilities() {
        return designationResponsibilities;
    }

    public void setDesignationResponsibilities(List<DesignationResponsibilities> designationResponsibilities) {
        this.designationResponsibilities = designationResponsibilities;
    }

    public List<DesignationResponsibilities> getDesignationResponsibilitiesToAdd() {
        return designationResponsibilitiesToAdd;
    }

    public void setDesignationResponsibilitiesToAdd(List<DesignationResponsibilities> designationResponsibilitiesToAdd) {
        this.designationResponsibilitiesToAdd = designationResponsibilitiesToAdd;
    }

    public List<DesignationResponsibilities> getDesignationResponsibilitiesHistory() {
        return designationResponsibilitiesHistory;
    }

    public void setDesignationResponsibilitiesHistory(List<DesignationResponsibilities> designationResponsibilitiesHistory) {
        this.designationResponsibilitiesHistory = designationResponsibilitiesHistory;
    }

    public DesignationResponsibilities getSelectDesignationResponsibilityToUpdate() {
        return selectDesignationResponsibilityToUpdate;
    }

    public void setSelectDesignationResponsibilityToUpdate(DesignationResponsibilities selectDesignationResponsibilityToUpdate) {
        this.selectDesignationResponsibilityToUpdate = selectDesignationResponsibilityToUpdate;
    }

    public DesignationResponsibilities getSelectDesignationresponsibilityToAdd() {
        return selectDesignationresponsibilityToAdd;
    }

    public void setSelectDesignationresponsibilityToAdd(DesignationResponsibilities selectDesignationresponsibilityToAdd) {
        this.selectDesignationresponsibilityToAdd = selectDesignationresponsibilityToAdd;
    }

    /**
     * Creates a new instance of DesignationResponcibilitiesDAO
     */
    public DesignationResponcibilitiesDAO() {
        this.designationList = new ArrayList<Designations>();
        this.designationResponsibilities = new ArrayList<DesignationResponsibilities>();
        this.designationResponsibilitiesToAdd = new ArrayList<DesignationResponsibilities>();
        this.designationResponsibilitiesHistory = new ArrayList<DesignationResponsibilities>();
        this.selectDesignationResponsibilityToUpdate = new DesignationResponsibilities();
        this.selectDesignationresponsibilityToAdd = new DesignationResponsibilities();

        this.selectedDesignation = new Designations();

        this.retrieveDesignationList();
        System.out.println(" New DesignationResponsibilitiesDAO is created......");
    }

    public void retrieveDesignationList() {

        Session session = null;
        Transaction trx = null;

        Query q = null;

        this.designationList.clear();

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            String hqlQuery = "from Designations";

            q = session.createQuery(hqlQuery);

            this.designationList.addAll(q.list());

            trx.commit();

        } catch (Exception e) {
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

    public void retrieveDesignationResponsibilitiesDetails() {


        Session session = null;
        Transaction trx = null;


        Query q = null;
        Criteria cr = null;

        int id = this.getSelectedDesignation();

        this.designationResponsibilities.clear();
        this.designationResponsibilitiesToAdd.clear();
        this.designationResponsibilitiesHistory.clear();

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            String hqlQuery = "select res from Responsibilities res left join res.designationResponsibilities dRes with dRes.designations.id=" + id + " and dRes.endDate is null where dRes.id is null";

            q = session.createQuery(hqlQuery);

            DesignationResponsibilities dr = null;
            List<Responsibilities> resp = new ArrayList<Responsibilities>();

            resp.addAll(q.list());

            for (int i = 0; i < resp.size(); i++) {

                Responsibilities r = (Responsibilities) resp.get(i);

                dr = new DesignationResponsibilities();
                dr.setResponsibilities(r);

                this.designationResponsibilitiesToAdd.add(dr);
                dr = null;
            }
            trx.commit();

            trx = session.beginTransaction();

            cr = session.createCriteria(DesignationResponsibilities.class, "dres").createAlias("dres.responsibilities", "res", JoinType.INNER_JOIN, Restrictions.isNull("dres.endDate")).setFetchMode("dres.designations", FetchMode.JOIN).add(Restrictions.eq("dres.designations.id", id));
            this.designationResponsibilities.addAll(cr.list());
            trx.commit();
            trx = session.beginTransaction();

            cr = session.createCriteria(DesignationResponsibilities.class, "dres").createAlias("dres.responsibilities", "res", JoinType.INNER_JOIN).createAlias("dres.designations", "desig", JoinType.INNER_JOIN, Restrictions.eq("desig.id", id)).add(Restrictions.isNotNull("dres.endDate")).addOrder(Order.desc("dres.startDate"));
            this.designationResponsibilitiesHistory.addAll(cr.list());
            trx.commit();

            RequestContext req = RequestContext.getCurrentInstance();

            req.update("growlmsgssage");
//            req.update("frmdesigResponse");
//            req.update("frmAddResponsibilities:addResponsibilitiestbl");
//            req.update("frmUpdateResponsibilities:respontbl");
//            req.update("frmHistory:historytbl");
//            
            this.setSelectDesignationresponsibilityToAdd(null);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e);

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

    public void addDesignationResponsibilities() {

        Session session = null;
        Transaction trx = null;

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

                this.selectDesignationresponsibilityToAdd.setDesignations(this.selectedDesignation);
                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                int userId = loginDAO.getLoggedinUser().getId();
                this.selectDesignationresponsibilityToAdd.setCreatedby(userId);

                Date date = new Date();
                this.selectDesignationresponsibilityToAdd.setCreatedon(new Timestamp(date.getTime()));


                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                session.save(this.selectDesignationresponsibilityToAdd);

                trx.commit();

                this.retrieveDesignationResponsibilitiesDetails();

                String message = text.getString("ui.Bean.ResponsibilityAdd");
                RequestContext req = RequestContext.getCurrentInstance();

                req.update("growlmsgssage");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));


            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

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

    public void updateDesignationresonsibilities() {
        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        Query q = null;
        Date current_Date = new Date();

        try {
            if (this.selectDesignationResponsibilityToUpdate == null) {
                String message = text.getString("ui.Bean.SelectDesigResToAdd");
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlrespontbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            } else if (this.selectDesignationResponsibilityToUpdate.getEndDate() == null) {
                String message = text.getString("ui.Bean.EndDate");

                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlrespontbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            } else if (this.selectDesignationResponsibilityToUpdate.getEndReason() == null) {
                String message = text.getString("ui.Bean.EndReason");

                RequestContext req = RequestContext.getCurrentInstance();
                req.update("growlmsgssage");
                req.update("pnlrespontbl");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            } else {
                if (this.selectDesignationResponsibilityToUpdate.getEndDate().compareTo(this.selectDesignationResponsibilityToUpdate.getStartDate()) >= 0) {
                    if (this.selectDesignationResponsibilityToUpdate.getEndDate().compareTo(current_Date) <= 0) {
                        FacesContext facesContext = FacesContext.getCurrentInstance();
                        Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                        int userId = loginDAO.getLoggedinUser().getId();

                        Date date = new Date();

                        this.selectDesignationResponsibilityToUpdate.setCreatedby(userId);
                        this.selectDesignationResponsibilityToUpdate.setCreatedon(new Timestamp(date.getTime()));

                        session = HibernateUtil.getSession();
                        trx = session.beginTransaction();

                        session.update(this.selectDesignationResponsibilityToUpdate);
                        trx.commit();

                        this.retrieveDesignationResponsibilitiesDetails();

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
            System.out.println(e.getMessage());
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
}
