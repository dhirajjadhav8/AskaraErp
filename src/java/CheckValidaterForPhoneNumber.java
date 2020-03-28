
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
@FacesValidator("PhoneNoValidater")
public class CheckValidaterForPhoneNumber implements Validator{

     private  String  PHONE_NO ="^(\\+91[\\s]{0,1})[\\-][\\s]{0,1}[0-9]{4}[\\-][\\s]{0,1}[0-9]{6}$";
 
	private Pattern pattern;
	private Matcher matcher;
   public CheckValidaterForPhoneNumber(){
            pattern = Pattern.compile(PHONE_NO);
       }
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        
        if(value==null){
            FacesMessage msg = new FacesMessage("Field must required. ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
         else{
            matcher=pattern.matcher(value.toString());
            if(!matcher.matches()){
                FacesMessage msg = new FacesMessage("Invalid Format.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
      
    }
    
}
}
