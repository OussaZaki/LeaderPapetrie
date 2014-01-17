package sql.model;

import org.hibernate.Session;
import sql.HibernateUtil;

/**
 *
 * @author Oussama
 */
public class mySession {

    public static Session session;

    public static void setSession() {
        mySession.session = HibernateUtil.getSessionFactory().openSession();
        mySession.session.beginTransaction();
    }
    
    public static Session getSession() {
        return session;
    }
    
}
