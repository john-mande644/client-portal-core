package com.owd.core.business.order;

import com.owd.core.business.Client;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipHold;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.spi.SessionImplementor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:41:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderManager {
private final static Logger log =  LogManager.getLogger();






    public static void main(String[] args)
    {
             //get list of groups for user name

        try
        {
            File temp = File.createTempFile("switchdc1",".csv");
            temp.setWritable(true);
            BufferedWriter output = new BufferedWriter(new FileWriter(temp));

            log.debug(temp.getCanonicalPath());
            log.debug(System.getProperty("java.io.tmpdir"));

            ResultSet rs = HibernateSession.getResultSet("select order_id, order_refnum,order_num from owd_order where facility_code='DC7' and client_fkey=489 and order_status='at warehouse' \n" +
                    "and order_id not in (select order_id from zzprintedslips) and salesperson='switch'\n" +
                    "order by post_date asc");

            while(rs.next())
            {
                int orderId = rs.getInt(1);
                log.debug(orderId);
                OrderStatus status = new OrderStatus(""+orderId);
                if(switchOrderFacility(status,"DC1")) {
                    log.debug("Switched!");
                    output.write(rs.getString(2) + "," + rs.getString(3));
                    output.flush();
                }   else
                {
                    log.debug("No switch");
                }
            }
            output.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }                        finally
        {
            //output.close();
             HibernateSession.closeSession();
        }

    }


    public static boolean switchOrderFacility(OrderStatus order, String toFacilityCode) throws Exception
    {
          boolean switched=false;
          if(orderHasStockAtFacility(order,toFacilityCode))
          {
              try{
                  order.unpostOrder();
                  order.changeLocation(toFacilityCode);

                  Map skuMap = OrderUtilities.updateLineItemsForAvailability( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), order.items, OrderXMLDoc.kRejectBackOrder, true, FacilitiesManager.getFacilityForCode(order.shipLocation).getId() );

                  String backorderRef = OrderUtilities.shipExistingOrder(order);

                  switched = true;
                  HibUtils.commit(HibernateSession.currentSession());
              }catch(Exception ex)
              {
                  log.debug("ERROR:"+order.order_id);
                  ex.printStackTrace();
              }

          }

             return switched;


    }

    static boolean orderHasStockAtFacility(OrderStatus order, String toFacilityCode)   throws Exception
    {
        for(LineItem line: (Vector<LineItem>)order.items)
        {
            int qty = InventoryManager.getStockLevelForFacility(Integer.parseInt(line.inventory_fkey),toFacilityCode);
            if(qty<=line.quantity_actual)
            {
                return false;
            }

        }

        return true;

    }
    public static void dupeAndReleasePhotoOrder(int originalOrderId, int clientId, String originalrefnum) {
        try {

            try {
                OrderStatus status = new OrderStatus(""+originalOrderId,""+clientId);
                ClientPolicy policy = Client.getClientForID(""+clientId).getClientPolicy();


                Order order = status.createOrderFromOrderStatus(policy.createInitializedOrder(), true);

                order.contactID = "";
                order.order_type = "Insert Reorder";

                order.skuList = new Vector<LineItem>();

                order.addLineItem("844923092090","1","0.00","0.00","","","");
                order.total_shipping_cost=0.00f;
                order.total_tax_cost=0.00f;
                order.tax_pct=0.00f;
                order.discount=0.00f;
                order.discount_pct=0.00f;

                order.order_refnum=originalrefnum+"B";

                order.recalculateBalance();

                order.paid_amount = order.total_order_cost;

                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.is_paid = 1;

                if(order.getShippingAddress().isInternational())
                {
                    order.getShippingInfo().setShipOptions("USPS First-Class Mail International","Prepaid",null);
                }   else
                {
                    order.getShippingInfo().setShipOptions("USPS First-Class Mail","Prepaid",null);

                }

                String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
                if(reference==null)
                {
                    log.debug("FAIL creating new order for id "+originalOrderId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void saveAndCommitOrderHoldInfo(OwdOrderShipHold holder, OwdOrder order)
    {
        try{

                   if(holder==null)
                   {     throw new Exception("Null shipHold information object when attemtping to save order shiphold info");
                   }

                       holder.setOrder(order);

                       HibernateSession.currentSession().saveOrUpdate(holder);

                 HibernateSession.currentSession().flush();
                   HibUtils.commit(HibernateSession.currentSession());


               }catch(Exception ex)
               {
                   ex.printStackTrace();
               }                        finally
               {
                   // HibernateSession.closeSession();
               }

    }

}







