package com.owd.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 31, 2007
 * Time: 3:15:04 PM
 * To change this template use File | Settings | File Templates.
 */
 public  class HibUtils {
private final static Logger log =  LogManager.getLogger();

         
    public static void commit(Session se) throws Exception
    {
        se.getTransaction().commit();

        try{ ((SessionImplementor)se).getJdbcConnectionAccess().obtainConnection().commit(); }catch(Exception ex){ex.printStackTrace();}
        se.beginTransaction();
    }
    public static void rollback(Session se) throws Exception
      {
          se.getTransaction().rollback();
        try{ ((SessionImplementor)se).getJdbcConnectionAccess().obtainConnection().rollback(); }catch(Exception ex){ex.printStackTrace();}
          se.beginTransaction();
      }
}
