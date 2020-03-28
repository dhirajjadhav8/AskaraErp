
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author dhirajj
 */
@FacesValidator("validateForPassword")
public class CheckValidaterForPassword implements Validator {
    
    private  String  Password = "^[A-Z]{1}+[A-Za-z0-9]$";
 
	private Pattern pattern;
	private Matcher matcher;  
    
     public CheckValidaterForPassword(){
            pattern = Pattern.compile(Password);
       }

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        if(value== null){
            FacesMessage msg = new FacesMessage("Password Required");
           msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    throw new ValidatorException(msg);
        }
        String length=(String) value;
        if(length.length()<8){
            FacesMessage msg = 
				new FacesMessage("Password validation failed.", 
						"Password must contains more than 8 charecter.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
        }
        else{
        matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
 
			FacesMessage msg = 
				new FacesMessage("Password validation failed.", 
						"Invalid Password format.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
       
                    }
            }
    }
}
