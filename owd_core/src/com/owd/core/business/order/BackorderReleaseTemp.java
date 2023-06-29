package com.owd.core.business.order;

import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 23, 2005
 * Time: 1:09:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class BackorderReleaseTemp {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args)
    {
        try {

          //  Session sess = HibernateSession.currentSession();

            releaseAllForClient();
           // log.debug("broken");
            for (int x=1;x<10;x++)
            {
                log.debug(x+":"+(x==1?x:(x%2==0?x/2:((x-1)/2)+1)));
            }

               }catch(Exception ex)
    {
        try
        {
            HibUtils.rollback(HibernateSession.currentSession());
        } catch(Exception exc){exc.printStackTrace();}
        ex.printStackTrace();

    }   finally
    {
         HibernateSession.closeSession();
    }
    }


  public static void releaseAllForClient()
        {
            try

    {


       ResultSet rs = HibernateSession.getResultSet("select distinct order_id from owd_order (NOLOCK) join owd_line_item (NOLOCK) on order_id=order_fkey\n" +
               "               and  quantity_request>0 and quantity_back=0 and quantity_actual=0\n" +
               "               where client_fkey=535 and owd_order.created_date>='2014-12-1' and owd_order.created_date<'2014-12-13' and order_status='Backorder (Active)'");


    while(rs.next() )
    {
        try
        {
        partialShipOrder(rs.getString(1));
            }catch(Exception ex)
    {
        try
        {
            HibUtils.rollback(HibernateSession.currentSession());
        } catch(Exception exc){exc.printStackTrace();}
        ex.printStackTrace();

    }
    }
    }catch(Exception ex)
    {
        try
        {
            HibUtils.rollback(HibernateSession.currentSession());
        } catch(Exception exc){exc.printStackTrace();}
        ex.printStackTrace();

    }
    finally
    {
         HibernateSession.closeSession();
    }
    }

    private static void partialShipOrder(String rs) {
        try
        {
    OrderStatus status = new OrderStatus( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(),rs);

            log.debug("partial shipping "+status.orderReference);
   // status.removeDuplicateSKUs();
                               Map mapper = OrderUtilities.updateLineItemsForAvailability( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), status.items,
                                       OrderXMLDoc.kPartialShip, true, FacilitiesManager.getFacilityForCode(status.shipLocation).getId());
       int ttlavailable=0;
            for(LineItem item: (Vector<LineItem>) status.items)
        {
           ttlavailable += item.quantity_actual;
        }


            log.debug(""+mapper);

        if(ttlavailable>0)
        {
   log.debug("gotcha");

               Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE,-1);
                                Date poster = cal.getTime();
            OrderUtilities.shipExistingOrder( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(),status,poster);
        }
        HibUtils.commit(HibernateSession.currentSession());
      }catch(Exception ex)
    {
        try
        {
            HibUtils.rollback(HibernateSession.currentSession());
        } catch(Exception exc){//exc.printStackTrace();
             }
        ex.printStackTrace();

    }
    }
}
