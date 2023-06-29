package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.HibUtils;
import com.owd.core.business.order.OrderUtilities;import com.owd.core.business.order.Order;
import com.owd.core.business.order.LineItem;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 4, 2007
 * Time: 12:33:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class verifyExistingOrdersAgainstKitDefinitions {
private final static Logger log =  LogManager.getLogger();

   public static void main(String[] args)
   {

       try {
              Session sess = HibernateSession.currentSession();

           ResultSet rs = HibernateSession.getResultSet(sess,"select o.client_fkey,i.inventory_num,req_inventory_quant*quantity_request,line_item_id,order_id\n" +
                   "from owd_line_item l (NOLOCK)\n" +
                   "join owd_inventory_required_skus r (NOLOCK)\n" +
                   "    join owd_inventory i (NOLOCK) on i.inventory_id=r.req_inventory_fkey\n" +
                   "on r.inventory_fkey=l.inventory_id \n" +
                   "join owd_order o (NOLOCK) on order_id=order_fkey and is_parent=1 \n" +
                   "where quantity_actual=0 and quantity_request>0 and is_void=0 and post_date is null");

           int row=1;
           while(rs.next())
           {
               log.debug(row++);
               try
               {
           Order order = new Order(rs.getString(1));
           order.addLineItem(rs.getString(2),rs.getString(3),"0.00","0.00","","","");
        //   order.addLineItem("20256","1","0.00","0.00","Culture Warrior Hardcover - Book","","");
           for(int i=0;i<order.skuList.size();i++)
           {
               LineItem item = (LineItem) order.skuList.elementAt(i);
               item.parent_line=new Integer(rs.getString(4));
               item.order_fkey=rs.getString(5);
               item.client_ref_num="kit created" ;
               item.dbsave( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection());

           }


           sess.flush();
 HibUtils.commit(sess);
             } catch (Exception exx) {
           exx.printStackTrace();
       }
           }

       } catch (Exception ex) {
           ex.printStackTrace();
       } finally {

              HibernateSession.closeSession();
       }


   }
}
