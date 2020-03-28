/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.Login;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dhirajj
 */
@ManagedBean
@ApplicationScoped
public class ForceSessionCreateA implements Serializable {

    int q = 0;
    
   private String separator;

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
          

    /**
     * Creates a new instance of ForceSessionCreateA
     */
    public ForceSessionCreateA() {
        
        this.setSeparator(System.getProperty("file.separator"));
    }

    public void preRenderView() {

        try {

            FacesContext ctx = FacesContext.getCurrentInstance();
            ExternalContext ectx = ctx.getExternalContext();

            if (q <= 1) {

                Login l = (Login) ectx.getSessionMap().get("loginbean");

                if (l != null) {
                    if (l.isLoggedin() == false) {
                        q++;

                        if (q == 1) {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
                            return;
                        }
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
                        return;
                    }
                } else {
                    return;
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            return;
        }

    }
}
