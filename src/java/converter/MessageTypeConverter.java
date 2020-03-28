/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author dhirajj
 */
@FacesConverter("messageType")
public class MessageTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null) {
            return null;
        } else if (value == "M") {
            return "Message";
        } else if (value == "R") {
            return "Request";
        } else if (value == "I") {
            return "Information";
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null) {
            return null;
        } else if (value.toString().charAt(0) == 'M') {
            return "Message";
        } else if (value.toString().charAt(0) == 'R') {
            return "Request";
        } else if (value.toString().charAt(0) == 'I') {
            return "Information";
        }
        return null;
    }
}
