package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.batchimporters.DealSegmentsUploadOrdersData;
import com.owd.jobs.jobobjects.batchimporters.PhionOmxImportData;
import com.owd.jobs.jobobjects.utilities.RateShopper;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 27, 2005
 * Time: 10:57:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class DealSegmentsOrderImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();



    public static void main(String[] args) throws Exception {

        //  internalExecute("2005-4-7");
        run();

    }

    static Map<String,String> packslipMap = new HashMap<String,String>();
    static Map<String,Integer> accountMap = new HashMap<String,Integer>();

    static {
        packslipMap.put("The Doctors Deals","doctor");
        packslipMap.put("The Doctors","doctor");
        packslipMap.put("Access Hollywood","chictreat");
        packslipMap.put("Chic Treat","chictreat");
        packslipMap.put("Deal Ticket","dealticket");
        packslipMap.put("Buzz Steals","buzz");
        packslipMap.put("Star Style Deals","starstyle");
        packslipMap.put("Queens Deals","queendeals");
        packslipMap.put("EGlass","eglass");
        packslipMap.put("E-Glass","eglass");
        packslipMap.put("Ali Golden","aligolden");
        packslipMap.put("Vere Verto","vereverto");

        accountMap.put("Queens Deals",539);
        accountMap.put("Deal Ticket",540);
        accountMap.put("The Doctors Deals",542);
        accountMap.put("The Doctors",542);
        accountMap.put("Chic Treat",541);
        accountMap.put("EGlass",537);
        accountMap.put("E-Glass",537);
        accountMap.put("Star Style Deals",538);
        accountMap.put("Ali Golden",544);
        accountMap.put("Vere Verto",543);


    }
    public void internalExecute() {

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", OWDConstants.SMTPServer);

            URLName url = new URLName("pop3", "secure.emailsrvr.com", -1, "INBOX", "dealsegmentsorders@owd.com", "one7172");


            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(false);
            Store popStore = mailSession.getStore(url);

            popStore.connect();
            Folder inbox = popStore.getDefaultFolder();
            if (inbox == null)
                throw new MessagingException("No default folder");

            inbox = inbox.getFolder("INBOX");
            if (inbox == null)
                throw new MessagingException("Invalid folder");

            inbox.open(Folder.READ_WRITE);

            Message[] messages = {};
            int count = inbox.getMessageCount();
            log.debug("avpi got " + count + " messages...");
            if (count > 0)
                messages = inbox.getMessages();

            for (int i = 0; i < messages.length; i++) {
                int newOrders = 0;

                try {
                    String subject = messages[i].getSubject();
                    log.debug("got message: " + subject);

                    Address[] replies = messages[i].getFrom();

                    Multipart mp = null;
                    int parts = 1;
                    try {
                        MimeMessage message = (MimeMessage) messages[i];
                        //log.debug("got javax.mail.Message content=" + message.getContentType());
                        mp = (Multipart) (((MimeMessage) message).getContent());
                        //log.debug("got message content");
                        parts = mp.getCount();
                        //log.debug("got message partcount=" + parts);
                        //log.debug("got MimeMessage, partcount=" + parts + ", content-type=" + mp.getContentType() + ", subject=" + subject);
                    } catch (ClassCastException ecc1) {
                        //////log.debug("avpi imp got null mp");


                    }

                    InputStream in = null;
                    String partName = "";
                    for (int j = 0; j < parts; j++) {
                        //log.debug("checking part " + j);
                        if (mp != null) {
                            MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(j);
                            //////log.debug("got message bodypart");


                            if (part.getContentType().indexOf("binhex") >= 0) {
                                in = new BinHex4InputStream(part.getInputStream());
                                //////log.debug(((com.owd.BinHex4InputStream)in).getHeader());
                            } else {
                                in = part.getInputStream();
                            }
                            partName = part.getFileName();
                            if (partName == null) partName = "";
                            //////log.debug("got inputstream");
                            //log.debug("got part " + j + " , encoding=" + part.getEncoding() + ", content-type=" + part.getContentType() + " , description=" + part.getDescription() + " , disposition=" + part.getDisposition() + ", size=" + part.getSize() + ", name=" + part.getFileName());
                        } else {
                            in = messages[i].getInputStream();

                        }

                        try {
                            log.debug("partname:"+partName);
                            if (partName.toUpperCase().endsWith(".CSV")) {
                                StringBuffer sbx = new StringBuffer();

                                List results = processDataFile(new CSVReader(new BufferedReader(new InputStreamReader(in)), true));

                                if (results.size() > 0) {
                                    newOrders = 1;
                                }
                                Iterator it = results.iterator();
                                while (it.hasNext()) {
                                    sbx.append("\r\n" + it.next());
                                }

                                Vector emailsx = new Vector();
                                log.debug(sbx);
                                emailsx.add("Lchang@dealsegments.com");
                                emailsx.add("vstotz@owd.com");
                                emailsx.add("kspiroff@dealsegments.com");
                                        emailsx.add("jperera@dealsegments.com");
                                                emailsx.add("dsicustomerservice3@gmail.com");


                                for(Address from:replies)
                                {
                                    log.debug("From:"+from.toString());
                                    emailsx.add(from.toString());
                                }
                            //    emailsx.add("michael@sociablegroup.com");
                                log.debug(sbx.toString());
                                if(sbx.toString().length()<2)
                                {
                                    sbx = new StringBuffer();
                                    sbx.append("No orders were found in attached file");
                                }
                                Mailer.postMailMessage("DS Order Import Report (" + partName + ")", sbx.toString(), emailsx, "donotreply@owd.com");
                            } else {
                                //log.debug("Got non-CSV part");
                            }
                            messages[i].setFlag(Flags.Flag.DELETED, true);
                        } catch (Exception mimepartex) {
                            mimepartex.printStackTrace();
                        }
                    } //for each MIME part

                } catch (ClassCastException ecc) {
                    messages[i].setFlag(Flags.Flag.DELETED,true);
                    ecc.printStackTrace();
                    StringBuffer sb = new StringBuffer();
                    String stamper = OWDUtilities.getSQLDateTimeForToday();
                    sb.append("\nException: " + ecc.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
                    //////////log.debug("CUTImporter stamper="+stamper);
                    ecc.printStackTrace();
                    //      Mailer.postMailMessage("AVPIImporter import error", sb.toString(), "owditadmin@owd.com", "owditadmin@owd.com");

                } catch (Exception ex) {

                    ex.printStackTrace();
                    StringBuffer sb = new StringBuffer();
                    String stamper = OWDUtilities.getSQLDateTimeForToday();
                    sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
                    //////////log.debug("CUTImporter stamper="+stamper);
                    ex.printStackTrace();
                    Vector emails = new Vector();
                    emails.add("owditadmin@owd.com");
                    //     Mailer.postMailMessage("AVPIImporter import error", sb.toString(), emails, "owditadmin@owd.com");
                }
              //  if (newOrders == 0) {

              //  }
            }    //for each message


            inbox.close(true);


        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            //  Mailer.postMailMessage("AVPIImporter import error", sb.toString(), "owditadmin@owd.com", "owditadmin@owd.com");
        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            //   Mailer.postMailMessage("AVPIImporter import error", sb.toString(), "owditadmin@owd.com", "owditadmin@owd.com");
        }

    }

    private List processDataFile(DelimitedReader data) throws Exception {

        List resultsList = new ArrayList();


        DealSegmentsUploadOrdersData dataHandler = new DealSegmentsUploadOrdersData();
        dataHandler.init(data);

        //log.debug("toprocess " + dataHandler.getOrderCount());

        for (int orderIndex = 0; orderIndex < dataHandler.getOrderCount(); orderIndex++) {
            try {
                List resultL = processOrder(dataHandler, "535", OrderXMLDoc.kBackOrderAll, orderIndex);
                //log.debug(resultL);
            //    if (resultL.toString().indexOf(",") > 0 || resultL.toString().toUpperCase().indexOf("BACKORDER") >= 0 ||
             //           resultL.toString().toUpperCase().indexOf("ON HOLD") >= 0) {
                    //log.debug("adding to result list");
                    resultsList.add(resultL);
            //    }
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



    private List processOrder(DealSegmentsUploadOrdersData dataHandler, String clientID, String backorderRule, int orderIndex) {
        //returns list of two elements - client Order ID and response
        List response = new ArrayList();
        //add new
        String showName = dataHandler.getShowName(orderIndex).trim();
        if(accountMap.containsKey(showName))
        {
           clientID = ""+accountMap.get(showName);
        }

        if(!(OrderUtilities.orderRefnumExists(dataHandler.getOrderReference(orderIndex),clientID))) {
            response.add(dataHandler.getOrderReference(orderIndex));

            try {

                Order order = new Order(clientID);


                order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.backorder_rule = backorderRule;
                dataHandler.loadOrder(order, orderIndex);
                //log.debug("after load order");


                order.is_future_ship = 0;



                translateShipMethod(order,order.getShippingInfo().carr_service);
                if(packslipMap.keySet().contains(order.ship_operator))
                {
                    order.template = packslipMap.get(order.ship_operator);

                } else
                {
                    throw new Exception("The value '"+order.ship_operator+"' is not recognized as a valid pack slip template code.");
                }


                order.recalculateBalance();

                order.paid_amount = order.total_order_cost;
                order.is_paid = 1;
                log.debug("total:" + order.total_order_cost + "::paid:" + order.paid_amount);
                order.recalculateBalance();

                if(order.containsPhysicalItem()) {
                    String reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, true);
                    log.debug("reference=" + reference);
                    if (reference == null) {
                        if ((order.last_payment_error + " " + order.last_error).indexOf("reference already exists") < 1
                                &&
                                (order.last_payment_error + " " + order.last_error).indexOf("no physical items") < 1) {
                            log.debug("reporting error on import");
                            throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
                        } else {
                            //duplicate
                        }
                    } else {
                        log.debug("got valid order import");


                        if (order.is_future_ship == 1) {
                            response.add("[HELD ORDER] " + order.hold_reason);
                        } else if (order.is_backorder == 1) {


                            response.add("[BACKORDER] ");
                        } else {
                            response.add("[RELEASED] ");

                        }
                    }
                }else
                {
                    response.add("[SKIPPED - NO SHIPPABLE ITEMS] ");

                }

            } catch (Exception
                    ex) {


                ex.printStackTrace();


                response.add("[NOT IMPORTED] " + ex.getMessage());
            } finally {


            }
        } else {
            response.add(dataHandler.getOrderReference(orderIndex));

            response.add("[DUPLICATE - ORDER ALREADY EXISTS] " );
        }
        return response;
    }


    public void translateShipMethod(Order order, String oldMethod) throws Exception {

        try {
            OrderRater rater = new OrderRater(order);
            rater.setCalculateKitWeights(true);

            List<String> alternateShipMethodList = new ArrayList<String>();
            alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
            alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_STD");

            order.getShippingInfo().setShipOptions(RateShopper.rateShop(order, alternateShipMethodList)
                    , "Prepaid", "");        } catch (Exception ex) {
            throw ex;
        }
        log.debug("SELECTED:" + order.getShippingInfo().carr_service);
    }


}
