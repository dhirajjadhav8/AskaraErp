
import java.util.StringTokenizer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dhirajj
 */
@FacesConverter("display")
public class CheckConvertersForLists implements Converter{

     
    
    String list="";
   
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) throws ConverterException {
        
         System.out.println("11111111111111111111");
        if(value== null){
            return null;
        }
        else{
       
             System.out.println("2222222222222222222");
            System.out.println(value);
            String values = value;
            String str= values.replaceAll(" ","");
            int num= str.length();
            System.out.println(num);
            String val=str.substring(1, num-1);
            System.out.println(val);
            StringTokenizer st = new StringTokenizer(val, ",");
            while(st.hasMoreElements()){
               String lists=(String) st.nextElement();
                if(lists==null){
                    break;
                }
                else{
                list=list+lists+":";
                }
                
            }
            System.out.println(list);
            return list;
        }
        
       
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) throws ConverterException {
         System.out.println("33333333333333333333");
        if(value== null){
            return null;
        }
        else{
             System.out.println("4444444444444444444444444");
            System.out.println(value);
            String values = (String) value;
            System.out.println("55555555555555555555555555");
            String str=values.replaceAll(" ","");
             int num= str.length();
              System.out.println("666666666666666666666666666");
               System.out.println(num);
            String val=str.substring(1, num-1);
            System.out.println(val);
            
            StringTokenizer st = new StringTokenizer(val, ",");
            while(st.hasMoreElements()){
              String  lists=(String) st.nextElement();
                 if(lists==null){
                    break;
                }
                list=list+lists+":";
                System.out.println(lists);
            }
            System.out.println(list);
            return list;
        }
        
       
      
    }
    
}
