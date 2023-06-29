package com.owd.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.service.ServiceRegistry;

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
public class HibernateBoxworksSession {
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
                Configuration hbmConfig = null;
                Properties hibernateProperties = new Properties();
               // hibernateProperties.setProperty("hibernate.connection.driver_class", com.owd.hibernate.HibUtils.owdDriverClass);
               // hibernateProperties.setProperty("hibernate.connection.url", "jdbc:"+com.owd.hibernate.HibUtils.owdLoggingDriver+"jtds:sqlserver://172.16.2.254/lgx_adhoc");
               // hibernateProperties.setProperty("hibernate.connection.username", "sa");
               // hibernateProperties.setProperty("hibernate.connection.password", "wahoo");
                hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
                hibernateProperties.setProperty("hibernate.show_sql", "true");
                hibernateProperties.setProperty("hibernate.use_outer_join", "true");
              //  hibernateProperties.setProperty("hibernate.c3p0.max_size", "20");
              //  hibernateProperties.setProperty("hibernate.c3p0.min_size", "2");
              //  hibernateProperties.setProperty("hibernate.c3p0.timeout", "5000");
              //  hibernateProperties.setProperty("hibernate.c3p0.max_statements", "100");
              //  hibernateProperties.setProperty("hibernate.c3p0.validate", "true");
                //        hibernateProperties.setProperty("hibernate.cglib.use_reflection_optimizer","false"); //needs to be in -D params

                hbmConfig = new Configuration();
                hibernateProperties.setProperty(Environment.CONNECTION_PROVIDER, "com.owd.hibernate.MyConnectionProvider");
                hibernateProperties.setProperty("hibernate.owd.dbname", "Boxworks");
                hbmConfig.setProperties(hibernateProperties);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                        hbmConfig.getProperties()).build();

                sessionFactory = hbmConfig.buildSessionFactory(serviceRegistry);

            }


            s = sessionFactory.withOptions().openSession();

            session.set(s);

            ((SessionImplementor)s).getJdbcConnectionAccess().obtainConnection().setAutoCommit(false);

            s.getTransaction().begin();
        }
        return s;
    }

    static String testerSQL = "SELECT COUNT(*) FROM wms.dbo.company";
  

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
                try
               {
                   if( ((SessionImplementor)s).getJdbcConnectionAccess().obtainConnection() != null)
                   {
                        ((SessionImplementor)s).getJdbcConnectionAccess().obtainConnection().rollback();
                   }
                  } catch (Exception ex) {
            }    try
               {
                   if(s != null)
                   {
                       s.getTransaction().rollback();
                   }
                  } catch (Exception ex) {
            }
               try
               {
                   if( ((SessionImplementor)s).getJdbcConnectionAccess().obtainConnection() != null)
                   {
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
