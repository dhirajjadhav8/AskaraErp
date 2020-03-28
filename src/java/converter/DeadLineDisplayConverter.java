/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author dhirajj
 */
@FacesConverter("deadlineDisplay")
public class DeadLineDisplayConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null) {
            return null;
        } else {
            try {
                //create SimpleDateFormat object with source string date format
                SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                //parse the string into Date object
                Date date = sdfSource.parse(value);

                //create SimpleDateFormat object with desired date format
                SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyy-MM-dd HH:mm a");

                //parse the date into another format
                value = sdfDestination.format(date);

                return value;

            } catch (Exception e) {
                e.getMessage();
            }


        }


        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null) {
            return null;
        } else {
            try {
                //create SimpleDateFormat object with source string date format
                SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                //parse the string into Date object
                Date date = sdfSource.parse(value.toString());

                //create SimpleDateFormat object with desired date format
                SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyy-MM-dd HH:mm a");

                //parse the date into another format
                value = sdfDestination.format(date);

                return value.toString();

            } catch (Exception e) {
                e.getMessage();
            }


        }


        return null;
    }
}
