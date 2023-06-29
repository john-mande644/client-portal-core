package com.owd.web.autoship;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.autoship.AutoShipFactory;
import com.owd.core.business.autoship.AutoShipManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderAuto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 3, 2007
 * Time: 4:24:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoshipDAOService {
private final static Logger log =  LogManager.getLogger();


    public static void updateAutoship(OwdOrderAuto as, String note) throws Exception
    {
        AutoShipManager.saveOrUpdateAutoShip(as, note);
        HibUtils.commit(HibernateSession.currentSession());
    }

    public static void insertAutoship(OwdOrderAuto as, String note)  throws Exception
    {
        AutoShipManager.saveOrUpdateAutoShip(as, note);
        HibUtils.commit(HibernateSession.currentSession());
    }

    public static void deleteAutoship(Integer id) throws Exception
    {
        AutoShipManager.deleteAutoShip((OwdOrderAuto)HibernateSession.currentSession().load(OwdOrderAuto.class,id));
        HibUtils.commit(HibernateSession.currentSession());
    }

    public static OwdOrderAuto getAutoship(Integer id) throws Exception
    {
       return (OwdOrderAuto) HibernateSession.currentSession().load(OwdOrderAuto.class,id);
    }

    public static OwdOrderAuto getNewAutoship(int clientID) throws Exception
    {
       return AutoShipFactory.getNewAutoShip(clientID);
    }

    public static List getAutoshipsForClient(int clientID) throws Exception
    {
      return new ArrayList();

    }
}
