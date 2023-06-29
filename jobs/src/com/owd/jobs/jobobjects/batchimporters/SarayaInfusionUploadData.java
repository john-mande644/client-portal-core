package com.owd.jobs.jobobjects.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.DelimitedReader;
import com.owd.core.business.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jun 18, 2007
 * Time: 11:01:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class SarayaInfusionUploadData extends OWDUploadOrdersData_1{
private final static Logger log =  LogManager.getLogger();

    public static int Sku	= 3;

   public static int Shipping = 4    ;
   public static int Id	=0  ;


  public static int Description	=20     ;
   public static int Qty=	21   ;

   public static int FirstName	=24     ;
   public static int LastName	=25 ;
   public static int Company	=26	;
   public static int Phone1	=28  ;
    
   public static int Email	=36  ;
   public static int StreetAddress1	=37    ;
   public static int StreetAddress2	=38   ;
   public static int City=	39 ;
   public static int State=	40   ;
   public static int PostalCode	=41   ;
   public static int Country	=43  ;


   public static int ShipFirstName	=5     ;
   public static int ShipLastName	=7 ;
   public static int ShipStreet1 = 10	 ;
   public static int ShipStreet2	= 11 ;
   public static int ShipCity	= 12 ;
   public static int ShipState	 = 13;
   public static int ShipPostalCode = 14;

       public Order loadOrder(Order order, int orderIndex) throws Exception {
        //log.debug("in sch loadOrder");
        if (orderIndex < 0 || orderIndex >= getOrderPositionList().size())
            throw new Exception("Could not load order index " + orderIndex + "; did not match order list size of " + getOrderPositionList().size());

        OrderData data = (OrderData) getOrderPositionList().get(orderIndex);
        DelimitedReader rdr = getDataReader();

           long itemIDTotal = 0;

        for (int row = data.rowIndexStart; row < rdr.getRowCount(); row++) {
            //log.debug("Sch loading row " + row);
            if (row == data.rowIndexStart) {
                //first row
                order.order_refnum = "IS-"+rdr.getStrValue(Id, row, "");
                if (order.order_refnum.length() < 1) {
                    throw new Exception("Client order reference was invalid or not found.");
                }
                //response.add(order.order_refnum);
                //log.debug("Sch loading ref " + order.order_refnum);

                order.getBillingContact().setName(rdr.getStrValue(FirstName, row, "")+" "+rdr.getStrValue(LastName, row, ""));
                order.getBillingContact().email = rdr.getStrValue(Email, row, "");
                order.getBillingContact().phone = rdr.getStrValue(Phone1, row, "");

                order.getBillingAddress().company_name = rdr.getStrValue(Company, row, ".");
                if (order.getBillingAddress().company_name.trim().length() < 1) order.getBillingAddress().company_name = ".";
                order.getBillingAddress().address_one = rdr.getStrValue(StreetAddress1, row, "");
                order.getBillingAddress().address_two = rdr.getStrValue(StreetAddress2, row, "");
                order.getBillingAddress().city = rdr.getStrValue(City, row, "");
                order.getBillingAddress().state = rdr.getStrValue(State, row, "");
                order.getBillingAddress().zip = rdr.getStrValue(PostalCode, row, "");
                order.getBillingAddress().country = "USA";


                order.getShippingContact().setName(order.getBillingContact().getName());
                order.getShippingContact().email = order.getBillingContact().getEmail();
                order.getShippingContact().phone = order.getBillingContact().getPhone();

                order.getShippingAddress().company_name = order.getBillingAddress().getCompany_name();
                if (order.getShippingAddress().company_name.trim().length() < 1) order.getShippingAddress().company_name = ".";

                if("".equals(rdr.getStrValue(ShipStreet1, row, "").replaceAll("null","")))
                {
                         order.getShippingAddress().address_one = rdr.getStrValue(StreetAddress1, row, "");
                order.getShippingAddress().address_two = rdr.getStrValue(StreetAddress2, row, "");
                order.getShippingAddress().city = rdr.getStrValue(City, row, "");
                order.getShippingAddress().state = rdr.getStrValue(State, row, "");
                order.getShippingAddress().zip = rdr.getStrValue(PostalCode, row, "");
                order.getShippingAddress().country = "USA";

                }   else
                {
                order.getShippingAddress().address_one = rdr.getStrValue(ShipStreet1, row, "");
                order.getShippingAddress().address_two = rdr.getStrValue(ShipStreet2, row, "");
                order.getShippingAddress().city = rdr.getStrValue(ShipCity, row, "");
                order.getShippingAddress().state = rdr.getStrValue(ShipState, row, "");
                order.getShippingAddress().zip = rdr.getStrValue(ShipPostalCode, row, "");
                order.getShippingAddress().country = "USA";
                }

                if (!CountryNames.exists(order.getBillingAddress().country.toUpperCase())) {
                    throw new Exception("Customer Country name not recognized: " + order.getBillingAddress().country.toUpperCase());
                }
                if (!CountryNames.exists(order.getShippingAddress().country.toUpperCase())) {
                    throw new Exception("Shipping Country name not recognized: " + order.getShippingAddress().country);
                }
                order.getShippingAddress().country = CountryNames.getCountryName("USA");
                order.getBillingAddress().country = CountryNames.getCountryName("USA");

                order.total_shipping_cost = 0.00f;
                order.total_tax_cost = 0.00f;
                order.discount = 0.00f;

                order.total_shipping_cost = 0.00f;

                String shipmethod = unmapShip(rdr.getStrValue(Shipping, row, ""));
                order.getShippingInfo().setShipOptions(shipmethod, "Prepaid", "");



                order.order_type = "InfusionSoft Download";
                order.ship_operator = "InfusionSoft";
                    //log.debug("Checking is_paid translation for value:"+rdr.getStrValue(kOrder_Paid, row, "nuthin"));
                //log.debug("...translates to int value:"+rdr.getIntValue(kOrder_Paid, row, -1));
                order.is_paid = 1;
                order.is_gift = "1";
                order.gift_message = "Thank you for your order!";



            }
            if(("IS-"+rdr.getStrValue(Id, row, "")).equals(order.order_refnum))
            {
                itemIDTotal += rdr.getIntValue(16,row,0);
            //product data collected for every row
                String SKU = rdr.getStrValue(Sku,row,"").trim();
                if(transMap.get(SKU)!=null)
                {
                    SKU = transMap.get(SKU);
                }
                log.debug("Saraya-IS adding SKU <"+rdr.getStrValue(Sku,row,"")+">");
            addLineItem(order,rdr.getStrValue(Sku, row, ""),
                    rdr.getStrValue(Qty, row, ""),
                    "0.00",
                    "0.00",
                    "",
                    "", "");
            }
        }

        order.order_refnum = order.order_refnum+"-"+itemIDTotal+"9";
        return order;
    }

    public static Map<String,String> transMap;

    {
     transMap = new TreeMap();

                transMap.put("Regular","USPS First-Class Mail");
                transMap.put("Priority","USPS Priority Mail");

}
     public String unmapShip(String method) throws Exception{


        if(transMap.containsKey(method)){
            return transMap.get(method).toString();
        }   else{
            return "USPS First-Class Mail";
        }
       // throw new Exception("Ship method not found in map "+method);


    }

     protected void processReader() {

        String orderRef = "thisisaninvalidorderreference";
        if (getDataReader() == null) return;

         List<String> orderrefs = new ArrayList<String>();


        for (int row = 0; row < getDataReader().getRowCount(); row++) {
            log.debug("DH handling row " + row);
            log.debug("Working on ref "+getDataReader().getStrValue(Id, row, "XXX"));
            log.debug(orderrefs);
            if (!(getDataReader().getStrValue(Id, row, "XXX").trim().equals("XXX"))) {
                if (orderRef.equals(getDataReader().getStrValue(Id, row, "XXX").trim())) {
                    //got the same order
                    log.debug("DH found extra row");
                    OrderData data = (OrderData) getOrderPositionList().get(getOrderPositionList().size() - 1);
                    data.rowsForOrder++;
                    //log.debug("DH adding item to order " + data.orderRef);
                } else {
                    //new order
                    log.debug("DH found new row");
                    if(!(orderrefs.contains(getDataReader().getStrValue(Id, row, "XXX").trim())))
                    {
                    OrderData data = new OrderData();
                    orderRef = getDataReader().getStrValue(Id, row, "XXX").trim();
                    data.orderRef = orderRef;
                    orderrefs.add(orderRef);
                    data.rowIndexStart = row;
                    data.rowsForOrder = 1;
                    getOrderPositionList().add(data);
                    }
                    log.debug("DH added entry for " + orderRef + ", row " + row);
                }
            }
        }
    }



}
