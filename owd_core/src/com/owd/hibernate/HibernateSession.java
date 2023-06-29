package com.owd.hibernate;

import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.generated.OwdFacility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.mapping.Table;
import org.hibernate.service.ServiceRegistry;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 17, 2004
 * Time: 1:45:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateSession {
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
        PreparedStatement ps = null;
        try
        {
            ps =  ((SessionImplementor)currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(query);
        }catch(Exception sql)
        {
           HibernateSession.closeSession();
           ps =  ((SessionImplementor)currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(query);
        }
        preparedStatement.set(ps);
        return ps;


    }

    public static void main(String[] args)
    {
        try
        {
             List<OwdFacility>  facilityList = (List<OwdFacility>)(HibernateSession.currentSession().createQuery("from OwdFacility where isActive=1").list());


        HibernateSession.closeSession();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public static StatelessSession getStatelessSession() throws Exception
    {
        if(sessionFactory==null)
        {
            Session sess = HibernateSession.currentSession();
        }

        return sessionFactory.openStatelessSession();
    }

    public static Session currentSession()
            throws Exception {


         //log.debug("Get Current HSession");
        Session s = (Session) session.get();
        if (s == null) {

            // Don't get from JNDI, use a static SessionFactory
            if (sessionFactory == null) {

                // Use default hibernate.cfg.xml
                // sessionFactory = new Configuration().configure().buildSessionFactory();
                Configuration hbmConfig = null;
                Properties hibernateProperties = new Properties();
                //hibernateProperties.setProperty("hibernate.connection.driver_class", com.owd.hibernate.HibUtils.owdDriverClass);
                //hibernateProperties.setProperty("hibernate.connection.url", "jdbc:"+com.owd.hibernate.HibUtils.owdLoggingDriver+"jtds:sqlserver://172.16.2.254/OWD");
                //hibernateProperties.setProperty("hibernate.connection.username", "sa");
                //hibernateProperties.setProperty("hibernate.connection.password", "wahoo");
                hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
                hibernateProperties.setProperty("hibernate.show_sql", "false");
                hibernateProperties.setProperty("hibernate.use_outer_join", "true");
                hibernateProperties.setProperty("hibernate.connection.characterEncoding", "utf8");
                        //  hibernateProperties.setProperty("hibernate.c3p0.max_size", "100");
              //  hibernateProperties.setProperty("hibernate.c3p0.min_size", "2");
             //   hibernateProperties.setProperty("hibernate.c3p0.timeout", "300");
                //hibernateProperties.setProperty("hibernate.c3p0.max_statements", "0");
                //hibernateProperties.setProperty("hibernate.c3p0.validate", "true");
                //        hibernateProperties.setProperty("hibernate.cglib.use_reflection_optimizer","false"); //needs to be in -D params

      //  log.debug("getting path...");
                // String fullPath = HibernateSession.class.getResource("./hibernate/OwdOrder.hbm.xml" ).getFile();


                log.debug(Thread.currentThread());
                log.debug(Thread.currentThread().getContextClassLoader());
                String fullPath = null;

                try{
                    URL classesRootDir = HibernateSession.class.getProtectionDomain().getCodeSource().getLocation();
                    log.debug(classesRootDir.getPath());
                    fullPath = HibernateSession.class.getResource("xml/Asn.hbm.xml").getFile();
                    log.debug("HBM PATH:"+fullPath);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    try {
                        fullPath = Thread.currentThread().getContextClassLoader().getResource("Asn.hbm.xml").getFile();
                        log.debug("HBM PATH2:"+fullPath);

                    } catch (Exception e) {
                         e.printStackTrace();
                        try {
                            fullPath = Thread.currentThread().getContextClassLoader().getResource("corehibernatemappings/").getFile();
                            log.debug("HBM PATH3:"+fullPath);

                        } catch (Exception e2) {
                             e2.printStackTrace();
                            fullPath = Thread.currentThread().getContextClassLoader().getResource("hibernate/").getFile();
                            log.debug("HBM PATH4:"+fullPath);

                        }
                    }
                }
                //
                hbmConfig = new Configuration();
                try
                {
                log.debug("FOUND:"+fullPath);

                if (fullPath.contains("WEB-INF"))  {

                   String jarPath = fullPath.substring(0, fullPath.indexOf("WEB-INF"));


                   log.debug(jarPath.substring(jarPath.indexOf(":") + 1) + "WEB-INF/hibernate/");
                    hbmConfig = new Configuration().addJar(new File("**/owdcore.jar")).setProperties(hibernateProperties);
                  // hbmConfig = new Configuration().addDirectory(new File(jarPath.substring(jarPath.indexOf(":") + 1) + "WEB-INF/hibernate/"))
                 //          .setProperties(hibernateProperties);

                } else if (fullPath.contains("Asn.hbm.xml")) {
                    log.debug(fullPath.replaceAll("%20"," ").replaceAll("Asn.hbm.xml", ""));

                    //Set this line for jasperserver on xp machine. Make sure you copy the xml files to that directory long day work around

                  /*  if((new File("C:/xml/").exists())) {
                        hbmConfig = new Configuration().addDirectory(new File("/C:/xml/"))
                                .setProperties(hibernateProperties);
                    }else{
                         hbmConfig = new Configuration().addDirectory(new File(fullPath.replaceAll("%20"," ").replaceAll("Asn.hbm.xml","")))
                            .setProperties(hibernateProperties);
                    }*/

                    hbmConfig = new Configuration().addDirectory(new File(fullPath.replaceAll("%20"," ").replaceAll("Asn.hbm.xml","")))
                            .setProperties(hibernateProperties);


                } else
                {
                  //  log.debug("jar path:"+new File(fullPath));
                    throw new Exception("look in jar");
                }


                }catch(Exception ex)
                {
                  //  ex.printStackTrace();
                    try
                    {
                   // log.debug("adding jar:"+fullPath.substring(fullPath.indexOf(":")+1,fullPath.indexOf("!")>0?fullPath.indexOf("!"):9999).replaceAll("%20"," "));

                                   hbmConfig.addJar(new File(fullPath.substring(fullPath.indexOf(":")+1,fullPath.indexOf("!")>0?fullPath.indexOf("!"):9999).replaceAll("%20"," "))).setProperties(hibernateProperties);
                    }catch (Exception jarex)
                    {
                        hbmConfig = new Configuration().addDirectory(new File(fullPath.replaceAll("%20"," ").replaceAll("Asn.hbm.xml","")))
                            .setProperties(hibernateProperties);
                    }
                }
                hbmConfig.setProperty(Environment.CONNECTION_PROVIDER, "com.owd.hibernate.MyConnectionProvider");
                hbmConfig.setProperty("hibernate.owd.dbname", "OWD");

              //  hbmConfig.registerTypeOverride(new UtfVarcharType());

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                        hbmConfig.getProperties()).build();



                      sessionFactory = hbmConfig.buildSessionFactory(serviceRegistry);
                log.debug("Mappings!");
                Iterator<Table> it = hbmConfig.getTableMappings();
                while(it.hasNext())
                {
                    log.debug('*'+it.next().getName());
                }
            }


            s = sessionFactory.openSession();

            session.set(s);

             ((SessionImplementor)s).getJdbcConnectionAccess().obtainConnection().setAutoCommit(false);

            s.getTransaction().begin();

        }

        if( ((SessionImplementor)s).getJdbcConnectionAccess().obtainConnection().isClosed())
        {
//          s = sessionFactory.openSession(ConnectionManager.getSpecifiedConnection(ConnectionManager.owdDriverClass,
//                    "jdbc:"+ ConnectionManager.owdLoggingDriver+"jtds:sqlserver://172.16.2.254/OWD","sa","wahoo","production-hibernate",testerSQL));
         //   s = sessionFactory.openSession(ConnectionManager.getSpecifiedConnection("OWD"));
            SessionBuilder sb = sessionFactory.withOptions();
            s = sb.connection(ConnectionManager.getSpecifiedConnection("OWD")).openSession();

            session.set(s);
             ((SessionImplementor)s).getJdbcConnectionAccess().obtainConnection().setAutoCommit(false);
            s.getTransaction().begin();
        }

        return s;
    }
    static String testerSQL = "SELECT COUNT(*) FROM OWD.dbo.pooltester";

    public static void closeStatement() {

        try {
            Statement s = (Statement) statement.get();
            statement.set(null);
            if (s != null) s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void closePreparedStatement() {

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
