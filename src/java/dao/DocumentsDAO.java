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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import orm.Documents;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "documentsDAO")
@ViewScoped
public class DocumentsDAO implements Serializable {

    private List<Documents> allDocuments;
    private Documents documents;

    public List<Documents> getAllDocuments() {
        return allDocuments;
    }

    public void setAllDocuments(List<Documents> allDocuments) {
        this.allDocuments = allDocuments;
    }

    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    /**
     * Creates a new instance of DocumentsDAO
     */
    public DocumentsDAO() {

        this.allDocuments = new ArrayList<Documents>();
        this.documents = new Documents();

        System.out.println(" New DocumentsDAO is created......");

        this.retrieveDocuments();

    }

    // retrieve document list from document table.
    private void retrieveDocuments() {

        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());


        Query q = null;
        this.allDocuments.clear();

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            q = session.createQuery("from Documents");

            this.allDocuments.addAll(q.list());
            trx.commit();


        } catch (Exception e) {

            trx.rollback();

            if (this.allDocuments == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            }


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

    // adding new documents 
    public void addDocuments() {

        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());


        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            int userId = loginDAO.getLoggedinUser().getId();
            this.documents.setCreatedby(userId);
            Date date = new Date();

            this.documents.setCreatedon(new Timestamp(date.getTime()));

            session.save(this.documents);
            trx.commit();

            this.retrieveDocuments();
            RequestContext requestContext = RequestContext.getCurrentInstance();

            requestContext.update("frmdocument:tbldocuments");

            FacesMessage message = new FacesMessage(text.getString("ui.Bean.DocumentAddedSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, message);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text.getString("ui.Bean.DocumentAddedSuccess"), text.getString("ui.Bean.DocumentAddedSuccess")));

        } catch (Exception e) {


            trx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.ErrorMsgclickNext"), text.getString("ui.Bean.ErrorMsgclickNext")));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.ErrorMsgRefreshPage"), text.getString("ui.Bean.ErrorMsgRefreshPage")));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.ErrorSysAdmin"), text.getString("ui.Bean.ErrorSysAdmin")));

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
