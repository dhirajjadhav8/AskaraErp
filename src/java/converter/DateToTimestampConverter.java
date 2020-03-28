/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author dhirajj
 */
@FacesConverter("dateToTimestamp")
public class DateToTimestampConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            SimpleDateFormat strDate = new SimpleDateFormat("yyyy-MM-dd");
            Date datetoformat = strDate.parse(string);
            Timestamp temp = new Timestamp(datetoformat.getTime());
            return temp;
        } catch (ParseException ex) {
            Logger.getLogger(DateToTimestampConverter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o.equals("") || o == null) {
            return null;
        } else {
            return o.toString();
        }
    }
}
