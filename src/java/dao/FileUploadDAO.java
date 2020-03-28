/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author dhirajj
 */
@ManagedBean (name="fileUploadDAO")
@ViewScoped
public class FileUploadDAO implements Serializable{

    /**
     * Creates a new instance of FileUploadDAO
     */
    
    private UploadedFile file;
    String strName = null;
    private int  BUFFER_SIZE=4096;
    public FileUploadDAO() {
        
         System.out.println(" New FieUploadDAO is created......");
        
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

       
    public void upload() {  
        if(file != null) {  
            FacesMessage msg = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");  
            FacesContext.getCurrentInstance().addMessage(null, msg);  
        }  
    }  
    public void handleFileUpload(FileUploadEvent event) {
     
        
    
        
        File result = new File("/home/dhirajj/work/primefaces/temp/" + event.getFile().getFileName());
        
        
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(result);

                byte[] buffer = new byte[BUFFER_SIZE];

                int bulk;
                InputStream inputStream = event.getFile().getInputstream();
                while (true) {
                    bulk = inputStream.read(buffer);
                    if (bulk < 0) {
                        break;
                    }
                    fileOutputStream.write(buffer, 0, bulk);
                    fileOutputStream.flush();
                }

                fileOutputStream.close();
                inputStream.close();
                
               FacesMessage msg = 
                            new FacesMessage("File Description"+ "file name: " +
                            event.getFile().getFileName() + " \n file size: " + 
                            event.getFile().getSize() / 1024 + 
                            " Kb \n content type: " + 
                            event.getFile().getContentType() + 
                                    " The file was uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                
      

            } catch (Exception e) {
                e.printStackTrace();

                FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                               "The files were not uploaded!", "");
                FacesContext.getCurrentInstance().addMessage(null, error);
            }       
    }   
    
     public void chaneFileUploadName( FileUploadEvent event) { 
          String strName = event.getFile().getFileName();
          event.getFile().getFileName().replaceAll(strName, "1_"+strName);
         
     }

    private void updateFileName() {
        
       
        
        File file1 = new File("/home/dhirajj/work/primefaces/temp/"+strName);
        File file2 = new File("/tmp/test/star_"+strName);
       
        file1.renameTo(file2);
          
    }

   
    }
  


