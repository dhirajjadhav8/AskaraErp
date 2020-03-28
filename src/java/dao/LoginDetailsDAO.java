/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import orm.LoginDetails;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "loginDetailsDAO")
@ViewScoped
public class LoginDetailsDAO implements Serializable {

    private String currentDate;
    private List<LoginDetails> allLoginDetails;
    private LoginDetails loginDetailsfrom;
    private LoginDetails loginDetailsto;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public List<LoginDetails> getAllLoginDetails() {
        return allLoginDetails;
    }

    public void setAllLoginDetails(List<LoginDetails> allLoginDetails) {
        this.allLoginDetails = allLoginDetails;
    }

    public LoginDetails getLoginDetailsfrom() {
        return loginDetailsfrom;
    }

    public void setLoginDetailsfrom(LoginDetails loginDetailsfrom) {
        this.loginDetailsfrom = loginDetailsfrom;
    }

    public LoginDetails getLoginDetailsto() {
        return loginDetailsto;
    }

    public void setLoginDetailsto(LoginDetails loginDetailsto) {
        this.loginDetailsto = loginDetailsto;
    }

    /**
     * Creates a new instance of LoginDetailsDAO
     */
    public LoginDetailsDAO() {
        this.allLoginDetails = new ArrayList<LoginDetails>();
        this.loginDetailsfrom = new LoginDetails();
        this.loginDetailsto = new LoginDetails();

        System.out.println(" New LoginDetailsDAO is created......");

    }

    public void retrieveAllloginDetails() {

        Query q = null;

        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {

                this.allLoginDetails.clear();

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();


                Date date = new Date();

                String curr_date = date.toString();

                SimpleDateFormat strDate = new SimpleDateFormat("EEE MMM d hh:mm:ss Z yyyy");
                Date datetoformat = strDate.parse(curr_date);
                SimpleDateFormat strDate1 = new SimpleDateFormat("yyyy-MM-dd");

                curr_date = strDate1.format(datetoformat);

                Date currentDate = strDate1.parse(curr_date);

                this.setCurrentDate(curr_date);


                q = session.createQuery("from LoginDetails ld where cast(ld.loginDate as date)='" + curr_date + "'");
                q.setMaxResults(50);
                this.setAllLoginDetails(q.list());

                trx.commit();
            }
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

    public void searchLoginDetails() {

        Session session = null;
        Transaction trx = null;

        Query q = null;

        RequestContext req = RequestContext.getCurrentInstance();

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        try {
            if (this.loginDetailsfrom.getLoginDate() == null || this.loginDetailsfrom.getLoginDate().toString().equals("")) {
                FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.fromDaterequired"), text.getString("ui.Bean.fromDaterequired"));
                FacesContext.getCurrentInstance().addMessage(null, error);
                req.update(":frmsearchLoginByDate:grolwlmsg");
            } else if (this.loginDetailsto.getLoginDate() == null || this.loginDetailsto.getLoginDate().toString().equals("")) {
                FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.toDaterequired"), text.getString("ui.Bean.toDaterequired"));
                FacesContext.getCurrentInstance().addMessage(null, error);
                req.update(":frmsearchLoginByDate:grolwlmsg");
            } else if (this.loginDetailsto.getLoginDate().compareTo(this.loginDetailsfrom.getLoginDate()) < 0) {
                FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("ui.Bean.LoginDateSmaller"), text.getString("ui.Bean.LoginDateSmaller"));
                FacesContext.getCurrentInstance().addMessage(null, error);
                req.update(":frmsearchLoginByDate:grolwlmsg");
            } else {
                this.allLoginDetails.clear();
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                String loginDateFirst = this.loginDetailsfrom.getLoginDate().toString();
                String loginDateSecond = this.loginDetailsto.getLoginDate().toString();

//                SimpleDateFormat strDate = new SimpleDateFormat("EEE MMM d hh:mm:ss Z yyyy");
//                Date datetoformatFirst = strDate.parse(loginDateFirst);
//                Date datetoFormatSecond = strDate.parse(loginDateSecond);
//                SimpleDateFormat strDate1 = new SimpleDateFormat("yyyy-MM-dd");
//
//                loginDateFirst = strDate1.format(datetoformatFirst);
//                loginDateSecond = strDate1.format(datetoFormatSecond);

                q = session.createQuery("from LoginDetails ld where cast(ld.loginDate as date) between '" + loginDateFirst + "'" + " and '" + loginDateSecond + "'");
                q.setMaxResults(50);
                this.setAllLoginDetails(q.list());
                trx.commit();
            }

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
