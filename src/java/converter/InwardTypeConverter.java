/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author dhirajj
 */
@FacesConverter("Type")
public class InwardTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) throws ConverterException {

        if (value == null) {
            return null;
        } else if ("I".equals(value)) {
            return "Item";
        } else if ("D".equals(value)) {
            return "Document";
        }
        return null;
    }

    /**
     *
     * @param fc
     * @param uic
     * @param value
     * @return
     * @throws ConverterException
     */
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) throws ConverterException {
        if (value == null) {
            return null;
        } else if ("I".equals(value.toString())) {
            return "Item";
        } else if ("D".equals(value.toString())) {
            return "Document";
        }
        return null;
    }
}
