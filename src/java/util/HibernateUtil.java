/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author dhirajj
 */
public final class HibernateUtil {

    public HibernateUtil() {
    }

    
    public static synchronized Session getSession()
        throws HibernateException
    {
        return sessionFactory.openSession();
    }

    public static synchronized StatelessSession getStatelessSession()
        throws HibernateException
    {
        return sessionFactory.openStatelessSession();
    }

    private static final SessionFactory sessionFactory;
    
    public static synchronized SessionFactory getSessionFactory() {
    return sessionFactory;

    }

    static 
    {
        try
        {
            Configuration cfg = new Configuration();
            sessionFactory = cfg.configure("hibernate.cfg.xml").buildSessionFactory();
        }
        catch(Throwable ex)
        {
            System.out.println(ex.getMessage());
            throw new ExceptionInInitializerError(ex);
            
        }
    }
}
    

