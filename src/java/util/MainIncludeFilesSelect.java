/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author dhirajj
 */
@ManagedBean(name="mainIncludeFilesSelect")
@ViewScoped
public class MainIncludeFilesSelect implements Serializable{
    
    Date currentDate;

    /**
     * Creates a new instance of MainIncludeFilesSelect
     */
    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    
    
    public MainIncludeFilesSelect() {
        this.currentDate = new Date();
    }
     private String includeFile="null";

    public String getIncludeFile() {
        return includeFile;
    }

    public void setIncludeFile(String includeFile) {
        this.includeFile = includeFile;
    }
    
    
}
