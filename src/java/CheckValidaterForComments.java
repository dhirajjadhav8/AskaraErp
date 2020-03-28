
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
@FacesValidator("CommentsValidater")
public class CheckValidaterForComments implements Validator{

    private String alphaMueric="^[a-zA-Z0-9]{10,100}$";
    private Pattern pattern;
    private Matcher matcher;

    public CheckValidaterForComments(){
        pattern=Pattern.compile(alphaMueric);
    }
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {

        if(value==null){
            FacesMessage msg = new FacesMessage("Comments must required. ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        String values = (String) value;
        if(values.length()<10 && values.length()>100){
            FacesMessage msg = new FacesMessage("Comments having chrecter range 10 to 100. ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
            
        }
        else{
            matcher=pattern.matcher(value.toString());
            if(!matcher.matches()){
                FacesMessage msg = new FacesMessage("Enter only alphnumeric values.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
        
    }
    
}
