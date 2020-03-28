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
import orm.AreaTypes;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "areaTypesDAO")
@ViewScoped
public class AreaTypesDAO implements Serializable {

    private List<AreaTypes> allAreaTypes;
    private AreaTypes selectedAreaType;

    public List<AreaTypes> getAllAreaTypes() {
        return allAreaTypes;
    }

    public void setAllAreaTypes(List<AreaTypes> allAreaTypes) {
        this.allAreaTypes = allAreaTypes;
    }

    public AreaTypes getSelectedAreaType() {
        return selectedAreaType;
    }

    public void setSelectedAreaType(AreaTypes selectedAreaType) {
        this.selectedAreaType = selectedAreaType;
    }

    /**
     * Creates a new instance of AreaTypesDAO
     */
    public AreaTypesDAO() {

        this.allAreaTypes = new ArrayList<AreaTypes>();

        this.selectedAreaType = new AreaTypes();

        System.out.println("New AreaTypesDAO is created......");

    }

    public void retrieveAllAreaType() {

        Session session = null;
        Transaction trx = null;

        Query q = null;

        Criteria cr = null;

        try {
           
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                this.allAreaTypes.clear();


//             String hqlQueryforAreaType = "from AreaTypes";
//
//            q = session.createQuery(hqlQueryforAreaType);

                cr = session.createCriteria(AreaTypes.class, "areatypes").addOrder(Order.desc("areatypes.id"));

                this.allAreaTypes.addAll(cr.list());

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

        this.setSelectedAreaType(null);

        this.setSelectedAreaType(new AreaTypes());
    }

    public void addAreaType() {

        Session session = null;
        Transaction trx = null;
        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            Date date = new Date();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            this.selectedAreaType.setCreatedby(loginDAO.getLoggedinUser().getId());
            this.selectedAreaType.setCreatedon(new Timestamp(date.getTime()));

            session.save(this.selectedAreaType);

            trx.commit();

            FacesMessage msg = new FacesMessage("Area Type added successfully.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            this.setSelectedAreaType(null);
            this.setSelectedAreaType(new AreaTypes());
            this.retrieveAllAreaType();

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
