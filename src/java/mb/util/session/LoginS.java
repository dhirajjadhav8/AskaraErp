/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.util.session;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import manager.impl.LoginDetailsManagerImpl;
import manager.impl.RolesManagerImpl;
import manager.impl.UsersManagerImpl;
import manager.interfaces.ILoginDetailsManager;
import manager.interfaces.IRolesManager;
import manager.interfaces.IUsersManager;
import org.hibernate.Query;
import org.primefaces.context.RequestContext;
import orm.LoginDetails;
import orm.Roles;
import orm.Users;
import util.ForceSessionCreateA;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "loginbean")
@SessionScoped
public class LoginS implements Serializable {

    private boolean loggedin;
    private String username;
    private String password;
//    private List<Users> userList;
    private String HttpServletRequest;
    private LoginDetails loginDetails;
    private Users loggedinUser;
    String seperator = System.getProperty("file.separator");
    /*
     ILoginDetailsManager
     */
    private ILoginDetailsManager ldm = new LoginDetailsManagerImpl();
    private IUsersManager um = new UsersManagerImpl();
    /*
     IRolesManager
     */
    private IRolesManager rm = new RolesManagerImpl();
    private List<Roles> rolesOfUser;

    /**
     * Creates a new instance of LoginS
     */
    public LoginS() {
//        this.userList = new ArrayList<Users>();
        this.loginDetails = new LoginDetails();

        System.out.println(" New Login is created......");
    }

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

    public List<Roles> getRolesOfUser() {
        return rolesOfUser;
    }

    public void setRolesOfUser(List<Roles> rolesOfUser) {
        this.rolesOfUser = rolesOfUser;
    }

//    public List<Users> getUserList() {
//        return userList;
//    }
//
//    public void setUserList(List<Users> userList) {
//        this.userList = userList;
//    }
    public boolean isEInwardsOutwardsRolePresent() {
        boolean isRoleAvaliable = false;
        for (int i = 0; i < this.getRolesOfUser().size(); i++) {
            if (this.getRolesOfUser().get(i).getId() == 3) {
                isRoleAvaliable = true;
            }
        }
        System.out.println("isEInwardsOutwardsRolePresent " + isRoleAvaliable);
        System.out.println("empId " + loggedinUser.getEmployees().getId());
        return isRoleAvaliable;
    }

    public void checkLogin() {

        Query q = null;
        HibernateUtil.beginTransaction();
        System.out.println("111111111111111111111111111111111111111111111");


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

                    Users u = um.checkLoginAndReturnLoggedInUser(username, password);

                    if (u == null) {

                        this.setUsername("");
                        this.setPassword("");

                        FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                text.getString("ui.Bean.InvalidUserPass"), "");
                        FacesContext.getCurrentInstance().addMessage(null, error);


                    } else {
                        rolesOfUser = rm.getRolesOfUser(u.getId());
                        setLoggedinUser(u);
                        q = HibernateUtil.getSession().createQuery("select count(*) from UserLocking uloc where uloc.unlockDate is null and uloc.users.id=" + u.getId());

                        long i = (Long) q.list().get(0);

                        // if user is not locked.
                        if (i == 0) {
                            Date date = new Date();

                            Timestamp stmt = new Timestamp(date.getTime());

                            String ipAddress = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
                            String sessionId = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestedSessionId();
                            String isSuccess = "Y";

                            this.loginDetails.setIpaddress(ipAddress);
                            this.loginDetails.setSessionid(sessionId);
                            this.loginDetails.setUsername(this.username);

                            this.loginDetails.setLoginDate(new Timestamp(date.getTime()));
                            this.loginDetails.setIssuccess(isSuccess.charAt(0));
//                            this.loginDetails.setCreatedby(u.getId());
//                            this.loginDetails.setCreatedon(new Timestamp(date.getTime()));
                            ldm.saveNew(loginDetails);


                            this.setLoggedinUser(u);
                            this.setLoggedin(true);

                            FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");

                        } else {

                            // if user is locked.

                            Date date = new Date();
                            Date date1 = new Date();

                            String ipAddress = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
                            String sessionId = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestedSessionId();
                            String isSuccess = "N";
                            this.loginDetails.setIpaddress(ipAddress);
                            this.loginDetails.setSessionid(sessionId);
                            this.loginDetails.setUsername(this.username);
                            this.loginDetails.setLoginDate(new Timestamp(date.getTime()));
                            this.loginDetails.setIssuccess(isSuccess.charAt(0));
                            this.loginDetails.setFailReason("User is Locked.");
//                            this.loginDetails.setCreatedby(u.getId());
//                            this.loginDetails.setCreatedon(new Timestamp(date1.getTime()));


                            HibernateUtil.getSession().saveOrUpdate(this.loginDetails);

                            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    text.getString("ui.Bean.AccountLock"), "");
                            FacesContext.getCurrentInstance().addMessage(null, error);
                            RequestContext.getCurrentInstance().update(":frmlogin:grolwlmsg");

                            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");

                        }

                    }

                }
            }
            System.out.println("2222222222222222222222222222222222222222");
            HibernateUtil.commitTransaction();
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
            HibernateUtil.rollbackTransaction();

        } finally {

            if (q != null) {
                q = null;
            }

            HibernateUtil.closeSession();

        }
    }

    public void logout() {

        try {
            Date date = new Date();
            Timestamp stmt = new Timestamp(date.getTime());

            this.loginDetails.setLogoutDate(new Timestamp(date.getTime()));

            HibernateUtil.beginTransaction();
            ldm.updateExisting(loginDetails);
            HibernateUtil.commitTransaction();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ForceSessionCreateA forceSessionCreateA = (ForceSessionCreateA) facesContext.getApplication().createValueBinding("#{forceSessionCreateA}").getValue(facesContext);
            FacesContext.getCurrentInstance().getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + forceSessionCreateA.getSeparator() + "faces" + forceSessionCreateA.getSeparator() + "login.xhtml");


        } catch (Exception ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
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
