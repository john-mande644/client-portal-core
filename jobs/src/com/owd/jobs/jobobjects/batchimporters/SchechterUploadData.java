package com.owd.jobs.jobobjects.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.DelimitedReader;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.business.order.Order;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jun 18, 2007
 * Time: 11:01:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class SchechterUploadData extends OWDUploadOrdersData_1{
private final static Logger log =  LogManager.getLogger();


       public Order loadOrder(Order order, int orderIndex) throws Exception {
        //log.debug("in sch loadOrder");
        if (orderIndex < 0 || orderIndex >= getOrderPositionList().size())
            throw new Exception("Could not load order index " + orderIndex + "; did not match order list size of " + getOrderPositionList().size());

        OrderData data = (OrderData) getOrderPositionList().get(orderIndex);
        DelimitedReader rdr = getDataReader();

        for (int row = data.rowIndexStart; row < (data.rowIndexStart + data.rowsForOrder); row++) {
            //log.debug("Sch loading row " + row);
            if (row == data.rowIndexStart) {
                //first row
                order.order_refnum = rdr.getStrValue(kOrder_Reference, row, "");
                if (order.order_refnum.length() < 1) {
                    throw new Exception("Client order reference was invalid or not found.");
                }
                //response.add(order.order_refnum);
                //log.debug("Sch loading ref " + order.order_refnum);

                order.getBillingContact().setName(rdr.getStrValue(kCustomer_Name, row, ""));
                order.getBillingContact().email = rdr.getStrValue(kCustomer_Email, row, "");
                order.getBillingContact().phone = rdr.getStrValue(kCustomer_Phone, row, "");

                order.getBillingAddress().company_name = rdr.getStrValue(kCustomer_Company, row, ".");
                if (order.getBillingAddress().company_name.trim().length() < 1) order.getBillingAddress().company_name = ".";
                order.getBillingAddress().address_one = rdr.getStrValue(kCustomer_Address_1, row, "");
                order.getBillingAddress().address_two = rdr.getStrValue(kCustomer_Address_2, row, "");
                order.getBillingAddress().city = rdr.getStrValue(kCustomer_City, row, "");
                order.getBillingAddress().state = rdr.getStrValue(kCustomer_State, row, "");
                order.getBillingAddress().zip = rdr.getStrValue(kCustomer_Postal_Code, row, "");
                order.getBillingAddress().country = rdr.getStrValue(kCustomer_Country, row, "");


                order.getShippingContact().setName(rdr.getStrValue(kShip_Name, row, ""));
                order.getShippingContact().email = rdr.getStrValue(kShip_Email, row, "");
                order.getShippingContact().phone = rdr.getStrValue(kShip_Phone, row, "");

                order.getShippingAddress().company_name = rdr.getStrValue(kShip_Company, row, ".");
                if (order.getShippingAddress().company_name.trim().length() < 1) order.getShippingAddress().company_name = ".";

                order.getShippingAddress().address_one = rdr.getStrValue(kShip_Address_1, row, "");
                order.getShippingAddress().address_two = rdr.getStrValue(kShip_Address_2, row, "");
                order.getShippingAddress().city = rdr.getStrValue(kShip_City, row, "");
                order.getShippingAddress().state = rdr.getStrValue(kShip_State, row, "");
                order.getShippingAddress().zip = rdr.getStrValue(kShip_Postal_Code, row, "");
                order.getShippingAddress().country = rdr.getStrValue(kShip_Country, row, "");


                if(order.getBillingAddress().country.equalsIgnoreCase("England")) order.getBillingAddress().country = "United Kingdom";
                if(order.getShippingAddress().country.equals("England")) order.getShippingAddress().country = "United Kingdom";
                if(order.getShippingAddress().country.equals("Republic of Ireland")) order.getShippingAddress().country = "Ireland";
                 if(order.getBillingAddress().country.equals("Republic of Ireland")) order.getBillingAddress().country = "Ireland";
               if(order.getShippingAddress().country.indexOf("Caicos")>0) order.getShippingAddress().country = "Turkey";
                 if(order.getBillingAddress().country.indexOf("Caicos")>0) order.getBillingAddress().country = "Turkey";


                if(order.getShippingAddress().country.indexOf("AQ")>=0) order.getShippingAddress().country = "AG";
                if(order.getBillingAddress().country.indexOf("AQ")>=0) order.getBillingAddress().country = "AG";

                log.debug("*"+order.getBillingAddress().country+"*");
                if (!CountryNames.exists(order.getBillingAddress().country.toUpperCase())) {
                    throw new Exception("Customer Country name not recognized: " + order.getBillingAddress().country.toUpperCase());
                }
                if (!CountryNames.exists(order.getShippingAddress().country.toUpperCase())) {
                    throw new Exception("Shipping Country name not recognized: " + order.getShippingAddress().country);
                }
                order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                order.total_shipping_cost = rdr.getFloatValue(kShip_Handling_Fee, row, 0.00f);
                order.total_tax_cost = rdr.getFloatValue(kSales_Tax, row, 0.00f);
                order.discount = -1 * Math.abs(rdr.getFloatValue(kOrder_Discount, row, 0.00f));
                order.getShippingInfo().comments = rdr.getStrValue(kComments, row, "");
                order.getShippingInfo().whse_notes = rdr.getStrValue(kWarehouse_Notes, row, "");

                order.getShippingInfo().ss_saturday = rdr.getStrValue(kShip_Handling_Fee, row, "0").equals("1") ? "1" : "0";
                float cod_charge = OWDUtilities.roundFloat(rdr.getFloatValue(kCOD_Shipment, row, 0.00f));
                if (cod_charge > 0.00f) {
                    order.getShippingInfo().cod_charge = "" + cod_charge;
                    order.getShippingInfo().ss_cod = "1";
                }

                order.total_shipping_cost = rdr.getFloatValue(kShip_Handling_Fee, row, 0.00f);

                String shipmethod = unmapShip(rdr.getStrValue(kShip_Method, row, ""));
                //bill to client account
                if (rdr.getStrValue(kShip_Account, row, "").length() > 0) {
                    order.getShippingInfo().setShipOptions(shipmethod, "Third Party Billing", rdr.getStrValue(kShip_Account, row, ""));
                }

                      //log.debug("Checking shipping for ship method "+shipmethod);
                //bill to non-OWD, non-client account
                if (("Consignee".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, "")))) {
                    //log.debug("Shipping Consignee");
                    order.getShippingInfo().setShipOptions(shipmethod, "Freight Collect", "");

                } else if (("Freight Collect".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, "")))) {
                    //log.debug("Shipping Freight Collect : " + rdr.getStrValue(kBilltoShip_Account, row, ""));
                    order.getShippingInfo().setShipOptions(shipmethod, "Freight Collect", rdr.getStrValue(kBilltoShip_Account, row, ""));
                    //order.getShippingInfo().shipperAddress

                } else if (("Prepaid".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, ""))) || ("".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, "")))) {
                    //log.debug("Shipping Normal");
                    order.getShippingInfo().setShipOptions(shipmethod, "Prepaid", "");

                } else {
                    //log.debug("Shipping 3rd Party");
                    order.getShippingInfo().setShipOptions(shipmethod, "Prepaid", rdr.getStrValue(kBilltoShip_Account, row, ""));

                    order.getShippingInfo().shipperAddress = new Address(rdr.getStrValue(kBilltoShip_Company, row, ""),
                            rdr.getStrValue(kBilltoShip_Address_1, row, ""),
                            rdr.getStrValue(kBilltoShip_Address_2, row, ""),
                            rdr.getStrValue(kBilltoShip_City, row, ""),
                            rdr.getStrValue(kBilltoShip_State, row, ""),
                            rdr.getStrValue(kBilltoShip_Postal_Code, row, ""),
                            CountryNames.getCountryName(rdr.getStrValue(kBilltoShip_Country, row, "US")));

                    order.getShippingInfo().shipperContact = new Contact(rdr.getStrValue(kBilltoShip_Name, row, ""),
                            rdr.getStrValue(kBilltoShip_Phone, row, "605-845-7172"), "", "", "");

                }


                order.order_type = rdr.getStrValue(kOrder_Source, row, "OWD General Order Upload");
                order.ship_operator = rdr.getStrValue(kOrder_Taker, row, "");
                    //log.debug("Checking is_paid translation for value:"+rdr.getStrValue(kOrder_Paid, row, "nuthin"));
                //log.debug("...translates to int value:"+rdr.getIntValue(kOrder_Paid, row, -1));
                order.is_paid = rdr.getIntValue(kOrder_Paid, row, 0) > 0 ? 1 : 0;
                order.is_gift = "" + (rdr.getIntValue(kIs_Gift, row, 0) > 0 ? 1 : 0);
                order.gift_message = rdr.getStrValue(kGift_Message, row, "");
                order.cc_num = rdr.getStrValue(kCustomer_CC, row, "");
                order.cc_exp_mon = rdr.getIntValue(kCC_Expiration_Month, row, 0);
                order.cc_exp_year = rdr.getIntValue(kCC_Expiration_Year, row, 0);


                order.po_num = rdr.getStrValue(kPONumber, row, "");


            }
            if(!(rdr.getStrValue(kProduct_SKU, row, "").toUpperCase().startsWith("PLAN-"))
                    && !(rdr.getStrValue(kProduct_SKU, row, "").toUpperCase().equals("EXISTINGACCOUNT")))
            {
            //product data collected for every row
            addLineItem(order,rdr.getStrValue(kProduct_SKU, row, ""),
                    rdr.getStrValue(kProduct_Quantity, row, ""),
                    rdr.getStrValue(kProduct_Unit_Price, row, ""),
                    "0.00",
                    rdr.getStrValue(kProduct_Description, row, ""),
                    "", "");
            }
        }


        return order;
    }



     public String unmapShip(String method) throws Exception{
     Map ship = new TreeMap();
         ship.put("FedEx Standard Overnight","FedEx Standard Overnight");
                ship.put("FedEx Priority Overnight","FedEx Priority Overnight");
                ship.put("FedEx Two-day","FedEx 2Day");
         ship.put("FedEx 2Day","FedEx 2Day");
           ship.put("FedEx 2 Day","FedEx 2Day");
         ship.put("FedEx Home Delivery","FedEx Ground");
                ship.put("UPS Overnight","UPS Next Day Air");
                ship.put("UPS 2nd Day Air","UPS 2nd Day Air");
                ship.put("UPS Ground","UPS Ground");
                ship.put("USPS Priority Mail","USPS Priority Mail");
         ship.put("USPS Priority Mail Express","USPS Priority Mail Express");
         ship.put("USPS Priority Mail Express International","USPS Priority Mail Express International");
                ship.put("Free Shipping (4-7 Day Ground)","USPS First-Class Mail");
                ship.put("Free Shipping (4-7 Day)","USPS First-Class Mail");
         ship.put("Free Shipping (4-7 Business Days)","USPS First-Class Mail");
         ship.put("Free Shipping (4-7 Days)","USPS First-Class Mail");
         ship.put("UPS 3-Day Select®","UPS 3 Day Select");
         ship.put("UPS Saver","UPS Worldwide Express Saver");


        if(ship.containsKey(method)){
            return ship.get(method).toString();
        }   else{
            return method;
        }
       // throw new Exception("Ship method not found in map "+method);


    }



}
