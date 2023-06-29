package com.owd.jobs.clients;

import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderAuto;
import com.owd.hibernate.generated.OwdOrderAutoItem;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.DuplicateOrderException;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import com.owd.core.business.autoship.AutoShipFactory;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.*;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.utilities.lineItemBean;
import com.owd.jobs.jobobjects.utilities.orderInfoBean;
import org.apache.xpath.XPathAPI;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 15, 2007
 * Time: 9:00:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class CoralCalciumOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    static protected String clientID = "450";
    static protected String backorderRule = OrderXMLDoc.kBackOrderAll;
      static protected String orderDownloadURL = "http://ezxwp.zwhfn.servertrust.com/net/WebService.aspx?Login=servicerequests@owd.com&EncryptedPassword=F3A863D05F99B0EC45445E2360811B0F7D022554ADB3A66853E9DFD407325372&EDI_Name=Generic\\Orders&SELECT_Columns=*&WHERE_Column=o.OrderStatus&WHERE_Value=Processing";

    static protected String singleOrderDownloadURL = "http://ezxwp.zwhfn.servertrust.com/net/WebService.aspx?Login=servicerequests@owd.com&EncryptedPassword=F3A863D05F99B0EC45445E2360811B0F7D022554ADB3A66853E9DFD407325372&EDI_Name=Generic\\Orders&SELECT_Columns=*&WHERE_Column=o.OrderID&WHERE_Value=";

    static protected String paymentLogDownloadURL = "http://ezxwp.zwhfn.servertrust.com/net/WebService.aspxLogin=servicerequests@owd.com&EncryptedPassword=F3A863D05F99B0EC45445E2360811B0F7D022554ADB3A66853E9DFD407325372&EDI_Name=Generic\\Payment_Log&SELECT_Columns=*&WHERE_Column=Pay_OrderID&WHERE_Value=112";

    //http://www.banishthebulge.com/net/WebService.aspx?Login=owditadmin@owd.com&EncryptedPassword=4768C9547551DC40FB4B34BEDCA368625248BD3A4A635F3BDF3B46B091594775


    public static void main(String[] args) throws Exception {
        run();
     /*   WebResource server = new WebResource(singleOrderDownloadURL+"232", WebResource.kGETMethod);

        //log.debug("updating galapagos inventory");
        BufferedReader response = server.getResource();


        StringBuffer sb = new StringBuffer();
        int line;
        line =  response.read();
        while (line != -1) {
            sb.append((char) line);
            line = response.read();
        }
        log.debug(sb);*/

    }

    public void internalExecute() {
        List results = new ArrayList();
        try {
            WebResource catalog = new WebResource(orderDownloadURL, WebResource.kGETMethod);

            log.debug("" + orderDownloadURL);
            //  InputStream in = catalog.getResourceAsInputStream(false);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);

            factory.setValidating(false);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();


            Document doc = builder.parse(catalog.getResourceAsInputStream(false));

            doc.getDocumentElement().normalize();


            NodeIterator nlstatus = XPathAPI.selectNodeIterator(doc, "/xmldata/Orders");
            Node ns;
            //  log.debug("34243");
            while ((ns = nlstatus.nextNode()) != null) {

                log.debug("processing order");
                List l = processOrder(ns);


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
             //   Mailer.postMailMessage("Galapagos Import Error", "The following orders were not imported due to the indicated error:\r\n\r\n" + sbx.toString(), emailsx, "imports@owd.com");
            }

        //    updateInventoryStockLevels();

        } catch (SAXParseException spe) {
            String err = spe.toString() +
                    "\n  Line number: " + spe.getLineNumber() +
                    "\nColumn number: " + spe.getColumnNumber() +
                    "\n Public ID: " + spe.getPublicId() +
                    "\n System ID: " + spe.getSystemId();
            if(spe.getPublicId()==null)
            {
                try{
                    log.debug("sending mail");
                 //   Mailer.sendMail("Volusion API Communication Error","OWD was unable to obtain a valid response when requesting new orders from Volusion. We have been notified and will investigate shortly. If you recently changed your Volusion administrator password, please call or send the new password to servicerequests@owd.com to update the OWD importer.","richardb007@gmail.com","errors-do-not-reply@owd.com");

                Mailer.sendMail("Coral Calcium Volusion API Communication Error","OWD was unable to obtain a valid response when requesting new orders from Volusion. We have been notified and will investigate shortly. If you recently changed your Volusion administrator password, please call or send the new password to servicerequests@owd.com to update the OWD importer.","servicerequests@owd.com","errors-do-not-reply@owd.com");
            } catch(Exception exx){}
            }
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
        shipMethodMap.put("109", "UPS Standard Canada");
        shipMethodMap.put("108", "UPS Ground");
        shipMethodMap.put("107", "UPS 3 Day Select");
        shipMethodMap.put("105", "UPS 2nd Day Air A.M.");
        shipMethodMap.put("106", "UPS 2nd Day Air");
        shipMethodMap.put("102", "UPS Next Day Air");
        shipMethodMap.put("104", "UPS Next Day Air Saver");
        shipMethodMap.put("101", "UPS Next Day Air Early A.M.");
        shipMethodMap.put("110", "UPS Worldwide Express");
        shipMethodMap.put("111", "UPS Worldwide Express Plus");
        shipMethodMap.put("112", "UPS Worldwide Expedited");
        shipMethodMap.put("255", "UPS World Wide Saver");
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
        shipMethodMap.put("201", "USPS Library Mail Single-Piece");
        shipMethodMap.put("205", "USPS Priority Mail");
        shipMethodMap.put("202", "USPS Media Mail Single-Piece");
        shipMethodMap.put("207", "USPS First-Class Mail");
        shipMethodMap.put("203", "USPS Bnd Prt Mtr Single Piece");
        shipMethodMap.put("204", "USPS Parcel Select Nonpresort");
        shipMethodMap.put("218", "USPS Priority Mail");
        shipMethodMap.put("219", "USPS Priority Mail");
        shipMethodMap.put("206", "USPS Priority Mail Express");
        shipMethodMap.put("220", "USPS Priority Mail Express");
        shipMethodMap.put("210", "USPS First-Class Mail International");
        shipMethodMap.put("212", "USPS Global Express Guaranteed");
        shipMethodMap.put("213", "USPS Global Express Guaranteed Non-Document Rectangular");
        shipMethodMap.put("214", "USPS Priority Mail Express International");
        shipMethodMap.put("216", "USPS Priority Mail International");
        shipMethodMap.put("217", "USPS Priority Mail International");
    }

    protected static String getShipMethod(orderInfoBean o, String volusionShipMethodCode) {

        log.debug("VOLcode="+volusionShipMethodCode);
        String shipMethodName = shipMethodMap.get(volusionShipMethodCode);



        log.debug("VOLname="+shipMethodName);
        //,case when Shippingmethodname like '%Shipping%'  THEN 'USPS Priority Mail' when Shippingmethodname like '%USPS Global Priority%' Then 'USPS Global Priority Mail'  else shippingmethodname end,
        return shipMethodName;

    }

    private static List processOrder(Node ns) {
        List response = new ArrayList();
        orderInfoBean o = new orderInfoBean();
         Order order = new Order(clientID);
        try {

            log.debug("GOT ORDER "+XPathAPI.eval(ns, "./OrderID").toString());
            if (OrderUtilities.clientOrderReferenceExists(XPathAPI.eval(ns, "./OrderID").toString(), clientID, false)) {
                throw new DuplicateOrderException();
            }
            loadInfo(ns, o);


            String shipMethodCode = (XPathAPI.eval(ns, "./ShippingMethodID").toString());
            o.setShippingMethod(getShipMethod(o, shipMethodCode));

            NodeIterator products = XPathAPI.selectNodeIterator(ns, "./OrderDetails");
            Node prod;
            List<lineItemBean> l = new ArrayList<lineItemBean>();
            while ((prod = products.nextNode()) != null) {
                try {

                  if(XPathAPI.eval(prod, "./ProductCode").toString().startsWith("DSC-"))
                    {
                       int qty = Integer.parseInt(XPathAPI.eval(prod, "./Quantity").toString());
                       double discount = Double.parseDouble(XPathAPI.eval(prod, "./ProductPrice").toString());
                        discount*=qty;
                        log.debug("calculated discount of "+discount);
                        order.discount+=discount;
                        log.debug("New total order discount="+order.discount);
                    }   else

                    {

                    l.add(loadItem(prod, o));
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
                log.debug(o.getShippingState());




                order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
                //order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.backorder_rule = backorderRule;

                o.loadOwdOrder(order);


                order.getShippingInfo().ss_declared_value = "1";
                order.getShippingInfo().declared_value = (order.total_product_cost / 2.00) + "";

                String ckCountry = order.getShippingAddress().country.toUpperCase();
                if(ckCountry.indexOf("GUAM")>=0){order.getShippingAddress().state="GU";}
                 if(ckCountry.indexOf("RICO")>=0){order.getShippingAddress().state="PR";}
                 if(ckCountry.indexOf("VIRGIN ISLANDS, U.S.")>=0){order.getShippingAddress().state="VI";}

                log.debug("shipstate="+order.getShippingAddress().state);

                log.debug("ship method:" + order.getShippingInfo().carr_service);
               /* if (order.getShippingInfo().carr_service.toUpperCase().indexOf("CANADA") >= 0
                        && order.getShippingInfo().carr_service.toUpperCase().indexOf("UPS") >= 0) {
                    //   currMethods = Arrays.asList(new String[]{"TANDATA_USPS.USPS.I_PRIORITY", "CONNECTSHIP_UPS.UPS.STD"});
                    order.getShippingInfo().setShipOptions("USPS Priority Mail International", "Prepaid", "");
                }
                if (order.getShippingInfo().carr_service.toUpperCase().indexOf("PRIORITY") >= 0
                        || order.getShippingInfo().carr_service.toUpperCase().indexOf("FLAT") >= 0) {
                    OrderRater rater = new OrderRater(order);
                    if("1".equals(order.getShippingInfo().ss_declared_value))
                    {
                        try{
                        rater.insuredAmount = Float.parseFloat(order.getShippingInfo().declared_value);
                        }catch(Exception ex){ex.printStackTrace();}
                    }
                    List currMethods;
                    currMethods = Arrays.asList("TANDATA_USPS.USPS.FIRST","TANDATA_USPS.USPS.PRIORITY", "CONNECTSHIP_UPS.UPS.GND");
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


                order.recalculateBalance();

                order.paid_amount = order.total_order_cost;

                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.is_paid = 1;
                order.order_type = "Volusion Store";

           //     order.addInsertItemIfAvailable("Return Slip",1);

                // order.is_future_ship = 1;

                String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
                log.debug(reference);
                if(order.containsSKU("858800000406") && reference !=null)
                              {
                                  addSubscriptionOrder(order,order.getQuantityForSKU("858800000406"));
                              }


                log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
              if (order.is_backorder == 1 && reference != null) {
                    if (null != order.getBillingContact().email) {
                      // sendBackorderNotificationMessage(order);
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

    static void addSubscriptionOrder(Order o, long quantity) throws Exception
    {
        Session sess = HibernateSession.currentSession();
            OwdOrderAuto auto = AutoShipFactory.getNewAutoShip(450);
            auto.setBillName(o.getBillingContact().getName());
            auto.setBillPhone(o.getBillingContact().phone);
            auto.setBillEmail(o.getBillingContact().email);
            auto.setBillAddressOne(o.getBillingAddress().address_one);
            auto.setBillAddressTwo(o.getBillingAddress().address_two);
            auto.setBillCity(o.getBillingAddress().city);
            auto.setBillState(o.getBillingAddress().state);
            auto.setBillZip(o.getBillingAddress().zip);
            auto.setBillCountry(o.getBillingAddress().country);

            auto.setShipName(o.getShippingContact().getName());
            auto.setShipPhone(o.getShippingContact().phone);
            auto.setShipEmail(o.getShippingContact().email);
            auto.setShipAddressOne(o.getShippingAddress().address_one);
            auto.setShipAddressTwo(o.getShippingAddress().address_two);
            auto.setShipCity(o.getShippingAddress().city);
            auto.setShipState(o.getShippingAddress().state);
            auto.setShipZip(o.getShippingAddress().zip);
            auto.setShipCountry(o.getShippingAddress().country);

            // auto.setShipMethod(o.getShippingInfo().carr_service);
            auto.setShipInterval(new Integer(3));
            auto.setRequirePayment(new Integer(1));
            auto.setShipCost(new BigDecimal(o.total_shipping_cost));

            auto.setCreatedBy("Volusion Cart");
            auto.setCreated(Calendar.getInstance().getTime());
            auto.setStatus(new Integer(0));
            auto.setClientFkey(Integer.decode(o.clientID));
            auto.setCalendarUnit("month");
           OwdOrder source = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,new Integer(o.getID()));
        if(source!=null)
        {
          auto.getSourceOrders().add(source);
        }
            auto.setOrderSource(o.order_type);
           // Calendar expected = Calendar.getInstance();

            // expected.setTime(os.post_date);
         //   expected.add(Calendar.MONTH, 1);
            auto.setNextShipmentDate(getFirstShipmentDate().getTime());
            auto.setOrderSource(o.order_type);
          //  auto.setCcNum(o.cc_num);
         //   auto.setCcExpMon(new Integer(o.cc_exp_mon));
         //   auto.setCcExpYear(new Integer(o.cc_exp_year));
            sess.saveOrUpdate(auto);

        float subtotal = 0.00f;
        int units = 0;


            OwdOrderAutoItem autoitem = new OwdOrderAutoItem();
            autoitem.setSku("858800000406");
            autoitem.setUnitPrice(new BigDecimal(35.49));
            autoitem.setQuantity((int)quantity);


            autoitem.setOwdOrderAuto(auto);
            auto.getOwdOrderAutoItems().add(autoitem);
            sess.save(autoitem);



                     auto.setShipMethod(o.getShippingInfo().carr_service);

            sess.saveOrUpdate(auto);
          sess.flush();
      HibUtils.commit(sess);  



    }

      static public Calendar getFirstShipmentDate()
    {

         Calendar   firstShipment = Calendar.getInstance();
            firstShipment.add(Calendar.MONTH,3);


        return firstShipment;


    }


    private static lineItemBean loadItem(Node ns, orderInfoBean o) throws Exception {
        lineItemBean li = new lineItemBean();

        li.setInventory_num(XPathAPI.eval(ns, "./ProductCode").toString());
        li.setQuanity(XPathAPI.eval(ns, "./Quantity").toString());
        li.setPrice(XPathAPI.eval(ns, "./ProductPrice").toString());
        li.setSubtotal(XPathAPI.eval(ns, "./Totalprice").toString());
        li.setDesc("");//XPathAPI.eval(ns, "./ProductName").toString());
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

            ResultSet rs = HibernateSession.getResultSet("select inventory_num,qty_on_hand from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey and client_fkey=344");
            log.debug("got rs");
                       WebResource server = new WebResource("http://ezxwp.zwhfn.servertrust.com/net/WebService.aspx?Login=servicerequests@owd.com&EncryptedPassword=F3A863D05F99B0EC45445E2360811B0F7D022554ADB3A66853E9DFD407325372&" +
                        "Import=Update", WebResource.kPOSTMethod);

            int groupCounter=0;
            StringBuffer sbu = new StringBuffer();
            while (rs.next()) {
                groupCounter++;
                log.debug("updating galapagos inventory");




                sbu.append("<Products><ProductCode>" + rs.getString(1) + "</ProductCode>");
                sbu.append("<StockStatus>" + rs.getInt(2) + "</StockStatus></Products>");

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
        log.debug(sb);
    }

    static protected String getCustomerEmailAddressFromCustomerID(String custID) {
        String emailAddress = "";
        String custDownloadURL = "http://ezxwp.zwhfn.servertrust.com/net/WebService.aspx?Login=servicerequests@owd.com&EncryptedPassword=F3A863D05F99B0EC45445E2360811B0F7D022554ADB3A66853E9DFD407325372&EDI_Name=Generic\\Customers&SELECT_Columns=*&WHERE_Column=CustomerID&WHERE_Value=";
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

}
