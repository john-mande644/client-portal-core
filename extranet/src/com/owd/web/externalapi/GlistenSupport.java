package com.owd.web.externalapi;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk1
 * Date: 6/15/13
 * Time: 8:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class GlistenSupport {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception {
        sendOrderConfirmationEmail(5093270, "tommy.leikas@gmail.com", null);
          sendShipmentConfirmationEmail(5093270,"tommy.leikas@gmail.com", null);

    }


    public static void sendOrderConfirmationEmail(int orderId, String overrideToAddress, ServletContext context) throws Exception {
     /*
        try {
            String subject = "Your Glisten.com Order Confirmation";

            DecimalFormat moneyFormat = new DecimalFormat("$###,##0.00");

            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);

            String oref = order.getOrderRefnum();

            log.debug(oref.split("-").length);
            if(oref.split("-").length==3)
            {
                oref = oref.split("-")[2];
            }
            log.debug(oref);

            String html="<html xmlns=\"http://www.w3.org/1666/xhtml\">\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                    "<title>Glisten Order Confirmation - " + oref + "</title>\n" +
                    "</head>\n" +
                    "<body style=\"font-family:'Lucida Grande', Tahoma, Sans-serif;\">\n" +
                    "<div align=\"center\" style=\"font-family:'Lucida Grande', Tahoma, Sans-serif;font-size:12px; line-height:22px; margin:0; background-color:#EFEFEF;\">\n" +
                    "\t<table width=\"100%\" height=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"10\" bgcolor=\"#f2f3f4\">\n" +
                    "\t\t<tr>\n" +
                    "\t\t\t<td>\n" +
                    "\t\t\t\t<div align=\"center\">\n" +
                    "\t\t\t\t\t<div style=\"width:650px; background-color:#fff;\">\n" +
                    "\t\t\t\t\t\t<div style=\"background-color:#289EC4; height:55px;\">\n" +
                    "\t\t\t\t\t\t\t<img src=\"cid:image.png\" style=\"position:relative; top:8px;\">\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t<div class=\"contentSection\" style=\"text-align:left; padding:20px; font-size:14px;\">\n" +
                    "\t\t\t\t\t\t\t<p style=\"margin:0; padding:5px;\">Hi " + order.getBillFirstName() + ",</p>\n" +

                    "\t\t\t\t\t\t\t<p style=\"margin:0; padding:5px;\">This is probably the greatest news you will get all day - your order was placed!</p>\n" +
                    "\t\t\t\t\t\t\t<p style=\"margin:0; padding:5px;\">We've asked that your order get special treatment! We're talking white gloves, body guards, and velvet pillow type service. Our staff will probably chat with your box for a bit wishing it save travels too.</p>\n" +
                    "\t\t\t\t\t\t\t<p style=\"margin:0; padding:5px;\">Lets just say we take your order pretty seriously.</p>\n" +
                    "\t\t\t\t\t\t\t<p style=\"font-size:14px; font-weight:bold; padding-top:10px;\">Here's your order number: " + oref + "</p>\n" +
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
                    "\t\t\t\t\t\t\t\t\t<td>Price:</td>\n" +
                    "\t\t\t\t\t\t\t\t</tr>\n";
            for (OwdLineItem item : order.getLineitems()) {
                html = html +
                        "\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                        "\t\t\t\t\t\t\t\t\t<td>" + item.getDescription() + "<br>Item #: " + item.getInventoryNum() + "<br>" + item.getItemSize() + "</td>\n" +
                        "\t\t\t\t\t\t\t\t\t<td>" + item.getQuantityRequest() + "</td>\n" +
                        "\t\t\t\t\t\t\t\t\t<td>" + moneyFormat.format(item.getPrice()) + "</td>\n" +
                        "\t\t\t\t\t\t\t\t</tr>\n";

            }

            html = html +
                    "\t\t\t\t\t\t\t</table>\n" +
                    "\t\t\t\t\t\t\t<hr style=\"border: 1px solid #888;\">\n" +
                    "\t\t\t\t\t\t\t<table style=\"font-size:12px; float:right;\">\n" +
                    "\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t<td style=\"width:130px;\">Subtotal:</td>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>" + moneyFormat.format(order.getOrderSubTotal()) + "</td>\n" +
                    "\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>Shipping & Handling:</td>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>" + moneyFormat.format(order.getShipHandlingFee()) + "</td>\n" +
                    "\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>Collected Tax:</td>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>" + moneyFormat.format(order.getTaxAmount()) + "</td>\n" +
                    "\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t<td><strong>Total:</strong></td>\n" +
                    "\t\t\t\t\t\t\t\t\t<td>" + moneyFormat.format(order.getOrderTotal()) + "</td>\n" +
                    "\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t</table>\n" +
                    "\t\t\t\t\t\t\t<div style=\"clear:both;\"></div>\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t<div class=\"headingSection\" style=\"padding:10px; padding-left:20px; font-size:18px; text-align:left; background-color:#9F9F9F; color:#fff;\">\n" +
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
                    "\t\t\t\t\t\t<div style=\"background-color:#289EC4; padding:10px 0;\">                        \n" +
                    "\t\t\t\t\t\t\t<table style=\"position:relative; font-size:16px; text-align:center; color:#fff;\" width=\"100%\">\n" +
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



            InputStream stream = null;

            if (stream == null) {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                if (classLoader == null) {
                    classLoader = new GlistenSupport().getClass().getClassLoader();
                }

                stream = classLoader.getResourceAsStream("glistenheader.png");
            }

            DataSource ds = new ByteArrayDataSource(stream, "image/png");

            //  DataSource fds = new URLDataSource(localURL);//new URL("http://danny.owd.com/LHP.jpg"));
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(ds));

            messageBodyPart.setHeader("Content-ID", "<image.png>");
            Vector to = new Vector();
            to.addElement(overrideToAddress == null ? order.getBillEmailAddress() : overrideToAddress);
            log.debug(overrideToAddress == null ? order.getBillEmailAddress() : overrideToAddress);
            Mailer.sendHTMLMailWithEmbededPic(subject, "", to.toArray(), null, null, html, "GLISTEN <hello@glisten.com>", messageBodyPart);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        */
    }

    public static void sendShipmentConfirmationEmail(int orderId, String overrideToAddress, ServletContext context) throws Exception {
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
                    "\t\t\t\t\t\t\t<img src=\"cid:image.png\" style=\"position:relative; top:8px;\">\n" +
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


            InputStream stream = null;

            if (stream == null) {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                if (classLoader == null) {
                    classLoader = new GlistenSupport().getClass().getClassLoader();
                }

                stream = classLoader.getResourceAsStream("glistenheader.png");
            }

            DataSource ds = new ByteArrayDataSource(stream, "image/png");

            //  DataSource fds = new URLDataSource(localURL);//new URL("http://danny.owd.com/LHP.jpg"));
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(ds));
            messageBodyPart.setHeader("Content-ID", "<image.png>");
            Vector to = new Vector();
            to.addElement(overrideToAddress == null ? order.getBillEmailAddress() : overrideToAddress);
            log.debug(overrideToAddress == null ? order.getBillEmailAddress() : overrideToAddress);
          //  Mailer.sendHTMLMailWithEmbededPic(subject, "", to.toArray(), null, null, html, "GLISTEN <hello@glisten.com>", messageBodyPart);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
