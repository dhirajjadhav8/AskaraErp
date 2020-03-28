
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
@FacesValidator("NumberValidaterForOrderLevel")
public class CheckValidatorForReorderLevel implements Validator{
    
     private String numeric="^(0|[1-9][0-9]*)$";
    private Pattern pattern;
    private Matcher matcher;

    public CheckValidatorForReorderLevel() {
        pattern=Pattern.compile(numeric);
    }
    

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
       if(value==null){
            FacesMessage msg = new FacesMessage("ReorderLevel is required. ");
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
