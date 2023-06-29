package com.owd.jobs.jobobjects.oneshoppingcart;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.Mailer;
import com.owd.core.WebResource;
import com.owd.core.business.Client;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.Order;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Dec 21, 2009
 * Time: 3:06:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class OneShoppingCartAPIService {
private final static Logger log =  LogManager.getLogger();

    static final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    static final int orderBatchSize = 100;

    public static List<String> getPendingOrders(String mid, String key, Date startDate, Date endDate, int offset) throws Exception {
        WebResource server = new WebResource("https://www.mcssl.com/API/" + mid + "/Orders/LIST", WebResource.kGETMethod);
        server.addParameter("KEY", key);
       // server.addParameter("LimitEndDate", df.format(endDate));
        server.addParameter("LimitStartDate", df.format(startDate));
        server.addParameter("LimitCount", "" + orderBatchSize);
        server.addParameter("LimitOffset", "" + offset);
        log.debug("end:"+df.format(endDate));
        
        log.debug("start:"+df.format(startDate));
        BufferedReader br = server.getResource();
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();

        Document doc = domBuilder.parse(new InputSource(br));
        List<String> orderLinks = new ArrayList<String>();
          Transformer serializer =
                    TransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(
                    OutputKeys.OMIT_XML_DECLARATION, "yes");
            serializer.setOutputProperty(
                    OutputKeys.INDENT, "yes");


            serializer.transform(
                    new DOMSource(doc),
                    new StreamResult(System.out));

        if ("true".equals(XPathAPI.eval(doc, "//Response/@success").toString())) {
            NodeList links = XPathAPI.selectNodeList(doc, "//Response/Orders/Order/@href");
            for (int i = 0; i < links.getLength(); i++) {
                orderLinks.add(((Node) links.item(i)).getNodeValue());
            }
        } else {
            throw new Exception("Unable to retrieve pending orders from 1shoppingcart; " + XPathAPI.eval(doc, "//Response/Error").toString());
        }
        if (XPathAPI.selectSingleNode(doc, "//Response/NextRecordSet") != null) {
           // log.debug("Running next batch (" + (offset + orderBatchSize) + " to " + (offset + orderBatchSize + orderBatchSize));
            orderLinks.addAll(getPendingOrders(mid, key, startDate, endDate, offset + orderBatchSize));
        }
        return orderLinks;

    }

    public static Document getCustomerInfo(String apiClientLink, String key) throws Exception {
        try {
            WebResource server = new WebResource(apiClientLink, WebResource.kGETMethod);
            server.addParameter("KEY", key);

            BufferedReader br = server.getResource();
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domBuilder = domFactory.newDocumentBuilder();

            Document doc = domBuilder.parse(new InputSource(br));
            if ("true".equals(XPathAPI.eval(doc, "//Response/@success").toString())) {

            } else {
                throw new Exception("Unable to retrieve customer info from 1shoppingcart; " + XPathAPI.eval(doc, "//Response/Error").toString());
            }

            /*  Transformer serializer =
           TransformerFactory.newInstance().newTransformer();
   serializer.setOutputProperty(
           OutputKeys.OMIT_XML_DECLARATION, "yes");
   serializer.setOutputProperty(
           OutputKeys.INDENT, "yes");


   serializer.transform(
           new DOMSource(doc),
           new StreamResult(System.out));*/

            return doc;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }


    }


    public static void importOneShoppingCartOrder(int clientID, String apiOrderLink, String key) throws Exception {
        try {
            WebResource server = new WebResource(apiOrderLink, WebResource.kGETMethod);
            server.addParameter("KEY", key);

            BufferedReader br = server.getResource();
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domBuilder = domFactory.newDocumentBuilder();

            Document doc = domBuilder.parse(new InputSource(br));
            
            Transformer serializer =
                   TransformerFactory.newInstance().newTransformer();
           serializer.setOutputProperty(
                   OutputKeys.OMIT_XML_DECLARATION, "yes");
           serializer.setOutputProperty(
                   OutputKeys.INDENT, "yes");


           serializer.transform(
                   new DOMSource(doc),
                   new StreamResult(System.out));

            if ("true".equals(XPathAPI.eval(doc, "//Response/@success").toString())) {
                log.debug("Order Reference=" + XPathAPI.eval(doc, "//Response/OrderInfo/Id").toString());

            } else {
                throw new Exception("Unable to retrieve pending orders from 1shoppingcart due to remote server error; " + XPathAPI.eval(doc, "//Response/Error").toString());
            }


            Client client = Client.getClientForID(clientID + "");

            ClientPolicy policy = client.getClientPolicy();


            Order order = policy.createInitializedOrder();
            order.order_refnum = XPathAPI.eval(doc, "//Response/OrderInfo/Id").toString();


            String apiCustomerLink = XPathAPI.eval(doc, "//Response/OrderInfo/ClientId/@href").toString();

            String comments = XPathAPI.eval(doc,"//Response/OrderInfo/Comments").toString();
            log.debug("COMMENT:"+comments);
            if(comments.trim().length()>1)
            {
                order.getShippingInfo().comments = "HELD FOR COMMENTS REVIEW: "+comments;
                order.is_future_ship=1;

                        
            }
            log.debug("STATUS:::"+XPathAPI.eval(doc, "//Response/OrderInfo/OrderStatusType").toString());
            if(!("Accepted".equals(XPathAPI.eval(doc, "//Response/OrderInfo/OrderStatusType").toString())))
            {
                log.debug("holding for pending payment");
                order.getShippingInfo().comments = "HELD DUE TO PENDING PAYMENT STATUS: "+comments;
                order.is_future_ship=1;
            }

            Document customerDoc = getCustomerInfo(apiCustomerLink, key);
            order.getBillingContact().phone = XPathAPI.eval(customerDoc, "//Response/ClientInfo/Phone").toString();
            order.getBillingContact().email = XPathAPI.eval(customerDoc, "//Response/ClientInfo/Email").toString();
            order.getShippingContact().email = XPathAPI.eval(customerDoc, "//Response/ClientInfo/Email").toString();
            order.getShippingContact().setName(XPathAPI.eval(doc, "//Response/OrderShippingProfileInfo/Name").toString());
            order.getShippingContact().phone = XPathAPI.eval(customerDoc, "//Response/ClientInfo/Phone").toString();


            order.getBillingContact().setName(XPathAPI.eval(customerDoc, "//Response/ClientInfo/FirstName").toString() + " " + XPathAPI.eval(customerDoc, "//Response/ClientInfo/LastName").toString());
            order.getBillingAddress().address_one = XPathAPI.eval(customerDoc, "//Response/ClientInfo/Address").toString();

            order.getBillingAddress().city = XPathAPI.eval(customerDoc, "//Response/ClientInfo/City").toString();
            order.getBillingAddress().state = XPathAPI.eval(customerDoc, "//Response/ClientInfo/StateName").toString();
            log.debug("BILLSTATE=" + XPathAPI.eval(customerDoc, "//Response/ClientInfo/StateName").toString());
            order.getBillingAddress().zip = XPathAPI.eval(customerDoc, "//Response/ClientInfo/Zip").toString();
            order.getBillingAddress().country = XPathAPI.eval(customerDoc, "//Response/ClientInfo/CountryName").toString();
            order.getShippingAddress().address_one = XPathAPI.eval(doc, "//Response/OrderShippingProfileInfo/Address1").toString();
            order.getShippingAddress().address_two = XPathAPI.eval(doc, "//Response/OrderShippingProfileInfo/Address2").toString();
            order.getShippingAddress().city = XPathAPI.eval(doc, "//Response/OrderShippingProfileInfo/City").toString();
            order.getShippingAddress().state = XPathAPI.eval(doc, "//Response/OrderShippingProfileInfo/StateName").toString();
            order.getShippingAddress().zip = XPathAPI.eval(doc, "//Response/OrderShippingProfileInfo/Zip").toString();
            order.getShippingAddress().country = CountryNames.getCountryName(XPathAPI.eval(doc, "//Response/OrderShippingProfileInfo/CountryName").toString());

            NodeList links = XPathAPI.selectNodeList(doc, "//Response/OrderInfo/LineItems/LineItemInfo");
            log.debug("item lines ="+links.getLength());
            for (int i = 0; i < links.getLength(); i++) {
                Node item = links.item(i);
                 

                log.debug("item sku ="+ XPathAPI.eval(item, "./Sku").toString().trim());

                policy.addLineItemToOrder(order, XPathAPI.eval(item, "./Sku").toString().trim(),
                        "", XPathAPI.eval(item, "./UnitPrice").toString().trim(),
                        XPathAPI.eval(item, "./Quantity").toString().trim()
                );
            }


            //  order.total_tax_cost = Float.parseFloat(mo.order.get("tax_amount"));
            order.total_shipping_cost = Float.parseFloat(XPathAPI.eval(doc, "//Response/OrderInfo/ShippingInfo/ShippingAmount").toString()+"0");
            //  order.discount = Math.abs(Float.parseFloat(mo.order.get("discount_amount")));
            String shipMethod = translateShipMethod(policy, XPathAPI.eval(doc, "//Response/OrderInfo/ShippingInfo/ShippingMethodName").toString(), order);
            order.getShippingInfo().setShipOptions(shipMethod, "Prepaid", "");
            order.recalculateBalance();
            order.order_type = "1ShoppingCart Download";
            if ("96.2.236.130".equals(XPathAPI.eval(doc, "//Response/OrderInfo/CreatedFromIp").toString())) {
                order.order_type = order.order_type + " (OWDCC)";
            }
            order.bill_cc_type = "CL";
            order.forcePayment = false;
            order.is_paid = 1;

            order.paid_amount = order.total_order_cost;


            policy.setSignatureRequired(order);

            policy.saveNewOrder(order, false);
            String orderNum = order.orderID;
            log.debug("order id=" + order.orderID);
            if (orderNum == null || "".equals(orderNum) || "0".equals(orderNum)) {
                log.debug(order.last_error);
                if(!order.last_error.toUpperCase().contains("ORDER REFERENCE ALREADY EXISTS"))
                {
                   Mailer.sendMail("Error importing 1shoppingcart order ("+order.order_refnum+")","Error was: "+order.last_error,"sales@sarayahealth.com","donotreply@owd.com");
                  //  log.debug("MAIL");
                } else
                {
                  //  throw new Exception("duplicate order id import!!");
                }
            } else {

                if(order.getShippingInfo().comments.contains("HELD FOR REVIEW"))
                {
                    Mailer.sendMail("1ShoppingCart order held for comment review ("+order.order_refnum+")","Comment is: "+order.getShippingInfo().comments,"sales@sarayahealth.com","donotreply@owd.com");
                  //  log.debug("MAIL");
                }
                policy.sendNotificationMessage(order, null, null);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            if(!(ex.getMessage()==null || "null".equalsIgnoreCase(ex.getMessage())))
            {
            Mailer.sendMail("Error importing 1shoppingcart order","Error was: "+ex.getMessage()+"\r\n\r\nFor order link: "+apiOrderLink,"sales@sarayahealth.com","donotreply@owd.com");
            }
            throw ex;
        } finally {
            HibernateSession.closeSession();
        }


    }

    public static String translateShipMethod(ClientPolicy policy, String moShip, Order order) throws Exception {
        moShip = moShip.toUpperCase();
        moShip = moShip.toUpperCase().replaceAll("SELECT SHIPPING METHOD - ", "");

        String moShipTranslated = moShip;
        log.debug("moship=" + moShip);
        if (moShip.startsWith("UPS") || moShip.startsWith("UNITED PARCEL SERVICE") || moShip.contains("UPS GROUND")) {
            //ups
            if (moShip.indexOf("NEXT DAY") > 0) {
                moShipTranslated = "UPS Next Day Air Saver";
            } else if (moShip.indexOf("GROUND") > 0) {
                moShipTranslated = "UPS Ground";
            } else if (moShip.indexOf("2ND DAY") > 0) {
                moShipTranslated = "UPS 2nd Day Air";
            } else if (moShip.indexOf("3 DAY") > 0) {
                moShipTranslated = "UPS 3 Day Select";
            } else if (moShip.indexOf("STANDARD") > 0) {
                moShipTranslated = "UPS Standard Canada";
            } else if (moShip.indexOf("EXPRESS PLUS") > 0) {
                moShipTranslated = "UPS Worldwide Express Plus";
            }else if (moShip.indexOf("EXPRESS") > 0) {
                moShipTranslated = "UPS Worldwide Express";
            }else if (moShip.indexOf("EXPEDITED") > 0) {
                moShipTranslated = "UPS Worldwide Expedited";
            }
        }
        if (moShip.startsWith("FDX") || moShip.startsWith("FEDEX")) {
            //
            //todo
        } else {
            //usps
            if (moShip.indexOf("USPS EXPRESS") >= 0) {
                moShipTranslated = "USPS Priority Mail Express International";
            } else if (moShip.indexOf("CLASS MAIL INTERNATIONAL") >= 0) {
                moShipTranslated = "USPS First-Class Mail International";
            } else if (moShip.indexOf("FIRST-CLASS") >= 0) {
                moShipTranslated = "USPS First-Class Mail";
            } else if (moShip.indexOf("PRIORITY MAIL INTERNATIONAL") >= 0) {
                moShipTranslated = "USPS Priority Mail International";
            } else if (moShip.indexOf("USPS PRIORITY") >= 0) {
                moShipTranslated = "USPS Priority Mail";
            } else if (moShip.indexOf("MEDIA MAIL") >= 0) {
                moShipTranslated = "USPS Media Mail Single-Piece";
            }
        }

        if (moShip.equals(moShipTranslated) && (!(order.orderRefnumExists(order.order_refnum))))    //ie, couldn't find it in the above list, like "Standard"
        {
            log.debug("getting real ship method through clientpolicy object from " + moShip);
            List svcCodes = new ArrayList();
            svcCodes.add("TANDATA_USPS.USPS.I_FIRST");
            svcCodes.add("TANDATA_USPS.USPS.I_PRIORITY");
            svcCodes.add("TANDATA_USPS.USPS.PRIORITY");
            svcCodes.add("TANDATA_USPS.USPS.FIRST");
            svcCodes.add("CONNECTSHIP_UPS.UPS.STD");
            svcCodes.add("CONNECTSHIP_UPS.UPS.GND");
            svcCodes.add("TANDATA_FEDEXFSMS.FEDEX.GND");
            svcCodes.add("TANDATA_FEDEXFSMS.FEDEX.FHD");
            moShipTranslated =         RateShopper.rateShop(order, svcCodes);

            // order.getShippingInfo().setShipOptions(shipType, "Prepaid", "");

            // moShipTranslated = policy.getOWDShipMethodForExternalShipMethodName("magento", moShip, order);
        }  else if (moShip.equals(moShipTranslated)){
            moShipTranslated="USPS Priority Mail";
        }
        log.debug("moshipdone=" + moShipTranslated);
        return moShipTranslated;
    }

    public static void main(String[] args) throws Exception {


            try {
            } catch (Exception ex) {
                ex.printStackTrace();
            }

    }

}
