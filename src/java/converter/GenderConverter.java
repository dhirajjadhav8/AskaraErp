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
@FacesConverter("gender")
public class GenderConverter implements Converter{

    /**
     *
     * @param fc
     * @param uic
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) throws ConverterException{
        
        if(value== null){
            return null;
        }
        else if(value.charAt(1) == 'M'){
            return "Male";
        }
        else if(value.charAt(1) == 'F'){
            return "Female";
        }
        return null;
        
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) throws ConverterException{
         
        if(value== null){
            return null;
        }
        else if(value.toString().charAt(1) == 'M'){
            return "Male";
        }
        else if(value.toString().charAt(1) == 'F'){
            return "Female";
        }
        return null;
    }
    
}
