/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Property;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import orm.Documents;
import orm.EmployeeDocuments;
import orm.Employees;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "employeeDocumentsDAO")
@ViewScoped
public class EmployeeDocumentsDAO implements Serializable {

    private Employees selectedEmployee;
    private Documents selectedDocument;
    private EmployeeDocuments employeeDocuments;
    private EmployeeDocuments selectedEmpDoc;
    private Documents document;
    private List<Employees> allEmployees;
    private List<Documents> employeeDocumentsToadd;
    private List<EmployeeDocuments> employeeDocumentSubmitted;

    /**
     * Creates a new instance of EmployeeDocumentsDAO
     */
    public EmployeeDocumentsDAO() {
        this.allEmployees = new ArrayList<Employees>();
        this.selectedEmployee = new Employees();
        this.selectedDocument = new Documents();
        this.employeeDocuments = new EmployeeDocuments();
        this.selectedEmpDoc = new EmployeeDocuments();
        this.document = new Documents();

        this.employeeDocumentsToadd = new ArrayList<Documents>();
        this.employeeDocumentSubmitted = new ArrayList<EmployeeDocuments>();

        System.out.println(" New EmployeeDocumentsDAO is created......");


    }

    public List<Employees> getAllEmployees() {
        return allEmployees;
    }

    public void setAllEmployees(List<Employees> allEmployees) {
        this.allEmployees = allEmployees;
    }

    public int getSelectedEmployee() {
        return selectedEmployee.getId();
    }

    public void setSelectedEmployee(int selectedEmployee) {
        this.selectedEmployee.setId(selectedEmployee);
    }

    public List<EmployeeDocuments> getEmployeeDocumentSubmitted() {
        return employeeDocumentSubmitted;
    }

    public void setEmployeeDocumentSubmitted(List<EmployeeDocuments> employeeDocumentSubmitted) {
        this.employeeDocumentSubmitted = employeeDocumentSubmitted;
    }

    public List<Documents> getEmployeeDocumentsToadd() {
        return employeeDocumentsToadd;
    }

    public void setEmployeeDocumentsToadd(List<Documents> employeeDocumentsToadd) {
        this.employeeDocumentsToadd = employeeDocumentsToadd;
    }

    public int getSelectedDocument() {
        return selectedDocument.getId();
    }

    public void setSelectedDocument(int selectedDocument) {
        this.selectedDocument.setId(selectedDocument);
    }

    public EmployeeDocuments getEmployeeDocuments() {
        return employeeDocuments;
    }

    public void setEmployeeDocuments(EmployeeDocuments employeeDocuments) {
        this.employeeDocuments = employeeDocuments;
    }

    public EmployeeDocuments getSelectedEmpDoc() {
        return selectedEmpDoc;
    }

    public void setSelectedEmpDoc(EmployeeDocuments selectedEmpDoc) {
        this.selectedEmpDoc = selectedEmpDoc;
    }

    public Documents getDocument() {
        return document;
    }

    public void setDocument(Documents document) {
        this.document = document;
    }
    // for file seperator
    String seperator = System.getProperty("file.separator");

    public void hideConfirmDialog() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("docvar.hide()");
        requestContext.update("pnlDocAdd");
        requestContext.update("mainPanelForEmpDoc");
    }

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

            Date date = new Date();

            this.document.setCreatedby(loginDAO.getLoggedinUser().getId());
            this.document.setCreatedon(new Timestamp(date.getTime()));
            session.save(this.document);
            trx.commit();
            RequestContext req = RequestContext.getCurrentInstance();
            FacesMessage message = new FacesMessage("Document added Successfully");
            FacesContext.getCurrentInstance().addMessage(null, message);
            req.update(":frmempDocAdd:growlmsg");


            this.setDocument(null);
            this.setDocument(new Documents());
            this.retrieveEmployeeDocumentsDetails();


        } catch (Exception e) {


            trx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
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

    public void retrieveEmployee() {

        Session session = null;
        Transaction trx = null;

        Query q = null;


        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            if (!FacesContext.getCurrentInstance().isPostback()) {
                this.allEmployees.clear();

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                q = session.createQuery("select emp from Employees emp");

                this.allEmployees.addAll(q.list());

                trx.commit();
            }

        } catch (Exception e) {

            trx.rollback();

            if (this.allEmployees == null) {
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

    public void retrieveEmployeeDocumentsDetails() {


        Session session = null;
        Transaction trx = null;
        Transaction trx1 = null;

        Query q = null;
        Criteria cr = null;


        this.employeeDocumentsToadd.clear();
        this.employeeDocumentSubmitted.clear();

        int id = this.getSelectedEmployee();

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

//            q = session.createQuery("select new Documents(doc.id,doc.name,doc.description) "
//                    + "from Documents doc "
//                    + "left join doc.employeeDocuments edoc with edoc.employees.id = " + id + " "
//                    + "where edoc.id is null");

            q = session.createQuery("select doc from Documents doc where doc.id not in(select empDoc.documents.id from EmployeeDocuments empDoc where empDoc.employees.id = " + id + ")");

            this.employeeDocumentsToadd.addAll(q.list());
            trx.commit();

            trx1 = session.beginTransaction();

            cr = session.createCriteria(EmployeeDocuments.class, "edoc").createAlias("documents", "doc", JoinType.INNER_JOIN).setFetchMode("edoc.employees", FetchMode.JOIN).add(Restrictions.eq("edoc.employees.id", id));

            this.employeeDocumentSubmitted.addAll(cr.list());
            trx1.commit();


        } catch (Exception e) {
            trx.rollback();
            trx1.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));


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
            if (cr != null) {
                cr = null;
            }
            if (trx1 != null) {
                trx1 = null;
            }
        }
    }

    // for uploading the employee documents pdf.
    public void handleFileUpload(FileUploadEvent event) {

        int BUFFER_SIZE = 4096;
        int id = this.getSelectedEmployee();
        int docid = this.selectedDocument.getId();

        String tempDir = System.getProperty("java.io.tmpdir");

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
            FacesMessage msg =
                    new FacesMessage("File Description" + "file name: "
                    + event.getFile().getFileName() + " \n file size: "
                    + event.getFile().getSize() / 1024
                    + " Kb \n content type: "
                    + event.getFile().getContentType()
                    + " The file was uploaded.");

            if (id == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.SelectEmployee"), text.getString("ui.Bean.SelectEmployee")));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");
                return;
            } else {
//                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
//                .getExternalContext().getContext();
//                System.out.println(ctx.getRealPath(""));

                System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "ed" + seperator + id);

                File file2 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "ed" + seperator + id);
                file2.mkdir();
            }
            if (docid == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.SelectDocument"), text.getString("ui.Bean.SelectDocument")));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");
                return;
            } else {
                File file1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "ed" + seperator + id + seperator + id + "_" + docid + ".pdf");

                result.renameTo(file1);
                String strdocumentPath = seperator + "resources" + seperator + "ed" + seperator + id + seperator + id + "_" + docid + ".pdf";
                this.employeeDocuments.setFilename(strdocumentPath);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.FileNotUpload"), text.getString("ui.Bean.FileNotUpload")));
            RequestContext req = RequestContext.getCurrentInstance();
            req.update("messages");
        }
    }
    private StreamedContent file;

    /**
     *
     */
    public void downloadFileController() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        try {

            if (this.getSelectedEmployee() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text.getString("ui.Bean.EmpSelecttoDownloadFile")));

                RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");
                req.update(":frmempDocAdd:growlmsg");

            } else if (this.getSelectedEmpDoc() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text.getString("ui.Bean.EmpDocSelecttoDownloadFile")));

                RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");
                req.update(":frmempDocAdd:growlmsg");

            } else {
                int id = this.getSelectedEmployee();
                int docid = this.getSelectedEmpDoc().getDocuments().getId();
                FileInputStream stream;

                stream = new FileInputStream(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "ed" + seperator + id + seperator + id + "_" + docid + ".pdf");

                file = new DefaultStreamedContent(stream, "application/pdf", "downloaded_file.pdf");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmployeeDocumentsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public StreamedContent getFile() {
        return file;
    }

    public void addEmployeedocuments() throws Exception {
        Session session = null;
        Transaction trx = null;

        this.employeeDocuments.setEmployees(this.selectedEmployee);
        this.employeeDocuments.setDocuments(this.selectedDocument);

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            if (this.employeeDocuments.getDocuments() == null) {

                RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.SelectEmpDoc"), text.getString("ui.Bean.SelectEmpDoc")));
            } else {
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

                this.employeeDocuments.setCreatedby(loginDAO.getLoggedinUser().getId());

                Date date = new Date();

                this.employeeDocuments.setCreatedon(new Timestamp(date.getTime()));

                session.save(this.employeeDocuments);
                trx.commit();
                this.setEmployeeDocuments(null);
                this.setEmployeeDocuments(new EmployeeDocuments());
                this.retrieveEmployeeDocumentsDetails();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text.getString("ui.Bean.DocumentAddedSuccess")));

                RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");
            }
        } catch (Exception e) {
            trx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
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
    // Adding new Document on click to show pop up box.

    public void addDocOnclick() {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("docvar.show()");
        requestContext.update("addDoc");

    }

    public void deleteEmpDocument() {

        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());


        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            session.delete(this.selectedEmpDoc);
            trx.commit();

            this.retrieveEmployeeDocumentsDetails();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text.getString("ui.Bean.EmpDocUpdateSuccess")));

            RequestContext req = RequestContext.getCurrentInstance();
            req.update("messages");

        } catch (Exception e) {
            trx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            RequestContext req = RequestContext.getCurrentInstance();
            req.update("messages");

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
