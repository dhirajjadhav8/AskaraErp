/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;
import orm.LoginDetails;
import orm.Users;
import util.ForceSessionCreateA;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "loginbean")
@SessionScoped
public class Login {

    private boolean loggedin;
    private String username;
    private String password;
    private List<Users> userList;
    private String HttpServletRequest;
    private LoginDetails loginDetails;
    private Users loggedinUser;
    String seperator = System.getProperty("file.separator");

    public Users getLoggedinUser() {
        return loggedinUser;
    }

    public void setLoggedinUser(Users loggedinUser) {
        this.loggedinUser = loggedinUser;
    }

    public boolean isLoggedin() {
        return loggedin;
    }

    public void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Users> getUserList() {
        return userList;
    }

    public void setUserList(List<Users> userList) {
        this.userList = userList;
    }

    public LoginDetails getLoginDetails() {
        return loginDetails;
    }

    public void setLoginDetails(LoginDetails loginDetails) {
        this.loginDetails = loginDetails;
    }

    /**
     * Creates a new instance of Login
     */
    public Login() {
        this.userList = new ArrayList<Users>();
        this.loginDetails = new LoginDetails();

        System.out.println(" New Login is created......");
    }

    public void checkLogin() {

        Session session = null;
        Transaction trx = null;

        this.userList.clear();

        Criteria cr = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {
            if (this.username == null || this.username.equals("")) {
                FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        text.getString("ui.Bean.UserName"), "");
                FacesContext.getCurrentInstance().addMessage(null, error);
                RequestContext.getCurrentInstance().update(":frmlogin:grolwlmsg");
            } else {
                if (this.password == null || this.password.equals("")) {
                    FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            text.getString("ui.Bean.Password"), "");
                    FacesContext.getCurrentInstance().addMessage(null, error);
                    RequestContext.getCurrentInstance().update(":frmlogin:grolwlmsg");

                } else {
                    session = HibernateUtil.getSession();
                    trx = session.beginTransaction();

                    cr = session.createCriteria(Users.class).add(Restrictions.and(Restrictions.eq("username", this.username), Restrictions.eq("password", this.password)));
                    this.userList.addAll(cr.list());

                    if (this.userList.isEmpty() || this.userList.size() > 1) {

                        this.setUsername("");
                        this.setPassword("");

                        FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                text.getString("ui.Bean.InvalidUserPass"), "");
                        FacesContext.getCurrentInstance().addMessage(null, error);


                    } else {

                        Query q = session.createQuery("select count(*) from UserLocking uloc where uloc.unlockDate is null and uloc.users.id=" + this.userList.get(0).getId());

                        long i = (Long) q.list().get(0);

                        // if user is not locked.
                        if (i == 0) {
                            Date date = new Date();

                            Timestamp stmt = new Timestamp(date.getTime());

                            String ipAddress = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
                            String sessionId = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestedSessionId();
                            String isSuccess = "Y";

                            this.loginDetails.setIpAddress(ipAddress);
                            this.loginDetails.setSessionId(sessionId);
                            this.loginDetails.setUserName(this.username);

                            this.loginDetails.setLoginDate(new Timestamp(date.getTime()));
                            this.loginDetails.setIsSuccess(isSuccess.charAt(0));
                            this.loginDetails.setCreatedby(this.userList.get(0).getId());
                            this.loginDetails.setCreatedon(new Timestamp(date.getTime()));

                            session.saveOrUpdate(this.loginDetails);



                            this.setLoggedinUser(this.userList.get(0));
                            this.setLoggedin(true);

                            FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");

                        } else {

                            // if user is locked.

                            Date date = new Date();
                            Date date1 = new Date();

                            String ipAddress = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
                            String sessionId = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestedSessionId();
                            String isSuccess = "N";
                            this.loginDetails.setIpAddress(ipAddress);
                            this.loginDetails.setSessionId(sessionId);
                            this.loginDetails.setUserName(this.username);
                            this.loginDetails.setLoginDate(new Timestamp(date.getTime()));
                            this.loginDetails.setIsSuccess(isSuccess.charAt(0));
                            this.loginDetails.setFailReason("User is Locked.");
                            this.loginDetails.setCreatedby(this.getUserList().get(0).getId());
                            this.loginDetails.setCreatedon(new Timestamp(date1.getTime()));


                            session.saveOrUpdate(this.loginDetails);

                            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    text.getString("ui.Bean.AccountLock"), "");
                            FacesContext.getCurrentInstance().addMessage(null, error);
                            RequestContext.getCurrentInstance().update(":frmlogin:grolwlmsg");

                            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");

                        }

                    }

                }
            }
            trx.commit();

//            String userAgent = req.getHeader("user-agent");
//            UserAgent ua = UserAgent.parseUserAgentString(userAgent);
//            Version browserVersion = ua.getBrowserVersion();
//            String browserName = ua.getBrowser().toString();
//            int majVersion = Integer.parseInt(browserVersion.getMajorVersion());


        } catch (Exception e) {


            if (e.getMessage().equals("Could not open connection")) {
                String connectionError = text.getString("ui.connectionStart");

                FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        e.getMessage() + ". " + connectionError, "");
                FacesContext.getCurrentInstance().addMessage(null, error);
                RequestContext.getCurrentInstance().update(":frmlogin:grolwlmsg");
            }

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

    public void logout() {

        try {

            Session session = HibernateUtil.getSession();
            Transaction trx = session.beginTransaction();

            Date date = new Date();
            Timestamp stmt = new Timestamp(date.getTime());

            this.loginDetails.setLogoutDate(new Timestamp(date.getTime()));

            session.saveOrUpdate(this.loginDetails);
            trx.commit();

            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ForceSessionCreateA forceSessionCreateA = (ForceSessionCreateA) facesContext.getApplication().createValueBinding("#{forceSessionCreateA}").getValue(facesContext);


            FacesContext.getCurrentInstance().getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + forceSessionCreateA.getSeparator() + "faces" + forceSessionCreateA.getSeparator() + "login.xhtml");


        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
        }

    }

    public void checkAccess() {

        try {
            if (this.isLoggedin() == false) {

                FacesContext facesContext = FacesContext.getCurrentInstance();
                ForceSessionCreateA forceSessionCreateA = (ForceSessionCreateA) facesContext.getApplication().createValueBinding("#{forceSessionCreateA}").getValue(facesContext);
                FacesContext.getCurrentInstance().getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + forceSessionCreateA.getSeparator() + "faces" + forceSessionCreateA.getSeparator() + "login.xhtml");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
        }

    }
}
