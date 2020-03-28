
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dhirajj
 */
@FacesValidator("NumberValidater")
public class CheckValidaterForNumber implements Validator{
     private String numeric="^[0-9]{6}$";
    private Pattern pattern;
    private Matcher matcher;
    public CheckValidaterForNumber(){
        pattern=Pattern.compile(numeric);
    }

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
       if(value==null){
            FacesMessage msg = new FacesMessage("PostalCode is required. ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        String values=(String) value;
        if(values.length()<6 && values.length()>6)
        {
            FacesMessage msg = new FacesMessage("PostalCode field required 6 numbers ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        else{
            matcher=pattern.matcher(value.toString());
            
            if(!matcher.matches()){
                
                FacesMessage msg= new FacesMessage("Insert only Numbers.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
            
        }
    }
    
}
