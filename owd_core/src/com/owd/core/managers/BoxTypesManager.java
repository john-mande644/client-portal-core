package com.owd.core.managers;

import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 4, 2007
 * Time: 11:50:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class BoxTypesManager {
private final static Logger log =  LogManager.getLogger();


    public static List getBoxTypeMethodList(String boxID) throws Exception
    {
         List methods = new ArrayList();
         try
         {
             ResultSet rs = HibernateSession.getResultSet("select td_reference from owd_boxtypes_methods where owd_boxtypes_fkey="+boxID);
             if(rs != null)
             {
             while(rs.next())
             {
                 methods.add(rs.getString(1));
             }
             }

         }   catch(Exception ex )
         {
             throw ex;
         }            finally
         {
             // HibernateSession.closeSession();
         }

        return methods;


    }

    public static void main(String[] args) throws Exception
    {
        log.debug(getBoxTypeMethodList("4"));
    }
}
