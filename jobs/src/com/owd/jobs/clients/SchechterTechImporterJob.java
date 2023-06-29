package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.batchimporters.SchechterUploadData;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jun 13, 2007
 * Time: 2:19:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class SchechterTechImporterJob   extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

   // static String updateStockStatusSQL = "update products set stockstatus=? where productcode=?";
    static int clientKey = 358;
     static Map ship = new TreeMap();


   // static String updateOrderIsProcessedSQL = "update orders set locked='Y',orderstatus='Pending Shipment' where ('SDL'+convert(varchar,orderid))=? ";
    /*static String ordersToDoSQL = "select 'SDL'+convert(varchar,o.orderid),\n" +
            "                    billingfirstname+' '+billinglastname,\n" +
            "                    billingcompanyname,\n" +
            "                    o.billingaddress1,\n" +
            "                    o.billingaddress2,\n" +
            "                    billingcity,\n" +
            "                    billingstate,\n" +
            "                    billingpostalcode,\n" +
            "                    billingcountry,\n" +
            "                    billingphonenumber,\n" +
            "                    emailaddress,\n" +
            "                    1,'','','',\n" +
            "                    shipfirstname+' '+shiplastname,\n" +
            "                    shipCompanyName,\n" +
            "                    shipaddress1,\n" +
            "                    shipaddress2,\n" +
            "                    shipcity,\n" +
            "                    shipstate,\n" +
            "                    shippostalcode,\n" +
            "                    shipcountry,\n" +
            "                    shipphonenumber,\n" +
            "                    '',\n" +
            "                    case when Shippingmethodname like 'Free%' THEN 'USPS Media Mail Single-Piece' else  +\n" +
            "                   case when shippingmethodname='USPS Media Mail' then 'USPS Media Mail Single-Piece' else  +\n" +
            "                   case when shippingmethodname='USPS Airmail Parcel Post' then 'USPS Int''l Parcel Post Air' else  +\n" +
            "                   case when shippingmethodname='USPS Airmail Letter Post' then 'USPS Int''l Letter-Post Air' else shippingmethodname end end end end,\n" +
            "                    '',\n" +
            "                    0,\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    case when ISNULL(paycode,'')='MANUAL' then 'Mail Order' else 'Volusion Cart' END,\n" +
            "                    customer_ipaddress,\n" +
            "                    salestax1+salestax2+salestax3,\n" +
            "                    totalshippingcost,\n" +
            "                    0.00,\n" +
            "                    productcode,\n" +
            "                    convert(int,quantity),\n" +
            "                    productprice,\n" +
            "                    productname,\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    '',\n" +
            "                    '' from orders o\n" +
            " left outer join (select pay_orderid,max(pay_authcode) as 'paycode' from payment_log group by pay_orderid ) as pay on pay.pay_orderid=o.orderid\n" +
            "                    join customers c on o.customerid=c.customerid \n" +
            "                    join shippingmethods s on s.shippingmethodid=o.shippingmethodid join orderdetails l on l.orderid=o.orderid\n" +
            "                     where orderstatus='Processing' and (paymentmethodid=1 or paymentamount >0.00) order by o.orderid asc";
*/
     //  private String orderDownloadUrl = "https://www.temperaturealert.com/Wireless-Temperature-Store/apiv1/orders.aspx"; //https://store.temperaturealert.com/sql/orders.asp";
    private String orderDownloadUrl = "http://shop.tempalert.com/apiv1/orders.aspx";
   // private String orderUpdateUrl = "https://www.temperaturealert.com/Wireless-Temperature-Store/apiv1/insert.aspx"; //https://store.temperaturealert.com/sql/insert.asp";
    private String orderUpdateUrl = "http://shop.tempalert.com/apiv1/insert.aspx";
    public static void main(String[] args) {

        run();

    }

     public void internalExecute() {


        try {

             WebResource server = new WebResource(orderDownloadUrl, WebResource.kGETMethod);


            server.addParameter("passwd","somerandompassword");// "aE4Ft3>g$yd#c^bfDyn$ds@t5g3d#$");
          
            BufferedReader response = server.getResource();


            log.debug("gpot response");
            StringBuffer sb = new StringBuffer();
            int line = 0;
            line = (int) response.read();
            while (line != -1) {
              //  log.debug("reading resp1111");
                String s =  String.valueOf((char)line);
                System.out.print(s);
             if(!s.equals("\""))
                sb.append((char)line);

                line = response.read();
            }


             log.debug(sb.toString());

            /* if(1==1){
                 throw new Exception("just testing");
             }*/

            StringBuffer sbx = new StringBuffer();

            List results = processDataFile(new TabReader(new BufferedReader(new StringReader(sb.toString())), false));


            Iterator it = results.iterator();
            while (it.hasNext()) {
                sbx.append("\r\n" + it.next());
            }
            Vector emailsx = new Vector();
            emailsx.add("owditadmin@owd.com");
            emailsx.add("diane@temperaturealert.com");
            emailsx.add("fulfillment@schechtertech.com");
            emailsx.add("caitlin@temperaturealert.com");
           // emailsx.add("dnickels@owd.com");


            if (results.size() > 0) {
                //log.debug("The following orders were not imported due to the indicated error:\r\n\r\n" + sbx.toString());
                Mailer.postMailMessage("OWD-SchechterTech Import Error", "The following orders were not imported due to the indicated error:\r\n\r\n" + sbx.toString(), emailsx, "owditadmin@owd.com");
            }

           /* ResultSet rsx = HibernateSession.getResultSet("select inventory_num,qty_on_hand from owd_inventory (NOLOCK) \n" +
                    "join owd_inventory_oh (NOLOCK) \n" +
                    "on inventory_id=inventory_fkey and is_active=1 and client_fkey=319");
        PreparedStatement psx = HibernateSDLearnSession.getPreparedStatement(updateStockStatusSQL);

            while (rsx.next()) {
                psx.setInt(1, rsx.getInt(2));
                psx.setString(2, rsx.getString(1));
                try {
                    psx.executeUpdate();
                    HibernateSDLearnSession.currentSession().getTransaction().commit();
                } catch (Exception ex) {

                }
            }*/

        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            //  Mailer.postMailMessage("NatJourImporter import error", sb.toString(), "owditadmin@owd.com", "owditadmin@owd.com");
        } finally {
            HibernateSession.closeSession();
        }

    }

    private List processDataFile(DelimitedReader data) throws Exception {

        List resultsList = new ArrayList();


        SchechterUploadData dataHandler = new SchechterUploadData();
        dataHandler.init(data);

        //log.debug("toprocess " + dataHandler.getOrderCount());

        for (int orderIndex = 0; orderIndex < dataHandler.getOrderCount(); orderIndex++) {
            try {

                List resultL = processOrder(dataHandler, "358", OrderXMLDoc.kBackOrderAll, orderIndex);
                //log.debug(resultL);
                if (resultL.toString().indexOf(",") > 0) {
                    //log.debug("adding to result list");
                    resultsList.add(resultL);
                }
                //log.debug("processed " + (orderIndex + 1));
                //record success
            } catch (Exception ex) {
                //record error
                //ex.printStackTrace();
                //log.debug("Uncaught error at row " + orderIndex + ":" + ex.getMessage());
            }


        }
        return resultsList;
    }

    private List processOrder(SchechterUploadData dataHandler, String clientID, String backorderRule, int orderIndex) {
        //returns list of two elements - client Order ID and response
        List response = new ArrayList();
        //add new
        response.add(dataHandler.getOrderReference(orderIndex));


       
        try {

                Order order = new Order(clientID);
                order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
                //order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.backorder_rule = backorderRule;

                // order.is_future_ship=1;
                dataHandler.loadOrder(order, orderIndex);
            if(OrderUtilities.orderRefnumExists(order.order_refnum,clientID))
            {
                updateOrder(dataHandler.getOrderReference(orderIndex));

            }   else {
                //log.debug(order.getShippingInfo().carr_service);
                //log.debug(order.getShippingInfo().carr_service_ref_num);


                //fix payment status, do more validation stuff?
                order.recalculateBalance();
                //log.debug("IS PAID:" + order.is_paid);
                if (order.is_paid == 1) {
                    //paid for - push it through!
                    order.bill_cc_type = "CK";
                    order.check_num = "999999";
                    order.paid_amount = order.total_order_cost;
                    order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                    order.is_paid = 1;
                } else {
                    //not paid - needs payment
                    if (order.cc_num.trim().length() > 1) {
                        order.bill_cc_type = "CC";
                        order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
                    } else {
                        order.bill_cc_type = "CK";
                        order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

                        order.is_future_ship = 1;
                    }

                    order.is_paid = 0;
                    order.check_num = "";
                    order.paid_amount = 0.00f;
                }

                //figure out how to report errors
                if (order.total_order_cost >= 5000.00 && order.getShippingInfo().carr_service.startsWith("UPS")) {
                    order.getShippingInfo().ss_proof_delivery = "1";
                }
                // order.is_future_ship=1;

                //  order.getShippingInfo().setShipOptions("USPS First-Class Mail","Prepaid","");

                // order.order_type="AIS Order Import";

                boolean intlHold = false;
                if(order.getShippingAddress().isInternational() && order.containsSKU("TM-CELL400-Z"))
                {
                    order.is_future_ship = 1;
                    intlHold = true;
                }
                boolean highUnits = false;
                if (order.getTotalPhysicalUnitQuantity() >= 25) {
                    order.is_future_ship = 1;
                    highUnits = true;
                }

                String reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, false);


                //   String reference = new String();
                if (reference == null) {
                    if ((order.last_payment_error + " " + order.last_error).indexOf("reference already exists") < 1) {
                        throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
                    }
                } else {
                    updateOrder(dataHandler.getOrderReference(orderIndex));

                    if (highUnits) {
                        Mailer.postMailMessage("Order " + order.order_refnum + " on hold due to quantities>=25", "This is to alert you that your order reference " + order.order_refnum + " has been received on hold at OWD due to having 25 or more units in the order.", "hjs@temperaturealert.com", "donotreply@owd.com");
                        Mailer.postMailMessage("Order " + order.order_refnum + " on hold due to quantities>=25", "This is to alert you that your order reference " + order.order_refnum + " has been received on hold at OWD due to having 25 or more units in the order.", "diane@temperaturealert.com", "donotreply@owd.com");
                    }

                    if (intlHold) {
                        Mailer.postMailMessage("Order " + order.order_refnum + " on hold due to being international and containing TM-CELL400-Z", "This is to alert you that your order reference " + order.order_refnum + " has been received on hold at OWD due to containing TM-CELL400-Z and shipping internationally.", "diane@temperaturealert.com", "donotreply@owd.com");
                    }

                }

            /*  response.add("Confirmed as OWD Reference: " + reference);
              if (order.is_backorder == 1)
                  response.set(1, response.get(1) + "; Backorder created");
              if (order.is_future_ship == 1)
                  response.set(1, response.get(1) + "; Order placed On Hold for followup; "+order.last_payment_error + " " + order.last_error );
              if(order.backorder_order_num != null)
              {
              if (order.backorder_order_num.length() > 0)
                  response.set(1, response.get(1) + "; Backorder created as OWD order reference " + order.backorder_order_num);
              }*/
/*
            PreparedStatement pstmt = HibernateSDLearnSession.getPreparedStatement(updateOrderIsProcessedSQL);

            pstmt.setString(1, dataHandler.getOrderReference(orderIndex));
            pstmt.executeUpdate();
            HibernateSDLearnSession.currentSession().getTransaction().commit();*/
                //todo    todo run order update

            }
        } catch (Exception
                ex) {
            ex.printStackTrace();


            response.add(ex.getMessage());
        } finally {


        }

        return response;
    }
   private void updateOrder(String orderRef){
       try{
           log.debug("Updating status to pending shipment");
        WebResource server = new WebResource(orderUpdateUrl, WebResource.kGETMethod);


            server.addParameter("passwd",  "somerandompassword");// "aE4Ft3>g$yd#c^bfDyn$ds@t5g3d#$");
           server.addParameter("orderNum", orderRef.replaceAll("SCH",""));


            BufferedReader response = server.getResource();


            StringBuffer sb = new StringBuffer();
            int line = 0;
            line = (int) response.read();
            while (line != -1) {
                sb.append((char)line);
                line = response.read();
            }


             //log.debug(sb.toString());
       }catch (Exception e){
                            e.printStackTrace();
           
   }


   }





    
}
