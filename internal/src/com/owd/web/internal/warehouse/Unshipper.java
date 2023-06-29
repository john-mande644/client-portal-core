package com.owd.web.internal.warehouse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.HibUtils;
import com.owd.core.business.order.OrderUtilities;import com.owd.core.business.order.Order;
import com.owd.core.business.order.Event;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 28, 2006
 * Time: 10:40:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class Unshipper {
private final static Logger log =  LogManager.getLogger();

    public static String unshipOrder(String orderNum, String orderRefNum, String whom)  throws Exception
    {
        //verify order was shipped and has not been unshipped previously

        try
        {


        PreparedStatement ps = HibernateSession.getPreparedStatement("select count(*) from owd_order_track t " +
                "join owd_order on order_id=order_fkey where t.is_void=0 and order_num=? and order_refnum=?" +
                "");

        ps.setString(1,orderNum);
        ps.setString(2,orderRefNum);
            //log.debug("executing ps1");

            ResultSet rs = ps.executeQuery();

            int oldShips = 0;
        if(rs.next())
        {
            oldShips=rs.getInt(1);
        }
            if(oldShips<1)
            {
                throw new Exception("No unvoided shipments found");
            }

          rs.close();


        HibernateSession.closePreparedStatement();

        ps = HibernateSession.getPreparedStatement("select count(*) from owd_order_track t " +
                "join owd_order on order_id=order_fkey where t.modified_by='unshipper' and order_num=? and order_refnum=?");
            //log.debug("ps 2");
        ps.setString(1,orderNum);
        ps.setString(2,orderRefNum);
        rs = ps.executeQuery();


            int reShips = 0;
        if(rs.next())
        {
            reShips=rs.getInt(1);
        }
            if(reShips>0)
            {
                throw new Exception("This order was already unshipped by this tool - nothing to process");
            }
         rs.close();

             }catch (Exception ex)
        {
            throw new Exception("Error looking up order number "+orderNum+" : "+ex.getMessage());
        }

         HibernateSession.closePreparedStatement();
        //get shipments that need to be undone

        //log.debug("ps 3");
        //void existing shipment(s)
    PreparedStatement ps =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("select order_track_id, client_fkey,order_fkey,(-1.00*total_billed), owd_order.facility_code from owd_order_track t " +
                "join owd_order on order_id=order_fkey where t.is_void=0 and order_num=? and order_refnum=?" +
                "");

        PreparedStatement psv =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("update owd_order_track  " +
                "set is_void=1, modified_by='reshipper' where is_void=0 and order_track_id=?");

        PreparedStatement psr =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.owdbill_shipping_trans (activity_date, recorded_date, created_date, amount, location_code, invoice_date, trans_fkey, \n" +
                "carrier, ship_method, order_fkey, activity_type_fkey, activity_desc, is_inbound, client_fkey, is_manual) values (\n" +
                "convert(datetime,convert(varchar,getdate(),101)), \n" +
                "convert(datetime,convert(varchar,getdate(),101)), \n" +
                "getdate(), \n" +
                "?, \n" +       //amount
                "?, \n" +        //location
                "null, \n" +
                "null, \n" +
                "'', \n" +
                "'', \n" +    //ship method
                "?, \n" +    //order fkey
                "4, \n" +
                "'Not Changed/MISC/Credit for reship due to carrier return', \n" +
                "0, \n" +
                "?, \n" +    //client_fkey
                "0)");

        ps.setString(1,orderNum);
        ps.setString(2,orderRefNum);
        ResultSet rs = ps.executeQuery();

        //log.debug("ps x");
        while(rs.next())
               {
                   //log.debug("ps iter");
                   int orderid = rs.getInt(3);
                   float creditamt = rs.getFloat(4);
                   int clientid = rs.getInt(2);
                   int trackid = rs.getInt(1);
                   String shipLoc = rs.getString(5);

                   //insert refund record
                   psr.setFloat(1,creditamt);
                   psr.setString(2,shipLoc);
                   psr.setInt(3,orderid);
                   psr.setInt(4,clientid);
                   psr.executeUpdate();
                   psv.setInt(1,trackid);
                   psv.executeUpdate();
                   //insert order comment
                      Event.addOrderEvent( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(),orderid,Event.kEventTypeEdit,"Adjustment issued of $"+
                              creditamt+" to shipping account due to reship after carrier return",whom);

               }

                rs.close();
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());


        //report activity/results
        return "Done";
    }

    public static void main(String[] args)
    {
        try
        {
            //log.debug(unshipOrder("5369244","14596-2-1","sbuskirk"));
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
