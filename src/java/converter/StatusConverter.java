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
@FacesConverter("status")
public class StatusConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null) {
            return null;
        } else if (value == "O") {
            return "Open";
        } else if (value == "C") {
            return "Closed";
        }else if (value == "R") {
            return "Rejected";
        }else if (value == "I") {
            return "Inprocess";
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null) {
            return null;
        } else if (value.toString().charAt(0) == 'O') {
            return "Open";
        } else if (value.toString().charAt(0) == 'C') {
            return "Closed";
        }else if (value.toString().charAt(0) == 'R') {
            return "Rejected";
        }else if (value.toString().charAt(0) == 'I') {
            return "Inprocess";
        }
        return null;
    }
}
