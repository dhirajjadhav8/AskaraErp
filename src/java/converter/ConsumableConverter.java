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
@FacesConverter("Consumable")
public class ConsumableConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null) {
            return null;
        } else if (value == "C") {
            return "Consumable";
        } else if (value == "N") {
            return "Nonconsumable";
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null) {
            return null;
        } else if (value.toString().charAt(0) == 'C') {
            return "Consumable";
        } else if (value.toString().charAt(0) == 'N') {
            return "Nonconsumable";
        }
        return null;
    }
}
