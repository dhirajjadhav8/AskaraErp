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
@FacesConverter("priority")
public class PriorityConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
         if (value == null) {
            return null;
        } else if (value == "L") {
            return "Low";
        } else if (value == "M") {
            return "Medium";
        }else if (value == "H") {
            return "High";
        }else if (value == "I") {
            return "Imadiate";
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null) {
            return null;
        } else if (value.toString().charAt(0) == 'L') {
            return "Low";
        } else if (value.toString().charAt(0) == 'M') {
            return "Medium";
        }else if (value.toString().charAt(0) == 'H') {
            return "High";
        }else if (value.toString().charAt(0) == 'I') {
            return "Imadiate";
        }
        return null;
    }
    
}
