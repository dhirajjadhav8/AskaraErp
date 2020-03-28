/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 *
 * @author dhirajj
 */
public class HibernateAppServletContextListener implements ServletContextListener{
   
    public HibernateAppServletContextListener() {
    }

    
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
      HibernateUtil.getSessionFactory();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.getSessionFactory().close();
    }
    
}
