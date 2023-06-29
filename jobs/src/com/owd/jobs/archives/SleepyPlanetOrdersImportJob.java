package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.Mailer;
import com.owd.core.OWDConstants;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.utilities.lineItemBean;
import com.owd.jobs.jobobjects.utilities.orderInfoBean;
import org.apache.commons.lang.StringUtils;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Apr 23, 2007
 * Time: 4:21:28 PM
 * To change this template use File | Settings | Fes.
 */
public class SleepyPlanetOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    Folder inbox = null;
    static int removeEmail = 0;
    static String isGift = "0";
    static String giftMessage = "";
    static boolean singleDVD = false;

    public static void main(String[] args) {

        run();
    }

    public void internalExecute() {

        List results = new ArrayList();
        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", OWDConstants.SMTPServer);

            URLName url = new URLName("pop3", "pop.emailsrvr.com", -1, "INBOX", "sleepyplanet@orders.owd.com", "one7172");

            javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props, null);
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

            javax.mail.Message[] messages = {};
            int count = inbox.getMessageCount();
            log.debug("Receive got " + count + " messages for Sleepy Planet...");
            if (count > 0)
                messages = inbox.getMessages();

            for (int i = 0; i < messages.length; i++) {
                removeEmail = 0;
                List l = null;

                String subject = messages[i].getSubject();
                log.debug("got message: " + subject);

                javax.mail.internet.MimeMessage message = (javax.mail.internet.MimeMessage) messages[i];
                log.debug("got javax.mail.Message content=" + message.getContentType());
                //log.debug(message.getContent().toString());


                javax.mail.Multipart mp = null;
                int parts = 1;
                try {
                    log.debug("got javax.mail.Message content=" + message.getContentType());
                    mp = (javax.mail.Multipart) (((javax.mail.internet.MimeMessage) message).getContent());
                    //log.debug("got message content");
                    if (mp.getContentType().indexOf("multipart") >= 0) {
                        mp = (javax.mail.Multipart) mp.getBodyPart(0).getContent();
                    }

                    parts = mp.getCount();
                    log.debug("got message partcount=" + parts);
                    log.debug("got MimeMessage, partcount=" + parts + ", content-type=" + mp.getContentType() + ", subject=" + subject);
                } catch (ClassCastException ecc1) {
                    log.debug(" imp got null mp");


                }
                String partName = "";
                for (int j = 0; j < parts; j++) {
                    InputStream in = null;
                    //log.debug("checking part "+j);
                    if (mp != null) {
                        MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(j);
                        //log.debug("got message bodypart");
                        log.debug("part type:" + part.getContentType());


                        if (part.getContentType().indexOf("text/plain") >= 0) {
                            in = part.getInputStream();
                        }
                        partName = part.getFileName();
                        //log.debug("got inputstream");
                        log.debug("got part "+j+" , encoding="+part.getEncoding()+", content-type="+part.getContentType()+" , description="+part.getDescription()+" , disposition="+part.getDisposition()+", size="+part.getSize()+", name="+part.getFileName());
                    } else {
                        if (messages[i].getContentType().indexOf("text/plain") >= 0) {
                            log.debug("message type:" + messages[i].getContentType());
                            in = messages[i].getInputStream();
                            //com.owd.crypto.UUDecoder decoder = new com.owd.crypto.UUDecoder();
                        }
                        //byte[] bytes = decoder.decodeBuffer(in);
                        //in = new ByteArrayInputStream(bytes);

                    }

                    if (in != null) {
                        BufferedReader content = new BufferedReader(new InputStreamReader(in));

                        l = (processOrder((new BufferedReader(new InputStreamReader(in))), "356", OrderXMLDoc.kPartialShip));
                        if (l.toString().indexOf(",") > 0) {
                            //log.debug("adding to result list");
                            results.add(l);
                             removeEmail = 1;
                        } else {
                            //log.debug("success");

                            removeEmail = 1;
                        }

                        //If good order delete email
                        if (removeEmail == 1) {
                            //log.debug("Removing Email");
                            messages[i].setFlag(Flags.Flag.DELETED, true);
                        }


                    }
                    ////yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy
                    //  log.debug(message.getContent().toString());


                }
            }    //for each message


            inbox.close(true);

            /////////////xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            //loop through emails
            StringBuffer sbx = new StringBuffer();


            Iterator it = results.iterator();
            while (it.hasNext()) {
                sbx.append("\r\n" + it.next());
            }
            Vector emailsx = new Vector();
            emailsx.add("dnickels@owd.com");


            if (results.size() > 0) {
                //  Mailer.postMailMessage("Sleepy Planet Import Error", "The following orders were not imported due to the indicated error:\r\n\r\n" + sbx.toString(), emailsx, "do-not-reply@owd.com");
            }


        } catch (Exception ex) {

            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            Mailer.postMailMessage("SleepyPlanet import error import error", sb.toString(), "owditadmin@owd.com", "trouble@owd.com");
        } finally {
            try {
                inbox.close(true);
            } catch (Exception e) {

            }
        }

    }


    private List processOrder(BufferedReader br, String clientID, String backorderRule) {
        //returns list of two elements - client Order ID and response
        //log.debug("inprocessOrder");
        List response = new ArrayList();
        giftMessage = "";
        isGift="0";
        singleDVD = false;
        //add new
        //  response.add(dataHandler.getOrderReference(orderIndex));
        orderInfoBean o = new orderInfoBean();
        try {
            Order order = new Order(clientID);
            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
            //order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.backorder_rule = backorderRule;

            //order.is_future_ship=1;
            //log.debug("before loadorder");
            o = loadOrder(br);
            //log.debug("Loading OWD ORDER");
            o.loadOwdOrder(order);
            //log.debug("order loaded going to save it now");

            order.recalculateBalance();
            order.is_gift = isGift;
            order.gift_message = giftMessage;
            order.paid_amount = order.total_order_cost;
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.is_paid = 1;
            order.order_type = "Sleepy Planet Download";
            // order.is_future_ship=1;
            //  order.getShippingInfo().setShipOptions("USPS First-Class Mail","Prepaid","");

            // order.order_type="AIS Order Import";

            if(!(order.getLineCount()==1 && order.containsSKU("SE-QA-ONLINE")))
            {
            if (o.getItems().size() >= 1) {
                String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
                //log.debug(reference);
                if (reference == null) {
                    if ((order.last_payment_error + " " + order.last_error).indexOf("reference already exists") < 1) {
                        throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
                    }
                }
            }else
            {

            }
            }


        } catch (Exception
                ex) {
            ex.printStackTrace();


            response.add(o.getOrderRef() + ", " + ex.getMessage());

        } finally {


        }
        return response;
    }

    public static orderInfoBean loadOrder(BufferedReader br) throws Exception {
        String s;
        orderInfoBean o = new orderInfoBean();
        String emailpart = "main";
        //Loading line items
        boolean keepgoing = true;
        boolean items = false;

        List lines = new ArrayList();
        BufferedReader rr = new BufferedReader(br);
        rr = br;
        String currLine = "";
        while (keepgoing && (s = rr.readLine()) != null) {
            log.debug(s);

            if (s.indexOf("Order number:") >= 0) {
                o.setOrderRef(s.substring(s.indexOf(":") + 1).trim());
                log.debug("Got ORder Ref:" + o.getOrderRef());

            }


            if (items) {
                if (s.indexOf("Sub-total") >= 0 || (keepgoing && s.indexOf("Total:") >= 0)) {
                    log.debug(s);
                    o.setItems(lines);
                    keepgoing = false;


                } else {
                    //log.debug(lines.size());
                    if (s.indexOf("$") >= 0) {
                        currLine = currLine + "\r" + s.trim();
                        currLine = currLine.replaceAll("\r&nbsp;", "<SKU>");
                        log.debug("currLine:" + currLine);
                        lines.add(parseItem(currLine));
                        currLine = "";
                    } else {
                        if (s.trim().length() > 1) {
                            currLine = currLine + "\r" + s.trim();
                        }
                    }

                }
            }
            if (s.indexOf("Quantity") > 0 && !items) {
                items = true;
            }
        }
        //done with line items, do the order info;

        int shipLine = 0;
        int billLine = 0;

        while ((s = br.readLine()) != null) {
            log.debug("S:" + s);
            loadShipping(s.trim(), o);
            if (!s.equals("")) {
                ////log.debug(s.charAt(0)+":  length "+s.length());
                if (s.indexOf("Shipping Information") >= 0) {
                    //get billing email
                    log.debug(":::"+s);
                    s = s.trim();
                    o.setBillingEmail(s.indexOf("@") > 0 ? s.substring(0, s.indexOf(" ")).trim() : "");
                    log.debug("got billing email "+o.getBillingEmail());
                    emailpart = "Ship";
                } else if (s.indexOf("Billing Information") >= 0) {
                    emailpart = "Bill";
                } else if (emailpart.equals("main")) {

                    loadOther(s, o);
                }
                if (emailpart.equals("Ship")) {
                    //  System.out.print("Going shippinginfo line");
                    if (shipLine == 1) {
                        if (s.indexOf("&nbsp;") >= 0) {
                            o.setShippingFirstName(s.substring(0, s.indexOf("&nbsp;")).trim());
                            o.setShippingLastName(s.substring(s.indexOf("&nbsp;") + 6).trim());

                        } else {
                            if (s.indexOf(" ") > 0) {
                                o.setShippingFirstName(s.substring(0, s.indexOf(" ")).trim());
                                o.setShippingLastName(s.substring(s.indexOf(" ")).trim());
                            } else {
                                o.setShippingFirstName(s);
                            }
                        }
                    } else if (shipLine == 2) {
                        o.setShippingAddress1(s.trim());
                    } else if (shipLine == 3) {
                        s = s.replaceAll("&nbsp;", " ");
                        log.debug("ss:" + s);
                        o.setShippingCity(s.substring(0, s.indexOf(",")).trim());
                        s = s.substring(s.indexOf(",") + 1).trim();
                        o.setShippingState(s.substring(0, s.indexOf(" ")).trim());
                        s = s.substring(s.indexOf(" ") + 1).trim();
                        o.setShippingZip(s.trim());
                    } else if (shipLine == 4) {
                        o.setShippingCountry(s.trim());
                    } else if (shipLine == 5) {
                        o.setBillingPhone1(s.trim());
                    }
                    shipLine++;
                }
                if (emailpart.equals("Bill")) {
                    //      //log.debug("doing billing stuff");
                    if (billLine == 1) {
                        if (s.indexOf("&nbsp;") >= 0) {
                            o.setBillingFirstName(s.substring(0, s.indexOf("&nbsp;")).trim());
                            o.setBillingLastName(s.substring(s.indexOf("&nbsp;") + 6).trim());

                        } else {
                            if (s.indexOf(" ") > 0) {
                                o.setBillingFirstName(s.substring(0, s.indexOf(" ")).trim());
                                o.setBillingLastName(s.substring(s.indexOf(" ")).trim());
                            } else {
                                o.setBillingFirstName(s);
                            }
                        }
                    } else if (billLine == 2) {
                        o.setBillingAddress1(s.trim());
                    } else if (billLine == 3) {
                        s = s.replaceAll("&nbsp;", " ");
                        log.debug("ss:" + s);
                        o.setBillingCity(s.substring(0, s.indexOf(",")).trim());
                        s = s.substring(s.indexOf(",") + 1).trim();
                        o.setBillingState(s.substring(0, s.indexOf(" ")).trim());
                        s = s.substring(s.indexOf(" ") + 1).trim();
                        o.setBillingZip(s.trim());
                    } else if (billLine == 4) {
                        o.setBillingCountry(s.trim());
                    }
                    billLine++;
                }

                //end if not null
            } else {////log.debug("null line next");
            }

        }

        log.debug("here is what we have");
        log.debug(o.getShippingandHandling());
        log.debug(o.getTax());
        log.debug(o.getOrderRef());
        log.debug(o.getClientOrderNum());
        log.debug(o.getBillingAddress1());
        log.debug(o.getShippingAddress1());
        log.debug(o.getShippingMethod());
        log.debug(o.getItems().size() + " lines");
        log.debug("total: " + o.getGrandTotal());
        return o;
    }


    public static void loadOther(String s, orderInfoBean o) {


        if (s.indexOf("Tax") >= 0) {
            o.setTax(colonSplit(s.replaceAll("[$]", ":")));
        }
        if (s.indexOf("Shipping") >= 0 && s.indexOf("Method") < 0) {
            o.setShippingandHandling(colonSplit(s.replaceAll("[$]", ":")));
        }


    }


    public static void loadOrderId(String s, orderInfoBean o) {
        //log.debug("loading order");
        String[] sa = s.split(":");
        //log.debug(sa.length);
        //o.setOrderRef(StringUtils.replace(sa[1],"CUST NO","").trim());
        o.setCustomerNumber(StringUtils.replace(sa[2], "OrderNo", "").trim());
        // o.setClientOrderNum(StringUtils.replace(sa[3],"***","").trim());
        o.setOrderRef(StringUtils.replace(sa[3], "***", "").trim());
    }

    public static void loadShipping(String a, orderInfoBean o) throws Exception {


        if (a.equalsIgnoreCase("EXPRESS SHIPPING")) {
            o.setShippingMethod("USPS Priority Mail Express");
        }
        if (a.equalsIgnoreCase("OVERNIGHT SHIPPING")) {
            o.setShippingMethod("UPS Next Day Air Saver");
        }
        if (a.equalsIgnoreCase("OUTSIDE UNITED STATES")) {
            o.setShippingMethod("USPS Priority Mail International");
        }
        if (a.equalsIgnoreCase("PRIORITY MAIL")) {
            o.setShippingMethod("USPS Priority Mail");
        }

        if (a.equalsIgnoreCase("STANDARD MAIL")) {
            o.setShippingMethod("USPS Media Mail Single-Piece");
        }

        /*if(s.indexOf("Name:")>=0){
             boolean one = false;
             String[] sa = s.split(":\\s");
             String a = colonSplit(s);
          String[] last= a.split("^\\w*\\s");
             if(last.length>1){
          o.setShippingLastName(last[1]);
             }else{
                 one=true;
             }
         if(one){
             o.setShippingFirstName(sa[1]);
         }  else{
             String[] first = a.split("\\s\\w*");
             o.setShippingFirstName(first[0]);
         }

        }*/


    }

    public static void loadBilling(String s, orderInfoBean o) throws Exception {


        if (s.indexOf("Name:") >= 0) {
            boolean one = false;
            String[] sa = s.split(":\\s");
            String a = colonSplit(s);
            String[] last = a.split("^\\w*\\s");
            if (last.length > 1) {
                o.setBillingLastName(last[1]);
            } else {
                one = true;
            }
            if (one) {
                o.setBillingFirstName(sa[1]);
            } else {
                String[] first = a.split("\\s\\w*");
                o.setBillingFirstName(first[0]);
            }

        }
        if (s.indexOf("Company:") >= 0) {
            String a = colonSplit(s);
            o.setBillingCompany(a);
        }
        if (s.indexOf("Street:") >= 0) {
            String a = colonSplit(s);
            o.setBillingAddress1(a);
        }
        if (s.indexOf("Street2:") >= 0) {
            String a = colonSplit(s);
            o.setBillingAddress2(a);
        }
        if (s.indexOf("City:") >= 0) {
            String a = colonSplit(s);
            o.setBillingCity(a);
        }
        if (s.indexOf("State:") >= 0) {
            String a = colonSplit(s);
            o.setBillingState(a);
        }
        if (s.indexOf("Postal Code:") >= 0) {
            String a = colonSplit(s);
            o.setBillingZip(a);
        }
        if (s.indexOf("Country:") >= 0) {
            String a = colonSplit(s);
            o.setBillingCountry(getCountry(a));
        }
        if (s.indexOf("Phone:") >= 0) {
            String a = colonSplit(s);
            o.setBillingPhone1(a);
        }
        if (s.indexOf("Email:") >= 0) {
            String a = colonSplit(s);
            o.setBillingEmail(a);
        }
        if (s.indexOf("Fax:") >= 0) {
            String a = colonSplit(s);
            o.setBillingFax(a);
        }

    }


    public static String colonSplit(String s) {
        String a = "";
        String[] sa = s.split("^(\\w*\\s*)*:\\s");
        if (sa.length > 1) {
            //  //log.debug("doing2");
            a = sa[1].trim();
        } else {
            String[] sb = s.split("^(\\w*\\s*)*:");
            if (sb.length > 1) {

                a = sb[1].trim();
            }
        }


        return a;
    }

    public static lineItemBean parseItem(String s) {
        lineItemBean lb = new lineItemBean();
        lb.setDesc("");
        //   Pattern pat = Pattern.compile("(\\S{2,}\\s)");
        log.debug("PARSING");
        s = s.replaceAll("[\r\n]", ":");


        log.debug(s);
        log.debug("PARSING1");
        String[] sa = s.split(":");
        log.debug("PARSING2");
        for (String aSa : sa) {
            log.debug("PARSING LOOP " + aSa);
            if (aSa.indexOf("<SKU>") >= 0) {
                lb.setInventory_num(aSa.replaceAll("<SKU>", "").trim());
            }
            if (aSa.indexOf("$") >= 0 && aSa.indexOf("$") != aSa.lastIndexOf("$")) {
                aSa = aSa.substring(aSa.indexOf("$"));
                String[] details = aSa.split("\\t");

                lb.setPrice(details[0].replaceAll("[$]", ""));
                lb.setQuanity(details[1]);

                lb.setSubtotal("" + (Double.valueOf(lb.getPrice()) * Double.valueOf(lb.getQuanity())));


            }

        }


        if (lb.getInventory_num().equals("SE-DVD")) singleDVD = true;

        /*if(lb.getInventory_num().indexOf("GIFT")>=0){
            isGift="1";
           giftMessage=lb.getDesc();
            if(lb.getInventory_num().equals("GIFTWRAP1")) lb.setDesc("Pastel Gift Wrap");
            if(lb.getInventory_num().equals("GIFTWRAP2")) lb.setDesc("Bubble Gift Wrap");
        }*/
        log.debug("Line:" + lb);
        if (lb.getInventory_num().endsWith("-ONLINE")) {
            lb = null;
        }
        return lb;
    }

    public static String getPart(String s, int p) {
        String[] b = s.split(";");
        return b[p];

    }

    public static String getCountry(String s) throws Exception {
        if (s.equals("NZL")) s = "NZ";
        if (s.equals("AUS")) s = "AU";
        if (!CountryNames.exists(s)) {
            throw new Exception("Country name not recognized: " + s);
        }

        return CountryNames.getCountryName(s);


    }

    public void test() {
        StringBuffer sb = new StringBuffer();
        sb.append("Order Notification\n" +
                "========================================================================\n" +
                "\n" +
                "An order has been received:\n" +
                "\n" +
                "\n" +
                " \n" +
                " Renee Maetaga \n" +
                " 12 Jasper Corner \n" +
                " CanningVale, WA 6155\n" +
                " AUS\n" +
                "\n" +
                " \n" +
                "\n" +
                " \n" +
                "\n" +
                " *** ORDER ID: 47AFCE4882B71 CUST NO: 1928 OrderNo: 1201 ***\n" +
                "Quantity Prod Code Product Name                    Unit Price Subtotal  \n" +
                "-------- --------- ------------------------------- ---------- ----------\n" +
                "       1      SE-BK Sleepeasy Solution Book                     14.95      14.95\n" +
                " \n" +
                "\n" +
                "Subtotal:   14.95\n" +
                "Taxes:       0.00\n" +
                "Shipping:   17.00\n" +
                "Total:      31.95\n" +
                "\n" +
                " \n" +
                "\n" +
                "******* Payment: creditcard *******\n" +
                "\n" +
                " \n" +
                "\n" +
                "*** BILLING INFO: \n" +
                "\n" +
                "PO Number: \n" +
                "Company: \n" +
                "Name: Renee Maetaga\n" +
                "Street: 12 Jasper Corner\n" +
                "Street2: \n" +
                "City: CanningVale\n" +
                "State: WA\n" +
                "Postal Code: 6155\n" +
                "Country: AU\n" +
                "Phone: 0431478366\n" +
                "Fax: \n" +
                "Email: rmaetaga@smartsoftware.com.au\n" +
                "\n" +
                " \n" +
                "\n" +
                "*** SHIPPING INFO:\n" +
                "\n" +
                "Shipping: 6\n" +
                "Shipping Method: \n" +
                "Shipping Option: International\n" +
                "First Name: Renee\n" +
                "Last Name: Maetaga\n" +
                "Company: \n" +
                "Street: 12 Jasper Corner\n" +
                "Street2: \n" +
                "City: CanningVale\n" +
                "State: WA\n" +
                "Postal Code: 6155\n" +
                "Country: AU\n" +
                " \n" +
                "\n" +
                "Thank you for your order!"
        );


        processOrder((new BufferedReader(new StringReader(sb.toString()))), "356", OrderXMLDoc.kPartialShip);

    }

}
