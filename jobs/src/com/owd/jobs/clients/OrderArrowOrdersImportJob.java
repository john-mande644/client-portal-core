package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.DuplicateOrderException;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.utilities.lineItemBean;
import com.owd.jobs.jobobjects.utilities.orderInfoBean;
import org.apache.xpath.XPathAPI;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 15, 2007
 * Time: 9:00:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderArrowOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    static protected String clientID = "431";
    static protected String backorderRule = OrderXMLDoc.kBackOrderAll;
    static protected String orderDownloadURL = "http://tradingpost.oa-bsa.org/net/WebService.aspx?Login=stewart@owd.com&EncryptedPassword=53499CF50F7EBA5405403C354E58142BDE1A3C99F768F93077E5312173B03564&EDI_Name=Generic\\Orders&SELECT_Columns=*&WHERE_Column=o.OrderStatus&WHERE_Value=Processing";


    static List<String> skus = new ArrayList<String>();

    public static void main(String[] args) throws Exception{
      run();

      //  updateInventoryStockLevels();
    }



    public void internalExecute() {
        List results = new ArrayList();
        try {

            skus = new ArrayList<String>();
            ResultSet rs = HibernateSession.getResultSet("select distinct inventory_num from owd_inventory where client_fkey="+clientID);
            while(rs.next())
            {
                skus.add(rs.getString(1));
            }
            rs.close();

            WebResource catalog = new WebResource(orderDownloadURL, WebResource.kGETMethod);

            log.debug("" + orderDownloadURL);
            //  InputStream in = catalog.getResourceAsInputStream(false);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);

            factory.setValidating(false);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();


            Document doc = builder.parse(catalog.getResourceAsInputStream(false));
            log.debug(" got volusion response...");
            doc.getDocumentElement().normalize();
              DOMSource source = new DOMSource(doc);
                  StreamResult result = new StreamResult(System.out);
                  TransformerFactory tf = TransformerFactory.newInstance();
                 Transformer t = tf.newTransformer();
                //uncomment to print raw XML repsonse to console
                     t.transform(source, result);


            NodeIterator nlstatus = XPathAPI.selectNodeIterator(doc, "/xmldata/Orders");
            Node ns;
            //  log.debug("34243");
         //   int ocount=0;
            Map<String,List<String>> badSkuMap = new HashMap<String,List<String>>();

            while (((ns = nlstatus.nextNode()) != null)) {
           //     ocount++;
                log.debug("processing order");

                boolean fail=false;
                String orderReference = XPathAPI.eval(ns, "./OrderID").toString();
                log.debug(orderReference);
                if(Integer.parseInt(orderReference)<10254)
                {
                   fail = true;
                    log.debug("fail import - order reference too old : "+orderReference);

                } else {
                    String shipMethodCode = (XPathAPI.eval(ns, "./ShippingMethodID").toString());
                    if ("501".equalsIgnoreCase(shipMethodCode) || "502".equalsIgnoreCase(shipMethodCode)) {
                       log.debug("fail import - ship method code "+shipMethodCode);
                        fail = true;
                    } else {

                        NodeIterator xproducts = XPathAPI.selectNodeIterator(ns, "./OrderDetails");
                        Node xprod;
                        while ((xprod = xproducts.nextNode()) != null) {
                            try {
                                String xsku = (XPathAPI.eval(xprod, "./ProductCode").toString());
                                if (!(skus.contains(xsku)) && !(xsku.startsWith(("DSC-")))) {
                                    if(!(badSkuMap.containsKey(orderReference)))
                                    {
                                      badSkuMap.put(orderReference,new ArrayList<String>());
                                    }
                                    badSkuMap.get(orderReference).add(xsku);

                                    fail = true;
                                }
                            } catch (Exception ex) {
                               // fail = true;
                                throw new Exception(ex.getMessage());
                            }
                        }

                    }
                }
                List l = new ArrayList();
                if(!fail) {
                     l = processOrder(ns);
                }

                if (l.toString().indexOf(",") > 0) {
                    log.debug("adding to result list");
                    results.add(l);
                } else {
                    log.debug("success");

                }


            }

            StringBuffer sbx = new StringBuffer();
            Iterator it = results.iterator();
            while (it.hasNext()) {
                sbx.append("\r\n" + it.next());
            }
            Vector emailsx = new Vector();
            emailsx.add("owditadmin@owd.com");


            if (results.size() > 0) {
              //  Mailer.postMailMessage("Order of the Arrow Import Error", "The following orders were not imported due to the indicated error:\r\n\r\n" + sbx.toString(), emailsx, "donotreply@owd.com");
            }

            for(String key:badSkuMap.keySet())
            {
                StringBuilder sb = new StringBuilder();

                for(String sku:badSkuMap.get(key))
                {
                    if(sb.length()>0)
                    {
                        sb.append(", ");
                    }
                    sb.append(sku);
                }
                log.debug(key+":"+sb.toString());
            }
            updateInventoryStockLevels();

        } catch (SAXParseException spe) {
            String err = spe.toString() +
                    "\n  Line number: " + spe.getLineNumber() +
                    "\nColumn number: " + spe.getColumnNumber() +
                    "\n Public ID: " + spe.getPublicId() +
                    "\n System ID: " + spe.getSystemId();
            log.debug(err);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    protected static Map<String, String> shipMethodMap = new HashMap<String, String>();

    static {
        shipMethodMap.put("0", "PLEASE SELECT");
        shipMethodMap.put("501", "Online Delivery / No Shipping");
        shipMethodMap.put("502", "In-Store Pickup");
        shipMethodMap.put("503", "Rates Unavailable. We will contact you.");
        shipMethodMap.put("504", "Shipping & Handling");
        shipMethodMap.put("1000", "Free Ground Shipping");
        shipMethodMap.put("1100", "Free Shipping");
        shipMethodMap.put("451", "7 Day Ground Saver");
        shipMethodMap.put("500", "USPS Priority Mail");
        shipMethodMap.put("452", "5 Day Ground");
        shipMethodMap.put("453", "3 Day Express");
        shipMethodMap.put("454", "2nd Day Express");
        shipMethodMap.put("455", "Next Day Air");
        shipMethodMap.put("109", "UPS Standard");
        shipMethodMap.put("108", "UPS Ground");
        shipMethodMap.put("107", "UPS 3 Day Select");
        shipMethodMap.put("105", "UPS 2nd Day Air");
        shipMethodMap.put("106", "UPS 2nd Day Air");
        shipMethodMap.put("102", "UPS Next Day Air");
        shipMethodMap.put("104", "UPS Next Day Air Saver");
        shipMethodMap.put("101", "UPS Next Day Air");
        shipMethodMap.put("110", "UPS Worldwide Express");
        shipMethodMap.put("111", "UPS Worldwide Express");
        shipMethodMap.put("112", "UPS Worldwide Expedited");
        shipMethodMap.put("255", "UPS World Wide Express Saver");
        shipMethodMap.put("801", "Australia Post Regular Parcel");
        shipMethodMap.put("802", "Australia Post Express Post Parcel");
        shipMethodMap.put("803", "Australia Post Sea Mail");
        shipMethodMap.put("804", "Australia Post Air Mail");
        shipMethodMap.put("805", "Australia Post ECI Documents");
        shipMethodMap.put("806", "Australia Post ECI Merchandise");
        shipMethodMap.put("807", "Australia Post Express Post International");
        shipMethodMap.put("701", "Canada Post - Regular");
        shipMethodMap.put("702", "Canada Post - Expedited");
        shipMethodMap.put("703", "Canada Post - XPresspost");
        shipMethodMap.put("704", "Canada Post - Priority Courier");
        shipMethodMap.put("705", "Canada Post - Expedited Evening");
        shipMethodMap.put("706", "Canada Post - Xpresspost Evening");
        shipMethodMap.put("707", "Canada Post - Expedited Saturday");
        shipMethodMap.put("708", "Canada Post - Xpresspost Saturday");
        shipMethodMap.put("709", "Canada Post - Surface US");
        shipMethodMap.put("710", "Canada Post - Expedited US Business");
        shipMethodMap.put("711", "Canada Post - Xpresspost USA");
        shipMethodMap.put("712", "Canada Post - Purolator US");
        shipMethodMap.put("713", "Canada Post - Puropack US");
        shipMethodMap.put("714", "Canada Post - Surface International");
        shipMethodMap.put("715", "Canada Post - Air International");
        shipMethodMap.put("716", "Canada Post - Purolator International");
        shipMethodMap.put("717", "Canada Post - Puropack International");
        shipMethodMap.put("718", "Canada Post - Expedited US Commercial");
        shipMethodMap.put("251", "DHL WorldWide Express");
        shipMethodMap.put("252", "DHL Ground");
        shipMethodMap.put("253", "DHL 2nd Day");
        shipMethodMap.put("254", "DHL Next Day 3:00 PM");
        shipMethodMap.put("141", "FedEx Ground");
        shipMethodMap.put("142", "FedEx Home Delivery");
        shipMethodMap.put("151", "FedEx Express Saver");
        shipMethodMap.put("152", "FedEx 2Day");
        shipMethodMap.put("153", "FedEx Standard Overnight");
        shipMethodMap.put("154", "FedEx Priority Overnight");
        shipMethodMap.put("155", "FedEx First Overnight");
        shipMethodMap.put("156", "FedEx International Economy");
        shipMethodMap.put("157", "FedEx International Priority");
        shipMethodMap.put("158", "FedEx International First");
        shipMethodMap.put("159", "FedEx International Ground");
        shipMethodMap.put("602", "Airborne Express");
        shipMethodMap.put("603", "Australia Post");
        shipMethodMap.put("604", "Yellow Freight");
        shipMethodMap.put("605", "Emery Worldwide");
        shipMethodMap.put("606", "BAX Global");
        shipMethodMap.put("607", "FedEx Freight");
        shipMethodMap.put("851", "Royal Mail UK Next day");
        shipMethodMap.put("852", "Royal Mail UK 48 hour");
        shipMethodMap.put("853", "Royal Mail Before 9:00 Delivery Next Day");
        shipMethodMap.put("854", "Royal Mail Before 10:00 Delivery Next Day");
        shipMethodMap.put("855", "Royal Mail Before 12:00 Delivery Next Day");
        shipMethodMap.put("201", "USPS Library Mail");
        shipMethodMap.put("205", "USPS Priority");
        shipMethodMap.put("202", "USPS Media Mail");
        shipMethodMap.put("207", "USPS First-Class Mail");
        shipMethodMap.put("203", "USPS BPM (Bound Printed Matter)");
        shipMethodMap.put("204", "USPS Parcel (7-14 Business Days)");
        shipMethodMap.put("218", "USPS Priority Mail");
        shipMethodMap.put("219", "USPS Priority Mail");
        shipMethodMap.put("206", "USPS Express (1-2 Business Day)");
        shipMethodMap.put("220", "USPS Priority Mail Express Envelope");
        shipMethodMap.put("210", "USPS First-Class Mail International");
        shipMethodMap.put("212", "USPS Global Express Guaranteed");
        shipMethodMap.put("213", "USPS Global Express Guaranteed Non-Document Rectangular");
        shipMethodMap.put("214", "USPS Priority Mail Express International");
        shipMethodMap.put("216", "USPS Priority Mail International Flat Rate Envelope");
        shipMethodMap.put("217", "USPS Priority Mail International");
    }

    protected static String getShipMethod(orderInfoBean o, String volusionShipMethodCode) {


        String shipMethodName = shipMethodMap.get(volusionShipMethodCode);
        if (shipMethodName == null) {
            log.debug("****NO VALID SHIP METHOD!!!****");
            if (o.getShippingCountry().equals("USA")) {
                shipMethodName = "USPS Priority Mail";
            } else {
                shipMethodName = "USPS Priority Mail International";
            }
        }
        shipMethodName = shipMethodName.replaceAll("&reg;", "").replaceAll("<sup>SM</sup>", "");

       if (shipMethodName.toUpperCase().indexOf("USPS GLOBAL PRIORITY") >= 0) {
            shipMethodName = "USPS Global Priority Mail";
        }else if (shipMethodName.toUpperCase().indexOf("USPS FIRST CLASS")>=0)
       {
           shipMethodName="USPS First-Class Mail";
       }else if (shipMethodName.toUpperCase().indexOf("USPS PRIORITY")>=0)
       {
           shipMethodName="USPS Priority Mail";
       }

        log.debug("(found ship method "+shipMethodName+" for code "+volusionShipMethodCode+")");
        //,case when Shippingmethodname like '%Shipping%'  THEN 'USPS Priority Mail' when Shippingmethodname like '%USPS Global Priority%' Then 'USPS Global Priority Mail'  else shippingmethodname end,
        return shipMethodName;

    }

    private static List processOrder(Node ns) {
        List response = new ArrayList();
        orderInfoBean o = new orderInfoBean();
         Order order = null;

        try{
            order = new Order(clientID);

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }


        try {
            String orderReference = XPathAPI.eval(ns, "./OrderID").toString();
            log.debug(orderReference);


            if (OrderUtilities.clientOrderReferenceExists(orderReference, clientID, false)) {
                log.debug("Duplicate order reference "+orderReference);
                throw new DuplicateOrderException();
            }
            loadInfo(ns, o);

            String shipMethodCode = (XPathAPI.eval(ns, "./ShippingMethodID").toString());

            log.debug("input ship method "+shipMethodCode) ;
            o.setShippingMethod(getShipMethod(o, shipMethodCode));
            log.debug("ship method result = "+o.getShippingMethod()) ;
            if(o.getShippingMethod().contains("No Shipping"))
            {
                return response;
            }
            NodeIterator products = XPathAPI.selectNodeIterator(ns, "./OrderDetails");
            Node prod;
            List<lineItemBean> l = new ArrayList<lineItemBean>();
            while ((prod = products.nextNode()) != null) {
                try {
                    log.debug("Checking SKU "+XPathAPI.eval(prod, "./ProductCode").toString());
                  int qty = Integer.parseInt(XPathAPI.eval(prod, "./Quantity").toString());
                  if(XPathAPI.eval(prod, "./ProductCode").toString().startsWith("DSC-"))
                    {

                       double discount = Double.parseDouble(XPathAPI.eval(prod, "./ProductPrice").toString());
                        discount*=qty;
                        log.debug("calculated discount of "+discount);
                        order.discount+=discount;
                        log.debug("New total order discount="+order.discount);
                    }   else

                    {
                    if(qty>0)
                    {
                    l.add(loadItem(prod, o));
                    }
                }
                }catch (Exception ex) {
                    throw new Exception(ex.getMessage());
                }
            }
            if (l.size() > 0) {
                //throw new Exception("No items in order");


                o.setItems(l);

                log.debug(o.getBillingFirstName());
                log.debug(o.getOrderRef());
                log.debug(o.getGrandTotal());
                log.debug(o.getShippingCity());




                order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
                //order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.backorder_rule = backorderRule;

                o.loadOwdOrder(order);

            /*    log.debug("ship method:" + order.getShippingInfo().carr_service);
                if (order.getShippingInfo().carr_service.toUpperCase().indexOf("CANADA") >= 0
                        && order.getShippingInfo().carr_service.toUpperCase().indexOf("UPS") >= 0) {
                    //   currMethods = Arrays.asList(new String[]{"TANDATA_USPS.USPS.I_PRIORITY", "CONNECTSHIP_UPS.UPS.STD"});
                    order.getShippingInfo().setShipOptions("USPS Priority Mail International", "Prepaid", "");
                }
                if (order.getShippingInfo().carr_service.toUpperCase().indexOf("PRIORITY") >= 0
                        || order.getShippingInfo().carr_service.toUpperCase().indexOf("FLAT") >= 0) {
                    OrderRater rater = new OrderRater(order);
                    List currMethods;
                    currMethods = Arrays.asList(new String[]{"TANDATA_USPS.USPS.FIRST", "CONNECTSHIP_UPS.UPS.GND"});
                    if (order.getShippingAddress().isInternational()) {
                        //   currMethods = Arrays.asList(new String[]{"TANDATA_USPS.USPS.I_PRIORITY", "CONNECTSHIP_UPS.UPS.STD"});
                        order.getShippingInfo().setShipOptions("USPS Priority Mail International", "Prepaid", "");
                    }
                    if (order.getShippingAddress().isPOAPO()) {
                        if (order.getDecimalPoundsWeight() > 0.8125) {
                            order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
                        } else {
                            order.getShippingInfo().setShipOptions("USPS First-Class Mail", "Prepaid", "");
                        }
                    } else if (!(order.getShippingAddress().isInternational())) {
                        try {
                            order.getShippingInfo().setShipOptions(rater.selectBestWay(currMethods), "Prepaid", "");
                        } catch (Exception exx) {
                            AddressManager.checkAndCorrectAddress(order.getShippingInfo().shipAddress, "OWD.1ST.PRIORITY");
                            order.getShippingInfo().setShipOptions(rater.selectBestWay(currMethods), "Prepaid", "");
                        }
                    }
                    //log.debug("Shipping method: "+method);
                }*/
                //fix payment status, do more validation stuff?
                order.recalculateBalance();
                log.debug("ship method:" + order.getShippingInfo().carr_service);
                //log.debug("Settting insureance XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
          //      order.getShippingInfo().ss_declared_value = "1";
          //      order.getShippingInfo().declared_value = (order.total_product_cost / 2.00) + "";


                order.recalculateBalance();

                order.paid_amount = order.total_order_cost;

                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.is_paid = 1;
                order.order_type = "Volusion Download";
                  order.recalculateBalance();

             //   order.addInsertItemIfAvailable("Return Slip",1);

          //      order.is_future_ship = 1;

                String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
                log.debug(reference);
                if(reference==null && order.last_error.contains("no physical items included in shipment"))
                {
                    Date now = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    log.debug("marking "+orderReference+" shipped");
                    sendOldOrderShipConfirmation(orderReference,sdf.format(now));
                }

                log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                      if (order.is_backorder == 1 && reference != null) {
                    if (null != order.getBillingContact().email) {
                        sendBackorderNotificationMessage(order);
                    }
                }
            }
        } catch (DuplicateOrderException e) {

            //   e.printStackTrace();
            //ignore these

            //     response.add(o.getOrderRef() + ", " + e.getMessage());


        } catch (Exception e) {

            e.printStackTrace();


            response.add(o.getOrderRef() + ", " + e.getMessage());

        }
        return response;
    }

    private static void sendBackorderNotificationMessage(Order order) throws Exception {

      /*  Vector emails = new Vector();
        emails.addElement(order.getBillingContact().email);
        Vector bcc = new Vector();
        bcc.addElement("dnickels@owd.com");

        StringBuffer sb = new StringBuffer();


        sb.append("\n\nBackordered Items:\n");
        for (int i = 0; i < order.skuList.size(); i++) {
            LineItem item = (LineItem) order.skuList.elementAt(i);
            int available = dbUtilities.getAvailableInventory(item.inventory_fkey);
            if (available < 0) available = 0;

            if ((item.quantity_request + item.quantity_backordered) > available) {
                sb.append("\n Qty: " + (item.quantity_request + item.quantity_backordered) + " Item: (" + item.client_part_no + ") " + item.description);
            }


        }


        String body = "Dear " + order.getBillingContact().name + ", \n" +
                "\n" +
                "This e-mail is to inform you that your Glamour411 order " + order.order_refnum + " placed\n" +
                "on " + OWDUtilities.getMMDDYYSlashedDateForToday() + " contains one or more items that are currently out-of-stock.\n" +
                "\n" +
                "Please note: Most of our out-of-stock items are restocked within 5 business days\n" +
                "with few exceptions. And, you won't be charged until your order has been shipped.\n" +
                "\n" +
                "You have the following options: \n" +
                "\n" +
                "1.) Do nothing and your complete order will ship once all items are in-stock.\n" +
                "If you requested expedited shipping, please let us know whether or not you want to\n" +
                "change the shipping method\n" +
                "\n" +
                "2.) Reply to this e-mail (or call our customer service if you need the available items \n" +
                "right away) and request a partial shipment. \n" +
                "At no additional charge we will ship you the in-stock item(s) now and ship the out-of-stock\n" +
                "item(s) upon product arrival (Sorry, this option not applicable on International orders). If you" +
                " requested expedited shipping for your order, and we do a partial shipment, the first shipment " +
                "will be sent expedited and the back-order will be shipped standard shipping unless you opt to " +
                "pay for expedited shipping again.\n" +
                "\n" +
                "3.) At any time you can reply to this e-mail (or call our customer service) to cancel this\n" +
                "order or any portion of this order that has not yet shipped.\n" +
                "\n" +
                "4.) If your order is needed for a special occasion that requires it be delivered by\n" +
                "a specific date, call or email us. We can check our retail boutique for availability, or may be\n" +
                "able to suggest an alternative color/style.\n" +
                "\n" +
                "Sincerely, \n" +
                "\n" +
                "\n" +
                "Margaret Knight\n" +
                "Customer Service Manager\n" +
                "401-322-1300\n" +
                "www.GalapagosBoutique.com";
        body = body + sb.toString();

        Mailer.postMailMessage("Order " + order.order_refnum + " Status Update", body, emails, null, bcc, "backorder@galapagosboutique.com");
 */   }

    private static lineItemBean loadItem(Node ns, orderInfoBean o) throws Exception {
        lineItemBean li = new lineItemBean();

        li.setInventory_num(XPathAPI.eval(ns, "./ProductCode").toString());
        li.setDesc(XPathAPI.eval(ns, "./ProductName").toString());
        if(li.getInventory_num().toUpperCase().endsWith("-DS")
                ||(li.getInventory_num().toUpperCase().equals("2006-43"))
                ||(li.getInventory_num().toUpperCase().equals("2006-44"))
                ||(li.getInventory_num().toUpperCase().equals("2009-80"))
                ||(li.getInventory_num().toUpperCase().equals("2009-79"))
                )


        {
            //drop ship item
            li.setDesc("("+li.getInventory_num()+") / Shipping Separately / "+li.getDesc());
            li.setInventory_num("SHIPPING SEPARATELY");
        }

        li.setQuanity(XPathAPI.eval(ns, "./Quantity").toString());
        li.setPrice(XPathAPI.eval(ns, "./ProductPrice").toString());
        li.setSubtotal(XPathAPI.eval(ns, "./Totalprice").toString());
        log.debug(li);

        return li;
    }

    private static void loadInfo(Node ns, orderInfoBean o) throws Exception {

        o.setOrderRef(XPathAPI.eval(ns, "./OrderID").toString());
        o.setBillingFirstName(XPathAPI.eval(ns, "./BillingFirstName").toString());
        o.setBillingLastName(XPathAPI.eval(ns, "./BillingLastName").toString());
        //  o.setBillingEmail(XPathAPI.eval(ns, "./Billing/Email").toString());
        o.setBillingPhone1(XPathAPI.eval(ns, "./BillingPhoneNumber").toString());
        o.setBillingAddress1(XPathAPI.eval(ns, "./BillingAddress1").toString());
        o.setBillingAddress2(XPathAPI.eval(ns, "./BillingAddress2").toString());
        o.setBillingCity(XPathAPI.eval(ns, "./BillingCity").toString());
        o.setBillingState(XPathAPI.eval(ns, "./BillingState").toString());
        o.setBillingZip(XPathAPI.eval(ns, "./BillingPostalCode").toString());
        o.setBillingCountry(XPathAPI.eval(ns, "./BillingCountry").toString());
        o.setBillingEmail(getCustomerEmailAddressFromCustomerID(XPathAPI.eval(ns, "./CustomerID").toString()));
        log.debug("captured billing email "+o.getBillingEmail()+" from custID:"+XPathAPI.eval(ns, "./CustomerID")+" and Order ID:"+o.getOrderRef());
        o.setBillingCompany(XPathAPI.eval(ns, "./BillingCompanyName").toString());
        o.setShippingFirstName(XPathAPI.eval(ns, "./ShipFirstName").toString());
        o.setShippingLastName(XPathAPI.eval(ns, "./ShipLastName").toString());

        o.setShippingPhone1(XPathAPI.eval(ns, "./ShipPhoneNumber").toString());
        o.setShippingAddress1(XPathAPI.eval(ns, "./ShipAddress1").toString());
        o.setShippingAddress2(XPathAPI.eval(ns, "./ShipAddress2").toString());
        o.setShippingCity(XPathAPI.eval(ns, "./ShipCity").toString());
        o.setShippingState(XPathAPI.eval(ns, "./ShipState").toString());
        o.setShippingZip(XPathAPI.eval(ns, "./ShipPostalCode").toString());
        o.setShippingCountry(XPathAPI.eval(ns, "./ShipCountry").toString());
        o.setShippingCompany(XPathAPI.eval(ns, "./ShipCompanyName").toString());

        // System.out.printf("Tax : %s\n",XPathAPI.eval(ns, "./Totals/Tax/TaxAmount").toString());
        o.setTax("" + (Double.parseDouble(XPathAPI.eval(ns, "./SalesTax1").toString())
                + Double.parseDouble(XPathAPI.eval(ns, "./SalesTax2").toString())
                + Double.parseDouble(XPathAPI.eval(ns, "./SalesTax3").toString())));

        o.setShippingandHandling(XPathAPI.eval(ns, "./TotalShippingCost").toString());

    }


    static protected void updateInventoryStockLevels() {

        try {

            ResultSet rs = HibernateSession.getResultSet("select inventory_num,qty_on_hand,(is_auto_inventory) as auto, case when (select count(*) from owd_inventory_required_skus s where s.inventory_fkey=inventory_id)>0 then 1 else 0 end as kit\n" +
                    " from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey and client_fkey="+clientID);
            log.debug("got rs");
                       WebResource server = new WebResource("http://tradingpost.oa-bsa.org/net/WebService.aspx?Login=stewart@owd.com&EncryptedPassword=53499CF50F7EBA5405403C354E58142BDE1A3C99F768F93077E5312173B03564&" +
                        "Import=Update", WebResource.kPOSTMethod);

            int groupCounter=0;
            StringBuffer sbu = new StringBuffer();
            while (rs.next()) {
                groupCounter++;
              //  log.debug("updating galapagos inventory");




                sbu.append("<Products><ProductCode>" + rs.getString(1) + "</ProductCode>");
                sbu.append("<StockStatus>" + ((rs.getInt(3)==0?1:rs.getInt(4)==0?-1:1)*rs.getInt(2)) + "</StockStatus></Products>");

                if(groupCounter>90)
                {
                sendProductUpdateData(server, sbu);

                sbu = new StringBuffer();
                    groupCounter=0;
                }
            }
            if(groupCounter>0)
            {
                sendProductUpdateData(server, sbu);

                    groupCounter=0;
            }

        } catch (Exception ex) {
            ex.printStackTrace();


        } finally {

            HibernateSession.closeSession();
        }
    }

    private static void sendProductUpdateData(WebResource server, StringBuffer sbu) throws Exception {
        StringBuffer sbx = new StringBuffer();
        sbx.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><xmldata>");
        sbx.append(sbu.toString());
        sbx.append("</xmldata>");
      log.debug(sbx.toString()+"");
        server.setContent(sbx.toString());
        //log.debug("updating galapagos inventory");
        BufferedReader response = server.getResource();


        StringBuffer sb = new StringBuffer();
        int line;
        line =  response.read();
        while (line != -1) {
            sb.append((char) line);
            line = response.read();
        }
        log.debug("RESPONSE:"+sb);
    }


    static protected String getCustomerEmailAddressFromCustomerID(String custID) {
        String emailAddress = "";
        String custDownloadURL = "http://tradingpost.oa-bsa.org/net/WebService.aspx?Login=stewart@owd.com&EncryptedPassword=53499CF50F7EBA5405403C354E58142BDE1A3C99F768F93077E5312173B03564&EDI_Name=Generic\\Customers&SELECT_Columns=*&WHERE_Column=CustomerID&WHERE_Value=";
        try {
            WebResource catalog = new WebResource(custDownloadURL + custID, WebResource.kGETMethod);

            //  InputStream in = catalog.getResourceAsInputStream(false);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);

            factory.setValidating(false);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();


            Document doc = builder.parse(catalog.getResourceAsInputStream(false));

            doc.getDocumentElement().normalize();


            NodeIterator nlstatus = XPathAPI.selectNodeIterator(doc, "/xmldata/Customers");
            Node ns;
            //  log.debug("34243");
            while ((ns = nlstatus.nextNode()) != null) {


                emailAddress = XPathAPI.eval(ns, "./EmailAddress").toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return emailAddress;

    }


     static boolean sendOldOrderShipConfirmation(String orderReference, String shipDate) //returns true if OK to clear shipment notification flag
      {


          try {

              if(!StringUtils.isNumeric(orderReference))
              {
                  return true; //not a volusion order
              }   else
              {



                            WebResource server = new WebResource("http://tradingpost.oa-bsa.org/net/WebService.aspx?Login=stewart@owd.com&EncryptedPassword=53499CF50F7EBA5405403C354E58142BDE1A3C99F768F93077E5312173B03564&" +
                                    "Import=Update",WebResource.kPOSTMethod) ;


                  StringBuffer sbx = new StringBuffer();
                  sbx.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><xmldata><TrackingNumbers>");
                          sbx.append("<OrderID>"+orderReference+"</OrderID>");
                          sbx.append("<ShipDate>"+shipDate+"</ShipDate>");
                          sbx.append("<Shipment_Cost>0.00</Shipment_Cost>");
                          sbx.append("<TrackingNumber>"+orderReference+"NOTRACKING"+"</TrackingNumber>");
                          sbx.append("<MarkOrderShipped>true</MarkOrderShipped>");

                  sbx.append("</TrackingNumbers></xmldata>");
              //    log.debug(sbx.toString()+"");
                  server.setContent(sbx.toString());
                     //log.debug("updating galapagos order");
                    BufferedReader response = server.getResource();


                   StringBuffer sb = new StringBuffer();
                   int line = 0;
                   line = (int) response.read();
                   while (line != -1) {
                       sb.append((char)line);
                       line = response.read();
                   }


                    log.debug(sb.toString());
                   if (sb.toString().toUpperCase().indexOf("<SUCCESS>TRUE")>=0){
                      return true;
                   }

              }
                 } catch (Exception ex) {
                     ex.printStackTrace();
                     if (ex.getMessage().indexOf("Cannot insert duplicate key") > 0) {


                         return true;
                     }
                       if(ex instanceof NumberFormatException)
                   {
                       return true;
                   }

                 } finally {


                 }
                 return false;



      }
}
