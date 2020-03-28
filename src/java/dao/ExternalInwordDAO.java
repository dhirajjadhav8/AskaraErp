/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import converter.ContactsConverter;
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
import javax.faces.event.ValueChangeEvent;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import orm.Contacts;
import orm.Employees;
import orm.Inwordse;
import util.HibernateUtil;
import util.MainIncludeFilesSelect;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "externalInwordDAO")
@ViewScoped
public class ExternalInwordDAO implements Serializable {

    private List<Contacts> allfroms;
    private List<Employees> allForwardedTo;
    private List<Inwordse> allInwordse;
    private List<Inwordse> allInwardsHistory;
    private Inwordse selectedInword;
    private Contacts selectedContact;
    private Employees selectedEmployee;
    private Inwordse fromInward;
    private Inwordse toInward;
    private Inwordse updateInward;
    private boolean checkComments;

    public List<Contacts> getAllfroms() {
        return allfroms;
    }

    public void setAllfroms(List<Contacts> allfroms) {
        this.allfroms = allfroms;
    }

    public List<Employees> getAllForwardedTo() {
        return allForwardedTo;
    }

    public void setAllForwardedTo(List<Employees> allForwardedTo) {
        this.allForwardedTo = allForwardedTo;
    }

    public List<Inwordse> getAllInwordse() {
        return allInwordse;
    }

    public void setAllInwordse(List<Inwordse> allInwordse) {
        this.allInwordse = allInwordse;
    }

    public Inwordse getSelectedInword() {
        return selectedInword;
    }

    public void setSelectedInword(Inwordse selectedInword) {
        this.selectedInword = selectedInword;
    }

    public Contacts getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(Contacts selectedContact) {
        this.selectedContact = selectedContact;
    }

    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public Inwordse getFromInward() {
        return fromInward;
    }

    public void setFromInward(Inwordse fromInward) {
        this.fromInward = fromInward;
    }

    public Inwordse getToInward() {
        return toInward;
    }

    public void setToInward(Inwordse toInward) {
        this.toInward = toInward;
    }

    public List<Inwordse> getAllInwardsHistory() {
        return allInwardsHistory;
    }

    public void setAllInwardsHistory(List<Inwordse> allInwardsHistory) {
        this.allInwardsHistory = allInwardsHistory;
    }

    public Inwordse getUpdateInward() {
        return updateInward;
    }

    public void setUpdateInward(Inwordse updateInward) {
        this.updateInward = updateInward;
    }

    public boolean isCheckComments() {
        return checkComments;
    }

    public void setCheckComments(boolean checkComments) {
        this.checkComments = checkComments;
    }

    /**
     * Creates a new instance of ExternalInwordDAO
     */
    public ExternalInwordDAO() {

        this.allfroms = new ArrayList<Contacts>();
        this.allForwardedTo = new ArrayList<Employees>();
        this.allInwordse = new ArrayList<Inwordse>();
        this.allInwardsHistory = new ArrayList<Inwordse>();

        this.selectedInword = new Inwordse();
        this.selectedContact = new Contacts();
        this.selectedEmployee = new Employees();
        this.fromInward = new Inwordse();
        this.toInward = new Inwordse();
        this.updateInward = new Inwordse();
        this.setCheckComments(true);

        System.out.println("new ExternalInwordDAO created");
    }
    int counter = 0;

    public List<Contacts> completeContact(String contact) {

        Session session = null;
        Transaction trx = null;
        Query q = null;
        this.allfroms.clear();
        try {
            if (contact != null || !contact.equals("")) {
                List<Contacts> suggestions = new ArrayList<Contacts>();
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                String hqlQuery = "from Contacts";
                q = session.createQuery(hqlQuery);
                this.allfroms.addAll(q.list());
                trx.commit();
                for (Contacts c : allfroms) {
                    if ((c.getName().toLowerCase()).startsWith(contact.toLowerCase())) {
                        suggestions.add(c);
                    }
                }

                return suggestions;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Employees> completeEmployee(String employee) {
        Session session = null;
        Transaction trx = null;
        Query q = null;
        this.allForwardedTo.clear();
        try {
            if (employee != null || !employee.equals("")) {
                List<Employees> suggessionEmployees = new ArrayList<Employees>();
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                String hqlQuery = "from Employees";
                q = session.createQuery(hqlQuery);
                this.allForwardedTo.addAll(q.list());
                trx.commit();
                for (Employees e : allForwardedTo) {
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

    public void retrieve() {
        Session session = null;
        Transaction trx = null;

        Query q = null;

        try {


            if (this.selectedContact.getName() == null || this.selectedContact.getName().equals("")) {
                this.allfroms.add(selectedContact);
            } else {
                this.allfroms.clear();


                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                String hqlQuery = "from Contacts cts where Lower(cts.name) like  '" + this.selectedContact.getName().toLowerCase() + "%'";
                q = session.createQuery(hqlQuery);
                this.allfroms.addAll(q.list());
                trx.commit();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void show() {
        System.out.println(this.selectedContact.getId());
    }

    public void retrievefromForwardedto() {
        Session session = null;
        Transaction trx = null;


        Query q = null;
        Query q1 = null;

        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                this.allfroms.clear();
                this.allForwardedTo.clear();

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                String hqlQuery = "from Contacts";
                String hqlQuery1 = "from Employees";
                q = session.createQuery(hqlQuery);
                q1 = session.createQuery(hqlQuery1);

                this.allfroms.addAll(q.list());
                this.allForwardedTo.addAll(q1.list());

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

    public void retrieveEmpExternalInward() {
        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        try {
            if (!FacesContext.getCurrentInstance().isPostback() || counter > 0) {
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                this.allInwordse.clear();
                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                int id = loginDAO.getLoggedinUser().getId();
                if (id != 0) {
                    cr = session.createCriteria(Inwordse.class, "inwords")
                            .createAlias("inwords.ifrom", "from", JoinType.INNER_JOIN)
                            .createAlias("inwords.forwardedTo", "forwardedTo", JoinType.INNER_JOIN)
                            .add(Restrictions.eq("inwords.forwardedTo.id", id))
                            .add(Restrictions.eq("inwords.isreceived", false));
                    this.allInwordse.addAll(cr.list());
                    trx.commit();
                }
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

    public void updateCheckComment() {
        if (this.updateInward.isIsreceived() == true) {
            this.setCheckComments(false);
        } else {
            this.setCheckComments(true);
        }
    }

    public void retrieveExteralInword() {
        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        try {
            if (!FacesContext.getCurrentInstance().isPostback() || counter > 0) {
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                this.allInwordse.clear();

                cr = session.createCriteria(Inwordse.class, "inwords")
                        .createAlias("inwords.ifrom", "from", JoinType.INNER_JOIN)
                        .createAlias("inwords.forwardedTo", "forwardedTo", JoinType.INNER_JOIN);
                this.allInwordse.addAll(cr.list());
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

    public void addInword() {
        Session session = null;
        Transaction trx = null;

        Query q = null;
        Query q1 = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            this.selectedInword.setIfrom(this.selectedContact);
            this.selectedInword.setForwardedTo(this.selectedEmployee);

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            // Inword No. generation    

            String sqlQuery = "select YEAR(current_date) from SYSIBM.SYSDUMMY1";
            q = session.createSQLQuery(sqlQuery);

            String strYear = q.list().get(0).toString();

            String sqlQuery1 = "select count(*) from ERP.INWORDSE";
            q1 = session.createSQLQuery(sqlQuery1);

            String Count = q1.list().get(0).toString();
            int rowCount = Integer.parseInt(Count);

            if (rowCount == 0) {
                this.selectedInword.setInNo(strYear + "-1");
            }
            if (rowCount > 0) {
                int rowCount1 = ++rowCount;
                this.selectedInword.setInNo(strYear + "-" + rowCount1);
            }
            Login loginDAO = (Login) context.getApplication().createValueBinding("#{loginbean}").getValue(context);

            int userId = loginDAO.getLoggedinUser().getId();

            this.selectedInword.setCreatedby(userId);

            Date date = new Date();

            this.selectedInword.setCreatedon(new Timestamp(date.getTime()));

            session.save(this.selectedInword);

            trx.commit();
            this.setSelectedInword(null);
            this.setSelectedInword(new Inwordse());
            this.setSelectedContact(null);
            this.setSelectedEmployee(null);
            counter++;
            this.retrievefromForwardedto();



            String message = text.getString("ui.Bean.ExtInwordAddSuccess");

            RequestContext req = RequestContext.getCurrentInstance();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
            req.update(":frmAddExternalInword");

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

    public void retrieveInwardHistory() {

        Session session = null;
        Transaction trx = null;
        Criteria cr = null;

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            this.allInwardsHistory.clear();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
            MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);
            int id = loginDAO.getLoggedinUser().getId();
            if (mainIncludeFilesSelect.getIncludeFile().equals("History") && id != 0) {
                cr = session.createCriteria(Inwordse.class, "inwords")
                        .createAlias("inwords.ifrom", "from", JoinType.INNER_JOIN)
                        .createAlias("inwords.forwardedTo", "forwardedTo", JoinType.INNER_JOIN)
                        .add(Restrictions.eq("inwords.forwardedTo.id", id))
                        .add(Restrictions.eq("inwords.isreceived", true));
                cr.setMaxResults(50);
                this.allInwardsHistory.addAll(cr.list());

            } else if (mainIncludeFilesSelect.getIncludeFile().equals("History1")) {
                cr = session.createCriteria(Inwordse.class, "inwords")
                        .createAlias("inwords.ifrom", "from", JoinType.INNER_JOIN)
                        .createAlias("inwords.forwardedTo", "forwardedTo", JoinType.INNER_JOIN)
                        .add(Restrictions.eq("inwords.isreceived", true));
                cr.setMaxResults(50);
                this.allInwardsHistory.addAll(cr.list());
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

    public void setUpdateProperty() {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        MainIncludeFilesSelect mainIncludeFilesSelect = (MainIncludeFilesSelect) facesContext.getApplication().createValueBinding("#{mainIncludeFilesSelect}").getValue(facesContext);
        mainIncludeFilesSelect.setIncludeFile("Update");

    }

    public void searchInward() {

        Session session = null;
        Transaction trx = null;

        Query q = null;
        Criteria cr = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        try {

            if (this.fromInward.getInDate() == null || this.fromInward.getInDate().equals("")) {

                String msg = text.getString("ui.msg.fromDateRequire");
                RequestContext req = RequestContext.getCurrentInstance();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
                req.update(":frmAddExternalInword:growlmsg");

            } else if (this.toInward.getInDate() == null || this.toInward.getInDate().equals("")) {
                String msg = text.getString("ui.msg.toDateRequire");
                RequestContext req = RequestContext.getCurrentInstance();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
                req.update(":frmAddExternalInword:growlmsg");
            } else {
                this.allInwordse.clear();

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                cr = session.createCriteria(Inwordse.class, "inw")
                        .createAlias("inw.ifrom", "from", JoinType.INNER_JOIN)
                        .createAlias("inw.forwardedTo", "forwardTo", JoinType.INNER_JOIN)
                        .add(Restrictions.between("inw.inDate", this.fromInward.getInDate(), this.toInward.getInDate()));

                this.allInwordse.addAll(cr.list());

                trx.commit();

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (cr != null) {
                cr = null;
            }
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

    public void updateInwardToCheckReceivable() {

        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            RequestContext req = RequestContext.getCurrentInstance();

            if (this.updateInward.isIsreceived() == true) {

                if (this.updateInward.getComments().equals("")) {

                    String msg = text.getString("ui.msg.commentRequire");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
                    req.update(":frmAddExternalInword:growlmsg");


                } else {
                    session.update(this.updateInward);
                    trx.commit();
                    String message = text.getString("ui.Bean.ExtInwordUpdateSuccess");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                    req.execute("DlgConfirmForInward.hide()");
                    this.setUpdateInward(null);
                    this.setUpdateInward(new Inwordse());
                    counter++;
                    this.retrieveEmpExternalInward();
                }

            } else {

                String msg = text.getString("ui.msg.isReceivedRequire");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
                req.update(":frmAddExternalInword:growlmsg");

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
}
