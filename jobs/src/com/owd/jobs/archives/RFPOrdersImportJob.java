package com.owd.jobs.archives;

import com.owd.LogableException;
import com.owd.core.*;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.ftp.FTPManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.DelimitedFileOrderHandler;
import com.owd.jobs.jobobjects.FtpConnector;
import com.owd.jobs.jobobjects.batchimporters.RFPUploadOrderData;
import com.owd.jobs.jobobjects.batchimporters.RFPUploadOrderData;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class RFPOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

   // static String orderDownloadUrl = "http://clients.keybook.com/webservices/dryshine/sync_orders.php";
   protected static final int kClientID = 614;
    protected String status = "";
    //FtpConnector ftp = new FtpConnector("ftp.owd.com", "rfpebridge", "EbridgeOWD17!!", "toOWD");

    public static void main(String[] args) throws Exception {


      //  HibernateSession.currentSession().load(OwdOrder.class,1);
        run();


    }



     public void internalExecute() {


        try {

          //  BufferedReader response = server.getResource();
            FTPManager ftp = new FTPManager("ftp.owd.com", "rfpebridge", "EbridgeOWD17!!");
            ftp.setReadDirectory("/toOWD");
            ftp.setWriteDirectory("/");
            List<String> fileNames = ftp.getFileNames();
            for (String file : fileNames) {
            log.debug("Processing this file" +  file );
                log.debug(ftp.getFileData(file).length);

try {
    List results;
    if (new String(ftp.getFileData(file)).contains("\"")){
        results = processDataFile(new TabReader(new BufferedReader(new StringReader(new String(ftp.getFileData(file)).replaceAll("\"",""))), false));

       // throw new LogableException("File has quotes fix it "+file,"","614","Batch importer", LogableException.errorTypes.ORDER_IMPORT);
    }
     results = processDataFile(new TabReader(new BufferedReader(new StringReader(new String(ftp.getFileData(file)))), false));

    System.out.println(results);

        if(results.size()>0){
        //    System.out.println("Moving to issue folder");
          //  ftp.moveFile(file,"/../archive/issues/"+file);
            List  data = (ArrayList) results.get(0);

            throw new LogableException(data.get(1).toString(),file,"614","BatchImporter", LogableException.errorTypes.ORDER_IMPORT);
        }else {
            ftp.moveFile(file, "/../archive/" + file);
        }

}catch(Exception e){
    log.debug("Erroring");
    e.printStackTrace();
  //  ftp.moveFile(file,"/../archive/issues/"+file);
}
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
          //  Mailer.postMailMessage("TalkEnterprises Cart66 import error", sb.toString(), "owditadmin@owd.com", "donotreply@owd.com");
        }

    }

    private List processDataFile(DelimitedReader data) throws Exception {

        List resultsList = new ArrayList();


       RFPUploadOrderData dataHandler = new RFPUploadOrderData();
        dataHandler.init(data);

        log.debug("toprocess " + dataHandler.getOrderCount());
        log.debug(dataHandler.getDataReader().getRowCount());

        for (int orderIndex = 0; orderIndex < dataHandler.getOrderCount(); orderIndex++) {
            try {

                List resultL = processOrder(dataHandler, kClientID+"", OrderXMLDoc.kBackOrderAll, orderIndex);
                log.debug(resultL);
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

    private List processOrder(RFPUploadOrderData dataHandler, String clientID, String backorderRule, int orderIndex) {
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
           /* List<String>  shipMethods = Arrays.asList("TANDATA_USPS.USPS.FIRST", "TANDATA_USPS.USPS.PRIORITY", "CONNECTSHIP_UPS.UPS.GND","TANDATA_FEDEXFSMS.FEDEX.GND","TANDATA_FEDEXFSMS.FEDEX.FHD",
                    "CONNECTSHIP_UPS.UPS.STD", "TANDATA_USPS.USPS.I_FIRST", "TANDATA_USPS.USPS.I_PRIORITY","UPS.STDCAMX");

              order.getShippingInfo().setShipOptions(RateShopper.rateShop(order,shipMethods),"Prepaid","");*/

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
