
import java.text.SimpleDateFormat;
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
@FacesValidator("date")
public class CheckValidaterforDate implements Validator{
    
    private String Date ="^dd-MM-yyyy$";
     private static final String DATE_PATTERN = "((19|20)\\d\\d)-(0?[1-9]|1[012])-((0?[1-9]|[12][0-9]|3[01]))";
    private Pattern pattern;
	private Matcher matcher;

    public CheckValidaterforDate() {
        pattern = Pattern.compile(DATE_PATTERN);
    }
        
        
        
        

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        
        System.out.println(value);
        
         if(value==null){
             System.out.println("111111111111111111111111111");
            FacesMessage msg = new FacesMessage("Input is required. ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
         
         else{
             
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             System.out.println("222222222222222222222");
             value = sdf.format(value);
             System.out.println(value);
            matcher=pattern.matcher(value.toString());
            
            if(value.toString()==null){
                System.out.println("33333333333333333333333333");
            FacesMessage msg = new FacesMessage("Input is required. ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
            
            else if(!matcher.matches()){
                System.out.println("4444444444444444444444444444444");
                FacesMessage msg= new FacesMessage("Insert correct date format.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
            
        }
    }
    
}
