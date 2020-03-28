/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import orm.Contacts;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@FacesConverter("contacts")
public class ContactsConverter implements Converter {

    public static List<Contacts> allContacts;

    public void retrieveList() {

        try {
            allContacts = new ArrayList<Contacts>();

        
            Session session = HibernateUtil.getSession();
            Transaction trx = session.beginTransaction();

            Query q = session.createQuery("from Contacts");

            allContacts.addAll(q.list());
            trx.commit();
        } catch (Exception e) {
        }

    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {        this.retrieveList();
        if (value.trim().equals("")) {
            return "";
        } else {
            try {
               int id = Integer.parseInt(value);

                for (Contacts c : allContacts) {
                    if (c.getId() == id) {  
                        return c;  
                    } 
                }

            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid contact"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
      //  this.retrieveList();
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Contacts) value).getId());
        }
    }
}
