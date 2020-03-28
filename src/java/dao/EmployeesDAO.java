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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;
import orm.ContactTypes;
import orm.Contacts;
import orm.Employees;
import orm.Projects;
import orm.ZEmployees;
import util.HibernateUtil;
import util.MainIncludeFilesSelect;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "employeesDAO")
@ViewScoped
public class EmployeesDAO implements Serializable {

    private List<Projects> allProjects;
    private List<Employees> allEmployees;
    private List<ZEmployees> allZEmployees;
    private Projects selectedProject;
    private Employees selectedEmployee;
    private Employees employee;
    private Contacts selectedContact;
    private static Logger logger = Logger.getLogger(Employees.class.getName());
    private boolean skip;
// for testing the panelgrid size. 
    private Employees employeefortest;
    private UploadedFile file;
    private static final int IMG_WIDTH = 100;
    private static final int IMG_HEIGHT = 100;
    private static final int IMG_WIDTHs = 50;
    private static final int IMG_HEIGHTs = 50;
    private int id;
    private Employees updateEmployee;
    private List<Employees> searchEmployees;
    private String strfirstname;
    private String strlastname;
    private String selectedEmployeetatus;
    private boolean tabview;
    private int activeIndex;
    private char strGender;

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public boolean isTabview() {
        return tabview;
    }

    public void setTabview(boolean tabview) {
        this.tabview = tabview;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public List<Employees> getAllEmployees() {
        return allEmployees;
    }

    public void setAllEmployees(List<Employees> allEmployees) {
        this.allEmployees = allEmployees;
    }

    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public Employees getUpdateEmployee() {
        return updateEmployee;
    }

    public void setUpdateEmployee(Employees updateEmployee) {
        this.updateEmployee = updateEmployee;
    }

    public List<Employees> getSearchEmployees() {
        return searchEmployees;
    }

    public void setSearchEmployees(List<Employees> searchEmployees) {
        this.searchEmployees = searchEmployees;
    }

    public String getStrfirstname() {
        return strfirstname;
    }

    public void setStrfirstname(String strfirstname) {
        this.strfirstname = strfirstname;
    }

    public String getStrlastname() {
        return strlastname;
    }

    public void setStrlastname(String strlastname) {
        this.strlastname = strlastname;
    }

    public String getselectedEmployeetatus() {
        return selectedEmployeetatus;
    }

    public void setselectedEmployeetatus(String selectedEmployeetatus) {
        this.selectedEmployeetatus = selectedEmployeetatus;
    }

    public List<Projects> getAllProjects() {
        return allProjects;
    }

    public void setAllProjects(List<Projects> allProjects) {
        this.allProjects = allProjects;
    }

    public int getSelectedProject() {
        return selectedProject.getId();
    }

    public void setSelectedProject(int selectedProject) {
        this.selectedProject.setId(selectedProject);
    }

    public Employees getEmployeefortest() {
        return employeefortest;
    }

    public void setEmployeefortest(Employees employeefortest) {
        this.employeefortest = employeefortest;
    }

    public List<ZEmployees> getAllZEmployees() {
        return allZEmployees;
    }

    public void setAllZEmployees(List<ZEmployees> allZEmployees) {
        this.allZEmployees = allZEmployees;
    }

    public char getStrGender() {
        return strGender;
    }

    public void setStrGender(char strGender) {
        this.strGender = strGender;
    }

    public Contacts getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(Contacts selectedContact) {
        this.selectedContact = selectedContact;
    }
    // for created by and modified by  field  userid
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);
    //for createdby or modified by field in the table
    int userId = loginDAO.getLoggedinUser().getId();
    // for created on or modified on field in the table
    Date date = new Date();
    //for retriving the employee list only one time.
    int i = 0;
    // for updating the which dialog box add or update 
    int s = 0;
    // for message of uploading photo update
    int k = 0;
    // to temp directory of os
    String tempDir = System.getProperty("java.io.tmpdir");
    // for file seperator
    String seperator = System.getProperty("file.separator");

    /**
     * Creates a new instance of EmployeesDAO
     */
    public EmployeesDAO() {

        this.allEmployees = new ArrayList<Employees>();
        this.selectedEmployee = new Employees();
        this.employee = new Employees();
        this.searchEmployees = new ArrayList<Employees>();
        this.allProjects = new ArrayList<Projects>();
        this.selectedProject = new Projects();
        this.employeefortest = new Employees();
        this.selectedContact = new Contacts();

        this.allZEmployees = new ArrayList<ZEmployees>();
        System.out.println(" New EmployeesDAO is created......");

        this.setTabview(true);
        this.setStrGender('M');

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

            if (s > 0) {

                File file2 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "photos" + seperator + "up" + seperator + id + ".jpg");
                result.renameTo(file2);
                this.updateaddedEmployee();
            } // for add existing employee upload photo
            else {
                this.id = this.selectedEmployee.getId();
                File file2 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "photos" + seperator + "up" + seperator + id + ".jpg");
                result.renameTo(file2);
                String strPhotoPath = seperator + "resources" + seperator + "photos" + seperator + "up" + seperator + id + "_jpg.jpg";
                this.selectedEmployee.setPhoto(strPhotoPath);

                this.updateEmployees();

            }


            BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type);
            ImageIO.write(resizeImageHintJpg, "jpg", new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "photos" + seperator + "up" + seperator + id + "_jpg.jpg"));

            BufferedImage resizeImageJpg = resizeImage(originalImage, type);
            ImageIO.write(resizeImageJpg, "jpg", new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "photos" + seperator + "up" + seperator + id + ".jpg"));

            RequestContext.getCurrentInstance().update(":frmAddselectedEmployee:wizar:panel");
            RequestContext.getCurrentInstance().update("pnlMain");

        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    text.getString("ui.Bean.FileNotUpload"), "");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }

    // for uploading the employee photo.
    public void handleFileUploadforExistingEmployee(FileUploadEvent event) {
        k++;
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

            this.id = this.selectedEmployee.getId();
            File file2 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "photos" + seperator + "up" + seperator + id + ".jpg");
            result.renameTo(file2);
            String strPhotoPath = seperator + "resources" + seperator + "photos" + seperator + "up" + seperator + id + "_jpg.jpg";
            this.selectedEmployee.setPhoto(strPhotoPath);

            this.updateEmployees();


            BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type);
            ImageIO.write(resizeImageHintJpg, "jpg", new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "photos" + seperator + "up" + seperator + id + "_jpg.jpg"));

            BufferedImage resizeImageJpg = resizeImage(originalImage, type);
            ImageIO.write(resizeImageJpg, "jpg", new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(seperator) + "resources" + seperator + "photos" + seperator + "up" + seperator + id + ".jpg"));

            RequestContext.getCurrentInstance().update("empImage");
            RequestContext.getCurrentInstance().update(":frmPhoto:panelfornewphoto");
            RequestContext.getCurrentInstance().update(":pnlMain");




        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    text.getString("ui.Bean.FileNotUpload"), "");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }

    public void removeTabeChange() {
        this.setTabview(false);

    }

    public void addTabChangeForUpdate() {
        this.setTabview(true);
        this.setActiveIndex(0);

//        FacesContext facesContex = FacesContext.getCurrentInstance();
//        UsersDAO usersDAO = (UsersDAO) facesContex.getApplication().createValueBinding("#{usersDAO}").getValue(facesContex);
//        usersDAO.setIsAvailable(false);


        RequestContext.getCurrentInstance().update("tbvEmployees");
    }

    public void addTabchange() {
        this.setTabview(true);
        this.setActiveIndex(0);
//        FacesContext facesContex = FacesContext.getCurrentInstance();
//        UsersDAO usersDAO = (UsersDAO) facesContex.getApplication().createValueBinding("#{usersDAO}").getValue(facesContext);
//        usersDAO.setIsAvailable(false);
        RequestContext.getCurrentInstance().update("tbvEmployees");

        if (this.selectedEmployee != null) {

            RequestContext req = RequestContext.getCurrentInstance();

            this.setStrfirstname(this.selectedEmployee.getFirstname());

            this.setStrlastname(this.selectedEmployee.getLastname());

            if (this.selectedEmployeetatus == "" || this.selectedEmployeetatus == null) {
                this.setselectedEmployeetatus("Current");
            }
            try {
                this.retrieveAllEmployeesBysearch();
            } catch (Exception ex) {
                Logger.getLogger(EmployeesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            req.update("imgEmp");
            req.update("empName");
            req.update(":frmEmpPhoto:pnlgrdAddPhoto");
            req.update("pnlMain");


        }
    }

// for retrieving the allProject list       
    public void retrieveAllProjects() {

        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        Query q = null;


        try {

            if (!FacesContext.getCurrentInstance().isPostback()) {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                this.allProjects.clear();

                q = session.createQuery("from Projects");
                this.allProjects.addAll(q.list());
                trx.commit();

            }
        } catch (Exception e) {

            trx.rollback();

            if (this.allProjects == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"),
                        text.getString("ui.Bean.UnexpectedError")));
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

// for retrieving the allEmployee list  
    public void retrieveAllEmployees() {

        Session session = null;
        Transaction trx = null;

//        Criteria cr = null;

        Query q = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        this.allEmployees.clear();

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();


// hibernate query to select employee list.

//            cr = session.createCriteria(Employees.class)
//                    .setProjection(Projections.projectionList()
//                    .add(Projections.property("id"), "id")
//                    .add(Projections.property("photo"), "photo")
//                    .add(Projections.property("firstname"), "firstname")
//                    .add(Projections.property("lastname"), "lastname")
//                    .add(Projections.property("joiningDate"), "joiningDate")
//                    .add(Projections.property("emerCtc"), "emerCtc")
//                    .add(Projections.property("emerCtcNo"), "emerCtcNo")
//                    .add(Projections.property("tempAddress") , "tempAddress")
//                    .add(Projections.property("permAddress") , "permAddress")
//                    .add(Projections.property("gender") , "gender")
//                    .add(Projections.property("maritalStatus") , "maritalStatus")
//                    .add(Projections.property("tempPin") , "tempPin")
//                    .add(Projections.property("permPin") , "permPin")
//                    .add(Projections.property("birthDate") , "birthDate")
//                    .add(Projections.property("passportNo") , "passportNo")
//                    .add(Projections.property("passportExpdate") , "passportExpdate")
//                    .add(Projections.property("passportCopy") , "passportCopy")
//                    .add(Projections.property("emerCtcRel") , "emerCtcRel")
//                    .add(Projections.property("nationality") , "nationality")
//                    .add(Projections.property("tempCity") , "tempCity")
//                    .add(Projections.property("tempState") , "tempState")
//                    .add(Projections.property("tempCountry") , "tempCountry")
//                    .add(Projections.property("permCity") , "permCity")
//                    .add(Projections.property("permState"), "permState")
//                    .add(Projections.property("permCountry"), "permCountry")
//                    .add(Projections.property("phone") , "phone"))
//                    .addOrder(Order.desc("id"));


            q = session.createQuery("from Employees");

            this.allEmployees.addAll(q.list());

//            this.allEmployees.addAll(cr.list());
            trx.commit();

        } catch (Exception e) {

            trx.rollback();

            if (this.allEmployees == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"),
                        text.getString("ui.Bean.UnexpectedError")));
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

// flow liestner for addwizard.
    public String onFlowProcess(FlowEvent event) throws Exception {
        try {
            if ("photo".equals(event.getNewStep())) {
                s++;
                this.addEmployees();
            }

            if (skip) {
                skip = false;   //reset in case user goes back 
                return "confirm";
            } else {
                return event.getNewStep();
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

// flow liestner for updatewizard.
    public String onProcess(FlowEvent event) throws Exception {

        if ("Photo".equals(event.getNewStep())) {

            this.updateEmployees();

        } else if ("selectedEmployeeDesignation".equals(event.getNewStep())) {
        }
        if (skip) {
            skip = false;   //reset in case user goes back  
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

// adding the new employee (registering the new employee. )
    public void addEmployees() throws Exception {

        Session session = null;
        Transaction trx = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            this.selectedEmployee.setCreatedby(userId);
            this.selectedEmployee.setCreatedon(new Timestamp(date.getTime()));

            ContactTypes contactType = new ContactTypes();
            contactType.setId(6);

            if (this.selectedEmployee.getJoiningDate().compareTo(this.selectedEmployee.getBirthDate()) > 0) {

                if (!this.selectedEmployee.getEmail().equals("")) {
                    Pattern pattern;
                    Matcher matcher;
                    String MAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    pattern = Pattern.compile(MAIL_PATTERN);
                    matcher = pattern.matcher(this.selectedEmployee.getEmail());
                    if (matcher.matches()) {

                        session.save(this.selectedEmployee);
                        this.id = this.selectedEmployee.getId();

                        this.selectedContact.setName(this.getSelectedEmployee().getFirstname() + " " + this.getSelectedEmployee().getLastname());
                        this.selectedContact.setAddress(this.getSelectedEmployee().getPermAddress());
                        this.selectedContact.setCreatedby(userId);
                        this.selectedContact.setCreatedon(new Timestamp(date.getTime()));
                        this.selectedContact.setEmail(this.selectedEmployee.getEmail());
                        this.selectedContact.setContacttypes(contactType);
                        this.selectedContact.setMobile(this.selectedEmployee.getPhone());

                        session.save(this.selectedContact);

                        trx.commit();
                        FacesMessage msg = new FacesMessage(text.getString("ui.Bean.Employee") + " "
                                + this.selectedEmployee.getFirstname() + " " + this.selectedEmployee.getLastname() + " " + text.getString("ui.Bean.AddSuccess"));
                        FacesContext.getCurrentInstance().addMessage(null, msg);

                        this.setTabview(false);
                        this.setActiveIndex(1);

                        RequestContext.getCurrentInstance().update("tbvEmployees");


                    } else {
                        String message = text.getString("ui.Bean.emailnotvalidate");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                        RequestContext.getCurrentInstance().update("pnlMain");

                    }
                } else {
                    session.save(this.selectedEmployee);
                    this.id = this.selectedEmployee.getId();

                    this.selectedContact.setName(this.getSelectedEmployee().getFirstname() + " " + this.getSelectedEmployee().getLastname());
                    this.selectedContact.setAddress(this.getSelectedEmployee().getPermAddress());
                    this.selectedContact.setCreatedby(userId);
                    this.selectedContact.setCreatedon(new Timestamp(date.getTime()));
                    this.selectedContact.setContacttypes(contactType);
                    this.selectedContact.setMobile(this.selectedEmployee.getPhone());

                    session.save(this.selectedContact);

                    trx.commit();
                    FacesMessage msg = new FacesMessage(text.getString("ui.Bean.Employee") + " "
                            + this.selectedEmployee.getFirstname() + " " + this.selectedEmployee.getLastname() + " " + text.getString("ui.Bean.AddSuccess"));
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                    this.setTabview(false);
                    this.setActiveIndex(1);

                    RequestContext.getCurrentInstance().update("tbvEmployees");

                }



            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " joining date should be greator than the birth date", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext req = RequestContext.getCurrentInstance();
                //req.execute("dlgadd.hide()");
                req.update("pnlMain");
            }
        } catch (Exception e) {
            trx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    text.getString("ui.Bean.UnexpectedError"), text.getString("ui.Bean.UnexpectedError")));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    text.getString("ui.Bean.ErrorMsgclickNext"), text.getString("ui.Bean.ErrorMsgclickNext")));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    text.getString("ui.Bean.ErrorMsgRefreshPage"), text.getString("ui.Bean.ErrorMsgRefreshPage")));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    text.getString("ui.Bean.ErrorSysAdmin"), text.getString("ui.Bean.ErrorSysAdmin")));
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

// update the new added Employee after the photo uploading. 
    public void updateaddedEmployee() throws Exception {

        Session session = null;
        Transaction trx = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            String strPhotoPath = "/resources/photos/up/" + this.getSelectedEmployee().getId() + "_jpg.jpg";
            String empno = this.selectedEmployee.getEmployeeno();
            this.getSelectedEmployee().setPhoto(strPhotoPath);
            this.updateEmployee = this.selectedEmployee;
            session.update(this.updateEmployee);
            trx.commit();
            FacesMessage msgs = new FacesMessage(text.getString("ui.Bean.Employee") + " " + this.getSelectedEmployee().getFirstname()
                    + " " + this.getSelectedEmployee().getLastname() + " " + text.getString("ui.Bean.AddSuccess"));
            FacesContext.getCurrentInstance().addMessage(null, msgs);

            RequestContext req = RequestContext.getCurrentInstance();

            this.setStrfirstname(this.updateEmployee.getFirstname());

            this.setStrlastname(this.updateEmployee.getLastname());

            if (this.selectedEmployeetatus == "" || this.selectedEmployeetatus == null) {
                this.setselectedEmployeetatus("Current");
            }

            req.update(":frmItemPhoto:img");
//            req.update("empName");
//            req.update(":frmEmpPhoto:pnlgrdAddPhoto");
//            req.update("pnlMain");
            this.retrieveAllEmployeesBysearch();

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

// update the existing added employee.
    public void updateEmployees() throws Exception {
        Session session = null;
        Transaction trx = null;

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            session.saveOrUpdate(this.selectedEmployee);
            trx.commit();
            RequestContext req = RequestContext.getCurrentInstance();
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
            if (k == 1) {
                FacesMessage msg = new FacesMessage(text.getString("ui.Bean.Employee") + " "
                        + this.selectedEmployee.getFirstname() + " " + this.selectedEmployee.getLastname() + " " + text.getString("ui.Bean.UpdateSuccess"));
                FacesContext.getCurrentInstance().addMessage(null, msg);

                //req.update("pnlMain");
                req.update(":frmEmpUpdate:panelforemployeeupdate");
                req.update("frmSearch:growlmsg");

            } else {
                FacesMessage msg = new FacesMessage(text.getString("ui.Bean.Employee") + " "
                        + this.selectedEmployee.getFirstname() + " " + this.selectedEmployee.getLastname() + "  " + text.getString("ui.Bean.UpdatePhotoSuccess"));
                FacesContext.getCurrentInstance().addMessage(null, msg);
                k = 0;

                // req.update("pnlMain");
                req.update(":frmEmpPhoto:pnlgrdAddPhoto");
                req.update("frmSearch:growlmsg");
            }
        } catch (Exception e) {
            trx.rollback();
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

//delete the existing employee
    public void deleteEmployee() throws Exception {
        Session session = null;
        Transaction trx = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            session.delete(this.selectedEmployee);
            trx.commit();
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update(":pnlMain");
            FacesMessage msg = new FacesMessage(text.getString("ui.Bean.Employee") + " "
                    + this.selectedEmployee.getFirstname() + " " + this.selectedEmployee.getLastname() + " " + text.getString("ui.Bean.Delete"));
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception e) {
            trx.rollback();
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
    int z = 0;

// for updating the existing employee onclick update menuitem  
    public void updateforupdate() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (z > 0) {
            requestContext.execute("tabwiz.select(0)");
            requestContext.execute("dlgupdate.show()");
            requestContext.update("dialogforupdate");
            requestContext.update("frmUpdateEmployee");

        } else {
            requestContext.execute("dlgupdate.show()");
            requestContext.update("dialogforupdate");
            requestContext.update("frmUpdateEmployee");
        }
        z++;
    }

    public void retrieveAllEmployeesBysearch() throws Exception {

        Session session = null;
        Transaction trx = null;

        this.searchEmployees.clear();
        Criteria cr = null;
        Query q = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            RequestContext req = RequestContext.getCurrentInstance();
            //session.enableFilter("JoiningDateFilter").setParameter(":joiningDateFilterParam", new Date());
            if (this.getSelectedProject() == 0) {

                if (this.selectedEmployeetatus != null && !this.selectedEmployeetatus.equals("")) {

                    if (this.selectedEmployeetatus.equals("Current")) {

// for checking wheather the firstname string for search is null 

                        if (this.strfirstname != null && !"".equals(this.strfirstname)) {

                            q = session.createQuery("Select distinct employees from Employees employees where Lower(employees.firstname) like '"
                                    + strfirstname.toLowerCase() + "%' and employees.leavingDate is null");

                            this.searchEmployees.addAll(q.list());

                        } // for checking wheather the lastname string for search is null 
                        else if (this.strlastname != null && !"".equals(this.strlastname)) {

                            q = session.createQuery("Select distinct employees from Employees employees where Lower(employees.lastname) like '"
                                    + strlastname.toLowerCase() + "%' and employees.leavingDate is null");

                            this.searchEmployees.addAll(q.list());

                        } // if both firstname and lastname are null
                        else if ((this.strfirstname == null || this.strlastname == null) || ("".equals(this.strfirstname) || "".equals(this.strlastname))) {
                            return;
                        }
                    } else if (this.selectedEmployeetatus.equals("Past")) {

// for checking wheather the firstname string for search is null 

                        if (this.strfirstname != null && !"".equals(this.strfirstname)) {

//            cr = session.createCriteria(Employees.class)
//                    .add(Restrictions.like("firstname", strfirstname+"%"))
//                    .add(Restrictions.isNotNull(this.employee.getLeavingDate().toString()));
//            
//            this.searchEmployees.addAll(cr.list());

                            q = session.createQuery("Select distinct employees from Employees employees where Lower(employees.firstname) like '"
                                    + strfirstname.toLowerCase() + "%' and employees.leavingDate is not null");

                            this.searchEmployees.addAll(q.list());


                        } // for checking wheather the lastname string for search is null 
                        else if (this.strlastname != null && !"".equals(this.strlastname)) {

//                cr = session.createCriteria(Employees.class)
//                    .add(Restrictions.like("lastname", strlastname+"%"))
//                    .add(Restrictions.isNotNull(this.employee.getLeavingDate().toString()));
//               
//                this.searchEmployees.addAll(cr.list());   

                            q = session.createQuery("Select distinct employees from Employees employees where Lower(employees.lastname) like '"
                                    + strlastname.toLowerCase() + "%' and employees.leavingDate is null");

                            this.searchEmployees.addAll(q.list());

                        } // if both firstname and lastname are null
                        else if ((this.strfirstname == null || this.strlastname == null) || ("".equals(this.strfirstname) || "".equals(this.strlastname))) {

                            return;
                        }
                    }
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.EmployeeStatus"), null);

                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update(":pnlMain");
                }
            } else {
                if (this.selectedEmployeetatus != null && !this.selectedEmployeetatus.equals("")) {

                    if (this.selectedEmployeetatus.equals("Current")) {

// for checking wheather the firstname string for search is null 

                        if (this.strfirstname != null && !"".equals(this.strfirstname)) {

//            cr = session.createCriteria(Employees.class)
//                    .add(Restrictions.like("firstname", strfirstname+"%"))
//                    .add(Restrictions.isNull(this.employee.getLeavingDate().toString()));
//            this.searchEmployees.addAll(cr.list());

                            q = session.createQuery("Select distinct empdetails.employees "
                                    + "from EmployeeDetails empdetails "
                                    + "where Lower(empdetails.employees.firstname) like '" + strfirstname.toLowerCase() + "%'"
                                    + " and empdetails.projects=" + this.getSelectedProject()
                                    + " and empdetails.endDate is null");

//q = session.createQuery("Select employees from Employees employees where employees.firstname like '"+strfirstname+"%' and employees.joiningDate is not null");

                            this.searchEmployees.addAll(q.list());


                        } // for checking wheather the lastname string for search is null 
                        else if (this.strlastname != null && !"".equals(this.strlastname)) {

//                cr = session.createCriteria(Employees.class)
//                    .add(Restrictions.like("lastname", strlastname+"%"))
//                    .add(Restrictions.isNull(this.employee.getLeavingDate().toString()));
//                this.searchEmployees.addAll(cr.list());                 

                            q = session.createQuery("Select distinct empdetails.employees "
                                    + "from EmployeeDetails empdetails "
                                    + "where Lower(empdetails.employees.lastname) like '" + strlastname.toLowerCase() + "%'"
                                    + " and empdetails.projects=" + this.getSelectedProject()
                                    + " and empdetails.endDate is null");


//  q = session.createQuery("Select employees from Employees employees where employees.lastname like '"+strlastname+"%' and employees.joiningDate is not null");

                            this.searchEmployees.addAll(q.list());

                        } // if both firstname and lastname are null
                        else if ((this.strfirstname == null || this.strlastname == null) || ("".equals(this.strfirstname) || "".equals(this.strlastname))) {

                            return;
                        }
                    } else {

// for checking wheather the firstname string for search is null 

                        if (this.strfirstname != null && !"".equals(this.strfirstname)) {

//            cr = session.createCriteria(Employees.class)
//                    .add(Restrictions.like("firstname", strfirstname+"%"))                    
//                    .add(Restrictions.isNotNull(this.employee.getLeavingDate().toString()));
//               
//            this.searchEmployees.addAll(cr.list());

                            q = session.createQuery("Select distinct empdetails.employees "
                                    + "from EmployeeDetails empdetails "
                                    + "where Lower(empdetails.employees.firstname) like '" + strfirstname.toLowerCase() + "%'"
                                    + " and empdetails.projects=" + this.getSelectedProject()
                                    + " and empdetails.endDate is not null");

//q = session.createQuery("Select employees from Employees employees where employees.firstname like '"+strfirstname+"%' and employees.joiningDate is not null");

                            this.searchEmployees.addAll(q.list());


                        } // for checking wheather the lastname string for search is null 
                        else if (this.strlastname != null && !"".equals(this.strlastname)) {

//                cr = session.createCriteria(Employees.class)
//                    .add(Restrictions.like("lastname", strlastname+"%"))
//                    .add(Restrictions.isNotNull(this.employee.getLeavingDate().toString()));
//              
//                this.searchEmployees.addAll(cr.list());    

                            q = session.createQuery("Select distinct empdetails.employees "
                                    + "from EmployeeDetails empdetails "
                                    + "where Lower(empdetails.employees.lastname) like '" + strlastname.toLowerCase() + "%'"
                                    + " and empdetails.projects=" + this.getSelectedProject()
                                    + " and empdetails.endDate is null");

//q = session.createQuery("Select employees from Employees employees where employees.firstname like '"+strfirstname+"%' and employees.joiningDate is not null");

                            this.searchEmployees.addAll(q.list());
                        } // if both firstname and lastname are null
                        else if ((this.strfirstname == null || this.strlastname == null) || ("".equals(this.strfirstname) || "".equals(this.strlastname))) {
                            return;
                        }
                    }
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.EmployeeStatus"), null);

                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("pnlMain");
                }
            }
            trx.commit();
        } catch (Exception e) {
            trx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.UnexpectedError"),
                    text.getString("ui.Bean.UnexpectedError")));
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

    public void setSelectedEmployeeNewToAdd() {

        this.setSelectedEmployee(null);

        this.setSelectedEmployee(new Employees());
    }

    public void finishUploading() {
        try {

            this.setStrfirstname(this.selectedEmployee.getFirstname());

            this.setStrlastname(this.selectedEmployee.getLastname());

            this.retrieveAllEmployeesBysearch();
            RequestContext req = RequestContext.getCurrentInstance();

            req.update("imgEmp");
            req.execute("DlgConfirm.hide()");
            req.update(":frmSearch:tblEmployee");

            req.update("pnlMain");
            req.update("growlmsg");

            k = 0;
            s = 0;
        } catch (Exception ex) {
            Logger.getLogger(EmployeesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void returnToMain() {

        try {

            FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
        }

    }

    public void retrieveAllZEmployee() {

        Session session = null;
        Transaction trx = null;

        Query q = null;

        this.allZEmployees.clear();

        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            FacesContext facesContext = FacesContext.getCurrentInstance();

            int id = this.getSelectedEmployee().getId();

            q = session.createQuery("from ZEmployees ze where ze.employeeId=" + id + " order by ze.id desc");

            this.setAllZEmployees(q.list());
            trx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    public void retrieveEmpAddUser() {

        Session session = null;
        Transaction trx = null;

        Query q = null;

        this.searchEmployees.clear();
        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            RequestContext req = RequestContext.getCurrentInstance();

            if (this.strfirstname != null && !"".equals(this.strfirstname)) {
                q = session.createQuery("Select e "
                        + "from Employees e,Users u "
                        + "where Lower(e.firstname) like '" + strfirstname.toLowerCase() + "%'"
                        + " and u.employees.id != e.id");
                this.searchEmployees.addAll(q.list());
            } else if (this.strlastname != null && !"".equals(this.strlastname)) {

                q = session.createQuery("Select e "
                        + "from Employees e,Users u "
                        + "where Lower(e.lastname) like '" + strlastname.toLowerCase() + "%'"
                        + " and u.employees.id != e.id");
                this.searchEmployees.addAll(q.list());
            }
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
}
