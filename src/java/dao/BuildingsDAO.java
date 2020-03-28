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
import orm.Buildings;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "buildingsDAO")
@ViewScoped
public class BuildingsDAO implements Serializable {

    private List<Buildings> allBuildings;
    private Buildings selectedBuilding;

    public List<Buildings> getAllBuildings() {
        return allBuildings;
    }

    public void setAllBuildings(List<Buildings> allBuildings) {
        this.allBuildings = allBuildings;
    }

    public Buildings getSelectedBuilding() {
        return selectedBuilding;
    }

    public void setSelectedBuilding(Buildings selectedBuilding) {
        this.selectedBuilding = selectedBuilding;
    }

    /**
     * Creates a new instance of BuildingsDAO
     */
    public BuildingsDAO() {

        this.allBuildings = new ArrayList<Buildings>();
        this.selectedBuilding = new Buildings();

        System.out.println("New BuildingsDAO is created......");
    }

    public void retrieveAllBuildings() {

        Session session = null;
        Transaction trx = null;

        Query q = null;

        Criteria cr = null;

        try {
           
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                this.allBuildings.clear();


//            String hqlQueryforBuilding = "from Buildings";
//
//            q = session.createQuery(hqlQueryforBuilding);

                cr = session.createCriteria(Buildings.class, "buildings").addOrder(Order.desc("buildings.id"));

                this.allBuildings.addAll(cr.list());

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

        this.setSelectedBuilding(null);

        this.setSelectedBuilding(new Buildings());
    }

    public void addBuilding() {

        Session session = null;
        Transaction trx = null;
        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            Date date = new Date();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            this.selectedBuilding.setCreatedby(loginDAO.getLoggedinUser().getId());
            this.selectedBuilding.setCreatedon(new Timestamp(date.getTime()));

            session.save(this.selectedBuilding);

            trx.commit();

            FacesMessage msg = new FacesMessage("Floore added successfully.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            this.setSelectedBuilding(null);
            this.setSelectedBuilding(new Buildings());
            this.retrieveAllBuildings();

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
