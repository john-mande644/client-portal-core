package com.owd.core.business.order.clients;

/**
 * SFSDownloadImporter.java
 * importing order information from csv files through a Http connection
 */

import com.owd.core.*;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderImporter;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


/**
 * Liheng Qiao July 25,2002
 * <p/>
 * Order data is stored in a csv format file. Here is a sample:
 * "Invoice","Day","Month","Year","Price","Shipping+Handling","Sales_Tax","Item_Price_Total","PartName","PartNu","Quantity","ShippingMethod","ShippingName","ShippingAddr","ShippingCity","ShippingState","ShippingCountry","ShippingZip","BillingName","BillingAddr","BillingCity","BillingState","BillingCountry","BillingZip","CCNu","CCType","CCExp","Day_Phone","Night_Phone","Fax","Email","AVS_Match","Special_Order_Instructions","Auth_code","Ship Address Line 2","Bill Address Line 2","BillingCompany","ShippingCompany","CustomQuestion1","CustomQuestion2","CustomQuestion3","ShippingEmail","ShippingPhone","PaymentID","PaymentMethod","ShippingName1","ShippingName2","BillingName1","BillingName2","LoginUsername",
 * "00185367","3","7","2002","33.70","4.75","0.00","28.95","Portable Strength-Training Kit<BR>level: medium","ST-M","1","Priority Mail  ","Susan Bailey","PMB 734","Phoenix","AZ","USA","85085","Susan Bailey","PMB 734","Phoenix","AZ","USA","85085","4264290472010180","Visa","03/04","","","","susan_bailey@mindspring.com","","","Y:0350640d2299f100:YYYP:092184234098504TQB8W 01:","515 E Carefree Hwy","515 E Carefree Hwy","","","","","","susan_bailey@mindspring.com","","00185367","Visa","Susan","Bailey","Susan","Bailey","",
 */

public class SFSDownloadImporter extends OrderImporter {
private final static Logger log =  LogManager.getLogger();

    /**
     * Strategy:
     * <p/>
     * The file will be processed line by line. Then the values in each line will be parsed and stored in a Hashtable for retrieving, because we only import part of
     * all 50 fields.
     */


    //not good to hard code the header, but haven't got better solutions
    //Header names
    public static final String kOrderRefNum = "Invoice";
    public static final String kDay = "Day";
    public static final String kMonth = "Month";
    public static final String kYear = "Year";
    public static final String kOrderTotal = "Price";
    public static final String kS_H = "Shipping+Handling";
    public static final String kTax = "Sales_Tax";
    public static final String kProductTotal = "Item_Price_Total";
    public static final String kItemDesc = "PartName";
    public static final String kItemSKU = "PartNu";
    public static final String kQty = "Quantity";
    public static final String kShipMethod = "ShippingMethod";
    public static final String kShipName = "ShippingName";
    public static final String kShipAddr1 = "ShippingAddr";
    public static final String kShipCity = "ShippingCity";
    public static final String kShipState = "ShippingState";
    public static final String kShipCountry = "ShippingCountry";
    public static final String kShipZip = "ShippingZip";
    public static final String kBillName = "BillingName";
    public static final String kBillAddr1 = "BillingAddr";
    public static final String kBillCity = "BillingCity";
    public static final String kBillState = "BillingState";
    public static final String kBillCountry = "BillingCountry";
    public static final String kBillZip = "BillingZip";
    public static final String kCCNum = "CCNu";
    public static final String kCCType = "CCType";
    public static final String kCCExp = "CCExp";
    public static final String kBillPhone = "Day_Phone";
    public static final String kBillNightPhone = "Night_Phone";
    public static final String kFax = "Fax";
    public static final String kBillEmail = "Email";
    public static final String kAVSMatch = "AVS_Match";
    public static final String kSpecial = "Special_Order_Instructions";
    public static final String kAuthCode = "Auth_code";
    public static final String kShipAddr2 = "Ship Address Line 2";
    public static final String kBillAddr2 = "Bill Address Line 2";
    public static final String kBillCompany = "BillingCompany";
    public static final String kShipCompany = "ShippingCompany";
    public static final String kCustomQuestion1 = "CustomQuestion1";
    public static final String kCustomQuestion2 = "CustomQuestion2";
    public static final String kCustomQuestion3 = "CustomQuestion3";
   public static final String kPaymentID = "PaymentID";
   public static final String kPaymentMethod = "PaymentMethod";
   public static final String kShippingName1 = "ShippingName1";
   public static final String kShippingName2 = "ShippingName2";
   public static final String kBillingName1 = "BillingName1";
   public static final String kBillingName2 = "BillingName2";
   public static final String kLoginUsername = "LoginUsername";
   public static final String kCustomerIP = "CustomerIP";
   public static final String kOrder_Weight_Total = "Order_Weight_Total";
   public static final String kItem_Weight_Total = "Item_Weight_Total";
   public static final String kCustomerID = "CustomerID";
   public static final String kOrderStatus = "OrderStatus";
   public static final String kRecurring = "Recurring";
    public static final String kProductDiscount = "ProductDiscount";
   public static final String kLineItemDiscount = "RushOrderAmount";
    public static final String kCouponCode = "Coupons";
   public static final String kCustomQuestion4 = "CustomQuestion4";
   public static final String kProductMisc_001 = "ProductMisc_001";
   public static final String kProductMisc_002 = "ProductMisc_002";
   public static final String kProductMisc_003 = "ProductMisc_003";
   public static final String kProductMisc_004 = "ProductMisc_004";
   public static final String kProductMisc_005 = "ProductMisc_005";
   public static final String kProductMisc_006 = "ProductMisc_006";
   public static final String kProductMisc_007 = "ProductMisc_007";
   public static final String kProductMisc_008 = "ProductMisc_008";
   public static final String kProductMisc_009 = "ProductMisc_009";
   public static final String kProductMisc_010 = "ProductMisc_010";
   public static final String kEmail_Marketing = "Email_Marketing";
   public static final String kDateTime = "DateTime";
   public static final String kGiftCert = "GiftCert";
   public static final String kLot = "Lot";



    private final String url = "https://www.linkpointcart.net/orders/143737/";
    private final String method = "POST";
    private final String username = "143737";
    public static String client_id = "143"; //SFS
    public StringBuffer aLine;

    //password is found in billing address line 2 of order ID 2250439 (OWD ref 5913401) for Simple Fitness. SFS is responsible to update this field when the password
    // is changed on the shopping cart.

    private boolean sendDiscountMessage = false;
    private boolean sendSecureMessage = false;
    private boolean sendCatalogMessage = false;


    public void tempCatchup(String clientID, String url) {


        client_id = clientID;

        try {


            //establish connection and get a BufferedReader object

//temporary

            OwdOrder passwdOrder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,new Integer(2250439));

                WebResource client = new WebResource(url, method);
                client.addParameter("myUserName","143737");
                client.addParameter("myPassword",passwdOrder.getBillAddressTwo().trim());
                client.addParameter("myLoginContinue","Login");            imports(client);


        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            ex.printStackTrace();
            try{
            Mailer.sendMail("SFSImporter import error", sb.toString(), "support@owd.com", "support@owd.com");
        }catch(Exception exx){};}
    }

    public void checkForOrders(String clientID) {


        String current_fname = "";
        int days_before_today = 5;

        int day = 0;
        int month = 0;
        int year = 0;

        client_id = clientID;

        try {

            GregorianCalendar today = new GregorianCalendar();

            if (today.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                days_before_today = 6;
            }

            OwdOrder passwdOrder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,new Integer(2250439));

            for (int i = 0; i < days_before_today; i++) {
                day = today.get(Calendar.DAY_OF_MONTH);
                month = today.get(Calendar.MONTH);
                year = today.get(Calendar.YEAR);
                current_fname = "OnlineOrder_" + day + "_" + (month + 1) + "_" + year + ".db";
                log.debug(url + current_fname);
                log.debug("pass="+passwdOrder.getBillAddressTwo().trim());


                WebResource client = new WebResource(url + current_fname, method);
                client.addParameter("user","143737");
                client.addParameter("pass",passwdOrder.getBillAddressTwo().trim());
                client.addParameter("myLoginContinue","Login");
                imports(client);

                today.add(Calendar.DATE, -1);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            ex.printStackTrace();
         //   try{Mailer.sendMail("SFSImporter import error", sb.toString(), "support@owd.com", "support@owd.com");

     //  }catch(Exception exx){}; } catch (Throwable ext) {

            //log.debug("\nThrowable: " + ext);
       //     try{Mailer.sendMail("SFSImporter import error", "th", "support@owd.com", "support@owd.com");
       // }catch(Exception exx){};
            }   finally
        {
            // HibernateSession.closeSession();
        }


    }

    private void imports(WebResource client) {
        //log.debug("beginning sfs import ");

        boolean is_new_order = false;
        try {

            CSVReader order_data = new CSVReader(client.getResource(true), false);
            //log.debug("got sfs import file 2");
            //log.debug("got sfs import file " + order_data);
            //log.debug("got sfs import file rows:" + order_data.getRowCount());

            for (int i = 0; i < order_data.getRowCount(); i++) {
                for (int col = 0; col < order_data.getRowSize(i); col++) {
                  //  log.debug("Row "+i+", C "+col+" :"+order_data.getStrValue(col,i,"BADBADBAD"));
                }
            }


            Order order = null;

            Order last_order = null;

            for (int i = 1; i < order_data.getRowCount(); i++) {
                Hashtable order_info = new Hashtable();


                for (int col = 0; col < order_data.getRowSize(i); col++) {

                    order_info.put(order_data.getStrValue(col, 0, "").trim().replaceAll("[^\\p{Print}]", ""), order_data.getStrValue(col, i, "").trim());
                 //   log.debug("Row "+i+", "+order_data.getStrValue(col, 0, "")+":"+order_data.getStrValue(col, i, ""));
                 //   log.debug(order_data.getStrValue(col, 0, "").length()+":"+order_info.get(order_data.getStrValue(col, 0, "")));
                 //   log.debug(kOrderRefNum+":"+order_info.get(kOrderRefNum));
                }

                //check if the order has been imported

                String order_refnum = (String) order_info.get(kOrderRefNum);
                log.debug(order_refnum);
                log.debug(order_info.containsKey("Invoice"));
                if ((OrderUtilities.orderRefnumExists(order_refnum, client_id) )) {
                    log.debug("Order Ref No. " + order_refnum + " has been imported for SFS.\n");
                } else if (order_refnum == null || "".equals(order_refnum)) {

                    throw new Exception("No Order Number!");
                } else {

                    if (((last_order == null ? new Order(client_id) : last_order).order_refnum).equals(order_refnum))//check if it's another line of the same order
                    {
                        is_new_order = false;
                    } else {
                        //a new order line
                        order = new Order(client_id);//SFS's client id
                        order.order_refnum = order_refnum;
                        is_new_order = true;
                    }

                    //fill the order
                    this.fillOrder(order, order_info, is_new_order);

                    //save the completed order to OWD database
                    if (is_new_order) {
                        if (last_order != null)
                            processOrder(last_order);
                    }

                    last_order = order;
                }
            }

            //save the very last order
            if (last_order != null)
                processOrder(last_order);

        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            ex.printStackTrace();
         //   try{Mailer.sendMail("SFSImporter import error", sb.toString(), "support@owd.com", "support@owd.com");
      //  }catch(Exception exx){};
            }
    }

    private void fillOrder(Order anOrder, Hashtable order_info, boolean is_new_order) {

        String orderMessage = "";

        try {

            //general info

            float line_total = OWDUtilities.floatFromString((String) order_info.get(kProductTotal));
            if (!is_new_order) {
                anOrder.total_product_cost += line_total;
                anOrder.discount += -1.00f * (new Float((String) order_info.get(kLineItemDiscount)).floatValue());

                String ship_method = (String) order_info.get(kShipMethod);
                if(ship_method.toUpperCase().indexOf("MEDIA MAIL")<0)
                {
                    updateShipMethod(ship_method, anOrder);
                }


            } else {
                anOrder.discount += -1.00f * (new Float((String) order_info.get(kLineItemDiscount)).floatValue());
                anOrder.order_type = "Linkpoint File Download Import";
                anOrder.ship_call_date = OWDUtilities.getSQLDateForToday();
                anOrder.payment_status = OrderXMLDoc.kPaymentStatusClientManaged; //no payment processing
                anOrder.backorder_rule = OrderXMLDoc.kBackOrderAll;  //Changed per Deb 6.12.07
                anOrder.total_tax_cost = OWDUtilities.floatFromString((String) order_info.get(kTax));
                anOrder.total_order_cost = OWDUtilities.floatFromString((String) order_info.get(kOrderTotal));
                anOrder.total_shipping_cost = OWDUtilities.floatFromString((String) order_info.get(kS_H));
                anOrder.coupon_code =  (String) order_info.get(kCouponCode);
                //log.debug("coupon:"+anOrder.coupon_code);
                if(anOrder.coupon_code == null) anOrder.coupon_code = "";
              // try
              // {
               //    if(null==Coupon.dbloadByCoupon(anOrder.coupon_code,anOrder.clientID))

              //  {
             //       anOrder.coupon_code="";
             //   }
             //  }catch(Exception excoupon)
            //   {
             //      excoupon.printStackTrace();
            //       anOrder.coupon_code="";
             //  }
                //Handling "Special_Order_Instructions": copied from com.owd.business.order.clients.SFSImporter.java
                orderMessage = (String) order_info.get(kSpecial);
                if (orderMessage.length() > 0) {
                    //process order message according to rules
                    if (orderMessage.toUpperCase().indexOf("GIFT") >= 0) {
                        anOrder.is_gift = "1";
                        anOrder.gift_message = orderMessage;
                     //   anOrder.prt_invoice_reqd = 1;
                     //   anOrder.prt_pack_reqd = 0;
                    } else if (orderMessage.toUpperCase().indexOf("SHIPPING") >= 0) {
                        anOrder.getShippingInfo().whse_notes = orderMessage;
                    }
                    if (orderMessage.toUpperCase().indexOf("DISCOUNT") >= 0) {
                        sendDiscountMessage = true;
                    }
                    if (orderMessage.toUpperCase().indexOf("CATALOG") >= 0) {
                        anOrder.addLineItem("catalog", 1, 0, 0, "", "", "");
                        sendCatalogMessage = true;
                    }

                }

                String authCode = (order_info.get(kAuthCode) + " ").trim();
                if (authCode.equals("000000") || authCode.length() < 1) {
                    sendSecureMessage = true;
                    anOrder.is_future_ship = 1;
                }


                anOrder.getShippingInfo().whse_notes = (String) order_info.get(kSpecial);


                //Shipping info
                anOrder.getShippingContact().setName((String) order_info.get(kShipName));
                anOrder.getShippingAddress().company_name = (String) order_info.get(kShipCompany);
                anOrder.getShippingContact().setPhone((String) order_info.get(kBillPhone));
                anOrder.getShippingContact().setEmail((String) order_info.get(kBillEmail));
                anOrder.getShippingAddress().address_one = (String) order_info.get(kShipAddr1);
                anOrder.getShippingAddress().address_two = (String) order_info.get(kShipAddr2);
                anOrder.getShippingAddress().country = (String) order_info.get(kShipCountry);
                anOrder.getShippingAddress().city = (String) order_info.get(kShipCity);
                anOrder.getShippingAddress().state = (String) order_info.get(kShipState);
                anOrder.getShippingAddress().zip = (String) order_info.get(kShipZip);

                if(anOrder.getShippingAddress().address_one.length()<2)
                {
                    anOrder.getShippingAddress().address_one = "NO ADDRESS LINE 1 PROVIDED";
                }

                if ("".equals(anOrder.getShippingAddress().company_name)) anOrder.getShippingAddress().company_name = ".";

                String ship_method = (String) order_info.get(kShipMethod);
                updateShipMethod(ship_method, anOrder);

                 if(anOrder.getShippingAddress().country.toUpperCase().equals("ENGLAND"))
                 {
                     anOrder.getShippingAddress().country = "United Kingdom";
                 }
                //force ship method to any foreign shipping country to "Priority Mail"
                String cn = CountryNames.getCountryName(anOrder.getShippingAddress().country);
                if (!(("USA".equals(cn)) || ("CANADA".equals(cn))))
                {
                 //removed per SFS case ID 4149
                    //    anOrder.getShippingInfo().setShipOptions("USPS Global Priority Mail", "Prepaid", "");
                }
                log.debug("AVS Match:"+ ((String)order_info.get(kAVSMatch)));
                if(((String)order_info.get(kAVSMatch)).equalsIgnoreCase("NNN"))
                {
                  anOrder.is_future_ship=1;
                }

                // Billing Info
                anOrder.getBillingContact().setName((String) order_info.get(kBillName));
                anOrder.getBillingAddress().company_name = (String) order_info.get(kBillCompany);
                anOrder.getBillingContact().setPhone((String) order_info.get(kBillPhone));
                anOrder.getBillingContact().setEmail((String) order_info.get(kBillEmail));
                anOrder.getBillingAddress().address_one = (String) order_info.get(kBillAddr1);
                anOrder.getBillingAddress().address_two = (String) order_info.get(kBillAddr2);
                anOrder.getBillingAddress().city = (String) order_info.get(kBillCity);
                anOrder.getBillingAddress().state = (String) order_info.get(kBillState);
                anOrder.getBillingAddress().zip = (String) order_info.get(kBillZip);
                anOrder.getBillingAddress().country = (String) order_info.get(kBillCountry);

                if ("".equals(anOrder.getBillingAddress().company_name)) anOrder.getBillingAddress().company_name = ".";

            }
                     if(anOrder.getBillingAddress().country.toUpperCase().equals("ENGLAND"))
                 {
                     anOrder.getBillingAddress().country = "United Kingdom";
                 }
            //Item Info
            StringBuffer itemDescsb = new StringBuffer((String) order_info.get(kItemDesc));
            int startBR = -1;
            int endBR = -1;
            try {
                startBR = (itemDescsb.toString()).indexOf("<BR>");
            } catch (Exception ne) {
                //////log.debug("Null item description"+"\n");
            }

            if (startBR > 0) {
                endBR = startBR + 4;
                try {
                    itemDescsb = itemDescsb.replace(startBR, endBR, " ");
                } catch (Exception e) {
                    //////log.debug(e+ "\n");
                }
            }

            String itemDesc = itemDescsb.toString();
            String tempQty = (String) order_info.get(kQty);
            if (tempQty.indexOf(".") > 0) {
                tempQty = tempQty.substring(0, tempQty.indexOf("."));
            }
            int qty = OWDUtilities.intFromString(tempQty);
            float itemUnitPrice = (line_total / qty);
            String itemSKU = (String) order_info.get(kItemSKU);

            if (itemSKU.indexOf(":") > 0) {
                //multi-sku item
                int realSKUQty = 0;


                //add dummy item
                anOrder.addLineItem("KITITEM", qty, itemUnitPrice, line_total, itemDesc, "", "");

                //add individual SKUs
                OWDStringTokenizer skuTokens = new OWDStringTokenizer(itemSKU, ":", false);
                Vector skuVector = skuTokens.getUniqueStrings();
                Enumeration skus = skuVector.elements();
                while (skus.hasMoreElements()) {
                    String aSKU = (String) skus.nextElement();
                    realSKUQty = (skuTokens.getRepeatTimes(aSKU)) * qty;
                    if (anOrder.findLineItemForSKU(aSKU) >= 0) {
                        long old_qty = anOrder.getLineItemReqQtyForSKU(aSKU);
                        anOrder.setLineItemReqQty(aSKU, old_qty + realSKUQty);
                    } else
                        anOrder.addLineItem(aSKU, realSKUQty, 0, 0, "", "", "");
                }
            } else {
                //single sku
                anOrder.addLineItem(itemSKU, qty, itemUnitPrice, line_total, itemDesc, "", "");
            }
        } catch (Exception ex) {
            log.debug("Error processing order "+anOrder.order_refnum);
            ex.printStackTrace();
       //     try{Mailer.sendMail("SFSImporter import error", sb.toString(), " support@owd.com", "support@owd.com");
      // }catch(Exception exx){};
        }
    }

    private void updateShipMethod(String ship_method, Order anOrder) throws Exception {
        log.debug(">>Checking Ship Method "+ship_method+" for "+anOrder.order_refnum);
        String lship_method = ship_method.toLowerCase().replaceAll(" ","");
        if (lship_method.indexOf("upsground") >= 0) {
            anOrder.getShippingInfo().setShipOptions("UPS Ground", "Prepaid", "");
        } else if (lship_method.indexOf("upsground") >= 0) {
            anOrder.getShippingInfo().setShipOptions("UPS Ground", "Prepaid", "");
        }else if (lship_method.indexOf("nextday") >= 0) {
            anOrder.getShippingInfo().setShipOptions("UPS Next Day Air Saver", "Prepaid", "");
        } else if (lship_method.indexOf("globalexpress") >= 0) {
            anOrder.getShippingInfo().setShipOptions("USPS Intl Expr Mail-On Demand", "Prepaid", "");
        } else if (lship_method.indexOf("globalpriority") >= 0) {
            anOrder.getShippingInfo().setShipOptions("USPS Global Priority Mail", "Prepaid", "");
        } else if (lship_method.indexOf("globalpriority") >= 0) {
            anOrder.getShippingInfo().setShipOptions("USPS Global Priority Mail", "Prepaid", "");
        } else if (lship_method.indexOf("worldwideexpress") >= 0) {
            anOrder.getShippingInfo().setShipOptions("UPS Worldwide Express", "Prepaid", "");
        } else if (lship_method.indexOf("2ndday") >= 0) {
            anOrder.getShippingInfo().setShipOptions("UPS 2nd Day Air", "Prepaid", "");
        } else if (lship_method.indexOf("3day") >= 0) {
            anOrder.getShippingInfo().setShipOptions("UPS 3 Day Select", "Prepaid", "");
        } else if (lship_method.indexOf("canada") >= 0) {
            anOrder.getShippingInfo().setShipOptions("UPS Standard Canada", "Prepaid", "");
        } else if (lship_method.indexOf("prioritymail") >= 0) {
            anOrder.getShippingInfo().setShipOptions("Priority Mail", "Prepaid", "");
        } else if (lship_method.indexOf("uspsprioritymail") >= 0) {
            anOrder.getShippingInfo().setShipOptions("Priority Mail", "Prepaid", "");
        }else if (lship_method.toUpperCase().indexOf("MEDIAMAIL") >= 0 ||
                   lship_method.toUpperCase().indexOf("FIRST-CLASS") >= 0 ||
                   lship_method.toUpperCase().indexOf("USPSFIRSTCLASSMAIL") >= 0 ||
                   lship_method.toUpperCase().indexOf("FIRSTCLASSMAIL") >= 0)  {
            if(anOrder.getShippingAddress().isInternational())
            {
                log.debug("USPS First-Class Mail International");
                anOrder.getShippingInfo().setShipOptions("USPS First-Class Mail International", "Prepaid", "");
            }   else
            {
                log.debug("USPS First-Class Mail");

                anOrder.getShippingInfo().setShipOptions("USPS First-Class Mail", "Prepaid", "");
            }

        }else
            throw new Exception("Shipping method [" + lship_method + "] not recognized by OWD importer");
    }


    public void processOrder(Order anOrder) {

        try {
            //process order: copied from com.owd.business.order.clients.SFSImporter.java


            //changed from 200 to 150 per case 10001
            if(anOrder.total_order_cost>250.00f && anOrder.getShippingAddress().isUS())
            {
                anOrder.is_future_ship=1;
            }else if (anOrder.total_order_cost>180.00f &&
                            anOrder.getShippingAddress().isInternational())
            {

                anOrder.is_future_ship=1;
            }
            if(CountryNames.getCountryName(anOrder.getShippingAddress().country).equals("CANADA")){
                if(!anOrder.getBillingAddress().address_one.equals(anOrder.getShippingAddress().address_one)||
                        !anOrder.getBillingAddress().city.equals(anOrder.getShippingAddress().city)||
                        !anOrder.getBillingAddress().zip.equals(anOrder.getShippingAddress().zip)){
                    //log.debug("puttingorder on hold because address don't match");
                    anOrder.is_future_ship=1;
                }
            }

            log.debug("has catalog:"+anOrder.containsSKU("catalog"));
            log.debug("line count="+anOrder.getLineCount());
              if(anOrder.containsSKU("catalog") && anOrder.skuList.size()==1  )
                                   {
                                         anOrder.is_future_ship=0;
                                       anOrder.bill_cc_type="FREE";
                                       anOrder.is_paid=1;
                                       sendSecureMessage = false;
                                       log.debug("caught catalog order");

                                       }
            log.debug("On Hold status before save="+anOrder.is_future_ship);
            String reference = anOrder.saveNewOrder(OrderUtilities.kHoldForPayment, false);
            StringBuffer sb = new StringBuffer();
            StringBuffer sub = new StringBuffer();


            if (reference == null) {
                //order not saved
              //  Mailer.sendMail("SFSImporter import order not saved(" + (anOrder.order_refnum == null ? anOrder.old_refnum : anOrder.order_refnum) + ")", "", "support@owd.com", "support@owd.com");
            } else {
                if (sendDiscountMessage)
                    Mailer.sendMail("Discount Order Received by OWD", "Discount keyword found in order " + (anOrder.order_refnum == null ? anOrder.old_refnum : anOrder.order_refnum), "support@owd.com", "support@owd.com");
                if (sendSecureMessage)
                    Mailer.sendMail("Nonsecure Order Placed On Hold by OWD", "Auth code not found (insecure connection) for order " + (anOrder.order_refnum == null ? anOrder.old_refnum : anOrder.order_refnum), "support@owd.com", "support@owd.com");
                if (sendCatalogMessage)
                    Mailer.sendMail("Catalog Request Received by OWD", "Catalog keyword found in order " + (anOrder.order_refnum == null ? anOrder.old_refnum : anOrder.order_refnum), "support@owd.com", "support@owd.com");


                if (anOrder.is_future_ship == 1) {
                    if (sendSecureMessage) {
                        sub.append("SFS Order (" + (anOrder.order_refnum == null ? anOrder.old_refnum : anOrder.order_refnum) + " On Hold - No Secure Connection)");
                        sb.append("This order was placed but put on hold due to a missing auth code (insecure connection) \r\n\r\n");

                    } else {
                        sub.append("SFS Order (" + (anOrder.order_refnum == null ? anOrder.old_refnum : anOrder.order_refnum) + " On Hold)");
                        sb.append("This order was placed but put on hold. The most likely reason is:\r\n\r\n");
                        sb.append(anOrder.last_payment_error + " " + anOrder.last_error + ": " + anOrder.hold_reason + "");
                    }
                    log.debug(sb);
                } else {
                    if (anOrder.post_date != null) {
                        sub.append("SFS Order (" + (anOrder.order_refnum == null ? anOrder.old_refnum : anOrder.order_refnum) + " ");
                        sb.append("This order was placed and was posted to the warehouse:\r\n\r\n");
                        if (anOrder.backorder_order_num != null) {
                            sub.append("Partial Backorder)");
                        } else
                            sub.append("OK)");
                    } else {
                        if (anOrder.is_backorder == 1) {
                            sub.append("SFS Order (" + (anOrder.order_refnum == null ? anOrder.old_refnum : anOrder.order_refnum) + " Full Backorder)");
                            sb.append("This order imported as an active backorder:\r\n\r\n");
                        } else {
                            sub.append("SFS Order (" + (anOrder.order_refnum == null ? anOrder.old_refnum : anOrder.order_refnum) + " Error)");
                            sb.append("(INTERNAL ERROR)This order was received but did not post to the warehouse:\r\n\r\n");
                        }
                    }
                }
                sb.append("\r\n\r\nOrder Report - Ref# " + anOrder.order_refnum + " for SFS Import");
                if (anOrder.backorder_order_num != null) {
                    sb.append("\r\nPartial backorder created as reference: " + anOrder.backorder_order_num);
                }
                sb.append("\r\nCustomer: " + anOrder.getBillingContact().getName());
                sb.append("\r\nCustomer Email: " + anOrder.getBillingContact().getEmail());
                sb.append("\r\nCustomer Phone: " + anOrder.getBillingContact().getPhone());
                sb.append("\r\nCustomer City:State:Zip: " + anOrder.getBillingAddress().city + ":" + anOrder.getBillingAddress().state + ":" + anOrder.getBillingAddress().zip);
                sb.append("\r\nCustomer Country: " + anOrder.getBillingAddress().country);
                sb.append("\r\nShip Method: " + anOrder.getShippingInfo().carr_service);
                sb.append("\r\nTotal: " + anOrder.total_order_cost);
                sb.append("\r\nItems: \r\n");
                for (int i2 = 0; i2 < anOrder.skuList.size(); i2++) {
                    LineItem item = (LineItem) anOrder.skuList.elementAt(i2);
                    sb.append("\r\nReq:" + (item.quantity_request + item.quantity_backordered) + " BO:" + item.quantity_backordered + " Desc:" + item.description + " SKU:" + item.client_part_no + " @ " + item.sku_price);
                }

                Vector emails = new Vector();
                emails.addElement("support@owd.com");
                //emails.addElement("sfitness@mail.digitalputty.com");
                //Mailer.sendMail(sub.toString(),sb.toString(),emails,"support@owd.com");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            ex.printStackTrace();
          // try{ Mailer.sendMail("SFSImporter import error", sb.toString(), "support@owd.com", "support@owd.com");
  //    }catch(Exception exx){};
        }

        sendDiscountMessage = false;
        sendSecureMessage = false;
        sendCatalogMessage = false;
    }


    public static void main(String[] args) {

        SFSDownloadImporter importer2 =
                new SFSDownloadImporter();
        importer2.checkForOrders("143");
      //  importer2.tempCatchup("143", "https://www.linkpointcart.net/orders/143737/OnlineOrder_28_01_2012.db");


    }


}

