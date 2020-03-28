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
@FacesConverter("maritalStatus")
public class MaritalStatusConverter implements Converter {
    
    
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
        if(value.charAt(1) == 'M'){
            return "Married";
        }
        else if(value.charAt(1) == 'U'){
            return "Unmarried";
        }
        return null;
        
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) throws ConverterException{
         
        if(value== null){
            return null;
        }
        if("M".equals(value.toString())){
            return "Married";
        }
        else if("U".equals(value.toString())){
            return "Unmarried";
        }
        return null;
    }

}