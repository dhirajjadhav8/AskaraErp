/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author dhirajj
 */
@ManagedBean
@RequestScoped
public class fileBean {

    private StreamedContent file;

    /**
     * Creates a new instance of fileBean
     */
    public fileBean() {
        
        System.out.println(" New FIleBean is created......");

        FileInputStream stream;
        try {
            stream = new FileInputStream("/home/dhirajj/work/training/Projects/askara/build/web/resources/ed/1/1_6.pdf");
            
            this.file = new DefaultStreamedContent(stream, "application/pdf", "downloaded_file.pdf");
           
        } catch (FileNotFoundException ex) {
            Logger.getLogger(fileBean.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public StreamedContent getFile() {
        return file;
    }
}
