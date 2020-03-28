
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
@FacesValidator("NameValidater")
public class CheckValidaterForName implements Validator{

    private String Name ="^[a-zA-z [\\s]]{3,255}$";
    private Pattern pattern;
	private Matcher matcher;
    
    public CheckValidaterForName(){
        pattern=Pattern.compile(Name);
        
    }
    
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        
        if(value==null){
            FacesMessage msg = new FacesMessage("Name is required. ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        String values=(String) value;
        if(values.length()<=3 && values.length()>255)
        {
            FacesMessage msg = new FacesMessage("Name field is having more than 2  and less than 255 charecters. ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        else{
            matcher=pattern.matcher(value.toString());
            
            if(!matcher.matches()){
                
                FacesMessage msg= new FacesMessage("Insert only Alphabets.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
            
        }
    }
    
}
