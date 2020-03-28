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
import org.primefaces.context.RequestContext;
import orm.RoomTypes;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "roomTypesDAO")
@ViewScoped
public class RoomTypesDAO implements Serializable {

    private List<RoomTypes> allRoomTypes;
    private RoomTypes selectedRoomType;

    public List<RoomTypes> getAllRoomTypes() {
        return allRoomTypes;
    }

    public void setAllRoomTypes(List<RoomTypes> allRoomTypes) {
        this.allRoomTypes = allRoomTypes;
    }

    public RoomTypes getSelectedRoomType() {
        return selectedRoomType;
    }

    public void setSelectedRoomType(RoomTypes selectedRoomType) {
        this.selectedRoomType = selectedRoomType;
    }

    /**
     * Creates a new instance of RoomTypesDAO
     */
    public RoomTypesDAO() {

        this.allRoomTypes = new ArrayList<RoomTypes>();
        this.selectedRoomType = new RoomTypes();

        System.out.println("New RoomTypesDAO is created......");
    }

    public void retrieveAllRoomTypes() {

        Session session = null;
        Transaction trx = null;

        Query q = null;

        Criteria cr = null;



        try {
           
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                this.allRoomTypes.clear();

//            String hqlQueryforRoomType = "from RoomTypes";
//
//            q = session.createQuery(hqlQueryforRoomType);

                cr = session.createCriteria(RoomTypes.class, "roomtypes").addOrder(Order.desc("roomtypes.id"));

                this.allRoomTypes.addAll(cr.list());

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

        this.setSelectedRoomType(null);

        this.setSelectedRoomType(new RoomTypes());

        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("DlgAreaConfirm.hide()");
        req.execute("DlgRoomTypeConfirm.show()");
        req.update("confirmRoomTypeDlg");


    }

    public void addRoomType() {

        Session session = null;
        Transaction trx = null;
        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            Date date = new Date();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            this.selectedRoomType.setCreatedby(loginDAO.getLoggedinUser().getId());
            this.selectedRoomType.setCreatedon(new Timestamp(date.getTime()));

            session.save(this.selectedRoomType);

            trx.commit();

            FacesMessage msg = new FacesMessage("Room Type added successfully.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            this.setSelectedRoomType(null);
            this.setSelectedRoomType(new RoomTypes());
            
            this.retrieveAllRoomTypes();

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
