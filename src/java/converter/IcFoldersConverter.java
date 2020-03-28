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
import orm.IcFolders;
import util.HibernateUtil;

/**
 *
 * @author dhirajj
 */
@FacesConverter("folders")
public class IcFoldersConverter implements Converter {

    private List<IcFolders> allIcFolders;

    public void retrieveList() {

        try {
            allIcFolders = new ArrayList<IcFolders>();


            Session session = HibernateUtil.getSession();
            Transaction trx = session.beginTransaction();

            Query q = session.createQuery("from IcFolders");

            allIcFolders.addAll(q.list());
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

                for (IcFolders icf : allIcFolders) {
                    if (icf.getId() == id) {
                        return icf;
                    }
                }

            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Folder"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value.toString() == null || value.toString().equals("")) {
            return "";
        } else {
            return String.valueOf(((IcFolders) value).getId());
        }
    }
}
