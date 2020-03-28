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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import orm.Floores;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "flooresDAO")
@ViewScoped
public class FlooresDAO implements Serializable {

    private List<Floores> allFloores;
    private Floores selectedFloore;

    public List<Floores> getAllFloores() {
        return allFloores;
    }

    public void setAllFloores(List<Floores> allFloores) {
        this.allFloores = allFloores;
    }

    public Floores getSelectedFloore() {
        return selectedFloore;
    }

    public void setSelectedFloore(Floores selectedFloore) {
        this.selectedFloore = selectedFloore;
    }

    /**
     * Creates a new instance of FlooresDAO
     */
    public FlooresDAO() {

        this.allFloores = new ArrayList<Floores>();
        this.selectedFloore = new Floores();

        System.out.println("New FlooresDAO is created......");
    }

    public void retrieveAllFloores() {

        Session session = null;
        Transaction trx = null;

        Query q = null;
        Criteria cr = null;

        try {
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();


                this.allFloores.clear();


//            String hqlQueryforFloore = "from Floores";
//
//            q = session.createQuery(hqlQueryforFloore);

                cr = session.createCriteria(Floores.class, "floores").addOrder(Order.desc("floores.id"));

                this.allFloores.addAll(cr.list());

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

    public void removeSelected() {

        this.setSelectedFloore(null);

        this.setSelectedFloore(new Floores());
    }

    public void addFloore() {

        Session session = null;
        Transaction trx = null;
        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            Date date = new Date();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            this.selectedFloore.setCreatedby(loginDAO.getLoggedinUser().getId());
            this.selectedFloore.setCreatedon(new Timestamp(date.getTime()));

            session.save(this.selectedFloore);

            trx.commit();

            FacesMessage msg = new FacesMessage("Floore added successfully.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            this.setSelectedFloore(null);
            this.setSelectedFloore(new Floores());
            
            this.retrieveAllFloores();

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
