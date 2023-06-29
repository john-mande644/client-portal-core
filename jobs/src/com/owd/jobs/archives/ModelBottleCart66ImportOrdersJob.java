package com.owd.jobs.archives;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CSVReader;
import com.owd.core.DelimitedReader;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.batchimporters.RIEImportData;
import com.owd.jobs.jobobjects.utilities.OpenHostnameVerifier;
import com.owd.jobs.jobobjects.utilities.OpenTrustManager;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;



/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 4/19/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModelBottleCart66ImportOrdersJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    static String orderDownloadUrl = "https://modelinabottle.com/wp-content/plugins/cart66-owd-integration/cart66-owd-integration.php";

    public static void main(String[] args) throws Exception {


        run();
        //log.debug(OWDUtilities.encryptData("504"));

    }



    public void internalExecute() {


        try {

        //   installAllTrustManager();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

            Calendar cal = Calendar.getInstance();
            String today = sdf.format(cal.getTime());
            cal.add(Calendar.DATE,-30);
            String twodaysago = sdf.format(cal.getTime());


            URL testUrl = new URL(orderDownloadUrl+"?start=" + twodaysago + "&end="+today+"&auth=hZksiPG65J1049Jh");

            HttpsURLConnection testConnection = (HttpsURLConnection) testUrl.openConnection();
            testConnection.setReadTimeout(120000);
            testConnection.setConnectTimeout(120000);
            //   testConnection.setRequestProperty("Content-Type", "text/xml");


            testConnection.setRequestProperty("Host", "modelinabottle.com");
            testConnection.setRequestProperty("User-Agent", "OWD");
            testConnection.setRequestProperty("Content-Language", "en-US");

            testConnection.setRequestMethod("GET");

            testConnection.setDoOutput(true);

          //  OutputStreamWriter out = new OutputStreamWriter(
          //          testConnection.getOutputStream());
         //   out.write("?start=" + twodaysago + "&end="+today+"&auth=hZksiPG65J1049Jh");

          //  out.close();

            //https://shoppingcart.franklinhart.com/api?api_token=qvvd9cMdPymZlpdUnQzNvScUm8QdIR2Ycr2btIfc7w2nP587KBKakMUBLFIk&api_action=transaction_list

             BufferedReader inr = new BufferedReader(new InputStreamReader(testConnection.getInputStream()));

            StringBuilder sb = new StringBuilder();

            String line;
            try {

                while ((line = inr.readLine()) != null) {
                    sb.append(line+"\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inr != null) {
                    try {
                        inr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

          //  while ((current = response.readLine()) != null) {
          //      urlString += current+"\r";
         //   }

            log.debug(sb.toString().replaceAll("\\\\", "").replaceAll("'", ""));
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

            List results = processDataFile(new CSVReader(new BufferedReader(new StringReader(sb.toString().replaceAll("\\\\","").replaceAll("'",""))), true));


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
                Mailer.postMailMessage("OWD-ModelInABottle Import Error", "The following orders were not imported due to the indicated error:\r\n\r\n" + sbx.toString(), emailsx, "donotreply@owd.com");
            }



        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + OWDUtilities.getStackTraceAsString(ex) + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            Mailer.postMailMessage("ModelInABottle Cart66 import error", sb.toString(), "owditadmin@owd.com", "donotreply@owd.com");
        }

    }

    private List processDataFile(DelimitedReader data) throws Exception {

        List resultsList = new ArrayList();


        RIEImportData dataHandler = new RIEImportData();
        dataHandler.init(data);

        log.debug("toprocess " + dataHandler.getOrderCount());

        for (int orderIndex = 0; orderIndex < dataHandler.getOrderCount(); orderIndex++) {
            try {

                List resultL = processOrder(dataHandler, "316", OrderXMLDoc.kBackOrderAll, orderIndex);
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
            //log.debug("before loadorder");
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


            if (!order.orderRefnumExists(order.order_refnum))
            {

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
