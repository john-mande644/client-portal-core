package com.owd.web.internal.warehouse.admin.boxcodes;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdBoxtypes;
import com.owd.hibernate.generated.OwdBoxtypesMethods;
import com.owd.hibernate.HibUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 3, 2007
 * Time: 4:24:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoxTypesDAOService {
private final static Logger log =  LogManager.getLogger();


    public static void updateBoxcode(OwdBoxtypes box) throws Exception
    {
        HibernateSession.currentSession().saveOrUpdate(box);
        if(box.getShipMethods().size()>0)
        {
            Iterator it = box.getShipMethods().iterator();
            while(it.hasNext())
            {
               HibernateSession.currentSession().saveOrUpdate((OwdBoxtypesMethods)it.next()); 
            }
        }
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
    }

    public static void insertBoxcode(OwdBoxtypes box)  throws Exception
    {
       HibernateSession.currentSession().saveOrUpdate(box);
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
    }

    public static void deleteBoxcode(Integer id) throws Exception
    {
        HibernateSession.currentSession().delete(HibernateSession.currentSession().load(OwdBoxtypes.class,id));
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
    }

    public static OwdBoxtypes getBoxcode(Integer id) throws Exception
    {
       return (OwdBoxtypes) HibernateSession.currentSession().load(OwdBoxtypes.class,id);
    }

    public static OwdBoxtypes getNewBoxcode() throws Exception
    {
        OwdBoxtypes box = new OwdBoxtypes();
        box.setShipMethods(new HashSet());
        box.setLocation("DC1");
       return box;
    }

     public static OwdBoxtypes getNewBoxcode(String location) throws Exception
    {
        OwdBoxtypes box = new OwdBoxtypes();
        box.setShipMethods(new HashSet());
        box.setLocation(location);
       return box;
    }

    public static List<OwdBoxtypes> getAllBoxcodes() throws Exception
    {
      return HibernateSession.currentSession().createQuery("from OwdBoxtypes").list();

    }

    public static List getAllBoxcodesForLocation(String facilityCode) throws Exception
    {
      return HibernateSession.currentSession().createQuery("from OwdBoxtypes as boxtype where boxtype.location=:code").setString("code",facilityCode).list();

    }


}
