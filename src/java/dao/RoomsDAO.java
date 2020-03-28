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
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import orm.RoomTypes;
import orm.Rooms;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "roomsDAO")
@ViewScoped
public class RoomsDAO implements Serializable {

    private List<Rooms> allRooms;
    private Rooms selectedRoom;

    public List<Rooms> getAllRooms() {
        return allRooms;
    }

    public void setAllRooms(List<Rooms> allRooms) {
        this.allRooms = allRooms;
    }

    public Rooms getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Rooms selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    /**
     * Creates a new instance of RoomsDAO
     */
    public RoomsDAO() {

        this.allRooms = new ArrayList<Rooms>();
        this.selectedRoom = new Rooms();

        System.out.println("New RoomsDAO is created......");
    }

    public void retrieveAllRooms() {

        Session session = null;
        Transaction trx = null;

        Query q = null;

        Criteria cr = null;

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            this.allRooms.clear();


//            String hqlQueryforRoom = "from Rooms rooms order by rooms.id desc";
//
//            q = session.createQuery(hqlQueryforRoom);

            cr = session.createCriteria(Rooms.class, "rooms").createCriteria("rooms.roomtypes", "roomtype", JoinType.INNER_JOIN).addOrder(Order.desc("rooms.id"));

            this.allRooms.addAll(cr.list());

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

        this.setSelectedRoom(null);

        this.setSelectedRoom(new Rooms());
    }

    public void addRoom() {

        Session session = null;
        Transaction trx = null;
        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            Date date = new Date();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            RoomTypesDAO roomTypesDAO = (RoomTypesDAO) facesContext.getApplication().createValueBinding("#{roomTypesDAO}").getValue(facesContext);

            this.selectedRoom.setRoomtypes(roomTypesDAO.getSelectedRoomType());
            this.selectedRoom.setCreatedby(loginDAO.getLoggedinUser().getId());
            this.selectedRoom.setCreatedon(new Timestamp(date.getTime()));

            session.save(this.selectedRoom);

            trx.commit();

            FacesMessage msg = new FacesMessage("Room added successfully.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            this.setSelectedRoom(null);
            this.selectedRoom = new Rooms();

            roomTypesDAO.setSelectedRoomType(new RoomTypes());
            this.retrieveAllRooms();
          //  RequestContext.getCurrentInstance().update(":tblRoomdetails:tblRoom");

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
