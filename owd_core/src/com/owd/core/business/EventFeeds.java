package com.owd.core.business;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdExternalEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 12/4/12
 * Time: 10:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventFeeds {
private final static Logger log =  LogManager.getLogger();

    public static final String kOrderEntityType="order";

    public static final String kOrderStatusEventType="orderstatus";

    public static final String kOrderStatusCreatedSubEventType="created";
    public static final String kOrderStatusShippedSubEventType="shipped";
    public static final String kOrderStatusHeldSubEventType="On Hold";
    public static final String kOrderStatusStockoutSubEventType="stockout";

    public static final String kAddressVerifySourceType="addressverify";
    public static final String kManualSourceType="manual";
    public static final String kApiSourceType="api";


    public static void reportOrderHeld(int orderId, int clientId, String source, String note) throws Exception
    {

        OwdExternalEvent ev = new OwdExternalEvent();
        ev.setClientFkey(clientId);
        ev.setEntityId(orderId);
        ev.setEntitytype(kOrderEntityType);
        ev.setEventSubtype(kOrderStatusHeldSubEventType);
        ev.setEventType(kOrderStatusEventType);
        ev.setSource(source);
        ev.setNote(note);
        ev.setData("");
        ev.setEventTime(GregorianCalendar.getInstance().getTime());

        HibernateSession.currentSession().save(ev);
        HibUtils.commit(HibernateSession.currentSession());

    }

    public static void reportOrderStockout(int orderId, int clientId, String source, String note) throws Exception
    {

        OwdExternalEvent ev = new OwdExternalEvent();
        ev.setClientFkey(clientId);
        ev.setEntityId(orderId);
        ev.setEntitytype(kOrderEntityType);
        ev.setEventSubtype(kOrderStatusStockoutSubEventType);
        ev.setEventType(kOrderStatusEventType);
        ev.setSource(source);
        ev.setNote(note);
        ev.setData("");
        ev.setEventTime(GregorianCalendar.getInstance().getTime());

        HibernateSession.currentSession().save(ev);
        HibUtils.commit(HibernateSession.currentSession());

    }

    public static void main(String[] args)  throws Exception
    {
        reportOrderHeld(12312332, 55, kAddressVerifySourceType, "note");
    }


}
