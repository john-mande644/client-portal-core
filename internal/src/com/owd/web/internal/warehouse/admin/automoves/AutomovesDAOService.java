package com.owd.web.internal.warehouse.admin.automoves;

import com.owd.hibernate.generated.OwdAutoTransfer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 3, 2007
 * Time: 4:24:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutomovesDAOService {
private final static Logger log =  LogManager.getLogger();


    public static void updateAutomove(OwdAutoTransfer rule) throws Exception
    {
        HibernateSession.currentSession().saveOrUpdate(rule);
   /*     if(box.getShipMethods().size()>0)
        {
            Iterator it = box.getShipMethods().iterator();
            while(it.hasNext())
            {
               HibernateSession.currentSession().saveOrUpdate((OwdBoxtypesMethods)it.next());
            }
        }*/
        HibernateSession.currentSession().flush();
        HibUtils.rollback(HibernateSession.currentSession());
    }

    public static void insertAutomove(OwdAutoTransfer rule)  throws Exception
    {
       HibernateSession.currentSession().saveOrUpdate(rule);
        HibernateSession.currentSession().flush();
        HibUtils.rollback(HibernateSession.currentSession());
    }


    public static void deleteAutomove(Integer id) throws Exception
    {
        HibernateSession.currentSession().delete(HibernateSession.currentSession().load(OwdAutoTransfer.class,id));
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
    }



    public static OwdAutoTransfer getNewItemKittingTriggerRule() throws Exception
    {
        return new OwdAutoTransfer();
    }

    public static OwdAutoTransfer getNewItemTransferTriggerRule() throws Exception
    {
        return new OwdAutoTransfer();
    }

    public static OwdAutoTransfer getNewItemBreakdownTriggerRule() throws Exception
    {
        return new OwdAutoTransfer();
    }

 public static OwdAutoTransfer getAutomove(Integer id) throws Exception
     {
        return (OwdAutoTransfer) HibernateSession.currentSession().load(OwdAutoTransfer.class,id);
     }


    public static List getAllRules() throws Exception
    {
      return HibernateSession.currentSession().createQuery("from OwdAutoTransfer").list();

    }
}
