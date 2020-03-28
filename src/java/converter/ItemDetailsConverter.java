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
import orm.ItemDetails;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@FacesConverter("itemDetails")
public class ItemDetailsConverter implements Converter{
    
    public static List<ItemDetails> allItemDetails;

    public void retrieveList() {

        try {
            allItemDetails = new ArrayList<ItemDetails>();

        
            Session session = HibernateUtil.getSession();
            Transaction trx = session.beginTransaction();

            Query q = session.createQuery("from ItemDetails");

            allItemDetails.addAll(q.list());
            trx.commit();
        } catch (Exception e) {
        }

    }


    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        this.retrieveList();
        if (value.trim().equals("")) {
            return "";
        } else {
            try {
               int id = Integer.parseInt(value);

                for (ItemDetails idet : allItemDetails) {
                    if (idet.getId() == id) {  
                        return idet;  
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
            return String.valueOf(((ItemDetails) value).getId());
        }
    }
    
}
