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
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;
import orm.*;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "areaDAO")
@ViewScoped
public class AreaDAO implements Serializable {

    private List<Area> allArea;
    private Area selectedArea;

    public List<Area> getAllArea() {
        return allArea;
    }

    public void setAllArea(List<Area> allArea) {
        this.allArea = allArea;
    }

    public Area getSelectedArea() {
        return selectedArea;
    }

    public void setSelectedArea(Area selectedArea) {
        this.selectedArea = selectedArea;
    }

    /**
     * Creates a new instance of AreaDAO
     */
    public AreaDAO() {

        this.allArea = new ArrayList<Area>();

        this.selectedArea = new Area();
        System.out.println("New AreaDAO is created......");
    }

    public void retrieveAllArea() {

        Session session = null;
        Transaction trx = null;

        Query q = null;

        Criteria cr = null;       

        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                this.allArea.clear();

//            String hqlQuery = "from Area";
//
//            q = session.createQuery(hqlQuery);

                cr = session.createCriteria(Area.class, "area").createCriteria("area.rooms", JoinType.INNER_JOIN).createCriteria("area.floores", JoinType.INNER_JOIN).createCriteria("area.buildings", JoinType.INNER_JOIN).createCriteria("area.areatypes", JoinType.INNER_JOIN).addOrder(Order.desc("area.buildings.id")).addOrder(Order.desc("area.floores.id")).addOrder(Order.desc("area.rooms.id")).addOrder(Order.desc("area.areatypes.id"));

                this.allArea.addAll(cr.list());

                trx.commit();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (q != null) {
                q = null;
            }
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

    public void addArea() {

        Session session = null;
        Transaction trx = null;

        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            Date date = new Date();

            FacesContext facesContext = FacesContext.getCurrentInstance();

            RoomsDAO roomsDAO = (RoomsDAO) facesContext.getApplication().createValueBinding("#{roomsDAO}").getValue(facesContext);
            FlooresDAO flooresDAO = (FlooresDAO) facesContext.getApplication().createValueBinding("#{flooresDAO}").getValue(facesContext);
            BuildingsDAO buildingsDAO = (BuildingsDAO) facesContext.getApplication().createValueBinding("#{buildingsDAO}").getValue(facesContext);
            AreaTypesDAO areaTypesDAO = (AreaTypesDAO) facesContext.getApplication().createValueBinding("#{areaTypesDAO}").getValue(facesContext);
            Login loginDAO = (Login) facesContext.getApplication().createValueBinding("#{loginbean}").getValue(facesContext);

            this.selectedArea.setRooms(roomsDAO.getSelectedRoom());
            this.selectedArea.setFloores(flooresDAO.getSelectedFloore());
            this.selectedArea.setBuildings(buildingsDAO.getSelectedBuilding());
            this.selectedArea.setAreatypes(areaTypesDAO.getSelectedAreaType());
            this.selectedArea.setCreatedby(loginDAO.getLoggedinUser().getId());
            this.selectedArea.setCreatedon(new Timestamp(date.getTime()));

            session.save(this.selectedArea);

            trx.commit();

            this.retrieveAllArea();

            FacesMessage msg = new FacesMessage("Area added successfully.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            this.setSelectedArea(null);
            this.setSelectedArea(new Area());
            flooresDAO.setSelectedFloore(new Floores());
            areaTypesDAO.setSelectedAreaType(new AreaTypes());
            buildingsDAO.setSelectedBuilding(new Buildings());
            roomsDAO.setSelectedRoom(new Rooms());


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
