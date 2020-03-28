
import java.text.SimpleDateFormat;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dhirajj
 */
@FacesConverter("date")
public class CheckConverterForDate implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value== null){
            return null;
        }
        else{
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
            String val = sdf.format(value);
            System.out.println(val);          
            return (String) val;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if(value== null){
            return null;
        }
        else{
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
            String val = sdf.format(value);
               System.out.println(val);          
            return (String) val;
        }
    }
    
}
