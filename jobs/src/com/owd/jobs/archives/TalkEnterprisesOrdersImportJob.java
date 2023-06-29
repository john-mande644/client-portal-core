package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import com.owd.jobs.jobobjects.batchimporters.RIEImportData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Nov 17, 2005
 * Time: 11:36:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class TalkEnterprisesOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    static String orderDownloadUrl = "http://clients.keybook.com/webservices/dryshine/sync_orders.php";

    public static void main(String[] args) throws Exception {

        HibernateSession.currentSession().load(OwdOrder.class,1);
        run();
        //log.debug(OWDUtilities.encryptData("504"));

    }



     public void internalExecute() {


        try {

            WebResource server = new WebResource(orderDownloadUrl, WebResource.kGETMethod);
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            String today = sdf.format(cal.getTime());
            cal.add(Calendar.DATE,-30);
            String twodaysago = sdf.format(cal.getTime());
            server.addParameter("start", twodaysago);
            server.addParameter("end", today);
            log.debug(twodaysago);
            log.debug(today);
           server.addParameter("auth", "hZksiPG65J1049Jh");
          //  BufferedReader response = server.getResource();
           BufferedReader response = new BufferedReader(new InputStreamReader(server.getResourceAsInputStream(false)));

    String urlString = "";
    String current;
    while ((current = response.readLine()) != null) {
      urlString += current+"\r";
    }
    log.debug(urlString.replaceAll("\\\\","").replaceAll("'",""));
           /* StringBuffer sb = new StringBuffer();
            int line = 0;
            line = (int) response.read();
            while (line != -1) {
                sb.append((char) line);
                line = response.read();
            }
            log.debug(sb);*/
                // //log.debug(sb.toString());
                //decode responce to get rid of the squares!!!!
             /*   Charset charset = Charset.forName("UTF-16");
                CharsetDecoder decoder = charset.newDecoder();
                ByteBuffer bbuf = (ByteBuffer.wrap(sb.toString().getBytes()));
                CharBuffer cbuf = decoder.decode(bbuf);
                log.debug(""+cbuf.toString());*/
                // DelimitedReader reader = (new TabReader(new BufferedReader(new StringReader(cbuf.toString())), true));
           // log.debug(urlString.toString());
                StringBuffer sbx = new StringBuffer();

                List results = processDataFile(new CSVReader(new BufferedReader(new StringReader(urlString.toString().replaceAll("\\\\","").replaceAll("'",""))), true));


                Iterator it = results.iterator();
                while (it.hasNext()) {
                    String note = (""+(it.next()));
                    //if (!(note.contains("Inventory SKU 16")) )
                   // {
                    sbx.append("\r\n" + note);
                  //  }
                }
                Vector emailsx = new Vector();
                emailsx.add("owditadmin@owd.com");


                if (results.size() > 0 && sbx.length()>0) {
                    log.debug("IMPORT ERROR REPORT");
                    Mailer.postMailMessage("OWD-TalkEnterprises Import Error", "The following orders were not imported due to the indicated error:\r\n\r\n" + sbx.toString(), emailsx, "donotreply@owd.com");
                }



        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            Mailer.postMailMessage("TalkEnterprises Cart66 import error", sb.toString(), "owditadmin@owd.com", "donotreply@owd.com");
        }

    }

    private List processDataFile(DelimitedReader data) throws Exception {

        List resultsList = new ArrayList();


        RIEImportData dataHandler = new RIEImportData();
        dataHandler.init(data);

        log.debug("toprocess " + dataHandler.getOrderCount());

        for (int orderIndex = 0; orderIndex < dataHandler.getOrderCount(); orderIndex++) {
            try {

                List resultL = processOrder(dataHandler, "504", OrderXMLDoc.kBackOrderAll, orderIndex);
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

    private List processOrder(RIEImportData dataHandler, String clientID, String backorderRule, int orderIndex) {
        //returns list of two elements - client Order ID and response
        //log.debug("inprocessOrder");
        List response = new ArrayList();
        //add new
        response.add(dataHandler.getOrderReference(orderIndex));

        try {
            Order order = new Order(clientID);
            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
            //order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.backorder_rule = backorderRule;

            //order.is_future_ship=1;
            log.debug("before loadorder");
            dataHandler.loadOrder(order, orderIndex);

            //fix payment status, do more validation stuff?
            order.recalculateBalance();
            // //log.debug("IS PAID:" + order.is_paid);
            // if (order.is_paid == 1) {
            //     //paid for - push it through!
            //     order.bill_cc_type = "CK";
            //     order.check_num = "999999";
            order.paid_amount = order.total_order_cost;
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.is_paid = 1;
            // } else {
            //     //not paid - needs payment
            //     if (order.cc_num.trim().length() > 1) {
            //         order.bill_cc_type = "CC";
            //         order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
            //     } else {
            //        order.bill_cc_type = "CK";
            //        order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            //
            //        order.is_future_ship = 1;
            //    }

            //    order.is_paid = 0;
            //     order.check_num = "";
            //     order.paid_amount = 0.00f;
            // }

            //figure out how to report errors

            // order.is_future_ship=1;

            if (!order.orderRefnumExists(order.order_refnum))
            {
            List<String>  shipMethods = Arrays.asList("TANDATA_USPS.USPS.FIRST", "TANDATA_USPS.USPS.PRIORITY", "CONNECTSHIP_UPS.UPS.GND","TANDATA_FEDEXFSMS.FEDEX.GND","TANDATA_FEDEXFSMS.FEDEX.FHD",
                    "CONNECTSHIP_UPS.UPS.STD", "TANDATA_USPS.USPS.I_FIRST", "TANDATA_USPS.USPS.I_PRIORITY","UPS.STDCAMX");

              order.getShippingInfo().setShipOptions(RateShopper.rateShop(order,shipMethods),"Prepaid","");

            // order.order_type="AIS Order Import";


            String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);

            if (reference == null) {
                if ((order.last_payment_error + " " + order.last_error).indexOf("reference already exists") < 1) {
                    throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
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
            }

        } catch (Exception
                ex) {
            ex.printStackTrace();


            response.add(ex.getMessage());
        } finally {


        }
        return response;
    }


}
