package com.owd.jobs;

import com.owd.core.managers.AWS_S3Api;
import com.owd.jobs.jobobjects.clients.StanleyBlackAndDeckerService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Event;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.clients.DogearedEmailSender;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.IntegerType;


import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 30, 2004
 * Time: 4:14:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomerShipmentNotificationJob extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();


    public CustomerShipmentNotificationJob() {
    }

    static String sweepSQL = "\tSELECT\n" +
            "\tDISTINCT(order_fkey) AS ID\n" +
            "FROM\n" +
            "\towd_order_track t (NOLOCK)\n" +
            "JOIN owd_order o (NOLOCK)\n" +
            "\tON\n" +
            "\t(order_fkey = order_id\n" +
            "\t\tand o.is_void = 0\n" +
            "\t\tand t.is_void = 0\n" +
            "\t\tand ISNULL(o.is_shipping,\n" +
            "\t\t0)= 0 )\n" +
            "WHERE\n" +
            "\temail_sent = 0\n";


    public void internalExecute() {

        try {
            List<Integer> idList = HibernateSession.currentSession().createSQLQuery(sweepSQL).addScalar("ID", new IntegerType()).list();
            log.debug("got list size "+idList.size());

            for (Integer ids:idList) {
                sendMail(ids);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }

    public static void main(String[] args) throws Exception {

        //  run();
        //  sendAlgaeCalEmail((OwdOrder)HibernateSession.currentSession().load(OwdOrder.class,7783138), "vivian@algaecal.com", "support@algaecal.com");
        //  sendAlgaeCalEmail((OwdOrder)HibernateSession.currentSession().load(OwdOrder.class,7036091), "owditadmin@owd.com","stewart@algaecal.com");
        //  byte[] data = AWS_S3Api.getObjectFromBucket("owdmailimage","RenuHerbsInstructions.pdf");
        //  sendLifeSpanEmail((OwdOrder)HibernateSession.currentSession().load(OwdOrder.class,12371411),"dnickels@owd.com","support@xendurance.com");
        //  sendMail(24239187);
        //  sendMail(7783138);
    }



    static void sendMail(Integer orderID) {
        String message;
        String subject = "";
        StringBuffer header = new StringBuffer();
        StringBuffer items = new StringBuffer();
        Connection cxn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String orderReference = "";
        String carrier = "";
        String shipDate = "";
        String tracker = "";
        String toEmail = "";
        String ccEmail = "";
        String bccEmail = "";
        String clientID = "";
        String returnEmail = "";
        String custName = "";
        String footer = "";
        String toShipEmail = "";
        String carrier_code = "";
        int packages = 0;
        boolean gotOrder = false;
        boolean gotItems = false;
        boolean gotFooter = false;
        boolean hasTracker = false;

        log.debug("got order ID "+orderID);

        if (null != orderID) {

            try {
                cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();

                rs = HibernateSession.getResultSet("select ISNULL(order_refnum,order_num), CONVERT(varchar,MIN(o.shipped_on),107),MIN(carr_service),MIN(o.tracking_nums),ISNULL(MIN(o.bill_email_address),''),\n" +
                        "                         MIN(client_id),ISNULL(MIN(ship_email_from),''),ISNULL(MIN(ship_email_cc),''),ISNULL(MIN(ship_email_bcc),''),\n" +
                        "                        ISNULL(MIN(o.bill_first_name+' '+o.bill_last_name),''),ISNULL(MIN(s.ship_email_address),''),min(o.ship_packs),min(carrier_code)\n" +
                        "                        from  owd_order o \n" +
                        "                        join owd_order_track t on t.order_fkey=order_id and t.is_void=0 join owd_order_ship_info s on (order_id = s.order_fkey) \n" +
                        "                         join owd_client c on (client_id = client_fkey) \n" +
                        "                         where  o.is_void = 0 and order_id ="+orderID+" " +
                        "and t.email_sent =0 " +
                        " and c.ship_email = 1\n" +
                        "                         group by  order_refnum,order_num   ");

                if (rs.next()) {
                    //log.debug("got result");
                    orderReference = rs.getString(1);

                    log.debug("Sending shipped email for orderref "+orderReference);

                    shipDate = rs.getString(2);

                    carrier = rs.getString(3);

                    tracker = rs.getString(4);

                    toEmail = rs.getString(5);

                    clientID = rs.getString(6);

                    returnEmail = rs.getString(7);

                    ccEmail = rs.getString(8);

                    bccEmail = rs.getString(9);

                    custName = rs.getString(10);

                    toShipEmail = rs.getString(11);

                    packages = rs.getInt(12);

                    carrier_code = rs.getString(13);

                    gotOrder = true;

                    if (tracker.length() < 10) {

                        tracker = "Not Available";

                        hasTracker = false;

                    }

                    subject = getEmailSubject(orderReference, clientID);


                    getEmailHeader(header, orderReference, shipDate, carrier, packages, tracker, clientID, custName, carrier_code);

                    HibernateSession.closeStatement();


                    stmt = cxn.createStatement();


                    stmt.execute("select ship_email_ftr  from owd_client "

                            + " where client_id = " + clientID);

                    rs = stmt.getResultSet();

                    if (rs.next()) {

                        footer = rs.getString(1);

                        if ("NULL".equalsIgnoreCase(footer)) footer = "";

                        gotFooter = true;

                    }

                    rs.close();


                    stmt.close();

                    //get line item info

                    stmt = cxn.createStatement();


                    stmt.execute("select inventory_num, quantity_actual,ISNULL(description,\"\"), parent_line  from owd_line_item "

                            + " where order_fkey = " + orderID + " and quantity_actual > 0");

                    rs = stmt.getResultSet();

                    while (rs.next()) {

                        getItems(rs, items, clientID);


                        gotItems = true;

                    }

                    if(clientID.equals("354")){
                        items.append("</table>");
                    }else{
                        items.append("\n");
                    }
                    rs.close();


                    stmt.close();


                }

                //update and post
                message = header.toString() + items.toString() + footer;
                stmt = cxn.createStatement();

                int rowsUpdated = 0;





                if (true) {



                    //log.debug("getting addresses");
                    //log.debug("items:" + items);
                    //log.debug("orderref:" + orderReference);
                    //log.debug("tomail:" + toEmail);
                    //log.debug("gotfoot:" + gotFooter);
                    if (items.length() > 0 && orderReference.length() > 0 && ((clientID.equals("345") && toShipEmail.indexOf("@")>0) || toEmail.indexOf("@") > 0) && gotOrder && gotItems && gotFooter) {
                        //log.debug("in addresses");
                        String addressee = toEmail;

                        Vector ccs = new Vector();

                        Vector bccs = new Vector();

                        String areturnEmail = "";

                        if(returnEmail == null) returnEmail="";

                        if (returnEmail.indexOf("@") < 1) {
                            areturnEmail = "do-not-reply@owd.com";
                        } else {
                            areturnEmail = returnEmail;
                        }

                        if (ccEmail.indexOf("@") > 0) {

                            StringTokenizer tokens = new StringTokenizer(ccEmail, ",");

                            while (tokens.hasMoreTokens()) {

                                String addr = tokens.nextToken();

                                try {
                                    InternetAddress iAddr = new InternetAddress(addr);
                                } catch (AddressException ea) {
                                    addr = null;
                                }

                                if (addr != null)

                                    ccs.addElement(addr);

                            }

                        }

                        if (bccEmail.indexOf("@") > 1) {

                            StringTokenizer tokens = new StringTokenizer(bccEmail, ",");

                            while (tokens.hasMoreTokens()) {

                                String addr = tokens.nextToken();

                                try {
                                    InternetAddress iAddr = new InternetAddress(addr);
                                } catch (AddressException ea) {
                                    addr = null;
                                }

                                if (addr != null)

                                    bccs.addElement(addr);

                            }

                        }


                        if (bccs.size() < 1) bccs = null;

                        if (ccs.size() < 1) ccs = null;

                        boolean skipSummary = false;

                        if ("109".equals(clientID) || "365".equals(clientID)) {
                            addressee = toShipEmail;
                            try {
                                InternetAddress iAddr = new InternetAddress(addressee);
                            } catch (AddressException ea) {
                                addressee = null;
                            }

                            if (addressee != null) {
                                Mailer.postMailMessage(subject, message, addressee, ccs, bccs, areturnEmail);
                            }
                            addressee = toEmail;
                            if (!(addressee.trim().equalsIgnoreCase(toShipEmail.trim()))) {
                                try {
                                    InternetAddress iAddr = new InternetAddress(addressee);
                                } catch (AddressException ea) {
                                    addressee = null;
                                }
                                if (addressee != null) {
                                    Mailer.postMailMessage(subject, message, addressee, ccs, bccs, areturnEmail);
                                }
                            }


                        } else if ("495".equals(clientID) ) {


                            sendGlistenShipmentConfirmationEmail(orderID,addressee,null);

                        }  else if ("496".equals(clientID) ) {


                            try{
                                message = getHuggaloShippedEmailBody(orderID ,  shipDate,  carrier,  tracker, packages);
                                Mailer.postMailMessage("Your Huggalo order has shipped!", message, addressee, ccs, bccs, returnEmail);

                            }  catch (Exception exm) {
                                exm.printStackTrace();
                            }

                        }
                        else if ("503".equals(clientID) ) {



                            message = getMantryShippedEmailBody(orderID ,  shipDate,  carrier,  tracker, packages);
                            Mailer.postMailMessage("Your order from Mantry has been shipped!", message, addressee, ccs, bccs, returnEmail);


                        } else if ("409".equals(clientID) ) {



                            message = getLOOPShippedEmailBody(orderID ,  shipDate,  carrier,  packages);

                            Mailer.postMailMessage("Your order from LOOP has been shipped!", message, addressee, ccs, bccs, returnEmail);


                        } else if ("304".equals(clientID) ) {
                            try {
                                if(!orderReference.startsWith("razorama"))
                                {
                                    Mailer.postMailMessage(subject, message, addressee, ccs, bccs, areturnEmail);
                                }else
                                {
                                    skipSummary = true;
                                }
                            } catch (Exception exm) {
                                exm.printStackTrace();
                            }
                        }else if ("529".equals(clientID) ) {
                            try {
                                if(orderReference.startsWith("AUTOSHIP"))
                                {
                                    Mailer.postMailMessage(subject, message, addressee, ccs, bccs, areturnEmail);
                                }else
                                {
                                    skipSummary = true;
                                }
                            } catch (Exception exm) {
                                exm.printStackTrace();
                            }
                        } else if ("117".equals(clientID) ) {
                            try {
                                OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderID);
                                if(order.getPoNum().contains("dance.com"))
                                {
                                    //todo
                                    message = message.replace("The Active World Co, Inc.","DANCE.COM");
                                    message = message.substring(0,message.indexOf("Look for"));
                                    message = message.trim();

                                    message = message+"\r\n\r\nLook for our instructional dance, fitness and sports videos at http://www.dance.com/";
                                    log.debug(message);
                                    Mailer.postMailMessage(subject, message, addressee, ccs, bccs, "support@dance.com");
                                }    else
                                {
                                    Mailer.postMailMessage(subject, message, addressee, ccs, bccs, areturnEmail);
                                }
                            } catch (Exception exm) {
                                exm.printStackTrace();
                            }
                        }  else if ("375".equals(clientID) ) {
                            try {
                                //log.debug("In Renu");

                                byte[] data = AWS_S3Api.getObjectFromBucket("owdmailimage","renu herbs_packet_dec15.pdf");

                                Mailer.sendMailWithAttachment(subject, message, addressee, ccs==null?null:ccs.toArray(), bccs==null?null:bccs.toArray(), areturnEmail,data,"RenuHerbsInstructions.pdf","application/pdf",null);

                            } catch (Exception exm) {
                                exm.printStackTrace();
                            }
                        } else if ("632".equals(clientID) ) {
                            try {

                                StanleyBlackAndDeckerService.sendEmail(orderID);
                            } catch (Exception exm) {
                                exm.printStackTrace();
                            }
                        }  else if ("266".equals(clientID)  ) {
                            try {


                                OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderID);

                                if(!(order.getOrderType().contains("AMAZON"))) {
                                    String email = order.getBillEmailAddress();

                                    if (!(OWDUtilities.isValidEmailAddress(order.getBillEmailAddress()))) {
                                        email = order.getShipinfo().getShipEmailAddress();

                                    }
                                    if (OWDUtilities.isValidEmailAddress(email)) {
                                        sendAlgaeCalEmail(order, email, "support@algaecal.com");
                                    }
                                }
                            } catch (Exception exm) {
                                exm.printStackTrace();
                            }
                        } else if ("345".equals(clientID)  ) {
                            try {


                                OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderID);
                                String email =addressee;

                                if(!(OWDUtilities.isValidEmailAddress(order.getBillEmailAddress())))
                                {
                                    email = order.getShipinfo().getShipEmailAddress();

                                }
                                if(OWDUtilities.isValidEmailAddress(email))
                                {
                                    DogearedEmailSender.sendHTMLEmailConfirmation(order, email, "service@dogeared.com", (ccs == null ? null : ccs.toArray()), (bccs == null ? null : bccs.toArray()));
                                }

                            } catch (Exception exm) {
                                exm.printStackTrace();
                            }
                        }
                        else if("354".equals(clientID)) {
                            //log.debug("Doing LH HTML email");
                            sendLivingHerbalEmail(orderID, subject, header.toString(), items.toString(), carrier, tracker, addressee, ccs, bccs, areturnEmail);


                        }else if("320".equals(clientID)){
                            try {
                                OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderID);
                                if(order.getOrderRefnum().startsWith("DP")){
                                    sendLifeSpanEmail(order,addressee,"support@xendurance.com");


                                    // Mailer.postMailMessage(subject, message, addressee, ccs, bccs, areturnEmail);
                                }   else{
                                    gotOrder = false;
                                    skipSummary = true;
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            //Sean 2020/09/18 case 864122
                        }else if("671".equals(clientID)){

                            sendHotTacoShipmentConfirmationEmail(orderID, addressee,null);

                        }else{
                            log.debug(ccs);
                            log.debug(bccs);
                            log.debug(addressee);
                            log.debug(areturnEmail);
                            Mailer.postMailMessage(subject, message, addressee, ccs, bccs, areturnEmail);
                        }




                        if(!skipSummary)
                        {
                            log.debug("doing summery");
                            StringBuffer mailSummary = new StringBuffer();

                            mailSummary.append("Mail To: " + addressee + "\n");

                            mailSummary.append("Subject: " + subject + "\n");

                            mailSummary.append(message);



                            //log.debug("sent mail");
                            Event.addOrderEvent(orderID.intValue(), Event.kEventTypeEmailSent, mailSummary.toString(), null);
                        }

                    }


                    if (gotOrder && gotItems) {

                        stmt.executeUpdate("update owd_order_track set email_sent = 2 where order_fkey = " + orderID);

                    } else {

                        stmt.executeUpdate("update owd_order_track set email_sent = 1 where order_fkey = " + orderID);

                    }
                    HibUtils.commit(HibernateSession.currentSession());

                } else {

                    HibUtils.rollback(HibernateSession.currentSession());

                }


            } catch (Exception ex) {

                try {

                    HibUtils.rollback(HibernateSession.currentSession());
                } catch (Exception e) {
                }

                Mailer.postMailMessage("ShipNotify err", ex.getMessage() + "\n\n\n" + OWDUtilities.getStackTraceAsString(ex), "noop@owd.com", "noop@owd.com");

                ex.printStackTrace();

            } finally {

                HibernateSession.closeSession();

                try {
                    rs.close();
                } catch (Exception ex) {
                }

                try {
                    stmt.close();
                } catch (Exception ex) {
                }

                try {
                    cxn.close();
                } catch (Exception ex) {
                }

            }


        }

    }

    /**
     * case 864122 HOT TACO - shipment confirmation email with tracking info
     * @param orderId
     * @param overrideToAddress
     * @param areturnEmail
     */
    public static void sendHotTacoShipmentConfirmationEmail(int orderId, String overrideToAddress,
                                                            String areturnEmail)  {
        try {

            String subject = "Your HOT TACO Shipment Confirmation";
            String carrier = "USPS";
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            DecimalFormat moneyFormat = new DecimalFormat("$###,##0.00");

            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
            String oref = order.getOrderRefnum();
            if(oref.split("-").length==3){
                oref = oref.split("-")[2];
            }

            if (order.getShipinfo().getCarrService().toUpperCase().startsWith("UPS")) {
                carrier = "UPS";
            } else if (order.getShipinfo().getCarrService().toUpperCase().startsWith("FEDEX")) {
                carrier = "FEDEX";
            }

            String html = "Dear "+order.getBillFirstName()+" "+order.getBillLastName()+ ",\r\n<br><br>" +
                    "Thank you so much for backing Bold Made!  It means the world to us!  We are so excited to say " +
                    "that your rewards are on their way to you! The tracking information for your reward is listed " +
                    "below!<br><br>"+
                    "Order Reference: "+order.getOrderRefnum()+" \r\n<br>" +
                    "Shipped "+df.format(order.getShippedDate())+ " via "+order.getShipinfo().getCarrService()+ " in "+ order.getPackagesShipped()+ " package"+
                    "\r\n<br>" +
                    "Tracking number "+order.getTrackingNums() +".<br><br> " +
                    "Please contact us at hello@boldmade.com if you have any questions!\r\n<br><br><br>" +
                    "Thank you again for helping bring Bold Made to life! You are the best!!<br><br><br>" +
                    "Leslie & Alex";


            Vector to = new Vector();
            to.addElement(overrideToAddress == null ? order.getBillEmailAddress() : overrideToAddress);
            log.debug(overrideToAddress == null ? order.getBillEmailAddress() : overrideToAddress);

            Mailer.sendHTMLMail(subject, "", html, to.toArray(), null, null, " <hello@boldmade.com>");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendGlistenShipmentConfirmationEmail(int orderId, String overrideToAddress, ServletContext context) throws Exception {
        try {

            String subject = "Your Glisten.com Shipment Confirmation";
            String carrier = "USPS";
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");


            DecimalFormat moneyFormat = new DecimalFormat("$###,##0.00");

            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
            String oref = order.getOrderRefnum();
            if(oref.split("-").length==3)
            {
                oref = oref.split("-")[2];
            }

            if (order.getShipinfo().getCarrService().toUpperCase().startsWith("UPS")) {
                carrier = "UPS";
            } else if (order.getShipinfo().getCarrService().toUpperCase().startsWith("FEDEX")) {
                carrier = "FEDEX";
            }

            String html = "<html xmlns=\"http://www.w3.org/1666/xhtml\">\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                    "<title>Glisten Shipment Confirmation - " + oref + "</title>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body style=\"font-family:'Lucida Grande', Tahoma, Sans-serif;\">\n" +
                    "<div align=\"center\" style=\"font-family:'Lucida Grande', Tahoma, Sans-serif;font-size:12px; line-height:22px; margin:0; background-color:#EFEFEF;\">\n" +
                    "\t<table width=\"100%\" height=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"10\" bgcolor=\"#f2f3f4\">\n" +
                    "\t\t<tr>\n" +
                    "\t\t\t<td>\n" +
                    "\t\t\t\t<div align=\"center\">\n" +
                    "\t\t\t\t\t<div style=\"width:650px; background-color:#fff;\">\n" +
                    "\t\t\t\t\t\t<div style=\"background-color:#289EC4; height:55px;\">\n" +
                    "\t\t\t\t\t\t\t<img src=\"http://owdmailimage.s3.amazonaws.com/glistenheader.png\" style=\"position:relative; top:8px;\">\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t<div class=\"contentSection\" style=\"text-align:left; padding:20px; font-size:14px;\">\n" +
                    "\t\t\t\t\t\t\t<p style=\"margin:0; padding:5px;\">Hi " + order.getBillFirstName() + ",</p>\n" +

                    "\t\t\t\t\t\t\t<p style=\"margin:0; padding:5px;\">This is probably the greatest news you will get all day - your order has shipped!</p>\n" +
                    "\t\t\t\t\t\t\t<p style=\"margin:0; padding:5px;\">That is neat.</p>\n" +
                    "\t\t\t\t\t\t\t<p style=\"margin:0; padding:5px;\">Excited about your order? We are too! Let's celebrate!</p>\n" +
                    "\t\t\t\t\t\t\t<div style=\"background-color:#ddd; border:1px solid #888; padding:10px; margin:10px 0;\">\n" +
                    "\t\t\t\t\t\t\t\t<span style='color:#289EC4'>" + (carrier) + " Tracking Number:</span> <strong>" + order.getTrackingNums() + "</strong><br>\n" +

                    "\t\t\t\t\t\t\t\t<span style=\"font-style:italic; font-size:12px;\">Please be aware it make take up to 24 hours for your tracking number to return any information.</span>\n" +
                    "\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t<table style=\"font-size:14px; font-weight:bold; padding-top:10px;\">\n" +
                    "\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t<td style=\"width:170px;\">Date Shipped:</td>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>" + df.format(order.getShippedDate()) + "</td>\n" +
                    "\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t</table>\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t<div class=\"headingSection\" style=\"padding:10px; padding-left:20px; font-size:18px; text-align:left; background-color:#9F9F9F; color:#fff;\">\n" +
                    "\t\t\t\t\t\t\tORDER INFORMATION\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t<div class=\"contentSection\" style=\"text-align:left; padding:20px; font-size:14px;\">\n" +
                    "\t\t\t\t\t\t\t<table style=\"font-size:12px;\" width=\"100%\">\n" +
                    "\t\t\t\t\t\t\t\t<tr style=\"color:#289EC4;\">\n" +
                    "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>Item:</td>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>Quantity:</td>\n" +
                    "\t\t\t\t\t\t\t\t</tr>\n";
            for (OwdLineItem item : order.getLineitems()) {
                if(item.getQuantityActual()>0)
                {
                    html = html +
                            "\t\t\t\t\t\t\t\t<tr>\n" +
                            "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                            "\t\t\t\t\t\t\t\t\t<td>" + item.getDescription() + "<br>Item #: " + item.getInventoryNum() + "<br>" + item.getItemSize() + "</td>\n" +
                            "\t\t\t\t\t\t\t\t\t<td>" + item.getQuantityActual() + "</td>\n" +
                            "\t\t\t\t\t\t\t\t</tr>\n";
                }

            }
            html = html + "\t\t\t\t\t\t\t</table>\n" +
                    "\t\t\t\t\t\t\t<div style=\"clear:both;\"></div>\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t<div class=\"headingSection\"style=\"padding:10px; padding-left:20px; font-size:18px; text-align:left; background-color:#9F9F9F; color:#fff;\">\n" +
                    "\t\t\t\t\t\t\tSHIPPING & BILLING INFORMATION\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t<div class=\"contentSection\" style=\"text-align:left; padding:20px; font-size:14px;\">\n" +
                    "\t\t\t\t\t\t\t<p style=\"margin:0; padding:5px;\">Shipping Method: " + order.getShipinfo().getCarrService() + "</p>\n" +

                    "\t\t\t\t\t\t\t<br>\n" +
                    "\t\t\t\t\t\t\t<hr style=\"border: 1px solid #888;\">\n" +
                    "\t\t\t\t\t\t\t<br>\n" +
                    "\t\t\t\t\t\t\t<table style=\"font-size:14px;\" width=\"100%\">\n" +
                    "\t\t\t\t\t\t\t\t<tr>\n" +

                    "\t\t\t\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t<span style=\"color:#289EC4;\">Shipping Address:</span> <br>\n" +
                    "\t\t\t\t\t\t\t\t\t\t" + order.getShipinfo().getShipFirstName() + " " + order.getShipinfo().getShipLastName() + "<br>\n" +
                    "\t\t\t\t\t\t\t\t\t\t" + order.getShipinfo().getShipAddressOne() + "<br>\n" +
                    "\t\t\t\t\t\t\t\t\t\t" + order.getShipinfo().getShipAddressTwo() + "<br>\n" +
                    "\t\t\t\t\t\t\t\t\t\t" + order.getShipinfo().getShipCity() + ", " + order.getShipinfo().getShipState() + ", " + order.getShipinfo().getShipZip() + " <br>\n" +
                    "\t\t\t\t\t\t\t\t\t</td>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t<span style=\"color:#289EC4;\">Billing Address:</span> <br>\n" +
                    "\t\t\t\t\t\t\t\t\t\t" + order.getBillFirstName() + " " + order.getBillLastName() + "<br>\n" +
                    "\t\t\t\t\t\t\t\t\t\t" + order.getBillAddressOne() + "<br>\n" +
                    "\t\t\t\t\t\t\t\t\t\t" + order.getBillAddressTwo() + "<br>\n" +
                    "\t\t\t\t\t\t\t\t\t\t" + order.getBillCity() + ", " + order.getBillState() + ", " + order.getBillZip() + " <br>\n" +
                    "\t\t\t\t\t\t\t\t\t</td>\n" +

                    "\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t</table>\n" +
                    "\t\t\t\t\t\t\t<br>\n" +
                    "\t\t\t\t\t\t\t<hr style=\"border: 1px solid #888;\">\n" +
                    "\t\t\t\t\t\t\t<br>\n" +
                    "\t\t\t\t\t\t\t<p style=\"margin:0; padding:5px;\">Questions? Need to hear a joke? Looking for more Glisten Fun? We can help with that!</p>\n" +
                    "\t\t\t\t\t\t\t<p style=\"margin:0; padding:5px;\">Be Good,<br>Team Glisten</p>\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t<div style=\"background-color:#289EC4; padding:10px 0;\">\n" +
                    "\t\t\t\t\t\t\t<table style=\"font-size:16px; text-align:center; color:#fff;\" width=\"100%\">\n" +
                    "\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t<a style=\"text-decoration:none !important; color:white !important;\" href=\"\"><span style=\"color:#ffffff; text-decoration:none;\">800.815.4079</span></a>\n" +
                    "\t\t\t\t\t\t\t\t\t</td>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t<a style=\"text-decoration:none !important; color:white !important;\" href=\"mailto:hello@glisten.com\"><span style=\"color:#ffffff; text-decoration:none;\">hello@glisten.com</span></a>\n" +
                    "\t\t\t\t\t\t\t\t\t</td>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t@GlistenBeauty\n" +
                    "\t\t\t\t\t\t\t\t\t</td>\n" +
                    "\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t</table>\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t</div>\n" +
                    "\t\t\t\t</div>\n" +
                    "\t\t\t</td>\n" +
                    "\t\t</tr>\n" +
                    "\t</table>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";




            Vector to = new Vector();
            to.addElement(overrideToAddress == null ? order.getBillEmailAddress() : overrideToAddress);
            log.debug(overrideToAddress == null ? order.getBillEmailAddress() : overrideToAddress);
            Mailer.sendHTMLMail(subject, "", html, to.toArray(), null, null, "GLISTEN <hello@glisten.com>");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    private static void sendLifeSpanEmail(OwdOrder order,String addressee, String areturnEmail) throws Exception {






        StringBuffer s = new StringBuffer();
        s.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html\n" +
                "charset=utf-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<table style=\"margin: 0 auto;\"\n" +
                "       align=\"center\" bgcolor=\"#f0f0f0\" border=\"0\" cellpadding=\"0\"\n" +
                "       cellspacing=\"0\" width=\"600\" class=\"\">\n" +
                "    <tbody class=\"\">\n" +
                "    <tr class=\"\">\n" +
                "        <td align=\"center\" bgcolor=\"#000000\" valign=\"top\"\n" +
                "            width=\"600\" class=\"\">\n" +
                "            <a\n" +
                "                    href=http://xendurance.com\"\n" +
                "                    target=\"_blank\" class=\"\"><img\n" +
                "                    src=\"http://cdn-ecomm.dreamingcode.com/public/257/images/logo-xendurance20150319053036.png\"\n" +
                "                    title=\"0\" border=\"0\" height=\"86\" width=\"132\"\n" +
                "                    class=\"\"></a>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr class=\"\">\n" +
                "        <td align=\"left\" bgcolor=\"#f0f0f0\" valign=\"top\"\n" +
                "            width=\"600\" class=\"\">\n" +
                "            <br class=\"\">\n" +
                "            <br class=\"\">\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                   width=\"600\" class=\"\">\n" +
                "                <tbody class=\"\">\n" +
                "                <tr class=\"\">\n" +
                "                    <td align=\"left\" valign=\"top\" width=\"20\"\n" +
                "                        class=\"\">\n" +
                "                    </td>\n" +
                "                    <td style=\"font-size: 14px; line-height: 17px; color: #878787;\" align=\"left\" valign=\"top\"\n" +
                "                        width=\"560\" class=\"\">\n" +
                "                        <strong style=\"font-size: 20px;\" class=\"\">Your order has completely shipped</strong><br\n" +
                "                            class=\"\">\n" +
                "                        <br class=\"\">\n" +
                "                        Dear Customer,\n" +
                "                        <br class=\"\">\n" +
                "                        <br class=\"\">\n" +
                "                        Thank\n" +
                "                        you for choosing Xendurance for your sports nutrition regimen. We want\n" +
                "                        to let you know that your order is complete and has shipped from our\n" +
                "                        warehouse. For more information on your order, please log into your\n" +
                "                        account for tracking information. Tracking updates should be available\n" +
                "                        24 hours after your order has shipped.\n" +
                "                        <br class=\"\">\n" +
                "                        <br class=\"\">\n" +
                "                        Once\n" +
                "                        your order has arrived, go out and set a new PR!\n" +
                "                        <br class=\"\">\n" +
                "                        <br class=\"\">\n" +
                "                        Your\n" +
                "                        order was shipped via:\n" +
                "                        <strong class=\"\">");
        s.append(order.getShipinfo().getCarrService());
        s.append("\n" +
                "                            Mail</strong><br class=\"\">\n" +
                "                        <br class=\"\">\n" +
                "                        Tracking\n" +
                "                        #:\n" +
                "                        <strong class=\"\">");

        s.append(order.getTrackingNums());
        s.append(";</strong><br class=\"\">\n" +
                "\n" +
                "                        <br class=\"\">\n" +
                "\n" +
                "                        Sincerely,\n" +
                "                        <br class=\"\">\n" +
                "                        The\n" +
                "                        Xendurance Team\n" +
                "                        <br class=\"\">\n" +
                "                        <br class=\"\">\n" +
                "                        If you\n" +
                "                        have any further questions regarding your order, please email us at:\n" +
                "                        <a href=\"mailto:support@xendurance.com\"\n" +
                "                           class=\"\">support@xendurance.com</a>\n" +
                "                    </td>\n" +
                "                    <td align=\"left\" valign=\"top\" width=\"20\"\n" +
                "                        class=\"\">\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "            <br class=\"\">\n" +
                "        </td>\n" +
                "\n" +
                "    </tr>\n" +
                "    <tr class=\"\">\n" +
                "        <td style=\"font-size: 12px; line-height: 15px; color:\n" +
                "#ffffff;\" align=\"center\" bgcolor=\"#000000\" valign=\"top\"\n" +
                "            width=\"600\" class=\"\">\n" +
                "            <br class=\"\">\n" +
                "            <br class=\"\">\n" +
                "            &copy;2013 Xendurance\n" +
                "            <br class=\"\"><br class=\"\">\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>");



        Vector to = new Vector();
        to.addElement(addressee);
        log.debug(addressee);


        Mailer.sendHTMLMail("Your Order Has Been Shipped", "", s.toString(), to.toArray(), null, null, areturnEmail);

    }
    private static void sendAlgaeCalEmail(OwdOrder order,String addressee, String areturnEmail) throws Exception {

        String brandName = "AlgaeCal";
        String website = "www.algaecal.com";
        String contact = "support@algaecal.com";
        String phone = "800-820-0184";
        boolean useImage = true;

        if(order.getOrderRefnum().endsWith("CC"))
        {
            brandName = "True Blue";
            website = "www.brazillivecoral.com";
            contact = "support@brazillivecoral.com";
            phone="888-259-1290";
            useImage = false;

        }   else if (order.getOrderRefnum().endsWith("BL"))
        {
            brandName = "Brazil Live";
            website = "www.brazillivecoral.com";
            contact = "support@brazillivecoral.com";
            phone="800-852-4064";
            useImage = false;

        }

        StringBuffer s = new StringBuffer();
        s.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body bgcolor=\"#ffffff\" text=\"#000000\"><br>\n");
        String tracker="";

        if(order.getTrackingNums().length()>10 && order.getTrackingNums().indexOf("Not")<0){
            if(order.getShipinfo().getCarrService().indexOf("USPS")>=0){
                tracker =("<a href=\"https://tools.usps.com/go/TrackConfirmAction.action?tLabels="+order.getTrackingNums()+"\">"+order.getTrackingNums()+"</a>");
            }
            if(order.getShipinfo().getCarrService().indexOf("UPS")>=0){
                tracker =("<a href=\"http://wwwapps.ups.com/WebTracking/track?track=yes&trackNums="+order.getTrackingNums()+"\">"+order.getTrackingNums()+"</a>");
            }
            if(order.getShipinfo().getCarrService().indexOf("FedEx")>=0){
                tracker =("<a href=\"http://www.fedex.com/Tracking?ascend_header=1&clienttype=dotcom&cntry_code=us&language=english&tracknumbers="+order.getTrackingNums()+"\">"+order.getTrackingNums()+"</a>");
            }


        }

        s.append("Dear "+order.getBillFirstName()+" "+order.getBillLastName()+",<br><br>");
        s.append("Thank you for placing your order with "+brandName+" (<a class=\"moz-txt-link-abbreviated\" href=\""+website+"\">"+website+"</a>).\n" +
                "We are pleased to inform you that your order "+order.getOrderRefnum()+" has been processed and\n" +
                "shipped.<br>\n" +
                "<br>The tracking number assigned to your package is "+tracker+".\n" +
                "Your package will be delivered by "+order.getShipinfo().getCarrService()+".<BR><BR>");

        s.append("If you do not receive your package within the specified times, please let\n" +
                "us know. We would be happy to help you resolve any issues with the carrier.\n" +
                "<br><br>" +
                "If you have any questions regarding your order, please call us toll free at " +
                "1-"+phone+" or international 1-510-564-7192.<br>\n" +
                "<br>\n" +
                "Thanks!<br><p/><br>\n" +
                brandName+" Customer Care Team<br>\n" +
                "<a class=\"moz-txt-link-abbreviated\" href=\""+website+"\">"+website+"</a><br>\n" +
                "<a class=\"moz-txt-link-abbreviated\" href=\"mailto:"+contact+"\">"+contact+"</a><br>\n" +
                (useImage?"<br><img src=\"http://owdmailimage.s3.amazonaws.com/algaecal3.jpg\" alt=\"\">\n":"") +

                "</body>\n" +
                "</html>");

        Vector to = new Vector();
        to.addElement(addressee);
        log.debug(addressee);

        // Sean 2/3/2020 case 748614 - Send tracking info to orders@algaecal.com

        String [] bcc = {"orders@algaecal.com "};

        Mailer.sendHTMLMail("" + order.getBillFirstName() + " " + order.getBillLastName() + ", Your " + brandName + " Order " + order.getOrderRefnum() + " has Shipped", "",
                s.toString(), to.toArray(), null, bcc , areturnEmail);

    }



    private static void sendLivingHerbalEmail(Integer orderID, String subject, String header, String items, String carrier, String tracker, String addressee, Vector ccs, Vector bccs, String areturnEmail) throws Exception {
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderID);
        StringBuffer s = new StringBuffer();
        s.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body bgcolor=\"#ffffff\" text=\"#000000\">\n" +
                "<img src=\"http://owdmailimage.s3.amazonaws.com/LHP.jpg\" alt=\"\"><br>\n");
        s.append(header.toString());

        s.append(items.toString());

        s.append("<br><br>");
        s.append("Thank you for your order!<br>\n" +
                "<br>\n");
        if(tracker.length()>10&&tracker.indexOf("Not")<0){
            if(carrier.indexOf("USPS")>=0){
                s.append("You can track your package at <a href=\"http://www.usps.com\">http://www.usps.com</a><br><br>\n");
            }
            if(carrier.indexOf("UPS")>=0){
                s.append("You can track your package at <a href=\"http://www.ups.com\">http://www.ups.com</a><br><br>\n");
            }
            if(carrier.indexOf("FedEx")>=0){
                s.append("You can track your package at <a href=\"http://www.fedex.com\">http://www.fedex.com</a><br><br>\n");
            }


        }
        s.append("Ship To:<br>");
        s.append(order.getShipinfo().getShipFirstName()+ " "+order.getShipinfo().getShipLastName()+"<br>\n");
        s.append(order.getShipinfo().getShipAddressOne()+"<br>");
        if(order.getShipinfo().getShipAddressTwo().length()>2)s.append(order.getShipinfo().getShipAddressTwo()+"<br>");
        s.append(order.getShipinfo().getShipCity()+", "+order.getShipinfo().getShipState()+" "+order.getShipinfo().getShipZip());
        s.append("<br><br>" +
                "Please contact us if you have any questions or need assistance with your order.<br>\n" +
                "<br>\n" +
                "To Your Health,<br>\n" +
                "Dr. Brantley's Living Herbal Pharmacy<br>\n" +
                "<a class=\"moz-txt-link-abbreviated\" href=\"http://www.brantleycure.com\">www.brantleycure.com</a><br>\n" +
                "<a class=\"moz-txt-link-abbreviated\" href=\"mailto:service@brantleycure.com\">service@brantleycure.com</a><br>\n" +
                "1-800-560-CURE (2873)<br>\n" +

                "</body>\n" +
                "</html>");


        Vector to = new Vector();
        to.addElement(addressee);
        log.debug(addressee);
        Mailer.sendHTMLMail(subject, "", s.toString(), to.toArray(), (ccs == null ? null : ccs.toArray()), bccs == null ? null : bccs.toArray(), areturnEmail);
    }



    private static String getMantryShippedEmailBody(Integer orderID, String shipDate, String carrier, String tracker, int packages) throws Exception
    {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        DecimalFormat mf = new DecimalFormat("$#,###,##0.00");


        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, new Integer(orderID));


        String body = "<html style=\"background:#fff;font-family:Helvetica;\">\n" +
                "\t<head>\n" +
                "\t\t\n" +
                "\t\t<title>Your Order Has Been Shipped!</title>\n" +
                "\t\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t\t<div id=\"email_container\">\n" +
                "\t\t\t<div style=\"width:570px; padding:0 0 0 20px; margin:50px auto 35px auto; height:125px;text-align:center;\" id=\"email_header\">\n" +
                "\t\t\t<img src=\"http://mantry.com/wp-content/uploads/2012/04/MantryHeader1.png\" style=\"margin:0 auto;\"/>\n" +
                "\t\t\t</div>\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\t<div style=\"width:570px; padding:0; background:#fff; margin:0 auto; color:#454545;line-height:1.5em; \" id=\"email_content\">\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t<h1 style=\"padding:5px 20px 15px;font-weight:500;font-size:24px;color:#000;border-bottom:1px solid #bbb\">\n" +
                "\t\t\t\t\tYour Order Has Been Shipped!\n" +
                "\t\t\t\t</h1>\n" +
                "\t\t\t\t<div style=\"width:100%;padding:40px 20px;\">\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t<p>Order Reference "+order.getOrderRefnum()+"</p>\n" +
                "\t\t\t\t<p>Shipped : "+shipDate+", via "+carrier+", in "+packages+" package"+(packages>1?"s":"")+"</p>\n" +
                "\t\t\t\t<p>Tracking Number : <a href=\""+(carrier.startsWith("UPS")?"http://wwwapps.ups.com/WebTracking/track?track=yes&trackNums=":"https://tools.usps.com/go/TrackConfirmAction?qtc_tLabels1=")+tracker+"\">"+tracker+"</a></p>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t</div>\n" +
                "<div style=\"text-align:center; border-top:1px solid #eee;padding:5px 0 0 0;\" id=\"email_footer\"> \n" +
                "\t\t\t\t\t<small style=\"font-size:11px; color:#999; line-height:14px;\">\n" +
                "\t\t\t\t\t\tThis is an automated email generated because you completed an action at mantry.com.\n" +
                "\t\t\t\t\t</small>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>";

        return body;
    }

    private static String getHuggaloShippedEmailBody(Integer orderID, String shipDate, String carrier, String tracker, int packages) throws Exception
    {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        DecimalFormat mf = new DecimalFormat("$#,###,##0.00");


        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, new Integer(orderID));
        if(!(order.getOrderType().equalsIgnoreCase("SHOPIFY")))
        {

            StringBuffer productString = new StringBuffer();
            for(OwdLineItem item:order.getLineitems())
            {
                if(item.getParentKey()==null)
                {
                    productString.append((productString.toString().length()>1?", ":"")+item.getQuantityActual()+" of "+item.getInventoryNum()+" ("+item.getDescription()+")");
                }
            }
            String body = "Dear "+order.getBillFirstName()+" "+order.getBillLastName()+",\r\n" +
                    "\r\n" +
                    "Your order of "+productString+" has shipped.\r\n" +
                    "\r\n" +
                    "Your order shipped via "+order.getShipinfo().getCarrService()+" to:\r\n" +
                    "\r\n" +
                    (order.getShipinfo().getShipCompanyName().length()>1?order.getShipinfo().getShipCompanyName()+"\r\n":"")+
                    order.getShipinfo().getShipAddressOne()+"\r\n" +
                    (order.getShipinfo().getShipAddressTwo().length()>1?order.getShipinfo().getShipAddressTwo()+"\r\n":"")+
                    order.getShipinfo().getShipCity()+", "+order.getShipinfo().getShipState()+" "+order.getShipinfo().getShipZip()+"\r\n"+
                    "\r\n" +
                    "The tracking number is "+tracker+". You can see the status of your shipment by clicking the link below (or by pasting into your browser):\r\n" +
                    "\r\n" +
                    ""+(carrier.toUpperCase().startsWith("FEDEX")?"http://www.fedex.com/Tracking?action=track&tracknumbers="+tracker:(carrier.startsWith("UPS")?"http://wwwapps.ups.com/WebTracking/track?track=yes&trackNums="+tracker:"https://tools.usps.com/go/TrackConfirmAction?qtc_tLabels1="+tracker))+"\r\n" +
                    "\r\n" +
                    "It may take up to 24 hours for tracking information to be updated.\r\n" +
                    "\r\n" +
                    "Thank you for ordering from Huggalo!";

            return body;
        }else
        {
            throw new Exception("No emails sent for shopify orders");
        }


    }

    private static String getLOOPShippedEmailBody(Integer orderID, String shipDate, String carrier, int packages) throws Exception {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        DecimalFormat mf = new DecimalFormat("$#,###,##0.00");


        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, new Integer(orderID));

        //send problem email
        String body = "\nCustomer Name : "+(order.getBillCompanyName().length()>2 && (!(order.getBillCompanyName().equals(order.getBillFirstName()+" "+order.getBillLastName())))?order.getBillCompanyName()+"/":"")+order.getBillFirstName()+" "+order.getBillLastName()+"\n"+
                "Your P.O. Number : "+order.getPoNum()+"\n"+
                "LOOP Invoice Number : "+order.getOrderRefnum()+"\n"+
                "Shipment/Return Number:	"+order.getOrderNum()+"\n\n"+
                "Shipped To : "+(order.getShipinfo().getShipCompanyName().length()>2 && (!(order.getShipinfo().getShipCompanyName().equals(order.getShipinfo().getShipFirstName()+" "+order.getShipinfo().getShipLastName())))?order.getShipinfo().getShipCompanyName()+"/":"")+order.getShipinfo().getShipFirstName()+" "+order.getShipinfo().getShipLastName()+"\n"+
                "Shipped : "+ shipDate + ", via " + carrier + ", in " + packages + " package" + (packages > 1 ? "s" : "") + "\n"+"\n"+
                "Tracking Number : "+order.getTrackingNums()+"\n\n";

        body = body+(OWDUtilities.padRight("Item", 20) + OWDUtilities.padRight("Count", 10) + OWDUtilities.padRight("Description", 40) + "\n");

        for (int i = 0; i < 70; i++) {
            body = body+("=");
        }

        body = body+("\n");

        for (int i = 0; i < order.getLineitems().size(); i++) {
            OwdLineItem li =  (OwdLineItem) order.getLineitems().toArray()[i];
            StringTokenizer st = new StringTokenizer(li.getDescription(), " ");

            int currentLen = 0;

            StringBuffer currLine = new StringBuffer();

            body = body+(OWDUtilities.padRight(li.getInventoryNum(), 20) + OWDUtilities.padRight(li.getQuantityActual()+"", 10));

            while (st.hasMoreTokens()) {

                String word = st.nextToken() + " ";

                if ((currentLen + word.length()) > 40) {

                    body = body+(OWDUtilities.padRight(currLine.toString(), 40) + "\n" + OWDUtilities.padRight("", 30));

                    currentLen = 0;

                    currLine.setLength(0);

                }

                currLine.append(word);

                currentLen += word.length();

            }

            body = body+(OWDUtilities.padRight(currLine.toString(), 40) + "\n");
        }

        HibernateSession.currentSession().evict(order);
        return body;
    }

    private static void getItems(ResultSet rs, StringBuffer items, String clientID) throws SQLException {


        boolean dosku = true;
        if("382".equalsIgnoreCase(clientID)){


            String parentLine = rs.getString(4);
            if(parentLine != null){
                log.debug("Parent line is true for OWS, skipping it from email");
                dosku=false;

            }


        }
        String description = rs.getString(3);

        if (description == null) description = "";

        if ("346".equals(clientID)) {

            items.append("Item: " + rs.getString(1) + "\n");
            items.append("Quantity: " + rs.getString(2) + "\n");
            items.append("Description: " + description + "\n\n");

        } else if("354".equals(clientID)) {
            getHTMLItems(rs, items, clientID);
        } else if ("683".equals(clientID)) {
            items.append(description + " (" + rs.getString(2) + ")\n\n");
        } else {
            if(dosku){
                StringTokenizer st = new StringTokenizer(description, " ");

                int currentLen = 0;

                StringBuffer currLine = new StringBuffer();

                items.append(OWDUtilities.padRight(rs.getString(1), 20) + OWDUtilities.padRight(rs.getString(2), 10));

                while (st.hasMoreTokens()) {

                    String word = st.nextToken() + " ";

                    if ((currentLen + word.length()) > 40) {

                        items.append(OWDUtilities.padRight(currLine.toString(), 40) + "\n" + OWDUtilities.padRight("", 30));

                        currentLen = 0;

                        currLine.setLength(0);

                    }

                    currLine.append(word);

                    currentLen += word.length();

                }

                items.append(OWDUtilities.padRight(currLine.toString(), 40) + "\n");
            }
        }
    }
    private static void getHTMLItems(ResultSet rs, StringBuffer items, String clientID) throws SQLException {


        items.append("<tr><td>");
        items.append(rs.getString(1));
        items.append("</td><td>");
        items.append(rs.getString(2));
        items.append("</td><td>");
        items.append(rs.getString(3));
        items.append("</td></tr>");
       /* String description = rs.getString(3);

        if (description == null) description = "";

        StringTokenizer st = new StringTokenizer(description, " ");

        int currentLen = 0;

        StringBuffer currLine = new StringBuffer();

        items.append(OWDUtilities.padRightHTML(rs.getString(1), 20) + OWDUtilities.padRightHTML(rs.getString(2), 10));

        while (st.hasMoreTokens()) {

            String word = st.nextToken() + " ";

            if ((currentLen + word.length()) > 40) {

                items.append(OWDUtilities.padRightHTML(currLine.toString(), 40) + "<br>" + OWDUtilities.padRightHTML("", 30));

                currentLen = 0;

                currLine.setLength(0);

            }

            currLine.append(word);

            currentLen += word.length();

        }

        items.append(OWDUtilities.padRight(currLine.toString(), 40) + "<br>");*/

    }

    private static void getHTMLHeader(StringBuffer header, String orderReference, String shipDate, String carrier, int packages, String tracker, String clientID, String custName) {
        header.append("\nOrder Reference : " + orderReference + "<br><br>");

        header.append("Shipped : " + shipDate + ", via " + carrier + ", in " + packages + " package" + (packages > 1 ? "s" : "") + "<br>");

        header.append("Tracking Number(s) : " + tracker + "<br><br>");

        header.append("<table>\n<tr>\n<th align=left>Item</th>\n<th align=left>Count</th>\n<th align=left>Description</th>\n</tr>");
        //header.append(OWDUtilities.padRightHTML("Item", 20) + OWDUtilities.padRightHTML("Count", 10) + OWDUtilities.padRightHTML("Description", 40) + "<br>");
        header.append("<tr><td colspan=\"3\">\n");
        for (int i = 0; i < 50; i++) {
            header.append("=");
        }
        header.append("\n</td></tr>\n");
        // header.append("<br>");




    }

    private static void getEmailHeader(StringBuffer header, String orderReference, String shipDate, String carrier, int packages, String tracker, String clientID, String custName, String carrierCode)
    {
        if ("346".equals(clientID)) {
            header.append("\nDear " + custName + ",\n\n");

            header.append("Thank you for your Dacor accessory order with www.EverythingDacor.com.  This provides confirmation of shipment.");
            header.append("\n\nOrder Reference: " + orderReference + "\n\n");
            header.append("Date Shipped: " + shipDate + "\n");
            header.append("Shipping Method: " + carrier + "\n");
            header.append("Number of Packages: " + packages + "\n");
            header.append("Tracking Number: " + tracker + "\n\n");


        } else if("354".equals(clientID)){

            getHTMLHeader(header,orderReference,shipDate,carrier,packages,tracker,clientID,custName);


        }
        else if("143".equals(clientID)){

            header.append("\nOrder Reference : " + orderReference + "\n\n");

            header.append("Shipped : " + shipDate + ", via " + carrier + ", in " + packages + " package" + (packages > 1 ? "s" : "") + "\n");
            header.append("Package Number : " + tracker + "\n\n");


            header.append(OWDUtilities.padRight("Item", 20) + OWDUtilities.padRight("Count", 10) + OWDUtilities.padRight("Description", 40) + "\n");

            for (int i = 0; i < 70; i++) {
                header.append("=");
            }

            header.append("\n");
        }
        // case 1147314: Specific carrier for Beadle and Grimm
        else if ("683".equals(clientID)) {
            header.append("ORDER FROM: Beadle & Grimm's Pandemonium Warehouse\n\n");
            header.append("Order Reference : " + orderReference + "\n");
            header.append("Shipped : " + shipDate + ", via " + getCarrierFromCarrierCode(carrierCode) + ", in " + packages + " package" + (packages > 1 ? "s" : "") + "\n\n");
            header.append("Tracking Number : " + tracker + "\n\n");
            header.append("Products included in this shipment:\n");

            for (int i = 0; i < 70; i++) {
                header.append("=");
            }

            header.append("\n");
        }
        else {
            header.append("\nOrder Reference : " + orderReference + "\n\n");

            header.append("Shipped : " + shipDate + ", via " + carrier + ", in " + packages + " package" + (packages > 1 ? "s" : "") + "\n");
            header.append("Tracking Number : " + tracker + "\n\n");


            header.append(OWDUtilities.padRight("Item", 20) + OWDUtilities.padRight("Count", 10) + OWDUtilities.padRight("Description", 40) + "\n");

            for (int i = 0; i < 70; i++) {
                header.append("=");
            }

            header.append("\n");
        }
    }

    private static String getEmailSubject(String orderReference, String clientID) {

        if ("346".equals(clientID)) {
            return "EverythingDacor.com Shipping Confirmation (Order Reference #: " + orderReference + ")";
        } else if ("345".equals(clientID)) {
            return "Dogeared Shipping Confirmation (Order Reference #: " + orderReference + ")";
        } else {
            return "Your order has been shipped (Reference: " + orderReference + ")";
        }
    }

    private static String getCarrierFromCarrierCode(String carrierCode) {
        if (carrierCode.toLowerCase().contains("apc"))
            return "APC";
        if (carrierCode.toLowerCase().contains("ups"))
            return "UPS";
        if (carrierCode.toLowerCase().contains("usps"))
            return "USPS";
        if (carrierCode.toLowerCase().contains("fedex") || carrierCode.toLowerCase().contains("fxrs"))
            return "FedEx";
        if (carrierCode.toLowerCase().contains("dhlglobalmail"))
            return "DHL Global Mail";
        if (carrierCode.toLowerCase().contains("dhl"))
            return "DHL";
        if (carrierCode.toLowerCase().contains("ontrac"))
            return "OnTrac";
        if (carrierCode.toLowerCase().contains("ltl"))
            return "LTL";
        return "";
    }


}


