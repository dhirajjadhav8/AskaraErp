
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
@FacesValidator("validate")
public class CheckValidateForInput implements Validator{
    private  String  EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
			"(\\.[A-Za-z]{2,})$";
 
	private Pattern pattern;
	private Matcher matcher;
   public CheckValidateForInput(){
            pattern = Pattern.compile(EMAIL_PATTERN);
       }

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        
        if(value== null){
            FacesMessage msg = new FacesMessage("E-mail Required");
           msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    throw new ValidatorException(msg);
        }
        String length=(String) value;
        if(length.length()<4){
            FacesMessage msg = 
				new FacesMessage("E-mail validation failed.", 
						"E-mail must contains more than 4 charecter.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
        }
        else{
        matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
 
			FacesMessage msg = 
				new FacesMessage("E-mail validation failed.", 
						"Invalid E-mail format.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
       
                    }
            }
    
        }
}
