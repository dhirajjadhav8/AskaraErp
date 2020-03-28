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
import orm.Employees;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@FacesConverter("employees")
public class EmployeesConverter implements Converter{
    
     public static List<Employees> allEmployees;

    public void retrieveList() {

        try {
            allEmployees = new ArrayList<Employees>();

        
            Session session = HibernateUtil.getSession();
            Transaction trx = session.beginTransaction();

            Query q = session.createQuery("from Employees");

            allEmployees.addAll(q.list());
            trx.commit();
        } catch (Exception e) {
        }

    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) { this.retrieveList();
        if (value.trim().equals("")) {
            return "";
        } else {
            try {
               int id = Integer.parseInt(value);

                for (Employees e : allEmployees) {
                    if (e.getId() == id) {  
                        return e;  
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
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Employees) value).getId());
        }
    }
    
    
    
}
