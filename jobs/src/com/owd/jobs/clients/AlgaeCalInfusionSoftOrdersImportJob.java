package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.batchimporters.RenuInfusionUploadData;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Feb 18, 2008
 * Time: 3:23:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlgaeCalInfusionSoftOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    Folder inbox = null;
    static int removeEmail = 0;

    public static void main(String[] args) {

        run();
     //   sendHCGEmail("Sheri Opitz","sopitz@owd.com");

    }

    public void internalExecute() {

        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", OWDConstants.SMTPServer);

            URLName url = new URLName("pop3", "secure.emailsrvr.com", -1, "INBOX", "algaeorders@owd.com", "one7172");

            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(false);

            Store popStore = mailSession.getStore(url);


            popStore.connect();

            inbox = popStore.getDefaultFolder();

            if (inbox == null)
                throw new MessagingException("No default folder");
            //log.debug("hello");
            inbox = inbox.getFolder("INBOX");
            if (inbox == null)
                throw new MessagingException("Invalid folder");

            inbox.open(Folder.READ_WRITE);

            Message[] messages = {};
            int count = inbox.getMessageCount();
            log.debug("Receive got " + count + " messages for ...");
            if (count > 0)
                messages = inbox.getMessages();

            for (Message message1 : messages) {
                try {
                    removeEmail = 0;


                    String subject = message1.getSubject();
                    log.debug("got message: " + subject);

                    MimeMessage message = (MimeMessage) message1;
                    //log.debug("got javax.mail.Message content=" + message.getContentType());
                    log.debug(message.getContent().toString());
                    //Check subject for no good orders
                    if ((subject.toUpperCase().contains("BAD SPAM EMAIL"))) {
                        log.debug("bad order email, remove it from the box ");
                        if(subject.contains("failed") || subject.contains("Offline"))
                        {
                           removeEmail = 1;
                        }   else
                        {
                        removeEmail = 0;
                        }
                    } else {
                        Multipart mp = (Multipart) (((MimeMessage) message).getContent());

                        int parts = mp.getCount();

                        for (int j = 0; j < parts; j++)

                        {
                            log.debug("getting message bodypart for part " + j);

                            try {
                                MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(j);
                                InputStream in = null;

                                in = part.getInputStream();

                                log.debug("got part " + j + " , encoding=" + part.getEncoding() + ", content-type=" + part.getContentType() + " , description=" + part.getDescription() + " , disposition=" + part.getDisposition() + ", size=" + part.getSize() + ", name=" + part.getFileName());
                                if(part.getFileName().endsWith(".csv"))
                                {
                                    log.debug("got match");
                                    BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                                 //   log.debug(""+bf.readLine());
                                 //     log.debug(""+bf.readLine());
                                    DelimitedReader orders = new CSVReader(bf, true);
                                    log.debug("order lines:"+orders.getRowCount());
                                    for (int orderRow = 0; orderRow < orders.getRowCount(); orderRow++)

                                {
                                   // log.debug("got cols=" + orders.getRowSize(orderRow) + " for row " + orderRow);
                                    for (int c = 0; c < orders.getRowSize(orderRow); c++) {
                                        log.debug(c+":" + orders.getStrValue(c, orderRow, "xxx") + ":");

                                    }

                                }
                                //got valid attachment to process
                                    List results = processDataFile(orders);

                                           // log.debug(sb.toString());
                                          StringBuffer sbx = new StringBuffer();

                                           Iterator it = results.iterator();
                                           while (it.hasNext()) {
                                               sbx.append("\r\n" + it.next());
                                           }
                                           Vector emailsx = new Vector();
                                           emailsx.add("owditadmin@owd.com");
                                         //  emailsx.add("alerts@dogeared.com");
                                           //  emailsx.add("ordererror@galapagosboutique.com");
                                        log.debug(sbx.toString());
                                           if (results.size() > 0) {
                                               Mailer.postMailMessage("OWD-AlgaeCal-Infusionsoft Import Status Notification", "The following orders may need attention:\r\n\r\n" + sbx.toString(), emailsx, "do-not-reply@owd.com");
                                               //alerts@dogeared.com
                                               removeEmail = 0;
                                           } else
                                           {
                                               removeEmail = 1;
                                           }



                              }//end if fulfillment.csv
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                        //If good order delete email

                    }
                       if (removeEmail == 1) {
                            log.debug("Removing Email");
                             message1.setFlag(Flags.Flag.DELETED, true);
                        }
                } catch (Exception exx) {
                    log.debug("bad message");
                    exx.printStackTrace();
                }
            }    //for each message


                    inbox.close(true);




                } catch (Exception ex) {

                    ex.printStackTrace();
                    StringBuffer sb = new StringBuffer();
                    String stamper = OWDUtilities.getSQLDateTimeForToday();
                    sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
                    //////////log.debug("CUTImporter stamper="+stamper);
                    ex.printStackTrace();
                  //  Mailer.postMailMessage("renu import error import error", sb.toString(), "casetracker@owd.com", "dnickels@owd.com");
                } finally {
                    try {
                        inbox.close(true);
                    } catch (Exception e) {

                    }
                    HibernateSession.closeSession();
                }

            }


 private List processDataFile(DelimitedReader data) throws Exception {

        List resultsList = new ArrayList();


        RenuInfusionUploadData dataHandler = new RenuInfusionUploadData();
        dataHandler.init(data);

        log.debug("TOPROCESS: " + dataHandler.getOrderCount());

        for (int orderIndex = 0; orderIndex < dataHandler.getOrderCount(); orderIndex++) {
            try {

                List resultL = processOrder(dataHandler,  "266", OrderXMLDoc.kBackOrderAll, orderIndex);
                log.debug(resultL);
                if (resultL.toString().indexOf(",") > 0 || resultL.toString().toUpperCase().indexOf("BACKORDER") >= 0 ||
                        resultL.toString().toUpperCase().indexOf("ON HOLD") >= 0) {
                    //log.debug("adding to result list");
                    resultsList.add(resultL);
                }
                log.debug("processed " + (orderIndex + 1));
                //record success
            } catch (Exception ex) {
                //record error
                ex.printStackTrace();
                log.debug("Uncaught error at row " + orderIndex + ":" + ex.getMessage());
            }


        }
        return resultsList;
    }

    private List processOrder(RenuInfusionUploadData dataHandler, String clientID, String backorderRule, int orderIndex) {
        //returns list of two elements - client Order ID and response
        List response = new ArrayList();
        //add new
        response.add(dataHandler.getOrderReference(orderIndex));

        try {

            Order order = new Order(clientID);
            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
            //order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.backorder_rule = backorderRule;

            //order.is_future_ship=1;
            dataHandler.loadOrder(order, orderIndex);
            //log.debug("after load order");

            order.recalculateBalance();
            log.debug("IS PAID:" + order.is_paid);

                //paid for - push it through!
                order.bill_cc_type = "CL";
                order.check_num = "999999";
                order.paid_amount = order.total_order_cost;
                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.is_paid = 1;


            order.getShippingInfo().setShipOptions("Priority Mail", "Prepaid", "");

            if (order.getShippingAddress().country.equalsIgnoreCase("United States"))
                order.getShippingAddress().country = "USA";
            if (order.getBillingAddress().country.equalsIgnoreCase("United States"))
                order.getBillingAddress().country = "USA";

            if (order.getShippingAddress().country.equalsIgnoreCase("CHINA"))
            {
                order.getShippingAddress().country = "CHINA PEOPLES REPUBLIC OF";
            }

            if (order.getShippingAddress().country.equalsIgnoreCase("KOREA"))
            {
                order.getShippingAddress().country = "KOREA SOUTH";
            }


            float pkgWgt = 0;
            for (int j = 0; j < order.skuList.size(); j++)
            {
                LineItem iteml = (LineItem) order.skuList.elementAt(j);
                pkgWgt += (iteml.quantity_request) * Inventory.getKittedWeight(iteml.inventory_fkey);

            }

            //////log.debug("got pkg wgt "+pkgWgt);
            if (pkgWgt < ((float) 0.8))
            {
                order.getShippingInfo().setShipOptions("First Class", "Prepaid", "");
            }

            if (pkgWgt >= ((float) 2.0))
            {
                order.getShippingInfo().setShipOptions("UPS Ground", "Prepaid", "");
            }

            if (order.getShippingAddress().country.equalsIgnoreCase("NETHERLANDS THE"))
            {
                order.getShippingAddress().country = "NETHERLANDS";
            }
            if (order.getShippingAddress().country.equalsIgnoreCase("BRUNEI DARUSSALAM"))
            {
                order.getShippingAddress().country = "BRUNEI";
            }

            //String cn = CountryNames.getCountryName(anOrder.getShippingAddress().country);
            if (!(order.getShippingAddress().isUS()))
            {
                order.getShippingInfo().setShipOptions("USPS Priority Mail Express International", "Prepaid", "");
                order.is_future_ship = 1;
            }




            if (order.total_order_cost > 500.00f)
            {
                order.is_future_ship = 1;
            }

            String state = order.getShippingInfo().shipAddress.state;
            if (state.equalsIgnoreCase("HI") ||
                    state.equalsIgnoreCase("AK") ||
                    state.equalsIgnoreCase("Alaska") ||
                    state.equalsIgnoreCase("Hawaii"))
            {
                order.is_future_ship = 1;
             //   Mailer.sendMail("HL Distribution Order Imported On Hold: " + order.order_refnum, "Order Reference: " + order.order_refnum + "\r\nPlaced on hold due to HI or AK shipping destination", "jeanne.roberts@shaw.ca", "do-not-reply@owd.com");


            }

            if (order.getShippingAddress().isPOAPO() && (order.getShippingAddress().isInternational() || order.getShippingInfo().carr_service.toUpperCase().indexOf("FEDEX") >= 0
                    || order.getShippingInfo().carr_service.toUpperCase().indexOf("UPS") >= 0))
            {
                order.is_future_ship = 1;
            //    Mailer.sendMail("HL Distribution Order Imported On Hold: " + order.order_refnum, "Order Reference: " + order.order_refnum + "\r\nPlaced on hold due to possible PO or APO address for Intl/UPS/FedEx order", "hlorders@shaw.ca", "do-not-reply@owd.com");
            //    Mailer.sendMail("HL Distribution Order Imported On Hold: " + order.order_refnum, "Order Reference: " + order.order_refnum + "\r\nPlaced on hold due to possible PO or APO address for Intl/UPS/FedEx order", "jeanne.roberts@shaw.ca", "do-not-reply@owd.com");


            }

            order.postDateHoursDelay = 24;


            log.debug(""+order.toString());
            // Add "Triple Power Brochure" to all orders

            if(order.getOrderRefNum().toUpperCase().endsWith("BC")) {
                order.addInsertItemIfAvailable("Triple Power Brochure", 1);
            }

            String reference = order.saveNewOrder( OrderUtilities.kHoldForPayment, false);
            log.debug("reference="+reference);
            if (reference == null) {
                if(order.last_error.indexOf("reference already exists")>=0)  {

                }   else
                {
                    response.add("[ERROR] " + "save order error - "+order.last_error);
                }
            } else {
                log.debug("got valid order import");


                if (order.is_future_ship == 1) {
                  //  response.add("[HELD ORDER] " + order.hold_reason);
                }
                if (order.is_backorder == 1) {
                    StringBuffer sb = new StringBuffer();

                    for (int i = 0; i < order.skuList.size(); i++) {
                        LineItem item = (LineItem) order.skuList.elementAt(i);
                        int available = OrderUtilities.getAvailableInventory(item.inventory_fkey, FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId());
                        if (available < 0) available = 0;

                        if ((item.quantity_request) > available) {
                            sb.append("\r\n    SKU:" + item.client_part_no + " Requested:" + (item.quantity_request) + " Available:" + available);
                        }


                    }
                   // response.add("[BACKORDER] " + sb);


                }
            }


        } catch (Exception
                ex) {


            ex.printStackTrace();

             if(!(ex.getMessage().contains("Duplicate order reference"))) {
                 response.add("[NOT IMPORTED] " + ex.getMessage());
             }
        } finally {


        }
        return response;
    }


    }
