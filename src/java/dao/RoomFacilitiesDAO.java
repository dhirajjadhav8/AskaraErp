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
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.context.RequestContext;
import orm.Icategory;
import orm.ItemDetails;
import orm.RoomFacilities;
import orm.Rooms;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name = "roomFacilitiesDAO")
@ViewScoped
public class RoomFacilitiesDAO implements Serializable {

    private List<Rooms> allrooms;
    private List<Icategory> allCategories;
    private List<ItemDetails> allItemDetails;
    private List<RoomFacilities> allRoomFacilities;
    private List<RoomFacilities> allRoomFacilitiesHistory;
    private orm.RoomFacilities selectedRoomFacilities;
    private RoomFacilities selectedRoomFacilityToUpdate;
    private Rooms selectedRoom;
    private Icategory selectedCategory;
    private ItemDetails selectedItemDetails;

    public List<Rooms> getAllrooms() {
        return allrooms;
    }

    public void setAllrooms(List<Rooms> allrooms) {
        this.allrooms = allrooms;
    }

    public List<Icategory> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<Icategory> allCategories) {
        this.allCategories = allCategories;
    }

    public List<ItemDetails> getAllItemDetails() {
        return allItemDetails;
    }

    public void setAllItemDetails(List<ItemDetails> allItemDetails) {
        this.allItemDetails = allItemDetails;
    }

    public RoomFacilities getSelectedRoomFacilities() {
        return selectedRoomFacilities;
    }

    public void setSelectedRoomFacilities(RoomFacilities selectedRoomFacilities) {
        this.selectedRoomFacilities = selectedRoomFacilities;
    }

    public Rooms getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Rooms selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    public Icategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Icategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public ItemDetails getSelectedItemDetails() {
        return selectedItemDetails;
    }

    public void setSelectedItemDetails(ItemDetails selectedItemDetails) {
        this.selectedItemDetails = selectedItemDetails;
    }

    public List<RoomFacilities> getAllRoomFacilities() {
        return allRoomFacilities;
    }

    public void setAllRoomFacilities(List<RoomFacilities> allRoomFacilities) {
        this.allRoomFacilities = allRoomFacilities;
    }

    public RoomFacilities getSelectedRoomFacilityToUpdate() {
        return selectedRoomFacilityToUpdate;
    }

    public void setSelectedRoomFacilityToUpdate(RoomFacilities selectedRoomFacilityToUpdate) {
        this.selectedRoomFacilityToUpdate = selectedRoomFacilityToUpdate;
    }

    public List<RoomFacilities> getAllRoomFacilitiesHistory() {
        return allRoomFacilitiesHistory;
    }

    public void setAllRoomFacilitiesHistory(List<RoomFacilities> allRoomFacilitiesHistory) {
        this.allRoomFacilitiesHistory = allRoomFacilitiesHistory;
    }

    /**
     * Creates a new instance of RoomFacilitiesDAO
     */
    public RoomFacilitiesDAO() {
        this.allCategories = new ArrayList<Icategory>();
        this.allItemDetails = new ArrayList<ItemDetails>();
        this.allrooms = new ArrayList<Rooms>();
        this.allRoomFacilities = new ArrayList<RoomFacilities>();
        this.allRoomFacilitiesHistory = new ArrayList<RoomFacilities>();
        this.selectedRoomFacilities = new RoomFacilities();
        this.selectedRoom = new Rooms();
        this.selectedCategory = new Icategory();
        this.selectedItemDetails = new ItemDetails();
        this.selectedRoomFacilityToUpdate = new RoomFacilities();

        System.out.println(" New RoomFacilitiesDAO is created......");
    }

    public void retrieveAllCategories() {
        Session session = null;
        Transaction trx = null;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());

        Query q = null;
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {

                this.allCategories.clear();
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                String hqlQuery = "from Icategory";
                q = session.createQuery(hqlQuery);
                this.allCategories.addAll(q.list());
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

    public List<Rooms> completeRoom(String room) {
        Session session = null;
        Transaction trx = null;
        Query q = null;
        this.allrooms.clear();
        try {
            if (room != null || !room.equals("")) {
                List<Rooms> suggestions = new ArrayList<Rooms>();
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                String hqlQuery = "from Rooms";
                q = session.createQuery(hqlQuery);
                this.allrooms.addAll(q.list());
                trx.commit();
                for (Rooms r : allrooms) {
                    if ((r.getName().toLowerCase()).startsWith(room.toLowerCase())) {
                        suggestions.add(r);
                    }
                }
                return suggestions;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;

        }

    }

    public List<ItemDetails> completeItemDetails(String itemDetails) {
        Session session = null;
        Transaction trx = null;
        Query q = null;
        Criteria cr = null;
        this.allItemDetails.clear();
        try {
            if (itemDetails != null || !itemDetails.equals("")) {
                List<ItemDetails> suggestions = new ArrayList<ItemDetails>();
                session = HibernateUtil.getSession();
                trx = session.beginTransaction();
                q = session.createQuery("select roomfacilities.itemdetails from RoomFacilities roomfacilities where roomfacilities.endDate is null");

                List<ItemDetails> itemDetailsList = new ArrayList<ItemDetails>();
                itemDetailsList.addAll(q.list());
                List<Integer> id = new ArrayList<Integer>();
                for (int i = 0; i < itemDetailsList.size(); i++) {
                    id.add(itemDetailsList.get(i).getId());
                }
                if (id.isEmpty()) {
                    cr = session.createCriteria(ItemDetails.class, "idet")
                            .createAlias("idet.item", "item", JoinType.INNER_JOIN)
                            .createAlias("item.itype", "itype", JoinType.INNER_JOIN)
                            .createAlias("item.imake", "imake", JoinType.INNER_JOIN)
                            .createAlias("itype.icategory", "icategory", JoinType.INNER_JOIN)
                            .add(Restrictions.eq("icategory.id", this.selectedCategory.getId()));
                } else {
                    cr = session.createCriteria(ItemDetails.class, "idet")
                            .createAlias("idet.item", "item", JoinType.INNER_JOIN)
                            .createAlias("item.itype", "itype", JoinType.INNER_JOIN)
                            .createAlias("item.imake", "imake", JoinType.INNER_JOIN)
                            .createAlias("itype.icategory", "icategory", JoinType.INNER_JOIN)
                            .add(Restrictions.eq("icategory.id", this.selectedCategory.getId()))
                            .add(Restrictions.not(Restrictions.in("idet.id", id)));
                }

                this.allItemDetails.addAll(cr.list());
                trx.commit();
                for (ItemDetails idetails : allItemDetails) {
                    if (((idetails.getCode().toLowerCase()).startsWith(itemDetails.toLowerCase()))
                            || (idetails.getItem().getModel().toLowerCase()).startsWith(itemDetails.toLowerCase())
                            || (idetails.getItem().getItype().getName().toLowerCase()).startsWith(itemDetails.toLowerCase())
                            || (idetails.getItem().getImake().getName().toLowerCase()).startsWith(itemDetails.toLowerCase())) {
                        suggestions.add(idetails);
                    }
                }
                return suggestions;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;

        }
    }

    public void addRoomFacilities() {
        Session session = null;
        Transaction trx = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        try {
            if (this.getSelectedRoom() == null) {
                String message = text.getString("ui.Bean.selectRoomRequired");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update(":frmaddRoomFacilities:growlmsg");
            } else if (this.getSelectedItemDetails() == null) {
                String message = text.getString("ui.Bean.selectItemDetailsRequired");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update(":frmaddRoomFacilities:growlmsg");
            } else {

                session = HibernateUtil.getSession();
                trx = session.beginTransaction();

                Login loginDAO = (Login) context.getApplication().createValueBinding("#{loginbean}").getValue(context);

                int userId = loginDAO.getLoggedinUser().getId();
                this.selectedRoomFacilities.setCreatedby(userId);
                Date date = new Date();
                this.selectedRoomFacilities.setCreatedon(new Timestamp(date.getTime()));
                this.selectedRoomFacilities.setRooms(this.getSelectedRoom());
                this.selectedRoomFacilities.setItemdetails(this.getSelectedItemDetails());

                session.save(this.selectedRoomFacilities);
                trx.commit();
                this.retrieveRoomFacilities();

                String message = text.getString("ui.Bean.roomFacilityAddSuccess");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update(":frmaddRoomFacilities:growlmsg");
                this.setSelectedRoomFacilities(null);
                this.setSelectedRoomFacilities(new RoomFacilities());
                this.setSelectedCategory(null);
                this.setSelectedCategory(new Icategory());
                this.setSelectedItemDetails(null);
                this.setSelectedItemDetails(new ItemDetails());
                this.setSelectedRoom(null);
                this.setSelectedRoom(new Rooms());
            }


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

    public void retrieveRoomFacilities() {
        Session session = null;
        Transaction trx = null;

        Query q = null;
        Criteria cr = null;

        this.allRoomFacilities.clear();
        try {
            session = HibernateUtil.getSession();
            trx = session.beginTransaction();
            cr = session.createCriteria(RoomFacilities.class, "roomfacilities")
                    .createAlias("roomfacilities.itemdetails", "itemdetails", JoinType.INNER_JOIN)
                    .add(Restrictions.isNull("roomfacilities.endDate"));
//            String hqlQuery = "from RoomFacilities";
//            q = session.createQuery(hqlQuery);

            this.allRoomFacilities.addAll(cr.list());
            trx.commit();

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

    public void updateRoomFacilities() {
        Session session = null;
        Transaction trx = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("employees", context.getViewRoot().getLocale());
        try {
            if (this.selectedRoomFacilityToUpdate.getEndDate() == null) {
                String message = text.getString("ui.Bean.EndDate ");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update(":frmaddRoomFacilities:growlmsg");
            } else if (this.selectedRoomFacilityToUpdate.getEndReason() == null) {
                String message = text.getString("ui.Bean.EndReason");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                RequestContext req = RequestContext.getCurrentInstance();
                req.update(":frmaddRoomFacilities:growlmsg");
            } else {
                Date current_date = new Date();
                if (this.selectedRoomFacilityToUpdate.getEndDate().compareTo(this.selectedRoomFacilityToUpdate.getStartDate()) >= 0) {
                    if (this.selectedRoomFacilityToUpdate.getEndDate().compareTo(current_date) <= 0) {
                        session = HibernateUtil.getSession();
                        trx = session.beginTransaction();
                        session.update(this.selectedRoomFacilityToUpdate);
                        trx.commit();
                        String message = text.getString("ui.Bean.roomFacilityUpdateSuccess");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, message));
                        RequestContext req = RequestContext.getCurrentInstance();
                        req.update(":frmaddRoomFacilities:growlmsg");
                    } else {
                        String message = text.getString("ui.Bean.EnddatelessthanCurrentDate");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                        RequestContext req = RequestContext.getCurrentInstance();
                        req.update(":frmaddRoomFacilities:growlmsg");

                    }
                } else {
                    String message = text.getString("ui.Bean.EnddateGreterThanStartDate");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                    RequestContext req = RequestContext.getCurrentInstance();
                    req.update(":frmaddRoomFacilities:growlmsg");

                }

            }
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

    public void retrieveAllRoomFacilitiesHistory() {
        Session session = null;
        Transaction trx = null;

        Criteria cr = null;
        this.allRoomFacilitiesHistory.clear();
        try {

            session = HibernateUtil.getSession();
            trx = session.beginTransaction();

            cr = session.createCriteria(RoomFacilities.class, "roomFacilities")
                    .createAlias("roomFacilities.rooms", "rooms", JoinType.INNER_JOIN)
                    .createAlias("roomFacilities.itemdetails", "itemdetails", JoinType.INNER_JOIN)
                    .createAlias("itemdetails.item", "item", JoinType.INNER_JOIN)
                    .add(Restrictions.isNotNull("roomFacilities.endDate"))
                    .addOrder(Order.asc("roomFacilities.startDate"));

            this.allRoomFacilitiesHistory.addAll(cr.list());

            trx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

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
}
