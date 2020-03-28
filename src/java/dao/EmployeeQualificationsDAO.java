/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import orm.EmployeeQualifications;
import orm.Employees;
import orm.Qualifications;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "employeeQualificationsDAO")
@ViewScoped
public class EmployeeQualificationsDAO implements Serializable {

    private List<Employees> allEmployees;
    private Employees selectedEmployee;
    private EmployeeQualifications employeeQualification;
    private EmployeeQualifications selectedEmpQualification;
    private UploadedFile file;
    private List<Qualifications> employeeQualificationsToadd;
    private List<EmployeeQualifications> employeeQualifications;
    private Qualifications selectedQualifications;

    /**
     * Creates a new instance of EmployeeQualificationsDAO
     */
    public EmployeeQualificationsDAO() {

        this.allEmployees = new ArrayList<Employees>();
        this.selectedEmployee = new Employees();
        this.employeeQualification = new EmployeeQualifications();
        this.selectedEmpQualification = new EmployeeQualifications();


        this.employeeQualifications = new ArrayList<EmployeeQualifications>();
        this.employeeQualificationsToadd = new ArrayList<Qualifications>();

        this.selectedQualifications = new Qualifications();


        this.retrieveEmployee();
        System.out.println(" New EmployeeQualifications is created......");

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

    public List<EmployeeQualifications> getEmployeeQualifications() {
        return employeeQualifications;
    }

    public void setEmployeeQualifications(List<EmployeeQualifications> employeeQualifications) {
        this.employeeQualifications = employeeQualifications;
    }

    public List<Qualifications> getEmployeeQualificationsToadd() {
        return employeeQualificationsToadd;
    }

    public void setEmployeeQualificationsToadd(List<Qualifications> employeeQualificationsToadd) {
        this.employeeQualificationsToadd = employeeQualificationsToadd;
    }

    public int getSelectedQualifications() {
        return selectedQualifications.getId();
    }

    public void setSelectedQualifications(int selectedQualifications) {
        this.selectedQualifications.setId(selectedQualifications);
    }

    public EmployeeQualifications getEmployeeQualification() {
        return employeeQualification;
    }

    public void setEmployeeQualification(EmployeeQualifications employeeQualification) {
        this.employeeQualification = employeeQualification;
    }

    public EmployeeQualifications getSelectedEmpQualification() {
        return selectedEmpQualification;
    }

    public void setSelectedEmpQualification(EmployeeQualifications selectedEmpQualification) {
        this.selectedEmpQualification = selectedEmpQualification;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    
    // for file seperator
    
    String seperator = System.getProperty("file.separator");
    
    

    // for uploading the employee qualification pdf.
    public void handleFileUpload(FileUploadEvent event) {

        int BUFFER_SIZE = 4096;
        int id = this.getSelectedEmployee();
        int quaid = this.selectedQualifications.getId();

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        
         String tempDir = System.getProperty("java.io.tmpdir");

        File result = new File(tempDir + event.getFile().getFileName());


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

//                BufferedImage originalImage = ImageIO.read(new File("/home/dhirajj/primefaces/temp"+ event.getFile().getFileName()));
//		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();




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
                File file2 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources"+seperator+"eq"+seperator+ id);
                file2.mkdir();
            }

            if (quaid == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.SelectQualificationUpload"), text.getString("ui.Bean.SelectQualificationUpload")));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");

                return;
            } else {
                File file1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources"+seperator+"eq"+seperator+ id + seperator + id + "_" + quaid + ".pdf");

                result.renameTo(file1);
                String strqualificationPath = seperator+"resources"+seperator+"eq"+seperator+ id +seperator+ id + "_" + quaid + ".pdf";
                this.employeeQualification.setFilename(strqualificationPath);


//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text.getString("ui.Bean.FileUpload"), text.getString("ui.Bean.FileUpload")));
//                RequestContext req = RequestContext.getCurrentInstance();
//                req.update("messages");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }


    }

    public void retrieveEmployee() {

        Session session = null;
        Transaction trx = null;

        Query q = null;
        this.allEmployees.clear();

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());


        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            q = session.createQuery("select emp from Employees emp");

            this.allEmployees.addAll(q.list());

            trx.commit();

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

    public void retrieveQualificationDetails() {

        Session session = null;
        Transaction trx = null;
        Transaction trx1 = null;

        Query q = null;
        Criteria cr = null;

        int id = this.getSelectedEmployee();

        this.employeeQualifications.clear();
        this.employeeQualificationsToadd.clear();


        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

//            q = session.createQuery("select new Qualifications(qua.id,qua.name,qua.description) "
//                    + "from Qualifications qua "
//                    + "left join qua.employeeQualifications equa with equa.employees.id = " + id + " "
//                    + "where equa.id is null");

            q = session.createQuery("select qualification from Qualifications qualification where qualification.id "
                    + "not in(select empQualify.qualifications.id from EmployeeQualifications empQualify where empQualify.employees.id = " + id + ")");

            this.employeeQualificationsToadd.addAll(q.list());
            trx.commit();

            trx1 = session.beginTransaction();

            cr = session.createCriteria(EmployeeQualifications.class, "equa").createAlias("qualifications", "qua", JoinType.INNER_JOIN).setFetchMode("equa.employees", FetchMode.JOIN).add(Restrictions.eq("equa.employees.id", id));

            this.employeeQualifications.addAll(cr.list());
            trx1.commit();


        } catch (Exception e) {
            trx.rollback();
            trx1.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected Error occured.", "Unexpected Error occured."));


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

    public void addEmployeeQualification() {


        Session session = null;
        Transaction trx = null;

        this.employeeQualification.setEmployees(this.selectedEmployee);
        this.employeeQualification.setQualifications(this.selectedQualifications);

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            if (this.employeeQualification.getQualifications() == null) {

                RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select at least one Employee_Qualification to add.", "Please select at least one Employee_Qualification to add."));
            } else {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                
                FacesContext facesContext = FacesContext.getCurrentInstance();
                Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
                
                this.employeeQualification.setCreatedby(loginDAO.getLoggedinUser().getId());
                
                Date date = new Date();
                
                this.employeeQualification.setCreatedon(new Timestamp(date.getTime()));

                session.save(this.employeeQualification);

                trx.commit();
                this.setEmployeeQualification(null);
                this.setEmployeeQualification(new EmployeeQualifications());

                this.retrieveQualificationDetails();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Qualification added successfully."));

                RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");

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

    public void removeEmployeeQualification() {

        Session session = null;
        Transaction trx = null;

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            session.delete(this.selectedEmpQualification);
            trx.commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("EmployeeQualification updated successfully.Refresh the page to proceed."));

            RequestContext req = RequestContext.getCurrentInstance();
            req.update("messages");

        } catch (Exception e) {
            trx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected Error occured.", "Unexpected Error occured."));
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
    
    private StreamedContent fileDownload;
    
    

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

            } else if (this.getSelectedEmpQualification() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text.getString("ui.Bean.EmpQualificationSelecttoDownloadFile")));

                RequestContext req = RequestContext.getCurrentInstance();
                req.update("messages");
                req.update(":frmempDocAdd:growlmsg");

            } else {
                int id = this.getSelectedEmployee();
                int qualificationid = this.getSelectedEmpQualification().getQualifications().getId();
                FileInputStream stream;

                stream = new FileInputStream(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "eq"+seperator+ id + seperator + id + "_" + qualificationid + ".pdf");

                fileDownload = new DefaultStreamedContent(stream, "application/pdf", "downloaded_file.pdf");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmployeeDocumentsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public StreamedContent getFileDownload(){
        return fileDownload;
    }
    
}
