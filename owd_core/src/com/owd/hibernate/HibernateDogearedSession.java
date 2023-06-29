package com.owd.hibernate;

import com.owd.core.managers.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 17, 2004
 * Time: 1:45:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateDogearedSession {
private final static Logger log =  LogManager.getLogger();

    private static SessionFactory sessionFactory;
    public static final ThreadLocal session = new ThreadLocal();


    public static final ThreadLocal statement = new ThreadLocal();
    public static final ThreadLocal preparedStatement = new ThreadLocal();

    public static ResultSet getResultSet(Session session, String query)
            throws Exception {

        closeStatement();
        Statement stmt =  ((SessionImplementor)session).getJdbcConnectionAccess().obtainConnection().createStatement();
        statement.set(stmt);
        return stmt.executeQuery(query);

    }

    public static ResultSet getResultSet(String query)
            throws Exception {

        closeStatement();
        Statement stmt =  ((SessionImplementor)currentSession()).getJdbcConnectionAccess().obtainConnection().createStatement();
        statement.set(stmt);
        return stmt.executeQuery(query);

    }
    public static PreparedStatement getPreparedStatement(String query)
            throws Exception {

        closePreparedStatement();
        PreparedStatement ps =  ((SessionImplementor)currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(query);
        preparedStatement.set(ps);
        return ps;


    }


    public static Session currentSession()
            throws Exception {

        Session s = (Session) session.get();
        if (s == null) {

            // Don't get from JNDI, use a static SessionFactory
            if (sessionFactory == null) {

                // Use default hibernate.cfg.xml
                // sessionFactory = new Configuration().configure().buildSessionFactory();

                Properties hibernateProperties = new Properties();
               // hibernateProperties.setProperty("hibernate.connection.driver_class", com.owd.hibernate.HibUtils.owdDriverClass);
              //  hibernateProperties.setProperty("hibernate.connection.url", "jdbc:"+com.owd.hibernate.HibUtils.owdLoggingDriver+"jtds:sqlserver://abweb2.internal.owd.com:4378/qqest");
              //  hibernateProperties.setProperty("hibernate.connection.username", "sa");
              //  hibernateProperties.setProperty("hibernate.connection.password", "dr8gedog");
                hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                hibernateProperties.setProperty("hibernate.show_sql", "true");
                hibernateProperties.setProperty("hibernate.use_outer_join", "true");
              //  hibernateProperties.setProperty("hibernate.c3p0.max_size", "20");
              //  hibernateProperties.setProperty("hibernate.c3p0.min_size", "2");
              //  hibernateProperties.setProperty("hibernate.c3p0.timeout", "300");
              //  hibernateProperties.setProperty("hibernate.c3p0.max_statements", "0");
              //  hibernateProperties.setProperty("hibernate.c3p0.validate", "true");
                //        hibernateProperties.setProperty("hibernate.cglib.use_reflection_optimizer","false"); //needs to be in -D params




                sessionFactory = new Configuration().setProperties(hibernateProperties).buildSessionFactory();

            }

       //     s = sessionFactory.openSession(ConnectionManager.getSpecifiedConnection("com.mysql.jdbc.Driver",
       //             "jdbc:mysql://mysql.db2.islandtechnologies.net:3306/dogeared_miva","dogowd","gold1wood#","Dogeared-Miva DB","select count(*) from s01_Orders"));



//            s = sessionFactory.openSession(ConnectionManager.getSpecifiedConnection("com.mysql.jdbc.Driver",
//                    "jdbc:mysql://mysql.db2.islandtechnologies.net:3306/dogeared_island_shop","dogowd","gold1wood#","Dogeared-Miva DB","select count(*) from dogeared_island_shop.orders"));
          //  s = sessionFactory.openSession(ConnectionManager.getSpecifiedConnection("Dogeared"));

            SessionBuilder sb = sessionFactory.withOptions();
            s = sb.connection(ConnectionManager.getSpecifiedConnection("Dogeared")).openSession();

            session.set(s);
             s.getTransaction().begin();
            PreparedStatement ps = HibernateDogearedSession.getPreparedStatement("set session wait_timeout=3700;");
             try {
                ps.executeUpdate();

                HibUtils.commit(HibernateDogearedSession.currentSession());
            } catch (Exception ex) {
              ex.printStackTrace();
            }
            HibernateDogearedSession.closePreparedStatement();
            ps = HibernateDogearedSession.getPreparedStatement("SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ;");
                         try {
                            ps.executeUpdate();

                            HibUtils.commit(HibernateDogearedSession.currentSession());
                        } catch (Exception ex) {
                          ex.printStackTrace();
                        }
                        HibernateDogearedSession.closePreparedStatement();



        }
        return s;
    }
    static String testerSQL = "select count(*) from dogeared_island_shop.orders";

    protected static void closeStatement() {

        try {
            Statement s = (Statement) statement.get();
            statement.set(null);
            if (s != null) s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected static void closePreparedStatement() {

        try {
            PreparedStatement s = (PreparedStatement) preparedStatement.get();
            preparedStatement.set(null);
            if (s != null) s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



   public static void closeSession() {
        //log.debug("Close HSession");
        closePreparedStatement();
        closeStatement();
        try {
            Session s = (Session) session.get();
            session.set(null);
            try {
                if ( ((SessionImplementor)s).getJdbcConnectionAccess().obtainConnection() != null) {
                     ((SessionImplementor)s).getJdbcConnectionAccess().obtainConnection().rollback();
                }
            } catch (Exception ex) {
            }
            try {
                if (s != null) {
                    s.getTransaction().rollback();
                }
            } catch (Exception ex) {
            }
            try {
                if ( ((SessionImplementor)s).getJdbcConnectionAccess().obtainConnection() != null) {
                     ((SessionImplementor)s).getJdbcConnectionAccess().obtainConnection().close();
                }
            } catch (Exception ex) {
            }
            if (s != null) s.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
