package com.owd.jobs;

import com.owd.core.business.order.distributedOrderManagement.DOMUtilities;
import com.owd.core.business.order.distributedOrderManagement.Model.domFillable;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.order.*;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Feb 17, 2006
 * Time: 12:58:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class BackorderReleaseJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

        static String borSweepSQL = "SELECT DISTINCT\n" +
            "    *\n" +
            "FROM\n" +
            "    (\n" +
            "        SELECT\n" +
            "            (order_id) AS order_id,\n" +
            " MIN(order_num)                       AS order_num,\n" +
            "            MIN(o.client_fkey)                            AS client_id,\n" +
            "            2                                             AS gid,\n" +
            "            MIN(ISNULL(actual_order_date,o.created_date)) AS 'ddate'\n" +
            "        FROM\n" +
            "            owd_order o (NOLOCK) join borfday bb on bb.refnum=o.order_refnum\n" +
            "        JOIN owd_client c (NOLOCK)\n" +
            "        ON\n" +
            "            c.client_id=o.client_fkey and c.client_id=160\n" +
            "        AND is_active=1\n" +
            "        JOIN owd_line_item l (NOLOCK)\n" +
            "        JOIN owd_inventory_oh h (NOLOCK)\n" +
            "        JOIN owd_inventory oi (NOLOCK)\n" +
            "        ON\n" +
            "            oi.inventory_id=h.inventory_fkey\n" +
            "        ON\n" +
            "            l.inventory_id = h.inventory_fkey\n" +
            "        ON\n" +
            "            order_id = order_fkey\n" +
            "        WHERE\n" +
            "            order_status='Backorder (Active)' and o.client_fkey=160\n" +
            "        GROUP BY\n" +
            "            order_id\n" +
            "        HAVING\n" +
            "            SUM(\n" +
            "                CASE\n" +
            "                    WHEN is_auto_inventory=1\n" +
            "                    THEN 1\n" +
            "                    ELSE\n" +
            "                        CASE\n" +
            "                            WHEN \n" +
            "                                    ISNULL(is_scan_for_release,0)=0\n" +
            "                             and\n" +
            "                                (\n" +
            "                                    qty_on_hand >= quantity_request\n" +
            "                                )\n" +
            "                            THEN 1\n" +
            "                            ELSE 0\n" +
            "                        END\n" +
            "                END) =COUNT(*)\n" +
            "    ) AS bos \n" +
            "ORDER BY\n" +
            "    gid ASC,\n" +
            "    ddate ASC";

    static String sweepSQL = "SELECT DISTINCT\n" +
            "    *\n" +
            "FROM\n" +
            "    (\n" +
            "        SELECT\n" +
            "            (order_id) AS order_id,\n" +
            " MIN(order_num)                       AS order_num,\n" +
            "            MIN(o.client_fkey)                            AS client_id,\n" +
            "            2                                             AS gid,\n" +
            "            MIN(ISNULL(actual_order_date,o.created_date)) AS 'ddate'\n" +
            "        FROM\n" +
            "            owd_order o (NOLOCK) " +
            "        JOIN owd_client c (NOLOCK) left outer join owd_client_test_accounts ct on ct.client_fkey=c.client_id \n" +
            "        ON\n" +
            "            c.client_id=o.client_fkey \n" +
            "        AND is_backship=1 and is_active=1\n" +
            "        JOIN owd_line_item l (NOLOCK)\n" +
            "        JOIN owd_inventory_oh h (NOLOCK)\n" +
            "        JOIN owd_inventory oi (NOLOCK)\n" +
            "        ON\n" +
            "            oi.inventory_id=h.inventory_fkey\n" +
            "        ON\n" +
            "            l.inventory_id = h.inventory_fkey\n" +
            "        ON\n" +
            "            order_id = order_fkey\n" +
            "        WHERE\n" +
            "            ct.id is null and  order_status like '%Backorder%' and is_future_ship=0 \n" +
            "        GROUP BY\n" +
            "            order_id\n" +
            "        HAVING\n" +
            "            SUM(\n" +
            "                CASE\n" +
            "                    WHEN is_auto_inventory=1\n" +
            "                    THEN 1\n" +
            "                    ELSE\n" +
            "                        CASE\n" +
            "                            WHEN \n" +
            "                                    ISNULL(is_scan_for_release,0)=0\n" +
            "                             and\n" +
            "                                (\n" +
            "                                    qty_on_hand >= quantity_request\n" +
            "                                )\n" +
            "                            THEN 1\n" +
            "                            ELSE 0\n" +
            "                        END\n" +
            "                END) =COUNT(*)\n" +
            "    ) AS bos\n" +
            "ORDER BY\n" +
            "    gid ASC,\n" +
            "    ddate ASC";

        //static String sweepSQL = "select order_id, order_num, c.client_id from vw_back_sweep v, owd_client c where v.client_id = c.client_id and v.client_id=160 and min_avail >= 0 order by order_id asc ";
	   public static void main(String[] args) {

       run();


    }



     public void internalExecute() {
            Connection cxn= null;
            Statement stmt = null;
            ResultSet rs = null;
                //log.debug("start sweep");
            try
            {
                cxn = ConnectionManager.getConnection();
                Vector oids = new Vector();
                Vector onums = new Vector();
                Vector cids = new Vector();
                stmt = cxn.createStatement();

               // sweepSQL = borSweepSQL;
                
         //    sweepSQL = "select order_id, order_num, c.client_id from vw_back_sweep v, owd_client c (NOLOCK) where v.client_id = c.client_id and c.client_id=160 and min_avail >= 0 order by order_id asc ";
                log.debug(sweepSQL);
                stmt.execute(sweepSQL);
                rs = stmt.getResultSet();
                if(rs != null)
                {
                    while(rs.next())
                    {
                        oids.addElement(rs.getString(1));
                        onums.addElement(rs.getString(2));
                        cids.addElement(rs.getString(3));
                    }
                }else
                {
                //log.debug("rs null");
                }

                rs.close();
                stmt.close();
                cxn.rollback();


                for (int i=0;i<oids.size();i++)
                {
                    OrderUtilities.releaseBackorder(cxn,(String)oids.elementAt(i), (String)cids.elementAt(i));

                }

            }catch(Throwable ex)
            {
                ex.printStackTrace();
                Mailer.postMailMessage("Error during backorder sweep",ex.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","owditadmin@owd.com");
            }finally{             
                try{ cxn.rollback();}catch(Exception ex){}
                try{ rs.close();}catch(Exception ex){}
                try{ stmt.close();}catch(Exception ex){}
                try{ cxn.close();}catch(Exception ex){}
            }

        }

        public static void shipExistingOrderOnHold(String orderNum)
        {
            try
                                {
                                    //get order status
                                    //log.debug("getting OS for "+(String)oids.elementAt(i));
                                     Vector statuses = OrderStatus.getOrderStatusByKey(OrderStatus.kByOrderRef,orderNum,null);
                                    OrderStatus status = (OrderStatus) statuses.elementAt(0);
                                    if(status.is_on_hold)
                                    {
                                    //log.debug("got OS for "+(String)oids.elementAt(i));
                                    int units = 0;


                                        OrderUtilities.updateLineItemsForAvailability(((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(),status.items,OrderXMLDoc.kBackOrderAll,true, FacilitiesManager.getFacilityForCode(status.shipLocation).getId());
                      //log.debug("done with shipping rules");
                        //release it
                             //log.debug("Shipping backorder...");
                        String backorderRef = OrderUtilities.shipExistingOrder(((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(),status);
                             log.debug("Shipped backorder..."+backorderRef);

                        Event.addOrderEvent(((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(),new Integer(status.order_id).intValue(),Event.kEventTypeHandling,"On Hold Address Error cleared",null);
                    //log.debug("done with shipOrder");
                      HibUtils.commit(HibernateSession.currentSession());
                                    }
                    }catch(Exception ex)
                    {

                     try{ HibUtils.rollback(HibernateSession.currentSession()); }catch(Exception exx){}
                        ex.printStackTrace();
                        //Mailer.postMailMessage("Couldn't clear autoship backorder",ex.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","owditadmin@owd.com");
                    }


        }

}
